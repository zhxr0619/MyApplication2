package com.miloisbadboy;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.provider.Settings;
import android.telephony.TelephonyManager;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.webkit.WebSettings;
import android.widget.Button;

import java.io.UnsupportedEncodingException;
import java.util.UUID;

import app.univs.cn.myapplication.R;

public class PullToRefreshViewActivity extends Activity implements OnClickListener{
    Button listviewBtn;
    Button gridviewBtn;
    Button scrollviewBtn;
    
	@Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main);
        init();
    }
	private void init(){
		listviewBtn = (Button)findViewById(R.id.test_listview_btn);
		gridviewBtn = (Button)findViewById(R.id.test_gridview_btn);
		scrollviewBtn = (Button)findViewById(R.id.test_scrollview_btn);
		
		listviewBtn.setOnClickListener(this);
		gridviewBtn.setOnClickListener(this);
		scrollviewBtn.setOnClickListener(this);

		getStringUserAgent();
	}
	@Override
	public void onClick(View v) {
		if(v == listviewBtn){
			Intent intent = new Intent(this,TestListView.class);
			startActivity(intent);
		}else if(v == gridviewBtn){
			Intent intent = new Intent(this,TestGridView.class);
			startActivity(intent);
		}else if(v == scrollviewBtn){
			Intent intent = new Intent(this,TestScrollView.class);
			startActivity(intent);
		}
	}

	private void getStringUserAgent(){

		Log.e("getDefaultUserAgent", WebSettings.getDefaultUserAgent(this));

		String userAgent =  System.getProperty("http.agent");

//		String userAgent = AndroidPlatform.getUAFromProperties();
		Log.i("useragent",userAgent);

		Log.e("deviceId",getUDID(this));
	}

	protected static String uuid;

	public static String getUDID(Context context) {


				final String androidId = Settings.Secure.getString(context.getContentResolver(), Settings.Secure.ANDROID_ID);
				// Use the Android ID unless it's broken, in which case
				// fallback on deviceId,
				// unless it's not available, then fallback on a random
				// number which we store
				// to a prefs file
				try {
					if (!"9774d56d682e549c".equals(androidId)) {
						uuid = UUID.nameUUIDFromBytes(androidId.getBytes("utf8")).toString();
					} else {
						final String deviceId = ((TelephonyManager) context.getSystemService(Context.TELEPHONY_SERVICE)).getDeviceId();
						uuid = deviceId != null ? UUID.nameUUIDFromBytes(deviceId.getBytes("utf8")).toString() : UUID.randomUUID().toString();
					}
				} catch (UnsupportedEncodingException e) {
					throw new RuntimeException(e);
				}


		return uuid;
	}
}