package com.rmmis.platform;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.TextView;

import com.google.gson.Gson;
import com.lzy.okgo.model.Response;
import com.rmmis.platform.base.BaseActivity;
import com.rmmis.platform.model.LoginModel;
import com.rmmis.platform.net.Constant;
import com.rmmis.platform.net.DialogCallback;
import com.rmmis.platform.net.OkGoUtil;
import com.rmmis.platform.primaryapplication.activity.ModifyPasswordActivity;
import com.rmmis.platform.util.LogUtil;
import com.rmmis.platform.util.MobileInfoUtil;
import com.rmmis.platform.util.PrefUtil;
import com.rmmis.platform.util.ToastUtil;
import com.rmmis.platform.view.ClearEditText;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;
import cn.jpush.android.api.JPushInterface;

/**
 * Created by Edianzu on 2018/10/15.
 */

public class LoginActivity extends BaseActivity {
    @Bind(R.id.et_username)
    ClearEditText etUsername;
    @Bind(R.id.et_password)
    ClearEditText etPassword;
    @Bind(R.id.tv_login)
    TextView tvLogin;
    public static int sequence = 1;
    @Bind(R.id.cb_username)
    CheckBox cbUsername;
    private String phone_no;//移动端设备型号
    private String phone_system;//移动端设备系统
    private String phone_version;//移动端设备版本
    private String phone_imei;//移动端设备唯一标识
    private String type;// close 关闭手势密码

    @Override
    protected int getContentView() {
        return R.layout.activity_login;
    }

    @Override
    protected void initData() {
        ButterKnife.bind(this);
        titlebar.setVisibility(View.GONE);
        phone_no = MobileInfoUtil.getSystemModel();
        phone_system = Constant.PHONE_SYSTEM;
        phone_version = MobileInfoUtil.getSystemVersion();
        phone_imei = MobileInfoUtil.getIMEI(mContext);
        cbUsername.setChecked(PrefUtil.getBooleanPref(mContext,Constant.REMENBER_USER_NAME,true));
        if(PrefUtil.getBooleanPref(mContext,Constant.REMENBER_USER_NAME,true)){
            if(!TextUtils.isEmpty(PrefUtil.getStringPref(mContext,Constant.USERNAME))){
                etUsername.setText(PrefUtil.getStringPref(mContext,Constant.USERNAME));
            }
        }
        type=getIntent().getStringExtra("type");

    }

    @OnClick(R.id.tv_login)
    public void onViewClicked() {//登录

        if (TextUtils.isEmpty(etUsername.getText().toString().trim())) {
            ToastUtil.showToast(mContext, "请输入用户名");
            return;
        }
        if (TextUtils.isEmpty(etPassword.getText().toString().trim())) {
            ToastUtil.showToast(mContext, "请输入密码");
            return;
        }

        doLogin();


    }

    @Override
    protected void setListener() {
        super.setListener();
        cbUsername.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                PrefUtil.savePref(mContext,Constant.REMENBER_USER_NAME,isChecked);
            }
        });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == 1 && resultCode == 1) {
            toMainActivity();
        }

    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        ButterKnife.unbind(this);
    }


    /**
     * 跳转到主页
     */
    private void toMainActivity() {
        Intent intent = new Intent(LoginActivity.this, MainActivity.class);
        startActivity(intent);
        LoginActivity.this.finish();
    }

    /**
     * 跳转到修改密码界面
     */
    private void toModifyPasswordActivity() {
        Intent intent = new Intent(mContext, ModifyPasswordActivity.class);
        intent.putExtra("type", "default");
        startActivityForResult(intent, 1);
    }

    /**
     * 登录接口
     */
    private void doLogin() {
        Map map = new HashMap();
        map.put("user_id", etUsername.getText().toString().trim());
        map.put("password", etPassword.getText().toString().trim());
        map.put("phone_no", phone_no);
        map.put("phone_system", phone_system);
        map.put("phone_version", phone_version);
        map.put("phone_imei", phone_imei);
        OkGoUtil.post(Constant.doLogin, this, map, new DialogCallback<String>(this) {
            @Override
            public void onSuccess(Response<String> response) {

                try {
                    LogUtil.showLog(response.body());
                    LoginModel model = new Gson().fromJson(response.body(), LoginModel.class);
                    if (model.getStateCode().equals("1")) {
                        if(type.equals("close")){//忘记手势密码
                            PrefUtil.savePref(mContext,"PASSWORD","");
                        }
                        /**
                         * 数据存储
                         */
                        PrefUtil.savePref(mContext, Constant.USERINFO, new Gson().toJson(model.getResult().getUserInfo()));
                        PrefUtil.savePref(mContext, Constant.ORG, new Gson().toJson(model.getResult().getOrg()));
                        PrefUtil.savePref(mContext, Constant.USERNAME, etUsername.getText().toString().trim());//保存用户名
                        PrefUtil.savePref(mContext, Constant.USER_PASSWRD, etPassword.getText().toString().trim());//保存密码
                        /**
                         * 推送设置标签和别名
                         */
                        if (PrefUtil.getBooleanPref(mContext, "NOTICE", true)) {
                            JPushInterface.setAlias(mContext, sequence, model.getResult().getUserInfo().getUser_id());
                            Set set = new HashSet();
                            for (int i = 0; i < model.getResult().getOrg().size(); i++) {
                                set.add(model.getResult().getOrg().get(i).getApplication_id());
                            }
                            JPushInterface.setTags(mContext, sequence, set);
                        }
                        /**
                         * 密码和系统默认的密码一致时，跳转到修改密码的界面
                         */
                        if (etPassword.getText().toString().trim().equals(Constant.USER_PASSWRD_DEFAULT)) {
                            toModifyPasswordActivity();
                        } else {
                            toMainActivity();
                        }


                    } else {
                        ToastUtil.showToast(mContext, model.getMessage());
                    }

                } catch (Exception e) {
                    LogUtil.showLog(e.getMessage());
                    ToastUtil.showToast(mContext, e.getMessage());
                }

            }

            @Override
            public void onError(Response<String> response) {
                super.onError(response);
                ToastUtil.showToast(mContext, Constant.error);
            }
        });

    }


}
