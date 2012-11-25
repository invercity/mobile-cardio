package ua.stu.view.fragments;


import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;

import net.pbdavey.awt.Graphics2D;
import ua.stu.scplib.data.DataHandler;
import ua.stu.view.scpview.GraphicView;
import ua.stu.view.scpview.R;
import android.content.BroadcastReceiver;
import android.content.ComponentName;
import android.content.ContentResolver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.IntentSender;
import android.content.ServiceConnection;
import android.content.SharedPreferences;
import android.content.IntentSender.SendIntentException;
import android.content.pm.ApplicationInfo;
import android.content.pm.PackageManager;
import android.content.pm.PackageManager.NameNotFoundException;
import android.content.res.AssetManager;
import android.content.res.Resources;
import android.content.res.Resources.Theme;
import android.database.DatabaseErrorHandler;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteDatabase.CursorFactory;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.support.v4.app.Fragment;
import android.util.DisplayMetrics;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.SeekBar;
import android.widget.SeekBar.OnSeekBarChangeListener;
import android.widget.TextView;

public class ECGPanelFragment extends Fragment implements OnSeekBarChangeListener {
	
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
	private static int MAX_POWER = 24;
	
	private int speed;
	private int power;
	
	private SeekBar sliderSpeed;
	private SeekBar sliderPower;
	
	private TextView speedValue;
	private TextView powerValue;

	private GraphicView graphicView;
	
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
	
		speedValue = (TextView)view.findViewById(R.id.speed_value);
		powerValue = (TextView)view.findViewById(R.id.power_value);
		graphicView=(GraphicView)view.findViewById(R.id.ecgpanel);
		graphicView.setH(h);
		graphicView.setXScale(speed);
		graphicView.setYScale(power);
		
		//temporary
		speedValue.setText(getSpeed()+" mm/c");
		powerValue.setText(getPower()+" mV/cm");
		
		sliderSpeed = (SeekBar)view.findViewById(R.id.speed);
		sliderSpeed.setMax(MAX_SPEED);
		sliderSpeed.setProgress(getSpeed());
		sliderSpeed.incrementProgressBy(1);
		sliderSpeed.setOnSeekBarChangeListener(this);
		
		sliderPower = (SeekBar)view.findViewById(R.id.power);
		sliderPower.setMax(MAX_POWER);
		sliderPower.setProgress(getPower());
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

	public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
		// TODO Auto-generated method stub
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

	public void onStopTrackingTouch(SeekBar arg0) {
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

}
