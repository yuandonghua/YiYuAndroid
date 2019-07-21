package com.yixinhuayuan.yiyu.mvp.ui.activity;

import android.Manifest;
import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.BottomNavigationView;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.FragmentManager;
import android.support.v4.content.ContextCompat;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;

import com.jess.arms.base.BaseActivity;
import com.jess.arms.di.component.AppComponent;
import com.jess.arms.utils.ArmsUtils;

import com.jpeng.jptabbar.JPTabBar;
import com.jpeng.jptabbar.anno.NorIcons;
import com.jpeng.jptabbar.anno.SeleIcons;
import com.jpeng.jptabbar.anno.Titles;
import com.yixinhuayuan.yiyu.di.component.DaggerMainComponent;
import com.yixinhuayuan.yiyu.mvp.contract.MainContract;
import com.yixinhuayuan.yiyu.mvp.presenter.MainPresenter;

import com.yixinhuayuan.yiyu.R;

import butterknife.BindView;
import timber.log.Timber;

import static com.jess.arms.utils.Preconditions.checkNotNull;

/**
 * ================================================
 * Description:
 * <p>
 * Created by MVPArmsTemplate on 01/15/2019 22:50
 * <parseJsonUserInfo href="mailto:jess.yan.effort@gmail.com">Contact me</parseJsonUserInfo>
 * <parseJsonUserInfo href="https://github.com/JessYanCoding">Follow me</parseJsonUserInfo>
 * <parseJsonUserInfo href="https://github.com/JessYanCoding/MVPArms">Star me</parseJsonUserInfo>
 * <parseJsonUserInfo href="https://github.com/JessYanCoding/MVPArms/wiki">See me</parseJsonUserInfo>
 * <parseJsonUserInfo href="https://github.com/JessYanCoding/MVPArmsTemplate">模版请保持更新</parseJsonUserInfo>
 * ================================================
 */
public class MainActivity extends BaseActivity<MainPresenter> implements MainContract.View {
   /* @BindView(R.id.bottom_navigation)
    BottomNavigationView bottomNavigationView;
    private NavController mNavController;*/

    /**
     * 底部导航栏实例
     */
    @BindView(R.id.mian_activity_nav)
    JPTabBar main_acty_nav;
    /**
     * 设置底部导航栏 标题
     */
    @Titles
    private static final String[] mTitles = {"首页"
            , "动态"
            , null
            , "消息"
            , "我的"};
    /**
     * 设置底部导航栏 选中图标
     */
    @SeleIcons
    private static final int[] mSeleIcons = {R.drawable.select_home
            , R.drawable.select_trends
            , R.drawable.select_publish
            , R.drawable.select_message
            , R.drawable.select_my};
    /**
     * 设置底部导航栏 未选中图标
     */
    @NorIcons
    private static final int[] mNormalIcons = {R.drawable.no_select_home
            , R.drawable.no_select_trends
            , R.drawable.no_select_publish
            , R.drawable.no_select_message
            , R.drawable.no_select_my};


    @Override
    public void setupActivityComponent(@NonNull AppComponent appComponent) {
        DaggerMainComponent //如找不到该类,请编译一下项目
                .builder()
                .appComponent(appComponent)
                .view(this)
                .build()
                .inject(this);
    }

    @Override
    public int initView(@Nullable Bundle savedInstanceState) {
        return R.layout.activity_main; //如果你不需要框架帮你设置 setContentView(id) 需要自行设置,请返回 0
    }

    @SuppressLint("ResourceAsColor")
    @Override
    public void initData(@Nullable Bundle savedInstanceState) {
        // bottomNavigationView.setOnNavigationItemSelectedListener(mOnNavigationItemSelectedListener);
        // mNavController = Navigation.findNavController(this, R.id.nav_host_fragment);
        // NavigationUI.setupWithNavController(bottomNavigationView, mNavController);

        // 添加权限
        mPresenter.isPermission();
        // 底部导航栏的一些属性设置和事件处理
        mPresenter.mianNavSomeSet(main_acty_nav);
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        Timber.i("点击返回按钮");
    }

    @Override
    public boolean onSupportNavigateUp() {


        return super.onSupportNavigateUp();
    }
//    private BottomNavigationView.OnNavigationItemSelectedListener mOnNavigationItemSelectedListener
//            = new BottomNavigationView.OnNavigationItemSelectedListener() {
//
//        @Override
//        public boolean onNavigationItemSelected(@NonNull MenuItem item) {
//            switch (item.getItemId()) {
//                //点击首页
//                case R.id.navigation_home:
//                    mNavController.navigate(R.id.homeFragment);
//                    return true;
//                //点击动态
//                case R.id.navigation_trends:
//                    mNavController.navigate(R.id.action_homeFragment_to_trendsFragment);
//                    return true;
//                //点击发布
//                case R.id.navigation_publish:
//                    mNavController.navigate(R.id.action_homeFragment_to_publishFragment);
//                    return true;
//                //点击消息
//                case R.id.navigation_messages:
//                    mNavController.navigate(R.id.action_homeFragment_to_messageFragment);
//                    return true;
//                //点击我的
//                case R.id.navigation_my:
//                    mNavController.navigate(R.id.action_homeFragment_to_myFragment);
//                    return true;
//                default:
//            }
//            return false;
//        }
//    };

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

    public void day() {

        recreate();
    }

    public void night() {

    }

}
