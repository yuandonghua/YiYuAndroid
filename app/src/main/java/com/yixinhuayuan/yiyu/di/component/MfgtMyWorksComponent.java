package com.yixinhuayuan.yiyu.di.component;

import dagger.BindsInstance;
import dagger.Component;

import com.jess.arms.di.component.AppComponent;

import com.yixinhuayuan.yiyu.di.module.MfgtMyWorksModule;
import com.yixinhuayuan.yiyu.mvp.contract.MfgtMyWorksContract;

import com.jess.arms.di.scope.ActivityScope;
import com.yixinhuayuan.yiyu.mvp.ui.activity.in_myfragment.MfgtMyWorksActivity;


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
@ActivityScope
@Component(modules = MfgtMyWorksModule.class, dependencies = AppComponent.class)
public interface MfgtMyWorksComponent {
    void inject(MfgtMyWorksActivity activity);

    @Component.Builder
    interface Builder {
        @BindsInstance
        MfgtMyWorksComponent.Builder view(MfgtMyWorksContract.View view);

        MfgtMyWorksComponent.Builder appComponent(AppComponent appComponent);

        MfgtMyWorksComponent build();
    }
}