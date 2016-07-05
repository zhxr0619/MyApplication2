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
import android.widget.RelativeLayout;

import com.by.dalitek.modelhouse.service.ModelService;
import com.by.dalitek.modelhouse.util.BaseBroadReceiverI;
import com.by.dalitek.modelhouse.util.ConstantUtil;
import com.by.dalitek.modelhouse.util.MyFunction;

import app.univs.cn.myapplication.R;

/**
 *
 * @author zhxr
 * @Description TODO
 * @Name EntertainmentFragment.java
 * @param
 * @time: 2015年2月11日 上午11:23:04
 */
public class EntertainmentFragment extends Fragment implements BaseBroadReceiverI{

	private String tag = "EntertainmentFragment";

	private Button enter_btn_music, enter_btn_tv;
	private RelativeLayout enter_layout_music, enter_layout_tv;

	// 背景音乐界面控件
	private Button music_btn_open, music_btn_off, music_btn_voiceadd,
			music_btn_voicereduce;// 开、关、音量加、音量减
	private Button music_btn_fm, music_btn_mp3, music_btn_pre, music_btn_pause,
			music_btn_next, music_btn_mute;// 输入源、上一首、暂停|播放、下一首、静音

	// 电视界面控件
	private Button tv_btn_open, tv_btn_off, tv_btn_voiceadd,
			tv_btn_voicereduce, tv_btn_channeladd, tv_btn_channelreduce;// 开、关、音量加、音量减、频道加、频道减
	private Button tv_btn_source, tv_btn_up, tv_btn_mute;// 输入源、上、静音
	private Button tv_btn_left, tv_btn_ok, tv_btn_right;// 左、ok、右
	private Button tv_btn_menu, tv_btn_down, tv_btn_back;// 菜单、下、返回

	private int layout_flag = 1;// 当前显示界面标识

	private static final int MUSICLAYOUT_FLAG = 2;
	private static final int TVLAYOUT_FLAG = 1;
	private int WHITE = 0xffffffff;
	private int BLACK = 0xff000000;

	@Override
	public void onAttach(Activity activity) {
		super.onAttach(activity);
	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
							 Bundle savedInstanceState) {
		View view = inflater.inflate(R.layout.fragment_enter, container, false);
		findEnterView(view);
		return view;
	}

	/**
	 *
	 * @param @param view
	 * @Description 初始化娱乐界面
	 * @return void
	 */
	private void findEnterView(View view) {
		enter_btn_music = (Button) view.findViewById(R.id.enter_btn_music);
		enter_btn_tv = (Button) view.findViewById(R.id.enter_btn_tv);

		enter_layout_music = (RelativeLayout) view
				.findViewById(R.id.enter_layout_music);
		enter_layout_tv = (RelativeLayout) view
				.findViewById(R.id.enter_layout_tv);

		enter_btn_music.setOnClickListener(new EnterBtnClickListener(
				MUSICLAYOUT_FLAG));
		enter_btn_tv
				.setOnClickListener(new EnterBtnClickListener(TVLAYOUT_FLAG));

		/** 背景音樂控件 */
		findMusicView(view);
		/** 電視界面控件 */
		findTvView(view);

	}

