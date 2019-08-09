package com.example.lixiaona.study.fragment.base;

import android.app.ProgressDialog;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.support.annotation.ColorRes;
import android.support.annotation.DrawableRes;
import android.support.annotation.IdRes;
import android.support.annotation.StringRes;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.content.res.ResourcesCompat;
import android.util.DisplayMetrics;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.FrameLayout;

import com.example.lixiaona.study.R;
import com.example.lixiaona.study.utils.StatusBarUtils;
import com.mingle.widget.LoadingView;

import butterknife.ButterKnife;
import butterknife.Unbinder;


public abstract class BaseFragment extends Fragment implements IBaseFragment {

    private FrameLayout frameLayout;
    private LoadingView baseLoadView;
    //根布局视图
    protected View mContentView;
    //用于butterknife解绑
    private Unbinder unbinder;
    //视图是否已经初始化完毕
    protected boolean isViewReady = false;
    //是否第一次
    protected boolean isFirstLoad = true;
    // fragment是否处于可见状态
    public boolean isFragmentVisible;
    //是否已经加载过
    protected boolean isLoaded = false;

    protected boolean isResume = false;

    protected int mWidth;
    protected int mHeight;

    protected FragmentManager fm;

    //
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        if (mContentView == null) {
            mContentView = inflater.inflate(R.layout.fragment_base, container, false);
            View view = inflater.inflate(getLayoutId(), null);
            frameLayout = (FrameLayout) mContentView.findViewById(R.id.f_base_content);
            baseLoadView = mContentView.findViewById(R.id.base_loadView);
            frameLayout.addView(view);
            unbinder = ButterKnife.bind(this, mContentView);
        }

        fm = getFragmentManager();
        WindowManager manager = getActivity().getWindowManager();
        DisplayMetrics outMetrics = new DisplayMetrics();
        manager.getDefaultDisplay().getMetrics(outMetrics);
        mWidth = outMetrics.widthPixels;
        mHeight = outMetrics.heightPixels;
        return mContentView;
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
//        //视图准备完毕
        isViewReady = true;

//        LogUtil.i("onActivityCreated - isViewReady: "+isViewReady+"\tisFragmentVisible: "+isFragmentVisible+"\tmContentView: "+mContentView);
        //如果视图准备完Fragment处于可见状态，则开始初始化操作
//        if (isViewReady && isFragmentVisible) onFragmentVisiable();
        onFragmentVisiable();
//        initView();
//        initEvent();
    }

    public <T extends View> T findViewById(@IdRes int id) {
        return mContentView.findViewById(id);
    }


    @Override
    public void onHiddenChanged(boolean hidden) {
        super.onHiddenChanged(hidden);
        isFragmentVisible = !hidden;
//        LogUtil.i("onHiddenChanged - isViewReady: "+isViewReady+"\tisFragmentVisible: "+isFragmentVisible+"\tmContentView: "+mContentView);
//        if (isViewReady && isFragmentVisible) onFragmentVisiable();
    }

    //懒加载
    @Override
    public void setUserVisibleHint(boolean isVisibleToUser) {
        super.setUserVisibleHint(isVisibleToUser);
        isFragmentVisible = isVisibleToUser;
//        LogUtil.i("setUserVisibleHint - isViewReady: "+isViewReady+"\tisFragmentVisible: "+isFragmentVisible+"\tmContentView: "+mContentView);
//        如果视图准备完毕且Fragment处于可见状态，则开始初始化操作
//        if (isViewReady && isFragmentVisible) onFragmentVisiable();
    }

    protected void loadingStart() {
        baseLoadView.setVisibility(View.VISIBLE);
        baseLoadView.setVisibility(View.VISIBLE, 80);

//        new ShapeLoadingDialog.Builder(this)
//                .loadText("加载中...")
//                .build();
    }

    protected void loadingStop() {
        baseLoadView.setVisibility(View.GONE);
        baseLoadView.setVisibility(View.GONE, 80);
    }


    //设置并返回布局ID
    protected abstract int getLayoutId();

    //做视图相关的初始化
    protected abstract void initView();

    //来做数据的初始化
    protected abstract void initData();

    //做事件监听的初始化
    protected abstract void setListener();

    private void onFragmentVisiable() {
        if (!isLoaded) {
            isLoaded = true;
            initView();
            initData();
            setListener();
        }
    }


    /**
     * 这个函数用于移除根视图
     * 因为已经有过父布局的View是不能再次添加到另一个新的父布局上面的
     */
    @Override
    public void onDestroyView() {
        if (mContentView != null) {
            ViewGroup parent = (ViewGroup) mContentView.getParent();
            if (parent != null) {
                parent.removeView(mContentView);
            }
        }
        isViewReady = false;
        //ButterKnife解绑
        if (unbinder != null) unbinder.unbind();
        super.onDestroyView();
    }

    public void onResume() {
        super.onResume();
        isResume = true;
    }

    public void onPause() {
        super.onPause();
        isResume = false;
    }


    @Override
    public int getResourceColor(@ColorRes int colorId) {
        return isAdded() ? ResourcesCompat.getColor(getResources(), colorId, null) : 0;
    }

    @Override
    public String getResourceString(@StringRes int stringId) {
        return isAdded() ? getResources().getString(stringId) : null;
    }

    @Override
    public Drawable getResourceDrawable(@DrawableRes int id) {
        return isAdded() ? ResourcesCompat.getDrawable(getResources(), id, null) : null;
    }

    /**
     * 进度对话框
     */
    private ProgressDialog progressDialog;

    protected void showProgress(String message) {
        if (progressDialog == null) {
            progressDialog = new ProgressDialog(getContext());
        }
        progressDialog.setCanceledOnTouchOutside(false);
        progressDialog.setMessage(message);
        progressDialog.setOnKeyListener((dialog, keyCode, event) -> {
            if (progressDialog != null && progressDialog.isShowing()
                    && keyCode == KeyEvent.KEYCODE_BACK) {
                return true;
            }
            return false;
        });

        if (progressDialog.isShowing()) {
            progressDialog.dismiss();
        }
        progressDialog.show();
    }

    public void dismissProgress() {
        if (progressDialog != null && progressDialog.isShowing())
            progressDialog.dismiss();
    }

    /**
     * 将一个布局延长状态栏的高度
     */

    protected void setStatusBar(@IdRes int id) {
        if (mContentView != null) {
            final View linear_bar = mContentView.findViewById(id);
            final int statusHeight = StatusBarUtils.getStatusHeight(getContext());
            linear_bar.post(() -> {
                ViewGroup.LayoutParams params = linear_bar.getLayoutParams();
                params.height = statusHeight;
                linear_bar.setLayoutParams(params);
            });
        }
    }
}
