package ua.stu.scplib.attribute;

import java.io.*;

import java.text.NumberFormat;

/**
 * <p>An abstract class specializing {@link com.pixelmed.dicom.Attribute Attribute} for
 * the family of string attributes.</p>
 *
 * @author	dclunie
 */
abstract public class StringAttribute extends Attribute {

	/***/
	protected SpecificCharacterSet specificCharacterSet;	// always null except for derived classes

	/**
	 * <p>Get the specific character set for this attribute.</p>
	 *
	 * @return		the specific character set, or null if none
	 */
	public SpecificCharacterSet getSpecificCharacterSet() { return specificCharacterSet; }

	/***/
	byte[] originalByteValues;
	/***/
	String originalValues[];
	/***/
	String cachedUnpaddedStringCopy[];
	/***/
	int[] cachedIntegerCopy;
	/***/
	long[] cachedLongCopy;
	/***/
	float[] cachedFloatCopy;
	/***/
	double[] cachedDoubleCopy;
	/***/
	byte[] cachedPaddedByteValues;

	/***/
	private void flushCachedCopies() {
		cachedUnpaddedStringCopy=null;
		cachedIntegerCopy=null;
		cachedLongCopy=null;
		cachedFloatCopy=null;
		cachedDoubleCopy=null;
		cachedPaddedByteValues=null;
	}

	/**
	 * <p>Decode a byte array into a string.</p>
	 *
	 * @param	bytes	the byte buffer in which the encoded string is located
	 * @param	offset	the offset into the buffer
	 * @param	length	the number of bytes to be decoded
	 * @return		the string decoded according to the specified or default specific character set
	 */
	protected String translateByteArrayToString(byte[] bytes,int offset,int length) {	// NOT static
//System.err.println("StringAttribute.translateByteArrayToString()");
//System.err.println("StringAttribute.translateByteArrayToString() - specificCharacterSet is "+specificCharacterSet);
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
//System.err.println("StringAttribute.translateStringToByteArray() - string is <"+string+">");
//System.err.println("StringAttribute.translateStringToByteArray() - string is "+com.pixelmed.utils.StringUtilities.dump(string));
//System.err.println("StringAttribute.translateStringToByteArray() - specificCharacterSet is "+specificCharacterSet);
		byte[] b = specificCharacterSet == null ? string.getBytes() : specificCharacterSet.translateStringToByteArray(string);
//System.err.println("StringAttribute.translateStringToByteArray(): return byte array is:\n"+com.pixelmed.utils.HexDump.dump(b));
		return b;
	}

	/**
	 * <p>Construct an (empty) attribute; called only by concrete sub-classes.</p>
	 *
	 * @param	t	the tag of the attribute
	 */
	protected StringAttribute(AttributeTag t) {
		super(t);
		doCommonConstructorStuff(null);
	}

	/**
	 * <p>Construct an (empty) attribute; called only by concrete sub-classes.</p>
	 *
	 * @param	t			the tag of the attribute
	 * @param	specificCharacterSet	the character set to be used for the text
	 */
	protected StringAttribute(AttributeTag t,SpecificCharacterSet specificCharacterSet) {
		super(t);
		doCommonConstructorStuff(specificCharacterSet);
	}

	/**
	 * <p>Read an attribute from an input stream; called only by concrete sub-classes.</p>
	 *
	 * @param	t			the tag of the attribute
	 * @param	vl			the value length of the attribute
	 * @param	i			the input stream
	 * @exception	IOException
	 * @exception	DicomException
	 */
	protected StringAttribute(AttributeTag t,long vl,DicomInputStream i) throws IOException, DicomException {
		super(t);
		doCommonConstructorStuff(vl,i,null);
	}


