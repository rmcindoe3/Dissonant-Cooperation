package com.mcindoe.dissonantcooperation.controllers;

import java.util.ArrayList;
import java.util.List;

import android.content.Context;
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
	private List<PlayerListener> mFirebasePlayers;
	private ChildEventListener mChildEventListener;
	
	public GameManager(String firebaseURL) {

		mFirebaseURL = firebaseURL;

		mPlayer = new Player(0,1,mHxW,mHxW);
		mCoins = new ArrayList<Coin>();
		
		for(int i = 0; i < 5; i++) {
			mCoins.add(new Coin((int)(Math.random()*500), (int)(Math.random()*500), mHxW, mHxW));
		}
		
		mFirebasePlayerRoot = new Firebase(mFirebaseURL + "player/");
		mFirebasePlayers = new ArrayList<PlayerListener>();
		
		mChildEventListener = new ChildEventListener() {

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
		
		mFirebasePlayerRoot.addChildEventListener(mChildEventListener);
	}
	
	public void setDimensions(int height, int width) {
		mScreenHeight = height;
		mScreenWidth = width;
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
				Log.d("DISS COOP", "deleting a coin");
				mCoins.remove(i--);
			}
		}
		
	}
	
	public void disconnect() {
		mFirebasePlayerRoot.removeEventListener(mChildEventListener);
		
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
			mPlayer.setXdel(mPlayer.getXdel() - Integer.parseInt(snap.getValue().toString()) + prevValue);
			prevValue = Integer.parseInt(snap.getValue().toString());
			Log.d("DISS COOP", "left value changed, xdel: " + mPlayer.getXdel());
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
			mPlayer.setYdel(mPlayer.getYdel() - Integer.parseInt(snap.getValue().toString()) + prevValue);
			prevValue = Integer.parseInt(snap.getValue().toString());
			Log.d("DISS COOP", "up value changed, ydel: " + mPlayer.getYdel());
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
			mPlayer.setYdel(mPlayer.getYdel() + Integer.parseInt(snap.getValue().toString()) - prevValue);
			prevValue = Integer.parseInt(snap.getValue().toString());
			Log.d("DISS COOP", "down value changed, ydel: " + mPlayer.getYdel());
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
			mPlayer.setXdel(mPlayer.getXdel() + Integer.parseInt(snap.getValue().toString()) - prevValue);
			prevValue = Integer.parseInt(snap.getValue().toString());
			Log.d("DISS COOP", "right value changed, xdel: " + mPlayer.getXdel());
		}

		@Override
		public void onCancelled(FirebaseError err) {
			//Nothing here...
		}
	}

}
