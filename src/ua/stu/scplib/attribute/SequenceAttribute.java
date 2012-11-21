package ua.stu.scplib.attribute;

import java.util.*;

/**
 * <p>A concrete class specializing {@link com.pixelmed.dicom.Attribute Attribute} for
 * Sequence (SQ) attributes.</p>
 *
 * <p>Though an instance of this class may be created
 * using its constructors, there is also a factory class, {@link com.pixelmed.dicom.AttributeFactory AttributeFactory}.</p>
 *
 * @see com.pixelmed.dicom.SequenceItem
 * @see com.pixelmed.dicom.Attribute
 * @see com.pixelmed.dicom.AttributeFactory
 * @see com.pixelmed.dicom.AttributeList
 *
 * @author	dclunie
 */
public class SequenceAttribute extends Attribute {

	private LinkedList itemList;		// each member is a SequenceItem

	/**
	 * <p>Construct an (empty) attribute.</p>
	 *
	 * @param	t	the tag of the attribute
	 */
	public SequenceAttribute(AttributeTag t) {
		super(t);
		itemList=new LinkedList();
		valueLength=0xffffffffl;	// for the benefit of writebase();
	}

	// no constructor for input stream ... done manually elsewhere
	
	/***/
	public String toString(DicomDictionary dictionary) {
		StringBuffer str = new StringBuffer();
		str.append(super.toString(dictionary));
		str.append("\n%seq\n");
		Iterator i = iterator();
		while (i.hasNext()) {
			str.append(((SequenceItem)i.next()).toString(dictionary));
			str.append("\n");
		}
		str.append("%endseq");
		return str.toString();
	}

	/**
	 * Add an item to the sequence (after any existing items).
	 *
	 * @param	item	the list of attributes that comprise the item
	 */
	public void addItem(AttributeList item) {
		itemList.addLast(new SequenceItem(item));
	}

	/**
	 * Add an item to the sequence (after any existing items), keeping tracking of input byte offsets.
	 *
	 * @param	item		the list of attribuites that comprise the item
	 * @param	byteOffset	the byte offset in the input stream of the start of the item
	 */
	public void addItem(AttributeList item,long byteOffset) {
		itemList.addLast(new SequenceItem(item,byteOffset));
	}

	/**
	 * Get an {@link java.util.Iterator Iterator} of the items in the sequence.
	 *
	 * @return	a {@link java.util.Iterator Iterator} of items, each encoded as an {@link com.pixelmed.dicom.SequenceItem SequenceItem}
	 */
	public Iterator iterator() {
		return itemList.listIterator(0);
	}

	/**
	 * Get the number of items in the sequence.
	 *
	 * @return	the number of items
	 */
	public int getNumberOfItems() {
		return itemList.size();
	}

	/**
	 * Get particular item in the sequence.
	 *
	 * @param	index	which item to return, numbered from zero
	 * @return		a {@link com.pixelmed.dicom.SequenceItem SequenceItem}, null if no items or no such item
	 */
	public SequenceItem getItem(int index) {
		return (itemList == null || index >= itemList.size()) ? null : (SequenceItem)itemList.get(index);
	}

	/**
	 * <p>Get the value representation of this attribute (SQ).</p>
	 *
	 * @return	'S','Q' in ASCII as a two byte array; see {@link com.pixelmed.dicom.ValueRepresentation ValueRepresentation}
	 */
	public byte[] getVR() { return ValueRepresentation.SQ; }

	/**
	 * <p>From the specified sequence which has one item, and from within that extract the specified attribute.</p>
	 *
	 * @param	sequenceAttribute	the sequence attribute that has one item (may be null in which case returns null)
	 * @param	namedTag		the tag of the attribute within the item of the sequence
	 * @return				the attribute if found else null
	 */
	public static Attribute getNamedAttributeFromWithinSequenceWithSingleItem(SequenceAttribute sequenceAttribute,AttributeTag namedTag) {
		Attribute a = null;
		if (sequenceAttribute != null) {
			// assert sequenceAttribute.getNumberOfItems() == 1
			Iterator sitems = sequenceAttribute.iterator();
			if (sitems.hasNext()) {
				SequenceItem sitem = (SequenceItem)sitems.next();
				AttributeList slist = sitem.getAttributeList();
				if (slist != null) {
					a=slist.get(namedTag);
				}
			}
		}
		return a;
	}

	/**
	 * <p>From within a list of attributes, get the specified sequence which has one item, and from within that extract the specified attribute.</p>
	 *
	 * @param	list		the list that contains the sequence (may not be null)
	 * @param	sequenceTag	the tag of the sequence attribute that has one item
	 * @param	namedTag	the tag of the attribute within the item of the sequence
	 * @return			the attribute if found else null
	 */
	public static Attribute getNamedAttributeFromWithinSequenceWithSingleItem(AttributeList list,AttributeTag sequenceTag,AttributeTag namedTag) {
		SequenceAttribute sequenceAttribute = (SequenceAttribute)list.get(sequenceTag);
		return getNamedAttributeFromWithinSequenceWithSingleItem(sequenceAttribute,namedTag);
	}
	
	/**
	 * @param	list
	 * @param	tag
	 * @param	dflt
	 */
	public static String getMeaningOfCodedSequenceAttributeOrDefault(AttributeList list,AttributeTag tag,String dflt) {
		String meaning=dflt;
		Attribute a=getNamedAttributeFromWithinSequenceWithSingleItem(list,tag,TagFromName.CodeMeaning);
		if (a != null) meaning = a.getSingleStringValueOrDefault(dflt);
		return meaning;
	}
	

}

