package app.univs.cn.myapplication;

import android.app.Activity;
import android.os.Bundle;
import android.util.Log;

import java.text.SimpleDateFormat;
import java.util.Date;

public class TestActivity extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_test);


        try {
            String dateStr="20151221114125";
            SimpleDateFormat sdf1 = new SimpleDateFormat("yyyyMMddhhmmss");
            Date date = (Date) sdf1.parse(dateStr);
            SimpleDateFormat sdf2 = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
            String str=sdf2.format(date);
            Log.e("TestActivity",str);
        }catch (Exception e){
            e.printStackTrace();
        }


    }



}
