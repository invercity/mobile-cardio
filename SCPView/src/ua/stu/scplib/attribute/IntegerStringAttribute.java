package ua.stu.scplib.attribute;

import java.io.*;

import java.text.NumberFormat;

/**
 * <p>A concrete class specializing {@link com.pixelmed.dicom.Attribute Attribute} for
 * Integer String (IS) attributes.</p>
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
public class IntegerStringAttribute extends StringAttribute {

	/**
	 * <p>Construct an (empty) attribute.</p>
	 *
	 * @param	t	the tag of the attribute
	 */
	public IntegerStringAttribute(AttributeTag t) {
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
	public IntegerStringAttribute(AttributeTag t,long vl,DicomInputStream i) throws IOException, DicomException {
		super(t,vl,i);
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
	public IntegerStringAttribute(AttributeTag t,Long vl,DicomInputStream i) throws IOException, DicomException {
		super(t,vl.longValue(),i);
	}

	/**
	 * <p>Get the value representation of this attribute (IS).</p>
	 *
	 * @return	'I','S' in ASCII as a two byte array; see {@link com.pixelmed.dicom.ValueRepresentation ValueRepresentation}
	 */
	public byte[] getVR() { return ValueRepresentation.IS; }

        /**
	 * @param	format		the format to use for each numerical or decimal value
         * @exception	DicomException
         */
        public String[] getStringValues(NumberFormat format) throws DicomException {
		String sv[] = null;
		if (format == null) {
			sv=super.getStringValues(null);
		}
		else {
			long[] v = getLongValues();
			if (v != null) {
				sv=new String[v.length];
				for (int j=0; j<v.length; ++j) {
					sv[j] = format.format(v[j]);
				}
			}
		}
		return sv;
	}
}

