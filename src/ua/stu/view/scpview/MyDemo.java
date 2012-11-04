package ua.stu.view.scpview;

import java.util.Random;

import and.awt.BasicStroke;
import and.awt.Color;
import and.awt.Dimension;
import android.content.Context;
import android.util.AttributeSet;
import net.pbdavey.awt.AwtView;
import net.pbdavey.awt.Graphics2D;

public class MyDemo extends AwtView {

	public MyDemo(Context context) {
		super(context);
		// TODO Auto-generated constructor stub
	}
	
	public MyDemo(Context context, AttributeSet attribSet) {
		super(context, attribSet);
	}
	
	final static Color red = Color.red;
    final static Color bg = Color.white;//цвет фона
    final static Color fg = Color.black;//стандартный цвет
	
    public void init() {
        //Initialize drawing colors
        setBackground(bg);
        setForeground(fg);//
    }
	@Override
	public void paint(Graphics2D g2) {
		// TODO Auto-generated method stub
		Dimension d = getSize();

		g2.drawLine(0, d.height/2, d.width, d.height/2);
		g2.drawString("x", 2, d.height/2+10);
		g2.drawLine(d.width/2, 0, d.width/2, d.height);
		g2.drawString("y", d.width/2+2, 15);
		int xp=0;
    	int yp=d.height/2;
		int sin=0;
		Random r = new Random();
		g2.setStroke(new BasicStroke(2.0f));
		for (int i=1; i<d.width; i++) {
			Color col=new Color(r.nextInt(255),r.nextInt(255),r.nextInt(255));
			g2.setColor(col);
			sin=r.nextInt(d.height-d.height/2)+d.height/4;		
			g2.drawLine(xp, yp, i*5, sin);
			xp=i*5;
			yp=sin;
			System.out.println(yp);
		}
	}

}
