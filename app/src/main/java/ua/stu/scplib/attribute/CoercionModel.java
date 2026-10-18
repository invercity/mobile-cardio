package ua.stu.scplib.attribute;

import java.io.BufferedInputStream;
import java.io.FileInputStream;

import java.util.HashMap;
import java.util.Iterator;
import java.util.Vector;

/**
 * <p>A class to encapsulate patient and study information to be used for coercion of
 * identifiers in DICOM instances.</p>
 *
 * @author	dclunie
 */
public class CoercionModel {

	private class Patient {
		String patientName;
		String patientID;
		String patientBirthDate;
		String patientSex;
		
		Patient(AttributeList list) {
			     patientName=Attribute.getSingleStringValueOrEmptyString(list,TagFromName.PatientName);
			       patientID=Attribute.getSingleStringValueOrEmptyString(list,TagFromName.PatientID);
			patientBirthDate=Attribute.getSingleStringValueOrEmptyString(list,TagFromName.PatientBirthDate);
			      patientSex=Attribute.getSingleStringValueOrEmptyString(list,TagFromName.PatientSex);
		}
		
		Patient(String patientName,String patientID,String patientBirthDate,String patientSex) {
			     this.patientName=patientName;
			       this.patientID=patientID;
			this.patientBirthDate=patientBirthDate;
			      this.patientSex=patientSex;
		}

		String getKey() { return patientName+":"+patientID+":"+patientBirthDate+":"+patientSex; }
	
		public String toString() {
			StringBuffer strbuf = new StringBuffer();
			strbuf.append(getKey());
			return strbuf.toString();
		}
	}
	
	private class PatientConvertor {
		Patient oldPatientIdentifiers;
		Patient newPatientIdentifiers;
	}
	
	private HashMap patients;	// map of patient keys to CoercionModel.PatientConvertor objects
	
	/**
	 * @param	paths	paths to DICOM files from which to extract all patient and study information
	 */
	public CoercionModel(Vector paths) /*throws DicomException, IOException*/ {
		patients = new HashMap();
		if (paths != null) {
			for (int j=0; j< paths.size(); ++j) {
				String dicomFileName = (String)(paths.get(j));
				if (dicomFileName != null) {
					try {
						DicomInputStream i = new DicomInputStream(new BufferedInputStream(new FileInputStream(dicomFileName)));
						AttributeList list = new AttributeList();
						list.read(i,TagFromName.PixelData);
						i.close();
						Patient patient=new Patient(list);
						String key=patient.getKey();
						if (patients.get(key) == null) {
							patients.put(key,patient);
						}
					} catch (Exception e) {
						e.printStackTrace(System.err);
					}
				}
			}
		}
	}
	
	/**
	 *
	 */
	public String toString() {
		StringBuffer strbuf = new StringBuffer();
		Iterator i = patients.values().iterator();
		while (i.hasNext()) {
			Patient p = (Patient)(i.next());
			strbuf.append(p);
			strbuf.append("\n");
		}
		return strbuf.toString();
	}
}

