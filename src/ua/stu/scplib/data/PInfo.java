package ua.stu.scplib.data;

import java.util.StringTokenizer;

import ua.stu.scplib.structure.SCPECG;
/**
 * Класс вмещающий в себе информацию о пациенте
 * @author ivan
 *
 */
public class PInfo  {
	private SCPECG scpecg =null;
	
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
	 * 
	 * @return String[]
	 * <p>0 - ID пациента </p>
	 * <p>1 - Имя пациента </p>
	 * <p>2 - Фамилия пациента </p>
	 * <p>3 - Отчество пациента </p>
	 * <p>4 - Возраст пациента </p>
	 * <p>5 - Дата рождения пациента </p>
	 * <p>6 - Рост пациента </p>
	 * <p>7 - Вес пациента </p>
	 * <p>8 - Пол пациента </p>
	 * <p>9 - Раса пациента </p>
	 * <p>10 - Лекарства пациента </p>
	 * <p>11 - Систолическое давления пациента </p>
	 * <p>12 - Диастолическое давления пациента </p>
	 * <p>13 - Диагноз или направления </p>
	 * <p>14 - История болезни пациента </p>
	 * <p>15 - Почтовый код </p>
	 * <p>16 - Район </p>
	 * <p>17 - Область </p>
	 * <p>18 - Населенный пункт </p>
	 * <p>19 - Улица </p>
	 * <p>20 - Дом </p>
	 * <p>21 - Время проживания </p>
	 */
	public String[] getAllPInfo()
	{
		String[] allPInfo = null;
		if (scpecg != null)
		{
			allPInfo = new String[]{
				getPatientId(),
				getFirstName(),
				getLastName(),
				getSecondLastName(),
				getAge(),
				getDataOfBirth(),
				getHeight(),
				getWeight(),
				getSex(),
				getRace(),
				getDrugs(),
				getSystolicBloodPressure(),
				getDiastolicBloodPressure(),
				getDiagnosisOrReferralIndication(),
				getFreeTextMedicalHistory(),
				getPostCode(),
				getRegion(),
				getDistrict(),
				getTown(),
				getStreet(),
				getHouse(),
				getTimeOfresidence()
			};
		}
		
		return allPInfo;
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
