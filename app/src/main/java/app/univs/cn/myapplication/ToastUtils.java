package app.univs.cn.myapplication;

import android.content.Context;
import android.widget.Toast;

public class ToastUtils {

    private static Toast toast = null;

    public static void showShortText(Context context, String info) {
        if (toast == null) {
            toast = Toast.makeText(context, info, Toast.LENGTH_SHORT);
        } else {
            toast.setText(info);
        }
        toast.show();
    }

    public static void showLongText(Context context, String info) {
        if (toast == null) {
            toast = Toast.makeText(context, info, Toast.LENGTH_LONG);
        } else {
            toast.setText(info);
        }
        toast.show();
    }
}
