package com.yixinhuayuan.yiyu.di.component;

import dagger.BindsInstance;
import dagger.Component;

import com.jess.arms.di.component.AppComponent;

import com.yixinhuayuan.yiyu.di.module.MfgtTrendsModule;
import com.yixinhuayuan.yiyu.mvp.contract.MfgtTrendsContract;

import com.jess.arms.di.scope.ActivityScope;
import com.yixinhuayuan.yiyu.mvp.ui.activity.in_myfragment.MfgtTrendsActivity;


/**
 * ================================================
 * Description:
 * <p>
 * Created by MVPArmsTemplate on 04/10/2019 18:42
 * <a href="mailto:jess.yan.effort@gmail.com">Contact me</a>
 * <a href="https://github.com/JessYanCoding">Follow me</a>
 * <a href="https://github.com/JessYanCoding/MVPArms">Star me</a>
 * <a href="https://github.com/JessYanCoding/MVPArms/wiki">See me</a>
 * <a href="https://github.com/JessYanCoding/MVPArmsTemplate">模版请保持更新</a>
 * ================================================
 */
@ActivityScope
@Component(modules = MfgtTrendsModule.class, dependencies = AppComponent.class)
public interface MfgtTrendsComponent {
    void inject(MfgtTrendsActivity activity);

    @Component.Builder
    interface Builder {
        @BindsInstance
        MfgtTrendsComponent.Builder view(MfgtTrendsContract.View view);

        MfgtTrendsComponent.Builder appComponent(AppComponent appComponent);

        MfgtTrendsComponent build();
    }
}