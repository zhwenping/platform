package com.rmmis.platform.base;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Context;
import android.content.pm.ActivityInfo;
import android.content.pm.PackageManager;
import android.content.res.Configuration;
import android.content.res.Resources;
import android.hardware.input.InputManager;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.os.IBinder;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.FragmentActivity;
import android.support.v4.content.ContextCompat;
import android.util.TypedValue;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.rmmis.platform.PermissionListener;
import com.rmmis.platform.R;

import java.util.ArrayList;
import java.util.List;

import me.imid.swipebacklayout.lib.SwipeBackLayout;
import me.imid.swipebacklayout.lib.app.SwipeBackActivity;


/**
 * Created by enway on 17/10/31.
 */

public abstract class BaseActivity extends SwipeBackActivity {

    public ViewGroup contentView;
    private TextView rightBtn;
    private View leftBtn;
    private TextView titltTv;
    protected View titlebar;
    protected Context mContext;
    protected Handler handler=new Handler();
    private ProgressBar progressBar;

    private PermissionListener mListener;
    private static final int PERMISSION_REQUESTCODE = 100;

    private SwipeBackLayout mSwipeBackLayout;

    @SuppressLint("InlinedApi")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
//        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
        mSwipeBackLayout = getSwipeBackLayout();
        //设置滑动方向，可设置EDGE_LEFT, EDGE_RIGHT, EDGE_ALL, EDGE_BOTTOM
        mSwipeBackLayout.setEdgeTrackingEnabled(SwipeBackLayout.EDGE_LEFT);
        setContentView(R.layout.activity_base);
        setTranslucentStatus();
        mContext=BaseActivity.this;
        int titlebarResId = getTitlebarResId();

