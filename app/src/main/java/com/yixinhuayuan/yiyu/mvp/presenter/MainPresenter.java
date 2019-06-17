package com.yixinhuayuan.yiyu.mvp.presenter;

import android.app.Application;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.view.LayoutInflater;
import android.view.View;

import com.dim.widget.ImageView;
import com.dim.widget.TextView;
import com.jess.arms.integration.AppManager;
import com.jess.arms.di.scope.ActivityScope;
import com.jess.arms.mvp.BasePresenter;
import com.jess.arms.http.imageloader.ImageLoader;

import me.jessyan.rxerrorhandler.core.RxErrorHandler;

import javax.inject.Inject;

import com.jpeng.jptabbar.JPTabBar;
import com.jpeng.jptabbar.OnTabSelectListener;
import com.yixinhuayuan.yiyu.R;
import com.yixinhuayuan.yiyu.mvp.contract.MainContract;
import com.yixinhuayuan.yiyu.mvp.ui.activity.MainActivity;
import com.yixinhuayuan.yiyu.mvp.ui.activity.in_publichfragment.PublishTrendActivity;
import com.yixinhuayuan.yiyu.mvp.ui.activity.in_publichfragment.PublishWorkActivity;
import com.yixinhuayuan.yiyu.mvp.ui.fragment.HomeFragment;
import com.yixinhuayuan.yiyu.mvp.ui.fragment.MessageFragment;
import com.yixinhuayuan.yiyu.mvp.ui.fragment.MyFragment;
import com.yixinhuayuan.yiyu.mvp.ui.fragment.TrendsFragment;
import com.yixinhuayuan.yiyu.mvp.ui.view.dialog.MyDialog;


/**
 * ================================================
 * Description:
 * <p>
 * Created by MVPArmsTemplate on 01/14/2019 19:14
 * <parseJsonUserInfo href="mailto:jess.yan.effort@gmail.com">Contact me</parseJsonUserInfo>
 * <parseJsonUserInfo href="https://github.com/JessYanCoding">Follow me</parseJsonUserInfo>
 * <parseJsonUserInfo href="https://github.com/JessYanCoding/MVPArms">Star me</parseJsonUserInfo>
 * <parseJsonUserInfo href="https://github.com/JessYanCoding/MVPArms/wiki">See me</parseJsonUserInfo>
 * <parseJsonUserInfo href="https://github.com/JessYanCoding/MVPArmsTemplate">模版请保持更新</parseJsonUserInfo>
 * ================================================
 */
@ActivityScope
public class MainPresenter extends BasePresenter<MainContract.Model, MainContract.View> {
    @Inject
    RxErrorHandler mErrorHandler;
    @Inject
    Application mApplication;
    @Inject
    ImageLoader mImageLoader;
    @Inject
    AppManager mAppManager;

    /**
     * 底部导航栏的,条目下标
     */
    private final static int HOME = 0;// 首页
    private final static int TRENDS = 1;// 动态
    private final static int PUBLISH = 2;// 发布
    private final static int MESSAGE = 3;// 消息
    private final static int MY = 4;// 我的
    /**
     * Fragment的管理器,用于向主界面不居中的FrameLayou中添加Fragment
     */
    private FragmentTransaction fragmentTransaction;
    /**
     * 导航栏个item对应的Fragment
     */
    private Fragment home;// 首页
    private Fragment trends;// 动态
    private Fragment publish;// 发布
    private Fragment message;// 消息
    private Fragment my;// 我的

