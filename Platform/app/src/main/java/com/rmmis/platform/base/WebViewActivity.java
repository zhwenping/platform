package com.rmmis.platform.base;

import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.webkit.CookieManager;
import android.webkit.CookieSyncManager;
import android.webkit.JavascriptInterface;
import android.webkit.WebChromeClient;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.ProgressBar;

import com.rmmis.platform.R;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * Created by Sfy on 2018/3/6.
 */

public class WebViewActivity extends BaseActivity {

    @Bind(R.id.web_view)
    WebView web_view;
    @Bind(R.id.progressBar)
    ProgressBar progressBar;
    private String title = "";
    private String url = "";
    private String appId = "";
    private static final String APP_CACAHE_DIRNAME = "/webcache";
    private boolean hideTopbar = false;
    private int states = 0;

    @Override
    protected int getContentView() {

        return R.layout.activity_webview;
    }

    @Override
    protected void initData() {
        ButterKnife.bind(this);
        title = getIntent().getStringExtra("title");
        url = getIntent().getStringExtra("url");
//        appId = getIntent().getStringExtra("appId");
        hideTopbar = getIntent().getBooleanExtra("hideTopbar", false);
        states = getIntent().getIntExtra("states", 0);
        if (hideTopbar) {
            titlebar.setVisibility(View.GONE);
        } else {
            if (states == 1) {
                titlebar.setVisibility(View.GONE);
            }
        }
        setTitle(title);
        web_view.setWebViewClient(new WebViewClient() {

            @Override
            public void onPageStarted(WebView view, String url, Bitmap favicon) {
                super.onPageStarted(view, url, favicon);
            }

            @Override
            public boolean shouldOverrideUrlLoading(WebView view, String url) {
                view.loadUrl(url);
                return true;
            }

            @Override
            public void onPageFinished(WebView view, String url) {
                super.onPageFinished(view, url);

            }
        });


        //声明WebSettings子类
        WebSettings webSettings = web_view.getSettings();

        //如果访问的页面中要与Javascript交互，则webview必须设置支持Javascript
        webSettings.setJavaScriptEnabled(true);

        //设置自适应屏幕，两者合用
        webSettings.setUseWideViewPort(true); //将图片调整到适合webview的大小
        webSettings.setLoadWithOverviewMode(true); // 缩放至屏幕的大小

        //缩放操作
        webSettings.setSupportZoom(true); //支持缩放，默认为true。是下面那个的前提。
        webSettings.setBuiltInZoomControls(true); //设置内置的缩放控件。若为false，则该WebView不可缩放
        webSettings.setDisplayZoomControls(false); //隐藏原生的缩放控件
        // 自适应屏幕
        webSettings.setLayoutAlgorithm(WebSettings.LayoutAlgorithm.SINGLE_COLUMN);
        webSettings.setLoadWithOverviewMode(true);
        /**
         * 清理缓存
         */
        webSettings.setCacheMode(WebSettings.LOAD_NO_CACHE);
        web_view.clearCache(true);
        web_view.clearFormData();
        webSettings.setJavaScriptCanOpenWindowsAutomatically(true); //支持通过JS打开新窗口
        webSettings.setLoadsImagesAutomatically(true); //支持自动加载图片
        webSettings.setDefaultTextEncodingName("utf-8");//设置编码格式
        // 开启DOM storage API 功能
        webSettings.setDomStorageEnabled(true);
        // 开启database storage API功能
        webSettings.setDatabaseEnabled(true);
//        webSettings.setCacheMode(WebSettings.LOAD_CACHE_ELSE_NETWORK);
//        String cacheDirPath = getFilesDir().getAbsolutePath() + APP_CACAHE_DIRNAME;
//        Log.i("cachePath", cacheDirPath);
        // 设置数据库缓存路径
//        webSettings.setAppCachePath(cacheDirPath);
//        webSettings.setAppCacheEnabled(true);
        // 设置缓冲大小，我设的是8M
//        webSettings.setAppCacheMaxSize(1024 * 1024 * 8);
        web_view.addJavascriptInterface(new JSInterface(), "Android");
        setCookies(url);
        web_view.loadUrl(url);
        web_view.setWebChromeClient(new WebChromeClient() {
            @Override
            public void onProgressChanged(WebView view, int newProgress) {
                if (newProgress == 100) {
                    progressBar.setVisibility(View.GONE);

                } else {
                    progressBar.setVisibility(View.VISIBLE);
                }
            }

        });

    }


    private void setCookies(String url) {
//        synchronousWebCookies(url, "mobileInfoCookie1=" + Constant.mobileType);
//        synchronousWebCookies(url, "mobileInfoCookie2=" + Constant.mobileModel);
//        synchronousWebCookies(url, "mobileInfoCookie3=" + Constant.systemVersion);
//        synchronousWebCookies(url, "mobileInfoCookie4=" + Constant.getAndroidID(mContext));
//        synchronousWebCookies(url, "mobileInfoCookie5=" + Constant.getVersionName(mContext));
//        synchronousWebCookies(url, "mobileInfoCookie6=" + (UserInfo.getInstance() == null ? null : UserInfo
//                .getInstance().getToken()));
//        synchronousWebCookies(url, "userLanguage=" + (MyApplication.language.equals("zh") ? "zh-CN" : "en"));
//        synchronousWebCookies(url, "appId=" + appId);
    }

    public void synchronousWebCookies(String url, String cookies) {
        CookieManager cookieManager = CookieManager.getInstance();
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            cookieManager.flush();
        } else {
            CookieSyncManager.getInstance().sync();
        }
        cookieManager.setAcceptCookie(true);
        cookieManager.setCookie(url, cookies);
        String newCookie = cookieManager.getCookie(url);

    }




    class JSInterface {

        @JavascriptInterface
        public void downloadFile(final String url) {
            runOnUiThread(new Runnable() {
                @Override
                public void run() {

                    Intent intent = new Intent();
                    intent.setAction("android.intent.action.VIEW");
                    Uri content_url = Uri.parse(url);
                    intent.setData(content_url);
                    startActivity(intent);
                }
            });
        }


        @JavascriptInterface
        public void closeHtmlView() {

            runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    finish();
                }
            });
        }

        //点击返回按钮
        @JavascriptInterface
        public void backHtmlView() {

            runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    if (web_view.canGoBack()) {
                        web_view.goBack();// 返回前一个页面
                    } else {
                        finish();
                    }
                }
            });
        }

        //船级检验的确定按钮
        @JavascriptInterface
        public void saveClass(final String classList) {

            runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    Intent intent = new Intent();
                    intent.putExtra("cjjyData", classList);
                    setResult(RESULT_OK, intent);
                    finish();
                }
            });
        }

        //法定检验的确定按钮
        @JavascriptInterface
        public void saveLegal(final String checkVal) {

            runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    Intent intent = new Intent();
                    intent.putExtra("fdjyData", checkVal);
                    setResult(RESULT_OK, intent);
                    finish();
                }
            });
        }


    }


    @Override
    protected void setListener() {


    }


    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK && web_view.canGoBack()) {
            web_view.goBack();// 返回前一个页面
            return true;
        } else {
            finish();
        }
        return super.onKeyDown(keyCode, event);
    }
}
