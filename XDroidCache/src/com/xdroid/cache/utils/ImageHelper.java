package com.xdroid.cache.utils;

import java.io.ByteArrayOutputStream;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.PixelFormat;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;

/**
 * Image helper
 * @author Robin
 * @since 2015-11-23 19:36:35
 *
 */
public class ImageHelper {
	public static byte[] bitmap2Bytes(Bitmap bm) {
		if (bm == null) {
			return null;
		}
		ByteArrayOutputStream baos = new ByteArrayOutputStream();
		bm.compress(Bitmap.CompressFormat.PNG, 100, baos);
		return baos.toByteArray();
	}

	public static Bitmap bytes2Bitmap(byte[] bytes) {
		return BitmapFactory.decodeByteArray(bytes, 0, bytes.length);
	}

	public static Bitmap drawable2Bitmap(Drawable drawable) {
		if (drawable == null) {
			return null;
		}
		int w = drawable.getIntrinsicWidth();
		int h = drawable.getIntrinsicHeight();
		Bitmap.Config config = drawable.getOpacity() != PixelFormat.OPAQUE ? Bitmap.Config.ARGB_8888
				: Bitmap.Config.RGB_565;
		Bitmap bitmap = Bitmap.createBitmap(w, h, config);
		Canvas canvas = new Canvas(bitmap);
		drawable.setBounds(0, 0, w, h);
		drawable.draw(canvas);
		return bitmap;
	}

	@SuppressWarnings("deprecation")
	public static Drawable bitmap2Drawable(Bitmap bm) {
		if (bm == null) {
			return null;
		}
		BitmapDrawable bd = new BitmapDrawable(bm);
		bd.setTargetDensity(bm.getDensity());
		return new BitmapDrawable(bm);
	}
}
