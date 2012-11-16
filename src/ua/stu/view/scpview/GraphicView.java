package ua.stu.view.scpview;

import ua.stu.scplib.attribute.GraphicAttribute;
import ua.stu.scplib.attribute.GraphicAttributeBase;
import ua.stu.scplib.data.DataHandler;
import and.awt.Color;
import and.awt.geom.GeneralPath;
import and.awt.geom.Line2D;
import and.awt.geom.Rectangle2D;
import android.content.Context;
import android.util.AttributeSet;
import net.pbdavey.awt.AwtView;
import net.pbdavey.awt.Font;
import net.pbdavey.awt.Graphics2D;
import net.pbdavey.awt.RenderingHints;

public class GraphicView extends AwtView {
	//duim constant
	private float duim = (float) 25.4;
	// screen size in dpi
	private int sizeScreen = 72;
	//set of graphic attributes
	private GraphicAttributeBase g;
	//the number of tiles to display per column
	private int nTilesPerColumn;
	//the number of tiles to display per row (if 1, then nTilesPerColumn should == numberOfChannels)
	private int nTilesPerRow;
	//how may pixels to use to represent one millivolt
	private float yPixelsInMillivolts;
	//how may pixels to use to represent one millisecond
	private float xPixelsInMilliseconds;
	//how much of the sample data to skip, specified in milliseconds from the start of the samples
	private float timeOffsetInMilliSeconds;
	//Rectangle parameters
	private int width;
	private int height;
	//Color map
	Color backgroundColor;
	Color curveColor;
	Color boxColor;
	Color gridColor;
	Color channelNameColor;
	//Basic font
	Font font;
	private boolean fillBackgroundFirst;
	
	public GraphicView(Context context) {
		super(context);
	}
	
	public GraphicView(Context context, AttributeSet attribSet) {
		super(context, attribSet);
	}
	
	void setGraphicParameters(GraphicAttribute g) {
		this.g = g;
	}
	
	//default = 25
	public void setXScale(int millimetersPerSecond) {
		this.xPixelsInMilliseconds = millimetersPerSecond/(1000*duim/sizeScreen);
	}
	
	//default = 10
	public void setYScale(int millimetersPerMillivolt) {
		this.yPixelsInMillivolts = millimetersPerMillivolt/(duim/sizeScreen);
	}
	
	public int getXSCale() {
		return 0;
	}
	
	public int getYScale() {
		return 0;
	}
	
	public void setRectangle(int w, int h) {
		this.width = w;
		this.height = h;
	}
	
	public void init() {
		DataHandler h = new DataHandler("/mnt/sdcard/Example.scp");
		g = h.getGraphic();
		setnTilesPerColumn(12);
		setnTilesPerRow(1);
		setRectangle(400, 400);
		setXScale(25);
		setYScale(10);
		backgroundColor = Color.white;
		curveColor = Color.blue;
		boxColor = Color.black;
		gridColor = Color.red;
		channelNameColor = Color.black;
		font = new Font("SansSerif",0,14);
	}

	/**
	 * @param	g2
	 * @param	r
	 * @param	fillBackgroundFirst
	 */
	@Override
	public void paint(Graphics2D g2) {
		init();
		int channelNameXOffset = 10;
		int channelNameYOffset = 20;
		
		g2.setBackground(backgroundColor);
		g2.setColor(backgroundColor);
		  setBackground(backgroundColor);
	     // setForeground(backgroundColor);
		if (fillBackgroundFirst) {
			g2.fill(new Rectangle2D.Float(0,0,width,height));
		}
		
		float widthOfTileInPixels = (float)width/nTilesPerRow;
		float heightOfTileInPixels = (float)height/nTilesPerColumn;
		
		float widthOfTileInMilliSeconds = widthOfTileInPixels/xPixelsInMilliseconds;
		float heightOfTileInMilliVolts =  heightOfTileInPixels/yPixelsInMillivolts;

		// first draw boxes around each tile, with anti-aliasing turned on (only way to get consistent thickness)
		g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING,RenderingHints.VALUE_ANTIALIAS_ON);

