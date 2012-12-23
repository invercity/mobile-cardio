/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package ua.stu.view.scpview;

import ua.stu.scpview.R;
import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.*;
import android.util.AttributeSet;
import android.view.View;
import android.view.GestureDetector;
import android.view.GestureDetector.*;
import android.view.MotionEvent;
import android.view.ScaleGestureDetector;
import android.view.ScaleGestureDetector.OnScaleGestureListener;
import android.widget.Scroller;

/**
 *
 * @author Ruslan Yanchyshyn, RII Research Labs
 */
public class ImageViewer extends View
{
	private Bitmap image = null;
	
	private final GestureDetector gestureDetector;
	private final ScaleGestureDetector scaleGestureDetector;
	private final Scroller scroller;
	
	private final Paint paint = new Paint();
	
	private float scaleFactor;
	
	public int getScaleFactor() {
		return (int)(scaleFactor*100);
	}

	private int getScaledWidth()
	{
		if (image!=null)
		return (int)(image.getWidth() * scaleFactor);
		else
			return 0;
	}
	
	private int getScaledHeight()
	{
		if (image!=null)
		return (int)(image.getHeight() * scaleFactor);
		else
			return 0;
	}
	
	public ImageViewer(Context context)
	{
		super(context);
		
		gestureDetector = new GestureDetector(context, new MyGestureListener());
		scaleGestureDetector = new ScaleGestureDetector(context, new MyScaleGestureListener());
		
		// init scrollbars
		setHorizontalScrollBarEnabled(true);
		setVerticalScrollBarEnabled(true);

		TypedArray a = context.obtainStyledAttributes(R.styleable.View);
		initializeScrollbars(a);
		a.recycle();
		
		scroller = new Scroller(context);
		
		paint.setFilterBitmap(true);
		paint.setDither(false);
	}
	public ImageViewer(Context context, AttributeSet attribSet)
	{
		super(context,attribSet);
		
		gestureDetector = new GestureDetector(context, new MyGestureListener());
		scaleGestureDetector = new ScaleGestureDetector(context, new MyScaleGestureListener());
		
		// init scrollbars
		setHorizontalScrollBarEnabled(true);
		setVerticalScrollBarEnabled(true);

		TypedArray a = context.obtainStyledAttributes(R.styleable.View);
		initializeScrollbars(a);
		a.recycle();
		
		scroller = new Scroller(context);
		
		paint.setFilterBitmap(true);
		paint.setDither(false);
	}
	@Override
	public void onDraw(Canvas canvas)
	{
		if (image!=null){
		Rect dst = new Rect(0, 0, getScaledWidth(), getScaledHeight());
		canvas.drawBitmap(image, null, dst, paint);		
		}
	}

	@Override
	protected int computeHorizontalScrollRange()
	{
		return getScaledWidth();
	}

	@Override
	protected int computeVerticalScrollRange()
	{
		return getScaledHeight();
	}
	
	@Override
	public boolean onTouchEvent(MotionEvent event)
	{
		// check for tap and cancel fling
		if ((event.getAction() & MotionEvent.ACTION_MASK) == MotionEvent.ACTION_DOWN)
		{
			if (!scroller.isFinished()) scroller.abortAnimation();
		}
		
		// handle pinch zoom gesture
		// don't check return value since it is always true
		scaleGestureDetector.onTouchEvent(event);
		
		// check for scroll gesture
		if (gestureDetector.onTouchEvent(event)) return true;

		// check for pointer release 
		if ((event.getPointerCount() == 1) && ((event.getAction() & MotionEvent.ACTION_MASK) == MotionEvent.ACTION_UP))
		{
			int newScrollX = getScrollX();
			if (getScaledWidth() < getWidth()) newScrollX = -(getWidth() - getScaledWidth()) / 2;
			else if (getScrollX() < 0) newScrollX = 0;
			else if (getScrollX() > getScaledWidth() - getWidth()) newScrollX = getScaledWidth() - getWidth();

			int newScrollY = getScrollY();
			if (getScaledHeight() < getHeight()) newScrollY = -(getHeight() - getScaledHeight()) / 2;
			else if (getScrollY() < 0) newScrollY = 0;
			else if (getScrollY() > getScaledHeight() - getHeight()) newScrollY = getScaledHeight() - getHeight();

			if ((newScrollX != getScrollX()) || (newScrollY != getScrollY()))
			{
				scroller.startScroll(getScrollX(), getScrollY(), newScrollX - getScrollX(), newScrollY - getScrollY());
				awakenScrollBars(scroller.getDuration());
			}
		}
		
		return true;
	}

