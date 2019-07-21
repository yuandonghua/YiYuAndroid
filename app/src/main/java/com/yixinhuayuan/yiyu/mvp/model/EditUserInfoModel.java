package com.yixinhuayuan.yiyu.mvp.model;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.Application;
import android.content.Context;
import android.content.SharedPreferences;
import android.util.Log;

import com.google.gson.Gson;
import com.jess.arms.integration.IRepositoryManager;
import com.jess.arms.mvp.BaseModel;

import com.jess.arms.di.scope.ActivityScope;

import javax.inject.Inject;

import com.yixinhuayuan.yiyu.mvp.contract.EditUserInfoContract;
import com.yixinhuayuan.yiyu.mvp.ui.activity.LoginActivity;

import org.json.JSONException;
import org.json.JSONObject;

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
 * Created by MVPArmsTemplate on 03/18/2019 20:01
 * <a href="mailto:jess.yan.effort@gmail.com">Contact me</a>
 * <a href="https://github.com/JessYanCoding">Follow me</a>
 * <a href="https://github.com/JessYanCoding/MVPArms">Star me</a>
 * <a href="https://github.com/JessYanCoding/MVPArms/wiki">See me</a>
 * <a href="https://github.com/JessYanCoding/MVPArmsTemplate">模版请保持更新</a>
 * ================================================
 */
@ActivityScope
public class EditUserInfoModel extends BaseModel implements EditUserInfoContract.Model {
    @Inject
    Gson mGson;
    @Inject
    Application mApplication;

    @Inject
    public EditUserInfoModel(IRepositoryManager repositoryManager) {
        super(repositoryManager);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        this.mGson = null;
        this.mApplication = null;
    }

    @Override
    public void requestChangeUserinfo(String nick, String sex, String photo, String sign, String url, String token, Activity activity) {
        // 创建OkHttpClient对象
        OkHttpClient changeClient = new OkHttpClient();
        // 创建请求体
        FormBody.Builder changeBody = new FormBody.Builder();
        changeBody.add("nickname", nick);
        changeBody.add("sex", sex);
        changeBody.add("photo", photo);
        changeBody.add("introduce", sign);
        changeBody.build();
        // 发送请求
        Request changeRequset = new Request.Builder()
                .put(changeBody.build())
                .addHeader("authorization", token)
                .url(url)
                .build();
        // 接收数据
        changeClient.newCall(changeRequset).enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                // 请求失败
            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                // 请求成功
                String jsonStr = response.body().string();
                Log.d("EditUserInfoModel", "修改用户信息返回的信息");
                parseAndSaveData(jsonStr, activity);
            }
        });
    }

    @Override
    public void parseAndSaveData(String json, Activity activity) {
        try {
            JSONObject jsonObject = new JSONObject(json);
            JSONObject object = jsonObject.getJSONObject("data");
            Log.d("EditUserInfoModel", "修改用户信息返回的信息处理结果:" + "code" + jsonObject.getInt("code"));
            Log.d("EditUserInfoModel", "修改用户信息返回的信息处理结果:" + "昵称" + object.getInt("nickname"));
            @SuppressLint("WrongConstant")
            SharedPreferences spUsererInfo = activity.getSharedPreferences(activity.getBaseContext().getPackageName(), Context.MODE_APPEND);
            SharedPreferences.Editor edit = spUsererInfo.edit();
            edit.putBoolean("is_login", jsonObject.getBoolean("status"));// 是否登录成功
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


        } catch (JSONException e) {
            e.printStackTrace();
        }


    }


}