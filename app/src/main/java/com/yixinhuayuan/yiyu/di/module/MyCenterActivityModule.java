package com.yixinhuayuan.yiyu.di.module;

import dagger.Binds;
import dagger.Module;

import com.yixinhuayuan.yiyu.mvp.contract.MyCenterActivityContract;
import com.yixinhuayuan.yiyu.mvp.model.MyCenterModel;


/**
 * ================================================
 * Description:
 * <p>
 * Created by MVPArmsTemplate on 01/29/2019 17:10
 * <a href="mailto:jess.yan.effort@gmail.com">Contact me</a>
 * <a href="https://github.com/JessYanCoding">Follow me</a>
 * <a href="https://github.com/JessYanCoding/MVPArms">Star me</a>
 * <a href="https://github.com/JessYanCoding/MVPArms/wiki">See me</a>
 * <a href="https://github.com/JessYanCoding/MVPArmsTemplate">模版请保持更新</a>
 * ================================================
 */
@Module
public abstract class MyCenterActivityModule {

    @Binds
    abstract MyCenterActivityContract.Model bindMyCenterActivityModel(MyCenterModel model);
}