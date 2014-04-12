package com.mcindoe.dissonantcooperation.controllers;

import java.util.ArrayList;
import java.util.List;

import android.util.Log;

import com.firebase.client.ChildEventListener;
import com.firebase.client.DataSnapshot;
import com.firebase.client.Firebase;
import com.firebase.client.FirebaseError;
import com.firebase.client.ValueEventListener;
import com.mcindoe.dissonantcooperation.models.Coin;
import com.mcindoe.dissonantcooperation.models.Player;

public class GameManager {
	
	private static final int mHxW = 68;
	
	private Player mPlayer;
	private List<Coin> mCoins;
	private String mFirebaseURL;
	
	private int mScreenHeight;
	private int mScreenWidth;
	
	private Firebase mFirebasePlayerRoot;
	private Firebase mFirebaseGameState;
	private List<PlayerListener> mFirebasePlayers;
	private ChildEventListener mPlayerChildEventListener;
	private ValueEventListener mGameStateEventListener;
	
	private GameEventListener mGameEventListener;
	
	private boolean gameOver;
	
	public GameManager(GameEventListener gel, String firebaseURL) {
		
		gameOver = false;
		
		mGameEventListener = gel;

		mFirebaseURL = firebaseURL;

		mPlayer = new Player(0,1,mHxW,mHxW);
		mCoins = new ArrayList<Coin>();
		
		mFirebasePlayerRoot = new Firebase(mFirebaseURL + "player/");
		mFirebaseGameState = new Firebase(mFirebaseURL + "gamestate/");
		
		mGameStateEventListener = new ValueEventListener() {
			
			private final int NO_PREV_STATE = 0, GAME_ON = 1, GAME_OVER = 2;
			int prevState = NO_PREV_STATE;

			@Override
			public void onCancelled(FirebaseError arg0) {
			}

			@Override
			public void onDataChange(DataSnapshot snap) {
				
				int newState = Integer.parseInt(snap.getValue().toString());

				if(prevState == NO_PREV_STATE) {
					if(newState == GAME_ON) {
						prevState = GAME_ON;
					}
					else if(newState == GAME_OVER) {
						prevState = GAME_ON;
						mFirebaseGameState.child("currState").setValue(GAME_ON);
					}
				}
				else {
					if(newState == GAME_OVER) {
						//WE LOST!!!
						if(!gameOver) {
							Log.d("DISS COOP", "We lost, maybe");
							mGameEventListener.onGameLost();
						}
					}
				}
			}
		};

		mFirebaseGameState.child("currState").addValueEventListener(mGameStateEventListener);
		
		mFirebasePlayers = new ArrayList<PlayerListener>();
		
		mPlayerChildEventListener = new ChildEventListener() {

			@Override
			public void onCancelled(FirebaseError arg0) {
			}

			@Override
			public void onChildAdded(DataSnapshot snap, String prevChildName) {
				mFirebasePlayers.add(new PlayerListener(mFirebaseURL + "player/" + snap.getName()));
			}

			@Override
			public void onChildChanged(DataSnapshot arg0, String arg1) {
			}

			@Override
			public void onChildMoved(DataSnapshot arg0, String arg1) {
			}

			@Override
			public void onChildRemoved(DataSnapshot arg0) {
			}
		};
		
		mFirebasePlayerRoot.addChildEventListener(mPlayerChildEventListener);
	}
	
	public void setDimensions(int height, int width) {
		mScreenHeight = height;
		mScreenWidth = width;
		
		for(int i = 0; i < 5; i++) {
			mCoins.add(new Coin((int)(Math.random()*(width-mHxW)), (int)(Math.random()*(height-mHxW)), mHxW, mHxW));
		}
	}
	
	public boolean dimensionsAreSet() {
		return (mScreenHeight != 0);
	}
	
	public void updateGame() {
		
		mPlayer.updatePosition();

		if(mPlayer.getX() + mPlayer.getWidth() > mScreenWidth) {
			mPlayer.setX(mScreenWidth - mPlayer.getWidth());
		}
		else if(mPlayer.getX() < 0) {
			mPlayer.setX(0);
		}

		if(mPlayer.getY() + mPlayer.getHeight() > mScreenHeight) {
			mPlayer.setY(mScreenHeight - mPlayer.getHeight());
		}
		else if(mPlayer.getY() < 0) {
			mPlayer.setY(0);
		}
		
		for(int i = 0; i < mCoins.size(); i++) {
			if(mPlayer.isColliding(mCoins.get(i))) {

				mCoins.remove(i--);

				if(mCoins.size() == 0) {
					
					mFirebaseGameState.child("currState").setValue(2);
					gameOver = true;
					mGameEventListener.onGameWon();
				}
			}
		}
		
	}
	
