package com.yixinhuayuan.yiyu.di.component;

import dagger.BindsInstance;
import dagger.Component;

import com.jess.arms.di.component.AppComponent;

import com.yixinhuayuan.yiyu.di.module.MfgtFavoritesTrendsModule;
import com.yixinhuayuan.yiyu.mvp.contract.MfgtFavoritesTrendsContract;

import com.jess.arms.di.scope.FragmentScope;
import com.yixinhuayuan.yiyu.mvp.ui.fragment.fragment_of_in_mfgtfavorites.MfgtFavoritesTrendsFragment;


/**
 * ================================================
 * Description:
 * <p>
 * Created by MVPArmsTemplate on 04/11/2019 01:48
 * <a href="mailto:jess.yan.effort@gmail.com">Contact me</a>
 * <a href="https://github.com/JessYanCoding">Follow me</a>
 * <a href="https://github.com/JessYanCoding/MVPArms">Star me</a>
 * <a href="https://github.com/JessYanCoding/MVPArms/wiki">See me</a>
 * <a href="https://github.com/JessYanCoding/MVPArmsTemplate">模版请保持更新</a>
 * ================================================
 */
@FragmentScope
@Component(modules = MfgtFavoritesTrendsModule.class, dependencies = AppComponent.class)
public interface MfgtFavoritesTrendsComponent {
    void inject(MfgtFavoritesTrendsFragment fragment);

    @Component.Builder
    interface Builder {
        @BindsInstance
        MfgtFavoritesTrendsComponent.Builder view(MfgtFavoritesTrendsContract.View view);

        MfgtFavoritesTrendsComponent.Builder appComponent(AppComponent appComponent);

        MfgtFavoritesTrendsComponent build();
    }
}