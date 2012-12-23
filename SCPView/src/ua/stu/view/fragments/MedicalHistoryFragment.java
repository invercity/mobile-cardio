package ua.stu.view.fragments;

import ua.stu.scpview.R;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

public class MedicalHistoryFragment extends Fragment {
	/**
	 * История болезни
	 */
	private TextView medicalHistory;
	
	
	public MedicalHistoryFragment()
	{
		
	}
	
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
		      Bundle savedInstanceState) 
	{
		View v = inflater.inflate(R.layout.medical_history, null);
		//Fragment doesn't call onDestroy и onCreate
		setRetainInstance(true);
	    init(v);
		return v;
	}
	
	private final void init(View v)
	{
		this.setMedicalHistory((TextView)v.findViewById(R.id.medical_history_value));
	}

	private final void setMedicalHistory(TextView medicalHistory) {
		this.medicalHistory = medicalHistory;
	}
	
	private final void setMedicalHistory(CharSequence medicalHistory) {
		if (!medicalHistory.equals(""))
			this.medicalHistory.setText(medicalHistory);
	}
}
