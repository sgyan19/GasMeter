package com.cqgas.gasmeter.utils;

import java.io.File;
import java.io.IOException;

import android.content.Context;
import android.text.TextUtils;

import com.cqgas.gasmeter.MyApplication;
import com.cqgas.gasmeter.R;

/**
 * Provides application storage paths
 * 
 * @author Sergey Tarasevich (nostra13[at]gmail[dot]com)
 * @since 1.0.0
 */
public final class StorageUtils {

	private static final String EXTERNAL_STORAGE_PERMISSION = "android.permission.WRITE_EXTERNAL_STORAGE";




	public static String getSdcardAppFile(Context context,String document){
		String sdacad = getSdcardDir();
		if(sdacad == null) return null;
		File file = new File(sdacad,context.getResources().getString(R.string.app_name));
		File appCacheDir = new File(file.getPath(), document);
		if (!appCacheDir.exists()) {
			if (!appCacheDir.mkdirs()) {
				return null;
			}
			try {
				new File(appCacheDir, ".nomedia").createNewFile();
			} catch (IOException e) {

			}
		}
		return appCacheDir.getAbsolutePath();
	}

	public static String getSdcardDir(){
		String result = null;
		File file = new File("/storage/sdcard0");
		if(file.exists() && file.isDirectory()){
			result = file.getAbsolutePath();
		}else{
			file = new File("/mnt/sdcard");
			if(file.exists() && file.isDirectory()){
				result = file.getAbsolutePath();
			}
		}
		return result;
	}

	public static void initTargetPath(){
		String sdcardDir = getSdcardDir();
		Context context = MyApplication.getContext();
		if(!TextUtils.isEmpty(sdcardDir)){
			File appDir = new File(sdcardDir,context.getResources().getString(R.string.app_name));
			if(!appDir.exists()){
				appDir.mkdirs();
			}
			TargetDirPath = appDir.getAbsolutePath();
			File targetFormPcFile = new File(TargetDirPath,context.getResources().getString(R.string.target_from_pc_file_name));
			File targetToPcFile = new File(TargetDirPath,context.getResources().getString(R.string.target_to_pc_file_name));
			TargetFormPcFilePath = targetFormPcFile.getAbsolutePath();
			TargetToPcFilePath = targetToPcFile.getAbsolutePath();
		}
	}

	public static String getTargetFormPcFilePath(){
		if(TextUtils.isEmpty(TargetFormPcFilePath)){
			initTargetPath();
		}
		return TargetFormPcFilePath;
	}

	public static String getTargetToPcFilePath(){
		if(TextUtils.isEmpty(TargetToPcFilePath)){
			initTargetPath();
		}
		return TargetToPcFilePath;
	}

	public static String getTargetDirPath(){
		if(TextUtils.isEmpty(TargetDirPath)){
			initTargetPath();
		}
		return TargetDirPath;

	}

	public static String[] getFileList(String dirPath){
		File dir = new File(dirPath);
		File[] files = dir.listFiles();
		String[] names = new String[files.length];
		for(int i = 0;i < files.length;i++){
			names[i] = files[i].getName();
		}
		return names;
	}
	private static String TargetDirPath;
	private static String TargetFormPcFilePath;
	private static String TargetToPcFilePath;
}
