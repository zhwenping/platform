package com.rmmis.platform;

import android.os.Bundle;
import android.widget.TextView;

import com.rmmis.platform.base.BaseActivity;
import com.rmmis.platform.util.PrefUtil;
import com.rmmis.platform.view.GraphicLockView;

import butterknife.Bind;
import butterknife.ButterKnife;
import de.greenrobot.event.EventBus;

/**
 * Created by Edianzu on 2018/10/17.
 * 设置手势密码功能
 */

public class SetGesturePasswordActivity extends BaseActivity {

    GraphicLockView lockView;

    TextView text;
    private String type;


    @Override
    protected int getContentView() {
        return R.layout.activity_set_gesturepassword;
    }

    @Override
    protected void initData() {
        lockView=(GraphicLockView)findViewById(R.id.lock_view);
        text=(TextView)findViewById(R.id.text);
        setTitle("设置手势密码");
        type = getIntent().getStringExtra("type");
        GraphicLockView.mPassword = "";

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
                    PrefUtil.savePref(mContext, "PASSWORD", password);

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




}
