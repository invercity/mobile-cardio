package ua.stu.scplib.attribute;

import java.text.NumberFormat;

/**
 * <p>The {@link com.pixelmed.dicom.Attribute Attribute} class is an abstract class that contains the core
 * functionality for storing, accessing and maintaining values of a DICOM Attribute.</p>
 *
 * <p>Though instances of concrete sub-classes of this abstract class may be created
 * using their constructors, there is also a factory class, {@link com.pixelmed.dicom.AttributeFactory AttributeFactory}.</p>
 *
 * @see com.pixelmed.dicom.AttributeFactory
 * @see com.pixelmed.dicom.AttributeList
 *
 * @author	dclunie
 */
abstract public class Attribute {

	private AttributeTag tag;

	/***/
	protected long valueLength;
	/***/
	protected int  valueMultiplicity=0;

	/**
	 * <p>Construct an (empty) attribute; called only by concrete sub-classes.</p>
	 *
	 * @param	t	the tag of the attribute
	 */
	protected Attribute(AttributeTag t) {
		tag=t;
		valueLength=0;
		valueMultiplicity=0;
	}

	/**
	 * <p>Get the tag of this attribute.</p>
	 *
	 * @return	the tag
	 */
	public AttributeTag getTag()   { return tag; }

	/**
	 * <p>Get the group of the tag of this attribute.</p>
	 *
	 * @return	the group of the tag
	 */
	public int getGroup()          { return tag.getGroup(); }

	/**
	 * <p>Get the element of the tag of this attribute.</p>
	 *
	 * @return	the element of the tag
	 */
	public int getElement()        { return tag.getElement(); }

	/**
	 * <p>Get the value length of this attribute.</p>
	 *
	 * @return	the value length (does not include need for even-length padding, hence may be odd length)
	 */
	public long getVL()		{ return valueLength; }

	/**
	 * <p>Get the value multiplicity of this attribute.</p>
	 *
	 * @return	the value multiplicity
	 */
	public int  getVM()		{ return valueMultiplicity; }

	/**
	 * <p>Get the value representation of this attribute.</p>
	 *
	 * @return	the value representation as a two byte array
	 */
	public byte[] getVR()		{ return ValueRepresentation.UN; }

	/**
	 * <p>Get the value representation of this attribute.</p>
	 *
	 * @return	the value representation as a {@link java.lang.String String}
	 */
	public String getVRAsString()	{ return ValueRepresentation.getAsString(getVR()); }

	/**
	 * <p>Get the value length of this attribute, accounting for the need for even-length padding.</p>
	 *
	 * @return	the value length (padded to an even length)
	 */
	public long getPaddedVL()	{ return valueLength; }		// Needs to be overridden esp. in String attributes

	/**
	 * <p>Get the values of this attribute as strings, the way they were originally inserted or read.</p>
	 *
	 * @return			the values as an array of {@link java.lang.String String}
	 * @exception	DicomException	thrown if values are not available (such as not supported for this concrete attribute class)
	 */
	public String[] getOriginalStringValues()    throws DicomException { throw new DicomException("internal error - wrong value type for attribute "+tag); }

	/**
	 * <p>Get the values of this attribute as strings.</p>
	 *
	 * <p>The strings may have been cleaned up into a canonical form, such as to remove padding.</p>
	 *
	 * @return			the values as an array of {@link java.lang.String String}
	 * @exception	DicomException	thrown if values are not available (such as not supported for this concrete attribute class)
	 */
	public String[] getStringValues()            throws DicomException { return getStringValues(null); }

	/**
	 * <p>Get the values of this attribute as strings.</p>
	 *
	 * <p>The strings may have been cleaned up into a canonical form, such as to remove padding as well as numbers formatted.</p>
	 *
	 * @param	format		the format to use for each numerical or decimal value
	 * @return			the values as an array of {@link java.lang.String String}
	 * @exception	DicomException	thrown if values are not available (such as not supported for this concrete attribute class)
	 */
	public String[] getStringValues(NumberFormat format)            throws DicomException { throw new DicomException("internal error - wrong value type for attribute "+tag); }

	/**
	 * <p>Get the values of this attribute as a byte array.</p>
	 *
	 * @return			the values as an array of bytes
	 * @exception	DicomException	thrown if values are not available (such as not supported for this concrete attribute class)
	 */
	public byte[]   getByteValues()              throws DicomException { throw new DicomException("internal error - wrong value type for attribute "+tag); }

	/**
	 * <p>Get the values of this attribute as a short array.</p>
	 *
	 * @return			the values as an array of short
	 * @exception	DicomException	thrown if values are not available (such as not supported for this concrete attribute class)
	 */
	public short[]  getShortValues()             throws DicomException { throw new DicomException("internal error - wrong value type for attribute "+tag); }

	/**
	 * <p>Get the values of this attribute as an int array.</p>
	 *
	 * @return			the values as an array of int
	 * @exception	DicomException	thrown if values are not available (such as not supported for this concrete attribute class)
	 */
	public int[]    getIntegerValues()           throws DicomException { throw new DicomException("internal error - wrong value type for attribute "+tag); }

