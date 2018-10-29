package com.rmmis.platform.util;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;



import android.content.Context;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;

public class PrefUtil {

	private static final String APPLICATION_PREFERENCES = "app_prefs";

	private static Editor editor;
	private static SharedPreferences pref;

	/**
	 * 保存MapPref
	 * 
	 * @param context
	 * @param key
	 * @param value
	 */
	public static void savePref(Context context, String key, String value) {
		initEditor(context);
		editor.putString(key, value);
		editor.commit();
	}

	/**
	 * 保存布尔值Pref
	 * 
	 * @param context
	 * @param key
	 * @param value
	 */
	public static void savePref(Context context, String key, boolean value) {
		initEditor(context);
		editor.putBoolean(key, value);
		editor.commit();
	}

	/**
	 * 保存整形Pref
	 * 
	 * @param context
	 * @param key
	 * @param value
	 */
	public static void savePref(Context context, String key, int value) {
		initEditor(context);
		editor.putInt(key, value);
		editor.commit();
	}

	/**
	 * 获取pref中key对应的字符串值
	 * 
	 * @param context
	 * @param key
	 * @return
	 */
	public static String getStringPref(Context context, String key) {
		return getStringPref(context, key, "");
	}

	public static String getStringPref(Context context, String key,
			String defaultValue) {
		initPref(context);
		return pref.getString(key, defaultValue);
	}

	/**
	 * 获取pref中key对应的布尔值
	 * 
	 * @param context
	 * @param key
	 * @param defaultValue
	 * @return
	 */
	public static boolean getBooleanPref(Context context, String key,
			boolean defaultValue) {
		initPref(context);
		return pref.getBoolean(key, defaultValue);
	}

	/**
	 * 获取pref中key对应的整形
	 * 
	 * @param context
	 * @param key
	 * @param defaultValue
	 * @return
	 */
	public static int getIntPref(Context context, String key, int defaultValue) {
		initPref(context);
		return pref.getInt(key, defaultValue);
	}

	private static void initEditor(Context context) {
		initPref(context);
		editor = pref.edit();
	}

	private static void initPref(Context context) {
		// 空判断，防止空指针，导致程序崩溃。
		if (null == pref)
			pref = context.getSharedPreferences(APPLICATION_PREFERENCES,
					Context.MODE_PRIVATE);
	}

}
