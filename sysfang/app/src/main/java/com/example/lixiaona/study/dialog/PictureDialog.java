package com.example.lixiaona.study.dialog;

import android.support.v7.widget.AppCompatTextView;
import android.view.View;

import com.example.lixiaona.study.R;
import com.example.lixiaona.study.dialog.base.BaseDialog;

/**
 * 相册选择弹框
 */
public class PictureDialog extends BaseDialog {
    private OnPersonalListener onPersonalListener;
    private AppCompatTextView p_top;
    private AppCompatTextView p_bottom;
    private AppCompatTextView p_cancel;

    private String topStr = "";
    private String bottomStr = "";

    public PictureDialog setTop(String str) {
        topStr = str;
        return this;
    }

    public PictureDialog setBottom(String str) {
        bottomStr = str;
        return this;
    }

    public PictureDialog setOnPersonalListener(OnPersonalListener onPersonalListener) {
        this.onPersonalListener = onPersonalListener;
        return this;
    }

    @Override
    public int getLayoutId() {
        return R.layout.dialog_picture;
    }

    @Override
    public void initView(View view) {
        p_top = view.findViewById(R.id.p_top);
        p_bottom = view.findViewById(R.id.p_bottom);
        p_cancel = view.findViewById(R.id.p_cancel);
    }

    @Override
    public void initData() {
        p_top.setText(topStr);
        p_bottom.setText(bottomStr);
    }

    @Override
    public void initEvent() {
        p_top.setOnClickListener(v -> {
            dismiss();
            if (onPersonalListener != null) {
                onPersonalListener.onTop();
            }
        });
        p_bottom.setOnClickListener(v -> {
            dismiss();
            if (onPersonalListener != null) {
                onPersonalListener.onBottom();
            }
        });
        p_cancel.setOnClickListener(v -> dismiss());
    }

    public interface OnPersonalListener {
        void onTop();

        void onBottom();
    }
}
