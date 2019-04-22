package com.yixinhuayuan.yiyu.di.component;

import dagger.BindsInstance;
import dagger.Component;

import com.jess.arms.di.component.AppComponent;

import com.yixinhuayuan.yiyu.di.module.MyFgtFavoritesModule;
import com.yixinhuayuan.yiyu.mvp.contract.MyFgtFavoritesContract;

import com.jess.arms.di.scope.ActivityScope;
import com.yixinhuayuan.yiyu.mvp.ui.activity.in_myfragment.MfgtFavoritesActivity;


/**
 * ================================================
 * Description:
 * <p>
 * Created by MVPArmsTemplate on 04/10/2019 18:57
 * <a href="mailto:jess.yan.effort@gmail.com">Contact me</a>
 * <a href="https://github.com/JessYanCoding">Follow me</a>
 * <a href="https://github.com/JessYanCoding/MVPArms">Star me</a>
 * <a href="https://github.com/JessYanCoding/MVPArms/wiki">See me</a>
 * <a href="https://github.com/JessYanCoding/MVPArmsTemplate">模版请保持更新</a>
 * ================================================
 */
@ActivityScope
@Component(modules = MyFgtFavoritesModule.class, dependencies = AppComponent.class)
public interface MyFgtFavoritesComponent {
    void inject(MfgtFavoritesActivity activity);

    @Component.Builder
    interface Builder {
        @BindsInstance
        MyFgtFavoritesComponent.Builder view(MyFgtFavoritesContract.View view);

        MyFgtFavoritesComponent.Builder appComponent(AppComponent appComponent);

        MyFgtFavoritesComponent build();
    }
}