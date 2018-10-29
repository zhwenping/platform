package com.rmmis.platform.primaryapplication.activity;

import android.os.Bundle;
import android.widget.TextView;

import com.rmmis.platform.R;
import com.rmmis.platform.base.BaseActivity;
import com.rmmis.platform.util.ToastUtil;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * Created by Edianzu on 2018/10/19.
 *  主页“消息”-消息/公告（纯文本类型）界面
 */

public class MessageActivity extends BaseActivity {
    @Bind(R.id.tv_title)
    TextView tvTitle;
    @Bind(R.id.tv_time)
    TextView tvTime;
    @Bind(R.id.tv_people)
    TextView tvPeople;
    @Bind(R.id.tv_content)
    TextView tvContent;
    private String title;//标题
    private String time;//时间
    private String people;//推送人
    private String content;//内容

    @Override
    protected int getContentView() {
        return R.layout.activity_message;
    }

    @Override
    protected void initData() {
        ButterKnife.bind(this);
        setTitle(getIntent().getStringExtra("page_title"));
        title=getIntent().getStringExtra("title");
        time=getIntent().getStringExtra("time");
        people=getIntent().getStringExtra("people");
        content=getIntent().getStringExtra("content");
        try{
            tvTitle.setText(title);
            tvTime.setText("时间："+time);
            tvPeople.setText("推送人："+people);
            tvContent.setText(content);
        }catch (Exception e){
            ToastUtil.showToast(mContext,"解析失败");
        }

    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        ButterKnife.unbind(this);
    }
}
