package com.rmmis.platform.primaryapplication.frament;

import android.app.Dialog;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.rmmis.platform.GesturePasswordActivity;
import com.rmmis.platform.LoginActivity;
import com.rmmis.platform.MainActivity;
import com.rmmis.platform.R;
import com.rmmis.platform.SetGesturePasswordActivity;
import com.rmmis.platform.base.BaseFragment;
import com.rmmis.platform.model.LoginModel;
import com.rmmis.platform.model.ShowPlatformVersionModel;
import com.rmmis.platform.net.Constant;
import com.rmmis.platform.primaryapplication.activity.ModifyPasswordActivity;
import com.rmmis.platform.util.CommomDialog;
import com.rmmis.platform.util.CommonUtil;
import com.rmmis.platform.util.PrefUtil;
import com.rmmis.platform.util.ToastUtil;
import com.rmmis.platform.view.SwitchButton;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;
import cn.jpush.android.api.JPushInterface;
import de.greenrobot.event.EventBus;

/**
 * Created by Edianzu on 2018/10/15.
 * 工作台界面
 */

public class MyFragment extends BaseFragment {
    @Bind(R.id.rl_sys_update)
    RelativeLayout rlSysUpdate;
    @Bind(R.id.op_reback)
    RelativeLayout opReback;
    @Bind(R.id.rl_notice)
    RelativeLayout rlNotice;
    @Bind(R.id.rl_modify_password)
    RelativeLayout rlModifyPassword;
    @Bind(R.id.tv_logout)
    TextView tvLogout;
    @Bind(R.id.switchbutton)
    SwitchButton switchbutton;
    @Bind(R.id.rl_modify_firpassword)
    RelativeLayout rlModifyFirpassword;
    @Bind(R.id.switchbutton_firgue_password)
    ImageView switchbuttonFirguePassword;
    @Bind(R.id.tv_role)
    TextView tvRole;
    @Bind(R.id.tv_phone)
    TextView tvPhone;
    @Bind(R.id.rl_firgue_password)
    RelativeLayout rlFirguePassword;
    @Bind(R.id.tv_version)
    TextView tvVersion;
    public static int sequence = 1;
    private ShowPlatformVersionModel.PlcatformApplicationBean pmodel;//主应用版本信息
    private LoginModel.ResultBean.UserInfoBean umodel;//用户基本信息
    private List<LoginModel.ResultBean.OrgBean> omodel;//子应用平台信息
    private String versionCode;//当前App的版本号


    @Override
    protected View init(LayoutInflater inflater) {
        View view = inflater.inflate(R.layout.fragment_my, null);
        ButterKnife.bind(this, view);
        EventBus.getDefault().register(this);
        return view;
    }

    @Override
    protected void initData() {
        pmodel = new Gson().fromJson(PrefUtil.getStringPref(mContext, Constant.PLATFORMAPPLICATION), ShowPlatformVersionModel.PlcatformApplicationBean.class);
        umodel = new Gson().fromJson(PrefUtil.getStringPref(mContext, Constant.USERINFO), LoginModel.ResultBean.UserInfoBean.class);
        omodel = (List<LoginModel.ResultBean.OrgBean>) new Gson().fromJson(PrefUtil.getStringPref(mContext, Constant.ORG), new TypeToken<List<LoginModel.ResultBean.OrgBean>>(){}.getType());
        tvRole.setText("Bob: "+umodel.getReal_name());
        tvPhone.setText("手机号： " + umodel.getPhone());
        versionCode = CommonUtil.getVersionName(mContext);
        if(Double.parseDouble(versionCode)<Double.parseDouble(pmodel.getVersion_number())){
            tvVersion.setText("有新版本");
            tvVersion.setTextColor(mContext.getResources().getColor(R.color.red));
        }else{
            tvVersion.setText("已是最新版本");
            tvVersion.setTextColor(mContext.getResources().getColor(R.color.ligth_grey));
        }
        switchbutton.setSelected(PrefUtil.getBooleanPref(mContext, "NOTICE", true));
        if (!TextUtils.isEmpty(PrefUtil.getStringPref(mContext, "PASSWORD"))) {
            switchbuttonFirguePassword.setImageResource(R.mipmap.wode_on);

            rlModifyFirpassword.setVisibility(View.VISIBLE);

        } else {
            switchbuttonFirguePassword.setImageResource(R.mipmap.wode_off);
            rlModifyFirpassword.setVisibility(View.GONE);
        }

        switchbutton.setOnSwitchButtonClick(new SwitchButton.OnSwitchButtonClick() {
            @Override
            public void onClick(boolean selected) {
                if (selected) {//显示通知栏通知信息
                    PrefUtil.savePref(getActivity(), "NOTICE", true);
                    JPushInterface.setAlias(mContext, sequence, umodel.getUser_id());
                    Set set = new HashSet();
                    for (int i = 0; i < omodel.size(); i++) {
                        set.add(omodel.get(i).getApplication_id());
                    }
                    JPushInterface.setTags(mContext, sequence, set);
                } else {//不显示通知栏通知信息
                    PrefUtil.savePref(getActivity(), "NOTICE", false);
                    JPushInterface.deleteAlias(mContext, sequence);
                    Set set = new HashSet();
                    for (int i = 0; i < omodel.size(); i++) {
                        set.add(omodel.get(i).getApplication_id());
                    }
                    JPushInterface.deleteTags(mContext, sequence, set);
                }
            }
        });


    }


