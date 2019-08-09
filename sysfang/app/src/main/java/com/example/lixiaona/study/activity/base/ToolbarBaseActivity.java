package com.example.lixiaona.study.activity.base;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Build;
import android.support.annotation.IdRes;
import android.support.v7.widget.AppCompatImageView;
import android.support.v7.widget.AppCompatTextView;
import android.view.KeyEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.RelativeLayout;

import com.example.lixiaona.study.R;
import com.example.lixiaona.study.utils.StatusBarUtils;
import com.mingle.widget.LoadingView;

import butterknife.ButterKnife;
import butterknife.Unbinder;

public abstract class ToolbarBaseActivity extends PermissionsActivity {
    //根布局
    protected View rootView;
    //用于解绑ButterKnife
    private Unbinder bind;
    //title部分
    private RelativeLayout baseTitleRl;
    private AppCompatImageView baseTitleLeftImg;
    private AppCompatTextView baseTitleRightTv;
    private AppCompatTextView baseTitleTv;
    private LoadingView baseLoadView;

    @Override
    public void setContentView(int layoutResID) {
        rootView = getLayoutInflater().inflate(R.layout.activity_base, null);
        super.setContentView(rootView);
        //填充内容布局
        FrameLayout baseContentRl = findViewById(R.id.base_contentRl);
        View view = getLayoutInflater().inflate(layoutResID, null);
        baseContentRl.addView(view);

        bind = ButterKnife.bind(this, this);

        //初始化标题布局
        baseTitleRl = findViewById(R.id.base_titleRl);
        baseTitleTv = findViewById(R.id.base_titleTv);
        baseTitleLeftImg = findViewById(R.id.base_titleLeftImg);
        baseTitleRightTv = findViewById(R.id.base_titleRightTv);
        baseLoadView = findViewById(R.id.base_loadView);

        baseTitleLeftImg.setOnClickListener(v -> finish());
    }

    /**
     * 将一个布局延长状态栏的高度
     */
    protected void setStatusBar(@IdRes int id) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
            final View linear_bar = findViewById(id);
            final int statusHeight = StatusBarUtils.getStatusHeight(getContext());
            linear_bar.post(() -> {
                ViewGroup.LayoutParams params = linear_bar.getLayoutParams();
                params.height = statusHeight;
                linear_bar.setLayoutParams(params);
            });
        }
    }


    protected void goneTitleRl() {
        baseTitleRl.setVisibility(View.GONE);
    }

    protected void setBaseTitle(String title) {
        baseTitleTv.setText(title);
    }

    public RelativeLayout getBaseTitleRl() {
        return baseTitleRl;
    }

    public AppCompatTextView getBaseTitleTv() {
        return baseTitleTv;
    }

    protected void setRightTv(String str, View.OnClickListener onClickListener) {
        baseTitleRightTv.setVisibility(View.VISIBLE);
        baseTitleRightTv.setText(str);
        baseTitleRightTv.setOnClickListener(onClickListener);
    }

    public AppCompatTextView getBaseTitleRightTv() {
        return baseTitleRightTv;
    }

    protected void loadingStart() {
        baseLoadView.setVisibility(View.VISIBLE);
        baseLoadView.setVisibility(View.VISIBLE, 80);
    }

    protected void loadingStop() {
        baseLoadView.setVisibility(View.GONE);
        baseLoadView.setVisibility(View.GONE, 80);
    }

    @Override
    protected void onDestroy() {
        if (bind != null)
            bind.unbind();
        super.onDestroy();
    }

    /**
     * 进度对话框
     */
    private ProgressDialog progressDialog;

    /**
     * 加载框
     *
     * @param message       信息
     * @param cancel        点击框外是否消失  false: 不消失   true: 消失
     * @param interceptBack 是否拦截返回      false: 不拦截   true: 拦截
     */
    protected void showProgress(String message, boolean cancel, boolean interceptBack) {
        if (progressDialog == null) {
            progressDialog = new ProgressDialog(getContext());
        }
        progressDialog.setCanceledOnTouchOutside(cancel);
        progressDialog.setMessage(message);
        progressDialog.setOnKeyListener((dialog, keyCode, event) -> {
            if (progressDialog != null && progressDialog.isShowing()
                    && keyCode == KeyEvent.KEYCODE_BACK && interceptBack) {
                return true;
            }
            return false;
        });

        if (progressDialog.isShowing()) {
            progressDialog.dismiss();
        }
        progressDialog.show();
    }

    protected void dismissProgress() {
        if (progressDialog != null && progressDialog.isShowing())
            progressDialog.dismiss();
    }

    protected void startActivity(Class clazz) {
        Intent intent = new Intent(this, clazz);
        startActivity(intent);
    }

    protected void startActivityForResult(Class clazz, int req) {
        Intent intent = new Intent(this, clazz);
        startActivityForResult(intent, req);
    }
}
