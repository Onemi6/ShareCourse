package sharecourse.adapter;

import java.util.ArrayList;
import java.util.List;

import com.example.sharecourse.R;
import com.example.sharecourse.R.drawable;
import com.example.sharecourse.R.id;
import com.example.sharecourse.R.layout;

import sharecourse.activity.CommentActivity;
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
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.view.ViewGroup.LayoutParams;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

public class DynamicAdapter extends BaseAdapter {

	private LayoutInflater mInflater;
	private Activity context;
	private List<DynamicInfo> list;
	private FinalBitmap finalImageLoader;
	private GVPictureAdapter pictureAdapter;
	private int wh;
	int  nowcollection, nowzan;
	private boolean iszan = false, iscollection = false;

	public DynamicAdapter(Activity context, List<DynamicInfo> list) {
		super();
		this.mInflater = LayoutInflater.from(context);
		this.context = context;
		this.wh = (SysUtils.getScreenWidth(context) - SysUtils.Dp2Px(context,
				99)) / 3;
		this.list = list;
		this.finalImageLoader = FinalBitmap.create(context);
		this.finalImageLoader.configLoadingImage(R.drawable.ic_launcher);
	}

	public List<DynamicInfo> getList() {
		return list;
	}

	public void setList(List<DynamicInfo> list) {
		this.list = list;
	}

	@Override
	public int getCount() {
		return list == null ? 0 : list.size();
	}

	@Override
	public Object getItem(int arg0) {
		return list == null ? null : list.get(arg0);
	}

