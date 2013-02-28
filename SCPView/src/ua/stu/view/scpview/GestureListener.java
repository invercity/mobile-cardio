package ua.stu.view.scpview;

import net.pbdavey.awt.AwtView;
import android.view.MotionEvent;
import android.view.GestureDetector.SimpleOnGestureListener;

/**
 * Класс для совмесного управления скролом
 * 
 * @author ivan
 * 
 */
public class GestureListener extends SimpleOnGestureListener {
	private GraphicView graphic = null;
	private DrawChanels chanels = null;
	private float currentdistX = 0;
	private float currentdistY = 0;

	/**
	 * 
	 * @param v
	 *            - graphic
	 * @param v1
	 *            - chanels
	 */
	public GestureListener(GraphicView v, DrawChanels v1) {
		this.graphic = v;
		this.chanels = v1;
	}

	@Override
	public boolean onScroll(MotionEvent e1, MotionEvent e2, float distanceX,
			float distanceY)

	{
		/**
		 * обработчик для того чтобы скролл не полз вниз
		 */
		if (currentdistY + distanceY >= 0) {
			currentdistY += distanceY;
			chanels.scrollBy((int) 0, (int) distanceY);
			graphic.scrollBy((int) 0, (int) distanceY);
		} else {
			chanels.scrollBy((int) 0, (int) ((-1) * currentdistY));
			graphic.scrollBy((int) 0, (int) ((-1) * currentdistY));
			currentdistY = 0;
		}
		/**
		 * обработчик для того чтобы скролл не полз вправо для графика
		 */
		if (currentdistX + distanceX >= 0) {
			currentdistX += distanceX;
			graphic.scrollBy((int) distanceX, (int) 0);
			graphic.setTime(distanceX);
		} else {
			graphic.scrollBy((int) ((-1) * currentdistX), (int) 0);
			graphic.setTimeInNull();
			currentdistX = 0;
		}
		return true;
	}
}
