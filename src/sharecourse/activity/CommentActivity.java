package sharecourse.activity;

import java.util.Calendar;

import sharecourse.myclass.Comment;
import sharecourse.myclass.DynamicInfo;
import sharecourse.myclass.MyApplication;

import com.example.sharecourse.R;
import com.example.sharecourse.R.id;
import com.example.sharecourse.R.layout;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.Window;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

public class CommentActivity extends Activity implements OnClickListener {

	private Button comment_cancle;
	private TextView comment_username;
	private TextView comment_text;
	private Button comment_send;
	private Boolean isempty = true;
	private Comment onecomment = new Comment();
	private DynamicInfo dynamic = new DynamicInfo();
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.activity_comment);

		comment_cancle = (Button) findViewById(R.id.comment_cancel_btn);
		comment_cancle.setOnClickListener(this);
		comment_username = (TextView) findViewById(R.id.comment_username);
		comment_username.setText(((MyApplication) getApplication())
				.getUserName());
		comment_text = (EditText) findViewById(R.id.comment_text);
		comment_send = (Button) findViewById(R.id.comment_send_btn);
		comment_send.setOnClickListener(this);

		if (comment_text.getText().toString().equals("")) {
			comment_send.setBackgroundColor(Color.WHITE);
			isempty = true;
		}
		comment_text.addTextChangedListener(textWatcher);
		comment_username.setText(((MyApplication) getApplication())
				.getUserName().toString());
	}

	private TextWatcher textWatcher = new TextWatcher() {
		@Override
		public void beforeTextChanged(CharSequence s, int start, int count,
				int after) {
			// TODO Auto-generated method stub
		}

		@Override
		public void onTextChanged(CharSequence s, int start, int before,
				int count) {
			comment_send.setBackgroundColor(Color.GRAY);
			isempty = false;
		}

		@Override
		public void afterTextChanged(Editable s) {
			// TODO 自动生成的方法存根
			if (comment_text.getText().toString().equals("")) {
				comment_send.setBackgroundColor(Color.WHITE);
				isempty = true;
			}
		}
	};

	@Override
	public void onClick(View v) {
		// TODO 自动生成的方法存根
		switch (v.getId()) {
		case R.id.comment_cancel_btn:
			Intent to_back = new Intent(CommentActivity.this,
					DetailsActivity.class);
			startActivity(to_back);
			finish();
			break;
		case R.id.comment_send_btn:
			Calendar time = Calendar.getInstance();
			int year = time.get(Calendar.YEAR);
			int month = time.get(Calendar.MONTH);
			int day = time.get(Calendar.DAY_OF_MONTH);
			int hour = time.get(Calendar.HOUR_OF_DAY);
			int minute = time.get(Calendar.MINUTE);
			int second = time.get(Calendar.SECOND);
			String nowtime = (month + 1) + "-" + day + " " + hour
					+ ":" + minute;
			if (isempty == false) {
				
				onecomment.setRespondent(comment_username.getText().toString());
				onecomment.setReplytime(nowtime);
				onecomment.setReplytext(comment_text.getText().toString());
				Intent to_detail = new Intent(CommentActivity.this,
						DetailsActivity.class);
				to_detail.putExtra("dynamicnum", onecomment.getBydynamicnum());
				to_detail.putExtra("comment_text", onecomment.getReplytext());
				to_detail.putExtra("comment_username", "Sky1ine");
				to_detail.putExtra("comment_time", onecomment.getReplytime());
				
				startActivity(to_detail);
				finish();
			}
			break;
		default:
			break;
		}
	}
}
