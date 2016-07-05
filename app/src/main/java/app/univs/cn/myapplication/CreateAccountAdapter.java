package app.univs.cn.myapplication;

import android.content.Context;
import android.graphics.Color;
import android.text.Editable;
import android.text.InputType;
import android.text.TextWatcher;
import android.text.method.PasswordTransformationMethod;
import android.util.TypedValue;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.EditorInfo;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.Spinner;
import android.widget.TextView;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;

/**
 * Created by huangfeihong on 2015/11/19.
 */
public class CreateAccountAdapter extends BaseAdapter {
    /**
     * 该接口用来与CreateAccountActivity进行交互
     */
    public static interface CreateAccountDelegate {
        int FORWARD = 1;
        int BACKWARD = -1;

        void scroll(int type);//该方法当点击”Next“按钮时执行

        void processForm(Account account);//当用户提交表单时，执行该方法
    }

    public static final String FULL_NAME_KEY = "fullname";
    public static final String EMAIL_KEY = "email";
    public static final String PASSWORD_KEY = "password";
    public static final String GENDER_KEY = "gender";
    public static final String CITY_KEY = "city";
    public static final String COUNTRY_KEY = "country";
    public static final String ZIP_KEY = "zipcode";

    private static final int FORMS_QTY = 4;
    private Context mContext;
    private LayoutInflater mInflator;
    private CreateAccountDelegate mDelegate;
    private HashMap<String, String> mFormData;
    private Account mAccount;

    public CreateAccountAdapter(Context ctx) {
        mContext = ctx;
        mInflator = LayoutInflater.from(ctx);
        mFormData = new HashMap<String, String>();
        mAccount = new Account();
    }

    @Override
    public int getCount() {
        return FORMS_QTY;
    }

