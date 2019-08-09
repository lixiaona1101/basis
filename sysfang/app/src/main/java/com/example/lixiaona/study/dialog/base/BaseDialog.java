package com.example.lixiaona.study.dialog.base;

import android.animation.Animator;
import android.animation.AnimatorSet;
import android.animation.ObjectAnimator;
import android.app.Dialog;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.DialogFragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.view.Gravity;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;

import com.example.lixiaona.study.R;
import com.example.lixiaona.study.utils.StatusBarUtils;
import com.orhanobut.logger.Logger;


public abstract class BaseDialog extends DialogFragment {

    public boolean isShowing = false;
    public boolean isDestroy = false;

    protected View coordinatorLayout;

    protected FragmentManager fm;

    public BaseDialog setFragmentManager(FragmentManager fm) {
        this.fm = fm;
        return this;
    }

    /**
     * 绑定父布局 用于，弹出框时 缩放
     */
    public BaseDialog setCoordinatorLayout(View coordinatorLayout) {
        this.coordinatorLayout = coordinatorLayout;
        return this;
    }

    /**
     * 点击边界是否消失
     *
     * @return true 消失 false 不消失
     */
    protected boolean getCanceledOnTouchOutside() {
        return true;
    }

    /**
     * 弹框的位置
     *
     * @return
     */
    protected int getDialogGravity() {
        return Gravity.BOTTOM;
    }

    /**
     * 弹框的高度
     *
     * @return
     */
    protected int getDialogHeight() {
        return ViewGroup.LayoutParams.WRAP_CONTENT;
    }


    /**
     * 弹框弹出的样式
     *
     * @return
     */
    protected int getDialogStyle() {
        return R.style.NormalDialogStyle;
    }

    /**
     * 弹框的宽度
     *
     * @return
     */
    protected int getDialogWight() {
        return StatusBarUtils.getScreenWidth(getContext());
    }


    public abstract int getLayoutId();

    @Nullable
    public View getView() {
        return super.getView();
    }

    public abstract void initView(View view);

    public abstract void initData();

    public abstract void initEvent();

    @NonNull
    public Dialog onCreateDialog(Bundle paramBundle) {
        getActivity().getWindow().setSoftInputMode(32);
        View localObject = getActivity().getLayoutInflater().inflate(getLayoutId(), null);
        Dialog dialog = new Dialog(getActivity(), getDialogStyle());
        dialog.setContentView(localObject);
        dialog.setCanceledOnTouchOutside(getCanceledOnTouchOutside());
        dialog.setCancelable(getCanceledOnTouchOutside());
        Window window = dialog.getWindow();

        window.setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_ADJUST_PAN);

