package com.by.dalitek.modelhouse.fragment;

import android.app.Activity;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.RelativeLayout;

import com.by.dalitek.modelhouse.adapter.LightChannelListAdapter;
import com.by.dalitek.modelhouse.adapter.LightSceneListAdapter;
import com.by.dalitek.modelhouse.domain.LightDomain;
import com.by.dalitek.modelhouse.util.BaseBroadReceiverI;
import com.by.dalitek.modelhouse.util.ConstantUtil;

import java.util.ArrayList;
import java.util.List;

import app.univs.cn.myapplication.R;

/**
 *
 * @author zhxr
 * @Description TODO
 * @Name LightFragment.java
 * @param
 * @time: 2015年2月10日 下午4:53:35
 */
public class LightFragment extends Fragment implements BaseBroadReceiverI{
	private String  tag="LightFragment";
	private Button light_btn_restaurant, light_btn_bedroom, light_btn_parlor,light_btn_meet;
	private RelativeLayout light_layout_restaurant, light_layout_bedroom,
			light_layout_parlor,light_layout_meet;

	//Adapter
	LightChannelListAdapter res_channelListAdapter,bedroom_channelListAdapter,parlor_channelListAdapter,meet_channelListAdapter;


	// 餐厅界面控件
	private ListView res_lv_channel, res_lv_scene;
	private List<LightDomain> res_channel_lightDomains = new ArrayList<LightDomain>();
	private List<LightDomain> res_scene_lightDomains = new ArrayList<LightDomain>();

	// 卧室界面控件
	private ListView bedroom_lv_channel, bedroom_lv_scene;
	private List<LightDomain> bedroom_channel_lightDomains = new ArrayList<LightDomain>();
	private List<LightDomain> bedroom_scene_lightDomains = new ArrayList<LightDomain>();

	// 客厅界面控件
	private ListView parlor_lv_channel, parlor_lv_scene;
	private List<LightDomain> parlor_channel_lightDomains = new ArrayList<LightDomain>();
	private List<LightDomain> parlor_scene_lightDomains = new ArrayList<LightDomain>();

	//会议室界面控件
	private ListView meet_lv_channel,meet_lv_scene;
	private List<LightDomain> meet_channel_lightDomains=new ArrayList<LightDomain>();
	private List<LightDomain> meet_scene_lightDomains=new ArrayList<LightDomain>();

	private int layout_flag = 1;// 当前显示界面标识

	private static final int RESLAYOUT_FLAG = 1;
	private static final int BEDROOM_FLAG = 2;
	private static final int PARLORLAYOUT_FLAG = 3;
	private static final int MEETLAYOUT_FLAG=4;

	private int WHITE = 0xffffffff;
	private int BLACK = 0xff000000;

	@Override
	public void onAttach(Activity activity) {
		// TODO Auto-generated method stub
		super.onAttach(activity);

	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
							 Bundle savedInstanceState) {
		View view = inflater.inflate(R.layout.fragment_light, container, false);
		findLightView(view);
		return view;
	}

	/*
	 * 初始化界面控件
	 */
	private void findLightView(View view) {
		light_btn_restaurant = (Button) view
				.findViewById(R.id.light_btn_restaurant);
		light_btn_bedroom = (Button) view.findViewById(R.id.light_btn_bedroom);
		light_btn_parlor = (Button) view.findViewById(R.id.light_btn_parlor);
		light_btn_meet = (Button) view.findViewById(R.id.light_btn_meet);

		light_layout_restaurant = (RelativeLayout) view
				.findViewById(R.id.light_layout_restaurant);
		light_layout_bedroom = (RelativeLayout) view
				.findViewById(R.id.light_layout_bedroom);
		light_layout_parlor = (RelativeLayout) view
				.findViewById(R.id.light_layout_parlor);
		light_layout_meet = (RelativeLayout) view
				.findViewById(R.id.light_layout_meet);

		light_btn_restaurant.setOnClickListener(new LightBtnClickListener(
				RESLAYOUT_FLAG));
		light_btn_bedroom.setOnClickListener(new LightBtnClickListener(
				BEDROOM_FLAG));
		light_btn_parlor.setOnClickListener(new LightBtnClickListener(
				PARLORLAYOUT_FLAG));
		light_btn_meet.setOnClickListener(new LightBtnClickListener(
				MEETLAYOUT_FLAG));

		initResLightView(view);
		initBedroomLightView(view);
		initParlorLightView(view);
		initMeetLightView(view);
	}

