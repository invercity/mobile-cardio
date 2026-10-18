package ua.stu.scplib.attribute;

import java.io.*;
// for test timing of routines

/**
 * <p>A class that extends {@link java.io.InputStream InputStream} by adding
 * a mechanism for unecapsulating an undefined length DICOM attribute, such as is used for
 * compressed Pixel Data.</p>
 *
 * <p>The read methods hide the fact that the data is encapsulated by removing the Items and
 * Item and Sequence delimiter tags, as well as skipping any Basic Offset Table
 * that may be present in the first Item.</p>
 *
 * <p>Since an individual frame may be fragmented and padded woth 0xff bytes beyond the
 * JPEG EOI marker (0xffd9), and since the codec used for decoding may be "reading ahead"
 * this class also removes any padding 0xff bytes at the end of any fragment, back as
 * far as the EOI marker. Note that this means that theorectically frames could span
 * fragments as long as there was no padding between them.</p>
 *
 * @author	dclunie
 */
public class EncapsulatedInputStream extends InputStream {

	/***/
	private BinaryInputStream i;
	/***/
	private boolean bigEndian;
	/***/
	private byte buffer[];			// just for read() one byte method
	/***/
	private boolean firstTime;
	/***/
	private byte fragment[];
	/***/
	private int fragmentSize;
	/***/
	private int fragmentOffset;
	/***/
	private int fragmentRemaining;
	/***/
	private boolean sequenceDelimiterEncountered;
	/***/
	private boolean endOfFrameEncountered;
	/***/
	private boolean currentFragmentContainsEndOfFrame;
	
	/**
	 * @param	i
	 * @exception	IOException
	 */
	private AttributeTag readAttributeTag() throws IOException {
		int group   = i.readUnsigned16();
		int element = i.readUnsigned16();
		return new AttributeTag(group,element);
	}

	private long readItemTag() throws IOException {
		AttributeTag tag = readAttributeTag();
//System.err.println("EncapsulatedInputStream.readItemTag: tag="+tag);
		long vl = i.readUnsigned32();		// always implicit VR form for items and delimiters
		if (tag.equals(TagFromName.SequenceDelimitationItem)) {
//System.err.println("EncapsulatedInputStream.readItemTag: SequenceDelimitationItem");
			vl=0;	// regardless of what was read
			sequenceDelimiterEncountered=true;
		}
		else if (!tag.equals(TagFromName.Item)) {
			throw new IOException("Unexpected DICOM tag "+tag+" (vl="+vl+") in encapsulated data whilst expecting Item or SequenceDelimitationItem");
		}
//System.err.println("EncapsulatedInputStream.readItemTag: length="+vl);
		return vl;
	}
	
//	private void readItemDelimiter() throws IOException {
//		AttributeTag tag = readAttributeTag();
//System.err.println("EncapsulatedInputStream.readItemDelimiter: tag="+tag);
//		i.readUnsigned32();		// always implicit VR form for items and delimiters
//		if (!tag.equals(TagFromName.ItemDelimitationItem)) {
//			throw new IOException("Expected DICOM Item Delimitation Item tag in encapsulated data");
//		}
//	}
	
//	private void readSequenceDelimiter() throws IOException {
//		AttributeTag tag = readAttributeTag();
//System.err.println("EncapsulatedInputStream.readSequenceDelimiter: tag="+tag);
//		i.readUnsigned32();		// always implicit VR form for items and delimiters
//		if (!tag.equals(TagFromName.SequenceDelimitationItem)) {
//			throw new IOException("Expected DICOM Sequence Delimitation Item tag in encapsulated data");
//		}
//	}

	/**
	 * <p>Construct a byte ordered stream from the supplied stream.</p>
	 *
	 * <p>The byte order may be changed later.</p>
	 *
	 * @param	i	the input stream to read from
	 */
	public EncapsulatedInputStream(BinaryInputStream i) {
		this.i=i;
		bigEndian=i.isBigEndian();
		buffer=new byte[8];
		fragment=null;
		firstTime=true;
		sequenceDelimiterEncountered=false;
		endOfFrameEncountered=false;
	}
	
