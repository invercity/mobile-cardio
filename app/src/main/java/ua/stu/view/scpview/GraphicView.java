package ua.stu.view.scpview;

import java.math.BigDecimal;
import java.math.RoundingMode;

import ua.stu.scplib.attribute.GraphicAttribute;
import ua.stu.scplib.attribute.GraphicAttributeBase;
import ua.stu.scplib.data.DataHandler;
import ua.stu.scplib.tools.Scale;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.Typeface;
import android.util.AttributeSet;
import android.util.Log;
import android.view.GestureDetector;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Scroller;
import android.widget.TextView;

public class GraphicView extends View {
	// object which holds all required data for drawing
	private DataHandler h = null;
	// class of channels
	private DrawChanels drawChanels = null;
	// scale function
	private Scale scale = new Scale();
	// window size param.
	private int W = 920;
	private int H = 600;
	// scroll window param.
	private int SW = 920;
	private int SH = 600;
	// display metrics
	private int displayHeight;
	private int displayWidht;
	private int displayDensity;
	// scale parameters
	private String scaleHeightValueText = "";
	private String scaleWidthValueText = "";
	// inch constant
	private float duim = (float) 25.4;
	// screen size in dpi
	private int sizeScreen = 126;
	// set of graphic attributes
	private GraphicAttributeBase g;
	// the number of tiles to display per column
	private int nTilesPerColumn = 12;
	// the number of tiles to display per row (if 1, then nTilesPerColumn should == numberOfChannels)
	private int nTilesPerRow = 1;
	// how may pixels to use to represent one millivolt
	private float yPixelsInMillivolts;
	// how may pixels to use to represent one millisecond
	private float xPixelsInMilliseconds;
	// how much of the sample data to skip, specified in milliseconds from the start of the samples
	private float timeOffsetInMilliSeconds;
	// color map
	private int curveColor = Color.BLUE;
	// array for channel offsets
	private float offsets[] = new float[12];
	// status bar variables
	private double speed = 0;
	private double gain = 0;
	private int time = 0;
	// X scroll offset
	private float xOffset = 0;
	// Y scroll offset
	private float yOffset = 0;
	// touch mode [DEFAULT]
	private int touchMode = GestureListener.MODE_BASIC;
	// flag - fill linear rectangle
	private boolean fillRect = false;
	// status bar 
	private TextView tvStatus = null;
	// invert flag
	private boolean invert = false;
	// touch gestures
	private GestureDetector gestureDetector;	
	// scroll
	private Scroller scroller;

	// paints and path
	private Paint ecgPaint;
	private Paint scalePaint;
	private Paint scaleTextPaint;
	private Path thePath = new Path();
		
	public void initscale(GestureListener mg) {
		gestureDetector = new GestureDetector(getContext(), mg);
		scroller = new Scroller(getContext());
	}
	
	public GraphicView(Context context) {
		super(context);
		initPaints();
		setHorizontalScrollBarEnabled(true);
		setVerticalScrollBarEnabled(true);
	}
	
	public GraphicView(Context context, AttributeSet attribSet) {
		super(context, attribSet);
		initPaints();
		setHorizontalScrollBarEnabled(true);
		setVerticalScrollBarEnabled(true);
	}

	private void initPaints() {
		ecgPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
		ecgPaint.setColor(curveColor);
		ecgPaint.setStrokeWidth(1.5f);
		ecgPaint.setStyle(Paint.Style.STROKE);

		scalePaint = new Paint(Paint.ANTI_ALIAS_FLAG);
		scalePaint.setColor(Color.GREEN);
		scalePaint.setStrokeWidth(3f);
		scalePaint.setStyle(Paint.Style.STROKE);

		scaleTextPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
		scaleTextPaint.setColor(Color.BLACK);
		scaleTextPaint.setTextSize(14 * getResources().getDisplayMetrics().density);
		scaleTextPaint.setTypeface(Typeface.SANS_SERIF);
	}
	
