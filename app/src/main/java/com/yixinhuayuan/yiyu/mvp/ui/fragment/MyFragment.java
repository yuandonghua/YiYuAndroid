package com.yixinhuayuan.yiyu.mvp.ui.fragment;

import android.content.Intent;
import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
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

import com.jess.arms.base.BaseFragment;
import com.jess.arms.di.component.AppComponent;
import com.jess.arms.utils.ArmsUtils;

import com.wuyr.rippleanimation.RippleAnimation;
import com.yixinhuayuan.yiyu.di.component.DaggerMyComponent;
import com.yixinhuayuan.yiyu.mvp.contract.MyContract;
import com.yixinhuayuan.yiyu.mvp.presenter.MyPresenter;

import com.yixinhuayuan.yiyu.R;
import com.yixinhuayuan.yiyu.mvp.ui.view.DayNightToggleButton;
import com.yixinhuayuan.yiyu.mvp.ui.view.ToggleSettings;

import androidx.navigation.Navigation;
import androidx.navigation.fragment.NavHostFragment;
import butterknife.BindView;
import timber.log.Timber;

import static com.jess.arms.utils.Preconditions.checkNotNull;


/**
 * ================================================
 * Description:
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
    /**
     * 根布局
     */
    @BindView(R.id.container)
    ViewGroup container;
    /**
     * 子view数组
     */
    private View[] mChildViews;
    private boolean mDayOrNight = true;
    private int mDayOrNightColor;

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
//        initStatusBar();
        initThemeButton();
        //跳转登陆页面
//        NavHostFragment.findNavController(this).navigate(R.id.action_myFragment_to_loginActivity);
    }

    /**
     * 初始化主题风格view
     */
    private void initThemeButton() {
        //获取所有子view
        mChildViews = new View[container.getChildCount()];
        for (int i = 0; i < mChildViews.length; i++) {
            mChildViews[i] = container.getChildAt(i);
        }

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
                    mDayOrNightColor = getResources().getColor(R.color.black);
                } else {
                    //夜间操作
                    mDayOrNight = true;
                    mDayOrNightColor = getResources().getColor(R.color.white);
                }


//                switch (view.getId()) {
//                    case R.id.red:
//                        color = Color.RED;
//                        break;
//                    case R.id.green:
//                        color = Color.GREEN;
//                        break;
//                    case R.id.blue:
//                        color = Color.BLUE;
//                        break;
//                    case R.id.yellow:
//                        color = Color.YELLOW;
//                        break;
//                    case R.id.black:
//                        color = Color.DKGRAY;
//                        break;
//                    case R.id.cyan:
//                        color = Color.CYAN;
//                        break;
//                    default:
//                        color = Color.TRANSPARENT;
//                        break;
//                }
                updateColor(mDayOrNightColor);

            }
        });
    }

    /**
     * 更新UI主题颜色
     *
     * @param color
     */
    private void updateColor(int color) {
        for (View view : mChildViews) {
            if (view instanceof TextView) {
                ((TextView) view).setTextColor(color);
            } else {
                view.setBackgroundColor(color);
            }
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
