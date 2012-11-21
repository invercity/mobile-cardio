package ua.stu.scplib.data;

import java.util.HashMap;
import java.util.Map;
import java.util.StringTokenizer;

import ua.stu.scplib.structure.SCPECG;

/**
 * Класс вмещающий в себе прочую информацию
 * 
 * @author ivan
 * 
 */
public class OInfo {

	private SCPECG scpecg = null;
	private Map<String, String> mpADIN = null;
	int[] capabilitiesOfDevice = new int[8];

	OInfo(SCPECG sc) {
		scpecg = sc;
		setCapabilitiesOfDevice();
	}

	private void getAcquiringDeviceIdentificationNumber() {
		StringTokenizer st = null;
		// System.out.println(scpecg.getNamedField("AcquiringDeviceIdentificationNumber"));
		st = new StringTokenizer(
				scpecg.getNamedField("AcquiringDeviceIdentificationNumber"),
				", ");
		mpADIN = new HashMap<String, String>();
		while (st.hasMoreTokens()) {
			StringTokenizer token = new StringTokenizer(st.nextToken(), "=");
			if (token.countTokens() == 2)
				mpADIN.put(token.nextToken(), token.nextToken());
		}
	}

	/**
	 * Производитель
	 * 
	 * @return String
	 */
	public String getManufacturer() {
		if (mpADIN == null)
			getAcquiringDeviceIdentificationNumber();
		return scpecg.getSection1().getManufucture();

	}

	/**
	 * Номер организации
	 * 
	 * @return String
	 */
	public String getInstitutionNumber() {
		if (mpADIN == null)
			getAcquiringDeviceIdentificationNumber();
		return mpADIN.get("institutionNumber");

	}

	/**
	 * Номер отдела
	 * 
	 * @return String
	 */
	public String getDepartmentNumber() {
		if (mpADIN == null)
			getAcquiringDeviceIdentificationNumber();
		return mpADIN.get("departmentNumber");

	}

	/**
	 * ID устройства
	 * 
	 * @return String
	 */
	public String getDeviceID() {
		if (mpADIN == null)
			getAcquiringDeviceIdentificationNumber();
		return mpADIN.get("deviceID");

	}

	/**
	 * Тип устройства
	 * 
	 * @return String
	 */
	public String getDeviceType() {
		if (mpADIN == null)
			getAcquiringDeviceIdentificationNumber();
		int id = Integer.parseInt(mpADIN.get("deviceType"));
		String idS;
		switch (id) {
		case 1:
			idS = "Хост-система";
			break;
		case 0:
			idS = "Перевозочное";
			break;
		default:
			idS = "Неизвестно";
			break;
		}

		return idS;

	}

	/**
	 * Частота
	 * 
	 * @return String
	 */
	public String getFrequency() {
		if (mpADIN == null)
			getAcquiringDeviceIdentificationNumber();
		int id = Integer.parseInt(mpADIN.get("mainsFrequency"));
		String idS;
		switch (id) {
		case 1:
			idS = "50";
			break;
		case 2:
			idS = "60";
			break;
		default:
			idS = "Неизвестно";
			break;
		}

		return idS;

	}

	/**
	 * 
	 * Модель
	 * 
	 * @return String
	 */
	public String getModel() {
		if (mpADIN == null)
			getAcquiringDeviceIdentificationNumber();
		return mpADIN.get("modelDescription");

	}

	/**
	 * Версия анализирующего ПО и Системное ПО (Оно одинаковое плкрайне мере в
	 * тех файлах что я тестил)
	 * 
	 * @return String
	 */
	public String getVersionPO() {
		if (mpADIN == null)
			getAcquiringDeviceIdentificationNumber();
		return scpecg.getSection1().getVerPO();

	}

	/**
	 * Серийный номер
	 * 
	 * @return String
	 */
	public String getSerialNumber() {
		if (mpADIN == null)
			getAcquiringDeviceIdentificationNumber();
		return scpecg.getSection1().getSerialnumber();

	}

	/**
	 * ПО,реализуещее протокол SCP
	 * 
	 * @return String
	 */
	public String getPOSCP() {
		if (mpADIN == null)
			getAcquiringDeviceIdentificationNumber();
		return scpecg.getSection1().getPOSCP();

	}

	/**
	 * Возвожности устройства
	 * 
	 * @return String
	 */
	private void setCapabilitiesOfDevice() {
		if (mpADIN == null)
			getAcquiringDeviceIdentificationNumber();
		String binput = Integer.toBinaryString(Integer.parseInt(mpADIN
				.get("capabilitiesCode")));
		for (int i = 0; i < binput.length(); i++) {
			capabilitiesOfDevice[i] = Integer.valueOf(String.valueOf(binput
					.charAt(i)));
		}
	}

	/**
	 * Возможность печать
	 * 
	 * @return boolean
	 */
	public boolean getPrint() {
		return capabilitiesOfDevice[3] != 0;
	}

	/**
	 * Возможность анализа
	 * 
	 * @return boolean
	 */
	public boolean getAnalysis() {
		return capabilitiesOfDevice[2] != 0;
	}

	/**
	 * Возможность храниения
	 * 
	 * @return boolean
	 */
	public boolean getStorage() {
		return capabilitiesOfDevice[1] != 0;
	}

	/**
	 * Возможность приема
	 * 
	 * @return boolean
	 */
	public boolean getReceive() {
		return capabilitiesOfDevice[0] != 0;
	}

	/**
	 * Учреждение, записывавшее ЭКГ
	 * 
	 * @return String
	 */
	public String getAcquiringInstitutionDescription() {
		return scpecg.getNamedField("AcquiringInstitutionDescription");

	}

	/**
	 * Учреждение, анализируещее ЭКГ
	 * 
	 * @return String
	 */
	public String getAnalyzingInstitutionDescription() {
		return scpecg.getNamedField("AnalyzingInstitutionDescription");

	}

	/**
	 * Отдел, принимавший ЭКГ
	 * 
	 * @return String
	 */
	public String getAcquiringDepartmentDescription() {
		return scpecg.getNamedField("AcquiringDepartmentDescription");

	}

	/**
	 * Отдел, анализировавший ЭКГ
	 * 
	 * @return String
	 */
	public String getAnalyzingDepartmentDescription() {
		return scpecg.getNamedField("AnalyzingDepartmentDescription");

	}

	/**
	 * Направляющие врачи
	 * 
	 * @return String
	 */
	public String getReferringPhysician() {
		return scpecg.getNamedField("ReferringPhysician");

	}

	/**
	 * Подтверждающие врачи
	 * 
	 * @return String
	 */
	public String getLatestConfirmingPhysician() {
		return scpecg.getNamedField("LatestConfirmingPhysician");

	}

	/**
	 * Медсестры
	 * 
	 * @return String
	 */
	public String getTechnicianDescription() {
		return scpecg.getNamedField("TechnicianDescription");

	}

	/**
	 * Комментарий
	 * 
	 * @return String
	 */
	public String getFreeTextField() {
		return scpecg.getNamedField("FreeTextField");

	}

	/**
	 * Дата получения ЭКГ
	 * 
	 * @return String
	 */
	public String getDateOfAcquisition() {
		return scpecg.getNamedField("DateOfAcquisition");

	}

	/**
	 * Время получения ЭКГ
	 * 
	 * @return String
	 */
	public String getTimeOfAcquisition() {
		return scpecg.getNamedField("TimeOfAcquisition");

	}
}
