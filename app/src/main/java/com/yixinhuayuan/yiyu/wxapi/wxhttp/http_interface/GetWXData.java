package com.yixinhuayuan.yiyu.wxapi.wxhttp.http_interface;

import io.reactivex.Observable;
import okhttp3.ResponseBody;
import retrofit2.http.GET;
import retrofit2.http.Query;

/**
 * 获取微信登录用户数据的网络请求接口
 */
public interface GetWXData {
    //public static String GETWXDATAURL = "https://api.weixin.qq.com/";

    /**
     * 获取微信access_token的请求
     *
     * @param appid
     * @param secret
     * @param code
     * @param grant_type
     * @return
     */
    @GET("sns/oauth2/access_token")
  /*  Call<ResponseBody> getWXToken(@Query("appid") String appid
            , @Query("secret") String secret
            , @Query("code") String code
            , @Query("grant_type") String grant_type);*/
    Observable<ResponseBody> getWXAccessToken(@Query("appid") String appid
            , @Query("secret") String secret
            , @Query("code") String code
            , @Query("grant_type") String grant_type);

    /**
     * 获取微信UserInfo的请求
     *
     * @param access_token
     * @param openid
     * @return
     */
    @GET("sns/userinfo")
    /*Call<WXAccessTokenData> getWXUserInfo(@Query("access_token") String access_token
            , @Query("openid") String openid);*/
    Observable<ResponseBody> getWXUserInfo(@Query("access_token") String access_token
            , @Query("openid") String openid);
}