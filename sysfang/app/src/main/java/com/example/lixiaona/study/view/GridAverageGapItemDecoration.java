package com.example.lixiaona.study.view;

import android.graphics.Rect;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.DisplayMetrics;
import android.util.TypedValue;
import android.view.View;

/**
 * 应用于RecyclerView的GridLayoutManager，水平方向上固定间距大小，从而使条目宽度自适应。<br>
 * Item的宽度应设为MATCH_PARAENT
 *
 * @author : renpeng
 * @org :Aurora Team
 * @since : 2018/9/29
 */
public class GridAverageGapItemDecoration extends RecyclerView.ItemDecoration {

    private float gapHorizontalDp;
    private float gapVerticalDp;
    private float edgePaddingDp;
    private int gapHSizePx = -1;
    private int gapVSizePx = -1;
    private int edgePaddingPx = -1;
    private int eachItemPaddingH = -1; //每个条目应该在水平方向上加的padding 总大小，即=paddingLeft+paddingRight
    private Rect preRect = new Rect();

    /**
     * @param gapHorizontalDp 水平间距
     * @param gapVerticalDp   垂直间距
     * @param edgePaddingDp   两端的padding大小
     */
    public GridAverageGapItemDecoration(float gapHorizontalDp, float gapVerticalDp, float edgePaddingDp) {
        this.gapHorizontalDp = gapHorizontalDp;
        this.gapVerticalDp = gapVerticalDp;
        this.edgePaddingDp = edgePaddingDp;
    }

    @Override
    public void getItemOffsets(Rect outRect, View view, RecyclerView parent, RecyclerView.State state) {
        if (parent.getLayoutManager() instanceof GridLayoutManager) {
            GridLayoutManager layoutManager = (GridLayoutManager) parent.getLayoutManager();
            int spanCount = layoutManager.getSpanCount();
            int count = parent.getAdapter().getItemCount();
            int position = parent.getChildAdapterPosition(view);
            if (gapHSizePx < 0 || gapVSizePx < 0) {
                DisplayMetrics displayMetrics = new DisplayMetrics();
                parent.getDisplay().getMetrics(displayMetrics);
                gapHSizePx = (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, gapHorizontalDp, displayMetrics);
                gapVSizePx = (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, gapVerticalDp, displayMetrics);
                edgePaddingPx = (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, edgePaddingDp, displayMetrics);
                eachItemPaddingH = (edgePaddingPx * 2 + gapHSizePx * (spanCount - 1)) / spanCount;
            }
            outRect.top = gapVSizePx;
            outRect.bottom = 0;
            int visualPos = position + 1;
            if (visualPos % spanCount == 1) {
                //第一列
                outRect.left = edgePaddingPx;
                outRect.right = eachItemPaddingH - edgePaddingPx;
            } else if (visualPos % spanCount == 0) {
                //最后一列
                outRect.left = eachItemPaddingH - edgePaddingPx;
                outRect.right = edgePaddingPx;
            } else {
                outRect.left = gapHSizePx - preRect.right;
                outRect.right = eachItemPaddingH - outRect.left;
            }
            if (visualPos - spanCount <= 0) {
                //第一行
                outRect.top = 0;
            } else if (isLastRow(visualPos, spanCount, count)) {
                //最后一行
            }
            preRect = new Rect(outRect);
//            Log.w("GridAverageGapItem", "pos=" + position + "," + outRect.toShortString());
        } else {
            super.getItemOffsets(outRect, view, parent, state);
        }
    }

    private boolean isLastRow(int visualPos, int spanCount, int sectionItemCount) {
        int lastRowCount = sectionItemCount % spanCount;
        lastRowCount = lastRowCount == 0 ? spanCount : lastRowCount;
        return visualPos > sectionItemCount - lastRowCount;
    }
}
