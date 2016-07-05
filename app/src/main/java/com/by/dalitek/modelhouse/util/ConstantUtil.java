package com.by.dalitek.modelhouse.util;
/**
 *
 * @author zhxr
 * @Description 常量
 * @Name ConstantUtil.java
 * @param
 * @time: 2015年2月12日 上午10:29:00
 */
public class ConstantUtil {

	public static final String CONFIG_FILE_NAME="config";

	public static final String IPADDRESS="192.168.2.28";//服务器地址 192.168.0.149
	public static final int PORT=2001;//端口号

	//主界面常量
	public static class MainConstants{

		public static final String REQUEST_CMD="01031B0001001BC5";

		public static final String WELCOME_CMD="F5FFFFFE0D64009E";//欢迎模式
		public static final String LEAVE_CMD1="F5FFFFFE086400A3";//离家模式 1
		public static final String LEAVE_CMD2="F5FDFFFE016400AC";//离家模式 2
	}
	//环境界面常量
	public static class EnvirConstants{
		public static final int AIR_BOX=1;//空调box号

		//煤气
		public static final String GAS_CLOSE_CMD="F501FFFE006400";//关阀
		public static final String GAS_WIND_CMD="F501FFFE006400";//排风

		//PM
		public static final String PM_OPEN_CMD="F501FFFE006400";//PM 开
		public static final String PM_CLOSE_CMD="F501FFFE006400";//PM 关
		//PM 风速
		public static final String PM_LOW_CMD="F501FFFE006400";//
		public static final String PM_MIDDLE_CMD="F501FFFE006400";
		public static final String PM_HIGH_CMD="F501FFFE006400";
	}
	//娱乐界面常量
	public static class EnterConstants{
		//背景音乐
		public static final String MUSIC_OPEN_CMD="F5FDFFFE006400AD";
		public static final String MUSIC_CLOSE_CMD="F5FDFFFE016400AC";
		public static final String MUSIC_VOICEADD_CMD="F5FDFFFE096400A4";//音量加
		public static final String MUSIC_VOICEREDUCE_CMD="F5FDFFFE086400A5";//音量减
		//输入源切换
		public static final String MUSIC_SOURCE_MP3_CMD="F5FDFFFE036400AA";
		public static final String MUSIC_SOURCE_FM_CMD="F5FDFFFE026400AB";

		public static final String MUSIC_PRE_CMD="F5FDFFFE046400A9";//上一曲
		public static final String MUSIC_NEXT_CMD="F5FDFFFE056400A8";//下一曲
		//		public static final String MUSIC_PAUSE_CMD="F501FFFE006400";//暂停
		public static final String MUSIC_PLAY_CMD="F5FDFFFE066400A7";//播放 暂停
		public static final String MUSIC_MUTE_CMD="F5FDFFFE076400A6";//静音 取消

		//电视
		public static final String TV_OPEN_CMD="F5FCFFFE006400AE";
		public static final String TV_CLOSE_CMD="F5FCFFFE016400AD";


		public static final String TV_VOICEADD_CMD="F501FFFE006400";//音量加
		public static final String TV_VOICEREDUCE_CMD="F501FFFE006400";//音量减
		public static final String TV_CHANNELADD_CMD="F501FFFE006400";//频道加
		public static final String TV_CHANNELREDUCE_CMD="F501FFFE006400";//频道减
		//输入源切换
		public static final String TV_SOURCE_1_CMD="F501FFFE006400";
		public static final String TV_SOURCE_2_CMD="F501FFFE006400";
		public static final String TV_UP_CMD="F501FFFE006400";//上
		public static final String TV_DOWN_CMD="F501FFFE006400";//下
		public static final String TV_LEFT_CMD="F501FFFE006400";//左
		public static final String TV_RIGHT_CMD="F501FFFE006400";//右
		public static final String TV_OK_CMD="F501FFFE006400";//OK
		public static final String TV_MUTE_CMD="F501FFFE006400";//静音
		public static final String TV_MENU_CMD="F501FFFE006400";//菜单
		public static final String TV_BACK_CMD="F501FFFE006400";//返回
	}

	//遮阳界面常量
	public static class SunConstants{
		public static final String curtain_open_cmd="F5FFFFDB0100FF32";//
		public static final String curtain_pause_cmd="F5FFFFDA0100FF33";//
		public static final String curtain_off_cmd="F5FFFFDC0100FF31";//

//		//百叶窗  开停      关停
//		public static final String curtain_open_cmd1="F5FFFFDB0100FF32";//
//		public static final String curtain_open_cmd2="F5FFFFDA0100FF33";//
//		public static final String curtain_off_cmd1="F5FFFFDC0100FF31";//
//		public static final String curtain_off_cmd2="F5FFFFDA0100FF33";//
//		//百叶窗   开   关
//		public static final String glass_open_cmd="F5FFFFDB0100FF32";//
//		public static final String glass_off_cmd="F5FFFFDC0100FF31";//


