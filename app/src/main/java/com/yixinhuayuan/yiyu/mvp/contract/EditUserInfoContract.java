package com.yixinhuayuan.yiyu.mvp.contract;

import android.app.Activity;
import android.content.Context;

import com.jess.arms.mvp.IView;
import com.jess.arms.mvp.IModel;


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
public interface EditUserInfoContract {
    //对于经常使用的关于UI的方法可以定义到IView中,如显示隐藏进度条,和显示文字消息
    interface View extends IView {

    }

    //Model层定义接口,外部只需关心Model返回的数据,无需关心内部细节,即是否使用缓存
    interface Model extends IModel {

        /**
         * 用于请求修改用户信息的接口
         *
         * @param nick    用户的昵称
         * @param sex     用户的性别
         * @param photo   用户的头像地址
         * @param sign    用户的个性签名
         * @param url     请求修改用户信息的url
         * @param token   用户ID的请求头
         * @param activity
         */
        void requestChangeUserinfo(String nick, String sex, String photo, String sign, String url, String token, Activity activity);

        /**
         * 解析修改用户信息请求返回的数据并保存到本地
         *
         * @param json    请求修改用户信息接口返回的数据
         * @param activity
         */
        void parseAndSaveData(String json, Activity activity);

    }
}
