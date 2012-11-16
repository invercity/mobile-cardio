package ua.stu.view.fragments;

import ua.stu.view.scpview.R;
import android.annotation.TargetApi;
import android.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;


@TargetApi(11) 
public class PatientInfo extends Fragment
{
	
	private static String TAG = "PatientInfo";
	
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
		      Bundle savedInstanceState) 
	{
		View v = inflater.inflate(R.layout.patientinfo, null);

		init(v);
		
		return v;
	}

	private final void init (View v)
	{

	}
}
