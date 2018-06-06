package sharecourse.fragment;

import sharecourse.activity.PublishedActivity;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.sharecourse.R;
import com.example.sharecourse.R.layout;

public class PublishFragment extends Fragment {

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		View view = inflater.inflate(R.layout.fragment_publish, null);
		
		startActivity(new android.content.Intent(getActivity(),
				PublishedActivity.class));
		
		return view;
	}

}