	// 初始化餐厅 灯光界面
	private void initResLightView(View view) {
		res_lv_channel = (ListView) view.findViewById(R.id.res_lv_channel);
		res_lv_scene = (ListView) view.findViewById(R.id.res_lv_scene);

		res_channelListAdapter = new LightChannelListAdapter(
				getActivity(), R.layout.item_light_channel_list,
				getResChannelData());
		res_lv_channel.setAdapter(res_channelListAdapter);
		//在为ListView组件的实例设置适配器时候，要对组件高度进行动态设置
		setListViewHeightBasedOnChildren(res_lv_channel);
	}

	private List<LightDomain> getResChannelData() {
		List<LightDomain> data = new ArrayList<LightDomain>();
		String[] names = ConstantUtil.LightConstants.RESTAURANT_CHANNEL_NAMES;
		String[] command_opens = ConstantUtil.LightConstants.RESTAURANT_CHANNEL_OPENS;
		String[] command_offs = ConstantUtil.LightConstants.RESTAURANT_CHANNEL_OFFS;

		for (int i = 0; i < names.length; i++) {
			LightDomain lightDomain = new LightDomain();
			lightDomain.setName(names[i]);
			lightDomain.setCommand_open(command_opens[i]);
			lightDomain.setCommand_off(command_offs[i]);
			data.add(lightDomain);
		}
		return data;
	}

	// 初始化卧室 灯光界面
	private void initBedroomLightView(View view) {
		bedroom_lv_channel = (ListView) view
				.findViewById(R.id.bedroom_lv_channel);
		bedroom_lv_scene = (ListView) view.findViewById(R.id.bedroom_lv_scene);

		bedroom_channelListAdapter = new LightChannelListAdapter(
				getActivity(), R.layout.item_light_channel_list,
				getBedroomChannelData());
		bedroom_lv_channel.setAdapter(bedroom_channelListAdapter);
		setListViewHeightBasedOnChildren(bedroom_lv_channel);

		LightSceneListAdapter sceneListAdapter = new LightSceneListAdapter(
				getActivity(), R.layout.item_light_scene_list,
				getBedroomSceneData());
		bedroom_lv_scene.setAdapter(sceneListAdapter);
		setListViewHeightBasedOnChildren(bedroom_lv_scene);
	}
	//卧室通道
	private List<LightDomain> getBedroomChannelData() {
		List<LightDomain> data = new ArrayList<LightDomain>();
		String[] names =  ConstantUtil.LightConstants.BEDROOM_CHANNEL_NAMES;
		String[] command_opens =  ConstantUtil.LightConstants.BEDROOM_CHANNEL_OPENS;
		String[] command_offs =  ConstantUtil.LightConstants.BEDROOM_CHANNEL_OFFS;

		for (int i = 0; i < names.length; i++) {
			LightDomain lightDomain = new LightDomain();
			lightDomain.setName(names[i]);
			lightDomain.setCommand_open(command_opens[i]);
			lightDomain.setCommand_off(command_offs[i]);
			data.add(lightDomain);
		}
		return data;
	}
	//卧室场景
	private List<LightDomain> getBedroomSceneData() {
		List<LightDomain> data = new ArrayList<LightDomain>();
		String[] names =  ConstantUtil.LightConstants.BEDROOM_SCENE_NAMES;
		String[] command_opens = ConstantUtil.LightConstants.BEDROOM_SCENE_OPENS;
		String[] command_offs = ConstantUtil.LightConstants.BEDROOM_SCENE_OFFS;

		for (int i = 0; i < names.length; i++) {
			LightDomain lightDomain = new LightDomain();
			lightDomain.setName(names[i]);
			lightDomain.setCommand_open(command_opens[i]);
			lightDomain.setCommand_off(command_offs[i]);
			data.add(lightDomain);
		}
		return data;
	}

