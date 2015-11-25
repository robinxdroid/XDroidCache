package com.xdroid.cache;

import java.io.Serializable;

import org.json.JSONArray;
import org.json.JSONObject;

import com.xdroid.cache.interfaces.ICache;
import com.xdroid.cache.manage.DiskCacheManager;
import com.xdroid.cache.manage.MemoryCacheManager;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.drawable.Drawable;
import android.util.Log;

/**
 * The second level cache tool
 * 
 * @author Robin
 * @since 2015-10-14 19:06:11
 *
 */
public class SecondLevelCacheKit implements ICache {

	private static final String TAG = "system.out";

	private static Context mContext;

	private static DiskCacheManager mDdiskCacheManager;

	private static volatile SecondLevelCacheKit INSTANCE = null;

	public static SecondLevelCacheKit getInstance(Context context) {
		if (INSTANCE == null) {
			synchronized (SecondLevelCacheKit.class) {
				if (INSTANCE == null) {
					INSTANCE = new SecondLevelCacheKit();
				}
			}
			mContext = context.getApplicationContext();
			mDdiskCacheManager = DiskCacheManager.getInstance(mContext);
		}
		return INSTANCE;
	}

	/*----------------------------------------------------String-----------------------------------------------------*/

	@Override
	public void put(String key, String value) {
		// Memory
		@SuppressWarnings("unchecked")
		MemoryCacheManager<String, String> memoryCacheManager = (MemoryCacheManager<String, String>) MemoryCacheManager
				.getInstance();
		memoryCacheManager.put(key, value);
		Log.i(TAG, "Memory cache insert success");

		// Disk
		mDdiskCacheManager.put(key, value);
		Log.i(TAG, "Disk cache insert success");
	}

	@Override
	public void put(String key, String value, int cacheTime, int timeUnit) {
		// Memory
		@SuppressWarnings("unchecked")
		MemoryCacheManager<String, String> memoryCacheManager = (MemoryCacheManager<String, String>) MemoryCacheManager
				.getInstance();
		memoryCacheManager.put(key, value, cacheTime, timeUnit);
		Log.i(TAG, "Memory cache insert success");

		// Disk
		mDdiskCacheManager.put(key, value, cacheTime, timeUnit);
		Log.i(TAG, "Disk cache insert success");
	}

	@Override
	public String getAsString(String key) {
		// Memory
		@SuppressWarnings("unchecked")
		MemoryCacheManager<String, String> memoryCacheManager = (MemoryCacheManager<String, String>) MemoryCacheManager
				.getInstance();
		String result = memoryCacheManager.getAsString(key);
		if (result != null) {
			return result;
		}

		// Disk
		result = mDdiskCacheManager.getAsString(key);

		return result;
	}

	/*----------------------------------------------------JSONObject-----------------------------------------------------*/

	@Override
	public void put(String key, JSONObject jsonObject) {
		// Memory
		@SuppressWarnings("unchecked")
		MemoryCacheManager<String, JSONObject> memoryCacheManager = (MemoryCacheManager<String, JSONObject>) MemoryCacheManager
				.getInstance();
		memoryCacheManager.put(key, jsonObject);
		Log.i(TAG, "Memory cache insert success");

		// Disk
		mDdiskCacheManager.put(key, jsonObject);
		Log.i(TAG, "Disk cache insert success");
	}

	@Override
	public void put(String key, JSONObject jsonObject, int cacheTime, int timeUnit) {
		// Memory
		@SuppressWarnings("unchecked")
		MemoryCacheManager<String, JSONObject> memoryCacheManager = (MemoryCacheManager<String, JSONObject>) MemoryCacheManager
				.getInstance();
		memoryCacheManager.put(key, jsonObject, cacheTime, timeUnit);
		Log.i(TAG, "Memory cache insert success");

		// Disk
		mDdiskCacheManager.put(key, jsonObject, cacheTime, timeUnit);
		Log.i(TAG, "Disk cache insert success");

	}

	@Override
	public JSONObject getAsJSONObject(String key) {
		// Memory
		@SuppressWarnings("unchecked")
		MemoryCacheManager<String, JSONObject> memoryCacheManager = (MemoryCacheManager<String, JSONObject>) MemoryCacheManager
				.getInstance();
		JSONObject result = memoryCacheManager.getAsJSONObject(key);
		if (result != null) {
			return result;
		}

		// Disk
		result = mDdiskCacheManager.getAsJSONObject(key);
		return result;
	}

