package ua.stu.view.scpview;


import ua.stu.scplib.attribute.GraphicAttribute;
import ua.stu.scplib.attribute.GraphicAttributeBase;
import ua.stu.scplib.data.DataHandler;
import and.awt.BasicStroke;
import and.awt.Color;

import and.awt.geom.GeneralPath;
import and.awt.geom.Line2D;
import and.awt.geom.Rectangle2D;
import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.drawable.Drawable;
import android.util.AttributeSet;
import android.view.GestureDetector;
import android.view.GestureDetector.SimpleOnGestureListener;
import android.view.MotionEvent;
import android.view.ScaleGestureDetector.OnScaleGestureListener;
import android.widget.Scroller;
import android.widget.TextView;
import net.pbdavey.awt.AwtView;
import net.pbdavey.awt.Font;
import net.pbdavey.awt.Graphics2D;
import net.pbdavey.awt.RenderingHints;

public class GraphicView extends AwtView {
	// object which holds all required data for drawing
	private DataHandler h = null;
	//class of chanels
	private DrawChanels drawChanels=null;
	// window size params
	private int W = 920;
	private int H = 600;
	// scroll window params
	private int SW = 920;
	private int SH = 600;
	// inch constant
	private float duim = (float) 25.4;
	// screen size in dpi
	private int sizeScreen = 126;
	//set of graphic attributes
	private GraphicAttributeBase g;
	//the number of tiles to display per column
	private int nTilesPerColumn = 12;
	//the number of tiles to display per row (if 1, then nTilesPerColumn should == numberOfChannels)
	private int nTilesPerRow = 1;
	//how may pixels to use to represent one millivolt
	private float yPixelsInMillivolts;
	//how may pixels to use to represent one millisecond
	private float xPixelsInMilliseconds;
	//how much of the sample data to skip, specified in milliseconds from the start of the samples
	private float timeOffsetInMilliSeconds;
	//offset for graphic
	private float xTitlesOffset =2* sizeScreen/3;
	// how many pixels per mV use for grid
	private float xPixelsGrid;
	// how many pixels per mS use for grid
	private float yPixelsGrid;
	// color map
	Color curveColor = Color.blue;
	// basic font
	Font font = null;
	// any info?
	//переменны для вывода в строку состояния
	private double speed=0;
	private double gain=0;
	private double time=0;
	private TextView tvStatus=null;

	private boolean invert=false;
	/*
	 * Scrolling
	 */
	// scrolling
		private  GestureDetector gestureDetector;	

		private Scroller scroller;
		

		public void initscale(GestureListener mg){
			gestureDetector = new GestureDetector(getContext(),mg);
			scroller = new Scroller(getContext());

	
		}
	public GraphicView(Context context) {

		super(context);
		// init scrollbars
        setHorizontalScrollBarEnabled(true);
        setVerticalScrollBarEnabled(true);
		   TypedArray a = context.obtainStyledAttributes(R.styleable.View);
	        initializeScrollbars(a);
	        a.recycle();
	}
	
	
	public GraphicView(Context context, AttributeSet attribSet) {
		super(context, attribSet);
		// init scrollbars
        setHorizontalScrollBarEnabled(true);
        setVerticalScrollBarEnabled(true);
        TypedArray a = context.obtainStyledAttributes(R.styleable.View);
        initializeScrollbars(a);
        a.recycle();
        
	}
	
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

	 @Override
	    protected int computeHorizontalScrollRange()
	    {
	        return getSW();
	    }

