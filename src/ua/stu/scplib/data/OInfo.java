package ua.stu.scplib.data;

import ua.stu.scplib.structure.SCPECG;

/**
 * Класс вмещающий в себе прочую информацию
 * @author ivan
 *
 */
public class OInfo{

	private SCPECG scpecg =null;
	
	OInfo(SCPECG sc ) {		
		scpecg=sc;
	}

	/**
	 * 
	 * @return String
	 */
public String getAcquiringDeviceIdentificationNumber() {
return  scpecg.getNamedField("AcquiringDeviceIdentificationNumber");
}

public String getAnalyzingDeviceIdentificationNumber() {
return  scpecg.getNamedField("AnalyzingDeviceIdentificationNumber");
}
public String getAcquiringInstitutionDescription() {
return scpecg.getNamedField("AcquiringInstitutionDescription");
}
public String getAnalyzingInstitutionDescription() {
return scpecg.getNamedField("AnalyzingInstitutionDescription");
}
public String getAcquiringDepartmentDescription() {
return scpecg.getNamedField("AcquiringDepartmentDescription");
}
public String getAnalyzingDepartmentDescription() {
return scpecg.getNamedField("AnalyzingDepartmentDescription");
}
public String getReferringPhysician() {
return scpecg.getNamedField("ReferringPhysician");
}
public String getLatestConfirmingPhysician() {
return scpecg.getNamedField("LatestConfirmingPhysician");
}
public String getTechnicianDescription() {
return scpecg.getNamedField("TechnicianDescription");
}
public String getRoomDescription() {
return scpecg.getNamedField("RoomDescription");
}
public String getStatCode() {
return scpecg.getNamedField("StatCode");
}
public String getDateOfAcquisition() {
return scpecg.getNamedField("DateOfAcquisition");
}
public String getTimeOfAcquisition() {
return scpecg.getNamedField("TimeOfAcquisition");
}
public String getBaselineFilter() {
return scpecg.getNamedField("BaselineFilter");
}
public String getLowPassFilter() {
return scpecg.getNamedField("LowPassFilter");
}
public String getFilterBitmap() {
return scpecg.getNamedField("FilterBitmap");
}

public String geteCGSequenceNumber() {
return scpecg.getNamedField("CGSequenceNumber");
}

public String getElectrodeConfigurationCode() {
return scpecg.getNamedField("ElectrodeConfigurationCode");
}
public String getDateTimeZone() {
return scpecg.getNamedField("DateTimeZone");
}
public String getMedicalHistoryCodes() {
return scpecg.getNamedField("MedicalHistoryCodes");
}
public String getFreeTextField() {
return scpecg.getNamedField("FreeTextField");
}

}
