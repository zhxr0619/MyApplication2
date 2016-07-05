package app.univs.cn.myapplication;

import android.app.Activity;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.webkit.JavascriptInterface;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;

import java.io.Serializable;

/**
 * @ClassName: HomeAdvWebview
 * @Description:首页广告Html
 * @author lj
 * @date 2015-8-14 下午3:47:08
 */
public class HomeAdvWebview extends Activity implements Serializable,View.OnClickListener {

	private ProgressWebView web;

	private String buyProUrl = "";


	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.webview_product_buy);
		init();
	}

	@Override
	public void onClick(View arg0) {
		switch (arg0.getId()) {
			case R.id.left:
				finish();
				break;

			default:
				break;
		}
	}
	@JavascriptInterface
	public void init() {

		findViewById(R.id.left).setOnClickListener(this);

		web = (ProgressWebView) findViewById(R.id.webview);
		WebSettings settings = web.getSettings();

		//use html5 viewport attribute

		 web.requestFocus();// 触摸焦点起作用

		buyProUrl ="http://www.jntlj.com/";

//		CookieSyncManager.createInstance(this);
//		CookieSyncManager.getInstance().startSync();
//		CookieManager.getInstance().removeSessionCookie();

		web.getSettings().setLoadWithOverviewMode(true);
		web.getSettings().setUseWideViewPort(true);
		web.getSettings().setDomStorageEnabled(true);
		web.getSettings().setJavaScriptEnabled(true);
		web.setWebViewClient(new WebViewClient() {
			@Override
			public boolean shouldOverrideUrlLoading(WebView view, String url) {
				// TODO Auto-generated method stub
				view.loadUrl(url);// 使用当前WebView处理跳转
				Log.e("===","shouldOverrideUrlLoading");
				return true;// true表示此事件在此处被处理，不需要再广播
			}

			@Override
			// 转向错误时的处理
			public void onReceivedError(WebView view, int errorCode, String description, String failingUrl) {
				// TODO Auto-generated method stub
				// Toast.makeText(WebViewTest.this, "Oh no! " + description,
				// Toast.LENGTH_SHORT).show();
				Log.e("===","onReceivedError");
			}

			@Override
			public void onPageFinished(WebView view, String url) {
				super.onPageFinished(view, url);
				Log.e("===","onPageFinished");
			}
		});
		if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN){
			web.getSettings().setAllowUniversalAccessFromFileURLs(true);
			web.enablecrossdomain41();
		}

		web.loadUrl(buyProUrl);

	}
}
