package com.rmmis.platform;

import android.*;
import android.Manifest;
import android.app.Activity;
import android.app.Dialog;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.Nullable;
import android.text.TextUtils;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;

import com.google.gson.Gson;
import com.lzy.okgo.OkGo;
import com.lzy.okgo.model.HttpParams;
import com.lzy.okgo.model.Response;
import com.rmmis.platform.base.BaseActivity;
import com.rmmis.platform.model.LoginModel;
import com.rmmis.platform.model.ShowPlatformVersionModel;
import com.rmmis.platform.net.Constant;
import com.rmmis.platform.net.DialogCallback;
import com.rmmis.platform.net.OkGoUtil;
import com.rmmis.platform.util.CommomDialog;
import com.rmmis.platform.util.CommonUtil;
import com.rmmis.platform.util.LogUtil;
import com.rmmis.platform.util.PrefUtil;
import com.rmmis.platform.util.ToastUtil;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import cn.jpush.android.api.JPushInterface;

/**
 * Created by Edianzu on 2018/10/15.
 */

public class StartActivity extends BaseActivity{
    private Handler handler=new Handler();
    private String phone_system;
    private String versionCode;//当前App的版本号

    @Override
    protected int getContentView() {
        return R.layout.activity_start;
    }

    @Override
    protected void initData() {
        titlebar.setVisibility(View.GONE);
        phone_system= Constant.PHONE_SYSTEM;
        versionCode= CommonUtil.getVersionName(mContext);
        permission();

    }

    @Override
    protected void setListener() {

    }

    /**
     * 权限处理
     */
    private void permission(){
        requestRunPermisssion(new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE,Manifest.permission.READ_PHONE_STATE}, new PermissionListener() {
            @Override
            public void onGranted() {
                //表示所有权限都授权了,版本更新接口
               showPlatformVersion();

            }
            @Override
            public void onDenied(List<String> deniedPermission) {
                for(String permission : deniedPermission){

                    finish();
                }
            }
        });
    }


    /**
     * 手势密码取消时，跳转登录界面，打开时，跳到手势密码界面
     */
    private void toNextActivity(){
        if(TextUtils.isEmpty(PrefUtil.getStringPref(StartActivity.this,"PASSWORD"))){
            Intent intent=new Intent(StartActivity.this,LoginActivity.class);
            intent.putExtra("type","open");
            startActivity(intent);
            StartActivity.this.finish();
        }else{
            Intent intent=new Intent(StartActivity.this,GesturePasswordActivity.class);
            intent.putExtra("type","StartActivity");
            startActivity(intent);
            StartActivity.this.finish();
        }
    }

    /**
     * 版本更新接口
     */
    private void showPlatformVersion(){
        Map map=new HashMap();
        map.put("phone_system",phone_system);
        OkGoUtil.post(Constant.showPlatformVersion, this, map, new DialogCallback<String>(this) {
            @Override
            public void onSuccess(Response<String> response) {
                try{
                    LogUtil.showLog(response.body());
                    ShowPlatformVersionModel model=new Gson().fromJson(response.body(),ShowPlatformVersionModel.class);
                    if(model.getStateCode().equals("1")){
                        PrefUtil.savePref(mContext,Constant.PLATFORMAPPLICATION, new Gson().toJson(model.getResult()));
                        update(model.getResult());
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
    /**
     * 版本更新模块
     */
    public void update(final ShowPlatformVersionModel.PlcatformApplicationBean bean){
        try{
            if(Double.parseDouble(versionCode)< Double.parseDouble(bean.getFore_version_number())){

                //强制更新
                new CommomDialog(mContext, R.style.dialog,bean.getRemark(), new CommomDialog.OnCloseListener() {
                    @Override
                    public void onClick(Dialog dialog, boolean confirm) {
                        if (confirm) {//确定
                            downApk(bean.getApplication_file());

                        }else{

                        }

                    }
                }).setTitle("版本更新") .setContentTxtVisble(true).show();

            }else{
                if(Double.parseDouble(versionCode)<Double.parseDouble(bean.getVersion_number())){
                    /**
                     * 判断强制更新还是自动更新
                     */
                    if(bean.getVersion_type().equals("0")){//普通更新
                        //普通更新
                        new CommomDialog(mContext, R.style.dialog,bean.getRemark(), new CommomDialog.OnCloseListener() {
                            @Override
                            public void onClick(Dialog dialog, boolean confirm) {
                                if (confirm) {//确定
                                    //根据地址，跳转浏览器下载安装
                                    downApk(bean.getApplication_file());
                                }else{//取消
                                    /**
                                     * 界面跳转
                                     */
                                    toNextActivity();
                                }

                            }
                        }).setTitle("版本更新").setContentTxtVisble(false).show();

                    }else if(bean.getVersion_type().equals("1")){//强制更新
                        //强制更新
                        new CommomDialog(mContext, R.style.dialog,bean.getRemark(), new CommomDialog.OnCloseListener() {
                            @Override
                            public void onClick(Dialog dialog, boolean confirm) {
                                if (confirm) {//确定
                                    //根据地址，跳转浏览器下载安装
                                    downApk(bean.getApplication_file());
                                }else{

                                }

                            }
                        }).setTitle("版本更新") .setContentTxtVisble(true).show();

                    }
                }else{
                    /**
                     * 界面跳转
                     */
                    toNextActivity();
                }

            }

        }catch (Exception e){

        }

    }
    /**
     * 跳转到浏览器指定网页
     * @param url
     */
    private void  downApk(String url){
        try{
            CommonUtil.openWeb(mContext,url);
        }catch(Exception e){
            ToastUtil.showToast(mContext,"下载地址错误");
        }
    }
    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK) {
            return true;
        }
        return super.onKeyDown(keyCode, event);


    }

  private void test(){
        String url="";
        HttpParams params=new HttpParams();
        OkGo.<String>post(url).params(params).execute(new DialogCallback<String>(StartActivity.this) {
            @Override
            public void onSuccess(Response<String> response) {

            }

            @Override
            public void onError(Response<String> response) {
                super.onError(response);
            }
        });
  }


}
