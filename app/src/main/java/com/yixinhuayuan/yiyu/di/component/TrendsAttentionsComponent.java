package com.yixinhuayuan.yiyu.di.component;

import dagger.BindsInstance;
import dagger.Component;

import com.jess.arms.di.component.AppComponent;

import com.yixinhuayuan.yiyu.di.module.TrendsAttentionsModule;
import com.yixinhuayuan.yiyu.mvp.contract.TrendsAttentionsContract;

import com.jess.arms.di.scope.ActivityScope;
import com.yixinhuayuan.yiyu.mvp.ui.activity.in_trends.TrendsAttentionsActivity;


/**
 * ================================================
 * Description:
 * <p>
 * Created by MVPArmsTemplate on 06/25/2019 16:21
 * <a href="mailto:jess.yan.effort@gmail.com">Contact me</a>
 * <a href="https://github.com/JessYanCoding">Follow me</a>
 * <a href="https://github.com/JessYanCoding/MVPArms">Star me</a>
 * <a href="https://github.com/JessYanCoding/MVPArms/wiki">See me</a>
 * <a href="https://github.com/JessYanCoding/MVPArmsTemplate">模版请保持更新</a>
 * ================================================
 */
@ActivityScope
@Component(modules = TrendsAttentionsModule.class, dependencies = AppComponent.class)
public interface TrendsAttentionsComponent {
    void inject(TrendsAttentionsActivity activity);

    @Component.Builder
    interface Builder {
        @BindsInstance
        TrendsAttentionsComponent.Builder view(TrendsAttentionsContract.View view);

        TrendsAttentionsComponent.Builder appComponent(AppComponent appComponent);

        TrendsAttentionsComponent build();
    }
}