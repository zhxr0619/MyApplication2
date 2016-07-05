package com.by.dalitek.modelhouse.service;

import android.app.Service;
import android.content.Intent;
import android.os.Binder;
import android.os.IBinder;
import android.os.Message;
import android.util.Log;

import com.by.dalitek.modelhouse.ui.MainActivity;
import com.by.dalitek.modelhouse.util.ConstantUtil;
import com.by.dalitek.modelhouse.util.MyFunction;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.InetSocketAddress;
import java.net.Socket;
import java.net.SocketAddress;
import java.util.ArrayList;
import java.util.List;

public class ModelService extends Service {

	private static SocketAddress proxyAddress;

	private static Socket socket;

	private static OutputStream outputStresm;

	private static InputStream inputStresm;

	private static int connectTimeout = 1000; // 重连时间

	public final static byte CMD_START = 0x5A; // 命令开始标志

	public String CMD_5A = "5A"; // 命令开始标志

	public String CMD_F5 = "F5"; // 命令开始标志

	public String CMD_FA = "FA"; // 命令开始标志
	public String CMD_01 = "01"; // 命令开始标志

	private final static byte[] JUMP_CMD = new byte[] { CMD_START, 0, 0, 0, 0,
			0, 0, 166 - 256 };

	private static final int MESSAGE_RECEIVED_CMD = 1;

	private static final int MESSAGE_NET_CONNECTED = 2;

	private static final int MESSAGE_NET_DISCONNECTED = 3;

	private static boolean isDoConnecting = false;

	private static int listenCmdInterval = 1; // 监听命令的间隔时间

	// 猫眼
//	public static int flag_recat = 0;

	private static List<SendCmdThread> sendingCmdThreads = new ArrayList<SendCmdThread>();

	private String heartbeat = "5AFFFFFFFFFFFFAC";// 心跳
//	private String cateye = "F564FFFE13640033";// 猫眼命令

	private static boolean hasJumpFeedback = true;// 判断心跳是否正常

	private static int jumpTimeout = 10000;// 每隔6秒发送心跳

	public boolean flag_send = true;
	public boolean flag_listen = true;

	private static String tag="ModelService";

	@Override
	public IBinder onBind(Intent intent) {
		Log.e(tag, "service===onBind");
		System.out.println("flag_send=" + flag_send);
		return mBind;
	}

	@Override
	public boolean onUnbind(Intent intent) {
		Log.e(tag, "service===onUnbind");
		flag_send = false;
		flag_listen = false;
		return super.onUnbind(intent);

	}

	private MyBind mBind = new MyBind();
	public class MyBind extends Binder {
		public ModelService getMyService() {
			return ModelService.this;
		}
	}

	@Override
	public void onCreate() {
		Log.e(tag, "service===onCreate");
		super.onCreate();
	}

	@Override
	public int onStartCommand(Intent intent, int flags, int startId) {
		Log.e(tag, "service===onStartCommand");
		return super.onStartCommand(intent, flags, startId);
	}

	@Override
	public void onDestroy() {
		Log.e(tag, "service===onDestroy");
		super.onDestroy();
	}

