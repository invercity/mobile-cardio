package ua.stu.scplib.attribute;

import java.io.*;

/**
 * <p>An abstract class specializing {@link com.pixelmed.dicom.Attribute Attribute} for
 * the family of string attributes that support different specific character sets.</p>
 *
 * @author	dclunie
 */
abstract public class StringAttributeAffectedBySpecificCharacterSet extends StringAttribute {

	/**
	 * @param	t
	 */
	protected StringAttributeAffectedBySpecificCharacterSet(AttributeTag t) {
		super(t);
	}
	
	/**
	 * @param	t
	 * @param	specificCharacterSet
	 */
	protected StringAttributeAffectedBySpecificCharacterSet(AttributeTag t,SpecificCharacterSet specificCharacterSet) {
		super(t,specificCharacterSet);
	}

	/**
	 * @param	t
	 * @param	vl
	 * @param	i
	 * @param	specificCharacterSet
	 * @exception	IOException
	 * @exception	DicomException
	 */
	protected StringAttributeAffectedBySpecificCharacterSet(AttributeTag t,long vl,DicomInputStream i,SpecificCharacterSet specificCharacterSet) throws IOException, DicomException {
		super(t,vl,i,specificCharacterSet);
	}
}

