package com.yixinhuayuan.yiyu.mvp.presenter;

import android.Manifest;
import android.app.Activity;
import android.app.Application;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Build;
import android.provider.MediaStore;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v4.content.FileProvider;
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

import java.io.File;
import java.io.IOException;


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

    // 性别识别 0:未知,1:男,2:女
    private int sex = 0;

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
     * 设置单选框是否选中
     */

    public int setSexRbBtnIcon(RadioGroup group, RadioButton[] rbs) {

        group.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                switch (checkedId) {
                    case R.id.rb_sex_man:
                        //Toast.makeText((EditUserInfoActivity) mRootView, "袁超华,是一位先生", Toast.LENGTH_SHORT).show();
                        sex = 1;
                        break;
                    case R.id.rb_sex_woman:
                        //Toast.makeText((EditUserInfoActivity) mRootView, "袁超华,是一位女士", Toast.LENGTH_SHORT).show();
                        sex = 2;
                        break;
                }
            }
        });
        return sex;
    }

    public static final int TAKE_PHOTO_PERMISSION_REQUEST_CODE = 0; // 拍照的权限处理返回码
    public static final int WRITE_SDCARD_PERMISSION_REQUEST_CODE = 1; // 读储存卡内容的权限处理返回码

    public static final int TAKE_PHOTO_REQUEST_CODE = 3; // 拍照返回的 requestCode
    public static final int CHOICE_FROM_ALBUM_REQUEST_CODE = 4; // 相册选取返回的 requestCode
    public static final int CROP_PHOTO_REQUEST_CODE = 5; // 裁剪图片返回的 requestCode

    public Uri photoUri = null;
    public Uri photoOutputUri = null; // 图片最终的输出文件的 Uri

    /**
     * 先判断用户以前有没有对我们的应用程序允许过读写内存卡内容的权限，
     * 用户处理的结果在 onRequestPermissionResult 中进行处理
     */
    public void isPermission() {
        if (ContextCompat.checkSelfPermission(((Activity) mRootView), Manifest.permission.WRITE_EXTERNAL_STORAGE)
                != PackageManager.PERMISSION_GRANTED) {
            // 申请读写内存卡内容的权限
            ActivityCompat.requestPermissions(((Activity) mRootView),
                    new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE}, WRITE_SDCARD_PERMISSION_REQUEST_CODE);
        }
    }

    /**
     * 拍照
     */
    public void startCamera() {
        /**
         * 设置拍照得到的照片的储存目录，因为我们访问应用的缓存路径并不需要读写内存卡的申请权限，
         * 因此，这里为了方便，将拍照得到的照片存在这个缓存目录中
         */
        File file = new File(((Activity) mRootView).getExternalCacheDir(), "image.jpg");
        try {
            if (file.exists()) {
                file.delete();
            }
            file.createNewFile();
        } catch (IOException e) {
            e.printStackTrace();
        }
        /**
         * 因 Android 7.0 开始，不能使用 file:// 类型的 Uri 访问跨应用文件，否则报异常，
         * 因此我们这里需要使用内容提供器，FileProvider 是 ContentProvider 的一个子类，
         * 我们可以轻松的使用 FileProvider 来在不同程序之间分享数据(相对于 ContentProvider 来说)
         */
        if (Build.VERSION.SDK_INT >= 24) {
            photoUri = FileProvider.getUriForFile(((Activity) mRootView), "com.zhi_dian.provider", file);
        } else {
            photoUri = Uri.fromFile(file); // Android 7.0 以前使用原来的方法来获取文件的 Uri
        }
        // 打开系统相机的 Action，等同于："android.media.action.IMAGE_CAPTURE"
        Intent takePhotoIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        // 设置拍照所得照片的输出目录
        takePhotoIntent.putExtra(MediaStore.EXTRA_OUTPUT, photoUri);
        ((Activity) mRootView).startActivityForResult(takePhotoIntent, TAKE_PHOTO_REQUEST_CODE);
    }

    /**
     * 从相册选取
     */
    public void choiceFromAlbum() {
        // 打开系统图库的 Action，等同于: "android.intent.action.GET_CONTENT"
        Intent choiceFromAlbumIntent = new Intent(Intent.ACTION_GET_CONTENT);
        // 设置数据类型为图片类型
        choiceFromAlbumIntent.setType("image/*");
        ((Activity) mRootView).startActivityForResult(choiceFromAlbumIntent, CHOICE_FROM_ALBUM_REQUEST_CODE);
    }

    /**
     * 裁剪图片
     */
    public void cropPhoto(Uri inputUri) {
        // 调用系统裁剪图片的 Action
        Intent cropPhotoIntent = new Intent("com.android.camera.action.CROP");
        // 设置数据Uri 和类型
        cropPhotoIntent.setDataAndType(inputUri, "image/*");
        // 授权应用读取 Uri，这一步要有，不然裁剪程序会崩溃
        cropPhotoIntent.addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION);
        // 设置图片的最终输出目录
        cropPhotoIntent.putExtra(MediaStore.EXTRA_OUTPUT,
                photoOutputUri = Uri.parse("file:////sdcard/image_output.jpg"));

        ((Activity) mRootView).startActivityForResult(cropPhotoIntent, CROP_PHOTO_REQUEST_CODE);
    }


}
