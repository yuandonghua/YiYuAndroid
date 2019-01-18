package com.yixinhuayuan.yiyu.di.component;

import dagger.BindsInstance;
import dagger.Component;

import com.jess.arms.di.component.AppComponent;

import com.yixinhuayuan.yiyu.di.module.TrendsModule;
import com.yixinhuayuan.yiyu.mvp.contract.TrendsContract;

import com.jess.arms.di.scope.FragmentScope;
import com.yixinhuayuan.yiyu.mvp.ui.fragment.TrendsFragment;


/**
 * ================================================
 * Description:
 * <p>
 * Created by MVPArmsTemplate on 01/16/2019 00:55
 * <a href="mailto:jess.yan.effort@gmail.com">Contact me</a>
 * <a href="https://github.com/JessYanCoding">Follow me</a>
 * <a href="https://github.com/JessYanCoding/MVPArms">Star me</a>
 * <a href="https://github.com/JessYanCoding/MVPArms/wiki">See me</a>
 * <a href="https://github.com/JessYanCoding/MVPArmsTemplate">模版请保持更新</a>
 * ================================================
 */
@FragmentScope
@Component(modules = TrendsModule.class, dependencies = AppComponent.class)
public interface TrendsComponent {
    void inject(TrendsFragment fragment);

    @Component.Builder
    interface Builder {
        @BindsInstance
        TrendsComponent.Builder view(TrendsContract.View view);

        TrendsComponent.Builder appComponent(AppComponent appComponent);

        TrendsComponent build();
    }
}