	// 初始化餐厅 灯光界面
	private void initParlorLightView(View view) {
		parlor_lv_channel = (ListView) view
				.findViewById(R.id.parlor_lv_channel);
		parlor_lv_scene = (ListView) view.findViewById(R.id.parlor_lv_scene);

		parlor_channelListAdapter = new LightChannelListAdapter(
				getActivity(), R.layout.item_light_channel_list,
				getParlorChannelData());
		parlor_lv_channel.setAdapter(parlor_channelListAdapter);
		setListViewHeightBasedOnChildren(parlor_lv_channel);

		LightSceneListAdapter sceneListAdapter = new LightSceneListAdapter(
				getActivity(), R.layout.item_light_scene_list,
				getParlorSceneData());
		parlor_lv_scene.setAdapter(sceneListAdapter);
		setListViewHeightBasedOnChildren(parlor_lv_scene);
	}
	//客厅通道
	private List<LightDomain> getParlorChannelData() {
		List<LightDomain> data = new ArrayList<LightDomain>();
		String[] names = ConstantUtil.LightConstants.PARLOUR_CHANNEL_NAMES;
		String[] command_opens = ConstantUtil.LightConstants.PARLOUR_CHANNEL_OPENS;
		String[] command_offs =  ConstantUtil.LightConstants.PARLOUR_CHANNEL_OFFS;

		for (int i = 0; i < names.length; i++) {
			LightDomain lightDomain = new LightDomain();
			lightDomain.setName(names[i]);
			lightDomain.setCommand_open(command_opens[i]);
			lightDomain.setCommand_off(command_offs[i]);
			data.add(lightDomain);
		}
		return data;
	}
	//客厅场景
	private List<LightDomain> getParlorSceneData() {
		List<LightDomain> data = new ArrayList<LightDomain>();
		String[] names = ConstantUtil.LightConstants.PARLOUR_SCENE_NAMES;
		String[] command_opens = ConstantUtil.LightConstants.PARLOUR_SCENE_OPENS;
		String[] command_offs =ConstantUtil.LightConstants.PARLOUR_SCENE_OFFS;

		for (int i = 0; i < names.length; i++) {
			LightDomain lightDomain = new LightDomain();
			lightDomain.setName(names[i]);
			lightDomain.setCommand_open(command_opens[i]);
			lightDomain.setCommand_off(command_offs[i]);
			data.add(lightDomain);
		}
		return data;
	}

	// 初始化会议室 灯光界面
	private void initMeetLightView(View view) {
		meet_lv_channel = (ListView) view
				.findViewById(R.id.meet_lv_channel);
		meet_lv_scene = (ListView) view.findViewById(R.id.meet_lv_scene);

		meet_channelListAdapter = new LightChannelListAdapter(
				getActivity(), R.layout.item_light_channel_list,
				getMeetChannelData());
		meet_lv_channel.setAdapter(meet_channelListAdapter);
		setListViewHeightBasedOnChildren(meet_lv_channel);

		LightSceneListAdapter sceneListAdapter = new LightSceneListAdapter(
				getActivity(), R.layout.item_light_scene_list,
				getMeetSceneData());
		meet_lv_scene.setAdapter(sceneListAdapter);
		setListViewHeightBasedOnChildren(meet_lv_scene);
	}
	//会议室通道
	private List<LightDomain> getMeetChannelData() {
		List<LightDomain> data = new ArrayList<LightDomain>();
		String[] names = ConstantUtil.LightConstants.MEET_CHANNEL_NAMES;
		String[] command_opens = ConstantUtil.LightConstants.MEET_CHANNEL_OPENS;
		String[] command_offs =  ConstantUtil.LightConstants.MEET_CHANNEL_OFFS;

		for (int i = 0; i < names.length; i++) {
			LightDomain lightDomain = new LightDomain();
			lightDomain.setName(names[i]);
			lightDomain.setCommand_open(command_opens[i]);
			lightDomain.setCommand_off(command_offs[i]);
			data.add(lightDomain);
		}
		return data;
	}
	//会议室场景
	private List<LightDomain> getMeetSceneData() {
		List<LightDomain> data = new ArrayList<LightDomain>();
		String[] names = ConstantUtil.LightConstants.MEET_SCENE_NAMES;
		String[] command_opens = ConstantUtil.LightConstants.MEET_SCENE_OPENS;
		String[] command_offs =ConstantUtil.LightConstants.MEET_SCENE_OFFS;

		for (int i = 0; i < names.length; i++) {
			LightDomain lightDomain = new LightDomain();
			lightDomain.setName(names[i]);
			lightDomain.setCommand_open(command_opens[i]);
			lightDomain.setCommand_off(command_offs[i]);
			data.add(lightDomain);
		}
		return data;
	}

