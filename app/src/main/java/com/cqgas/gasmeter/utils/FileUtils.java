package com.cqgas.gasmeter.utils;

import android.text.TextUtils;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.io.OutputStreamWriter;

/**
 * Created by sinash94857 on 2015/12/4.
 */
public class FileUtils {

    public static boolean writeOut(String path,String data){
        boolean result = true;
        File file = new File(path);
        try {
            OutputStream stream = new FileOutputStream(file);
            byte[] buffer = data.getBytes();// new String(data.getBytes(),"gb2312").getBytes();
            stream.write(buffer);
            stream.flush();
            stream.close();
        }catch (IOException e){
            e.printStackTrace();
            result = false;
        }
        return result;
    }
}
