package ua.stu.scplib.attribute;

import java.io.*;

import java.text.NumberFormat;

/**
 * <p>A concrete class specializing {@link com.pixelmed.dicom.Attribute Attribute} for
 * Attribute Tag (AT) attributes.</p>
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
public class AttributeTagAttribute extends Attribute {

	int[] groups;
	int[] elements;

	static int bytesPerValue=4;

	/**
	 * <p>Construct an (empty) attribute.</p>
	 *
	 * @param	t	the tag of the attribute
	 */
	public AttributeTagAttribute(AttributeTag t) {
		super(t);
		groups=null;
		elements=null;
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
	public AttributeTagAttribute(AttributeTag t,long vl,DicomInputStream i) throws IOException, DicomException {
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
	public AttributeTagAttribute(AttributeTag t,Long vl,DicomInputStream i) throws IOException, DicomException {
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
		valueLength=vl;
		if (valueLength%bytesPerValue != 0) throw new DicomException("incorrect value length for VR "+getVR());
		int vm=(int)(valueLength/bytesPerValue);

		//valueMultiplicity=vm;
		//values=new short[valueMultiplicity];
		//for (int j=0; j<valueMultiplicity; ++j) values[j]=(short)(i.readUnsigned16());

		groups=null;
		elements=null;
		for (int j=0; j<vm; ++j) {
			int g = ((int)(i.readUnsigned16())&0xffff);
			int e = ((int)(i.readUnsigned16())&0xffff);
			addValue(g,e);
		}
	}
	
	/***/
	public String toString(DicomDictionary dictionary) {
		StringBuffer str = new StringBuffer();
		str.append(super.toString(dictionary));
		str.append(" [");
		try {
			if (groups != null && elements != null) {
				for (int j=0; j<groups.length; ++j) {
					if (j > 0) str.append(",");
					str.append("(0x");
					String groupString = Integer.toHexString(groups[j]);
					for (int i=groupString.length(); i<4; ++i) str.append("0");
					str.append(groupString);
					str.append(",0x");
					String elementString = Integer.toHexString(elements[j]);
					for (int i=elementString.length(); i<4; ++i) str.append("0");
					str.append(elementString);
					str.append(")");
				}
			}
		}
		catch (Exception e) {
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
		// ignore number format
		String sv[] = null;
		if (groups != null && elements != null) {
			sv=new String[groups.length];
			for (int j=0; j<groups.length; ++j) {
				StringBuffer str = new StringBuffer();
				str.append("(0x");
				String groupString = Integer.toHexString(groups[j]);
				for (int i=groupString.length(); i<4; ++i) str.append("0");
				str.append(groupString);
				str.append(",0x");
				String elementString = Integer.toHexString(elements[j]);
				for (int i=elementString.length(); i<4; ++i) str.append("0");
				str.append(elementString);
				str.append(")");
				sv[j]=str.toString();
			}
		}
		return sv;
	}
	
	/**
	 * @exception	DicomException
	 */
	public AttributeTag[] getAttributeTagValues() throws DicomException {
		AttributeTag atv[] = null;
		if (groups != null && elements != null) {
			atv=new AttributeTag[groups.length];
			for (int j=0; j<groups.length; ++j) {
				atv[j]=new AttributeTag(groups[j],elements[j]);
			}
		}
		return atv;
	}

	/**
	 * @param	g
	 * @param	e
	 * @exception	DicomException
	 */
	public void addValue(int g,int e) throws DicomException {
		groups=ArrayCopyUtilities.expandArray(groups);
		groups[valueMultiplicity]=g;
		elements=ArrayCopyUtilities.expandArray(elements);
		elements[valueMultiplicity++]=e;
		valueLength=valueMultiplicity*4;
	}

	/**
	 * @param	t
	 * @exception	DicomException
	 */
	public void addValue(AttributeTag t) throws DicomException {
		addValue(t.getGroup(),t.getElement());
	}

	/**
	 * <p>Get the value representation of this attribute (AT).</p>
	 *
	 * @return	'A','T' in ASCII as a two byte array; see {@link com.pixelmed.dicom.ValueRepresentation ValueRepresentation}
	 */
	public byte[] getVR() { return ValueRepresentation.AT; }

}

