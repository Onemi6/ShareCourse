package sharecourse.fragment;

import java.io.IOException;
import java.io.StringReader;
import java.util.ArrayList;
import java.util.List;

import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlPullParserException;
import org.xmlpull.v1.XmlPullParserFactory;

import sharecourse.activity.DetailsActivity;
import sharecourse.activity.PublishedActivity;
import sharecourse.activity.TestPicActivity;
import sharecourse.adapter.DynamicAdapter;
import sharecourse.http.HttpCallbackListener;
import sharecourse.http.HttpUtil;
import sharecourse.myclass.DynamicInfo;
import sharecourse.myclass.MyApplication;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v4.app.Fragment;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v4.widget.SwipeRefreshLayout.OnRefreshListener;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ListView;
import android.widget.TabHost;
import android.widget.Toast;

import com.example.sharecourse.R;

public class HomeFragment extends Fragment {
	private final int SELECT_CONTENT_SUCCESS = 1;
	private final int SELECT_CONTENT_FAIL = 0;
	private ListView dynamics;
	private DynamicAdapter dynamicAdapter;
	private List<DynamicInfo> dynamiclist = new ArrayList<DynamicInfo>();;
	private static final int REFRESH_COMPLETE = 0X110;
	private SwipeRefreshLayout mSwipeLayout;
	private boolean isRefresh = false;
	private String address, userNum, time;
	private int pageNow = 1;
	// private RefreshLayout mSwipeLayout;

	private Handler mHandler = new Handler() {
		@Override
		public void handleMessage(android.os.Message msg) {
			switch (msg.what) {
			case REFRESH_COMPLETE:
				// TODO 自动生成的方法存根
				dynamicAdapter.clear(dynamiclist);
				dynamicAdapter.notifyDataSetChanged();
				pageNow =dynamiclist.size()/10+1;
				for (int i = 0; i < ((List<DynamicInfo>) msg.obj).size(); i++) {
					dynamiclist.add(((List<DynamicInfo>) msg.obj).get(i));
				}
				
				dynamicAdapter = new DynamicAdapter(getActivity(), dynamiclist);
				dynamics.setAdapter(dynamicAdapter);
				//dynamicAdapter.notifyDataSetChanged();
				mSwipeLayout.setRefreshing(false);
				break;
			case SELECT_CONTENT_SUCCESS: {
				/*dynamicAdapter.clear(dynamiclist);
				dynamicAdapter.notifyDataSetChanged();*/
				pageNow =dynamiclist.size()/10+1;
				for (int i = 0; i < ((List<DynamicInfo>) msg.obj).size(); i++) {
					dynamiclist.add(((List<DynamicInfo>) msg.obj).get(i));
				}
				dynamicAdapter = new DynamicAdapter(getActivity(), dynamiclist);
				dynamics.setAdapter(dynamicAdapter);
				break;
			}
			}

		};
	};

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		View view = inflater.inflate(R.layout.fragment_home, container, false);
		// requestWindowFeature(Window.FEATURE_NO_TITLE);
		// dynamiclist = new ArrayList<DynamicInfo>();
		dynamics = (ListView) view.findViewById(R.id.dynamic_listview);
		mSwipeLayout = (SwipeRefreshLayout) view.findViewById(R.id.id_swipe_ly);
		mSwipeLayout.setColorSchemeResources(android.R.color.holo_blue_bright,
				android.R.color.holo_green_light,
				android.R.color.holo_orange_light,
				android.R.color.holo_red_light);
		if(dynamiclist.size()>0)
			dynamiclist.clear();
		initData();

