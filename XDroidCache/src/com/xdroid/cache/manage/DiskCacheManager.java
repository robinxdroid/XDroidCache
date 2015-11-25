package com.xdroid.cache.manage;

import java.io.BufferedWriter;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.io.Serializable;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import com.xdroid.cache.disk.DiskLruCache;
import com.xdroid.cache.interfaces.ICache;
import com.xdroid.cache.utils.CacheHelper;
import com.xdroid.cache.utils.ImageHelper;

import android.content.Context;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager.NameNotFoundException;
import android.graphics.Bitmap;
import android.graphics.drawable.Drawable;
import android.os.Environment;
import android.util.Log;

/**
 * Manage the disk cache
 * 
 * @author Robin
 * @since 2015-05-07 23:31:23
 */
public class DiskCacheManager implements ICache {

	private static DiskCacheManager mCacheManager;

	private DiskLruCache mDiskLruCache;

	private static final int DEFAULT_VALUE_COUNT = 1;

	private static final int DEFAULT_MAX_SIZE = 10 * 1024 * 1024;

	private static final String TAG = "system.out";

	/*
	 * ====================================================================
	 * Constructor
	 * ====================================================================
	 */

	public DiskCacheManager(Context context) {
		init(context);
	}

	public static DiskCacheManager getInstance(Context context) {
		if (mCacheManager == null) {
			mCacheManager = new DiskCacheManager(context);
		}
		return mCacheManager;
	}

	private void init(Context context) {
		if (mDiskLruCache == null) {
			File cacheDir = getDiskCacheDir(context, "diskcache");
			if (!cacheDir.exists()) {
				cacheDir.mkdirs();
			}
			try {
				mDiskLruCache = DiskLruCache.open(cacheDir, getAppVersion(context), DEFAULT_VALUE_COUNT,
						DEFAULT_MAX_SIZE);
			} catch (IOException e) {
				e.printStackTrace();
			}
		}

	}

	/*
	 * ====================================================================
	 * Public Method
	 * ====================================================================
	 */

	public void close() throws IOException {
		mDiskLruCache.close();
	}

	public void delete() throws IOException {
		mDiskLruCache.delete();
	}

	public void flush() throws IOException {
		mDiskLruCache.flush();
	}

	public boolean isClosed() {
		return mDiskLruCache.isClosed();
	}

	public long size() {
		return mDiskLruCache.size();
	}

	public void setMaxSize(long maxSize) {
		mDiskLruCache.setMaxSize(maxSize);
	}

	public File getDirectory() {
		return mDiskLruCache.getDirectory();
	}

	public long getMaxSize() {
		return mDiskLruCache.getMaxSize();
	}

	/*
	 * =========================================================================
	 * Utilities
	 * =========================================================================
	 */

	public InputStream get(String key) {
		try {
			DiskLruCache.Snapshot snapshot = mDiskLruCache.get(hashKeyForDisk(key));
			if (snapshot == null) {
				Log.e(TAG, "Not find entry from disk , or entry.readable = false");
				return null;
			}
			return snapshot.getInputStream(0);

		} catch (IOException e) {
			e.printStackTrace();
			return null;
		}

	}

	public DiskLruCache.Editor editor(String key) {
		try {
			key = hashKeyForDisk(key);
			DiskLruCache.Editor edit = mDiskLruCache.edit(key);
			if (edit == null) {
				Log.w(TAG, "the entry spcified key:" + key + " is editing by other . ");
			}
			return edit;
		} catch (IOException e) {
			e.printStackTrace();
		}

		return null;
	}

	/**
	 * Record cache synchronization to the journal file.
	 */
	/*
	 * public void fluchCache() { if (mDiskLruCache != null) { try {
	 * mDiskLruCache.flush(); } catch (IOException e) { e.printStackTrace(); } }
	 * }
	 */

	public static String InputStreamToString(InputStream is) throws Exception {
		int BUFFER_SIZE = 4096;
		ByteArrayOutputStream outStream = new ByteArrayOutputStream();
		byte[] data = new byte[BUFFER_SIZE];
		int count = -1;
		while ((count = is.read(data, 0, BUFFER_SIZE)) != -1)
			outStream.write(data, 0, count);

		data = null;
		return new String(outStream.toByteArray(), "ISO-8859-1");
	}

