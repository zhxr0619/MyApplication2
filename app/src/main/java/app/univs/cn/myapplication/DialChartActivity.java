package app.univs.cn.myapplication;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Color;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.view.ViewGroup.LayoutParams;
import android.widget.LinearLayout;

import org.achartengine.ChartFactory;
import org.achartengine.GraphicalView;
import org.achartengine.model.CategorySeries;
import org.achartengine.renderer.DialRenderer;
import org.achartengine.renderer.DialRenderer.Type;
import org.achartengine.renderer.SimpleSeriesRenderer;

import java.util.Random;
import java.util.Timer;
import java.util.TimerTask;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class DialChartActivity extends Activity {
    private CategorySeries category = new CategorySeries("");
    private DialRenderer renderer = new DialRenderer();
    private GraphicalView mChartView;

    private long xhqd = 0;

    Thread mediathread;
    final Handler handler = new Handler() {
        public void handleMessage(Message msg) {
            switch (msg.what) {
                case 1:
                    mChartView.invalidate();
                    break;
            }
            super.handleMessage(msg);
        }
    };
    Timer timer = new Timer();
    TimerTask task = new TimerTask() {
        public void run() {
            Message message = new Message();
            message.what = 1;
            handler.sendMessage(message);
        }
    };

    /** Called when the activity is first created. */
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dial_chart);
        category.add("Current", 0); // 给仪表一个初始值，不然报错
        renderer.setChartTitleTextSize(20);
        renderer.setLabelsTextSize(15);
        renderer.setLegendTextSize(15); // 说明字体的大小
        renderer.setShowGrid(true);
        renderer.setMargins(new int[] { 30, 50, 25, 10 });
        SimpleSeriesRenderer r = new SimpleSeriesRenderer();
        r.setColor(Color.RED); // 指针的颜色
        renderer.addSeriesRenderer(r);
        renderer.setLabelsTextSize(20); // 仪表盘字体大小
        renderer.setLabelsColor(Color.GREEN); // 仪表盘格子的颜色
        renderer.setShowLabels(true);
        // 设置指针样式（默认为三角形）此为箭头
        renderer.setVisualTypes(new DialRenderer.Type[] { Type.NEEDLE });
        renderer.setMinValue(0); // 仪表盘最小值
        renderer.setMaxValue(50000); // 仪表盘最大值
        timer.schedule(task, 0, 1000 * 1); // 启动timer,1秒绘制一次
        new ValuesThread().start();

        int a=3;
        boolean isLogin=false;
        test(a,isLogin);

        isLogin=true;
        test(a,isLogin);
        test(2,isLogin);

        float money=18000;
        //可售金额
        if (money >= 0) {
            float ddd = money;
            String remainingMoney = null;
            if (ddd <= 0) {
               Log.d("money","不限");
            } else if (ddd > 10000) {
                remainingMoney =(money/ 10000) + "万元";
                Log.d("money",remainingMoney);
            } else {
                remainingMoney =money + "元";
                Log.d("money",remainingMoney);
            }
        } else {
            Log.d("money","不限");
        }

        String url= "http://mds.bsy.com/wdx/test/index.html?action=appinvite&img=http://mds.bisouyi.com/test/images/banerqz.png&tie=%E6%AF%94%E6%90%9C%E7%9B%8A%E5%B8%A6%E4%BD%A0%E9%87%8D%E8%BF%94%E7%AB%A5%E5%B9%B4&desc=%E9%A2%86%E5%8F%96%E7%99%BE%E4%B8%87%E7%90%86%E8%B4%A2%E5%A5%96%E5%AD%A6%E9%87%91";
        String imgUrl=url.substring(getCharacterEndPosition(url,"img=",1), getCharacterStartPosition(url,"&",2));
        String title=url.substring(getCharacterEndPosition(url,"tie=",1),getCharacterStartPosition(url,"&",3));
        String desc=url.substring(getCharacterEndPosition(url,"desc=",1), url.length());
        Log.e("====",url.lastIndexOf("shareurl=")+"");
        Log.e("====",url.indexOf("?")+"");
//            String shareurl=url.substring(StringUtils.getCharacterEndPosition(url,"shareurl=",1), url.length());
        String shareurl=url.substring(0, url.indexOf("?"));
        Log.e("====",shareurl+"");


        Main.main(new String[]{"C:\\Users\\zxr\\Desktop\\test\\Bisouyi-gaoxin-anzhuomarket.apk"});

        Intent intent = getIntent();
        String scheme = intent.getScheme();
        Uri uri = intent.getData();
        System.out.println("scheme:"+scheme);
        if (uri != null) {
            String host = uri.getHost();
            String dataString = intent.getDataString();
            String id = uri.getQueryParameter("d");
            String path = uri.getPath();
            String path1 = uri.getEncodedPath();
            String queryString = uri.getQuery();
            System.out.println("host:"+host);
            System.out.println("dataString:"+dataString);
            System.out.println("id:"+id);
            System.out.println("path:"+path);
            System.out.println("path1:"+path1);
            System.out.println("queryString:"+queryString);
        }

    }
    public static int getCharacterStartPosition(String str,String target,int n){
        Matcher slashMatcher = Pattern.compile(target).matcher(str);
        int mIdx = 0;
        while(slashMatcher.find()) {
            mIdx++;
            if(mIdx == n) //    target第n次出现的位置
                break;
        }
        return slashMatcher.start();    //返回值是target的位置
    }
    public static int getCharacterEndPosition(String str,String target,int n){
        Matcher slashMatcher = Pattern.compile(target).matcher(str);
        int mIdx = 0;
        while(slashMatcher.find()) {
            mIdx++;
            if(mIdx == n) //    target第n次出现的位置
                break;
        }
        return slashMatcher.end();    //返回值是target的位置
    }
    private void test(int a,boolean isLogin){
        switch (a){
            case 1:
                break;
            case 3:
                if(!isLogin){
                    Log.e("====","需要登录");
                    break;
                }
                Log.e("====","需要");
            case 2:
                Log.e("====","已登录");
                break;

        }
    }

    @Override
    protected void onResume() {
        super.onResume();
        if (mChartView == null) {
            LinearLayout layout = (LinearLayout) findViewById(R.id.ll_xiediong);
            mChartView = ChartFactory.getDialChartView(DialChartActivity.this,
                    category, renderer);
            layout.addView(mChartView, new LayoutParams(
                    LayoutParams.FILL_PARENT, LayoutParams.FILL_PARENT));

        } else {
            mChartView.setBackgroundResource(R.id.ll_xiediong);
            mChartView.repaint();
        }
    }

    private class ValuesThread extends Thread {
        Random random = new Random(50000); // 指定随机数的种子数

        @Override
        public void run() {
            while (true) {
                try {
                    category.clear();
                    xhqd = random.nextInt() % 50000;
                    category.add("1111", xhqd);
                    Thread.sleep(1000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }
    }
}