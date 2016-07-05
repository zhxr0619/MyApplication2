package app.univs.cn.myapplication;

import android.app.Activity;
import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;
import android.os.Bundle;
import android.view.SurfaceHolder;
import android.view.SurfaceView;

public class ViewTest extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(new MyView(this));
    }

    class MyView extends SurfaceView implements SurfaceHolder.Callback {
        private SurfaceHolder holder;
        private MyThread myThread;

        public MyView(Context context) {
            super(context);

            holder = this.getHolder();
            holder.addCallback(this);
            // 创建一个绘图线程
            myThread = new MyThread(holder);

        }


        @Override
        public void surfaceCreated(SurfaceHolder holder) {
            myThread.isRun = true;
            myThread.start();
        }

        @Override
        public void surfaceChanged(SurfaceHolder holder, int format, int width, int height) {

        }

        @Override
        public void surfaceDestroyed(SurfaceHolder holder) {
            myThread.isRun = false;
        }
    }

    //线程内部类
    class MyThread extends Thread {
        private SurfaceHolder holder;
        public boolean isRun;

        public MyThread(SurfaceHolder holder) {
            this.holder = holder;
            isRun = true;
        }

        @Override
        public void run() {
            int count = 0;
            while (isRun) {
                Canvas c = null;
                try {
                    synchronized (holder) {
                        //锁定画布，一般在锁定后就可以通过其返回的画布对象Canvas，在其上面画图等操作
                        c = holder.lockCanvas();
                        //设置画布背景颜色
                        c.drawColor(Color.WHITE);
                        Paint p = new Paint();//创建画笔
                        //jux
                        Rect r = new Rect(100, 50, 300, 250);
                        c.drawRect(r, p);
                        c.drawText("这是第" + (count++) + "秒", 100, 310, p);

                        Thread.sleep(1000);

                    }
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }finally {
                    if(c!=null){
                        ////结束锁定画图，并提交改变。
                        holder.unlockCanvasAndPost(c);
                    }
                }
            }

        }
    }

}
