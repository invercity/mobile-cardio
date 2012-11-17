package ua.stu.view.fragments;

import ua.stu.view.scpview.R;
import android.os.Bundle;
import android.support.v4.app.Fragment;
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
	
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
		      Bundle savedInstanceState) 
	{
		View v = inflater.inflate(R.layout.ecg_info, null);
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
	}

	public TextView getOrgWriteECG() {
		return orgWriteECG;
	}

	private final void setOrgWriteECG(TextView orgWriteECG) {
		this.orgWriteECG = orgWriteECG;
	}

	public TextView getOrgAnalysECG() {
		return orgAnalysECG;
	}

	private final void setOrgAnalysECG(TextView orgAnalysECG) {
		this.orgAnalysECG = orgAnalysECG;
	}

	public TextView getDepTakeECG() {
		return depTakeECG;
	}

	private final void setDepTakeECG(TextView depTakeECG) {
		this.depTakeECG = depTakeECG;
	}

	public TextView getDepAnalysECG() {
		return depAnalysECG;
	}

	private final void setDepAnalysECG(TextView depAnalysECG) {
		this.depAnalysECG = depAnalysECG;
	}

	public TextView getGuideDoctors() {
		return guideDoctors;
	}

	private final void setGuideDoctors(TextView guideDoctors) {
		this.guideDoctors = guideDoctors;
	}

	public TextView getConfirmDoctors() {
		return confirmDoctors;
	}

	private final void setConfirmDoctors(TextView confirmDoctors) {
		this.confirmDoctors = confirmDoctors;
	}

	public TextView getDateECG() {
		return dateECG;
	}

	private final void setDateECG(TextView dateECG) {
		this.dateECG = dateECG;
	}

	public TextView getTimeECG() {
		return timeECG;
	}

	private final void setTimeECG(TextView timeECG) {
		this.timeECG = timeECG;
	}
}
