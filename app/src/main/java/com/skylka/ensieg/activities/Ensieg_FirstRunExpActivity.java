package com.skylka.ensieg.activities;

import java.util.Timer;
import java.util.TimerTask;

import com.skylka.ensieg.R;
import com.skylka.ensieg.fragments.FirstRunImageViewFragment;
import com.skylka.ensieg.viewpagerindicator.CirclePageIndicator;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences.Editor;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v4.view.ViewPager.OnPageChangeListener;
import android.support.v7.app.AppCompatActivity;
import android.view.ViewGroup;

public class Ensieg_FirstRunExpActivity extends AppCompatActivity {
	private ViewPager mPager;
	private PagerAdapter mPagerAdapter;
	public static int NUM_PAGES_;
	private int currentpage = 0;
	private CirclePageIndicator mIndicator;
	Timer timer;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_firstrun);
		getSupportActionBar().hide();
		NUM_PAGES_ = 3;
		Editor edit = getSharedPreferences("Ensieg_sharedpreferences", Context.MODE_PRIVATE).edit();
		edit.putBoolean("FirstRun_status", true);
		edit.commit();
		getPager();

	}

	public void getPager() {
		try {
			mPager = (ViewPager) findViewById(R.id.pager_firstrun);
			mIndicator = (CirclePageIndicator) findViewById(R.id.indicator);

			mPagerAdapter = new ScreenSlidePagerAdapter(getSupportFragmentManager());
			mPager.setAdapter(mPagerAdapter);
			mPager.setCurrentItem(0);
			mIndicator.setViewPager(mPager);
			pageSwitcher(5);
			mPager.setOnPageChangeListener(new OnPageChangeListener() {
				@SuppressLint("NewApi")
				@Override
				public void onPageSelected(int position) {
					// currentPage = position;
					mIndicator.onPageSelected(position);
					// invalidateOptionsMenu();
				}

				@Override
				public void onPageScrollStateChanged(int state) {
					mIndicator.onPageScrollStateChanged(state);
				}

				@Override
				public void onPageScrolled(int arg0, float arg1, int arg2) {
					mIndicator.onPageScrolled(arg0, arg1, arg2);
				}
			});

		} catch (Exception e) {
			e.printStackTrace();
		}

	}

	public void pageSwitcher(int seconds) {
		timer = new Timer(); // At this line a new Thread will be created
		timer.scheduleAtFixedRate(new RemindTask(), 0, seconds * 1000); // delay
																		// in
		// milliseconds
	}

	@Override
	protected void onActivityResult(int arg0, int arg1, Intent arg2) {
		// TODO Auto-generated method stub
		super.onActivityResult(arg0, arg1, arg2);
		if (arg0 == 101) {
			currentpage = 0;
			mPager.setCurrentItem(currentpage);
			pageSwitcher(5);
		}
	}

	// this is an inner class...
	class RemindTask extends TimerTask {

		@Override
		public void run() {

			// As the TimerTask run on a seprate thread from UI thread we have
			// to call runOnUiThread to do work on UI thread.
			runOnUiThread(new Runnable() {
				public void run() {

					if (currentpage > 2) { // In my case the number of pages
											// are 5
						timer.cancel();
						startActivityForResult(
								new Intent(Ensieg_FirstRunExpActivity.this, Ensieg_WelcomeActivity.class), 101);
						// Showing a toast for just testing purpose

					} else {

						mPager.setCurrentItem(currentpage++, true);
					}
				}
			});

		}
	}

	private class ScreenSlidePagerAdapter extends FragmentPagerAdapter {
		@Override
		public void destroyItem(ViewGroup container, int position, Object object) {
			FragmentManager manager = ((Fragment) object).getFragmentManager();
			FragmentTransaction trans = manager.beginTransaction();
			trans.remove((Fragment) object);
			trans.commitAllowingStateLoss();
		}

		public ScreenSlidePagerAdapter(FragmentManager fm) {
			super(fm);
		}

		@Override
		public Fragment getItem(int position) {
			return FirstRunImageViewFragment.create(position);

		}

		@Override
		public int getCount() {
			return NUM_PAGES_;
		}
	}

}
