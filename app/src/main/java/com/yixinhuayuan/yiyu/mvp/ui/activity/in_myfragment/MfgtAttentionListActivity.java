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
import com.yixinhuayuan.yiyu.di.component.DaggerAttentionListComponent;
import com.yixinhuayuan.yiyu.mvp.contract.AttentionListContract;
import com.yixinhuayuan.yiyu.mvp.presenter.MfgtAttentionListPresenter;

import com.yixinhuayuan.yiyu.R;


import butterknife.BindView;
import butterknife.OnClick;
import okhttp3.FormBody;
import okhttp3.OkHttpClient;
import okhttp3.Request;

import static com.jess.arms.utils.Preconditions.checkNotNull;


/**
 * ================================================
 * Description:
 * <p>
 * Created by MVPArmsTemplate on 04/01/2019 19:37
 * <a href="mailto:jess.yan.effort@gmail.com">Contact me</a>
 * <a href="https://github.com/JessYanCoding">Follow me</a>
 * <a href="https://github.com/JessYanCoding/MVPArms">Star me</a>
 * <a href="https://github.com/JessYanCoding/MVPArms/wiki">See me</a>
 * <a href="https://github.com/JessYanCoding/MVPArmsTemplate">模版请保持更新</a>
 * ================================================
 */
public class MfgtAttentionListActivity extends BaseActivity<MfgtAttentionListPresenter> implements AttentionListContract.View {

    @BindView(R.id.rv_al_attentionlist)
    RecyclerView attentionList;

    @Override
    public void setupActivityComponent(@NonNull AppComponent appComponent) {
        DaggerAttentionListComponent //如找不到该类,请编译一下项目
                .builder()
                .appComponent(appComponent)
                .view(this)
                .build()
                .inject(this);
    }

    @Override
    public int initView(@Nullable Bundle savedInstanceState) {
        return R.layout.activity_attention_list; //如果你不需要框架帮你设置 setContentView(id) 需要自行设置,请返回 0
    }

    @Override
    public void initData(@Nullable Bundle savedInstanceState) {
        initAttentionList();
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

    private void initAttentionList() {

        // 设置布局管理器
        LinearLayoutManager manager = new LinearLayoutManager(this);
        attentionList.setLayoutManager(manager);
        manager.setOrientation(OrientationHelper.VERTICAL);
        // 设置适配器
        PcaAttentionListAdapter adapter = new PcaAttentionListAdapter(this);
        attentionList.setAdapter(adapter);
        //
        attentionList.setItemAnimator(new DefaultItemAnimator());
    }


    @OnClick(R.id.iv_fl_back)
    void back() {
        this.finish();
    }


    public void downloadAttention() {
        new Thread() {
            @Override
            public void run() {
                OkHttpClient client = new OkHttpClient();
                FormBody.Builder downloadBody = new FormBody.Builder();

            }
        }.start();
    }
}
