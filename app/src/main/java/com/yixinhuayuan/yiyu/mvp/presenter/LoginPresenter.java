package com.yixinhuayuan.yiyu.mvp.presenter;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.Application;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.text.TextUtils;
import android.util.Log;
import android.widget.Toast;

import com.google.gson.Gson;
import com.jess.arms.integration.AppManager;
import com.jess.arms.di.scope.ActivityScope;
import com.jess.arms.mvp.BasePresenter;
import com.jess.arms.http.imageloader.ImageLoader;

import me.jessyan.rxerrorhandler.core.RxErrorHandler;
import okhttp3.FormBody;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

import javax.inject.Inject;

import com.jess.arms.utils.ArmsUtils;
import com.sina.weibo.sdk.WbSdk;
import com.sina.weibo.sdk.auth.AuthInfo;
import com.sina.weibo.sdk.auth.Oauth2AccessToken;
import com.sina.weibo.sdk.auth.WbAuthListener;
import com.sina.weibo.sdk.auth.WbConnectErrorMessage;
import com.sina.weibo.sdk.auth.sso.SsoHandler;
import com.tencent.connect.UserInfo;
import com.tencent.connect.common.Constants;
import com.tencent.mm.opensdk.modelmsg.SendAuth;
import com.tencent.mm.opensdk.openapi.IWXAPI;
import com.tencent.mm.opensdk.openapi.WXAPIFactory;
import com.tencent.tauth.IUiListener;
import com.tencent.tauth.Tencent;
import com.yixinhuayuan.yiyu.app.GlobalConfiguration;
import com.yixinhuayuan.yiyu.app.utils.jsoninstance.LoginDataJson;
import com.yixinhuayuan.yiyu.mvp.contract.LoginContract;
import com.yixinhuayuan.yiyu.mvp.model.QQLoginModel;
import com.yixinhuayuan.yiyu.mvp.ui.activity.LoginActivity;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;

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
        Log.d("LoginPresenter", "QQ登录开始");
        mTencent = tencent;
        if (!mTencent.isSessionValid()) {
            Log.d("LoginPresenter", "调用QQ登陆API");
            qqLoginFlag = 1;
            mTencent.login((Activity) mRootView, "all", mIUiListener);
        } else {
            ArmsUtils.snackbarText("QQ登陆实例对象无效");
            Log.d("LoginPresenter", "QQ登陆实例对象无效");
        }
    }

    /**
     * 获取QQ用户信息
     */
    public void updateQQUserInfo() {
        Log.d("LoginPresenter", "获取QQ用户信息");
        if (mTencent != null && mTencent.isSessionValid()) {
            Log.d("LoginPresenter", "获取QQ用户信息A");
            qqLoginFlag = 2;
            UserInfo mInfo = new UserInfo((Activity) mRootView, mTencent.getQQToken());
            mInfo.getOpenId(mIUiListener);
            mInfo.getUserInfo(mIUiListener);
        }
    }

    /**
     * QQ登陆，获取用户信息回调监听
     */
    public IUiListener mIUiListener = new QQLoginModel() {

        @Override
        protected void doComplete(JSONObject values) {
            Log.d("LoginPresenter", "QQ登陆,获取用户信息回调监听");
            if (qqLoginFlag == 1) {
                ArmsUtils.snackbarText("QQ登陆成功");
                initOpenidAndToken(values);
                updateQQUserInfo();
                Log.d("LoginPresenter", "QQ登陆成功");
            } else if (qqLoginFlag == 2) {
                mRootView.showUserInfo(values.toString());
                Log.d("LoginPresenter", "QQ登陆成功,处理返回的数据");
                try {
                    String openid = values.getString("openid");
                    //QQRegistryUser(openid);

                } catch (
                        JSONException e) {
                    e.printStackTrace();
                }

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
            Log.d("LoginPresenter", "处理QQ返回的数据");
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

    /**
     * QQ登录
     * 初始化 Tencent
     *
     * @param mTencent
     * @param context
     * @return
     */
    public Tencent initTencent(Tencent mTencent, Context context) {
        Tencent tencent = null;
        if (mTencent == null) {
            tencent = Tencent.createInstance(GlobalConfiguration.QQ_APP_ID, context);
        }
        return tencent;
    }

    /**
     * 微信登录
     * 初始化 IWXAPI
     *
     * @param mIwxapi
     * @param context
     * @return
     */
    public IWXAPI initIWXAPI(IWXAPI mIwxapi, Context context) {
        IWXAPI iwxapi = null;
        if (mIwxapi == null) {
            iwxapi = WXAPIFactory.createWXAPI(context, GlobalConfiguration.WX_APP_ID);
        }
        return iwxapi;
    }

    /**
     * 微博登录
     * 初始化 WbSdk
     *
     * @param context
     */
    public void initWbSdk(Context context) {
        WbSdk.install(context,
                new AuthInfo(context,
                        GlobalConfiguration.WB_APP_KEY,
                        GlobalConfiguration.REDIRECT_URL,
                        GlobalConfiguration.SCOPE));
    }

    /**
     * 用于请求微博用户数的Url
     */
    private String wbUrl = "https://api.weibo.com/";

    /**
     * 微博登录
     * 登录
     *
     * @param mSsoHandler
     */
    public void WBLogin(SsoHandler mSsoHandler) {
        mSsoHandler.authorize(new WbAuthListener() {
            String TAG = "WBLogin";

            @Override
            public void onSuccess(Oauth2AccessToken oauth2AccessToken) {
                if (oauth2AccessToken.isSessionValid()) {
                    Log.d(TAG, "onSuccess:------>Oauth2AccessToken:" + oauth2AccessToken);
                    String token = oauth2AccessToken.getToken();
                    String uid = oauth2AccessToken.getUid();
                    Log.d(TAG, "token is:" + token + "   uid is:" + uid);

                    new Thread() {
                        @Override
                        public void run() {
                            super.run();
                            OkHttpClient okHttpClient = new OkHttpClient();

                            Request request = new Request.Builder()
                                    .url("https://api.weibo.com/2/users/show.json?access_token=" +
                                            token + "&uid=" + uid)
                                    .build();
                            try {
                                Response response = okHttpClient.newCall(request).execute();
                                String string = response.body().string();
                                Log.d(TAG, "微博用户数据: " + string);
                                /*
                                 * 微博登录没有请求成功,原因:
                                 * applications over the unaudited use restrictions!
                                 * 应用程序超过未经审核的使用限制!
                                 */
                            } catch (IOException e) {
                                e.printStackTrace();
                            }

                        }
                    }.start();


                }

            }

            @Override
            public void cancel() {
                Log.d(TAG, "cancel: ------->?????");
            }

            @Override
            public void onFailure(WbConnectErrorMessage wbConnectErrorMessage) {
                Log.d(TAG, "onFailure: ---------->WbConnectErrorMessage:" + wbConnectErrorMessage);
            }
        });
    }

    /**
     * 通过QQ用户数据,向艺语服务器发出 登录/注册请求
     */
    public void QQRegistryUser(String openId) {
        Log.d("LoginPresenter", "请求登录接口");
        new Thread() {
            @Override
            public void run() {
                super.run();
                OkHttpClient httpClient = new OkHttpClient();
                FormBody.Builder qqLoginBody = new FormBody.Builder();
                qqLoginBody.add("account", openId)
                        .add("type", "2")
                        .add("nickname", "QQ一")
                        .add("sex", "0")
                        .add("photo", "www.baidu.com");
                // 请求服务的注册登录接口
                Request qqLogin = new Request.Builder()
                        .url("http://yy.363626256.top/api/login")
                        .post(qqLoginBody.build())
                        .build();
                try {
                    // 获取注册登录接口返回的数据,并解析处理
                    Response loginData = httpClient.newCall(qqLogin).execute();
                    LoginDataJson loginDataJson = new Gson().fromJson(loginData.body().string(), LoginDataJson.class);
                    // 获取注册登录接口的的请求头信息用于获取用户信息使用
                    String authorization = loginData.headers().get("Authorization");
                    // 请求获取用户信息的接口
                    FormBody.Builder userInfoBody = new FormBody.Builder();
                    Request request1 = new Request.Builder()
                            .url("http://yy.363626256.top/api/me")
                            .post(userInfoBody.build())
                            .addHeader("Authorization", authorization)
                            .build();
                    // 获取请求获取用户新的接口返回的数据并解析处理
                    Response getUserInfo = httpClient.newCall(request1).execute();
                    Log.d("LoginPresenter", "QQ用户数据getUserInfo is: " + getUserInfo);
                    // Json解析
                    JSONObject jsonObject = new JSONObject(getUserInfo.body().string());
                    Log.d("LoginPresenter", "Json解析的数据1 is: " + jsonObject);
                    JSONObject object = jsonObject.getJSONObject("data");
                    Log.d("LoginPresenter", "Json解析的数据2 is: " + object);
                    // Gson解析
                    //UserInfoJson userInfoJson = new Gson().fromJson(getUserInfo.body().string(), UserInfoJson.class);
                    //Log.d("LoginPresenter", "Gson解析的数据1 is: " + userInfoJson);
                    //UserInfoJson.DataBean userInfo = userInfoJson.getData();
                    // Log.d("LoginPresenter", "Gson解析的数据2 is: " + userInfo);
                    Log.d("LoginPresenter", "请求状态码: " + jsonObject.getInt("code"));
                    // 保存登录状态户用户数据
                    LoginActivity context = (LoginActivity) LoginPresenter.this.mRootView;
                    @SuppressLint("WrongConstant")
                    SharedPreferences sp = context.getSharedPreferences(context.getPackageName(), Context.MODE_APPEND);
                    SharedPreferences.Editor edit = sp.edit();
                    edit.putString("authorization", authorization);
                    /*if (userInfo != null) {
                        Log.d("LoginPresenter", "Gson执行");
                        edit.putBoolean("is_login", userInfoJson.isStatus());// 是否登录成功
                        edit.putInt("id", userInfo.getId());// ID
                        edit.putInt("user_id", userInfo.getUser_id());// USER_ID
                        edit.putString("account", userInfo.getAccount());
                        edit.putInt("type", userInfo.getType());
                        edit.putInt("status", userInfo.getStatus());
                        edit.putString("nick_name", userInfo.getNickname());// 昵称
                        edit.putString("header", userInfo.getPhoto());// 头像
                        edit.putInt("fans", userInfo.getFans());// 粉丝数
                        edit.putInt("star", userInfo.getStar());// 关注数
                        edit.putInt("sex", userInfo.getSex());// 性别
                        edit.putString("introduce", userInfo.getIntroduce());// 个人介绍
                        edit.commit();
                    } else {*/
                    Log.d("LoginPresenter", "Json执行");
                    edit.putBoolean("is_login", jsonObject.getBoolean("status"));// 是否登录成功
                    LoginActivity.is_login = jsonObject.getBoolean("status");
                    edit.putInt("id", object.getInt("id"));// ID
                    edit.putInt("user_id", object.getInt("user_id"));// USER_ID
                    edit.putString("account", object.getString("account"));
                    edit.putInt("type", object.getInt("type"));
                    edit.putInt("status", object.getInt("status"));
                    edit.putString("nick_name", object.getString("nickname"));// 昵称
                    edit.putString("header", object.getString("photo"));// 头像
                    edit.putInt("fans", object.getInt("fans"));// 粉丝数
                    edit.putInt("star", object.getInt("star"));// 关注数
                    edit.putInt("sex", object.getInt("sex"));// 性别
                    edit.putString("introduce", object.getString("introduce"));// 个人介绍
                    edit.commit();
                    //}
                   /* Intent intent = new Intent(((LoginActivity) mRootView), MainActivity.class);
                    ((LoginActivity) mRootView).setResult(102, intent);
                    ((LoginActivity) mRootView).finish();*/
                } catch (IOException e) {
                    e.printStackTrace();
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        }.start();

    }

}

