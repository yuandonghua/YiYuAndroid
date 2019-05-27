package com.yixinhuayuan.yiyu.di.module;

import com.jess.arms.di.scope.ActivityScope;

import dagger.Binds;
import dagger.Module;
import dagger.Provides;

import com.yixinhuayuan.yiyu.mvp.contract.SelectPublishContract;
import com.yixinhuayuan.yiyu.mvp.model.SelectPublishModel;


/**
 * ================================================
 * Description:
 * <p>
 * Created by MVPArmsTemplate on 05/27/2019 21:17
 * <a href="mailto:jess.yan.effort@gmail.com">Contact me</a>
 * <a href="https://github.com/JessYanCoding">Follow me</a>
 * <a href="https://github.com/JessYanCoding/MVPArms">Star me</a>
 * <a href="https://github.com/JessYanCoding/MVPArms/wiki">See me</a>
 * <a href="https://github.com/JessYanCoding/MVPArmsTemplate">模版请保持更新</a>
 * ================================================
 */
@Module
public abstract class SelectPublishModule {

    @Binds
    abstract SelectPublishContract.Model bindSelectPublishModel(SelectPublishModel model);
}