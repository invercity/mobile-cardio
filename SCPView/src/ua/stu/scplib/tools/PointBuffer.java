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
	private float delta = 22;
	
	public PointBuffer() {
		x1 = 0;
		x2 = 0;
		y1 = 0;
		y2 = 0;
	}
	
	public boolean push(float x, float y) {
		float deltaX1 = (Math.abs(x - x1));
		float deltaY1 = (Math.abs(y - y1));
		// if first point buffer empty
		if ((x1 == 0) && (y1 == 0)) {
			x1 = x;
			y1 = y;
			return true;
		}
		// if second point buffer empty, end current point is not equal to first
		else if ((x2 == 0) && (y2 == 0) && ((deltaX1 > delta) || (deltaY1 > delta))) {
			x2 = x;
			y2 = y;
			return true;
		}
		return false;
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
	
	public float getRectTopX() {
		if (x1 < x2) return x1;
		return x2;
	}
	
	public float getRectTopY() {
		if (y1 < y2) return y1;
		return y2;
	}
	
	public float getRectW() {
		return Math.abs(x1 - x2);
	}
	
	public float getRectH() {
		return Math.abs(y1 - y2);
	}
	
	public boolean insideRect(float x, float y) {
		float minX = (x1 < x2) ? x1 : x2;
		float minY = (y1 < y2) ? y1 : y2;
		float maxX = (x1 > x2) ? x1 : x2;
		float maxY = (y1 > y2) ? y1 : y2;
		if ((x > minX) && (x < maxX) && (y < maxY) && (y > minY)) return true;
		return false;
	}
	
	public boolean move(float x, float y) {
		float minX = (x1 < x2) ? x1 : x2;
		float minY = (y1 < y2) ? y1 : y2;
		float maxX = (x1 > x2) ? x1 : x2;
		float maxY = (y1 > y2) ? y1 : y2;
		/*
		 * 			1
		 * ------------------
		 * |				|
		 * | 4			  2	|
		 * |		3		|
		 * ------------------
		 * 0 - out of the rectangle
		 */
		// 1
		if ((x < maxX) && (x > minX) && (Math.abs(y - minY) < delta)) {
			if ((Math.abs(x - x1) < delta) || (Math.abs(y - y1) < delta)) y1 = y;
			else y2 = y;
			return true;
		}
		// 2
		else if ((y > minY) && (y < maxY) && (Math.abs(x - maxX) < delta)) {
			if ((Math.abs(x - x1) < delta) || (Math.abs(y - y1) < delta)) x1 = x;
			else x2 = x;
			return true;
		}
		else if ((x < maxX) && (x > minX) && (Math.abs(y - maxY) < delta)) {
			if ((Math.abs(x - x1) < delta) || (Math.abs(y - y1) < delta)) y1 = y;
			else y2 = y;
			return true;
		}
		else if ((y > minY) && (y < maxY) && (Math.abs(x - minX) < delta)) {
			if ((Math.abs(x - x1) < delta) || (Math.abs(y - y1) < delta)) x1 = x;
			else x2 = x;
			return true;
		}
		return false;
	}
	
	public float getMidddleHeight() {
		float minY = (y1 < y2) ? y1 : y2;
		float maxY = (y1 > y2) ? y1 : y2;
		return minY + (maxY - minY)/2;
	}
	
	public float getMiddleWight() {
		float minX = (x1 < x2) ? x1 : x2;
		float maxX = (x1 > x2) ? x1 : x2;
		return minX + (maxX - minX)/2;
	}
	
	public float getMaxY() {
		return (y1 > y2) ? y1 : y2;
	}
	
	public float getMaxX() {
		return (x1 > x2) ? x1 : x2;
	}
}
