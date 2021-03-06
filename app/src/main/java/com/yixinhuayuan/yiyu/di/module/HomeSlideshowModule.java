package com.yixinhuayuan.yiyu.di.module;

import com.jess.arms.di.scope.FragmentScope;

import dagger.Binds;
import dagger.Module;
import dagger.Provides;

import com.yixinhuayuan.yiyu.mvp.contract.HomeSlideshowContract;
import com.yixinhuayuan.yiyu.mvp.model.HomeSlideshowModel;


/**
 * ================================================
 * Description:
 * <p>
 * Created by MVPArmsTemplate on 06/28/2019 02:16
 * <a href="mailto:jess.yan.effort@gmail.com">Contact me</a>
 * <a href="https://github.com/JessYanCoding">Follow me</a>
 * <a href="https://github.com/JessYanCoding/MVPArms">Star me</a>
 * <a href="https://github.com/JessYanCoding/MVPArms/wiki">See me</a>
 * <a href="https://github.com/JessYanCoding/MVPArmsTemplate">模版请保持更新</a>
 * ================================================
 */
@Module
public abstract class HomeSlideshowModule {

    @Binds
    abstract HomeSlideshowContract.Model bindHomeSlideshowModel(HomeSlideshowModel model);
}