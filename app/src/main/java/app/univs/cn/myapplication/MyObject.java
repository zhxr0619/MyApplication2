package app.univs.cn.myapplication;

/**
 * Created by zxr on 2016/4/14.
 */
public class MyObject {
   private static MyObject instance=null;
    private MyObject(){

    }
    static {
        instance=new MyObject();
    }
    public static MyObject getInstance(){
        return instance;
    }

//    private static MyObject myObject;
//    private MyObject(){
//
//    }
//    //使用双检测机制解决问题，既保证了不需要同步代码的异步执行性，又保证了单例的效果
//    public static MyObject getInstance() {
//        try {
//            if(myObject!=null){
//            }else {
//                //模拟在创建对象之前做一些准备性的工作
//                Thread.sleep(3000);
//                synchronized (MyObject.class) {
//                    if (myObject == null) {
//                        myObject = new MyObject();
//                    }
//                }
//            }
//        } catch (InterruptedException e) {
//            e.printStackTrace();
//        }
//        return myObject;
//    }

//    synchronized public static MyObject getInstance(){
//        if(myObject==null){
//            myObject=new MyObject();
//        }
//        return myObject;
//    }

//    //立即加载方式 == 饿汉模式
//    private static MyObject myObject=new MyObject();
//    private MyObject(){
//
//    }
//    public static MyObject getInstance(){
//        /**
//         * 此代码版本为立即加载
//         * 此版本代码的缺点是不能有其他实例变量
//         * getInstance方法没有同步所以有可能出现非线程安全问题
//         */
//        return myObject;
//    }
}