	@Override
	public void computeScroll()
	{
		if (scroller.computeScrollOffset())
		{
			int oldX = getScrollX();
			int oldY = getScrollY();
			int x = scroller.getCurrX();
			int y = scroller.getCurrY();
			scrollTo(x, y);
			if (oldX != getScrollX() || oldY != getScrollY())
			{
				onScrollChanged(getScrollX(), getScrollY(), oldX, oldY);
			}

			postInvalidate();
		}
	}
	
	@Override
	protected void onScrollChanged(int x, int y, int oldX, int oldY)
	{
	}
	
	@Override
	protected void onSizeChanged(int width, int height, int oldWidth, int oldHeight)
	{
		//int scrollX = (getScaledWidth() < width ? -(width - getScaledWidth()) / 2 : getScaledWidth() / 2);
		//int scrollY = (getScaledHeight() < height ? -(height - getScaledHeight()) / 2 : getScaledHeight() / 2);
		int scrollY=0;
		int scrollX=0;
		scrollTo(scrollX, scrollY);
	}
	
	public void loadImage(Bitmap img)
	{
		/*BitmapFactory.Options options = new BitmapFactory.Options();
		options.inPreferredConfig = Bitmap.Config.ARGB_8888;*/
		
		//image = BitmapFactory.decodeFile(fileName, options);
		image=img;
		if (image == null) throw new NullPointerException("The image can't be decoded.");
		
		scaleFactor = 1;

		// center image on the screen
		int width = getWidth();
		int height = getHeight();
		if ((width != 0) || (height != 0))
		{
			//int scrollX = (image.getWidth() < width ? -(width - image.getWidth()) / 2 : image.getWidth() / 2);
			//int scrollY = (image.getHeight() < height ? -(height - image.getHeight()) / 2 : image.getHeight() / 2);
			int scrollY=0;
			int scrollX=0;
			scrollTo(scrollX, scrollY);
		}
	}
	
	private class MyGestureListener extends SimpleOnGestureListener
	{
		@Override
		public boolean onScroll(MotionEvent e1, MotionEvent e2, float distanceX, float distanceY)
		{
			scrollBy((int)distanceX, (int)distanceY);
			return true;
		}
		
		@Override
		public boolean onFling(MotionEvent e1, MotionEvent e2, float velocityX, float velocityY)
		{
			int fixedScrollX = 0, fixedScrollY = 0;
			int maxScrollX = getScaledWidth(), maxScrollY = getScaledHeight();
			
			if (getScaledWidth() < getWidth())
			{
				fixedScrollX = -(getWidth() - getScaledWidth()) / 2;
				maxScrollX = fixedScrollX + getScaledWidth();
			}
			
			if (getScaledHeight() < getHeight())
			{
				fixedScrollY = -(getHeight() - getScaledHeight()) / 2;
				maxScrollY = fixedScrollY + getScaledHeight();
			}

			boolean scrollBeyondImage = (fixedScrollX < 0) || (fixedScrollX > maxScrollX) || (fixedScrollY < 0) || (fixedScrollY > maxScrollY);
			if (scrollBeyondImage) return false;
			
			scroller.fling(getScrollX(), getScrollY(), -(int)velocityX, -(int)velocityY, 0, getScaledWidth() - getWidth(), 0, getScaledHeight() - getHeight());
			awakenScrollBars(scroller.getDuration());
			
			return true;
		}
	}

	private class MyScaleGestureListener implements OnScaleGestureListener
	{
		public boolean onScale(ScaleGestureDetector detector)
		{
			scaleFactor *= detector.getScaleFactor();

			int newScrollX = (int)((getScrollX() + detector.getFocusX()) * detector.getScaleFactor() - detector.getFocusX());
			int newScrollY = (int)((getScrollY() + detector.getFocusY()) * detector.getScaleFactor() - detector.getFocusY());
			scrollTo(newScrollX, newScrollY);
			
			invalidate();
			
			return true;
		}

		public boolean onScaleBegin(ScaleGestureDetector detector)
		{
			return true;
		}

		public void onScaleEnd(ScaleGestureDetector detector)
		{
		}
	}
}
