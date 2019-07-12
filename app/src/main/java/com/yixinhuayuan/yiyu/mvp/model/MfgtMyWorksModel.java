package com.yixinhuayuan.yiyu.mvp.model;

import android.app.Application;
import android.util.Log;

import com.google.gson.Gson;
import com.jess.arms.integration.IRepositoryManager;
import com.jess.arms.mvp.BaseModel;

import com.jess.arms.di.scope.ActivityScope;

import javax.inject.Inject;

import com.yixinhuayuan.yiyu.mvp.contract.MfgtMyWorksContract;

import java.io.IOException;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.FormBody;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;


/**
 * ================================================
 * Description:
 * <p>
 * Created by MVPArmsTemplate on 04/09/2019 19:57
 * <a href="mailto:jess.yan.effort@gmail.com">Contact me</a>
 * <a href="https://github.com/JessYanCoding">Follow me</a>
 * <a href="https://github.com/JessYanCoding/MVPArms">Star me</a>
 * <a href="https://github.com/JessYanCoding/MVPArms/wiki">See me</a>
 * <a href="https://github.com/JessYanCoding/MVPArmsTemplate">模版请保持更新</a>
 * ================================================
 */
@ActivityScope
public class MfgtMyWorksModel extends BaseModel implements MfgtMyWorksContract.Model {

    @Inject
    Gson mGson;
    @Inject
    Application mApplication;
    private static final String TAG = MfgtMyWorksModel.class.getSimpleName();

    @Inject
    public MfgtMyWorksModel(IRepositoryManager repositoryManager) {
        super(repositoryManager);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        this.mGson = null;
        this.mApplication = null;
    }

    private String jsonStr;

    @Override
    public String changeClassify(String url, String token, String class_name) {


        // 创建OkHttpClient 对象
        OkHttpClient changeClient = new OkHttpClient();
        // 创建请求表单
        FormBody.Builder changeBody = new FormBody.Builder();
        changeBody.add("class_name", class_name);
        changeBody.build();
        // 请求接口
        Request changeRequest = new Request.Builder()
                .put(changeBody.build())
                .url(url)
                .addHeader("authorization", token)
                .build();
        changeClient.newCall(changeRequest).enqueue(new Callback() {
            String string;

            @Override
            public void onFailure(Call call, IOException e) {
                Log.d(TAG, "修改作品集请求失败");
            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                string = response.body().string();
                Log.d(TAG, "修改作品集返回数据 : " + string);
                MfgtMyWorksModel.this.jsonStr = string;
            }
        });
        return jsonStr;
    }

    @Override
    public void delClassify(String url, String token) {
        // 创建OkHttpClient 对象
        OkHttpClient delClient = new OkHttpClient();
        // 创建请求表单
        FormBody.Builder delBody = new FormBody.Builder();
        // 开始删除请求
        Request delRequest = new Request.Builder()
                .delete()
                .addHeader("authorization", token)
                .url(url)
                .build();
        try {
            Response delExecute = delClient.newCall(delRequest).execute();
            String string = delExecute.code() + "\n" + "body" + delExecute.body().string();
            Log.d(TAG, "请求删除接口后返回的数据: " + string);
        } catch (IOException e) {
            e.printStackTrace();
        }

    }
}