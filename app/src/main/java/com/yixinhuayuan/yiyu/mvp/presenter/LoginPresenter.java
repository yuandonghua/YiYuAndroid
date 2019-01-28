package com.yixinhuayuan.yiyu.mvp.presenter;

import android.app.Activity;
import android.app.Application;
import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Message;
import android.text.TextUtils;
import android.widget.Toast;

import com.jess.arms.http.OkHttpUrlLoader;
import com.jess.arms.integration.AppManager;
import com.jess.arms.di.scope.ActivityScope;
import com.jess.arms.mvp.BasePresenter;
import com.jess.arms.http.imageloader.ImageLoader;

import me.jessyan.rxerrorhandler.core.RxErrorHandler;
import timber.log.Timber;

import javax.inject.Inject;

import com.jess.arms.utils.ArmsUtils;
import com.tencent.connect.UserInfo;
import com.tencent.connect.common.Constants;
import com.tencent.mm.opensdk.modelbase.BaseReq;
import com.tencent.mm.opensdk.modelmsg.SendAuth;
import com.tencent.mm.opensdk.openapi.IWXAPI;
import com.tencent.tauth.IUiListener;
import com.tencent.tauth.Tencent;
import com.tencent.tauth.UiError;
import com.yixinhuayuan.yiyu.app.GlobalConfiguration;
import com.yixinhuayuan.yiyu.mvp.contract.LoginContract;
import com.yixinhuayuan.yiyu.mvp.model.QQLoginModel;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.Scanner;


/**
 * ================================================
 * Description:
 * <p>
 * Created by MVPArmsTemplate on 01/16/2019 16:20
 * <parseJsonUserInfo href="mailto:jess.yan.effort@gmail.com">Contact me</parseJsonUserInfo>
 * <parseJsonUserInfo href="https://github.com/JessYanCoding">Follow me</parseJsonUserInfo>
 * <parseJsonUserInfo href="https://github.com/JessYanCoding/MVPArms">Star me</parseJsonUserInfo>
 * <parseJsonUserInfo href="https://github.com/JessYanCoding/MVPArms/wiki">See me</parseJsonUserInfo>
 * <parseJsonUserInfo href="https://github.com/JessYanCoding/MVPArmsTemplate">模版请保持更新</parseJsonUserInfo>
 * ================================================
 */
@ActivityScope
public class LoginPresenter extends BasePresenter<LoginContract.Model, LoginContract.View> {
    @Inject
    RxErrorHandler mErrorHandler;
    @Inject
    Application mApplication;
    @Inject
    ImageLoader mImageLoader;
    @Inject
    AppManager mAppManager;
    /**
     * 腾讯API的实例对象
     */
    private Tencent mTencent;
    /**
     * QQ登陆标识，1=登陆，2=获取用户信息
     */
    private int qqLoginFlag = 0;

    @Inject
    public LoginPresenter(LoginContract.Model model, LoginContract.View rootView) {
        super(model, rootView);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        this.mErrorHandler = null;
        this.mAppManager = null;
        this.mImageLoader = null;
        this.mApplication = null;
    }

    /**
     * 调用QQ登陆API
     *
     * @param tencent
     */
    public void QQLogin(Tencent tencent) {

        mTencent = tencent;
        if (!mTencent.isSessionValid()) {
            qqLoginFlag = 1;
            mTencent.login((Activity) mRootView, "all", mIUiListener);
        } else {
            ArmsUtils.snackbarText("QQ登陆实例对象无效");
        }
    }

    /**
     * 获取QQ用户信息
     */
    public void updateQQUserInfo() {
        if (mTencent != null && mTencent.isSessionValid()) {
            qqLoginFlag = 2;
            UserInfo mInfo = new UserInfo((Activity) mRootView, mTencent.getQQToken());
            mInfo.getUserInfo(mIUiListener);
        }
    }

    /**
     * QQ登陆，获取用户信息回调监听
     */
    public IUiListener mIUiListener = new QQLoginModel() {
        @Override
        protected void doComplete(JSONObject values) {
            if (qqLoginFlag == 1) {
                ArmsUtils.snackbarText("QQ登陆成功");
                initOpenidAndToken(values);
                updateQQUserInfo();
            } else if (qqLoginFlag == 2) {
                mRootView.showUserInfo(values.toString());

            }


        }
    };

    /**
     * 处理qq登陆成功后返回的数据
     *
     * @param jsonObject
     */
    public void initOpenidAndToken(JSONObject jsonObject) {
        try {
            String token = jsonObject.getString(Constants.PARAM_ACCESS_TOKEN);
            String expires = jsonObject.getString(Constants.PARAM_EXPIRES_IN);
            String openId = jsonObject.getString(Constants.PARAM_OPEN_ID);
            if (!TextUtils.isEmpty(token) && !TextUtils.isEmpty(expires)
                    && !TextUtils.isEmpty(openId)) {
                mTencent.setAccessToken(token, expires);
                mTencent.setOpenId(openId);
            }
        } catch (Exception e) {
        }
    }

    /**
     * 调用微信登录API
     *
     * @param iwxapi
     */
    public void WXLogin(IWXAPI iwxapi) {
        iwxapi.registerApp(GlobalConfiguration.WX_APP_ID);
        // 判断是否安装微信
        if (iwxapi.isWXAppInstalled()) {
            SendAuth.Req req = new SendAuth.Req();
            req.scope = "snsapi_userinfo";
            req.state = "1";
            // 发送请求或响应到微信
            iwxapi.sendReq(req);

        } else {
            Toast.makeText((Activity) mRootView, "请下载最新版微信", Toast.LENGTH_SHORT).show();
        }
    }

}
