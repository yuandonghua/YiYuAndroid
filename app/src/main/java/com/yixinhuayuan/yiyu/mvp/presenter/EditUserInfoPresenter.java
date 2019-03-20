package com.yixinhuayuan.yiyu.mvp.presenter;

import android.app.Application;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Toast;

import com.jess.arms.integration.AppManager;
import com.jess.arms.di.scope.ActivityScope;
import com.jess.arms.mvp.BasePresenter;
import com.jess.arms.http.imageloader.ImageLoader;

import me.jessyan.rxerrorhandler.core.RxErrorHandler;

import javax.inject.Inject;

import com.yixinhuayuan.yiyu.R;
import com.yixinhuayuan.yiyu.mvp.contract.EditUserInfoContract;
import com.yixinhuayuan.yiyu.mvp.ui.activity.EditUserInfoActivity;


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
@ActivityScope
public class EditUserInfoPresenter extends BasePresenter<EditUserInfoContract.Model, EditUserInfoContract.View> {
    @Inject
    RxErrorHandler mErrorHandler;
    @Inject
    Application mApplication;
    @Inject
    ImageLoader mImageLoader;
    @Inject
    AppManager mAppManager;

    @Inject
    public EditUserInfoPresenter(EditUserInfoContract.Model model, EditUserInfoContract.View rootView) {
        super(model, rootView);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        this.mErrorHandler = null;
        this.mAppManager = null;
        this.mImageLoader = null;
        this.mApplication = null;
    }

    /**
     *
     */
    public void setSexRbBtnIcon(RadioGroup group, RadioButton[] rbs) {
        group.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                switch (checkedId) {
                    case R.id.rb_sex_man:
                        Toast.makeText((EditUserInfoActivity) mRootView, "袁超华,是一位先生", Toast.LENGTH_SHORT).show();
                        break;
                    case R.id.rb_sex_woman:
                        Toast.makeText((EditUserInfoActivity) mRootView, "袁超华,是一位女士", Toast.LENGTH_SHORT).show();
                        break;
                }
            }
        });

    }
}
