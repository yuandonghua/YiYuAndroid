package com.yixinhuayuan.yiyu.di.component;

import dagger.BindsInstance;
import dagger.Component;

import com.jess.arms.di.component.AppComponent;

import com.yixinhuayuan.yiyu.di.module.MyWorksModule;
import com.yixinhuayuan.yiyu.mvp.contract.MyWorksContract;

import com.jess.arms.di.scope.FragmentScope;
import com.yixinhuayuan.yiyu.mvp.ui.fragment.fragment_of_in_pcaactivity.PcaMyWorksFragment;


/**
 * ================================================
 * Description:
 * <p>
 * Created by MVPArmsTemplate on 03/30/2019 12:09
 * <a href="mailto:jess.yan.effort@gmail.com">Contact me</a>
 * <a href="https://github.com/JessYanCoding">Follow me</a>
 * <a href="https://github.com/JessYanCoding/MVPArms">Star me</a>
 * <a href="https://github.com/JessYanCoding/MVPArms/wiki">See me</a>
 * <a href="https://github.com/JessYanCoding/MVPArmsTemplate">模版请保持更新</a>
 * ================================================
 */
@FragmentScope
@Component(modules = MyWorksModule.class, dependencies = AppComponent.class)
public interface MyWorksComponent {
    void inject(PcaMyWorksFragment fragment);

    @Component.Builder
    interface Builder {
        @BindsInstance
        MyWorksComponent.Builder view(MyWorksContract.View view);

        MyWorksComponent.Builder appComponent(AppComponent appComponent);

        MyWorksComponent build();
    }
}