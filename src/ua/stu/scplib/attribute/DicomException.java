package ua.stu.scplib.attribute;

import java.io.Serializable;

/**
 * @author	dclunie
 */
public class DicomException extends Exception implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	/**
	 * @param	msg
	 */
	public DicomException(String msg) {
		super(msg);
	}
}


