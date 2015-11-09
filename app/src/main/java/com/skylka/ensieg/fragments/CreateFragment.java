package com.skylka.ensieg.fragments;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

import com.google.gson.Gson;
import com.skylka.ensieg.R;
import com.skylka.ensieg.activities.Ensieg_MatchCenter;
import com.skylka.ensieg.constants.SportsUtilities;
import com.skylka.ensieg.database.EnsiegDB;
import com.skylka.ensieg.model.EventModel;
import com.skylka.ensieg.model.GameModel;
import com.skylka.ensieg.model.TeamModel;

import android.content.Context;
import android.content.Intent;
import android.content.res.Resources;
import android.graphics.Canvas;
import android.graphics.Typeface;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.Html;
import android.util.Log;
//github.com/blackgoogle/ENSIEG-AndroidDev.git
import android.util.TypedValue;
import android.view.GestureDetector;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnTouchListener;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

public class CreateFragment extends Fragment {

	RecyclerView feed_listview;
	TextView nocards_tx;
	private int mPreviousVisibleItem;
	ArrayList<GameModel> favoriate_list;
	public static final String ARG_PAGE = "page";
	private int mPageNumber;
	int firstVisibleItem, visibleItemCount, totalItemCount;
	private int previousTotal = 0;
	ArrayList<EventModel> list;

	public CreateFragment() {

	}

	public static CreateFragment create(int pageNumber) {
		CreateFragment fragment = new CreateFragment();
		Bundle args = new Bundle();
		args.putInt(ARG_PAGE, pageNumber);
		fragment.setArguments(args);
		return fragment;
	}

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		if (getArguments() != null) {
			mPageNumber = getArguments().getInt(ARG_PAGE);
		}
		/*
		 * conMgr = (ConnectivityManager) getActivity().getSystemService(
		 * Context.CONNECTIVITY_SERVICE);
		 */

	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

		View rootView = inflater.inflate(R.layout.fragment_create, container, false);
		EnsiegDB database = new EnsiegDB(getActivity());
		feed_listview = (RecyclerView) rootView.findViewById(R.id.feed_list);
		nocards_tx = (TextView) rootView.findViewById(R.id.no_cards_text);
		favoriate_list = new ArrayList<GameModel>();
		// UserProfileDb profile = new
		// UserProfileDb(getActivity().getApplicationContext());
		database.Open();
		ArrayList<String> allProfileData = database.getAllProfileData();
		database.close();

		int count = 4;
		if (!(favoriate_list.size() > 0)) {
			for (int i = 0; i < 7; i++) {
				if (allProfileData.get(count).toString().equalsIgnoreCase("yes")) {
					favoriate_list.add(SportsUtilities.sport_list.get(i));
				}
				count++;
			}
		}
		if (favoriate_list.size() > 1) {

			GameModel bean = new GameModel();
			bean.setGame_name("All");
			bean.setSportID(0);
			bean.setGame_small_image(R.drawable.all_img);
			bean.setGame_big_image(R.drawable.all_img);
			bean.setGame_image(R.drawable.all_img);
			favoriate_list.add(0, bean);
		}

		database.Open();
		list = database.getCardsFromDB(favoriate_list.get(mPageNumber).getSportID());
		database.close();
	//	Log.d("", " list data " + new Gson().toJson(list));
		if (list != null && list.size() > 0) {
			feed_listview.setVisibility(View.VISIBLE);
			nocards_tx.setVisibility(View.GONE);

			// GameFeedAdapter adapter = new GameFeedAdapter(getActivity(),
			// list);
			LinearLayoutManager lm = new LinearLayoutManager(getActivity());
			feed_listview.setLayoutManager(lm);
			GameAdapter recyclerAdapter = new GameAdapter(list);
			feed_listview.setAdapter(recyclerAdapter);
			//feed_listview.addItemDecoration(new SimpleDividerItemDecoration(getContext()));
			final GestureDetector mGestureDetector = new GestureDetector(getActivity(),
					new GestureDetector.SimpleOnGestureListener() {

						@Override
						public boolean onSingleTapUp(MotionEvent e) {
							return true;
						}

					});

			/*
			 * feed_listview.setOnScrollListener(new
			 * MyScrollListener(getActivity().getApplicationContext(), lm) {
			 * public void onMoved(int distance) { onMoved(distance); } });
			 */

		} else {
			feed_listview.setVisibility(View.GONE);
			nocards_tx.setVisibility(View.VISIBLE);
		}