	/**
	 * <p>Get the values of this attribute as a long array.</p>
	 *
	 * @return			the values as an array of long
	 * @exception	DicomException	thrown if values are not available (such as not supported for this concrete attribute class)
	 */
	public long[]   getLongValues()              throws DicomException { throw new DicomException("internal error - wrong value type for attribute "+tag); }

	/**
	 * <p>Get the values of this attribute as a float array.</p>
	 *
	 * @return			the values as an array of float
	 * @exception	DicomException	thrown if values are not available (such as not supported for this concrete attribute class)
	 */
	public float[]  getFloatValues()             throws DicomException { throw new DicomException("internal error - wrong value type for attribute "+tag); }

	/**
	 * <p>Get the values of this attribute as a double array.</p>
	 *
	 * @return			the values as an array of double
	 * @exception	DicomException	thrown if values are not available (such as not supported for this concrete attribute class)
	 */
	public double[] getDoubleValues()            throws DicomException { throw new DicomException("internal error - wrong value type for attribute "+tag); }

	/**
	 * Add a(nother) {@link java.lang.String String} value after any existing values of this attribute.
	 *
	 * @param	v		value to add
	 * @exception	DicomException	thrown if value of this type is not valid for this concrete attribute class
	 */
	public void addValue(String v) throws DicomException { throw new DicomException("internal error - wrong value type for attribute "+tag); }

	/**
	 * Add a(nother) byte value after any existing values of this attribute.
	 *
	 * @param	v		value to add
	 * @exception	DicomException	thrown if value of this type is not valid for this concrete attribute class
	 */
	public void addValue(byte v)   throws DicomException { throw new DicomException("internal error - wrong value type for attribute "+tag); }

	/**
	 * Add a(nother) short value after any existing values of this attribute.
	 *
	 * @param	v		value to add
	 * @exception	DicomException	thrown if value of this type is not valid for this concrete attribute class
	 */
	public void addValue(short v)  throws DicomException { throw new DicomException("internal error - wrong value type for attribute "+tag); }

	/**
	 * Add a(nother) int value after any existing values of this attribute.
	 *
	 * @param	v		value to add
	 * @exception	DicomException	thrown if value of this type is not valid for this concrete attribute class
	 */
	public void addValue(int v)    throws DicomException { throw new DicomException("internal error - wrong value type for attribute "+tag); }

	/**
	 * Add a(nother) long value after any existing values of this attribute.
	 *
	 * @param	v		value to add
	 * @exception	DicomException	thrown if value of this type is not valid for this concrete attribute class
	 */
	public void addValue(long v)   throws DicomException { throw new DicomException("internal error - wrong value type for attribute "+tag); }

	/**
	 * Add a(nother) float value after any existing values of this attribute.
	 *
	 * @param	v		value to add
	 * @exception	DicomException	thrown if value of this type is not valid for this concrete attribute class
	 */
	public void addValue(float v)  throws DicomException { throw new DicomException("internal error - wrong value type for attribute "+tag); }

	/**
	 * Add a(nother) double value after any existing values of this attribute.
	 *
	 * @param	v		value to add
	 * @exception	DicomException	thrown if value of this type is not valid for this concrete attribute class
	 */
	public void addValue(double v) throws DicomException { throw new DicomException("internal error - wrong value type for attribute "+tag); }

	/**
	 * Replace any existing values with the supplied array of byte.
	 *
	 * @param	v		the array of new values
	 * @exception	DicomException	thrown if values of this type are not valid for this concrete attribute class
	 */
	public void setValues(byte[] v)   throws DicomException { throw new DicomException("internal error - wrong value type for attribute "+tag); }

	/**
	 * Replace any existing values with the supplied array of short.
	 *
	 * @param	v		the array of new values
	 * @exception	DicomException	thrown if values of this type are not valid for this concrete attribute class
	 */
	public void setValues(short[] v)  throws DicomException { throw new DicomException("internal error - wrong value type for attribute "+tag); }

	/**
	 * Replace any existing values with the supplied array of float.
	 *
	 * @param	v		the array of new values
	 * @exception	DicomException	thrown if values of this type are not valid for this concrete attribute class
	 */
	public void setValues(float[] v)  throws DicomException { throw new DicomException("internal error - wrong value type for attribute "+tag); }

	/**
	 * <p>Remove any existing values, making the attribute empty (zero length).</p>
	 *
	 * @exception	DicomException
	 */
	public void removeValues()   throws DicomException { throw new DicomException("internal error - removeValues() not implemented for attribute "+tag); }

