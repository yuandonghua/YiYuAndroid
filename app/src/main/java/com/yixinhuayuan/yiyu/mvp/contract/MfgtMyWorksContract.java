package com.yixinhuayuan.yiyu.mvp.contract;

import android.os.Handler;

import com.jess.arms.mvp.IView;
import com.jess.arms.mvp.IModel;


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
public interface MfgtMyWorksContract {
    //对于经常使用的关于UI的方法可以定义到IView中,如显示隐藏进度条,和显示文字消息
    interface View extends IView {
        Handler getmHandler();
    }

    //Model层定义接口,外部只需关心Model返回的数据,无需关心内部细节,即是否使用缓存
    interface Model extends IModel {
        /**
         * 此方法用于请求修改作品集接口
         *
         * @param url        请求地址
         * @param token      需要的请求头字段
         * @param class_name 新的作品集名称
         * @return 返回一个jsonString
         */
        String changeClassify(String url, String token, String class_name);

        /**
         * 此方法用于请求删除作品集接口
         *
         * @param url   请求地址
         * @param token 网络请求需要的请求头
         */
        void delClassify(String url, String token);
    }
}
