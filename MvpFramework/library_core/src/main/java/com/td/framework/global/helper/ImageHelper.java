package com.td.framework.global.helper;

import android.content.Context;
import android.graphics.Bitmap;
import android.net.Uri;
import android.support.v4.graphics.drawable.RoundedBitmapDrawable;
import android.support.v4.graphics.drawable.RoundedBitmapDrawableFactory;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.Priority;
import com.bumptech.glide.request.RequestOptions;
import com.bumptech.glide.request.target.BitmapImageViewTarget;
import com.td.framework.R;
import com.td.framework.utils.L;

import java.io.File;


/**
 * <p>作者：jc on 2016/6/25 10:18</p>
 * <p>邮箱：aohanyao@gmail.com</p>
 * <p>图片加载帮助类</p>
 */
public class ImageHelper {

    private static RequestOptions options = new RequestOptions()
            .centerCrop()
            .placeholder(R.drawable.image_loading)
            .error(R.drawable.image_loading)
            .priority(Priority.HIGH);
    ;

    /**
     * Glide 加载图片
     *
     * @param mContext
     * @param imageView
     */
    public static void loadImageFromGlide(Context mContext, String url, ImageView imageView) {

//        L.e(url);
        Glide.with(mContext).load(url)
                .apply(options)
                .into(imageView);
    }


    /**
     * 从URI中加载图片
     *
     * @param mContext
     * @param uri
     * @param imageView
     */
    public static void loadImageFromUri(Context mContext, String uri, ImageView imageView) {
        L.e(uri);
        Glide.with(mContext).load(Uri.parse(uri))
                .apply(options)
                .into(imageView);
    }

    public static void loadImageFromUriNoCache(Context mContext, String uri, ImageView imageView) {
//        L.e(uri);
        Glide.with(mContext).load(Uri.parse(uri))
                .apply(options)
                .into(imageView);
    }

    public static void loadMenuImage(Context mContext, String uri, ImageView imageView) {
//        L.e(uri);
        if (uri.contains("android_asset")) {
            loadImageFromUriNoCache(mContext, uri, imageView);
        } else {
            Glide.with(mContext).load(uri)
                    .apply(options)
                    .into(imageView);
        }
    }

    public static void loadImageFromGlide(Context mContext, File file, ImageView imageView) {
        Glide.with(mContext).load(file)
                .apply(options)
                .into(imageView);
    }

    /**
     * 加载圆形的图像
     *
     * @param mContext
     * @param url
     * @param imageView
     */
    public static void loadShopRoundImage(final Context mContext, String url, final ImageView imageView) {


        Glide.with(mContext)
                .asBitmap()
                .load(url)
                .apply(options)
                .into(new BitmapImageViewTarget(imageView) {
                    @Override
                    protected void setResource(Bitmap resource) {
                        RoundedBitmapDrawable circularBitmapDrawable =
                                RoundedBitmapDrawableFactory.create(mContext.getResources(), resource);
                        circularBitmapDrawable.setCircular(true);
                        imageView.setImageDrawable(circularBitmapDrawable);
                    }
                });
    }

    public static void loadShopRoundImage(final Context mContext, int resid, final ImageView imageView) {
        Glide.with(mContext)
                .asBitmap()
                .load(resid)
                .apply(options)
                .into(new BitmapImageViewTarget(imageView) {
                    @Override
                    protected void setResource(Bitmap resource) {
                        RoundedBitmapDrawable circularBitmapDrawable =
                                RoundedBitmapDrawableFactory.create(mContext.getResources(), resource);
                        circularBitmapDrawable.setCircular(true);
                        imageView.setImageDrawable(circularBitmapDrawable);
                    }
                });
    }

    /**
     * 加载圆形的图像
     *
     * @param mContext
     * @param imageView
     */
    public static void loadShopRoundImage(final Context mContext, File file, final ImageView imageView) {
        Glide.with(mContext)
                .asBitmap()
                .load(file)
                .apply(options)
                .into(new BitmapImageViewTarget(imageView) {
                    @Override
                    protected void setResource(Bitmap resource) {
                        RoundedBitmapDrawable circularBitmapDrawable =
                                RoundedBitmapDrawableFactory.create(mContext.getResources(), resource);
                        circularBitmapDrawable.setCircular(true);
                        imageView.setImageDrawable(circularBitmapDrawable);
                    }
                });
    }


    /**
     * 清除Glide缓存
     *
     * @param mContext
     */
    public static void clearGlideMoneryCache(Context mContext) {
        if (mContext != null) {
            Glide.get(mContext).clearMemory();
        }
    }
}
