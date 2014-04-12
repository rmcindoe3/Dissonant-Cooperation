package com.mcindoe.dissonantcooperation.models;

import android.util.Log;

public class Player extends GameObject {
	
	private int xdel, ydel;

	public Player(int x, int y, int height, int width) {
		super(x,y,height,width);
		setXdel(0);
		setYdel(0);
	}

	public int getXdel() {
		return xdel;
	}

	public void setXdel(int xdel) {
		this.xdel = xdel;
	}

	public int getYdel() {
		return ydel;
	}

	public void setYdel(int ydel) {
		this.ydel = ydel;
	}
	
	public void updatePosition() {
		x += 10*xdel;
		y += 10*ydel;
	}
	
	public void printPlayerInfo() {
		Log.d("DISS COOP", "X: " + x);
		Log.d("DISS COOP", "Y: " + y);
		Log.d("DISS COOP", "Xdel: " + xdel);
		Log.d("DISS COOP", "Ydel: " + ydel);
	}
	
}
