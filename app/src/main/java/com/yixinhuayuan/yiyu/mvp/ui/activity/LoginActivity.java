package com.yixinhuayuan.yiyu.mvp.ui.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.text.TextUtils;
import android.util.Log;
import android.widget.ImageView;
import android.widget.TextView;

import com.jess.arms.base.BaseActivity;
import com.jess.arms.di.component.AppComponent;
import com.jess.arms.integration.ConfigModule;
import com.jess.arms.utils.ArmsUtils;

import com.tencent.connect.common.Constants;
import com.tencent.tauth.IUiListener;
import com.tencent.tauth.Tencent;
import com.yixinhuayuan.yiyu.app.GlobalConfiguration;
import com.yixinhuayuan.yiyu.di.component.DaggerLoginComponent;
import com.yixinhuayuan.yiyu.mvp.contract.LoginContract;
import com.yixinhuayuan.yiyu.mvp.model.QQLoginModel;
import com.yixinhuayuan.yiyu.mvp.presenter.LoginPresenter;

import com.yixinhuayuan.yiyu.R;


import org.json.JSONObject;

import androidx.navigation.Navigation;
import butterknife.BindView;
import butterknife.OnClick;
import timber.log.Timber;

import static com.jess.arms.utils.Preconditions.checkNotNull;


/**
 * ================================================
 * Description:
 * <p>
 * Created by MVPArmsTemplate on 01/16/2019 16:20
 * <a href="mailto:jess.yan.effort@gmail.com">Contact me</a>
 * <a href="https://github.com/JessYanCoding">Follow me</a>
 * <a href="https://github.com/JessYanCoding/MVPArms">Star me</a>
 * <a href="https://github.com/JessYanCoding/MVPArms/wiki">See me</a>
 * <a href="https://github.com/JessYanCoding/MVPArmsTemplate">模版请保持更新</a>
 * ================================================
 */
public class LoginActivity extends BaseActivity<LoginPresenter> implements LoginContract.View {
    /**
     * 返回按钮
     */
    @BindView(R.id.btnBack)
    ImageView btnBack;
    /**
     * QQ登陆按钮
     */
    @BindView(R.id.btnLoginQQ)
    ImageView btnLoginQQ;
    /**
     * 微信登陆按钮
     */
    @BindView(R.id.btnLoginWX)
    ImageView btnLoginWX;
    /**
     * 微博登陆按钮
     */
    @BindView(R.id.btnLoginWB)
    ImageView btnLoginWB;
    /**
     * 腾讯API的实例对象
     */
    private Tencent mTencent;
    /**
     * 用户信息
     */
    @BindView(R.id.userInfo)
    TextView userInfo;


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
        if (mTencent == null) {
            mTencent = Tencent.createInstance(GlobalConfiguration.QQ_APP_ID, this);
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
    }

    /**
     * 点击微博登陆
     */
    @OnClick(R.id.btnLoginWB)
    void loginWB() {
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
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        //QQ登陆需要
        if (requestCode == Constants.REQUEST_LOGIN ||
                requestCode == Constants.REQUEST_APPBAR) {
            Tencent.onActivityResultData(requestCode, resultCode, data, mPresenter.mIUiListener);
        }
        super.onActivityResult(requestCode, resultCode, data);
    }


}
