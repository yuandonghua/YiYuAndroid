package com.yixinhuayuan.yiyu.di.module;

import dagger.Binds;
import dagger.Module;

import com.yixinhuayuan.yiyu.mvp.contract.MfgtMyWorksContract;
import com.yixinhuayuan.yiyu.mvp.model.MfgtMyWorksModel;


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
@Module
public abstract class MfgtMyWorksModule {

    @Binds
    abstract MfgtMyWorksContract.Model bindMfgtMyWorksModel(MfgtMyWorksModel model);
}