package com.td.framework.global.helper;

import android.content.Context;
import android.graphics.Bitmap;
import android.net.Uri;
import android.support.annotation.DrawableRes;
import android.support.v4.graphics.drawable.RoundedBitmapDrawable;
import android.support.v4.graphics.drawable.RoundedBitmapDrawableFactory;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.Priority;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.bumptech.glide.request.RequestOptions;
import com.bumptech.glide.request.target.BitmapImageViewTarget;
import com.td.framework.R;

import java.io.File;


/**
 * <p>作者：jc on 2016/6/25 10:18</p>
 * <p>图片加载帮助类</p>
 */
public class ImageHelper {

    /**
     * 普通
     */
    private static RequestOptions options = new RequestOptions()
//            .centerCrop()
            .placeholder(R.drawable.image_loading)
            .error(R.drawable.image_loading)
            .priority(Priority.HIGH);

    /**
     * 没有占位图
     */
    private static RequestOptions noPlaceHolderOptions = new RequestOptions()
            .centerCrop()
            .priority(Priority.HIGH);

    /**
     * 圆角
     */
    private static RequestOptions roundOptions = new RequestOptions()
            .centerCrop()
            .placeholder(R.mipmap.default_header)
            .error(R.mipmap.default_header)
            .priority(Priority.HIGH);
    /**
     * 不缓存
     */
    private static RequestOptions optionsNoCache = new RequestOptions()
            .centerCrop()
            .placeholder(R.drawable.image_loading)
            .error(R.drawable.image_loading)
            .priority(Priority.HIGH)
            .diskCacheStrategy(DiskCacheStrategy.NONE);


    /**
     * Glide 加载图片
     *
     * @param mContext
     * @param imageView
     */
    public static void loadImageNoPlaceHolder(Context mContext, String url, ImageView imageView) {
        Glide.with(mContext)
                .load(url)
                .apply(noPlaceHolderOptions)
                .into(imageView);
    }


    /**
     * Glide 加载图片
     *
     * @param mContext
     * @param imageView
     */
    public static void loadImageFromGlide(Context mContext, String url, ImageView imageView) {
        Glide.with(mContext)
                .load(url)
                .apply(options)
                .into(imageView);
    }

    /**
     * Glide 加载图片
     *
     * @param mContext
     * @param imageView
     * @param defaultLoading 默认加载
     */
    public static void loadImageFromGlide(Context mContext, String url, ImageView imageView, @DrawableRes int defaultLoading) {

        Glide.with(mContext)
                .load(url)
                .apply(new RequestOptions()
                        .placeholder(defaultLoading)
                        .error(defaultLoading)
                        .priority(Priority.HIGH))
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
        Glide.with(mContext).load(Uri.parse(uri))
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
    public static void loadImageFromUri(Context mContext, String uri, ImageView imageView, @DrawableRes int defaultLoading) {
        Glide.with(mContext).load(Uri.parse(uri))
                .apply(new RequestOptions()
                        .placeholder(defaultLoading)
                        .error(defaultLoading)
                        .priority(Priority.HIGH))
                .into(imageView);
    }


    /**
     * 从URI中加载图像<br/>
     * 1.圆形的
     *
     * @param mContext
     * @param uri
     * @param imageView
     */
    public static void loadImageFromUriNoCache(final Context mContext, String uri, final ImageView imageView, @DrawableRes int defaultLoading) {

        Glide.with(mContext)
                .asBitmap()
                .load(Uri.parse(uri))
                .apply(new RequestOptions()
                        .centerCrop()
                        .placeholder(R.drawable.image_loading)
                        .error(R.drawable.image_loading)
                        .priority(Priority.HIGH)
                        .diskCacheStrategy(DiskCacheStrategy.NONE))
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
     * 从URI中加载图像<br/>
     * 1.圆形的
     *
     * @param mContext
     * @param uri
     * @param imageView
     */
    public static void loadImageFromUriNoCache(final Context mContext, String uri, final ImageView imageView) {

        Glide.with(mContext)
                .asBitmap()
                .load(Uri.parse(uri))
                .apply(optionsNoCache)
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
     * 加载圆形菜单
     *
     * @param mContext
     * @param uri
     * @param imageView
     */
    public static void loadRoundMenuImage(final Context mContext, String uri, final ImageView imageView) {
        if (uri.contains("android_asset")) {
            loadImageFromUriNoCache(mContext, uri, imageView);
        } else {
            Glide.with(mContext)
                    .asBitmap()
                    .load(uri)
                    .apply(optionsNoCache)
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
    }


    public static void loadImageFromGlide(Context mContext, File file, ImageView imageView) {
        Glide.with(mContext).load(file)
                .apply(options)
                .into(imageView);
    }

    public static void loadImageFromGlide(Context mContext, int resId, ImageView imageView) {
        Glide.with(mContext).load(resId)
                .apply(options)
                .into(imageView);
    }

    public static void loadImageFromGlide(Context mContext, int resId, ImageView imageView, @DrawableRes int defaultLoading) {
        Glide.with(mContext).load(resId)
                .apply(new RequestOptions()
                        .placeholder(defaultLoading)
                        .error(defaultLoading)
                        .priority(Priority.HIGH))
                .into(imageView);
    }

    public static void loadRoundImageByFile(final Context mContext, File file, final ImageView imageView) {

        Glide.with(mContext)
                .asBitmap()
                .load(file)
                .apply(roundOptions)
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
     * @param url
     * @param imageView
     */
    public static void loadRoundImage(final Context mContext,
                                      String url,
                                      @DrawableRes int defaultLoading,
                                      final ImageView imageView) {

        if (url == null || !url.startsWith("http")) {
            return;
        }

        Glide.with(mContext)
                .asBitmap()
                .load(url)
                .apply(new RequestOptions()
                        .centerCrop()
                        .placeholder(defaultLoading)
                        .error(defaultLoading)
                        .priority(Priority.HIGH))
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
     * @param url
     * @param imageView
     */
    public static void loadRoundImage(final Context mContext, String url,
                                      final ImageView imageView) {

        if (url == null || !url.startsWith("http")) {
            return;
        }

        Glide.with(mContext)
                .asBitmap()
                .load(url)
                .apply(roundOptions)
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

    public static void loadRoundImage(final Context mContext, int resid, final ImageView imageView) {
        Glide.with(mContext)
                .asBitmap()
                .load(resid)
                .apply(roundOptions)
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
    public static void loadRoundImage(final Context mContext,
                                      String url,
                                      final ImageView imageView, final int ra) {

        Glide.with(mContext)
                .asBitmap()
                .load(url)
                .apply(roundOptions)
                .into(new BitmapImageViewTarget(imageView) {
                    @Override
                    protected void setResource(Bitmap resource) {
                        RoundedBitmapDrawable circularBitmapDrawable =
                                RoundedBitmapDrawableFactory.create(mContext.getResources(), resource);
                        circularBitmapDrawable.setCornerRadius(ra);
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
