package com.yixinhuayuan.yiyu.mvp.model;

import android.text.TextUtils;

import com.jess.arms.utils.ArmsUtils;
import com.tencent.connect.common.Constants;
import com.tencent.tauth.IUiListener;
import com.tencent.tauth.UiError;

import org.json.JSONObject;

import timber.log.Timber;

/**
 * Description:
 * Author: 袁东华
 * Date: 2019/1/16
 */
public class QQLoginModel implements IUiListener {

    /**
     * 登陆完成
     *
     * @param response
     */
    @Override
    public void onComplete(Object response) {
        if (null == response) {
            ArmsUtils.snackbarText("返回为空,登录失败");
            return;
        }
        JSONObject jsonResponse = (JSONObject) response;
        if (null != jsonResponse && jsonResponse.length() == 0) {
            ArmsUtils.snackbarText("返回为空,登录失败");
            return;
        }

        doComplete((JSONObject) response);
    }

    /**
     * 处理登陆数据
     *
     * @param values
     */
    protected void doComplete(JSONObject values) {

    }

    @Override
    public void onError(UiError e) {
        ArmsUtils.snackbarTextWithLong("登陆失败:" + e.errorDetail);
    }

    @Override
    public void onCancel() {
        ArmsUtils.snackbarText("取消登陆");
    }


}