	/**
	 * Using the MD5 algorithm to encrypt the key of the incoming and return.
	 */
	public String hashKeyForDisk(String key) {
		String cacheKey;
		try {
			final MessageDigest mDigest = MessageDigest.getInstance("MD5");
			mDigest.update(key.getBytes());
			cacheKey = bytesToHexString(mDigest.digest());
		} catch (NoSuchAlgorithmException e) {
			cacheKey = String.valueOf(key.hashCode());
		}
		return cacheKey;
	}

	private String bytesToHexString(byte[] bytes) {
		StringBuilder sb = new StringBuilder();
		for (int i = 0; i < bytes.length; i++) {
			String hex = Integer.toHexString(0xFF & bytes[i]);
			if (hex.length() == 1) {
				sb.append('0');
			}
			sb.append(hex);
		}
		return sb.toString();
	}

	private static int getAppVersion(Context context) {
		try {
			PackageInfo info = context.getPackageManager().getPackageInfo(context.getPackageName(), 0);
			return info.versionCode;
		} catch (NameNotFoundException e) {
			e.printStackTrace();
		}
		return 1;
	}

	/**
	 * According to the incoming a unique name for the path of the hard disk
	 * cache address.
	 */
	public static File getDiskCacheDir(Context context, String uniqueName) {
		String cachePath;
		if (Environment.MEDIA_MOUNTED.equals(Environment.getExternalStorageState())
				|| !Environment.isExternalStorageRemovable()) {
			File cacheDir = context.getExternalCacheDir();
			if (cacheDir != null) {
				cachePath = cacheDir.getPath(); /// sdcard/Android/data/<application
												/// package>/cache
			} else {
				cachePath = context.getCacheDir().getPath(); // /data/data/<application
																// package>/cache
			}

		} else {
			cachePath = context.getCacheDir().getPath(); // /data/data/<application
															// package>/cache
		}
		return new File(cachePath + File.separator + uniqueName);
	}

	/*
	 * =======================================================================
	 * Private Method
	 * =======================================================================
	 */

