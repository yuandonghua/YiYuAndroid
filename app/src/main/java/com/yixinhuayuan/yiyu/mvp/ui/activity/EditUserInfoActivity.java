package com.yixinhuayuan.yiyu.mvp.ui.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.widget.RadioButton;
import android.widget.RadioGroup;

import com.jess.arms.base.BaseActivity;
import com.jess.arms.di.component.AppComponent;
import com.jess.arms.utils.ArmsUtils;

import com.yixinhuayuan.yiyu.di.component.DaggerEditUserInfoComponent;
import com.yixinhuayuan.yiyu.mvp.contract.EditUserInfoContract;
import com.yixinhuayuan.yiyu.mvp.presenter.EditUserInfoPresenter;

import com.yixinhuayuan.yiyu.R;


import butterknife.BindView;
import butterknife.OnClick;

import static com.jess.arms.utils.Preconditions.checkNotNull;


/**
 * ================================================
 * Description:
 * <p>
 * Created by MVPArmsTemplate on 03/18/2019 20:01
 * <a href="mailto:jess.yan.effort@gmail.com">Contact me</a>
 * <a href="https://github.com/JessYanCoding">Follow me</a>
 * <a href="https://github.com/JessYanCoding/MVPArms">Star me</a>
 * <a href="https://github.com/JessYanCoding/MVPArms/wiki">See me</a>
 * <a href="https://github.com/JessYanCoding/MVPArmsTemplate">模版请保持更新</a>
 * ================================================
 */
public class EditUserInfoActivity extends BaseActivity<EditUserInfoPresenter> implements EditUserInfoContract.View {

    /**
     * 拿到编辑性别的RadioGroup对象
     */
    @BindView(R.id.rg_edit_sex)
    RadioGroup editSexGroup;
    /**
     * 拿到性别是男的RadioButton
     */
    @BindView(R.id.rb_sex_man)
    RadioButton man;
    /**
     * 拿到性别是女的RadioButton
     */
    @BindView(R.id.rb_sex_woman)
    RadioButton woman;

    @Override
    public void setupActivityComponent(@NonNull AppComponent appComponent) {
        DaggerEditUserInfoComponent //如找不到该类,请编译一下项目
                .builder()
                .appComponent(appComponent)
                .view(this)
                .build()
                .inject(this);
    }

    @Override
    public int initView(@Nullable Bundle savedInstanceState) {
        return R.layout.activity_edit_user_info; //如果你不需要框架帮你设置 setContentView(id) 需要自行设置,请返回 0
    }

    @Override
    public void initData(@Nullable Bundle savedInstanceState) {

        mPresenter.setSexRbBtnIcon(editSexGroup, new RadioButton[]{man, woman});

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
     * 关闭当前界面
     */
    @OnClick(R.id.iv_edit_back)
    void back(){
        this.finish();
    }
}