	/**
	 * <p>Dump the contents of the attribute as a human-readable string.</p>
	 *
	 * <p>No new line is appended.</p>
	 *
	 * <p>The result is of the form:</p>
	 * <pre>
	 * (0xgggg,0xeeee) Name VR=&lt;XX&gt; VL=&lt;0xnnnn&gt; &lt;...&gt;
	 * </pre>
	 * <p>For example:</p>
	 * <pre>
	 * (0x0018,0x0020) ScanningSequence VR=&lt;CS> VL=&lt;0x2&gt; &lt;GR&gt;
	 * </pre>
	 *
	 * @param       dictionary      the dictionary to use to look up the name
	 * @return                      a single {@link java.lang.String String} value
	 */
	public String toString(DicomDictionary dictionary) {
		StringBuffer str = new StringBuffer();
		str.append(tag.toString());
		if (dictionary != null) {
			String name = dictionary.getNameFromTag(tag);
			if (name != null) {
				str.append(" ");
				str.append(name);
			}
		}
		str.append(" VR=<");
		str.append(getVRAsString());
		str.append("> VL=<0x");
		str.append(Long.toHexString(getVL()));
		str.append(">");
		return str.toString();
	}

	/**
	 * <p>Dump the contents of the attribute as a human-readable string.</p>
	 *
	 * <p>No new line is appended.</p>
	 *
	 * <p>The result is of the form:</p>
	 * <pre>
	 * (0xgggg,0xeeee) VR=&lt;XX&gt; VL=&lt;0xnnnn&gt; &lt;...&gt;
	 * </pre>
	 * <p>For example:</p>
	 * <pre>
	 * (0x0018,0x0020) VR=&lt;CS> VL=&lt;0x2&gt; &lt;GR&gt;
	 * </pre>
	 * @return		a single {@link java.lang.String String} value
	 */
	public String toString() {
		return toString(null);
	}
	
	// Some convenience methods ...

	/**
	 * <p>Get a single string value for the attribute.</p>
	 *
	 * <p>If there is more than one string value, only the first is returned.</p>
	 *
	 * <p>If there is no string value, the supplied default is returned.</p>
	 *
	 * <p>If there is an exception trying to fetch the value, the supplied default is returned.</p>
	 *
	 * <p>A canonicalized (unpadded) form is returned, not the original string.</p>
	 *
	 * @param	dflt	what to return if there is no (valid) string value
	 * @return		a single {@link java.lang.String String} value
	 */
	public String getSingleStringValueOrDefault(String dflt) {
		return getSingleStringValueOrDefault(dflt,null);
	}

	/**
	 * <p>Get a single string value for the attribute.</p>
	 *
	 * <p>If there is more than one string value, only the first is returned.</p>
	 *
	 * <p>If there is no string value, the supplied default is returned.</p>
	 *
	 * <p>If there is an exception trying to fetch the value, the supplied default is returned.</p>
	 *
	 * <p>A canonicalized (unpadded) form is returned, not the original string.</p>
	 *
	 * @param	dflt	what to return if there is no (valid) string value
	 * @param	format	the format to use for each numerical or decimal value (null if none)
	 * @return		a single {@link java.lang.String String} value
	 */
	public String getSingleStringValueOrDefault(String dflt,NumberFormat format) {
		String value = dflt;
		try {
			String[] sv = getStringValues(format);
			//String[] sv = getOriginalStringValues();
			if (sv != null && sv.length > 0) {
				String v = sv[0];
				if (v != null) value = v;
			}
		}
		catch (DicomException e) {
		}
		return value;
	}

	/**
	 * <p>Get a single string value for the attribute.</p>
	 *
	 * <p>If there is more than one string value, only the first is returned.</p>
	 *
	 * <p>If there is no string value, an empty string is returned.</p>
	 *
	 * <p>If there is an exception trying to fetch the value, an empty string is returned.</p>
	 *
	 * <p>A canonicalized (unpadded) form is returned, not the original string.</p>
	 *
	 * @return		a single {@link java.lang.String String} value
	 */
	public String getSingleStringValueOrEmptyString() { return getSingleStringValueOrDefault(""); }

	/**
	 * <p>Get a single string value for the attribute.</p>
	 *
	 * <p>If there is more than one string value, only the first is returned.</p>
	 *
	 * <p>If there is no string value, an empty string is returned.</p>
	 *
	 * <p>If there is an exception trying to fetch the value, an empty string is returned.</p>
	 *
	 * <p>A canonicalized (unpadded) form is returned, not the original string.</p>
	 *
	 * @param	format	the format to use for each numerical or decimal value
	 * @return		a single {@link java.lang.String String} value
	 */
	public String getSingleStringValueOrEmptyString(NumberFormat format) { return getSingleStringValueOrDefault("",format); }

	/**
	 * <p>Get a single string value for the attribute.</p>
	 *
	 * <p>If there is more than one string value, only the first is returned.</p>
	 *
	 * <p>If there is no string value, <code>null</code> is returned.</p>
	 *
	 * <p>If there is an exception trying to fetch the value, <code>null</code> is returned.</p>
	 *
	 * <p>A canonicalized (unpadded) form is returned, not the original string.</p>
	 *
	 * @return		a single {@link java.lang.String String} value
	 */
	public String getSingleStringValueOrNull() { return getSingleStringValueOrDefault(null); }

