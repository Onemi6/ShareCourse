package sharecourse.activity;

import java.io.IOException;
import java.io.StringReader;

import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlPullParserException;
import org.xmlpull.v1.XmlPullParserFactory;

import sharecourse.http.HttpCallbackListener;
import sharecourse.http.HttpUtil;
import sharecourse.myclass.AccountNumber;
import sharecourse.myclass.MyApplication;
import sharecourse.others.ConnectDB;
import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.preference.PreferenceManager;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;

public class LoginActivity extends Activity implements android.view.View.OnClickListener{

	private SharedPreferences preferences;
	private SharedPreferences.Editor editor;
	private CheckBox rememberPass;
	private static final int LOGIN_TRUE = 0;
	private static final int LOGIN_FALSE = 1;
	private static final int LOGIN_FAIL = 3;
	private static final String IP = "115.159.214.57:8080";
	boolean isRemember;
	EditText contactWay1;
	String contactWay ;
	EditText userPswd1;
	String userPswd ;
	RadioGroup object;
	RadioButton button=null;
	AlertDialog.Builder dialog;
	String userType;
	String type="2";
	
	
	private Handler handler = new Handler(){
		public void handleMessage(Message msg)
		{
		    AlertDialog.Builder dialog;
			switch (msg.what) 
			{
			case LOGIN_TRUE:
			{
				
				MyApplication application = new MyApplication();
				application = (MyApplication) msg.obj;
				String response =application.getPassWord();
				
				AccountNumber account = new AccountNumber();
				
				if(response.equals(userPswd))
				{
				if(application.getUserName() == ""||application.getUserName() == null)
				{
					((MyApplication)getApplication()).setUserName(contactWay);
					 account.setAccountName(contactWay);
				}
				else
				{
					((MyApplication)getApplication()).setUserName(application.getUserName());
					 account.setAccountName(application.getUserName());
				}
				((MyApplication)getApplication()).setUserNum(application.getUserNum());
				((MyApplication)getApplication()).setPhoneNum(contactWay);
				((MyApplication)getApplication()).setUserType(userType);
				
			
				
				account.setAccountNum(contactWay);
                account.setAccountType(userType);			
				ConnectDB db = new ConnectDB(LoginActivity.this);
				db.insertAccount(account);
				
				editor = preferences.edit();
				if(rememberPass.isChecked())
				{
					((MyApplication)getApplication()).setFlagCheck(true);
					editor.putBoolean("rememberPass", true);
					editor.putString("contactWay", contactWay);
					editor.putString("userPswd", userPswd);
					editor.putString("userType", userType);
				}
				else
				{
				   ((MyApplication)getApplication()).setFlagCheck(false);
					editor.clear();
				}
				editor.commit();
					startActivity(new android.content.Intent(LoginActivity.this,TabHostActivity.class));
				finish();
				}
				else
				{
					dialog = new AlertDialog.Builder(LoginActivity.this);
					dialog.setTitle("提示");
					dialog.setMessage("密码错误，请重新填写！");
					dialog.setCancelable(false);
					dialog.setPositiveButton("知道了", new DialogInterface.OnClickListener() {
						
						@Override
						public void onClick(DialogInterface dialog, int which) {
							
						}
					});
					dialog.show();
				}
				break;
			}
			case LOGIN_FALSE:
			{
				dialog = new AlertDialog.Builder(LoginActivity.this);
				dialog.setTitle("提示");
				dialog.setMessage("您还没有注册，请注册后登录！");
				dialog.setCancelable(false);
				dialog.setPositiveButton("现在注册", new DialogInterface.OnClickListener() {
					
					@Override
					public void onClick(DialogInterface dialog, int which) {
						
						startActivity(new android.content.Intent(LoginActivity.this,RegisterActivity.class));
					}
				});
				dialog.setNegativeButton("知道了,等下去注册", new DialogInterface.OnClickListener() {
					
					@Override
					public void onClick(DialogInterface dialog, int which) {
						
					}
				});
				dialog.show();
				break;
			    }
			
			case LOGIN_FAIL:
			{
				dialog = new AlertDialog.Builder(LoginActivity.this);
				dialog.setTitle("提示");
				dialog.setMessage("发生未知错误，登录失败！");
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
	        setContentView(R.layout.login);
	        
	        contactWay1 = (EditText)findViewById(R.id.userId);
	        userPswd1 = (EditText)findViewById(R.id.userPswd);
			object =(RadioGroup)findViewById(R.id.object);
			   
	        preferences = PreferenceManager.getDefaultSharedPreferences(this);
	        rememberPass = (CheckBox)findViewById(R.id.rememberPass);
	        isRemember = preferences.getBoolean("rememberPass", false);
	        if(isRemember)
	        {
	        	String contactWay = preferences.getString("contactWay", "");
	        	String userPswd = preferences.getString("userPswd", "");
	        	String userType = preferences.getString("userType", "");
	        	contactWay1.setText(contactWay);
	        	userPswd1.setText(userPswd);
	        	if(userType.equals("教师"))
	        	{
	        		button = (RadioButton)object.getChildAt(0);
	    			button.setChecked(true);
	        	}
	        	else if(userType.equals("在校生家长"))
	        	{
	        		button = (RadioButton)object.getChildAt(1);
	    			button.setChecked(true);
	        	}
	        	else if(userType.equals("毕业生家长"))
	        	{
	        		button = (RadioButton)object.getChildAt(2);
	    			button.setChecked(true);
	        	}
	        	rememberPass.setChecked(true);
	        }
	        else
	        {
	        	button = (RadioButton)object.getChildAt(0);
    			button.setChecked(true);
	        }
	       
	        findViewById(R.id.login).setOnClickListener(LoginActivity.this);
	        findViewById(R.id.register).setOnClickListener(LoginActivity.this);
	    }

	@Override
	public void onClick(View v) {
		
		int i;
		switch (v.getId()) {
		
		case R.id.login:
			
			contactWay = contactWay1.getText().toString();
			userPswd = userPswd1.getText().toString();
			for(i=0; i<object.getChildCount();i++)
			{
			 button = (RadioButton)object.getChildAt(i);
			   if(button.isChecked())
				   break;
			}
			
			if(i == object.getChildCount() || contactWay.equals("") || userPswd.equals(""))
			{
				AlertDialog.Builder dialog = new AlertDialog.Builder(LoginActivity.this);
				dialog.setTitle("提示");
				dialog.setMessage("请填写登录账号和密码以及身份信息！");
				dialog.setCancelable(false);
				dialog.setPositiveButton("知道了", new DialogInterface.OnClickListener() {
					
					@Override
					public void onClick(DialogInterface dialog, int which) {
					}
				});
				dialog.show();
			}
			else 
			{
				userType =  button.getText().toString();
				
				if(userType.equals("教师"))
					type="2";
				else 
				{
					type="1";
				}
				
				   HttpUtil.sendHttpRequest("http://115.159.214.57:8080/sharecourse/User/getUserByPhoneNum.jsp?phoneNum="+contactWay+"&userType="+type, 
		    			   new HttpCallbackListener() {

							@Override
							public void onFinish(String response) {
								Log.d("listener1",response.toString());
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
												Log.d("listener2",Integer.toString(eventType));
											}
											if("password".equals(nodeName))
											{
												try {
													application.setPassWord(xmlPullParser.nextText());
												} catch (IOException e) {
													e.printStackTrace();
												}
												Log.d("listener2",Integer.toString(eventType));
											}
											if("personName".equals(nodeName))
											{
												try {
													application.setUserName(xmlPullParser.nextText());
												} catch (IOException e) {
													e.printStackTrace();
												}
												Log.d("listener2",Integer.toString(eventType));
											}
											if("userNum".equals(nodeName))
											{
												try {
													application.setUserNum(xmlPullParser.nextText());
												} catch (IOException e) {
													e.printStackTrace();
												}
												Log.d("listener2",Integer.toString(eventType));
											}
											break;
										}
										case XmlPullParser.END_TAG:
										{
											Log.d("listener3",Integer.toString(userType));
											
											if(userType == 1|| userType == 2)
											{
												Message message = new Message();
												message.what = LOGIN_TRUE;
												message.obj = application;
												handler.sendMessage(message);
											}
											else if(userType == 0)
											{
												Message message = new Message();
												message.what = LOGIN_FALSE;
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
								message.what = LOGIN_FAIL;
								handler.sendMessage(message);
							}
				   });
			}
			break;
		case R.id.register:
			startActivity(new android.content.Intent(this,RegisterActivity.class));
			break;
		default:
			break;
		}
		
	}

}
