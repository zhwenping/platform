package com.rmmis.platform.primaryapplication.activity;

import android.os.Bundle;
import android.text.TextUtils;
import android.widget.TextView;

import com.google.gson.Gson;
import com.lzy.okgo.model.Response;
import com.rmmis.platform.R;
import com.rmmis.platform.base.BaseActivity;
import com.rmmis.platform.model.LoginModel;
import com.rmmis.platform.model.UpdatePasswordModel;
import com.rmmis.platform.net.Constant;
import com.rmmis.platform.net.DialogCallback;
import com.rmmis.platform.net.OkGoUtil;
import com.rmmis.platform.util.CommonUtil;
import com.rmmis.platform.util.LogUtil;
import com.rmmis.platform.util.PrefUtil;
import com.rmmis.platform.util.ToastUtil;
import com.rmmis.platform.view.ClearEditText;

import java.util.HashMap;
import java.util.Map;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;
import de.greenrobot.event.EventBus;

/**
 * Created by Edianzu on 2018/10/19.
 * 主页“我的”-修改密码界面
 */

public class ModifyPasswordActivity extends BaseActivity {
    @Bind(R.id.et_old_password)
    ClearEditText etOldPassword;
    @Bind(R.id.et_new_password)
    ClearEditText etNewPassword;
    @Bind(R.id.et_confirm_password)
    ClearEditText etConfirmPassword;
    @Bind(R.id.tv_confirm)
    TextView tvConfirm;
    private String type;
    private LoginModel.ResultBean.UserInfoBean umodel;//用户基本信息
    @Override
    protected int getContentView() {
        return R.layout.activity_modify_password;
    }

    @Override
    protected void initData() {
        ButterKnife.bind(this);
        umodel = new Gson().fromJson(PrefUtil.getStringPref(mContext, Constant.USERINFO), LoginModel.ResultBean.UserInfoBean.class);

        setTitle("修改密码");
        type=getIntent().getStringExtra("type");


    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        ButterKnife.unbind(this);
    }

    @OnClick(R.id.tv_confirm)
    public void onViewClicked() {
        if(TextUtils.isEmpty(etOldPassword.getText().toString().trim())){
            ToastUtil.showToast(mContext,"请输入原密码");
            return;
        }
        if(TextUtils.isEmpty(etNewPassword.getText().toString().trim())){
            ToastUtil.showToast(mContext,"请输入新密码");
            return;
        }
        if(TextUtils.isEmpty(etConfirmPassword.getText().toString().trim())){
            ToastUtil.showToast(mContext,"请再次输入新密码");
            return;
        }
        if(!etNewPassword.getText().toString().trim().equals(etConfirmPassword.getText().toString().trim())){
            ToastUtil.showToast(mContext,"两次输入的密码不一致");
            return;
        }
        if(etNewPassword.getText().toString().trim().equals(etOldPassword.getText().toString().trim())){
            ToastUtil.showToast(mContext,"新密码和旧密码一致");
            return;
        }
        if(!CommonUtil.isLetterOrDigit(etNewPassword.getText().toString().trim())){
            ToastUtil.showToast(mContext,"密码必须是数字或者字母");
            return;
        }
        if(etNewPassword.getText().toString().length()>8){
            ToastUtil.showToast(mContext,"密码长度不能大于8位");
            return;
        }

        //调用修改密码接口
        updatePassword();

    }

    private void updatePassword(){
        Map map=new HashMap();
        map.put("user_id",umodel.getUser_id());
        map.put("oldPassword",etOldPassword.getText().toString().trim());
        map.put("newPassword",etNewPassword.getText().toString().trim());
        OkGoUtil.post(Constant.updatePassword, this, map, new DialogCallback<String>(this) {
            @Override
            public void onSuccess(Response<String> response) {

                try{
                    LogUtil.showLog(response.body());
                    UpdatePasswordModel model=new Gson().fromJson(response.body(),UpdatePasswordModel.class);
                    if(model.getStateCode().equals("1")){
                        if(type.equals("default")){
                             setResult(1);
                         }else{
                             EventBus.getDefault().post("modify_password");
                         }
                        ToastUtil.showToast(mContext,"密码修改成功");
                        finish();

                    }else{
                        ToastUtil.showToast(mContext,model.getMessage());
                    }

                }catch (Exception e){
                    ToastUtil.showToast(mContext,e.getMessage());
                }

            }

            @Override
            public void onError(Response<String> response) {
                super.onError(response);
                ToastUtil.showToast(mContext,Constant.error);
            }
        });
    }
}
