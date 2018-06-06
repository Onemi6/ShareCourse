package sharecourse.activity;

import java.io.IOException;
import java.io.StringReader;
import java.util.ArrayList;
import java.util.List;

import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlPullParserException;
import org.xmlpull.v1.XmlPullParserFactory;

import sharecourse.adapter.AccountAdapter;
import sharecourse.http.HttpCallbackListener;
import sharecourse.http.HttpUtil;
import sharecourse.myclass.AccountNumber;
import sharecourse.myclass.MyApplication;
import sharecourse.others.ConnectDB;

import android.R.integer;
import android.app.Activity;
import android.app.AlertDialog;
import android.app.Application;
import android.content.DialogInterface;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.preference.PreferenceManager;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.Window;
import android.widget.AdapterView;
import android.widget.CheckBox;
import android.widget.Toast;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ListView;

public class AccountManageActivity extends Activity{
	
	private SharedPreferences preferences;
	private SharedPreferences.Editor editor;
	
	private List<AccountNumber> accountList = new ArrayList<AccountNumber>();
	Cursor cursor;
	ConnectDB db = new ConnectDB();;
	int x = -1;
	private final int CHANG_TRUE = 0;
	private final int CHANG_FAIL = 1;
	private final int CHANG_ERROR = 2;
	String contactWay,userType,userPswd;
	private Handler handler = new Handler(){
		public void handleMessage(Message msg)
		{
		    AlertDialog.Builder dialog;
			switch (msg.what) 
			{
			case CHANG_TRUE:
			{
				MyApplication application = new MyApplication();
				application = (MyApplication) msg.obj;
				userPswd =application.getPassWord();
				if(application.getUserName() == ""||application.getUserName() == null)
				{
					((MyApplication)getApplication()).setUserName(contactWay);
				}
				else
				{
					((MyApplication)getApplication()).setUserName(application.getUserName());
				}
				((MyApplication)getApplication()).setUserNum(application.getUserNum());
				((MyApplication)getApplication()).setPhoneNum(contactWay);
				((MyApplication)getApplication()).setUserType(userType);
				
				editor = preferences.edit();
				if(((MyApplication)getApplication()).isFlagCheck())
				{
					editor.putBoolean("rememberPass", true);
					Log.d("check", Boolean.toString(((MyApplication)getApplication()).isFlagCheck()));
					editor.putString("contactWay", contactWay);
					editor.putString("userPswd", userPswd);
					editor.putString("userType", userType);
				}
				editor.commit();
				break;
			}
			case CHANG_ERROR:
			{
				Toast.makeText(AccountManageActivity.this,"LOGIN_FAIL", Toast.LENGTH_LONG).show();
				dialog = new AlertDialog.Builder(AccountManageActivity.this);
				dialog.setTitle("提示");
				dialog.setMessage("该账号已被更改，是否删除此条记录");
				dialog.setCancelable(false);
				dialog.setPositiveButton("是", new DialogInterface.OnClickListener() {
					@Override
					public void onClick(DialogInterface dialog, int which) {
						db.deleteAccount(contactWay,userType);
					    startActivity(new android.content.Intent(AccountManageActivity.this,AccountManageActivity.class));
					    finish();
					}
				});
				dialog.setNegativeButton("否", new DialogInterface.OnClickListener() {
					@Override
					public void onClick(DialogInterface dialog, int which) {
					}
				});
				dialog.show();
				break;
			}
			
			case CHANG_FAIL:
			{
				Toast.makeText(AccountManageActivity.this,"LOGIN_FAIL", Toast.LENGTH_LONG).show();
				dialog = new AlertDialog.Builder(AccountManageActivity.this);
				dialog.setTitle("提示ʾ");
				dialog.setMessage("发生未知错误，切换失败!");
				dialog.setCancelable(false);
				dialog.setPositiveButton("确定", new DialogInterface.OnClickListener() {
					@Override
					public void onClick(DialogInterface dialog, int which) {
					}
				});
				dialog.show();
				break;
			 }
		}
	}
};
		
