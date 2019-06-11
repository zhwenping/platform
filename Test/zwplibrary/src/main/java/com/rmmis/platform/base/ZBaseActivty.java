package com.rmmis.platform.base;

import android.content.pm.ActivityInfo;
import android.graphics.drawable.GradientDrawable;
import android.os.Build;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.rmmis.platform.R;


public abstract class ZBaseActivty extends AppCompatActivity {

    protected Toolbar toolbarImg;
    public ViewGroup contentView;
    protected TextView toolbar_img_title;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
        super.setContentView(R.layout.activity_zbase);
        /**
         * android4.3以上的沉浸式 ，4.3以下没效果，所以不要头部填充状态栏高度
         */
        int sysVersion = Build.VERSION.SDK_INT;
        if (sysVersion > Build.VERSION_CODES.JELLY_BEAN_MR2) {
            int result = 0;
            int resourceId = getResources().getIdentifier("status_bar_height", "dimen", "android");
            if (resourceId > 0) {
                result = getResources().getDimensionPixelSize(resourceId);
            }

            LinearLayout toptop = (LinearLayout) this.findViewById(R.id.toptop);
            RelativeLayout.LayoutParams para = new RelativeLayout.LayoutParams(this.getWindowManager().getDefaultDisplay().getWidth(), result);
            //设置修改后的布局。
            toptop.setLayoutParams(para);
        }
        contentView = (ViewGroup) findViewById(R.id.base_contentview);
        contentView.addView(View.inflate(this, getContentView(), null));
        initView();
        initData();
    }
    /**
     * 获取中间内容显示区
     *
     * @return
     */
    protected abstract int getContentView();
    /**
     * 初始化数据
     */
    protected abstract void initData();
    private void initView() {
        toolbarImg = (Toolbar) findViewById(R.id.toolbar_img);
        toolbarImg.setNavigationIcon(R.mipmap.back_button);
        toolbarImg.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
        toolbar_img_title=(TextView) findViewById(R.id.toolbar_img_title);
    }
    /**
     * 设置左侧按钮显示与隐藏
     *
     * @param visible
     */
    public void setLeftBtnVisible(Boolean visible) {
        if (toolbarImg != null) {
            if (visible) {
                toolbarImg.setVisibility(View.VISIBLE);
            } else {
                toolbarImg.setVisibility(View.GONE);
            }
        }

    }
    /**
     * 设置中间标题
     *
     * @param title
     */
    public void setTitle(String title) {
        if (toolbar_img_title != null) {
            if (toolbar_img_title != null) {
                toolbar_img_title.setText(title);
            }
        }
    }

}