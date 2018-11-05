package com.rmmis.platform.base;


import android.content.Context;
import android.graphics.Bitmap;
import android.util.SparseArray;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;


/**
 * 通用的ViewHolder
 *
 * @author 李学祺
 */
public class ViewHolder {

    private SparseArray<View> mviews;
    private View mConverView;
    private int mPosition;
    private Context mContext;

    public ViewHolder(Context context, ViewGroup parent, int layoutId,
                      int position) {
        this.mviews = new SparseArray<View>();
        this.mPosition = position;
        this.mContext = context;

        mConverView = LayoutInflater.from(context).inflate(layoutId, parent,
                false);
        mConverView.setTag(this);

    }

    public static ViewHolder getHolder(View convertView, Context context,
                                       ViewGroup parent, int layoutId, int position) {

        if (convertView == null) {

            return new ViewHolder(context, parent, layoutId, position);
        } else {
            ViewHolder holder = (ViewHolder) convertView.getTag();
            holder.mPosition = position;
            return holder;
        }
    }

    /**
     * 通过viewId返回控件
     *
     * @param viewId
     * @return View
     */
    public <T extends View> T getView(int viewId) {

        View view = mviews.get(viewId);
        if (view == null) {
            view = mConverView.findViewById(viewId);
            mviews.put(viewId, view);
        }
        return (T) view;
    }

    /**
     * 给TextView设置文字
     *
     * @param viewId
     * @param text
     * @return ViewHolder
     */
    public ViewHolder setText(int viewId, String text) {
        TextView textView = getView(viewId);
        textView.setText(text);
        return this;
    }

    /**
     * 给ImageView设置图片
     *
     * @param viewId
     * @param resId
     * @return ViewHolder
     */
    public ViewHolder setImageResource(int viewId, int resId) {
        ImageView imageView = getView(viewId);
        imageView.setImageResource(resId);
        return this;
    }

    /**
     * 给ImageView设置图片
     *
     * @param viewId
     * @param bitmap
     * @return ViewHolder
     */
    public ViewHolder setImageBitmap(int viewId, Bitmap bitmap) {
        ImageView imageView = getView(viewId);
        imageView.setImageBitmap(bitmap);
        return this;
    }

    /**
     * 给ImageView设置图片
     *
     * @param viewId
     * @param uri
     * @return ViewHolder
     */
    public ViewHolder setImageUri(int viewId, String uri) {
        ImageView imageView = getView(viewId);


        return this;
    }

    public View getmConverView() {
        return mConverView;
    }

    /**
     * 给TextView设置颜色
     *
     * @param viewId
     * @return ViewHolder
     */
    public ViewHolder setTextColor(int viewId, int resId) {
        TextView textView = getView(viewId);
        textView.setTextColor(resId);
        return this;
    }

    public int getPosition() {
        return mPosition;
    }

}
