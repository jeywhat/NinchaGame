package com.nincha.utils;

/**
 * Coord est utilisé pour définir les coordonnée(x,y) de chaque entity.
 * 
 * @author Nincha Corporation
 * @version 1.0
 */
public class Coord {

	private float x,y;
	
	public Coord(float x, float y)
	{
		this.x = x;
		this.y = y;
	}

	public float getX() {
		return x;
	}

	public void setX(float x) {
		this.x = x;
	}

	public float getY() {
		return y;
	}

	public void setY(float y) {
		this.y = y;
	}
}
