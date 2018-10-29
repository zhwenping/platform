package com.rmmis.platform.primaryapplication.adapter;

import android.content.Context;
import android.text.TextUtils;

import com.rmmis.platform.R;
import com.rmmis.platform.base.CommonAdapter;
import com.rmmis.platform.base.ViewHolder;
import com.rmmis.platform.model.HistoryListBeanModel;

import java.util.List;

/**
 * Created by Edianzu on 2018/10/23.
 */

public class MessageAdapter extends CommonAdapter<HistoryListBeanModel.HistoryListBean> {
    public MessageAdapter(Context context, List<HistoryListBeanModel.HistoryListBean> datas, int layoutId) {
        super(context, datas, layoutId);
    }
    @Override
    public void convert(ViewHolder holder, HistoryListBeanModel.HistoryListBean historyListBean) {

        try {
            if(!TextUtils.isEmpty(historyListBean.getPush_type())){
                if(historyListBean.getPush_type().equals("1")){//通知
                    holder.setImageResource(R.id.iv,R.mipmap.tz);
                    holder.setText( R.id.tv_push_title,"通知:  "+historyListBean.getPush_title());
                }else if(historyListBean.getPush_type().equals("2")){//消息
                    holder.setImageResource(R.id.iv,R.mipmap.xx);
                    holder.setText( R.id.tv_push_title,"消息:  "+historyListBean.getPush_title());
                }else if(historyListBean.getPush_type().equals("3")){//公告
                    holder.setImageResource(R.id.iv,R.mipmap.gg);
                    holder.setText( R.id.tv_push_title,"公告:  "+historyListBean.getPush_title());
                }else{
                    holder.setText(R.id.tv_push_title,historyListBean.getPush_title());
                }
            }

        }catch (Exception e){

        }
    }
}
