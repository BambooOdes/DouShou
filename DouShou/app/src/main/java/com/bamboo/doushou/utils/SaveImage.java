package com.bamboo.doushou.utils;

import android.content.Context;
import android.graphics.Bitmap;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;


public class SaveImage {

    public static String saveImage(Context context,Bitmap bitmap){
        //File appPicsDir = new File(context.getFilesDir(),"pics");
        File appPicsDir = new File(String.valueOf(context.getExternalFilesDir("pics")));
        if(appPicsDir.exists()){
            appPicsDir.mkdir();
        }
        String picName = System.currentTimeMillis() + ".jpg";
        File pic = new File(appPicsDir,picName);
        try{
            FileOutputStream fos = new FileOutputStream(pic);
            bitmap.compress(Bitmap.CompressFormat.JPEG,100,fos);
            fos.flush();
            fos.close();
        }catch (FileNotFoundException e){
            e.printStackTrace();
        }
        catch (IOException e){
            e.printStackTrace();
        }
        return pic.getAbsolutePath();
    }

}
