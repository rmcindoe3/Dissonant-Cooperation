package com.mcindoe.dissonantcooperation.views;

import android.content.Context;
import android.util.AttributeSet;
import android.view.View;

import com.mcindoe.dissonantcooperation.controllers.GameManager;

public class GameView extends View {
	
	private GameManager mGameManager;

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

}
