package ua.stu.scplib.attribute;

import java.io.*;
import java.text.NumberFormat;

/**
 * <p>A concrete class specializing {@link com.pixelmed.dicom.Attribute Attribute} for
 * Unsigned Short (US) attributes.</p>
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
public class UnsignedShortAttribute extends Attribute {

	short[] values;
	int[] cachedIntegerCopy;
	long[] cachedLongCopy;

	static int bytesPerValue=2;

	private void flushCachedCopies() {
		cachedIntegerCopy=null;
		cachedLongCopy=null;
	}

	/**
	 * <p>Construct an (empty) attribute.</p>
	 *
	 * @param	t	the tag of the attribute
	 */
	public UnsignedShortAttribute(AttributeTag t) {
		super(t);
		flushCachedCopies();
		values=null;
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
	public UnsignedShortAttribute(AttributeTag t,long vl,DicomInputStream i) throws IOException, DicomException {
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
	public UnsignedShortAttribute(AttributeTag t,Long vl,DicomInputStream i) throws IOException, DicomException {
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
		flushCachedCopies();
		if (vl%bytesPerValue != 0) throw new DicomException("incorrect value length for VR "+getVR());
		int vm=(int)(vl/bytesPerValue);
		values=null;
		for (int j=0; j<vm; ++j) addValue((short)(i.readUnsigned16()));
	}

	/***/
	public String toString(DicomDictionary dictionary) {
		StringBuffer str = new StringBuffer();
		str.append(super.toString(dictionary));
		str.append(" [");
		try {
			//short[] v = getShortValues();
			int[] v = getIntegerValues();
			if (v != null) {
				for (int j=0; j<v.length; ++j) {
					if (j > 0) str.append(",");
					str.append("0x");
					str.append(Integer.toHexString(v[j]));
				}
			}
		}
		catch (DicomException e) {
			str.append("XXXX");
		}
		str.append("]");
		return str.toString();
	}

        /**
	 * @param	format		the format to use for each numerical or decimal value
         * @exception	DicomException
         */
        public String[] getStringValues(NumberFormat format) throws DicomException {
		String sv[] = null;
		int[] v = getIntegerValues();
		if (v != null) {
			sv=new String[v.length];
			for (int j=0; j<v.length; ++j) {
				sv[j] = (format == null) ? Integer.toString(v[j]) : format.format(v[j]);
			}
		}
		return sv;
	}

	/**
	 * @exception	DicomException
	 */
	public short[] getShortValues() throws DicomException {
		return values;
	}

	/**
	 * @exception	DicomException
	 */
	public int[] getIntegerValues() throws DicomException {
		if (cachedIntegerCopy == null) cachedIntegerCopy=ArrayCopyUtilities.copyUnsignedShortToIntArray(values);
		return cachedIntegerCopy;
	}

	/**
	 * @exception	DicomException
	 */
	public long[] getLongValues() throws DicomException {
		if (cachedLongCopy == null) cachedLongCopy=ArrayCopyUtilities.copyUnsignedShortToLongArray(values);
		return cachedLongCopy;
	}

	/**
	 * @param	v
	 * @exception	DicomException
	 */
	public void addValue(short v) throws DicomException {
		flushCachedCopies();
		values=ArrayCopyUtilities.expandArray(values);
		values[valueMultiplicity++]=v;
		valueLength+=2;
	}

	/**
	 * @param	v
	 * @exception	DicomException
	 */
	public void addValue(int v) throws DicomException {
		addValue((short)v);
	}

	/**
	 * @param	v
	 * @exception	DicomException
	 */
	public void addValue(long v) throws DicomException {
		addValue((short)v);
	}

	/**
	 * @param	v
	 * @exception	DicomException
	 */
	public void addValue(float v) throws DicomException {
		addValue((short)v);
	}

	/**
	 * @param	v
	 * @exception	DicomException
	 */
	public void addValue(double v) throws DicomException {
		addValue((short)v);
	}

	/**
	 * @param	v
	 * @exception	DicomException
	 */
	public void addValue(String v) throws DicomException {
		short shortValue = 0;
		try {
			shortValue=(short)Integer.parseInt(v);
		}
		catch (NumberFormatException e) {
			throw new DicomException(e.toString());
		}
		addValue(shortValue);
	}

	/**
	 * <p>Get the value representation of this attribute (US).</p>
	 *
	 * @return	'U','S' in ASCII as a two byte array; see {@link com.pixelmed.dicom.ValueRepresentation ValueRepresentation}
	 */
	public byte[] getVR() { return ValueRepresentation.US; }

}

