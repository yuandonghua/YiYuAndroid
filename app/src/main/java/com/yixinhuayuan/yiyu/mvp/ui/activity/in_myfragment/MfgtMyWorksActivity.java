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
import com.dim.widget.LinearLayout;
import com.dim.widget.TextView;
import com.google.gson.Gson;
import com.jess.arms.base.BaseActivity;
import com.jess.arms.di.component.AppComponent;
import com.jess.arms.utils.ArmsUtils;

import com.sina.weibo.sdk.utils.LogUtil;
import com.yixinhuayuan.yiyu.app.utils.adapter.WorksListAdapter;
import com.yixinhuayuan.yiyu.app.utils.data.MyWorksData;
import com.yixinhuayuan.yiyu.app.utils.jsoninstance.MyWorksCreateJson;
import com.yixinhuayuan.yiyu.app.utils.jsoninstance.MyWorksDownloadJson;
import com.yixinhuayuan.yiyu.di.component.DaggerMfgtMyWorksComponent;
import com.yixinhuayuan.yiyu.mvp.contract.MfgtMyWorksContract;
import com.yixinhuayuan.yiyu.mvp.presenter.MfgtMyWorksPresenter;

import com.yixinhuayuan.yiyu.R;


import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

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
    private List<MyWorksData> myWorksData = null;
    //
    private int num = 0;
    public Handler mHandler = new Handler() {
        @Override
        public void dispatchMessage(Message msg) {
            // 初始化作品集列表
            if (msg.arg1 == 0) {
                Log.d(TAG, "开始初始化作品集列表");
                initMyWorksList();
                MfgtMyWorksActivity.this.num++;
            } else if (msg.arg1 == 1) {
                MfgtMyWorksActivity.this.adapter.notifyDataSetChanged();
                MfgtMyWorksActivity.this.worksList.invalidate();
            }
            if (msg.arg2 == 2) {
                MfgtMyWorksActivity.this.adapter.notifyDataSetChanged();
                MfgtMyWorksActivity.this.worksList.invalidate();
                Toast.makeText(MfgtMyWorksActivity.this, "作品集添加成功", Toast.LENGTH_SHORT).show();
            }
            super.dispatchMessage(msg);
        }
    };

    public Handler getmHandler() {
        return mHandler;
    }

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
        Log.d(TAG, "开始初始化下载作品集列表数据");
        downloadMyWorks();
        // 初始化作品集列表
        // Log.d(TAG, "开始初始化作品集列表");
        // initMyWorksList();
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
        Log.d(TAG, "正在初始化作品集列表");
        GridLayoutManager manager = new GridLayoutManager(this, 2);
        this.worksList.setLayoutManager(manager);
        manager.setOrientation(OrientationHelper.VERTICAL);
        this.adapter = new WorksListAdapter(this, this.myWorksData);
        this.worksList.setAdapter(this.adapter);
        // 给RecyclerView设置点击事件
        this.adapter.setOnItemClickListener(new WorksListAdapter.OnItemClickListener() {
            @Override
            public void onClick(int position) {
                if (position == 0) {
                    // 创建作品集
                    setDialog(0, 0);
                } else if (position > 0) {
                    // 进入作品集详情
                    Toast.makeText(MfgtMyWorksActivity.this, "这是点击事件 position is:" + position + " ,", Toast.LENGTH_SHORT).show();
                }
            }
        });
        // 给RecyclerView设置长按事件
        this.adapter.setOnItemLongClickListener(new WorksListAdapter.OnItemLongClickListener() {
            @Override
            public void onLongClick(int position, int id) {
                // 修改或者删除作品集.
                Toast.makeText(MfgtMyWorksActivity.this, "这是长按事件 position is:" + position + " ,", Toast.LENGTH_SHORT).show();
                Log.d(TAG, "通过长按时间获取到的 id :" + id);
                MfgtMyWorksActivity.this.setDialog(2, id);
                //dialog.setContentView();
            }
        });
        this.worksList.setItemAnimator(new DefaultItemAnimator());

    }

    /**
     * 获取作品集
     */
    private void downloadMyWorks() {

        new Thread() {
            @Override
            public void run() {
                Log.d(TAG, "正在下载作品结列表数据");
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
                    Log.d(TAG, "作品集列表是否为空: " + json);
                    Gson gson = new Gson();
                    boolean equals = gson.equals(null);
                    Log.d(TAG, "Gson是否为空: " + equals);
                    MyWorksDownloadJson myWorksJson = gson.fromJson(json, MyWorksDownloadJson.class);
                    Log.d(TAG, "MyWorksDownloadJson is-->: " + myWorksJson);
                    List<MyWorksDownloadJson.DataBean> jsonData = myWorksJson.getData();
                    Log.d(TAG, "List<MyWorksDownloadJson.DataBean> is-->: " + jsonData);


                    if (jsonData != null) {
                        List<MyWorksData> works = new ArrayList<>();
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
                            works.add(data);
                        }
                        MfgtMyWorksActivity.this.myWorksData = works;
                    } else {
                        MfgtMyWorksActivity.this.myWorksData = myWorksData(json);
                    }
                    Log.d(TAG, "数据下载完毕--->发送消息通知界面加载数据");
                    Message message = new Message();
                    if (MfgtMyWorksActivity.this.num == 0) {
                        message.arg1 = 0;
                    } else if (MfgtMyWorksActivity.this.num > 0) {
                        message.arg1 = 1;
                    }
                    mHandler.sendMessage(message);
                    //Log.d("adapter", "Data2:---> " + myWorksData.get(0).toString());
                } catch (IOException e) {
                    e.printStackTrace();
                } catch (JSONException e) {
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
                Log.d(TAG, "正在创建作品集");
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
                    // MyWorksCreateJson myWorksCreateJson = new Gson().fromJson(json, MyWorksCreateJson.class);
                    // MyWorksCreateJson.DataBean jsonData = myWorksCreateJson.getData();
                    JSONObject jsonObject = new JSONObject(json);
                    JSONObject objectData = jsonObject.getJSONObject("data");
                    Log.d(TAG, "String 2 ----->  " + json);
                    MyWorksData data = new MyWorksData();
                    data.setClass_name(objectData.getString("class_name"));
                    data.setNumber(objectData.getInt("number"));
                    data.setImage_url(objectData.getString("image_url"));
                    data.setId(objectData.getInt("id"));
                    data.setUser_id(objectData.getInt("user_id"));
                    myWorksData.add(data);
                    // 获取成功没有数据
                } catch (IOException e) {
                    e.printStackTrace();
                } catch (JSONException e) {
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

    /**
     * 通过Json解析来解析数据
     *
     * @param json
     * @return
     * @throws JSONException
     */
    private List<MyWorksData> myWorksData(String json) throws JSONException {
        Log.d(TAG, "通过Json解析来解析数据: " + json);
        List<MyWorksData> myWorksData = new ArrayList<>();

        JSONObject jsonObject = new JSONObject(json);
        boolean status = jsonObject.getBoolean("status");
        int code = jsonObject.getInt("code");
        String message = jsonObject.getString("message");
        JSONArray data = jsonObject.getJSONArray("data");
        //
        for (int i = 0; i < data.length(); i++) {
            JSONObject object = (JSONObject) data.get(i);
            int id = object.getInt("id");
            int user_id = object.getInt("user_id");
            String class_name = object.getString("class_name");
            int number = object.getInt("number");
            String image_url = object.getString("image_url");
            //
            MyWorksData work = new MyWorksData();
            work.setId(id);
            work.setUser_id(user_id);
            work.setClass_name(class_name);
            work.setImage_url(image_url);
            work.setNumber(number);
            myWorksData.add(work);
            Log.d(TAG, "作品结名称: " + class_name);
        }
        Log.d(TAG, "Json解析数据完成: " + myWorksData);
        return myWorksData;
    }

    /**
     * 用来输入创建作品集或者修改作品集的信息的对话框
     *
     * @param num 它有两个值-->0:代表创建作品集,1:代表修改作品集,2:选择删除作品集还是添加作品集
     */
    private void setDialog(int num, int value) {
        Log.d(TAG, "创建对话框");
        // 创建作品集
        Dialog dialog = new Dialog(MfgtMyWorksActivity.this);
        View view = LayoutInflater.from(MfgtMyWorksActivity.this).inflate(R.layout.layout_add_works_dialog_my, null);
        dialog.show();
        dialog.setContentView(view);
        if (num == 0 || num == 1) {// 创建或修改作品集是同布局 当num==0或者num==1时会加载他们共同的布局
            TextView title = dialog.findViewById(R.id.tv_addtitle_my);
            EditText text = dialog.findViewById(R.id.et_class_name_my);
            Button add = dialog.findViewById(R.id.btn_add_works_my);
            // 当 num==1是说明是修改作品集 改变创建作品集里面的一些属性
            if (num == 1) {
                title.setText("修改作品集");
                add.setText("确认修改");
            }
            add.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    String name = text.getText().toString().trim();
                    Log.d(TAG, "判断前的 name: " + name);
                    if (!name.equals("") && name != null) {
                        Log.d(TAG, "判断后的 name: " + name);
                        // num==0时 请求创建作品集接口
                        if (num == 0) {
                            Log.d(TAG, "开始创建作品集");
                            createMyWork(name);

                            dialog.dismiss();
                        } else if (num == 1) {// 当num==1时请求修改作品集接口
                            Log.d(TAG, "开始修改作品集" + "再次传入对话框的 id" + value);
                            mPresenter.changeUserClassify(value, MfgtMyWorksActivity.this.authorization, name);
                            dialog.dismiss();
                        }
                    }
                }
            });
        } else if (num == 2) {// 当num==2 隐藏创建或者修改作品集界面
            LinearLayout layout1 = dialog.findViewById(R.id.ll_group_my);
            LinearLayout layout2 = dialog.findViewById(R.id.ll_group2_my);
            layout1.setVisibility(View.GONE);
            layout2.setVisibility(View.VISIBLE);

            Button del = dialog.findViewById(R.id.btn_delworks_my);
            Button change = dialog.findViewById(R.id.btn_changeworks_my);
            //设置点击事件用于 删除作品集
            del.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Log.d(TAG, "删除作品集");
                    mPresenter.delUserClassify(value, MfgtMyWorksActivity.this.authorization);
                    dialog.dismiss();
                }
            });
            // 设置点击事件用于 修改作品集
            change.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Log.d(TAG, "修改作品集" + "传入对话框的 id" + value);
                    dialog.dismiss();
                    MfgtMyWorksActivity.this.setDialog(1, value);

                }
            });
        }
    }
}
