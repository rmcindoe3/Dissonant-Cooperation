package com.mcindoe.dissonantcooperation.views;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnTouchListener;
import android.view.ViewGroup;
import android.widget.ImageButton;

import com.firebase.client.Firebase;
import com.mcindoe.dissonantcooperation.R;
import com.mcindoe.dissonantcooperation.controllers.GameManager;

public class GameControlFragment extends Fragment {

	public static final String KW_FIREBASE_URL = "firebase_url";
	
	private GameView mGameView;

	private ImageButton mLeftButton;
	private ImageButton mRightButton;
	private ImageButton mUpButton;
	private ImageButton mDownButton;
	
	private String mFirebaseURL;
	
	private Firebase mFirebase;
	
	private GameManager.GameEventListener mGameEventListener;

	public GameControlFragment() {

	}
	
	public void setGameEventListener(GameManager.GameEventListener gel) {
		mGameEventListener = gel;
	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
		View rootView = inflater.inflate(R.layout.fragment_game, container, false);

		mFirebaseURL = this.getArguments().getString(KW_FIREBASE_URL);
		mFirebase = new Firebase(mFirebaseURL);

		mFirebase.child("left").setValue(0);
		mFirebase.child("right").setValue(0);
		mFirebase.child("up").setValue(0);
		mFirebase.child("down").setValue(0);
		
		mLeftButton = (ImageButton)rootView.findViewById(R.id.button_left);
		mRightButton = (ImageButton)rootView.findViewById(R.id.button_right);
		mUpButton = (ImageButton)rootView.findViewById(R.id.button_up);
		mDownButton = (ImageButton)rootView.findViewById(R.id.button_down);
		
		mGameView = (GameView)rootView.findViewById(R.id.game_view);
		mGameView.setGameManager(new GameManager(mGameEventListener, getResources().getString(R.string.firebase_base_url)));
		
		mLeftButton.setOnTouchListener(new OnTouchListener() {

			@Override
			public boolean onTouch(View v, MotionEvent event) {

				switch(event.getAction()) {

				case MotionEvent.ACTION_DOWN:
					//Pressed
					mFirebase.child("left").setValue(1);
					break;

				case MotionEvent.ACTION_UP:
					//Released
					mFirebase.child("left").setValue(0);
					break;

				}
				return false;
			}
		});
		
		mRightButton.setOnTouchListener(new OnTouchListener() {

			@Override
			public boolean onTouch(View v, MotionEvent event) {

				switch(event.getAction()) {

				case MotionEvent.ACTION_DOWN:
					//Pressed
					mFirebase.child("right").setValue(1);
					break;

				case MotionEvent.ACTION_UP:
					//Released
					mFirebase.child("right").setValue(0);
					break;

				}
				return false;
			}
		});
		
		mUpButton.setOnTouchListener(new OnTouchListener() {

			@Override
			public boolean onTouch(View v, MotionEvent event) {

				switch(event.getAction()) {

				case MotionEvent.ACTION_DOWN:
					//Pressed
					mFirebase.child("up").setValue(1);
					break;

				case MotionEvent.ACTION_UP:
					//Released
					mFirebase.child("up").setValue(0);
					break;

				}
				return false;
			}
		});
		
		mDownButton.setOnTouchListener(new OnTouchListener() {

			@Override
			public boolean onTouch(View v, MotionEvent event) {

				switch(event.getAction()) {

				case MotionEvent.ACTION_DOWN:
					//Pressed
					mFirebase.child("down").setValue(1);
					break;

				case MotionEvent.ACTION_UP:
					//Released
					mFirebase.child("down").setValue(0);
					break;

				}
				return false;
			}
		});
		
		return rootView;
	}
		
	public void disconnect() {
		mGameView.disconnect();
	}
}
