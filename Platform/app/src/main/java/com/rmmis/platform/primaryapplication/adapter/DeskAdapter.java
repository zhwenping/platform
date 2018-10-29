package com.rmmis.platform.primaryapplication.adapter;

import android.content.Context;
import android.widget.ImageView;

import com.rmmis.platform.R;
import com.rmmis.platform.base.CommonAdapter;
import com.rmmis.platform.base.ViewHolder;
import com.rmmis.platform.model.ShowApplicationModel;
import com.rmmis.platform.net.Constant;
import com.rmmis.platform.util.GlideUtil;
import com.rmmis.platform.util.LogUtil;

import java.util.List;

/**
 * Created by Edianzu on 2018/10/25.
 */

public class DeskAdapter extends CommonAdapter<ShowApplicationModel.ResultBean>{
    public DeskAdapter(Context context, List<ShowApplicationModel.ResultBean> datas, int layoutId) {
        super(context, datas, layoutId);
    }

    @Override
    public void convert(ViewHolder holder, ShowApplicationModel.ResultBean resultBean) {
        try{
            if(Constant.ormImgemap.get(resultBean.getApplication_id())==null){//应用没有图标
                GlideUtil.loadImage(context,R.mipmap.logo,(ImageView) holder.getView(R.id.iv_icon));
            }else{
                GlideUtil.loadImage(context, (int)Constant.ormImgemap.get(resultBean.getApplication_id()),(ImageView) holder.getView(R.id.iv_icon));

            }
            holder.setText(R.id.tv_title,resultBean.getApplication_name());
            holder.setText(R.id.tv_des,resultBean.getDescription());


        }catch(Exception e){
            LogUtil.showLog(e.getMessage());

        }

    }
}
