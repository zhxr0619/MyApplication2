package app.univs.cn.myapplication;

/**
 * Created by zxr on 2016/4/11.
 * 生产者与消费者
 */
public class ProductAndConsumer {

    public static void productAndC(){
        String lock=new String("");
        P p=new P(lock);
        C c=new C(lock);
        ThreadP threadP=new ThreadP(p);
        ThreadC threadC=new ThreadC(c);
        threadP.start();
        threadC.start();
    }

}

class ThreadP extends Thread{
    private P p;
    public ThreadP(P p){
        super();
        this.p=p;
    }

    @Override
    public void run() {
        super.run();
        while (true){
            p.setValue();
        }
    }
}
class ThreadC extends Thread{
    private C c;
    public ThreadC(C c){
        super();
        this.c=c;
    }

    @Override
    public void run() {
        super.run();
        while (true){
            c.getValue();
        }
    }
}
