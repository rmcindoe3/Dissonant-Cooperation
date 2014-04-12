package com.mcindoe.dissonantcooperation.views;

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
	private Bitmap playerImg = BitmapFactory.decodeResource(getResources(), R.drawable.one);
	private Bitmap coinImg = BitmapFactory.decodeResource(getResources(), R.drawable.coin);

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
	}

	public void disconnect() {
		mGameManager.disconnect();
	}
	
    protected void onDraw(Canvas canvas) {
    	super.onDraw(canvas);
    	paint.setColor(Color.RED);
    	drawGameImg(canvas);
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
}
