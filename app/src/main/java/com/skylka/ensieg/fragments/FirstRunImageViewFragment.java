package com.skylka.ensieg.fragments;

import com.skylka.ensieg.R;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

public class FirstRunImageViewFragment extends Fragment {
	public static final String ARG_PAGE = "page";
	static int page_no;
	private int mPageNumber;

	int[] image_arr = { R.drawable.sc_1, R.drawable.sc_2, R.drawable.sc_3 };

	public static FirstRunImageViewFragment create(int pageNumber) {
		FirstRunImageViewFragment fragment = new FirstRunImageViewFragment();
		page_no = pageNumber;
		Bundle args = new Bundle();
		args.putInt(ARG_PAGE, pageNumber);
		fragment.setArguments(args);
		return fragment;
	}

	public FirstRunImageViewFragment() {
	}

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		mPageNumber = getArguments().getInt(ARG_PAGE);

	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
		ViewGroup rootView = (ViewGroup) inflater.inflate(R.layout.fragment_firstrun, container, false);
		ImageView img = (ImageView) rootView.findViewById(R.id.first_run_img);
		img.setImageResource(image_arr[mPageNumber]);

		return rootView;
	}

}
