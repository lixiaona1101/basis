package com.example.lixiaona.study.dialog;

import android.support.v7.widget.AppCompatTextView;
import android.view.Gravity;
import android.view.View;
import android.widget.LinearLayout;

import com.example.lixiaona.study.R;
import com.example.lixiaona.study.dialog.base.BaseDialog;


/**
 * 提示对话框
 */
public class PromptDialog extends BaseDialog {

    private AppCompatTextView sureBtn;
    private AppCompatTextView cancleBtn;

    private String title = "";
    private String message = "";
    private String left = "";
    private String right = "";
    private AppCompatTextView tip;
    private AppCompatTextView body;
    private View line;

    private OnRightClick onRightClick;
    private OnLeftClick onLeftClick;
    private boolean isTouchOutside = true;

    public PromptDialog setCanceledOnTouchOutside(boolean touchOutside) {
        isTouchOutside = touchOutside;
        return this;
    }

    @Override
    protected boolean getCanceledOnTouchOutside() {
        return isTouchOutside;
    }

    public PromptDialog setOnRightClick(OnRightClick onRightClick) {
        this.onRightClick = onRightClick;
        return this;
    }


    public PromptDialog setOnLeftClick(OnLeftClick onLeftClick) {
        this.onLeftClick = onLeftClick;
        return this;
    }

    @Override
    protected int getDialogStyle() {
        return R.style.ScaleDialogStyle;
    }

    public PromptDialog setLeft(String left) {
        this.left = left;
        return this;
    }

    public PromptDialog setRight(String right) {
        this.right = right;
        return this;
    }

    public PromptDialog setTitle(String title) {
        this.title = title;
        return this;
    }

    public PromptDialog setContent(String content) {
        this.message = content;
        return this;
    }

    @Override
    protected int getDialogHeight() {
        return LinearLayout.LayoutParams.WRAP_CONTENT;
    }

    @Override
    protected int getDialogWight() {
        return LinearLayout.LayoutParams.WRAP_CONTENT;
    }

    @Override
    protected int getDialogGravity() {
        return Gravity.CENTER;
    }

    @Override
    public int getLayoutId() {
        return R.layout.dialog_confirm;
    }

    @Override
    public void initData() {
        if (title.equals("")) {
            tip.setVisibility(View.GONE);
        }
        if (message.equals("")) {
            body.setVisibility(View.GONE);
        }
        if (left.equals("")) {
            cancleBtn.setVisibility(View.GONE);
            line.setVisibility(View.GONE);
        }
        if (right.equals("")) {
            sureBtn.setVisibility(View.GONE);
            line.setVisibility(View.GONE);
        }
        tip.setText(title);
        body.setText(message);
        cancleBtn.setText(left);
        sureBtn.setText(right);
    }

    @Override
    public void initEvent() {
        sureBtn.setOnClickListener(v -> {
            if (onRightClick != null) {
                onRightClick.onRight();
            }
        });
        cancleBtn.setOnClickListener(v -> {
            if (onLeftClick != null) {
                onLeftClick.onLeft();
            }
            dismiss();
        });

        setInterceptBack(!isTouchOutside);

    }

    @Override
    public void initView(View paramView) {
        sureBtn = paramView.findViewById(R.id.dialog_confirm_sure);
        cancleBtn = paramView.findViewById(R.id.dialog_confirm_cancle);
        tip = paramView.findViewById(R.id.dialog_title);
        body = paramView.findViewById(R.id.dialog_body);
        line = paramView.findViewById(R.id.line);
    }

    public interface OnRightClick {
        void onRight();
    }

    public interface OnLeftClick {
        void onLeft();

    }

}
