package com.xdroid.cache.example.fragment;

import com.xdroid.cache.SecondLevelCacheKit;
import com.xdroid.cache.example.R;
import com.xdroid.cache.example.bean.PersonBean;
import com.xdroid.cache.interfaces.TimeUnit;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

public class ObjectFragment extends Fragment {

	private EditText mNameEditText;
	private EditText mEmailEditText;
	private EditText mCacheTimeEditText;
	private TextView mTipsTextView;

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
		View view = inflater.inflate(R.layout.fragment_object, container, false);
		init(view);
		return view;

	}

	private void init(View view) {
		initView(view);
	}

	private void initView(View view) {
		mNameEditText = (EditText) view.findViewById(R.id.et_name);
		mEmailEditText = (EditText) view.findViewById(R.id.et_email);
		mCacheTimeEditText = (EditText) view.findViewById(R.id.et_cache_time);
		mTipsTextView = (TextView) view.findViewById(R.id.tv_tips);

		initSerializeCache(view);
	}

	/**
	 * Serialize 缓存
	 * 
	 * @param view
	 */
	private void initSerializeCache(View view) {
		view.findViewById(R.id.btn_serialize_save).setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				String name = mNameEditText.getText().toString().trim();
				String email = mEmailEditText.getText().toString().trim();
				
				PersonBean cacheValue = new PersonBean(name, email);

				int cacheTime = 0;
				// 如果缓存时间为空，那么不设置缓存时间（永久缓存）
				if (!TextUtils.isEmpty(mCacheTimeEditText.getText().toString().trim())) {
					cacheTime = Integer.parseInt(mCacheTimeEditText.getText().toString().trim());
					// 存储key为”key_serialize“
					SecondLevelCacheKit.getInstance(getActivity()).put("key_serialize", cacheValue, cacheTime,
							TimeUnit.SECOND);
					Toast.makeText(getActivity(), "缓存成功，缓存时间：" + cacheTime + "秒", Toast.LENGTH_SHORT).show();
				} else {
					SecondLevelCacheKit.getInstance(getActivity()).put("key_serialize", cacheValue);
					Toast.makeText(getActivity(), "缓存成功，永久缓存", Toast.LENGTH_SHORT).show();
				}

			}
		});
		view.findViewById(R.id.btn_serialize_read).setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				PersonBean result = SecondLevelCacheKit.getInstance(getActivity()).getAsSerializable("key_serialize");
				if (result== null) {
					mTipsTextView.setText("未查找到缓存数据");
				} else {
					mTipsTextView.setText("Key:key_serialize  Value:" + result);
				}
			}
		});
	}

}
