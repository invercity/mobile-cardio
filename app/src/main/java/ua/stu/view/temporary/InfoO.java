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

	public InfoO(Object[] allInfoO)
	{
		super();

		setManufacturer((String)allInfoO[0]);
		setInstitutionNumber((String)allInfoO[1]);
		setDepartmentNumber((String)allInfoO[2]);
		setDeviceID((String)allInfoO[3]);
		setDeviceType((String)allInfoO[4]);
		setFrequency((String)allInfoO[5]);
		setModel((String)allInfoO[6]);
		setVersionPO((String)allInfoO[7]);
		setSerialNumber((String)allInfoO[8]);
		setPOSCP((String)allInfoO[9]);
		setPrint((Boolean)allInfoO[10]);
		setAnalysis((Boolean)allInfoO[11]);
		setStorage((Boolean)allInfoO[12]);
		setReceive((Boolean)allInfoO[13]);
		setAcquiringInstitutionDescription((String)allInfoO[14]);
		setAnalyzingInstitutionDescription((String)allInfoO[15]);
		setAcquiringDepartmentDescription((String)allInfoO[16]);
		setAnalyzingDepartmentDescription((String)allInfoO[17]);
		setReferringPhysician((String)allInfoO[18]);
		setLatestConfirmingPhysician((String)allInfoO[19]);
		setTechnicianDescription((String)allInfoO[20]);
		setFreeTextField((String)allInfoO[21]);
		setDateOfAcquisition((String)allInfoO[22]);
		setTimeOfAcquisition((String)allInfoO[23]);
	}
	
	/**
	 * Производитель
	 * @return String
	 */
	public String getManufacturer() {
		return (manufacturer == null) ? "" : manufacturer;
	}

	/**
	 * Производитель
	 * @param manufacturer
	 */
	public void setManufacturer(String manufacturer) {
		this.manufacturer = manufacturer;
	}

	/**
	 * @return the institutionNumber
	 */
	public String getInstitutionNumber() {
		return (institutionNumber == null) ? "" : institutionNumber;
	}

	/**
	 * @param institutionNumber the institutionNumber to set
	 */
	public void setInstitutionNumber(String institutionNumber) {
		this.institutionNumber = institutionNumber;
	}

	/**
	 * @return the departmentNumber
	 */
	public String getDepartmentNumber() {
		return (departmentNumber == null) ? "" : departmentNumber;
	}

	/**
	 * @param departmentNumber the departmentNumber to set
	 */
	public void setDepartmentNumber(String departmentNumber) {
		this.departmentNumber = departmentNumber;
	}

	/**
	 * @return the deviceID
	 */
	public String getDeviceID() {
		return (deviceID == null) ? "" : deviceID;
	}

	/**
	 * @param deviceID the deviceID to set
	 */
	public void setDeviceID(String deviceID) {
		this.deviceID = deviceID;
	}

	/**
	 * @return the deviceType
	 */
	public String getDeviceType() {
		return (deviceType == null) ? "" : deviceType;
	}

	/**
	 * @param deviceType the deviceType to set
	 */
	public void setDeviceType(String deviceType) {
		this.deviceType = deviceType;
	}

	/**
	 * @return the frequency
	 */
	public String getFrequency() {
		return (frequency == null) ? "" : frequency;
	}

	/**
	 * @param frequency the frequency to set
	 */
	public void setFrequency(String frequency) {
		this.frequency = frequency;
	}

	/**
	 * @return the model
	 */
	public String getModel() {
		return (model == null) ? "" : model;
	}

	/**
	 * @param model the model to set
	 */
	public void setModel(String model) {
		this.model = model;
	}

	/**
	 * @return the versionPO
	 */
	public String getVersionPO() {
		return (versionPO == null) ? "" : versionPO;
	}

	/**
	 * @param versionPO the versionPO to set
	 */
	public void setVersionPO(String versionPO) {
		this.versionPO = versionPO;
	}

	/**
	 * @return the serialNumber
	 */
	public String getSerialNumber() {
		return (serialNumber == null) ? "" : serialNumber;
	}

	/**
	 * @param serialNumber the serialNumber to set
	 */
	public void setSerialNumber(String serialNumber) {
		this.serialNumber = serialNumber;
	}

	/**
	 * @return the pOSCP
	 */
	public String getPOSCP() {
		return (POSCP == null) ? "" : POSCP;
	}

	/**
	 * @param pOSCP the pOSCP to set
	 */
	public void setPOSCP(String pOSCP) {
		POSCP = pOSCP;
	}

	/**
	 * @return the print
	 */
	public boolean isPrint() {
		return print;
	}

	/**
	 * @param print the print to set
	 */
	public void setPrint(boolean print) {
		this.print = print;
	}

	/**
	 * @return the analysis
	 */
	public boolean isAnalysis() {
		return analysis;
	}

	/**
	 * @param analysis the analysis to set
	 */
	public void setAnalysis(boolean analysis) {
		this.analysis = analysis;
	}

	/**
	 * @return the storage
	 */
	public boolean isStorage() {
		return storage;
	}

	/**
	 * @param storage the storage to set
	 */
	public void setStorage(boolean storage) {
		this.storage = storage;
	}

	/**
	 * @return the receive
	 */
	public boolean isReceive() {
		return receive;
	}

	/**
	 * @param receive the receive to set
	 */
	public void setReceive(boolean receive) {
		this.receive = receive;
	}

	/**
	 * @return the acquiringInstitutionDescription
	 */
	public String getAcquiringInstitutionDescription() {
		return (acquiringInstitutionDescription == null) ? "" : acquiringInstitutionDescription;
	}

	/**
	 * @param acquiringInstitutionDescription the acquiringInstitutionDescription to set
	 */
	public void setAcquiringInstitutionDescription(
			String acquiringInstitutionDescription) {
		this.acquiringInstitutionDescription = acquiringInstitutionDescription;
	}

	/**
	 * @return the analyzingInstitutionDescription
	 */
	public String getAnalyzingInstitutionDescription() {
		return (analyzingInstitutionDescription == null) ? "" : analyzingInstitutionDescription;
	}

	/**
	 * @param analyzingInstitutionDescription the analyzingInstitutionDescription to set
	 */
	public void setAnalyzingInstitutionDescription(
			String analyzingInstitutionDescription) {
		this.analyzingInstitutionDescription = analyzingInstitutionDescription;
	}

	/**
	 * @return the acquiringDepartmentDescription
	 */
	public String getAcquiringDepartmentDescription() {
		return (acquiringDepartmentDescription == null) ? "" : acquiringDepartmentDescription;
	}

	/**
	 * @param acquiringDepartmentDescription the acquiringDepartmentDescription to set
	 */
	public void setAcquiringDepartmentDescription(
			String acquiringDepartmentDescription) {
		this.acquiringDepartmentDescription = acquiringDepartmentDescription;
	}

	/**
	 * @return the analyzingDepartmentDescription
	 */
	public String getAnalyzingDepartmentDescription() {
		return (analyzingDepartmentDescription == null) ? "" : analyzingDepartmentDescription;
	}

	/**
	 * @param analyzingDepartmentDescription the analyzingDepartmentDescription to set
	 */
	public void setAnalyzingDepartmentDescription(
			String analyzingDepartmentDescription) {
		this.analyzingDepartmentDescription = analyzingDepartmentDescription;
	}

	/**
	 * @return the referringPhysician
	 */
	public String getReferringPhysician() {
		return (referringPhysician == null) ? "" : referringPhysician;
	}

	/**
	 * @param referringPhysician the referringPhysician to set
	 */
	public void setReferringPhysician(String referringPhysician) {
		this.referringPhysician = referringPhysician;
	}

	/**
	 * @return the latestConfirmingPhysician
	 */
	public String getLatestConfirmingPhysician() {
		return (latestConfirmingPhysician == null) ? "" : latestConfirmingPhysician;
	}

	/**
	 * @param latestConfirmingPhysician the latestConfirmingPhysician to set
	 */
	public void setLatestConfirmingPhysician(String latestConfirmingPhysician) {
		this.latestConfirmingPhysician = latestConfirmingPhysician;
	}

	/**
	 * @return the technicianDescription
	 */
	public String getTechnicianDescription() {
		return (technicianDescription == null) ? "" : technicianDescription;
	}

	/**
	 * @param technicianDescription the technicianDescription to set
	 */
	public void setTechnicianDescription(String technicianDescription) {
		this.technicianDescription = technicianDescription;
	}

	/**
	 * @return the freeTextField
	 */
	public String getFreeTextField() {
		return (freeTextField == null) ? "" : freeTextField;
	}

	/**
	 * @param freeTextField the freeTextField to set
	 */
	public void setFreeTextField(String freeTextField) {
		this.freeTextField = freeTextField;
	}

	/**
	 * @return the dateOfAcquisition
	 */
	public String getDateOfAcquisition() {
		return (dateOfAcquisition == null) ? "" : dateOfAcquisition;
	}

	/**
	 * @param dateOfAcquisition the dateOfAcquisition to set
	 */
	public void setDateOfAcquisition(String dateOfAcquisition) {
		this.dateOfAcquisition = dateOfAcquisition;
	}

	/**
	 * @return the timeOfAcquisition
	 */
	public String getTimeOfAcquisition() {
		return (timeOfAcquisition == null) ? "" : timeOfAcquisition;
	}

	/**
	 * @param timeOfAcquisition the timeOfAcquisition to set
	 */
	public void setTimeOfAcquisition(String timeOfAcquisition) {
		this.timeOfAcquisition = timeOfAcquisition;
	}

}
