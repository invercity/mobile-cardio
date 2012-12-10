package ua.stu.scplib.attribute;

import java.io.*;

/**
 * <p>A concrete class specializing {@link com.pixelmed.dicom.Attribute Attribute} for
 * Other Byte (OB) attributes.</p>
 *
 * <p>Though an instance of this class may be created
 * using its constructors, there is also a factory class, {@link com.pixelmed.dicom.AttributeFactory AttributeFactory}.</p>
 *
 * @see com.pixelmed.dicom.Attribute
 * @see com.pixelmed.dicom.AttributeFactory
 * @see com.pixelmed.dicom.AttributeList
 *
 * @author	dclunie
 */
public class OtherByteAttribute extends Attribute {

	private byte[] values;

	/**
	 * <p>Construct an (empty) attribute.</p>
	 *
	 * @param	t	the tag of the attribute
	 */
	public OtherByteAttribute(AttributeTag t) {
		super(t);
	}

	/**
	 * <p>Read an attribute from an input stream.</p>
	 *
	 * @param	t			the tag of the attribute
	 * @param	vl			the value length of the attribute
	 * @param	i			the input stream
	 * @exception	IOException
	 * @exception	DicomException
	 */
	public OtherByteAttribute(AttributeTag t,long vl,DicomInputStream i) throws IOException, DicomException {
		super(t);
		doCommonConstructorStuff(vl,i);
	}

	/**
	 * <p>Read an attribute from an input stream.</p>
	 *
	 * @param	t			the tag of the attribute
	 * @param	vl			the value length of the attribute
	 * @param	i			the input stream
	 * @exception	IOException
	 * @exception	DicomException
	 */
	public OtherByteAttribute(AttributeTag t,Long vl,DicomInputStream i) throws IOException, DicomException {
		super(t);
		doCommonConstructorStuff(vl.longValue(),i);
	}

	/**
	 * @param	vl
	 * @param	i
	 * @exception	IOException
	 * @exception	DicomException
	 */
	private void doCommonConstructorStuff(long vl,DicomInputStream i) throws IOException, DicomException {
		values=null;
		valueLength=vl;

		if (vl > 0) {
			byte[] buffer = new byte[(int)vl];
			try {
				i.readInsistently(buffer,0,(int)vl);
			}
			catch (IOException e) {
				throw new DicomException("Failed to read value (length "+vl+" dec) in "+ValueRepresentation.getAsString(getVR())+" attribute "+getTag());
			}
			setValues(buffer);
		}
	}

	/***/
	public long getPaddedVL() {
		long vl = getVL();
		if (vl%2 != 0) ++vl;
		return vl;
	}
	
	/***/
	public String toString(DicomDictionary dictionary) {
		StringBuffer str = new StringBuffer();
		str.append(super.toString(dictionary));
		str.append(" []");		// i.e. don't really dump values ... too many
		return str.toString();
	}

	/**
	 * @param	v
	 * @exception	DicomException
	 */
	public void setValues(byte[] v) throws DicomException {
		values=v;
		valueMultiplicity=1;		// different from normal value types where VM is size of array
		valueLength=v.length;
	}

	/**
	 * @exception	DicomException
	 */
	public byte[] getByteValues() throws DicomException { return values; }

	/**
	 * <p>Get the value representation of this attribute (OB).</p>
	 *
	 * @return	'O','B' in ASCII as a two byte array; see {@link com.pixelmed.dicom.ValueRepresentation ValueRepresentation}
	 */
	public byte[] getVR() { return ValueRepresentation.OB; }
}

