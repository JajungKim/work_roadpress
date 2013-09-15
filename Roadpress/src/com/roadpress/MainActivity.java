package com.roadpress;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Toast;

public class MainActivity extends Activity {
	private OnClickListener mClickListener = new MainClickListener();
	private Context 		mContext;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);

		mContext = this;

		setClickListener(R.id.button1);
		setClickListener(R.id.button2);
		setClickListener(R.id.button3);
		setClickListener(R.id.button4);
		setClickListener(R.id.button5);
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.main, menu);
		return true;
	}
	
	private void setClickListener(int rid) {
		findViewById(rid).setOnClickListener(mClickListener);
	}

	class MainClickListener implements OnClickListener {
		@Override
		public void onClick(View v) {
			Intent 	intent		= new Intent();
			int 	viewId 		= v.getId();
			String 	pageName 	= "";

			switch (viewId) {
			case R.id.button1: 
				// 교통정보 API
				pageName = "교통정보API";
//				intent.setClass(mContext, TrafficInfoActivity.class);
				startActivity(intent);
				break;
			case R.id.button2:
				// 구글 API
				pageName = "구글 API";
				break;
			case R.id.button3: 
				// 페이스북 API
				pageName = "페이스북 API";
//				intent.setClass(mContext, FacebookActivity.class);
				startActivity(intent);
				break;
			case R.id.button4: 
				// DataBase
				pageName = "DataBase";
//				intent.setClass(mContext, DbActivity.class);
				startActivity(intent);
				break;
			case R.id.button5: 
				// Activity Sample
				pageName = "Sample Page";
//				intent.setClass(mContext, SampleActivity.class);
				startActivity(intent);
				break;
			}

			String message = String.format("Activity:%s", pageName);
			showToast(message);
		}

		private void showToast(String message) {
			Toast.makeText(mContext, String.format("%s", message), Toast.LENGTH_SHORT).show();
		}
	}
}
