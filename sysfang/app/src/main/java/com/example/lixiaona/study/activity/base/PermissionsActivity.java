package com.example.lixiaona.study.activity.base;

import android.Manifest;
import android.app.Activity;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Handler;
import android.provider.Settings;
import android.support.annotation.NonNull;
import android.support.v4.content.ContextCompat;

import com.example.lixiaona.study.R;
import com.example.lixiaona.study.dialog.PromptDialog;
import com.example.lixiaona.study.utils.ToastUtils;
import com.tbruyelle.rxpermissions2.Permission;
import com.tbruyelle.rxpermissions2.RxPermissions;

import java.util.ArrayList;
import java.util.List;

import io.reactivex.Observer;
import io.reactivex.disposables.Disposable;


/**
 * 权限类
 */
public abstract class PermissionsActivity extends BaseActivity {
    //联系人
    public final int contact = 1;
    public final String[] contacts = {Manifest.permission.READ_CONTACTS, Manifest.permission.WRITE_CONTACTS};
    // 相机
    public final int camera = 2;
    public final String[] cameras = {Manifest.permission.CAMERA};
    //存储
    public final int external = 3;
    public final String[] externals = {Manifest.permission.READ_EXTERNAL_STORAGE, Manifest.permission.WRITE_EXTERNAL_STORAGE};
    //定位
    public final int location = 4;
    public final String[] locations = {Manifest.permission.ACCESS_COARSE_LOCATION};
    //手机标识
    public final int phoneState = 5;
    public final String[] phoneStates = {Manifest.permission.READ_PHONE_STATE};

    private final int MANAGER_CAMERA = 0xbee1;    //授权管理界面
    private final int MANAGER_CONTACT = 0xbee2;    //授权管理界面通讯录
    private final int MANAGER_EXTERNAL = 0xbee3;    //授权管理界面

    protected final int no = -1;

    /**
     * 请求权限
     */
    public void requestPermissions(final int type, String... permissions) {
        List<String> deniedPermissions = getDeniedPermissions(permissions);
        if (deniedPermissions != null && deniedPermissions.size() > 0) {
            permissions = deniedPermissions.toArray(new String[deniedPermissions.size()]);

            final int[] isRequestSuccess = {0};
            RxPermissions rxPermissions = new RxPermissions(this);
            rxPermissions.requestEach(permissions)
                    .subscribe(new Observer<Permission>() {
                        @Override
                        public void onSubscribe(Disposable d) {
                            // 开始检测
                        }

                        @Override
                        public void onNext(Permission permission) {
                            if (permission.granted) {
                                // 用户已经同意该权限
                                isRequestSuccess[0] = 1;
                            } else if (permission.shouldShowRequestPermissionRationale) {
                                // 用户拒绝了该权限，没有选中『不再询问』（Never ask again）,那么下次再次启动时，还会提示请求权限的对话框
                                ToastUtils.show(R.string.Authorization_failure);
                                isRequestSuccess[0] = -1;
                            } else {
                                // 用户拒绝了该权限，并且选中『不再询问』，提醒用户手动打开权限
                                isRequestSuccess[0] = -2;
                            }
                        }

                        @Override
                        public void onError(Throwable e) {
                            //检测出错
                            isRequestSuccess[0] = 400;
                            ToastUtils.show(R.string.Authorization_error);
                        }

                        @Override
                        public void onComplete() {
                            new Handler().postDelayed(() -> {
                                //检测完毕
                                if (isRequestSuccess[0] == 1) {
                                    authorizationSuccess(type);
                                } else if (isRequestSuccess[0] == -2) {
                                    permissionManager(type);
                                } else if (isRequestSuccess[0] == -1) {
//                                    permissionManager(type);
                                }
                            }, 100);
                        }
                    });
        } else {
            authorizationSuccess(type);
        }
    }

    private PromptDialog confirmDialog;

    /**
     * 显示取权限管理设置的提示
     */
    protected void permissionManager(int type) {
        confirmDialog = new PromptDialog();
        confirmDialog.setTitle("权限申请");

        if (type == camera) {
            confirmDialog.setLeft("取消")
                    .setRight("确定")
                    .setContent("是否前往权限管理界面开启相机权限")
                    .setOnRightClick(() -> {
                        startAppDetailSetting(PermissionsActivity.this, MANAGER_CAMERA);
                        confirmDialog.dismiss();
                    })
                    .show(fm);
        } else if (type == external) {
            confirmDialog.setCanceledOnTouchOutside(false)
                    .setRight("去授权")
                    .setContent("是否前往权限管理界面获取存储空间权限")
                    .setOnRightClick(() -> startAppDetailSetting(PermissionsActivity.this, MANAGER_EXTERNAL))
                    .show(fm);
        }
    }

    /**
     * 已授权
     */
    protected void authorizationSuccess(int type) {
        if (type == external) {
            requestPermissions(phoneState, phoneStates);
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == MANAGER_CONTACT) {
            detection(contact, contacts);
        } else if (requestCode == MANAGER_CAMERA) {
            detection(camera, cameras);
        } else if (requestCode == MANAGER_EXTERNAL) {
            detection(external, externals);
        }
    }

    /**
     * 从管理界面回来时，检测是否授权
     */
    private void detection(final int type, String... permissions) {
        List<String> deniedPermissions = getDeniedPermissions(permissions);
        if (deniedPermissions != null && deniedPermissions.size() > 0) {
            ToastUtils.show("权限授取失败");
        } else {
            if (confirmDialog != null) {
                confirmDialog.dismiss();
            }
            ToastUtils.show("权限授取成功");
            authorizationSuccess(type);
        }
    }


    /**
     * 获取应用详情页面intent
     */
    public static void startAppDetailSetting(Activity context, int request) {
        Intent intent = new Intent(Settings.ACTION_APPLICATION_DETAILS_SETTINGS);
        Uri uri = Uri.fromParts("package", context.getPackageName(), null);
        intent.setData(uri);
        context.startActivityForResult(intent, request);
    }

    /**
     * 传入权限，检查并返回其中未成功获取的权限。
     */
    public List<String> getDeniedPermissions(@NonNull String... permissions) {
        List<String> deniedPermissions = new ArrayList<>();
        for (String permission : permissions) {
            if (ContextCompat.checkSelfPermission(this, permission) != PackageManager.PERMISSION_GRANTED) {
                deniedPermissions.add(permission);
            }
        }
        if (!deniedPermissions.isEmpty()) {
            return deniedPermissions;
        }
        return null;
    }
}
