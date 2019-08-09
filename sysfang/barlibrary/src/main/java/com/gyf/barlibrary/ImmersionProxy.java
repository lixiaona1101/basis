package com.gyf.barlibrary;

import android.content.res.Configuration;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;

/**
 * Fragment快速实现沉浸式的代理类
 *
 * @author geyifeng
 * @date 2018/11/15 12:53 PM
 */
public class ImmersionProxy {
    /**
     * 要操作的Fragment对象
     */
    private Fragment mFragment;
    /**
     * 沉浸式实现接口
     */
    private ImmersionOwner mImmersionOwner;
    /**
     * Fragment的view是否已经初始化完成
     */
    private boolean mIsActivityCreated;
    /**
     * 懒加载，是否已经在view初始化完成之前调用
     */
    private boolean mIsLazyAfterView;
    /**
     * 懒加载，是否已经在view初始化完成之后调用
     */
    private boolean mIsLazyBeforeView;

    public ImmersionProxy(Fragment fragment) {
        this.mFragment = fragment;
        if (fragment instanceof ImmersionOwner) {
            this.mImmersionOwner = (ImmersionOwner) fragment;
        } else {
            throw new IllegalArgumentException("Fragment请实现ImmersionOwner接口");
        }
    }

    public void setUserVisibleHint(boolean isVisibleToUser) {
        if (mFragment.getUserVisibleHint()) {
            if (!mIsLazyBeforeView) {
                mImmersionOwner.onLazyBeforeView();
                mIsLazyBeforeView = true;
            }
            if (mIsActivityCreated) {
                if (mFragment.getUserVisibleHint()) {
                    if (mImmersionOwner.immersionBarEnabled()) {
                        mImmersionOwner.initImmersionBar();
                    }
                    if (!mIsLazyAfterView) {
                        mImmersionOwner.onLazyAfterView();
                        mIsLazyAfterView = true;
                    }
                    mImmersionOwner.onVisible();
                }
            }
        } else {
            if (mIsActivityCreated) {
                mImmersionOwner.onInvisible();
            }
        }
    }

    public void onCreate(@Nullable Bundle savedInstanceState) {
        if (mFragment.getUserVisibleHint()) {
            if (!mIsLazyBeforeView) {
                mImmersionOwner.onLazyBeforeView();
                mIsLazyBeforeView = true;
            }
        }
    }

    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        mIsActivityCreated = true;
        if (mFragment.getUserVisibleHint()) {
            if (mImmersionOwner.immersionBarEnabled()) {
                mImmersionOwner.initImmersionBar();
            }
            if (!mIsLazyAfterView) {
                mImmersionOwner.onLazyAfterView();
                mIsLazyAfterView = true;
            }
        }
    }

    public void onResume() {
        if (mFragment.getUserVisibleHint()) {
            mImmersionOwner.onVisible();
        }
    }

    public void onPause() {
        mImmersionOwner.onInvisible();
    }

    public void onDestroy() {
        if (mImmersionOwner.immersionBarEnabled() && mFragment != null && mFragment.getActivity() != null) {
            ImmersionBar.with(mFragment).destroy();
        }
        mFragment = null;
        mImmersionOwner = null;
    }

    public void onConfigurationChanged(Configuration newConfig) {
        if (mFragment.getUserVisibleHint()) {
            if (mImmersionOwner.immersionBarEnabled()) {
                mImmersionOwner.initImmersionBar();
            }
            mImmersionOwner.onVisible();
        }
    }

    public void onHiddenChanged(boolean hidden) {
        mFragment.setUserVisibleHint(!hidden);
    }

    /**
     * 是否已经对用户可见
     * Is user visible hint boolean.
     *
     * @return the boolean
     */
    public boolean isUserVisibleHint() {
        return mFragment.getUserVisibleHint();
    }
}