		/*
		 * feed_listview.setOnScrollListener(new OnScrollListener() {
		 * 
		 * @Override public void onScrollStateChanged(AbsListView view, int
		 * scrollState) { // TODO Auto-generated method stub
		 * 
		 * }
		 * 
		 * @Override public void onScroll(AbsListView view, int
		 * firstVisibleItem, int visibleItemCount, int totalItemCount) {
		 * 
		 * if (firstVisibleItem > mPreviousVisibleItem) { fab.hide(true);
		 * 
		 * // ((AppCompatActivity) //
		 * getActivity()).getSupportActionBar().hide();
		 * 
		 * } else if (firstVisibleItem < mPreviousVisibleItem) { fab.show(true);
		 * // ((AppCompatActivity) //
		 * getActivity()).getSupportActionBar().show();
		 * 
		 * } mPreviousVisibleItem = firstVisibleItem;
		 * 
		 * } });
		 */
		/*
		 * feed_listview.setOnScrollListener(new
		 * MyScrollListener(getActivity().getApplicationContext()) {
		 * 
		 * @Override public void onMoved(int distance) { AppBarLayout toolbar =
		 * (AppBarLayout) getActivity().findViewById(R.id.appLayout);
		 * 
		 * toolbar.setTranslationY(-distance); } });
		 */

		/*
		 * fab.setOnClickListener(new View.OnClickListener() {
		 * 
		 * @Override public void onClick(View v) {
		 * 
		 * if (favoriate_list.size() > 1) { Intent intent = new
		 * Intent(getActivity(), Ensieg_PreHostAGameActivity.class);
		 * favoriate_list.remove(0); intent.putExtra("favoriate_list",
		 * favoriate_list); startActivity(intent); } else { Intent intent1 = new
		 * Intent(getActivity(), Ensieg_HostAGameActivity.class);
		 * intent1.putExtra("favoriate_list", favoriate_list);
		 * startActivity(intent1); } } });
		 */

		/*
		 * feed_listview.setOnItemClickListener(new OnItemClickListener() {
		 * 
		 * @Override public void onItemClick(AdapterView<?> parent, View view,
		 * int position, long id) {
		 * 
		 * EventModel eventModel = list.get(position); ArrayList<String> alEvent
		 * = new ArrayList<String>(); ArrayList<TeamModel> team_list =
		 * eventModel.getTeam_list(); TeamModel teamModel1 = team_list.get(0);
		 * TeamModel teamModel2 = team_list.get(1);
		 * alEvent.add(eventModel.getCommentcount());// -->0
		 * alEvent.add(eventModel.getNameofhost());// -->1
		 * alEvent.add(eventModel.getDate());// -->2
		 * alEvent.add(eventModel.getVenue());// -->3
		 * alEvent.add(eventModel.getHost_photo());// -->4
		 * alEvent.add(eventModel.getEventId());// -->5
		 * alEvent.add(eventModel.getSportsId());// -->6
		 * alEvent.add(eventModel.getTypeofEvent());// -->7
		 * alEvent.add(teamModel1.getTeamId());// -->8
		 * alEvent.add(teamModel2.getTeamId());// -->9 Log.d("",
		 * "teamModels are " + teamModel1.getTeamId() + " " +
		 * teamModel2.getTeamId() + " " + eventModel.getTypeofEvent()); Intent
		 * match = new Intent(getActivity(), Ensieg_MatchCenter.class);
		 * match.putStringArrayListExtra("event", alEvent);
		 * startActivity(match);
		 * 
		 * } });
		 */

