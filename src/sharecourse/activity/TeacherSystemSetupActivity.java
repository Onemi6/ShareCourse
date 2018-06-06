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
			dialog.setTitle("���������");
			dialog.setMessage("        ʹ�ù������������⣬�������ϵ��ϵͳ˧˧�Ĺ�����Ա\n" +
					"�����˺ţ�yy321@qq.com\n      ��Ȼ������������õĻ��������ٶ�һ�£���Ȼ����һ���ܽ�����⣡");
			dialog.setCancelable(false);
			dialog.setPositiveButton("ȷ��", new DialogInterface.OnClickListener() {
				
				@Override
				public void onClick(DialogInterface dialog, int which) {
					
				}
			});
			dialog.setNegativeButton("�ٶ�һ��", new DialogInterface.OnClickListener() {
				
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
			dialog.setTitle("����ϵͳ��");
			dialog.setMessage("�汾�ţ�SC V1.0\n" +
					"���ܽ��ܣ����÷���ϵͳ��ΪѧУ��ʦ���ҳ���������һ���ѧ�����������Androidϵͳ��ƵĶ�������Ҫ������Ϊ�ҳ���Ħ�����ۿ��ý�ѧ�����ṩ����");
			dialog.setCancelable(false);
			dialog.setPositiveButton("֪����", new DialogInterface.OnClickListener() {
				
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
