package com.by.dalitek.modelhouse.adapter;

import android.content.Context;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.os.CountDownTimer;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnTouchListener;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.by.dalitek.modelhouse.domain.LightDomain;
import com.by.dalitek.modelhouse.service.ModelService;
import com.by.dalitek.modelhouse.ui.MainActivity;
import com.by.dalitek.modelhouse.util.ConstantUtil;
import com.by.dalitek.modelhouse.util.MyFunction;

import java.util.List;

import app.univs.cn.myapplication.R;

/**
 *
 * @author zhxr
 * @Description 窗帘配置列表的适配器
 * @Name CurtainListAdapter.java
 * @param
 * @time: 2015年1月30日 上午11:11:08
 */
public class LightChannelListAdapter extends BaseAdapter {

	private String tag="LightChannelListAdapter";

	private Context context;
	private int resource;
	private List<LightDomain> data;

	private SharedPreferences sharedPreferences;
	private Editor editor;

	public LightChannelListAdapter(Context context, int resource,
								   List<LightDomain> data) {
		super();
		this.context = context;
		this.resource = resource;
		this.data = data;

		sharedPreferences = context.getSharedPreferences(ConstantUtil.CONFIG_FILE_NAME,
				Context.MODE_PRIVATE);
		editor = sharedPreferences.edit();
	}

	@Override
	public int getCount() {
		// TODO Auto-generated method stub
		return data.size();
	}

	@Override
	public Object getItem(int position) {
		// TODO Auto-generated method stub
		return data.get(position);
	}

	@Override
	public long getItemId(int position) {
		// TODO Auto-generated method stub
		return position;
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {

		if(convertView==null){
			LayoutInflater inflater=(LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
			convertView=inflater.inflate(resource, null);
		}

		//获取控件
		TextView tv_name=(TextView) convertView.findViewById(R.id.itemlight_channel_tv_name);
		final ImageView iv_onoff=(ImageView) convertView.findViewById(R.id.itemlight_channel_iv_onoff);

		final LightDomain lightDomain=data.get(position);
		tv_name.setText(lightDomain.getName());

		final int channel=Integer.parseInt(lightDomain.getCommand_open().substring(10, 12),16)+1;
		int status=sharedPreferences.getInt("channel"+channel, 0);
		if(status!=0){
			iv_onoff.setImageResource(R.drawable.on);
		}else{
			iv_onoff.setImageResource(R.drawable.off);
		}

		iv_onoff.setOnTouchListener(new OnTouchListener() {

			@Override
			public boolean onTouch(View v, MotionEvent event) {
				switch (event.getAction()) {
					case MotionEvent.ACTION_DOWN:
						((MainActivity)context).isSend=false;

						break;
					case MotionEvent.ACTION_UP:

						Log.i(tag, "channel="+channel);

						if(sharedPreferences.getInt("channel"+channel, 0)!=0){
							iv_onoff.setImageResource(R.drawable.off);
							ModelService.sendControlCmd(MyFunction.HexString2Bytes(lightDomain.getCommand_off()));
							editor.putInt("channel"+channel, 0);
							editor.commit();
						}else{
							iv_onoff.setImageResource(R.drawable.on);
							ModelService.sendControlCmd(MyFunction.HexString2Bytes(lightDomain.getCommand_open()));
							editor.putInt("channel"+channel, 1);
							editor.commit();
						}

//					((MainActivity)context).isSend=true;
						requestTimer.cancel();
						requestTimer.start();
						break;
					default:
						break;
				}
				return false;
			}
		});

		return convertView;
	}

	@Override
	public void notifyDataSetChanged() {
		// TODO Auto-generated method stub
		super.notifyDataSetChanged();
	}

	CountDownTimer requestTimer=new CountDownTimer(6000,1000) {
		@Override
		public void onTick(long millisUntilFinished) {
		}
		@Override
		public void onFinish() {
			((MainActivity)context).isSend=true;
		}
	};

}