	/**
	 * <p>Get a single string value for the attribute.</p>
	 *
	 * <p>If there is more than one string value, only the first is returned.</p>
	 *
	 * <p>If there is no string value, <code>null</code> is returned.</p>
	 *
	 * <p>If there is an exception trying to fetch the value, <code>null</code> is returned.</p>
	 *
	 * <p>A canonicalized (unpadded) form is returned, not the original string.</p>
	 *
	 * @param	format	the format to use for each numerical or decimal value
	 * @return		a single {@link java.lang.String String} value
	 */
	public String getSingleStringValueOrNull(NumberFormat format) { return getSingleStringValueOrDefault(null,format); }

	/**
	 * <p>Get all the string values for the attribute, separated by the appropriate delimiter.</p>
	 *
	 * <p>If there is no string value, the supplied default is returned.</p>
	 *
	 * <p>If there is an exception trying to fetch the values, the supplied default is returned.</p>
	 *
	 * <p>A canonicalized (unpadded) form is returned, not the original string.</p>
	 *
	 * @param	dflt	what to return if there are no (valid) string values
	 * @return		the values as a delimited {@link java.lang.String String}
	 */
	public String getDelimitedStringValuesOrDefault(String dflt) {
		return getDelimitedStringValuesOrDefault(dflt,null);
	}

	/**
	 * <p>Get all the string values for the attribute, separated by the appropriate delimiter.</p>
	 *
	 * <p>If there is no string value, the supplied default is returned.</p>
	 *
	 * <p>If there is an exception trying to fetch the values, the supplied default is returned.</p>
	 *
	 * <p>A canonicalized (unpadded) form is returned, not the original string.</p>
	 *
	 * @param	dflt	what to return if there are no (valid) string values
	 * @param	format	the format to use for each numerical or decimal value (null if none)
	 * @return		the values as a delimited {@link java.lang.String String}
	 */
	public String getDelimitedStringValuesOrDefault(String dflt,NumberFormat format) {
		String value = dflt;
		try {
			String[] sv = getStringValues(format);
			if (sv != null) {
				StringBuffer str = new StringBuffer();
				for (int i=0; i< sv.length; ++i) {
					if (i > 0) str.append("\\");
					String v = sv[i];
					if (v != null) str.append(v);
				}
				value=str.toString();
			}
		}
		catch (DicomException e) {
		}
		return value;
	}

	/**
	 * <p>Get all the string values for the attribute, separated by the appropriate delimiter.</p>
	 *
	 * <p>If there is no string value, an empty string is returned.</p>
	 *
	 * <p>If there is an exception trying to fetch the values, an empty string is returned.</p>
	 *
	 * <p>A canonicalized (unpadded) form is returned, not the original string.</p>
	 *
	 * @return		the values as a delimited {@link java.lang.String String}
	 */
	public String getDelimitedStringValuesOrEmptyString() { return getDelimitedStringValuesOrDefault(""); }

	/**
	 * <p>Get all the string values for the attribute, separated by the appropriate delimiter.</p>
	 *
	 * <p>If there is no string value, an empty string is returned.</p>
	 *
	 * <p>If there is an exception trying to fetch the values, an empty string is returned.</p>
	 *
	 * <p>A canonicalized (unpadded) form is returned, not the original string.</p>
	 *
	 * @param	format	the format to use for each numerical or decimal value
	 * @return		the values as a delimited {@link java.lang.String String}
	 */
	public String getDelimitedStringValuesOrEmptyString(NumberFormat format) { return getDelimitedStringValuesOrDefault("",format); }

	/**
	 * <p>Get all the string values for the attribute, separated by the appropriate delimiter.</p>
	 *
	 * <p>If there is no string value, <code>null</code> is returned.</p>
	 *
	 * <p>If there is an exception trying to fetch the values, <code>null</code> is returned.</p>
	 *
	 * <p>A canonicalized (unpadded) form is returned, not the original string.</p>
	 *
	 * @return		the values as a delimited {@link java.lang.String String}
	 */
	public String getDelimitedStringValuesOrNull() { return getDelimitedStringValuesOrDefault(null); }

	/**
	 * <p>Get all the string values for the attribute, separated by the appropriate delimiter.</p>
	 *
	 * <p>If there is no string value, <code>null</code> is returned.</p>
	 *
	 * <p>If there is an exception trying to fetch the values, <code>null</code> is returned.</p>
	 *
	 * <p>A canonicalized (unpadded) form is returned, not the original string.</p>
	 *
	 * @param	format	the format to use for each numerical or decimal value
	 * @return		the values as a delimited {@link java.lang.String String}
	 */
	public String getDelimitedStringValuesOrNull(NumberFormat format) { return getDelimitedStringValuesOrDefault(null,format); }

	// Some static convenience methods ...

