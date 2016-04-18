package com.cqgas.gasmater.utils;

import static android.os.Environment.MEDIA_MOUNTED;

import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;

import android.content.Context;
import android.content.pm.PackageManager;
import android.os.Environment;
import android.os.StatFs;
import android.util.Log;

import com.cqgas.gasmeter.R;

/**
 * Provides application storage paths
 * 
 * @author Sergey Tarasevich (nostra13[at]gmail[dot]com)
 * @since 1.0.0
 */
public final class StorageUtils {

	private static final String EXTERNAL_STORAGE_PERMISSION = "android.permission.WRITE_EXTERNAL_STORAGE";


	/**
	 * 主题目录
	 */
	private static final String INDIVIDUAL_DIR_THEME = "app_mobile_theme";

	private static SimpleDateFormat mFormat = new SimpleDateFormat("yyyyMMdd-HHmmss");
	public static File mLocalTempImgDir = new File("weiji/images");
	public static File mLocalTempImgDirradio = new File("weiji/radio");
	public static int WEIJI_NOTIFICATION_DEFALT = 101;

	private StorageUtils() {
	}

	/**
	 * Returns application cache directory. Cache directory will be created on
	 * SD card <i>("/Android/data/[app_package_name]/cache")</i> if card is
	 * mounted and app has appropriate permission. Else - Android defines cache
	 * directory on device's file system.
	 * 
	 * @param context
	 *            Application context
	 * @return Cache {@link File directory}
	 */
	public static File getCacheDirectory(Context context) {
		File appCacheDir = null;
		if (MEDIA_MOUNTED.equals(Environment.getExternalStorageState()) && hasExternalStoragePermission(context)) {
			appCacheDir = getExternalCacheDir(context);
		}
		if (appCacheDir == null) {
			appCacheDir = context.getCacheDir();
		}
		if (appCacheDir == null) {
			Log.w("getCacheDirectory", "Can't define system cache directory! The app should be re-installed.");
		}
		return appCacheDir;
	}

	/**
	 * Returns specified application cache directory. Cache directory will be
	 * created on SD card by defined path if card is mounted and app has
	 * appropriate permission. Else - Android defines cache directory on
	 * device's file system.
	 * 
	 * @param context
	 *            Application context
	 * @param cacheDir
	 *            Cache directory path (e.g.: "AppCacheDir",
	 *            "AppDir/cache/images")
	 * @return Cache {@link File directory}
	 */
	public static File getOwnCacheDirectory(Context context, String cacheDir) {
		File appCacheDir = null;
		if (MEDIA_MOUNTED.equals(Environment.getExternalStorageState()) && hasExternalStoragePermission(context)) {
			appCacheDir = new File(Environment.getExternalStorageDirectory(), cacheDir);
		}

		if (appCacheDir == null || (!appCacheDir.exists() && !appCacheDir.mkdirs())) {
			appCacheDir = context.getCacheDir();
		}
		return appCacheDir;
	}

	private static File getExternalCacheDir(Context context) {
		File dataDir = new File(new File(Environment.getExternalStorageDirectory(), "Android"), "data");
		File appCacheDir = new File(new File(dataDir, context.getPackageName()), "cache");
		if (!appCacheDir.exists()) {
			if (!appCacheDir.mkdirs()) {
				Log.w("getExternalCacheDir", "Unable to create external cache directory");
				return null;
			}
			try {
				new File(appCacheDir, ".nomedia").createNewFile();
			} catch (IOException e) {
				Log.i("getExternalCacheDir", "Can't create \".nomedia\" file in application external cache directory");
			}
		}
		return appCacheDir;
	}

	public static String getExternal(Context context, String documentName) {

		File appCacheDir = null;
		if (MEDIA_MOUNTED.equals(Environment.getExternalStorageState()) && hasExternalStoragePermission(context)) {
			appCacheDir = getExternalCacheDir(context, documentName);
		}
		if (appCacheDir == null) {
			appCacheDir = context.getCacheDir();
		}
		if (appCacheDir == null) {
			Log.d("sun", "Can't define system cache directory! The app should be re-installed.");
		}

		return appCacheDir.toString();
	}