		dynamics.setOnItemClickListener(new OnItemClickListener() {
			@Override
			public void onItemClick(AdapterView<?> arg0, View arg1, int arg2,
					long arg3) {
				((MyApplication) getActivity().getApplication())
						.setDynamicNum(dynamiclist.get(arg2).getNum());
				Toast.makeText(getActivity(), "点击了第" + (arg2 + 1) + "项",
						Toast.LENGTH_LONG).show();
				Intent intent = new Intent(getActivity(),
						DetailsActivity.class);
				startActivity(intent);
			}
		});
		mSwipeLayout.setOnRefreshListener(new OnRefreshListener() {
			@Override
			public void onRefresh() {
				//mHandler.sendEmptyMessageDelayed(REFRESH_COMPLETE, 9000);
				userNum = ((MyApplication) getActivity().getApplication())
						.getUserNum();
				if (((MyApplication) getActivity().getApplication())
						.getUserType().equals("教师")) {
					address = "http://115.159.214.57:8080/sharecourse/Content/contentlist.jsp?"
							+ "teacherNum="
							+ userNum
							+ "&pageNow="
							+ pageNow
							+ "&time=" + time;
				} else {
					address = "http://115.159.214.57:8080/sharecourse/Content/contentlist.jsp?"
							+ "parentNum="
							+ userNum
							+ "&pageNow="
							+ pageNow
							+ "&time=" + time;
				}

				HttpUtil.sendHttpRequest(address, new HttpCallbackListener() {

					@Override
					public void onFinish(String response) {
						try {
							XmlPullParserFactory factory = XmlPullParserFactory
									.newInstance();
							XmlPullParser xmlPullParser = factory
									.newPullParser();
							xmlPullParser.setInput(new StringReader(response));
							int eventType = xmlPullParser.getEventType();
							String contentNum = "", teacherName = "", text = "", path = "", dynamictime = "";
							List<DynamicInfo> dynamiclist1 = new ArrayList<DynamicInfo>();

							while (eventType != XmlPullParser.END_DOCUMENT) {
								String nodeName = xmlPullParser.getName();
								switch (eventType) {
								case XmlPullParser.START_TAG: {
									if ("pageNow".equals(nodeName)) {
										try {
											pageNow = Integer
													.valueOf(xmlPullParser
															.nextText());
										} catch (IOException e) {
											// TODO 自动生成的 catch 块
											e.printStackTrace();
										}
									} else if ("contentNum".equals(nodeName)) {
										try {
											contentNum = xmlPullParser
													.nextText();
										} catch (IOException e) {
											// TODO 自动生成的 catch 块
											e.printStackTrace();
										}
									} else if ("teacherName".equals(nodeName)) {
										try {
											teacherName = xmlPullParser
													.nextText();
										} catch (IOException e) {
											// TODO 自动生成的 catch 块
											e.printStackTrace();
										}
									} else if ("text".equals(nodeName)) {
										try {
											text = xmlPullParser.nextText();
										} catch (IOException e) {
											// TODO 自动生成的 catch 块
											e.printStackTrace();
										}
									} else if ("path".equals(nodeName)) {
										try {
											path = xmlPullParser.nextText();
										} catch (IOException e) {
											// TODO 自动生成的 catch 块
											e.printStackTrace();
										}
									} else if ("time".equals(nodeName)) {
										try {
											dynamictime = xmlPullParser
													.nextText();
										} catch (IOException e) {
											// TODO 自动生成的 catch 块
											e.printStackTrace();
										}
									}
									break;
								}
								case XmlPullParser.END_TAG: {
									if ("app".equals(nodeName)) {
										DynamicInfo onenamic = new DynamicInfo();
										onenamic.setNum(contentNum);
										onenamic.setName(teacherName);
										onenamic.setIcon("http://hdn.xnimg.cn/photos/hdn321/20130612/2235/h_main_NNN4_e80a000007df111a.jpg");
										onenamic.setText(text);
										onenamic.setTime(dynamictime);
										onenamic.setImages(path);
										onenamic.setCollection("11");
										onenamic.setComment("22");
										onenamic.setZan("33");
										dynamiclist1.add(onenamic);
									}
									break;
								}
								default:
									break;
								}
								try {
									eventType = xmlPullParser.next();
								} catch (IOException e) {
									// TODO 自动生成的 catch 块
									e.printStackTrace();
								}
							}
							Message message = new Message();
							message.what = REFRESH_COMPLETE;
							message.obj = dynamiclist1;
							mHandler.sendMessage(message);
						} catch (XmlPullParserException e) {
							// TODO 自动生成的 catch 块
							e.printStackTrace();
						}
					}

					@Override
					public void onError(Exception e) {
					}
				});
			}
		});
		InputMethodManager im = (InputMethodManager) getActivity()
				.getSystemService(Context.INPUT_METHOD_SERVICE);
		im.hideSoftInputFromWindow(null, InputMethodManager.HIDE_NOT_ALWAYS);
		return view;
	}

	private void initData() {
		
		userNum = ((MyApplication) getActivity().getApplication()).getUserNum();
		if (((MyApplication) getActivity().getApplication()).getUserType()
				.equals("教师")) {
			address = "http://115.159.214.57:8080/sharecourse/Content/contentlist.jsp?"
					+ "teacherNum="
					+ userNum
					+ "&pageNow="
					+ pageNow
					+ "&time=" + time;
		} else {
			address = "http://115.159.214.57:8080/sharecourse/Content/contentlist.jsp?"
					+ "parentNum="
					+ userNum
					+ "&pageNow="
					+ pageNow
					+ "&time="
					+ time;
		}

		HttpUtil.sendHttpRequest(address, new HttpCallbackListener() {

			@Override
			public void onFinish(String response) {
				try {
					XmlPullParserFactory factory = XmlPullParserFactory
							.newInstance();
					XmlPullParser xmlPullParser = factory.newPullParser();
					xmlPullParser.setInput(new StringReader(response));
					int eventType = xmlPullParser.getEventType();
					String contentNum = "", teacherName = "", text = "", path = "", dynamictime = "";
					List<DynamicInfo> dynamiclist1 = new ArrayList<DynamicInfo>();

					while (eventType != XmlPullParser.END_DOCUMENT) {
						String nodeName = xmlPullParser.getName();
						switch (eventType) {
						case XmlPullParser.START_TAG: {
							//dynamiclist1.clear();
							if ("pageNow".equals(nodeName)) {
								try {
									pageNow = Integer.valueOf(xmlPullParser
											.nextText());
								} catch (IOException e) {
									// TODO 自动生成的 catch 块
									e.printStackTrace();
								}
							} else if ("contentNum".equals(nodeName)) {
								try {
									contentNum = xmlPullParser.nextText();
								} catch (IOException e) {
									// TODO 自动生成的 catch 块
									e.printStackTrace();
								}
							} else if ("teacherName".equals(nodeName)) {
								try {
									teacherName = xmlPullParser.nextText();
								} catch (IOException e) {
									// TODO 自动生成的 catch 块
									e.printStackTrace();
								}
							} else if ("text".equals(nodeName)) {
								try {
									text = xmlPullParser.nextText();
								} catch (IOException e) {
									// TODO 自动生成的 catch 块
									e.printStackTrace();
								}
							} else if ("path".equals(nodeName)) {
								try {
									path = xmlPullParser.nextText();
								} catch (IOException e) {
									// TODO 自动生成的 catch 块
									e.printStackTrace();
								}
							} else if ("time".equals(nodeName)) {
								try {
									dynamictime = xmlPullParser.nextText();
								} catch (IOException e) {
									// TODO 自动生成的 catch 块
									e.printStackTrace();
								}
							}
							break;
						}
						case XmlPullParser.END_TAG: {
							if ("app".equals(nodeName)) {
								DynamicInfo onenamic = new DynamicInfo();
								onenamic.setNum(contentNum);
								onenamic.setName(teacherName);
								onenamic.setIcon("http://hdn.xnimg.cn/photos/hdn321/20130612/2235/h_main_NNN4_e80a000007df111a.jpg");
								onenamic.setText(text);
								onenamic.setTime(dynamictime);
								onenamic.setImages(path);
								onenamic.setCollection("11");
								onenamic.setComment("22");
								onenamic.setZan("33");
								dynamiclist1.add(onenamic);
							}
							break;
						}
						default:
							break;
						}
						try {
							eventType = xmlPullParser.next();
						} catch (IOException e) {
							// TODO 自动生成的 catch 块
							e.printStackTrace();
						}
					}
					Message message = new Message();
					message.what = SELECT_CONTENT_SUCCESS;
					message.obj = dynamiclist1;
					mHandler.sendMessage(message);
				} catch (XmlPullParserException e) {
					// TODO 自动生成的 catch 块
					e.printStackTrace();
				}
			}
			@Override
			public void onError(Exception e) {
			}
		});
	}
}
