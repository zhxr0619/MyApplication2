package app.univs.cn.myapplication;

import java.io.IOException;
import java.io.PipedOutputStream;

/**
 * Created by zxr on 2016/4/12.
 */
public class WriteData {
    public void writeMethod(PipedOutputStream out){
        try {
            System.out.println("write:");
            for (int i = 0; i < 300; i++) {
                String outData = "" + (i + 1);
                out.write(outData.getBytes());
                System.out.print(outData);
            }
            System.out.println();
            out.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
