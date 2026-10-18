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
	 * 
	 * @return Object[]
	 * <p>0 - Производитель </p>
	 * <p>1 - Номер организации </p>
	 * <p>2 - Номер отдела </p>
	 * <p>3 - ID устройства </p>
	 * <p>4 - Тип устройства </p>
	 * <p>5 - Частота </p>
	 * <p>6 - Модель </p>
	 * <p>7 - Версии анализирующего и системного ПО </p>
	 * <p>8 - Серийный номер </p>
	 * <p>9 - ПО реализующее SCP протолок </p>
	 * <p>10 - Возможность печати </p>
	 * <p>11 - Возможность анализа </p>
	 * <p>12 - Возможность хранения </p>
	 * <p>13 - Возможность приема </p>
	 * <p>14 - Учреждения записывающее ЕКГ </p>
	 * <p>15 - Учереждения анализирующее ЕКГ </p>
	 * <p>16 - Отдел принимавший ЕКГ </p>
	 * <p>17 - Отдел анализировавший ЕКГ </p>
	 * <p>18 - Направляющие врачи </p>
	 * <p>19 - Подтверждающие врачи </p>
	 * <p>20 - Медсестры </p>
	 * <p>21 - Коментарии </p>
	 * <p>22 - Дата получения ЕКГ </p>
	 * <p>23 - Время получения ЕКГ </p>
	 */
	public Object[] getAllOInfo()
	{
		Object[] allOInfo = null;
		if (scpecg != null)
		{
			allOInfo = new Object[]{
					getManufacturer(),
					getInstitutionNumber(),
					getDepartmentNumber(),
					getDeviceID(),
					getDeviceType(),
					getFrequency(),
					getModel(),
					getVersionPO(),
					getSerialNumber(),
					getPOSCP(),
					getPrint(),
					getAnalysis(),
					getStorage(),
					getReceive(),
					getAcquiringInstitutionDescription(),
					getAnalyzingInstitutionDescription(),
					getAcquiringDepartmentDescription(),
					getAnalyzingDepartmentDescription(),
					getReferringPhysician(),
					getLatestConfirmingPhysician(),
					getTechnicianDescription(),
					getFreeTextField(),
					getDateOfAcquisition(),
					getTimeOfAcquisition()
			};
		}
		
		return allOInfo;
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
		//init mas
		for(int i=0;i<8;i++) capabilitiesOfDevice[i]=0;
		int k=0;
		for (int i=binput.length()-1; i >=0 ; i--) {		
			capabilitiesOfDevice[k] = Integer.valueOf(String.valueOf(binput
					.charAt(i)));
			k++;
		}		
	}

	/**
	 * Возможность печать
	 * 
	 * @return boolean
	 */
	public boolean getPrint() {
		return capabilitiesOfDevice[4] != 0;
	}

	/**
	 * Возможность анализа
	 * 
	 * @return boolean
	 */
	public boolean getAnalysis() {
		return capabilitiesOfDevice[5] != 0;
	}

	/**
	 * Возможность храниения
	 * 
	 * @return boolean
	 */
	public boolean getStorage() {
		return capabilitiesOfDevice[6] != 0;
	}

	/**
	 * Возможность приема
	 * 
	 * @return boolean
	 */
	public boolean getReceive() {
		return capabilitiesOfDevice[7] != 0;
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
