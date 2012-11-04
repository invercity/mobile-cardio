package ua.stu.scplib.graphic;

import and.awt.BasicStroke;
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


public class ECGPanel extends AwtView {
	private short[][] samples;
	private int numberOfChannels;
	private int nSamplesPerChannel;
	private int nTilesPerColumn;
	private int nTilesPerRow;
	private float samplingIntervalInMilliSeconds;
	private float[] amplitudeScalingFactorInMilliVolts;
	private String[] channelNames;
	private float widthOfPixelInMilliSeconds;
	private float heightOfPixelInMilliVolts;
	private float timeOffsetInMilliSeconds;
	private int displaySequence[];
	private int width;
	private int height;
	private boolean fillBackgroundFirst;
	
	public ECGPanel(Context context) {
		super(context);
		// TODO Auto-generated constructor stub
	}
	
	public ECGPanel(Context context, AttributeSet attribSet) {
		super(context, attribSet);
	}
	
	/**
	 * <p>Construct a component containing an array of tiles of ECG waveforms.</p>
	 *
	 * @param	samples					the ECG data as separate channels
	 * @param	numberOfChannels			the number of channels (leads)
	 * @param	nSamplesPerChannel			the number of samples per channel (same for all channels)
	 * @param	channelNames				the names of each channel with which to annotate them
	 * @param	nTilesPerColumn				the number of tiles to display per column
	 * @param	nTilesPerRow				the number of tiles to display per row (if 1, then nTilesPerColumn should == numberOfChannels)
	 * @param	samplingIntervalInMilliSeconds		the sampling interval (duration of each sample) in milliseconds
	 * @param	amplitudeScalingFactorInMilliVolts	how many millivolts per unit of sample data (may be different for each channel)
	 * @param	horizontalPixelsPerMilliSecond		how may pixels to use to represent one millisecond 
	 * @param	verticalPixelsPerMilliVolt		how may pixels to use to represent one millivolt
	 * @param	timeOffsetInMilliSeconds		how much of the sample data to skip, specified in milliseconds from the start of the samples
	 * @param	displaySequence				an array of indexes into samples (etc.) sorted into desired sequential display order
	 * @param	width					the width of the resulting component (sample data is truncated to fit if necessary)
	 * @param	height					the height of the resulting component (sample data is truncated to fit if necessary)
	 */
	public void setParameters (short[][] samples,int numberOfChannels,int nSamplesPerChannel,String[] channelNames,int nTilesPerColumn,int nTilesPerRow,
			float samplingIntervalInMilliSeconds,float[] amplitudeScalingFactorInMilliVolts,
			float horizontalPixelsPerMilliSecond,float verticalPixelsPerMilliVolt,
			float timeOffsetInMilliSeconds,int[] displaySequence,
			int width,int height,boolean fillBackgroundFirst) {
		this.samples=samples;
		this.numberOfChannels=numberOfChannels;
		this.nSamplesPerChannel=nSamplesPerChannel;
		this.channelNames=channelNames;
		this.nTilesPerColumn=nTilesPerColumn;
		this.nTilesPerRow=nTilesPerRow;
		this.samplingIntervalInMilliSeconds=samplingIntervalInMilliSeconds;
		this.amplitudeScalingFactorInMilliVolts=amplitudeScalingFactorInMilliVolts;
		this.widthOfPixelInMilliSeconds = 1/horizontalPixelsPerMilliSecond;
		this.heightOfPixelInMilliVolts = 1/verticalPixelsPerMilliVolt;
		this.timeOffsetInMilliSeconds=timeOffsetInMilliSeconds;
		this.displaySequence=displaySequence;
		this.width=width;
		this.height=height;
		this.fillBackgroundFirst = fillBackgroundFirst;
	}

