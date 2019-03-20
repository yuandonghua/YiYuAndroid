package com.yixinhuayuan.yiyu.di.component;

import dagger.BindsInstance;
import dagger.Component;

import com.jess.arms.di.component.AppComponent;

import com.yixinhuayuan.yiyu.di.module.EditUserInfoModule;
import com.yixinhuayuan.yiyu.mvp.contract.EditUserInfoContract;

import com.jess.arms.di.scope.ActivityScope;
import com.yixinhuayuan.yiyu.mvp.ui.activity.EditUserInfoActivity;


/**
 * ================================================
 * Description:
 * <p>
 * Created by MVPArmsTemplate on 03/18/2019 20:01
 * <a href="mailto:jess.yan.effort@gmail.com">Contact me</a>
 * <a href="https://github.com/JessYanCoding">Follow me</a>
 * <a href="https://github.com/JessYanCoding/MVPArms">Star me</a>
 * <a href="https://github.com/JessYanCoding/MVPArms/wiki">See me</a>
 * <a href="https://github.com/JessYanCoding/MVPArmsTemplate">模版请保持更新</a>
 * ================================================
 */
@ActivityScope
@Component(modules = EditUserInfoModule.class, dependencies = AppComponent.class)
public interface EditUserInfoComponent {
    void inject(EditUserInfoActivity activity);

    @Component.Builder
    interface Builder {
        @BindsInstance
        EditUserInfoComponent.Builder view(EditUserInfoContract.View view);

        EditUserInfoComponent.Builder appComponent(AppComponent appComponent);

        EditUserInfoComponent build();
    }
}