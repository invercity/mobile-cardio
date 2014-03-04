package ua.stu.scplib.tools;

import java.lang.Math;

/*
 * Tool class for working with point coordinates during
 * touch events
 */
public class PointBuffer {
	
	// first point coordinates
	private float x1;
	private float y1;
	// second point coordinates
	private float x2;
	private float y2;
	// delta value (for comparing)
	private float delta = 10;
	
	public PointBuffer() {
		x1 = 0;
		x2 = 0;
		y1 = 0;
		y2 = 0;
	}
	
	public void push(float x, float y) {
		float deltaX1 = (Math.abs(x - x1));
		float deltaX2 = (Math.abs(x - x2));
		float deltaY1 = (Math.abs(y - y1));
		float deltaY2 = (Math.abs(y - y2));
		// if first point buffer empty
		if ((x1 == 0) && (y1 == 0)) {
			x1 = x;
			y1 = y;
		}
		// if second point buffer empty, end current point is not equal to first
		else if ((x2 == 0) && (y2 == 0) && ((deltaX1 > delta) || (deltaY1 > delta))) {
			x2 = x;
			y2 = y;
		}
		else {
			// if this is point1 with new coordinates
			if ((deltaX1 < delta) && (deltaY1 < delta)) {
				x1 = x;
				y1 = y;
			}
			else if ((deltaX2 < delta) && (deltaY2 < delta)) {
				x2 = x;
				y2 = y;
			}
		}
	}
	
	public float getX1() {
		return x1;
	}
	
	public float getY1() {
		return y1;
	}
	
	public float getX2() {
		return x2;
	}
	
	public float getY2() {
		return y2;
	}
	
	public boolean isFull() {
		return ((x1 != 0) && (x2 != 0) && (y1 != 0) && (y2 != 0));
	}
	
	public void clear() {
		x1 = 0;
		x2 = 0;
		y1 = 0;
		y2 = 0;
	}
}