		g2.setColor(gridColor);
		// setForeground(gridColor);
		float drawingOffsetY = 0;
		for (int row=0;row<nTilesPerColumn;++row) {
			float drawingOffsetX = 0;
			for (int col=0;col<nTilesPerRow;++col) {
				//g2.setStroke(new BasicStroke(gridWidth));				
				for (float time=0; time<widthOfTileInMilliSeconds; time+=200) {
					float x = drawingOffsetX+time*xPixelsInMilliseconds;
					g2.draw(new Line2D.Float(x,drawingOffsetY,x,drawingOffsetY+heightOfTileInPixels));
				}

			//	g2.setStroke(new BasicStroke(gridWidth));
				for (float milliVolts=-heightOfTileInMilliVolts/2; milliVolts<=heightOfTileInMilliVolts/2; milliVolts+=0.5) {
					float y = drawingOffsetY + heightOfTileInPixels/2 + milliVolts/heightOfTileInMilliVolts*heightOfTileInPixels;
					g2.draw(new Line2D.Float(drawingOffsetX,y,drawingOffsetX+widthOfTileInPixels,y));
				}
				drawingOffsetX+=widthOfTileInPixels;
			}
			drawingOffsetY+=heightOfTileInPixels;
		}

		g2.setColor(boxColor);
		 //setForeground(boxColor);
		//g2.setStroke(new BasicStroke(boxWidth));

		drawingOffsetY = 0;
		int channel=0;
		for (int row=0;row<nTilesPerColumn;++row) {
			float drawingOffsetX = 0;
			for (int col=0;col<nTilesPerRow;++col) {
				// Just drawing each bounding line once doesn't seem to help them sometimes
				// being thicker than others ... is this a stroke width problem (better if anti-aliasing on, but then too slow) ?
				//g2d.draw(new Rectangle2D.Double(drawingOffsetX,drawingOffsetY,drawingOffsetX+widthOfTile-1,drawingOffsetY+heightOfTile-1));
				if (row == 0)
					g2.draw(new Line2D.Float(drawingOffsetX,drawingOffsetY,drawingOffsetX+widthOfTileInPixels,drawingOffsetY));					// top
				if (col == 0)
					g2.draw(new Line2D.Float(drawingOffsetX,drawingOffsetY,drawingOffsetX,drawingOffsetY+heightOfTileInPixels));					// left
				g2.draw(new Line2D.Float(drawingOffsetX,drawingOffsetY+heightOfTileInPixels,drawingOffsetX+widthOfTileInPixels,drawingOffsetY+heightOfTileInPixels));	// bottom
				g2.draw(new Line2D.Float(drawingOffsetX+widthOfTileInPixels,drawingOffsetY,drawingOffsetX+widthOfTileInPixels,drawingOffsetY+heightOfTileInPixels));	// right
				
				if (g.getChannelNames() != null && channel < g.getDisplaySequence().length && 
						g.getDisplaySequence()[channel] < g.getChannelNames().length) {
					String channelName=g.getChannelNames()[g.getDisplaySequence()[channel]];
					if (channelName != null) {
						g2.setColor(channelNameColor);
						g2.setFont(font);
						g2.drawString(channelName,drawingOffsetX+channelNameXOffset,drawingOffsetY+channelNameYOffset);
					}
				}
				
				drawingOffsetX+=widthOfTileInPixels;
				++channel;
			}
			drawingOffsetY+=heightOfTileInPixels;
		}

		g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING,RenderingHints.VALUE_ANTIALIAS_ON);	// ugly without

		g2.setColor(curveColor);
		//setForeground(curveColor);
		//g2.setStroke(new BasicStroke(curveWidth));
		float interceptY = heightOfTileInPixels/2;
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
			float drawingOffsetX = 0;
			for (int col=0;col<nTilesPerRow && channel<g.getNumberOfChannels();++col) {
				float yOffset = drawingOffsetY + interceptY;
				short[] samplesForThisChannel = g.getSamples()[g.getDisplaySequence()[channel]];
				int i = timeOffsetInSamples;
				float rescaleY = g.getAmplitudeScalingFactorInMilliVolts()[g.getDisplaySequence()[channel]]*yPixelsInMillivolts;

				float fromXValue = drawingOffsetX;
				float fromYValue = yOffset - samplesForThisChannel[i]*rescaleY;
				thePath.reset();
				thePath.moveTo(fromXValue,fromYValue);
				++i;
				for (int j=1;j<usableSamples;++j) {
					float toXValue = fromXValue + widthOfSampleInPixels;
					float toYValue = yOffset - samplesForThisChannel[i]*rescaleY;
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

	public void setWidth(int width) {
		this.width = width;
	}

	public void setHeight(int height) {
		this.height = height;
	}

	public boolean isFillBackgroundFirst() {
		return fillBackgroundFirst;
	}

	public void setFillBackgroundFirst(boolean fillBackgroundFirst) {
		this.fillBackgroundFirst = fillBackgroundFirst;
	}
	
	public void setFont(Font font) {
		this.font = font;
	}
	
	public void setFont(String fontName) {
		this.font = new Font(fontName,0,14);
	}
	
}