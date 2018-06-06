package sharecourse.others;

import android.content.Context;
import android.database.DatabaseErrorHandler;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteDatabase.CursorFactory;
import android.database.sqlite.SQLiteOpenHelper;
import android.widget.Toast;

public class DatebaseHelper extends SQLiteOpenHelper{
	private String Account = "CREATE TABLE account("
			+"accountName text," 
			+"accountNum text,"
			+"accountType text,"
			+"PRIMARY KEY (accountNum,accountType))";
	
	Context mContext;
	
	public DatebaseHelper(Context context, String name, CursorFactory factory,
			int version) {
		super(context, name, factory, version);
		mContext = context;
	}

	@Override
	public void onCreate(SQLiteDatabase db) {
		db.execSQL(Account);
	}

	@Override
	public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
		db.execSQL("drop table if exists Account");
		onCreate(db);
		
	}

}
