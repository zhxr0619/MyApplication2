package com.by.dalitek.modelhouse.fragment;

import android.app.Activity;
import android.graphics.Typeface;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.by.dalitek.modelhouse.service.ModelService;
import com.by.dalitek.modelhouse.ui.MainActivity;
import com.by.dalitek.modelhouse.util.BaseBroadReceiverI;
import com.by.dalitek.modelhouse.util.ConstantUtil;
import com.by.dalitek.modelhouse.util.MyFunction;

import app.univs.cn.myapplication.R;

/**
 *
 * @author zhxr
 * @Description TODO
 * @Name EnvironmentFragment.java
 * @param
 * @time: 2015年2月10日 下午3:52:03
 */
public class EnvironmentFragment extends Fragment implements BaseBroadReceiverI{
	private String tag="EnvironmentFragment";

	private MainActivity activity;
	//
	private Button envir_btn_aircon,envir_btn_leak,envir_btn_pm;

	private RelativeLayout envir_layout_aircon,envir_layout_leak,envir_layout_pm;
	//空调界面控件
	private TextView aircon_tv_temp;//当前温度
	private Button aircon_btn_power,aircon_btn_model,aircon_btn_wind,aircon_btn_up,aircon_btn_down;//空调开关、模式、风速、增加、减少按钮
	//漏水检测界面控件
	private ImageView leak_iv_status;//正常  报警 图片
	private TextView leak_tv_status;//当前状态---正常0    故障1
	private Button leak_btn_off,leak_btn_wind;//关阀  排风 按钮
	//pm2.5界面控件
	private TextView pm_tv_aqi,pm_tv_aqistatus,pm_tv_roomhumi;//空气质量指数aqi   状态 轻度污染     湿度
	private ImageView pm_iv_freshair;//新风状态
	private Button pm_btn_wind,pm_btn_power;//风速  开关按钮

	private Typeface typeface_num;//数字

	//变量
	private boolean isAirOn;//空调开关
	private int airModel;//空调模式
	private int airWind;//空调风速
	private int airSetTemp;//空调设定温度
	private int airCurTemp;//空调当前温度

	private int layout_flag=1;//当前显示界面标识
	/**空调界面标识*/
	private static final int AIRCONLAYOUT_FLAG=1;
	/**煤气leak界面标识*/
	private static final int leakLAYOUT_FLAG=2;
	/**PM 2.5界面标识*/
	private static final int PMLAYOUT_FLAG=3;

	private int WHITE=0xffffffff;
	private int BLACK=0xff000000;

	@Override
	public void onAttach(Activity activity) {
		super.onAttach(activity);
		this.activity=(MainActivity) activity;

		typeface_num=Typeface.createFromAsset(activity.getAssets(), "font/Quartz Regular.ttf");
	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
							 Bundle savedInstanceState) {
		View view=inflater.inflate(R.layout.fragment_environment, container,false);
		findEnvirView(view);
		return view;
	}
	/**
	 *
	 * @param @param view
	 * @Description 初始化界面 控件
	 * @return void
	 */
	private void findEnvirView(View view){
		envir_btn_aircon=(Button) view.findViewById(R.id.envir_btn_aircon);
		envir_btn_leak=(Button) view.findViewById(R.id.envir_btn_leak);
		envir_btn_pm=(Button) view.findViewById(R.id.envir_btn_pm);

		envir_layout_aircon=(RelativeLayout) view.findViewById(R.id.envir_layout_aircon);
		envir_layout_leak=(RelativeLayout) view.findViewById(R.id.envir_layout_leak);
		envir_layout_pm=(RelativeLayout) view.findViewById(R.id.envir_layout_pm);

		envir_btn_aircon.setOnClickListener(new EnvirBtnClickListener(AIRCONLAYOUT_FLAG));
		envir_btn_leak.setOnClickListener(new EnvirBtnClickListener(leakLAYOUT_FLAG));
		envir_btn_pm.setOnClickListener(new EnvirBtnClickListener(PMLAYOUT_FLAG));

		//空调界面控件
		findAirconView(view);
		//煤气界面控件
		findleakView(view);
		//电视界面控件
		findPmView(view);

	}
	/** 初始化空调界面控件*/
	private void findAirconView(View view){
		aircon_tv_temp=(TextView)view.findViewById(R.id.aircon_tv_settemp);
		aircon_tv_temp.setTypeface(typeface_num);

		aircon_btn_power=(Button)view.findViewById(R.id.aircon_btn_power);
		aircon_btn_model=(Button)view.findViewById(R.id.aircon_btn_model);
		aircon_btn_wind=(Button)view.findViewById(R.id.aircon_btn_wind);
		aircon_btn_up=(Button)view.findViewById(R.id.aircon_btn_up);
		aircon_btn_down=(Button)view.findViewById(R.id.aircon_btn_down);

//		aircon_btn_power.setOnClickListener(new AirconClickListener(0));
//		aircon_btn_model.setOnClickListener(new AirconClickListener(1));
//		aircon_btn_wind.setOnClickListener(new AirconClickListener(2));
//		aircon_btn_up.setOnClickListener(new AirconClickListener(3));
//		aircon_btn_down.setOnClickListener(new AirconClickListener(4));

		initAircon();
	}

