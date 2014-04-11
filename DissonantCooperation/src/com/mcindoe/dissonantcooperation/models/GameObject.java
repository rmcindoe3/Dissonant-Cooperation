package com.mcindoe.dissonantcooperation.models;

public abstract class GameObject {
	
	private int x, y, height, width;
	
	public GameObject(int x, int y, int height, int width) {
		setX(x);
		setY(y);
		setHeight(height);
		setWidth(width);
	}
	
	public boolean isColliding(GameObject other) {
		
		if((x+width) > other.getX() && (x < (other.getX() + other.getWidth()))) {
			if((y+height) > other.getY() && y < (other.getY() + other.getHeight())) {
				return true;
			}
		}
		return false;
	}

	public int getX() {
		return x;
	}

	public void setX(int x) {
		this.x = x;
	}

	public int getY() {
		return y;
	}

	public void setY(int y) {
		this.y = y;
	}

	public int getHeight() {
		return height;
	}

	public void setHeight(int height) {
		this.height = height;
	}

	public int getWidth() {
		return width;
	}

	public void setWidth(int width) {
		this.width = width;
	}

}