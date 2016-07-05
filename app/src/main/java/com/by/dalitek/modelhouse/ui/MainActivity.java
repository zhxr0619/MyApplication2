package com.by.dalitek.modelhouse.ui;

import android.annotation.SuppressLint;
import android.app.Dialog;
import android.content.BroadcastReceiver;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.ServiceConnection;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Typeface;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.os.Handler;
import android.os.IBinder;
import android.os.Message;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentTransaction;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.View.OnTouchListener;
import android.view.ViewGroup.LayoutParams;
import android.view.Window;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.PopupWindow;
import android.widget.TextView;

import com.by.dalitek.modelhouse.domain.WeatherDomain;
import com.by.dalitek.modelhouse.fragment.EntertainmentFragment;
import com.by.dalitek.modelhouse.fragment.EnvironmentFragment;
import com.by.dalitek.modelhouse.fragment.LightFragment;
import com.by.dalitek.modelhouse.fragment.SunShadeFragment;
import com.by.dalitek.modelhouse.service.ModelService;
import com.by.dalitek.modelhouse.service.ModelService.MyBind;
import com.by.dalitek.modelhouse.util.BaseBroadReceiverI;
import com.by.dalitek.modelhouse.util.ConstantUtil;
import com.by.dalitek.modelhouse.util.MyFunction;
import com.by.dalitek.modelhouse.util.WeatherQuery;
import com.by.dalitek.modelhouse.view.SetNetDialog;

import java.io.IOException;
import java.io.InputStream;
import java.text.SimpleDateFormat;
import java.util.Date;

import app.univs.cn.myapplication.R;

/**
 *
 * @author zhxr
 * @Description TODO
 * @Name MainActivity.java
 * @param
 * @time: 2015年2月10日 下午2:23:37
 */
@SuppressLint("NewApi")
public class MainActivity extends FragmentActivity implements OnTouchListener {
	// pop窗口 控件
	private PopupWindow mPopupWindow;
	private ImageView pop_iv_weat;// 天气预报图标
	private TextView pop_tv_weattemp, pop_tv_weat;// 天气预报
	private TextView pop_tv_roomtemp, pop_tv_roomhumi, pop_tv_pm;// 室内温度、湿度
	private TextView pop_tv_time;// 当前时间
	private Button pop_btn_wel, pop_btn_leave;// 欢迎、离开按钮
	private ImageView pop_iv_netstatus;// 网络链接状态
	private ImageButton pop_ib_setnet;// 网络设置按钮

	// 主界面 控件
	private ImageView main_iv_home;// 主页 控件
	private TextView main_tv_top, main_tv_time;// 菜单栏 导航 时间
	private Button main_btn_envir, main_btn_light, main_btn_sun,
			main_btn_enter;// 环境、灯光、遮阳、娱乐

	private Fragment fragment;// 切换界面

	private static final int ENVIRONMENT_FLAG = 1;
	private static final int SUN_FLAG = 2;
	private static final int LIGHT_FLAG = 3;
	private static final int ENTER_FLAG = 4;

	private int fragment_flag = 1;// 显示切换界面标识
	private int WHITE = 0xffffffff;
	private int BLACK = 0xff000000;

	private String[] topStr = { "ENVIRONMENT", "SUNSHADE","LIGHT","ENTERTAINMENT" };

	public static SharedPreferences sharedPreferences;
	public static Editor editor;

	private Typeface typeface;


	private int netstatus=0;
	private String ipaddress=ConstantUtil.IPADDRESS;
	private String port="2001";
	private MyReceiver receiver;//自定义广播接收
	/**初始化城市Name*/
	private String cityName="上海";
	/**初始化城市Id*/
	private String cityId = "101020100";
	/**查询天气线程*/
	private Thread thread;
	/**时间  定时器标识*/
	private boolean isTimeThread=true;
	private boolean isRequestThread=true;
	public  boolean isSend=true;