	// optional speed values
	private static double[] SPEEDS = {12.5, 25, 50, 100};
	// optional volt values
	private static double[] VOLTS = {2.5, 5, 10, 20};
	
	private int indexX;
	private int indexY;
	
	// gesture types
	private static final int NONE = -1;
	private static final int DRAG = 1;
	private static final int ZOOM = 2;
	private static final int EndZOOM = 3;	
	private static final int UP 	= 5;
	private static final int DOWN	= 6;
	private static final int CLICK = 10;
	
	private float oldDist;
	// gesture mode
	private int mode;
	
	@Override
	public boolean dispatchTouchEvent(MotionEvent event) {
		int actionMask = event.getActionMasked();
		switch (this.touchMode) {
			case GestureListener.MODE_BASIC:
				switch (actionMask) {
				case MotionEvent.ACTION_POINTER_DOWN:
					oldDist = spacing(event);
					if (oldDist > 10f) {
						mode = ZOOM;
					}
					break;
				case MotionEvent.ACTION_DOWN:
					mode = DRAG;
					break;
				case MotionEvent.ACTION_UP:
				case MotionEvent.ACTION_POINTER_UP:
					mode = NONE;
					break;
				case MotionEvent.ACTION_MOVE:
					if (mode == DRAG) {
						if (!scroller.isFinished()) scroller.abortAnimation();
					} else if (mode == ZOOM) {
						float newDist = spacing(event);
						if (newDist > 10f) {
							float scale = newDist / oldDist;
							if (this.ZoomIt(scale)) {
								invalidate();
								mode = EndZOOM;
							}
						}
					}
					break;
				}
				if ((mode == DRAG) || (mode == NONE)) {
					if (gestureDetector != null) gestureDetector.onTouchEvent(event);
				}
				break;
			case GestureListener.MODE_LINEAR:
				switch (actionMask) {
				case MotionEvent.ACTION_DOWN:
					mode = CLICK;
					break;
				case MotionEvent.ACTION_UP:
				case MotionEvent.ACTION_POINTER_UP:
					if (mode == CLICK) {
						if ((scale.insideRect(event.getX(), event.getY()))) {
							fillRect = !fillRect;
						}
						invalidate();
					}
					break;
				case MotionEvent.ACTION_MOVE:
					mode = NONE;
					boolean move = scale.move(event.getX(), event.getY());
					if (move) {
						invalidate();
						setTime(0);
					}
					break;
				}
				break;
		}
		return true;
	}
	
	private boolean ZoomIt(float Zoom) {
		if (Zoom > 1.2) {
			double y = nextY(DOWN);
			double x = nextX(UP);
			
			reDrawY(y);
			reDrawX(x);
			return true;
		} else if (Zoom < 0.8) {
			double x = nextX(DOWN);
			double y = nextY(UP);

			reDrawX(x);
			reDrawY(y);
			return true;
		}
		return false;
	}
	
	private float spacing(MotionEvent event) {
		if (event.getPointerCount() < 2) return 0f;
		float x = event.getX(0) - event.getX(1);
		float y = event.getY(0) - event.getY(1);
		return (float) Math.sqrt(x * x + y * y);
	}
	
	private double nextY(int mode) {
		double out = NONE;
		indexY = getIndexY(gain);
		if (mode == UP) {
			for (int i = 0; i < VOLTS.length; i++) {
				if (VOLTS[i] > gain) {
					out = VOLTS[i];
					break;
				}
			}
		} else if (mode == DOWN) {
			if ((indexY - 1) != NONE) out = VOLTS[indexY - 1];
			else out = VOLTS[0];
		}
		return out;
	}
	
	private double nextX(int mode) {
		double out = NONE;
		indexX = getIndexX(speed);
		if (mode == UP) {
			for (int i = 0; i < SPEEDS.length; i++) {
				if (SPEEDS[i] > speed) {
					out = SPEEDS[i];
					break;
				}
			}
		} else if (mode == DOWN) {
			if ((indexX - 1) != NONE) out = SPEEDS[indexX - 1];
			else out = SPEEDS[0];
		}
		return out;
	}
	
