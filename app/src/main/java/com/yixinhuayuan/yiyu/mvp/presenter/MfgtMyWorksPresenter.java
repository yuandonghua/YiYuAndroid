package com.yixinhuayuan.yiyu.mvp.presenter;

import android.app.Application;
import android.util.Log;

import com.jess.arms.integration.AppManager;
import com.jess.arms.di.scope.ActivityScope;
import com.jess.arms.mvp.BasePresenter;
import com.jess.arms.http.imageloader.ImageLoader;

import me.jessyan.rxerrorhandler.core.RxErrorHandler;

import javax.inject.Inject;

import com.yixinhuayuan.yiyu.mvp.contract.MfgtMyWorksContract;


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
@ActivityScope
public class MfgtMyWorksPresenter extends BasePresenter<MfgtMyWorksContract.Model, MfgtMyWorksContract.View> {
    @Inject
    RxErrorHandler mErrorHandler;
    @Inject
    Application mApplication;
    @Inject
    ImageLoader mImageLoader;
    @Inject
    AppManager mAppManager;
    private String change_Url = "http://yy.363626256.top/api/v1/userClassify/$id";
    private String del_Url = "http://yy.363626256.top/api/v1/userClassify/$id";

    @Inject
    public MfgtMyWorksPresenter(MfgtMyWorksContract.Model model, MfgtMyWorksContract.View rootView) {
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
     * @param value
     */
    public void changeUserClassify(int value, String token, String class_name) {
        StringBuffer url = new StringBuffer(change_Url);
        url.append("=" + value);
        new Thread() {
            @Override
            public void run() {
                String jsonStr = mModel.changeClassify(url.toString(), token, class_name);
                Log.d(TAG, "修改作品集名称接口请求完成后返回的数据: " + jsonStr);

            }
        }.start();

    }

    public void delUserClassify(int value, String token) {
        StringBuffer url = new StringBuffer(change_Url);
        url.append("=" + value);
        new Thread() {
            @Override
            public void run() {
                mModel.delClassify(url.toString(), token);
            }
        }.start();

    }
}
