package com.geetion.job138.activity;

import java.util.ArrayList;
import java.util.List;
import com.geetion.job138.adapter.ViewImageAdapter;
import com.liqi.job.R;

import android.app.Activity;
import android.os.Bundle;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v4.view.ViewPager.OnPageChangeListener;
import android.widget.TextView;

public class ViewImageActivity extends Activity {
	private ViewPager viewPaper;
	private PagerAdapter adapter;
	private List<String> list = new ArrayList<String>();
	private List<String> smallList = new ArrayList<String>();
	private TextView pageText;
	public static String IMAGE = "image", SMALL_IMAGE = "small",
			POSITION = "position";

	@Override
	protected void onCreate(Bundle arg0) {
		super.onCreate(arg0);
		setContentView(R.layout.activity_viewimages);
		initView();
		initData();
	}

	private void initView() {
		viewPaper = (ViewPager) findViewById(R.id.text_view_paper);
		pageText = (TextView) findViewById(R.id.textView1);
		adapter = new ViewImageAdapter(this, list, smallList);
		viewPaper.setAdapter(adapter);
		viewPaper.setOnPageChangeListener(new OnPageChangeListener() {
			@Override
			public void onPageSelected(int arg0) {
				pageText.setText((arg0 + 1) + "/" + list.size());
			}

			@Override
			public void onPageScrolled(int arg0, float arg1, int arg2) {
				// TODO Auto-generated method stub

			}

			@Override
			public void onPageScrollStateChanged(int arg0) {
				// TODO Auto-generated method stub

			}
		});
	}

	private void initData() {
		List<String> image = getIntent().getStringArrayListExtra(IMAGE);
		List<String> small = getIntent()
				.getStringArrayListExtra(SMALL_IMAGE);
		int position = getIntent().getIntExtra(POSITION, 0);
		if (image != null) {
			list.addAll(image);
			pageText.setText("1/" + list.size());
			adapter.notifyDataSetChanged();
			viewPaper.setCurrentItem(position);
		}
		if (small != null) {
			smallList.addAll(small);
		}
	}
}
