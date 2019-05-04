package com.yixinhuayuan.yiyu.mvp.ui.activity;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v4.content.FileProvider;
import android.util.Log;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Toast;

import com.jess.arms.base.BaseActivity;
import com.jess.arms.di.component.AppComponent;
import com.jess.arms.utils.ArmsUtils;

import com.tencent.tac.storage.TACStorageReference;
import com.tencent.tac.storage.TACStorageService;
import com.yixinhuayuan.yiyu.app.utils.userinfo.SPUserInfo;
import com.yixinhuayuan.yiyu.di.component.DaggerEditUserInfoComponent;
import com.yixinhuayuan.yiyu.mvp.contract.EditUserInfoContract;
import com.yixinhuayuan.yiyu.mvp.presenter.EditUserInfoPresenter;

import com.yixinhuayuan.yiyu.R;


import java.io.ByteArrayOutputStream;
import java.io.File;
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
 * Created by MVPArmsTemplate on 03/18/2019 20:01
 * <a href="mailto:jess.yan.effort@gmail.com">Contact me</a>
 * <a href="https://github.com/JessYanCoding">Follow me</a>
 * <a href="https://github.com/JessYanCoding/MVPArms">Star me</a>
 * <a href="https://github.com/JessYanCoding/MVPArms/wiki">See me</a>
 * <a href="https://github.com/JessYanCoding/MVPArmsTemplate">模版请保持更新</a>
 * ================================================
 * 用来编辑用户信息的Activity
 * 在此界面可以对,昵称,性别,个性签名进行编辑
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
    /**
     * 头像
     */
    @BindView(R.id.iv_userinfo_header)
    ImageView header;
    private Bitmap bitmapHeader;
    /**
     * 昵称
     */
    @BindView(R.id.et_edit_nickname)
    EditText editNickName;

    // 性别识别 0:未知,1:男,2:女
    private String sex;

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
        int iSex = mPresenter.setSexRbBtnIcon(editSexGroup, new RadioButton[]{man, woman});
        // 设置权限
        this.sex = iSex + "";
        mPresenter.isPermission();
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
    void back() {
        this.finish();
    }


    /**
     * 点击头像编辑用户头像。
     */
    @OnClick(R.id.iv_userinfo_header)
    void editHeader() {
        //调用相册
        mPresenter.choiceFromAlbum();
        // 同上面的权限申请逻辑
        /*if (ContextCompat.checkSelfPermission(this, Manifest.permission.CAMERA)
                != PackageManager.PERMISSION_GRANTED) {
            //下面是对调用相机拍照权限进行申请
            ActivityCompat.requestPermissions(this,
                    new String[]{Manifest.permission.CAMERA,}, EditUserInfoPresenter.TAKE_PHOTO_PERMISSION_REQUEST_CODE);
        } else {
            mPresenter.startCamera();
        }*/


    }

    public byte[] getByteBitmap(Bitmap bitmap) {
        ByteArrayOutputStream outputStream = new ByteArrayOutputStream(bitmap.getByteCount());
        bitmap.compress(Bitmap.CompressFormat.JPEG, 100, outputStream);
        return outputStream.toByteArray();

    }

    /**
     * 在这里进行用户权限授予结果处理
     *
     * @param requestCode  权限要求码，即我们申请权限时传入的常量
     * @param permissions  保存权限名称的 String 数组，可以同时申请一个以上的权限
     * @param grantResults 每一个申请的权限的用户处理结果数组(是否授权)
     */
    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        switch (requestCode) {
            // 调用相机拍照：
            case EditUserInfoPresenter.TAKE_PHOTO_PERMISSION_REQUEST_CODE:
                // 如果用户授予权限，那么打开相机拍照
                if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    mPresenter.startCamera();
                } else {
                    Toast.makeText(this, "拍照权限被拒绝", Toast.LENGTH_SHORT).show();
                }
                break;
            // 打开相册选取：
            case EditUserInfoPresenter.WRITE_SDCARD_PERMISSION_REQUEST_CODE:
                if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                } else {
                    Toast.makeText(this, "读写内存卡内容权限被拒绝", Toast.LENGTH_SHORT).show();
                }
                break;
        }
    }

    /**
     * 通过这个 activity 启动的其他 Activity 返回的结果在这个方法进行处理
     * 我们在这里对拍照、相册选择图片、裁剪图片的返回结果进行处理
     *
     * @param requestCode 返回码，用于确定是哪个 Activity 返回的数据
     * @param resultCode  返回结果，一般如果操作成功返回的是 RESULT_OK
     * @param data        返回对应 activity 返回的数据
     */
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (resultCode == RESULT_OK) {
            // 通过返回码判断是哪个应用返回的数据
            switch (requestCode) {
                // 拍照
                case EditUserInfoPresenter.TAKE_PHOTO_REQUEST_CODE:
                    mPresenter.cropPhoto(mPresenter.photoUri);
                    break;
                // 相册选择
                case EditUserInfoPresenter.CHOICE_FROM_ALBUM_REQUEST_CODE:
                    mPresenter.cropPhoto(data.getData());
                    break;
                // 裁剪图片
                case EditUserInfoPresenter.CROP_PHOTO_REQUEST_CODE:
                    File file = new File(mPresenter.photoOutputUri.getPath());
                    if (file.exists()) {
                        bitmapHeader = BitmapFactory.decodeFile(mPresenter.photoOutputUri.getPath());
                        header.setImageBitmap(bitmapHeader);
                        new Thread() {

                            @Override
                            public void run() {
                                // 腾讯云-移动存储实例
                                TACStorageService storage = TACStorageService.getInstance();
                                TACStorageReference reference = storage.referenceWithPath("header_images/header1.jpg");

                                // 上传文件

                                byte[] byteBitmap = getByteBitmap(bitmapHeader);
                                reference.putData(byteBitmap, null);
                                TACStorageReference root = reference.root();
                                String path2 = root.getPath();
                                TACStorageReference parent = reference.parent();
                                String path1 = parent.getPath();
                                String path = reference.getBucket();
                                Log.d(TAG, "腾讯云移动存储--用户头像: " + path2);

                            }
                        }.start();
//                        file.delete(); // 选取完后删除照片
                    } else {
                        Toast.makeText(this, "找不到照片", Toast.LENGTH_SHORT).show();
                    }
                    break;
            }
        }
    }


    /**
     * 保存用户信息
     */
    @OnClick(R.id.btn_save_userinfo)
    void saveUserInfo() {
        new Thread() {
            @Override
            public void run() {
                SPUserInfo.setContext(EditUserInfoActivity.this);
                OkHttpClient client = new OkHttpClient();
                FormBody.Builder changeBody = new FormBody.Builder();
                changeBody.add("nickname", editNickName.getText().toString())
                        .add("sex", sex)
                        .add("photo", "www.baidu.com")
                        .add("introduce", "这是第一个账号");
                // 请求 修改用户信息 接口
                Request save = new Request.Builder()
                        .url("http://yy.363626256.top/api/v1/user/userInfo")
                        .put(changeBody.build())
                        .addHeader("authorization", SPUserInfo.spUserInfo().getString("authorization", null))
                        .build();
                try {
                    // 获取修改后的用户信息
                    Response saveData = client.newCall(save).execute();
                    Log.d("SAVE", saveData.body().string());
                } catch (IOException e) {
                    e.printStackTrace();
                }

                SPUserInfo.delContext();
            }
        }.start();
    }
}
