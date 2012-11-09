package ua.stu.scplib.data;

import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.StringTokenizer;
import ua.stu.scplib.attribute.BinaryInputStream;
import ua.stu.scplib.attribute.GraphicAttribute;
import ua.stu.scplib.attribute.GraphicAttributeBase;
import ua.stu.scplib.structure.SCPECG;

public class DataHandler {
	 private BinaryInputStream i = null;
     private SCPECG scpecg = null;
     private GraphicAttributeBase graphic;
/*   //Patient info
     private String patientId;
     private String firstName;
     private String lastName;
     private String secondLastName;
     private String age;
     private String dataOfBirth;
     private String height;//рост
     private String weight;//вес
     private String sex;
     private String race;//расса
     private String drugs;//припараты
     private String systolicBloodPressure;//систолическое давление
     private String diastolicBloodPressure;//диастолическое давление
     private String diagnosisOrReferralIndication;//Диагноз или направлени
     private String freeTextMedicalHistory;//История болезни
*/   
/*   private String postCode;//История болезни
     private String region;
     private String	district;
     private String	town;
     private String	street;
     private String	house;
     private String	timeOfresidence;*/
     
     //информация для вкладки прочее
     
     //Устройство для получения ЭКГ
     private String manufacturer;
     private String departmentNumber;
     private String deviceID;
     private String deviceType;

     private String acquiringDeviceIdentificationNumber;
     private String analyzingDeviceIdentificationNumber;
     private String acquiringInstitutionDescription;
     private String analyzingInstitutionDescription;
     private String acquiringDepartmentDescription;
     private String analyzingDepartmentDescription;
     private String referringPhysician;
     private String latestConfirmingPhysician;
     private String technicianDescription;
     private String statCode;
     private String dateOfAcquisition;
     private String timeOfAcquisition;
     private String baselineFilter;
     private String lowPassFilter;
     private String filterBitmap;
     private String freeTextField;
     private String eCGSequenceNumber;
     private String medicalHistoryCodes;
     private String electrodeConfigurationCode;
     private String dateTimeZone;
     
     private boolean ifErrorOpenFile = false;
     
     public boolean ifError() {return ifErrorOpenFile;}
     
    public BinaryInputStream getI() {
		return i;
	}
    
	public SCPECG getScpecg() {
		return scpecg;
	}
	
	public GraphicAttributeBase getGraphicAttribute() {
		return graphic;
	}

	private String nToken(String str){
		if (!str.equals("")){
			StringTokenizer st=new StringTokenizer(str," ");
			return st.nextToken();
			}
			else return "";			
	}
	
	private String nTokenCatEnd(String str){
		if (!str.equals("")){
			StringTokenizer st=new StringTokenizer(str," ");
			st.nextToken();
			String s=st.nextToken();
			return s.substring(1,s.length()-1);
			}
			else return "";			
	}
	// Гетеры для вкладки Пациента
	public String getPatientId() {
		return scpecg.getNamedField("PatientIdentificationNumber");
	}
	public String getFirstName() {
		return scpecg.getNamedField("FirstName");
	}
	public String getLastName() {
		return scpecg.getNamedField("LastName");
	}
	public String getSecondLastName() {
		return scpecg.getNamedField("SecondLastName");
	}
	public String getAge() {
		return nToken(scpecg.getNamedField("Age"));
	}
	public String getDataOfBirth() {
		return scpecg.getNamedField("DateOfBirth");
	}
	public String getHeight() {
		return nToken(scpecg.getNamedField("Height"));
	}
	public String getWeight() {
		return nToken(scpecg.getNamedField("Weight"));
	}
	public String getSex() {
		return nTokenCatEnd(scpecg.getNamedField("Sex"));
	}
	public String getRace() {
		return nTokenCatEnd(scpecg.getNamedField("Race"));
	}
	public String getDrugs() {
		return  scpecg.getNamedField("Drugs");
	}
	public String getSystolicBloodPressure() {
		return  scpecg.getNamedField("SystolicBloodPressure");
	}
	public String getDiastolicBloodPressure() {
		return  scpecg.getNamedField("DiastolicBloodPressure");
	}
	public String getDiagnosisOrReferralIndication() {
		return  scpecg.getNamedField("DiagnosisOrReferralIndication");		
	}
	public String getFreeTextMedicalHistory() {
		return scpecg.getNamedField("FreeTextMedicalHistory");
	}
	  //Адрес пациента
	public String getPostCode() {
		return scpecg.getNamedField("PostCode");
	}		
	public String getRegion() {
		return scpecg.getNamedField("Region");
	}
	public String getDistrict() {
		return scpecg.getNamedField("District");
	}
	public String getTown() {
		return scpecg.getNamedField("Town");
	}
	public String getStreet() {
		return scpecg.getNamedField("Street");
	}
	public String getHouse() {
		return scpecg.getNamedField("House");
	}
	public String getTimeOfresidence() {
		return scpecg.getNamedField("TimeOfresidence");
	}

