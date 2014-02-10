package com.geetion.job138.util;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Locale;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.pm.ApplicationInfo;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.content.pm.PackageManager.NameNotFoundException;
import android.content.pm.ResolveInfo;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Rect;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.View;
import android.view.View.MeasureSpec;
import android.view.Window;
import android.view.WindowManager;


public class AndroidUtil {

	private static final String LOGTAG = "AndroidUtil";

	/**
	 * 判断系统语言 例如：返回en-US(也可能是en-XX)表示英语,zh-CN表示�?��中文,zh-TW表示繁体中文,以此类推�?
	 * 
	 * @return
	 */
	public static String getLocaleLanguage() {
		Locale l = Locale.getDefault();
		return String.format("%s-%s", l.getLanguage(), l.getCountry());
	}
	
	/**
	 *屏幕截图 
	 */
	public static Bitmap takeScreenShot(Activity activity){ 
        View view =activity.getWindow().getDecorView(); 
        view.setDrawingCacheEnabled(true); 
        view.buildDrawingCache(); 
        Bitmap bitmap = view.getDrawingCache(); 
        Rect rect = new Rect(); 
        activity.getWindow().getDecorView().getWindowVisibleDisplayFrame(rect); 
        int statusBarHeight = rect.top; 
        int width = activity.getWindowManager().getDefaultDisplay().getWidth(); 
        int height = activity.getWindowManager().getDefaultDisplay().getHeight(); 
        Bitmap bitmap2 = Bitmap.createBitmap(bitmap,0,statusBarHeight, width, height - statusBarHeight); 
        view.destroyDrawingCache(); 
        return bitmap2; 
    } 
	
	/**
	 * 把View绘制到Bitmap�?
	 * @param view
	 * @return
	 */
	public static Bitmap convertViewToBitmap(View view) {
		view.measure(MeasureSpec.makeMeasureSpec(0, MeasureSpec.UNSPECIFIED),
				MeasureSpec.makeMeasureSpec(0, MeasureSpec.UNSPECIFIED));
		view.layout(0, 0, view.getMeasuredWidth(), view.getMeasuredHeight());
		view.buildDrawingCache();
		Bitmap bitmap = view.getDrawingCache();
		return bitmap;
	}

	/**
	 * 把View绘制到Bitmap�?
	 * 
	 * @param view
	 *            �?��绘制的View
	 * @param width
	 *            该View的宽�?
	 * @param height
	 *            该View的高�?
	 * @return 返回Bitmap对象
	 */
	public static Bitmap getBitmapFromView(View view, int width, int height) {
		int widthSpec = View.MeasureSpec.makeMeasureSpec(width,
				View.MeasureSpec.EXACTLY);
		int heightSpec = View.MeasureSpec.makeMeasureSpec(height,
				View.MeasureSpec.EXACTLY);
		view.measure(widthSpec, heightSpec);
		view.layout(0, 0, width, height);
		Bitmap bitmap = Bitmap.createBitmap(width, height,
				Bitmap.Config.RGB_565);
		Canvas canvas = new Canvas(bitmap);
		view.draw(canvas);

		return bitmap;
	}

	/**
	 * 去除标题
	 * 
	 * @param Activity
	 *            act
	 */
	public static void setFullNoTitleScreen(Activity act) {
		act.setTheme(android.R.style.Theme_Black_NoTitleBar_Fullscreen);
		act.requestWindowFeature(Window.FEATURE_NO_TITLE);
		act.getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
				WindowManager.LayoutParams.FLAG_FULLSCREEN);
	}

	/**
	 * 释放bmp资源
	 * 
	 * @param bmp
	 */
	public static void recycleBmp(Bitmap bmp) {
		if (null != bmp && !bmp.isRecycled()) {
			bmp.recycle();
			Log.d(LOGTAG, "=======recycleBmp ======");
		}
	}

	/**
	 * 
	 * @param context
	 * @return
	 */
	public static DisplayMetrics getDisplayMetrics(Context context) {
		return context.getApplicationContext().getResources()
				.getDisplayMetrics();
	}

	public static int dpToPx(int dp, Context context) {
		return (int) (context.getResources().getDisplayMetrics().density * dp + 0.5f);
	}

	public static float spToPx(int dp, Context context) {
		return (context.getResources().getDisplayMetrics().density * dp + 0.5f);
	}
	public static int dpToPx(float dp, Context context) {
		return (int) (context.getResources().getDisplayMetrics().density * dp + 0.5f);
	}


	/**
	 * 根据设备和包名判断是否已安装应用
	 * 
	 * @param context
	 *            上下�?
	 * @param packageName
	 *            包名
	 * @return
	 */
	public boolean checkApkExist(Context context, String packageName) {
		if (packageName == null || "".equals(packageName))
			return false;
		try {
			ApplicationInfo info = context.getPackageManager()
					.getApplicationInfo(packageName,
							PackageManager.GET_UNINSTALLED_PACKAGES);
			return true;
		} catch (NameNotFoundException e) {
			return false;
		}
	}
	
	/**
	 * 获取版本�?
	 * @param context
	 * @return
	 */
	public static String getApplicationVersion(Context context) {
		try {
			return context.getApplicationContext().getPackageManager()
					.getPackageInfo(context.getPackageName(), 0).versionName;
		} catch (NameNotFoundException e) {
			Log.e("NameNotFoundException", e.getMessage());
		}
		return "";
	}

	/**
	 * 根据包名打开APP
	 */
	public static void openApp(String packageName, Context context) {
		PackageInfo pi = null;
		try {
			pi = context.getPackageManager().getPackageInfo(packageName, 0);
		} catch (NameNotFoundException e) {
			e.printStackTrace();
		}
		Intent resolveIntent = new Intent(Intent.ACTION_MAIN, null);
		resolveIntent.addCategory(Intent.CATEGORY_LAUNCHER);
		resolveIntent.setPackage(pi.packageName);
		List<ResolveInfo> apps = context.getPackageManager()
				.queryIntentActivities(resolveIntent, 0);
		ResolveInfo ri = apps.iterator().next();
		if (ri != null) {
			String packageName2 = ri.activityInfo.packageName;
			String className = ri.activityInfo.name;
			Intent intent = new Intent(Intent.ACTION_MAIN);
			intent.addCategory(Intent.CATEGORY_LAUNCHER);
			ComponentName cn = new ComponentName(packageName2, className);
			intent.setComponent(cn);
			context.startActivity(intent);

		}
	}

	@SuppressLint("SimpleDateFormat")
	public static String formatDateTime(String datetime) {
		String result = "";
		DateFormat format = new SimpleDateFormat("yyyy-MM-dd");
		try {
			Date date = format.parse(datetime);
			result = format.format(date);
		} catch (ParseException e) {
			e.printStackTrace();
		}
		return result;
	}
	
	
}
