package app.univs.cn.myapplication;

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.util.AttributeSet;
import android.util.Log;
import android.webkit.WebView;
import android.widget.ProgressBar;

import java.lang.reflect.Field;
import java.lang.reflect.Method;


public class ProgressWebView extends WebView {

    private ProgressBar progressbar;

    public ProgressWebView(Context context, AttributeSet attrs) {
        super(context, attrs);
        progressbar = new ProgressBar(context, null,
                android.R.attr.progressBarStyleHorizontal);
        progressbar.setLayoutParams(new LayoutParams(LayoutParams.MATCH_PARENT,
                6, 0, 0));

        Drawable drawable = context.getResources().getDrawable(
                R.drawable.progress_bar_states);
        progressbar.setProgressDrawable(drawable);
        addView(progressbar);
        // setWebViewClient(new WebViewClient(){});
        setWebChromeClient(new MyWebChromeClient());
        // 是否支持缩放
        getSettings().setSupportZoom(true);
        getSettings().setBuiltInZoomControls(true);
    }

    public class MyWebChromeClient extends android.webkit.WebChromeClient {
        @Override
        public void onProgressChanged(WebView view, int newProgress) {
//            super.onProgressChanged(view, newProgress);
            if (newProgress == 100) {
                progressbar.setVisibility(GONE);
            } else {
                if (progressbar.getVisibility() == GONE)
                    progressbar.setVisibility(VISIBLE);
                progressbar.setProgress(newProgress);
            }
        }


    }

    @Override
    protected void onScrollChanged(int l, int t, int oldl, int oldt) {
        LayoutParams lp = (LayoutParams) progressbar.getLayoutParams();
        lp.x = l;
        lp.y = t;
        progressbar.setLayoutParams(lp);
        super.onScrollChanged(l, t, oldl, oldt);
    }
    public void enablecrossdomain41()
    {
        try{
            Field webviewclassic_field = WebView.class.getDeclaredField("mProvider");
            webviewclassic_field.setAccessible(true);
            Object webviewclassic=webviewclassic_field.get(this);
            Field webviewcore_field=webviewclassic.getClass().getDeclaredField("mWebViewCore");
            webviewcore_field.setAccessible(true);
            Object mWebViewCore=webviewcore_field.get(webviewclassic);
            Field nativeclass_field=webviewclassic.getClass().getDeclaredField("mNativeClass");
            nativeclass_field.setAccessible(true);
            Object mNativeClass=nativeclass_field.get(webviewclassic);

            Method method=mWebViewCore.getClass().getDeclaredMethod("nativeRegisterURLSchemeAsLocal",new Class[] {int.class,String.class});
            method.setAccessible(true);
            method.invoke(mWebViewCore,mNativeClass, "http");
            method.invoke(mWebViewCore,mNativeClass, "https");
        } catch(Exception   e){
            Log.d("enablecrossdomain41","enablecrossdomain error");
            e.printStackTrace();
        }
    }
}

