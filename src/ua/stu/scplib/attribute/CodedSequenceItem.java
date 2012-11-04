package ua.stu.scplib.attribute;

import java.util.*;

/**
 * <p>A class to encapsulate the attributes contaianed within a Sequence Item that represents
 * a Coded Sequence item.</p>
 *
 * @author	dclunie
 */
public class CodedSequenceItem {

	private AttributeList list;

	/**
	 * <p>Construct a <code>CodedSequenceItem</code> from a list of attributes.</p>
	 *
	 * @param	l	the list of attributes to include in the item
	 */
	public CodedSequenceItem(AttributeList l) {
		list=l;
	}

	/**
	 * <p>Get the list of attributes in the <code>CodedSequenceItem</code>.</p>
	 *
	 * @return	all the attributes in the <code>CodedSequenceItem</code>
	 */
	public AttributeList getAttributeList() { return list; }

	/**
	 * <p>Get the code value.</p>
	 *
	 * @return	a string containing the code value, or an empty string if none
	 */
	public String getCodeValue() {
		return Attribute.getSingleStringValueOrEmptyString(list,TagFromName.CodeValue);
	}

	/**
	 * <p>Get the coding scheme designator.</p>
	 *
	 * @return	a string containing the coding scheme designator, or an empty string if none
	 */
	public String getCodingSchemeDesignator() {
		return Attribute.getSingleStringValueOrEmptyString(list,TagFromName.CodingSchemeDesignator);
	}

	/**
	 * <p>Get the code meaning.</p>
	 *
	 * @return	a string containing the code meaning, or an empty string if none
	 */
	public String getCodeMeaning() {
		return Attribute.getSingleStringValueOrEmptyString(list,TagFromName.CodeMeaning);
	}

	/**
	 * <p>Get a {@link java.lang.String String} representation of the contents of the <code>CodedSequenceItem</code>.</p>
	 *
	 * @return	a string containing the code value, coding scheme designator and code meaning values
	 */
	public String toString() {
		StringBuffer str = new StringBuffer();
		str.append("(");
		str.append(getCodeValue());
		str.append(",");
		str.append(getCodingSchemeDesignator());
		str.append(",\"");
		str.append(getCodeMeaning());
		str.append("\")");
		return str.toString();
	}

	// Some static convenience methods ...

	/**
	 * <p>Extract the first (hopefully only) item of a coded sequence attribute contained
	 * within a list of attributes.</p>
	 *
	 * @param	list	the list in which to look for the Sequence attribute
	 * @param	tag	the tag of the Sequence attribute to extract
	 * @return		the (first) coded sequence item if found, otherwise null
	 */
	public static CodedSequenceItem getSingleCodedSequenceItemOrNull(AttributeList list,AttributeTag tag) {
		CodedSequenceItem value = null;
		if (list != null) {
			value = getSingleCodedSequenceItemOrNull(list.get(tag));
		}
		return value;
	}

	/**
	 * <p>Extract the first (hopefully only) item of a coded sequence attribute.</p>
	 *
	 * @param	a	the attribute
	 * @return		the (first) coded sequence item if found, otherwise null
	 */
	public static CodedSequenceItem getSingleCodedSequenceItemOrNull(Attribute a) {
		CodedSequenceItem value = null;
		if (a != null && a instanceof SequenceAttribute) {
			SequenceAttribute sa = (SequenceAttribute)(a);
			Iterator i = sa.iterator();
			if (i.hasNext()) {
				SequenceItem item = ((SequenceItem)i.next());
				if (item != null) value=new CodedSequenceItem(item.getAttributeList());
			}
		}
		return value;
	}
}

