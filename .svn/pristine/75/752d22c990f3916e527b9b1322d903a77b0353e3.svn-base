package com.geetion.job138.adapter;

import java.util.List;

import com.geetion.job138.imagecache.ImageLoader;
import com.geetion.job138.imagecache.ImageLoader.onLoadingFinishListener;
import com.geetion.job138.widget.touchview.TouchImageView;
import com.liqi.job.R;


import android.app.Activity;
import android.content.Context;
import android.graphics.Bitmap;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ImageView;

public class ViewImageAdapter extends PagerAdapter {
	LayoutInflater infater;
	private Context context;
	private List<String> list;
	private List<String> smallList;
	private ImageLoader imageLoader;

	public ViewImageAdapter(Activity context, List<String> list,
			List<String> smallList) {
		infater = context.getLayoutInflater();
		this.context = context;
		this.list = list;
		if (smallList != null)
			this.smallList = smallList;
		imageLoader = new ImageLoader(context);
	}

	public void destroyItem(View arg0, int arg1, Object arg2) {
		((ViewPager) arg0).removeView(arg0.findViewWithTag("item" + arg1));
	}

	@Override
	public Object instantiateItem(View arg0, int position) {
		final View baseView = infater.inflate(R.layout.item_viewimage, null);
		ImageView imageView = (TouchImageView) baseView
				.findViewById(R.id.imageView1);
		String object = null;
		if (smallList.size() > 0) {
			object = smallList.get(position);
			imageLoader.DisplayImage(object, imageView, 0, 0, 0);
		}
		if (list.size() > 0) {
			object = list.get(position);
			imageLoader.DisplayImage(object, 0, 0, imageView,
					new onLoadingFinishListener() {
						@Override
						public void onFinish(Bitmap bitmap,
								ImageView imageView, List<View> list) {
							if (bitmap != null) {
								imageView.setImageBitmap(bitmap);
							}
							baseView.findViewById(R.id.progressBar1)
									.setVisibility(View.GONE);
						}
					}, null, -1);
		}
		imageView.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View arg0) {
				((Activity) context).finish();
			}
		});
		((ViewPager) arg0).addView(baseView, 0);
		return baseView;
	}

	@Override
	public boolean isViewFromObject(View baseView, Object arg1) {
		return baseView == (arg1);
	}

	@Override
	public int getCount() {
		return list.size();
	}
}
