package com.example.lixiaona.study.utils;

import android.annotation.SuppressLint;
import android.content.Context;
import android.os.Looper;
import android.support.annotation.StringRes;
import android.widget.Toast;

import static android.widget.Toast.makeText;


/**
 * 自定义toast
 */
public class ToastUtils {
    private static Toast mToast;
    private static Context mContext = null;

    public static void init(Context context) {
        mContext = context;
    }

    /**
     * 显示Toast,页面中重复Toast不会重复实例化Toast对象
     * 2000ms
     *
     * @param charSequence 字符串
     */
    public static void show(final CharSequence charSequence) {
        if (Looper.myLooper() != Looper.getMainLooper()) {
            ThreadUtils.executeMainThread(() -> createToast(charSequence, Toast.LENGTH_SHORT));
        } else {
            createToast(charSequence, Toast.LENGTH_SHORT);
        }
    }

    /**
     * 显示Toast,页面中重复Toast不会重复实例化Toast对象
     * 3500ms
     */
    public static void showLong(final CharSequence charSequence) {
        if (Looper.myLooper() != Looper.getMainLooper()) {
            ThreadUtils.executeMainThread(() -> createToast(charSequence, Toast.LENGTH_LONG));
        } else {
            createToast(charSequence, Toast.LENGTH_LONG);
        }
    }

    public static void show(@StringRes final int resId) {
        if (Looper.myLooper() != Looper.getMainLooper()) {
            ThreadUtils.executeMainThread(() -> createToast(resId, Toast.LENGTH_SHORT));
        } else {
            createToast(resId, Toast.LENGTH_SHORT);
        }
    }

    public static void showLong(@StringRes final int resId) {
        if (Looper.myLooper() != Looper.getMainLooper()) {
            ThreadUtils.executeMainThread(() -> createToast(resId, Toast.LENGTH_LONG));
        } else {
            createToast(resId, Toast.LENGTH_LONG);
        }
    }

    /**
     * 取消Toast显示
     */
    public static void cancelToast() {
        if (Looper.myLooper() != Looper.getMainLooper()) {
            ThreadUtils.executeMainThread(() -> {
                // 在这里执行你要想的操作 比如直接在这里更新ui或者调用回调在 在回调中更新ui
                if (mToast != null) {
                    mToast.cancel();
                }
            });

        } else {
            if (mToast != null) {
                mToast.cancel();
            }
        }

    }

    @SuppressLint("ShowToast")
    private static void createToast(CharSequence charSequence, int time) {
        if (mToast == null) {
            mToast = makeText(mContext, "", time);
            mToast.setText(charSequence);
        } else {
            mToast.setText(charSequence);
            mToast.setDuration(time);
        }
//        mToast.getView().setBackgroundColor(mContext.getResources().getColor(R.color.theme_orange));
        mToast.show();
    }

    @SuppressLint("ShowToast")
    private static void createToast(int charSequence, int time) {
        try {
            if (mToast == null) {
                mToast = makeText(mContext, "", time);
                mToast.setText(charSequence);
            } else {
                mToast.setText(charSequence);
                mToast.setDuration(time);
            }
            mToast.show();
//        mToast.getView().setBackgroundColor(mContext.getResources().getColor(R.color.theme_orange));
        } catch (Exception e) {
            createToast(charSequence + "", Toast.LENGTH_SHORT);
        }
    }

}
