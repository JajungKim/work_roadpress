package com.roadpress;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;

public class IntroActivity extends Activity {
	private Context mContext;
	private Handler mHnd;
	private Runnable mRunnable;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_intro);
		
		mContext = this;
		
		mHnd = new Handler();
		mRunnable = new IntroRunnable();

		mHnd.postDelayed(mRunnable, 1000);
	}
	
	class IntroRunnable implements Runnable {
		@Override
		public void run() {
			Intent intent = new Intent(mContext, MainActivity.class);
			startActivity(intent);
			finish();
			
			overridePendingTransition(android.R.anim.fade_in, android.R.anim.fade_out);
		}
	}
	
	@Override
	public void onBackPressed() {
		super.onBackPressed();
		mHnd.removeCallbacks(mRunnable);
	}
}
