package com.yixinhuayuan.yiyu.mvp.ui.activity.in_myfragment;

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

import com.yixinhuayuan.yiyu.app.utils.adapter.in_my.MyTrendsItemAdapter;
import com.yixinhuayuan.yiyu.di.component.DaggerMfgtTrendsComponent;
import com.yixinhuayuan.yiyu.mvp.contract.MfgtTrendsContract;
import com.yixinhuayuan.yiyu.mvp.presenter.MfgtTrendsPresenter;

import com.yixinhuayuan.yiyu.R;
import com.yixinhuayuan.yiyu.mvp.ui.activity.in_trends.TrendsDetailsActivity;


import butterknife.BindView;
import butterknife.OnClick;

import static com.jess.arms.utils.Preconditions.checkNotNull;


/**
 * ================================================
 * Description:
 * <p>
 * Created by MVPArmsTemplate on 04/10/2019 18:42
 * <a href="mailto:jess.yan.effort@gmail.com">Contact me</a>
 * <a href="https://github.com/JessYanCoding">Follow me</a>
 * <a href="https://github.com/JessYanCoding/MVPArms">Star me</a>
 * <a href="https://github.com/JessYanCoding/MVPArms/wiki">See me</a>
 * <a href="https://github.com/JessYanCoding/MVPArmsTemplate">模版请保持更新</a>
 * ================================================
 */
public class MfgtTrendsActivity extends BaseActivity<MfgtTrendsPresenter> implements MfgtTrendsContract.View {

    @Override
    public void setupActivityComponent(@NonNull AppComponent appComponent) {
        DaggerMfgtTrendsComponent //如找不到该类,请编译一下项目
                .builder()
                .appComponent(appComponent)
                .view(this)
                .build()
                .inject(this);
    }

    @Override
    public int initView(@Nullable Bundle savedInstanceState) {
        return R.layout.activity_mfgt_trends; //如果你不需要框架帮你设置 setContentView(id) 需要自行设置,请返回 0
    }

    @Override
    public void initData(@Nullable Bundle savedInstanceState) {
        // 加载我的动态列表
        initMyTrendsList();
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
     * 结束当前界面
     */
    @OnClick(R.id.iv_mfgtmytrends_back)
    void back() {
        this.finish();
    }

    /**
     * 拿到用于展示我的动态列表的RecyclerView
     */
    @BindView(R.id.rv_mytrendlist_my)
    RecyclerView myTrendsList;

    /**
     * 初始化我的动态列表
     */
    private void initMyTrendsList() {
        LinearLayoutManager manager = new LinearLayoutManager(this);
        myTrendsList.setLayoutManager(manager);
        manager.setOrientation(LinearLayout.VERTICAL);
        MyTrendsItemAdapter adapter = new MyTrendsItemAdapter(this);
        myTrendsList.setAdapter(adapter);
        adapter.setOnItemClickListener(new MyTrendsItemAdapter.OnItemClickListener() {
            @Override
            public void onClick(int position) {
                startActivity(new Intent(MfgtTrendsActivity.this, TrendsDetailsActivity.class));
            }
        });
        myTrendsList.setItemAnimator(new DefaultItemAnimator());

    }
}
