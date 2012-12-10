package ua.stu.scplib.attribute;

import java.io.*;

/**
 * <p>A concrete class specializing {@link com.pixelmed.dicom.Attribute Attribute} for
 * Other Word (OW) attributes.</p>
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
public class OtherWordAttribute extends Attribute {

	private short[] values;

	/**
	 * <p>Construct an (empty) attribute.</p>
	 *
	 * @param	t	the tag of the attribute
	 */
	public OtherWordAttribute(AttributeTag t) {
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
	public OtherWordAttribute(AttributeTag t,long vl,DicomInputStream i) throws IOException, DicomException {
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
	public OtherWordAttribute(AttributeTag t,Long vl,DicomInputStream i) throws IOException, DicomException {
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
			int len = (int)(vl/2);
			short buffer[] = new short[len];
			i.readUnsigned16(buffer,len);
			setValues(buffer);
		}
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
	public void setValues(short[] v) throws DicomException {
		values=v;
		valueMultiplicity=1;		// different from normal value types where VM is size of array
		valueLength=v.length*2;
	}

	/**
	 * @exception	DicomException
	 */
	public short[] getShortValues() throws DicomException { return values; }

	/**
	 * <p>Get the value representation of this attribute (OW).</p>
	 *
	 * @return	'O','W' in ASCII as a two byte array; see {@link com.pixelmed.dicom.ValueRepresentation ValueRepresentation}
	 */
	public byte[] getVR() { return ValueRepresentation.OW; }

}

