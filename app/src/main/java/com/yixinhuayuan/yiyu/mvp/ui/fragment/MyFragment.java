package com.yixinhuayuan.yiyu.mvp.ui.fragment;

import android.content.Intent;
import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.TextView;

import com.dim.skin.SkinConfig;
import com.dim.skin.SkinStyle;
import com.dim.skin.hepler.SkinCompat;
import com.jess.arms.base.BaseFragment;
import com.jess.arms.di.component.AppComponent;
import com.jess.arms.utils.ArmsUtils;

import com.wuyr.rippleanimation.RippleAnimation;
import com.yixinhuayuan.yiyu.app.GlobalConfiguration;
import com.yixinhuayuan.yiyu.di.component.DaggerMyComponent;
import com.yixinhuayuan.yiyu.mvp.contract.MyContract;
import com.yixinhuayuan.yiyu.mvp.presenter.MyPresenter;

import com.yixinhuayuan.yiyu.R;
import com.yixinhuayuan.yiyu.mvp.ui.activity.SettingActivity;
import com.yixinhuayuan.yiyu.mvp.ui.view.DayNightToggleButton;
import com.yixinhuayuan.yiyu.mvp.ui.view.ToggleSettings;

import androidx.navigation.Navigation;
import androidx.navigation.fragment.NavHostFragment;
import butterknife.BindView;
import timber.log.Timber;

import static com.jess.arms.utils.Preconditions.checkNotNull;


/**
 * ================================================
 * Description:我的页面
 * <p>
 * Created by MVPArmsTemplate on 01/15/2019 17:19
 * <a href="mailto:jess.yan.effort@gmail.com">Contact me</a>
 * <a href="https://github.com/JessYanCoding">Follow me</a>
 * <a href="https://github.com/JessYanCoding/MVPArms">Star me</a>
 * <a href="https://github.com/JessYanCoding/MVPArms/wiki">See me</a>
 * <a href="https://github.com/JessYanCoding/MVPArmsTemplate">模版请保持更新</a>
 * ================================================
 */
public class MyFragment extends BaseFragment<MyPresenter> implements MyContract.View {

    /**
     * 主题切换按钮
     */
    @BindView(R.id.dayNightToggleButton)
    DayNightToggleButton dayNightToggleButton;
    private boolean initDayOrNight = true;

    public static MyFragment newInstance() {
        MyFragment fragment = new MyFragment();
        return fragment;
    }

    @Override
    public void setupFragmentComponent(@NonNull AppComponent appComponent) {
        DaggerMyComponent //如找不到该类,请编译一下项目
                .builder()
                .appComponent(appComponent)
                .view(this)
                .build()
                .inject(this);
    }

    @Override
    public View initView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_my, container, false);
    }

    @Override
    public void initData(@Nullable Bundle savedInstanceState) {
        //跳转登陆页面
//        NavHostFragment.findNavController(this).navigate(R.id.action_myFragment_to_settingActivity);
        initThemeButton();
    }


    /**
     * 初始化主题风格view
     */
    private void initThemeButton() {

        ToggleSettings mBuilderSettings = new ToggleSettings.Builder()
                .setToggleUnCheckedColor(getResources().getColor(R.color.light_day))
                .setBackgroundUncheckedColor(getResources().getColor(R.color.gray))
                .setToggleCheckedColor(getResources().getColor(R.color.night_day))
                .setBackgroundCheckedColor(getResources().getColor(R.color.gray))
                .setDuration(200)
                .buildSettings();
        dayNightToggleButton.setToggleSettings(mBuilderSettings);
        if (SkinConfig.getSkinStyle(getActivity()) == SkinStyle.Dark) {
            //需要延时设置默认模式按钮样式,初始化按钮设置
            new Handler().postDelayed(() -> {
                if (dayNightToggleButton != null) {
                    dayNightToggleButton.toggle();
                }
            }, 50);
        }
        dayNightToggleButton.setOnCheckChangeListener(new DayNightToggleButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(View buttonView, boolean isChecked) {
                if (isChecked && SkinConfig.getSkinStyle(getActivity()) == SkinStyle.Light) {
                    //日间模式
                    selectDayOrNight(buttonView, SkinStyle.Dark);
                } else if (!isChecked && SkinConfig.getSkinStyle(getActivity()) == SkinStyle.Dark) {
                    //夜间模式
                    selectDayOrNight(buttonView, SkinStyle.Light);
                }

            }
        });


    }

    /**
     * 切换皮肤设置
     *
     * @param buttonView
     * @param skinStyle
     */
    private void selectDayOrNight(View buttonView, SkinStyle skinStyle) {
        //切换动画
        RippleAnimation.create(buttonView).setDuration(250).start();
        //切换皮肤
        SkinCompat.setSkinStyle(getActivity(), skinStyle, mSkinStyleChangeListenerImp);
    }

    private SkinStyleChangeListenerImp mSkinStyleChangeListenerImp = new SkinStyleChangeListenerImp();

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

    private void initStatusBar() {
        Window window = getActivity().getWindow();
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
            window.setFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS,
                    WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                window.clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS
                        | WindowManager.LayoutParams.FLAG_TRANSLUCENT_NAVIGATION);
                window.getDecorView().setSystemUiVisibility(View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN
                        | View.SYSTEM_UI_FLAG_LAYOUT_HIDE_NAVIGATION
                        | View.SYSTEM_UI_FLAG_LAYOUT_STABLE);
                window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
                window.setStatusBarColor(Color.TRANSPARENT);
                window.setNavigationBarColor(Color.TRANSPARENT);
            }
        }
    }

    /**
     * 通过此方法可以使 Fragment 能够与外界做一些交互和通信, 比如说外部的 Activity 想让自己持有的某个 Fragment 对象执行一些方法,
     * 建议在有多个需要与外界交互的方法时, 统一传 {@link Message}, 通过 what 字段来区分不同的方法, 在 {@link #setData(Object)}
     * 方法中就可以 {@code switch} 做不同的操作, 这样就可以用统一的入口方法做多个不同的操作, 可以起到分发的作用
     * <p>
     * 调用此方法时请注意调用时 Fragment 的生命周期, 如果调用 {@link #setData(Object)} 方法时 {@link Fragment#onCreate(Bundle)} 还没执行
     * 但在 {@link #setData(Object)} 里却调用了 Presenter 的方法, 是会报空的, 因为 Dagger 注入是在 {@link Fragment#onCreate(Bundle)} 方法中执行的
     * 然后才创建的 Presenter, 如果要做一些初始化操作,可以不必让外部调用 {@link #setData(Object)}, 在 {@link #initData(Bundle)} 中初始化就可以了
     * <p>
     * Example usage:
     * <pre>
     * public void setData(@Nullable Object data) {
     *     if (data != null && data instanceof Message) {
     *         switch (((Message) data).what) {
     *             case 0:
     *                 loadData(((Message) data).arg1);
     *                 break;
     *             case 1:
     *                 refreshUI();
     *                 break;
     *             default:
     *                 //do something
     *                 break;
     *         }
     *     }
     * }
     *
     * // call setData(Object):
     * Message data = new Message();
     * data.what = 0;
     * data.arg1 = 1;
     * fragment.setData(data);
     * </pre>
     *
     * @param data 当不需要参数时 {@code data} 可以为 {@code null}
     */
    @Override
    public void setData(@Nullable Object data) {

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

    }
}
