package com.skylka.ensieg.adapters;

import com.skylka.ensieg.activities.Ensieg_HomeActivity;
import com.skylka.ensieg.fragments.CreateFragment;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

public class TabsPagerAdapter1 extends FragmentPagerAdapter {

	public TabsPagerAdapter1(FragmentManager fm) {
		super(fm);
	}

	@Override
	public Fragment getItem(int index) {

		return new CreateFragment();

	}

	@Override
	public int getCount() {
		// get item count - equal to number of tabs
		return Ensieg_HomeActivity.NUMBER_OF_PAGES;
	}

}
