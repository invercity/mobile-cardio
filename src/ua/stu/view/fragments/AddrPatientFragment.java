package ua.stu.view.fragments;

import ua.stu.view.scpview.R;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

public class AddrPatientFragment extends Fragment {

	/**
	 * Почтовый код
	 */
	private TextView postaNumber;
	/**
	 * Область
	 */
	private TextView province;
	/**
	 * Улица
	 */
	private TextView street;
	/**
	 * Район
	 */
	private TextView district;
	/**
	 * Номер дома
	 */
	private TextView numHouse;
	/**
	 * Насеоенный пункт
	 */
	private TextView locality;
	/**
	 * Время жизни
	 */
	private TextView timeLife;
	
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
		      Bundle savedInstanceState) 
	{
		View v = inflater.inflate(R.layout.addr_patient, null);
	    init(v);
		return v;
	}
	
	private final void init(View v)
	{
		this.setPostaNumber((TextView)v.findViewById(R.id.posta_code_patient_value));
		this.setProvince((TextView)v.findViewById(R.id.area_patient_value));
		this.setStreet((TextView)v.findViewById(R.id.street_patient_value));
		this.setDistrict((TextView)v.findViewById(R.id.zone_patient_value));
		this.setNumHouse((TextView)v.findViewById(R.id.house_patient_value));
		this.setLocality((TextView)v.findViewById(R.id.nas_patient_value));
		this.setTimeLife((TextView)v.findViewById(R.id.life_patient_value));
	}

	public TextView getPostaNumber() {
		return postaNumber;
	}

	private final void setPostaNumber(TextView postaNumber) {
		this.postaNumber = postaNumber;
	}

	public TextView getProvince() {
		return province;
	}

	private final void setProvince(TextView province) {
		this.province = province;
	}

	public TextView getStreet() {
		return street;
	}

	private final void setStreet(TextView street) {
		this.street = street;
	}

	public TextView getDistrict() {
		return district;
	}

	private final void setDistrict(TextView district) {
		this.district = district;
	}

	public TextView getNumHouse() {
		return numHouse;
	}

	private final void setNumHouse(TextView numHouse) {
		this.numHouse = numHouse;
	}

	public TextView getLocality() {
		return locality;
	}

	private final void setLocality(TextView locality) {
		this.locality = locality;
	}

	public TextView getTimeLife() {
		return timeLife;
	}

	private final void setTimeLife(TextView timeLife) {
		this.timeLife = timeLife;
	}
}
