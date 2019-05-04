package com.yixinhuayuan.yiyu.app.utils.userinfo;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.SharedPreferences;

public class SPUserInfo {
    private static Context context = null;

    public static void setContext(Context context) {
        SPUserInfo.context = context;
    }

    public static void delContext() {
        SPUserInfo.context = null;
    }

    public static SharedPreferences spUserInfo() {
        // 拿到本地用户数据
        @SuppressLint("WrongConstant")
        SharedPreferences sp = SPUserInfo.context.getSharedPreferences(SPUserInfo.context.getPackageName(), Context.MODE_APPEND);
        return sp;
    }

}
