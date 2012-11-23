package ua.stu.view.temporary;

import java.io.Serializable;


public class InfoP implements Serializable {
	/**
	 * Serial Version UID
	 */
	private static final long serialVersionUID = 768002720018876495L;

	/**
	 * ID пациента
	 * 
	 */
	private String idPatient;

	/**
	 * Имя
	 * 
	 */
	private String firstName;

	/**
	 * Фамилия
	 * 
	 */
	private String lastName;

	/**
	 * Отчество
	 * 
	 */
	private String secondName;

	/**
	 * Возраст
	 * 
	 */
	private String age;

	/**
	 * Дата рождения
	 * 
	 */
	private String birthDate;

	/**
	 * Рост
	 */
	private String height;

	/**
	 * Вес
	 * 
	 */
	private String weight;

	/**
	 * Пол
	 * 
	 */
	private String sex;

	/**
	 * Раса
	 * 
	 */
	private String race;

	/**
	 * Лекарства
	 * 
	 */
	private String drugs;

	/**
	 * Систолическое давление
	 * 
	 */
	private String systolicBloodPressure;

	/**
	 * Диастолическое давление
	 * 
	 */
	private String diastolicBloodPressure;

	/**
	 * Диагноз или направление
	 * 
	 */
	private String diagnosisOrReferralIndication;

	/**
	 * История болезни
	 * 
	 */
	private String medicalHistory;

	/**
	 * Почтовый код
	 * 
	 */
	private String postCode;

	/**
	 * Область
	 * 
	 */
	private String region;

	/**
	 * Район
	 * 
	 */
	private String district;

	/**
	 * Населенный пункт
	 * 
	 */
	private String town;

	/**
	 * Улица
	 * 
	 * @return String
	 */
	private String street;

	/**
	 * Дом
	 * 
	 */
	private String house;

	/**
	 * Время проживания
	 * 
	 */
	private String timeOfresidence;

	public InfoP(String[] allPInfo)
	{
		super();
		
		setIdPatient(allPInfo[0]);
		setFirstName(allPInfo[1]);
		setLastName(allPInfo[2]);
		setSecondName(allPInfo[3]);
		setAge(allPInfo[4]);
		setBirthDate(allPInfo[5]);
		setHeight(allPInfo[6]);
		setWeight(allPInfo[7]);
		setSex(allPInfo[8]);
		setRace(allPInfo[9]);
		setDrugs(allPInfo[11]);
		setSystolicBloodPressure(allPInfo[11]);
		setDiastolicBloodPressure(allPInfo[12]);
		setDiagnosisOrReferralIndication(allPInfo[13]);
		setMedicalHistory(allPInfo[14]);
		setPostCode(allPInfo[15]);
		setRegion(allPInfo[17]);
		setDistrict(allPInfo[16]);
		setTown(allPInfo[18]);
		setStreet(allPInfo[19]);
		setHouse(allPInfo[20]);
		setTimeOfresidence(allPInfo[21]);
	}
	
	public String getIdPatient() {
		return idPatient;
	}

	public void setIdPatient(String idPatient) {
		this.idPatient = idPatient;
	}

	public String getFirstName() {
		return firstName;
	}

	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}

	public String getLastName() {
		return lastName;
	}

	public void setLastName(String lastName) {
		this.lastName = lastName;
	}

	public String getSecondName() {
		return secondName;
	}

	public void setSecondName(String secondName) {
		this.secondName = secondName;
	}

	public String getAge() {
		return age;
	}

	public void setAge(String age) {
		this.age = age;
	}

	public String getBirthDate() {
		return birthDate;
	}

	public void setBirthDate(String birthDate) {
		this.birthDate = birthDate;
	}

	public String getHeight() {
		return height;
	}

	public void setHeight(String height) {
		this.height = height;
	}

	public String getWeight() {
		return weight;
	}

	public void setWeight(String weight) {
		this.weight = weight;
	}

	public String getSex() {
		return sex;
	}

	public void setSex(String sex) {
		this.sex = sex;
	}

	public String getRace() {
		return race;
	}

	public void setRace(String race) {
		this.race = race;
	}

	public String getDrugs() {
		return drugs;
	}

	public void setDrugs(String drugs) {
		this.drugs = drugs;
	}

	public String getSystolicBloodPressure() {
		return systolicBloodPressure;
	}

	public void setSystolicBloodPressure(String systolicBloodPressure) {
		this.systolicBloodPressure = systolicBloodPressure;
	}

	public String getDiastolicBloodPressure() {
		return diastolicBloodPressure;
	}

	public void setDiastolicBloodPressure(String diastolicBloodPressure) {
		this.diastolicBloodPressure = diastolicBloodPressure;
	}

	public String getDiagnosisOrReferralIndication() {
		return diagnosisOrReferralIndication;
	}

	public void setDiagnosisOrReferralIndication(
			String diagnosisOrReferralIndication) {
		this.diagnosisOrReferralIndication = diagnosisOrReferralIndication;
	}

	public String getMedicalHistory() {
		return medicalHistory;
	}

	public void setMedicalHistory(String medicalHistory) {
		this.medicalHistory = medicalHistory;
	}

	public String getPostCode() {
		return postCode;
	}

	public void setPostCode(String postCode) {
		this.postCode = postCode;
	}

	public String getRegion() {
		return region;
	}

	public void setRegion(String region) {
		this.region = region;
	}

	public String getDistrict() {
		return district;
	}

	public void setDistrict(String district) {
		this.district = district;
	}

	public String getTown() {
		return town;
	}

	public void setTown(String town) {
		this.town = town;
	}

	public String getStreet() {
		return street;
	}

	public void setStreet(String street) {
		this.street = street;
	}

	public String getHouse() {
		return house;
	}

	public void setHouse(String house) {
		this.house = house;
	}

	public String getTimeOfresidence() {
		return timeOfresidence;
	}

	public void setTimeOfresidence(String timeOfresidence) {
		this.timeOfresidence = timeOfresidence;
	}
}
