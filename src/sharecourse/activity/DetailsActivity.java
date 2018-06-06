package sharecourse.activity;

import java.util.ArrayList;
import java.util.List;

import com.example.sharecourse.R;
import com.example.sharecourse.R.drawable;
import com.example.sharecourse.R.id;
import com.example.sharecourse.R.layout;

import sharecourse.adapter.CommentAdapter;
import sharecourse.adapter.GVPictureAdapter;
import sharecourse.myclass.Comment;
import sharecourse.myclass.DynamicInfo;
import sharecourse.myclass.MyApplication;
import sharecourse.myclass.MyGridView;
import sharecourse.others.SysUtils;

import net.tsz.afinal.FinalBitmap;
import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup.LayoutParams;
import android.view.Window;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

public class DetailsActivity extends Activity implements OnClickListener{
	
	private ImageView details_back_btn;
	private ImageView details_icon;
	private TextView details_name;
	private TextView details_time;
	private TextView details_text;
	private RelativeLayout details_image_layout;
	private MyGridView details_images;
	private TextView details_collection;
	private TextView details_comment;
	private TextView details_zan;
	private ListView details_comment_listview;
	private List<Comment> commentlist = new ArrayList<Comment>();
	private GVPictureAdapter pictureAdapter;
	private int wh;
	private FinalBitmap finalImageLoader;
	private String dynamicnum;
	private DynamicInfo dynamic = new DynamicInfo();
	private boolean iscollection ,iszan;
	private int nowcollection,nowzan;

	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.details);
		this.wh = (SysUtils.getScreenWidth(this) - SysUtils.Dp2Px(this,
				99)) / 3;
		this.finalImageLoader = FinalBitmap.create(this);
		this.finalImageLoader.configLoadingImage(R.drawable.ic_launcher);
		
		details_back_btn = (ImageView)findViewById(R.id.details_back_btn);
		details_back_btn.setOnClickListener(this);
		details_icon = (ImageView)findViewById(R.id.details_item_icon);
		
		details_name = (TextView)findViewById(R.id.details_item_name);
		details_time = (TextView)findViewById(R.id.details_item_time);
		details_text = (TextView)findViewById(R.id.details_item_text);
		
		details_image_layout = (RelativeLayout)findViewById(R.id.details_images);// 图片布局
		details_images = (MyGridView)findViewById(R.id.details_item_picture_gridview);// 图片
		
		details_collection = (TextView)findViewById(R.id.details_item_collection);
		details_collection.setOnClickListener(this);
		details_comment = (TextView)findViewById(R.id.details_item_comment);
		details_comment.setOnClickListener(this);
		details_zan = (TextView)findViewById(R.id.details_item_zan);
		details_zan.setOnClickListener(this);
		
		Intent getintent = getIntent();
		Bitmap bitmap = BitmapFactory.decodeResource(getResources(), R.drawable.ic_launcher);
		finalImageLoader.display(details_icon, "http://ww2.sinaimg.cn/crop.0.0.1080.1080.1024/d773ebfajw8eum57eobkwj20u00u075w.jpg", bitmap, bitmap);
		details_name.setText("哇哈哈");
		details_time.setText("11:11");
		details_text.setText("按实际了发生计算机按附件是上看到了深刻的是的女你是");
		details_collection.setText("11");
		details_comment.setText("33");
		details_zan.setText("22");
		String images="http://hdn.xnimg.cn/photos/hdn321/20130612/2235/h_main_NNN4_e80a000007df111a.jpg#http://depot.nipic.com/file/20150605/13378630_23102978350.jpg#http://avatar.csdn.net/C/6/8/1_bz419927089.jpg#http://img5q.duitang.com/uploads/item/201411/03/20141103130157_WNakZ.jpeg";
	  if (images!= null && !images.equals("")) {
			details_image_layout.setVisibility(View.VISIBLE);
			initInfoImages(details_images, images);
		} else {
			details_image_layout.setVisibility(View.GONE);
		}
		details_comment_listview= (ListView)findViewById(R.id.details_comment_text_listview);
		initcomment();
		if(dynamic.getNum()==null||dynamic.getNum().equals(""))
		{
			CommentAdapter commentadapter = new CommentAdapter(DetailsActivity.this,R.layout.comment_item,commentlist);
			details_comment_listview.setAdapter(commentadapter);
		}
		else
		{
			Comment onecomment = new Comment();
			onecomment.setBydynamicnum(getintent.getStringExtra("dynamicnum").toString());
			((MyApplication)getApplication()).setDynamicNum(onecomment.getBydynamicnum());
			onecomment.setRespondent(getintent.getStringExtra("comment_username").toString());
			onecomment.setReplytime(getintent.getStringExtra("comment_time").toString());
			onecomment.setReplytext(getintent.getStringExtra("comment_text").toString());
			commentlist.add(0,onecomment);
			CommentAdapter commentadapter = new CommentAdapter(DetailsActivity.this,R.layout.comment_item,commentlist);
			details_comment_listview.setAdapter(commentadapter);
		}
	}

	private void initcomment() {
		// TODO 自动生成的方法存根
		Comment onecomment = new Comment();
		onecomment.setBydynamicnum("111");//实际由动态界面传过来
		onecomment.setRespondent("张三");
		onecomment.setReplytime("5-6 11:11");
		onecomment.setReplytext("说的真好！");
		commentlist.add(onecomment);
		
		Comment onecomment1 = new Comment();
		onecomment1.setBydynamicnum("111");//实际由动态界面传过来
		onecomment1.setRespondent("李四");
		onecomment1.setReplytime("5-4 12:12");
		onecomment1.setReplytext("点赞");
		commentlist.add(onecomment1);
	}

	@Override
	public void onClick(View v) {
		// TODO 自动生成的方法存根
		switch(v.getId())
		{
		case R.id.details_back_btn:
			Intent to_back = new Intent(DetailsActivity.this,TabHostActivity.class);
			startActivity(to_back);
			finish();
			break;
		case R.id.details_item_collection:
			if (iscollection == false) {
				nowcollection++;
				iscollection = true;
				details_collection.setText(nowcollection + "");
				Toast.makeText(this, "点击了收藏", Toast.LENGTH_SHORT).show();
			} else {
				nowcollection--;
				iscollection = false;
				details_collection.setText(nowcollection + "");
				Toast.makeText(this, "取消了收藏", Toast.LENGTH_SHORT).show();
			}
			break;
		case R.id.details_item_comment:
			Intent to_comment = new Intent(DetailsActivity.this,CommentActivity.class);
			
			/*to_comment.putExtra("icon", dynamic.getIcon());
			to_comment.putExtra("name", dynamic.getName());
			to_comment.putExtra("num", dynamic.getNum());
			to_comment.putExtra("dynamic_time", dynamic.getTime());
			to_comment.putExtra("text", dynamic.getText());
			to_comment.putExtra("images", dynamic.getImages());
			to_comment.putExtra("collection", dynamic.getCollection());
			to_comment.putExtra("comment", dynamic.getComment());
			to_comment.putExtra("zan", dynamic.getZan());*/
			startActivity(to_comment);
			finish();
			break;
		case R.id.details_item_zan:
			if (iszan == false) {
				nowzan++;
				iszan = true;
				details_zan.setText(nowzan + "");
				
				Drawable icon_like=getResources().getDrawable(R.drawable.icon_like_detail);  
				icon_like.setBounds(0, 0, icon_like.getMinimumWidth(), icon_like.getMinimumHeight());  
				details_zan.setCompoundDrawables(icon_like, null, null, null);
				
				Toast.makeText(this, "点击了赞", Toast.LENGTH_SHORT).show();
			} else {
				nowzan--;
				iszan = false;
				details_zan.setText(nowzan + "");
				Drawable icon_unlike=getResources().getDrawable(R.drawable.icon_unlike_detail);  
				icon_unlike.setBounds(0, 0, icon_unlike.getMinimumWidth(), icon_unlike.getMinimumHeight());  
				details_zan.setCompoundDrawables(icon_unlike, null, null, null);
				Toast.makeText(this, "取消了赞", Toast.LENGTH_SHORT).show();
			}
			break;
		}
	}
	
	/**
	 * 加载信息中包含的图片内容
	 * 
	 * @param imgspath
	 */
	public void initInfoImages(MyGridView gv_images, final String imgspath) {
		if (imgspath != null && !imgspath.equals("")) {
			String[] imgs = imgspath.split("#");
			ArrayList<String> list = new ArrayList<String>();
			for (int i = 0; i < imgs.length; i++) {
				list.add(imgs[i]);
			}
			int w = 0;
			switch (imgs.length) {
			case 1:
				w = wh;
				gv_images.setNumColumns(1);
				break;
			case 2:
			case 4:
				w = 2 * wh + SysUtils.Dp2Px(this, 2);
				gv_images.setNumColumns(2);
				break;
			case 3:
			case 5:
			case 6:
				w = wh * 3 + SysUtils.Dp2Px(this, 2) * 2;
				gv_images.setNumColumns(3);
				break;
			}
			RelativeLayout.LayoutParams lp = new RelativeLayout.LayoutParams(w,
					LayoutParams.WRAP_CONTENT);
			gv_images.setLayoutParams(lp);
			pictureAdapter = new GVPictureAdapter(this, list);
			gv_images.setAdapter(pictureAdapter);
			gv_images.setOnItemClickListener(new OnItemClickListener() {
				@Override
				public void onItemClick(AdapterView<?> arg0, View arg1,
						int arg2, long arg3) {
				}
			});
		}
	}
}
