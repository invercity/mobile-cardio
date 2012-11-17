package ua.stu.view.fragments;

import ua.stu.view.scpview.R;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

public class DeviceForECGFragment extends Fragment {
	/**
	 * Поле производитель
	 */
	private TextView maker;
	/**
	 * Номер организации
	 */
	private TextView orgNum;
	/**
	 * Номер отдела
	 */
	private TextView depNum;
	/**
	 * Id устройства
	 */
	private TextView idDev;
	/**
	 * Тип устройства
	 */
	private TextView typeDev;
	/**
	 * Версия анализирующего ПО
	 */
	private TextView softVersion;
	/**
	 * Серийный номер
	 */
	private TextView serialNum;
	/**
	 * Системное ПО
	 */
	private TextView sysSoft;
	/**
	 * ПО реализующее протокол SCP
	 */
	private TextView softSCP;
	
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
		      Bundle savedInstanceState) 
	{
		View v = inflater.inflate(R.layout.device_for_ecg, null);
	    init(v);
		return v;
	}
	
	private final void init(View v)
	{
		this.setMaker((TextView)v.findViewById(R.id.maker_value));
		this.setOrgNum((TextView)v.findViewById(R.id.org_num_value));
		this.setDepNum((TextView)v.findViewById(R.id.dep_num_value));
		this.setIdDev((TextView)v.findViewById(R.id.dev_id_value));
		this.setTypeDev((TextView)v.findViewById(R.id.dev_type_value));
		this.setSoftVersion((TextView)v.findViewById(R.id.soft_version_value));
		this.setSerialNum((TextView)v.findViewById(R.id.serial_num_value));
		this.setSysSoft((TextView)v.findViewById(R.id.sys_soft_value));
		this.setSoftSCP((TextView)v.findViewById(R.id.soft_scp_value));
	}
	
	public TextView getMaker() {
		return maker;
	}

	private final void setMaker(TextView maker) {
		this.maker = maker;
	}

	public void setMaker(CharSequence maker) {
		this.maker.setText(maker);
	}
	
	public TextView getOrgNum() {
		return orgNum;
	}

	private final void setOrgNum(TextView orgNum) {
		this.orgNum = orgNum;
	}

	public void setOrgNum(CharSequence orgNum) {
		this.orgNum.setText(orgNum);
	}
	
	public TextView getDepNum() {
		return depNum;
	}

	private final void setDepNum(TextView depNum) {
		this.depNum = depNum;
	}

	public void setDepNum(CharSequence depNum) {
		this.depNum.setText(depNum);
	}
	
	public TextView getIdDev() {
		return idDev;
	}

	private final void setIdDev(TextView idDev) {
		this.idDev = idDev;
	}

	public void setIdDev(CharSequence idDev) {
		this.idDev.setText(idDev);
	}

	public TextView getTypeDev() {
		return typeDev;
	}

	private final void setTypeDev(TextView typeDev) {
		this.typeDev = typeDev;
	}

	public void setTypeDev(CharSequence typeDev) {
		this.typeDev.setText(typeDev);
	}

	public TextView getSoftVersion() {
		return softVersion;
	}

	private final void setSoftVersion(TextView softVersion) {
		this.softVersion = softVersion;
	}

	public void setSoftVersion(CharSequence softVersion) {
		this.softVersion.setText(softVersion);
	}

	public TextView getSerialNum() {
		return serialNum;
	}

	private final void setSerialNum(TextView serialNum) {
		this.serialNum = serialNum;
	}

	public void setSerialNum(CharSequence serialNum) {
		this.serialNum.setText(serialNum);
	}

	public TextView getSysSoft() {
		return sysSoft;
	}

	private final void setSysSoft(TextView sysSoft) {
		this.sysSoft = sysSoft;
	}

	public void setSysSoft(CharSequence sysSoft) {
		this.sysSoft.setText(sysSoft);
	}

	public TextView getSoftSCP() {
		return softSCP;
	}

	private final void setSoftSCP(TextView softSCP) {
		this.softSCP = softSCP;
	}

	public void setSoftSCP(CharSequence softSCP) {
		this.softSCP.setText(softSCP);
	}
}
