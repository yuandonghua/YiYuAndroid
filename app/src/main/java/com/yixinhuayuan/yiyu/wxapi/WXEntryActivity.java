package com.yixinhuayuan.yiyu.wxapi;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.SharedPreferences;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.os.Bundle;
import android.util.Log;
import android.widget.Toast;

import com.dim.widget.TextView;
import com.google.gson.Gson;
import com.jess.arms.base.BaseActivity;
import com.jess.arms.di.component.AppComponent;
import com.tencent.mm.opensdk.modelbase.BaseReq;
import com.tencent.mm.opensdk.modelbase.BaseResp;
import com.tencent.mm.opensdk.modelmsg.SendAuth;
import com.tencent.mm.opensdk.openapi.IWXAPI;
import com.tencent.mm.opensdk.openapi.IWXAPIEventHandler;
import com.tencent.mm.opensdk.openapi.WXAPIFactory;
import com.yixinhuayuan.yiyu.R;
import com.yixinhuayuan.yiyu.app.GlobalConfiguration;
import com.yixinhuayuan.yiyu.app.utils.GlobalGetOrPostRequest;
import com.yixinhuayuan.yiyu.app.utils.GlobalHttpClient;
import com.yixinhuayuan.yiyu.app.utils.jsoninstance.GetMyUserInfoJson;
import com.yixinhuayuan.yiyu.app.utils.jsoninstance.RegistryUserJson;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;

import butterknife.BindView;
import io.reactivex.Observer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;
import okhttp3.FormBody;
import okhttp3.Headers;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import okhttp3.ResponseBody;

/**
 * 在微信授权页面,授权成功后(点击授权按钮)会回调该Activity进行用户数据的获取
 */
public class WXEntryActivity extends BaseActivity implements IWXAPIEventHandler {
    /**
     * 获取微信数据的网络请求url
     */
    private String url = "https://api.weixin.qq.com/";
    /**
     * 微信的APP_SECRET
     */
    private static String APP_SECRET = "20f1852ff4223980097af4314563bc19";

    @BindView(R.id.wxInfo)
    TextView wxInfo;

    // 微信是否登录
    private static boolean IS_LOGIN = false;
    // IWXAPI 是第三方app和微信通信的openapi接口
    private IWXAPI wxapi;

    @Override
    public int initView(@Nullable Bundle savedInstanceState) {
        return R.layout.activity_wxentry;
    }

    @Override
    public void initData(@Nullable Bundle savedInstanceState) {
        // 通过WXAPIFactory工厂，获取IWXAPI的实例
        wxapi = WXAPIFactory.createWXAPI(this, GlobalConfiguration.WX_APP_ID, false);

        //注意：
        //第三方开发者如果使用透明界面来实现WXEntryActivity，需要判断handleIntent的返回值，
        // 如果返回值为false，则说明入参不合法未被SDK处理，应finish当前透明界面，
        // 避免外部通过传递非法参数的Intent导致停留在透明界面，引起用户的疑惑
        /*if (wxapi != null) {
            try {*/
        wxapi.handleIntent(getIntent(), this);
            /*} catch (Exception e) {
                e.printStackTrace();
                wxInfo.setText(e.getMessage());
            }
        }*/

    }

    // 微信发送请求到第三方应用时，会回调到该方法
    @Override
    public void onReq(BaseReq req) {

    }

    // 第三方应用发送到微信的请求处理后的响应结果，会回调到该方法

    /**
     * 第三方应用发送到微信的请求处理后的响应结果，会回调到该方法
     * <p>
     * 这里 我们在resp.errCode返回0时也就是发送成功时，拿到code并调用方法通过code获取access_token。
     *
     * @param resp
     */
    @Override
    public void onResp(BaseResp resp) {
        int result = 0;
        //Toast.makeText(this, "baseresp.getType = " + resp.getType(),Toast.LENGTH_SHORT).show();
        switch (resp.errCode) {
            // 发送成功
            case BaseResp.ErrCode.ERR_OK:
                result = R.string.errcode_success;
                // 拿到 code
                SendAuth.Resp sResp = (SendAuth.Resp) resp;
                String code = sResp.code;
                // 调用获取access_token的方法

                getAccessToken(code);

                break;
            // 发送取消
            case BaseResp.ErrCode.ERR_USER_CANCEL:
                result = R.string.errcode_cancel;
                break;
            //发送被拒绝
            case BaseResp.ErrCode.ERR_AUTH_DENIED:
                result = R.string.errcode_deny;
                break;
            // 不支持错误
            case BaseResp.ErrCode.ERR_UNSUPPORT:
                result = R.string.errcode_unsupported;
                break;
            default:
                // 发送返回
                result = R.string.errcode_unknown;
                break;
        }
    }

    @Override
    public void setupActivityComponent(@NonNull AppComponent appComponent) {

    }

