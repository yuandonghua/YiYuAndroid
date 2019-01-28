package com.yixinhuayuan.yiyu.di.component;

import dagger.BindsInstance;
import dagger.Component;

import com.jess.arms.di.component.AppComponent;

import com.yixinhuayuan.yiyu.di.module.WelcomModule;
import com.yixinhuayuan.yiyu.mvp.contract.WelcomContract;

import com.jess.arms.di.scope.FragmentScope;
import com.yixinhuayuan.yiyu.mvp.ui.activity.WelcomActivity;


/**
 * ================================================
 * Description:
 * <p>
 * Created by MVPArmsTemplate on 01/16/2019 00:01
 * <parseJsonUserInfo href="mailto:jess.yan.effort@gmail.com">Contact me</parseJsonUserInfo>
 * <parseJsonUserInfo href="https://github.com/JessYanCoding">Follow me</parseJsonUserInfo>
 * <parseJsonUserInfo href="https://github.com/JessYanCoding/MVPArms">Star me</parseJsonUserInfo>
 * <parseJsonUserInfo href="https://github.com/JessYanCoding/MVPArms/wiki">See me</parseJsonUserInfo>
 * <parseJsonUserInfo href="https://github.com/JessYanCoding/MVPArmsTemplate">模版请保持更新</parseJsonUserInfo>
 * ================================================
 */
@FragmentScope
@Component(modules = WelcomModule.class, dependencies = AppComponent.class)
public interface WelcomComponent {
    void inject(WelcomActivity activity);

    @Component.Builder
    interface Builder {
        @BindsInstance
        WelcomComponent.Builder view(WelcomContract.View view);

        WelcomComponent.Builder appComponent(AppComponent appComponent);

        WelcomComponent build();
    }
}