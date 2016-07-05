package app.univs.cn.myapplication;

import android.app.Activity;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.KeyEvent;
import android.widget.Gallery;
import android.widget.Toast;

import java.util.HashMap;

/**
 * Created by huangfeihong on 2015/11/19.
 * 用于跟踪用户当前�?在的页面，并处理页面跳转的�?�辑
 */
public class CreateAccountActivity extends Activity implements
        CreateAccountAdapter.CreateAccountDelegate {
    private Gallery mGallery;
    private CreateAccountAdapter mAdapter;
    private int mGalleryPosition;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.create_account);
        mGallery = (Gallery) findViewById(R.id.create_account_gallery);

        mAdapter = new CreateAccountAdapter(this);
        mGallery.setAdapter(mAdapter);
        mGalleryPosition = 0;
    }

    /**
     * 在onResume()方法中将当前Activity设置为Adapter的委托，
     * 并且在onPause()方法中将委托置空
     */
    @Override
    protected void onResume() {
        super.onResume();
        mAdapter.setDelegate(this);
    }

    @Override
    protected void onPause() {
        super.onPause();
        mAdapter.setDelegate(null);
    }

    /**
     * 重写onBackPressed()方法，用于返回上�?个页�?
     */
    @Override
    public void onBackPressed() {
        if (mGalleryPosition > 0) {
            scroll(BACKWARD);
        } else {
            super.onBackPressed();
        }
    }

    /**
     * 在该方法中，Activity可以根据参数将Gallery移动到下�?个页面或者上�?个页面中
     * 遗憾的是，我们无法为Gallery控件的页面切换添加动画效果�?�我唯一想到方法是发�?
     * KeyEvent.KEYCODE_DPAD_RIGHT事件，虽然这是投机取巧，却也管用�?
     */
    @Override
    public void scroll(int type) {
        switch (type) {
            case FORWARD:
            default:
                if (mGalleryPosition < mGallery.getCount() - 1) {
                    mGallery.onKeyDown(KeyEvent.KEYCODE_DPAD_RIGHT, new KeyEvent(0,
                            0));
                    mGalleryPosition++;
                }
                break;
            case BACKWARD:
                if (mGalleryPosition > 0) {
                    mGallery.onKeyDown(KeyEvent.KEYCODE_DPAD_LEFT, new KeyEvent(0,
                            0));
                    mGalleryPosition--;
                }
        }
    }

    @Override
    public void processForm(Account account) {
        HashMap<String, String> errors = account.getErrors();
        String name = account.getName();

        if (TextUtils.isEmpty(name)) {
            errors.put(CreateAccountAdapter.FULL_NAME_KEY, "Name is empty");
        }
//        if (account.getEmail().toLowerCase().equals("me@my.com")) {
//            errors.put(CreateAccountAdapter.EMAIL_KEY,
//                    "E-mail is already taken");
//        }

        if (errors.isEmpty()) {
            Toast.makeText(this, "Form ok!", Toast.LENGTH_SHORT).show();
            finish();
        } else {
            mAdapter.showErrors(account);
            mGallery.setSelection(0);
            mGalleryPosition = 0;
        }
    }

}
