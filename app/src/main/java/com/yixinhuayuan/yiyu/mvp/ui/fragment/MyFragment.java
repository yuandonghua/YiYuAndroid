package com.yixinhuayuan.yiyu.mvp.ui.fragment;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
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
import com.yixinhuayuan.yiyu.app.utils.userinfo.SPUserInfo;
import com.yixinhuayuan.yiyu.di.component.DaggerMyComponent;
import com.yixinhuayuan.yiyu.mvp.contract.MyContract;
import com.yixinhuayuan.yiyu.mvp.presenter.MyPresenter;

import com.yixinhuayuan.yiyu.R;
import com.yixinhuayuan.yiyu.mvp.ui.activity.LoginActivity;
import com.yixinhuayuan.yiyu.mvp.ui.activity.PersonalCenterActivity;
import com.yixinhuayuan.yiyu.mvp.ui.activity.in_myfragment.MfgtAttentionListActivity;
import com.yixinhuayuan.yiyu.mvp.ui.activity.in_myfragment.MfgtFansListActivity;
import com.yixinhuayuan.yiyu.mvp.ui.activity.in_myfragment.MfgtFavoritesActivity;
import com.yixinhuayuan.yiyu.mvp.ui.activity.in_myfragment.MfgtMyWorksActivity;
import com.yixinhuayuan.yiyu.mvp.ui.activity.in_myfragment.MfgtSettingActivity;
import com.yixinhuayuan.yiyu.mvp.ui.activity.in_myfragment.MfgtTrendsActivity;
import com.yixinhuayuan.yiyu.mvp.ui.view.DayNightToggleButton;
import com.yixinhuayuan.yiyu.mvp.ui.view.ToggleSettings;

import butterknife.BindView;
import butterknife.OnClick;

import static com.jess.arms.utils.Preconditions.checkNotNull;


/**
 * ================================================
 * Description:我的页面
 * <p>
 * Created by MVPArmsTemplate on 01/15/2019 17:19
 * <parseJsonUserInfo href="mailto:jess.yan.effort@gmail.com">Contact me</parseJsonUserInfo>
 * <parseJsonUserInfo href="https://github.com/JessYanCoding">Follow me</parseJsonUserInfo>
 * <parseJsonUserInfo href="https://github.com/JessYanCoding/MVPArms">Star me</parseJsonUserInfo>
 * <parseJsonUserInfo href="https://github.com/JessYanCoding/MVPArms/wiki">See me</parseJsonUserInfo>
 * <parseJsonUserInfo href="https://github.com/JessYanCoding/MVPArmsTemplate">模版请保持更新</parseJsonUserInfo>
 * ================================================
 */
public class MyFragment extends BaseFragment<MyPresenter> implements MyContract.View {

    /**
     * 主题切换按钮
     */
    @BindView(R.id.dayNightToggleButton)
    DayNightToggleButton dayNightToggleButton;
    private boolean initDayOrNight = true;
    /**
     * 跳到个人中心的控件
     */
    @BindView(R.id.userDetails)
    TextView userDetails;

    /**
     * 用户昵称
     */
    @BindView(R.id.nickName)
    TextView nickName;
    /**
     * 个性签名
     */
    @BindView(R.id.userSign)
    TextView userSign;
    /**
     * 关注数
     */
    @BindView(R.id.attentionQuantity)
    TextView star;
    /**
     * 粉丝数
     */
    @BindView(R.id.fanQuantity)
    TextView fans;

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

    public int a = 0;

    @Override
    public void initData(@Nullable Bundle savedInstanceState) {
        // 初始化用户简介
        initUserInfo();
        // 判断是否登录
        @SuppressLint("WrongConstant")
        SharedPreferences sp = getContext().getSharedPreferences(getContext().getPackageName()
                , Context.MODE_APPEND);
        boolean is_login = sp.getBoolean("is_login", false);
        if (!is_login) {
            startActivity(new Intent(getContext(), LoginActivity.class));
        }
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

    // 启动个人中心
    @OnClick(R.id.userDetails)
    void toUserDetails() {
        startActivity(new Intent(getContext()
                , PersonalCenterActivity.class));
    }

    /**
     * 点击关注跳转到关注列表界面
     */
    @OnClick(R.id.attentionQuantity)
    void toAttentionList() {
        startActivity(new Intent(this.getContext(), MfgtAttentionListActivity.class));
    }

    /**
     * 点击关注跳转到关注列表界面
     */
    @OnClick(R.id.fanQuantity)
    void toFanQuantity() {
        startActivity(new Intent(this.getContext(), MfgtFansListActivity.class));
    }

    /**
     * 点击 我的界面的设置跳转到 设置界面
     */
    @OnClick(R.id.tv_myfgt_setting)
    void toSetting() {

        startActivity(new Intent(this.getContext(), MfgtSettingActivity.class));
    }

    /**
     * 在 我的 界面点击 我的作品 选项跳转到我的作品界面
     */
    @OnClick(R.id.tv_myfgt_myworks)
    void toMyWorks() {
        startActivity(new Intent(this.getContext(), MfgtMyWorksActivity.class));
    }

    /**
     * 在 我的 界面点击 我的动态 选项跳转到我的动态界面
     */
    @OnClick(R.id.tv_myfgt_trends)
    void toMyTrends() {
        startActivity(new Intent(this.getContext(), MfgtTrendsActivity.class));
    }

    /**
     * 在 我的 界面点击 我的收藏 选项跳转到我的收藏界面
     */
    @OnClick(R.id.tv_myfgt_favorites)
    void toMyFavorites() {
        startActivity(new Intent(this.getContext(), MfgtFavoritesActivity.class));
    }

    /**
     * 初始化我的界面用户简介数据
     */
    private void initUserInfo() {
        SPUserInfo.setContext(getContext());
        // 设置昵称
        nickName.setText(SPUserInfo.spUserInfo().getString("nick_name", null));
        // 设置关注数
        star.setText("关注:" + SPUserInfo.spUserInfo().getInt("star", 0));
        // 设置粉丝数
        fans.setText("粉丝:" + SPUserInfo.spUserInfo().getInt("fans", 0));
        // 设置个性签名
        userSign.setText(SPUserInfo.spUserInfo().getString("introduce", null));

        SPUserInfo.delContext();
    }

}