	/** 初始化背景音樂界面控件 */
	private void findMusicView(View view) {
		music_btn_open = (Button) view.findViewById(R.id.music_btn_open);
		music_btn_off = (Button) view.findViewById(R.id.music_btn_off);
		music_btn_voiceadd = (Button) view
				.findViewById(R.id.music_btn_voiceadd);
		music_btn_voicereduce = (Button) view
				.findViewById(R.id.music_btn_voicereduce);
		music_btn_fm = (Button) view.findViewById(R.id.music_btn_fm);
		music_btn_mp3 = (Button) view.findViewById(R.id.music_btn_mp3);
		music_btn_next = (Button) view.findViewById(R.id.music_btn_next);
		music_btn_pause = (Button) view.findViewById(R.id.music_btn_pause);
		music_btn_pre = (Button) view.findViewById(R.id.music_btn_pre);
		music_btn_mute = (Button) view.findViewById(R.id.music_btn_mute);

		music_btn_open.setOnClickListener(new MusicOnClickListener(1));
		music_btn_off.setOnClickListener(new MusicOnClickListener(2));
		music_btn_voiceadd.setOnClickListener(new MusicOnClickListener(3));
		music_btn_voicereduce.setOnClickListener(new MusicOnClickListener(4));
		music_btn_fm.setOnClickListener(new MusicOnClickListener(5));
		music_btn_mp3.setOnClickListener(new MusicOnClickListener(6));
		music_btn_pre.setOnClickListener(new MusicOnClickListener(7));
		music_btn_pause.setOnClickListener(new MusicOnClickListener(8));
		music_btn_next.setOnClickListener(new MusicOnClickListener(9));
		music_btn_mute.setOnClickListener(new MusicOnClickListener(10));

	}
	/**背景音乐界面控件点击事件*/
	class MusicOnClickListener implements OnClickListener {
		private int flag;

		public MusicOnClickListener(int flag) {
			this.flag = flag;
		}

		@Override
		public void onClick(View v) {
			switch (flag) {
				case 1:
					ModelService
							.sendControlCmd(MyFunction
									.HexString2Bytes(ConstantUtil.EnterConstants.MUSIC_OPEN_CMD));
					break;
				case 2:
					ModelService
							.sendControlCmd(MyFunction
									.HexString2Bytes(ConstantUtil.EnterConstants.MUSIC_CLOSE_CMD));
					break;
				case 3:
					ModelService
							.sendControlCmd(MyFunction
									.HexString2Bytes(ConstantUtil.EnterConstants.MUSIC_VOICEADD_CMD));
					break;
				case 4:
					ModelService
							.sendControlCmd(MyFunction
									.HexString2Bytes(ConstantUtil.EnterConstants.MUSIC_VOICEREDUCE_CMD));
					break;
				case 5:
					ModelService
							.sendControlCmd(MyFunction
									.HexString2Bytes(ConstantUtil.EnterConstants.MUSIC_SOURCE_FM_CMD));
					break;
				case 6:
					ModelService
							.sendControlCmd(MyFunction
									.HexString2Bytes(ConstantUtil.EnterConstants.MUSIC_SOURCE_MP3_CMD));
					break;
				case 7:
					ModelService
							.sendControlCmd(MyFunction
									.HexString2Bytes(ConstantUtil.EnterConstants.MUSIC_PRE_CMD));
					break;
				case 8:
					ModelService
							.sendControlCmd(MyFunction
									.HexString2Bytes(ConstantUtil.EnterConstants.MUSIC_PLAY_CMD));
					break;
				case 9:
					ModelService
							.sendControlCmd(MyFunction
									.HexString2Bytes(ConstantUtil.EnterConstants.MUSIC_NEXT_CMD));
					break;
				case 10:
					ModelService
							.sendControlCmd(MyFunction
									.HexString2Bytes(ConstantUtil.EnterConstants.MUSIC_MUTE_CMD));
					break;

				default:
					break;
			}

		}
	}

