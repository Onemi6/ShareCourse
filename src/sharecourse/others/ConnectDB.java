package sharecourse.others;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;


import sharecourse.activity.LoginActivity;
import sharecourse.myclass.AccountNumber;


import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;
import android.widget.Toast;

public class ConnectDB {
	public static final String DB_NAME = "ShareCourse.db";
	public static final int VERSION = 10;
	private static DatebaseHelper helper;
	private SQLiteDatabase db;
	Context mContext;

	public ConnectDB() {

	}

	public ConnectDB(Context context) {
		helper = new DatebaseHelper(context, DB_NAME, null,
				VERSION);
		mContext = context;
	}

	public Cursor showPageInformation()
	{
		db = helper.getWritableDatabase();
		Cursor cursor = db.rawQuery("select * from Account",null);
		return cursor;
	}
	
	public void insertAccount(AccountNumber account)
	{
		Toast.makeText(mContext,"insertAccount", Toast.LENGTH_LONG).show();
		ContentValues values = new ContentValues();
		values.put("accountName", account.getAccountName());
		values.put("accountNum", account.getAccountNum());
		values.put("accountType", account.getAccountType());
		db = helper.getWritableDatabase();
		db.insert("Account", null, values);
		
	}
	
	public void deleteAccount(String account,String userType)
	{
		db = helper.getWritableDatabase();
		db.delete("Account", "accountNum=? and accountType=?", new String[]{account,userType});
	}
	
	public void updateAccount(AccountNumber account,String oldAccountNum,String userType)
	{
		Log.d("accountNum", account.getAccountNum());
		Log.d("accountName", account.getAccountName());
		Log.d("accountType", account.getAccountType());
		Log.d("accountType2", oldAccountNum);
		
		db = helper.getWritableDatabase();
		ContentValues values = new ContentValues();
		values.put("accountNum", account.getAccountNum());
		values.put("accountName", account.getAccountName());
		values.put("accountType", account.getAccountType());
		db.update("Account", values, "accountNum=? and accountType=?",new String[]{oldAccountNum,userType});
	}
	
}