	/**
	 * <p>Get a single string value for a named attribute in an attribute list.</p>
	 *
	 * <p>If there is no such attribute, the supplied default is returned.</p>
	 *
	 * <p>If there is more than one string value, only the first is returned.</p>
	 *
	 * <p>If there is no string value, the supplied default is returned.</p>
	 *
	 * <p>If there is an exception trying to fetch the value, the supplied default is returned.</p>
	 *
	 * <p>A canonicalized (unpadded) form is returned, not the original string.</p>
	 *
	 * @param	list	the list of attributes in which to look for the attribute
	 * @param	tag	the tag of the attribute to find
	 * @param	dflt	what to return if there is no (valid) string value
	 * @return		a single {@link java.lang.String String} value
	 */
	public static String getSingleStringValueOrDefault(AttributeList list,AttributeTag tag,String dflt) {
		String value = dflt;
		if (list != null) {
			Attribute a = list.get(tag);
			if (a != null) value = a.getSingleStringValueOrDefault(dflt);
		}
		return value;
	}

	/**
	 * <p>Get a single string value for a named attribute in an attribute list.</p>
	 *
	 * <p>If there is no such attribute, the supplied default is returned.</p>
	 *
	 * <p>If there is more than one string value, only the first is returned.</p>
	 *
	 * <p>If there is no string value, the supplied default is returned.</p>
	 *
	 * <p>If there is an exception trying to fetch the value, the supplied default is returned.</p>
	 *
	 * <p>A canonicalized (unpadded) form is returned, not the original string.</p>
	 *
	 * @param	list	the list of attributes in which to look for the attribute
	 * @param	tag	the tag of the attribute to find
	 * @param	dflt	what to return if there is no (valid) string value
	 * @param	format	the format to use for each numerical or decimal value
	 * @return		a single {@link java.lang.String String} value
	 */
	public static String getSingleStringValueOrDefault(AttributeList list,AttributeTag tag,String dflt,NumberFormat format) {
		String value = dflt;
		if (list != null) {
			Attribute a = list.get(tag);
			if (a != null) value = a.getSingleStringValueOrDefault(dflt,format);
		}
		return value;
	}

	/**
	 * <p>Get a single string value for a named attribute in an attribute list.</p>
	 *
	 * <p>If there is no such attribute, an empty string is returned.</p>
	 *
	 * <p>If there is more than one string value, only the first is returned.</p>
	 *
	 * <p>If there is no string value, an empty string is returned.</p>
	 *
	 * <p>If there is an exception trying to fetch the value, an empty string is returned.</p>
	 *
	 * <p>A canonicalized (unpadded) form is returned, not the original string.</p>
	 *
	 * @param	list	the list of attributes in which to look for the attribute
	 * @param	tag	the tag of the attribute to find
	 * @return		a single {@link java.lang.String String} value
	 */
	public static String getSingleStringValueOrEmptyString(AttributeList list,AttributeTag tag) {
		return getSingleStringValueOrDefault(list,tag,"");
	}

	/**
	 * <p>Get a single string value for a named attribute in an attribute list.</p>
	 *
	 * <p>If there is no such attribute, an empty string is returned.</p>
	 *
	 * <p>If there is more than one string value, only the first is returned.</p>
	 *
	 * <p>If there is no string value, an empty string is returned.</p>
	 *
	 * <p>If there is an exception trying to fetch the value, an empty string is returned.</p>
	 *
	 * <p>A canonicalized (unpadded) form is returned, not the original string.</p>
	 *
	 * @param	list	the list of attributes in which to look for the attribute
	 * @param	tag	the tag of the attribute to find
	 * @param	format	the format to use for each numerical or decimal value
	 * @return		a single {@link java.lang.String String} value
	 */
	public static String getSingleStringValueOrEmptyString(AttributeList list,AttributeTag tag,NumberFormat format) {
		return getSingleStringValueOrDefault(list,tag,"",format);
	}

	/**
	 * <p>Get a single string value for a named attribute in an attribute list.</p>
	 *
	 * <p>If there is no such attribute, <code>null</code> is returned.</p>
	 *
	 * <p>If there is more than one string value, only the first is returned.</p>
	 *
	 * <p>If there is no string value, <code>null</code> is returned.</p>
	 *
	 * <p>If there is an exception trying to fetch the value, <code>null</code> is returned.</p>
	 *
	 * <p>A canonicalized (unpadded) form is returned, not the original string.</p>
	 *
	 * @param	list	the list of attributes in which to look for the attribute
	 * @param	tag	the tag of the attribute to find
	 * @return		a single {@link java.lang.String String} value
	 */
	public static String getSingleStringValueOrNull(AttributeList list,AttributeTag tag) {
		return getSingleStringValueOrDefault(list,tag,null);
	}

