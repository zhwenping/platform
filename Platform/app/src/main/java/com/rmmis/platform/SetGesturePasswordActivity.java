package com.rmmis.platform;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.widget.TextView;

import com.rmmis.platform.base.BaseActivity;
import com.rmmis.platform.util.PrefUtil;
import com.rmmis.platform.util.ToastUtil;
import com.rmmis.platform.view.GraphicLockView;

import butterknife.Bind;
import butterknife.ButterKnife;
import de.greenrobot.event.EventBus;

/**
 * Created by Edianzu on 2018/10/17.
 * 设置手势密码功能
 */

public class SetGesturePasswordActivity extends BaseActivity{
    @Bind(R.id.lock_view)
    GraphicLockView lockView;
    @Bind(R.id.text)
    TextView text;
    private String type;


    @Override
    protected int getContentView() {
        return R.layout.activity_set_gesturepassword;
    }

    @Override
    protected void initData() {
        ButterKnife.bind(this);
        setTitle("设置手势密码");
        type=getIntent().getStringExtra("type");
        GraphicLockView.mPassword = "";
        if(type.equals("add")){//设置手势密码
            text.setText("请绘制您的手势密码");
        }else{//x修改手势密码
            text.setText("请重新绘制您的手势密码");
        }
    }

    @Override
    protected void setListener() {
        lockView.setOnGraphicLockListener(new GraphicLockView.OnGraphicLockListener() {

            @Override
            public void LockSuccess(int what, String password) {
                if (what == 0) {
                    GraphicLockView.mPassword = password;
                    text.setText("请确认您的手势密码");
                } else {
                    PrefUtil.savePref(mContext,"PASSWORD",password);
                    EventBus.getDefault().post(type);
                    finish();
                }
            }

            @Override
            public void LockFailure() {
                text.setText("与上次绘制的手势密码不一致");
            }

            @Override
            public void LockShort() {
                text.setText("最少连接4个点");
            }
        });
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        ButterKnife.unbind(this);
    }
}