	/**发送消息更新视图*/
	private static final int VIEW_UPDATE=1;
	private static final int TIME_UPDATE = 0;
	private static final int REQUESTSTATUS=2;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main_model);

		System.out.println("onCreate");

		typeface=Typeface.createFromAsset(getAssets(), "font/Helvetica.ttf");

		sharedPreferences = getSharedPreferences(ConstantUtil.CONFIG_FILE_NAME,
				Context.MODE_PRIVATE);
		editor = sharedPreferences.edit();
		Intent intent = new Intent(this,
				com.by.dalitek.modelhouse.service.ModelService.class);
		// stopService(service);
		// startService(service);
		bindService(intent, connection, BIND_AUTO_CREATE);
		receiver = new MyReceiver();
		IntentFilter filter = new IntentFilter();
		filter.addAction("android.intent.action.MY_RECEIVER");
		// 注册
		registerReceiver(receiver, filter);

		findMainView();
		setFragment();

		new TimeThread().start();
		new RequestThread().start();
	}

	/**
	 *
	 * @param
	 * @Description 初始化主界面控件
	 * @return void
	 */
	private void findMainView() {
		main_iv_home = (ImageView) findViewById(R.id.main_iv_home);
		main_tv_top = (TextView) findViewById(R.id.main_tv_top);
		main_tv_time = (TextView) findViewById(R.id.main_tv_time);

		main_tv_time.setTypeface(typeface);
		main_tv_top.setTypeface(typeface);

		main_btn_envir = (Button) findViewById(R.id.main_btn_envir);
		main_btn_light = (Button) findViewById(R.id.main_btn_light);
		main_btn_sun = (Button) findViewById(R.id.main_btn_sun);
		main_btn_enter = (Button) findViewById(R.id.main_btn_enter);

		main_btn_envir.setOnClickListener(new MainBtnClickListener(
				ENVIRONMENT_FLAG));
		main_btn_light.setOnClickListener(new MainBtnClickListener(LIGHT_FLAG));
		main_btn_sun.setOnClickListener(new MainBtnClickListener(SUN_FLAG));
		main_btn_enter.setOnClickListener(new MainBtnClickListener(ENTER_FLAG));
	}

	/*
	 * 主界面 按钮点击事件
	 */
	class MainBtnClickListener implements OnClickListener {
		private int flag = 1;
		public MainBtnClickListener(int flag) {
			super();
			this.flag = flag;
		}

		@Override
		public void onClick(View v) {
			if (flag == fragment_flag) {
				return;
			}
			fragment_flag = flag;
			setMainButton();
			setFragment();
		}
	}

	/*
	 * 设置主界面按钮状态
	 */
	private void setMainButton() {
		main_btn_envir.setBackgroundResource(R.drawable.envir_normal);
		main_btn_light.setBackgroundResource(R.drawable.light_normal);
		main_btn_sun.setBackgroundResource(R.drawable.sun_normal);
		main_btn_enter.setBackgroundResource(R.drawable.enter_normal);

		switch (fragment_flag) {
			case ENVIRONMENT_FLAG:// 环境
				main_btn_envir.setBackgroundResource(R.drawable.envir_selected);
				break;
			case LIGHT_FLAG:// 灯光
				main_btn_light.setBackgroundResource(R.drawable.light_selected);
				break;
			case SUN_FLAG:// 遮阳
				main_btn_sun.setBackgroundResource(R.drawable.sun_selected);
				break;
			case ENTER_FLAG:// 娱乐
				main_btn_enter.setBackgroundResource(R.drawable.enter_selected);
				break;
			default:
				break;
		}

		main_tv_top.setText(topStr[fragment_flag - 1]);
	}

	/*
	 * 切换界面
	 */
	private void setFragment() {
		switch (fragment_flag) {
			case ENVIRONMENT_FLAG:// 环境
				fragment = new EnvironmentFragment();
				break;
			case LIGHT_FLAG:// 灯光
				fragment = new LightFragment();
				break;
			case SUN_FLAG:// 遮阳
				fragment = new SunShadeFragment();
				break;
			case ENTER_FLAG:// 娱乐
				fragment = new EntertainmentFragment();
				break;
			default:
				break;
		}
		FragmentTransaction ft = getSupportFragmentManager().beginTransaction();
		// ft.setTransition(FragmentTransaction.TRANSIT_FRAGMENT_FADE);
		ft.setCustomAnimations(R.anim.push_up_in, R.anim.push_up_out);
		ft.replace(R.id.main_layout_show, fragment).commit();

	}

	/*
	 * 主界面 主页图标点击 事件
	 */
	public void showPop(View view) {
		popTimer.onFinish();
	}

	@Override
	protected void onResume() {
		super.onResume();
		System.out.println("onResume");
		getWindow().getDecorView().setSystemUiVisibility(View.GONE);// 4.0pad去掉隐藏状态栏

	}

	@Override
	public void onWindowFocusChanged(boolean hasFocus) {// activity 启动立即弹出Pop窗口
		super.onWindowFocusChanged(hasFocus);
		System.out.println("onWindowFocusChanged");
		popTimer.onFinish();

	}

	@Override
	protected void onDestroy() {
		super.onDestroy();

		popTimer.cancel();//
		isTimeThread=false;
		isRequestThread=false;

		if(mPopupWindow!=null&&mPopupWindow.isShowing()){
			mPopupWindow.dismiss();
		}

		System.out.println("onDestroy");
		unregisterReceiver(receiver);
		unbindService(connection);
	}

	@Override
	public boolean onTouch(View v, MotionEvent event) {

		switch (event.getAction()) {
			case MotionEvent.ACTION_DOWN:
				switch (v.getId()) {
					case R.id.welcome_btn_wel:

						break;
					case R.id.welcome_btn_leave:
						ModelService.sendControlCmd(MyFunction.HexString2Bytes(ConstantUtil.MainConstants.LEAVE_CMD1));
						break;
					default:
						break;
				}

				break;
			case MotionEvent.ACTION_UP:
				switch (v.getId()) {
					case R.id.welcome_btn_wel:
						ModelService.sendControlCmd(MyFunction.HexString2Bytes(ConstantUtil.MainConstants.WELCOME_CMD));
						break;
					case R.id.welcome_btn_leave:
						ModelService.sendControlCmd(MyFunction.HexString2Bytes(ConstantUtil.MainConstants.LEAVE_CMD2));
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
	public boolean dispatchTouchEvent(MotionEvent ev) {
		popTimer.cancel();
		popTimer.start();

		return super.dispatchTouchEvent(ev);
	}

	/*
	 * 初始化获取控件
	 */
	private void findPopView(View layout) {

		System.out.println("findPopView");

		pop_iv_weat = (ImageView) layout.findViewById(R.id.welcome_iv_weat);
		pop_tv_weattemp = (TextView) layout
				.findViewById(R.id.welcome_tv_weattemp);
		pop_tv_weat = (TextView) layout.findViewById(R.id.welcome_tv_weat);
		pop_tv_roomtemp = (TextView) layout
				.findViewById(R.id.welcome_tv_roomtemp);
		pop_tv_roomhumi = (TextView) layout
				.findViewById(R.id.welcome_tv_roomhumi);
		pop_tv_pm = (TextView) layout.findViewById(R.id.welcome_tv_pm);

		//初始值
		pop_tv_roomtemp.setText(sharedPreferences.getInt("htemp", 25)+"");
		pop_tv_roomhumi.setText(sharedPreferences.getInt("humi", 63)+"");
		int pm=sharedPreferences.getInt("pm", 55);
		pop_tv_pm.setText(pm+" "+((pm>=0&&pm<=50)?"优":pm<=100?"良":pm<=150?"轻度":pm<=200?"中度":pm<=300?"重度":"严重"));

		pop_tv_time = (TextView) layout.findViewById(R.id.welcome_tv_time);
		pop_tv_time.setTypeface(typeface);

		pop_btn_wel = (Button) layout.findViewById(R.id.welcome_btn_wel);
		pop_btn_leave = (Button) layout.findViewById(R.id.welcome_btn_leave);

		pop_iv_netstatus = (ImageView) layout
				.findViewById(R.id.welcome_iv_netstatus);
		pop_ib_setnet = (ImageButton) layout
				.findViewById(R.id.welcome_ib_setnet);

		pop_btn_wel.setOnTouchListener(this);

		pop_btn_leave.setOnTouchListener(this);

		pop_ib_setnet.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				showSetNetDialog();
			}
		});

	}

	/*
	 * 显示设置网络 对话框
	 */
	private void showSetNetDialog() {
		String ip = sharedPreferences.getString("ipaddress",
				ConstantUtil.IPADDRESS);
		int port1 = sharedPreferences.getInt("port", 2001);
		final Dialog dialog = new SetNetDialog(this, R.style.MyDialog);
		dialog.show();
		dialog.setCanceledOnTouchOutside(true);
		Window window = dialog.getWindow();

		final EditText et_ipaddress = (EditText) window
				.findViewById(R.id.dialog_et_ipaddress);
		final EditText et_port = (EditText) window.findViewById(R.id.dialog_et_port);
		et_ipaddress.setText(ip);
		et_port.setText(port1+"");

		Button btn_cancel = (Button) window
				.findViewById(R.id.dialog_btn_cancel);
		Button btn_confirm = (Button) window
				.findViewById(R.id.dialog_btn_confirm);
		// 取消按键
		btn_cancel.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				dialog.dismiss();
			}
		});
		// 确认按钮
		btn_confirm.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				ipaddress = et_ipaddress.getText().toString();
				port = et_port.getText().toString();
				if("".equals(ipaddress))
					ipaddress=ConstantUtil.IPADDRESS;
				if ("".equals(port))
					port = "2001";

				int send_port = Integer.parseInt(port);
				MainActivity.editor.putString("ipaddress", ipaddress);
				MainActivity.editor.putInt("port", send_port);
				MainActivity.editor.commit();
				try {
					ModelService.setProxy(ipaddress, send_port);
				} catch (IOException e) {
					e.printStackTrace();
				}

				dialog.cancel();
			}
		});

	}

	WeatherDomain weatherDomain=new WeatherDomain();
	/**
	 * 向中央气象台查询天气
	 */
	private void queryWeather(){
		thread = new Thread(){
			@Override
			public void run() {

				System.out.println("=====查詢天氣");

				WeatherDomain[] weatherDomains = new WeatherDomain[3];
				WeatherQuery weatherQuery = new WeatherQuery();
				//查询天气，返回3天的天气信息
				weatherDomains = weatherQuery.getWeatherByCityId(cityId);

				if(weatherDomains.length>=1){
					if(weatherDomains[0].getName()!=null){
						System.out.println("==今日天气");
						weatherDomain=weatherDomains[0];

						editor.putString("img_single", weatherDomain.getImg_single());
						editor.putString("temp", weatherDomain.getTemp());
						editor.putString("weather", weatherDomain.getWeather());
						editor.commit();

					}else{
						weatherDomain.setImg_single(sharedPreferences.getString("img_single", "1"));
						weatherDomain.setTemp(sharedPreferences.getString("temp", "23.5℃"));
						weatherDomain.setWeather(sharedPreferences.getString("weather", "Cloudy"));
					}
				}
				Message message = new Message();
				message.what=VIEW_UPDATE;
				mHandler.sendMessage(message);
			}
		};
		thread.start();
	}

	//自定义广播接受者
	class MyReceiver extends BroadcastReceiver{
		@Override
		public void onReceive(Context context, Intent intent) {
			//广播接收器
			Bundle bundle=intent.getExtras();
			//听广播的命令
			String readCmd=bundle.getString("readCmd");

			netstatus=bundle.getInt("netstatus");
			//根据接收的状态改变连接状态
			if(netstatus==1){
				if(pop_iv_netstatus!=null)
					pop_iv_netstatus.setImageResource(R.drawable.netstatus_conneting);
			}else{
				if(pop_iv_netstatus!=null)
					pop_iv_netstatus.setImageResource(R.drawable.netstatus_disconneted);
			}

			//
			if(readCmd!=null)
				manageReadCmd(readCmd);

		}

	}
	/**
	 *
	 * @param @param readCmd
	 * @Description 处理接收到的数据
	 * @return void
	 */
	private void manageReadCmd(String readCmd){
		int len=readCmd.length();
		readCmd=readCmd.toUpperCase();
		System.out.println("===manageReadCmd==="+readCmd);
		if(len==54){//01041B 23字节（pm 2B 温度 1B  湿度 1B  1-18通道亮度  ） checksum

			if(!isSend){
				Log.e("===isSend==", "111111111");
				return;
			}

			String checkSum=readCmd.substring(52, 54);
			String cmd=MyFunction.CheckSum(readCmd.substring(0, 52));
//			System.out.println(cmd.length());
			String checkSum1=cmd.substring(52, 54);
			if(checkSum1.equalsIgnoreCase(checkSum)&&readCmd.substring(0, 6).equalsIgnoreCase("01041B")){
				byte[] cmds=MyFunction.HexString2Bytes(readCmd);
				int pm=Integer.parseInt(readCmd.substring(7, 10),16);//PM
				int temp=Integer.parseInt(readCmd.substring(10, 12),16);//温度
				int humi=Integer.parseInt(readCmd.substring(12, 14),16);//湿度

				int leak=Integer.parseInt(readCmd.substring(50, 52),16);//第26个字节的最后一位      漏水检测值   0  1
				///====
				String str=Integer.toBinaryString(leak);
				str="00000000"+str;
				str=str.substring(str.length()-8, str.length());
				leak=Integer.parseInt(str.substring(7, 8));

				if(pop_tv_pm!=null)
					pop_tv_pm.setText(pm+" "+((pm>=0&&pm<=50)?"优":pm<=100?"良":pm<=150?"轻度":pm<=200?"中度":pm<=300?"重度":"严重"));
				if(pop_tv_roomtemp!=null)
					pop_tv_roomtemp.setText(temp+"");
				if(pop_tv_roomhumi!=null)
					pop_tv_roomhumi.setText(humi+"");

				editor.putInt("pm", pm);
				editor.putInt("htemp", temp);
				editor.putInt("humi", humi);

				editor.putInt("leak", leak);

				for (int i = 0; i <18; i++) {//通道亮度
					int bright=cmds[7+i]>=0?cmds[7+i]:(cmds[7+i]+256);
					editor.putInt("channel"+(i+1), bright>0?1:0);
				}
				editor.commit();
				((BaseBroadReceiverI) fragment).manageReadCmd(readCmd);
			}
		}else if(len==16){
			String checkSum=readCmd.substring(14, 16);
			String cmd=MyFunction.CheckSum(readCmd.substring(0, 14));
//			System.out.println(cmd.length());
			String checkSum1=cmd.substring(14, 16);
			if(checkSum1.equalsIgnoreCase(checkSum)&&readCmd.substring(0, 2).equalsIgnoreCase("F5")){
				byte[] cmds=MyFunction.HexString2Bytes(readCmd);
				//F5FFFFD3010D012B
				if((cmds[1]==0xFF-256)&&(cmds[3]==0xD3-256)){
					int channel=Integer.parseInt(readCmd.substring(10,12),16)+1;
					int l=Integer.parseInt(readCmd.substring(12, 14),16);

					int bright=MyFunction.DecToHex(l);

					System.out.println("bright=="+bright);
					editor.putInt("channel"+channel, bright>0?1:0);
					editor.commit();
					((BaseBroadReceiverI) fragment).manageReadCmd(readCmd);
				}
			}
		}
	}

	private ModelService mService = new ModelService();
	ServiceConnection connection = new ServiceConnection() {
		@Override
		public void onServiceDisconnected(ComponentName name) {
			// Toast.makeText(ServiceDemo03_Bind.this,
			// "这里是：onServiceDisconnected", 1000).show();
		}

		@Override
		public void onServiceConnected(ComponentName name, IBinder service) {
			// Toast.makeText(ServiceDemo03_Bind.this, "这里是：onServiceConnected",
			// 1000).show();
			System.out.println("onServiceConnected");
			ModelService.MyBind myBind = (MyBind) service;
			mService = myBind.getMyService();
			try {
				mService.initNet();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	};

	// 时间定时器
	CountDownTimer popTimer = new CountDownTimer(30000, 1000) {
		@Override
		public void onTick(long millisUntilFinished) {
//			System.out.println("==millisUntilFinished==="+millisUntilFinished);
		}
		@Override
		public void onFinish() {
//			Log.e("=====弹出pop窗口=====", "弹出pop窗口");
			if (null == mPopupWindow) {
				View layout = LayoutInflater.from(MainActivity.this).inflate(
						R.layout.pop_welcome, null);
				findPopView(layout);
				layout.setOnTouchListener(new View.OnTouchListener() {
					@Override
					public boolean onTouch(View v, MotionEvent event) {
						if (event.getAction() == MotionEvent.ACTION_DOWN) {
							mPopupWindow.dismiss();
							popTimer.cancel();
							popTimer.start();

						}
						return false;
					}
				});
				// 设置弹出部分和面积大小
				mPopupWindow = new PopupWindow(layout,
						LayoutParams.MATCH_PARENT, LayoutParams.MATCH_PARENT);
				// 设置动画弹出效果
				mPopupWindow.setAnimationStyle(R.style.PopupAnimation);
				mPopupWindow.setClippingEnabled(true);
			}
			//显示
			mPopupWindow.showAtLocation(getWindow().getDecorView(),
					Gravity.RIGHT | Gravity.TOP, 0, 0);
//			queryWeather();

		}
	};

	class TimeThread extends Thread {
		@Override
		public void run() {
			do {
				try {
					Thread.sleep(1000);
					Message msg = new Message();
					msg.what = TIME_UPDATE;
					mHandler.sendMessage(msg);
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
			} while (isTimeThread);
		}
	}

	class RequestThread extends Thread {
		@Override
		public void run() {
			do {
				try {
					Thread.sleep(6000);
//					Log.e("RequestThread", ""+isSend+"==isRequestThread="+isRequestThread);
					if(isSend){
						Message msg = new Message();
						msg.what = REQUESTSTATUS;
						mHandler.sendMessage(msg);
					}

				} catch (InterruptedException e) {
					e.printStackTrace();
				}
			} while (isRequestThread);
		}
	}


	private Handler mHandler = new Handler() {
		@Override
		public void handleMessage(Message msg) {
			super.handleMessage(msg);
			switch (msg.what) {
				case TIME_UPDATE:
//				System.out.println("更新时间==");
					Date now=new Date();
//				SimpleDateFormat dateFormat=new SimpleDateFormat("HH:mm:ss");
					SimpleDateFormat dateFormat2=new SimpleDateFormat("HH:mm");
					if (pop_tv_time != null)
						pop_tv_time.setText(dateFormat2.format(now));
					if (main_tv_time != null)
						main_tv_time.setText(dateFormat2.format(now));

					break;
				case VIEW_UPDATE:
//				System.out.println("更新天气=="+weatherDomain);
					try {
						InputStream is=MainActivity.this.getAssets().open("weather/a_"+weatherDomain.getImg_single()+".jpg");
						Bitmap bm=BitmapFactory.decodeStream(is);
						if(pop_iv_weat!=null)
							pop_iv_weat.setImageBitmap(bm);
					} catch (IOException e) {
						e.printStackTrace();
					}
					if(pop_tv_weattemp!=null)
						pop_tv_weattemp.setText(weatherDomain.getTemp());
					if(pop_tv_weat!=null)
						pop_tv_weat.setText(weatherDomain.getWeather());
					break;
				case REQUESTSTATUS:
					System.out.println("请求通道亮度=");
					ModelService.sendControlCmd(MyFunction.HexString2Bytes(ConstantUtil.MainConstants.REQUEST_CMD));
					break;
				default:
					break;
			}
		}
	};

}