	/**
	 * <p>Get a single string value for a named attribute in an attribute list.</p>
	 *
	 * <p>If there is no such attribute, <code>null</code> is returned.</p>
	 *
	 * <p>If there is more than one string value, only the first is returned.</p>
	 *
	 * <p>If there is no string value, <code>null</code> is returned.</p>
	 *
	 * <p>If there is an exception trying to fetch the value, <code>null</code> is returned.</p>
	 *
	 * <p>A canonicalized (unpadded) form is returned, not the original string.</p>
	 *
	 * @param	list	the list of attributes in which to look for the attribute
	 * @param	tag	the tag of the attribute to find
	 * @param	format	the format to use for each numerical or decimal value
	 * @return		a single {@link java.lang.String String} value
	 */
	public static String getSingleStringValueOrNull(AttributeList list,AttributeTag tag,NumberFormat format) {
		return getSingleStringValueOrDefault(list,tag,null,format);
	}

	/**
	 * <p>Get all the string values for a named attribute in an attribute list, separated by the appropriate delimiter.</p>
	 *
	 * <p>If there is no such attribute, the supplied default is returned.</p>
	 *
	 * <p>If there is no string value, the supplied default is returned.</p>
	 *
	 * <p>If there is an exception trying to fetch the values, the supplied default is returned.</p>
	 *
	 * <p>A canonicalized (unpadded) form is returned, not the original string.</p>
	 *
	 * @param	list	the list of attributes in which to look for the attribute
	 * @param	tag	the tag of the attribute to find
	 * @param	dflt	what to return if there are no (valid) string values
	 * @return		the values as a delimited {@link java.lang.String String}
	 */
	public static String getDelimitedStringValuesOrDefault(AttributeList list,AttributeTag tag,String dflt) {
		String value = dflt;
		if (list != null) {
			Attribute a = list.get(tag);
			if (a != null) value = a.getDelimitedStringValuesOrDefault(dflt);
		}
		return value;
	}

	/**
	 * <p>Get all the string values for a named attribute in an attribute list, separated by the appropriate delimiter.</p>
	 *
	 * <p>If there is no such attribute, the supplied default is returned.</p>
	 *
	 * <p>If there is no string value, the supplied default is returned.</p>
	 *
	 * <p>If there is an exception trying to fetch the values, the supplied default is returned.</p>
	 *
	 * <p>A canonicalized (unpadded) form is returned, not the original string.</p>
	 *
	 * @param	list	the list of attributes in which to look for the attribute
	 * @param	tag	the tag of the attribute to find
	 * @param	dflt	what to return if there are no (valid) string values
	 * @param	format	the format to use for each numerical or decimal value
	 * @return		the values as a delimited {@link java.lang.String String}
	 */
	public static String getDelimitedStringValuesOrDefault(AttributeList list,AttributeTag tag,String dflt,NumberFormat format) {
		String value = dflt;
		if (list != null) {
			Attribute a = list.get(tag);
			if (a != null) value = a.getDelimitedStringValuesOrDefault(dflt,format);
		}
		return value;
	}

	/**
	 * <p>Get all the string values for a named attribute in an attribute list, separated by the appropriate delimiter.</p>
	 *
	 * <p>If there is no such attribute, an empty string is returned.</p>
	 *
	 * <p>If there is no string value, an empty string is returned.</p>
	 *
	 * <p>If there is an exception trying to fetch the values, an empty string is returned.</p>
	 *
	 * <p>A canonicalized (unpadded) form is returned, not the original string.</p>
	 *
	 * @param	list	the list of attributes in which to look for the attribute
	 * @param	tag	the tag of the attribute to find
	 * @return		the values as a delimited {@link java.lang.String String}
	 */
	public static String getDelimitedStringValuesOrEmptyString(AttributeList list,AttributeTag tag) {
		return getDelimitedStringValuesOrDefault(list,tag,"");
	}

	/**
	 * <p>Get all the string values for a named attribute in an attribute list, separated by the appropriate delimiter.</p>
	 *
	 * <p>If there is no such attribute, an empty string is returned.</p>
	 *
	 * <p>If there is no string value, an empty string is returned.</p>
	 *
	 * <p>If there is an exception trying to fetch the values, an empty string is returned.</p>
	 *
	 * <p>A canonicalized (unpadded) form is returned, not the original string.</p>
	 *
	 * @param	list	the list of attributes in which to look for the attribute
	 * @param	tag	the tag of the attribute to find
	 * @param	format	the format to use for each numerical or decimal value
	 * @return		the values as a delimited {@link java.lang.String String}
	 */
	public static String getDelimitedStringValuesOrEmptyString(AttributeList list,AttributeTag tag,NumberFormat format) {
		return getDelimitedStringValuesOrDefault(list,tag,"",format);
	}

	/**
	 * <p>Get all the string values for a named attribute in an attribute list, separated by the appropriate delimiter.</p>
	 *
	 * <p>If there is no such attribute, <code>null</code> is returned.</p>
	 *
	 * <p>If there is no string value, <code>null</code> is returned.</p>
	 *
	 * <p>If there is an exception trying to fetch the values, <code>null</code> is returned.</p>
	 *
	 * <p>A canonicalized (unpadded) form is returned, not the original string.</p>
	 *
	 * @param	list	the list of attributes in which to look for the attribute
	 * @param	tag	the tag of the attribute to find
	 * @return		the values as a delimited {@link java.lang.String String}
	 */
	public static String getDelimitedStringValuesOrNull(AttributeList list,AttributeTag tag) {
		return getDelimitedStringValuesOrDefault(list,tag,null);
	}

