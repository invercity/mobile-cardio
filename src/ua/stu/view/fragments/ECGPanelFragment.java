package ua.stu.view.fragments;




import com.actionbarsherlock.app.SherlockFragment;

import ua.stu.scplib.data.DataHandler;
import ua.stu.view.scpview.GraphicView;
import ua.stu.view.scpview.R;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.DisplayMetrics;
import android.view.GestureDetector;
import android.view.GestureDetector.SimpleOnGestureListener;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.ScaleGestureDetector;
import android.view.ScaleGestureDetector.OnScaleGestureListener;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.View.OnTouchListener;
import android.view.ViewGroup;
import android.view.ViewGroup.LayoutParams;
import android.widget.CheckBox;
import android.widget.SeekBar;
import android.widget.SeekBar.OnSeekBarChangeListener;
import android.widget.TextView;

public class ECGPanelFragment extends Fragment implements OnSeekBarChangeListener{
	
	private DataHandler h;	
	
	/**
	 * Длина экрана
	 */
	private int displayWidth;
	/**
	 * Ширина экрана
	 */
	private int displayHeight;
	/**
	 * Максимальная скорость ЕКГ
	 */
	private static int MAX_SPEED = 100;
	/**
	 * Кореляция усиления для слайдера(потому что отображает только целый шаг)
	 */
	private static int CORRECTION_POWER = 4;
	/**
	 * Максимальное усиление ЕКГ
	 */
	private static int MAX_POWER = 16;
	
	private int speed=25;
	private int power=1;
	
	private SeekBar sliderSpeed;
	private SeekBar sliderPower;
	
	private TextView speedValue;
	private TextView powerValue;

	private GraphicView graphicView;

	private CheckBox invert;
	OnClickListener checkBoxListener;
	OnTouchListener graphicViewScaleListener;

	private TextView zoom;

	public ECGPanelFragment(DataHandler dh){
		this.h=dh;
	}
	
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,Bundle savedInstanceState)
	{
		tempInit();
		
		DisplayMetrics metrics = inflater.getContext().getResources().getDisplayMetrics();
		setDisplayWidth(metrics.widthPixels);
		setDisplayHeight(metrics.heightPixels);
		
		View view = inflater.inflate(R.layout.ecg_panel, null);
		//Fragment doesn't call onDestroy и onCreate
		setRetainInstance(true);
		speedValue = (TextView)view.findViewById(R.id.speed_value);
		powerValue = (TextView)view.findViewById(R.id.power_value);
		zoom = (TextView)view.findViewById(R.id.zoom);
		
		invert = (CheckBox)view.findViewById(R.id.check_revert_ecg);
		checkBoxListener=new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				if (invert.isChecked()){
	                graphicView.setInvert(true);
	            	graphicView.invalidate();				
			}else{
                graphicView.setInvert(false);
            	graphicView.invalidate();			
			}
			}
	
		};
		invert.setOnClickListener(checkBoxListener);
		
		graphicView=(GraphicView)view.findViewById(R.id.ecgpanel);
		graphicView.setH(h);
		graphicView.setXScale(speed);
		graphicView.setYScale((float)0.75);		
		zoom.setText("Увеличение: "+graphicView.getScaleFactor()+" %");	
		graphicViewScaleListener =new OnTouchListener() {
			
			@Override
			public boolean onTouch(View v, MotionEvent event) {
				zoom.setText("Увеличение: "+graphicView.getScaleFactor()+" %");				
				return false;
			
			}
		};		
		graphicView.setOnTouchListener(graphicViewScaleListener);
	    //temporary
		speedValue.setText(getSpeed()+" mm/c");
		powerValue.setText("0.75"+" mV/cm");
		
		sliderSpeed = (SeekBar)view.findViewById(R.id.speed);
		sliderSpeed.setMax(MAX_SPEED);
		sliderSpeed.setProgress(getSpeed());
		sliderSpeed.incrementProgressBy(1);
		sliderSpeed.setOnSeekBarChangeListener(this);
		
		sliderPower = (SeekBar)view.findViewById(R.id.power);
		sliderPower.setMax(MAX_POWER);
		sliderPower.setProgress(1);
		sliderSpeed.incrementProgressBy(1);
		sliderPower.setOnSeekBarChangeListener(this);
		
	    return view;
	    
	}

	public void tempInit()
	{
		setSpeed(25);
		setPower(5);
	}
	
	public int getDisplayWidth() {
		return displayWidth;
	}
	
	public void setDisplayWidth(int displayWidth) {
		this.displayWidth = displayWidth;
	}

	public int getDisplayHeight() {
		return displayHeight;
	}

	public void setDisplayHeight(int displayHeight) {
		this.displayHeight = displayHeight;
	}

	
	@Override
	public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) 
	{	
		float step = progress;
		if (fromUser)
		{
			switch(seekBar.getId())
			{
			case R.id.speed:
				speedValue.setText(progress+" mm/c");
				graphicView.setXScale(progress);
				graphicView.invalidate();
				break;
			case R.id.power:
				powerValue.setText(step/CORRECTION_POWER+" mV/cm");	
				graphicView.setYScale(step/CORRECTION_POWER);
				graphicView.invalidate();
				break;
			}
		}
	}
	
	public void onStartTrackingTouch(SeekBar arg0) {
		// TODO Auto-generated method stub
		
	}

	

	public int getSpeed() {
		return speed;
	}

	public void setSpeed(int speed) {
		this.speed = speed;
	}

	public int getPower() {
		return power;
	}

	public void setPower(int power) {
		this.power = power;
	}

	@Override
	public void onStopTrackingTouch(SeekBar seekBar) {
		// TODO Auto-generated method stub
		
	}

}
