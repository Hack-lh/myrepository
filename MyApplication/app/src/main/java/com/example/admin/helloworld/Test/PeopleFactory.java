package com.example.admin.helloworld.Test;

/**
 * Created by admin on 2017/6/20.
 */

public class PeopleFactory {
    public static People create(int type){
        if(type==1){
            return new Chinese();
        }else if(type==2){
            return new American();
        }

        return null;
    }

}
