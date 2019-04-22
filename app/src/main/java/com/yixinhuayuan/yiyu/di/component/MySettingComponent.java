package com.yixinhuayuan.yiyu.di.component;

import dagger.BindsInstance;
import dagger.Component;

import com.jess.arms.di.component.AppComponent;

import com.yixinhuayuan.yiyu.di.module.MySettingModule;
import com.yixinhuayuan.yiyu.mvp.contract.MySettingContract;

import com.jess.arms.di.scope.ActivityScope;
import com.yixinhuayuan.yiyu.mvp.ui.activity.in_myfragment.MfgtSettingActivity;


/**
 * ================================================
 * Description:
 * <p>
 * Created by MVPArmsTemplate on 04/09/2019 19:06
 * <a href="mailto:jess.yan.effort@gmail.com">Contact me</a>
 * <a href="https://github.com/JessYanCoding">Follow me</a>
 * <a href="https://github.com/JessYanCoding/MVPArms">Star me</a>
 * <a href="https://github.com/JessYanCoding/MVPArms/wiki">See me</a>
 * <a href="https://github.com/JessYanCoding/MVPArmsTemplate">模版请保持更新</a>
 * ================================================
 */
@ActivityScope
@Component(modules = MySettingModule.class, dependencies = AppComponent.class)
public interface MySettingComponent {
    void inject(MfgtSettingActivity activity);

    @Component.Builder
    interface Builder {
        @BindsInstance
        MySettingComponent.Builder view(MySettingContract.View view);

        MySettingComponent.Builder appComponent(AppComponent appComponent);

        MySettingComponent build();
    }
}