    @Override
    public void onDestroyView() {
        super.onDestroyView();
        ButterKnife.unbind(this);
        EventBus.getDefault().unregister(this);
    }

    @OnClick({R.id.rl_sys_update, R.id.op_reback, R.id.rl_notice, R.id.rl_modify_password, R.id.tv_logout, R.id.rl_modify_firpassword, R.id.switchbutton_firgue_password})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.rl_sys_update://版本更新
                if(Double.parseDouble(versionCode)<Double.parseDouble(pmodel.getVersion_number())){
                    new CommomDialog(mContext, R.style.dialog,pmodel.getRemark(), new CommomDialog.OnCloseListener() {
                        @Override
                        public void onClick(Dialog dialog, boolean confirm) {
                            if (confirm) {//确定
                                //根据地址，跳转浏览器下载安装
                                Intent intent = new Intent();
                                intent.setData(Uri.parse(pmodel.getApplication_file()));//Url 就是你要打开的网址
                                intent.setAction(Intent.ACTION_VIEW);
                                getActivity().startActivity(intent); //启动浏览器
                            }else{//取消


                            }

                        }
                    }).setTitle("版本更新").setContentTxtVisble(false).show();
                }
                break;
            case R.id.op_reback://意见反馈


                break;
            case R.id.rl_modify_password://修改密码
                Intent intent3 = new Intent(mContext, ModifyPasswordActivity.class);
            intent3.putExtra("type","add");
                startActivity(intent3);
                break;
            case R.id.tv_logout://退出登录
                new CommomDialog(getActivity(), R.style.dialog, "您确定退出登录吗？", new CommomDialog.OnCloseListener() {
                    @Override
                    public void onClick(Dialog dialog, boolean confirm) {
                        if (confirm) {//确定
                            /**
                             *取消设备的标签和别名
                             */
                            PrefUtil.savePref(getActivity(), "NOTICE", false);
                            JPushInterface.deleteAlias(mContext, sequence);
                            Set set = new HashSet();
                            for (int i = 0; i < omodel.size(); i++) {
                                set.add(omodel.get(i).getApplication_id());
                            }
                            JPushInterface.deleteTags(mContext, sequence, set);
                            getActivity().finish();
                            Intent intent4 = new Intent(getActivity(), LoginActivity.class);
                            intent4.putExtra("type","open");
                            getActivity().startActivity(intent4);

                        }


                    }
                }).setTitle("提示").show();

                break;
            case R.id.switchbutton_firgue_password://手势密码开关
                if (TextUtils.isEmpty(PrefUtil.getStringPref(mContext, "PASSWORD"))) {
                    Intent intent4 = new Intent(mContext, SetGesturePasswordActivity.class);
                    intent4.putExtra("type", "add");
                    startActivity(intent4);
                } else {
                    Intent intent5 = new Intent(mContext, GesturePasswordActivity.class);
                    intent5.putExtra("type", "MainActivity_cancel");
                    startActivity(intent5);
                }
                break;
            case R.id.rl_modify_firpassword://修改手势密码
                Intent intent6 = new Intent(mContext, GesturePasswordActivity.class);
                intent6.putExtra("type", "MainActivity_modify");
                startActivity(intent6);
                break;
        }
    }


    public void onEvent(String message) {
        if (message.equals("add")) {//设置手势密码
            switchbuttonFirguePassword.setImageResource(R.mipmap.wode_on);
            rlModifyFirpassword.setVisibility(View.VISIBLE);
        } else {
            if (message.equals("cancel")) {//取消手势密码
                switchbuttonFirguePassword.setImageResource(R.mipmap.wode_off);
                rlModifyFirpassword.setVisibility(View.GONE);
                ToastUtil.showToast(mContext, "取消手势密码成功");
            } else if (message.equals("MainActivity_modify")) {//修改手势密码
                ToastUtil.showToast(mContext, "修改手势密码成功");
            } else  if (message.equals("modify_password")) {//修改密码成功

                Intent intent =new Intent(mContext,LoginActivity.class);
                intent.putExtra("type","open");
                startActivity(intent);

                getActivity().finish();
            }else{

            }

        }


    }



}
