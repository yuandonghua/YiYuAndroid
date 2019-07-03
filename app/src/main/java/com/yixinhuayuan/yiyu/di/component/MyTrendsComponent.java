package com.yixinhuayuan.yiyu.di.component;

import dagger.BindsInstance;
import dagger.Component;

import com.jess.arms.di.component.AppComponent;

import com.yixinhuayuan.yiyu.di.module.MyTrendsModule;
import com.yixinhuayuan.yiyu.mvp.contract.MyTrendsContract;

import com.jess.arms.di.scope.FragmentScope;
import com.yixinhuayuan.yiyu.mvp.ui.fragment.in_my.fragment_of_in_pcaactivity.PcaMyTrendsFragment;


/**
 * ================================================
 * Description:
 * <p>
 * Created by MVPArmsTemplate on 03/30/2019 13:53
 * <a href="mailto:jess.yan.effort@gmail.com">Contact me</a>
 * <a href="https://github.com/JessYanCoding">Follow me</a>
 * <a href="https://github.com/JessYanCoding/MVPArms">Star me</a>
 * <a href="https://github.com/JessYanCoding/MVPArms/wiki">See me</a>
 * <a href="https://github.com/JessYanCoding/MVPArmsTemplate">模版请保持更新</a>
 * ================================================
 */
@FragmentScope
@Component(modules = MyTrendsModule.class, dependencies = AppComponent.class)
public interface MyTrendsComponent {
    void inject(PcaMyTrendsFragment fragment);

    @Component.Builder
    interface Builder {
        @BindsInstance
        MyTrendsComponent.Builder view(MyTrendsContract.View view);

        MyTrendsComponent.Builder appComponent(AppComponent appComponent);

        MyTrendsComponent build();
    }
}