	/**
	 * <p>Read an attribute from an input stream; called only by concrete sub-classes.</p>
	 *
	 * @param	t			the tag of the attribute
	 * @param	vl			the value length of the attribute
	 * @param	i			the input stream
	 * @exception	IOException
	 * @exception	DicomException
	 */
	protected StringAttribute(AttributeTag t,Long vl,DicomInputStream i) throws IOException, DicomException {
		super(t);
		doCommonConstructorStuff(vl.longValue(),i,null);
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
	protected StringAttribute(AttributeTag t,long vl,DicomInputStream i,SpecificCharacterSet specificCharacterSet) throws IOException, DicomException {
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
	protected StringAttribute(AttributeTag t,Long vl,DicomInputStream i,SpecificCharacterSet specificCharacterSet) throws IOException, DicomException {
		super(t);
		doCommonConstructorStuff(vl.longValue(),i,specificCharacterSet);
	}

	/**
	 * <p>Flesh out a constructed (empty) attribute; called only by concrete sub-classes.</p>
	 *
	 * @param	specificCharacterSet	the character set to be used for the text
	 */
	private void doCommonConstructorStuff(SpecificCharacterSet specificCharacterSet) {
		flushCachedCopies();
		this.specificCharacterSet=specificCharacterSet;
		originalValues=null;
		originalByteValues=null;
	}

	/**
	 * <p>Read a constructed attribute from an input stream; called only by concrete sub-classes.</p>
	 *
	 * @param	vl			the value length of the attribute
	 * @param	i			the input stream
	 * @param	specificCharacterSet	the character set to be used for the text
	 * @exception	IOException
	 * @exception	DicomException
	 */
	private void doCommonConstructorStuff(long vl,DicomInputStream i,SpecificCharacterSet specificCharacterSet) throws IOException, DicomException {
		doCommonConstructorStuff(specificCharacterSet);
		originalValues=null;
		originalByteValues=null;
		if (vl > 0) {
			originalByteValues = new byte[(int)vl];
			try {
				i.readInsistently(originalByteValues,0,(int)vl);
			}
			catch (IOException e) {
				throw new DicomException("Failed to read value (length "+vl+" dec) in "+ValueRepresentation.getAsString(getVR())+" attribute "+getTag());
			}
			String sbuf = translateByteArrayToString(originalByteValues,0,(int)vl);		// may fail due to unsuported encoding and return null, though should not happen since should use default (000307)
			if (sbuf != null) {
				vl=sbuf.length();	// NB. this only makes a difference for multi-byte character sets
				int start=0;
				int delim=0;
				while (true) {
					if (delim >= vl || sbuf.charAt(delim) == '\\') {
						addValue(sbuf.substring(start,delim));
						++delim;
						start=delim;
						if (delim >= vl) break;
					}
					else {
					++delim;
					}
				}
			}
			// else do not add values since translateByteArrayToString failed (probably unsuported encoding), but leave VL alone (>0) in case untranslated original bytes are useful (000307) :(
		}
	}
	
	/***/
	public long getPaddedVL() {
		byte[] b = null;
		try {
			b = getPaddedByteValues();
		}
		catch (DicomException e) {
			b = null;
		}
		return b == null ? 0 : b.length;
	}
	
	/**
	 * <p>Get the appropriate byte for padding a string to an even length.</p>
	 *
	 * @return	the byte pad value appropriate to the VR
	 */
	protected byte getPadByte() { return 0x20; }	// space for most everything, UI will override to 0x00

	/**
	 * @exception	DicomException
	 */
	private byte[] getPaddedByteValues() throws DicomException {
		if (cachedPaddedByteValues == null) {
			cachedPaddedByteValues = extractPaddedByteValues();
		}
		return cachedPaddedByteValues;
	}
	
	/**
	 * @exception	DicomException
	 */
	private byte[] extractPaddedByteValues() throws DicomException {
		StringBuffer sb = new StringBuffer();
		String[] v = getOriginalStringValues();
		if (v != null) {
			for (int j=0; j<v.length; ++j) {
				if (j > 0) sb.append("\\");
				sb.append(v[j]);
			}
		}
		//byte[] b = sb.toString().getBytes();
		byte[] b = null;
		try {
			b = translateStringToByteArray(sb.toString());
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
			//if (getPaddedVL() != b.length) {
			//	throw new DicomException("Internal error - "+this+" - byte array length ("+b.length+") not equal to expected padded VL("+getPaddedVL()+")");
			//}
		}
		return b;
	}

	/**
	 * @param	o
	 * @exception	IOException
	 * @exception	DicomException
	 */
//	public void write(DicomOutputStream o) throws DicomException, IOException {
//		writeBase(o);
//		byte b[] = getPaddedByteValues();
//		if (b != null && b.length > 0) o.write(b);
//	}
	
	/***/
	public String toString(DicomDictionary dictionary) {
		StringBuffer str = new StringBuffer();
		str.append(super.toString(dictionary));
		str.append(" <");
		try {
			//String[] v = getStringValues();
			String[] v = getOriginalStringValues();
			if (v != null) {
				for (int j=0; j<v.length; ++j) {
					if (j > 0) str.append("\\");
					str.append(v[j]);
				}
			}
		}
		catch (DicomException e) {
			str.append("XXXX");
		}
		str.append(">");
		return str.toString();
	}



	/**
	 * <p>Get the values of this attribute as a byte array.</p>
	 *
	 * <p>Returns the originally read byte values, if read from a stream, otherwise converts the string to bytes and pads them.</p>
	 *
	 * @return			the values as an array of bytes
	 * @exception	DicomException	thrown if values are not available
	 */
	public byte[]   getByteValues() throws DicomException {
		return originalByteValues == null ? getPaddedByteValues() : originalByteValues;
	}

	/**
	 * <p>Get the values of this attribute as strings.</p>
	 *
	 * <p>The strings are first cleaned up into a canonical form, to remove leading and trailing padding.</p>
	 *
	 * @param	format		the format to use for each numerical or decimal value
	 * @return			the values as an array of {@link java.lang.String String}
	 * @exception	DicomException	not thrown
	 */
	public String[] getStringValues(NumberFormat format) throws DicomException {
		// ignore number format for generic string attributes
		if (cachedUnpaddedStringCopy == null) cachedUnpaddedStringCopy=ArrayCopyUtilities.copyStringArrayRemovingLeadingAndTrailingPadding(originalValues);
		return cachedUnpaddedStringCopy;
	}

	/**
	 * <p>Get the values of this attribute as strings, the way they were originally inserted or read.</p>
	 *
	 * @return			the values as an array of {@link java.lang.String String}
	 * @exception	DicomException	not thrown
	 */
	public String[] getOriginalStringValues() throws DicomException {
		return originalValues;
	}

	/**
	 * @exception	DicomException
	 */
	public int[] getIntegerValues() throws DicomException {
		if (cachedIntegerCopy == null) cachedIntegerCopy=ArrayCopyUtilities.copyStringToIntArray(getStringValues());	// must be unpadded
		return cachedIntegerCopy;
	}

	/**
	 * @exception	DicomException
	 */
	public long[] getLongValues() throws DicomException {
		if (cachedLongCopy == null) cachedLongCopy=ArrayCopyUtilities.copyStringToLongArray(getStringValues());		// must be unpadded
		return cachedLongCopy;
	}

	/**
	 * @exception	DicomException
	 */
	public float[] getFloatValues() throws DicomException {
		if (cachedFloatCopy == null) cachedFloatCopy=ArrayCopyUtilities.copyStringToFloatArray(getStringValues());	// must be unpadded
		return cachedFloatCopy;
	}

	/**
	 * @exception	DicomException
	 */
	public double[] getDoubleValues() throws DicomException {
		if (cachedDoubleCopy == null) cachedDoubleCopy=ArrayCopyUtilities.copyStringToDoubleArray(getStringValues());	// must be unpadded
		return cachedDoubleCopy;
	}

	/**
	 * @param	v
	 * @exception	DicomException
	 */
	public void addValue(String v) throws DicomException {
		flushCachedCopies();
		originalValues=ArrayCopyUtilities.expandArray(originalValues);
		valueLength+=v.length();
		if (valueMultiplicity > 0) ++valueLength; // for the delimiter
		originalValues[valueMultiplicity++]=v;
	}
	

	/**
	 * @param	v
	 * @exception	DicomException
	 */
	public void addValue(byte v) throws DicomException {
		addValue(Short.toString((short)(((int)v)&0xff)));	// don't ask !
	}

	/**
	 * @param	v
	 * @exception	DicomException
	 */
	public void addValue(short v) throws DicomException {
		addValue(Short.toString(v));
	}

	/**
	 * @param	v
	 * @exception	DicomException
	 */
	public void addValue(int v) throws DicomException {
		addValue(Integer.toString(v));
	}

	/**
	 * @param	v
	 * @exception	DicomException
	 */
	public void addValue(long v) throws DicomException {
		addValue(Long.toString(v));
	}

	/**
	 * @param	v
	 * @exception	DicomException
	 */
	public void addValue(float v) throws DicomException {
		addValue(Float.toString(v));
	}

	/**
	 * @param	v
	 * @exception	DicomException
	 */
	public void addValue(double v) throws DicomException {
		addValue(Double.toString(v));
	}

	/**
	 * @exception	DicomException
	 */
	public void removeValues() throws DicomException {
		valueLength=0;
		valueMultiplicity=0;
		originalValues=null;
		flushCachedCopies();
	}
}

