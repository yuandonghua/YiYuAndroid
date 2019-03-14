package com.yixinhuayuan.yiyu.app.utils;

import com.yixinhuayuan.yiyu.BuildConfig;

import java.util.concurrent.TimeUnit;

import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * 对Retrofit进行封装,可以在全局内进行Http网络请求
 */
public class GlobalHttpClient {
    public static Retrofit mRetrofit;
    public static Retrofit retrofit(String url) {

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
                    .baseUrl(url)
                    .addConverterFactory(GsonConverterFactory.create())
                    .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                    .client(client)
                    .build();

        }

        return mRetrofit;
    }


}
