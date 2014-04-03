package ua.stu.scplib.tools;

import java.lang.Math;

import android.util.DisplayMetrics;

/*
 * Tool class for working with point coordinates during
 * touch events
 */
public class Scale {
	
	// first point coordinates
	private float x1;
	private float y1;
	// second point coordinates
	private float x2;
	private float y2;
	// delta value (for comparing)
	private float delta = 22;
	
	public Scale() {
		x1 = 0;
		x2 = 0;
		y1 = 0;
		y2 = 0;
	}
	
	public void makeBasicRect(int width, int height) {
		// rectangle size
		int rectSize = height/5;
		// 70 - draw channels width
		int midX = (width - 70)/2;
		int midY = height/2;
		int midRect = rectSize/2;
		x1 = midX - midRect;
		x2 = midX + midRect;
		y1 = midY - midRect;
		y2 = midY + midRect;
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
			float tx = x1;
			float ty = y1;
			x1 = (tx < x) ? tx : x;
			y1 = (ty < y) ? ty : y;
			x2 = (tx > x) ? tx : x;
			y2 = (ty > y) ? ty : y;
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
		return ((x > x1) && (x < x2) && (y < y2) && (y > y1));
	}
	
	// get distance
	private float heron(float a, float b, float c) {
		return (a + b + c)/2;
	}
	
	private float d (float x1, float y1, float x2, float y2) {
		return (float) Math.sqrt((x1 - x2)*(x1 - x2) + (y1 - y2) * (y1 - y2));
	}
	
	private float dist(float x, float y, float x1, float y1, float x2, float y2) {
		// distance of X1-X
		float a = d(x,y,x1,y1);
		// distance of X2-X
		float b = d(x,y,x2,y2);
		// distance of X1-X2
		float c = d(x1,y1,x2,y2);
		float p = heron(a,b,c);
		return (int) (2*Math.sqrt(p*(p-a)*(p-b)*(p-c))/c);
	}
	
	private float min(float a, float b, float c, float d) {
		float min = a;
		if (b < min) min = b;
		if (c < min) min = c;
		if (d < min) min = d;
		return min;
	}
	
	public boolean move(float x, float y) {
		if (!insideRect(x, y)) {
			if ((y < y2) && (y > y1)) {
				if (x > x2) x2 = x;
				else if (x < x1) x1 = x;
			}
			else if ((x < x2) && (x > x1)) {
				if (y > y2) y2 = y;
				else if (y < y1) y1 = y;
			}
			return true;
		}
		else {
			float l1 = dist(x, y, x1, y1, x2, y1);
			float l2 = dist(x, y, x2, y1, x2, y2);
			float l3 = dist(x, y, x2, y2, x1, y2);
			float l4 = dist(x, y, x1, y2, x1, y1);
			
			float m = min(l1,l2,l3,l4);
			
			if (m == l1) {
				y1 = y;
				return true;
			}
			else if (m == l2) {
				x2 = x;
				return true;
			}
			else if (m == l3) {
				y2 = y;
				return true;
			}
			else if (m == l4) {
				x1 = x;
				return true;
			}
		}
		return false;
	}
	
	public float getMidddleHeight() {
		return y1 + (y2 - y1)/2;
	}
	
	public float getMiddleWight() {
		return x1 + (x2 - x1)/2;
	}
	
	public float getMaxY() {
		return (y1 > y2) ? y1 : y2;
	}
	
	public float getMaxX() {
		return (x1 > x2) ? x1 : x2;
	}
}
