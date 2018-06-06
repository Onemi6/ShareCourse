package sharecourse.fragment;

import sharecourse.activity.LoginActivity;
import sharecourse.activity.PDIActivity;
import sharecourse.activity.ParentSystemSetupActivity;
import sharecourse.myclass.MyApplication;
import sharecourse.others.ConnectDB;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.TextView;



public class PPCFragment extends Fragment implements OnClickListener {
	
	AlertDialog.Builder dialog;
	 String userId = "";
	 ConnectDB cDb;
    @Override
    public View onCreateView(LayoutInflater inflater,ViewGroup container,Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.parent_personal_center, container,false);
        
        
        String name = ((MyApplication)getActivity().getApplication()).getUserName();
        
        TextView textView = (TextView)view.findViewById(R.id.text_name);
        
        
        if(name.equals(null) || name.equals(""))
            textView.setText(((MyApplication)getActivity().getApplication()).getPhoneNum());
        else
        	 textView.setText(name);
        view.findViewById(R.id.head_portrait).setOnClickListener(this);
        view.findViewById(R.id.Seting).setOnClickListener(this);
        view.findViewById(R.id.comment).setOnClickListener(this);
        view.findViewById(R.id.newMessage).setOnClickListener(this);
        view.findViewById(R.id.myCollection).setOnClickListener(this);
        view.findViewById(R.id.btn_exit).setOnClickListener(this);
        return view;
    }
    @Override
    public void onClick(View v) {
    	switch (v.getId()) {
		case R.id.head_portrait:
			startActivity(new android.content.Intent(getActivity(),PDIActivity.class));
			break;
		case R.id.Seting:
			startActivity(new android.content.Intent(getActivity(),ParentSystemSetupActivity.class));
			break;
			
		case R.id.comment:
			dialog = new AlertDialog.Builder(getActivity());
			dialog.setTitle("提示");
			dialog.setMessage("功能正在完善中，客官请耐心等待。。。");
			dialog.setCancelable(false);
			dialog.setPositiveButton("好的", new DialogInterface.OnClickListener() {
				@Override
				public void onClick(DialogInterface dialog, int which) {
				}
			});
			dialog.show();
			
			break;
		case R.id.newMessage:
			dialog = new AlertDialog.Builder(getActivity());
			dialog.setTitle("提示");
			dialog.setMessage("功能正在完善中，客官请耐心等待。。。");
			dialog.setCancelable(false);
			dialog.setPositiveButton("好的", new DialogInterface.OnClickListener() {
				@Override
				public void onClick(DialogInterface dialog, int which) {
				}
			});
			dialog.show();
			break;
		case R.id.myCollection:
			dialog = new AlertDialog.Builder(getActivity());
			dialog.setTitle("提示");
			dialog.setMessage("功能正在完善中，客官请耐心等待。。。");
			dialog.setCancelable(false);
			dialog.setPositiveButton("好的", new DialogInterface.OnClickListener() {
				@Override
				public void onClick(DialogInterface dialog, int which) {
				}
			});
			dialog.show();
			break;
		case R.id.btn_exit:
			startActivity(new android.content.Intent(getActivity(),LoginActivity.class));
			break;
		default:
			break;
		}
    }
}
