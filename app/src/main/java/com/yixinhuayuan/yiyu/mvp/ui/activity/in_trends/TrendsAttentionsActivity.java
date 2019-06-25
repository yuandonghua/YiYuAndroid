package com.yixinhuayuan.yiyu.mvp.ui.activity.in_trends;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import com.dim.widget.LinearLayout;
import com.jess.arms.base.BaseActivity;
import com.jess.arms.di.component.AppComponent;
import com.jess.arms.utils.ArmsUtils;

import com.yixinhuayuan.yiyu.app.utils.adapter.in_trends.TrendsAttentionAdapter;
import com.yixinhuayuan.yiyu.di.component.DaggerTrendsAttentionsComponent;
import com.yixinhuayuan.yiyu.mvp.contract.TrendsAttentionsContract;
import com.yixinhuayuan.yiyu.mvp.presenter.TrendsAttentionsPresenter;

import com.yixinhuayuan.yiyu.R;


import butterknife.BindView;

import static com.jess.arms.utils.Preconditions.checkNotNull;


/**
 * ================================================
 * Description:
 * <p>
 * Created by MVPArmsTemplate on 06/25/2019 16:21
 * <a href="mailto:jess.yan.effort@gmail.com">Contact me</a>
 * <a href="https://github.com/JessYanCoding">Follow me</a>
 * <a href="https://github.com/JessYanCoding/MVPArms">Star me</a>
 * <a href="https://github.com/JessYanCoding/MVPArms/wiki">See me</a>
 * <a href="https://github.com/JessYanCoding/MVPArmsTemplate">模版请保持更新</a>
 * ================================================
 */
public class TrendsAttentionsActivity extends BaseActivity<TrendsAttentionsPresenter> implements TrendsAttentionsContract.View {

    @Override
    public void setupActivityComponent(@NonNull AppComponent appComponent) {
        DaggerTrendsAttentionsComponent //如找不到该类,请编译一下项目
                .builder()
                .appComponent(appComponent)
                .view(this)
                .build()
                .inject(this);
    }

    @Override
    public int initView(@Nullable Bundle savedInstanceState) {
        return R.layout.activity_trends_attentions; //如果你不需要框架帮你设置 setContentView(id) 需要自行设置,请返回 0
    }

    @Override
    public void initData(@Nullable Bundle savedInstanceState) {
        // 加载动态点赞人数列表
        initAttentionsView();
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
     * 拿到用来展示动态点赞人数列表的RecyclerView
     */
    @BindView(R.id.rv_view_trendsattention)
    RecyclerView attentionsView;

    /**
     * 初始化点赞人数列表
     * 通过RecyclerView展示动态点赞人数列表,
     */
    private void initAttentionsView() {
        LinearLayoutManager manager = new LinearLayoutManager(this);
        this.attentionsView.setLayoutManager(manager);
        manager.setOrientation(LinearLayout.VERTICAL);
        this.attentionsView.setAdapter(new TrendsAttentionAdapter(this));
        this.attentionsView.setItemAnimator(new DefaultItemAnimator());

    }


}
