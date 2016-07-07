package com.OutSideTheByte.disparateshades;

import java.util.Random;

import android.view.View;
import android.view.View.OnClickListener;
import android.view.animation.Animation;
import android.view.animation.Animation.AnimationListener;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.LinearLayout.LayoutParams;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences.Editor;
import android.graphics.Color;
import android.os.Bundle;
import android.os.Handler;

public class StartGame extends Activity implements AnimationListener {

	int level = 0, time = 60, points = 0;
	Button timer, replay, finalScore, share, gameover_highScore;
	TextView score, timeup, best;
	LinearLayout gameBoard, gameOver, gamePanel;
	RelativeLayout gameBoard_bg;
	Animation fade_in, fade_out, translate_in, translate_out;

	Boolean is_gameover_shown, is_gameboard_shown = true, is_first_time = true;

	Handler handler;
	Runnable runnable;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_start_game);

		timer = (Button) findViewById(R.id.button_timer);
		timer.setTypeface(MainActivity.custom_font);
		score = (TextView) findViewById(R.id.tv_score);
		score.setTypeface(MainActivity.custom_font);
		gameBoard_bg = (RelativeLayout) findViewById(R.id.main_board);
		gameBoard = (LinearLayout) findViewById(R.id.gameBoard);
		gamePanel = (LinearLayout) findViewById(R.id.game_Panel);

		gameOver = (LinearLayout) findViewById(R.id.gameOver);
		replay = (Button) findViewById(R.id.button_replay);
		finalScore = (Button) findViewById(R.id.button_score);
		finalScore.setTypeface(MainActivity.custom_font);
		share = (Button) findViewById(R.id.button_share);
		timeup = (TextView) findViewById(R.id.tv_timeup);
		timeup.setTypeface(MainActivity.custom_font);
		best = (TextView) findViewById(R.id.tv_gameover_best);
		best.setTypeface(MainActivity.custom_font);
		gameover_highScore = (Button) findViewById(R.id.button_gameover_highscore);
		gameover_highScore.setTypeface(MainActivity.custom_font);

		fade_in = AnimationUtils.loadAnimation(this, R.anim.fade_in);
		fade_out = AnimationUtils.loadAnimation(this, R.anim.fade_out);
		translate_in = AnimationUtils.loadAnimation(this, R.anim.translate_in);
		translate_out = AnimationUtils
				.loadAnimation(this, R.anim.translate_out);

		is_gameover_shown = false;
		is_gameboard_shown = false;

		startGame();
	}

	public void startGame() {

		points = 0;
		level = 0;
		time = 60;
		
		setBoard();
		startTimer();

	}

	public void setBoard() {

		score.setText("Score : " + points);

		Random random = new Random();
		int randRow = random.nextInt((level + 2));
		int randCol = random.nextInt((level + 2));

		int red = random.nextInt(256);
		int green = random.nextInt(256);
		int blue = random.nextInt(256);

		int color = Color.argb(255, red, green, blue);

		int colorDiff = Color.argb(200, red, green, blue);

		if (points > 10)
			colorDiff = Color.argb(210, red, green, blue);
		else if (points > 15)
			colorDiff = Color.argb(220, red, green, blue);
		else if (points > 20)
			colorDiff = Color.argb(225, red, green, blue);
		else if (points > 25)
			colorDiff = Color.argb(230, red, green, blue);
		else if (points > 30)
			colorDiff = Color.argb(235, red, green, blue);
		else if (points > 40)
			colorDiff = Color.argb(240, red, green, blue);
		else if (points > 50)
			colorDiff = Color.argb(245, red, green, blue);

		gameBoard.removeAllViews();
		LayoutParams buttonLayoutParams = new LayoutParams(
				LayoutParams.MATCH_PARENT, LayoutParams.MATCH_PARENT, 1.0f);
		buttonLayoutParams.setMargins(2, 2, 2, 2);

		for (int i = 0; i < level + 2; i++) {
			LinearLayout row = new LinearLayout(this);
			row.setLayoutParams(buttonLayoutParams);
			for (int j = 0; j < level + 2; j++) {
				Button col = new Button(this);
				col.setBackgroundColor(color);
				col.setLayoutParams(buttonLayoutParams);
				row.addView(col);
				if (randRow == i && randCol == j) {
					col.setBackgroundColor(colorDiff);

					col.setOnClickListener(new OnClickListener() {

						@Override
						public void onClick(View v) {

							if (points < 6)
								level++;
							else if (points == 9)
								level++;
							else if (level == 12)
								level++;
							else if (points == 15)
								level++;
							else if (level == 18)
								level++;
							else if (points == 21)
								level++;
							else if (level == 24)
								level++;
							else if (points == 27)
								level++;
							else if (level == 30)
								level++;

							points++;
							setBoard();

						}
					});
				}
			}
			gameBoard.addView(row);

		}

	}

	public void startTimer() {

		timer.setText("" + time);

		handler = new Handler();
		runnable = new Runnable() {

			@Override
			public void run() {

				time--;
				if (time < 11) {
					timer.setTextColor(Color.RED);
				} else
					timer.setTextColor(Color.BLACK);
				timer.setText("" + time);
				if (time > 0) {
					handler.postDelayed(runnable, 1000);
				} else {
					timesUp();
				}

			}
		};
		handler.postDelayed(runnable, 1000);
	}

	public void timesUp() {

		hide_board(1);

		finalScore.setText("" + points);
		replay.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {

				hide_board(2);
				startGame();

			}
		});

		share.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {

				Intent shareIntent = new Intent(
						android.content.Intent.ACTION_SEND);
				shareIntent.setType("text/plain");
				shareIntent.putExtra(android.content.Intent.EXTRA_TEXT,
						"Hey , Check this app out , it's called Disparate Shades.I just scored  "
								+ points + " \n\n" + "www.blah.com");
				startActivity(Intent.createChooser(shareIntent, "Share "));
			}
		});

		if (MainActivity.HighScore < points) {
			Editor editor = MainActivity.sharedpreferences.edit();
			editor.putInt(MainActivity.BestScore, points);
			editor.commit();

			MainActivity.HighScore = points;
		}

		gameover_highScore.setText(""+MainActivity.HighScore);
	}

	public void hide_board(int board_no) {
		if (board_no == 1) {
			gamePanel.startAnimation(fade_out);
			fade_out.setAnimationListener(this);
		} else if (board_no == 2) {
			gameOver.startAnimation(translate_out);
			translate_out.setAnimationListener(this);

		}

	}

	@Override
	public void onAnimationEnd(Animation animation) {
		// TODO Auto-generated method stub
		if (animation == fade_out) {
			gamePanel.setVisibility(View.GONE);
		}
		if (animation == translate_in) {
			gameOver.setVisibility(View.VISIBLE);
		}
		if (animation == translate_out) {
			gameOver.setVisibility(View.GONE);
		}
		if (animation == fade_in) {
			gamePanel.setVisibility(View.VISIBLE);
		}

	}

	@Override
	public void onAnimationRepeat(Animation animation) {
		// TODO Auto-generated method stub

	}

	@Override
	public void onAnimationStart(Animation animation) {

		if (animation == fade_out) {
			gameOver.startAnimation(translate_in);
			translate_in.setAnimationListener(this);
		}

		if (animation == translate_out) {
			gamePanel.startAnimation(fade_in);
			fade_in.setAnimationListener(this);
		}

	}

}