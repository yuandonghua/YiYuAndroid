package com.yixinhuayuan.yiyu.mvp.ui.activity.in_myfragment;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.OrientationHelper;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;

import com.dim.widget.EditText;
import com.dim.widget.LinearLayout;
import com.jess.arms.base.BaseActivity;
import com.jess.arms.di.component.AppComponent;
import com.jess.arms.utils.ArmsUtils;

import com.yixinhuayuan.yiyu.app.utils.adapter.WorksListAdapter;
import com.yixinhuayuan.yiyu.di.component.DaggerMfgtMyWorksComponent;
import com.yixinhuayuan.yiyu.mvp.contract.MfgtMyWorksContract;
import com.yixinhuayuan.yiyu.mvp.presenter.MfgtMyWorksPresenter;

import com.yixinhuayuan.yiyu.R;


import java.io.IOException;

import butterknife.BindView;
import butterknife.OnClick;
import okhttp3.FormBody;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

import static com.jess.arms.utils.Preconditions.checkNotNull;


/**
 * ================================================
 * Description:
 * <p>
 * Created by MVPArmsTemplate on 04/09/2019 19:57
 * <a href="mailto:jess.yan.effort@gmail.com">Contact me</a>
 * <a href="https://github.com/JessYanCoding">Follow me</a>
 * <a href="https://github.com/JessYanCoding/MVPArms">Star me</a>
 * <a href="https://github.com/JessYanCoding/MVPArms/wiki">See me</a>
 * <a href="https://github.com/JessYanCoding/MVPArmsTemplate">模版请保持更新</a>
 * ================================================
 */
public class MfgtMyWorksActivity extends BaseActivity<MfgtMyWorksPresenter> implements MfgtMyWorksContract.View {
    // 拿到用于展示作品集列表的recycleview实例
    @BindView(R.id.rv_workslist_mmwa)
    RecyclerView worksList;

    @Override
    public void setupActivityComponent(@NonNull AppComponent appComponent) {
        DaggerMfgtMyWorksComponent //如找不到该类,请编译一下项目
                .builder()
                .appComponent(appComponent)
                .view(this)
                .build()
                .inject(this);
    }

    @Override
    public int initView(@Nullable Bundle savedInstanceState) {
        return R.layout.activity_mfgt_my_works; //如果你不需要框架帮你设置 setContentView(id) 需要自行设置,请返回 0
    }

    @Override
    public void initData(@Nullable Bundle savedInstanceState) {
        // 初始化作品集列表
        setWorksListAdapter();
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
     * 关闭当前页面
     */
    @OnClick(R.id.iv_mfgtmyworks_back)
    void back() {
        this.finish();
    }

    /**
     *
     */
    private void setWorksListAdapter() {
        httpData();
        GridLayoutManager manager = new GridLayoutManager(this, 2);
        worksList.setLayoutManager(manager);
        manager.setOrientation(OrientationHelper.VERTICAL);
        WorksListAdapter adapter = new WorksListAdapter(this);
        worksList.setAdapter(adapter);
        worksList.setItemAnimator(new DefaultItemAnimator());

    }


    public void httpData() {

        new Thread() {
            @Override
            public void run() {
                @SuppressLint("WrongConstant")
                SharedPreferences spUsererInfo = getSharedPreferences(getBaseContext().getPackageName(), Context.MODE_APPEND);
                String string = spUsererInfo.getString("authorization", "Bearer anEisUMtAbGEbKvlxmNNPliECaph6r7FMAZQpVbv");
                OkHttpClient client = new OkHttpClient();
                // http://yy.363626256.top/api/v1/userClassify?user_id=7
                String user_id = spUsererInfo.getInt("user_id", 7)+"";
                FormBody.Builder body = new FormBody.Builder();
                body.add("user_id",user_id);
                Request builder = new Request.Builder()
                        .url("http://yy.363626256.top/api/v1/userClassify")
                        .post(body.build())
                        //.addHeader("authorization", string)
                        .build();
                try {
                    Response execute = client.newCall(builder).execute();

                    String str = execute.body().string().toString();
                    Log.d("YCHTEST",
                            "code: " + execute.code() +
                                    "||||" + str);

                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }.start();

    }


}