	/**
	 * @param	g2
	 * @param	r
	 * @param	fillBackgroundFirst
	 */
	@Override
	public void paint(Graphics2D g2) {
		Color backgroundColor = Color.white;
		Color curveColor = Color.blue;
		Color boxColor = Color.black;
		Color gridColor = Color.red;
		Color channelNameColor = Color.black;
		
		float curveWidth = 1.5f;
		float boxWidth = 2;
		float gridWidth = 1;
		
		Font channelNameFont = new Font("SansSerif",0,14);
		
		int channelNameXOffset = 10;
		int channelNameYOffset = 20;
		
		g2.setBackground(backgroundColor);
		g2.setColor(backgroundColor);
		if (fillBackgroundFirst) {
			g2.fill(new Rectangle2D.Float(0,0,width,height));
		}
		
		float widthOfTileInPixels = (float)width/nTilesPerRow;
		float heightOfTileInPixels = (float)height/nTilesPerColumn;
		
		float widthOfTileInMilliSeconds = widthOfPixelInMilliSeconds*widthOfTileInPixels;
		float  heightOfTileInMilliVolts =  heightOfPixelInMilliVolts*heightOfTileInPixels;

		// first draw boxes around each tile, with anti-aliasing turned on (only way to get consistent thickness)
		g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING,RenderingHints.VALUE_ANTIALIAS_ON);

		g2.setColor(gridColor);

		float drawingOffsetY = 0;
		for (int row=0;row<nTilesPerColumn;++row) {
			float drawingOffsetX = 0;
			for (int col=0;col<nTilesPerRow;++col) {
				g2.setStroke(new BasicStroke(gridWidth));
				for (float time=0; time<widthOfTileInMilliSeconds; time+=200) {
					float x = drawingOffsetX+time/widthOfPixelInMilliSeconds;
					g2.draw(new Line2D.Float(x,drawingOffsetY,x,drawingOffsetY+heightOfTileInPixels));
				}

				g2.setStroke(new BasicStroke(gridWidth));
				for (float milliVolts=-heightOfTileInMilliVolts/2; milliVolts<=heightOfTileInMilliVolts/2; milliVolts+=0.5) {
					float y = drawingOffsetY + heightOfTileInPixels/2 + milliVolts/heightOfTileInMilliVolts*heightOfTileInPixels;
					g2.draw(new Line2D.Float(drawingOffsetX,y,drawingOffsetX+widthOfTileInPixels,y));

				}
				drawingOffsetX+=widthOfTileInPixels;
			}
			drawingOffsetY+=heightOfTileInPixels;
		}

		g2.setColor(boxColor);
		g2.setStroke(new BasicStroke(boxWidth));

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
				