	/*----------------------------------------------------JSONArray-----------------------------------------------------*/

	@Override
	public void put(String key, JSONArray jsonArray) {
		// Memory
		@SuppressWarnings("unchecked")
		MemoryCacheManager<String, JSONArray> memoryCacheManager = (MemoryCacheManager<String, JSONArray>) MemoryCacheManager
				.getInstance();
		memoryCacheManager.put(key, jsonArray);
		Log.i(TAG, "Memory cache insert success");

		// Disk
		mDdiskCacheManager.put(key, jsonArray);
		Log.i(TAG, "Disk cache insert success");
	}

	@Override
	public void put(String key, JSONArray jsonArray, int cacheTime, int timeUnit) {
		// Memory
		@SuppressWarnings("unchecked")
		MemoryCacheManager<String, JSONArray> memoryCacheManager = (MemoryCacheManager<String, JSONArray>) MemoryCacheManager
				.getInstance();
		memoryCacheManager.put(key, jsonArray, cacheTime, timeUnit);
		Log.i(TAG, "Memory cache insert success");

		// Disk
		mDdiskCacheManager.put(key, jsonArray, cacheTime, timeUnit);
		Log.i(TAG, "Disk cache insert success");
	}

	@Override
	public JSONArray getAsJSONArray(String key) {
		// Memory
		@SuppressWarnings("unchecked")
		MemoryCacheManager<String, JSONArray> memoryCacheManager = (MemoryCacheManager<String, JSONArray>) MemoryCacheManager
				.getInstance();
		JSONArray result = memoryCacheManager.getAsJSONArray(key);
		if (result != null) {
			return result;
		}

		// Disk
		result = mDdiskCacheManager.getAsJSONArray(key);
		return result;
	}

	/*----------------------------------------------------byte[ ]-----------------------------------------------------*/

	@Override
	public void put(String key, byte[] value) {
		// Memory
		@SuppressWarnings("unchecked")
		MemoryCacheManager<String, byte[]> memoryCacheManager = (MemoryCacheManager<String, byte[]>) MemoryCacheManager
				.getInstance();
		memoryCacheManager.put(key, value);
		Log.i(TAG, "Memory cache insert success");

		// Disk
		mDdiskCacheManager.put(key, value);
		Log.i(TAG, "Disk cache insert success");
	}

	@Override
	public void put(String key, byte[] value, int cacheTime, int timeUnit) {
		// Memory
		@SuppressWarnings("unchecked")
		MemoryCacheManager<String, byte[]> memoryCacheManager = (MemoryCacheManager<String, byte[]>) MemoryCacheManager
				.getInstance();
		memoryCacheManager.put(key, value, cacheTime, timeUnit);
		Log.i(TAG, "Memory cache insert success");

		// Disk
		mDdiskCacheManager.put(key, value, cacheTime, timeUnit);
		Log.i(TAG, "Disk cache insert success");
	}

	@Override
	public byte[] getAsBytes(String key) {
		// Memory
		@SuppressWarnings("unchecked")
		MemoryCacheManager<String, byte[]> memoryCacheManager = (MemoryCacheManager<String, byte[]>) MemoryCacheManager
				.getInstance();
		byte[] result = memoryCacheManager.getAsBytes(key);
		if (result != null) {
			return result;
		}

		// Disk
		result = mDdiskCacheManager.getAsBytes(key);
		return result;
	}

	/*----------------------------------------------------Serializable-----------------------------------------------------*/

	@Override
	public void put(String key, Serializable value) {
		// Memory
		@SuppressWarnings("unchecked")
		MemoryCacheManager<String, Serializable> memoryCacheManager = (MemoryCacheManager<String, Serializable>) MemoryCacheManager
				.getInstance();
		memoryCacheManager.put(key, value);
		Log.i(TAG, "Memory cache insert success");

		// Disk
		mDdiskCacheManager.put(key, value);
		Log.i(TAG, "Disk cache insert success");
	}

	@Override
	public void put(String key, Serializable value, int cacheTime, int timeUnit) {
		// Memory
		@SuppressWarnings("unchecked")
		MemoryCacheManager<String, Serializable> memoryCacheManager = (MemoryCacheManager<String, Serializable>) MemoryCacheManager
				.getInstance();
		memoryCacheManager.put(key, value, cacheTime, timeUnit);
		Log.i(TAG, "Memory cache insert success");

		// Disk
		mDdiskCacheManager.put(key, value, cacheTime, timeUnit);
		Log.i(TAG, "Disk cache insert success");
	}