	public void disconnect() {
		mFirebasePlayerRoot.removeEventListener(mPlayerChildEventListener);
		mFirebaseGameState.removeEventListener(mGameStateEventListener);
		
		while(!mFirebasePlayers.isEmpty()) {
			mFirebasePlayers.get(0).stopListening();
			mFirebasePlayers.remove(0);
		}
	}
	
	public Player getPlayer() {
		return mPlayer;
	}
	
	public List<Coin> getCoins() {
		return mCoins;
	}
	
	private class PlayerListener {
		
		private Firebase mFirebase;
		private LeftValueEventListener mLeftListener;
		private RightValueEventListener mRightListener;
		private UpValueEventListener mUpListener;
		private DownValueEventListener mDownListener;
		
		public PlayerListener(String firebaseURL) {
			
			mFirebase = new Firebase(firebaseURL);

			mLeftListener = new LeftValueEventListener();
			mRightListener = new RightValueEventListener();
			mUpListener = new UpValueEventListener();
			mDownListener = new DownValueEventListener();
			
			mFirebase.child("left").addValueEventListener(mLeftListener);
			mFirebase.child("right").addValueEventListener(mRightListener);
			mFirebase.child("up").addValueEventListener(mUpListener);
			mFirebase.child("down").addValueEventListener(mDownListener);
		}
		
		public void stopListening() {
			mFirebase.child("left").removeEventListener(mLeftListener);
			mFirebase.child("right").removeEventListener(mRightListener);
			mFirebase.child("up").removeEventListener(mUpListener);
			mFirebase.child("down").removeEventListener(mDownListener);
		}
		
	}
	
	private class LeftValueEventListener implements ValueEventListener {

		private int prevValue;
		
		public LeftValueEventListener() {
			prevValue = 0;
		}

		@Override
		public void onDataChange(DataSnapshot snap) {
			int newValue = Integer.parseInt(snap.getValue().toString());
			mPlayer.setXdel(mPlayer.getXdel() - newValue + prevValue);
			prevValue = newValue;
			mGameEventListener.updateLeftArrowImage(newValue == 1);
		}

		@Override
		public void onCancelled(FirebaseError err) {
			//Nothing here...
		}
	}
	
	private class UpValueEventListener implements ValueEventListener {

		private int prevValue;
		
		public UpValueEventListener() {
			prevValue = 0;
		}

		@Override
		public void onDataChange(DataSnapshot snap) {
			int newValue = Integer.parseInt(snap.getValue().toString());
			mPlayer.setYdel(mPlayer.getYdel() - newValue + prevValue);
			prevValue = newValue;
			mGameEventListener.updateUpArrowImage(newValue == 1);
		}

		@Override
		public void onCancelled(FirebaseError err) {
			//Nothing here...
		}
	}
	
	private class DownValueEventListener implements ValueEventListener {

		private int prevValue;
		
		public DownValueEventListener() {
			prevValue = 0;
		}

		@Override
		public void onDataChange(DataSnapshot snap) {
			int newValue = Integer.parseInt(snap.getValue().toString());
			mPlayer.setYdel(mPlayer.getYdel() + newValue - prevValue);
			prevValue = newValue;
			mGameEventListener.updateDownArrowImage(newValue == 1);
		}

		@Override
		public void onCancelled(FirebaseError err) {
			//Nothing here...
		}
	}
	
	private class RightValueEventListener implements ValueEventListener {

		private int prevValue;
		
		public RightValueEventListener() {
			prevValue = 0;
		}

		@Override
		public void onDataChange(DataSnapshot snap) {
			int newValue = Integer.parseInt(snap.getValue().toString());
			mPlayer.setXdel(mPlayer.getXdel() + newValue - prevValue);
			prevValue = newValue;
			mGameEventListener.updateRightArrowImage(newValue == 1);
		}

		@Override
		public void onCancelled(FirebaseError err) {
			//Nothing here...
		}
	}

	public interface GameEventListener {
		public abstract void onGameLost();
		public abstract void onGameWon();
		public void updateUpArrowImage(boolean highlighted);
		public void updateDownArrowImage(boolean highlighted);
		public void updateRightArrowImage(boolean highlighted);
		public void updateLeftArrowImage(boolean highlighted);
	}
}