	/** 初始化電視界面控件 */
	private void findTvView(View view) {
		tv_btn_open = (Button) view.findViewById(R.id.tv_btn_open);
		tv_btn_off = (Button) view.findViewById(R.id.tv_btn_off);
		tv_btn_voiceadd = (Button) view.findViewById(R.id.tv_btn_voiceadd);
		tv_btn_voicereduce = (Button) view
				.findViewById(R.id.tv_btn_voicereduce);
		tv_btn_channeladd = (Button) view.findViewById(R.id.tv_btn_channeladd);
		tv_btn_channelreduce = (Button) view
				.findViewById(R.id.tv_btn_channelreduce);
		tv_btn_source = (Button) view.findViewById(R.id.tv_btn_source);
		tv_btn_up = (Button) view.findViewById(R.id.tv_btn_up);
		tv_btn_mute = (Button) view.findViewById(R.id.tv_btn_mute);
		tv_btn_left = (Button) view.findViewById(R.id.tv_btn_left);
		tv_btn_ok = (Button) view.findViewById(R.id.tv_btn_ok);
		tv_btn_right = (Button) view.findViewById(R.id.tv_btn_right);
		tv_btn_menu = (Button) view.findViewById(R.id.tv_btn_menu);
		tv_btn_down = (Button) view.findViewById(R.id.tv_btn_down);
		tv_btn_back = (Button) view.findViewById(R.id.tv_btn_back);

		tv_btn_open.setOnClickListener(new TvOnClickListener(1));
		tv_btn_off.setOnClickListener(new TvOnClickListener(2));
//		tv_btn_voiceadd.setOnClickListener(new TvOnClickListener(3));
//		tv_btn_voicereduce.setOnClickListener(new TvOnClickListener(1));
//		tv_btn_channeladd.setOnClickListener(new TvOnClickListener(1));
//		tv_btn_channelreduce.setOnClickListener(new TvOnClickListener(1));
//		tv_btn_source.setOnClickListener(new TvOnClickListener(1));
//		tv_btn_up.setOnClickListener(new TvOnClickListener(1));
//		tv_btn_mute.setOnClickListener(new TvOnClickListener(1));
//		tv_btn_left.setOnClickListener(new TvOnClickListener(1));
//		tv_btn_ok.setOnClickListener(new TvOnClickListener(1));
//		tv_btn_right.setOnClickListener(new TvOnClickListener(1));
//		tv_btn_menu.setOnClickListener(new TvOnClickListener(1));
//		tv_btn_down.setOnClickListener(new TvOnClickListener(1));
//		tv_btn_back.setOnClickListener(new TvOnClickListener(1));

	}

	/*
	 * 电视界面控件点击事件
	 */
	class TvOnClickListener implements OnClickListener {

		private int flag;

		public TvOnClickListener(int flag) {
			this.flag = flag;
		}

		@Override
		public void onClick(View v) {
			Log.i(tag, "TvOnClickListener=" + v.getId());
			switch (flag) {
				case 1:
					ModelService
							.sendControlCmd(MyFunction
									.HexString2Bytes(ConstantUtil.EnterConstants.TV_OPEN_CMD));
					break;
				case 2:
					ModelService
							.sendControlCmd(MyFunction
									.HexString2Bytes(ConstantUtil.EnterConstants.TV_CLOSE_CMD));
					break;

				default:
					break;
			}

		}
	}

	/*
	 * 界面 导航栏按钮点击事件
	 */
	class EnterBtnClickListener implements OnClickListener {
		private int flag = 1;

		public EnterBtnClickListener(int flag) {
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
	 * 设置导航栏按钮状态
	 */
	private void setTopButton() {
		enter_layout_music.setVisibility(View.GONE);
		enter_layout_tv.setVisibility(View.GONE);

		enter_btn_music.setBackgroundResource(R.drawable.music_normal);
		enter_btn_tv.setBackgroundResource(R.drawable.tv_normal);

		switch (layout_flag) {
			case MUSICLAYOUT_FLAG:
				enter_layout_music.setVisibility(View.VISIBLE);
				enter_btn_music.setBackgroundResource(R.drawable.music_selected);
				break;
			case TVLAYOUT_FLAG:
				enter_layout_tv.setVisibility(View.VISIBLE);
				enter_btn_tv.setBackgroundResource(R.drawable.tv_selected);
				break;
			default:
				break;
		}
	}

	@Override
	public void manageReadCmd(String readCmd) {
		// TODO Auto-generated method stub
//		Log.d(tag, readCmd);
	}
}
