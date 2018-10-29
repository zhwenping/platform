package com.rmmis.platform.util;


import android.app.Activity;
import android.content.Context;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.rmmis.platform.R;

public class ToastUtil {
    private static Toast toast;

    /**
     * 自定义UI，解决连续弹框显示问题
     *
     * @param text
     */
    public static void showToast(Context mContext, String text) {
        if(toast == null){
            toast = new Toast(mContext);
        }
        toast.setDuration(Toast.LENGTH_SHORT);
        toast.setGravity(Gravity.CENTER, 0, 0);
        LayoutInflater inflater = ((Activity)mContext).getLayoutInflater();
        LinearLayout toastLayout = (LinearLayout)inflater.inflate(R.layout.toast, null);
        TextView txtToast = (TextView)toastLayout.findViewById(R.id.txt_toast);
        txtToast.setText(text);
        toast.setView(toastLayout);
        toast.show();
    }
}
