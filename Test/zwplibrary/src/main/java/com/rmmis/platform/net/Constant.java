package com.rmmis.platform.net;

import com.rmmis.platform.R;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by Edianzu on 2018/10/17.
 */

public class Constant {
    /**
     * 网络url
     */
//    private static String baseurl = "http://192.168.1.123:8080/mamServer/";
    private static String baseurl = "http://172.20.104.39:8888/mamServer/";//测试地址（测试服务器）
    public static String GetJpushListByUserid = baseurl + "GetJpushListByUserid";//获取推送历史记录
    public static String showApplication = baseurl + "showApplication";//根据用户的账户（手机号）来给用户显示子应用信息
    public static String doLogin = baseurl + "doLogin";//登录接口
    public static String updatePassword=baseurl+"updatePassword";//修改密码
    public static String showPlatformVersion=baseurl+"showPlatformVersion";//版本更新
    public static String selectInterface=baseurl+"selectInterface";//乘务助手列表查询
    /**
     * 常量
     */
    public static String AESKEY = "1234567890abcdef";//AES加密的key值
    public static String USER_PASSWRD_DEFAULT = "123456";//登录的默认密码
    public static String PHONE_SYSTEM = "0";//（0 android  1 ios）设备系统
    public static String error = "服务器异常";
    public static boolean logFlag=true;//是否打印日志；true：打印 false：
    /**
     * 用于保存数据的key值
     */
    public static String USERINFO = "userInfo";//用户基本信息
    public static String ORG = "org";//子应用平台信息
    public static String PLATFORMAPPLICATION = "plcatformApplication";//主应用版本信息
    public static String USERNAME = "username";//登录的用户名
    public static String USER_PASSWRD = "user_password";//登录的密码
    public static String REMENBER_USER_NAME = "remembername";//登录的默认密码
    public static String PRE_APPS="pre_appapplications";//
    /**
     * 子应用对应图标集合
     */



}
