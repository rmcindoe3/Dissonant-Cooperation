package com.mcindoe.dissonantcooperation.views;

import java.util.Timer;
import java.util.TimerTask;

import android.app.Activity;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.util.AttributeSet;
import android.view.View;

import com.mcindoe.dissonantcooperation.R;
import com.mcindoe.dissonantcooperation.controllers.GameManager;

public class GameView extends View {
	
	private Context mContext;

	private GameManager mGameManager;
	private Paint paint = new Paint();
	private Bitmap myImg = BitmapFactory.decodeResource(getResources(), R.drawable.one);
	
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
    	float left = (float) mGameManager.getPlayer().getX();
    	float top = (float) mGameManager.getPlayer().getY();
    	canvas.drawBitmap(myImg, left, top, null);
    }

	private class GameUpdateTimerTask extends TimerTask {

		@Override
		public void run() {
			
			mGameManager.updateGame();
			mGameManager.getPlayer().printPlayerInfo();

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
