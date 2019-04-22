package com.yixinhuayuan.yiyu.di.component;

import dagger.BindsInstance;
import dagger.Component;

import com.jess.arms.di.component.AppComponent;

import com.yixinhuayuan.yiyu.di.module.FansListModule;
import com.yixinhuayuan.yiyu.mvp.contract.FansListContract;

import com.jess.arms.di.scope.ActivityScope;
import com.yixinhuayuan.yiyu.mvp.ui.activity.in_myfragment.MfgtFansListActivity;


/**
 * ================================================
 * Description:
 * <p>
 * Created by MVPArmsTemplate on 04/02/2019 19:21
 * <a href="mailto:jess.yan.effort@gmail.com">Contact me</a>
 * <a href="https://github.com/JessYanCoding">Follow me</a>
 * <a href="https://github.com/JessYanCoding/MVPArms">Star me</a>
 * <a href="https://github.com/JessYanCoding/MVPArms/wiki">See me</a>
 * <a href="https://github.com/JessYanCoding/MVPArmsTemplate">模版请保持更新</a>
 * ================================================
 */
@ActivityScope
@Component(modules = FansListModule.class, dependencies = AppComponent.class)
public interface FansListComponent {
    void inject(MfgtFansListActivity activity);

    @Component.Builder
    interface Builder {
        @BindsInstance
        FansListComponent.Builder view(FansListContract.View view);

        FansListComponent.Builder appComponent(AppComponent appComponent);

        FansListComponent build();
    }
}