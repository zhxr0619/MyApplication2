package app.univs.cn.myapplication;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Handler;
import android.os.Handler.Callback;
import android.os.Message;
import android.support.v4.app.Fragment;
import android.view.View;
import android.view.View.OnClickListener;

import com.tandong.sa.json.Gson;



public class BaseFragment extends Fragment implements OnClickListener, Callback, XListView.IXListViewListener {
    Gson gson = new Gson();
    protected ProgressDialog progressDialog = null;

    @Override
    public void onClick(View arg0) {
        // TODO Auto-generated method stub

    }

    public void waitPlease() {
        showLongToast("敬请期待");
    }

    public void loadingDialog() {
        if (null == progressDialog) {
            progressDialog = new ProgressDialog(getActivity());
        }
        progressDialog.setCanceledOnTouchOutside(false);
        progressDialog.setMessage("正在加载...");
        progressDialog.show();
    }

    public void loadingDialogDismiss() {
        if (progressDialog != null) {
            progressDialog.dismiss();
        }
    }

    @Override
    public boolean handleMessage(Message arg0) {
        // TODO Auto-generated method stub
        loadingDialogDismiss();
        switch (arg0.what) {
            case 1234567:
                showLongToast("网络未连接，请检查网路设置!");
                break;
//            case NumCode.ERROR_SOCKET_TIMEOUT:
//                showLongToast("网络连接超时");
//                break;
//            case NumCode.ERROR_JSON_PARSER:// 解析异常
//                showLongToast("数据解析异常");
//                break;
//            case NumCode.ERROR_UTF8_ENCODE:// 解析异常
//                showLongToast("字节转换异常");
//                break;
            case 0000:
                showLongToast("网络不给力请重试");
                break;

        }
        return false;
    }

    protected void showLongToast(String msg) {
        if (!StringUtils.isEmpty(msg)) {
            ToastUtils.showLongText(getActivity(), msg);
        }
    }

    protected void showShortText(String msg) {
        if (!StringUtils.isEmpty(msg)) {
            ToastUtils.showShortText(getActivity(), msg);
        }
    }

    public void gotoActivity(Class<? extends Activity> clazz) {
        Intent intent = new Intent(getActivity(), clazz);
        startActivity(intent);

    }


    public void init() {
    }

    protected Handler handler = new Handler(this);

    @Override
    public void onRefresh() {
        // TODO Auto-generated method stub

    }

    @Override
    public void onLoadMore() {
        // TODO Auto-generated method stub

    }

}