	private void reDrawY(double y) {
		if (y != NONE) this.setYScale((float) y);
	}
	
	private void reDrawX(double x) {
		if (x != NONE) this.setXScale((float) x);
	}
	
	private int getIndexX(double value) {
		int index = NONE;
		for (int i = 0; i < SPEEDS.length; i++) if (SPEEDS[i] == value) index = i;
		return index;
	}

	private int getIndexY(double value) {
		int index = NONE;
		for (int i = 0; i < VOLTS.length; i++) if (VOLTS[i] == value) index = i;
		return index;
	}
	
	public void init() {
		if (h != null) {
			g = h.getGraphic();
			if (drawChanels != null) drawChanels.setGraphicAttributeBase(g);	
		}
	}

	@Override
	protected void onDraw(Canvas canvas) {
		super.onDraw(canvas);
		if (h == null) return;
		init();
		drawECG(canvas);

		if ((this.touchMode == GestureListener.MODE_LINEAR) && (scale.isFull())) {
			thePath.reset();
			thePath.moveTo(scale.getX1() + xOffset, scale.getY1() + yOffset);
			thePath.lineTo(scale.getX2() + xOffset, scale.getY1() + yOffset);
			thePath.lineTo(scale.getX2() + xOffset, scale.getY2() + yOffset);
			thePath.lineTo(scale.getX1() + xOffset, scale.getY2() + yOffset);
			thePath.lineTo(scale.getX1() + xOffset, scale.getY1() + yOffset);

			scalePaint.setColor(Color.GREEN);
			scalePaint.setStrokeWidth(3f);
			canvas.drawPath(thePath, scalePaint);

			updateScaleParameters();

			canvas.drawText(scaleHeightValueText, scale.getMaxX() + 5 + xOffset, scale.getMidddleHeight() + yOffset, scaleTextPaint);
			canvas.drawText(scaleWidthValueText, scale.getMiddleWight() - 20 + xOffset, scale.getMaxY() + 15 + yOffset, scaleTextPaint);

			if (fillRect) {
				drawRect(canvas, scale.getRectTopX() + xOffset, scale.getRectTopY() + yOffset,
						scale.getRectW(), scale.getRectH());
			}
		}
	}
	
	private void updateScaleParameters() {
		double width = scale.getRectW() / (float) (displayDensity / duim);
		double height = scale.getRectH() / (float) (displayDensity / duim);
		width = new BigDecimal(width).setScale(2, RoundingMode.UP).doubleValue();
		height = new BigDecimal(height).setScale(2, RoundingMode.UP).doubleValue();
		scaleWidthValueText = String.valueOf(width) + " mm.";
		scaleHeightValueText = String.valueOf(height) + " mm.";
	}
	
	private void drawRect(Canvas canvas, float x, float y, float w, float h) {
		scalePaint.setColor(Color.GREEN);
		scalePaint.setStrokeWidth(1.5f);
		int frequency = 10;
		int cnt = (int) w / frequency;
		Path p = new Path();
		for (int i = 1; i < cnt + 1; i++) {
			p.moveTo(x + i * frequency, y);
			p.lineTo(x + i * frequency, y + h);
		}
		int cnt2 = (int) h / frequency;
		for (int i = 1; i < cnt2 + 1; i++) {
			p.moveTo(x, y + i * frequency);
			p.lineTo(x + w, y + i * frequency);
		}
		canvas.drawPath(p, scalePaint);
	}

