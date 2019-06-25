package com.yixinhuayuan.yiyu.mvp.ui.activity.in_trends;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.widget.Toast;

import com.dim.widget.LinearLayout;
import com.jess.arms.base.BaseActivity;
import com.jess.arms.di.component.AppComponent;
import com.jess.arms.utils.ArmsUtils;

import com.yixinhuayuan.yiyu.app.utils.adapter.TrendsAdapter;
import com.yixinhuayuan.yiyu.app.utils.adapter.in_trends.TrendsDetailsAdapter;
import com.yixinhuayuan.yiyu.di.component.DaggerTremdsTedailsComponent;
import com.yixinhuayuan.yiyu.mvp.contract.TremdsTedailsContract;
import com.yixinhuayuan.yiyu.mvp.presenter.TrendsDetailsPresenter;

import com.yixinhuayuan.yiyu.R;


import butterknife.BindView;

import static com.jess.arms.utils.Preconditions.checkNotNull;


/**
 * ================================================
 * Description:
 * <p>
 * Created by MVPArmsTemplate on 06/25/2019 10:43
 * <a href="mailto:jess.yan.effort@gmail.com">Contact me</a>
 * <a href="https://github.com/JessYanCoding">Follow me</a>
 * <a href="https://github.com/JessYanCoding/MVPArms">Star me</a>
 * <a href="https://github.com/JessYanCoding/MVPArms/wiki">See me</a>
 * <a href="https://github.com/JessYanCoding/MVPArmsTemplate">模版请保持更新</a>
 * ================================================
 */
public class TrendsDetailsActivity extends BaseActivity<TrendsDetailsPresenter> implements TremdsTedailsContract.View {

    @Override
    public void setupActivityComponent(@NonNull AppComponent appComponent) {
        DaggerTremdsTedailsComponent //如找不到该类,请编译一下项目
                .builder()
                .appComponent(appComponent)
                .view(this)
                .build()
                .inject(this);
    }

    @Override
    public int initView(@Nullable Bundle savedInstanceState) {
        return R.layout.layout_trends_details; //如果你不需要框架帮你设置 setContentView(id) 需要自行设置,请返回 0
    }

    @Override
    public void initData(@Nullable Bundle savedInstanceState) {
        // 加载动态详情页画面
        intTrendsView();
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
     * 拿到用于展示动态详情的RecycleView
     */
    @BindView(R.id.rv_view_trendsdetails)
    RecyclerView trendsView;

    /**
     * 初始化动态详情界面 采用RecyclerView充当滑动布局
     */
    private void intTrendsView() {
        LinearLayoutManager manager = new LinearLayoutManager(this);
        this.trendsView.setLayoutManager(manager);
        manager.setOrientation(LinearLayout.VERTICAL);
        TrendsDetailsAdapter adapter = new TrendsDetailsAdapter(this);
        this.trendsView.setAdapter(adapter);
        adapter.setOnItemClickListener(new TrendsAdapter.OnItemClickListener() {
            @Override
            public void onClick(int position) {
                Toast.makeText(TrendsDetailsActivity.this, "动态详情第 " + position + " 个条目", Toast.LENGTH_SHORT).show();

                //startActivity(new Intent(TrendsDetailsActivity.this, TrendsAttentionsActivity.class));
            }
        });
        trendsView.setItemAnimator(new DefaultItemAnimator());
    }
}