		/*	*/
		return rootView;

	}

	public static int getPixelValue(CreateFragment createFragment, float f) {
		Resources resources = createFragment.getResources();
		return (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, f, resources.getDisplayMetrics());
	}

	public class GameViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
		TextView chat_count, host_name, game_versers_tx, date_time_tx, location_tx, open_places_tx;
		ImageView host_img, game_img;
		int positon;

		public GameViewHolder(View v, int i) {
			super(v);
			// v.setTag(i + "");
			chat_count = (TextView) v.findViewById(R.id.chat_count);
			host_name = (TextView) v.findViewById(R.id.host_name);
			game_versers_tx = (TextView) v.findViewById(R.id.game_versers_tx);
			date_time_tx = (TextView) v.findViewById(R.id.date_time_tx);
			location_tx = (TextView) v.findViewById(R.id.location_tx);
			open_places_tx = (TextView) v.findViewById(R.id.open_places_tx);
			host_img = (ImageView) v.findViewById(R.id.host_profile_img);
			game_img = (ImageView) v.findViewById(R.id.game_image);
			v.setOnClickListener(this);
		}

		@Override
		public void onClick(View v) {
			System.out.println("view ID>>>>>>>>>>>>>>>>" + getPosition());
			goToMatchCenter(getPosition());

		}
	}

	public void goToMatchCenter(int position) {
		EventModel eventModel = list.get(position);
		ArrayList<String> alEvent = new ArrayList<String>();
		ArrayList<TeamModel> team_list = eventModel.getTeam_list();
		TeamModel teamModel1 = team_list.get(0);
		TeamModel teamModel2 = team_list.get(1);
		alEvent.add(eventModel.getCommentcount());// -->0
		alEvent.add(eventModel.getNameofhost());// -->1
		alEvent.add(eventModel.getDate());// -->2
		alEvent.add(eventModel.getVenue());// -->3
		alEvent.add(eventModel.getHost_photo());// -->4
		alEvent.add(eventModel.getEventId());// -->5
		alEvent.add(eventModel.getSportsId());// -->6
		alEvent.add(eventModel.getTypeofEvent());// -->7
		alEvent.add(teamModel1.getMaxplayer());//-->8
		alEvent.add(teamModel1.getTeamId());// -->9
		alEvent.add(teamModel2.getTeamId());// -->10
//		Log.d("", "teamModels are " + teamModel1.getTeamId() + " " + teamModel2.getTeamId() + " "
//				+ eventModel.getTypeofEvent());
		Intent match = new Intent(getActivity(), Ensieg_MatchCenter.class);
		match.putStringArrayListExtra("event", alEvent);
		startActivity(match);
	}

	/*
	 * public void setOnItemClickListener(MyClickListener myClickListener) {
	 * this.myClickListener = myClickListener; }
	 */
	public interface MyClickListener {
		public void onItemClick(int position, View v);
	}

	public class GameAdapter extends RecyclerView.Adapter<GameViewHolder> {

		Typeface font;
		ArrayList<EventModel> list;
		SimpleDateFormat fmt = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");
		SimpleDateFormat fmtOut = new SimpleDateFormat("dd MMM  hh:mm aa");

		public GameAdapter(ArrayList<EventModel> list) {
			this.list = list;
		}

		@Override
		public int getItemCount() {
			return list.size();
		}

		@Override
		public void onBindViewHolder(GameViewHolder contactViewHolder, int position) {
			// contactViewHolder.host_img.setImageResource(R.drawable.default_profile);
			/* } */
//			Log.d("", "position is  " + position);
			contactViewHolder.chat_count.setText(list.get(position).getCommentcount());

			String host = "<html><b>" + list.get(position).getNameofhost() + "</b></html>" + " is hosting game";
			contactViewHolder.host_name.setText(Html.fromHtml(host));
			contactViewHolder.game_versers_tx.setText(list.get(position).getTeam_list().get(0).getMaxplayer() + " V "
					+ list.get(position).getTeam_list().get(1).getMaxplayer());

			contactViewHolder.date_time_tx.setText(

			getParsedDate(list.get(position).getDate()));

			contactViewHolder.location_tx.setText(list.get(position).getVenue());
			contactViewHolder.open_places_tx
					.setText(Integer.parseInt(list.get(position).getTeam_list().get(0).getOpen_positions())
							+ Integer.parseInt(list.get(position).getTeam_list().get(1).getOpen_positions())
							+ " Places Open");
			GameModel mdl = SportsUtilities.getGameBeanBySportID(list.get(position).getSportsId());

			contactViewHolder.game_img.setImageResource(mdl.getGame_image());
		}

		@Override
		public GameViewHolder onCreateViewHolder(ViewGroup viewGroup, int i) {
			View itemView = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.feed_listitem, viewGroup,
					false);
			itemView.setTag(i + "");

			return new GameViewHolder(itemView, i);
		}

		private String getParsedDate(String date1) {

			Date date;

			try {
				date = fmt.parse(date1);
				return fmtOut.format(date);
			} catch (ParseException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}

			return "";
		}
	}

	public class SimpleDividerItemDecoration extends RecyclerView.ItemDecoration {
		private Drawable mDivider;

		public SimpleDividerItemDecoration(Context context) {
			mDivider = context.getResources().getDrawable(R.drawable.line_divider);
		}

		@Override
		public void onDrawOver(Canvas c, RecyclerView parent, RecyclerView.State state) {
			int left = parent.getPaddingLeft();
			int right = parent.getWidth() - parent.getPaddingRight();

			int childCount = parent.getChildCount();
			for (int i = 0; i < childCount; i++) {
				View child = parent.getChildAt(i);

				RecyclerView.LayoutParams params = (RecyclerView.LayoutParams) child.getLayoutParams();

				int top = child.getBottom() + params.bottomMargin;
				int bottom = top + mDivider.getIntrinsicHeight();

				mDivider.setBounds(left, top, right, bottom);
				mDivider.draw(c);
			}
		}
	}
	/*
	 * public abstract class MyScrollListener extends
	 * RecyclerView.OnScrollListener {
	 * 
	 * LinearLayoutManager lm;
	 * 
	 * public MyScrollListener(Context context, LinearLayoutManager lm) {
	 * this.lm = lm; }
	 * 
	 * @Override public void onScrolled(RecyclerView recyclerView, int dx, int
	 * dy) { super.onScrolled(recyclerView, dx, dy);
	 * 
	 * visibleItemCount = recyclerView.getChildCount(); totalItemCount =
	 * lm.getItemCount(); firstVisibleItem = lm.findFirstVisibleItemPosition();
	 * 
	 * if (firstVisibleItem > previousTotal) {
	 * 
	 * fab.hide(true); } else if (firstVisibleItem < mPreviousVisibleItem) {
	 * fab.show(true);
	 * 
	 * } mPreviousVisibleItem = firstVisibleItem; }
	 * 
	 * public abstract void onMoved(int distance); }
	 */
}