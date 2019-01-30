package com.yixinhuayuan.yiyu.di.component;

import dagger.BindsInstance;
import dagger.Component;

import com.jess.arms.di.component.AppComponent;

import com.yixinhuayuan.yiyu.di.module.MyCenterActivityModule;
import com.yixinhuayuan.yiyu.mvp.contract.MyCenterActivityContract;

import com.jess.arms.di.scope.ActivityScope;
import com.yixinhuayuan.yiyu.mvp.ui.activity.MyCenterActivity;


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
@ActivityScope
@Component(modules = MyCenterActivityModule.class, dependencies = AppComponent.class)
public interface MyCenterActivityComponent {
    void inject(MyCenterActivity activity);

    @Component.Builder
    interface Builder {
        @BindsInstance
        MyCenterActivityComponent.Builder view(MyCenterActivityContract.View view);

        MyCenterActivityComponent.Builder appComponent(AppComponent appComponent);

        MyCenterActivityComponent build();
    }
}