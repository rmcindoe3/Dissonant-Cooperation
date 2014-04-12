package com.mcindoe.dissonantcooperation.views;

import java.util.Timer;
import java.util.TimerTask;
import android.annotation.SuppressLint;
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
	private Bitmap playerImg = BitmapFactory.decodeResource(getResources(), R.drawable.one);
	private Bitmap coinImg = BitmapFactory.decodeResource(getResources(), R.drawable.coin);
	
	private Timer mGameUpdateTimer;

	public GameView(Context context, AttributeSet attrs, int defStyleAttr) {
		super(context, attrs, defStyleAttr);
		mGameUpdateTimer = new Timer();
		mContext = context;
	}

	public GameView(Context context, AttributeSet attrs) {
		super(context, attrs);
		mGameUpdateTimer = new Timer();
		mContext = context;
	}

	public GameView(Context context) {
		super(context);
		mGameUpdateTimer = new Timer();
		mContext = context;
	}

	public void setGameManager(GameManager gm) {
		mGameManager = gm;
	}

	public void disconnect() {
		mGameUpdateTimer.cancel();
		mGameManager.disconnect();
	}
	
    protected void onDraw(Canvas canvas) {
		mGameManager.setDimensions(getHeight(), getWidth());
    	super.onDraw(canvas);
    	drawGameImg(canvas);

		mGameUpdateTimer.schedule(new GameUpdateTimerTask(), 15);
    }
    
    private void drawGameImg(Canvas canvas) {
    	// display coins
    	for(int i = 0; i < mGameManager.getCoins().size(); i++) {
    		float coinLeft = (float) mGameManager.getCoins().get(i).getX();
        	float coinTop = (float) mGameManager.getCoins().get(i).getY();
        	
        	canvas.drawBitmap(coinImg, coinLeft, coinTop, null);
    	}
    	
    	// display player
    	float playerLeft = (float) mGameManager.getPlayer().getX();
    	float playerTop = (float) mGameManager.getPlayer().getY();
    	
    	canvas.drawBitmap(playerImg, playerLeft, playerTop, null);
    }

	private class GameUpdateTimerTask extends TimerTask {

		@Override
		public void run() {
			
			mGameManager.updateGame();

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
