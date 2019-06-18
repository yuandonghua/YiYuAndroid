package com.yixinhuayuan.yiyu.di.component;

import dagger.BindsInstance;
import dagger.Component;

import com.jess.arms.di.component.AppComponent;

import com.yixinhuayuan.yiyu.di.module.HomeWorkItemsModule;
import com.yixinhuayuan.yiyu.mvp.contract.HomeWorkItemsContract;

import com.jess.arms.di.scope.FragmentScope;
import com.yixinhuayuan.yiyu.mvp.ui.fragment.in_homefragment.HomeWorkItemsFragment;


/**
 * ================================================
 * Description:
 * <p>
 * Created by MVPArmsTemplate on 06/19/2019 01:34
 * <a href="mailto:jess.yan.effort@gmail.com">Contact me</a>
 * <a href="https://github.com/JessYanCoding">Follow me</a>
 * <a href="https://github.com/JessYanCoding/MVPArms">Star me</a>
 * <a href="https://github.com/JessYanCoding/MVPArms/wiki">See me</a>
 * <a href="https://github.com/JessYanCoding/MVPArmsTemplate">模版请保持更新</a>
 * ================================================
 */
@FragmentScope
@Component(modules = HomeWorkItemsModule.class, dependencies = AppComponent.class)
public interface HomeWorkItemsComponent {
    void inject(HomeWorkItemsFragment fragment);

    @Component.Builder
    interface Builder {
        @BindsInstance
        HomeWorkItemsComponent.Builder view(HomeWorkItemsContract.View view);

        HomeWorkItemsComponent.Builder appComponent(AppComponent appComponent);

        HomeWorkItemsComponent build();
    }
}