	    @Override
	    protected int computeVerticalScrollRange()
	    {
	        return getSH();
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
  


		

	/*
	 * Initializing
	 */

	public void init() {
		if (h!=null) {
			g = h.getGraphic();
		  if (drawChanels!=null)
			 drawChanels.setGraphicAttributeBase(g);
			setYScaleGrid(5);
			setXScaleGrid((float) (12.5));			
		}
	}

	/**
	 * Drawing
	 */
	
	@Override
	public void paint(Graphics2D g2) {

		// check DataHandler
		if (h == null) return;
		init();
		font=new Font("Ubuntu",0,(int) (14));
		//g2.setColor(Color.RED);
		//	g2.drawRect(0, 0, getWidth(), getHeight());	
		float widthOfTileInPixels = getW()/nTilesPerRow;
		float heightOfTileInPixels = getH()/nTilesPerColumn;
		
		
		float widthOfTileInMilliSeconds = widthOfTileInPixels/xPixelsInMilliseconds;
		
		float drawingOffsetY = 0;
		int channel=0;
	
	
		g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING,RenderingHints.VALUE_ANTIALIAS_ON);	// ugly without

		g2.setColor(curveColor);
		//setForeground(curveColor);
		g2.setStroke(new BasicStroke((float) 0.7));
		float interceptY = heightOfTileInPixels/2;
		//System.out.println("SamplingIntervalInMilliSeconds");
		//System.out.println(g.getSamplingIntervalInMilliSeconds());
		//System.out.println("timeOffsetInMilliSeconds");
		//System.out.println(timeOffsetInMilliSeconds);
		float widthOfSampleInPixels=g.getSamplingIntervalInMilliSeconds()*xPixelsInMilliseconds;
		int timeOffsetInSamples = (int)(timeOffsetInMilliSeconds/g.getSamplingIntervalInMilliSeconds());
		int widthOfTileInSamples = (int)(widthOfTileInMilliSeconds/g.getSamplingIntervalInMilliSeconds());
		int usableSamples = g.getNumberOfSamplesPerChannel()-timeOffsetInSamples;
		if (usableSamples <= 0) {
			//usableSamples=0;
			return;
		}
		else if (usableSamples > widthOfTileInSamples) {
			usableSamples=widthOfTileInSamples-1;
		}

	drawingOffsetY = 0;
	 channel = 0;
		GeneralPath thePath = new GeneralPath();
		for (int row=0;row<nTilesPerColumn && channel<g.getNumberOfChannels();++row) {
			float drawingOffsetX = xTitlesOffset;

			for (int col=0;col<nTilesPerRow && channel<g.getNumberOfChannels();++col) {
				float yOffset = drawingOffsetY + interceptY;
				short[] samplesForThisChannel = g.getSamples()[g.getDisplaySequence()[channel]];				
				
				int i = timeOffsetInSamples;
				
				float rescaleY = g.getAmplitudeScalingFactorInMilliVolts()[g.getDisplaySequence()[channel]]*yPixelsInMillivolts;
				//float fromXValue = drawingOffsetX;
				//для того что-бы графики начинались с 0 
				float fromXValue = 0;
				
				float fromYValue;
				if (invert)
				 fromYValue = yOffset + samplesForThisChannel[i]*rescaleY;
				else
			  fromYValue = yOffset - samplesForThisChannel[i]*rescaleY;	
				thePath.reset();
				thePath.moveTo(fromXValue,fromYValue);
				++i;
				for (int j=1;j<usableSamples;++j) {
					float toXValue = fromXValue + widthOfSampleInPixels;
					float toYValue;
					if (invert)
					 toYValue = yOffset + samplesForThisChannel[i]*rescaleY;
					else 
					 toYValue = yOffset - samplesForThisChannel[i]*rescaleY;					
					i++;
					if ((int)fromXValue != (int)toXValue || (int)fromYValue != (int)toYValue) {
						thePath.lineTo(toXValue,toYValue);
					}
					fromXValue=toXValue;
					fromYValue=toYValue;
				}
				g2.draw(thePath);
				drawingOffsetX+=widthOfTileInPixels;
				++channel;
			}
			drawingOffsetY+=heightOfTileInPixels;
		}
		
		return;
	}

	/*
	 * Setters & getters
	 */
	
	public GraphicAttributeBase getG() {
		return g;
	}

	public void setG(GraphicAttributeBase g) {
		this.g = g;
	}

	public int getnTilesPerColumn() {
		return nTilesPerColumn;
	}