		//纱帘
		public static final String blind_open_cmd="F5FFFFDB0106FF2C";//
		public static final String blind_off_cmd="F5FFFFDC0106FF2B";//
		public static final String blind_pause_cmd="F5FFFFDA0106FF2D";//
		//投影
		public static final String shadow_open_cmd="F5FFFFDB0108FF2A";//
		public static final String shadow_pause_cmd="F5FFFFDA0108FF2B";//
		public static final String shadow_off_cmd="F5FFFFDC0108FF29";//

	}

	//灯光界面常量
	public static class LightConstants{
		//餐厅通道按钮
		public static final String[] RESTAURANT_CHANNEL_NAMES={ "走道筒灯", "餐桌吊灯", "吧台灯", "橱柜灯" ,"雾化玻璃"};
		public static final String[] RESTAURANT_CHANNEL_OPENS = { "F5FFFFD301100128", "F5FFFFD301110127",
				"F5FFFFD3010A012E", "F5FFFFD30109012F" ,"F5FFFFD301030135"};
		public static final String[] RESTAURANT_CHANNEL_OFFS = { "F5FFFFD30110FF2A", "F5FFFFD30111FF29",
				"F5FFFFD3010AFF30", "F5FFFFD30109FF31","F5FFFFD30103FF37" };
		//餐厅场景按钮
		public static final String[] RESTAURANT_SCENE_NAMES={  };
		public static final String[] RESTAURANT_SCENE_OPENS = {  };
		public static final String[] RESTAURANT_SCENE_OFFS = { };
		//卧室通道
		public static final String[] BEDROOM_CHANNEL_NAMES={ "门口筒灯", "床头筒灯", "吸顶灯" };
		public static final String[] BEDROOM_CHANNEL_OPENS = { "F5FFFFD3010C012C", "F5FFFFD3010B012D",
				"F5FFFFD3010D012B"};
		public static final String[] BEDROOM_CHANNEL_OFFS = { "F5FFFFD3010CFF2E", "F5FFFFD3010BFF2F",
				"F5FFFFD3010DFF2D"};
		//卧室场景
		public static final String[] BEDROOM_SCENE_NAMES={};
		public static final String[] BEDROOM_SCENE_OPENS={};
		public static final String[] BEDROOM_SCENE_OFFS={};

		//客厅通道
		public static final String[] PARLOUR_CHANNEL_NAMES= { "花灯", "落地灯","灯槽","筒灯"   };
		public static final String[] PARLOUR_CHANNEL_OPENS = { "F5FFFFD301050133", "F5FFFFD301040134",
				"F5FFFFD3010E012A", "F5FFFFD3010F0129" };
		public static final String[] PARLOUR_CHANNEL_OFFS = { "F5FFFFD30105FF35", "F5FFFFD30104FF36",
				"F5FFFFD3010EFF2C", "F5FFFFD3010FFF2B" };
		//客厅场景模式
		public static final String[] PARLOUR_SCENE_NAMES={ "聚会", "电视", "阅读","温馨"};
		public static final String[] PARLOUR_SCENE_OPENS = { "F5FFFFFE026400A9", "F5FFFFFE0A6400A1",
				"F5FFFFFE0B6400A0", "F5FFFFFE0C64009F"};
		public static final String[] PARLOUR_SCENE_OFFS = {"","","","",""};

		//会议室通道
		public static final String[] MEET_CHANNEL_NAMES={"吊灯","射灯"};
		public static final String[] MEET_CHANNEL_OPENS={"F5FFFFD301010137","F5FFFFD301020136"};
		public static final String[] MEET_CHANNEL_OFFS={"F5FFFFD30101FF39","F5FFFFD30102FF38"};
		//会议室场景模式
		public static final String[] MEET_SCENE_NAMES={ "工作", "会议", "投影","放映","清洁","全关"};
		public static final String[] MEET_SCENE_OPENS = { "F5FFFFFE096400A2", "F5FFFFFE016400AA",
				"F5FFFFFE046400A7", "F5FFFFFE056400A6","F5FFFFFE92640019","F5FFFFFE076400A4"};
		public static final String[] MEET_SCENE_OFFS = {"","","","","",""};

	}


}
