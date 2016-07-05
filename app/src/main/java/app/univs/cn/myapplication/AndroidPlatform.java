package app.univs.cn.myapplication;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;

/**
 * Created by zxr on 2016/3/15.
 */
public class AndroidPlatform {

    public static final String KEYSTRING_USER_AGENT = "user_agent_key";

    public static String getUAFromProperties() {
        try {
            FileInputStream is = getPropertyStream();
            ByteArrayOutputStream bytearrayoutputstream = new ByteArrayOutputStream();
            byte buf[] = new byte[1024];
            for (int k = 0; -1 != (k = is.read(buf)); )
                bytearrayoutputstream.write(buf, 0, k);

            String fileString = new String(bytearrayoutputstream.toByteArray(), "UTF-8");

            return getProperties(KEYSTRING_USER_AGENT, fileString);

            //System.out.println("IS FILE Android Platform " + bytearrayoutputstream.size() + " "+());

        } catch (Exception e) {
            // TODO: handle exception

            System.out.println("IS FILE erororo");
            e.printStackTrace();
        }
        return null;
    }

    public static FileInputStream getPropertyStream() {
        try {

            File property = new java.io.File("/opl/etc/properties.xml");
            if (property.exists()) {
                return new FileInputStream(new java.io.File("/opl/etc/properties.xml"));
            } else {
                property = new java.io.File("/opl/etc/product_properties.xml");
                if (property.exists()) {
                    return new FileInputStream(new java.io.File("/opl/etc/product_properties.xml"));
                } else {
                    return null;
                }
            }

        } catch (Exception e) {
            // TODO: handle exception
            e.printStackTrace();
        }
        return null;
    }

    public static String getProperties(String key, String content) {
        String STARTKEY = "<" + key + ">";
        String ENDKEY = "</" + key + ">";
        content = content.replace("\r", "");
        content = content.replace("\n", "");

        int startIndex = content.indexOf(STARTKEY) + STARTKEY.length();
        int endIndex = content.indexOf(ENDKEY);
        if (startIndex > -1 && endIndex > -1) {
            return content.substring(startIndex, endIndex);
        } else
            return null;
    }



//联网请求时，加入UA即可，这样就做到了自动适配了。具体如下：

   /* private int CountMoneyCMWAPNEWWAY(String urlstr) {

        String strHead = "";
        try {
            if (!GameLet._self.isNetworkCMWAPAvailable()) {
                GameLet._self.ActiveNetWorkByMode("wap");
                Thread.sleep(5000);
            }

            int splashIndex = urlstr.indexOf("/", 7);

            String hosturl = urlstr.substring(7, splashIndex);
            String hostfile = urlstr.substring(splashIndex);

            HttpHost proxy = new HttpHost("10.0.0.172", 80, "http");
            HttpHost target = new HttpHost(hosturl, 80, "http");

            HttpParams httpParams = new BasicHttpParams();
            HttpConnectionParams.setConnectionTimeout(httpParams, 20 * 1000);
            HttpConnectionParams.setSoTimeout(httpParams, 20 * 1000);
            HttpConnectionParams.setSocketBufferSize(httpParams, 8192);
            HttpClientParams.setRedirecting(httpParams, true);

            String userAgent = AndroidPlatform.getUAFromProperties();

            HttpProtocolParams.setUserAgent(httpParams, userAgent);
            DefaultHttpClient httpclient = new DefaultHttpClient(httpParams);

            httpclient.getParams().setParameter(ConnRoutePNames.DEFAULT_PROXY, proxy);

            HttpGet req = new HttpGet(hostfile);

            HttpResponse rsp = httpclient.execute(target, req);

            HttpEntity entity = rsp.getEntity();

            InputStream inputstream = entity.getContent();
            ByteArrayOutputStream bytearrayoutputstream = new ByteArrayOutputStream();
            byte abyte1[] = new byte[1024];
            for (int k = 0; -1 != (k = inputstream.read(abyte1)); )
                bytearrayoutputstream.write(abyte1, 0, k);

            strHead = new String(bytearrayoutputstream.toByteArray(), "UTF-8");

            httpclient.getConnectionManager().shutdown();

        } catch (Exception e) {
            return 2;
        }

        if (strHead.indexOf("status=1301") > -1 || strHead.indexOf("status=1300") > -1) {
            return 1;
        } else {
            return 0;
        }

    }*/

}