	private void drawECG(Canvas canvas) {
		float widthOfTileInPixels = getW() / nTilesPerRow;
		float widthOfTileInMilliSeconds = widthOfTileInPixels / xPixelsInMilliseconds;
		
		ecgPaint.setColor(curveColor);

		float widthOfSampleInPixels = g.getSamplingIntervalInMilliSeconds() * xPixelsInMilliseconds;
		int timeOffsetInSamples = (int) (timeOffsetInMilliSeconds / g.getSamplingIntervalInMilliSeconds());
		int widthOfTileInSamples = (int) (widthOfTileInMilliSeconds / g.getSamplingIntervalInMilliSeconds());
		int usableSamples = g.getNumberOfSamplesPerChannel() - timeOffsetInSamples;

		if (usableSamples <= 0) return;
		else if (usableSamples > widthOfTileInSamples) usableSamples = widthOfTileInSamples - 1;

		float drawingOffsetY = 0;
		float currentYChannelOffset = 0;
		float maxY, minY;
		int channel = 0;
		float tile = yPixelsInMillivolts * duim / sizeScreen;
		float yIdealTiles = 4 * tile - (int) (tile / 2);
		float beforeY = 10;
		float borderBetweenChannelNames = 15;

		for (int row = 0; row < nTilesPerColumn && channel < g.getNumberOfChannels(); ++row) {
			maxY = -9999;
			minY = 9999;
			int k = timeOffsetInSamples;
			float curY = 0;
			short[] currenSamplesForThisChannel = g.getSamples()[g.getDisplaySequence()[channel]];
			float currentRescaleY = g.getAmplitudeScalingFactorInMilliVolts()[g.getDisplaySequence()[channel]] * yPixelsInMillivolts;

			for (int j = 1; j < usableSamples; ++j) {
				if (invert) curY = currenSamplesForThisChannel[k] * currentRescaleY;
				else curY = -currenSamplesForThisChannel[k] * currentRescaleY;
				if (curY < minY) minY = curY;
				if (curY > maxY) maxY = curY;
				k++;
			}

			currentYChannelOffset = drawingOffsetY - minY;
			for (int col = 0; col < nTilesPerRow && channel < g.getNumberOfChannels(); ++col) {
				short[] samplesForThisChannel = g.getSamples()[g.getDisplaySequence()[channel]];                        
				int i = timeOffsetInSamples;
				float rescaleY = g.getAmplitudeScalingFactorInMilliVolts()[g.getDisplaySequence()[channel]] * yPixelsInMillivolts;
				float fromXValue = 0;
				float fromYValue;
				if (invert) fromYValue = currentYChannelOffset + samplesForThisChannel[i] * rescaleY;
				else fromYValue = currentYChannelOffset - samplesForThisChannel[i] * rescaleY;

				float delta = 0;
				if (fromYValue > beforeY + (maxY - minY)) delta = beforeY + (maxY - minY) - fromYValue + 20;
				if (fromYValue < beforeY + yIdealTiles + borderBetweenChannelNames) delta = beforeY + yIdealTiles - fromYValue + borderBetweenChannelNames;
				beforeY = fromYValue + delta;
				if (col == 0) offsets[row] = fromYValue + delta;

				thePath.reset();
				thePath.moveTo(fromXValue, fromYValue + delta);
				++i;
				for (int j = 1; j < usableSamples; ++j) {
					float toXValue = fromXValue + widthOfSampleInPixels;
					float toYValue;
					if (invert) toYValue = currentYChannelOffset + samplesForThisChannel[i] * rescaleY + delta;
					else toYValue = currentYChannelOffset - samplesForThisChannel[i] * rescaleY + delta;                                        
					i++;
					if ((int) fromXValue != (int) toXValue || (int) fromYValue != (int) toYValue)
						thePath.lineTo(toXValue, toYValue);
					fromXValue = toXValue;
					fromYValue = toYValue;
				}
				canvas.drawPath(thePath, ecgPaint);
				++channel;
			}
			drawingOffsetY = (currentYChannelOffset + maxY);
		}
		currentYChannelOffset = 0;
		if (drawChanels != null) {
			drawChanels.setOffsets(offsets);
			drawChanels.invalidate();
		}
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
		if (drawChanels != null) {
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
	
	public void setH(DataHandler h) {
		this.h = h;
	}
	
	public void setSW(int sW) {
		SW = sW;
	}

	public void setSH(int sH) {
		SH = sH;
	}
	public int getSW() {
		return (SW);
	}

	public int getSH() {
		return (SH);
	}
	public int getW() {
		return (W);
	}

	public void setW(int w) {
		W = w;
	}

	public int getH() {
		return H;
	}

	public void setGraphicParameters(GraphicAttribute g) {
		this.g = g;
	}
	
	public void setYScale(float santimetersPerMillivolt) {
		float millimetersPerMillivolt = santimetersPerMillivolt / 10;
		this.yPixelsInMillivolts = (7 / millimetersPerMillivolt / (duim / sizeScreen));
		if (drawChanels != null) {
			drawChanels.setYScale(millimetersPerMillivolt);
			drawChanels.invalidate();
			this.gain = santimetersPerMillivolt;		
		}	
		updateStatus();
	}
	
	public void setXScale(float millimetersPerSecond) {
		this.xPixelsInMilliseconds = (float) ((millimetersPerSecond * (3.15 / 5) / (1000 * duim / sizeScreen)));
		this.W = (int) (millimetersPerSecond * 32);
		this.SW = (int) ((int) millimetersPerSecond * (32.65) + 50);	
		this.speed = millimetersPerSecond;
		updateStatus();
	}

	public void setInvert(boolean invert) {
		this.invert = invert;
		if (drawChanels != null) {
			drawChanels.setInvert(invert);
			drawChanels.invalidate();
		}
	}

	public void setDrawChanels(DrawChanels drawChanels) {
		this.drawChanels = drawChanels;
	}
	
	public void setTvStatus(TextView tvStatus) {
		this.tvStatus = tvStatus;
	}

	public void setGraphicColor(int c) {
		this.curveColor = c;
		if (ecgPaint != null) ecgPaint.setColor(c);
	}
	
	public Scroller getScroller() {
		return scroller;
	}
	
	public void setXScrollOffset(float distanse) {
		xOffset += distanse;
		setTime(distanse);
	}
	
	public void setYScrollOffset(float distanse) {
		yOffset += distanse;
	}
	
	private void updateStatus() {
		if (tvStatus != null && this.h != null) {
			String text = time + " from start " + speed + " mm/sec. " + gain + " mV/mm.";
			if (this.touchMode == GestureListener.MODE_LINEAR) 
				text += " \tScale: height " + scaleHeightValueText + ", width " + scaleWidthValueText;
			tvStatus.setText(text);
		}
	}
	
	private void setTime(float distanse) {
		float koef = (float) 2.05;
		if (h != null && g != null) {
			if (g.isFlNonsection2())
				this.time += (int) distanse * (((g.getNumberOfSamplesPerChannel()) * koef) / getW()) * 2;
			else this.time += (int) distanse * (((g.getNumberOfSamplesPerChannel()) * koef) / getW());
			updateStatus();
		}
	}	

	public void setXScrollOffsetNull() {
		if (h != null) {
			this.time = 0;
			updateStatus();
		}
	}
	
	public void setYScrollOffsetnull() {
		this.yOffset = 0;
	}
	
	public void setMode(int mode) {
		this.touchMode = mode;
		if (mode == GestureListener.MODE_BASIC) {
			scale.clear();
			scaleHeightValueText = "";
			scaleWidthValueText = "";
			fillRect = false;
			updateStatus();
		} else if (mode == GestureListener.MODE_LINEAR) {
			scale.makeBasicRect(displayWidht, displayHeight);
			updateScaleParameters();
			updateStatus();
		}
	}
	
	public void setDisplayMetrics(int width, int height, int density) {
		this.displayDensity = density;
		this.displayHeight = height;
		this.displayWidht = width;
	}
}