package com.mcindoe.dissonantcooperation.views;

import android.app.Fragment;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.mcindoe.dissonantcooperation.R;

public class MainActivity extends ActionBarActivity {
	
	private int mWins, mLosses, mStreak;
	private PlaceholderFragment mFragment;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		
		getActionBar().hide();
		
		mWins = 0;
		mLosses = 0;
		mStreak = 0;
		
		mFragment = new PlaceholderFragment();

		if (savedInstanceState == null) {
			getFragmentManager().beginTransaction()
					.add(R.id.container, mFragment).commit();
		}
	}

	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		super.onActivityResult(requestCode, resultCode, data);
		
		Log.d("DISS COOP", "result received");
		
		if(resultCode == GameActivity.GAME_LOST) {
			mLosses++;
			
			if(mStreak > 0) {
				mStreak = -1;
			}
			else {
				mStreak--;
			}
		}
		else {
			mWins++;
			
			if(mStreak < 0) {
				mStreak = 1;
			}
			else {
				mStreak++;
			}
		}
		
		mFragment.updateStatistics(mWins, mLosses, mStreak);
		
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {

		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.main, menu);
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

	/**
	 * A placeholder fragment containing a simple view.
	 */
	public static class PlaceholderFragment extends Fragment {
		
		private Button mPlayButton;
		private EditText mNameEditText;
		
		private TextView mWinsTextView;
		private TextView mLossesTextView;
		private TextView mStreakTextView;

		public PlaceholderFragment() {
		}
		
		public void updateStatistics(int wins, int losses, int streak) {
			mWinsTextView.setText("Wins: " + wins);
			mLossesTextView.setText("Losses: " + losses);
			if(streak > 0) {
				mStreakTextView.setText("Win Streak: " + streak);
			}
			else {
				mStreakTextView.setText("Loss Streak: " + -1*streak);
			}
		}

		@Override
		public View onCreateView(LayoutInflater inflater, ViewGroup container,
				Bundle savedInstanceState) {
			View rootView = inflater.inflate(R.layout.fragment_main, container,
					false);
			
			mPlayButton = (Button)rootView.findViewById(R.id.button_play);
			mNameEditText = (EditText)rootView.findViewById(R.id.edit_text_enter_name);
			mWinsTextView = (TextView)rootView.findViewById(R.id.wins_text_view);
			mLossesTextView = (TextView)rootView.findViewById(R.id.losses_text_view);
			mStreakTextView = (TextView)rootView.findViewById(R.id.streak_text_view);
			
			mPlayButton.setOnClickListener(new OnClickListener() {

				@Override
				public void onClick(View v) {

					if(mNameEditText.getText().toString().length() >= 1) {
						Intent intent = new Intent(getActivity(), GameActivity.class);
						intent.putExtra(GameActivity.KW_NAME, mNameEditText.getText().toString());
						getActivity().startActivityForResult(intent, 0);
					}
					else {
						Toast.makeText(getActivity(), "Enter a name!" , Toast.LENGTH_LONG).show();
					}
				}
			});
			
			return rootView;
		}
	}

}
