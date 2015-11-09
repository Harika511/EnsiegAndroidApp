package com.skylka.ensieg.fragments;

import java.util.ArrayList;

import com.skylka.ensieg.R;
import com.skylka.ensieg.adapters.ContanctAdapter;
import com.skylka.ensieg.adapters.NonEnsiegContactsAdapter;
import com.skylka.ensieg.adapters.TimelineAdapter;
import com.skylka.ensieg.database.EnsiegDB;
import com.skylka.ensieg.model.ContactBean;
import com.skylka.ensieg.model.EventModel;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

public class NonEnsieg_ContactsFragment extends Fragment {
	ListView upcoming_listview;
	TextView nocards_tx;
	EnsiegDB database;
	ListView listView, listViewGroups;
	LinearLayout layout;

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

		View rootView = inflater.inflate(R.layout.fragment_contacts, container, false);
		// listView = (ListView) rootView.findViewById(R.id.list);
		// listViewGroups = (ListView)
		// rootView.findViewById(R.id.list_ensieg_groups);
		// // listView.setOnItemClickListener(this);
		// layout = (LinearLayout) rootView.findViewById(R.id.accepted_options);
		// layout.setVisibility(View.GONE);
		// listViewGroups.setVisibility(View.GONE);
		database = new EnsiegDB(getActivity());
		nocards_tx = (TextView) rootView.findViewById(R.id.no_contacts_text_up);
		upcoming_listview = (ListView) rootView.findViewById(R.id.contacts_listview);
		database.Open();
		ArrayList<ContactBean> list = database.getEnsiegNonContactsData();
		database.close();
		for (int i = 0; i < list.size(); i++) {
			ContactBean bean = list.get(i);
			Log.d("", " numbers " + bean.getName() + " " + bean.getPhoneNo());
		}
		if (list != null && list.size() > 0) {
			upcoming_listview.setVisibility(View.VISIBLE);
			nocards_tx.setVisibility(View.GONE);
			NonEnsiegContactsAdapter adapter = new NonEnsiegContactsAdapter(getActivity(), list);
			// ContanctAdapter adapter = new ContanctAdapter(getActivity(),
			// R.layout.alluser_row, list, "non_ensieg_contacts");
			upcoming_listview.setAdapter(adapter);
		} else {
			upcoming_listview.setVisibility(View.GONE);
			nocards_tx.setVisibility(View.VISIBLE);
			nocards_tx.setText("No Contacts ..");

		}
		return rootView;
	}
}
