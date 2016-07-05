package app.univs.cn.myapplication;

public class Main {
    public static void main(String[] args) {
        System.out
                .println("=====**====Code by H3c=====**======");
        System.out.println("==**==渠道验证工具==**==");

        if (args.length != 1) {
            System.out
                    .println("==ERROR==usage:java -jar channelV.jar apkDirectory======");
            System.out
                    .println("==INFO==Example: java -jar channelV.jar /apps======");
            return;
        }

        String apk = args[0];

        SplitApk sp = new SplitApk(apk);
        sp.mySplit();
    }
}  