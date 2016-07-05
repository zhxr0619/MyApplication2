package com.by.dalitek.modelhouse.receiver;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;

import com.by.dalitek.modelhouse.ui.MainActivity;

/**
 * 开机自启动
 *
 * @author zhxr
 *
 */
public class ModelReceiver extends BroadcastReceiver {

	@Override
	public void onReceive(Context context, Intent intent) {
		if ("android.intent.action.BOOT_COMPLETED".equals(intent.getAction())) {
			Intent forword = new Intent(context, MainActivity.class);
			forword.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
			context.startActivity(forword);
		}

	}

}
