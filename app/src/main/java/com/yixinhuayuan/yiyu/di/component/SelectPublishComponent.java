package com.yixinhuayuan.yiyu.di.component;

import dagger.BindsInstance;
import dagger.Component;

import com.jess.arms.di.component.AppComponent;

import com.yixinhuayuan.yiyu.di.module.SelectPublishModule;
import com.yixinhuayuan.yiyu.mvp.contract.SelectPublishContract;

import com.jess.arms.di.scope.ActivityScope;
import com.yixinhuayuan.yiyu.mvp.ui.activity.in_publichfragment.SelectPublishActivity;


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
@ActivityScope
@Component(modules = SelectPublishModule.class, dependencies = AppComponent.class)
public interface SelectPublishComponent {
    void inject(SelectPublishActivity activity);

    @Component.Builder
    interface Builder {
        @BindsInstance
        SelectPublishComponent.Builder view(SelectPublishContract.View view);

        SelectPublishComponent.Builder appComponent(AppComponent appComponent);

        SelectPublishComponent build();
    }
}