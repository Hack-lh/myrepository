package com.example.myscan.activity;

import android.Manifest;
import android.content.ContentResolver;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.Matrix;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.myscan.R;
import com.example.myscan.ZXing.EncodingHandler;
import com.google.zxing.BarcodeFormat;
import com.google.zxing.EncodeHintType;
import com.google.zxing.MultiFormatWriter;
import com.google.zxing.WriterException;
import com.google.zxing.common.BitMatrix;
import com.google.zxing.qrcode.decoder.ErrorCorrectionLevel;

import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Hashtable;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class MainActivity extends AppCompatActivity {

	private static final int MY_PERMISSIONS_REQUEST_CAMERA = 1;
	private static final String TAG = "MainActivity";
	private TextView resultTextView;
	private EditText qrStrEditText;
	private ImageView qrImgImageView;
	private static final int IMAGE_HALFWIDTH = 20;
	int[] pixels = new int[2 * IMAGE_HALFWIDTH * 2 * IMAGE_HALFWIDTH];
	private Bitmap mBitmap;
	private final String IMAGE_TYPE = "image/*";

	private final int IMAGE_CODE = 0;
	private final int SCANER_CODE = 1;
	private Button scanBarCodeButton;


	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		//全屏
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,WindowManager.LayoutParams.FLAG_FULLSCREEN);
		setContentView(R.layout.activity_main);
		testCall();
		resultTextView = (TextView) this.findViewById(R.id.tv_scan_result);
		qrStrEditText = (EditText) this.findViewById(R.id.et_qr_string);
		qrImgImageView = (ImageView) this.findViewById(R.id.iv_qr_image);

		scanBarCodeButton = (Button) this
				.findViewById(R.id.btn_scan_barcode);
		scanBarCodeButton.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				Intent openCameraIntent = new Intent(MainActivity.this,
						CaptureActivity.class);
				startActivityForResult(openCameraIntent, SCANER_CODE);
			}
		});

		Button generateQRCodeButton = (Button) this
				.findViewById(R.id.btn_add_qrcode);
		generateQRCodeButton.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				try {
					String contentString = qrStrEditText.getText().toString();
					if (contentString != null
							&& contentString.trim().length() > 0) {
						Bitmap qrCodeBitmap = EncodingHandler.createQRCode(
								contentString, 350);
						saveJpeg(qrCodeBitmap);
						qrImgImageView.setImageBitmap(qrCodeBitmap);
					} else {
						Toast.makeText(MainActivity.this,
								"文本不能为空哦!", Toast.LENGTH_SHORT)
								.show();
					}

				} catch (WriterException e) {
					e.printStackTrace();
				}
			}
		});

		findViewById(R.id.btn_add_img).setOnClickListener(
				new OnClickListener() {

					@Override
					public void onClick(View v) {
						String contentString = qrStrEditText.getText()
								.toString();
						if (contentString != null && !contentString.equals("")) {
							Intent getAlbum = new Intent(
									Intent.ACTION_GET_CONTENT);
							getAlbum.setType(IMAGE_TYPE);
							startActivityForResult(getAlbum, IMAGE_CODE);
						} else {
							Toast.makeText(getApplication(), "必须先生成二维码再添加图片哦!",
									Toast.LENGTH_LONG).show();
						}
					}
				});

	}

	public void testCall()
	{
		if (ContextCompat.checkSelfPermission(this,
				Manifest.permission.CAMERA)
				!= PackageManager.PERMISSION_GRANTED)
		{

			ActivityCompat.requestPermissions(this,
					new String[]{Manifest.permission.CAMERA},
					MY_PERMISSIONS_REQUEST_CAMERA);
		} else
		{

		}
	}

	@Override
	public void onRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResults)
	{

		if (requestCode == MY_PERMISSIONS_REQUEST_CAMERA)
		{
			if (grantResults[0] == PackageManager.PERMISSION_GRANTED)
			{
			} else
			{
				// Permission Denied
				Toast.makeText(MainActivity.this, "Permission Denied", Toast.LENGTH_SHORT).show();
			}
			return;
		}
		super.onRequestPermissionsResult(requestCode, permissions, grantResults);
	}

	public Bitmap cretaeBitmap(String str) throws WriterException {

		Hashtable<EncodeHintType, Object> hints = new Hashtable<EncodeHintType, Object>();
		hints.put(EncodeHintType.ERROR_CORRECTION, ErrorCorrectionLevel.H);
		hints.put(EncodeHintType.CHARACTER_SET, "utf-8");
		hints.put(EncodeHintType.MARGIN, 1);

		BitMatrix matrix = new MultiFormatWriter().encode(str,
				BarcodeFormat.QR_CODE, 300, 300, hints);
		int width = matrix.getWidth();
		int height = matrix.getHeight();
		int halfW = width / 2;
		int halfH = height / 2;
		int[] pixels = new int[width * height];
		for (int y = 0; y < height; y++) {
			for (int x = 0; x < width; x++) {
				if (x > halfW - IMAGE_HALFWIDTH && x < halfW + IMAGE_HALFWIDTH
						&& y > halfH - IMAGE_HALFWIDTH
						&& y < halfH + IMAGE_HALFWIDTH) {
					pixels[y * width + x] = mBitmap.getPixel(x - halfW
							+ IMAGE_HALFWIDTH, y - halfH + IMAGE_HALFWIDTH);
				} else {
					if (matrix.get(x, y)) {
						pixels[y * width + x] = 0xff000000;
					} else {
						pixels[y * width + x] = 0xffffffff;
					}
				}
			}
		}
		Bitmap bitmap = Bitmap.createBitmap(width, height,
				Bitmap.Config.ARGB_8888);
		bitmap.setPixels(pixels, 0, width, 0, 0, width, height);

		return bitmap;
	}

	public String initSavePath() {
		File dateDir = Environment.getExternalStorageDirectory();
		String path = dateDir.getAbsolutePath() + "/RectPhoto/";
		File folder = new File(path);
		if (!folder.exists()) {
			folder.mkdir();
		}
		return path;
	}

	public void saveJpeg(Bitmap bm) {

		long dataTake = System.currentTimeMillis();
		String jpegName = initSavePath() + dataTake + ".jpg";

		// File jpegFile = new File(jpegName);
		try {
			FileOutputStream fout = new FileOutputStream(jpegName);
			BufferedOutputStream bos = new BufferedOutputStream(fout);

			// Bitmap newBM = bm.createScaledBitmap(bm, 600, 800, false);

			bm.compress(Bitmap.CompressFormat.JPEG, 100, bos);
			bos.flush();
			bos.close();

		} catch (IOException e) {
			// TODO Auto-generated catch block

			e.printStackTrace();
		}
	}

	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		super.onActivityResult(requestCode, resultCode, data);
		if (resultCode == RESULT_OK) {
			if (requestCode == SCANER_CODE) {
				Bundle bundle = data.getExtras();
				String scanResult = bundle.getString("result");
				resultTextView.setText(scanResult);
				//匹配Url
				String regex = "(http://|ftp://|https://|www){0,1}[^\u4e00-\u9fa5\\s]*?\\.(com|net|cn|me|tw|fr)[^\u4e00-\u9fa5\\s]*";
				Pattern pattern = Pattern.compile(regex ,Pattern.CASE_INSENSITIVE );
				Matcher matcher = pattern.matcher(scanResult);
				boolean b = matcher.find();
				Log.i(TAG , "success:" + b);
				//匹配成功
				if (b && !scanResult.contains("http://weixin.qq.com/r/")){
					Intent intent = new Intent(MainActivity.this , SecondActivity.class);
					intent.putExtra("url" , scanResult);
					startActivity(intent);
				}
			}

			if (requestCode == IMAGE_CODE) {
				try {
					ContentResolver resolver = getContentResolver();
					Uri originalUri = data.getData();

					mBitmap = MediaStore.Images.Media.getBitmap(resolver,
							originalUri);
					Matrix m = new Matrix();
					float sx = (float) 2 * IMAGE_HALFWIDTH / mBitmap.getWidth();
					float sy = (float) 2 * IMAGE_HALFWIDTH
							/ mBitmap.getHeight();
					m.setScale(sx, sy);
					mBitmap = Bitmap.createBitmap(mBitmap, 0, 0,
							mBitmap.getWidth(), mBitmap.getHeight(), m, false);
					String contentString = qrStrEditText.getText().toString();
					mBitmap = cretaeBitmap(new String(contentString.getBytes(),
							"utf-8"));
					qrImgImageView.setImageBitmap(mBitmap);
					saveJpeg(mBitmap);
				} catch (Exception e) {
					Log.e("TAG-->Error", e.toString());
				}
			}
		}
	}
}
