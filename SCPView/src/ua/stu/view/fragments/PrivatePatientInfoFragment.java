package ua.stu.view.fragments;

import ua.stu.view.scpview.R;
import ua.stu.view.temporary.InfoP;
import android.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

public class PrivatePatientInfoFragment extends Fragment{
	
	private static String TAG = "PrivatePatientInfoFragment";
	
	/**
	 * ID пациента
	 */
	private TextView idPatient;
	/**
	 * Фамилия пациента
	 */
	private TextView lastName;
	/**
	 * Возраст пациента
	 */
	private TextView age;
	/**
	 * Имя пациента
	 */
	private TextView firstName;
	/**
	 * Отчество пациента
	 */
	private TextView fatherName;
	/**
	 * Дата рождения пациента
	 */
	private TextView birthDate;
	/**
	 * Рост пациента
	 */
	private TextView height;
	/**
	 * Вес пациента
	 */
	private TextView weight;
	/**
	 * Пол пациента
	 */
	private TextView sex;
	/**
	 * Раса пациента
	 */
	private TextView race;
	
	private InfoP infoP;
	
	public PrivatePatientInfoFragment()
	{
		
	}
	
	public PrivatePatientInfoFragment(InfoP info)
	{
		super();
		
		this.infoP = info;
	}
	
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
		      Bundle savedInstanceState) 
	{
		View v = inflater.inflate(R.layout.privatepatientinfo, null);
		//Fragment doesn't call onDestroy и onCreate
		setRetainInstance(true);
	    init(v);
		return v;
	}
	
	private final void init(View v)
	{
		this.setIdPatient((TextView)v.findViewById(R.id.id_patient_value));
		this.setLastName((TextView)v.findViewById(R.id.surname_patient_value));
		this.setFirstName((TextView)v.findViewById(R.id.name_patient_value));
		this.setSecondName((TextView)v.findViewById(R.id.father_patient_value));
		this.setBithDate((TextView)v.findViewById(R.id.birthday_patient_value));
		this.setWeight((TextView)v.findViewById(R.id.weight_patient_value));
		this.setSex((TextView)v.findViewById(R.id.sex_patient_value));
		this.setAge((TextView)v.findViewById(R.id.age_patient_value));
		this.setHeight((TextView)v.findViewById(R.id.stature_patient_value));
		this.setRace((TextView)v.findViewById(R.id.race_patient_value));
		
		this.setIdPatient(infoP.getIdPatient());
		this.setLastName(infoP.getLastName());
		this.setFirstName(infoP.getFirstName());
		this.setSecondName(infoP.getSecondName());
		this.setBithDate(infoP.getBirthDate());
		this.setWeight(infoP.getWeight());
		this.setSex(infoP.getSex());
		this.setAge(infoP.getAge());
		this.setHeight(infoP.getHeight());
		this.setRace(infoP.getRace());
	}

	private final void setIdPatient(TextView idPatient) {
		this.idPatient = idPatient;
	}

	public void setIdPatient(CharSequence idPatient) {
		if (!idPatient.equals(""))
			this.idPatient.setText(idPatient);
	}

	private final void setLastName(TextView lastName) {
		this.lastName = lastName;
	}

	public void setLastName(CharSequence lastName) {
		if (!lastName.equals(""))
			this.lastName.setText(lastName);
	}

	private final void setFirstName(TextView firstName) {
		this.firstName = firstName;
	}

	public void setFirstName(CharSequence firstName) {
		if (!firstName.equals(""))
			this.firstName.setText(firstName);
	}

	private final void setSecondName(TextView secondName) {
		this.fatherName = secondName;
	}

	public void setSecondName(CharSequence secondName) {
		if (!secondName.equals(""))
			this.fatherName.setText(secondName);
	}

	private final void setBithDate(TextView bithDate) {
		this.birthDate = bithDate;
	}

	public void setBithDate(CharSequence birthDate) {
		if(!birthDate.equals(""))
			this.birthDate.setText(birthDate);
	}

	private final void setHeight(TextView height) {
		this.height = height;
	}

	public void setHeight(CharSequence height) {
		if(!height.equals(""))
			this.height.setText(height);
	}

	private final void setWeight(TextView weight) {
		this.weight = weight;
	}

	public void setWeight(CharSequence weight) {
		if(!weight.equals(""))
			this.weight.setText(weight);
	}

	private final void setSex(TextView sex) {
		this.sex = sex;
	}

	public void setSex(CharSequence sex) {
		if(!sex.equals(""))
			this.sex.setText(sex);
	}

	private final void setRace(TextView race) {
		this.race = race;
	}

	public void setRace(CharSequence race) {
		if(!race.equals(""))
			this.race.setText(race);
	}

	private final void setAge(TextView age) {
		this.age = age;
	}
	
	public void setAge(CharSequence age) {
		if(!age.equals(""))
			this.age.setText(age);
	}
}