	/**
	 * <p>Skip to the start of a fragment, if not already there.</p> 
	 */
	public void nextFrame() {
//System.err.println("EncapsulatedInputStream.nextFrame()");
		// flush to start of next fragment unless already positioned at start of next fragment
		if (fragment != null && fragmentOffset != 0) {
//System.err.println("EncapsulatedInputStream.nextFrame(): fragment was not already null");
			fragment=null;
		}
		endOfFrameEncountered=false;
	}

	// Our own specific methods a la BinaryInputStream ...
	
	/**
	 * <p>Read an array of unsigned integer 16 bit values.</p>
	 *
	 * @param	w		an array of sufficient size in which to return the values read
	 * @param	offset		the offset in the array at which to begin storing values
	 * @param	len		the number of 16 bit values to read
	 * @exception	IOException
	 */
	public final void readUnsigned16(short[] w,int offset,int len) throws IOException {
		int blen = len*2;
		byte  b[] = new byte[blen];
		read(b,0,blen);				// read the bytes from the fragment(s)
		int bcount=0;
		int wcount=0;
		if (bigEndian) {
			for (;wcount<len;++wcount) {
				w[offset+wcount]=(short)((b[bcount++]<<8) + (b[bcount++]&0xff));	// assumes left to right evaluation
			}
		}
		else {
			for (;wcount<len;++wcount) {
				w[offset+wcount]=(short)((b[bcount++]&0xff) + (b[bcount++]<<8));	// assumes left to right evaluation
			}
		}
	}
	
	// Override the necessary methods from InputStream ...

	/**
	 * <p>Extracts the next byte of data from the current or
	 * subsequent fragments.</p>
	 *
	 * @return     the next byte of data, or -1 if there is no more data because the end of the stream has been reached.
	 * @exception  IOException  if an I/O error occurs.
	 */
	public final int read() throws IOException {
		int count = read(buffer,0,1);
		return count == -1 ? -1 : (buffer[0]&0xff);		// do not sign extend
	}

	/**
	 * <p>Extracts <code>byte.length</code> bytes of data from the current or
	 * subsequent fragments.</p> 
	 * <p>This method simply performs the call <code>read(b, 0, b.length)</code> and returns
	 * the  result.</p>
	 *
	 * @param      b   the buffer into which the data is read.
	 * @return     	   the total number of bytes read into the buffer (always whatever was asked for), or -1 if there is no more data because the end of the stream has been reached.
	 * @exception  IOException  if an I/O error occurs.
	 * @see        #read(byte[], int, int)
	 */
	public final int read(byte b[]) throws IOException {
		return read(b, 0, b.length);
	}