    @Inject
    public MainPresenter(MainContract.Model model, MainContract.View rootView) {
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
     * 底部导航栏一些操作
     * 1.设置一些属性
     * 2.给item设置事件处理,点击不通的条目跳转不通的Fragment
     *
     * @param jpTabBar
     */

    public void mianNavSomeSet(JPTabBar jpTabBar) {
        // 给导航栏设置一些,属性
        jpTabBar.setTabMargin(3);
        jpTabBar.setBottom(0);
        jpTabBar.setTabTextSize(13);
        jpTabBar.setSelectedColor(Color.parseColor("#C83A3A"));

        // 当主界面初始化后,并且首页处于选中转台,立刻加载首页布局
        // Log.d("SELE", "选中的是哪个item: " + jpTabBar.getSelectedTab().getTitle() + "");
        // Log.d("SELE", "选中的位置: " + jpTabBar.getSelectPosition() + "");
        if (jpTabBar.getSelectPosition() == HOME) {
            home = new HomeFragment();
            ((AppCompatActivity) mRootView)
                    .getSupportFragmentManager()
                    .beginTransaction()
                    .add(R.id.nav_host_fragment, home)
                    .commit();
        }

        // 给导航栏设置事件,点击不同的条目跳转到不同的Fragment
        // 定点击item的时候,首先判断当前Fragment是为null,如果为空就create,反之直接刷新
        setSelectClick(jpTabBar);
    }

    /**
     * 给导航栏设置事件,点击不同的条目跳转到不同的Fragment
     * 定点击item的时候,首先判断当前Fragment是为null,如果为空就create,反之直接刷新
     *
     * @param jpTabBar 导航栏
     */
    public void setSelectClick(JPTabBar jpTabBar) {
        jpTabBar.setTabListener(new OnTabSelectListener() {
            @Override
            public void onTabSelect(int index) {
                // Fragment的管理器,用于向主界面不居中的FrameLayou中添加Fragment
                fragmentTransaction = ((AppCompatActivity) mRootView).getSupportFragmentManager().beginTransaction();
                switch (index) {
                    case HOME:
                        hideAllFragment();
                        if (home == null) {
                            home = new HomeFragment();
                            fragmentTransaction.add(R.id.nav_host_fragment, home).commit();
                        } else {
                            fragmentTransaction.show(home).commit();
                        }
                        break;
                    case TRENDS:
                        hideAllFragment();
                        if (trends == null) {
                            trends = new TrendsFragment();
                            fragmentTransaction.add(R.id.nav_host_fragment, trends).commit();
                        } else {
                            fragmentTransaction.show(trends).commit();
                        }
                        break;
                    case PUBLISH:
                        /*hideAllFragment();
                        if (publish == null) {
                            publish = new PublishFragment();
                            fragmentTransaction.add(R.id.nav_host_fragment, publish).commit();
                        } else {
                            fragmentTransaction.show(publish).commit();
                        }*/
                        // 跳转到选择发布界面
                        setSelectPublishDialog(jpTabBar);
                        //((Activity) mRootView).startActivity(new Intent(((Activity) mRootView), SelectPublishActivity.class));
                        break;
                    case MESSAGE:
                        hideAllFragment();
                        if (message == null) {
                            message = new MessageFragment();
                            fragmentTransaction.add(R.id.nav_host_fragment, message).commit();
                        } else {
                            fragmentTransaction.show(message).commit();
                        }
                        break;
                    case MY:
                        hideAllFragment();
                        if (my == null) {
                            my = new MyFragment();
                            fragmentTransaction.add(R.id.nav_host_fragment, my).commit();
                        } else {
                            fragmentTransaction.show(my).commit();
                        }
                        break;
                }
            }

            @Override
            public boolean onInterruptSelect(int index) {
                return false;
            }
        });
    }

    /**
     * 隐藏所有Fragment
     */
    private void hideAllFragment() {
        if (home != null) {
            fragmentTransaction.hide(home);
        }
        if (trends != null) {
            fragmentTransaction.hide(trends);
        }
        if (publish != null) {
            fragmentTransaction.hide(publish);
        }
        if (message != null) {
            fragmentTransaction.hide(message);
        }
        if (my != null) {
            fragmentTransaction.hide(my);
        }
    }

    /**
     * 点击 发布 出现的选择是发布动态还是发布作品的界面
     * @param jpTabBar
     */
    private void setSelectPublishDialog(JPTabBar jpTabBar) {

        MyDialog dialog = new MyDialog((MainActivity) mRootView);
        View view = LayoutInflater.from((MainActivity) mRootView).inflate(R.layout.layout_select_publish_dialog, null);
        dialog.show();
        dialog.setContentView(view);
        TextView trends = dialog.findViewById(R.id.tv_selecttrends_spdialog);
        TextView woeks = dialog.findViewById(R.id.tv_selectwork_spdialog);
        ImageView select = dialog.findViewById(R.id.iv_isselect_publish);

        /**
         * 跳转到发布动态界面
         */
        trends.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ((MainActivity) mRootView).startActivity(new Intent((MainActivity) mRootView, PublishTrendActivity.class));
            }
        });
        /**
         * 跳转到发布作品界面
         */
        woeks.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ((MainActivity) mRootView).startActivity(new Intent((MainActivity) mRootView, PublishWorkActivity.class));
            }
        });
        /**
         * 关闭当前选着发布类型界面dialog
         */
        select.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
            }
        });
    }

}
