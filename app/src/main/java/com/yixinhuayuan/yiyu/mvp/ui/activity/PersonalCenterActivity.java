package com.yixinhuayuan.yiyu.mvp.ui.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.view.ViewPager;
import android.support.design.widget.TabLayout;

import com.jess.arms.base.BaseActivity;
import com.jess.arms.di.component.AppComponent;
import com.jess.arms.utils.ArmsUtils;

import com.yixinhuayuan.yiyu.app.utils.adapter.PcaTablOrPagerAdapter;
import com.yixinhuayuan.yiyu.di.component.DaggerMyCenterActivityComponent;
import com.yixinhuayuan.yiyu.mvp.contract.PersonalCenterContract;
import com.yixinhuayuan.yiyu.mvp.presenter.PersonalCenterPresenter;

import com.yixinhuayuan.yiyu.R;


import butterknife.BindView;
import butterknife.OnClick;

import static com.jess.arms.utils.Preconditions.checkNotNull;


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
public class PersonalCenterActivity extends BaseActivity<PersonalCenterPresenter> implements PersonalCenterContract.View {

    // 个人主页的 作品或动态的标题容器
    @BindView(R.id.tabl_title_pca)
    TabLayout pcaTabl;
    // 个人主页的 作品或动态定的页面容器
    @BindView(R.id.vp_content_pca)
    ViewPager pcaVpage;

    @Override
    public void setupActivityComponent(@NonNull AppComponent appComponent) {
        DaggerMyCenterActivityComponent //如找不到该类,请编译一下项目
                .builder()
                .appComponent(appComponent)
                .view(this)
                .build()
                .inject(this);
    }

    @Override
    public int initView(@Nullable Bundle savedInstanceState) {
        return R.layout.activity_my_center; //如果你不需要框架帮你设置 setContentView(id) 需要自行设置,请返回 0
    }

    @Override
    public void initData(@Nullable Bundle savedInstanceState) {
        this.setTablAndVpager();
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

    @OnClick(R.id.tv_edit_userinfo)
    void gotoEditUserInfo() {
        startActivity(new Intent(this, EditUserInfoActivity.class));
    }

    /**
     * 给pcaTabl和pcaPager设置适配器进行数据匹配
     */
    private void setTablAndVpager() {
        PcaTablOrPagerAdapter adapter = new PcaTablOrPagerAdapter(getSupportFragmentManager(), mPresenter.fragments, mPresenter.titles);
        pcaVpage.setAdapter(adapter);
        pcaTabl.setupWithViewPager(pcaVpage);
    }

    /**
     * 关闭当前界面
     */
    @OnClick(R.id.iv_pc_back)
    void back(){
        this.finish();
    }
}
