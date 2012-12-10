package ua.stu.scplib.attribute;

import java.io.*;

import java.text.NumberFormat;

import ua.stu.scplib.tools.FloatFormatter;

/**
 * <p>A concrete class specializing {@link com.pixelmed.dicom.Attribute Attribute} for
 * Float Single (FL) attributes.</p>
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
public class FloatSingleAttribute extends Attribute {

	float[] values;

	static int bytesPerValue=4;

	private void flushCachedCopies() {
	}

	/**
	 * <p>Construct an (empty) attribute.</p>
	 *
	 * @param	t	the tag of the attribute
	 */
	public FloatSingleAttribute(AttributeTag t) {
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
	public FloatSingleAttribute(AttributeTag t,long vl,DicomInputStream i) throws IOException, DicomException {
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
	public FloatSingleAttribute(AttributeTag t,Long vl,DicomInputStream i) throws IOException, DicomException {
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
		for (int j=0; j<vm; ++j) addValue(i.readFloat());
	}
	
	/***/
	public String toString(DicomDictionary dictionary) {
		StringBuffer str = new StringBuffer();
		str.append(super.toString(dictionary));
		str.append(" [");
		try {
			float[] v = getFloatValues();
			if (v != null) {
				for (int j=0; j<v.length; ++j) {
					if (j > 0) str.append(",");
					str.append(v[j]);
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
		float[] v = getFloatValues();
		if (v != null) {
			sv=new String[v.length];
			for (int j=0; j<v.length; ++j) {
				sv[j] = (format == null) ? FloatFormatter.toString(v[j]) : format.format(v[j]);
			}
		}
		return sv;
	}

	/**
	 * @exception	DicomException
	 */
	public float[] getFloatValues() throws DicomException {
		return values;
	}

	/**
	 * @param	v
	 * @exception	DicomException
	 */
	public void addValue(float v) throws DicomException {
		flushCachedCopies();
		values=ArrayCopyUtilities.expandArray(values);
		values[valueMultiplicity++]=v;
		valueLength+=4;
	}

	/**
	 * @param	v
	 * @exception	DicomException
	 */
	public void addValue(double v) throws DicomException {
		addValue((float)v);
	}

	/**
	 * @param	v
	 * @exception	DicomException
	 */
	public void addValue(short v) throws DicomException {
		addValue((float)v);
	}

	/**
	 * @param	v
	 * @exception	DicomException
	 */
	public void addValue(int v) throws DicomException {
		addValue((float)v);
	}

	/**
	 * @param	v
	 * @exception	DicomException
	 */
	public void addValue(long v) throws DicomException {
		addValue((float)v);
	}

	/**
	 * @param	v
	 * @exception	DicomException
	 */
	public void addValue(String v) throws DicomException {
		float floatValue = 0;
		try {
			floatValue=(float)Double.parseDouble(v);
		}
		catch (NumberFormatException e) {
			throw new DicomException(e.toString());
		}
		addValue(floatValue);
	}

	/**
	 * <p>Get the value representation of this attribute (FL).</p>
	 *
	 * @return	'F','L' in ASCII as a two byte array; see {@link com.pixelmed.dicom.ValueRepresentation ValueRepresentation}
	 */
	public byte[] getVR() { return ValueRepresentation.FL; }

}

