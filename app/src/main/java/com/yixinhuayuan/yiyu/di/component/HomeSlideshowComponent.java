package com.yixinhuayuan.yiyu.di.component;

import dagger.BindsInstance;
import dagger.Component;

import com.jess.arms.di.component.AppComponent;

import com.yixinhuayuan.yiyu.di.module.HomeSlideshowModule;
import com.yixinhuayuan.yiyu.mvp.contract.HomeSlideshowContract;

import com.jess.arms.di.scope.FragmentScope;
import com.yixinhuayuan.yiyu.mvp.ui.fragment.in_home.HomeSlideshowFragment;


/**
 * ================================================
 * Description:
 * <p>
 * Created by MVPArmsTemplate on 06/28/2019 02:16
 * <a href="mailto:jess.yan.effort@gmail.com">Contact me</a>
 * <a href="https://github.com/JessYanCoding">Follow me</a>
 * <a href="https://github.com/JessYanCoding/MVPArms">Star me</a>
 * <a href="https://github.com/JessYanCoding/MVPArms/wiki">See me</a>
 * <a href="https://github.com/JessYanCoding/MVPArmsTemplate">模版请保持更新</a>
 * ================================================
 */
@FragmentScope
@Component(modules = HomeSlideshowModule.class, dependencies = AppComponent.class)
public interface HomeSlideshowComponent {
    void inject(HomeSlideshowFragment fragment);

    @Component.Builder
    interface Builder {
        @BindsInstance
        HomeSlideshowComponent.Builder view(HomeSlideshowContract.View view);

        HomeSlideshowComponent.Builder appComponent(AppComponent appComponent);

        HomeSlideshowComponent build();
    }
}