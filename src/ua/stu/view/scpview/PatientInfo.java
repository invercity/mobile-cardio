package ua.stu.view.scpview;

import android.annotation.TargetApi;
import android.app.Fragment;
import android.app.FragmentTransaction;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;


@TargetApi(11) 
public class PatientInfo extends Fragment implements OnItemClickListener 
{
//	private static String[] infoTypes = { "Пациент", "Кровяное давления",
//									  "Адрес","Диагноз или направления",
//									  "История болезни"};
//	private ListView lvMain;
	/**
	 * ID пациента
	 */
	private TextView idPatient;
	/**
	 * Фамилия пациента
	 */
	private TextView lastName;
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
	
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
		      Bundle savedInstanceState) 
	{
		View v = inflater.inflate(R.layout.privatepatientinfo, null);
		init(v);
//		//find list and add handler
//	    lvMain = (ListView) v.findViewById(R.id.lvMain);
//	    lvMain.setOnItemClickListener(this);
//
//	    //create adapter
//	    ArrayAdapter<String> adapter = new ArrayAdapter<String>(inflater.getContext(),
//	        android.R.layout.simple_list_item_1, infoTypes);
//
//	    //add adapter list
//	    lvMain.setAdapter(adapter);
	    
		return v;
	}

	private final void init(View v)
	{
		this.setRace((TextView)v.findViewById(R.id.race_patient_value));
		this.setIdPatient((TextView)v.findViewById(R.id.id_patient_value));
		this.setLastName((TextView)v.findViewById(R.id.surname_patient_value));
		this.setFirstName((TextView)v.findViewById(R.id.name_patient_value));
		this.setBithDate((TextView)v.findViewById(R.id.birthday_patient_value));
		this.setWeight((TextView)v.findViewById(R.id.weight_patient_value));
		this.setSex((TextView)v.findViewById(R.id.sex_patient_value));
	}
	
	public void onItemClick(AdapterView<?> parent, View view, int position, long id) 
	{
		switch(position)
		{
		case 0:
			break;
		case 1:
			break;
		case 2:
			break;
		case 3:
			break;
		}
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
}
