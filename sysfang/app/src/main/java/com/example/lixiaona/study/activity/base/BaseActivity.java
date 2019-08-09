package com.example.lixiaona.study.activity.base;

import android.content.Context;
import android.content.pm.ActivityInfo;
import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.FragmentManager;
import android.support.v4.view.ViewCompat;
import android.util.DisplayMetrics;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;

import com.orhanobut.logger.Logger;

//import com.example.fanhui.study.utils.ActivityStackManager;


public abstract class BaseActivity extends AbstractActivity {
//    页面的堆栈管理
//    private ActivityStackManager mStackManager;

    protected int mWidth;
    protected int mHeight;
    protected boolean isResume;
    protected FragmentManager fm;
    protected boolean isDestroy = false;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);//竖屏
        init();

        setContentLayout();//由具体的activity实现，设置内容布局ID
        initBarColor();
        initView();//由具体的activity实现，做视图相关的初始化
        obtainData();//由具体的activity实现，做数据的初始化
        initEvent();//由具体的activity实现，做事件监听的初始化
    }

    private void init() {
//        mStackManager = ActivityStackManager.getInstance();
//        mStackManager.pushOneActivity(this);

        WindowManager manager = this.getWindowManager();
        DisplayMetrics outMetrics = new DisplayMetrics();
        manager.getDefaultDisplay().getMetrics(outMetrics);
        mWidth = outMetrics.widthPixels;
        mHeight = outMetrics.heightPixels;

        fm = getSupportFragmentManager();
    }

    /*------------------------------------------ 状态栏和虚拟键盘  -------------------------------------*/
    private void initBarColor() {
//        Eyes.setStatusBarColor(this, ContextCompat.getColor(this, R.color.white));
        setDarkStatusIcon(true);
    }

    /**
     * 状态栏图标颜色 是否为黑色
     */
    public void setDarkStatusIcon(boolean bDark) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            View decorView = getWindow().getDecorView();
            if (decorView != null) {
                int vis = decorView.getSystemUiVisibility();
                if (bDark) {
                    vis |= View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR;
                } else {
                    vis &= ~View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR;
                }
                decorView.setSystemUiVisibility(vis);
            }
        }
    }

    /**
     * 状态栏全透明
     */
    protected void translucentStatusBar(boolean hideStatusBarBackground) {
        Window window = getWindow();
        //添加Flag把状态栏设为可绘制模式
        window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
        if (hideStatusBarBackground) {
            //如果为全透明模式，取消设置Window半透明的Flag
            window.clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
            //设置状态栏为透明
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                window.setStatusBarColor(Color.TRANSPARENT);
            }
            //设置window的状态栏不可见
            window.getDecorView().setSystemUiVisibility(View.SYSTEM_UI_FLAG_LAYOUT_STABLE | View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN);
        } else {
            //如果为半透明模式，添加设置Window半透明的Flag
            window.addFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
            //设置系统状态栏处于可见状态
            window.getDecorView().setSystemUiVisibility(View.SYSTEM_UI_FLAG_VISIBLE);
        }
        //view不根据系统窗口来调整自己的布局
        ViewGroup mContentView = (ViewGroup) window.findViewById(Window.ID_ANDROID_CONTENT);
        View mChildView = mContentView.getChildAt(0);
        if (mChildView != null) {
            mChildView.setFitsSystemWindows(false);
            ViewCompat.requestApplyInsets(mChildView);
        }
    }
    /*-------------------------------------------------------------------------------*/

    /**
     * 获取页面的堆栈管理
     */
//    public ActivityStackManager getActivityStackManager() {
//        return mStackManager;
//    }

    @Override
    protected void onDestroy() {
//        mStackManager.popOneActivity(this);
        isDestroy = true;
        super.onDestroy();
    }

    public void onResume() {
        super.onResume();
        isResume = true;
    }

    public void onPause() {
        super.onPause();
        isResume = false;
    }

    public Context getContext() {
        return this;
    }

    @Override
    public void onLowMemory() {
//        LogUtils.e("内存不足");
        Logger.e("内存不足");
        System.gc();
        super.onLowMemory();
    }

}
