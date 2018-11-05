package com.rmmis.platform.util;

import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.net.Uri;

/**
 * Created by Edianzu on 2018/10/24.
 */

public class CommonUtil {
    // 获取版本名
    public static String getVersionName(Context context) {
        return getPackageInfo(context).versionName;
    }
    // 获取版本号
    public static int getVersionCode(Context context) {
        return getPackageInfo(context).versionCode;
    }
    private static PackageInfo getPackageInfo(Context context) {
        PackageInfo pi = null;

        try {
            PackageManager pm = context.getPackageManager();
            pi = pm.getPackageInfo(context.getPackageName(), PackageManager.GET_CONFIGURATIONS);

            return pi;
        } catch (Exception e) {
            e.printStackTrace();
        }

        return pi;
    }
    /**
     * 判断应用是否安装包
     * @param context
     * @param packagename
     * @return true：已经安装 false:尚未安装
     */

    public static boolean isAppInstalled(Context context,String packagename)
    {

        PackageInfo packageInfo;
        try {
            packageInfo = context.getPackageManager().getPackageInfo(packagename, 0);
        }catch (Exception  e) {
            packageInfo = null;
            e.printStackTrace();
            LogUtil.showLog(e.getMessage());
        }
        if(packageInfo ==null){

            return false;
        }else{

            return true;
        }
    }

    /**
     * 通过浏览器打开指定网页
     * @param context
     * @param url：网页地址
     */
    public static void openWeb(Context context,String url){
        url="https://app.rails.cn/MobilePlatform/ICTTest/download/";
        Intent intent = new Intent();
        intent.setData(Uri.parse(url));//Url 就是你要打开的网址
        intent.setAction(Intent.ACTION_VIEW);
        context.startActivity(intent); //启动浏览器
    }

    /**
     * @param context
     * @param packagename 第三方应用的包名
     * @param activity    第三方应用的启动页
     */
    public static void openOtherApp(Context context,String packagename,String activity,String userInfo){

            Intent i = new Intent();
            i.putExtra("userInfo",userInfo);
            ComponentName cn = new ComponentName(packagename,
                    activity);
            i.setComponent(cn);
            context.startActivity(i);



    }
    /**
     * 判断字符串是数字和字母组合而成
     * @param str
     * @return true:是 false：不是
     */
    public static boolean isLetterOrDigit(String str) {
        String rex="^(?![0-9]+$)(?![a-zA-Z]+$)[0-9A-Za-z_]{1,8}$";
        return str.matches(rex);
    }

}
