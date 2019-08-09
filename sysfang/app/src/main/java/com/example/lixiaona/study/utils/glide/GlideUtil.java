package com.example.lixiaona.study.utils.glide;

import android.app.Activity;
import android.content.Context;
import android.support.annotation.DimenRes;
import android.util.Log;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.example.lixiaona.study.R;
import com.example.lixiaona.study.utils.StringUtils;

/**
 * Glide 图片加载工具类
 */

/**
 * diskCacheStrategy参数补充
 * <p>
 * DiskCacheStrategy.NONE   表示不缓存任何内容。
 * <p>
 * DiskCacheStrategy.DATA   表示只缓存原始图片。
 * <p>
 * DiskCacheStrategy.RESOURCE   表示只缓存转换过后的图片。
 * <p>
 * DiskCacheStrategy.ALL    表示既缓存原始图片，也缓存转换过后的图片。
 * <p>
 * DiskCacheStrategy.AUTOMATIC  表示让Glide根据图片资源智能地选择使用哪一种缓存策略（默认选项）。
 */
public class GlideUtil {
    private static String TAG = "GlideUtil";
    //加载失败 、占位   图片
    private static final int errorImg = R.mipmap.ic_launcher;
    private static final int errorRoundImg = R.mipmap.ic_launcher;
    private static final int errorCircleImg = R.mipmap.ic_launcher;


    /**
     * 正常
     */
    public static void load(Context context, int res, ImageView imageView) {
        if (context instanceof Activity) {
            if (((Activity) context).isDestroyed())
                return;
        }

        Glide.with(context)
                .load(res)
                .into(imageView);
    }

    public static void load(Context context, String url, ImageView imageView) {
        if (context instanceof Activity) {
            if (((Activity) context).isDestroyed())
                return;
        }
        RequestOptions options = new RequestOptions()
                .error(errorImg)
                .placeholder(errorImg);

        Glide.with(context)
                .load(url)
                .apply(options)
                .into(imageView);
    }

    public static void load(Context context, String url, @DimenRes int wRes, ImageView imageView) {
        if (context instanceof Activity) {
            if (((Activity) context).isDestroyed())
                return;
        }
        int w;
        try {
            w = (int) context.getResources().getDimension(wRes);
        } catch (Exception e) {
            w = wRes;
        }

        RequestOptions options = new RequestOptions()
                .override(w)
                .error(errorImg)
                .placeholder(errorImg);

        Glide.with(context)
                .load(url)
                .apply(options)
                .into(imageView);
    }

    public static void load(Context context, String url, @DimenRes int wRes, @DimenRes int hRes, ImageView imageView) {
        if (context instanceof Activity) {
            if (((Activity) context).isDestroyed())
                return;
        }
        int w;
        try {
            w = (int) context.getResources().getDimension(wRes);
        } catch (Exception e) {
            w = wRes;
        }
        int h;
        try {
            h = (int) context.getResources().getDimension(hRes);
        } catch (Exception e) {
            h = wRes;
        }

        RequestOptions options = new RequestOptions()
                .override(w, h)
                .error(errorImg)
                .placeholder(errorImg);

        Glide.with(context)
                .load(url)
                .apply(options)
                .into(imageView);
    }

    /**
     * 圆形
     */
    public static void loadCircle(Context context, String url, ImageView imageView) {
        if (context instanceof Activity) {
            if (((Activity) context).isDestroyed())
                return;
        }
        if (StringUtils.isEmpty(url))
            return;
        RequestOptions options = new RequestOptions()
                .error(errorCircleImg)
                .placeholder(errorCircleImg)
                .transforms(new CircleTransform());
        Log.i(TAG, url);
        Glide.with(context)
                .load(url)
                .apply(options)
                .into(imageView);
    }

    public static void loadCircle(Context context, String url, @DimenRes int wRes, ImageView imageView) {
        if (context instanceof Activity) {
            if (((Activity) context).isDestroyed())
                return;
        }

        if (StringUtils.isEmpty(url)) {
            return;
        }
        int w;
        try {
            w = (int) context.getResources().getDimension(wRes);
        } catch (Exception e) {
            w = wRes;
        }

        RequestOptions options = new RequestOptions()
                .error(errorCircleImg)
                .placeholder(errorCircleImg)
                .override(w)
                .transforms(new CircleTransform());
        Log.i(TAG, url);
        Glide.with(context)
                .load(url)
                .apply(options)
                .into(imageView);
    }

    /**
     * 加载成圆角
     */
    public static void loadRound(Context context, String url, ImageView imageView) {
        if (context instanceof Activity) {
            if (((Activity) context).isDestroyed())
                return;
        }

        int r = (int) context.getResources().getDimension(R.dimen.dp5);
        RequestOptions options = new RequestOptions()
                .error(errorRoundImg)
                .placeholder(errorRoundImg)
                .centerCrop()
                .transforms(new RoundTransform(context, r));
        Log.i(TAG, url);
        Glide.with(context)
                .load(url)
                .apply(options)
                .into(imageView);
    }

    public static void loadRound(Context context, String url, @DimenRes int wRes, @DimenRes int hRes,
                                 ImageView imageView, @DimenRes int rRes) {
        if (context instanceof Activity) {
            if (((Activity) context).isDestroyed())
                return;
        }
        int w;
        try {
            w = (int) context.getResources().getDimension(wRes);
        } catch (Exception e) {
            w = wRes;
        }
        int h;
        try {
            h = (int) context.getResources().getDimension(hRes);
        } catch (Exception e) {
            h = wRes;
        }

        int r;
        try {
            r = (int) context.getResources().getDimension(rRes);
        } catch (Exception e) {
            r = rRes;
        }
        RequestOptions options = new RequestOptions()
                .override(w, h)
                .error(errorRoundImg)
                .placeholder(errorRoundImg)
                .transforms(new RoundTransform(context, r));

        Log.i(TAG, url);
        Glide.with(context)
                .load(url)
                .apply(options)
                .into(imageView);
    }

}