	@SuppressWarnings("unchecked")
	@Override
	public <T> T getAsSerializable(String key) {
		// Memory
		MemoryCacheManager<String, Serializable> memoryCacheManager = (MemoryCacheManager<String, Serializable>) MemoryCacheManager
				.getInstance();
		Serializable result = memoryCacheManager.getAsSerializable(key);
		if (result != null) {
			return (T) result;
		}

		// Disk
		result = mDdiskCacheManager.getAsSerializable(key);
		return (T) result;
	}

	/*----------------------------------------------------Bitmap-----------------------------------------------------*/

	@Override
	public void put(String key, Bitmap bitmap) {
		// Memory
		@SuppressWarnings("unchecked")
		MemoryCacheManager<String, Bitmap> memoryCacheManager = (MemoryCacheManager<String, Bitmap>) MemoryCacheManager
				.getInstance();
		memoryCacheManager.put(key, bitmap);
		Log.i(TAG, "Memory cache insert success");

		// Disk
		mDdiskCacheManager.put(key, bitmap);
		Log.i(TAG, "Disk cache insert success");
	}

	@Override
	public void put(String key, Bitmap bitmap, int cacheTime, int timeUnit) {
		// Memory
		@SuppressWarnings("unchecked")
		MemoryCacheManager<String, Bitmap> memoryCacheManager = (MemoryCacheManager<String, Bitmap>) MemoryCacheManager
				.getInstance();
		memoryCacheManager.put(key, bitmap, cacheTime, timeUnit);
		Log.i(TAG, "Memory cache insert success");

		// Disk
		mDdiskCacheManager.put(key, bitmap, cacheTime, timeUnit);
		Log.i(TAG, "Disk cache insert success");

	}

	@Override
	public Bitmap getAsBitmap(String key) {
		// Memory
		@SuppressWarnings("unchecked")
		MemoryCacheManager<String, Bitmap> memoryCacheManager = (MemoryCacheManager<String, Bitmap>) MemoryCacheManager
				.getInstance();
		Bitmap result = memoryCacheManager.getAsBitmap(key);
		if (result != null) {
			return result;
		}

		// Disk
		result = mDdiskCacheManager.getAsBitmap(key);
		return result;
	}

	/*----------------------------------------------------Drawable-----------------------------------------------------*/

	@Override
	public void put(String key, Drawable value) {
		// Memory
		@SuppressWarnings("unchecked")
		MemoryCacheManager<String, Drawable> memoryCacheManager = (MemoryCacheManager<String, Drawable>) MemoryCacheManager
				.getInstance();
		memoryCacheManager.put(key, value);
		Log.i(TAG, "Memory cache insert success");

		// Disk
		mDdiskCacheManager.put(key, value);
		Log.i(TAG, "Disk cache insert success");
	}
	
	@Override
	public void put(String key, Drawable value, int cacheTime, int timeUnit) {
		// Memory
		@SuppressWarnings("unchecked")
		MemoryCacheManager<String, Drawable> memoryCacheManager = (MemoryCacheManager<String, Drawable>) MemoryCacheManager
				.getInstance();
		memoryCacheManager.put(key, value, cacheTime, timeUnit);
		Log.i(TAG, "Memory cache insert success");

		// Disk
		mDdiskCacheManager.put(key, value, cacheTime, timeUnit);
		Log.i(TAG, "Disk cache insert success");
	}

	@Override
	public Drawable getAsDrawable(String key) {
		// Memory
		@SuppressWarnings("unchecked")
		MemoryCacheManager<String, Drawable> memoryCacheManager = (MemoryCacheManager<String, Drawable>) MemoryCacheManager
				.getInstance();
		Drawable result = memoryCacheManager.getAsDrawable(key);
		if (result != null) {
			return result;
		}

		// Disk
		result = mDdiskCacheManager.getAsDrawable(key);
		return result;
	}

	@Override
	public boolean remove(String key) {
		// Memory
		@SuppressWarnings("unchecked")
		MemoryCacheManager<String, ?> memoryCacheManager = (MemoryCacheManager<String, ?>) MemoryCacheManager
				.getInstance();
		memoryCacheManager.remove(key);

		// Disk
		return mDdiskCacheManager.remove(key);
	}

}
