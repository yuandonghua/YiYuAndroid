package com.yixinhuayuan.yiyu.mvp.ui.activity.in_myfragment;

import android.annotation.SuppressLint;
import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.OrientationHelper;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Toast;

import com.dim.widget.Button;
import com.dim.widget.EditText;
import com.google.gson.Gson;
import com.jess.arms.base.BaseActivity;
import com.jess.arms.di.component.AppComponent;
import com.jess.arms.utils.ArmsUtils;

import com.yixinhuayuan.yiyu.app.utils.adapter.WorksListAdapter;
import com.yixinhuayuan.yiyu.app.utils.data.MyWorksData;
import com.yixinhuayuan.yiyu.app.utils.jsoninstance.MyWorksCreateJson;
import com.yixinhuayuan.yiyu.app.utils.jsoninstance.MyWorksDownloadJson;
import com.yixinhuayuan.yiyu.di.component.DaggerMfgtMyWorksComponent;
import com.yixinhuayuan.yiyu.mvp.contract.MfgtMyWorksContract;
import com.yixinhuayuan.yiyu.mvp.presenter.MfgtMyWorksPresenter;

import com.yixinhuayuan.yiyu.R;


import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

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
    // 用户数据
    private int user_id;
    private String authorization;
    // 作品集列表的的适配器
    private WorksListAdapter adapter;
    // 请求到的 我的作品集的数据
    private List<MyWorksData> myWorksData;
    Handler mHandler = new Handler() {
        @Override
        public void dispatchMessage(Message msg) {
            // 初始化作品集列表
            if (msg.arg1 == 1) {
                initMyWorksList();
            }
            if (msg.arg2 == 2) {
                adapter.notifyDataSetChanged();
                worksList.invalidate();
                Toast.makeText(MfgtMyWorksActivity.this, "作品集添加成功", Toast.LENGTH_SHORT).show();
            }
            super.dispatchMessage(msg);
        }
    };
    @SuppressLint("WrongConstant")
    private SharedPreferences spUsererInfo;


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
        // 获取部分用户信息
        getUserInfo();
        // 请求数据
        downloadMyWorks();
        // 初始化作品集列表
        //initMyWorksList();
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
    private void initMyWorksList() {
        GridLayoutManager manager = new GridLayoutManager(this, 2);
        worksList.setLayoutManager(manager);
        manager.setOrientation(OrientationHelper.VERTICAL);
        adapter = new WorksListAdapter(this, myWorksData);
        worksList.setAdapter(adapter);
        adapter.setOnItemClickListener(new WorksListAdapter.OnItemClickListener() {
            @Override
            public void onClick(int position) {
                if (position == 0) {
                    // 创建作品集
                    Dialog dialog = new Dialog(MfgtMyWorksActivity.this);
                    View view = LayoutInflater.from(MfgtMyWorksActivity.this).inflate(R.layout.layout_add_works_dialog_my, null);
                    dialog.show();
                    dialog.setContentView(view);
                    EditText text = dialog.findViewById(R.id.et_class_name_my);
                    Button add = dialog.findViewById(R.id.btn_add_works_my);
                    add.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            String name = text.getText().toString().trim();
                            Log.d(TAG, "判断前的 name: " + name);
                            if (!name.equals("") && name != null) {
                                Log.d(TAG, "判断后的 name: " + name);
                                createMyWork(name);
                                dialog.dismiss();
                            }
                        }
                    });
                }
            }
        });
        worksList.setItemAnimator(new DefaultItemAnimator());

    }

    /**
     * 获取作品集
     */
    private void downloadMyWorks() {

        new Thread() {
            @Override
            public void run() {
                StringBuffer url = new StringBuffer("http://yy.363626256.top/api/v1/userClassify");
                url.append("?user_id=" + user_id);
                OkHttpClient downloadWorks = new OkHttpClient();
                Request.Builder download = new Request.Builder();
                download.get()
                        .url(url.toString())
                        .build();
                try {
                    Response worksList = downloadWorks.newCall(download.build()).execute();
                    String json = worksList.body().string();
                    MyWorksDownloadJson myWorksJson = new Gson().fromJson(json, MyWorksDownloadJson.class);
                    List<MyWorksDownloadJson.DataBean> jsonData = myWorksJson.getData();
                    myWorksData = new ArrayList<>();
                    for (int i = 0; i < jsonData.size(); i++) {
                        MyWorksDownloadJson.DataBean dataBean = jsonData.get(i);
                        MyWorksData data = new MyWorksData();
                        data.setClass_name(dataBean.getClass_name());
                        data.setNumber(dataBean.getNumber());
                        data.setImage_url(dataBean.getImage_url());
                        data.setId(dataBean.getId());
                        data.setUser_id(dataBean.getUser_id());
                        /*String name = dataBean.getClass_name();
                        int number = dataBean.getNumber();
                        String image_url = dataBean.getImage_url();
                        int id = dataBean.getId();
                        int user_id = dataBean.getUser_id();*/
                        myWorksData.add(data);

                    }
                    Message message = new Message();
                    message.arg1 = 1;
                    mHandler.sendMessage(message);
                    //Log.d("adapter", "Data2:---> " + myWorksData.get(0).toString());
                } catch (IOException e) {
                    e.printStackTrace();
                }


            }
        }.start();

    }

    /**
     * 创建作品集
     */
    private void createMyWork(String name) {
        Log.d(TAG, "createMyWork: 请求创建作品集接口的方法接收到的 name: " + name);
        new Thread() {
            @Override
            public void run() {
                StringBuffer ccreate_url = new StringBuffer("http://yy.363626256.top/api/v1/userClassify");
                OkHttpClient createMyWork = new OkHttpClient();
                FormBody.Builder createBody = new FormBody.Builder();
                createBody.add("class_name", name);
                createBody.build();
                Request.Builder create = new Request.Builder();
                create.post(createBody.build())
                        .addHeader("authorization", spUsererInfo.getString("authorization", ""))
                        .url(ccreate_url.toString())
                        .build();
                try {
                    Response createData = createMyWork.newCall(create.build()).execute();
                    String json = createData.body().string();
                    MyWorksCreateJson myWorksCreateJson = new Gson().fromJson(json, MyWorksCreateJson.class);
                    MyWorksCreateJson.DataBean jsonData = myWorksCreateJson.getData();
                    Log.d(TAG, "String 2 ----->  " + json);
                    MyWorksData data = new MyWorksData();
                    data.setClass_name(jsonData.getClass_name());
                    data.setNumber(jsonData.getNumber());
                    data.setImage_url(jsonData.getImage_url());
                    data.setId(jsonData.getId());
                    data.setUser_id(jsonData.getUser_id());
                    myWorksData.add(data);
                    // 获取成功没有数据
                } catch (IOException e) {
                    e.printStackTrace();
                }
                // downloadMyWorks();
                Message message = new Message();
                message.arg2 = 2;
                mHandler.sendMessage(message);
            }
        }.start();
    }

    /**
     * 获取一些本地用户信息
     */
    @SuppressLint("WrongConstant")
    private void getUserInfo() {
        spUsererInfo = getSharedPreferences(getBaseContext().getPackageName(), Context.MODE_APPEND);
        this.authorization = spUsererInfo.getString("authorization", "");
        this.user_id = spUsererInfo.getInt("user_id", 0);
    }

}