	/**@Description 初始化空調控件*/
	private void initAircon(){

		isAirOn=MainActivity.sharedPreferences.getBoolean("isAirOn", false);//空调开关
		airModel=MainActivity.sharedPreferences.getInt("airModel", 1);//空调模式
		airWind=MainActivity.sharedPreferences.getInt("airWind", 1);//空调风速
		airSetTemp=MainActivity.sharedPreferences.getInt("airSetTemp", 26);//空调设定温度
		airCurTemp=MainActivity.sharedPreferences.getInt("htemp", 23);//空调当前温度
		System.out.println("airCurTemp=="+airCurTemp);
		aircon_tv_temp.setText(""+airCurTemp);

//		if(isAirOn){
//			aircon_btn_power.setBackgroundColor(BLACK);
//			aircon_btn_power.setTextColor(WHITE);
//		}else{
//			aircon_btn_power.setBackgroundColor(WHITE);
//			aircon_btn_power.setTextColor(BLACK);
//		}
//
//		switch (airModel) {
//		case 1://通风
//			aircon_btn_model.setText("通风");
//			break;
//		case 2://制冷
//			aircon_btn_model.setText("制冷");
//			break;
//		case 3://制热
//			aircon_btn_model.setText("制热");
//			break;
//		default:
//			break;
//		}
//		switch (airWind) {
//		case 1://低
//			aircon_btn_wind.setText("低速");
//			break;
//		case 2://中
//			aircon_btn_wind.setText("中速");
//			break;
//		case 3://高
//			aircon_btn_wind.setText("高速");
//			break;
//		default:
//			break;
//		}

	}

	/**
	 * @Description 空调界面控件点击事件
	 * @Name EnvironmentFragment.java
	 * @time: 2015年3月3日 上午9:02:58
	 */
	class AirconClickListener implements OnClickListener{
		private int flag;
		public AirconClickListener(int flag){
			this.flag=flag;
		}
		@Override
		public void onClick(View v) {
			switch (flag) {
				case 0:
					isAirOn=!isAirOn;
					break;
				case 1://模式
					if(!isAirOn){
						return;
					}
					airModel++;
					if(airModel==4){
						airModel=1;
					}
					break;
				case 2://風速
					if(!isAirOn){
						return;
					}
					airWind++;
					if(airWind==4){
						airWind=1;
					}
					break;
				case 3://溫度加
					if(!isAirOn){
						return;
					}
					if(airSetTemp<=36)
						airSetTemp++;
					break;
				case 4://溫度減
					if(!isAirOn){
						return;
					}
					if(airSetTemp>=16)
						airSetTemp--;
					break;
				default:
					break;
			}
			sendAirCmd();
			savaAirData();
		}
	}
	/**發送空調指令*/
	private void sendAirCmd(){
		final byte[] cmd = new byte[8];
		cmd[0] = (byte) 0xFA;
		cmd[1] = (byte) 0xB8;//--------------
		cmd[2] = (byte) ConstantUtil.EnvirConstants.AIR_BOX;
		cmd[3] = (byte) 0x8F;
		cmd[4]=(byte) (isAirOn?(airWind*16+airModel):0x00);
		cmd[5] = (byte) airSetTemp;
		cmd[6] = (byte) airCurTemp;
		MyFunction.setCheckSum(cmd);
		ModelService.sendControlCmd(cmd);
	}

	/** @Description 保存空調數據*/
	private void savaAirData(){
		MainActivity.editor.putBoolean("isAirOn", isAirOn);
		MainActivity.editor.putInt("airModel", airModel);
		MainActivity.editor.putInt("airWind", airWind);
		MainActivity.editor.putInt("airSetTemp", airSetTemp);
		MainActivity.editor.putInt("htemp", airCurTemp);
		MainActivity.editor.commit();

		initAircon();
	}

