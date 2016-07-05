package app.univs.cn.myapplication;

import java.io.IOException;
import java.io.PipedInputStream;
import java.io.PipedOutputStream;

/**
 * Created by zxr on 2016/4/12.
 */
public class WriteAndReadAction {

    public static void writeAndRead(){
        try {
            WriteData writeData=new WriteData();
            ReadData readData=new ReadData();

            PipedInputStream inputStream=new PipedInputStream();
            PipedOutputStream outputStream=new PipedOutputStream();

//            inputStream.connect(outputStream);
            outputStream.connect(inputStream);

            ThreadRead threadRead=new ThreadRead(readData,inputStream);
            threadRead.start();

            Thread.sleep(2000);

            ThreadWrite threadWrite=new ThreadWrite(writeData,outputStream);
            threadWrite.start();

        }catch (IOException e){
            e.printStackTrace();
        }catch (InterruptedException e){
            e.printStackTrace();
        }

    }

}

class ThreadWrite extends Thread{

    private WriteData writeData;

    private PipedOutputStream out;

    public ThreadWrite(WriteData writeData,PipedOutputStream out){
        super();
        this.writeData=writeData;
        this.out=out;
    }

    @Override
    public void run() {
        super.run();
        writeData.writeMethod(out);
    }
}

class ThreadRead extends Thread{
    private ReadData readData;
    private PipedInputStream input;
    public ThreadRead(ReadData readData,PipedInputStream input){
        super();
        this.readData=readData;
        this.input=input;
    }

    @Override
    public void run() {
        super.run();
        readData.readMethod(input);
    }
}