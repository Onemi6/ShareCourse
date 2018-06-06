package sharecourse.adapter;

import java.util.List;

import sharecourse.myclass.Comment;

import com.example.sharecourse.R;
import com.example.sharecourse.R.id;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

public class CommentAdapter extends ArrayAdapter<Comment>{

	private int resource;
	public CommentAdapter(Context context, int resource, List<Comment> objects) {
		super(context, resource, objects);
		// TODO 自动生成的构造函数存根
		this.resource=resource;
	}

	@Override
	public View getView(int position,View convertView ,ViewGroup parent){
		Comment onecomment = getItem(position);
		View view = LayoutInflater.from(getContext()).inflate(resource, null);
		TextView comment_respondent = (TextView)view.findViewById(R.id.comment_respondent); 
		TextView comment_time = (TextView)view.findViewById(R.id.comment_time); 
		TextView comment_text = (TextView)view.findViewById(R.id.comment_text); 
		
		comment_respondent.setText(onecomment.getRespondent());
		comment_time.setText(onecomment.getReplytime());
		comment_text.setText(onecomment.getReplytext());
		
		return view;
	}
}
