package com.yixinhuayuan.yiyu.mvp.ui.activity;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.text.TextUtils;
import android.util.Log;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.jess.arms.base.BaseActivity;
import com.jess.arms.di.component.AppComponent;
import com.jess.arms.integration.ConfigModule;
import com.jess.arms.utils.ArmsUtils;

import com.sina.weibo.sdk.WbSdk;
import com.sina.weibo.sdk.auth.AuthInfo;
import com.sina.weibo.sdk.auth.sso.SsoHandler;
import com.tencent.connect.common.Constants;
import com.tencent.mm.opensdk.modelmsg.SendAuth;
import com.tencent.mm.opensdk.modelmsg.SendMessageToWX;
import com.tencent.mm.opensdk.modelmsg.WXMediaMessage;
import com.tencent.mm.opensdk.modelmsg.WXTextObject;
import com.tencent.mm.opensdk.openapi.IWXAPI;
import com.tencent.mm.opensdk.openapi.WXAPIFactory;
import com.tencent.tauth.IUiListener;
import com.tencent.tauth.Tencent;
import com.yixinhuayuan.yiyu.app.GlobalConfiguration;
import com.yixinhuayuan.yiyu.di.component.DaggerLoginComponent;
import com.yixinhuayuan.yiyu.mvp.contract.LoginContract;
import com.yixinhuayuan.yiyu.mvp.model.QQLoginModel;
import com.yixinhuayuan.yiyu.mvp.presenter.LoginPresenter;

import com.yixinhuayuan.yiyu.R;
import com.yixinhuayuan.yiyu.mvp.ui.fragment.MyFragment;
import com.yixinhuayuan.yiyu.wxapi.WXEntryActivity;


import org.json.JSONObject;

import androidx.navigation.Navigation;
import butterknife.BindView;
import butterknife.OnClick;
import timber.log.Timber;

import static com.jess.arms.utils.Preconditions.checkNotNull;
import static com.tencent.mm.opensdk.modelmsg.SendMessageToWX.Req.WXSceneTimeline;


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
public class LoginActivity extends BaseActivity<LoginPresenter> implements LoginContract.View {

    public static boolean is_login;
    /**
     * 返回按钮
     */
    @BindView(R.id.btnBack)
    ImageView btnBack;
    /**
     * QQ登陆按钮
     */
    @BindView(R.id.btnLoginQQ)
    LinearLayout btnLoginQQ;
    /**
     * 微信登陆按钮
     */
    @BindView(R.id.btnLoginWX)
    LinearLayout btnLoginWX;
    /**
     * 微博登陆按钮
     */
    @BindView(R.id.btnLoginWB)
    LinearLayout btnLoginWB;
    /**
     * 腾讯API的实例对象
     */
    private Tencent mTencent;
    /**
     * 用户信息
     */
    @BindView(R.id.userInfo)
    TextView userInfo;
    /**
     * 微信API实例对象
     */
    private IWXAPI iwxapi;

    private SsoHandler mSsoHandler;


    @Override
    public void setupActivityComponent(@NonNull AppComponent appComponent) {
        DaggerLoginComponent //如找不到该类,请编译一下项目
                .builder()
                .appComponent(appComponent)
                .view(this)
                .build()
                .inject(this);
    }

    @Override
    public int initView(@Nullable Bundle savedInstanceState) {

        return R.layout.activity_login; //如果你不需要框架帮你设置 setContentView(id) 需要自行设置,请返回 0
    }

    @Override
    public void initData(@Nullable Bundle savedInstanceState) {

        // 初始化 腾讯Tencent
        mTencent = mPresenter.initTencent(mTencent, this);
        // 初始化 微信IWXAPI
        iwxapi = mPresenter.initIWXAPI(iwxapi, this);
        // 初始化 微博WbSdk
        AuthInfo mAuthInfo = new AuthInfo(this
                , GlobalConfiguration.WB_APP_KEY
                , GlobalConfiguration.REDIRECT_URL
                , GlobalConfiguration.SCOPE);
        WbSdk.install(this, mAuthInfo);
        // 初始化 微博SsoHandler
        if (mSsoHandler == null) {
            mSsoHandler = new SsoHandler(LoginActivity.this);
        }

    }


    @OnClick(R.id.btnBack)
    void back() {
        Timber.i("点击返回按钮");
        finish();
    }

    /**
     * 点击QQ登陆
     */
    @OnClick(R.id.btnLoginQQ)
    void loginQQ() {
        mPresenter.QQLogin(mTencent);
    }

    /**
     * 点击微信登陆
     */
    @OnClick(R.id.btnLoginWX)
    void loginWX() {
        mPresenter.WXLogin(iwxapi);
    }

    /**
     * 点击微博登陆
     */
    @OnClick(R.id.btnLoginWB)
    void loginWB() {
        mPresenter.WBLogin(mSsoHandler);
    }

    @Override
    public void showLoading() {

    }

    @Override
    public void hideLoading() {

    }

    @Override
    public void showMessage(@NonNull String message) {
        checkNotNull(message);
        ArmsUtils.snackbarText(message);
    }

    @Override
    public void launchActivity(@NonNull Intent intent) {
        checkNotNull(intent);
        ArmsUtils.startActivity(intent);
    }

    @Override
    public void killMyself() {
        finish();
    }

    @Override
    public void showUserInfo(String str) {
        userInfo.setText(str);
        GlobalConfiguration.LOGIN = true;
        // finish();
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        //QQ登陆需要
        if (requestCode == Constants.REQUEST_LOGIN ||
                requestCode == Constants.REQUEST_APPBAR) {
            Tencent.onActivityResultData(requestCode, resultCode, data, mPresenter.mIUiListener);
        }
        //新浪 login
        if (mSsoHandler != null) {
            mSsoHandler.authorizeCallBack(requestCode, resultCode, data);
        }
        super.onActivityResult(requestCode, resultCode, data);

    }

    @Override
    protected void onRestart() {
        Log.d(TAG, "onRestart: 关闭LoginActivity");
        Intent intent = new Intent(this,MainActivity.class);
        setResult(102, intent);
        this.killMyself();
        super.onRestart();
    }

    /**
     * 关闭LoginActivity
     */
    public static void finishLogin() {
        new LoginActivity().finish();
    }
}