        if (titlebarResId != 0) {

            LinearLayout view = (LinearLayout)findViewById(R.id.base_view);
            view.removeViewAt(0);
            LinearLayout.LayoutParams lp = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, 50, getResources().getDisplayMetrics()));
            ViewGroup titleView = (ViewGroup) View.inflate(this, titlebarResId, null);
            view.addView(titleView, 0, lp);
            view.setBackgroundDrawable(titleView.getBackground());

            titlebar = titleView;
        } else {
            titlebar = findViewById(R.id.base_titlebar);
            leftBtn = findViewById(R.id.base_back_btn);
            leftBtn.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    onClickLeft();
                }
            });

            rightBtn = (TextView) findViewById(R.id.base_menu_btn);
            rightBtn.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    onClickRight();
                }
            });

            titltTv = (TextView) findViewById(R.id.base_title_tv);
        }

        contentView = (ViewGroup) findViewById(R.id.base_contentview);
        contentView.addView(View.inflate(this, getContentView(), null));
        setRightBtnVisible(false);
        initData();
        setListener();

    }



    /**
     * 设置状态栏背景状态
     */
    private void setTranslucentStatus() {
        //判断当前SDK版本号，如果是4.4以上，就是支持沉浸式状态栏的

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
            getWindow().addFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
//            getWindow().addFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_NAVIGATION);

        }

    }

    /**
     * 点击左侧按钮
     * 默认是退出
     */
    protected void onClickLeft() {
        finish();
    }

    /**
     * 点击右侧按钮
     * 默认什么都不做
     */
    protected void onClickRight() {

    }

    /**
     * 设置左侧按钮显示与隐藏
     *
     * @param visible
     */
    public void setLeftBtnVisible(Boolean visible) {
        if (leftBtn != null) {
            if (visible) {
                leftBtn.setVisibility(View.VISIBLE);
            } else {
                leftBtn.setVisibility(View.GONE);
            }
        }

    }

    /**
     * 设置右侧按钮显示与隐藏
     *
     * @param visible
     */
    public void setRightBtnVisible(Boolean visible) {
        if (rightBtn != null) {
            if (visible) {
                rightBtn.setVisibility(View.VISIBLE);
            } else {
                rightBtn.setVisibility(View.GONE);
            }
        }

    }

    /**
     * 获取自定义标题栏
     * 如果子类复写并返回不等于0的布局文件，将会覆盖默认标题
     * 返回0 将会采用默认标题
     *
     * @return
     */
    protected int getTitlebarResId() {
        return 0;
    }

    /**
     * 设置中间标题
     *
     * @param title
     */
    public void setTitle(String title) {
        if (titltTv != null) {
            if (titltTv != null) {
                titltTv.setText(title);
            }
        }
    }


    /**
     * 设置右边你按钮文字属性
     *
     * @param title
     */
    public void setRtTitle(String title) {
        if (rightBtn != null) {
            rightBtn.setText(title);
        }
    }

    public View getTitleBar() {

        return titlebar;
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

    /**
     * 设置监听事件
     */
    protected  void setListener(){

    };

    /*
    * 设置字体不随系统的设置而改变
    *
    * @param newConfig
    */
    @Override
    public void onConfigurationChanged(Configuration newConfig) {
        if (newConfig.fontScale != 1)//非默认值
            getResources();
        super.onConfigurationChanged(newConfig);

    }

    @Override
    public Resources getResources() {

        Resources res = super.getResources();
        if (res.getConfiguration().fontScale != 1) {//非默认值
            Configuration newConfig = new Configuration();
            newConfig.setToDefaults();//设置默认
            res.updateConfiguration(newConfig, res.getDisplayMetrics());
        }
        return res;
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        finish();

    }

    @Override
    public boolean dispatchTouchEvent(MotionEvent ev) {
        if (ev.getAction() == MotionEvent.ACTION_DOWN) {

            // 获得当前得到焦点的View，一般情况下就是EditText（特殊情况就是轨迹求或者实体案件会移动焦点）
            View v = getCurrentFocus();

            if (isShouldHideInput(v, ev)) {
                hideSoftInput(v.getWindowToken());
            }
        }
        return super.dispatchTouchEvent(ev);
    }

    /**
     * 根据EditText所在坐标和用户点击的坐标相对比，来判断是否隐藏键盘，因为当用户点击EditText时没必要隐藏
     *
     * @param v
     * @param event
     * @return
     */
    private boolean isShouldHideInput(View v, MotionEvent event) {
        if (v != null && (v instanceof EditText)) {
            int[] l = { 0, 0 };
            v.getLocationInWindow(l);
            int left = l[0], top = l[1], bottom = top + v.getHeight(), right = left
                    + v.getWidth();
            if (event.getX() > left && event.getX() < right
                    && event.getY() > top && event.getY() < bottom) {
                // 点击EditText的事件，忽略它。
                return false;
            } else {
                return true;
            }
        }
        // 如果焦点不是EditText则忽略，这个发生在视图刚绘制完，第一个焦点不在EditView上，和用户用轨迹球选择其他的焦点
        return false;
    }

    /**
     * 多种隐藏软件盘方法的其中一种
     *
     * @param token
     */
    private void hideSoftInput(IBinder token) {
        if (token != null) {
            InputMethodManager im = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
            im.hideSoftInputFromWindow(token,
                    InputMethodManager.HIDE_NOT_ALWAYS);
        }
    }
    /**
     * 设置Progressbar的显示和隐藏
     * true:显示,false:隐藏
     *
     * @param visible
     */
    public void setProgressBarVisible(Boolean visible) {
        if (progressBar != null) {
            if (visible) {
                progressBar.setVisibility(View.VISIBLE);
            } else {
                progressBar.setVisibility(View.GONE);
            }
        }

    }

    /**
     * 权限封装
     * @param permissions
     * @param listener
     */
    public void requestRunPermisssion(String[] permissions, PermissionListener listener){
        mListener = listener;

        List<String> permissionLists = new ArrayList<>();
        for(String permission : permissions){
            if(ContextCompat.checkSelfPermission(this, permission) != PackageManager.PERMISSION_GRANTED){
                permissionLists.add(permission);
            }
        }

        if(!permissionLists.isEmpty()){

            ActivityCompat.requestPermissions(this, permissionLists.toArray(new String[permissionLists.size()]), PERMISSION_REQUESTCODE);
        }else{
            //表示全都授权了
            mListener.onGranted();
        }
    }


    @Override
    public void onRequestPermissionsResult(int requestCode,  String[] permissions,  int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        switch (requestCode){
            case PERMISSION_REQUESTCODE:
                if(grantResults.length > 0){
                    //存放没授权的权限
                    List<String> deniedPermissions = new ArrayList<>();
                    for(int i = 0; i < grantResults.length; i++){
                        int grantResult = grantResults[i];
                        String permission = permissions[i];
                        if(grantResult != PackageManager.PERMISSION_GRANTED){
                            deniedPermissions.add(permission);
                        }
                    }
                    if(deniedPermissions.isEmpty()){
                        //说明都授权了
                        mListener.onGranted();
                    }else{
                        mListener.onDenied(deniedPermissions);
                    }
                }
                break;
            default:
                break;
        }
    }



}

