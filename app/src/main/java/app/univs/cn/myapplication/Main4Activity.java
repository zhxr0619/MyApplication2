package app.univs.cn.myapplication;

import android.app.Activity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;

import java.util.Calendar;

public class Main4Activity extends Activity implements View.OnClickListener{

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main4);

        findViewById(R.id.set).setOnClickListener(this);
        findViewById(R.id.verify).setOnClickListener(this);
    }
    public static final int MIN_CLICK_DELAY_TIME = 1000;
    private long lastClickTime = 0;
    @Override
    public void onClick(View v) {
        long currentTime = Calendar.getInstance().getTimeInMillis();
        if(currentTime-lastClickTime<MIN_CLICK_DELAY_TIME){
            Log.e("===","222222222");
            return;
        }
        lastClickTime = currentTime;

        switch (v.getId()){
            case R.id.set:
                Log.e("====","set====");
                break;
            case R.id.verify:
                Log.e("====","verify====");
                break;
        }
    }
}
