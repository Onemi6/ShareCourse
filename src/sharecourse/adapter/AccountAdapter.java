package sharecourse.adapter;

import java.util.List;

import sharecourse.myclass.AccountNumber;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

public class AccountAdapter extends ArrayAdapter<AccountNumber> {

	private int resourceId;
	private int oldIndex,newIndex;
	public AccountAdapter(Context context, int resource,
			List<AccountNumber> objects,int oldIndex,int newIndex) {
		super(context, resource, objects);
		resourceId = resource;
		this.oldIndex = oldIndex;
		this.newIndex = newIndex;
	}
	@Override
	public View getView(int position,View convertView,ViewGroup parent)
	{
		AccountNumber account = getItem(position);
		View view = LayoutInflater.from(getContext()).inflate(resourceId, null);
		TextView accountName = (TextView)view.findViewById(R.id.account_name);
		TextView accountNum = (TextView)view.findViewById(R.id.account_num);
		TextView accountType = (TextView)view.findViewById(R.id.account_type);
		ImageView img = (ImageView)view.findViewById(R.id.account_img);
		
		if(position == oldIndex)
			img.setVisibility(View.INVISIBLE);
		if(position == newIndex)
			img.setVisibility(View.VISIBLE);
		
		accountName.setText(account.getAccountName());
		accountNum.setText(account.getAccountNum());
		accountType.setText(account.getAccountType());
		return view;
	}
}
