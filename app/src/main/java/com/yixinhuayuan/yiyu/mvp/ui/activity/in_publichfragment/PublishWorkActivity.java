package com.yixinhuayuan.yiyu.mvp.ui.activity.in_publichfragment;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.OrientationHelper;
import android.support.v7.widget.RecyclerView;

import com.jess.arms.base.BaseActivity;
import com.jess.arms.di.component.AppComponent;
import com.jess.arms.utils.ArmsUtils;

import com.yixinhuayuan.yiyu.app.utils.adapter.PublishWorkAdapter;
import com.yixinhuayuan.yiyu.di.component.DaggerPublishWorkComponent;
import com.yixinhuayuan.yiyu.mvp.contract.PublishWorkContract;
import com.yixinhuayuan.yiyu.mvp.presenter.PublishWorkPresenter;

import com.yixinhuayuan.yiyu.R;


import butterknife.BindView;

import static com.jess.arms.utils.Preconditions.checkNotNull;


/**
 * ================================================
 * Description:
 * <p>
 * Created by MVPArmsTemplate on 05/27/2019 21:34
 * <a href="mailto:jess.yan.effort@gmail.com">Contact me</a>
 * <a href="https://github.com/JessYanCoding">Follow me</a>
 * <a href="https://github.com/JessYanCoding/MVPArms">Star me</a>
 * <a href="https://github.com/JessYanCoding/MVPArms/wiki">See me</a>
 * <a href="https://github.com/JessYanCoding/MVPArmsTemplate">模版请保持更新</a>
 * ================================================
 */
public class PublishWorkActivity extends BaseActivity<PublishWorkPresenter> implements PublishWorkContract.View {
    // 加载PublishWork的Layout布局
    private int[] layout = new int[]{R.layout.layout_publishwork_item1, R.layout.layout_publishwork_item2};

    @Override
    public void setupActivityComponent(@NonNull AppComponent appComponent) {
        DaggerPublishWorkComponent //如找不到该类,请编译一下项目
                .builder()
                .appComponent(appComponent)
                .view(this)
                .build()
                .inject(this);
    }

    @Override
    public int initView(@Nullable Bundle savedInstanceState) {
        return R.layout.activity_publish_work; //如果你不需要框架帮你设置 setContentView(id) 需要自行设置,请返回 0
    }

    @Override
    public void initData(@Nullable Bundle savedInstanceState) {
        showPublishWorkView();
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

    /**
     * 拿到用来展示PublishWork界面的RecyclerView的对象
     */
    @BindView(R.id.rv_showview_publishwork)
    RecyclerView showView;

    /**
     * 给用来展示PublishWork界面的RecyclerView设置适配器
     */
    private void showPublishWorkView() {
        LinearLayoutManager manager = new LinearLayoutManager(this);
        showView.setLayoutManager(manager);
        manager.setOrientation(OrientationHelper.VERTICAL);
        showView.setAdapter(new PublishWorkAdapter(layout,this));
        showView.setItemAnimator(new DefaultItemAnimator());
    }


}
