package sharecourse.activity;

import java.io.IOException;
import java.io.StringReader;
import java.util.Calendar;

import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlPullParserException;
import org.xmlpull.v1.XmlPullParserFactory;

import sharecourse.http.HttpCallbackListener;
import sharecourse.http.HttpUtil;
import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.Window;
import android.widget.EditText;
import android.widget.Spinner;


public class RegisterActivity extends Activity implements OnClickListener{

	String result;
	public static final int TEACHER_ISEXITS = 0;
	public static final int TEACHER_NOTEXITS = 1;
	public static final int REGISTER_SUCCESE = 2;
	public static final int REGISTER_LOSE = 3;
	public static final int LOGIN_ISEXITS = 4;
	public static final int LOGIN_NOTEXITS = 5;
	EditText userId1;
	String userId;
	EditText contactWay1;
	String contactWay;
	EditText pswd1;
	String pswd;
	EditText pswd2;
	String rpswd;
	String type;
	Spinner spinner;
	int teacherIsexits = 0;
	String teacherFlag;
	public static String loginNotexits = "";
	private Handler handler = new Handler(){
		public void handleMessage(Message msg)
		{
		    AlertDialog.Builder dialog;
			switch (msg.what) {
			case LOGIN_ISEXITS:
				    dialog = new AlertDialog.Builder(RegisterActivity.this);
					dialog.setTitle("提示");
					dialog.setMessage("该用户已注册，是否立刻登录？");
					dialog.setCancelable(false);
					dialog.setPositiveButton("是", new DialogInterface.OnClickListener() {
						
						@Override
						public void onClick(DialogInterface dialog, int which) {
							startActivity(new android.content.Intent(RegisterActivity.this,LoginActivity.class));
						}
					});
					dialog.setNegativeButton("否", new DialogInterface.OnClickListener() {
						
						@Override
						public void onClick(DialogInterface dialog, int which) {
						}
					});
					dialog.show();
				break;
				
			case TEACHER_NOTEXITS:
				  dialog = new AlertDialog.Builder(RegisterActivity.this);
					dialog.setTitle("提示");
					dialog.setMessage("注册失败,该教师不存在！");
					dialog.setCancelable(false);
					dialog.setPositiveButton("知道了", new DialogInterface.OnClickListener() {
						@Override
						public void onClick(DialogInterface dialog, int which) {
							}
						});
					dialog.show();
				break;
			case REGISTER_SUCCESE:
					dialog = new AlertDialog.Builder(RegisterActivity.this);
					dialog.setTitle("提示");
					dialog.setMessage("恭喜您，注册成功！现在登录？");
					dialog.setCancelable(false);
					dialog.setPositiveButton("那是必须的！", new DialogInterface.OnClickListener() {
						
						@Override
						public void onClick(DialogInterface dialog, int which) {
							startActivity(new android.content.Intent(RegisterActivity.this,LoginActivity.class));
						}
					});
					dialog.setNegativeButton("暂时不用！", new DialogInterface.OnClickListener() {
						
						@Override
						public void onClick(DialogInterface dialog, int which) {
						}
					});
					dialog.show();
				break;

			//ע��ʧ��
			case REGISTER_LOSE:
			    dialog = new AlertDialog.Builder(RegisterActivity.this);
				dialog.setTitle("提示");
				dialog.setMessage("注册失败");
				dialog.setCancelable(false);
				dialog.setPositiveButton("知道了", new DialogInterface.OnClickListener() {
					@Override
					public void onClick(DialogInterface dialog, int which) {
						}
					});
				dialog.show();
				break;
			}
		}
	};

	
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
	        super.onCreate(savedInstanceState);
	        requestWindowFeature(Window.FEATURE_NO_TITLE);
	        setContentView(R.layout.register);
			   userId1 = (EditText) findViewById(R.id.num1);
			   contactWay1 = (EditText) findViewById(R.id.contactWay);
			   pswd1 = (EditText) findViewById(R.id.pswd1);
			   pswd2 = (EditText) findViewById(R.id.pswd2);
			   spinner = (Spinner)findViewById(R.id.spinner);
	        findViewById(R.id.return1).setOnClickListener(this);
	        findViewById(R.id.register1).setOnClickListener(this);
	    }

	@Override
	public void onClick(View v) {
		
		switch (v.getId()) {
		case R.id.return1:
			startActivity(new android.content.Intent(this,LoginActivity.class));
			break;
		case R.id.register1:
			    userId = userId1.getText().toString();
			    contactWay = contactWay1.getText().toString();
			    pswd = pswd1.getText().toString();
			    rpswd = pswd2.getText().toString();
			    
			    result = spinner.getSelectedItem().toString();
		        
		   if(userId.equals("")|| contactWay.equals("") || pswd.equals("") || rpswd.equals(""))
		   {
			   AlertDialog.Builder dialog = new AlertDialog.Builder(RegisterActivity.this);
				dialog.setTitle("提示");
				dialog.setMessage("请确保信息填写完整！");
				dialog.setCancelable(false);
				dialog.setPositiveButton("知道了", new DialogInterface.OnClickListener() {
					
					@Override
					public void onClick(DialogInterface dialog, int which) {
					}
				});
	
				dialog.show();
		   }
		   else if(contactWay.length()!=11)
		   {
			   AlertDialog.Builder dialog = new AlertDialog.Builder(RegisterActivity.this);
				dialog.setTitle("提示");
				dialog.setMessage("请确认联系方式的格式！");
				dialog.setCancelable(false);
				dialog.setPositiveButton("知道了", new DialogInterface.OnClickListener() {
					@Override
					public void onClick(DialogInterface dialog, int which) {
					}
				});
				dialog.show();
		   }
		   else if(!pswd.equals(rpswd))
		   { 
			   AlertDialog.Builder dialog = new AlertDialog.Builder(RegisterActivity.this);
				dialog.setTitle("提示");
				dialog.setMessage("请保持两个密码相同！");
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
			   if(result.equals("教师"))
			   {
				   type="2";
			   }
			   else
			   {
				   type="1";
				   Calendar c = Calendar.getInstance();  
				   String year = Integer.toString(c.get(Calendar.YEAR)); 
				   userId=year;
				   String month = Integer.toString(c.get(Calendar.MONTH));
				   userId += month;
				   String day = Integer.toString(c.get(Calendar.DAY_OF_MONTH));
				   userId += day;
				   String hour = Integer.toString(c.get(Calendar.HOUR_OF_DAY));
				   userId += hour;
				   String minute = Integer.toString(c.get(Calendar.MINUTE)); 
				   userId += minute;
			   }
			   HttpUtil.sendHttpRequest("http://115.159.214.57:8080/sharecourse/User/insertUser.jsp?phoneNum="+contactWay+"&password="+pswd+"&userType="+type+"&userNum="+userId, 
	    			   new HttpCallbackListener() {

						@Override
						public void onFinish(String response) {
							XmlPullParserFactory factory;
							try {
								factory = XmlPullParserFactory.newInstance();
								XmlPullParser xmlPullParser = factory.newPullParser();
								xmlPullParser.setInput(new StringReader(response));
								int eventType = xmlPullParser.getEventType();
								String answer="";
								while (eventType != XmlPullParser.END_DOCUMENT) {
									String nodeName = xmlPullParser.getName();
									
									switch (eventType) {
									case XmlPullParser.START_TAG:
									{
										if("answer".equals(nodeName))
											try {
												answer = xmlPullParser.nextText();
											} catch (IOException e) {
												e.printStackTrace();
											}
										break;
									}
									case XmlPullParser.END_TAG:
									{
										Log.d("answer",answer);
										if(answer.equals("exist"))
										{
											Message message = new Message();
											message.what = LOGIN_ISEXITS;
											handler.sendMessage(message);
										}
										else if(answer.equals("notTeacher"))
										{

											Message message = new Message();
											message.what = TEACHER_NOTEXITS;
											handler.sendMessage(message);
										}
										else if(answer.equals("success"))
										{

											Message message = new Message();
											message.what = REGISTER_SUCCESE;
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
							message.what = REGISTER_LOSE;
							handler.sendMessage(message);
						}
			   });
			  
	    	}
			break;
		default:
			break;
		}
		
	}

}