    @Override
    public Object getItem(int position) {
        return null;
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    public void setDelegate(CreateAccountDelegate delegate) {
        mDelegate = delegate;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        if (convertView == null) {
            //填充自定义View
            convertView = mInflator.inflate(
                    R.layout.create_account_generic_row, parent, false);
        }
        //获取放置表单项的LinearLayout
        LinearLayout formLayout = (LinearLayout) convertView
                .findViewById(R.id.create_account_form);

        //�?后一页不显示”Next“按�?
        View nextButton = convertView
                .findViewById(R.id.create_account_next);
        if (position == FORMS_QTY - 1) {
            nextButton.setVisibility(View.GONE);
        } else {
            nextButton.setVisibility(View.VISIBLE);
        }
        if (mDelegate != null) {
            nextButton.setOnClickListener(new View.OnClickListener() {

                @Override
                public void onClick(View v) {
                    mDelegate.scroll(CreateAccountDelegate.FORWARD);
                }
            });
        }

        //仅在�?后一页显示提交表单按�?
        Button createButton = (Button) convertView
                .findViewById(R.id.create_account_create);
        if (position == FORMS_QTY - 1) {
            createButton.setOnClickListener(new View.OnClickListener() {

                @Override
                public void onClick(View v) {
                    processForm();
                }
            });

            createButton.setVisibility(View.VISIBLE);
        } else {
            createButton.setVisibility(View.GONE);
        }

        //根据页面位置填充LinearLayout
        switch (position) {
            case 0:
                populateFirstForm(formLayout);
                break;

            case 1:
                populateSecondForm(formLayout);
                break;

            case 2:
                populateThirdForm(formLayout);
                break;

            case 3:
                populateFourthForm(formLayout);
                break;
        }

        return convertView;
    }
    /**
     * 创建第一个表�?
     */
    private void populateFirstForm(LinearLayout formLayout) {
        formLayout.addView(createTitle(mContext
                .getString(R.string.account_create_full_name_title)));

        EditText nameEditText = createEditText(
                mContext.getString(R.string.account_create_full_name_hint),
                InputType.TYPE_CLASS_TEXT, EditorInfo.IME_ACTION_NEXT, false,
                FULL_NAME_KEY);

        if (mFormData.get(FULL_NAME_KEY) != null) {
            nameEditText.setText(mFormData.get(FULL_NAME_KEY));
        }

        formLayout.addView(nameEditText);
        formLayout.addView(createErrorView(FULL_NAME_KEY));

        formLayout.addView(createTitle(mContext
                .getString(R.string.account_create_email_title)));

        EditText emailEditText = createEditText(
                mContext.getString(R.string.account_create_email_hint),
                InputType.TYPE_CLASS_TEXT, EditorInfo.IME_ACTION_NEXT, true,
                EMAIL_KEY);

        if (mFormData.get(EMAIL_KEY) != null) {
            emailEditText.setText(mFormData.get(EMAIL_KEY));
        }

        formLayout.addView(emailEditText);
        formLayout.addView(createErrorView(EMAIL_KEY));
    }

    /**
     * 创建第二个表�?
     */
    private void populateSecondForm(LinearLayout formLayout) {
        formLayout.addView(createTitle(mContext
                .getString(R.string.account_create_password_title)));
        EditText passwordEditText = createEditText(
                mContext.getString(R.string.account_create_password_hint),
                InputType.TYPE_CLASS_TEXT, EditorInfo.IME_ACTION_DONE, false,
                PASSWORD_KEY);
        //设置输入框文字展示形�?
        passwordEditText.setTransformationMethod(new PasswordTransformationMethod());

        formLayout.addView(passwordEditText);
        formLayout.addView(createErrorView(PASSWORD_KEY));

        formLayout.addView(createTitle(mContext
                .getString(R.string.account_create_gender_title)));
        Spinner spinner = new Spinner(mContext);
        LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(
                ViewGroup.LayoutParams.FILL_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        params.bottomMargin = 17;
        spinner.setLayoutParams(params);

        final ArrayAdapter<CharSequence> adapter = ArrayAdapter
                .createFromResource(mContext, R.array.sexes_array,
                        android.R.layout.simple_spinner_item);
        spinner.setAdapter(adapter);
        spinner.setPrompt(mContext
                .getString(R.string.account_create_sex_spinner_prompt));
        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {

            @Override
            public void onItemSelected(AdapterView<?> parent, View view,
                                       int pos, long id) {

                mFormData.put(GENDER_KEY, adapter.getItem(pos).toString());
            }

            @Override
            public void onNothingSelected(AdapterView<?> arg0) {
            }
        });

        if (mFormData.get(GENDER_KEY) != null) {
            spinner.setSelection(adapter.getPosition(mFormData
                    .get(GENDER_KEY)));
        }
        formLayout.addView(spinner);
    }

    /**
     * 创建第三个表�?
     */
    private void populateThirdForm(LinearLayout formLayout) {
        formLayout.addView(createTitle(mContext
                .getString(R.string.account_create_city_title)));
        EditText cityEditText = createEditText(
                mContext.getString(R.string.account_create_city_hint),
                InputType.TYPE_CLASS_TEXT, EditorInfo.IME_ACTION_DONE, false,
                CITY_KEY);

        if (mFormData.get(CITY_KEY) != null) {
            cityEditText.setText(mFormData.get(CITY_KEY));
        }

        formLayout.addView(cityEditText);
        formLayout.addView(createErrorView(CITY_KEY));

        formLayout.addView(createTitle(mContext
                .getString(R.string.account_create_country_title)));

        Spinner spinner = new Spinner(mContext);
        LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(
                ViewGroup.LayoutParams.FILL_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        params.bottomMargin = 17;
        spinner.setLayoutParams(params);

        final ArrayAdapter<String> adapter = new ArrayAdapter<String>(
                mContext, android.R.layout.simple_spinner_item,
                Countries.COUNTRIES);
        spinner.setAdapter(adapter);
        spinner.setPrompt(mContext
                .getString(R.string.account_create_country_spinner_prompt));
        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {

            @Override
            public void onItemSelected(AdapterView<?> parent, View view,
                                       int pos, long id) {
                mFormData.put(COUNTRY_KEY, Countries.COUNTRY_CODES[pos]);
            }

            @Override
            public void onNothingSelected(AdapterView<?> arg0) {
            }
        });

        if (mFormData.get(COUNTRY_KEY) != null) {
            List<String> array = Arrays.asList(Countries.COUNTRY_CODES);
            spinner.setSelection(array.indexOf(mFormData.get(COUNTRY_KEY)));
        }
        formLayout.addView(spinner);
    }

    /**
     * 创建第四个表�?
     */
    private void populateFourthForm(LinearLayout formLayout) {
        formLayout.addView(createTitle(mContext
                .getString(R.string.account_create_zip_title)));
        EditText zipEditText = createEditText(
                mContext.getString(R.string.account_create_zip_hint),
                InputType.TYPE_CLASS_TEXT, EditorInfo.IME_ACTION_GO, true,
                ZIP_KEY);

        if (mFormData.get(ZIP_KEY) != null) {
            zipEditText.setText(mFormData.get(ZIP_KEY));
        }

        formLayout.addView(zipEditText);
        formLayout.addView(createErrorView(ZIP_KEY));
    }

    /**
     * 创建标题
     */
    private TextView createTitle(String text) {
        TextView ret = new TextView(mContext);
        ViewGroup.LayoutParams params = new ViewGroup.LayoutParams(ViewGroup.LayoutParams.FILL_PARENT,
                ViewGroup.LayoutParams.WRAP_CONTENT);
        ret.setLayoutParams(params);
        ret.setTextSize(TypedValue.COMPLEX_UNIT_SP, 17);
        ret.setTextColor(Color.BLACK);
        ret.setText(text);

        return ret;
    }

    /**
     * 创建输入�?
     */
    private EditText createEditText(String hint, int inputType,
                                    int imeOption, boolean shouldMoveToNext, final String key) {

        EditText ret = new EditText(mContext);
        LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(
                ViewGroup.LayoutParams.FILL_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        ret.setLayoutParams(params);
        ret.setHint(hint);

        ret.setInputType(inputType);//设置输入框输入模�?
        ret.setImeOptions(imeOption);//设置软键盘模�?

        if (shouldMoveToNext) {
            ret.setOnEditorActionListener(new TextView.OnEditorActionListener() {

                @Override
                public boolean onEditorAction(TextView v, int actionId,
                                              KeyEvent event) {
                    if (mDelegate != null) {
                        //软键盘中的Next按钮
                        if (EditorInfo.IME_ACTION_NEXT == actionId) {
                            mDelegate.scroll(CreateAccountDelegate.FORWARD);
                        } else {
                            processForm();
                        }

                        return true;
                    } else {
                        return false;
                    }
                }
            });
        }

        ret.addTextChangedListener(new TextWatcher() {

            @Override
            public void onTextChanged(CharSequence s, int start, int before,
                                      int count) {
            }

            @Override
            public void beforeTextChanged(CharSequence s, int start,
                                          int count, int after) {
            }

            @Override
            public void afterTextChanged(Editable s) {
                mFormData.put(key, s.toString());
            }
        });

        return ret;
    }

    /**
     * 提交表单
     */
    private void processForm() {
        if (mDelegate != null) {
            Account account = new Account();
            account.setCity(mFormData.get(CITY_KEY));
            account.setCountry(mFormData.get(COUNTRY_KEY));
            account.setEmail(mFormData.get(EMAIL_KEY));
            account.setGender(mFormData.get(GENDER_KEY));
            account.setName(mFormData.get(FULL_NAME_KEY));
            account.setPassword(mFormData.get(PASSWORD_KEY));
            account.setPostalCode(mFormData.get(ZIP_KEY));

            mDelegate.processForm(account);
        }
    }

    /**
     * 创建错误提醒文字
     */
    private TextView createErrorView(String key) {
        TextView ret = new TextView(mContext);
        LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(
                ViewGroup.LayoutParams.FILL_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        params.topMargin = 10;
        params.bottomMargin = 10;
        ret.setLayoutParams(params);
        ret.setTextColor(Color.RED);
        ret.setTextSize(TypedValue.COMPLEX_UNIT_SP, 10);

        HashMap<String, String> errors = mAccount.getErrors();
        if (errors != null && errors.containsKey(key)) {
            ret.setText(errors.get(key));
            ret.setVisibility(View.VISIBLE);
        } else {
            ret.setVisibility(View.INVISIBLE);
        }

        return ret;
    }

    /**
     * 展示错误信息
     */
    public void showErrors(Account account) {
        mAccount = account;
        mFormData.clear();
        mFormData.put(FULL_NAME_KEY, mAccount.getName());
        mFormData.put(EMAIL_KEY, mAccount.getEmail());
        mFormData.put(GENDER_KEY, mAccount.getGender());
        mFormData.put(CITY_KEY, mAccount.getCity());
        mFormData.put(COUNTRY_KEY, mAccount.getCountry());
        mFormData.put(ZIP_KEY, mAccount.getPostalCode());

        notifyDataSetChanged();
    }
}
