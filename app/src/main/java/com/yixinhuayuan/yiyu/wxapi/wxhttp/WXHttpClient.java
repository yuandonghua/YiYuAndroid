package com.yixinhuayuan.yiyu.wxapi.wxhttp;

import com.yixinhuayuan.yiyu.BuildConfig;

import java.util.concurrent.TimeUnit;

import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;

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
