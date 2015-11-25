package com.xdroid.cache.example.fragment;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import com.xdroid.cache.SecondLevelCacheKit;
import com.xdroid.cache.example.R;
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

public class CharsetFragment extends Fragment {
	
	private EditText mCacheValueEditText;
	private EditText mCacheTimeEditText;
    private TextView mTipsTextView;
	
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
		View view = inflater.inflate(R.layout.fragment_charset, container, false);
		init(view);
		return view;
		
	}

	private void init(View view) {
		initView(view);
	}
	
	private void initView(View view) {
		mCacheValueEditText = (EditText) view.findViewById(R.id.et_cache_value);
		mCacheTimeEditText = (EditText) view.findViewById(R.id.et_cache_time);
		mTipsTextView = (TextView) view.findViewById(R.id.tv_tips);
		
		initStringCache(view);
		initJsonObjectCache(view);
		initJsonArrayCache(view);
		initByteArrayCache(view);
	}

	/**
	 * String 缓存
	 * @param view
	 */
	private void initStringCache(View view) {
		view.findViewById(R.id.btn_string_save).setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				String cacheValue = mCacheValueEditText.getText().toString().trim();
				int cacheTime = 0;
				if (!TextUtils.isEmpty(cacheValue)) {
					//如果缓存时间为空，那么不设置缓存时间（永久缓存）
					if (!TextUtils.isEmpty(mCacheTimeEditText.getText().toString().trim())) {
						cacheTime = Integer.parseInt(mCacheTimeEditText.getText().toString().trim());
						//存储key为”key_string“，值为EdiText输入内容
						SecondLevelCacheKit.getInstance(getActivity()).put("key_string", cacheValue, cacheTime, TimeUnit.SECOND);
						Toast.makeText(getActivity(), "缓存成功，缓存时间："+cacheTime+"秒", Toast.LENGTH_SHORT).show();
					}else {
						SecondLevelCacheKit.getInstance(getActivity()).put("key_string", cacheValue);
						Toast.makeText(getActivity(), "缓存成功，永久缓存", Toast.LENGTH_SHORT).show();
					}
				}

			}
		});
		view.findViewById(R.id.btn_string_read).setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				String result = SecondLevelCacheKit.getInstance(getActivity()).getAsString("key_string");
				if (TextUtils.isEmpty(result)) {
					mTipsTextView.setText("未查找到缓存数据");
				}else {
					mTipsTextView.setText("Key:key_string  Value:"+result);
				}
			}
		});
	}
	
	
	/**
	 * JSONObject 缓存
	 * @param view
	 */
	private void initJsonObjectCache(View view) {
		view.findViewById(R.id.btn_jso_save).setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				String cacheValue = mCacheValueEditText.getText().toString().trim();
				int cacheTime = 0;
				if (!TextUtils.isEmpty(cacheValue)) {
					
					JSONObject jsonObject = null;
					try {
					
						if (cacheValue.startsWith("{")&& cacheValue.endsWith("}")) {
							jsonObject = new JSONObject(cacheValue);
						}else {
							Toast.makeText(getActivity(), "JSONObject格式需要存储值以\"{\"开头\"}\"结尾", Toast.LENGTH_SHORT).show();
							return;
						}
					} catch (JSONException e) {
						e.printStackTrace();
					}
					
					//如果缓存时间为空，那么不设置缓存时间（永久缓存）
					if (!TextUtils.isEmpty(mCacheTimeEditText.getText().toString().trim())) {
						cacheTime = Integer.parseInt(mCacheTimeEditText.getText().toString().trim());
						//存储key为”key_jso“，值为EdiText输入内容
						SecondLevelCacheKit.getInstance(getActivity()).put("key_jso", jsonObject, cacheTime, TimeUnit.SECOND);
						Toast.makeText(getActivity(), "缓存成功，缓存时间："+cacheTime+"秒", Toast.LENGTH_SHORT).show();
					}else {
						SecondLevelCacheKit.getInstance(getActivity()).put("key_jso", jsonObject);
						Toast.makeText(getActivity(), "缓存成功，永久缓存", Toast.LENGTH_SHORT).show();
					}
				}
			}
		});
		view.findViewById(R.id.btn_jso_read).setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				JSONObject result = SecondLevelCacheKit.getInstance(getActivity()).getAsJSONObject("key_jso");
				if (result==null||TextUtils.isEmpty(result.toString())) {
					mTipsTextView.setText("未查找到缓存数据");
				}else {
					mTipsTextView.setText("Key:key_jso  Value:"+result.toString());
				}
			}
		});

	}
	
	
	/**
	 * JSONArray 缓存
	 * @param view
	 */
	private void initJsonArrayCache(View view) {
		view.findViewById(R.id.btn_jsa_save).setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				String cacheValue = mCacheValueEditText.getText().toString().trim();
				int cacheTime = 0;
				if (!TextUtils.isEmpty(cacheValue)) {
					
					JSONArray jsonArray = null;
					try {
						if (cacheValue.startsWith("[")&& cacheValue.endsWith("]")) {
							jsonArray = new JSONArray(cacheValue);
						}else {
							Toast.makeText(getActivity(), "JSONArray格式需要存储值以\"[\"开头\"]\"结尾", Toast.LENGTH_SHORT).show();
							return;
						}
			
					} catch (JSONException e) {
						e.printStackTrace();
					}
					
					//如果缓存时间为空，那么不设置缓存时间（永久缓存）
					if (!TextUtils.isEmpty(mCacheTimeEditText.getText().toString().trim())) {
						cacheTime = Integer.parseInt(mCacheTimeEditText.getText().toString().trim());
						//存储key为”key_jsa“，值为EdiText输入内容
						SecondLevelCacheKit.getInstance(getActivity()).put("key_jsa", jsonArray, cacheTime, TimeUnit.SECOND);
						Toast.makeText(getActivity(), "缓存成功，缓存时间："+cacheTime+"秒", Toast.LENGTH_SHORT).show();
					}else {
						SecondLevelCacheKit.getInstance(getActivity()).put("key_jsa", jsonArray);
						Toast.makeText(getActivity(), "缓存成功,永久缓存", Toast.LENGTH_SHORT).show();
					}
				}
			}
		});
		view.findViewById(R.id.btn_jsa_read).setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				JSONArray result = SecondLevelCacheKit.getInstance(getActivity()).getAsJSONArray("key_jsa");
				if (result==null||TextUtils.isEmpty(result.toString())) {
					mTipsTextView.setText("未查找到缓存数据");
				}else {
					mTipsTextView.setText("Key:key_jsa  Value:"+result.toString());
				}
			}
		});

	}
	
	
	/**
	 * ByteArray 缓存
	 * @param view
	 */
	private void initByteArrayCache(View view) {
		view.findViewById(R.id.btn_byte_save).setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				String cacheValue = mCacheValueEditText.getText().toString().trim();
				int cacheTime = 0;
				if (!TextUtils.isEmpty(cacheValue)) {
					//如果缓存时间为空，那么不设置缓存时间（永久缓存）
					if (!TextUtils.isEmpty(mCacheTimeEditText.getText().toString().trim())) {
						cacheTime = Integer.parseInt(mCacheTimeEditText.getText().toString().trim());
						//存储key为”key_byte“，值为EdiText输入内容
						SecondLevelCacheKit.getInstance(getActivity()).put("key_byte", cacheValue.getBytes(), cacheTime, TimeUnit.SECOND);
						Toast.makeText(getActivity(), "缓存成功，缓存时间："+cacheTime+"秒", Toast.LENGTH_SHORT).show();
					}else {
						SecondLevelCacheKit.getInstance(getActivity()).put("key_byte", cacheValue.getBytes());
						Toast.makeText(getActivity(), "缓存成功，永久缓存", Toast.LENGTH_SHORT).show();
					}
				
				}

			}
		});
		view.findViewById(R.id.btn_byte_read).setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				byte[] result = SecondLevelCacheKit.getInstance(getActivity()).getAsBytes("key_byte");
				if (result==null||TextUtils.isEmpty(new String(result))) {
					mTipsTextView.setText("未查找到缓存数据");
				}else {
					mTipsTextView.setText("Key:key_byte  Value:"+new String(result));
				}
			}
		});
	}
}
