package com.OutSideTheByte.disparateshades;

import android.view.View;
import android.view.View.OnClickListener;
import android.view.animation.Animation;
import android.view.animation.Animation.AnimationListener;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Typeface;
import android.os.Bundle;
import android.os.Handler;

public class MainActivity extends Activity {

	public static SharedPreferences sharedpreferences;
	public static final String MyPREFERENCES = "MyPrefs";
	public static final String BestScore = "BestScore";

	static int HighScore = 0;

	static Typeface custom_font;
	Button best;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);

		sharedpreferences = getSharedPreferences(MyPREFERENCES,
				Context.MODE_PRIVATE);

		HighScore = sharedpreferences.getInt(BestScore, 0);

		custom_font = Typeface.createFromAsset(getAssets(), "fonts/Chunq.ttf");

		final LinearLayout splashScreen = (LinearLayout)findViewById(R.id.splashScreen);
		final Animation fade_out = AnimationUtils.loadAnimation(this, R.anim.fade_out);
		fade_out.setAnimationListener(new AnimationListener() {
			
			@Override
			public void onAnimationStart(Animation animation) {
				// TODO Auto-generated method stub
				
			}
			
			@Override
			public void onAnimationRepeat(Animation animation) {
				// TODO Auto-generated method stub
				
			}
			
			@Override
			public void onAnimationEnd(Animation animation) {
				
				splashScreen.setVisibility(View.GONE);
				
			}
		});
		
		TextView tv = (TextView) findViewById(R.id.tv_best);
		tv.setTypeface(custom_font);
		best = (Button) findViewById(R.id.button_highscore);
		best.setTypeface(custom_font);

		best.setText("" + HighScore);

		Button startGame = (Button) findViewById(R.id.button_start);
		startGame.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {

				Intent intent = new Intent(getApplicationContext(),
						StartGame.class);
				startActivity(intent);

			}
		});

		Button shareGame = (Button) findViewById(R.id.button_share);
		shareGame.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {

				Intent shareIntent = new Intent(
						android.content.Intent.ACTION_SEND);
				shareIntent.setType("text/plain");
				if (HighScore > 0)
					shareIntent.putExtra(android.content.Intent.EXTRA_TEXT,
							"Hey , Check this app out , it's called Disparate Shades.My Highest score is "
									+ HighScore + " \n\n" + "www.blah.com");
				else
					shareIntent.putExtra(android.content.Intent.EXTRA_TEXT,
							"Hey , Check this app out , it's called Disparate Shades"
									+ " \n\n" + "www.blah.com");
				startActivity(Intent.createChooser(shareIntent, "Share "));
			}
		});
		
		Handler h = new Handler();
		Runnable r = new Runnable() {
			
			@Override
			public void run() {
				
				splashScreen.startAnimation(fade_out);
				
			}
		};
		h.postDelayed(r, 2500);

	}

	@Override
	protected void onStart() {
		// TODO Auto-generated method stub
		super.onStart();

		best.setText("" + HighScore);

	}

}