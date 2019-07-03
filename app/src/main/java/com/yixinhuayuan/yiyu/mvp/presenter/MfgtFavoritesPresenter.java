package com.yixinhuayuan.yiyu.mvp.presenter;

import android.app.Application;
import android.support.v4.app.Fragment;

import com.jess.arms.integration.AppManager;
import com.jess.arms.di.scope.ActivityScope;
import com.jess.arms.mvp.BasePresenter;
import com.jess.arms.http.imageloader.ImageLoader;

import me.jessyan.rxerrorhandler.core.RxErrorHandler;

import javax.inject.Inject;

import com.yixinhuayuan.yiyu.mvp.contract.MyFgtFavoritesContract;
import com.yixinhuayuan.yiyu.mvp.ui.fragment.in_my.fragment_of_in_mfgtfavorites.MfgtFavoritesTrendsFragment;
import com.yixinhuayuan.yiyu.mvp.ui.fragment.in_my.fragment_of_in_mfgtfavorites.MfgtFavoritesWorksFragment;

import java.util.ArrayList;
import java.util.List;


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
public class MfgtFavoritesPresenter extends BasePresenter<MyFgtFavoritesContract.Model, MyFgtFavoritesContract.View> {

    // favoritesTitle的标题
    public String[] titles = new String[]{"作品", "动态"};
    // favoritesPager的内容
    public List<Fragment> fragments = this.setFragments();
    @Inject
    RxErrorHandler mErrorHandler;
    @Inject
    Application mApplication;
    @Inject
    ImageLoader mImageLoader;
    @Inject
    AppManager mAppManager;

    @Inject
    public MfgtFavoritesPresenter(MyFgtFavoritesContract.Model model, MyFgtFavoritesContract.View rootView) {
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
     * 设置我的界面 我的收藏选项卡里的 作品和动态要展示的Fragment
     *
     * @return
     */
    private ArrayList<Fragment> setFragments() {
        ArrayList<Fragment> fragments = new ArrayList<Fragment>();
        fragments.add(new MfgtFavoritesWorksFragment());
        fragments.add(new MfgtFavoritesTrendsFragment());
        return fragments;
    }
}