	/**
	 * <p>Extracts <code>len</code> bytes of data from the current or
	 * subsequent fragments.</p> 
	 *
	 * @param      b     the buffer into which the data is read.
	 * @param      off   the start offset of the data.
	 * @param      len   the number of bytes read.
	 * @return     	     the total number of bytes read into the buffer (always whatever was asked for), or -1 if there is no more data because the end of a frame has been reached.
	 * @exception  IOException  if an I/O error occurs.
	 */
	public final int read(byte b[], int off, int len) throws IOException {
//System.err.println("EncapsulatedInputStream.read(byte [],"+off+","+len+")");
//System.err.println("EncapsulatedInputStream.read() at start, fragmentRemaining="+fragmentRemaining);
//System.err.println("EncapsulatedInputStream.read() at start, endOfFrameEncountered="+endOfFrameEncountered);
//System.err.println("EncapsulatedInputStream.read() at start, currentFragmentContainsEndOfFrame="+currentFragmentContainsEndOfFrame);
		if (endOfFrameEncountered) {
//System.err.println("EncapsulatedInputStream.read() returning -1 since endOfFrameEncountered");
			return -1;						// i.e., won't advance until nextFrame() is called to reset this state
		}
		int count=0;
		int remainingToDo = len;
		while (remainingToDo > 0 && !sequenceDelimiterEncountered && !endOfFrameEncountered) {
//System.err.println("EncapsulatedInputStream.read() remainingToDo="+remainingToDo);
			if (fragment == null) {
				if (firstTime) {
//System.err.println("EncapsulatedInputStream.read() firstTime");
					// first time ... skip offset table ...
					long offsetTableLength = readItemTag();
					if (sequenceDelimiterEncountered) {
						throw new IOException("Expected offset table item tag; got sequence delimiter");
					}
//System.err.println("EncapsulatedInputStream.read() skipping offsetTableLength="+offsetTableLength);
					i.skipInsistently(offsetTableLength);
					firstTime=false;
				}
				// load a new fragment ...
//System.err.println("EncapsulatedInputStream.read() loading a new fragment");
				long vl = readItemTag();	// if sequenceDelimiterEncountered, vl will be zero and no more will be done
				if (vl != 0) {
					currentFragmentContainsEndOfFrame=false;
					fragmentRemaining = fragmentSize = (int)vl;
					fragment = new byte[fragmentSize];
					i.readInsistently(fragment,0,fragmentSize);
					fragmentOffset=0;
//System.err.println("EncapsulatedInputStream.read() fragmentRemaining initially="+fragmentRemaining);
//System.err.println("EncapsulatedInputStream.read() fragment = "+com.pixelmed.utils.HexDump.dump(fragment));
					// Ignore everything between (the last) EOI marker and the end of the fragment
					int positionOfEOI = fragmentRemaining-1;
					while (--positionOfEOI > 0) {
						int firstMarkerByte  = fragment[positionOfEOI  ]&0xff;
						int secondMarkerByte = fragment[positionOfEOI +1]&0xff;
//System.err.println("EncapsulatedInputStream.read() fragment fragment["+positionOfEOI+"] = 0x"+Integer.toHexString(firstMarkerByte));
//System.err.println("EncapsulatedInputStream.read() fragment fragment["+(positionOfEOI+1)+"] = 0x"+Integer.toHexString(secondMarkerByte));
						if (firstMarkerByte == 0xff && secondMarkerByte == 0xd9) {
							currentFragmentContainsEndOfFrame=true;
							break;
						}
					}
//System.err.println("EncapsulatedInputStream.read() positionOfEOI="+positionOfEOI);
					if (positionOfEOI > 0) {			// will be zero if we did not find one
						fragmentRemaining = positionOfEOI+2;	// effectively skips all (hopefully padding) bytes after the EOI
					}
//System.err.println("EncapsulatedInputStream.read() fragmentRemaining after removing trailing padding="+fragmentRemaining);
				}
			}
			int amountToCopyFromThisFragment = remainingToDo < fragmentRemaining ? remainingToDo : fragmentRemaining;
//System.err.println("EncapsulatedInputStream.read() amountToCopyFromThisFragment="+amountToCopyFromThisFragment);
			if (amountToCopyFromThisFragment > 0) {
				System.arraycopy(fragment,fragmentOffset,b,off,amountToCopyFromThisFragment);
				off+=amountToCopyFromThisFragment;
				fragmentOffset+=amountToCopyFromThisFragment;
				fragmentRemaining-=amountToCopyFromThisFragment;
				remainingToDo-=amountToCopyFromThisFragment;
				count+=amountToCopyFromThisFragment;
			}
			if (fragmentRemaining <= 0) {
				fragment=null;
				if (currentFragmentContainsEndOfFrame) {
					endOfFrameEncountered = true;			// once EOI has been seen in a fragment, use the rest of the fragment including the EOI, but no further
				}
			}
		}
//System.err.println("EncapsulatedInputStream.read() returning count="+count);
//System.err.println("EncapsulatedInputStream.read() returning="+com.pixelmed.utils.HexDump.dump(fragment,off,count));
		return count == 0 ? -1 : count;		// always returns more than 0 unless end, which is signalled by -1
	}
}




