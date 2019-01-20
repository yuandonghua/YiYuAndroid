package com.yixinhuayuan.yiyu.mvp.ui.activity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatDelegate;
import android.view.View;

import com.dim.circletreveal.CircularRevealCompat;
import com.dim.skin.SkinConfig;
import com.dim.skin.SkinStyle;
import com.dim.skin.hepler.SkinCompat;
import com.dim.widget.animation.CRAnimation;
import com.jess.arms.base.BaseActivity;
import com.jess.arms.di.component.AppComponent;
import com.jess.arms.utils.ArmsUtils;

import com.wuyr.rippleanimation.RippleAnimation;
import com.yixinhuayuan.yiyu.di.component.DaggerSettingComponent;
import com.yixinhuayuan.yiyu.mvp.contract.SettingContract;
import com.yixinhuayuan.yiyu.mvp.presenter.SettingPresenter;

import com.yixinhuayuan.yiyu.R;
import com.yixinhuayuan.yiyu.mvp.ui.view.DayNightToggleButton;
import com.yixinhuayuan.yiyu.mvp.ui.view.ToggleSettings;


import butterknife.BindView;

import static android.support.v7.app.AppCompatDelegate.MODE_NIGHT_NO;
import static android.support.v7.app.AppCompatDelegate.MODE_NIGHT_YES;
import static com.jess.arms.utils.Preconditions.checkNotNull;


/**
 * ================================================
 * Description:
 * <p>
 * Created by MVPArmsTemplate on 01/19/2019 23:11
 * <a href="mailto:jess.yan.effort@gmail.com">Contact me</a>
 * <a href="https://github.com/JessYanCoding">Follow me</a>
 * <a href="https://github.com/JessYanCoding/MVPArms">Star me</a>
 * <a href="https://github.com/JessYanCoding/MVPArms/wiki">See me</a>
 * <a href="https://github.com/JessYanCoding/MVPArmsTemplate">模版请保持更新</a>
 * ================================================
 */
public class SettingActivity extends BaseActivity<SettingPresenter> implements SettingContract.View {
    /**
     * 主题切换按钮
     */
    @BindView(R.id.dayNightToggleButton)
    DayNightToggleButton dayNightToggleButton;
    private boolean mDayOrNight = true;

    @Override
    public void setupActivityComponent(@NonNull AppComponent appComponent) {
        DaggerSettingComponent //如找不到该类,请编译一下项目
                .builder()
                .appComponent(appComponent)
                .view(this)
                .build()
                .inject(this);
    }

    @Override
    public int initView(@Nullable Bundle savedInstanceState) {
        return R.layout.activity_setting; //如果你不需要框架帮你设置 setContentView(id) 需要自行设置,请返回 0
    }

    @Override
    public void initData(@Nullable Bundle savedInstanceState) {
        initThemeButton();

    }

    /**
     * 初始化主题风格view
     */
    private void initThemeButton() {

        ToggleSettings mBuilderSettings = new ToggleSettings.Builder()
                .setToggleUnCheckedColor(getResources().getColor(R.color.colorPrimaryDark))
                .setBackgroundUncheckedColor(getResources().getColor(R.color.gray))
                .setToggleCheckedColor(getResources().getColor(R.color.white))
                .setBackgroundCheckedColor(getResources().getColor(R.color.gray))
                .setDuration(200)
                .buildSettings();
        dayNightToggleButton.setToggleSettings(mBuilderSettings);
        dayNightToggleButton.setOnCheckChangeListener(new DayNightToggleButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(View buttonView, boolean isChecked) {
                //夜间模式切换
                //切换动画
                RippleAnimation.create(buttonView).setDuration(250).start();
                //日间操作
                if (mDayOrNight) {
                    mDayOrNight = false;
                    SkinStyle skinStyle = null;
                    if (SkinConfig.getSkinStyle(SettingActivity.this) == SkinStyle.Dark) {
                        skinStyle = SkinStyle.Light;
                    } else {
                        skinStyle = SkinStyle.Dark;
                    }
                    SkinCompat.setSkinStyle(SettingActivity.this, skinStyle, mSkinStyleChangeListenerImp) ;
                } else {
                    //夜间操作
                    mDayOrNight = true;
                    SkinStyle skinStyle = null;
                    if (SkinConfig.getSkinStyle(SettingActivity.this) == SkinStyle.Dark) {
                        skinStyle = SkinStyle.Light;
                    } else {
                        skinStyle = SkinStyle.Dark;
                    }
                    SkinCompat.setSkinStyle(SettingActivity.this, skinStyle, mSkinStyleChangeListenerImp) ;

                }


            }
        });
    }
    private SkinStyleChangeListenerImp mSkinStyleChangeListenerImp=new SkinStyleChangeListenerImp();

    class SkinStyleChangeListenerImp implements SkinCompat.SkinStyleChangeListener {

        @Override
        public void beforeChange() {

        }

        @Override
        public void afterChange() {

//            mRl.postDelayed(new Runnable() {
//                @Override
//                public void run() {
//                    mRl.setVisibility(View.VISIBLE);
//
//                    CRAnimation crA =
//                            new CircularRevealCompat(mRl).circularReveal(
//                                    mFloatingActionButton.getLeft() + mFloatingActionButton.getWidth() / 2, mFloatingActionButton.getTop() + mFloatingActionButton.getHeight() / 2, 0, mRl.getHeight());
//
//                    if (crA != null)
//                        crA.start();
//                }
//            },600);


        }

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

}
