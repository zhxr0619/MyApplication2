package app.univs.cn.myapplication;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.support.v4.app.Fragment;
import android.widget.TextView;


public class TabAFm extends Fragment {

    private String tag="TabAfm";
    private View currentView;

    private Context mContext;
    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        Log.d(tag,"onAttach");
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        Log.d(tag,"onCreateView");
        currentView = inflater.inflate(R.layout.tab_a, container, false);
        mContext=getActivity();
        return currentView;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        Log.d(tag,"onActivityCreated");

        ((TextView) getView().findViewById(R.id.tv)).setText(tag);
    }
    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        init();

    }
    private void init(){
        currentView.findViewById(R.id.tv).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//                sendSMS();
                sendMail("共享软件");
            }
        });
    }

    @Override
    public void onStart() {
        super.onStart();
        Log.d(tag,"onStart");
    }

    @Override
    public void onResume() {
        super.onResume();
        Log.d(tag,"onResume");
    }

    @Override
    public void onPause() {
        super.onPause();
        Log.d(tag,"onPause");
    }

    @Override
    public void onStop() {
        super.onStop();
        Log.d(tag,"onStop");
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        Log.d(tag,"onDestroyView");
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        Log.d(tag,"onDestroy");
    }

    @Override
    public void onDetach() {
        super.onDetach();
        Log.d(tag,"onDetach");
    }

    //发短信
    private   void  sendSMS(){
        Uri smsToUri = Uri.parse( "smsto:" );
        Intent sendIntent =  new  Intent(Intent.ACTION_VIEW, smsToUri);
        //sendIntent.putExtra("address", "123456"); // 电话号码，这行去掉的话，默认就没有电话
        sendIntent.putExtra( "sms_body" ,  "我要共享这个软件" );
        sendIntent.setType( "vnd.android-dir/mms-sms" );
        startActivityForResult(sendIntent, 1002 );
    }

    //发邮件
    private   void  sendMail(String emailBody){
        Intent email =  new  Intent(android.content.Intent.ACTION_SEND);
        email.setType( "plain/text" );
        String  emailSubject =  "共享软件" ;

        //设置邮件默认地址
        // email.putExtra(android.content.Intent.EXTRA_EMAIL, emailReciver);
        //设置邮件默认标题
        email.putExtra(android.content.Intent.EXTRA_SUBJECT, emailSubject);
        //设置要默认发送的内容
        email.putExtra(android.content.Intent.EXTRA_TEXT, emailBody);
        //调用系统的邮件系统
        startActivityForResult(Intent.createChooser(email,  "请选择邮件发送软件" ), 1001 );
    }
}


