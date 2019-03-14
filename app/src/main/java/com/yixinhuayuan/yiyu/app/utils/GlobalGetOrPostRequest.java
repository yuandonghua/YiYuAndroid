package com.yixinhuayuan.yiyu.app.utils;

import io.reactivex.Observable;
import okhttp3.ResponseBody;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Query;

/**
 * 这个接口由,GlobalHttpClient进行调用,可以在这个接口里面设置GET请求或者POST请求
 */
public interface GlobalGetOrPostRequest {
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
    Observable<ResponseBody> getWXUserInfo(@Query("access_token") String access_token
            , @Query("openid") String openid);


    /**
     * 获取微博用户信息的请求
     *
     * @param access_token
     * @param uid
     * @return
     */
    @GET("users/show")
    Observable<ResponseBody> getWBUserInfo(@Query("access_token") String access_token
            , @Query("uid") String uid);


    @POST("api/login")
    Observable<ResponseBody> registryUser(@Query("account") String openID, @Query("type") Integer type);
}
