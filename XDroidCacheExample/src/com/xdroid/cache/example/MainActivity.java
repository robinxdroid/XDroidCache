package com.xdroid.cache.example;

import com.xdroid.cache.example.CacheDemoActivity.DemoType;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;

public class MainActivity extends Activity implements OnClickListener {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);

		init();
	}

	private void init() {
		int[] ids = new int[] { R.id.btn_string, R.id.btn_jso, R.id.btn_jsa, R.id.btn_byte, R.id.btn_bitmap,
				R.id.btn_drawable, R.id.btn_serialize };
		for (int i = 0; i < ids.length; i++) {
			findViewById(ids[i]).setOnClickListener(this);
		}

	}

	@Override
	public void onClick(View v) {
		Intent intent = new Intent(this, CacheDemoActivity.class);
		switch (v.getId()) {
		case R.id.btn_string:
			intent.putExtra("demo_type", DemoType.CHARSET);
			break;
		case R.id.btn_jso:
			intent.putExtra("demo_type", DemoType.CHARSET);
			break;
		case R.id.btn_jsa:
			intent.putExtra("demo_type", DemoType.CHARSET);
			break;
		case R.id.btn_byte:
			intent.putExtra("demo_type", DemoType.CHARSET);
			break;
		case R.id.btn_bitmap:
			intent.putExtra("demo_type", DemoType.IMAGE);
			break;
		case R.id.btn_drawable:
			intent.putExtra("demo_type", DemoType.IMAGE);
			break;
		case R.id.btn_serialize:
			intent.putExtra("demo_type", DemoType.OBJECT);
			break;
		}
		startActivity(intent);
	}
}
