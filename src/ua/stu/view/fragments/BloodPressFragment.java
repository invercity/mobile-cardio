package ua.stu.view.fragments;

import ua.stu.view.scpview.R;
import android.annotation.TargetApi;
import android.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

@TargetApi(11)
public class BloodPressFragment extends Fragment {
	/**
	 * Систолическое давление
	 */
	private TextView sysPress;
	/**
	 * Диастолическое давление
	 */
	private TextView dyaPress;
	
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
		      Bundle savedInstanceState) 
	{
		View v = inflater.inflate(R.layout.blood_press, null);
	    init(v);
		return v;
	}
	
	private final void init(View v)
	{
		this.setSysPress((TextView)v.findViewById(R.id.sys_press_patient_value));
		this.setDyaPress((TextView)v.findViewById(R.id.dya_press_patient_value));
	}
	
	public TextView getSysPress() {
		return sysPress;
	}
	private final void setSysPress(TextView sysPress) {
		this.sysPress = sysPress;
	}

	public TextView getDyaPress() {
		return dyaPress;
	}

	private final void setDyaPress(TextView dyaPress) {
		this.dyaPress = dyaPress;
	}
}