	///////////////////////////////////
	//Гетеры для вкладки Прочее
	public String getAcquiringDeviceIdentificationNumber() {
		return  scpecg.getNamedField("AcquiringDeviceIdentificationNumber");
	}

	public String getAnalyzingDeviceIdentificationNumber() {
		return  scpecg.getNamedField("AnalyzingDeviceIdentificationNumber");
	}
	public String getAcquiringInstitutionDescription() {
		return scpecg.getNamedField("AcquiringInstitutionDescription");
	}
	/*public String getAnalyzingInstitutionDescription() {
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
*/

	/**
	 * <p>Open file and create SCPECGG.</p>
	 *
	 * @param	data filename
	 */
	private void openFile(String filename){
    	 try {
			i = new BinaryInputStream(new BufferedInputStream(new FileInputStream(new File(filename))),false);
			scpecg = new SCPECG(i,false);
			graphic = new GraphicAttribute(i, false);
			ifErrorOpenFile = false;
			
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			System.err.println("File=" + filename);
			ifErrorOpenFile = true;
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} 
     }

	/**
	 * @param	#1 SCPECG data filename,
	 */
	public DataHandler(String filename) {
		openFile(filename);
	}
	
	public static void main(String arg[]) {
		System.out.println("Start");
		DataHandler dh =new DataHandler("/home/ivan/11.scp");
		//вывод инфы о пациенте
		System.out.println(dh.getFirstName());
		System.out.println(dh.getAge());
		System.out.println(dh.getLastName());
		System.out.println(dh.getPatientId());
		System.out.println(dh.getSecondLastName());
		System.out.println(dh.getDataOfBirth());
		System.out.println(dh.getHeight());
		System.out.println(dh.getWeight());
		System.out.println(dh.getSex());
		System.out.println(dh.getRace());
		System.out.println(dh.getDrugs());
		System.out.println(dh.getSystolicBloodPressure());
		System.out.println(dh.getDiastolicBloodPressure());
		System.out.println(dh.getDiagnosisOrReferralIndication());
		System.out.println(dh.getFreeTextMedicalHistory());
		System.out.println(dh.getPostCode());
		System.out.println(dh.getDistrict());
		System.out.println(dh.getStreet());
		System.out.println(dh.getRegion());
		System.out.println(dh.getTown());
		System.out.println(dh.getHouse());
		System.out.println(dh.getTimeOfresidence());
		//System.out.println(dh.getAcquiringInstitutionDescription());
		//System.out.println(dh.getAnalyzingDeviceIdentificationNumber());
		/*	System.out.println(dh.getAnalyzingDepartmentDescription());
		System.out.println(dh.getAnalyzingDeviceIdentificationNumber());
		System.out.println(dh.getAnalyzingInstitutionDescription());
		System.out.println(dh.getBaselineFilter());
		System.out.println(dh.getDateOfAcquisition());
		System.out.println(dh.getDateTimeZone());
		System.out.println(dh.geteCGSequenceNumber());
		System.out.println(dh.getElectrodeConfigurationCode());
		System.out.println(dh.getFilterBitmap());
		System.out.println(dh.getFreeTextField());
		System.out.println(dh.getFreeTextMedicalHistory());
		System.out.println(dh.getLatestConfirmingPhysician());
		System.out.println(dh.getLowPassFilter());
		System.out.println(dh.getMedicalHistoryCodes());
		System.out.println(dh.getReferringPhysician());
		System.out.println(dh.getStatCode());*/
		System.out.println("End");
	}
}