	private static File getExternalCacheDir(Context context, String floder) {
		File dataDir = new File(Environment.getExternalStorageDirectory(), context.getResources().getString(R.string.app_name));
		File appCacheDir = new File(dataDir, floder);
		if (!appCacheDir.exists()) {
			if (!appCacheDir.mkdirs()) {
				return null;
			}
			try {
				new File(appCacheDir, ".nomedia").createNewFile();
			} catch (IOException e) {

			}
		}
		return appCacheDir;
	}

	private static boolean hasExternalStoragePermission(Context context) {
		int perm = context.checkCallingOrSelfPermission(EXTERNAL_STORAGE_PERMISSION);
		return perm == PackageManager.PERMISSION_GRANTED;
	}

	/**
	 * 判断使用的存储位置
	 * 
	 * @return
	 */
	public static String SNA(String type) {
		String dir = null;
		String status = Environment.getExternalStorageState();
		if (status.equals(Environment.MEDIA_MOUNTED)) {
			if (type.equals("images")) {
				dir = Environment.getExternalStorageDirectory() + "/" + mLocalTempImgDir;
			} else {
				dir = Environment.getExternalStorageDirectory() + "/" + mLocalTempImgDirradio;
			}
			long sdfree = availableMemory();
			if (sdfree < 5) {
				String varser = android.os.Build.VERSION.SDK;
				int varsernum = Integer.parseInt(varser);
				if (type.equals("images")) {
					if (varsernum >= 16) {
						dir = "/storage/sdcard0/" + mLocalTempImgDir;
					} else {
						dir = "/mnt/sdcard/" + mLocalTempImgDir;
					}

				} else {
					if (varsernum >= 16) {
						dir = "/storage/sdcard0/" + mLocalTempImgDirradio;
					} else {
						dir = "/mnt/sdcard/" + mLocalTempImgDirradio;
					}
				}
				if (getRootFreeSize() < 5) {
					return null;
				}
			}
		} else {
			String varser = android.os.Build.VERSION.SDK;
			int varsernum = Integer.parseInt(varser);
			if (type.equals("images")) {
				if (varsernum >= 16) {
					dir = "/storage/sdcard0/" + mLocalTempImgDir;
				} else {
					dir = "/mnt/sdcard/" + mLocalTempImgDir;
				}
			} else {
				if (varsernum >= 16) {
					dir = "/storage/sdcard0/" + mLocalTempImgDirradio;
				} else {
					dir = "/mnt/sdcard/" + mLocalTempImgDirradio;
				}
			}
			if (getRootFreeSize() < 5) {
				return null;
			}
		}
		File file = new File(dir);
		if (!file.exists()) {
			file.mkdirs();
		}
		return file.toString();

	}

	public static boolean isExistSDCard() {
		if (Environment.getExternalStorageState().equals(Environment.MEDIA_MOUNTED)) {
			return true;
		} else
			return false;
	}

	// 判断SdCard内存是否够用
	public static long availableMemory() {
		// 可用的内存
		long availableMemory = 0;

		String state = Environment.getExternalStorageState();
		if (Environment.MEDIA_MOUNTED.equals(state)) {
			File sdcardDir = Environment.getExternalStorageDirectory();
			StatFs sf = new StatFs(sdcardDir.getPath());
			long blockSize = sf.getBlockSize();
			long availCount = sf.getAvailableBlocks();
			// 可用内存
			availableMemory = availCount * blockSize / 1024 / 1024;
		}
		return availableMemory;
	}

	public static long getRootFreeSize() {
		File root = Environment.getRootDirectory();
		StatFs sf = new StatFs(root.getPath());
		long blockSize = sf.getBlockSize();
		// long blockCount = sf.getBlockCount();
		long availCount = sf.getAvailableBlocks();
		return availCount * blockSize / 1024 / 1024;
	}

	public static void deleteFile(File file) {
		if (file.exists()) {
			if (file.isFile()) {
				file.delete();
			} else if (file.isDirectory()) {
				File files[] = file.listFiles();
				for (int i = 0; i < files.length; i++) {
					deleteFile(files[i]);
				}
			}
			file.delete();
		} else {
		}
	}

	/**
	 * 判断文件是否存在
	 * 
	 * @param path
	 *            文件是否存在
	 * @return
	 */
	public static boolean isFile(String path) {
		File file = new File(path);
		if (!file.exists()) {
			return false;
		} else {
			return true;
		}
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

}
