package com.example.lixiaona.study.utils;

import android.app.Activity;
import android.content.Context;
import android.content.res.Resources;
import android.util.DisplayMetrics;
import android.view.WindowManager;


public class DisplayUtilone {
	public static float getDensity(Context context) {
		Resources resources = context.getResources();
		DisplayMetrics dm = resources.getDisplayMetrics();
		return dm.density;
	}

	public static int getDensityWdith(Context context) {
		Resources resources = context.getResources();
		DisplayMetrics dm = resources.getDisplayMetrics();
		return dm.widthPixels;
	}

	public static int getDensityHeight(Context context) {
		Resources resources = context.getResources();
		DisplayMetrics dm = resources.getDisplayMetrics();
		return dm.heightPixels;
	}

	// 1.代码中设置setXXSize的都是px单位，都需要把布局中的dp，sp转成px才能使用
	/**
	 * 根据手机分辨率从 px(像素) 单位 转成 dp
	 */
	public static int px2dip(Context context, float pxValue) {
		final float scale = context.getResources().getDisplayMetrics().density;
		return (int) (pxValue / scale + 0.5f);
	}

	/**
	 * 根据手机分辨率从 dp 单位 转成 px(像素)
	 */
	public static int dip2px(Context context, float dpValue) {
		final float scale = context.getResources().getDisplayMetrics().density;
		return (int) (dpValue * scale + 0.5f);
	}

	/**
	 * 将px值转换为sp值，保证文字大小不变
	 * 
	 * @param pxValue
	 * @param
	 *            （DisplayMetrics类中属性scaledDensity）
	 * @return
	 */
	public static int px2sp(Context context, float pxValue) {
		final float fontScale = context.getResources().getDisplayMetrics().scaledDensity;
		return (int) (pxValue / fontScale + 0.5f);
	}

	/**
	 * 将sp值转换为px值，保证文字大小不变
	 * 
	 * @param spValue
	 * @param
	 *            （DisplayMetrics类中属性scaledDensity）
	 * @return
	 */
	public static int sp2px(Context context, float spValue) {
		final float fontScale = context.getResources().getDisplayMetrics().scaledDensity;
		return (int) (spValue * fontScale + 0.5f);
	}

	public static int getWidth(Context context, int liangbianjuli, int num,
			int picjuli) {
		// 屏幕宽度-两边的距离-图片中间的距离 除以列数
		return (getDensityWdith(context)
				- DisplayUtilone.dip2px(context, liangbianjuli) * 2 - dip2px(
				context, picjuli) * (num - 1))
				/ num;
	}
	public static int getWidth(Context context, int percent) {
		// 获取屏幕的高度
		return Integer.parseInt((getDensityWdith(context)*percent)/100+"");
	}
	public static int getHeight(Context context, int fenmu) {
		// 获取屏幕的高度
		return (getDensityHeight(context) / fenmu);
	}

	public static void backgroundAlpha(Context context, float bgAlpha) {
		WindowManager.LayoutParams lp = ((Activity) context).getWindow()
				.getAttributes();
		//dialog 放开注释
//		((Activity) context).getWindow().addFlags(
//				WindowManager.LayoutParams.FLAG_DIM_BEHIND);
		lp.alpha = bgAlpha; // 0.0-1.0
		((Activity) context).getWindow().setAttributes(lp);
	}



}
