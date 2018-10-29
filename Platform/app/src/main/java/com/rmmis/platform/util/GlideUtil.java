package com.rmmis.platform.util;

import android.content.Context;

import android.widget.ImageView;



/**
 * 图片加载工具类
 */


import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;

import java.io.File;

/**
 * Description:
 * User: chenzheng
 * Date: 2016/8/31 0031
 * Time: 15:43
 */
public class GlideUtil {

    public static void loadImage(Context context, String url, int erroImg, int emptyImg, ImageView iv) {
        //原生 API
        Glide.with(context).load(url).placeholder(emptyImg).error(erroImg).into(iv);
    }

    public static void loadImage(Context context, String url, ImageView iv) {
        //原生 API
        Glide.with(context).load(url).crossFade().into(iv);
    }
//    public static void loadGifImage(Context context, String url, ImageView iv) {
//        Glide.with(context).load(url).asGif().diskCacheStrategy(DiskCacheStrategy.SOURCE).placeholder(R.mipmap.zwfm).error(R.mipmap.zwfm).into(iv);
//    }
    public static void loadImage(Context context, final File file, final ImageView imageView) {
        Glide.with(context)
                .load(file)
                .into(imageView);


    }
    public static void loadImage(Context context, final int resourceId, final ImageView imageView) {
        Glide.with(context)
                .load(resourceId)
                .into(imageView);
    }


}
