package ua.stu.view.fragments;


import ua.stu.scpview.R;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

public class BloodPressFragment extends Fragment{
	/**
	 * Систолическое давление
	 */
	private TextView systolicBloodPressure;
	/**
	 * Диастолическое давление
	 */
	private TextView diastolicBloodPressure;
	
	
	public BloodPressFragment()
	{
		
	}
	
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
		      Bundle savedInstanceState) 
	{
		View v = inflater.inflate(R.layout.blood_press, null);
		//Fragment doesn't call onDestroy и onCreate
		setRetainInstance(true);
	    init(v);
		return v;
	}
	
	private final void init(View v)
	{
		this.setSystolicBloodPressure((TextView)v.findViewById(R.id.sys_press_patient_value));
		this.setDyaPress((TextView)v.findViewById(R.id.dya_press_patient_value));
	}

	private final void setSystolicBloodPressure(TextView sysPress) {
		this.systolicBloodPressure = sysPress;
	}

	public void setSysPress(CharSequence sysPress) {
		if (!sysPress.equals(""))
			this.systolicBloodPressure.setText(sysPress);
	}

	private final void setDyaPress(TextView dyaPress) {
		this.diastolicBloodPressure = dyaPress;
	}
	
	public void setDiastolicBloodPressure(CharSequence dyaPress) {
		if (!dyaPress.equals(""))
			this.diastolicBloodPressure.setText(dyaPress);
	}
}
