package ua.stu.view.scpview;

import ua.stu.scplib.attribute.GraphicAttributeBase;
import ua.stu.scplib.data.DataHandler;

import and.awt.BasicStroke;
import and.awt.Color;
import and.awt.geom.Line2D;
import android.content.Context;
import android.content.res.TypedArray;
import android.util.AttributeSet;
import android.view.GestureDetector;
import android.view.MotionEvent;
import android.view.GestureDetector.SimpleOnGestureListener;
import android.widget.Scroller;
import net.pbdavey.awt.AwtView;
import net.pbdavey.awt.Font;
import net.pbdavey.awt.Graphics2D;
import net.pbdavey.awt.RenderingHints;
/**
 * Класс для отрисовки именов каналов и единичного сигнала
 * 
 * @author ivan
 *
 */
public class DrawChanels extends AwtView{

	// window size params
	private int W = 70;
	private int H = 600;
	// scroll window params
	private int SW = 920;
	private int SH = 600;
	// inch constant
	private float duim = (float) 25.4;
	// screen size in dpi
	private int sizeScreen = 126;
	//set of graphic attributes
	private GraphicAttributeBase g=null;
	
	//the number of tiles to display per column
	private int nTilesPerColumn = 12;
	//the number of tiles to display per row (if 1, then nTilesPerColumn should == numberOfChannels)
	private int nTilesPerRow = 1;
	//how may pixels to use to represent one millivolt
	private float yPixelsInMillivolts;
	// color map
	Color curveColor = Color.blue;
	Color channelNameColor = Color.black;
	// basic font
	Font font = null;
	// any info?

	// scrolling
	private  GestureDetector gestureDetector;	
	private  Scroller scroller;

	@Override
    public boolean onTouchEvent(MotionEvent event)
    {
		// check for tap and cancel fling
		if ((event.getAction() & MotionEvent.ACTION_MASK) == MotionEvent.ACTION_DOWN)
		{
			if (!scroller.isFinished()) scroller.abortAnimation();
		}	
		// check for scroll gesture
		if (gestureDetector.onTouchEvent(event)) return true;
		return true;
    }

			
	private boolean invert=false;
	public void initscale(GestureListener mg){
		gestureDetector = new GestureDetector(getContext(),mg);
		scroller = new Scroller(getContext());

		// init scrollbars
        setVerticalScrollBarEnabled(true);
        setHorizontalFadingEdgeEnabled(false);
        setHorizontalScrollBarEnabled(false);
        TypedArray a = getContext().obtainStyledAttributes(R.styleable.View);       
        initializeScrollbars(a);
        a.recycle();
	}
	public DrawChanels(Context context) {
		super(context);
		
	}
	public DrawChanels(Context context, AttributeSet attribSet) {
		super(context, attribSet);

	}

