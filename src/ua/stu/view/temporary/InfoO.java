package ua.stu.view.temporary;

import java.io.Serializable;

public class InfoO implements Serializable {

	/**
	 * Serial Version UID
	 */
	private static final long serialVersionUID = -7058998959078414027L;

	/**
	 * Производитель
	 * 
	 */
	private String manufacturer;

	/**
	 * Номер организации
	 * 
	 */
	private String institutionNumber;

	/**
	 * Номер отдела
	 * 
	 */
	private String departmentNumber;

	/**
	 * ID устройства
	 * 
	 */
	private String deviceID;

	/**
	 * Тип устройства
	 * 
	 */
	private String deviceType;

	/**
	 * Частота
	 * 
	 */
	private String frequency;

	/**
	 * 
	 * Модель
	 * 
	 */
	private String model;

	/**
	 * Версия анализирующего ПО и Системное ПО (Оно одинаковое плкрайне мере в
	 * тех файлах что я тестил)
	 * 
	 */
	private String versionPO;

	/**
	 * Серийный номер
	 * 
	 */
	private String serialNumber;

	/**
	 * ПО,реализуещее протокол SCP
	 * 
	 */
	private String POSCP;

	/**
	 * Возможность печать
	 * 
	 */
	private boolean print;

	/**
	 * Возможность анализа
	 * 
	 */
	private boolean analysis;

	/**
	 * Возможность храниения
	 * 
	 */
	private boolean storage;

	/**
	 * Возможность приема
	 * 
	 */
	private boolean receive;

	/**
	 * Учреждение, записывавшее ЭКГ
	 * 
	 */
	private String acquiringInstitutionDescription;

	/**
	 * Учреждение, анализируещее ЭКГ
	 * 
	 */
	private String analyzingInstitutionDescription;

	/**
	 * Отдел, принимавший ЭКГ
	 */
	private String acquiringDepartmentDescription;

	/**
	 * Отдел, анализировавший ЭКГ
	 * 
	 */
	private String analyzingDepartmentDescription;

	/**
	 * Направляющие врачи
	 */
	private String referringPhysician;

	/**
	 * Подтверждающие врачи
	 * 
	 */
	private String latestConfirmingPhysician;

	/**
	 * Медсестры
	 * 
	 */
	private String technicianDescription;

	/**
	 * Комментарий
	 * 
	 */
	private String freeTextField;

	/**
	 * Дата получения ЭКГ
	 * 
	 */
	private String dateOfAcquisition;

	/**
	 * Время получения ЭКГ
	 * 
	 */
	private String timeOfAcquisition;

	public String getManufacturer() {
		return manufacturer;
	}

	public void setManufacturer(String manufacturer) {
		this.manufacturer = manufacturer;
	}

	public String getInstitutionNumber() {
		return institutionNumber;
	}

	public void setInstitutionNumber(String institutionNumber) {
		this.institutionNumber = institutionNumber;
	}

	public String getDepartmentNumber() {
		return departmentNumber;
	}

	public void setDepartmentNumber(String departmentNumber) {
		this.departmentNumber = departmentNumber;
	}

	public String getDeviceID() {
		return deviceID;
	}

	public void setDeviceID(String deviceID) {
		this.deviceID = deviceID;
	}

	public String getDeviceType() {
		return deviceType;
	}

	public void setDeviceType(String deviceType) {
		this.deviceType = deviceType;
	}

	public String getFrequency() {
		return frequency;
	}

	public void setFrequency(String frequency) {
		this.frequency = frequency;
	}

	public String getModel() {
		return model;
	}

	public void setModel(String model) {
		this.model = model;
	}

	public String getVersionPO() {
		return versionPO;
	}

	public void setVersionPO(String versionPO) {
		this.versionPO = versionPO;
	}

	public String getSerialNumber() {
		return serialNumber;
	}

	public void setSerialNumber(String serialNumber) {
		this.serialNumber = serialNumber;
	}

	public String getPOSCP() {
		return POSCP;
	}

	public void setPOSCP(String pOSCP) {
		POSCP = pOSCP;
	}

	public boolean isPrint() {
		return print;
	}

	public void setPrint(boolean print) {
		this.print = print;
	}

	public boolean isAnalysis() {
		return analysis;
	}

	public void setAnalysis(boolean analysis) {
		this.analysis = analysis;
	}

	public boolean isStorage() {
		return storage;
	}

	public void setStorage(boolean storage) {
		this.storage = storage;
	}

	public boolean isReceive() {
		return receive;
	}

	public void setReceive(boolean receive) {
		this.receive = receive;
	}

	public String getAcquiringInstitutionDescription() {
		return acquiringInstitutionDescription;
	}

	public void setAcquiringInstitutionDescription(
			String acquiringInstitutionDescription) {
		this.acquiringInstitutionDescription = acquiringInstitutionDescription;
	}

	public String getAnalyzingInstitutionDescription() {
		return analyzingInstitutionDescription;
	}

	public void setAnalyzingInstitutionDescription(
			String analyzingInstitutionDescription) {
		this.analyzingInstitutionDescription = analyzingInstitutionDescription;
	}

	public String getAcquiringDepartmentDescription() {
		return acquiringDepartmentDescription;
	}

	public void setAcquiringDepartmentDescription(
			String acquiringDepartmentDescription) {
		this.acquiringDepartmentDescription = acquiringDepartmentDescription;
	}

	public String getAnalyzingDepartmentDescription() {
		return analyzingDepartmentDescription;
	}

	public void setAnalyzingDepartmentDescription(
			String analyzingDepartmentDescription) {
		this.analyzingDepartmentDescription = analyzingDepartmentDescription;
	}

	public String getReferringPhysician() {
		return referringPhysician;
	}

	public void setReferringPhysician(String referringPhysician) {
		this.referringPhysician = referringPhysician;
	}

	public String getLatestConfirmingPhysician() {
		return latestConfirmingPhysician;
	}

	public void setLatestConfirmingPhysician(String latestConfirmingPhysician) {
		this.latestConfirmingPhysician = latestConfirmingPhysician;
	}

	public String getTechnicianDescription() {
		return technicianDescription;
	}

	public void setTechnicianDescription(String technicianDescription) {
		this.technicianDescription = technicianDescription;
	}

	public String getFreeTextField() {
		return freeTextField;
	}

	public void setFreeTextField(String freeTextField) {
		this.freeTextField = freeTextField;
	}

	public String getDateOfAcquisition() {
		return dateOfAcquisition;
	}

	public void setDateOfAcquisition(String dateOfAcquisition) {
		this.dateOfAcquisition = dateOfAcquisition;
	}

	public String getTimeOfAcquisition() {
		return timeOfAcquisition;
	}

	public void setTimeOfAcquisition(String timeOfAcquisition) {
		this.timeOfAcquisition = timeOfAcquisition;
	}
}
