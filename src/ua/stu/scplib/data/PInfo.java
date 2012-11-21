package ua.stu.scplib.data;

import java.io.Serializable;
import java.util.StringTokenizer;

import android.os.Parcel;
import android.os.Parcelable;

import ua.stu.scplib.structure.SCPECG;
/**
 * Класс вмещающий в себе информацию о пациенте
 * @author ivan
 *
 */
public class PInfo implements Serializable  {
	
	public SCPECG scpecg =null;
	
	PInfo(SCPECG sc ) {		
		scpecg=sc;
	}

	private String nToken(String str) {
		if (!str.equals("")) {
			StringTokenizer st = new StringTokenizer(str, " ");
			return st.nextToken();
		} else
			return "";
	}

	private String nTokenCatEnd(String str) {
		if (!str.equals("")) {
			StringTokenizer st = new StringTokenizer(str, " ");
			st.nextToken();
			String s = st.nextToken();
			return s.substring(1, s.length() - 1);
		} else
			return "";
	}

	/**
	 * ID пациента
	 * 
	 * @return String
	 */
	public String getPatientId() {
		return scpecg.getNamedField("PatientIdentificationNumber");
	}

	/**
	 * Имя
	 * 
	 * @return String
	 */
	public String getFirstName() {
		return scpecg.getNamedField("FirstName");
	}

	/**
	 * Фамилия
	 * 
	 * @return String
	 */
	public String getLastName() {
		return scpecg.getNamedField("LastName");
	}

	/**
	 * Отчество
	 * 
	 * @return String
	 */
	public String getSecondLastName() {
		return scpecg.getNamedField("SecondLastName");
	}

	/**
	 * Возраст
	 * 
	 * @return String
	 */
	public String getAge() {
		return nToken(scpecg.getNamedField("Age"));
	}

	/**
	 * Дата рождения
	 * 
	 * @return String
	 */
	public String getDataOfBirth() {
		return scpecg.getNamedField("DateOfBirth");
	}

	/**
	 * Рост
	 * 
	 * @return String
	 */
	public String getHeight() {
		return nToken(scpecg.getNamedField("Height"));
	}

	/**
	 * Вес
	 * 
	 * @return String
	 */
	public String getWeight() {
		return nToken(scpecg.getNamedField("Weight"));
	}

	/**
	 * Пол
	 * 
	 * @return String
	 */
	public String getSex() {
		return nTokenCatEnd(scpecg.getNamedField("Sex"));
	}

	/**
	 * Раса
	 * 
	 * @return String
	 */
	public String getRace() {
		return nTokenCatEnd(scpecg.getNamedField("Race"));
	}

	/**
	 * Лекарства
	 * 
	 * @return String
	 */
	public String getDrugs() {
		return scpecg.getNamedField("Drugs");
	}

	/**
	 * Систолическое давление
	 * 
	 * @return String
	 */
	public String getSystolicBloodPressure() {
		return scpecg.getNamedField("SystolicBloodPressure");
	}

	/**
	 * Диастолическое давление
	 * 
	 * @return String
	 */
	public String getDiastolicBloodPressure() {
		return scpecg.getNamedField("DiastolicBloodPressure");
	}

	/**
	 * ЛДиагноз или направление
	 * 
	 * @return String
	 */
	public String getDiagnosisOrReferralIndication() {
		return scpecg.getNamedField("DiagnosisOrReferralIndication");
	}

	/**
	 * История болезни
	 * 
	 * @return String
	 */
	public String getFreeTextMedicalHistory() {
		return scpecg.getNamedField("FreeTextMedicalHistory");
	}

	/**
	 * Почтовый код
	 * 
	 * @return String
	 */
	// Адрес пациента
	public String getPostCode() {
		return scpecg.getNamedField("PostCode");
	}

	/**
	 * Район
	 * 
	 * @return String
	 */
	public String getRegion() {
		return scpecg.getNamedField("Region");
	}

	/**
	 * Область
	 * 
	 * @return String
	 */
	public String getDistrict() {
		return scpecg.getNamedField("District");
	}

	/**
	 * Населенный пункт
	 * 
	 * @return String
	 */
	public String getTown() {
		return scpecg.getNamedField("Town");
	}

	/**
	 * Улица
	 * 
	 * @return String
	 */
	public String getStreet() {
		return scpecg.getNamedField("Street");
	}

	/**
	 * Дом
	 * 
	 * @return String
	 */
	public String getHouse() {
		return scpecg.getNamedField("House");
	}

	/**
	 * Время проживания
	 * 
	 * @return String
	 */
	public String getTimeOfresidence() {
		return scpecg.getNamedField("TimeOfresidence");
	}
}
