package com.rmmis.platform;

import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.view.WindowManager;
import android.widget.FrameLayout;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Toast;

import com.rmmis.platform.base.BaseActivity;
import com.rmmis.platform.primaryapplication.frament.DeskFragment;
import com.rmmis.platform.primaryapplication.frament.MessageFragment;
import com.rmmis.platform.primaryapplication.frament.MyFragment;
import com.rmmis.platform.util.ToastUtil;

import butterknife.Bind;
import butterknife.ButterKnife;
import de.greenrobot.event.EventBus;

public class MainActivity extends BaseActivity {

    @Bind(R.id.container)
    FrameLayout container;
    @Bind(R.id.bottom)
    RadioGroup bottom;
    @Bind(R.id.bottom_1)
    RadioButton bottom1;
    @Bind(R.id.bottom_2)
    RadioButton bottom2;
    @Bind(R.id.bottom_3)
    RadioButton bottom3;
    private FragmentManager fragmentManager;
    private FragmentTransaction tx;
    private DeskFragment deskFragment;
    private MessageFragment messageFragment;
    private MyFragment myFragment;
    private long mExitTime;
    @Override
    protected int getContentView() {
        EventBus.getDefault().register(this);
        return R.layout.activity_main;
    }

    @Override
    protected void initData() {
        setSwipeBackEnable(false); //主 activity 可以调用该方法，禁止滑动删除
        ButterKnife.bind(this);
        setLeftBtnVisible(Boolean.FALSE);
        setTitle(getResources().getString(R.string.desk));
        titlebar.setBackgroundColor(mContext.getResources().getColor(R.color.blue));
        initFragment();
        /**
         * 设置默认显示工作台界面
         */
        setContent(0);
        bottom1.setChecked(true);



    }
    /**
     * 初始化工作台，消息，我的界面
     */
    private void initFragment(){
        fragmentManager = getSupportFragmentManager();
        tx = fragmentManager.beginTransaction();
        deskFragment=new DeskFragment();
        messageFragment=new MessageFragment();
        myFragment=new MyFragment();
        tx.add(R.id.container,  deskFragment);
        tx.add(R.id.container, messageFragment);
        tx.add(R.id.container, myFragment);
        tx.commit();
    }
    @Override
    protected void setListener() {
        /**
         * 底部按钮点击事件
         */
        bottom.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                switch (checkedId) {
                    case R.id.bottom_1:
                        setContent(0);
                        setTitle(getResources().getString(R.string.desk));
                        titlebar.setBackgroundColor(mContext.getResources().getColor(R.color.blue));
                        break;
                    case R.id.bottom_2:
                        ToastUtil.showToast(mContext,"消息");
                        setContent(1);
                        setTitle(getResources().getString(R.string.message));
                        titlebar.setBackgroundColor(mContext.getResources().getColor(R.color.blue));
                        break;
                    case R.id.bottom_3:
                        setContent(2);
                        setTitle(getResources().getString(R.string.my));
                        titlebar.setBackgroundResource(R.mipmap.bg1);
                        break;
                    default:
                        break;

                }
            }
        });


    }

    /**
     * 点击底部按钮，对应界面跳转
     * @param index
     */
    private void setContent(int index) {
        tx = fragmentManager.beginTransaction();
        switch (index) {
            case 0:
                tx.show(deskFragment);
                tx.hide(messageFragment);
                tx.hide(myFragment);
                break;
            case 1:
                tx.hide(deskFragment);
                tx.show(messageFragment);
                tx.hide(myFragment);
                break;
            case 2:
                tx.hide(deskFragment);
                tx.hide(messageFragment);
                tx.show(myFragment);
                break;

            default:
                break;
        }
        tx.commit();
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK) {
            if ((System.currentTimeMillis() - mExitTime) > 2000) {
                Toast.makeText(mContext, getResources().getString(R.string.exit_tip), Toast.LENGTH_SHORT).show();
                mExitTime = System.currentTimeMillis();
            } else {
                finish();
            }
            return true;
        }
        return super.onKeyDown(keyCode, event);

    }


    @Override
    protected void onDestroy() {
        super.onDestroy();
        ButterKnife.unbind(this);
        EventBus.getDefault().unregister(this);
    }

    public void onEvent(String message){
        if(message.equals("MessageFragment")){//消息界面-通知传递的广播
            bottom1.setChecked(true);
            setContent(0);
            setTitle(getResources().getString(R.string.desk));
            titlebar.setVisibility(View.VISIBLE);

        }
    }
}
