package com.rmmis.platform;
import android.content.Intent;

import android.view.KeyEvent;
import android.view.View;
import android.widget.TextView;


import com.rmmis.platform.base.BaseActivity;

import com.rmmis.platform.util.PrefUtil;
import com.rmmis.platform.util.ToastUtil;
import com.rmmis.platform.view.GraphicLockView;
import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;
import de.greenrobot.event.EventBus;

/**
 * Created by Edianzu on 2018/10/17.
 * 手势密码功能
 */

public class GesturePasswordActivity extends BaseActivity {
    @Bind(R.id.lock_view)
    GraphicLockView lockView;
    @Bind(R.id.text)
    TextView text;
    @Bind(R.id.tv_password)
    TextView tvPassword;
    private String type;//判断从哪个界面跳转的
    @Override
    protected int getContentView() {
        return R.layout.activity_gesturepassword;
    }

    @Override
    protected void initData() {
        ButterKnife.bind(this);
        setTitle("手势密码");
        type=getIntent().getStringExtra("type");
        if(type.equals("StartActivity")){
            tvPassword.setVisibility(View.VISIBLE);
        }else{
            tvPassword.setVisibility(View.GONE);
        }
        GraphicLockView.mPassword = PrefUtil.getStringPref(mContext,"PASSWORD");
    }

    @Override
    protected void setListener() {
        lockView.setOnGraphicLockListener(new GraphicLockView.OnGraphicLockListener() {
            @Override
            public void LockSuccess(int what, String password) {

                    if(type.equals("StartActivity")){//手势密码登录App
                        toMainActivity();

                    }else if(type.equals("MainActivity_cancel")) {//取消手势密码，验证
                        PrefUtil.savePref(mContext,"PASSWORD","");
                        EventBus.getDefault().post("cancel");
                        finish();
                    }else{//修改手势密码，验证
                        GesturePasswordActivity.this.finish();
                        toSetGesturePasswordActivity("modify");
                        finish();
                    }
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
    @OnClick(R.id.tv_password)
    public void onViewClicked() {
        Intent intent =new Intent(mContext,LoginActivity.class);
        intent.putExtra("type","close");
        startActivity(intent);
        finish();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        ButterKnife.unbind(this);
    }


    /**
     * 跳转设置手势密码界面
     * @param type
     */
    private void toSetGesturePasswordActivity(String type){
        Intent intent = new Intent(GesturePasswordActivity.this, SetGesturePasswordActivity.class);
        intent.putExtra("type",type);
        startActivity(intent);
    }
    /**
     * 跳转到主页
     */
    private void toMainActivity(){
        Intent intent=new Intent(mContext,MainActivity.class);
        startActivity(intent);
        finish();
    }
}

