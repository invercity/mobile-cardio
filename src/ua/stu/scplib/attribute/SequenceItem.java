package ua.stu.scplib.attribute;

/**
 * <p>A class to provide support for the contents of an individual item of a DICOM Sequence (SQ)
 * attribute, each of which consists of an entire dataset (list of attributes).</p>
 *
 * @see com.pixelmed.dicom.SequenceAttribute
 *
 * @author	dclunie
 */
public class SequenceItem {

	private AttributeList list;
	private long byteOffset;		// value of 0 is flag that it is not set

	/**
	 * <p>Construct a sequence attribute item with a list of attributes.</p>
	 *
	 * @param	l	the list of attributes that comprise the item
	 */
	public SequenceItem(AttributeList l) {
		list=l;
		byteOffset=0;
	}

	/**
	 * <p>Construct a sequence attribute item with a list of attributes,
	 * additionally keeping track of where in the byte stream that the
	 * attributes were read from the item starts, for use in supporting
	 * DICOM Directory Records which are indexed by physical byte offset
	 * (see {@link com.pixelmed.dicom.DicomDirectory DicomDirectory}).</p>
	 *
	 * @param	l	the list of attributes that comprise the item
	 * @param	offset
	 */
	public SequenceItem(AttributeList l,long offset) {
		list=l;
		byteOffset=offset;
	}

	/**
	 * <p>Get the list of attributes in this item.</p>
	 *
	 * @return	the attribute list
	 */
	public AttributeList getAttributeList() { return list; }

	/**
	 * <p>Get the byte offset of the start of this item recorded when the item was read.</p>
	 *
	 * @return	the byte offset
	 */
	public long getByteOffset() { return byteOffset; }
	
	/**
	 * <p>Dump the item in a human readable form, list the contained attributes.</p>
	 *
	 * @param	dictionary
	 * @return			the string representing the content of the item
	 */
	public String toString(DicomDictionary dictionary) {
		StringBuffer str = new StringBuffer();
		str.append("%item");
		if (byteOffset != 0) {
			str.append(" [starts at 0x");
			str.append(Long.toHexString(byteOffset));
			str.append("]");
		}
		str.append("\n");
		str.append(list.toString(dictionary));
		str.append("%enditem");
		return str.toString();
	}
	
	/**
	 * <p>Dump the item in a human readable form, list the contained attributes.</p>
	 *
	 * @return	the string representing the content of the item
	 */
	public String toString() {
		return toString(null);
	}

}

