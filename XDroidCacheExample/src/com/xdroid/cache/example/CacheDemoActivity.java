package com.xdroid.cache.example;

import com.xdroid.cache.example.fragment.CharsetFragment;
import com.xdroid.cache.example.fragment.ImageFragment;
import com.xdroid.cache.example.fragment.ObjectFragment;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;

/**
 * Demo演示FragmentActivity
 * @author Robin
 * @since 2015-11-24 16:19:19
 *
 */
public class CacheDemoActivity extends FragmentActivity {

	public interface DemoType {
		public int CHARSET = 0x01, IMAGE = 0x02, OBJECT = 0x03;
	}
	
	@Override
	protected void onCreate(Bundle arg0) {
		super.onCreate(arg0);
		setContentView(R.layout.activity_cache_demo);

		init();
	}

	private void init() {
		//Fragment[] fragments = new Fragment[]{new CharsetFragment(),new ImageFragment(),new ObjectFragment()};
		
		switch (getIntent().getIntExtra("demo_type", 0)) {
		case DemoType.CHARSET:
			switchFragment(CharsetFragment.class);
			break;

		case DemoType.IMAGE:
			switchFragment(ImageFragment.class);
			break;
		case DemoType.OBJECT:
			switchFragment(ObjectFragment.class);
			break;
		}
	}
	
	/**
	 * 切换Fragment
	 * @param cls
	 */
	public void switchFragment(Class<?> cls){
		int containerId = R.id.fragment_container;
		if (cls == null) {
			return;
		}
		try {
			String fragmentTag = cls.toString();
			FragmentManager fm = getSupportFragmentManager();
			Fragment fragment = (Fragment) fm.findFragmentByTag(fragmentTag);
			if (fragment == null) {
				fragment = (Fragment) cls.newInstance();
			}

			FragmentTransaction ft = fm.beginTransaction();
			if (fragment.isAdded()) {
				ft.show(fragment);
			} else {
				ft.add(containerId, fragment, fragmentTag);
			}

			//ft.addToBackStack(fragmentTag);
			ft.commitAllowingStateLoss();
		} catch (InstantiationException e) {
			e.printStackTrace();
		} catch (IllegalAccessException e) {
			e.printStackTrace();
		}
	}
	
	

}
