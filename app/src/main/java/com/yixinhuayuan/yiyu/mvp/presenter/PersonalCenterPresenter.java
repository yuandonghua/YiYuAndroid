package com.yixinhuayuan.yiyu.mvp.presenter;

import android.app.Application;
import android.support.v4.app.Fragment;

import com.jess.arms.integration.AppManager;
import com.jess.arms.di.scope.ActivityScope;
import com.jess.arms.mvp.BasePresenter;
import com.jess.arms.http.imageloader.ImageLoader;

import me.jessyan.rxerrorhandler.core.RxErrorHandler;

import javax.inject.Inject;

import com.yixinhuayuan.yiyu.mvp.contract.PersonalCenterContract;
import com.yixinhuayuan.yiyu.mvp.ui.fragment.in_my.fragment_of_in_pcaactivity.PcaMyTrendsFragment;
import com.yixinhuayuan.yiyu.mvp.ui.fragment.in_my.fragment_of_in_pcaactivity.PcaMyWorksFragment;

import java.util.ArrayList;
import java.util.List;


/**
 * ================================================
 * Description:
 * <p>
 * Created by MVPArmsTemplate on 01/29/2019 17:10
 * <a href="mailto:jess.yan.effort@gmail.com">Contact me</a>
 * <a href="https://github.com/JessYanCoding">Follow me</a>
 * <a href="https://github.com/JessYanCoding/MVPArms">Star me</a>
 * <a href="https://github.com/JessYanCoding/MVPArms/wiki">See me</a>
 * <a href="https://github.com/JessYanCoding/MVPArmsTemplate">模版请保持更新</a>
 * ================================================
 */
@ActivityScope
public class PersonalCenterPresenter extends BasePresenter<PersonalCenterContract.Model, PersonalCenterContract.View> {
    @Inject
    RxErrorHandler mErrorHandler;
    @Inject
    Application mApplication;
    @Inject
    ImageLoader mImageLoader;
    @Inject
    AppManager mAppManager;


    // pcaTitle的标题
    public String[] titles = new String[]{"作品", "动态"};
    // pcaPager的内容
    public List<Fragment> fragments = this.setFragments();

    @Inject
    public PersonalCenterPresenter(PersonalCenterContract.Model model, PersonalCenterContract.View rootView) {
        super(model, rootView);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        this.mErrorHandler = null;
        this.mAppManager = null;
        this.mImageLoader = null;
        this.mApplication = null;
    }

    /**
     * 设置个人主页要展示出来的 作品页和动态页。
     *
     * @return
     */
    private ArrayList<Fragment> setFragments() {
        ArrayList<Fragment> fragments = new ArrayList<Fragment>();
        fragments.add(new PcaMyWorksFragment());
        fragments.add(new PcaMyTrendsFragment());
        return fragments;
    }
}
