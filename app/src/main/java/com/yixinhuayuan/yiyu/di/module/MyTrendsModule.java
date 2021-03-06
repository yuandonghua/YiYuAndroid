package com.yixinhuayuan.yiyu.di.module;

import dagger.Binds;
import dagger.Module;

import com.yixinhuayuan.yiyu.mvp.contract.MyTrendsContract;
import com.yixinhuayuan.yiyu.mvp.model.PcaMyTrendsModel;


/**
 * ================================================
 * Description:
 * <p>
 * Created by MVPArmsTemplate on 03/30/2019 13:53
 * <a href="mailto:jess.yan.effort@gmail.com">Contact me</a>
 * <a href="https://github.com/JessYanCoding">Follow me</a>
 * <a href="https://github.com/JessYanCoding/MVPArms">Star me</a>
 * <a href="https://github.com/JessYanCoding/MVPArms/wiki">See me</a>
 * <a href="https://github.com/JessYanCoding/MVPArmsTemplate">模版请保持更新</a>
 * ================================================
 */
@Module
public abstract class MyTrendsModule {

    @Binds
    abstract MyTrendsContract.Model bindMyTrendsModel(PcaMyTrendsModel model);
}