				if (channelNames != null && channel < displaySequence.length && displaySequence[channel] < channelNames.length) {
					String channelName=channelNames[displaySequence[channel]];
					if (channelName != null) {
						g2.setColor(channelNameColor);
						g2.setFont(channelNameFont);
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
		g2.setStroke(new BasicStroke(curveWidth));

		float interceptY = heightOfTileInPixels/2;
		float widthOfSampleInPixels=samplingIntervalInMilliSeconds/widthOfPixelInMilliSeconds;
		int timeOffsetInSamples = (int)(timeOffsetInMilliSeconds/samplingIntervalInMilliSeconds);
		int widthOfTileInSamples = (int)(widthOfTileInMilliSeconds/samplingIntervalInMilliSeconds);
		int usableSamples = nSamplesPerChannel-timeOffsetInSamples;
		if (usableSamples <= 0) {
			//usableSamples=0;
			return;
		}
		else if (usableSamples > widthOfTileInSamples) {
			usableSamples=widthOfTileInSamples-1;
		}

		drawingOffsetY = 0;
		channel=0;
		GeneralPath thePath = new GeneralPath();
		for (int row=0;row<nTilesPerColumn && channel<numberOfChannels;++row) {
			float drawingOffsetX = 0;
			for (int col=0;col<nTilesPerRow && channel<numberOfChannels;++col) {
				float yOffset = drawingOffsetY + interceptY;
				short[] samplesForThisChannel = samples[displaySequence[channel]];
				int i = timeOffsetInSamples;
				float rescaleY =  amplitudeScalingFactorInMilliVolts[displaySequence[channel]]/heightOfPixelInMilliVolts;

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
	
	/**
	 * <p>For testing.</p>
	 *
	 * <p>Display the specified sample values as an array of tiles in a window,
	 * and take a snapshot of it as a JPEG file.</p>
	 *
	 * @param	arg	an argument selecting the input type ("RAW", "DICOM" or "SCPECG"), followed by
	 *			either 8 more arguments, the raw data filename (2 bytes per signed 16 bit sample interleaved),
	 *			the number of channels, the number of samples per channel, the number of tiles per column, the number of tiles per row,
	 *			the sampling interval in milliseconds, the amplitude scaling factor in millivolts,
	 *			and the time offset in milliseconds for the left edge of the display
	 * 			or 4 more arguments, the SCPECG or DICOM data filename,
	 *			the number of tiles per column, the number of tiles per row,
	 *			and the time offset in milliseconds for the left edge of the display
	 */
	public static void main(String arg[]) {
		/*try {
			SourceECG sourceECG = null;
			BinaryInputStream i = new BinaryInputStream(new BufferedInputStream(new FileInputStream(arg[1])),false);		// little endian
			int nTilesPerColumn = 0;
			int nTilesPerRow = 0;
			float widthOfTileInMilliSeconds = 0;
			float heightOfTileInMilliVolts = 0;
			float timeOffsetInMilliSeconds = 0;

			if (arg.length == 5) {
				nTilesPerColumn = Integer.parseInt(arg[2]);
				nTilesPerRow = Integer.parseInt(arg[3]);
				timeOffsetInMilliSeconds = Float.parseFloat(arg[4]);
				if (arg[0].toUpperCase().equals("SCPECG")) {
//System.err.println("ECGPanel.main(): about to create sourceECG from SCPECG");
					sourceECG = new SCPSourceECG(i,truederiveAdditionalLeads);
				}
			}
				
			// assume screen is 72 dpi aka 72/25.4 pixels/mm
			
			float milliMetresPerPixel = (float)(25.4/72);
//System.err.println("ECGPanel.main(): milliMetresPerPixel="+milliMetresPerPixel);

			// ECG's normally printed at 25mm/sec and 10 mm/mV
			
			float horizontalPixelsPerMilliSecond = 25/(1000*milliMetresPerPixel);
//System.err.println("ECGPanel.main(): horizontalPixelsPerMilliSecond="+horizontalPixelsPerMilliSecond);
			float verticalPixelsPerMilliVolt = 10/(milliMetresPerPixel);
//System.err.println("ECGPanel.main(): verticalPixelsPerMilliVolt="+verticalPixelsPerMilliVolt);

//System.err.println("ECGPanel.main(): about to create ECGPanel");
			ECGPanel pg = new ECGPanel(
				sourceECG.getSamples(),
				sourceECG.getNumberOfChannels(),
				sourceECG.getNumberOfSamplesPerChannel(),
				sourceECG.getChannelNames(),
				nTilesPerColumn,nTilesPerRow,
				sourceECG.getSamplingIntervalInMilliSeconds(),
				sourceECG.getAmplitudeScalingFactorInMilliVolts(),
				horizontalPixelsPerMilliSecond,verticalPixelsPerMilliVolt,
				timeOffsetInMilliSeconds,
				sourceECG.getDisplaySequence(),
				800,400);
				
			// set size ...
			pg.setPreferredSize(new Dimension(800,400));
			
			String title = sourceECG.getTitle();
//System.err.println("ECGPanel.main(): about to create frame");
			ApplicationFrame app = new ApplicationFrame(title == null ? "ECG Panel" : title);
			//ApplicationFrame app = new ApplicationFrame("Application");
//System.err.println("ECGPanel.main(): about to add content pane");
			app.getContentPane().add(pg);
//System.err.println("ECGPanel.main(): about to pack");
			app.pack();
//System.err.println("ECGPanel.main(): about to show");
			app.show();
			//app.takeSnapShot(app.getBounds());
		}
		catch (Exception e) {
			e.printStackTrace(System.err);
		}*/
	}
}

