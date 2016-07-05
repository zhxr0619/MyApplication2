package app.univs.cn.myapplication;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.HashMap;

public class SplitApk {
    HashMap<String, String> qudao = new HashMap<String, String>();// 渠道号，渠道名
    String curPath;// 当前文件夹路径
    String apkDirectory;

    public SplitApk(String directory) {
        this.curPath = new File("").getAbsolutePath();
        this.apkDirectory = directory;
    }

    public void mySplit() {
        File dire = new File(apkDirectory);
        if (!dire.exists()) {
            System.out.println("没有该文件");
            return;
        }

        if (dire.isDirectory()) {
            File[] sonFile = dire.listFiles();
            for (File file : sonFile) {
                modifyXudao(file.getAbsolutePath());
            }
        } else {
            modifyXudao(apkDirectory);
        }

        System.out.println("====Over====");
    }

    /**
     * apktool解压apk，替换渠道值
     *
     * @throws Exception
     */
    private void modifyXudao(String apkName) {
        // 解压 /C 执行字符串指定的命令然后终断
        String cmdUnpack = "cmd.exe /C java -jar apktool.jar d -f -s "
                + apkName;
        runCmd(cmdUnpack);

        String[] apkFilePath = apkName.split("\\\\");
        String shortApkName = apkFilePath[apkFilePath.length - 1];
        String dir = shortApkName.split(".apk")[0];
        File packDir = new File(dir);// 获得解压的apk目录

        String f_mani = packDir.getAbsolutePath() + "\\AndroidManifest.xml";
        File manifest = new File(f_mani);

        for (int i = 0; i < 10; i++) {
            if (manifest.exists()) {
                break;
            }
            try {
                Thread.sleep(1000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }

        if (!manifest.exists()) {
            System.out.println("====验证失败======");
        }

        /*
         * 遍历map，复制manifese进来，修改后打包，签名，存储在对应文件夹中
         */
        BufferedReader br = null;
        FileReader fr = null;
        String keyLine = null;
        try {
            fr = new FileReader(manifest);
            br = new BufferedReader(fr);
            String line = null;
            while ((line = br.readLine()) != null) {
                if (line.contains("\"UMENG_CHANNEL\"")) { // 关键代码===我这里是用的百度统计工具BaiduMobAd_CHANNEL
                    keyLine = line;
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
            System.out.println("====验证失败======");
        } finally {
            try {
                if (fr != null) {
                    fr.close();
                }
                if (br != null) {
                    br.close();
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

        if (keyLine != null) {
            String tmps[] = keyLine.split("\\\"");
            System.out.println("读到的渠道是:" + tmps[3]);
        } else {
            System.out.println("====验证失败,请关闭======");
        }

        // 删除中途文件
        String cmdKey = String.format("cmd.exe /C rd /s/q %s", dir);
        runCmd(cmdKey);
    }

    /**
     * 执行指令
     *
     * @param cmd
     */
    public void runCmd(String cmd) {
        Runtime rt = Runtime.getRuntime();
        BufferedReader br = null;
        InputStreamReader isr = null;
        try {
            Process p = rt.exec(cmd);
            // p.waitFor();
            isr = new InputStreamReader(p.getInputStream());
            br = new BufferedReader(isr);
            String msg = null;
            while ((msg = br.readLine()) != null) {
                System.out.println(msg);
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            try {
                if (isr != null) {
                    isr.close();
                }
                if (br != null) {
                    br.close();
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
}
