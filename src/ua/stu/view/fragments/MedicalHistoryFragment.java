package ua.stu.view.fragments;

import ua.stu.view.scpview.R;
import ua.stu.view.temporary.InfoP;
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
	
	private InfoP infoP;
	
	public MedicalHistoryFragment(InfoP info)
	{
		super();
		
		this.infoP = info;
	}
	
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
		      Bundle savedInstanceState) 
	{
		View v = inflater.inflate(R.layout.medical_history, null);
	    init(v);
		return v;
	}
	
	private final void init(View v)
	{
		this.setMedicalHistory((TextView)v.findViewById(R.id.medical_history_value));
		
		this.setMedicalHistory(infoP.getMedicalHistory());
	}

	private final void setMedicalHistory(TextView medicalHistory) {
		this.medicalHistory = medicalHistory;
	}
	
	public void setMedicalHistory(CharSequence medicalHistory) {
		if (!medicalHistory.equals(""))
			this.medicalHistory.setText(medicalHistory);
	}
}
