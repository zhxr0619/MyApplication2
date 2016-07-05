package com.by.dalitek.modelhouse.fragment;

import android.app.Activity;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnTouchListener;
import android.view.ViewGroup;
import android.widget.TextView;

import com.by.dalitek.modelhouse.service.ModelService;
import com.by.dalitek.modelhouse.util.BaseBroadReceiverI;
import com.by.dalitek.modelhouse.util.ConstantUtil;
import com.by.dalitek.modelhouse.util.MyFunction;

import app.univs.cn.myapplication.R;

/**
 *
 * @author zhxr
 * @Description 遮陽界面
 * @Name SunShadeFragment.java
 * @param
 * @time: 2015年2月11日 上午11:03:28
 */
public class SunShadeFragment extends Fragment implements BaseBroadReceiverI,OnTouchListener{

	private String  tag="SunShadeFragment";
	//纱帘控件
	private TextView blind_tv_open,blind_tv_pause,blind_tv_off;
	//布帘控件
	private TextView curtain_tv_open,curtain_tv_off,curtain_tv_pause;//curtain_tv_pause,
	//雾化玻璃
	private TextView glass_tv_open,glass_tv_off;
	//投影
	private TextView shadow_tv_open,shadow_tv_off,shadow_tv_pause;

	@Override
	public void onAttach(Activity activity) {
		super.onAttach(activity);
	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
							 Bundle savedInstanceState) {
		View view=inflater.inflate(R.layout.fragment_sunshade, container, false);
		findSunShadeView(view);
		return view;
	}
	/**初始化遮阳界面控件*/
	private void findSunShadeView(View view){
		blind_tv_open=(TextView) view.findViewById(R.id.blind_tv_open);
		blind_tv_pause=(TextView) view.findViewById(R.id.blind_tv_pause);
		blind_tv_off=(TextView) view.findViewById(R.id.blind_tv_off);

		curtain_tv_open=(TextView) view.findViewById(R.id.curtain_tv_open);
		curtain_tv_off=(TextView) view.findViewById(R.id.curtain_tv_off);
		curtain_tv_pause=(TextView) view.findViewById(R.id.curtain_tv_pause);

		glass_tv_open=(TextView) view.findViewById(R.id.glass_tv_open);
		glass_tv_off=(TextView) view.findViewById(R.id.glass_tv_off);

		shadow_tv_open=(TextView) view.findViewById(R.id.shadow_tv_open);
		shadow_tv_pause=(TextView) view.findViewById(R.id.shadow_tv_pause);
		shadow_tv_off=(TextView) view.findViewById(R.id.shadow_tv_off);

		blind_tv_open.setOnTouchListener(this);
		blind_tv_pause.setOnTouchListener(this);
		blind_tv_off.setOnTouchListener(this);
		curtain_tv_open.setOnTouchListener(this);
		curtain_tv_off.setOnTouchListener(this);

		curtain_tv_pause.setOnTouchListener(this);

		glass_tv_open.setOnTouchListener(this);
		glass_tv_off.setOnTouchListener(this);
		shadow_tv_open.setOnTouchListener(this);
		shadow_tv_pause.setOnTouchListener(this);
		shadow_tv_off.setOnTouchListener(this);
	}

	@Override
	public boolean onTouch(View v, MotionEvent event) {
		int id=v.getId();
		switch (event.getAction()) {
			case MotionEvent.ACTION_DOWN:
//			switch (id) {
//			case R.id.curtain_tv_open:
//				ModelService.sendControlCmd(MyFunction.HexString2Bytes(ConstantUtil.SunConstants.curtain_open_cmd));
//				break;
//			case R.id.curtain_tv_off:
//				ModelService.sendControlCmd(MyFunction.HexString2Bytes(ConstantUtil.SunConstants.curtain_off_cmd));
//				break;
//			default:
//				break;
//			}
				break;
			case MotionEvent.ACTION_UP:
				switch (id) {
					case R.id.blind_tv_open://纱帘
						ModelService.sendControlCmd(MyFunction.HexString2Bytes(ConstantUtil.SunConstants.blind_open_cmd));
						break;
					case R.id.blind_tv_pause:
						ModelService.sendControlCmd(MyFunction.HexString2Bytes(ConstantUtil.SunConstants.blind_pause_cmd));
						break;
					case R.id.blind_tv_off:
						ModelService.sendControlCmd(MyFunction.HexString2Bytes(ConstantUtil.SunConstants.blind_off_cmd));
						break;
					case R.id.curtain_tv_open://百叶窗 开停
						ModelService.sendControlCmd(MyFunction.HexString2Bytes(ConstantUtil.SunConstants.curtain_open_cmd));
						break;
					case R.id.curtain_tv_pause://百叶窗 开停
						ModelService.sendControlCmd(MyFunction.HexString2Bytes(ConstantUtil.SunConstants.curtain_pause_cmd));
						break;
					case R.id.curtain_tv_off://百叶窗 关停
						ModelService.sendControlCmd(MyFunction.HexString2Bytes(ConstantUtil.SunConstants.curtain_off_cmd));
						break;
					case R.id.glass_tv_open://百叶窗 开
						ModelService.sendControlCmd(MyFunction.HexString2Bytes(ConstantUtil.SunConstants.curtain_open_cmd));
						break;
					case R.id.glass_tv_off://百叶窗 关
						ModelService.sendControlCmd(MyFunction.HexString2Bytes(ConstantUtil.SunConstants.curtain_off_cmd));
						break;
					case R.id.shadow_tv_open://投影
						ModelService.sendControlCmd(MyFunction.HexString2Bytes(ConstantUtil.SunConstants.shadow_open_cmd));
						break;
					case R.id.shadow_tv_pause:
						ModelService.sendControlCmd(MyFunction.HexString2Bytes(ConstantUtil.SunConstants.shadow_pause_cmd));
						break;
					case R.id.shadow_tv_off:
						ModelService.sendControlCmd(MyFunction.HexString2Bytes(ConstantUtil.SunConstants.shadow_off_cmd));
						break;
					default:
						break;
				}
				break;
			default:
				break;
		}
		return false;
	}

	@Override
	public void manageReadCmd(String readCmd) {
//		Log.d(tag, readCmd);
	}
}
