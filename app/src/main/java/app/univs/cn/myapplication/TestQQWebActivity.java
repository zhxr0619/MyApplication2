package app.univs.cn.myapplication;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.DialogInterface.OnClickListener;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.os.Looper;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.webkit.DownloadListener;
import android.webkit.URLUtil;
import android.webkit.WebChromeClient;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.ImageButton;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import java.io.File;


public class TestQQWebActivity extends Activity implements View.OnClickListener,
        DownloadInterface {

    private WebView myweb; // webView
    private ProgressBar title_bar; // 标题栏进度条
    private TextView loadingTv; // 中间进度条
    private ImageButton back; // 后退
    private ImageButton forward; // 前进
    private ImageButton refresh; // 刷新
    private ProgressDialog dialog = null; //下载进度条
//    private final String url = "http://www.bbczjh.com/e/wap/";
    private final String url="http://www.licaifan.com/index.php/site/index?refer=685";
    private final String FILE_PATH = "/sdcard/download";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_test_qqweb);
        initView(); // 初始化控件
        setListener(); // 设置监听
        initWeb();// 执行WebView初始化函数
        loadurl(myweb, url);
    }

    /**
     * 初始化webView
     */
    private void initWeb() {
//        myweb.setScrollBarStyle(0);// 滚动条风格，为0就是不给滚动条留空间，滚动条覆盖在网页上
        WebSettings ws = myweb.getSettings();
        ws.setJavaScriptEnabled(true); // 设置支持javascript脚本
        ws.setAllowFileAccess(true); // 允许访问文件
        ws.setBuiltInZoomControls(true); // 设置不显示缩放按钮
        ws.setSupportZoom(true); // 支持缩放
        ws.setDisplayZoomControls(false);
        myweb.setWebViewClient(new WebViewClient() {
            @Override
            public boolean shouldOverrideUrlLoading(WebView view, String url) {
                if(URLUtil.isNetworkUrl(url)){
                    loadurl(view, url);// 载入网页
                }

                return true;
            }

            @Override
            public void onPageStarted(WebView view, String url, Bitmap favicon) {
                // TODO Auto-generated method stub
                Log.e("onPageStarted：", "开始");
                loadingTv.setVisibility(View.VISIBLE);
                title_bar.setVisibility(View.VISIBLE);
                super.onPageStarted(view, url, favicon);
            }

            @Override
            public void onPageFinished(WebView view, String url) {
                // TODO Auto-generated method stub
                Log.e("onPageFinished：", "结束");
                loadingTv.setVisibility(View.GONE);
                title_bar.setVisibility(View.GONE);
                super.onPageFinished(view, url);
            }

            @Override
            public void onReceivedError(WebView view, int errorCode,
                                        String description, String failingUrl) {
                // TODO Auto-generated method stub
                Toast.makeText(TestQQWebActivity.this,
                        description + " 错误代码：" + errorCode, Toast.LENGTH_SHORT)
                        .show();
                super.onReceivedError(view, errorCode, description, failingUrl);
            }
        });
        myweb.setWebChromeClient(new WebChromeClient() {
            public void onProgressChanged(WebView view, int progress) {// 载入进度改变而触发
                Log.e("进度：", progress + "%");
                loadingTv.setText(progress + "%");
                super.onProgressChanged(view, progress);
            }
        });
        /**
         * 设置下载监听，当url为文件时候开始下载文件
         */
//        myweb.setDownloadListener(new DownloadListener() {
//            @Override
//            public void onDownloadStart(String url, String userAgent,
//                                        String contentDisposition, String mimetype,
//                                        long contentLength) {
//                download(url);
//            }
//        });
        myweb.setDownloadListener(new DownloadListener() {

            @Override
            public void onDownloadStart(String url, String userAgent, String contentDisposition, String mimetype, long contentLength) {
                // 监听下载功能，当用户点击下载链接的时候，直接调用系统的浏览器来下载

                Uri uri = Uri.parse(url);
                Intent intent = new Intent(Intent.ACTION_VIEW, uri);
                startActivity(intent);
            }
        });

    }

    /**
     * 载入链接
     * @param view
     * @param url
     */
    public void loadurl(final WebView view, final String url) {
//        new Thread() {
//            public void run() {
                view.loadUrl(url);// 载入网页
//            }
//        }.start();
    }

    /**
     * 下载文件
     */
    private void download(String url) {
        // TODO Auto-generated method stub
        if (Environment.getExternalStorageState().equals(
                Environment.MEDIA_MOUNTED)) {
            File file = new File(FILE_PATH);
            if (!file.exists()) {
                file.mkdirs();
            }
            showDownloadDialog(); //显示下载窗口
            String expand_name = url.substring(url.lastIndexOf("."), url.length()); //获取下载文件的扩展名
            String save_file_name = System.currentTimeMillis()+expand_name; //新文件名
            final DownLoadFileThreadTask task = new DownLoadFileThreadTask(url,
                    file.getAbsolutePath() + "/"+save_file_name, this);
            Thread thread = new Thread(task);
            thread.start();
            dialog.setOnCancelListener(new DialogInterface.OnCancelListener() {
                @Override
                public void onCancel(DialogInterface dialog) {
                    // TODO Auto-generated method stub
                    task.stop();
                }
            });
        } else {
            Toast.makeText(this, "SD卡不可用！", Toast.LENGTH_SHORT).show();
        }
    }

    /**
     * 初始化控件
     */
    private void initView() {
        myweb = (WebView) findViewById(R.id.myweb);
        title_bar = (ProgressBar) findViewById(R.id.loadingbar);
        loadingTv = (TextView) findViewById(R.id.loadingTv);
        back = (ImageButton) findViewById(R.id.btn_back);
        forward = (ImageButton) findViewById(R.id.btn_go);
        refresh = (ImageButton) findViewById(R.id.btn_refresh);
    }

    /**
     * 设置监听器
     */
    private void setListener() {
        // TODO Auto-generated method stub
        back.setOnClickListener(this);
        forward.setOnClickListener(this);
        refresh.setOnClickListener(this);
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        // TODO Auto-generated method stub
        if ((keyCode == KeyEvent.KEYCODE_BACK) && myweb != null
                && myweb.canGoBack()) {
            goBack();
            return true;
        } else if (keyCode == KeyEvent.KEYCODE_BACK) {
            TestQQWebActivity.this.finish();
        }
        return super.onKeyDown(keyCode, event);
    }

    @Override
    protected void onDestroy() {
        // TODO Auto-generated method stub
        myweb.removeAllViews();
        myweb.destroy();
        Log.e("程序退出:", "------- 已经退出-----");
        super.onDestroy();
    }

    @Override
    public void onClick(View v) {
        // TODO Auto-generated method stub
        switch (v.getId()) {
            case R.id.btn_back: // 返回
                goBack();
                break;
            case R.id.btn_go: // 前进
                goForward();
                break;
            case R.id.btn_refresh: // 刷新
                refresh();
                break;
            default:
                break;
        }
    }

    /**
     * 返回
     */
    private void goBack() {
        // TODO Auto-generated method stub
        if (myweb != null && myweb.canGoBack()) {
            myweb.goBack();
        }
    }

    /**
     * 前进
     */
    private void goForward() {
        // TODO Auto-generated method stub
        if (myweb != null && myweb.canGoForward()) {
            myweb.goForward();
        }
    }

    /**
     * 刷新
     */
    private void refresh() {
        // TODO Auto-generated method stub
        if (myweb != null) {
            myweb.reload();
        }
    }

    /**
     * 显示下载进度条
     */
    private void showDownloadDialog() {
        if (dialog == null) {
            dialog = new ProgressDialog(this);
            dialog.setProgressStyle(ProgressDialog.STYLE_HORIZONTAL);
            dialog.setTitle("下载中...");
            dialog.setCanceledOnTouchOutside(false);
            dialog.setButton(DialogInterface.BUTTON_NEGATIVE, "取消", new OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    // TODO Auto-generated method stub
                    dialog.cancel();
                }
            });
        };
        dialog.show();
    }

    @Override
    public void sendMsg(ResponseBean response) {
        // TODO Auto-generated method stub
        if (response == null) {
            return;
        }
        switch (response.getStatus()) {
            case DownLoadFileThreadTask.DOWNLOAD_ING:
                dialog.setMax(response.getTotal());
                dialog.setProgress(response.getCurrent());
                break;
            case DownLoadFileThreadTask.DOWNLOAD_SUCCESS:
                dialog.dismiss();
                Looper.prepare(); //注意这段代码
                Toast.makeText(TestQQWebActivity.this, "下载成功，已经下载到"+FILE_PATH+"目录！", Toast.LENGTH_LONG)
                        .show();
                Looper.loop();
                break;
            case DownLoadFileThreadTask.DOWNLOAD_FAILURE:
                dialog.dismiss();
                Looper.prepare();
                Toast.makeText(TestQQWebActivity.this, response.getMsg(),
                        Toast.LENGTH_SHORT).show();
                Looper.loop();
                break;
            default:
                break;
        }
    }

}
