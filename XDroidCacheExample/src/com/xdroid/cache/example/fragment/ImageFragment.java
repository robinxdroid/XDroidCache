package com.xdroid.cache.example.fragment;

import com.xdroid.cache.SecondLevelCacheKit;
import com.xdroid.cache.example.R;
import com.xdroid.cache.example.bean.PersonBean;
import com.xdroid.cache.interfaces.TimeUnit;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

public class ImageFragment extends Fragment {

	private EditText mCacheTimeEditText;
	private ImageView mImageView;
	private TextView mTipsTextView;

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
		View view = inflater.inflate(R.layout.fragment_image, container, false);
		init(view);
		return view;

	}

	private void init(View view) {
		initView(view);
	}

	private void initView(View view) {
		mCacheTimeEditText = (EditText) view.findViewById(R.id.et_cache_time);
		mImageView = (ImageView) view.findViewById(R.id.img);
		mTipsTextView = (TextView) view.findViewById(R.id.tv_tips);

		initBitmapCache(view);
		initDrawableCache(view);
	}

	/**
	 * Bitmap 缓存
	 * 
	 * @param view
	 */
	private void initBitmapCache(View view) {
		view.findViewById(R.id.btn_bitmap_save).setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				
				Bitmap cacheValue = BitmapFactory.decodeResource(getResources(), R.drawable.beauty);

				int cacheTime = 0;
				// 如果缓存时间为空，那么不设置缓存时间（永久缓存）
				if (!TextUtils.isEmpty(mCacheTimeEditText.getText().toString().trim())) {
					cacheTime = Integer.parseInt(mCacheTimeEditText.getText().toString().trim());
					// 存储key为”key_bitmap“
					SecondLevelCacheKit.getInstance(getActivity()).put("key_bitmap", cacheValue, cacheTime,
							TimeUnit.SECOND);
					Toast.makeText(getActivity(), "缓存成功，缓存时间：" + cacheTime + "秒", Toast.LENGTH_SHORT).show();
				} else {
					SecondLevelCacheKit.getInstance(getActivity()).put("key_bitmap", cacheValue);
					Toast.makeText(getActivity(), "缓存成功，永久缓存", Toast.LENGTH_SHORT).show();
				}

			}
		});
		view.findViewById(R.id.btn_bitmap_read).setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				Bitmap result = SecondLevelCacheKit.getInstance(getActivity()).getAsBitmap("key_bitmap");
				if (result== null) {
					mTipsTextView.setText("未查找到缓存数据");
					mImageView.setImageResource(Color.GRAY);
				} else {
					mTipsTextView.setText("Key:key_bitmap  Value:" + result);
					mImageView.setImageBitmap(result);
				}
			}
		});
	}

	
	/**
	 * Drawable 缓存
	 * 
	 * @param view
	 */
	private void initDrawableCache(View view) {
		view.findViewById(R.id.btn_drawable_save).setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				
				Drawable cacheValue = getResources().getDrawable(R.drawable.beauty);

				int cacheTime = 0;
				// 如果缓存时间为空，那么不设置缓存时间（永久缓存）
				if (!TextUtils.isEmpty(mCacheTimeEditText.getText().toString().trim())) {
					cacheTime = Integer.parseInt(mCacheTimeEditText.getText().toString().trim());
					// 存储key为”key_drawable“
					SecondLevelCacheKit.getInstance(getActivity()).put("key_drawable", cacheValue, cacheTime,
							TimeUnit.SECOND);
					Toast.makeText(getActivity(), "缓存成功，缓存时间：" + cacheTime + "秒", Toast.LENGTH_SHORT).show();
				} else {
					SecondLevelCacheKit.getInstance(getActivity()).put("key_drawable", cacheValue);
					Toast.makeText(getActivity(), "缓存成功，永久缓存", Toast.LENGTH_SHORT).show();
				}

			}
		});
		view.findViewById(R.id.btn_drawable_read).setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				Drawable result = SecondLevelCacheKit.getInstance(getActivity()).getAsDrawable("key_drawable");
				if (result== null) {
					mTipsTextView.setText("未查找到缓存数据");
					mImageView.setImageResource(Color.GRAY);
				} else {
					mTipsTextView.setText("Key:key_drawable  Value:" + result);
					mImageView.setImageDrawable(result);
				}
			}
		});
	}

}
