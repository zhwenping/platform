package com.rmmis.platform.base;

import android.content.Context;
import android.content.pm.ActivityInfo;
import android.os.Bundle;
import android.os.IBinder;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.view.inputmethod.InputMethodManager;

/**
 * Created by enway on 18/1/30.
 */

public abstract class BaseFragment extends Fragment {
    protected Context mContext;
    protected View view;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        getActivity().setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
        mContext=getActivity();
        view=init(inflater);
//        findView();
        initData();
        setListener();
        return view;
    }

    /**
     * 初始化控件
     */
    protected abstract View init(LayoutInflater inflater);




    /**
     * 初始化数据
     */
    protected abstract void initData();
    /**
     * 监听事件
     */
    protected  void  setListener(){

    };


    /**
     * 多种隐藏软件盘方法的其中一种
     *
     * @param token
     */

    protected void hideSoftInput(IBinder token) {
        if (token != null) {
            InputMethodManager im = (InputMethodManager) mContext.getSystemService(Context.INPUT_METHOD_SERVICE);
            im.hideSoftInputFromWindow(token,
                    InputMethodManager.HIDE_NOT_ALWAYS);
        }
    }
    protected void showSoftInput(IBinder token) {
        if (token != null) {
            InputMethodManager im = (InputMethodManager) mContext.getSystemService(Context.INPUT_METHOD_SERVICE);
            im.hideSoftInputFromWindow(token,
                    InputMethodManager.SHOW_FORCED);
        }
    }


}
