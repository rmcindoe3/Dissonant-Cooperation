package com.mcindoe.dissonantcooperation.controllers;

import java.util.ArrayList;
import java.util.List;

import com.firebase.client.ChildEventListener;
import com.firebase.client.DataSnapshot;
import com.firebase.client.Firebase;
import com.firebase.client.FirebaseError;
import com.firebase.client.ValueEventListener;
import com.mcindoe.dissonantcooperation.models.Coin;
import com.mcindoe.dissonantcooperation.models.Player;

public class GameManager {
	
	private Player mPlayer;
	private List<Coin> mCoins;
	private String mFirebaseURL;
	
	private Firebase mFirebasePlayerRoot;
	private List<Firebase> mFirebasePlayers;
	
	public GameManager(String firebaseURL) {
		
		mFirebaseURL = firebaseURL;

		mPlayer = new Player(0,0,50,50);
		mCoins = new ArrayList<Coin>();
		
		for(int i = 0; i < 5; i++) {
			mCoins.add(new Coin((int)Math.random()*500, (int)Math.random()*500, 50, 50));
		}
		
		mFirebasePlayerRoot = new Firebase(mFirebaseURL + "player/");
		mFirebasePlayers = new ArrayList<Firebase>();
		
		mFirebasePlayerRoot.addChildEventListener(new ChildEventListener() {

			@Override
			public void onCancelled(FirebaseError arg0) {
			}

			@Override
			public void onChildAdded(DataSnapshot snap, String prevChildName) {
				
				Firebase temp = new Firebase(mFirebaseURL + "player/" + snap.getName());
				temp.child("left").addValueEventListener(new LeftValueEventListener());
				temp.child("right").addValueEventListener(new RightValueEventListener());
				temp.child("up").addValueEventListener(new UpValueEventListener());
				temp.child("down").addValueEventListener(new DownValueEventListener());

				mFirebasePlayers.add(temp);
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
			
		});
	}
	
	public void startListening() {
		
		Firebase.goOnline();
	}
	
	public void stopListening() {
		
		Firebase.goOffline();
		
		/* I THINK THIS IS WRONG, but im not going to delete it yet.
		mFirebasePlayerRoot.goOffline();
		mFirebasePlayerRoot = null;
		
		while(!mFirebasePlayers.isEmpty()) {
			
			mFirebasePlayers.get(0).child("left").goOffline();
			mFirebasePlayers.get(0).child("right").goOffline();
			mFirebasePlayers.get(0).child("up").goOffline();
			mFirebasePlayers.get(0).child("down").goOffline();
			mFirebasePlayers.get(0).goOffline();
			mFirebasePlayers.set(0, null);
			mFirebasePlayers.remove(0);
		}*/
	}
	
	private class LeftValueEventListener implements ValueEventListener {

		private int prevValue;
		
		public LeftValueEventListener() {
			prevValue = 0;
		}

		@Override
		public void onDataChange(DataSnapshot snap) {
			mPlayer.setXdel(mPlayer.getXdel() - (int)snap.getValue() + prevValue);
			prevValue = (int)snap.getValue();
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
			mPlayer.setYdel(mPlayer.getYdel() - (int)snap.getValue() + prevValue);
			prevValue = (int)snap.getValue();
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
			mPlayer.setYdel(mPlayer.getYdel() + (int)snap.getValue() - prevValue);
			prevValue = (int)snap.getValue();
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
			mPlayer.setXdel(mPlayer.getXdel() + (int)snap.getValue() - prevValue);
			prevValue = (int)snap.getValue();
		}

		@Override
		public void onCancelled(FirebaseError err) {
			//Nothing here...
		}
	}

}
