package app.univs.cn.myapplication;

import android.content.Context;

/**
 * Created by zxr on 2015/11/14.
 *
 */
public class HaoBuyInit {
    private static Context mContext;
    private static HaoBuyInit instance;

    private HaoBuyInit() {
    }

    public static synchronized HaoBuyInit getInstance() {
        if (instance == null) {
            instance = new HaoBuyInit();
        }
        return instance;
    }

    public void init(Context context) {
        mContext = context;
    }

    public Context getmContext() {
        return mContext;
    }

}
