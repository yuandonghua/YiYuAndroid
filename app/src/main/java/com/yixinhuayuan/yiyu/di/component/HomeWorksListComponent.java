package com.yixinhuayuan.yiyu.di.component;

import dagger.BindsInstance;
import dagger.Component;

import com.jess.arms.di.component.AppComponent;

import com.yixinhuayuan.yiyu.di.module.HomeWorksListModule;
import com.yixinhuayuan.yiyu.mvp.contract.HomeWorksListContract;

import com.jess.arms.di.scope.FragmentScope;
import com.yixinhuayuan.yiyu.mvp.ui.fragment.in_home.HomeWorksListFragment;


/**
 * ================================================
 * Description:
 * <p>
 * Created by MVPArmsTemplate on 06/29/2019 01:38
 * <a href="mailto:jess.yan.effort@gmail.com">Contact me</a>
 * <a href="https://github.com/JessYanCoding">Follow me</a>
 * <a href="https://github.com/JessYanCoding/MVPArms">Star me</a>
 * <a href="https://github.com/JessYanCoding/MVPArms/wiki">See me</a>
 * <a href="https://github.com/JessYanCoding/MVPArmsTemplate">模版请保持更新</a>
 * ================================================
 */
@FragmentScope
@Component(modules = HomeWorksListModule.class, dependencies = AppComponent.class)
public interface HomeWorksListComponent {
    void inject(HomeWorksListFragment fragment);

    @Component.Builder
    interface Builder {
        @BindsInstance
        HomeWorksListComponent.Builder view(HomeWorksListContract.View view);

        HomeWorksListComponent.Builder appComponent(AppComponent appComponent);

        HomeWorksListComponent build();
    }
}