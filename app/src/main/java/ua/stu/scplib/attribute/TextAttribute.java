package ua.stu.scplib.attribute;

import java.io.*;

import java.text.NumberFormat;

/**
 * <p>An abstract class specializing {@link com.pixelmed.dicom.Attribute Attribute} for
 * the family of text attributes.</p>
 *
 * @author	dclunie
 */
abstract public class TextAttribute extends Attribute {

	/***/
	protected SpecificCharacterSet specificCharacterSet;

	/***/
	String values[];

	/**
	 * <p>Decode a byte array into a string.</p>
	 *
	 * @param	bytes	the byte buffer in which the encoded string is located
	 * @param	offset	the offset into the buffer
	 * @param	length	the number of bytes to be decoded
	 * @return		the string decoded according to the specified or default specific character set
	 */
	protected String translateByteArrayToString(byte[] bytes,int offset,int length) {	// NOT static
		return specificCharacterSet == null ? new String(bytes,0,length) : specificCharacterSet.translateByteArrayToString(bytes,0,length);
	}

	/**
	 * <p>Encode a string into a byte array.</p>
	 *
	 * @param	string				the string to be encoded
	 * @return					the byte array encoded according to the specified or default specific character set
	 * @exception	UnsupportedEncodingException
	 */
	protected byte[] translateStringToByteArray(String string) throws UnsupportedEncodingException {	// NOT static
		return specificCharacterSet == null ? string.getBytes() : specificCharacterSet.translateStringToByteArray(string);
	}

	/**
	 * <p>Construct an (empty) attribute; called only by concrete sub-classes.</p>
	 *
	 * @param	t	the tag of the attribute
	 */
	protected TextAttribute(AttributeTag t) {
		super(t);
		doCommonConstructorStuff(null);
	}

	/**
	 * <p>Construct an (empty) attribute; called only by concrete sub-classes.</p>
	 *
	 * @param	t			the tag of the attribute
	 * @param	specificCharacterSet	the character set to be used for the text
	 */
	protected TextAttribute(AttributeTag t,SpecificCharacterSet specificCharacterSet) {
		super(t);
		doCommonConstructorStuff(specificCharacterSet);
	}

	/**
	 * <p>Read an attribute from an input stream; called only by concrete sub-classes.</p>
	 *
	 * @param	t			the tag of the attribute
	 * @param	vl			the value length of the attribute
	 * @param	i			the input stream
	 * @param	specificCharacterSet	the character set to be used for the text
	 * @exception	IOException
	 * @exception	DicomException
	 */
	protected TextAttribute(AttributeTag t,long vl,DicomInputStream i,SpecificCharacterSet specificCharacterSet) throws IOException, DicomException {
		super(t);
		doCommonConstructorStuff(vl,i,specificCharacterSet);
	}

	/**
	 * <p>Read an attribute from an input stream; called only by concrete sub-classes.</p>
	 *
	 * @param	t			the tag of the attribute
	 * @param	vl			the value length of the attribute
	 * @param	i			the input stream
	 * @param	specificCharacterSet	the character set to be used for the text
	 * @exception	IOException
	 * @exception	DicomException
	 */
	protected TextAttribute(AttributeTag t,Long vl,DicomInputStream i,SpecificCharacterSet specificCharacterSet) throws IOException, DicomException {
		super(t);
		doCommonConstructorStuff(vl.longValue(),i,specificCharacterSet);
	}

	/**
	 * @param	specificCharacterSet
	 */
	private void doCommonConstructorStuff(SpecificCharacterSet specificCharacterSet) {
		values=null;
		this.specificCharacterSet=specificCharacterSet;
	}
	
	/**
	 * @param	vl
	 * @param	i
	 * @param	specificCharacterSet
	 * @exception	IOException
	 * @exception	DicomException
	 */
	private void doCommonConstructorStuff(long vl,DicomInputStream i,SpecificCharacterSet specificCharacterSet) throws IOException, DicomException {
		doCommonConstructorStuff(specificCharacterSet);
		if (vl > 0) {
			byte[] buffer = new byte[(int)vl];
			try {
				i.readInsistently(buffer,0,(int)vl);
			}
			catch (IOException e) {
				throw new DicomException("Failed to read value (length "+vl+" dec) in "+ValueRepresentation.getAsString(getVR())+" attribute "+getTag());
			}
			String sbuf = translateByteArrayToString(buffer,0,(int)vl);
			vl=sbuf.length();	// NB. this only makes a difference for multi-byte character sets
			addValue(sbuf);
		}
	}

	/***/
	public long getPaddedVL() {
		long vl = getVL();
		if (vl%2 != 0) ++vl;
		return vl;
	}
	
	/**
	 * <p>Get the appropriate byte for padding a string to an even length.</p>
	 *
	 * @return	the byte pad value appropriate to the VR
	 */
	private byte getPadByte() { return 0x20; }

	/**
	 * @exception	DicomException
	 */
	private byte[] getPaddedByteValues() throws DicomException {
		String[] v = getStringValues();
		//byte[] b = v == null ? null : v[0].getBytes();
		byte[] b = null;
		try {
			if (v != null) b = translateStringToByteArray(v[0]);
		}
		catch (UnsupportedEncodingException e) {
			throw new DicomException("Unsupported encoding:"+e);
		}
		// should padding take into account character set, i.e. could the pad character be different ? :(
		if (b != null) {
			int bl = b.length;
			if (bl%2 != 0) {
				byte[] b2 = new byte[bl+1];
				System.arraycopy(b,0,b2,0,bl);
				b2[bl]=getPadByte();
				b=b2;
			}
			if (getPaddedVL() != b.length) {
				throw new DicomException("Internal error - byte array length not equal to expected padded VL");
			}
		}
		return b;
	}
	
	/***/
	public String toString(DicomDictionary dictionary) {
		StringBuffer str = new StringBuffer();
		str.append(super.toString(dictionary));
		str.append(" <");
		try {
			String[] v = getStringValues();
			if (v != null) str.append(v[0]);
		}
		catch (DicomException e) {
			str.append("XXXX");
		}
		str.append(">");
		return str.toString();
	}

	/**
	 * @param	format		the format to use for each numerical or decimal value
	 * @exception	DicomException
	 */
	public String[] getStringValues(NumberFormat format) throws DicomException {
		// ignore number format for generic text attributes
		return values;
	}

	/**
	 * @param	v
	 * @exception	DicomException
	 */
	public void addValue(String v) throws DicomException {
		if (values != null || valueMultiplicity > 0) throw new DicomException("No more than one value allowed for text attributes");
		values=new String[1];
		values[0]=v;
		valueLength=v.length();
		++valueMultiplicity;
	}

	/**
	 * @exception	DicomException
	 */
	public void removeValues() throws DicomException {
		valueLength=0;
		valueMultiplicity=0;
		values=null;
	}

}

