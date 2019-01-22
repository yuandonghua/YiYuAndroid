package com.yixinhuayuan.yiyu.wxapi;

import android.content.Intent;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.os.Bundle;

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
import com.yixinhuayuan.yiyu.mvp.ui.activity.LoginActivity;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.http.GET;
import retrofit2.http.Query;

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
    private IWXAPI api;
    private StringBuffer jsonDataBuf;


    Handler handler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            Bundle bundle = new Bundle();
            bundle.putString("WXUSERINFO", jsonDataBuf.toString());
            if (msg.arg1 == 1) {
                finish();
                //Intent intent = new Intent(WXEntryActivity.this, LoginActivity.class);
                //startActivity();
            }

        }
    };


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
                SendAuth.Resp sResp = (SendAuth.Resp) resp;
                String code = sResp.code;
                String state = sResp.state;
                String lang = sResp.lang;
                String country = sResp.country;
                //wxInfo.setText("code: " + code + " |state: " + state + "|lang: " + lang + "|country: " + country);
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

    @Override
    public int initView(@Nullable Bundle savedInstanceState) {
        return R.layout.activity_wxentry;
    }

    @Override
    public void initData(@Nullable Bundle savedInstanceState) {
        // 通过WXAPIFactory工厂，获取IWXAPI的实例
        api = WXAPIFactory.createWXAPI(this, GlobalConfiguration.WX_APP_ID, false);

        //注意：
        //第三方开发者如果使用透明界面来实现WXEntryActivity，需要判断handleIntent的返回值，
        // 如果返回值为false，则说明入参不合法未被SDK处理，应finish当前透明界面，
        // 避免外部通过传递非法参数的Intent导致停留在透明界面，引起用户的疑惑
        try {
            api.handleIntent(getIntent(), this);

        } catch (Exception e) {
            e.printStackTrace();
            wxInfo.setText(e.getMessage());
        }

    }

    /**
     * 获取用户信息
     *
     * @param code
     */
    private void getAccessToken(String code) {

        Retrofit retrofit = new Retrofit.Builder().baseUrl("https://api.weixin.qq.com/").build();
        BlogService service = retrofit.create(BlogService.class);

        Call<ResponseBody> call = service.getWXToken(GlobalConfiguration.WX_APP_ID
                , APP_SECRET
                , code
                , "authorization_code");

        call.enqueue(new Callback<ResponseBody>() {
            @Override
            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                try {

                    String string = response.body().string();
                    JSONObject json = new JSONObject(string);

                    String access_token = json.getString("access_token");
                    String openid = json.getString("openid");
                    getWXUserInfo(access_token, openid);

                    wxInfo.setText(response.body().string());
                } catch (IOException e) {
                    e.printStackTrace();
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }

            @Override
            public void onFailure(Call<ResponseBody> call, Throwable t) {
                wxInfo.setText(t.getMessage());
                t.printStackTrace();
            }
        });

    }

    public void getWXUserInfo(String access_token, String openid) {
        Retrofit retrofit = new Retrofit.Builder().baseUrl("https://api.weixin.qq.com/").build();
        BlogService service = retrofit.create(BlogService.class);

        Call<ResponseBody> call = service.getWXUserInfo(access_token, openid);

        call.enqueue(new Callback<ResponseBody>() {
            @Override
            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                try {

                    String string = response.body().string();
                    JSONObject json = new JSONObject(string);

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

                    jsonDataBuf = new StringBuffer();
                    jsonDataBuf.append("openid: " + openid1 + "\n");
                    jsonDataBuf.append("nickname: " + nickname + "\n");
                    jsonDataBuf.append("sex: " + sex + "\n");
                    jsonDataBuf.append("province: " + province + "\n");
                    jsonDataBuf.append("city: " + city + "\n");
                    jsonDataBuf.append("country: " + country + "\n");
                    jsonDataBuf.append("headimgurl: " + headimgurl + "\n");
                    /*for (String jads : jsonArrayData) {
                        jsonDataBuf.append("jsonArrayData : "+jads+"\n");
                    }*/
                    jsonDataBuf.append("\n");
                    jsonDataBuf.append("unionid: " + unionid + "\n");
                    wxInfo.setText(jsonDataBuf.toString());

                    Message message = new Message();
                    message.arg1 = 1;
                    handler.sendMessage(message);

                } catch (IOException e) {
                    e.printStackTrace();
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }

            @Override
            public void onFailure(Call<ResponseBody> call, Throwable t) {
                wxInfo.setText(t.getMessage());
                t.printStackTrace();
            }
        });

    }
}

interface BlogService {
    @GET("sns/oauth2/access_token")
    Call<ResponseBody> getWXToken(@Query("appid") String appid
            , @Query("secret") String secret
            , @Query("code") String code
            , @Query("grant_type") String grant_type);

    @GET("sns/userinfo")
    Call<ResponseBody> getWXUserInfo(@Query("access_token") String access_token
            , @Query("openid") String openid);
}

