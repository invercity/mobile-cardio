package ua.stu.view.scpview;

import android.app.Activity;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.DashPathEffect;
import android.graphics.Paint;
import android.util.AttributeSet;
import android.util.DisplayMetrics;
import android.view.View;
/**
 * Класс для прорисовки подложки(милеметровки).
 * Создается с помощью xml.
 * 
 * Перерисовывается при замене цветовой схемы с помощью вызова метода invalidate()
 * 
 * @author ivan
 *
 */
public class DrawGraphPaper extends View {
	public Paint mPaint = new Paint();
	private static final float InToSm = (float) 2.54;
	int Width = 0;
	int Height = 0;
	float Xcm, Ycm;
	private float[] mPts;
	Bitmap BackGround;
	int MetricsChanged = -1;
	int MaxSize = 1024;
	Canvas BkGr;
	private int colorLinesAndDot=Color.RED;

	public DrawGraphPaper(Context context) {
		super(context);
		GetMetrics();
		BackGround = Bitmap.createBitmap(MaxSize, MaxSize,
				Bitmap.Config.ARGB_8888);
		BkGr = new Canvas(BackGround);

		DrawBackGround(BkGr, MaxSize, MaxSize);
	}

	public DrawGraphPaper(Context context, AttributeSet attribSet) {
		super(context, attribSet);
		GetMetrics();
		BackGround = Bitmap.createBitmap(MaxSize, MaxSize,
				Bitmap.Config.ARGB_8888);
		BkGr = new Canvas(BackGround);
		DrawBackGround(BkGr, MaxSize, MaxSize);
	}

	private void MakeLines(float Coef, int W, int H) {
		float YBcm = (float) (Ycm * Coef);
		float XBcm = (float) (Xcm * Coef);
		int NumXLines = (int) (H / YBcm) + 1;
		int NumYLines = (int) (W / XBcm) + 1;
		mPts = new float[(NumXLines + NumYLines) * 4];
		for (int i = 0; i < NumXLines; i++) {
			mPts[i * 4] = 0;
			mPts[i * 4 + 1] = mPts[i * 4 + 3] = i * YBcm;
			mPts[i * 4 + 2] = Width;

		}
		for (int i = NumXLines; i < (NumXLines + NumYLines); i++) {
			mPts[i * 4 + 1] = 0;
			mPts[i * 4] = mPts[i * 4 + 2] = (i - NumXLines) * XBcm;
			mPts[i * 4 + 3] = Height;
		}
	}

	private int MakeDots(float Coef, int W, int H) {
		float YBcm = (float) (Ycm * Coef);
		float XBcm = (float) (Xcm * Coef);
		int Rows = (int) (H / YBcm) + 1;
		int Columns = (int) (W / XBcm) + 1;
		mPts = new float[((Rows - Rows / 5) * (Columns - Columns / 5)) * 2];
		int i = 0;
		for (int R = 0; R < Rows; R++) {
			if (R % 5 > 0)
				for (int C = 0; C < Columns; C++) {
					if (C % 5 > 0) {
						mPts[i++] = C * XBcm;
						mPts[i++] = R * YBcm;
					}
				}
		}
		return i;
	}

	private void DrawBackGround(Canvas canvas, int W, int H) {
		canvas.drawColor(Color.WHITE);
		Paint paint = mPaint;
		paint.setColor(getColorLinesAndDot());
		paint.setPathEffect(new DashPathEffect(new float[] { 8, 5 }, 0));
		paint.setStrokeWidth(0);
		MakeLines((float) 0.5, W, H);
		canvas.drawLines(mPts, paint);

		paint.setPathEffect(null);
		paint.setStrokeWidth(0);
		MakeLines((float) 2.5, W, H);
		canvas.drawLines(mPts, paint);

		MakeDots((float) 0.1, W, H);
		paint.setStrokeWidth(2);	
		canvas.drawPoints(mPts, paint);
	}

	private void GetMetrics() {
		DisplayMetrics metrics = new DisplayMetrics();
		((Activity) getContext()).getWindowManager().getDefaultDisplay()
				.getMetrics(metrics);
		Height = metrics.heightPixels;
		Width = metrics.widthPixels;
		Xcm = metrics.xdpi / InToSm;
		Ycm = metrics.ydpi / InToSm;
	}

	@Override
	protected void onDraw(Canvas canvas) {
		super.onDraw(canvas);
		GetMetrics();
		DrawBackGround(canvas, Width, Height);		
	}

	public int getColorLinesAndDot() {
		return colorLinesAndDot;
	}
/**
 * Установить цвет милиметровки после установки выполнить invalidate()
 * @param colorLinesAndDot цвет миллиметровки
 */
	public void setColorLinesAndDot(int colorLinesAndDot) {
		this.colorLinesAndDot = colorLinesAndDot;
	}
}
