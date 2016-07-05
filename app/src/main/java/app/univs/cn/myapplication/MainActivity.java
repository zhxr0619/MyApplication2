package app.univs.cn.myapplication;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.webkit.JavascriptInterface;
import android.webkit.JsResult;
import android.webkit.WebChromeClient;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.widget.Toast;

import com.tandong.sa.json.Gson;

public class MainActivity extends Activity {
//	WebView mWebView;

    private static String TAG = MainActivity.class.getName();
    private WebView mWebView;
    private Handler mHandler;
    private WebSettings mWebSettings;
    @SuppressLint("JavascriptInterface")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
		
		/*setContentView(R.layout.activity_main);
		
		
		 mWebView = (WebView) this.findViewById(R.id.web); 
	        mHandler = new Handler(); 
	 
	        // 设置支持JavaScript等 
	        mWebSettings = mWebView.getSettings(); 
	        mWebSettings.setJavaScriptEnabled(true); 
	        mWebSettings.setBuiltInZoomControls(true); 
	        mWebSettings.setLightTouchEnabled(true); 
	        mWebSettings.setSupportZoom(true); 
	        mWebView.setHapticFeedbackEnabled(false); 
	        // mWebView.setInitialScale(0); // 改变这个值可以设定初始大小 
	 
	        //重要,用于与页面交互! 
	        mWebView.addJavascriptInterface(new Object() { 
	            @SuppressWarnings("unused") 
	            public void oneClick(final String locX, final String locY) {//此处的参数可传入作为js参数 
	                mHandler.post(new Runnable() { 
	                    public void run() { 
	                        mWebView.loadUrl("javascript:shows(" + locX + "," + locY + ")"); 
	                    } 
	                }); 
	            } 
	            
	            public void updateData(){
	            	System.out.println("======");
	            }
	            
	        }, "demo");//此名称在页面中被调用,方法如下: 
	        //<body onClick="window.demo.clickOnAndroid(event.pageX,event.pageY)"> 
	 
	        final String mimeType = "text/html"; 
	        final String encoding = "utf-8"; 
	        final String html = "";// TODO 从本地读取HTML文件 
	 
//	        mWebView.loadDataWithBaseURL("file:///sdcard/", html, mimeType, 
//	                encoding, ""); 
	        
	        mWebView.loadUrl("file:///android_asset/index.html");
	 
*/
        showWebView();
    }
    @SuppressLint("JavascriptInterface")
    private void showWebView(){     // webView与js交互代码
        try {
            mWebView = new WebView(this);
            setContentView(mWebView);

            mWebView.requestFocus();

            mWebView.setWebChromeClient(new WebChromeClient(){
                @Override
                public void onProgressChanged(WebView view, int progress){
                    MainActivity.this.setTitle("Loading...");
                    MainActivity.this.setProgress(progress);

                    if(progress >= 80) {
                        MainActivity.this.setTitle("JsAndroid Test");
                    }
                }

                @Override
                public boolean onJsAlert(WebView view, String url, String message, JsResult result) {
                    Log.e("message",message);
                    return super.onJsAlert(view, url, message, result);
                }
            });

            mWebView.setOnKeyListener(new View.OnKeyListener() {        // webview can go back
                @Override
                public boolean onKey(View v, int keyCode, KeyEvent event) {
                    if(keyCode == KeyEvent.KEYCODE_BACK && mWebView.canGoBack()) {
                        mWebView.goBack();
                        return true;
                    }
                    return false;
                }
            });

            WebSettings webSettings = mWebView.getSettings();
            webSettings.setJavaScriptEnabled(true);
            webSettings.setDefaultTextEncodingName("utf-8");

            mWebView.addJavascriptInterface(getHtmlObject(), "BSYAPP");
//	        mWebView.loadUrl("http://192.168.1.121:8080/jsandroid/index.html");  
//            mWebView.loadUrl("file:///android_asset/test.html");
           // http://mds.bisouyi.com/wx/bsy.html
            mWebView.loadUrl("http://mds.bisouyi.com/wx/bsy.html");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private Object getHtmlObject(){
        Object insertObj = new Object(){

            @JavascriptInterface
            public void jsCallackFailure(String str){
                Log.e("jsCallackFailure",str);
            }

            @JavascriptInterface
            public void jsCallackSuccess(String str){
                Log.e("jsCallackSuccess",str);
            }

            @JavascriptInterface
            public String jsVersion(){
                Log.e("jsVersion","android");
                return "android";
            }
            @JavascriptInterface
            public void jsCallackException(String str){
                Log.e("jsCallackException",str);
            }


            @JavascriptInterface
            public String HtmlcallJava(){
                return "Html call Java";
            }
            @JavascriptInterface
            public String HtmlcallJava2(final String param){
                return "Html call Java : " + param;
            }
            @JavascriptInterface
            public String HtmlcallJava3(final String param){
                return "Html call Java : " + param;
            }
            @JavascriptInterface
            public void JavacallHtml(){
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        mWebView.loadUrl("javascript: showFromHtml()");
                        Toast.makeText(MainActivity.this, "clickBtn", Toast.LENGTH_SHORT).show();
                    }
                });
            }
            @JavascriptInterface
            public void JavacallHtml2(){
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        mWebView.loadUrl("javascript: showFromHtml2('IT-homer blog')");
                        Toast.makeText(MainActivity.this, "clickBtn2", Toast.LENGTH_SHORT).show();
                    }
                });
            }
            @JavascriptInterface
            public void JavacallHtml3(final String str){
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        mWebView.loadUrl("javascript: showFromHtml2('IT-homer blog')");
                        Toast.makeText(MainActivity.this, "clickBtn3"+str, Toast.LENGTH_SHORT).show();

                        Gson gson=new Gson();
                        Token token=gson.fromJson(str,Token.class);
                        Log.e("token==", token.getAuthorization());
                    }
                });
            }
            @JavascriptInterface
            public void JavacallHtml4(final String str,final String str2){
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        mWebView.loadUrl("javascript: showFromHtml2('IT-homer blog')");
                        Toast.makeText(MainActivity.this, "clickBtn3"+str, Toast.LENGTH_SHORT).show();
                    }
                });
            }
        };

        return insertObj;
    }

    class Token{
        private String authorization;

        public String getAuthorization() {
            return authorization;
        }

        public void setAuthorization(String authorization) {
            this.authorization = authorization;
        }
    }

}
