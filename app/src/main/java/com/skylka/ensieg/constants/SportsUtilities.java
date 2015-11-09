package com.skylka.ensieg.constants;

import java.util.ArrayList;

import com.skylka.ensieg.R;
import com.skylka.ensieg.model.GameModel;

public class SportsUtilities {

	public static ArrayList<GameModel> sport_list = new ArrayList<GameModel>();

	public static void initializeSports() {
		String[] game_names = { "Football", "Basketball", "Volleyball", "Tennis", "Badminton", "Table Tennis",
				"Cricket" };
		int[] game_small_imgs = { R.drawable.football_small, R.drawable.baseketball_small, R.drawable.volleyball_small,
				R.drawable.tennis_small, R.drawable.badmenton_small, R.drawable.table_tennis_small,
				R.drawable.cricket_small };

		int[] game_imgs = { R.drawable.football, R.drawable.basketball, R.drawable.volleyball, R.drawable.tennis,
				R.drawable.badminton, R.drawable.tabletennis, R.drawable.cricket };
		int[] game_image_big = { R.drawable.football_normal, R.drawable.basketball_normal, R.drawable.volleyball_normal,
				R.drawable.tennis_normal, R.drawable.hockey_normal, R.drawable.table_tennis,
				R.drawable.cricket_normal };
		
		
		
		int[] sportID = { 1, 2, 3, 4, 5, 6, 7 };
		if (sport_list.size() <= 0) {
			for (int i = 0; i < game_names.length; i++) {
				GameModel bean = new GameModel();
				bean.setGame_name(game_names[i]);
				bean.setGame_image(game_imgs[i]);
				bean.setGame_small_image(game_small_imgs[i]);
				bean.setSportID(sportID[i]);
				bean.setGame_big_image(game_image_big[i]);

				sport_list.add(bean);

			}
		}
	}

	public static GameModel getGameBeanBySportID(String id) {
		GameModel bean = new GameModel();
		for (int i = 0; i < sport_list.size(); i++) {
			if (id.equals(sport_list.get(i).getSportID() + "")) {
				bean = sport_list.get(i);
				break;
			}

		}
		return bean;
	}

	public static GameModel getGameBySportName(String name) {
		GameModel bean = new GameModel();
		for (int i = 0; i < sport_list.size(); i++) {
			if (name.equals(sport_list.get(i).getGame_name())) {
				bean = sport_list.get(i);
				break;
			}

		}
		return bean;
	}
}
