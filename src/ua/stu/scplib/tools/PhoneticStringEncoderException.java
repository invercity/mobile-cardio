package ua.stu.scplib.tools;

import java.io.Serializable;

/**
 * @author	dclunie
 */
public class PhoneticStringEncoderException extends Exception implements Serializable {

	/**
	 * @param	msg
	 */

	public PhoneticStringEncoderException(String msg) {
		super(msg);
	}
}
	
