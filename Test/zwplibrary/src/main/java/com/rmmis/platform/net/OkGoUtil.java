package com.rmmis.platform.net;

import android.text.TextUtils;

import com.lzy.okgo.OkGo;
import com.lzy.okgo.model.HttpParams;
import com.rmmis.platform.util.AESUtil;
import com.rmmis.platform.util.LogUtil;

import java.util.Map;

/**
 * Created by Edianzu on 2018/10/17.
 */

public class OkGoUtil {
    /**
     * post请求
     * @param url ：请求URL
     * @param tag ：标志
     * @param map ：请求参数集合
     * @param callback：回调
     * @param <T> ：返回实体
     */
    public static <T> void post(String url, Object tag, Map<String, T> map, DialogCallback<T> callback) {
        String  string="";
        HttpParams params=new HttpParams();
        for (Map.Entry<String, T> entry : map.entrySet())
        {
            if(TextUtils.isEmpty(string)){
                string=entry.getKey()+"="+entry.getValue().toString();
            }else{
                string=entry.getKey()+"="+entry.getValue().toString() +"&"+string;
            }
            params.put(entry.getKey(), AESUtil.Encrypt(entry.getValue().toString(),Constant.AESKEY));
        }
        LogUtil.showLog("加密前输入参数："+string);
        OkGo.<T>post(url)
                .tag(tag)
                .params(params)
                .execute(callback);
    }
    public static  void  cancelTag(Object tag){
        OkGo.getInstance().cancelTag(tag);
    }
}