package com.mcindoe.dissonantcooperation.views;

import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.view.Menu;
import android.view.MenuItem;

import com.mcindoe.dissonantcooperation.R;
import com.mcindoe.dissonantcooperation.controllers.GameManager;

public class GameActivity extends ActionBarActivity {
	
	public static final String KW_NAME = "name";
	
	private String mName;
	private GameControlFragment mGameControlFragment;
	private GameManager mGameManager;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_game);
		
		mName = getIntent().getExtras().getString(KW_NAME);
		setTitle(mName + "'s Game");

		if (savedInstanceState == null) {

			mGameControlFragment = new GameControlFragment();
			
			Bundle args = new Bundle();
			args.putString(GameControlFragment.KW_FIREBASE_URL, getResources().getString(R.string.firebase_player_url) + mName);
			mGameControlFragment.setArguments(args);

			getSupportFragmentManager().beginTransaction()
					.add(R.id.container, mGameControlFragment).commit();
		}
		
		mGameManager = new GameManager(getResources().getString(R.string.firebase_base_url));
	}

	@Override
	protected void onPause() {
		super.onPause();
		mGameManager.disconnect();
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

}