	public void setnTilesPerColumn(int nTilesPerColumn) {
		this.nTilesPerColumn = nTilesPerColumn;
	}

	public int getnTilesPerRow() {
		return nTilesPerRow;
	}

	public void setnTilesPerRow(int nTilesPerRow) {
		this.nTilesPerRow = nTilesPerRow;
	}

	public float getyPixelsInMillivolts() {
		return yPixelsInMillivolts;
	}

	public void setyPixelsInMillivolts(int yPixelsInMillivolts) {
		this.yPixelsInMillivolts = yPixelsInMillivolts;
		if (drawChanels!=null){
			drawChanels.setyPixelsInMillivolts(yPixelsInMillivolts);
			drawChanels.invalidate();
		}
	}

	public float getxPixelsInMilliseconds() {
		return xPixelsInMilliseconds;
	}

	public void setxPixelsInMilliseconds(int xPixelsInMilliseconds) {
		this.xPixelsInMilliseconds = xPixelsInMilliseconds;
	}

	public float getTimeOffsetInMilliSeconds() {
		return timeOffsetInMilliSeconds;
	}

	public void setTimeOffsetInMilliSeconds(float timeOffsetInMilliSeconds) {
		this.timeOffsetInMilliSeconds = timeOffsetInMilliSeconds;
	}
	
	public void setFont(Font font) {
		this.font = font;
	}
	
	public void setH(DataHandler h) {
		this.h = h;
	}
	
	public void setSW(int sW) {
		SW = sW;
	}

	public void setSH(int sH) {
		SH =sH;
	}
	public int getSW() {
		return  (int) (SW);
	}

	public int getSH() {
		return (int) (SH);
	}
	public int getW() {
		return (int) (W);
	}

	public void setW(int w) {
		W = w;
	}

	public int getH() {
		return H;
	}
	public void setFont(String fontName) {
		this.font = new Font(fontName,0,14);
	}
	
	void setGraphicParameters(GraphicAttribute g) {
		this.g = g;
	}
	
	public void setYScale(float santimetersPerMillivolt) {
		float millimetersPerMillivolt=santimetersPerMillivolt/10;
		this.yPixelsInMillivolts = (7/millimetersPerMillivolt/(duim/sizeScreen));
		if (drawChanels!=null){
		drawChanels.setYScale(millimetersPerMillivolt);
		drawChanels.invalidate();
		this.gain=santimetersPerMillivolt;		
		}	
		if (tvStatus!=null && this.h!=null)
			tvStatus.setText(time+" sec from start "+speed+" mm/sec. "+gain+" mV/mm");
	}
		
	public void setXScaleGrid(float millimetersPerSecond) {
		this.xPixelsGrid = millimetersPerSecond/(1000*duim/sizeScreen);	
	}
		
	public void setYScaleGrid(float millimetersPerMillivolt) {
		this.yPixelsGrid = millimetersPerMillivolt/(duim/sizeScreen);
	}

	
	public void setXScale(float millimetersPerSecond) {
		this.xPixelsInMilliseconds = (float)( (millimetersPerSecond*(3.15/5)/(1000*duim/sizeScreen)));
		this.W=(int) (millimetersPerSecond*32);
		this.SW=(int) ((int)millimetersPerSecond*(32.65)+50);	
		this.speed=millimetersPerSecond;
		if (tvStatus!=null && this.h!=null)
		tvStatus.setText(time+" sec from start "+speed+" mm/sec. "+gain+" mV/mm");
	
	}
	public void setInvert(boolean invert) {
		this.invert = invert;
		if (drawChanels!=null){
		drawChanels.setInvert(invert);
		drawChanels.invalidate();
		}
	}
	public void setDrawChanels(DrawChanels drawChanels){
		this.drawChanels=drawChanels;
	}
	
	public void setTvStatus(TextView tvStatus){
		this.tvStatus=tvStatus;
	}
	public void setGraphicColor(Color c){
		this.curveColor=c;
	}




	
	

}