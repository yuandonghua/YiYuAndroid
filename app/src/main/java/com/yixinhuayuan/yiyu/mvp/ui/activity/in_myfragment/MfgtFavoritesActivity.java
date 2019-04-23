package com.yixinhuayuan.yiyu.mvp.ui.activity.in_myfragment;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
import android.support.v7.widget.RecyclerView;

import com.jess.arms.base.BaseActivity;
import com.jess.arms.di.component.AppComponent;
import com.jess.arms.utils.ArmsUtils;

import com.yixinhuayuan.yiyu.app.utils.adapter.MffaTablOrPagerAdapter;
import com.yixinhuayuan.yiyu.di.component.DaggerMyFgtFavoritesComponent;
import com.yixinhuayuan.yiyu.mvp.contract.MyFgtFavoritesContract;
import com.yixinhuayuan.yiyu.mvp.presenter.MfgtFavoritesPresenter;

import com.yixinhuayuan.yiyu.R;


import butterknife.BindView;
import butterknife.OnClick;

import static com.jess.arms.utils.Preconditions.checkNotNull;


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
public class MfgtFavoritesActivity extends BaseActivity<MfgtFavoritesPresenter> implements MyFgtFavoritesContract.View {
    /**
     * 我的收藏界面 动态 或 作品的标题
     */
    @BindView(R.id.tabl_myfgtfavorites_title)
    TabLayout favoritesTitle;
    /**
     * 我的收藏界面 动态 或 作品的标题对应的内容
     */
    @BindView(R.id.vp_myfgtfavorites_content)
    ViewPager favoritesPager;

    @Override
    public void setupActivityComponent(@NonNull AppComponent appComponent) {
        DaggerMyFgtFavoritesComponent //如找不到该类,请编译一下项目
                .builder()
                .appComponent(appComponent)
                .view(this)
                .build()
                .inject(this);
    }

    @Override
    public int initView(@Nullable Bundle savedInstanceState) {
        return R.layout.activity_my_fgt_favorites; //如果你不需要框架帮你设置 setContentView(id) 需要自行设置,请返回 0
    }

    @Override
    public void initData(@Nullable Bundle savedInstanceState) {
        setTablOrPagerAdapter();
    }

    @Override
    public void showLoading() {

    }

    @Override
    public void hideLoading() {

    }

    @Override
    public void showMessage(@NonNull String message) {
        checkNotNull(message);
        ArmsUtils.snackbarText(message);
    }

    @Override
    public void launchActivity(@NonNull Intent intent) {
        checkNotNull(intent);
        ArmsUtils.startActivity(intent);
    }

    @Override
    public void killMyself() {
        finish();
    }

    private void setTablOrPagerAdapter() {
        MffaTablOrPagerAdapter adapter = new MffaTablOrPagerAdapter(getSupportFragmentManager(), mPresenter.fragments, mPresenter.titles);
        favoritesPager.setAdapter(adapter);
        favoritesTitle.setupWithViewPager(favoritesPager);
    }

    /**
     * 关闭当前界面
     */
    @OnClick(R.id.iv_mfgtmyworks_back)
    void back(){
        this.finish();
    }
}
