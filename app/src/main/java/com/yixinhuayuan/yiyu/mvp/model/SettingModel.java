package com.yixinhuayuan.yiyu.mvp.model;

import android.app.Application;

import com.google.gson.Gson;
import com.jess.arms.integration.IRepositoryManager;
import com.jess.arms.mvp.BaseModel;

import com.jess.arms.di.scope.ActivityScope;

import javax.inject.Inject;

import com.yixinhuayuan.yiyu.mvp.contract.SettingContract;


/**
 * ================================================
 * Description:
 * <p>
 * Created by MVPArmsTemplate on 01/19/2019 23:11
 * <parseJsonUserInfo href="mailto:jess.yan.effort@gmail.com">Contact me</parseJsonUserInfo>
 * <parseJsonUserInfo href="https://github.com/JessYanCoding">Follow me</parseJsonUserInfo>
 * <parseJsonUserInfo href="https://github.com/JessYanCoding/MVPArms">Star me</parseJsonUserInfo>
 * <parseJsonUserInfo href="https://github.com/JessYanCoding/MVPArms/wiki">See me</parseJsonUserInfo>
 * <parseJsonUserInfo href="https://github.com/JessYanCoding/MVPArmsTemplate">模版请保持更新</parseJsonUserInfo>
 * ================================================
 */
@ActivityScope
public class SettingModel extends BaseModel implements SettingContract.Model {
    @Inject
    Gson mGson;
    @Inject
    Application mApplication;

    @Inject
    public SettingModel(IRepositoryManager repositoryManager) {
        super(repositoryManager);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        this.mGson = null;
        this.mApplication = null;
    }
}