package com.rmmis.platform.primaryapplication.frament;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;

import com.google.gson.Gson;
import com.lzy.okgo.model.Response;
import com.rmmis.platform.R;
import com.rmmis.platform.base.BaseFragment;
import com.rmmis.platform.base.WebViewActivity;
import com.rmmis.platform.model.HistoryListBeanModel;
import com.rmmis.platform.model.LoginModel;
import com.rmmis.platform.net.Constant;
import com.rmmis.platform.net.DialogCallback;
import com.rmmis.platform.net.OkGoUtil;
import com.rmmis.platform.primaryapplication.activity.MessageActivity;
import com.rmmis.platform.primaryapplication.adapter.MessageAdapter;
import com.rmmis.platform.util.LogUtil;
import com.rmmis.platform.util.PrefUtil;
import com.rmmis.platform.util.ToastUtil;
import com.scwang.smartrefresh.layout.SmartRefreshLayout;
import com.scwang.smartrefresh.layout.api.RefreshLayout;
import com.scwang.smartrefresh.layout.listener.OnLoadmoreListener;
import com.scwang.smartrefresh.layout.listener.OnRefreshListener;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import butterknife.Bind;
import butterknife.ButterKnife;
import de.greenrobot.event.EventBus;

/**
 * Created by Edianzu on 2018/10/15.
 * 消息界面
 */

public class MessageFragment extends BaseFragment {
    @Bind(R.id.listview)
    ListView listview;
    @Bind(R.id.smartRefreshLayout)
    SmartRefreshLayout smartRefreshLayout;
    /**
     * 输入参数
     */
    private int page = 1;
    private int pageSize = 10;
    private String  userId;
    /**
     * 输出参数
     */
    private ArrayList<HistoryListBeanModel.HistoryListBean> data = new ArrayList<>();
    private MessageAdapter adapter;
    private LoginModel.ResultBean.UserInfoBean umodel;//用户基本信息
    @Override
    protected View init(LayoutInflater inflater) {
        View rootView = inflater.inflate(R.layout.fragment_message, null);
        ButterKnife.bind(this, rootView);
        return rootView;
    }

    @Override
    protected void initData() {

        umodel = new Gson().fromJson(PrefUtil.getStringPref(mContext, Constant.USERINFO), LoginModel.ResultBean.UserInfoBean.class);
        userId=umodel.getUser_id();
        getJpushListByUserid();
    }

    @Override
    protected void setListener() {
        super.setListener();
        listview.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                HistoryListBeanModel.HistoryListBean  historyListBean=data.get(position);
                if(!TextUtils.isEmpty(historyListBean.getPush_type())){
                    if(historyListBean.getPush_type().equals("1")){//通知
                        EventBus.getDefault().post("MessageFragment");
                    }else if(historyListBean.getPush_type().equals("2")){//消息
                        Intent intent = new Intent(mContext, MessageActivity.class);
                        intent.putExtra("page_title", "消息");
                        intent.putExtra("title", historyListBean.getPush_title());
                        intent.putExtra("time", historyListBean.getCreatetime());
                        intent.putExtra("people", historyListBean.getPush_userName());
                        intent.putExtra("content", historyListBean.getPush_content());
                        startActivity(intent);

                    }else if(historyListBean.getPush_type().equals("3")){//公告
                        if(TextUtils.isEmpty(historyListBean.getPush_link())){//纯文本公告
                            Intent intent = new Intent(mContext, MessageActivity.class);
                            intent.putExtra("page_title", "公告");
                            intent.putExtra("title", historyListBean.getPush_title());
                            intent.putExtra("time", historyListBean.getCreatetime());
                            intent.putExtra("people", historyListBean.getPush_userName());
                            intent.putExtra("content", historyListBean.getPush_content());
                            startActivity(intent);
                        }else{//内容为网页的公告
                            Intent intent = new Intent(mContext, WebViewActivity.class);
                            intent.putExtra("url", "http://"+historyListBean.getPush_link());
                            intent.putExtra("title", "公告");
                            startActivity(intent);
                        }


                    }else{//其他

                    }
                }

            }
        });
        smartRefreshLayout.setOnRefreshListener(new OnRefreshListener() {//下拉刷新
            @Override
            public void onRefresh(RefreshLayout refreshlayout) {

                page = 1;
                getJpushListByUserid();


            }
        });

        smartRefreshLayout.setOnLoadmoreListener(new OnLoadmoreListener() {//上拉加载
            @Override
            public void onLoadmore(RefreshLayout refreshlayout) {
                page++;
                getJpushListByUserid();

            }
        });

    }

    /**
     *  获取推送历史记录功能
     */
    private void getJpushListByUserid() {
        if (page == 1 && data.size() > 0) {
            data.clear();
        }
        Map map = new HashMap();
        map.put("userId", userId);
        map.put("page", page);
        map.put("number", pageSize);

        OkGoUtil.post(Constant.GetJpushListByUserid, "GetJpushListByUserid", map, new DialogCallback<String>(getActivity()) {
            @Override
            public void onSuccess(Response<String> response) {
                LogUtil.showLog("返回数据："+response.body());
                try {
                    if (smartRefreshLayout.isRefreshing()) {
                        smartRefreshLayout.finishRefresh();

                    } else if (smartRefreshLayout.isLoading()) {
                        smartRefreshLayout.finishLoadmore();

                    }
                    HistoryListBeanModel model = new Gson().fromJson(response.body(), HistoryListBeanModel.class);
                    if(model.getStateCode()==1){
                        if (page > 1 && model.getHistoryList().size()==0) {
                            ToastUtil.showToast(mContext, "没有更多数据");
//                            smartRefreshLayout.setEnableLoadmore(false);

                            return;
                        }
                        if (page == 1) {
                            data = model.getHistoryList();
                        } else {
                            data.addAll(model.getHistoryList());
                        }
                        if (adapter == null) {
                            adapter = new MessageAdapter(mContext, data, R.layout.item_message);
                            listview.setAdapter(adapter);
                        } else {
                            adapter.setmDatas(data);
                            adapter.notifyDataSetChanged();
                        }
                    }else{
                        ToastUtil.showToast(mContext,model.getMessage());
                    }
                } catch (Exception e) {
                    ToastUtil.showToast(mContext,e.getMessage());
                    LogUtil.showLog(e.getMessage());
                }


            }

            @Override
            public void onError(Response<String> response) {
                super.onError(response);

            }
        });

    }


    @Override
    public void onDestroyView() {
        super.onDestroyView();
        ButterKnife.unbind(this);
    }



}
