package app.univs.cn.myapplication;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.util.Log;
import android.widget.RadioGroup;

import java.util.ArrayList;
import java.util.List;

public class Main3Activity extends FragmentActivity {
    /**
     * Called when the activity is first created.
     */
    private RadioGroup rgs;
    public List<Fragment> fragments = new ArrayList<Fragment>();

    public String hello = "hello ";

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main3);

        fragments.add(new TabAFm());
        fragments.add(new TabBFm());
        fragments.add(new TabCFm());
        fragments.add(new TabDFm());
        fragments.add(new TabEFm());


        rgs = (RadioGroup) findViewById(R.id.tabs_rg);

        FragmentTabAdapter tabAdapter = new FragmentTabAdapter(this, fragments, R.id.tab_content, rgs);
        tabAdapter.setOnRgsExtraCheckedChangedListener(new FragmentTabAdapter.OnRgsExtraCheckedChangedListener(){
            @Override
            public void OnRgsExtraCheckedChanged(RadioGroup radioGroup, int checkedId, int index) {
                System.out.println("Extra---- " + index + " checked!!! ");
            }
        });

        String[] datas={"2","20.","20.0","20.00","200.0","200.00","2000.0","2000.00","200000.0","2000000.0"};
        for (int i=0;i<datas.length;i++){
            Log.e("data",StringUtils.fmtMicrometer2(datas[i]));
        }
        System.out.println("++++++++++");
        String[] datas2={"0.0054","20.","20.0","20.06","200.0","200.00","2000.0","2000.00","200000.0","2000000.0"};
        for (int i=0;i<datas2.length;i++){
            Log.e("data",StringUtils.fmtMicrometer2(datas2[i]));
        }

        TimerTest.test();
//        ProductAndConsumer.productAndC();
//        WriteAndReadAction.writeAndRead();

//        Intent data=new Intent(Intent.ACTION_SENDTO);
//        data.setData(Uri.parse("mailto:way.ping.li@gmail.com"));
//        data.putExtra(Intent.EXTRA_SUBJECT, "这是标题");
//        data.putExtra(Intent.EXTRA_TEXT, "这是内容");
//        startActivity(data);

//        SenderRunnable senderRunnable = new SenderRunnable(
//                "xxxxxxxxx@163.com", "xxxxxxxxx");
//        senderRunnable.setMail("this is the test subject",
//                "this is the test body", "xxxxxxxxx@gmail.com","/mnt/sdcard/test.txt");
//        new Thread(senderRunnable).start();
    }


}

class SenderRunnable implements Runnable {


    private String user;
    private String password;
    private String subject;
    private String body;
    private String receiver;
    private MailSender sender;
    private String attachment;


    public SenderRunnable(String user, String password) {
        this.user = user;
        this.password = password;
        sender = new MailSender(user, password);
        String mailhost=user.substring(user.lastIndexOf("@")+1, user.lastIndexOf("."));
        if(!mailhost.equals("gmail")){
            mailhost="smtp."+mailhost+".com";
            Log.i("hello", mailhost);
            sender.setMailhost(mailhost);
        }
    }


    public void setMail(String subject, String body, String receiver,String attachment) {
        this.subject = subject;
        this.body = body;
        this.receiver = receiver;
        this.attachment=attachment;
    }


    public void run() {
// TODO Auto-generated method stub
        try {
            sender.sendMail(subject, body, user, receiver,attachment);
        } catch (Exception e) {
// TODO Auto-generated catch block
            e.printStackTrace();
        }
    }

}
