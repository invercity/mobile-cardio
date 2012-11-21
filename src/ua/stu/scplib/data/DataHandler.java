package ua.stu.scplib.data;

import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;

import ua.stu.scplib.attribute.BinaryInputStream;
import ua.stu.scplib.attribute.GraphicAttribute;
import ua.stu.scplib.attribute.GraphicAttributeBase;
import ua.stu.scplib.structure.SCPECG;

public class DataHandler {
	private SCPECG scpecg = null;
	private BinaryInputStream i = null;
	private GraphicAttributeBase graphic = null;
	private PInfo pi = null;
	private OInfo oi = null;

	/**
	 * get patient info from ECG
	 * 
	 * @return PInfo
	 */
	public PInfo getPInfo() {
		return pi;
	}

	/**
	 * set patient info from ECG
	 * 
	 * @return void
	 * 
	 * @param PInfo
	 *            :object which handle patient info
	 */
	public void setPInfo(PInfo pi) {
		this.pi = pi;
	}

	/**
	 * get other information from ECG
	 * 
	 * @return OInfo
	 * 
	 */
	public OInfo getOInfo() {
		return oi;
	}

	/**
	 * set other information from ECG
	 * 
	 * @return void
	 * 
	 * @param OInfo
	 *            :object which handle other info
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
		// = null;
		try {
			i = new BinaryInputStream(new BufferedInputStream(
					new FileInputStream(new File(filename))), false);
			scpecg = new SCPECG(i, false);
			i = new BinaryInputStream(new BufferedInputStream(
					new FileInputStream(filename)), false);
			graphic = new GraphicAttribute(i, false);
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
	public DataHandler(String filename) {
		openFile(filename);
		pi = new PInfo(scpecg);
		oi = new OInfo(scpecg);
	}

	/**
	 * Take graphic attributes of ECG
	 * 
	 * @return the graphic
	 */
	public GraphicAttributeBase getGraphic() {
		return graphic;
	}

	public static void main(String arg[]) {
		System.out.println("Start");
		DataHandler dh = new DataHandler("/home/ivan/11.scp");
		System.out.println(dh.getOInfo().getManufacturer());
		System.out.println(dh.getOInfo().getPOSCP());
		System.out.println(dh.getOInfo().getSerialNumber());
		System.out.println(dh.getOInfo().getVersionPO());
		System.out.println(dh.getOInfo().getPrint());
		System.out.println(dh.getOInfo().getAnalysis());
		System.out.println(dh.getOInfo().getReceive());
		System.out.println(dh.getOInfo().getStorage());
		System.out.println(dh.getOInfo().getAcquiringInstitutionDescription());
		System.out.println(dh.getOInfo().getAnalyzingInstitutionDescription());
		System.out.println(dh.getOInfo().getAcquiringDepartmentDescription());
		System.out.println(dh.getOInfo().getAnalyzingDepartmentDescription());
		System.out.println(dh.getOInfo().getLatestConfirmingPhysician());
		System.out.println(dh.getOInfo().getReferringPhysician());
		System.out.println(dh.getOInfo().getTechnicianDescription());
		System.out.println(dh.getOInfo().getFreeTextField());
		System.out.println(dh.getOInfo().getTimeOfAcquisition());
		System.out.println(dh.getOInfo().getDateOfAcquisition());

		System.out.println("End");
	}
}
