package com.roadpress;

import java.util.ArrayList;
import java.util.List;

import android.app.Activity;
import android.content.Context;
import android.database.Cursor;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ListView;
import android.widget.TextView;

import com.roadpress.db.DbAdapter;

public class DbActivity extends Activity{
	
	private Context 		mContext;
	private ListView 		mList;
	private DbListAdapter 	mDbListAdt;
	private DbAdapter		mDbAdt;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_db);
		
		mContext = this;
		
		findViewById(R.id.btn_open).setOnClickListener(mClick);
		findViewById(R.id.btn_close).setOnClickListener(mClick);
		findViewById(R.id.btn_list).setOnClickListener(mClick);
		findViewById(R.id.btn_insert_item).setOnClickListener(mClick);
		findViewById(R.id.btn_delete_item).setOnClickListener(mClick);
		
		mDbAdt = new DbAdapter(mContext);
		
		mDbListAdt = new DbListAdapter();
		
		mList = (ListView)findViewById(android.R.id.list);
		mList.setAdapter(mDbListAdt);
		
	}
	
	@Override
	protected void onDestroy() {
		
		if (mDbAdt != null)
			mDbAdt.close();
		super.onDestroy();
	};
	
	View.OnClickListener mClick = new View.OnClickListener() {
		
		@Override
		public void onClick(View v) {
			switch(v.getId()) {
			
			case R.id.btn_open : {
				mDbAdt.open();
			}
			break;
			case R.id.btn_close : {
				mDbAdt.close();
			}
			break;
			case R.id.btn_list : {
				mDbListAdt.updateData();
				mDbListAdt.notifyDataSetChanged();
			}
			break;
			case R.id.btn_insert_item : {
				mDbAdt.createUser("aa", "aa", "aa");
			}
			break;
			case R.id.btn_delete_item : {
				int nRow = mDbAdt.getFirstId();
				if (nRow > 0)
					mDbAdt.deleteInfo("" + nRow);
			}
			break;
			}
		}
	};
	
	class DbListAdapter extends BaseAdapter{
		private List<User> mUserList;
		LayoutInflater mInflater;
		
		public DbListAdapter() {
			mUserList = new ArrayList<DbActivity.User>();
			mInflater = (LayoutInflater)mContext.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
		}

		public void updateData() {
			Cursor cursor = mDbAdt.getAllList();
			if (cursor == null)
				return;
			
			mUserList = new ArrayList<DbActivity.User>();
			
			if (cursor.getCount() <= 0)
				return;
			do{
				String sRow 		= cursor.getString(0);
				String sUserName 	= cursor.getString(1);
				String sUserId 		= cursor.getString(2);
				String sUserPwd 	= cursor.getString(3);
				String sUserTel 	= cursor.getString(4);
				String sUserBirth 	= cursor.getString(5);
				String sUserAddr 	= cursor.getString(6);
				String sUserEmail 	= cursor.getString(7);
				User item = new User();
				item.sRowId 		= sRow;
				item.sUserName 		= sUserName;
				item.sUserId 		= sUserId;
				item.sUserPwd 		= sUserPwd;
				item.sUserTel 		= sUserTel;
				item.sUserBirth 	= sUserBirth;
				item.sUserAddr 		= sUserAddr;
				item.sUserEmail 	= sUserEmail;
				
				mUserList.add(item);
			}
			while(cursor.moveToNext());
			
		}
		@Override
		public int getCount() {
			return mUserList.size();
		}

		@Override
		public Object getItem(int pos) {
			return mUserList.get(pos);
		}

		@Override
		public long getItemId(int pos) {
			return pos;
		}

		@Override
		public View getView(int pos, View v, ViewGroup vg) {
			if (v == null) {
				v = mInflater.inflate(android.R.layout.simple_list_item_1, vg, false);
			}
			if (v == null){
				return null;
			}
			
			TextView txt = (TextView) v.findViewById(android.R.id.text1);
			
			txt.setText("ROW = " + mUserList.get(pos).sRowId + "// NAME = " + mUserList.get(pos).sUserName + "// ID = " + mUserList.get(pos).sUserId);
			
			
			return v;
		}
		
	}
	
	public class User {
		public String sRowId;
		public String sUserName;
		public String sUserId;
		public String sUserPwd;
		public String sUserTel;
		public String sUserBirth;
		public String sUserAddr;
		public String sUserEmail;
	}
}
