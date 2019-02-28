package com.yixinhuayuan.yiyu.wxapi.wxhttp;

import android.util.Log;

import com.yixinhuayuan.yiyu.BuildConfig;

import java.io.IOException;
import java.util.concurrent.TimeUnit;

import okhttp3.Call;
import okhttp3.Connection;
import okhttp3.Interceptor;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * 对Retrofit的封装用于,获取微信登录的用户信息
 */
public class WXHttpClient {

    public static Retrofit mRetrofit;

    public static Retrofit retrofit() {

        if (mRetrofit == null) {
            OkHttpClient.Builder builder = new OkHttpClient.Builder();
            builder.readTimeout(5, TimeUnit.SECONDS);
            builder.connectTimeout(5, TimeUnit.SECONDS);
            if (BuildConfig.DEBUG) {
                // log信息拦截器
                HttpLoggingInterceptor loggingInterceptor = new HttpLoggingInterceptor();
                loggingInterceptor.setLevel(HttpLoggingInterceptor.Level.BODY);
                //
                builder.addInterceptor(loggingInterceptor);
            }
            OkHttpClient client = builder.build();

            mRetrofit = new Retrofit
                    .Builder()
                    .baseUrl("https://api.weixin.qq.com/")
                    .addConverterFactory(GsonConverterFactory.create())
                    .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                    .client(client)
                    .build();

        }

        return mRetrofit;
    }


}

class MyInterceptor implements Interceptor {

    String TAG = "Interceptor";

    @Override
    public Response intercept(Chain chain) throws IOException {

        Request request = chain.request();

        Call call = chain.call();

        Connection connection = chain.connection();


        Log.d(TAG, "request: "+request+"");
        Log.d(TAG, "call: "+call+"");
        Log.d(TAG, "connection: "+connection+"");


        return null;
    }
}