	/*
	 * 灯光界面点击事件
	 */
	class LightBtnClickListener implements OnClickListener {
		private int flag = 1;

		public LightBtnClickListener(int flag) {
			super();
			this.flag = flag;
		}

		@Override
		public void onClick(View v) {
			if (flag == layout_flag) {
				return;
			}
			layout_flag = flag;
			setTopButton();
		}
	}

	/*
	 * 设置 导航栏按钮状态
	 */
	private void setTopButton() {
		light_layout_restaurant.setVisibility(View.GONE);
		light_layout_bedroom.setVisibility(View.GONE);
		light_layout_parlor.setVisibility(View.GONE);
		light_layout_meet.setVisibility(View.GONE);

		light_btn_restaurant.setBackgroundResource(R.drawable.res_normal);;
		light_btn_bedroom.setBackgroundResource(R.drawable.bedroom_normal);;
		light_btn_parlor.setBackgroundResource(R.drawable.parlor_normal);;
		light_btn_meet.setBackgroundResource(R.drawable.meet_normal);;

		switch (layout_flag) {
			case RESLAYOUT_FLAG:
				light_layout_restaurant.setVisibility(View.VISIBLE);
				light_btn_restaurant.setBackgroundResource(R.drawable.res_selected);;
				break;
			case BEDROOM_FLAG:
				light_layout_bedroom.setVisibility(View.VISIBLE);
				light_btn_bedroom.setBackgroundResource(R.drawable.bedroom_selected);;
				break;
			case PARLORLAYOUT_FLAG:
				light_layout_parlor.setVisibility(View.VISIBLE);
				light_btn_parlor.setBackgroundResource(R.drawable.parlor_selected);;
				break;
			case MEETLAYOUT_FLAG:
				light_layout_meet.setVisibility(View.VISIBLE);
				light_btn_meet.setBackgroundResource(R.drawable.meet_selected);;
				break;
			default:
				break;
		}
	}

	/*
	 * 动态设置ListView组建的高度
	 */
	public void setListViewHeightBasedOnChildren(ListView listView) {
		ListAdapter listAdapter = listView.getAdapter();
		if (listAdapter == null) {
			return;
		}
		int totalHeight = 0;
		for (int i = 0; i < listAdapter.getCount(); i++) {
			View listItem = listAdapter.getView(i, null, listView);
			listItem.measure(0, 0);
			totalHeight += listItem.getMeasuredHeight();
		}
		ViewGroup.LayoutParams params = listView.getLayoutParams();
		params.height = totalHeight
				+ (listView.getDividerHeight() * (listAdapter.getCount() - 1));
		// params.height += 5;// if without this statement,the listview will be
		// a
		// little short
		// listView.getDividerHeight()获取子项间分隔符占用的高度
		// params.height最后得到整个ListView完整显示需要的高度
		listView.setLayoutParams(params);
	}

	@Override
	public void manageReadCmd(String readCmd) {
		Log.d(tag, readCmd);
		if(res_channelListAdapter!=null)
			res_channelListAdapter.notifyDataSetChanged();
		if(bedroom_channelListAdapter!=null)
			bedroom_channelListAdapter.notifyDataSetChanged();
		if(parlor_channelListAdapter!=null)
			parlor_channelListAdapter.notifyDataSetChanged();
		if(meet_channelListAdapter!=null)
			meet_channelListAdapter.notifyDataSetChanged();

	}

}
