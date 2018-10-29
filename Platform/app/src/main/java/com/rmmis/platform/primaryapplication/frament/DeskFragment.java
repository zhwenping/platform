package com.rmmis.platform.primaryapplication.frament;

import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.pm.ComponentInfo;
import android.content.pm.PackageInfo;
import android.net.Uri;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.lzy.okgo.model.Response;
import com.rmmis.platform.LoginActivity;
import com.rmmis.platform.R;
import com.rmmis.platform.base.BaseFragment;
import com.rmmis.platform.base.WebViewActivity;
import com.rmmis.platform.model.LoginModel;
import com.rmmis.platform.model.ShowApplicationModel;
import com.rmmis.platform.net.Constant;
import com.rmmis.platform.net.DialogCallback;
import com.rmmis.platform.net.OkGoUtil;
import com.rmmis.platform.primaryapplication.adapter.DeskAdapter;
import com.rmmis.platform.util.CommonUtil;
import com.rmmis.platform.util.LogUtil;
import com.rmmis.platform.util.PrefUtil;
import com.rmmis.platform.util.ToastUtil;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * Created by Edianzu on 2018/10/15.
 * 工作台界面
 */

public class DeskFragment extends BaseFragment {
    @Bind(R.id.listview)
    ListView listview;
    private String user_id;
    private String application_system;
    private LoginModel.ResultBean.UserInfoBean umodel;//用户基本信息
    private List<LoginModel.ResultBean.OrgBean> omodel;//子应用平台信息
    private DeskAdapter adapter;

    private List<ShowApplicationModel.ResultBean> data=new ArrayList<>();


    @Override
    protected View init(LayoutInflater inflater) {
        View rootView=inflater.inflate(R.layout.fragment_desk, null);
        ButterKnife.bind(this, rootView);
        return rootView;
    }

    @Override
    protected void initData() {

        application_system= Constant.PHONE_SYSTEM;
        umodel = new Gson().fromJson(PrefUtil.getStringPref(mContext, Constant.USERINFO), LoginModel.ResultBean.UserInfoBean.class);
        user_id=umodel.getUser_id();
        omodel = (List<LoginModel.ResultBean.OrgBean>) new Gson().fromJson(PrefUtil.getStringPref(mContext, Constant.ORG), new TypeToken<List<LoginModel.ResultBean.OrgBean>>(){}.getType());
        showApplication();
    }

    @Override
    protected void setListener() {
        super.setListener();
        listview.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                ShowApplicationModel.ResultBean bean=data.get(position);
                if(bean.getApplication_state().equals("0")){//应用未上架
                    ToastUtil.showToast(mContext,"应用未上架");
                }else if(bean.getApplication_state().equals("1")){//应用已上架
                    try{
                        /**
                         * 获取传递给子应用的用户信息
                         */
                        String ormInfo="";
                        for(int i=0;i<omodel.size();i++){
                            if(omodel.get(i).getApplication_id().equals(bean.getApplication_id())){
                                ormInfo=new Gson().toJson(omodel.get(i));
                                break;
                            }
                        }
                        LogUtil.showLog("ormInfo"+ormInfo);
                        if(bean.getApplication_type().equals("0")){//h5
                            /**
                             * 跳转界面
                             */
                            Intent intent = new Intent(mContext, WebViewActivity.class);
                            intent.putExtra("url",  bean.getApplication_invocation_class());
                            intent.putExtra("title", bean.getApplication_name());
//                            intent.putExtra("hideTopbar",true);
                            startActivity(intent);


                        }else if(bean.getApplication_type().equals("1")){//内置
                            CommonUtil.openOtherApp(mContext,mContext.getPackageName(),
                                    bean.getApplication_invocation_class());

                        }else if(bean.getApplication_type().equals("2")){//第三方
                            if(CommonUtil.isAppInstalled(mContext,bean.getApplication_package())){//已经安装
                                CommonUtil.openOtherApp(mContext,bean.getApplication_package(),
                                        bean.getApplication_invocation_class());
                            }else{//未安装
                                CommonUtil.openWeb(mContext,bean.getVersion().getPackage_file());
                            }

                        }
                    }catch (Exception e){

                    }

                }


            }
        });
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        ButterKnife.unbind(this);
    }

    /**
     * 获取子应用信息
     */
    private void showApplication(){
        Map map=new HashMap();
        map.put("user_id",user_id);
        map.put("application_system",application_system);
        OkGoUtil.post(Constant.showApplication, this, map, new DialogCallback<String>(getActivity()) {
            @Override
            public void onSuccess(Response<String> response) {
                try{
                    LogUtil.showLog(response.body());
                    ShowApplicationModel model=new Gson().fromJson(response.body(),ShowApplicationModel.class);
                    if(model.getStatus().equals("1")){
                        data=model.getResult();
                         if(adapter==null){
                             adapter=new DeskAdapter(mContext,data,R.layout.item_desk);
                             listview.setAdapter(adapter);
                         }else{
                             adapter.setmDatas(data);
                             adapter.notifyDataSetChanged();
                         }

                    }else{
                        ToastUtil.showToast(mContext,model.getMessage());
                    }

                }catch(Exception e){
                    LogUtil.showLog(e.getMessage());
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