	/**
	 * <p>Get all the string values for a named attribute in an attribute list, separated by the appropriate delimiter.</p>
	 *
	 * <p>If there is no such attribute, <code>null</code> is returned.</p>
	 *
	 * <p>If there is no string value, <code>null</code> is returned.</p>
	 *
	 * <p>If there is an exception trying to fetch the values, <code>null</code> is returned.</p>
	 *
	 * <p>A canonicalized (unpadded) form is returned, not the original string.</p>
	 *
	 * @param	list	the list of attributes in which to look for the attribute
	 * @param	tag	the tag of the attribute to find
	 * @param	format	the format to use for each numerical or decimal value
	 * @return		the values as a delimited {@link java.lang.String String}
	 */
	public static String getDelimitedStringValuesOrNull(AttributeList list,AttributeTag tag,NumberFormat format) {
		return getDelimitedStringValuesOrDefault(list,tag,null,format);
	}

	/**
	 * <p>Get the values of a named attribute in an attribute list, as an array of strings.</p>
	 *
	 * <p>If there is no such attribute, <code>null</code> is returned.</p>
	 *
	 * <p>If there is an exception trying to fetch the values, <code>null</code> is returned.</p>
	 *
	 * <p>The strings may have been cleaned up into a canonical form, such as to remove padding.</p>
	 *
	 * @param	list	the list of attributes in which to look for the attribute
	 * @param	tag	the tag of the attribute to find
	 * @return		the values as an array of {@link java.lang.String String}
	 */
	public static String[] getStringValues(AttributeList list,AttributeTag tag) {
		String[] values = null;
		if (list != null) {
			Attribute a = list.get(tag);
			if (a != null) {
				try {
					values = a.getStringValues();
				}
				catch (DicomException e) {
				}
			}
		}
		return values;
	}

	/**
	 * <p>Get the values of a named attribute in an attribute list, as an array of strings.</p>
	 *
	 * <p>If there is no such attribute, <code>null</code> is returned.</p>
	 *
	 * <p>If there is an exception trying to fetch the values, <code>null</code> is returned.</p>
	 *
	 * <p>The strings may have been cleaned up into a canonical form, such as to remove padding.</p>
	 *
	 * @param	list	the list of attributes in which to look for the attribute
	 * @param	tag	the tag of the attribute to find
	 * @param	format	the format to use for each numerical or decimal value
	 * @return		the values as an array of {@link java.lang.String String}
	 */
	public static String[] getStringValues(AttributeList list,AttributeTag tag,NumberFormat format) {
		String[] values = null;
		if (list != null) {
			Attribute a = list.get(tag);
			if (a != null) {
				try {
					values = a.getStringValues(format);
				}
				catch (DicomException e) {
				}
			}
		}
		return values;
	}

	/**
	 * <p>Get a single int value for the attribute.</p>
	 *
	 * <p>If there is more than one value, only the first is returned.</p>
	 *
	 * <p>If there is no value, the supplied default is returned.</p>
	 *
	 * <p>If there is an exception trying to fetch the value, the supplied default is returned.</p>
	 *
	 * @param	dflt	what to return if there is no (valid) value
	 * @return		a single int value
	 */
	public int getSingleIntegerValueOrDefault(int dflt) {
		int value = dflt;
		try {
			int[] v = getIntegerValues();
			if (v != null && v.length > 0) {
				value = v[0];
			}
		}
		catch (DicomException e) {
		}
		return value;
	}

	/**
	 * <p>Get a single int value of a named attribute in an attribute list.</p>
	 *
	 * <p>If there is more than one value, only the first is returned.</p>
	 *
	 * <p>If there is no value, the supplied default is returned.</p>
	 *
	 * <p>If there is an exception trying to fetch the value, the supplied default is returned.</p>
	 *
	 * @param	list	the list of attributes in which to look for the attribute
	 * @param	tag	the tag of the attribute to find
	 * @param	dflt	what to return if there is no (valid) value
	 * @return		a single int value
	 */
	public static int getSingleIntegerValueOrDefault(AttributeList list,AttributeTag tag,int dflt) {
		int value = dflt;
		if (list != null) {
			Attribute a = list.get(tag);
			if (a != null) {
				value = a.getSingleIntegerValueOrDefault(dflt);
			}
		}
		return value;
	}