	@Override
	public void onStart(Intent intent, int startId) {
		System.out.println("====onStart====");
		super.onStart(intent, startId);
		try {
			initNet();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public void initNet() throws IOException {
		Log.i(tag, "initNet");
		String str_ip = MainActivity.sharedPreferences.getString("ipaddress",ConstantUtil.IPADDRESS);
		int str_port = MainActivity.sharedPreferences.getInt("port", 2001);

		setProxy(str_ip, str_port);
		startSendJumpCmd();// 发送心跳包
		listenCmd();
	}

	/**
	 *
	 * @param
	 * @Description TODO
	 * @return void
	 */
	public void startSendJumpCmd() {

		new Thread() {
			public void run() {
				while (flag_send) {
//					Log.d(tag, "flag_send=="+flag_send);
					try {
						Thread.sleep(jumpTimeout);
						sendJump();
					} catch (Exception e) {
						try {
							connectProxy();
						} catch (IOException e1) {
						}
					}
				}

			}

			private void sendJump() throws IOException {
				Log.i(tag, "sendJump");
				if (!hasJumpFeedback) {
					// 上一次没有收到心跳反馈则断开网络重新连接
					Intent intent = new Intent();
					intent.setAction("android.intent.action.MY_RECEIVER");
					intent.putExtra("netstatus", 0);// 发送广播1为网络连接0为未连接
					sendBroadcast(intent);
					connectProxy();
				}

				hasJumpFeedback = false;
				outputStresm.write(JUMP_CMD);
				outputStresm.flush();

			}
		}.start();
	}

	/**
	 * 连接网络
	 *
	 * @param address
	 * @param port
	 * @throws IOException
	 */
	public static synchronized void setProxy(String address, int port)
			throws IOException {
		try {
			proxyAddress = new InetSocketAddress(address, port);
		} catch (Exception e) {
			throw new IOException();
		}
		connectProxy();
	}

	private static synchronized void connectProxy() throws IOException {
		if (isDoConnecting) {
			return;
		}

		isDoConnecting = true;
		if (socket != null) {
			// if (!socket.isInputShutdown()) {
			// socket.shutdownInput();
			// }
			// if (!socket.isOutputShutdown()) {
			// socket.shutdownOutput();
			// }
			if (!socket.isClosed()) {
				socket.close();
			}
			socket = null;
		}
		if (outputStresm != null) {
			outputStresm.close();
			outputStresm = null;
		}
		if (inputStresm != null) {
			inputStresm.close();
			inputStresm = null;
		}
		Log.i(tag,""+proxyAddress);
		if (proxyAddress != null) {
			new Thread() {
				@Override
				public void run() {
					try {
						Thread.sleep(1000);
					} catch (InterruptedException e1) {
					}
					try {
						socket = new Socket();
						socket.connect(proxyAddress, connectTimeout);

						outputStresm = socket.getOutputStream();
						inputStresm = socket.getInputStream();

						outputStresm.write(JUMP_CMD);
						outputStresm.flush();
					} catch (Exception e) {
						Message message = new Message();
						message.what = MESSAGE_NET_DISCONNECTED;
						// handler.sendMessage(message);
					} finally {
						isDoConnecting = false;
					}
				}
			}.start();
		} else {
			isDoConnecting = false;
		}
	}

	// 听命令
	public void listenCmd() {
		new Thread() {
			@Override
			public void run() {
				while (flag_listen) {
					try {
						Thread.sleep(listenCmdInterval);
						readCmd();
					} catch (InterruptedException e) {
					} catch (IOException e) {
						e.printStackTrace();
					}
				}
			}

			private synchronized void readCmd() throws IOException {
				if (inputStresm != null) {
					byte[] cmds = readCmd_Stresm(inputStresm);
					String ret = "";
					if(cmds!=null)

						if (cmds.length != 0) {
							//000000000000
							ret = MyFunction.bytesToHexString(cmds);

							if (ret.length() == 16) {
								ret = ret.toUpperCase();
								Log.i(tag, "ret=" + ret);
								String temp_head = ret.substring(0, 2);
								if (temp_head.equals(CMD_5A)) {
									if (ret.equals(heartbeat)) {
										hasJumpFeedback = true;
										Intent intent = new Intent();
										intent.setAction("android.intent.action.MY_RECEIVER");
										intent.putExtra("netstatus", 1);// 发送广播1为网络连接0为未连接
										sendBroadcast(intent);
									}

								} else if (temp_head.equals(CMD_F5)) {
									Intent intent = new Intent();
									intent.setAction("android.intent.action.MY_RECEIVER");
									intent.putExtra("netstatus", 1);// 发送广播1为网络连接0为未连接
									intent.putExtra("readCmd", ret);
									sendBroadcast(intent);
								} else if (temp_head.equals(CMD_FA)) {

								}
							}else if(ret.length()==54){
								if(ret.substring(0, 2).equals(CMD_01)){
									Intent intent = new Intent();
									intent.setAction("android.intent.action.MY_RECEIVER");
									intent.putExtra("netstatus", 1);// 发送广播1为网络连接0为未连接
									intent.putExtra("readCmd", ret);
									sendBroadcast(intent);
								}
							}
						}

				}
			}


		}.start();
	}

	// 读数据

	public static synchronized byte[] readCmd_Stresm(
			InputStream inputStresm) throws IOException {

		StringBuffer stringBuffer=new StringBuffer("");

		if (inputStresm != null&&socket!=null&&!socket.isClosed()) {
			int i = inputStresm.available();
			while (i > 0) {
//				System.out.println("readCmd_Stresm==len="+i);
				byte[] feedbackCmd = new byte[1024];// 定义有效字节数组长度
				ByteArrayOutputStream bos = new ByteArrayOutputStream();
				int len = 0;
				if ((len = inputStresm.read(feedbackCmd)) != -1) {
					bos.write(feedbackCmd, 0, len);
				}
				bos.close();
//				System.out.println("bos==="+MyFunction.bytesToHexString(bos.toByteArray()));
				stringBuffer.append(MyFunction.bytesToHexString(bos.toByteArray()))	;
				i = 0;
			}
		}
		return MyFunction.HexString2Bytes(stringBuffer.toString());
	}

	// 发送命令
	public static synchronized void sendControlCmd(final byte[] sendCmd) {

		SendCmdThread sendCmdThread = new SendCmdThread();
		sendCmdThread.setSendCmd(sendCmd);
		// sendCmdThread.setFeedbackCmd(calculateFeedback(sendCmd));
		sendingCmdThreads.add(sendCmdThread);
		sendCmdThread.start();
	}

	private static class SendCmdThread extends Thread {
		private byte[] sendCmd;
		private boolean hasFeedback;

		public SendCmdThread() {

		}

		public void setSendCmd(byte[] sendCmd) {
			this.sendCmd = sendCmd;
		}

		public void run() {
			try {
				outputStresm.write(sendCmd);
				outputStresm.flush();

				Thread.sleep(1000);
				if (hasFeedback) {
					// 收到了正确的反馈，就不用再次发送指令，
					// 否则重复发送指令
					return;
				}
			} catch (Exception e) {
				try {
					connectProxy();
				} catch (IOException e1) {
					e1.printStackTrace();
				}
			}

			// 若干次未能获得正确的反馈信息，停止发送命令
			sendingCmdThreads.remove(this);
			// Message message = new Message();
			// message.what = R.string.message_error_fail_to_send_cmd;
			// handler.sendMessage(message);
		}
	}
}
