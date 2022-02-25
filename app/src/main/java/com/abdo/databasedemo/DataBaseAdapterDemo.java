package com.abdo.databasedemo;
/*
import java.util.Locale;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

public class DataBaseAdapterDemo {
	private Context context;
	static DataBaseHelper dbHelper;
	private boolean englishFlag = true;
	public DataBaseAdapterDemo(Context _context){
		dbHelper = new DataBaseHelper(_context);
		context = _context;
	}
	
	public long insertSponser(SponserDTO sponser){
		SQLiteDatabase db = dbHelper.getWritableDatabase();
		ContentValues contentValues = new ContentValues();
		contentValues.put(DataBaseHelper.SPONSER_ID, sponser.getId());
		contentValues.put(DataBaseHelper.SPONSER_URL, sponser.getUrl());
		contentValues.put(DataBaseHelper.SPONSER_LOGO_PATH, sponser.getLogoPath());
		//contentValues.put(DataBaseHelper.SPONSER_LOGO_MODIFIED_DATE, sponser.getLastModifiedDate());
		long id = db.insert(DataBaseHelper.SPONSER_TABLE_NAME, null, contentValues);

		return id;
	}
	
	public String getBankLogoPath(String bankID){
		String path=null;
		Cursor c;
		SQLiteDatabase db = dbHelper.getReadableDatabase();
		String[] columns = {DataBaseHelper.BANK_ID,
				DataBaseHelper.LOGO_PATH};
		//               table name                , columns,  selection               , selection Args
		c = db.query(DataBaseHelper.BANK_TABLE_NAME, columns, columns[0] + "=?", new String[]{
				bankID}, null, null, null);
		while ( c.moveToNext()) {
			path = c.getString(1);
		}
		
		return path;
	}
	
	public SponserDTO[] getAllSponsers(){
		SponserDTO[] sponsers = null;
		int i=0;
		Cursor c;
		SQLiteDatabase db = dbHelper.getReadableDatabase();
		String[] columns = {DataBaseHelper.SPONSER_ID,
				DataBaseHelper.SPONSER_URL,
				DataBaseHelper.SPONSER_LOGO_PATH,
				DataBaseHelper.SPONSER_LOGO_MODIFIED_DATE};
		//query (String table, String[] columns, String selection, String[] selectionArgs, String groupBy, String having, String orderBy)
		c = db.query(DataBaseHelper.SPONSER_TABLE_NAME, columns, null, null, null, null, null);
		sponsers = new SponserDTO[c.getCount()];
		while ( c.moveToNext()) {
			SponserDTO sponser = new SponserDTO();
			sponser.setId(""+c.getInt(0));
			sponser.setUrl(c.getString(1));
			sponser.setLogoPath(c.getString(2));
			sponser.setLastModifiedDate(c.getString(3));
			sponsers[i++]=sponser;
		}		
		return sponsers;
	}
	
	public void deleteAllSponsers(){
		SQLiteDatabase db = dbHelper.getWritableDatabase();
		db.execSQL("delete from " + DataBaseHelper.SPONSER_TABLE_NAME);
	}
	
	public long insertBank(Bank b){
		SQLiteDatabase db = dbHelper.getWritableDatabase();
		ContentValues contentValues = new ContentValues();
		contentValues.put(DataBaseHelper.BANK_ID, b.getBankID());
		contentValues.put(DataBaseHelper.BANK_NAME, b.getBankName());
		contentValues.put(DataBaseHelper.BANK_NAME_AR, b.getBankName_ar());
		contentValues.put(DataBaseHelper.BANK_HOT_NUM, b.getBankHotNumber());
		contentValues.put(DataBaseHelper.BANK_URL, b.getBankURL());
		contentValues.put(DataBaseHelper.LOGO_PATH, b.getLogoPath());
		contentValues.put(DataBaseHelper.BANK_MODIFIED_DATE, b.getBankModifiedDate());
		contentValues.put(DataBaseHelper.LOGO_MODIFIED_DATE, b.getLogoModifiedDate());
		long id = db.insert(DataBaseHelper.BANK_TABLE_NAME, null, contentValues);
		return id;
	}
	
	public  void insertBankArray(Bank[] bArr){
		// we might have a thread here

		for (int i=0 ; i<bArr.length ; i++)
			insertBank(bArr[i]);
	}
	
	public Bank getBankById(String id){
		Bank bank = null;
		Cursor c;
		SQLiteDatabase db = dbHelper.getReadableDatabase();
		String[] columns = {DataBaseHelper.BANK_ID,
				DataBaseHelper.BANK_NAME,
				DataBaseHelper.BANK_NAME_AR,
				DataBaseHelper.BANK_URL,
				DataBaseHelper.BANK_HOT_NUM,
				DataBaseHelper.LOGO_PATH};
		//query (String table, String[] columns, String selection, String[] selectionArgs, String groupBy, String having, String orderBy)
		c = db.query(DataBaseHelper.BANK_TABLE_NAME, columns, DataBaseHelper.BANK_ID+" = ?", new String[]{id}, null, null, null);
		Locale current = context.getResources().getConfiguration().locale;
		if(current.getDisplayLanguage().equals("العربية"))
			bank = new Bank(false);
		else 
			bank = new Bank(true);
		if ( c.moveToNext()) {
			bank.setBankID(""+c.getInt(0));
			bank.setBankName(c.getString(1));
			bank.setBankName_ar(c.getString(2));
			bank.setBankURL(c.getString(3));
			bank.setBankHotNumber(c.getString(4));
			bank.setLogoPath(c.getString(5));	
		}
		return bank;
	}
	public  Bank[] getAllBanks(){
		Bank[] banks = null;
		int i=0;
		Cursor c;
		SQLiteDatabase db = dbHelper.getReadableDatabase();
		String[] columns = {DataBaseHelper.BANK_ID,
				DataBaseHelper.BANK_NAME,
				DataBaseHelper.BANK_NAME_AR,
				DataBaseHelper.BANK_URL,
				DataBaseHelper.BANK_HOT_NUM,
				DataBaseHelper.LOGO_PATH};
		//query (String table, String[] columns, String selection, String[] selectionArgs, String groupBy, String having, String orderBy)
		c = db.query(DataBaseHelper.BANK_TABLE_NAME, columns, null, null, null, null, DataBaseHelper.BANK_NAME+" COLLATE nocase;");
		banks = new Bank[c.getCount()];
		while ( c.moveToNext()) {
			Bank b = new Bank();
			b.setBankID(""+c.getInt(0));
			b.setBankName(c.getString(1));
			b.setBankName_ar(c.getString(2));
			b.setBankURL(c.getString(3));
			b.setBankHotNumber(c.getString(4));
			b.setLogoPath(c.getString(5));	
			banks[i++]=b;
		}		
		return banks;
	}
	public  Bank[] getAllBanksForSpinner(){
		Bank[] banks = null;
		Bank b;
		int i=0;
		Cursor c;
		SQLiteDatabase db = dbHelper.getReadableDatabase();
		String[] columns = {DataBaseHelper.BANK_ID,
				DataBaseHelper.BANK_NAME,
				DataBaseHelper.BANK_NAME_AR,
				DataBaseHelper.BANK_URL,
				DataBaseHelper.BANK_HOT_NUM,
				DataBaseHelper.LOGO_PATH};
		//query (String table, String[] columns, String selection, String[] selectionArgs, String groupBy, String having, String orderBy)
		c = db.query(DataBaseHelper.BANK_TABLE_NAME, columns, null, null, null, null, null);
		
		banks = new Bank[c.getCount()+1];
		Locale current = context.getResources().getConfiguration().locale;
		if(current.getDisplayLanguage().equals("العربية"))
			englishFlag = false;
		else
			englishFlag = true;
		b = new Bank(englishFlag);
		b.setBankName(context.getResources().getString(R.string.atm_id));
		b.setBankName_ar(context.getResources().getString(R.string.atm_id));
		banks[i++] = b;
		while ( c.moveToNext()) {
			b = new Bank(englishFlag);
			b.setBankID(""+c.getInt(0));
			b.setBankName(c.getString(1));
			b.setBankName_ar(c.getString(2));
			b.setBankURL(c.getString(3));
			Log.i("Value of url in the Database:",""+c.getString(3));
			b.setBankHotNumber(c.getString(4));
			b.setLogoPath(c.getString(5));	
			banks[i++]=b;
		}		
		return banks;
	}
	
	static class DataBaseHelper extends SQLiteOpenHelper{
		private static final int DATABASE_VERSION = 1;
		private static final String DATABASE_NAME = "atm_guide_db.db";

		private static final String BANK_TABLE_NAME = "BANK";
		private static final String UID = "_id";
		private static final String BANK_ID = "bnk_id";
		private static final String BANK_NAME = "bnk_name";
		private static final String BANK_NAME_AR = "bnk_name_ar";
		private static final String BANK_HOT_NUM = "bnk_hot_num";
		private static final String LOGO_PATH = "logo_path";
		private static final String BANK_URL = "bnk_url";
		private static final String BANK_MODIFIED_DATE = "bnk_modified_date";
		private static final String LOGO_MODIFIED_DATE = "logo_modified_date";
		
		private static final String CREATE_BANK_TABLE = "CREATE TABLE " + BANK_TABLE_NAME + " (" +UID + " INTEGER PRIMARY KEY AUTOINCREMENT, "+
		 BANK_ID+" INTEGER,"+ BANK_NAME +" TEXT, " + BANK_NAME_AR + " TEXT, " + BANK_HOT_NUM + " TEXT, "+
		 LOGO_PATH + " TEXT, " + BANK_URL + " TEXT, " + BANK_MODIFIED_DATE + " TEXT, "+
		 LOGO_MODIFIED_DATE + " TEXT);";
		
		private static final String SPONSER_TABLE_NAME = "SPONSER";
		private static final String SPONSER_UID = "_id";
		private static final String SPONSER_ID = "bnk_id";
		private static final String SPONSER_URL = "bnk_url";
		private static final String SPONSER_LOGO_PATH = "logo_path";
		private static final String SPONSER_LOGO_MODIFIED_DATE = "logo_modified_date";
		
		private static final String CREATE_SPONSER_TABLE = "CREATE TABLE " + SPONSER_TABLE_NAME + " (" +SPONSER_UID + " INTEGER PRIMARY KEY AUTOINCREMENT, "+
				SPONSER_ID+" INTEGER,"+ SPONSER_LOGO_PATH + " TEXT, " + SPONSER_URL + " TEXT, " + 
				SPONSER_LOGO_MODIFIED_DATE + " TEXT);";

		public DataBaseHelper(Context context){
			super(context, DATABASE_NAME, null, DATABASE_VERSION);
			
		}
		@Override
		public void onCreate(SQLiteDatabase db) {
			// TODO Auto-generated method stub
			db.execSQL(CREATE_BANK_TABLE);
			db.execSQL(CREATE_SPONSER_TABLE);
		}

		@Override
		public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
			// TODO Auto-generated method stub

		}
	} 
	

}
*/