	@Override
	protected void onCreate(Bundle savedInstanceState) {
	        super.onCreate(savedInstanceState);
	        requestWindowFeature(Window.FEATURE_NO_TITLE);
	        setContentView(R.layout.account_management);
	        
	        preferences = PreferenceManager.getDefaultSharedPreferences(this);
	        
	        contactWay = ((MyApplication)getApplication()).getPhoneNum();
	        userType = ((MyApplication)getApplication()).getUserType();
	        
	        int i = 0;
			cursor = db.showPageInformation();
			if (cursor.moveToFirst()) {
				do{
				AccountNumber account = new AccountNumber();
				account.setAccountName(cursor.getString(cursor
						.getColumnIndex("accountName")));
				account.setAccountNum(cursor.getString(cursor
						.getColumnIndex("accountNum")));
				account.setAccountType(cursor.getString(cursor
						.getColumnIndex("accountType")));
				
				if(contactWay.equals(cursor.getString(cursor.getColumnIndex("accountNum")))&&userType.equals(cursor.getString(cursor
						.getColumnIndex("accountType"))))
					x = i;
				accountList.add(account);
				i++;
				}while(cursor.moveToNext());
			}
			int len =  accountList.size();
			Log.d("name1",Integer.toString(len));
			
			AccountAdapter accountAdapter = new AccountAdapter(AccountManageActivity.this,R.layout.account, accountList,-1,x);
	        ListView listView = (ListView)findViewById(R.id.list_account);
	        listView.setAdapter(accountAdapter);
	        
	        
	        listView.setOnItemClickListener(new OnItemClickListener() {

				@Override
				public void onItemClick(AdapterView<?> parent, View view,
						int position, long id) {
					AccountAdapter accountAdapter = new AccountAdapter(AccountManageActivity.this,R.layout.account, accountList,x,position);
					ListView listView = (ListView)findViewById(R.id.list_account);
					listView.setAdapter(accountAdapter);
					contactWay = accountList.get(position).getAccountNum();
					x = position;
					userType = accountList.get(position).getAccountType();
					Log.d("userType", userType);
					int type;
					if(userType.equals("教师"))
					{
					 type = 2;
					}
	                else {
	                 type = 1;
			         }
					   HttpUtil.sendHttpRequest("http://115.159.214.57:8080/sharecourse/User/getUserByPhoneNum.jsp?phoneNum="+contactWay+"&userType="+type, 
			    			   new HttpCallbackListener() {
								@Override
								public void onFinish(String response) {
									XmlPullParserFactory factory;
									try {
										factory = XmlPullParserFactory.newInstance();
										XmlPullParser xmlPullParser = factory.newPullParser();
										xmlPullParser.setInput(new StringReader(response));
										int eventType = xmlPullParser.getEventType();
										int userType = -1;
										MyApplication application = new MyApplication();
										
										while (eventType != XmlPullParser.END_DOCUMENT) {
											String nodeName = xmlPullParser.getName();
											switch (eventType) {
											case XmlPullParser.START_TAG:
											{
												if("userType".equals(nodeName))
												{
													try {
														userType =  Integer.parseInt(xmlPullParser.nextText());
													} catch (IOException e) {
														e.printStackTrace();
													}
												}
												if("password".equals(nodeName))
												{
													try {
														application.setPassWord(xmlPullParser.nextText());
													} catch (IOException e) {
														e.printStackTrace();
													}
												}
												if("personName".equals(nodeName))
												{
													try {
														application.setUserName(xmlPullParser.nextText());
													} catch (IOException e) {
														e.printStackTrace();
													}
												}
												if("userNum".equals(nodeName))
												{
													try {
														application.setUserNum(xmlPullParser.nextText());
													} catch (IOException e) {
														e.printStackTrace();
													}
												}
												break;
											}
											case XmlPullParser.END_TAG:
											{
									
												if(userType == 1|| userType == 2)
												{
													Message message = new Message();
													message.what = CHANG_TRUE;
													message.obj = application;
													handler.sendMessage(message);
												}
												else if(userType == 0)
												{
													Message message = new Message();
													message.what = CHANG_ERROR;
													handler.sendMessage(message);
												}
												break;
											  }
										   }
											
											try {
												eventType  = xmlPullParser.next();
											} catch (IOException e) {
												e.printStackTrace();
											}
										}
									} catch (XmlPullParserException e) {
										e.printStackTrace();
									}
								}
								@Override
								public void onError(Exception e) {
									Message message = new Message();
									message.what = CHANG_FAIL;
									handler.sendMessage(message);
								}
					   });
				}
	        	
			});
	        findViewById(R.id.return_img).setOnClickListener(new OnClickListener() {
				
				@Override
				public void onClick(View arg0) {
					if(userType.equals("教师"))
					{
						startActivity(new android.content.Intent(AccountManageActivity.this,TeacherSystemSetupActivity.class));
					}
					else {
						startActivity(new android.content.Intent(AccountManageActivity.this,ParentSystemSetupActivity.class));
					}
				}
			});
	    }
}
