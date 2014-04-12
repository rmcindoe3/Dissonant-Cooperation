package com.mcindoe.dissonantcooperation.views;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.view.Menu;
import android.view.MenuItem;

import com.mcindoe.dissonantcooperation.R;
import com.mcindoe.dissonantcooperation.controllers.GameManager;

public class GameActivity extends ActionBarActivity implements GameManager.GameEventListener {
	
	public static final String KW_NAME = "name";
	
	private String mName;
	private GameControlFragment mGameControlFragment;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_game);
		
		mName = getIntent().getExtras().getString(KW_NAME);
		setTitle(mName + "'s Game");

		if (savedInstanceState == null) {

			mGameControlFragment = new GameControlFragment();
			mGameControlFragment.setGameEventListener(this);
			
			Bundle args = new Bundle();
			args.putString(GameControlFragment.KW_FIREBASE_URL, getResources().getString(R.string.firebase_player_url) + mName);
			mGameControlFragment.setArguments(args);

			getSupportFragmentManager().beginTransaction()
					.add(R.id.container, mGameControlFragment).commit();
		}
	}

	@Override
	protected void onPause() {
		super.onPause();
		mGameControlFragment.disconnect();
		finish();
	}

	@Override
	protected void onResume() {
		super.onResume();
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {

		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.game, menu);
		return true;
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		// Handle action bar item clicks here. The action bar will
		// automatically handle clicks on the Home/Up button, so long
		// as you specify a parent activity in AndroidManifest.xml.
		int id = item.getItemId();
		if (id == R.id.action_settings) {
			return true;
		}
		return super.onOptionsItemSelected(item);
	}

	@Override
	public void onGameLost() {
		
		final Activity con = this;

		runOnUiThread(new Runnable() {

			@Override
			public void run() {
				AlertDialog.Builder builder = new AlertDialog.Builder(con);
				builder.setTitle("Defeat");
				builder.setMessage("Someone else collected all their coins!");
				builder.setPositiveButton("Continue", new DialogInterface.OnClickListener() {
					@Override
					public void onClick(DialogInterface arg0, int arg1) {
						finish();
					}
				});
				AlertDialog dialog = builder.create();

				if(!con.isFinishing()) {
					dialog.show();
				}
			}
			
		});
	}

	@Override
	public void onGameWon() {
		
		final Activity con = this;

		runOnUiThread(new Runnable() {

			@Override
			public void run() {
				AlertDialog.Builder builder = new AlertDialog.Builder(con);
				builder.setTitle("Victory");
				builder.setMessage("You collected your coins before anyone else!");
				builder.setPositiveButton("Continue", new DialogInterface.OnClickListener() {
					@Override
					public void onClick(DialogInterface arg0, int arg1) {
						finish();
					}
				});
				AlertDialog dialog = builder.create();

				if(!con.isFinishing()) {
					dialog.show();
				}
			}
		});
	}

	@Override
	public void updateUpArrowImage(boolean highlighted) {
		mGameControlFragment.updateUpArrowImage(highlighted);
	}

	@Override
	public void updateDownArrowImage(boolean highlighted) {
		mGameControlFragment.updateDownArrowImage(highlighted);
	}

	@Override
	public void updateRightArrowImage(boolean highlighted) {
		mGameControlFragment.updateRightArrowImage(highlighted);
	}

	@Override
	public void updateLeftArrowImage(boolean highlighted) {
		mGameControlFragment.updateLeftArrowImage(highlighted);
	}

}
