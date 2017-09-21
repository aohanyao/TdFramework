package com.td.framework.global.helper;

import android.content.Context;
import android.graphics.Bitmap;
import android.net.Uri;
import android.support.v4.graphics.drawable.RoundedBitmapDrawable;
import android.support.v4.graphics.drawable.RoundedBitmapDrawableFactory;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.bumptech.glide.request.target.BitmapImageViewTarget;
import com.td.framework.R;
import com.td.framework.utils.L;

import java.io.File;
import java.util.concurrent.ExecutionException;


/**
 * <p>作者：江俊超 on 2016/6/25 10:18</p>
 * <p>邮箱：aohanyao@gmail.com</p>
 * <p>图片加载帮助类</p>
 */
public class ImageHelper {
    /**
     * Glide 加载图片
     *
     * @param mContext
     * @param imageView
     */
    public static void loadImageFromGlide(Context mContext, String url, ImageView imageView) {
//        L.e(url);
        Glide.with(mContext).load(url)
                .error(R.drawable.image_loading)
                .placeholder(R.drawable.image_loading)
                .into(imageView);
    }

    public static Bitmap loadImageFromGlide(Context mContext, String url) {
        L.e(url);
        Bitmap myBitmap = null;
        try {
            myBitmap = Glide.with(mContext)
                    .load(url)
                    .asBitmap() //must
                    .error(R.drawable.image_loading)
                    .placeholder(R.drawable.image_loading)
                    .centerCrop()
                    .into(150, 150)
                    .get();
        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (ExecutionException e) {
            e.printStackTrace();
        }

        return myBitmap;

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
                .error(R.drawable.image_loading)
                .placeholder(R.drawable.image_loading)
                .into(imageView);
    }

    public static void loadImageFromUriNoCache(Context mContext, String uri, ImageView imageView) {
//        L.e(uri);
        Glide.with(mContext).load(Uri.parse(uri))
                .error(R.drawable.image_loading)
                .diskCacheStrategy(DiskCacheStrategy.NONE)
                .skipMemoryCache(true)
                .placeholder(R.drawable.image_loading)
                .into(imageView);
    }

    public static void loadMenuImage(Context mContext, String uri, ImageView imageView) {
//        L.e(uri);
        if (uri.contains("android_asset")) {
            loadImageFromUriNoCache(mContext, uri, imageView);
        } else {
            Glide.with(mContext).load(uri)
                    .error(R.drawable.image_loading)
//                    .diskCacheStrategy(DiskCacheStrategy.NONE)
//                    .skipMemoryCache(true)
                    .placeholder(R.drawable.image_loading)
                    .into(imageView);
        }
    }

    public static void loadImageFromFile(Context mContext, File file, ImageView imageView) {
        Glide.with(mContext).load(file)
                .error(R.drawable.image_loading)
                .placeholder(R.drawable.image_loading)
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
                .load(url)
                .asBitmap()
                .error(R.mipmap.default_header)
                .placeholder(R.mipmap.default_header)
                .centerCrop()
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
                .load(resid)
                .asBitmap()
                .error(R.mipmap.default_header)
                .placeholder(R.mipmap.default_header)
                .centerCrop()
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
                .load(file)
                .asBitmap()
                .centerCrop()
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
