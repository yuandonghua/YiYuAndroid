package com.yixinhuayuan.yiyu.mvp.ui.view.dialog;

import android.app.AlertDialog;
import android.content.Context;
import android.view.Gravity;
import android.view.WindowManager;

import com.yixinhuayuan.yiyu.R;

public class MyDialog extends AlertDialog {
    private Context context;

    public MyDialog(Context context) {
        super(context, R.style.MyDialog);
        this.context = context;
    }

    protected MyDialog(Context context, boolean cancelable, OnCancelListener cancelListener) {
        super(context, cancelable, cancelListener);
        this.context = context;
    }

    protected MyDialog(Context context, int themeResId) {
        super(context, themeResId);
        this.context = context;
    }


    @Override
    public void show() {
        super.show();

        /**
         * 设置宽度全屏，要设置在show的后面
         */
        WindowManager.LayoutParams layoutParams = getWindow().getAttributes();
        layoutParams.gravity = Gravity.BOTTOM;
        layoutParams.width = WindowManager.LayoutParams.MATCH_PARENT;
        layoutParams.height = WindowManager.LayoutParams.MATCH_PARENT;
        getWindow().getDecorView().setPadding(0, 0, 0, 0);
        getWindow().setAttributes(layoutParams);

    }
}
