package com.rmmis.platform.view;

import android.content.Context;
import android.content.res.TypedArray;
import android.support.v7.widget.AppCompatImageView;
import android.util.AttributeSet;
import android.view.View;

import com.rmmis.platform.R;


/**
 * 自定义开关按钮
 */
public class SwitchButton extends AppCompatImageView implements View.OnClickListener {

    private int closeImage;//关闭状态下显示的图片
    private int openImage;//开启状态下显示的图片
    private OnSwitchButtonClick onSwitchButtonClick;

    public SwitchButton(Context context) {
        super(context);
        init();
    }

    public SwitchButton(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public SwitchButton(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);

        TypedArray a = context.obtainStyledAttributes(attrs,
                R.styleable.SwitchButton, defStyleAttr, 0);

        closeImage = a.getResourceId(R.styleable.SwitchButton_closeImage, 0);
        openImage = a.getResourceId(R.styleable.SwitchButton_openImage, 0);

        init();
    }

    /**
     * 初始化
     */
    private void init(){
        changeImageShow(isSelected());

        setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
        setSelected(!isSelected());
        changeImageShow(isSelected());

        if (onSwitchButtonClick != null) {
            onSwitchButtonClick.onClick(isSelected());
        }
    }

    /**
     * 更改图片的显示
     * @param isSelected
     */
    public void changeImageShow(boolean isSelected) {

        if (closeImage == 0 || openImage == 0) {
            return;
        }

        if (isSelected) {
            setImageResource(openImage);
        } else {
            setImageResource(closeImage);
        }
    }

    public interface OnSwitchButtonClick {

        void onClick(boolean selected);
    }

    public void setOnSwitchButtonClick(OnSwitchButtonClick onSwitchButtonClick) {
        this.onSwitchButtonClick = onSwitchButtonClick;
    }

    public void setCloseImage(int closeImage) {
        this.closeImage = closeImage;
    }

    public void setOpenImage(int openImage) {
        this.openImage = openImage;
    }

    @Override
    public void setSelected(boolean selected) {
        super.setSelected(selected);
        changeImageShow(selected);
    }
}