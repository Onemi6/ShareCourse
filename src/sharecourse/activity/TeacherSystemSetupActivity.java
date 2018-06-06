package sharecourse.activity;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.Window;

public class TeacherSystemSetupActivity extends Activity implements OnClickListener{
	 @Override
	    protected void onCreate(Bundle savedInstanceState) {
	        super.onCreate(savedInstanceState);
	        requestWindowFeature(Window.FEATURE_NO_TITLE);
	        setContentView(R.layout.system_setup);
	        findViewById(R.id.accountManagement).setOnClickListener(this);
	        findViewById(R.id.feedback).setOnClickListener(this);
	        findViewById(R.id.aboutSystem).setOnClickListener(this);
	        findViewById(R.id.setup_img_head).setOnClickListener(this);
	        
	    }

	@Override
	public void onClick(View v) {
		
		switch (v.getId()) {
		case R.id.accountManagement:
			startActivity(new android.content.Intent(this,AccountManageActivity.class));
			break;
		case R.id.feedback:
			AlertDialog.Builder dialog = new AlertDialog.Builder(TeacherSystemSetupActivity.this);
			dialog.setTitle("意见反馈：");
			dialog.setMessage("        使用过程中遇到问题，请务必联系本系统帅帅的管理人员\n" +
					"邮箱账号：yy321@qq.com\n      当然，如果流量够用的话，不妨百度一下，虽然并不一定能解决问题！");
			dialog.setCancelable(false);
			dialog.setPositiveButton("确定", new DialogInterface.OnClickListener() {
				
				@Override
				public void onClick(DialogInterface dialog, int which) {
					
				}
			});
			dialog.setNegativeButton("百度一下", new DialogInterface.OnClickListener() {
				
				@Override
				public void onClick(DialogInterface dialog, int which) {
					Intent intent = new Intent(Intent.ACTION_VIEW);
					intent.setData(Uri.parse("http://www.baidu.com"));
					startActivity(intent);
				}
			});
			dialog.show();
			break;
		case R.id.aboutSystem:
			dialog = new AlertDialog.Builder(TeacherSystemSetupActivity.this);
			dialog.setTitle("关于系统：");
			dialog.setMessage("版本号：SC V1.0\n" +
					"功能介绍：课堂分享系统是为学校老师、家长量身打造的一款教学互动软件。该Android系统设计的独特性主要表现在为家长观摩和评价课堂教学过程提供方便");
			dialog.setCancelable(false);
			dialog.setPositiveButton("知道了", new DialogInterface.OnClickListener() {
				
				@Override
				public void onClick(DialogInterface dialog, int which) {
					
				}
			});
			dialog.show();
		break;
		case R.id.setup_img_head:
			startActivity(new android.content.Intent(this,TabHostActivity.class));
			finish();
			break;
		default:
			break;
		}	
	}
}
