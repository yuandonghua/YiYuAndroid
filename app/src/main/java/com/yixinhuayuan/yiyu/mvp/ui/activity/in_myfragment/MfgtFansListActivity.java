package com.yixinhuayuan.yiyu.mvp.ui.activity.in_myfragment;

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

import com.yixinhuayuan.yiyu.app.utils.adapter.PcaAttentionListAdapter;
import com.yixinhuayuan.yiyu.di.component.DaggerFansListComponent;
import com.yixinhuayuan.yiyu.mvp.contract.FansListContract;
import com.yixinhuayuan.yiyu.mvp.presenter.MfgtFansListPresenter;

import com.yixinhuayuan.yiyu.R;


import butterknife.BindView;
import butterknife.OnClick;

import static com.jess.arms.utils.Preconditions.checkNotNull;


/**
 * ================================================
 * Description:
 * <p>
 * Created by MVPArmsTemplate on 04/02/2019 19:21
 * <a href="mailto:jess.yan.effort@gmail.com">Contact me</a>
 * <a href="https://github.com/JessYanCoding">Follow me</a>
 * <a href="https://github.com/JessYanCoding/MVPArms">Star me</a>
 * <a href="https://github.com/JessYanCoding/MVPArms/wiki">See me</a>
 * <a href="https://github.com/JessYanCoding/MVPArmsTemplate">模版请保持更新</a>
 * ================================================
 * 粉丝列表,展示自己被多少人关注的列表
 */
public class MfgtFansListActivity extends BaseActivity<MfgtFansListPresenter> implements FansListContract.View {
    @BindView(R.id.rv_fl_fanslist)
    RecyclerView fansList;

    @Override
    public void setupActivityComponent(@NonNull AppComponent appComponent) {
        DaggerFansListComponent //如找不到该类,请编译一下项目
                .builder()
                .appComponent(appComponent)
                .view(this)
                .build()
                .inject(this);
    }

    @Override
    public int initView(@Nullable Bundle savedInstanceState) {
        return R.layout.activity_fans_list; //如果你不需要框架帮你设置 setContentView(id) 需要自行设置,请返回 0
    }

    @Override
    public void initData(@Nullable Bundle savedInstanceState) {
        setAlAdapter();
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

    private void setAlAdapter() {

        // 设置布局管理器
        LinearLayoutManager manager = new LinearLayoutManager(this);
        fansList.setLayoutManager(manager);
        manager.setOrientation(OrientationHelper.VERTICAL);
        // 设置适配器
        PcaAttentionListAdapter adapter = new PcaAttentionListAdapter(this);
        fansList.setAdapter(adapter);
        //
        fansList.setItemAnimator(new DefaultItemAnimator());
    }

    /**
     * 关闭当前界面
     */
    @OnClick(R.id.iv_fl_back)
    void back() {
        this.finish();
    }
}
