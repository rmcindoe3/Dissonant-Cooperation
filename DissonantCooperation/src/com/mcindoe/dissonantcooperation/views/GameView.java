package com.mcindoe.dissonantcooperation.views;

import java.util.Timer;
import java.util.TimerTask;

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

	private GameManager mGameManager;
	private Paint paint = new Paint();
	private Bitmap myImg = BitmapFactory.decodeResource(getResources(), R.drawable.one);
	
	private Timer mGameUpdateTimer;

	public GameView(Context context, AttributeSet attrs, int defStyleAttr) {
		super(context, attrs, defStyleAttr);
	}

	public GameView(Context context, AttributeSet attrs) {
		super(context, attrs);
	}

	public GameView(Context context) {
		super(context);
	}

	public void setGameManager(GameManager gm) {
		mGameManager = gm;
		mGameManager.setDimensions(getHeight(), getWidth());
	}

	public void disconnect() {
		mGameManager.disconnect();
	}

	protected void onDraw(Canvas canvas) {
		super.onDraw(canvas);
		paint.setColor(Color.RED);
		canvas.drawRect(40, 40, 80, 80, paint);
		drawGameImg(canvas, getWidth(), getHeight());
		
		mGameUpdateTimer.schedule(new GameUpdateTimerTask(), 100);
	}

	private void drawGameImg(Canvas canvas, int viewWidth, int viewHeight) {
		float left = (float) Math.random() * viewWidth;
		float top = (float) Math.random() * viewHeight;
		canvas.drawBitmap(myImg, left, top, null);
	}
	
	private class GameUpdateTimerTask extends TimerTask {

		@Override
		public void run() {
			
			mGameManager.updateGame();
			invalidate();
		}
	}
}