	/** 初始化煤氣界面控件*/
	private void findleakView(View view){
		leak_iv_status=(ImageView) view.findViewById(R.id.leak_iv_status);
		leak_tv_status=(TextView) view.findViewById(R.id.leak_tv_status);
		leak_btn_off=(Button) view.findViewById(R.id.leak_btn_off);
		leak_btn_wind=(Button) view.findViewById(R.id.leak_btn_wind);

		initleak();
	}

	private void initleak(){
		int leak=activity.sharedPreferences.getInt("leak", 0);

		leak_tv_status.setText(leak==0?"正常":"故障");
		leak_iv_status.setImageResource(leak==0?R.drawable.normal:R.drawable.fault);

	}
	/** 初始化PM 2.5 界面控件*/
	private void findPmView(View view){
		pm_tv_aqi=(TextView) view.findViewById(R.id.pm_tv_aqi);
		pm_tv_aqistatus=(TextView) view.findViewById(R.id.pm_tv_aqistatus);

		pm_tv_roomhumi=(TextView) view.findViewById(R.id.pm_tv_roomhumi);

		pm_tv_aqi.setTypeface(typeface_num);
		pm_tv_aqistatus.setTypeface(typeface_num);
		pm_tv_roomhumi.setTypeface(typeface_num);

		pm_iv_freshair=(ImageView) view.findViewById(R.id.pm_iv_freshair);
		pm_btn_wind=(Button) view.findViewById(R.id.pm_btn_wind);
		pm_btn_power=(Button) view.findViewById(R.id.pm_btn_power);

		initPm();
	}

	private void initPm(){
		int pm=activity.sharedPreferences.getInt("pm", 55);
		pm_tv_aqi.setText(pm+"");
		pm_tv_aqistatus.setText((pm>=0&&pm<=50)?"优":pm<=100?"良":pm<=150?"轻度污染":pm<=200?"中度污染":pm<=300?"重度污染":"严重污染");

		pm_tv_roomhumi.setText(activity.sharedPreferences.getInt("humi", 63)+"");//湿度

	}

	/*
	 * 界面 导航栏按钮点击事件
	 */
	class EnvirBtnClickListener implements OnClickListener{
		private int flag=1;
		public EnvirBtnClickListener(int flag) {
			super();
			this.flag = flag;
		}
		@Override
		public void onClick(View v) {
			if(flag==layout_flag){
				return;
			}
			layout_flag=flag;
			setTopButton();
		}

	}
	/*
	 * 设置导航栏按钮状态
	 */
	private void setTopButton(){
		envir_layout_aircon.setVisibility(View.GONE);
		envir_layout_leak.setVisibility(View.GONE);
		envir_layout_pm.setVisibility(View.GONE);

		envir_btn_aircon.setBackgroundResource(R.drawable.air_normal);
		envir_btn_leak.setBackgroundResource(R.drawable.leak_normal);
		envir_btn_pm.setBackgroundResource(R.drawable.pm_normal);

		switch (layout_flag) {
			case AIRCONLAYOUT_FLAG:
				envir_layout_aircon.setVisibility(View.VISIBLE);
				envir_btn_aircon.setBackgroundResource(R.drawable.air_selected);
				break;
			case leakLAYOUT_FLAG:
				envir_layout_leak.setVisibility(View.VISIBLE);
				envir_btn_leak.setBackgroundResource(R.drawable.leak_selected);
				break;
			case PMLAYOUT_FLAG:
				envir_layout_pm.setVisibility(View.VISIBLE);
				envir_btn_pm.setBackgroundResource(R.drawable.pm_selected);
				break;
			default:
				break;
		}
	}

	@Override
	public void manageReadCmd(String readCmd) {
//		Log.d(tag, readCmd);
		if(aircon_tv_temp!=null)
			aircon_tv_temp.setText(activity.sharedPreferences.getInt("htemp", 25)+"");
		int pm=activity.sharedPreferences.getInt("pm", 55);
		if(pm_tv_aqi!=null)
			pm_tv_aqi.setText(pm+"");
		if(pm_tv_aqistatus!=null)
			pm_tv_aqistatus.setText((pm>=0&&pm<=50)?"优":pm<=100?"良":pm<=150?"轻度污染":pm<=200?"中度污染":pm<=300?"重度污染":"严重污染");
		if(pm_tv_roomhumi!=null)
			pm_tv_roomhumi.setText(activity.sharedPreferences.getInt("humi", 63)+"");//湿度

		int leak=activity.sharedPreferences.getInt("leak", 0);
		if(leak_tv_status!=null)
			leak_tv_status.setText(leak==0?"正常":"故障");
		if(leak_iv_status!=null)
			leak_iv_status.setImageResource(leak==0?R.drawable.normal:R.drawable.fault);
	}
}
