package app.univs.cn.myapplication;

import android.app.Activity;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.content.pm.ResolveInfo;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import me.leolin.shortcutbadger.ShortcutBadger;

public class TestIconActivity extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_test_icon);


        // 在应用的主Activity onCreate() 函数中开启推送服务
//        PushAgent mPushAgent = PushAgent.getInstance(this);
//        mPushAgent.enable();
//
//        final String[] umeng_device_token = {UmengRegistrar.getRegistrationId(this)};
//        Log.i("umeng_device_token", umeng_device_token[0]);


        final EditText numInput = (EditText) findViewById(R.id.numInput);

        Button button = (Button) findViewById(R.id.btnSetBadge);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int badgeCount = 0;
                try {
                    badgeCount = Integer.parseInt(numInput.getText().toString());
                } catch (NumberFormatException e) {
                    Toast.makeText(getApplicationContext(), "Error input", Toast.LENGTH_SHORT).show();
                }

                boolean success = ShortcutBadger.applyCount(TestIconActivity.this, badgeCount);

                Toast.makeText(getApplicationContext(), "Set count=" + badgeCount + ", success=" + success, Toast.LENGTH_SHORT).show();

                showBadge(badgeCount);
            }
        });

        Button removeBadgeBtn = (Button) findViewById(R.id.btnRemoveBadge);
        removeBadgeBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                boolean success = ShortcutBadger.removeCount(TestIconActivity.this);

                Toast.makeText(getApplicationContext(), "success=" + success, Toast.LENGTH_SHORT).show();
                if(badgeView!=null){
                    Log.e("===","==========");
                    badgeView.hide();
                }
            }
        });


        //find the home launcher Package
        Intent intent = new Intent(Intent.ACTION_MAIN);
        intent.addCategory(Intent.CATEGORY_HOME);
        ResolveInfo resolveInfo = getPackageManager().resolveActivity(intent, PackageManager.MATCH_DEFAULT_ONLY);
        String currentHomePackage = resolveInfo.activityInfo.packageName;

        TextView textViewHomePackage = (TextView) findViewById(R.id.textViewHomePackage);
        textViewHomePackage.setText("launcher:" + currentHomePackage);


    }

    private BadgeView badgeView;
    private View home_event_icon2;
    public  void showBadge(int badgeCount){
        if(badgeView!=null){
            badgeView.hide();
        }
        View home_event_icon2=findViewById(R.id.home_event_icon2);
        badgeView = new BadgeView(this, home_event_icon2);
        badgeView.setText(badgeCount+"");
        badgeView.show();

        Log.e("HomeFragment","showBadge");
    }
}
