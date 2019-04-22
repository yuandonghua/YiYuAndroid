package com.yixinhuayuan.yiyu.di.component;

import dagger.BindsInstance;
import dagger.Component;

import com.jess.arms.di.component.AppComponent;

import com.yixinhuayuan.yiyu.di.module.AttentionListModule;
import com.yixinhuayuan.yiyu.mvp.contract.AttentionListContract;

import com.jess.arms.di.scope.ActivityScope;
import com.yixinhuayuan.yiyu.mvp.ui.activity.in_myfragment.MfgtAttentionListActivity;


/**
 * ================================================
 * Description:
 * <p>
 * Created by MVPArmsTemplate on 04/01/2019 19:37
 * <a href="mailto:jess.yan.effort@gmail.com">Contact me</a>
 * <a href="https://github.com/JessYanCoding">Follow me</a>
 * <a href="https://github.com/JessYanCoding/MVPArms">Star me</a>
 * <a href="https://github.com/JessYanCoding/MVPArms/wiki">See me</a>
 * <a href="https://github.com/JessYanCoding/MVPArmsTemplate">模版请保持更新</a>
 * ================================================
 */
@ActivityScope
@Component(modules = AttentionListModule.class, dependencies = AppComponent.class)
public interface AttentionListComponent {
    void inject(MfgtAttentionListActivity activity);

    @Component.Builder
    interface Builder {
        @BindsInstance
        AttentionListComponent.Builder view(AttentionListContract.View view);

        AttentionListComponent.Builder appComponent(AppComponent appComponent);

        AttentionListComponent build();
    }
}