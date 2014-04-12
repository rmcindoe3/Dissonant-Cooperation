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
	private Bitmap myImg = BitmapFactory.decodeResource(getResources(), R.drawable.one);

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
    	float left = (float) mGameManager.getPlayer().getX();
    	float top = (float) mGameManager.getPlayer().getY();
    	canvas.drawBitmap(myImg, left, top, null);
    }
}
