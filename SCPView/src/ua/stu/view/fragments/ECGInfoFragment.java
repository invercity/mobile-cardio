package ua.stu.view.fragments;

import ua.stu.view.scpview.R;
import ua.stu.view.temporary.InfoO;
import android.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

public class ECGInfoFragment extends Fragment {
	/**
	 * Учреждения, записавающее ЕКГ
	 */
	private TextView orgWriteECG;
	/**
	 * Учреждения, анализирующее ЕКГ
	 */
	private TextView orgAnalysECG;
	/**
	 * Отдел принимавший ЕКГ
	 */
	private TextView depTakeECG;
	/**
	 * Отдел анализировавший ЕКГ
	 */
	private TextView depAnalysECG;
	/**
	 * Направляющие врачи
	 */
	private TextView guideDoctors;
	/**
	 * Подтверждающие врачи
	 */
	private TextView confirmDoctors;
	/**
	 * Дата получения ЕКГ
	 */
	private TextView dateECG;
	/**
	 * Время получения ЕКГ
	 */
	private TextView timeECG;
	
	private InfoO infoO;
	
	public ECGInfoFragment()
	{
		
	}
	
	public ECGInfoFragment(InfoO info)
	{
		super();
		
		this.infoO = info;
	}
	
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
		      Bundle savedInstanceState) 
	{
		View v = inflater.inflate(R.layout.ecg_info, null);
		//Fragment doesn't call onDestroy и onCreate
		setRetainInstance(true);
	    init(v);
		return v;
	}
	
	private final void init(View v)
	{
		this.setOrgWriteECG((TextView)v.findViewById(R.id.org_write_value));
		this.setOrgAnalysECG((TextView)v.findViewById(R.id.org_analys_value));
		this.setDepTakeECG((TextView)v.findViewById(R.id.dep_get_value));
		this.setDepAnalysECG((TextView)v.findViewById(R.id.dep_analys_value));
		this.setGuideDoctors((TextView)v.findViewById(R.id.go_doctor_value));
		this.setConfirmDoctors((TextView)v.findViewById(R.id.ok_doctor_value));
		this.setDateECG((TextView)v.findViewById(R.id.date_get_value));
		this.setTimeECG((TextView)v.findViewById(R.id.time_get_value));
		
		this.setOrgWriteECG(infoO.getAcquiringInstitutionDescription());
		this.setOrgAnalysECG(infoO.getAnalyzingInstitutionDescription());
		this.setDepTakeECG(infoO.getAcquiringDepartmentDescription());
		this.setDepAnalysECG(infoO.getAnalyzingDepartmentDescription());
		this.setGuideDoctors(infoO.getReferringPhysician());
		this.setConfirmDoctors(infoO.getLatestConfirmingPhysician());
		this.setDateECG(infoO.getDateOfAcquisition());
		this.setTimeECG(infoO.getTimeOfAcquisition());
	}

	private final void setOrgWriteECG(TextView orgWriteECG) {
		this.orgWriteECG = orgWriteECG;
	}

	public void setOrgWriteECG(CharSequence orgWriteECG) {
		if (!orgWriteECG.equals(""))
			this.orgWriteECG.setText(orgWriteECG);
	}
	
	private final void setOrgAnalysECG(TextView orgAnalysECG) {
		this.orgAnalysECG = orgAnalysECG;
	}

	public void setOrgAnalysECG(CharSequence orgAnalysECG) {
		if (!orgAnalysECG.equals(""))
			this.orgAnalysECG.setText(orgAnalysECG);
	}
	
	private final void setDepTakeECG(TextView depTakeECG) {
		if (!depTakeECG.equals(""))
			this.depTakeECG = depTakeECG;
	}

	public void setDepTakeECG(CharSequence depTakeECG) {
		if (!depTakeECG.equals(""))
			this.depTakeECG.setText(depTakeECG);
	}
	
	private final void setDepAnalysECG(TextView depAnalysECG) {
		this.depAnalysECG = depAnalysECG;
	}

	public void setDepAnalysECG(CharSequence depAnalysECG) {
		if (!depAnalysECG.equals(""))
			this.depAnalysECG.setText(depAnalysECG);
	}
	
	private final void setGuideDoctors(TextView guideDoctors) {
		this.guideDoctors = guideDoctors;
	}

	public void setGuideDoctors(CharSequence guideDoctors) {
		if (!guideDoctors.equals(""))
			this.guideDoctors.setText(guideDoctors);
	}
	
	private final void setConfirmDoctors(TextView confirmDoctors) {
		this.confirmDoctors = confirmDoctors;
	}

	public void setConfirmDoctors(CharSequence confirmDoctors) {
		if (!confirmDoctors.equals(""))
			this.confirmDoctors.setText(confirmDoctors);
	}
	
	private final void setDateECG(TextView dateECG) {
		this.dateECG = dateECG;
	}

	public void setDateECG(CharSequence dateECG) {
		if (!dateECG.equals(""))
			this.dateECG.setText(dateECG);
	}
	
	private final void setTimeECG(TextView timeECG) {
		this.timeECG = timeECG;
	}
	
	public void setTimeECG(CharSequence timeECG) {
		if (!timeECG.equals(""))
			this.timeECG.setText(timeECG);
	}
}