	/**
	 * <p>Get the values of a named attribute in an attribute list, as an array of int.</p>
	 *
	 * <p>If there is no such attribute, <code>null</code> is returned.</p>
	 *
	 * <p>If there is an exception trying to fetch the values, <code>null</code> is returned.</p>
	 *
	 * @param	list	the list of attributes in which to look for the attribute
	 * @param	tag	the tag of the attribute to find
	 * @return		the values as an array of int
	 */
	public static int[] getIntegerValues(AttributeList list,AttributeTag tag) {
		int[] values = null;
		if (list != null) {
			Attribute a = list.get(tag);
			if (a != null) {
				try {
					values = a.getIntegerValues();
				}
				catch (DicomException e) {
				}
			}
		}
		return values;
	}

	/**
	 * <p>Get a single long value for the attribute.</p>
	 *
	 * <p>If there is more than one value, only the first is returned.</p>
	 *
	 * <p>If there is no value, the supplied default is returned.</p>
	 *
	 * <p>If there is an exception trying to fetch the value, the supplied default is returned.</p>
	 *
	 * @param	dflt	what to return if there is no (valid) value
	 * @return		a single long value
	 */
	public long getSingleLongValueOrDefault(long dflt) {
		long value = dflt;
		try {
			long[] v = getLongValues();
			if (v != null && v.length > 0) {
				value = v[0];
			}
		}
		catch (DicomException e) {
		}
		return value;
	}

	/**
	 * <p>Get a single long value of a named attribute in an attribute list.</p>
	 *
	 * <p>If there is more than one value, only the first is returned.</p>
	 *
	 * <p>If there is no value, the supplied default is returned.</p>
	 *
	 * <p>If there is an exception trying to fetch the value, the supplied default is returned.</p>
	 *
	 * @param	list	the list of attributes in which to look for the attribute
	 * @param	tag	the tag of the attribute to find
	 * @param	dflt	what to return if there is no (valid) value
	 * @return		a single long value
	 */
	public static long getSingleLongValueOrDefault(AttributeList list,AttributeTag tag,long dflt) {
		long value = dflt;
		if (list != null) {
			Attribute a = list.get(tag);
			if (a != null) {
				value = a.getSingleLongValueOrDefault(dflt);
			}
		}
		return value;
	}


	/**
	 * <p>Get the values of a named attribute in an attribute list, as an array of long.</p>
	 *
	 * <p>If there is no such attribute, <code>null</code> is returned.</p>
	 *
	 * <p>If there is an exception trying to fetch the values, <code>null</code> is returned.</p>
	 *
	 * @param	list	the list of attributes in which to look for the attribute
	 * @param	tag	the tag of the attribute to find
	 * @return		the values as an array of long
	 */
	public static long[] getLongValues(AttributeList list,AttributeTag tag) {
		long[] values = null;
		if (list != null) {
			Attribute a = list.get(tag);
			if (a != null) {
				try {
					values = a.getLongValues();
				}
				catch (DicomException e) {
				}
			}
		}
		return values;
	}

	/**
	 * <p>Get a single double value for the attribute.</p>
	 *
	 * <p>If there is more than one value, only the first is returned.</p>
	 *
	 * <p>If there is no value, the supplied default is returned.</p>
	 *
	 * <p>If there is an exception trying to fetch the value, the supplied default is returned.</p>
	 *
	 * @param	dflt	what to return if there is no (valid) value
	 * @return		a single double value
	 */
	public double getSingleDoubleValueOrDefault(double dflt) {
		double value = dflt;
		try {
			double[] v = getDoubleValues();
			if (v != null && v.length > 0) {
				value = v[0];
			}
		}
		catch (DicomException e) {
		}
		return value;
	}

	/**
	 * <p>Get a single double value of a named attribute in an attribute list.</p>
	 *
	 * <p>If there is more than one value, only the first is returned.</p>
	 *
	 * <p>If there is no value, the supplied default is returned.</p>
	 *
	 * <p>If there is an exception trying to fetch the value, the supplied default is returned.</p>
	 *
	 * @param	list	the list of attributes in which to look for the attribute
	 * @param	tag	the tag of the attribute to find
	 * @param	dflt	what to return if there is no (valid) value
	 * @return		a single double value
	 */
	public static double getSingleDoubleValueOrDefault(AttributeList list,AttributeTag tag,double dflt) {
		double value = dflt;
		if (list != null) {
			Attribute a = list.get(tag);
			if (a != null) {
				value = a.getSingleDoubleValueOrDefault(dflt);
			}
		}
		return value;
	}

	/**
	 * <p>Get the values of a named attribute in an attribute list, as an array of double.</p>
	 *
	 * <p>If there is no such attribute, <code>null</code> is returned.</p>
	 *
	 * <p>If there is an exception trying to fetch the values, <code>null</code> is returned.</p>
	 *
	 * @param	list	the list of attributes in which to look for the attribute
	 * @param	tag	the tag of the attribute to find
	 * @return		the values as an array of double
	 */
	public static double[] getDoubleValues(AttributeList list,AttributeTag tag) {
		double[] values = null;
		if (list != null) {
			Attribute a = list.get(tag);
			if (a != null) {
				try {
					values = a.getDoubleValues();
				}
				catch (DicomException e) {
				}
			}
		}
		return values;
	}
}


