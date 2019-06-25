package com.yixinhuayuan.yiyu.di.module;

import dagger.Binds;
import dagger.Module;

import com.yixinhuayuan.yiyu.mvp.contract.TremdsTedailsContract;
import com.yixinhuayuan.yiyu.mvp.model.TrendsDetailsModel;


/**
 * ================================================
 * Description:
 * <p>
 * Created by MVPArmsTemplate on 06/25/2019 10:43
 * <a href="mailto:jess.yan.effort@gmail.com">Contact me</a>
 * <a href="https://github.com/JessYanCoding">Follow me</a>
 * <a href="https://github.com/JessYanCoding/MVPArms">Star me</a>
 * <a href="https://github.com/JessYanCoding/MVPArms/wiki">See me</a>
 * <a href="https://github.com/JessYanCoding/MVPArmsTemplate">模版请保持更新</a>
 * ================================================
 */
@Module
public abstract class TremdsTedailsModule {

    @Binds
    abstract TremdsTedailsContract.Model bindTremdsTedailsModel(TrendsDetailsModel model);
}