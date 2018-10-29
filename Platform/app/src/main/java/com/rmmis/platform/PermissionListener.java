package com.rmmis.platform;

import java.util.List;

/**
 * Created by Edianzu on 2018/10/23.
 */
public interface PermissionListener {
    void onGranted();//已授权
    void onDenied(List<String> deniedPermission);//未授权
}
