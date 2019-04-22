package com.yixinhuayuan.yiyu.di.component;

import dagger.BindsInstance;
import dagger.Component;

import com.jess.arms.di.component.AppComponent;

import com.yixinhuayuan.yiyu.di.module.MfgtFavoritesWorksModule;
import com.yixinhuayuan.yiyu.mvp.contract.MfgtFavoritesWorksContract;

import com.jess.arms.di.scope.FragmentScope;
import com.yixinhuayuan.yiyu.mvp.ui.fragment.fragment_of_in_mfgtfavorites.MfgtFavoritesWorksFragment;


/**
 * ================================================
 * Description:
 * <p>
 * Created by MVPArmsTemplate on 04/11/2019 01:44
 * <a href="mailto:jess.yan.effort@gmail.com">Contact me</a>
 * <a href="https://github.com/JessYanCoding">Follow me</a>
 * <a href="https://github.com/JessYanCoding/MVPArms">Star me</a>
 * <a href="https://github.com/JessYanCoding/MVPArms/wiki">See me</a>
 * <a href="https://github.com/JessYanCoding/MVPArmsTemplate">模版请保持更新</a>
 * ================================================
 */
@FragmentScope
@Component(modules = MfgtFavoritesWorksModule.class, dependencies = AppComponent.class)
public interface MfgtFavoritesWorksComponent {
    void inject(MfgtFavoritesWorksFragment fragment);

    @Component.Builder
    interface Builder {
        @BindsInstance
        MfgtFavoritesWorksComponent.Builder view(MfgtFavoritesWorksContract.View view);

        MfgtFavoritesWorksComponent.Builder appComponent(AppComponent appComponent);

        MfgtFavoritesWorksComponent build();
    }
}