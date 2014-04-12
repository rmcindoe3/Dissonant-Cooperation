package com.mcindoe.dissonantcooperation.views;

import java.util.Timer;
import java.util.TimerTask;

import android.app.Activity;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.util.AttributeSet;
import android.view.View;

import com.mcindoe.dissonantcooperation.R;
import com.mcindoe.dissonantcooperation.controllers.GameManager;

public class GameView extends View {
	
	private Context mContext;

	private GameManager mGameManager;
	private final Bitmap[] playerImages = {
			BitmapFactory.decodeResource(getResources(), R.drawable.one), 
			BitmapFactory.decodeResource(getResources(), R.drawable.two), 
			BitmapFactory.decodeResource(getResources(), R.drawable.three), 
			BitmapFactory.decodeResource(getResources(), R.drawable.four), 
			BitmapFactory.decodeResource(getResources(), R.drawable.three), 
			BitmapFactory.decodeResource(getResources(), R.drawable.two) };
	private final Bitmap[] coinImages = {
			BitmapFactory.decodeResource(getResources(), R.drawable.coin_one),
			BitmapFactory.decodeResource(getResources(), R.drawable.coin_two),
			BitmapFactory.decodeResource(getResources(), R.drawable.coin_three),
			BitmapFactory.decodeResource(getResources(), R.drawable.coin_four),
			BitmapFactory.decodeResource(getResources(), R.drawable.coin_three),
			BitmapFactory.decodeResource(getResources(), R.drawable.coin_two),
	};
	
	private Timer mGameUpdateTimer;
	
	private int animCounter;
	private int animCounterUpdate;
	
	private boolean mDisconnecting;

	public GameView(Context context, AttributeSet attrs, int defStyleAttr) {
		super(context, attrs, defStyleAttr);
		mGameUpdateTimer = new Timer();
		mContext = context;
		animCounter = 0;
		animCounterUpdate = 0;
		mDisconnecting = false;
	}

	public GameView(Context context, AttributeSet attrs) {
		super(context, attrs);
		mGameUpdateTimer = new Timer();
		mContext = context;
		animCounter = 0;
		animCounterUpdate = 0;
		mDisconnecting = false;
	}

	public GameView(Context context) {
		super(context);
		mGameUpdateTimer = new Timer();
		mContext = context;
		animCounter = 0;
		animCounterUpdate = 0;
		mDisconnecting = false;
	}

	public void setGameManager(GameManager gm) {
		mGameManager = gm;
	}

	public void disconnect() {
		mDisconnecting = true;
		mGameUpdateTimer.cancel();
		mGameManager.disconnect();
	}
	
    protected void onDraw(Canvas canvas) {

    	if(!mGameManager.dimensionsAreSet()) {
    		mGameManager.setDimensions(getHeight(), getWidth());
    	}

    	super.onDraw(canvas);
    	drawGameImg(canvas);

    	if(!mDisconnecting) {
    		mGameUpdateTimer.schedule(new GameUpdateTimerTask(), 15);
    	}
    }
    
    private void drawGameImg(Canvas canvas) {
    	// display coins
    	for(int i = 0; i < mGameManager.getCoins().size(); i++) {
    		float coinLeft = (float) mGameManager.getCoins().get(i).getX();
        	float coinTop = (float) mGameManager.getCoins().get(i).getY();
        	
        	canvas.drawBitmap(coinImages[animCounter%6], coinLeft, coinTop, null);
    	}
    	
    	// display player
    	float playerLeft = (float) mGameManager.getPlayer().getX();
    	float playerTop = (float) mGameManager.getPlayer().getY();
    	
    	canvas.drawBitmap(playerImages[animCounter%6], playerLeft, playerTop, null);
    }

	private class GameUpdateTimerTask extends TimerTask {

		@Override
		public void run() {
			
			mGameManager.updateGame();
			
			if((++animCounterUpdate)%3 == 0) {
				animCounter++;
			}

			//We have to run this on our UI thread according to Android OS.
			((Activity) mContext).runOnUiThread(new Runnable() {
				@Override
				public void run() {
					invalidate();
				}
			});
		}
	}
}