    /**
     * 获取微信 access_token 和 openid
     *
     * @param code
     */
    private void getAccessToken(String code) {
        new Thread() {
            @Override
            public void run() {
                super.run();
                GlobalHttpClient.retrofit(url)
                        .create(GlobalGetOrPostRequest.class)
                        .getWXAccessToken(GlobalConfiguration.WX_APP_ID
                                , APP_SECRET
                                , code
                                , "authorization_code")
                        .subscribeOn(Schedulers.io())
                        .observeOn(AndroidSchedulers.mainThread())
                        .subscribe(new Observer<ResponseBody>() {
                            @Override
                            public void onSubscribe(Disposable d) {

                            }

                            @Override
                            public void onNext(ResponseBody responseBody) {

                                try {
                                    String accessTokenData = responseBody.string();
                                    JSONObject jsonObject = new JSONObject(accessTokenData);
                                    String access_token = jsonObject.getString("access_token");
                                    String openid = jsonObject.getString("openid");
                                    wxInfo.setText("access_token: " + access_token + "\n" + "openid: " + openid);
                                    getWXUserInfo(access_token, openid);
                                } catch (IOException e) {
                                    e.printStackTrace();
                                } catch (JSONException e) {
                                    e.printStackTrace();
                                }

                            }

                            @Override
                            public void onError(Throwable e) {
                                Toast.makeText(WXEntryActivity.this, "登录失败", Toast.LENGTH_SHORT).show();
                            }

                            @Override
                            public void onComplete() {
                                Toast.makeText(WXEntryActivity.this, "登录成功", Toast.LENGTH_SHORT).show();
                            }
                        });
            }
        }.start();
    }

    /**
     * 获取微信UserInfo(用户信息)
     *
     * @param access_token
     * @param openid
     */
    public void getWXUserInfo(String access_token, String openid) {
        new Thread() {
            @Override
            public void run() {
                super.run();
                GlobalHttpClient.retrofit(url)
                        .create(GlobalGetOrPostRequest.class)
                        .getWXUserInfo(access_token, openid)
                        .subscribeOn(Schedulers.io())
                        .observeOn(AndroidSchedulers.mainThread())
                        .subscribe(new Observer<ResponseBody>() {
                            @Override
                            public void onSubscribe(Disposable d) {

                            }

                            @Override
                            public void onNext(ResponseBody responseBody) {
                                try {
                                    String userInfoData = responseBody.string();
                                    //String userInfo = parseJsonUserInfo(userInfoData);
                                    WXRegistryUser(new JSONObject(userInfoData).getString("openid"));
                                    //wxInfo.setText(userInfo);

                                } catch (IOException e) {
                                    e.printStackTrace();
                                } catch (JSONException e) {
                                    e.printStackTrace();
                                }
                            }

                            @Override
                            public void onError(Throwable e) {
                                Toast.makeText(WXEntryActivity.this, "登录失败", Toast.LENGTH_SHORT).show();
                            }

                            @Override
                            public void onComplete() {
                                Toast.makeText(WXEntryActivity.this, "登录成功", Toast.LENGTH_SHORT).show();
                            }
                        });
            }
        }.start();

    }

    /**
     * 通过获取到的微信的OpenId和其他用户数据进行登录注册并获取用户信息
     *
     * @param openId
     */
    private void WXRegistryUser(String openId) {


        new Thread() {
            @Override
            public void run() {
                super.run();

                OkHttpClient httpClient = new OkHttpClient();
                FormBody.Builder registryUserBody = new FormBody.Builder();
                registryUserBody.add("account", openId)
                        .add("type", "3");
                // 请求注册登录接口
                Request request = new Request.Builder()
                        .url("http://yy.363626256.top/api/login")
                        .post(registryUserBody.build())
                        .build();
                try {
                    // 得到请求登录注册接口返回的数据
                    Response RegistryResponse = httpClient.newCall(request).execute();
                    // 解析注册或登录返回的数据
                    RegistryUserJson registrUserData = new Gson().fromJson(RegistryResponse.body().string(), RegistryUserJson.class);
                    // 获取到注册或登录的请求头参数
                    String authorization = RegistryResponse.headers().get("Authorization");
                    Log.d("WXRegistryUser", "注册用户请求头信息:" + authorization);

                    // 通过获取到的请求头参数进行获取用户数据请求
                    FormBody.Builder getUserInfoBody = new FormBody.Builder();
                    Request request1 = new Request.Builder()
                            .url("http://yy.363626256.top/api/me")
                            .post(getUserInfoBody.build())
                            .addHeader("Authorization", authorization)
                            .build();
                    // 获取到返回的用户数据并解析
                    Response response1 = httpClient.newCall(request).execute();
                    GetMyUserInfoJson getMyUserInfo = new Gson().fromJson(response1.body().string()
                            , GetMyUserInfoJson.class);
                    GetMyUserInfoJson.DataBean userInfo = getMyUserInfo.getData();

                    Log.d("WXRegistryUser", "获取到的用户数据Code:" + getMyUserInfo.getCode());

                    // 保存登录状态户用户数据
                    @SuppressLint("WrongConstant")
                    SharedPreferences sp = getSharedPreferences(getBaseContext().getPackageName()
                            , Context.MODE_APPEND);
                    SharedPreferences.Editor edit = sp.edit();
                    edit.putBoolean("is_login", getMyUserInfo.isStatus());
                    edit.putString("account", userInfo.getAccount());
                    edit.putInt("id", userInfo.getId());
                    edit.putInt("sta", userInfo.getStatus());
                    edit.putInt("type", userInfo.getType());
                    edit.putInt("user_id", userInfo.getUser_id());
                    edit.commit();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }.start();
    }
}
