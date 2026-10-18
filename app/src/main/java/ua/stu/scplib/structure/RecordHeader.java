package ua.stu.scplib.structure;

import java.io.IOException;

import ua.stu.scplib.attribute.BinaryInputStream;


/**
 * <p>A class to encapsulate an SCP-ECG record header.</p>
 *
 * @author	stu
 */
public class RecordHeader {
//	private static final String identString = "@(#) $Header: /userland/cvs/pixelmed/imgbook/com/pixelmed/scpecg/RecordHeader.java,v 1.3 2004/01/25 03:47:20 dclunie Exp $";

	private int crc;
	private long recordLength;
	
	public int getCRC() { return crc; }
	public long getRecordLength() { return recordLength; }
		
	/**
	 * <p>Read a header from a stream.</p>
	 *
	 * @param	i	the input stream
	 */
	public long read(BinaryInputStream i) throws IOException {
		long bytesRead=0;
		crc = i.readUnsigned16();
		bytesRead+=2;		
		recordLength = i.readUnsigned32();
		bytesRead+=4;		
		return bytesRead;
	}

	/**
	 * <p>Dump the record header as a <code>String</code>.</p>
	 *
	 * @return		the header as a <code>String</code>
	 */
	public String toString() {
		return "CRC = "+crc+" dec (0x"+Integer.toHexString(crc)+")\n"
		     + "Record Length = "+recordLength+" dec (0x"+Long.toHexString(recordLength)+")\n";
	}
}

