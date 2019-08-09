package com.example.lixiaona.study.adapter.base;

import android.content.Context;
import android.support.annotation.IntRange;
import android.support.annotation.NonNull;
import android.support.v4.util.SparseArrayCompat;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.ViewGroup;

import com.example.lixiaona.study.utils.CollectionUtils;
import com.orhanobut.logger.Logger;

import java.util.List;

/**
 * recyclerview 的适配基类
 * @param <T>
 */
public abstract class HeaderAndFooterWrapper<T> extends RecyclerView.Adapter<ViewHolder> implements IRecyclerAdapter<T>  {
    private static final int BASE_ITEM_TYPE_HEADER = 100000;
    private static final int BASE_ITEM_TYPE_FOOTER = 200000;

    private SparseArrayCompat<View> mHeaderViews = new SparseArrayCompat<>();
    private SparseArrayCompat<View> mFootViews = new SparseArrayCompat<>();


    private List<T> mDataList;
    private Context mContext;

    public HeaderAndFooterWrapper(@NonNull Context context, @NonNull List<T> mDataList) {
        if (context == null) {
            throw new NullPointerException("context is not allow null!");
        }
        this.mDataList = mDataList;
        this.mContext = context;
    }


    public Context getContext() {
        return mContext;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        if (mHeaderViews.get(viewType) != null) {
            return new ViewHolder(mHeaderViews.get(viewType));
        } else if (mFootViews.get(viewType) != null) {
            return new ViewHolder(mFootViews.get(viewType));
        }
        return bindCreateViewHolder(parent, viewType);
    }

    protected abstract ViewHolder bindCreateViewHolder(ViewGroup parent, int viewType);

    @Override
    public int getItemViewType(int position) {
        if (isHeaderViewPos(position)) {
            return mHeaderViews.keyAt(position);
        } else if (isFooterViewPos(position)) {
            return mFootViews.keyAt(position - getHeadersCount() - getRealItemCount());
        }
        return position - getHeadersCount();
    }


    public int getRealItemCount() {
        return mDataList.size();
    }


    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        if (isHeaderViewPos(position)) {
            return;
        }
        if (isFooterViewPos(position)) {
            return;
        }
        final int itemViewType = getItemViewType(position);
        bindDataForView(holder, (mDataList != null && !mDataList.isEmpty()) ? (mDataList.get(itemViewType)) : null, itemViewType);
    }

    protected abstract void bindDataForView(ViewHolder holder, T t, int position);

    @Override
    public int getItemCount() {
        return getHeadersCount() + getFootersCount() + getRealItemCount();
    }

    private boolean isHeaderViewPos(int position) {
        return position < getHeadersCount();
    }

    private boolean isFooterViewPos(int position) {
        return position >= getHeadersCount() + getRealItemCount();
    }


    public void addHeaderView(View view) {
        mHeaderViews.put(mHeaderViews.size() + BASE_ITEM_TYPE_HEADER, view);
    }

    public void addFootView(View view) {
        mFootViews.put(mFootViews.size() + BASE_ITEM_TYPE_FOOTER, view);
    }

    public int getHeadersCount() {
        return mHeaderViews.size();
    }

    public int getFootersCount() {
        return mFootViews.size();
    }



    @Override
    public List<T> getDataList() {
        return mDataList;
    }

    //从某个位置开始，插入一组数据
    @Override
    public void insertItems(@NonNull List<T> list, @IntRange(from = 0) int startIndex) {

        if (mDataList == null) {
            return;
        }

        if (list == null || list.isEmpty()) {
            Logger.e( "插入的数据集为空或长度小于等于零, 请检查你的数据集!");
            return;
        }

        if (this.mDataList.containsAll(list)) {
            return;
        }

        notifyItemRangeInserted(startIndex+getHeadersCount(), list.size());
        mDataList.addAll(startIndex, list);
        notifyItemRangeChanged(startIndex+getHeadersCount(), getItemCount() - startIndex+getHeadersCount());
    }

    //从最底下插入一组数据
    @Override
    public void insertItems(@NonNull List<T> list) {
        this.insertItems(list, mDataList.size());
    }

    //从某个位置开始，插入一个数据
    @Override
    public void insertItem(@NonNull T t, @IntRange(from = 0) int position) {

        if (mDataList == null) {
            return;
        }

        if (t == null) {
            Logger.e( "插入的数据为空, 请检查你的数据!");
            return;
        }

//        if (this.mDataList.contains(t)) {
//            return;
//        }

        notifyItemInserted(position+getHeadersCount());
        mDataList.add(position, t);
        notifyItemRangeChanged(position+getHeadersCount(), getItemCount() - position+getHeadersCount());
    }

    //从最底下插入一个数据
    @Override
    public void insertItem(@NonNull T t) {
        this.insertItem(t, mDataList.size());
    }

    //替换所有数据
    @Override
    public void replaceData(@NonNull List<T> list) {
        if (mDataList == null) {
            return;
        }

//        if (list == null || list.isEmpty()) {
//            LogUtil.e(TAG, "插入的数据集为空或长度小于等于零, 请检查你的数据集!");
//            return;
//        }

        mDataList = list;
        notifyDataSetChanged();
    }

    //从某个位置开始，更新n个数据
    @Override
    public void updateItems(@IntRange(from = 0) int positionStart, @IntRange(from = 0) int itemCount) {
        notifyItemRangeChanged(positionStart, itemCount);
    }

    //更新所有数据
    @Override
    public void updateAll() {
        updateItems(0, mDataList.size());
    }

    //移除某个位置的数据
    @Override
    public void removeItem(@IntRange(from = 0) int position) {
        if (CollectionUtils.isEmpty(mDataList) || position <= -1) {
            return;
        }
        notifyItemRemoved(position);
        mDataList.remove(position);
        notifyItemRangeChanged(position, getItemCount() - position);
    }

    //移除所有数据
    @Override
    public void removeAll() {
        if (CollectionUtils.isEmpty(mDataList)) {
            return;
        }

        notifyItemRangeRemoved(0, getItemCount());
        mDataList.clear();
        notifyItemRangeChanged(0, getItemCount());
    }

    @Override
    public T getItem(@IntRange(from = 0) int position) {
        if (position <= -1) {
            return null;
        }
        return !CollectionUtils.isEmpty(mDataList) ? mDataList.get(position) : null;
    }
}
