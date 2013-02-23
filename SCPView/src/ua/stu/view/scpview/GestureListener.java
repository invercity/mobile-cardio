package ua.stu.view.scpview;

import net.pbdavey.awt.AwtView;
import android.view.MotionEvent;
import android.view.GestureDetector.SimpleOnGestureListener;

public class GestureListener extends SimpleOnGestureListener{
	private AwtView v=null;
	private AwtView v1=null;
	public  GestureListener(AwtView v,AwtView v1){
		this.v=v;
		this.v1=v1;
	}
	@Override
	public boolean onScroll(MotionEvent e1, MotionEvent e2, float distanceX, float distanceY)
	{
	
		
	 v.scrollBy((int)distanceX, (int)distanceY);
	
	 v1.scrollBy((int)0, (int)distanceY);
		return true;
	}
//    @Override
//	public boolean onFling(MotionEvent e1, MotionEvent e2, float velocityX, float velocityY)
//	{
//			
//		scroller.fling( v1.getScrollX(),  v1.getScrollY(), -(int)velocityX, -(int)velocityY, 0,v1.getWidth() -  v1.getWidth(), 0, v1.getHeight() -  v1.getHeight());
//		 v1.awakenScrollBars(scroller.getDuration());
//
//		return true;
//	}
}
