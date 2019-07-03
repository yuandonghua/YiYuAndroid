package com.yixinhuayuan.yiyu.mvp.ui.fragment.in_home;

import android.content.Intent;
import android.os.Bundle;
import android.os.Message;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.jess.arms.base.BaseFragment;
import com.jess.arms.di.component.AppComponent;
import com.jess.arms.utils.ArmsUtils;

import com.yixinhuayuan.yiyu.app.utils.adapter.in_home.HomeWorksClassifyAdapter;
import com.yixinhuayuan.yiyu.di.component.DaggerHomeWorksListComponent;
import com.yixinhuayuan.yiyu.mvp.contract.HomeWorksListContract;
import com.yixinhuayuan.yiyu.mvp.presenter.HomeWorksListPresenter;

import com.yixinhuayuan.yiyu.R;

import java.lang.reflect.Array;
import java.util.ArrayList;

import butterknife.BindView;

import static com.jess.arms.utils.Preconditions.checkNotNull;


/**
 * ================================================
 * Description:
 * <p>
 * Created by MVPArmsTemplate on 06/29/2019 01:38
 * <a href="mailto:jess.yan.effort@gmail.com">Contact me</a>
 * <a href="https://github.com/JessYanCoding">Follow me</a>
 * <a href="https://github.com/JessYanCoding/MVPArms">Star me</a>
 * <a href="https://github.com/JessYanCoding/MVPArms/wiki">See me</a>
 * <a href="https://github.com/JessYanCoding/MVPArmsTemplate">模版请保持更新</a>
 * ================================================
 */
public class HomeWorksListFragment extends BaseFragment<HomeWorksListPresenter> implements HomeWorksListContract.View {
    public static HomeWorksListFragment newInstance(ArrayList<Fragment> classify, String[] tabTitles) {
        HomeWorksListFragment fragment = new HomeWorksListFragment();
        fragment.classify = classify;
        fragment.tabTitles = tabTitles;
        return fragment;
    }


    @Override
    public void setupFragmentComponent(@NonNull AppComponent appComponent) {
        DaggerHomeWorksListComponent //如找不到该类,请编译一下项目
                .builder()
                .appComponent(appComponent)
                .view(this)
                .build()
                .inject(this);
    }

    @Override
    public View initView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_home_works_list, container, false);
    }

    @Override
    public void initData(@Nullable Bundle savedInstanceState) {
        // 初始化首页作品列表
        initWorksView();
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

    /**
     * 拿到首页用于展示导航栏标题的控件TabLayout
     */
    @BindView(R.id.tl_titles_home)
    TabLayout titles;
    /**
     * 拿到首页每个导航标题对应的作品列表
     */
    @BindView(R.id.page_works_home)
    ViewPager works;
    /**
     * 不同标题对应的ViewPager里面装的内容
     */
    private ArrayList<Fragment> classify;
    /**
     * TabLayout对应的每个标题
     */
    private String[] tabTitles;

    /**
     * 将TabLayout和ViewPager结合组成导航栏,并为其适配数据
     */
    private void initWorksView() {
        // 创建首页顶部导航栏的适配器
        HomeWorksClassifyAdapter adapter = new HomeWorksClassifyAdapter(this.getFragmentManager()
                , this.classify
                , this.tabTitles);
        // 将适配器设置给ViewPager
        this.works.setAdapter(adapter);
        // 将TabLayout跟ViewPager进行关联
        this.titles.setupWithViewPager(this.works);
    }


}
