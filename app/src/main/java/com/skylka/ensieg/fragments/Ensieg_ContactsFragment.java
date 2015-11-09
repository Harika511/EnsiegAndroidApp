package com.skylka.ensieg.fragments;

import java.util.ArrayList;

import com.skylka.ensieg.R;
import com.skylka.ensieg.adapters.ContanctAdapter;
import com.skylka.ensieg.adapters.TimelineAdapter;
import com.skylka.ensieg.database.EnsiegDB;
import com.skylka.ensieg.model.ContactBean;
import com.skylka.ensieg.model.EventModel;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;

public class Ensieg_ContactsFragment extends Fragment {
	ListView upcoming_listview;
	TextView nocards_tx;
	EnsiegDB database;
    ListView listView,listViewGroups;
    LinearLayout layout;
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

		View rootView = inflater.inflate(R.layout.fragment_contacts, container, false);
		database = new EnsiegDB(getActivity());
		nocards_tx = (TextView) rootView.findViewById(R.id.no_contacts_text_up);
		upcoming_listview = (ListView) rootView.findViewById(R.id.contacts_listview);
		database.Open();
		ArrayList<ContactBean> list = database.getEnsiegContactsData();
		database.close();
		if (list != null && list.size() > 0) {
			upcoming_listview.setVisibility(View.VISIBLE);
			nocards_tx.setVisibility(View.GONE);
			ContanctAdapter adapter = new ContanctAdapter(getActivity(), R.layout.alluser_row,list,"contacts");
			upcoming_listview.setAdapter(adapter);
		} else {
			upcoming_listview.setVisibility(View.GONE);
			nocards_tx.setVisibility(View.VISIBLE);
			nocards_tx.setText("No Ensieg Contacts ..");

		}
		return rootView;
	}
}