	private void putStringToDisk(String key, String value) {
		DiskLruCache.Editor edit = null;
		BufferedWriter bw = null;
		try {
			edit = editor(key);
			if (edit == null)
				return;
			OutputStream os = edit.newOutputStream(0);
			bw = new BufferedWriter(new OutputStreamWriter(os));
			bw.write(value);
			edit.commit();
		} catch (IOException e) {
			e.printStackTrace();
			try {
				edit.abort();
			} catch (IOException e1) {
				e1.printStackTrace();
			}
		} finally {
			try {
				if (bw != null)
					bw.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}

	private String getStringFromDisk(String key) {
		InputStream inputStream = null;
		try {
			inputStream = get(key);
			if (inputStream == null)
				return null;
			StringBuilder sb = new StringBuilder();
			int len = 0;
			byte[] buf = new byte[128];
			while ((len = inputStream.read(buf)) != -1) {
				sb.append(new String(buf, 0, len));
			}
			String result = sb.toString();

			return result;

		} catch (IOException e) {
			e.printStackTrace();
			if (inputStream != null)
				try {
					inputStream.close();
				} catch (IOException e1) {
					e1.printStackTrace();
				}
		}
		return null;
	}
	
	private void putBytesToDisk(String key, byte[] value) {
		OutputStream out = null;
		DiskLruCache.Editor editor = null;
		try {
			editor = editor(key);
			if (editor == null) {
				return;
			}
			out = editor.newOutputStream(0);
			out.write(value);
			out.flush();
			editor.commit();
		} catch (Exception e) {
			e.printStackTrace();
			try {
				editor.abort();
			} catch (IOException e1) {
				e1.printStackTrace();
			}

		} finally {
			if (out != null) {
				try {
					out.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		}
	}
	
	public byte[] getBytesFromDisk(String key) {
		byte[] res = null;
		InputStream is = get(key);
		if (is == null)
			return null;
		ByteArrayOutputStream baos = new ByteArrayOutputStream();
		try {
			byte[] buf = new byte[256];
			int len = 0;
			while ((len = is.read(buf)) != -1) {
				baos.write(buf, 0, len);
			}
			res = baos.toByteArray();
		} catch (IOException e) {
			e.printStackTrace();
		}
		
		return res;
	}

	private String checkCacheExpiredWithString(String key, String result) {
		if (result != null) {
			if (!CacheHelper.isExpired(result)) { // unexpired
				String originalResult = CacheHelper.clearDateInfo(result);
				Log.i(TAG, "Disk cache hint :" + originalResult + "---->time interval:"
						+ CacheHelper.getCacheTimeInterval(result) / 1000 + " s");
				return originalResult;
			} else {
				Log.i(TAG, "Disk cache expired:" + "---->time interval:"
						+ CacheHelper.getCacheTimeInterval(result) / 1000 + " s");
				remove(key);
				return null;
			}
		}else {
			return result;
		}

	}
	
	private byte[] checkCacheExpiredWithByte(String key, byte[] result) {
		if (result != null) {
			if (!CacheHelper.isExpired(result)) { // unexpired
				byte[] originalResult =  CacheHelper.clearDateInfo(result);
				Log.i(TAG, "Disk cache hint :" + originalResult + "---->time interval:"
						+ CacheHelper.getCacheTimeInterval(result) / 1000 + " s");
				return originalResult;
			} else {
				Log.i(TAG, "Disk cache expired:" + "---->time interval:"
						+ CacheHelper.getCacheTimeInterval(result) / 1000 + " s");
				remove(key);
				return null;
			}
		}else {
			return result;
		}
	
	}


	/*
	 * ====================================================================
	 * Override ICache
	 * ====================================================================
	 */

	/*----------------------------------------------------String-----------------------------------------------------*/


	@Override
	public void put(String key, String value) {
		putStringToDisk(key, value);

	}

	@Override
	public void put(String key, String value, int cacheTime, int timeUnit) {
		int expirationTime = CacheHelper.calculateCacheTime(cacheTime, timeUnit);
		putStringToDisk(key, CacheHelper.convertStringWithDate(expirationTime, value));
	}

	@Override
	public String getAsString(String key) {
		String result = getStringFromDisk(key);
		return checkCacheExpiredWithString(key, result);
	}

	/*----------------------------------------------------JSONObject-----------------------------------------------------*/

	@Override
	public void put(String key, JSONObject jsonObject) {
		putStringToDisk(key, jsonObject.toString());
	}

	@Override
	public void put(String key, JSONObject jsonObject, int cacheTime, int timeUnit) {
		int expirationTime = CacheHelper.calculateCacheTime(cacheTime, timeUnit);
		putStringToDisk(key, CacheHelper.convertStringWithDate(expirationTime, jsonObject.toString()));
	}

	@Override
	public JSONObject getAsJSONObject(String key) {
		String result = getStringFromDisk(key);
		String JSONString = checkCacheExpiredWithString(key, result);
		try {
			if (JSONString != null)
				return new JSONObject(JSONString);
		} catch (JSONException e) {
			e.printStackTrace();
		}
		return null;
	}
	
	/*----------------------------------------------------JSONArray-----------------------------------------------------*/

	@Override
	public void put(String key, JSONArray jsonArray) {
		putStringToDisk(key, jsonArray.toString());
	}
	
	@Override
	public void put(String key, JSONArray jsonArray, int cacheTime, int timeUnit) {
		int expirationTime = CacheHelper.calculateCacheTime(cacheTime, timeUnit);
		putStringToDisk(key, CacheHelper.convertStringWithDate(expirationTime, jsonArray.toString()));
	}

	@Override
	public JSONArray getAsJSONArray(String key) {
		String result = getStringFromDisk(key);
		String JSONString = checkCacheExpiredWithString(key, result);
		try {
			JSONArray obj = new JSONArray(JSONString);
			return obj;
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
	}
	
	/*----------------------------------------------------byte[ ]-----------------------------------------------------*/

	@Override
	public void put(String key, byte[] value) {
		putBytesToDisk(key, value);
	}


	@Override
	public void put(String key, byte[] value, int cacheTime, int timeUnit) {
		int expirationTime = CacheHelper.calculateCacheTime(cacheTime, timeUnit);
		putBytesToDisk(key, CacheHelper.convertByteArrayWithDate(expirationTime, value));
	}

	@Override
	public byte[] getAsBytes(String key) {
		byte[] bs = getBytesFromDisk(key);
		return checkCacheExpiredWithByte(key,bs );
	}
	
	/*----------------------------------------------------Serializable-----------------------------------------------------*/

	@Override
	public void put(String key, Serializable value) {
		/*DiskLruCache.Editor editor = editor(key);
		ObjectOutputStream oos = null;
		if (editor == null)
			return;
		try {
			OutputStream os = editor.newOutputStream(0);
			oos = new ObjectOutputStream(os);
			oos.writeObject(value);
			oos.flush();
			editor.commit();
		} catch (IOException e) {
			e.printStackTrace();
			try {
				editor.abort();
			} catch (IOException e1) {
				e1.printStackTrace();
			}
		} finally {
			try {
				if (oos != null)
					oos.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}*/
		ByteArrayOutputStream baos = null;
		ObjectOutputStream oos = null;
		try {
			baos = new ByteArrayOutputStream();
			oos = new ObjectOutputStream(baos);
			oos.writeObject(value);
			byte[] data = baos.toByteArray();
			putBytesToDisk(key, data);
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			try {
				oos.close();
			} catch (IOException e) {
			}
		}
	}
	

	@Override
	public void put(String key, Serializable value, int cacheTime, int timeUnit) {
		ByteArrayOutputStream baos = null;
		ObjectOutputStream oos = null;
		try {
			baos = new ByteArrayOutputStream();
			oos = new ObjectOutputStream(baos);
			oos.writeObject(value);
			byte[] data = baos.toByteArray();
			
			int expirationTime = CacheHelper.calculateCacheTime(cacheTime, timeUnit);
			putBytesToDisk(key, CacheHelper.convertByteArrayWithDate(expirationTime, data));
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			try {
				oos.close();
			} catch (IOException e) {
			}
		}
	
	}

	@SuppressWarnings("unchecked")
	@Override
	public <T> T getAsSerializable(String key) {
		T t = null;
		
//		InputStream is = get(key);
		byte[] bs = getBytesFromDisk(key);
		byte[] data = checkCacheExpiredWithByte(key, bs);
		if (data == null) {
			return null;
		}
		InputStream is = new ByteArrayInputStream(data);
		
		ObjectInputStream ois = null;
		try {
			ois = new ObjectInputStream(is);
			t = (T) ois.readObject();
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			try {
				if (ois != null)
					ois.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		return t;
	}
	
	/*----------------------------------------------------Bitmap-----------------------------------------------------*/

	@Override
	public void put(String key, Bitmap bitmap) {
		putBytesToDisk(key, ImageHelper.bitmap2Bytes(bitmap));
	}
	
	@Override
	public void put(String key, Bitmap bitmap, int cacheTime, int timeUnit) {
		int expirationTime = CacheHelper.calculateCacheTime(cacheTime, timeUnit);
		putBytesToDisk(key, CacheHelper.convertByteArrayWithDate(expirationTime, ImageHelper.bitmap2Bytes(bitmap)));
	}

	@Override
	public Bitmap getAsBitmap(String key) {
		byte[] bs = getBytesFromDisk(key);
		byte[] bytes = checkCacheExpiredWithByte(key, bs);
		if (bytes == null) {
			return null;
		}else {
			return ImageHelper.bytes2Bitmap(bytes);
		}
	}
	
	/*----------------------------------------------------Drawable-----------------------------------------------------*/

	@Override
	public void put(String key, Drawable value) {
		put(key, ImageHelper.drawable2Bitmap(value));
	}
	
	@Override
	public void put(String key, Drawable value, int cacheTime, int timeUnit) {
		put(key, ImageHelper.drawable2Bitmap(value), cacheTime, timeUnit);
	}

	@Override
	public Drawable getAsDrawable(String key) {
		byte[] bs = getBytesFromDisk(key);
		byte[] bytes = checkCacheExpiredWithByte(key, bs);
		if (bytes == null) {
			return null;
		}else {
			Bitmap bitmap = ImageHelper.bytes2Bitmap(bytes);
			return bitmap == null ? null : ImageHelper.bitmap2Drawable(bitmap);
		}

	}
	
	@Override
	public boolean remove(String key) {
		try {
			key = hashKeyForDisk(key);
			boolean isSuccess = mDiskLruCache.remove(key);
			if (isSuccess) {
				Log.i(TAG, "Disk cache delete success");
			} else {
				Log.i(TAG, "Disk cache delete failed");
			}
			return isSuccess;
		} catch (IOException e) {
			e.printStackTrace();
		}
		return false;
	}

}
