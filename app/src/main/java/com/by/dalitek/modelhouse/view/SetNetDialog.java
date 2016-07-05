package com.by.dalitek.modelhouse.view;

import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.view.View;

import app.univs.cn.myapplication.R;

/**
 *
 * @author zhxr
 * @Description 网络设置 对话框
 * @Name SetNetDialog.java
 * @param
 * @time: 2015年2月13日 上午10:45:57
 */
public class SetNetDialog extends Dialog {

    Context context;
    public SetNetDialog(Context context) {
        super(context);
        // TODO Auto-generated constructor stub
        this.context = context;
    }
    public SetNetDialog(Context context, int theme){
        super(context, theme);
        this.context = context;
    }
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        // TODO Auto-generated method stub
        super.onCreate(savedInstanceState);
        this.setContentView(R.layout.dialog_setnet);
        getWindow().getDecorView().setSystemUiVisibility(View.GONE);// 4.0pad去掉隐藏状态栏
    }



}