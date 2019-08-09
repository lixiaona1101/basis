package com.example.lixiaona.study.adapter.base;

import android.support.annotation.IdRes;
import android.support.v7.widget.RecyclerView;
import android.util.SparseArray;
import android.view.View;

import com.orhanobut.logger.Logger;


/**
 * description recycler view 的基类view holder
 */
@SuppressWarnings("unchecked")
public class ViewHolder extends RecyclerView.ViewHolder {

    private static final String TAG = ViewHolder.class.getSimpleName();
    //用来存放View以减少findViewById的次数
    private SparseArray<View> mHolder = null;

    public ViewHolder(View itemView) {
        super(itemView);
        mHolder = new SparseArray<>();
    }

    public <T extends View> T getView(@IdRes int id) {

        View childView = mHolder.get(id);
        if (null != childView) {
            return (T) childView;
        }
        childView = itemView.findViewById(id);
        if (null == childView) {
            Logger.e(TAG, "no view that id is " + id);
            return null;
        }
        mHolder.put(id, childView);

        return (T) childView;
    }

}
