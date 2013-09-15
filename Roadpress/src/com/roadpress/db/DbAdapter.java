package com.roadpress.db;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteException;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

public class DbAdapter {
	
	private static final String TAG = "DbAdapter";
	
	// DB name
	private static final String DATABASE_NAME = "TsiDb";
	
	// table info
	public static final String DATABASE_TSI_TABLE 	= "tbl_tsi";
	public static final String KEY_ROWID 			= "_id";
	public static final String KEY_USER_NAME 		= "user_name";		// 사용자
	public static final String KEY_USER_ID 			= "user_id";		// 사용자 아이디(페이스북)
	public static final String KEY_USER_PWD 		= "user_pwd";		// 사용자 비밀번호
    public static final String KEY_TEL_NO 			= "tel_no";			// 사용자 전화번호
    public static final String KEY_USER_BIRTH 		= "user_birth";		// 사용자 생일 
    public static final String KEY_USER_ADDR 		= "user_addr";		// 사용자 주소
    public static final String KEY_USER_EMAIL 		= "user_email";		// 사용자 email
    
	//sql statement
    private static final String CREATE_TABLE_TSI = " create table "+ DATABASE_TSI_TABLE
    	    +"(" 
    	    + KEY_ROWID + " INTEGER PRIMARY KEY AUTOINCREMENT" + ","
    		+ KEY_USER_NAME + " TEXT NOT NULL" + "," + KEY_USER_ID + " TEXT NOT NULL" + ","
    		+ KEY_USER_PWD + " TEXT NOT NULL" + "," + KEY_TEL_NO + " TEXT" + "," 
    		+ KEY_USER_BIRTH + " TEXT" + "," + KEY_USER_ADDR + " TEXT"+ "," + KEY_USER_EMAIL + " TEXT"+ ");";
    
    private final Context mContext;
    private static final int DATABASE_VERSION = 1;
    
    private static boolean DEBUG = true;
    
    private DatabaseHelper pDbHelper;
    private SQLiteDatabase pDb;
    
	private static class DatabaseHelper extends SQLiteOpenHelper {

    public DatabaseHelper(Context context) {
            super(context, DATABASE_NAME, null, DATABASE_VERSION);
        }

        @Override
        public void onCreate(SQLiteDatabase db) {
            db.execSQL(CREATE_TABLE_TSI);          
        }

        @Override
        public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
            if (DEBUG) Log.w("TAG", "Upgrading database from version " + oldVersion + " to "
                    + newVersion + ", which will destroy all old data");
            db.execSQL("DROP TABLE IF EXISTS "+DATABASE_TSI_TABLE);
            onCreate(db);
        }
        
    }
	
    public DbAdapter(Context ctx) {
        this.mContext = ctx;
    }
	
    public DbAdapter open() throws SQLException {
    	if (DEBUG) Log.w("TAG", "open()");
        pDbHelper = new DatabaseHelper(mContext);
        try {
            pDb = pDbHelper.getWritableDatabase();
        } catch (SQLiteException e) {
        }
        return this;
    }
    
    public void close() {
    	if (DEBUG) Log.w("TAG", "close()");
    	if (pDbHelper != null)
    		pDbHelper.close();
    }

    public Long createUser(String sUserName, String sUserId, String sUserPwd) {
    	if (DEBUG) Log.w("TAG", "createUser()");
    	
        ContentValues values = new ContentValues();
        
        values.put(KEY_USER_NAME, sUserName);
        values.put(KEY_USER_ID, sUserId);
        values.put(KEY_USER_PWD, sUserPwd);
        
        if (DEBUG) Log.w("TAG", "value = " + values.toString());
        
        return pDb.insert(DATABASE_TSI_TABLE, null, values);  
    }
	
    public boolean deleteList(String DB_TABLE, String sColumn1, String sData1, String sColumn2, String sData2) {
    	if (DEBUG) Log.w("TAG", "deleteList(2 param)");
    	if (DEBUG) Log.w("TAG", "value = " + sColumn1 + "= '" + sData1+"' AND "+sColumn2 + "= '" + sData2+"'");
        return pDb.delete(DB_TABLE, sColumn1 + "= '" + sData1+"' AND "+sColumn2 + "= '" + sData2+"'", null) > 0;
    }
	
    public boolean deleteList(String DB_TABLE, String sColumn, String sData) {
    	if (DEBUG) Log.w("TAG", "deleteList(1 param)");
    	if (DEBUG) Log.w("TAG", "value = " + sColumn + "= '" + sData+"'");
        return pDb.delete(DB_TABLE, sColumn + "= '" + sData+"'", null) > 0;
    }
    
    public boolean deleteAllList(String DB_TABLE) {
    	if (DEBUG) Log.w("TAG", "deleteAllList()");
        return pDb.delete(DB_TABLE, null, null) > 0;
    }
    
    public boolean deleteInfo(String sRow){
    	if (DEBUG) Log.w("TAG", "deleteInfo() sRow = " + sRow);
		return pDb.delete(DATABASE_TSI_TABLE, sRow, null) > 0;
    }
   
    public Cursor getAllList() {
    	if (DEBUG) Log.w("TAG", "getAllList() ");
		Cursor cursor = pDb.query(DATABASE_TSI_TABLE, new String[] {KEY_ROWID, KEY_USER_NAME, KEY_USER_ID, KEY_USER_PWD, 
				KEY_TEL_NO, KEY_USER_BIRTH, KEY_USER_ADDR, KEY_USER_EMAIL}, null, null, null, null, null);
		if (cursor != null) { 
			cursor.moveToFirst();
		} 
		return cursor; 
	}
    
    public Cursor getList(String DB_TABLE, String sUserId) throws SQLException {
    	if (DEBUG) Log.w("TAG", "getList() sUserId = " + sUserId);
        Cursor cursor = pDb.query(DATABASE_TSI_TABLE, new String[] {KEY_ROWID, KEY_USER_NAME, KEY_USER_ID, KEY_USER_PWD, 
				KEY_TEL_NO, KEY_USER_BIRTH, KEY_USER_ADDR, KEY_USER_EMAIL}, KEY_USER_ID 
				+ "='" + sUserId+"'", null, null, null, null, null);
        if (cursor != null) {
            cursor.moveToFirst();
        }
        return cursor;
    }
    
    public boolean updateListSub(int rowid, String sChangeTelNo, String sChangeBirth, String sChangeAddr, String sChangeEmail) {
        ContentValues values = new ContentValues();
        
        values.put(KEY_TEL_NO, sChangeTelNo);
        values.put(KEY_USER_BIRTH, sChangeBirth);
        values.put(KEY_USER_ADDR, sChangeAddr);
        values.put(KEY_USER_EMAIL, sChangeEmail);
        
        return pDb.update(DATABASE_TSI_TABLE, values, KEY_ROWID + "= " + rowid, null) > 0;
    }
    
    public int getFirstId() {
        int nRet =  -1;
        if (DEBUG) Log.w("TAG", "getAllList() ");
		Cursor cursor = pDb.query(DATABASE_TSI_TABLE, new String[] {KEY_ROWID, KEY_USER_NAME, KEY_USER_ID, KEY_USER_PWD, 
				KEY_TEL_NO, KEY_USER_BIRTH, KEY_USER_ADDR, KEY_USER_EMAIL}, null, null, null, null, null);
		
		if (DEBUG) Log.w("TAG", "cursor.getCount() =  " + cursor.getCount());
		if (cursor != null && cursor.getCount() > 0) {
			cursor.moveToFirst();
			String sRet = cursor.getString(0);
			nRet = Integer.parseInt(sRet);
		}
        
        return nRet;
    }

}


