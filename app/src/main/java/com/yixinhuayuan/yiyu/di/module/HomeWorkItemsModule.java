package com.yixinhuayuan.yiyu.di.module;

import com.jess.arms.di.scope.FragmentScope;

import dagger.Binds;
import dagger.Module;
import dagger.Provides;

import com.yixinhuayuan.yiyu.mvp.contract.HomeWorkItemsContract;
import com.yixinhuayuan.yiyu.mvp.model.HomeWorkItemsModel;


/**
 * ================================================
 * Description:
 * <p>
 * Created by MVPArmsTemplate on 06/19/2019 01:34
 * <a href="mailto:jess.yan.effort@gmail.com">Contact me</a>
 * <a href="https://github.com/JessYanCoding">Follow me</a>
 * <a href="https://github.com/JessYanCoding/MVPArms">Star me</a>
 * <a href="https://github.com/JessYanCoding/MVPArms/wiki">See me</a>
 * <a href="https://github.com/JessYanCoding/MVPArmsTemplate">模版请保持更新</a>
 * ================================================
 */
@Module
public abstract class HomeWorkItemsModule {

    @Binds
    abstract HomeWorkItemsContract.Model bindHomeWorkItemsModel(HomeWorkItemsModel model);
}