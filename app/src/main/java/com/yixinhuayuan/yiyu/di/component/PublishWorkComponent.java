package com.yixinhuayuan.yiyu.di.component;

import dagger.BindsInstance;
import dagger.Component;

import com.jess.arms.di.component.AppComponent;

import com.yixinhuayuan.yiyu.di.module.PublishWorkModule;
import com.yixinhuayuan.yiyu.mvp.contract.PublishWorkContract;

import com.jess.arms.di.scope.ActivityScope;
import com.yixinhuayuan.yiyu.mvp.ui.activity.in_publichfragment.PublishWorkActivity;


/**
 * ================================================
 * Description:
 * <p>
 * Created by MVPArmsTemplate on 05/27/2019 21:34
 * <a href="mailto:jess.yan.effort@gmail.com">Contact me</a>
 * <a href="https://github.com/JessYanCoding">Follow me</a>
 * <a href="https://github.com/JessYanCoding/MVPArms">Star me</a>
 * <a href="https://github.com/JessYanCoding/MVPArms/wiki">See me</a>
 * <a href="https://github.com/JessYanCoding/MVPArmsTemplate">模版请保持更新</a>
 * ================================================
 */
@ActivityScope
@Component(modules = PublishWorkModule.class, dependencies = AppComponent.class)
public interface PublishWorkComponent {
    void inject(PublishWorkActivity activity);

    @Component.Builder
    interface Builder {
        @BindsInstance
        PublishWorkComponent.Builder view(PublishWorkContract.View view);

        PublishWorkComponent.Builder appComponent(AppComponent appComponent);

        PublishWorkComponent build();
    }
}