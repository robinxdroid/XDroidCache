package com.xdroid.cache.utils;

import com.xdroid.cache.interfaces.TimeUnit;

/**
 * Cache helper
 * @author Robin
 * @since 2015-11-20 18:47:35
 */
public class CacheHelper {
	
	private static final char mSeparator = ' ';
	
	/**
	 *  Time calculation
	 * @param originalTime 
	 * @param timeUnit 
	 * @return
	 */
	public static int calculateCacheTime(int originalTime,int timeUnit){
		int resultTime = 0;
		switch (timeUnit) {
		case TimeUnit.SECOND:
			resultTime = originalTime ;
			break;
		case TimeUnit.MINUTES:
			resultTime = originalTime  * 60;
			break;
		case TimeUnit.HOUR:
			resultTime = originalTime  * 60 * 60;
			break;
		case TimeUnit.DAY:
			resultTime = originalTime  * 60 * 60 * 24;
			break;
		case TimeUnit.WEEK:
			resultTime = originalTime  * 60 * 60 * 24 * 7;
			break;
		case TimeUnit.MONTH:
			resultTime = originalTime  * 60 * 60 * 24 * 30;
			break;
		case TimeUnit.YEAR:
			resultTime = originalTime  * 60 * 60 * 24 * 30 * 12;
			break;
		}
		return resultTime;
	}

	/**
	 * Whether the cache expiration
	 * @param str
	 * @return
	 */
	public static boolean isExpired(String str) {
		return isExpired(str.getBytes());
	}

	/**
	 * Whether the cache expiration
	 * @param data
	 * @return
	 */
	public static boolean isExpired(byte[] data) {
		String[] strs = getDateInfoFromData(data);
		if (strs != null && strs.length == 2) {
			String saveTimeStr = strs[0];
			while (saveTimeStr.startsWith("0")) {
				saveTimeStr = saveTimeStr
						.substring(1, saveTimeStr.length());
			}
			long saveTime = Long.valueOf(saveTimeStr);
			long deleteAfter = Long.valueOf(strs[1]);
			if (System.currentTimeMillis() > saveTime + deleteAfter * 1000) {
				return true;
			}
		}
		return false;
	}
	
	/**
	 * Cache write time distance from the current time interval
	 * @param str
	 * @return
	 */
	public static long getCacheTimeInterval(String str){
		return getCacheTimeInterval(str.getBytes());
	}
	
	/**
	 * Cache write time distance from the current time interval
	 * @param data
	 * @return
	 */
	public static long getCacheTimeInterval(byte[] data){
		String[] strs = getDateInfoFromData(data);
		if (strs != null && strs.length == 2) {
			String saveTimeStr = strs[0];
			//If it is 0 at the beginning, eliminate 0
			while (saveTimeStr.startsWith("0")) {
				saveTimeStr = saveTimeStr
						.substring(1, saveTimeStr.length());
			}
			long saveTime = Long.valueOf(saveTimeStr);
			//long cacheTime = Long.valueOf(strs[1]);
			long intervalTime=System.currentTimeMillis()-saveTime;
			return intervalTime;
		}
		return 0;
	}
	
	/**
	 * Get the cache time
	 * @param str
	 * @return
	 */
	public static long getCacheTime(String str){
		return getCacheTime(str.getBytes());
	}
	
	/**
	 * Get the cache time
	 * @param data
	 * @return
	 */
	public static long getCacheTime(byte[] data){
		String[] strs = getDateInfoFromData(data);
		if (strs != null && strs.length == 2) {
			long cacheTime = Long.valueOf(strs[1]);
			return cacheTime;
		}
		return 0;
	}

	/**
	 * Synthetic time with string
	 * @param timeSecond 
	 * @param str 
	 * @return
	 */
	public static String convertStringWithDate(int timeSecond, String str) {
		return createDateInfo(timeSecond) + str;
	}

	/**
	 * Synthetic time with byte array，"cache time" + "original data"
	 * @param timeSecond
	 * @param originalData
	 * @return
	 */
	public static byte[] convertByteArrayWithDate(int timeSecond, byte[] originalData) {
		byte[] timeData = createDateInfo(timeSecond).getBytes();
		byte[] resultData = new byte[timeData.length + originalData.length];
		System.arraycopy(timeData, 0, resultData, 0, timeData.length);
		System.arraycopy(originalData, 0, resultData, timeData.length, originalData.length);
		return resultData;
	}

	/**
	 * Remove time information
	 * @param str
	 * @return 
	 */
	public static String clearDateInfo(String str) {
		if (str != null && hasDateInfo(str.getBytes())) {
			str = str.substring(str.indexOf(mSeparator) + 1,
					str.length());
		}
		return str;
	}

	/**
	 * Remove time information
	 * @param data 
	 * @return
	 */
	public static byte[] clearDateInfo(byte[] data) {
		if (hasDateInfo(data)) {
			return copyOfRange(data, indexOf(data, mSeparator) + 1,
					data.length);
		}
		return data;
	}

	/**
	 * Time information is read from the data, including data writing time, the cache time
	 * @param data 
	 * @return
	 */
	public static String[] getDateInfoFromData(byte[] data) {
		if (hasDateInfo(data)) {
			//Get the write time
			String saveDate = new String(copyOfRange(data, 0, 13));
			//Get the cache time
			String cacheTime = new String(copyOfRange(data, 14,
					indexOf(data, mSeparator)));
			return new String[] { saveDate, cacheTime };
		}
		return null;
	}
	
	/**
	 * Check whether there is time information
	 * @param data
	 * @return
	 */
	public static boolean hasDateInfo(byte[] data) {
		return data != null && data.length > 15 && data[13] == '-'
				&& indexOf(data, mSeparator) > 14;
	}

	/**
	 * To retrieve parameters "c" location
	 * @param data 
	 * @param c 
	 * @return 
	 */
	public static int indexOf(byte[] data, char c) {
		for (int i = 0; i < data.length; i++) {
			if (data[i] == c) {
				return i;
			}
		}
		return -1;
	}

	/**
	 * Copying the byte array 
	 * @param original
	 * @param from
	 * @param to
	 * @return 
	 */
	public static byte[] copyOfRange(byte[] original, int from, int to) {
		int newLength = to - from;
		if (newLength < 0)
			throw new IllegalArgumentException(from + " > " + to);
		byte[] copy = new byte[newLength];
		System.arraycopy(original, from, copy, 0,
				Math.min(original.length - from, newLength));
		return copy;
	}

	/**
	 * Create time information
	 * @param timeSecond 
	 * @return time information ，By "write time" + "-" + "cache time" + "separator"
	 */
	public static String createDateInfo(int timeSecond) {
		String currentTime = System.currentTimeMillis() + "";
		//If less than 13 digits, add 0 in front
		while (currentTime.length() < 13) {
			currentTime = "0" + currentTime;
		}
		return currentTime + "-" + timeSecond + mSeparator;
	}
	
}