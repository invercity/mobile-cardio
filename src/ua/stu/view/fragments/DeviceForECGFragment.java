package ua.stu.view.fragments;

import ua.stu.view.scpview.R;
import ua.stu.view.temporary.InfoO;
import android.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
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
	/**
	 * Модель
	 */
	private TextView model;
	/**
	 * Частота в сети
	 */
	private TextView frequency;
	/**
	 * Возможность печати
	 */
	private CheckBox print;
	/**
	 * Возможность анализировать
	 */
	private CheckBox analys;
	/**
	 * Возможность хранить
	 */
	private CheckBox store;
	/**
	 * Возможность приема
	 */
	private CheckBox receive;
	
	private InfoO infoO;
	
	public DeviceForECGFragment()
	{
		
	}
	
	public DeviceForECGFragment(InfoO info)
	{
		super();
		
		this.infoO = info;
	}
	
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
		      Bundle savedInstanceState) 
	{
		View v = inflater.inflate(R.layout.device_for_ecg, null);
		//Fragment doesn't call onDestroy и onCreate
		setRetainInstance(true);
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
		this.setPrint((CheckBox)v.findViewById(R.id.check_print_value));
		this.setAnalys((CheckBox)v.findViewById(R.id.check_analys_value));
		this.setStore((CheckBox)v.findViewById(R.id.check_save_value));
		this.setReceive((CheckBox)v.findViewById(R.id.check_take_value));
		this.setModel((TextView)v.findViewById(R.id.model_value));
		this.setFrequency((TextView)v.findViewById(R.id.hz_value));
		
		this.setMaker(infoO.getManufacturer());
		this.setOrgNum(infoO.getInstitutionNumber());
		this.setDepNum(infoO.getDepartmentNumber());
		this.setIdDev(infoO.getDeviceID());
		this.setTypeDev(infoO.getDeviceType());
		this.setSoftVersion(infoO.getVersionPO());
		this.setSerialNum(infoO.getSerialNumber());
		this.setSysSoft(infoO.getVersionPO());
		this.setSoftSCP(infoO.getPOSCP());
		this.setPrint(infoO.isPrint());
		this.setAnalys(infoO.isAnalysis());
		this.setReceive(infoO.isReceive());
		this.setStore(infoO.isStorage());
		this.setModel(infoO.getModel());
		this.setFrequency(infoO.getFrequency());
	}

	private final void setMaker(TextView maker) {
		this.maker = maker;
	}

	public void setMaker(CharSequence maker) {
		if (!maker.equals(""))
			this.maker.setText(maker);
	}

	private final void setOrgNum(TextView orgNum) {
		this.orgNum = orgNum;
	}

	public void setOrgNum(CharSequence orgNum) {
		if (!orgNum.equals(""))
			this.orgNum.setText(orgNum);
	}

	private final void setDepNum(TextView depNum) {
		this.depNum = depNum;
	}

	public void setDepNum(CharSequence depNum) {
		if (!depNum.equals(""))
			this.depNum.setText(depNum);
	}

	private final void setIdDev(TextView idDev) {
		this.idDev = idDev;
	}

	public void setIdDev(CharSequence idDev) {
		if (!idDev.equals(""))
			this.idDev.setText(idDev);
	}

	private final void setTypeDev(TextView typeDev) {
		this.typeDev = typeDev;
	}

	public void setTypeDev(CharSequence typeDev) {
		if (!typeDev.equals(""))
			this.typeDev.setText(typeDev);
	}

	private final void setSoftVersion(TextView softVersion) {
		this.softVersion = softVersion;
	}

	public void setSoftVersion(CharSequence softVersion) {
		if (!softVersion.equals(""))
			this.softVersion.setText(softVersion);
	}

	private final void setSerialNum(TextView serialNum) {
		this.serialNum = serialNum;
	}

	public void setSerialNum(CharSequence serialNum) {
		if (!serialNum.equals(""))
			this.serialNum.setText(serialNum);
	}

	private final void setSysSoft(TextView sysSoft) {
		this.sysSoft = sysSoft;
	}

	public void setSysSoft(CharSequence sysSoft) {
		if (!sysSoft.equals(""))
			this.sysSoft.setText(sysSoft);
	}

	private final void setSoftSCP(TextView softSCP) {
		this.softSCP = softSCP;
	}

	public void setSoftSCP(CharSequence softSCP) {
		if (!softSCP.equals(""))
			this.softSCP.setText(softSCP);
	}

	/**
	 * @param print the print to set
	 */
	private final void setPrint(CheckBox print) {
		this.print = print;
	}

	/**
	 * @param print the print to set
	 */
	public void setPrint(Boolean print) {
		this.print.setChecked(print);
	}
	
	/**
	 * @param analys the analys to set
	 */
	private final void setAnalys(CheckBox analys) {
		this.analys = analys;
	}

	/**
	 * @param analys the analys to set
	 */
	public void setAnalys(Boolean analys) {
		this.analys.setChecked(analys);
	}
	
	/**
	 * @param store the store to set
	 */
	private final void setStore(CheckBox store) {
		this.store = store;
	}

	/**
	 * @param store the store to set
	 */
	public void setStore(Boolean store) {
		this.store.setChecked(store);
	}
	
	/**
	 * @param receive CheckBox
	 */
	private final void setReceive(CheckBox receive) {
		this.receive = receive;
	}
	
	/**
	 * @param receive - Boolean
	 */
	public void setReceive(Boolean receive) {
		this.receive.setChecked(receive);
	}

	/**
	 * @param model the model to set
	 */
	private final void setModel(TextView model) {
		this.model = model;
	}

	/**
	 * @param model the model to set
	 */
	public void setModel(CharSequence model) {
		if (!model.equals(""))
			this.model.setText(model);
	}
	
	/**
	 * @param frequency the frequency to set
	 */
	private final void setFrequency(TextView frequency) {
		this.frequency = frequency;
	}
	
	/**
	 * @param frequency the frequency to set
	 */
	public void setFrequency(CharSequence frequency) {
		if (!frequency.equals(""))
			this.frequency.setText(frequency);
	}
}
