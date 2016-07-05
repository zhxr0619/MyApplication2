package com.by.dalitek.modelhouse.adapter;

import android.content.Context;
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
import com.by.dalitek.modelhouse.util.MyFunction;

import java.util.List;

import app.univs.cn.myapplication.R;

/**
 * @author zhxr
 * @Description 灯光  场景按钮的适配器
 * @Name LightSceneListAdapter.java
 * @param
 * @time: 2015年3月27日 下午12:47:57
 */
public class LightSceneListAdapter extends BaseAdapter {

	private String tag="LightSceneListAdapter";

	private Context context;
	private int resource;
	private List<LightDomain> data;

	public LightSceneListAdapter(Context context, int resource,
								 List<LightDomain> data) {
		super();
		this.context = context;
		this.resource = resource;
		this.data = data;
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
		TextView tv_name=(TextView) convertView.findViewById(R.id.itemlight_scene_tv_name);
		final ImageView iv_onoff=(ImageView) convertView.findViewById(R.id.itemlight_scene_iv_onoff);

		final View backView=convertView;

		final LightDomain lightDomain=data.get(position);
		tv_name.setText(lightDomain.getName());

		iv_onoff.setOnTouchListener(new OnTouchListener() {

			@Override
			public boolean onTouch(View v, MotionEvent event) {
				switch (event.getAction()) {
					case MotionEvent.ACTION_DOWN:
						iv_onoff.setImageResource(R.drawable.scene_on);
						backView.setBackgroundResource(R.drawable.item_on1);

						((MainActivity)context).isSend=false;
						break;
					case MotionEvent.ACTION_UP:
						Log.i(tag, "command_open="+lightDomain.getCommand_open());
						iv_onoff.setImageResource(R.drawable.scene_off);
						ModelService.sendControlCmd(MyFunction.HexString2Bytes(lightDomain.getCommand_open()));

//					((MainActivity)context).isSend=true;
						requestTimer.cancel();
						requestTimer.start();

					case MotionEvent.ACTION_CANCEL:
						iv_onoff.setImageResource(R.drawable.scene_off);
						backView.setBackgroundResource(R.drawable.item_off1);
						break;
					default:
						break;
				}
				return false;
			}
		});

		return convertView;
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
