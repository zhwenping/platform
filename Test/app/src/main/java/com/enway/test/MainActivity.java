package com.enway.test;


import android.content.Intent;
import android.os.Bundle;
import android.widget.TextView;

import com.rmmis.platform.SetGesturePasswordActivity;
import com.rmmis.platform.base.BaseActivity;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;


public class MainActivity extends BaseActivity {

    @Bind(R.id.test)
    TextView test;

    @Override
    protected int getContentView() {
        return R.layout.activity_main;
    }

    @Override
    protected void initData() {
        ButterKnife.bind(this);
        setTitle("测试");

    }



    @OnClick(R.id.test)
    public void onViewClicked() {
        Intent intent=new Intent(mContext, SetGesturePasswordActivity.class);
        startActivity(intent);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        ButterKnife.unbind(this);
    }
}
