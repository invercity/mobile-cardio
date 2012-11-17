package ua.stu.view.fragments;

import ua.stu.view.scpview.R;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

public class PrivatePatientInfoFragment extends Fragment {
	
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
	private TextView bithDate;
	/**
	 * Рост пациента
	 */
	private TextView stature;
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
	
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
		      Bundle savedInstanceState) 
	{
		View v = inflater.inflate(R.layout.privatepatientinfo, null);
	    init(v);
		return v;
	}
	
	private final void init(View v)
	{
		this.setIdPatient((TextView)v.findViewById(R.id.id_patient_value));
		this.setLastName((TextView)v.findViewById(R.id.surname_patient_value));
		this.setFirstName((TextView)v.findViewById(R.id.name_patient_value));
		this.setFatherName((TextView)v.findViewById(R.id.father_patient_value));
		this.setBithDate((TextView)v.findViewById(R.id.birthday_patient_value));
		this.setWeight((TextView)v.findViewById(R.id.weight_patient_value));
		this.setSex((TextView)v.findViewById(R.id.sex_patient_value));
		this.setAge((TextView)v.findViewById(R.id.age_patient_value));
		this.setStature((TextView)v.findViewById(R.id.stature_patient_value));
		this.setRace((TextView)v.findViewById(R.id.race_patient_value));
	}
	
	public TextView getIdPatient() {
		return idPatient;
	}

	private final void setIdPatient(TextView idPatient) {
		this.idPatient = idPatient;
	}

	public void setIdPatient(CharSequence idPatient) {
		this.idPatient.setText(idPatient);
	}

	public TextView getLastName() {
		return lastName;
	}

	private final void setLastName(TextView lastName) {
		this.lastName = lastName;
	}

	public void setLastName(CharSequence lastName) {
		this.lastName.setText(lastName);
	}

	public TextView getFirstName() {
		return firstName;
	}

	private final void setFirstName(TextView firstName) {
		this.firstName = firstName;
	}

	public void setFirstName(CharSequence firstName) {
		this.firstName.setText(firstName);
	}

	public TextView getFatherName() {
		return fatherName;
	}

	private final void setFatherName(TextView fatherName) {
		this.fatherName = fatherName;
	}

	public void setFatherName(CharSequence fatherName) {
		this.fatherName.setText(fatherName);
	}

	public TextView getBithDate() {
		return bithDate;
	}

	private final void setBithDate(TextView bithDate) {
		this.bithDate = bithDate;
	}

	public void setBithDate(CharSequence bithDate) {
		this.bithDate.setText(bithDate);
	}

	public TextView getStature() {
		return stature;
	}

	private final void setStature(TextView stature) {
		this.stature = stature;
	}

	public void setStature(CharSequence stature) {
		this.stature.setText(stature);
	}

	public TextView getWeight() {
		return weight;
	}

	private final void setWeight(TextView weight) {
		this.weight = weight;
	}

	public void setWeight(CharSequence weight) {
		this.weight.setText(weight);
	}

	public TextView getSex() {
		return sex;
	}

	private final void setSex(TextView sex) {
		this.sex = sex;
	}

	public void setSex(CharSequence sex) {
		this.sex.setText(sex);
	}

	public TextView getRace() {
		return race;
	}

	private final void setRace(TextView race) {
		this.race = race;
	}

	public void setRace(CharSequence race) {
		this.race.setText(race);
	}

	public TextView getAge() {
		return age;
	}

	private final void setAge(TextView age) {
		this.age = age;
	}

}