	@Override
	public long getItemId(int arg0) {
		return list == null ? null : arg0;
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		if (list.size() == 0) {
			return null;
		}
		final ViewHolder holder;
		if (convertView == null) {
			convertView = mInflater.inflate(R.layout.dynamic_item, null);
			holder = new ViewHolder();
			holder.icon = (ImageView) convertView
					.findViewById(R.id.dynamic_item_icon);// 头像
			holder.name = (TextView) convertView
					.findViewById(R.id.dynamic_item_name);// 昵称
			holder.time = (TextView) convertView
					.findViewById(R.id.dynamic_item_time);// 时间
			holder.text = (TextView) convertView
					.findViewById(R.id.dynamic_item_text);// 发布内容
			holder.image_layout = (RelativeLayout) convertView
					.findViewById(R.id.dynamic_images);// 图片布局
			holder.gv_images = (MyGridView) convertView
					.findViewById(R.id.dynamic_item_picture_gridview);// 图片
			holder.collection = (TextView) convertView
					.findViewById(R.id.dynamic_item_collection);

			holder.comment = (TextView) convertView
					.findViewById(R.id.dynamic_item_comment);

			holder.zan = (TextView) convertView
					.findViewById(R.id.dynamic_item_zan);
			convertView.setTag(holder);
		} else {
			holder = (ViewHolder) convertView.getTag();
		}
		final DynamicInfo onedynamic = list.get(position);

		String num = null, name=null,time = null, text = null, iconpath = null, images = null, collection = null, comment = null, zan = null;
		if (onedynamic != null) {
			num = onedynamic.getNum();
			name= onedynamic.getName();
			time = onedynamic.getTime();
			text = onedynamic.getText();
			iconpath = onedynamic.getIcon();
			images = onedynamic.getImages();
			collection = onedynamic.getCollection();
			nowcollection = Integer.valueOf(collection).intValue();
			comment = onedynamic.getComment();
			zan = onedynamic.getZan();
			nowzan = Integer.valueOf(zan).intValue();
		}
		// 收藏
		if (collection != null && !collection.equals("")) {
			holder.collection.setText(collection);
		}
		holder.collection.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View arg0) {
				if (iscollection == false) {
					nowcollection++;
					iscollection = true;
					holder.collection.setText(nowcollection + "");
					Toast.makeText(context, "点击了收藏", Toast.LENGTH_SHORT).show();
				} else {
					nowcollection--;
					iscollection = false;
					holder.collection.setText(nowcollection + "");
					Toast.makeText(context, "取消了收藏", Toast.LENGTH_SHORT).show();
				}
			}
		});

		// 评论
		if (comment != null && !comment.equals("")) {
			holder.comment.setText(comment);
		}
		holder.comment.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View arg0) {
				Toast.makeText(context, "点击了评论", Toast.LENGTH_SHORT).show();
				
				Intent to_comment = new Intent();
				to_comment.putExtra("icon", onedynamic.getIcon());
				to_comment.putExtra("name", onedynamic.getName());
				to_comment.putExtra("num", onedynamic.getNum());
				to_comment.putExtra("dynamic_time", onedynamic.getTime());
				to_comment.putExtra("text", onedynamic.getText());
				to_comment.putExtra("images", onedynamic.getImages());
				to_comment.putExtra("collection", onedynamic.getCollection());
				to_comment.putExtra("comment", onedynamic.getComment());
				to_comment.putExtra("zan", onedynamic.getZan());
				((MyApplication)context.getApplication()).setDynamicNum(onedynamic.getNum());
				to_comment.setClass(context,CommentActivity.class);
				context.startActivity(to_comment);
			}
		});
		// 赞
		if (zan != null && !zan.equals("")) {
			holder.zan.setText(zan);
		}
		holder.zan.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View arg0) {
				if (iszan == false) {
					nowzan++;
					iszan = true;
					holder.zan.setText(nowzan + "");
					
					Drawable icon_like=context.getResources().getDrawable(R.drawable.icon_like_detail);  
					icon_like.setBounds(0, 0, icon_like.getMinimumWidth(), icon_like.getMinimumHeight());  
					holder.zan.setCompoundDrawables(icon_like, null, null, null);
					
					Toast.makeText(context, "点击了赞", Toast.LENGTH_SHORT).show();
				} else {
					nowzan--;
					iszan = false;
					holder.zan.setText(nowzan + "");
					Drawable icon_unlike=context.getResources().getDrawable(R.drawable.icon_unlike_detail);  
					icon_unlike.setBounds(0, 0, icon_unlike.getMinimumWidth(), icon_unlike.getMinimumHeight());  
					holder.zan.setCompoundDrawables(icon_unlike, null, null, null);
					Toast.makeText(context, "取消了赞", Toast.LENGTH_SHORT).show();
				}
			}
		});
		// 昵称
		if (name != null && !name.equals("")) {
			holder.name.setText(name);
		}
		// 是否含有图片
		if (images != null && !images.equals("")) {
			holder.image_layout.setVisibility(View.VISIBLE);
			initInfoImages(holder.gv_images, images);
		} else {
			holder.image_layout.setVisibility(View.GONE);
		}
		// 发布时间
		if (time != null && !time.equals("")) {
			holder.time.setText(time);
		}
		// 内容
		if (text != null && !text.equals("")) {
			holder.text.setText(text);
			// Linkify.addLinks(holder.text, Linkify.WEB_URLS);
		}
		// 头像
		if (iconpath != null && !iconpath.equals("")) {
			Bitmap bitmap = BitmapFactory.decodeResource(
					context.getResources(), R.drawable.ic_launcher);
			finalImageLoader.display(holder.icon, iconpath, bitmap, bitmap);
		} else {
			holder.icon.setImageResource(R.drawable.ic_launcher);
		}
		holder.icon.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View arg0) {
				Toast.makeText(context, "点击了头像", Toast.LENGTH_SHORT).show();
			}
		});
		return convertView;
	}

	static class ViewHolder {
		ImageView icon;
		TextView name;
		TextView time;
		TextView text;
		MyGridView gv_images;
		RelativeLayout image_layout;

		TextView collection;
		TextView comment;
		TextView zan;
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
				w = 2 * wh + SysUtils.Dp2Px(context, 2);
				gv_images.setNumColumns(2);
				break;
			case 3:
			case 5:
			case 6:
				w = wh * 3 + SysUtils.Dp2Px(context, 2) * 2;
				gv_images.setNumColumns(3);
				break;
			}
			RelativeLayout.LayoutParams lp = new RelativeLayout.LayoutParams(w,
					LayoutParams.WRAP_CONTENT);
			gv_images.setLayoutParams(lp);
			pictureAdapter = new GVPictureAdapter(context, list);
			gv_images.setAdapter(pictureAdapter);
			gv_images.setOnItemClickListener(new OnItemClickListener() {
				@Override
				public void onItemClick(AdapterView<?> arg0, View arg1,
						int arg2, long arg3) {
					Toast.makeText(context, "点击了第" + (arg2 + 1) + "张图片",
							Toast.LENGTH_SHORT).show();
				}
			});
		}
	}

	public void clear(List<DynamicInfo> list) {
		// TODO 自动生成的方法存根
		if(list.size()>0)
			list.clear();
	}
}
