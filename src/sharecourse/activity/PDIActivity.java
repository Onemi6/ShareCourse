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
import sharecourse.myclass.Parent;
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

import com.example.sharecourse.R;

public class PDIActivity extends Activity implements
		OnClickListener {

	private TextView userId,Account,userName,userSex,stuNum;
	private ConnectDB cDb = new ConnectDB();
	EditText et;
	String phoneNum1,oldPhoneNum;
	String userType1,newName;;
	private final int UPDATEPARENTACCOUNT_SUCCESS = 1;
	private final int UPDATEPARENTACCOUNT_FAIL = 2;
	private final int UPDATEPARENTACCOUNT_ERROR = 3;
	private final int UPDATEPARENTNAME_SUCCESS= 4;
	private final int UPDATEPARENTNAME_FAIL= 5;
	private final int UPDATEPARENTNAME_ERROR= 6;
	private final int UPDATEPARENTSEX_SUCCESS = 7;
	private final int UPDATEPARENTSEX_FAIL = 8;
	private final int UPDATEPARENTSEX_ERROR = 9;
	private final int SHOW_ERROR = 10;
	private final int SHOW_SUCCESS = 11;
	private final int UPDATESTUNUM_SUCCESS = 12;
	private final int UPDATESTUNUM_FAIL = 13;
	private final int UPDATESTUNUM_ERROR = 14;
	
	
	private Handler handler = new Handler(){
		public void handleMessage(Message msg)
		{
			AccountNumber account = new AccountNumber();
		    AlertDialog.Builder dialog;
		    
			switch (msg.what) 
			{
			
			case UPDATEPARENTACCOUNT_SUCCESS:
			{
		      	oldPhoneNum = Account.getText().toString();
				((MyApplication)getApplication()).setPhoneNum((msg.obj).toString());
				Account.setText((msg.obj).toString());
				account.setAccountNum((msg.obj).toString());
				account.setAccountName(userName.getText().toString());
				account.setAccountType(userType1);
				cDb.updateAccount(account,oldPhoneNum,userType1);
				
				break;
			}
			
			case UPDATEPARENTACCOUNT_FAIL:
			{
        		dialog = new AlertDialog.Builder(PDIActivity.this);
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
			case UPDATEPARENTACCOUNT_ERROR:
			{
        		dialog = new AlertDialog.Builder(PDIActivity.this);
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
			
			case UPDATEPARENTNAME_SUCCESS:
			{
				((MyApplication)getApplication()).setUserName((msg.obj).toString());
				userName.setText((msg.obj).toString());
				account.setAccountName((msg.obj).toString());
				account.setAccountNum(Account.getText().toString());
				account.setAccountType(userType1);
				cDb.updateAccount(account,Account.getText().toString(),userType1);
				break;
			}
			
			case UPDATEPARENTNAME_FAIL:
			{
				dialog = new AlertDialog.Builder(PDIActivity.this);
				dialog.setTitle("提示");
				dialog.setMessage("修改失败！");
				dialog.setCancelable(false);
				dialog.setPositiveButton("知道了", new DialogInterface.OnClickListener() {
					
					@Override
					public void onClick(DialogInterface dialog, int which) {
					}
				});
				dialog.show();
				break;
			}
			case UPDATEPARENTNAME_ERROR:
			{
        		dialog = new AlertDialog.Builder(PDIActivity.this);
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
			
			case UPDATEPARENTSEX_SUCCESS:
			{
				userSex.setText(et.getText().toString());
				break;
			}
			
			case UPDATEPARENTSEX_FAIL:
			{
				dialog = new AlertDialog.Builder(PDIActivity.this);
				dialog.setTitle("提示");
				dialog.setMessage("修改失败！");
				dialog.setCancelable(false);
				dialog.setPositiveButton("知道了", new DialogInterface.OnClickListener() {
					
					@Override
					public void onClick(DialogInterface dialog, int which) {
					}
				});
				dialog.show();
				break;
			}
			case UPDATEPARENTSEX_ERROR:
			{
        		dialog = new AlertDialog.Builder(PDIActivity.this);
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
				userId.setText("");
		      	Account.setText(phoneNum1);
		      	userName.setText("");
		      	userSex.setText("");
		      	stuNum.setText("");
		      	
				break;
			}
			case SHOW_SUCCESS:
			{
		      	Parent parent = new Parent();
		      	parent = (Parent) msg.obj;
		     	
				userId.setText(parent.getParentNum());
		      	Account.setText(phoneNum1);
		      	userName.setText(parent.getParentName());
		      	userSex.setText(parent.getParentSex());
		      	stuNum.setText(parent.getStuNum());
		      	
				break;
			}
			case UPDATESTUNUM_SUCCESS:
			{
				stuNum.setText(et.getText().toString());
				break;
			}
			
			case UPDATESTUNUM_FAIL:
			{
				dialog = new AlertDialog.Builder(PDIActivity.this);
				dialog.setTitle("提示");
				dialog.setMessage("修改失败,请确认学号是否正确！");
				dialog.setCancelable(false);
				dialog.setPositiveButton("知道了", new DialogInterface.OnClickListener() {
					
					@Override
					public void onClick(DialogInterface dialog, int which) {
					}
				});
				dialog.show();
				break;
			}
			case UPDATESTUNUM_ERROR:
			{
        		dialog = new AlertDialog.Builder(PDIActivity.this);
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
        setContentView(R.layout.parent_detailed_information);
        
        phoneNum1=((MyApplication)getApplication()).getPhoneNum();
		userType1 = ((MyApplication)getApplication()).getUserType();
		
		 HttpUtil.sendHttpRequest("http://115.159.214.57:8080/sharecourse/User/loginInfo.jsp?phoneNum="+phoneNum1+"&userType=1", 
  			   new HttpCallbackListener() {

				@Override
				public void onFinish(String response) {
					XmlPullParserFactory factory;
					try {
						factory = XmlPullParserFactory.newInstance();
						XmlPullParser xmlPullParser = factory.newPullParser();
						xmlPullParser.setInput(new StringReader(response));
						int eventType = xmlPullParser.getEventType();
						Parent parent = new Parent();
						
						while (eventType != XmlPullParser.END_DOCUMENT) {
							String nodeName = xmlPullParser.getName();
							switch (eventType) {
							case XmlPullParser.START_TAG:
							{
								if("parentNum".equals(nodeName))
								{
									try {
										parent.setParentNum(xmlPullParser.nextText());
									} catch (IOException e) {
										e.printStackTrace();
									}
								}
								if("parentName".equals(nodeName))
								{
									try {
										parent.setParentName(xmlPullParser.nextText());
									} catch (IOException e) {
										e.printStackTrace();
									}
									
								}
								if("parentSex".equals(nodeName))
								{
									try {
										parent.setParentSex(xmlPullParser.nextText());
									} catch (IOException e) {
										e.printStackTrace();
									}
									
								}
								if("stuNum".equals(nodeName))
								{
									try {
										parent.setStuNum(xmlPullParser.nextText());
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
								message.obj = parent;
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
		
     
        userId = (TextView) findViewById(R.id.userId2);
       
      	Account = (TextView) findViewById(R.id.userAccount2);
      	

      	userName = (TextView) findViewById(R.id.userName2);
      	
      	userSex = (TextView) findViewById(R.id.userSex2);
      	
      	stuNum = (TextView) findViewById(R.id.stuNum1);
      	
        findViewById(R.id.img_head).setOnClickListener(this);
        findViewById(R.id.userAccount).setOnClickListener(this);
        findViewById(R.id.userName).setOnClickListener(this);
        findViewById(R.id.userSex).setOnClickListener(this);
        findViewById(R.id.stuNum).setOnClickListener(this);
    }

	@Override
	public void onClick(View v) {
		et = new EditText(this);
		
		switch (v.getId()) {
		case R.id.img_head:
			startActivity(new android.content.Intent(this, TabHostActivity.class));
			finish();
			break;
		case R.id.userAccount:
		    new AlertDialog.Builder(this).setTitle("请输入联系方式：")  
			            .setIcon(android.R.drawable.ic_dialog_info).setView(et)  
			            .setPositiveButton("确定", new DialogInterface.OnClickListener() {  
			                    @Override  
			                    public void onClick(DialogInterface arg0, int arg1) {
			                    	if(et.getText().toString().length()!=11)
			                    	{
			                    		AlertDialog.Builder dialog = new AlertDialog.Builder(PDIActivity.this);
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
			                    	
			                    	 HttpUtil.sendHttpRequest("http://115.159.214.57:8080/sharecourse/User/updatePhone.jsp?newPhone="+et.getText().toString()+"&userNum="+userId.getText().toString(), 
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
																Log.d("listener2",Integer.toString(eventType));
															}
															break;
														}
														case XmlPullParser.END_TAG:
														{
															if(result.equals("success"))
															{
															   Message message = new Message();
															   message.what = UPDATEPARENTACCOUNT_SUCCESS;
															   message.obj=et.getText().toString();
															   handler.sendMessage(message);
															}
															else {
																Message message = new Message();
																message.what = UPDATEPARENTACCOUNT_FAIL;
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
												message.what = UPDATEPARENTACCOUNT_ERROR;
												handler.sendMessage(message);	
											}
			                    		 
			                    	 });
			                    	}
			                    	 
			             }  
			       }).setNegativeButton("取消", null).show();  

			break;
		case R.id.userName:
			
		    new AlertDialog.Builder(this).setTitle("请输入用户名：")  
			            .setIcon(android.R.drawable.ic_dialog_info).setView(et)  
			            .setPositiveButton("确定", new DialogInterface.OnClickListener() {  
			                    @Override  
			                    public void onClick(DialogInterface arg0, int arg1) {
			                    	 HttpUtil.sendHttpRequest("http://115.159.214.57:8080/sharecourse/Parent/updateParent.jsp?parentNum="+userId.getText()+"&newParentSex="+java.net.URLEncoder.encode(userSex.getText().toString())+"&newParentName="+java.net.URLEncoder.encode(et.getText().toString()), 
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
																	Log.d("listener2",Integer.toString(eventType));
																}
																break;
															}
															case XmlPullParser.END_TAG:
															{
																if(result.equals("success"))
																{
																   Message message = new Message();
																   message.what = UPDATEPARENTNAME_SUCCESS;
																   message.obj=et.getText().toString();
																   handler.sendMessage(message);
																}
																else {
																	Message message = new Message();
																	message.what =  UPDATEPARENTNAME_FAIL;
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
													message.what =  UPDATEPARENTNAME_ERROR;
													handler.sendMessage(message);	
												}
				                    		 
				                    	 }); 	
			                    	Toast.makeText(PDIActivity.this, et.getText().toString(), Toast.LENGTH_LONG).show();        		
			                    }  
			       }).setNegativeButton("取消", null).show();  
			break;
		case R.id.userSex:
			 new AlertDialog.Builder(this).setTitle("请输入性别：")  
	            .setIcon(android.R.drawable.ic_dialog_info).setView(et)  
	            .setPositiveButton("确定", new DialogInterface.OnClickListener() {  
	                    @Override  
	                    public void onClick(DialogInterface arg0, int arg1) {
	                    	 HttpUtil.sendHttpRequest("http://115.159.214.57:8080/sharecourse/Parent/updateParent.jsp?parentNum="+userId.getText()+"&newParentSex="+java.net.URLEncoder.encode(et.getText().toString())+"&newParentName="+java.net.URLEncoder.encode(userName.getText().toString()), 
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
															Log.d("listener2",Integer.toString(eventType));
														}
														break;
													}
													case XmlPullParser.END_TAG:
													{
														if(result.equals("success"))
														{
														   Message message = new Message();
														   message.what = UPDATEPARENTSEX_SUCCESS;
														   message.obj=et.getText().toString();
														   handler.sendMessage(message);
														}
														else {
															Message message = new Message();
															message.what =  UPDATEPARENTSEX_FAIL;
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
											message.what =  UPDATEPARENTSEX_ERROR;
											handler.sendMessage(message);	
										}
		                    		 
		                    	 }); 	  	
	             }  
	       }).setNegativeButton("取消", null).show();  
			break;
		case R.id.stuNum:
			 new AlertDialog.Builder(this).setTitle("请输入学生学号：")  
	            .setIcon(android.R.drawable.ic_dialog_info).setView(et)  
	            .setPositiveButton("确定", new DialogInterface.OnClickListener() {  
	                    @Override  
	                    public void onClick(DialogInterface arg0, int arg1) {
	                    	 HttpUtil.sendHttpRequest("http://115.159.214.57:8080/sharecourse/Parent/updateStuNum.jsp?parentNum="+userId.getText()+"&newStuNum="+et.getText().toString()+"&stuNum="+stuNum.getText().toString(), 
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
															Log.d("listener2",Integer.toString(eventType));
														}
														break;
													}
													case XmlPullParser.END_TAG:
													{
														if(result.equals("success"))
														{
														   Message message = new Message();
														   message.what = UPDATESTUNUM_SUCCESS;
														   message.obj=et.getText().toString();
														   handler.sendMessage(message);
														}
														else {
															Message message = new Message();
															message.what =  UPDATESTUNUM_FAIL;
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
											message.what = UPDATESTUNUM_ERROR;
											handler.sendMessage(message);	
										}
		                    		 
		                    	 }); 	  	
	             }  
	       }).setNegativeButton("取消", null).show();  
		}
	}

}
