package ua.stu.view.fragments;

import ua.stu.scpview.R;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

public class DiagnoseFragment extends Fragment {
	/**
	 * Диагноз или направления
	 */
	private TextView diagnose;
	
	public DiagnoseFragment()
	{
		
	}
	
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
		      Bundle savedInstanceState) 
	{
		View v = inflater.inflate(R.layout.diagnose, null);
		//Fragment doesn't call onDestroy и onCreate
		setRetainInstance(true);
	    init(v);
		return v;
	}
	
	private final void init(View v)
	{
		this.setDiagnose((TextView)v.findViewById(R.id.diagnose_patient_value));
		
	}

	private final void setDiagnose(TextView diagnose) {
		this.diagnose = diagnose;
	}
	
	public void setDiagnose(CharSequence diagnose) {
		if (!diagnose.equals(""))
			this.diagnose.setText(diagnose);
	}
}
