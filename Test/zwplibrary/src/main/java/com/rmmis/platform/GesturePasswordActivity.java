package com.rmmis.platform;
import android.widget.TextView;
import com.rmmis.platform.base.BaseActivity;
import com.rmmis.platform.util.PrefUtil;
import com.rmmis.platform.util.ToastUtil;
import com.rmmis.platform.view.GraphicLockView;


/**
 * Created by Edianzu on 2018/10/17.
 * 手势密码功能
 */

public class GesturePasswordActivity extends BaseActivity {

    GraphicLockView lockView;

    TextView text;

    TextView tvPassword;


    protected int getContentView() {
        return R.layout.activity_gesturepassword;
    }

    @Override
    protected void initData() {
        lockView = (GraphicLockView) findViewById(R.id.lock_view);
        text = (TextView) findViewById(R.id.text);
        tvPassword = (TextView) findViewById(R.id.tv_password);
        setTitle("手势密码");

        GraphicLockView.mPassword = PrefUtil.getStringPref(mContext, "PASSWORD");
    }

    @Override
    protected void setListener() {
        lockView.setOnGraphicLockListener(new GraphicLockView.OnGraphicLockListener() {
            @Override
            public void LockSuccess(int what, String password) {


            }

            @Override
            public void LockFailure() {

                ToastUtil.showToast(mContext, "您的密码绘制错误");
            }

            @Override
            public void LockShort() {

                ToastUtil.showToast(mContext, "您的密码绘制错误");
            }
        });

    }

}