        WindowManager.LayoutParams localLayoutParams = (window).getAttributes();
        localLayoutParams.gravity = getDialogGravity();
        localLayoutParams.width = getDialogWight();
        localLayoutParams.height = getDialogHeight();
        window.setAttributes(localLayoutParams);
        return dialog;
    }

    @Nullable
    public View onCreateView(LayoutInflater paramLayoutInflater, @Nullable ViewGroup paramViewGroup, @Nullable Bundle paramBundle) {
        getActivity().getWindow().setSoftInputMode(32);
        View inflate = paramLayoutInflater.inflate(getLayoutId(), paramViewGroup);
        initView(inflate);
        initData();
        initEvent();
        return inflate;
    }

    /**
     * 是否拦截返回按钮
     *
     * @param isTouchOutside
     */
    protected void setInterceptBack(final boolean isTouchOutside) {
        if (getDialog() != null)
            getDialog().setOnKeyListener((dialog, keyCode, event) -> {
                if (keyCode == KeyEvent.KEYCODE_BACK) {
                    if (isTouchOutside) {
                        return true;
                    }
                }
                return false;
            });
    }

    @Override
    public void onResume() {
        super.onResume();
    }

    public void show(FragmentManager fm) {
        if (fm == null) {
            return;
        }
        showAnimator(coordinatorLayout);
        Logger.i(getTag() + "_" + this.toString());
        if (getTag() != null) {
            if (!getTag().contains(this.toString()) &&
                    !this.toString().contains(getTag())) {
                FragmentTransaction ft = fm.beginTransaction();
                show(ft, this.toString());
                isShowing = true;
            }
        } else {
            FragmentTransaction ft = fm.beginTransaction();
            show(ft, this.toString());
            isShowing = true;
        }
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        hideAnimator(coordinatorLayout);
        isDestroy = true;
        isShowing = false;
    }


    /**
     * 缩放动画
     */
    private final long duration = 400;

    protected void showAnimator(final View coordinatorLayout) {
        if (coordinatorLayout != null) {
            isHide = false;
            float[] scale = {1.0f, 0.9f};
            ObjectAnimator scaleX = ObjectAnimator.ofFloat(coordinatorLayout, "scaleX", scale).setDuration(duration);
            ObjectAnimator scaleY = ObjectAnimator.ofFloat(coordinatorLayout, "scaleY", scale).setDuration(duration);
            float[] translation = {-StatusBarUtils.getScreenWidth(getContext()) * 0.1f / 2};
            ObjectAnimator translationY = ObjectAnimator.ofFloat(coordinatorLayout, "translationY", translation).setDuration(duration);

            AnimatorSet showAnimatorSet = new AnimatorSet();
            showAnimatorSet.playTogether(scaleX, scaleY, translationY);
            showAnimatorSet.setTarget(coordinatorLayout);
            showAnimatorSet.setStartDelay(150);
            showAnimatorSet.start();
            showAnimatorSet.addListener(new Animator.AnimatorListener() {
                @Override
                public void onAnimationStart(Animator animator) {
                    Logger.i("showAnimator: onAnimationStart");
                }

                @Override
                public void onAnimationEnd(Animator animator) {
                    if (isHide) {
                        float[] scale = {1.0f, 1.0f};
                        ObjectAnimator.ofFloat(coordinatorLayout, "scaleX", scale).setDuration(1).start();
                        ObjectAnimator.ofFloat(coordinatorLayout, "scaleY", scale).setDuration(1).start();
                        float[] translation = {0};
                        ObjectAnimator.ofFloat(coordinatorLayout, "translationY", translation).setDuration(1).start();
                        isHide = false;
                    }
                    Logger.i("showAnimator: onAnimationEnd");
                }

                @Override
                public void onAnimationCancel(Animator animator) {
                    Logger.i("showAnimator: onAnimationCancel");
                }

                @Override
                public void onAnimationRepeat(Animator animator) {
                    Logger.i("showAnimator: onAnimationRepeat");
                }
            });
        }
    }

    private boolean isHide = false;

    protected void hideAnimator(View coordinatorLayout) {
        if (coordinatorLayout != null) {
            isHide = false;
            float[] scale = {0.9f, 1.0f};
            ObjectAnimator scaleX = ObjectAnimator.ofFloat(coordinatorLayout, "scaleX", scale).setDuration(duration);
            ObjectAnimator scaleY = ObjectAnimator.ofFloat(coordinatorLayout, "scaleY", scale).setDuration(duration);
            float[] translation = {0};
            ObjectAnimator translationY = ObjectAnimator.ofFloat(coordinatorLayout, "translationY", translation).setDuration(duration);

            final AnimatorSet animatorSet = new AnimatorSet();
            animatorSet.playTogether(scaleX, scaleY, translationY);
            animatorSet.setTarget(coordinatorLayout);
            animatorSet.start();
            animatorSet.addListener(new Animator.AnimatorListener() {
                @Override
                public void onAnimationStart(Animator animator) {
                    Logger.i("hideAnimator: onAnimationStart");
                }

                @Override
                public void onAnimationEnd(Animator animator) {
                    isHide = true;
                    Logger.i("hideAnimator: onAnimationEnd");
                }

                @Override
                public void onAnimationCancel(Animator animator) {
                    Logger.i("hideAnimator: onAnimationCancel");
                }

                @Override
                public void onAnimationRepeat(Animator animator) {
                    Logger.i("hideAnimator: onAnimationRepeat");
                }
            });
        }
    }
}
