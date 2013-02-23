package ua.stu.view.scpview;

import net.pbdavey.awt.AwtView;
import android.view.MotionEvent;
import android.view.GestureDetector.SimpleOnGestureListener;
/**
 * Класс для совмесного управления скролом
 * @author ivan
 *
 */
public class GestureListener extends SimpleOnGestureListener{
	private GraphicView graphic=null;
	private DrawChanels chanels=null;
	/**
	 * 
	 * @param v - graphic
	 * @param v1 - chanels
	 */
	public  GestureListener(GraphicView v,DrawChanels v1){
		this.graphic=v;
		this.chanels=v1;
	}
	@Override
	public boolean onScroll(MotionEvent e1, MotionEvent e2, float distanceX, float distanceY)
	{	
			System.out.println("graphic.getScrollX()="+graphic.getScrollX());	
			System.out.println("graphic.getScrollY()="+graphic.getScrollY());
		if (graphic.getScrollX()>=0 && distanceX>=0){		
	 graphic.scrollBy((int)distanceX, (int)distanceY);	
	 graphic.setTime(distanceX);
	 chanels.scrollBy((int)0, (int)distanceY);
		}else if (graphic.getScrollX()>=0 && distanceX<0) {			
			 graphic.scrollBy((int)distanceX, (int)distanceY);	
			graphic.setTime(distanceX);
			 chanels.scrollBy((int)0, (int)distanceY);
		} else {
			graphic.scrollTo(0, graphic.getScrollY());
		}
		return true;
	}
}
