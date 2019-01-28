package com.yixinhuayuan.yiyu.wxapi;

import android.os.Handler;
import android.os.Message;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.os.Bundle;
import android.util.Log;
import android.widget.Toast;

import com.dim.widget.TextView;
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
import com.yixinhuayuan.yiyu.wxapi.wxhttp.WXHttpClient;
import com.yixinhuayuan.yiyu.wxapi.wxhttp.http_interface.GetWXData;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import io.reactivex.Observer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;
import okhttp3.ResponseBody;

public class WXEntryActivity extends BaseActivity implements IWXAPIEventHandler {

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

    // private String userInfoData;

    Handler handler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            // Bundle bundle = new Bundle();
            //bundle.putString("WXUSERINFO", userInfoData);
            if (msg.arg1 == 1) {
                //finish();
                //Intent intent = new Intent(WXEntryActivity.this, LoginActivity.class);
                //startActivity();
            }

        }
    };

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
    @Override
    public void onResp(BaseResp resp) {
        int result = 0;
        //Toast.makeText(this, "baseresp.getType = " + resp.getType(),Toast.LENGTH_SHORT).show();
        switch (resp.errCode) {
            case BaseResp.ErrCode.ERR_OK:
                result = R.string.errcode_success;
                // 拿到 code
                SendAuth.Resp sResp = (SendAuth.Resp) resp;
                String code = sResp.code;
                getAccessToken(code);
                break;
            case BaseResp.ErrCode.ERR_USER_CANCEL:
                result = R.string.errcode_cancel;
                break;
            case BaseResp.ErrCode.ERR_AUTH_DENIED:
                result = R.string.errcode_deny;
                break;
            case BaseResp.ErrCode.ERR_UNSUPPORT:
                result = R.string.errcode_unsupported;
                break;
            default:
                result = R.string.errcode_unknown;
                break;
        }
        // Toast.makeText(this, result, Toast.LENGTH_LONG).show();
        //  wxInfo.setText("ErrCoe: " + result);
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
        WXHttpClient.retrofit()
                .create(GetWXData.class)
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

    /**
     * 获取 用户信息
     *
     * @param access_token
     * @param openid
     */
    public void getWXUserInfo(String access_token, String openid) {
        WXHttpClient.retrofit()
                .create(GetWXData.class)
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
                            String userInfo = parseJsonUserInfo(userInfoData);
                            wxInfo.setText(userInfo);
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


    /**
     * 解析获取到的UserInfo信息
     *
     * @param userInfo
     * @return
     * @throws JSONException
     */
    public String parseJsonUserInfo(String userInfo) throws JSONException {

        JSONObject json = new JSONObject(userInfo);
        String openid1 = json.getString("openid");
        String nickname = json.getString("nickname");
        int sex = json.getInt("sex");
        String province = json.getString("province");
        String city = json.getString("city");
        String country = json.getString("country");
        String headimgurl = json.getString("headimgurl");
        String unionid = json.getString("unionid");
        List<String> jsonArrayData = new ArrayList<>();
        JSONArray jsonArray = json.getJSONArray("privilege");
        for (int i = 0; i < jsonArray.length(); i++) {
            String jsonArrayString = jsonArray.getString(i);
            jsonArrayData.add(jsonArrayString);
        }

        StringBuffer jsonDataBuf = new StringBuffer();
        jsonDataBuf.append("openid: " + openid1 + "\n");
        jsonDataBuf.append("nickname: " + nickname + "\n");
        jsonDataBuf.append("sex: " + sex + "\n");
        jsonDataBuf.append("province: " + province + "\n");
        jsonDataBuf.append("city: " + city + "\n");
        jsonDataBuf.append("country: " + country + "\n");
        jsonDataBuf.append("headimgurl: " + headimgurl + "\n");
        for (String jads : jsonArrayData) {
            jsonDataBuf.append("jsonArrayData : " + jads + "\n");
        }
        jsonDataBuf.append("\n");
        jsonDataBuf.append("unionid: " + unionid + "\n");

        return jsonDataBuf.toString();
    }

}