	@Override
	public void paint(Graphics2D g2) {
		//g2.drawRect(0, 0, getWidth(), getHeight());	
		if (g==null) return;
		int channelNameXOffset = 10;
		
		int channelNameYOffset = 0;
		font=new Font("Ubuntu",0,(int) (14));		
		float widthOfTileInPixels = W/nTilesPerRow;
		float heightOfTileInPixels = H/nTilesPerColumn;		
		float drawingOffsetY = 0;
		int channel=0;
		g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING,RenderingHints.VALUE_ANTIALIAS_ON);	
		float tile = yPixelsInMillivolts*duim/sizeScreen;
		float yIdealTiles = 4*tile - (int)(tile/2);
		drawingOffsetY = 0;		
		for (int row=0;row<nTilesPerColumn;++row) {
			float drawingOffsetX = 0;
			for (int col=0;col<nTilesPerRow;++col) {
					g2.setStroke(new BasicStroke((float) 1.0));
			if (g.getChannelNames() != null && channel < g.getDisplaySequence().length && 
						g.getDisplaySequence()[channel] < g.getChannelNames().length) {
					String channelName=g.getChannelNames()[g.getDisplaySequence()[channel]];
					if (channelName != null) {
						g2.setStroke(new BasicStroke());
						g2.setColor(channelNameColor);
						g2.setFont(font);
						g2.drawString(channelName,drawingOffsetX+channelNameXOffset,drawingOffsetY+channelNameYOffset+25);
						g2.setColor(curveColor);
						// drawing ideal signal
						g2.drawLine((int)(channelNameXOffset*4 + heightOfTileInPixels/8)
								,(int)(heightOfTileInPixels/2 + channel*heightOfTileInPixels)
								,(int)(channelNameXOffset*4 + heightOfTileInPixels/4)
								,(int)(heightOfTileInPixels/2 + channel*heightOfTileInPixels));
						if (!invert) {
							g2.drawLine((int)(channelNameXOffset*4 + heightOfTileInPixels/4)
									,(int)(heightOfTileInPixels/2 + channel*heightOfTileInPixels)
									,(int)(channelNameXOffset*4 + heightOfTileInPixels/4)
									,(int)(heightOfTileInPixels/2 + channel*heightOfTileInPixels - yIdealTiles));
							g2.drawLine((int)(channelNameXOffset*4 + heightOfTileInPixels/4)
									,(int)(heightOfTileInPixels/2 + channel*heightOfTileInPixels - yIdealTiles)
									,(int)(channelNameXOffset*4 + heightOfTileInPixels/2)
									,(int)(heightOfTileInPixels/2 + channel*heightOfTileInPixels - yIdealTiles));
							g2.drawLine((int)(channelNameXOffset*4 + heightOfTileInPixels/2)
									,(int)(heightOfTileInPixels/2 + channel*heightOfTileInPixels - yIdealTiles)
									,(int)(channelNameXOffset*4 + heightOfTileInPixels/2)
									,(int)(heightOfTileInPixels/2 + channel*heightOfTileInPixels));
						}
						else {
							g2.drawLine((int)(channelNameXOffset*4 + heightOfTileInPixels/4)
									,(int)(heightOfTileInPixels/2 + channel*heightOfTileInPixels)
									,(int)(channelNameXOffset*4 + heightOfTileInPixels/4)
									,(int)(heightOfTileInPixels/2 + channel*heightOfTileInPixels + yIdealTiles));
							g2.drawLine((int)(channelNameXOffset*4 + heightOfTileInPixels/4)
									,(int)(heightOfTileInPixels/2 + channel*heightOfTileInPixels + yIdealTiles)
									,(int)(channelNameXOffset*4 + heightOfTileInPixels/2)
									,(int)(heightOfTileInPixels/2 + channel*heightOfTileInPixels + yIdealTiles));
							g2.drawLine((int)(channelNameXOffset*4 + heightOfTileInPixels/2)
									,(int)(heightOfTileInPixels/2 + channel*heightOfTileInPixels + yIdealTiles)
									,(int)(channelNameXOffset*4 + heightOfTileInPixels/2)
									,(int)(heightOfTileInPixels/2 + channel*heightOfTileInPixels));
						}
						g2.drawLine((int)(channelNameXOffset*4 + heightOfTileInPixels/2)
								,(int)(heightOfTileInPixels/2 + channel*heightOfTileInPixels)
								,(int)(channelNameXOffset*4 + 5*heightOfTileInPixels/8)
								,(int)(heightOfTileInPixels/2 + channel*heightOfTileInPixels));
					}
				}
				
				drawingOffsetX+=widthOfTileInPixels;
				++channel;
			}
			drawingOffsetY+=heightOfTileInPixels;
		}
		
	}
	public void setGraphicAttributeBase(GraphicAttributeBase g) {
		this.g=g;
	}
	public void setYScale(float millimetersPerMillivolt) {		
		this.yPixelsInMillivolts = (7/millimetersPerMillivolt/(duim/sizeScreen));
	}		
	
	public void setInvert(boolean invert) {
		this.invert = invert;
	}
	public void setyPixelsInMillivolts(int yPixelsInMillivolts) {
		this.yPixelsInMillivolts = yPixelsInMillivolts;
	}
	public void setGraphicColor(Color c){
		this.curveColor=c;
	}
	public void setChanelNameColor(Color c){
	this.channelNameColor=c;	
	}
	public Scroller getScroller() {
		return scroller;
	}
}
