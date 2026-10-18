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

	/**
	 * @return the idPatient
	 */
	public String getIdPatient() {
		return (idPatient == null) ? "" : idPatient;
	}

	/**
	 * @param idPatient the idPatient to set
	 */
	public void setIdPatient(String idPatient) {
		this.idPatient = idPatient;
	}

	/**
	 * @return the firstName
	 */
	public String getFirstName() {
		return (firstName == null) ? "" : firstName;
	}

	/**
	 * @param firstName the firstName to set
	 */
	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}

	/**
	 * @return the lastName
	 */
	public String getLastName() {
		return (lastName == null) ? "" : lastName;
	}

	/**
	 * @param lastName the lastName to set
	 */
	public void setLastName(String lastName) {
		this.lastName = lastName;
	}

	/**
	 * @return the secondName
	 */
	public String getSecondName() {
		return (secondName == null) ? "" : secondName;
	}

	/**
	 * @param secondName the secondName to set
	 */
	public void setSecondName(String secondName) {
		this.secondName = secondName;
	}

	/**
	 * @return the age
	 */
	public String getAge() {
		return (age == null) ? "" : age;
	}

	/**
	 * @param age the age to set
	 */
	public void setAge(String age) {
		this.age = age;
	}

	/**
	 * @return the birthDate
	 */
	public String getBirthDate() {
		return (birthDate == null) ? "" : birthDate;
	}

	/**
	 * @param birthDate the birthDate to set
	 */
	public void setBirthDate(String birthDate) {
		this.birthDate = birthDate;
	}

	/**
	 * @return the height
	 */
	public String getHeight() {
		return (height == null) ? "" : height;
	}

	/**
	 * @param height the height to set
	 */
	public void setHeight(String height) {
		this.height = height;
	}

	/**
	 * @return the weight
	 */
	public String getWeight() {
		return (weight == null) ? "" : weight;
	}

	/**
	 * @param weight the weight to set
	 */
	public void setWeight(String weight) {
		this.weight = weight;
	}

	/**
	 * @return the sex
	 */
	public String getSex() {
		return (sex == null) ? "" : sex;
	}

	/**
	 * @param sex the sex to set
	 */
	public void setSex(String sex) {
		this.sex = sex;
	}

	/**
	 * @return the race
	 */
	public String getRace() {
		return (race == null) ? "" : race;
	}

	/**
	 * @param race the race to set
	 */
	public void setRace(String race) {
		this.race = race;
	}

	/**
	 * @return the drugs
	 */
	public String getDrugs() {
		return (drugs == null) ? "" : drugs;
	}

	/**
	 * @param drugs the drugs to set
	 */
	public void setDrugs(String drugs) {
		this.drugs = drugs;
	}

	/**
	 * @return the systolicBloodPressure
	 */
	public String getSystolicBloodPressure() {
		return (systolicBloodPressure == null) ? "" : systolicBloodPressure;
	}

	/**
	 * @param systolicBloodPressure the systolicBloodPressure to set
	 */
	public void setSystolicBloodPressure(String systolicBloodPressure) {
		this.systolicBloodPressure = systolicBloodPressure;
	}

	/**
	 * @return the diastolicBloodPressure
	 */
	public String getDiastolicBloodPressure() {
		return (diastolicBloodPressure == null) ? "" : diastolicBloodPressure;
	}

	/**
	 * @param diastolicBloodPressure the diastolicBloodPressure to set
	 */
	public void setDiastolicBloodPressure(String diastolicBloodPressure) {
		this.diastolicBloodPressure = diastolicBloodPressure;
	}

	/**
	 * @return the diagnosisOrReferralIndication
	 */
	public String getDiagnosisOrReferralIndication() {
		return (diagnosisOrReferralIndication == null) ? "" : diagnosisOrReferralIndication;
	}

	/**
	 * @param diagnosisOrReferralIndication the diagnosisOrReferralIndication to set
	 */
	public void setDiagnosisOrReferralIndication(
			String diagnosisOrReferralIndication) {
		this.diagnosisOrReferralIndication = diagnosisOrReferralIndication;
	}

	/**
	 * @return the medicalHistory
	 */
	public String getMedicalHistory() {
		return (medicalHistory == null) ? "" : medicalHistory;
	}

	/**
	 * @param medicalHistory the medicalHistory to set
	 */
	public void setMedicalHistory(String medicalHistory) {
		this.medicalHistory = medicalHistory;
	}

	/**
	 * @return the postCode
	 */
	public String getPostCode() {
		return (postCode == null) ? "" : postCode;
	}

	/**
	 * @param postCode the postCode to set
	 */
	public void setPostCode(String postCode) {
		this.postCode = postCode;
	}

	/**
	 * @return the region
	 */
	public String getRegion() {
		return (region == null) ? "" : region;
	}

	/**
	 * @param region the region to set
	 */
	public void setRegion(String region) {
		this.region = region;
	}

	/**
	 * @return the district
	 */
	public String getDistrict() {
		return (district == null) ? "" : district;
	}

	/**
	 * @param district the district to set
	 */
	public void setDistrict(String district) {
		this.district = district;
	}

	/**
	 * @return the street
	 */
	public String getStreet() {
		return (street == null) ? "" : street;
	}

	/**
	 * @param street the street to set
	 */
	public void setStreet(String street) {
		this.street = street;
	}

	/**
	 * @return the house
	 */
	public String getHouse() {
		return (house == null) ? "" : house;
	}

	/**
	 * @param house the house to set
	 */
	public void setHouse(String house) {
		this.house = house;
	}

	/**
	 * @return the timeOfresidence
	 */
	public String getTimeOfresidence() {
		return (timeOfresidence == null) ? "" : timeOfresidence;
	}

	/**
	 * @param timeOfresidence the timeOfresidence to set
	 */
	public void setTimeOfresidence(String timeOfresidence) {
		this.timeOfresidence = timeOfresidence;
	}

	/**
	 * @return the town
	 */
	public String getTown() {
		return (town == null) ? "" : town;
	}

	/**
	 * @param town the town to set
	 */
	public void setTown(String town) {
		this.town = town;
	}

}
