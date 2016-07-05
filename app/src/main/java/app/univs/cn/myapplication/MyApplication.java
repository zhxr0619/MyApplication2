package app.univs.cn.myapplication;

import android.app.Application;
import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.widget.Toast;

import com.tandong.sa.json.Gson;
import com.umeng.message.PushAgent;
import com.umeng.message.UmengMessageHandler;
import com.umeng.message.UmengNotificationClickHandler;
import com.umeng.message.entity.UMessage;

import java.util.Map;


/**
 * @author
 * @Date
 * @Name MyApplication.java
 * @Descrition 应用
 */
public class MyApplication extends Application {
    private static Context mContext;
    public static String version = null;
    public static String token = null;
    public static String versionName = null;
    public static boolean downLodingFlag;

    private PushAgent mPushAgent;

    static {
        downLodingFlag = false;
    }

    @Override
    public void onTerminate() {
        super.onTerminate();
    }

    @Override
    public void onCreate() {
        super.onCreate();
        this.mContext = getApplicationContext();


//        setManageUmengMsg();
    }

    public static synchronized MyApplication getContext() {
        return (MyApplication) mContext;
    }

    private void setManageUmengMsg(){

        mPushAgent= PushAgent.getInstance(mContext);

        UmengMessageHandler messageHandler = new UmengMessageHandler(){
            @Override
            public void dealWithNotificationMessage(Context context, UMessage uMessage) {
                Log.d("dealWithNotificationM",uMessage.text);
                Log.d("umessage",new Gson().toJson(uMessage));
                Map<String,String> extras=uMessage.extra;
                for (String key:extras.keySet()){
                    Log.d(key,extras.get(key));
                }
            }

            @Override
            public void dealWithCustomMessage(Context context, UMessage uMessage) {
                Log.d("dealWithCustomMessage",uMessage.text);
            }
        };

        /**
         * 该Handler是在BroadcastReceiver中被调用，故
         * 如果需启动Activity，需添加Intent.FLAG_ACTIVITY_NEW_TASK
         * 参考集成文档的1.6.2
         * [url=http://dev.umeng.com/push/android/integration#1_6_2]http://dev.umeng.com/push/android/integration#1_6_2[/url]
         * */
        UmengNotificationClickHandler notificationClickHandler = new UmengNotificationClickHandler(){
            //点击通知的自定义行为
            @Override
            public void dealWithCustomAction(Context context, UMessage msg) {
                Toast.makeText(context, msg.custom, Toast.LENGTH_LONG).show();

                Intent intent=new Intent();

            }
        };

        mPushAgent.setMessageHandler(messageHandler);

    }

}
