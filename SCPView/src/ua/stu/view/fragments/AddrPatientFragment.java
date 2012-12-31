package ua.stu.view.fragments;

import ua.stu.view.scpview.R;
import ua.stu.view.temporary.InfoP;
import android.os.Bundle;
import android.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

public class AddrPatientFragment extends Fragment {

	/**
	 * Почтовый код
	 */
	private TextView postCode;
	/**
	 * Область
	 */
	private TextView region;
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
	private TextView house;
	/**
	 * Насеоенный пункт
	 */
	private TextView town;
	/**
	 * Время жизни
	 */
	private TextView TimeOfResidence;
	
	private InfoP infoP;
	
	public AddrPatientFragment()
	{
		
	}
	
	public AddrPatientFragment(InfoP info){
		super();
		
		this.infoP = info;
	}
	
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
		      Bundle savedInstanceState) 
	{
		View v = inflater.inflate(R.layout.addr_patient, null);
		//Fragment doesn't call onDestroy и onCreate
		setRetainInstance(true);
	    init(v);
		return v;
	}
	
	private final void init(View v)
	{
		this.setPostCode((TextView)v.findViewById(R.id.posta_code_patient_value));
		this.setRegion((TextView)v.findViewById(R.id.area_patient_value));
		this.setStreet((TextView)v.findViewById(R.id.street_patient_value));
		this.setDistrict((TextView)v.findViewById(R.id.zone_patient_value));
		this.setHouse((TextView)v.findViewById(R.id.house_patient_value));
		this.setTown((TextView)v.findViewById(R.id.nas_patient_value));
		this.setTimeOfResidence((TextView)v.findViewById(R.id.life_patient_value));
		
		this.setPostCode(infoP.getPostCode());
		this.setRegion(infoP.getRegion());
		this.setStreet(infoP.getStreet());
		this.setDistrict(infoP.getDistrict());
		this.setHouse(infoP.getHouse());
		this.setTown(infoP.getTown());
		this.setTimeOfResidence(infoP.getTimeOfresidence());
	}

	private final void setPostCode(TextView postCode) {
		this.postCode = postCode;
	}

	public void setPostCode(CharSequence postCode) {
		if (!postCode.equals(""))
			this.postCode.setText(postCode);
	}
	
	private final void setStreet(TextView street) {
		this.street = street;
	}

	public void setStreet(CharSequence street) {
		if (!street.equals(""))
			this.street.setText(street);
	}
	
	private final void setDistrict(TextView district) {
		this.district = district;
	}

	public void setDistrict(CharSequence district) {
		if (!district.equals(""))
			this.district.setText(district);
	}

	private final void setHouse(TextView house) {
		this.house = house;
	}

	public void setHouse(CharSequence house) {
		if (!house.equals(""))
		this.house.setText(house);
	}
	
	private final void setTown(TextView town) {
		this.town = town;
	}

	public void setTown(CharSequence town) {
		if (!town.equals(""))
			this.town.setText(town);
	}

	private final void setRegion(TextView region) {
		this.region = region;
	}
	
	public void setRegion(CharSequence region) {
		if (!region.equals(""))
			this.region.setText(region);
	}

	private final void setTimeOfResidence(TextView timeOfResidence) {
		TimeOfResidence = timeOfResidence;
	}
	
	public void setTimeOfResidence(CharSequence timeOfResidence) {
		if (!timeOfResidence.equals(""))
			TimeOfResidence.setText(timeOfResidence);
	}
}
