package com.yixinhuayuan.yiyu.di.component;

import dagger.BindsInstance;
import dagger.Component;

import com.jess.arms.di.component.AppComponent;

import com.yixinhuayuan.yiyu.di.module.TremdsTedailsModule;
import com.yixinhuayuan.yiyu.mvp.contract.TremdsTedailsContract;

import com.jess.arms.di.scope.ActivityScope;
import com.yixinhuayuan.yiyu.mvp.ui.activity.in_trends.TrendsDetailsActivity;


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
@ActivityScope
@Component(modules = TremdsTedailsModule.class, dependencies = AppComponent.class)
public interface TremdsTedailsComponent {
    void inject(TrendsDetailsActivity activity);

    @Component.Builder
    interface Builder {
        @BindsInstance
        TremdsTedailsComponent.Builder view(TremdsTedailsContract.View view);

        TremdsTedailsComponent.Builder appComponent(AppComponent appComponent);

        TremdsTedailsComponent build();
    }
}