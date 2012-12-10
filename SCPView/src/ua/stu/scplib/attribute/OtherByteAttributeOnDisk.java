package ua.stu.scplib.attribute;

import java.io.*;


/**
 * <p>A concrete class specializing {@link com.pixelmed.dicom.Attribute Attribute} for
 * Other Byte (OB) attributes whose values are not memory resident.</p>
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
public class OtherByteAttributeOnDisk extends Attribute {

	protected long byteOffset;
	protected File file;

	/**
	 * <p>Construct an (empty) attribute.</p>
	 *
	 * @param	t	the tag of the attribute
	 */
	public OtherByteAttributeOnDisk(AttributeTag t) {
		super(t);
		byteOffset = 0;
		file = null;
	}

	/**
	 * <p>Read an attribute from an input stream.</p>
	 *
	 * @param	t			the tag of the attribute
	 * @param	vl			the value length of the attribute
	 * @param	i			the input stream
	 * @param	byteOffset	the byte offset in the input stream of the start of the data
	 * @exception	IOException
	 * @exception	DicomException
	 */
	public OtherByteAttributeOnDisk(AttributeTag t,long vl,DicomInputStream i,long byteOffset) throws IOException, DicomException {
		super(t);
		doCommonConstructorStuff(vl,i,byteOffset);
	}

	/**
	 * <p>Read an attribute from an input stream.</p>
	 *
	 * @param	t			the tag of the attribute
	 * @param	vl			the value length of the attribute
	 * @param	i			the input stream
	 * @param	byteOffset	the byte offset in the input stream of the start of the data
	 * @exception	IOException
	 * @exception	DicomException
	 */
	public OtherByteAttributeOnDisk(AttributeTag t,Long vl,DicomInputStream i,Long byteOffset) throws IOException, DicomException {
		super(t);
		doCommonConstructorStuff(vl.longValue(),i,byteOffset.longValue());
	}

	/**
	 * @param	vl			the value length of the attribute
	 * @param	i			the input stream
	 * @param	byteOffset	the byte offset in the input stream of the start of the data
	 * @exception	IOException
	 * @exception	DicomException
	 */
	private void doCommonConstructorStuff(long vl,DicomInputStream i,long byteOffset) throws IOException, DicomException {
		valueLength=vl;
		this.byteOffset=byteOffset;
		file=i.getFile();
		if (file == null) {
				throw new DicomException("Cannot have an OtherByteAttributeOnDisk without a file available in the DicomInputStream");
		}

		if (vl > 0) {
			try {
				i.skipInsistently(vl);
			}
			catch (IOException e) {
				throw new DicomException("Failed to skip value (length "+vl+" dec) in "+ValueRepresentation.getAsString(getVR())+" attribute "+getTag());
			}
		}
	}

	/***/
	public long getPaddedVL() {
		long vl = getVL();
		if (vl%2 != 0) ++vl;
		return vl;
	}
	
	/***/
	public String toString(DicomDictionary dictionary) {
		StringBuffer str = new StringBuffer();
		str.append(super.toString(dictionary));
		str.append(" []");		// i.e. don't really dump values ... too many
		return str.toString();
	}

	/**
	 * @return		the offset from the start of the file in bytes
	 */
	public long getByteOffset() { return byteOffset; }

	/**
	 * @return		the file containing the data
	 */
	public File getFile() { return file; }

	/**
	 * <p>Change the file containing the data, for example if it has been renamed.</p>
	 *
	 * @param	file	the new file containing the data
	 */
	public void setFile(File file) { this.file = file; }

	/**
	 * <p>Get the value representation of this attribute (OB).</p>
	 *
	 * @return	'O','B' in ASCII as a two byte array; see {@link com.pixelmed.dicom.ValueRepresentation ValueRepresentation}
	 */
	public byte[] getVR() { return ValueRepresentation.OB; }
}

