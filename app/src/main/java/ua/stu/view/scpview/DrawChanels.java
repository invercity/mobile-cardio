package ua.stu.view.scpview;

import ua.stu.scplib.attribute.GraphicAttributeBase;
import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Typeface;
import android.util.AttributeSet;
import android.view.GestureDetector;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Scroller;

/**
 * Клас для відображення назв каналів та еталонного імпульсу
 */
public class DrawChanels extends View {

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
	// set of graphic attributes
	private GraphicAttributeBase g = null;
	// array for channel offsets
	private float offsets[] = null;
	// the number of tiles to display per column
	private int nTilesPerColumn = 12;
	// the number of tiles to display per row (if 1, then nTilesPerColumn should == numberOfChannels)
	private int nTilesPerRow = 1;
	// how may pixels to use to represent one millivolt
	private float yPixelsInMillivolts;
	// color map
	private int curveColor = Color.BLUE;
	private int channelNameColor = Color.BLACK;

	// paints
	private Paint textPaint;
	private Paint linePaint;

	// scrolling
	private GestureDetector gestureDetector;	
	private Scroller scroller;
	// inversion flag
	private boolean isInvert = false;

	public DrawChanels(Context context) {
		super(context);
		initPaints();
	}

	public DrawChanels(Context context, AttributeSet attribSet) {
		super(context, attribSet);
		initPaints();
	}

	private void initPaints() {
		textPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
		textPaint.setColor(channelNameColor);
		textPaint.setTextSize(14 * getResources().getDisplayMetrics().density);
		textPaint.setTypeface(Typeface.SANS_SERIF);

		linePaint = new Paint(Paint.ANTI_ALIAS_FLAG);
		linePaint.setColor(curveColor);
		linePaint.setStrokeWidth(1.0f);
		linePaint.setStyle(Paint.Style.STROKE);
	}

	@Override
	public boolean onTouchEvent(MotionEvent event) {
		if ((event.getAction() & MotionEvent.ACTION_MASK) == MotionEvent.ACTION_DOWN) {
			if (scroller != null && !scroller.isFinished()) scroller.abortAnimation();
		}	
		// check for scroll gesture
		if (gestureDetector != null && gestureDetector.onTouchEvent(event)) return true;
		return true;
	}

	public void initscale(GestureListener mg) {
		gestureDetector = new GestureDetector(getContext(), mg);
		scroller = new Scroller(getContext());
		// init scrollbars
		setVerticalScrollBarEnabled(true);
		setHorizontalFadingEdgeEnabled(false);
		setHorizontalScrollBarEnabled(false);
	}

	@Override
	protected void onDraw(Canvas canvas) {
		super.onDraw(canvas);
		if (g == null || offsets == null) return;
		drawChannels(canvas);
	}

	public void drawChannels(Canvas canvas) {
		int channelNameXOffset = 10;
		float widthOfTileInPixels = W / nTilesPerRow;
		float heightOfTileInPixels = H / nTilesPerColumn;		
		int channel = 0;
		float tile = yPixelsInMillivolts * duim / sizeScreen;
		float yIdealTiles = 4 * tile - (int)(tile / 2);	

		textPaint.setColor(channelNameColor);
		linePaint.setColor(curveColor);

		for (int row = 0; row < nTilesPerColumn; ++row) {
			float drawingOffsetX = 0;
			for (int col = 0; col < nTilesPerRow; ++col) {
				if (g.getChannelNames() != null && channel < g.getDisplaySequence().length && 
						g.getDisplaySequence()[channel] < g.getChannelNames().length) {
					String channelName = g.getChannelNames()[g.getDisplaySequence()[channel]];
					if (channelName != null) {
						canvas.drawText(channelName, drawingOffsetX + channelNameXOffset, offsets[row], textPaint);

						// drawing ideal signal
						canvas.drawLine((int)(channelNameXOffset * 4 + heightOfTileInPixels / 8),
								(int)(offsets[row]),
								(int)(channelNameXOffset * 4 + heightOfTileInPixels / 4),
								(int)(offsets[row]), linePaint);

						if (!isInvert) {
							canvas.drawLine((int)(channelNameXOffset * 4 + heightOfTileInPixels / 4),
									(int)(offsets[row]),
									(int)(channelNameXOffset * 4 + heightOfTileInPixels / 4),
									(int)(offsets[row] - yIdealTiles), linePaint);
							canvas.drawLine((int)(channelNameXOffset * 4 + heightOfTileInPixels / 4),
									(int)(offsets[row] - yIdealTiles),
									(int)(channelNameXOffset * 4 + heightOfTileInPixels / 2),
									(int)(offsets[row] - yIdealTiles), linePaint);
							canvas.drawLine((int)(channelNameXOffset * 4 + heightOfTileInPixels / 2),
									(int)(offsets[row] - yIdealTiles),
									(int)(channelNameXOffset * 4 + heightOfTileInPixels / 2),
									(int)(offsets[row]), linePaint);
						} else {
							canvas.drawLine((int)(channelNameXOffset * 4 + heightOfTileInPixels / 4),
									(int)(offsets[row]),
									(int)(channelNameXOffset * 4 + heightOfTileInPixels / 4),
									(int)(offsets[row] + yIdealTiles), linePaint);
							canvas.drawLine((int)(channelNameXOffset * 4 + heightOfTileInPixels / 4),
									(int)(offsets[row] + yIdealTiles),
									(int)(channelNameXOffset * 4 + heightOfTileInPixels / 2),
									(int)(offsets[row] + yIdealTiles), linePaint);
							canvas.drawLine((int)(channelNameXOffset * 4 + heightOfTileInPixels / 2),
									(int)(offsets[row] + yIdealTiles),
									(int)(channelNameXOffset * 4 + heightOfTileInPixels / 2),
									(int)(offsets[row]), linePaint);
						}

						canvas.drawLine((int)(channelNameXOffset * 4 + heightOfTileInPixels / 2),
								(int)(offsets[row]),
								(int)(channelNameXOffset * 4 + 5 * heightOfTileInPixels / 8),
								(int)(offsets[row]), linePaint);
					}
				}
				drawingOffsetX += widthOfTileInPixels;
				++channel;
			}
		}
	}

	public void setGraphicAttributeBase(GraphicAttributeBase g) {
		this.g = g;
	}

	public void setYScale(float millimetersPerMillivolt) {		
		this.yPixelsInMillivolts = (7 / millimetersPerMillivolt / (duim / sizeScreen));
	}		
	
	public void setInvert(boolean invert) {
		this.isInvert = invert;
	}

	public void setyPixelsInMillivolts(int yPixelsInMillivolts) {
		this.yPixelsInMillivolts = yPixelsInMillivolts;
	}

	public void setGraphicColor(int c) {
		this.curveColor = c;
		if (linePaint != null) linePaint.setColor(c);
	}

	public void setChanelNameColor(int c) {
		this.channelNameColor = c;
		if (textPaint != null) textPaint.setColor(c);
	}

	public Scroller getScroller() {
		return scroller;
	}
	
	public void setOffsets(float[] arr) {
		offsets = arr.clone();
	}
}
