package ua.stu.scplib.attribute;

/**
 * <p>An individual DICOM data element (attribute) tag that
 * includes a group and element (each a 16 bit unsigned binary).</p>
 *
 * <p>Implements {@link java.lang.Comparable Comparable} in order to facilitate sorting (e.g. in lists
 * which are indexed by AttributeTag).</p>
 * 
 * <p>Safe to use in hashed collections such as {@link java.util.Hashtable Hashtable} and {@link java.util.HashMap HashMap}
 * (i.e. it takes care to implement {@link java.lang.Object#hashCode() hashCode()} and {@link java.lang.Object#equals(Object) equals()} consistently).</p>
 * 
 * @author	dclunie
 */
public class AttributeTag implements Comparable {

	/***/
	private int group;
	/***/
	private int element;

	/**
	 * <p>Construct a DICOM data element (attribute) tag.</p>
	 *
	 * @param	group	the 16 bit unsigned binary group
	 * @param	element	the 16 bit unsigned binary element
	 */
	public AttributeTag(int group,int element) {
		this.group=group;
		this.element=element;
	}

	/**
	 * <p>Get the group value.</p>
	 *
	 * @return	the 16 bit unsigned binary group
	 */
	public int getGroup()   { return group; }

	/**
	 * <p>Get the element value.</p>
	 *
	 * @return	the 16 bit unsigned binary element
	 */
	public int getElement() { return element; }

	/**
	 * <p>Is the tag a private tag ?</p>
	 *
	 * <p>Private tags are those with odd-numbered groups.</p>
	 *
	 * @return	true if private
	 */
	public boolean isPrivate() { return group%2 != 0; }

	/**
	 * <p>Get a human-readable rendering of the tag.</p>
	 *
	 * <p>This takes the form "(0xgggg,0xeeee)" where gggg and eeee are the zero-padded
	 * hexadecimal representations of the group and element respectively.</p>
	 *
	 * @return	the string rendering of the tag
	 */
	public String toString() {
		StringBuffer str = new StringBuffer();
		str.append("(0x");
		String groupString = Integer.toHexString(group);
		for (int i=groupString.length(); i<4; ++i) str.append("0");
		str.append(groupString);
		str.append(",0x");
		String elementString = Integer.toHexString(element);
		for (int i=elementString.length(); i<4; ++i) str.append("0");
		str.append(elementString);
		str.append(")");
		return str.toString();
	}

	/**
	 * <p>Get a single long integer which represents the tag.</p>
	 *
	 * <p>The low 16 bits are filled with the element number and the next higher 16 bits with the group number.</p>
	 *
	 * @return	the long integer representation of the tag
	 */
	private Long getLong() {
		return new Long((((long)group)<<16)+(((long)element)&0xffff));
	}

	/**
	 * <p>Compare tags based on the numeric order of their group and then element values.</p>
	 *
	 * @param	o	the {@link com.pixelmed.dicom.AttributeTag AttributeTag} to compare this {@link com.pixelmed.dicom.AttributeTag AttributeTag} against
	 * @return		the value 0 if the argument tag is equal to this object; a value less than 0 if this tag is
	 *			less than the argument tag; and a value greater than 0 if this tag is greater than the argument tag
	 */
	public int compareTo(Object o) {
		//System.err.println("AttributeTag.compareTo: "+this+" vs. "+(AttributeTag)o+" = "+(getLong().compareTo(((AttributeTag)o).getLong())));
		return getLong().compareTo(((AttributeTag)o).getLong());
	}

	/**
	 * <p>Compare tags based on their group and element values.</p>
	 *
	 * @param	o	the {@link com.pixelmed.dicom.AttributeTag AttributeTag} to compare this {@link com.pixelmed.dicom.AttributeTag AttributeTag} against
	 * @return		true if the same group and element number
	 */
	public boolean equals(Object o) {
		//System.err.println("AttributeTag.equals: "+this+" vs. "+(AttributeTag)o+" = "+(group == ((AttributeTag)o).getGroup() && element == ((AttributeTag)o).getElement()));
		return group == ((AttributeTag)o).getGroup() && element == ((AttributeTag)o).getElement();
	}

	/**
	 * <p>Get a hash value which represents the tag.</p>
	 *
	 * <p>This method is implemented to override {@link java.lang.Object#hashCode() java.lang.Object.hashCode()}
	 * so as to comply with the contract that two tags that return true for equals()
	 * will return the same value for hashCode(), which would not be the case
	 * unless overridden (i.e. two allocations of a tag with the same group and
	 * element would be equal but the default implementation would return different hash values).</p>
	 *
	 * @return	a hash value representing the tag
	 */
	public int hashCode() {
		return (group<<16)+(element&0xffff);
	}

}



