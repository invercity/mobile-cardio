package ua.stu.scplib.data;

import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.StringTokenizer;

import ua.stu.scplib.attribute.BinaryInputStream;
import ua.stu.scplib.structure.SCPECG;

public class DataHandler {
	private BinaryInputStream i = null;
	private SCPECG scpecg = null;
	private PInfo pi = null;
	private OInfo oi = null;

	/**
	 * Получить информацию о пациенте
	 * 
	 * @return PInfo
	 */
	public PInfo getPInfo() {
		return pi;
	}

	/**
	 * Установить информацию о пациенте
	 * 
	 * @return void
	 * @param PInfo
	 */
	public void setPInfo(PInfo pi) {
		this.pi = pi;
	}

	/**
	 * Получить прочую информацию
	 * 
	 * @return OInfo
	 * 
	 */
	public OInfo getOInfo() {
		return oi;
	}

	/**
	 * Установить прочую информацию
	 * 
	 * @return void
	 * @param OInfo
	 */
	public void setOInfo(OInfo oi) {
		this.oi = oi;
	}

	/**
	 * <p>
	 * Open file and create SCPECGG.
	 * </p>
	 * 
	 * @param String
	 *            filename
	 */
	private void openFile(String filename) {
		try {
			i = new BinaryInputStream(new BufferedInputStream(
					new FileInputStream(new File(filename))), false);
			scpecg = new SCPECG(i, false);
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	/**
	 * @param #1 SCPECG data filename,
	 */
	DataHandler(String filename) {
		openFile(filename);
		pi = new PInfo(scpecg);
		oi = new OInfo(scpecg);
	}

	public static void main(String arg[]) {
		System.out.println("Start");		
		 DataHandler dh =new DataHandler("/home/ivan/11.scp");

		 System.out.println(dh.getPInfo().getFirstName());
		 System.out.println(dh.getOInfo().getAcquiringDeviceIdentificationNumber());
		 //System.out.println(dh.getOInfo().getAnalyzingDeviceIdentificationNumber());
		
		 /* System.out.println(dh.getOInfo().getAnalyzingDepartmentDescription());
		  System.out.println(dh.getOInfo().getAnalyzingDeviceIdentificationNumber());
		 System.out.println(dh.getOInfo().getAnalyzingInstitutionDescription());
		 System.out.println(dh.getOInfo().getAcquiringInstitutionDescription());
		  System.out.println(dh.getOInfo().getBaselineFilter());
		  System.out.println(dh.getOInfo().getDateOfAcquisition());
		  System.out.println(dh.getOInfo().getDateTimeZone());
		  System.out.println(dh.getOInfo().geteCGSequenceNumber());
		  System.out.println(dh.getOInfo().getElectrodeConfigurationCode());
		  System.out.println(dh.getOInfo().getFilterBitmap());
		  System.out.println(dh.getOInfo().getFreeTextField());
		  System.out.println(dh.getOInfo().getLatestConfirmingPhysician());
		  System.out.println(dh.getOInfo().getLowPassFilter());
		  System.out.println(dh.getOInfo().getMedicalHistoryCodes());
		  System.out.println(dh.getOInfo().getReferringPhysician());
		  System.out.println(dh.getOInfo().getStatCode());
		  System.out.println(dh.getOInfo().getTimeOfAcquisition());
		 */
		System.out.println("End");
	}
}
