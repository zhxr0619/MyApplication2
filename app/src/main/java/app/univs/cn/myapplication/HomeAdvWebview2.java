package app.univs.cn.myapplication;
import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.util.Log;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.Button;
import android.widget.TextView;

/**
 * @author lj
 * @ClassName: HomeAdvWebview
 * @Description:首页广告Html
 * @date 2015-8-14 下午3:47:08
 */
public class HomeAdvWebview2 extends Activity {

	private ProgressWebView web;

	private Button get_cash_btn;

	private TextView title_middle, mTitlre_right;

	private String buyProUrl = "";

	private String mOriginUrl;

	private Context mContext;

	private boolean mShowShare;

	private boolean mShowGetCash;

	private String bsyAction = "BSYAction_Purchase/";

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.webview_home_ad);
		init();

	}

	public void init() {

		mContext = this;

		web = (ProgressWebView) findViewById(R.id.webview);
		WebSettings settings = web.getSettings();

		//use html5 viewport attribute
		web.getSettings().setLoadWithOverviewMode(true);
		web.getSettings().setUseWideViewPort(true);
		web.getSettings().setDomStorageEnabled(true);

		web.getSettings().setJavaScriptEnabled(true);// 设置支持Javascript
		// settings.setAppCacheEnabled(false);
		// settings.setCacheMode(WebSettings.LOAD_NO_CACHE);
		// web.requestFocus();// 触摸焦点起作用
		// settings.setUseWideViewPort(true);
		// settings.setLoadWithOverviewMode(true);
		// settings.setSupportZoom(true);
		// settings.setBuiltInZoomControls(true);
		if (getIntent() != null) {

			Log.e("====",buyProUrl);
//             String testUrl = "https://m.nonobank.com/buy.html?id=1341&am_id=920&approach=LCNN%E9%AB%98%E9%91%ABCPSCPS&itType=1&model=inner";
//            web.loadUrl(testUrl);
			buyProUrl ="http://i.eqxiu.com/s/RGyEo2hP";
			web.loadUrl(buyProUrl);
			web.setWebViewClient(new WebViewClient() {
				@Override
				public boolean shouldOverrideUrlLoading(WebView view, String url) {
					// TODO Auto-generated method stub
					int position = url.lastIndexOf(bsyAction);
//                    int position = url.indexOf("BSYAction_Purchase");
					if (position == -1) {
						view.loadUrl(url);// 使用当前WebView处理跳转
					} else {
						String proId = url.substring(position + bsyAction.length(), url.length());
//						deakAction(proId);
					}
					return true;// true表示此事件在此处被处理，不需要再广播
				}

				@Override
				// 转向错误时的处理
				public void onReceivedError(WebView view, int errorCode, String description, String failingUrl) {
					// TODO Auto-generated method stub
					// Toast.makeText(WebViewTest.this, "Oh no! " + description,
					// Toast.LENGTH_SHORT).show();
				}
			});
		}

	}
}
