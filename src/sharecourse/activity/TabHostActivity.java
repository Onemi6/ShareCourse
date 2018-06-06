package sharecourse.activity;

import sharecourse.fragment.HomeFragment;
import sharecourse.fragment.PPCFragment;
import sharecourse.fragment.PublishFragment;
import sharecourse.fragment.TPCFragment;
import sharecourse.myclass.MyApplication;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentTabHost;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.ImageView;
import android.widget.TabHost.TabSpec;
import android.widget.TextView;
import android.widget.Toast;

import com.example.sharecourse.R;

public class TabHostActivity extends FragmentActivity {

	private long mExitTime;
	private FragmentTabHost mTabHost;
	private LayoutInflater mLayoutInflater;
	private Class mFragmentArray1[] = { HomeFragment.class,
			PublishFragment.class, PPCFragment.class };
	private Class mFragmentArray2[] = { HomeFragment.class,
			PublishFragment.class, TPCFragment.class };

	private int mImageViewArray[] = { R.drawable.tab_home_btn,
			R.drawable.tab_publish_btn, R.drawable.tab_my_btn };

	private String mTextArray[] = { "首页", "", "我的" };

	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_tabhost);
		initView();
	}

	private void initView() {
		mLayoutInflater = LayoutInflater.from(this);

		mTabHost = (FragmentTabHost) findViewById(android.R.id.tabhost);
		mTabHost.setup(this, getSupportFragmentManager(), R.id.realtabcontent);
		int count = mFragmentArray1.length;
		String type = ((MyApplication)getApplication()).getUserType();
		Toast.makeText(this, type, Toast.LENGTH_LONG).show();
		if(type.equals("教师"))
		{
		for (int i = 0; i < count; i++) {
			TabSpec tabSpec = mTabHost.newTabSpec(mTextArray[i]).setIndicator(
					getTabItemView(i));
			mTabHost.addTab(tabSpec, mFragmentArray2[i], null);
		}
		}
		else{
			for (int i = 0; i < count; i++) {
				TabSpec tabSpec = mTabHost.newTabSpec(mTextArray[i]).setIndicator(
						getTabItemView(i));
				mTabHost.addTab(tabSpec, mFragmentArray1[i], null);
			}
		}
	}

	private View getTabItemView(int index) {
		if (mTextArray[index].equals("")) {
			View view = mLayoutInflater.inflate(R.layout.tab_item_view2, null);
			ImageView imageView = (ImageView) view
					.findViewById(R.id.imageview2);
			imageView.setImageResource(mImageViewArray[index]);
			return view;
		} else {
			View view = mLayoutInflater.inflate(R.layout.tab_item_view, null);
			ImageView imageView = (ImageView) view.findViewById(R.id.imageview);
			imageView.setImageResource(mImageViewArray[index]);
			TextView textView = (TextView) view.findViewById(R.id.textview);
			textView.setText(mTextArray[index]);
			return view;
		}
	}

	public boolean onTouchEvent(MotionEvent event) {
		if (null != this.getCurrentFocus()) {
			InputMethodManager mInputMethodManager = (InputMethodManager) getSystemService(INPUT_METHOD_SERVICE);
			return mInputMethodManager.hideSoftInputFromWindow(this
					.getCurrentFocus().getWindowToken(), 0);
		}
		return super.onTouchEvent(event);
	}

	@Override
	protected void onStart() {
		super.onStart();
	}

	/*
	 * @Override protected void onRestart(){ super.onRestart();
	 * setContentView(R.layout.activity_tabhost); initView(); }
	 */

	/*
	 * @Override protected void onActivityResult(int requestCode, int
	 * resultCode, Intent data) { switch (requestCode) { case 1: if(requestCode
	 * == RESULT_OK){ String time = data.getStringExtra("time");
	 * Toast.makeText(TabHostActivity.this,time, Toast.LENGTH_LONG).show(); }
	 * break; } }
	 */

	@Override
	public boolean onKeyDown(int keyCode, KeyEvent event) {
		if (keyCode == KeyEvent.KEYCODE_BACK) {
			if ((System.currentTimeMillis() - mExitTime) > 2000) {
				Object mHelperUtils;
				Toast.makeText(this, "再按一次退出程序", Toast.LENGTH_SHORT).show();
				mExitTime = System.currentTimeMillis();
			} else {
				finish();
			}
			return true;
		}
		return super.onKeyDown(keyCode, event);
	}
}