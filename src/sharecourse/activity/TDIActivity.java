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
import sharecourse.myclass.Teacher;
import sharecourse.others.ConnectDB;
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
import android.widget.TextView;
import android.widget.Toast;

public class TDIActivity  extends Activity implements OnClickListener{
	private ConnectDB cDb = new ConnectDB();
	private String userId,Account,userName,userSex,subject,oldAccountNum;
	private TextView userId1,Account1,userName1,userSex1,subject1,userClass;
	private final int UPDATETEACHERACCOUNT_SUCCESS = 0;
	private final int UPDATETEACHERACCOUNT_FAIL = 1;
	private final int UPDATETEACHERACCOUNT_ERROR = 2;
	private final int UPDATETEACHERCLASS_SUCCESS = 3;
	private final int UPDATETEACHERCLASS_FAIL = 4;
	private final int UPDATETEACHERCLASS_ERROR = 5;
	private final int SHOW_SUCCESS = 6;
	private final int SHOW_ERROR = 7;
	private EditText et;
	
	private Handler handler = new Handler(){
		public void handleMessage(Message msg)
		{
		    AlertDialog.Builder dialog;
			switch (msg.what) 
			{
			
			case UPDATETEACHERACCOUNT_SUCCESS:
			{
				oldAccountNum = Account1.getText().toString();
				((MyApplication)getApplication()).setPhoneNum((msg.obj).toString());
				Account1.setText((msg.obj).toString());
				AccountNumber accountNumber = new AccountNumber();
				accountNumber.setAccountName(userName1.getText().toString());
				accountNumber.setAccountNum((msg.obj).toString());
				accountNumber.setAccountType("教师");
				cDb.updateAccount(accountNumber, oldAccountNum, "教师");
				break;
			}
			case UPDATETEACHERACCOUNT_FAIL:
			{
        		dialog = new AlertDialog.Builder(TDIActivity.this);
				dialog.setTitle("提示");
				dialog.setMessage("修改失败，此联系方式已经被人使用！");
				dialog.setCancelable(false);
				dialog.setPositiveButton("知道了", new DialogInterface.OnClickListener() {
					
					@Override
					public void onClick(DialogInterface dialog, int which) {
					}
				});
				dialog.show();
				
				break;
			    }
			case UPDATETEACHERACCOUNT_ERROR:
			{
        		dialog = new AlertDialog.Builder(TDIActivity.this);
				dialog.setTitle("提示");
				dialog.setMessage("惊现未知错误，更改失败！");
				dialog.setCancelable(false);
				dialog.setPositiveButton("知道了", new DialogInterface.OnClickListener() {
					
					@Override
					public void onClick(DialogInterface dialog, int which) {
					}
				});
				dialog.show();
				break;
			}
			case SHOW_ERROR:
			{
				userId1.setText("");
		      	Account1.setText(Account);
		      	userName1.setText("");
		      	userSex1.setText("");
		      	subject1.setText("");
		      	
				break;
			}
			case SHOW_SUCCESS:
			{
			    Teacher teacher = new Teacher();
			    teacher = (Teacher)(msg.obj);
			    userId1.setText(teacher.getTeacherNum());
			    Account1.setText(Account);
			    userName1.setText(teacher.getTeacherName());
			    userSex1.setText(teacher.getTeacherSex());
			    subject1.setText(teacher.getTeacherCourse());
				break;
			}
			case UPDATETEACHERCLASS_SUCCESS:
			{
				Toast.makeText(TDIActivity.this,"UPDATETEACHERACCOUNT_SUCCESS", Toast.LENGTH_LONG).show();
				userClass.setText((msg.obj).toString());
				break;
			}
			
			case UPDATETEACHERCLASS_FAIL:
			{
				Toast.makeText(TDIActivity.this,"UPDATEPARENTACCOUNT_FAIL", Toast.LENGTH_LONG).show();
        		dialog = new AlertDialog.Builder(TDIActivity.this);
				dialog.setTitle("提示");
				dialog.setMessage("修改失败，请填写班级全称！");
				dialog.setCancelable(false);
				dialog.setPositiveButton("知道了", new DialogInterface.OnClickListener() {
					
					@Override
					public void onClick(DialogInterface dialog, int which) {
					}
				});
				dialog.show();
				
				break;
			    }
			case UPDATETEACHERCLASS_ERROR:
			{
				Toast.makeText(TDIActivity.this,"UPDATEPARENTACCOUNT_FAIL", Toast.LENGTH_LONG).show();
        		dialog = new AlertDialog.Builder(TDIActivity.this);
				dialog.setTitle("提示");
				dialog.setMessage("惊现未知错误，更改失败！");
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
		}
	};

	@Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.teacher_detailed_information);
        
        Account = ((MyApplication)getApplication()).getPhoneNum();
        
        Log.d("Account", Account);
        HttpUtil.sendHttpRequest("http://115.159.214.57:8080/sharecourse/User/loginInfo.jsp?phoneNum="+Account+"&userType=2", 
   			   new HttpCallbackListener() {

					@Override
					public void onFinish(String response) {
						
						XmlPullParserFactory factory;
						try {
							factory = XmlPullParserFactory.newInstance();
							XmlPullParser xmlPullParser = factory.newPullParser();
							xmlPullParser.setInput(new StringReader(response));
							int eventType = xmlPullParser.getEventType();
							Teacher teacher = new Teacher();
							
							while (eventType != XmlPullParser.END_DOCUMENT) {
								String nodeName = xmlPullParser.getName();
								switch (eventType) {
								case XmlPullParser.START_TAG:
								{
									if("teacherNum".equals(nodeName))
									{
										try {
											teacher.setTeacherNum(xmlPullParser.nextText());
											
										} catch (IOException e) {
											e.printStackTrace();
										}
									}
									else if("teacherName".equals(nodeName))
									{
										try {
											teacher.setTeacherName(xmlPullParser.nextText());
											
										} catch (IOException e) {
											e.printStackTrace();
										}
									}
									else if("teacherSex".equals(nodeName))
									{
										try {
											teacher.setTeacherSex(xmlPullParser.nextText());
											
										} catch (IOException e) {
											e.printStackTrace();
										}
									}
									else if("teachCourse".equals(nodeName))
									{
										try {
											teacher.setTeacherCourse(xmlPullParser.nextText());
											
										} catch (IOException e) {
											e.printStackTrace();
										}
									}
									break;
								}
								case XmlPullParser.END_TAG:
								{
									   Message message = new Message();
									   message.what = SHOW_SUCCESS;
									   message.obj=teacher;
									   handler.sendMessage(message);
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
						message.what = SHOW_ERROR;
						handler.sendMessage(message);	
					}
     		 
     	 });
        userId1 = (TextView) findViewById(R.id.userId2);
      	Account1 = (TextView) findViewById(R.id.userAccount2);
      	
      	userName1 = (TextView) findViewById(R.id.userName2);
      	
      	userSex1 = (TextView) findViewById(R.id.userSex2);
      	
      	subject1 = (TextView) findViewById(R.id.subject2);
      	
        findViewById(R.id.img_head).setOnClickListener(this);
        findViewById(R.id.userAccount).setOnClickListener(this);
    }

	@Override
	public void onClick(View v) {
		Log.d("click", "click");
		et = new EditText(this);
		
		switch (v.getId()) {
		case R.id.img_head:
			startActivity(new android.content.Intent(this, TabHostActivity.class));
			break;
		case R.id.userAccount:
		    new AlertDialog.Builder(this).setTitle("请输入联系方式：")  
			            .setIcon(android.R.drawable.ic_dialog_info).setView(et)  
			            .setPositiveButton("确定", new DialogInterface.OnClickListener() {  
			                    @Override  
			                    public void onClick(DialogInterface arg0, int arg1) {
			                    	if(et.getText().toString().length()!=11)
			                    	{
			                    		AlertDialog.Builder dialog = new AlertDialog.Builder(TDIActivity.this);
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
			                    	else
			                    	{
			                    	 HttpUtil.sendHttpRequest("http://115.159.214.57:8080/sharecourse/User/updatePhone.jsp?newPhone="+et.getText().toString()+"&userNum="+userId1.getText().toString(), 
			          	    			   new HttpCallbackListener() {

											@Override
											public void onFinish(String response) {
												
												XmlPullParserFactory factory;
												try {
													factory = XmlPullParserFactory.newInstance();
													XmlPullParser xmlPullParser = factory.newPullParser();
													xmlPullParser.setInput(new StringReader(response));
													int eventType = xmlPullParser.getEventType();
													String result="";
													while (eventType != XmlPullParser.END_DOCUMENT) {
														String nodeName = xmlPullParser.getName();
														switch (eventType) {
														case XmlPullParser.START_TAG:
														{
															if("answer".equals(nodeName))
															{
																try {
																	result = xmlPullParser.nextText();
																	
																} catch (IOException e) {
																	e.printStackTrace();
																}
															}
															break;
														}
														case XmlPullParser.END_TAG:
														{
															if(result.equals("success"))
															{
															   Message message = new Message();
															   message.what = UPDATETEACHERACCOUNT_SUCCESS;
															   message.obj=et.getText().toString();
															   handler.sendMessage(message);
															}
															else {
																Message message = new Message();
																message.what = UPDATETEACHERACCOUNT_FAIL;
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
												message.what = UPDATETEACHERACCOUNT_ERROR;
												handler.sendMessage(message);	
											}
			                    	 });  	  
			                    	}
			             }  
			       }).setNegativeButton("取消", null).show();  

			break;
		}
	}
}
