package com.example.myzxing;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Build;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Toast;

import com.google.zxing.BarcodeFormat;
import com.google.zxing.client.android.CaptureActivity;
import com.google.zxing.client.android.Contents;
import com.google.zxing.client.android.Intents;
import com.google.zxing.client.android.encode.EncodeActivity;


public class MainActivity extends AppCompatActivity implements View.OnClickListener {


    private static final int REQUEST_CAMERA = 0x0000;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        findViewById(R.id.scan).setOnClickListener(this);

        findViewById(R.id.generate).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                launchSearch("第一次生成的二维码");
            }
        });
    }


    private void launchSearch(String text) {
        Intent intent = new Intent(MainActivity.this,EncodeActivity.class);
        intent.putExtra(Intents.Encode.TYPE, Contents.Type.TEXT);
        intent.putExtra(Intents.Encode.DATA, text);
        intent.putExtra(Intents.Encode.FORMAT, BarcodeFormat.QR_CODE.toString());
        startActivity(intent);
    }


    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.scan:
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                    if (checkSelfPermission(Manifest.permission.CAMERA) != PackageManager.PERMISSION_GRANTED) {
                        requestPermissions(new String[]{Manifest.permission.CAMERA}, REQUEST_CAMERA);
                    } else {
                        Intent openCameraIntent = new Intent(MainActivity.this, CaptureActivity.class);
                        startActivityForResult(openCameraIntent, 0);
                    }
                } else {
                    Intent openCameraIntent = new Intent(MainActivity.this, CaptureActivity.class);
                    startActivityForResult(openCameraIntent, 0);
                }
        }
    }


    @Override
    public void onRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResults) {
        switch (requestCode) {
            case REQUEST_CAMERA:
                if (grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    // 取得權限
                    Intent openCameraIntent = new Intent(MainActivity.this, CaptureActivity.class);
                    startActivityForResult(openCameraIntent, 0);
                } else {
                    // 未取得權限
                    Toast.makeText(MainActivity.this,"没有获得权限",Toast.LENGTH_SHORT).show();
                }
                break;
        }
    }

}
