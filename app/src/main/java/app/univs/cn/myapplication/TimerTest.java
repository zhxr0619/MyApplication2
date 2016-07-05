package app.univs.cn.myapplication;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Timer;
import java.util.TimerTask;

/**
 * Created by zxr on 2016/4/14.
 */
public class TimerTest {

//    private static Timer timer = new Timer();
//    static public class MyTask extends TimerTask{
//        @Override
//        public void run() {
//            System.out.println("运行了，时间："+new Date());
////            this.cancel();
////            timer.cancel();
//        }
//    }
//
//    public static void test(){
//        try {
//            MyTask task=new MyTask();
//            SimpleDateFormat sdf=new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
//            String dateString="2016-04-14 14:10:00";
//             Date dateRef=sdf.parse(dateString);
//            System.out.println("字符串时间："+dateRef.toLocaleString()+" 当前时间："+new Date().toLocaleString());
////            timer.schedule(task,dateRef);
//
//            timer.schedule(task,dateRef,4000);
//        } catch (ParseException e) {
//            e.printStackTrace();
////        }
////    }
//
//    static int i = 0;
//
//    static public class MyTask extends TimerTask {
//        @Override
//        public void run() {
//            System.out.println("运行了：" + i);
//        }
//    }
//
//    public static void test() {
//        while (true) {
//            try {
//                i++;
//                Timer timer = new Timer();
//                MyTask task = new MyTask();
//                SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
//                String dateString = "2016-04-14 14:10:00";
//                Date dateRef = sdf.parse(dateString);
//                timer.schedule(task, dateRef);
//                timer.cancel();
//            } catch (ParseException e) {
//                e.printStackTrace();
//            }
//        }
//    }


        private static Timer timer = new Timer();
    private static int runCount=0;
    static public class MyTask extends TimerTask {
        @Override
        public void run() {
            try {
                System.out.println("1 begin 运行了，时间："+new Date());
                Thread.sleep(2000);
                System.out.println("1 end 运行了，时间："+new Date());
                runCount++;
                if (runCount==5){
                    timer.cancel();
                }
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }

    public static void test(){
        try {
            MyTask task=new MyTask();
            SimpleDateFormat sdf=new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
            String dateString="2016-04-14 14:10:00";
             Date dateRef=sdf.parse(dateString);
            System.out.println("字符串时间："+dateRef.toLocaleString()+" 当前时间："+new Date().toLocaleString());
//            timer.schedule(task,dateRef,4000);
            timer.scheduleAtFixedRate(task,dateRef,4000);
        } catch (ParseException e) {
            e.printStackTrace();
        }
    }

}
