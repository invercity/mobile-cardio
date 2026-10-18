package ua.stu.scplib.structure;

import java.io.IOException;

import ua.stu.scplib.attribute.BinaryInputStream;

/**
 * <p>A class to encapsulate the header portion of an SCP-ECG section.</p>
 *
 * @author	stu
 */
public class SectionHeader {

	private int sectionCRC;
	private int sectionIDNumber;
	private long sectionLength;
	private int sectionVersionNumber;
	private int protocolVersionNumber;
	private byte[] reserved = new byte[6];
		
	private long bytesRead;
	private long byteOffset;
	
	public int getSectionCRC() { return sectionCRC; }
	public int getSectionIDNumber() { return sectionIDNumber; }
	public long getSectionLength() { return sectionLength; }
	public int getSectionVersionNumber() { return sectionVersionNumber; }
	public int getProtocolVersionNumber() { return protocolVersionNumber; }
	public byte[] getReservedBytes() { return reserved; }
	public long getBytesRead() { return bytesRead; }
	public long getByteOffset() { return byteOffset; }
		
	/**
	 * <p>Read the section header from a stream.</p>
	 *
	 * @param	i		the input stream
	 * @param	byteOffset	byte offset
	 * @return			the number of bytes read
	 */
	public long read(BinaryInputStream i,long byteOffset) throws IOException {
		this.byteOffset=byteOffset;
		bytesRead=0;
		sectionCRC = i.readUnsigned16();
		bytesRead+=2;		
		sectionIDNumber = i.readUnsigned16();
		bytesRead+=2;		
		sectionLength = i.readUnsigned32();
		bytesRead+=4;		
		sectionVersionNumber = i.readUnsigned8();
		bytesRead++;		
		protocolVersionNumber = i.readUnsigned8();
		bytesRead++;
		i.readInsistently(reserved,0,6);
		bytesRead+=6;
			
		return bytesRead;
	}
		
	/**
	 * <p>Dump the header as a <code>String</code>.</p>
	 *
	 * @return		the header as a <code>String</code>
	 */
	public String toString() {
		return "[Byte offset = "+byteOffset+" dec (0x"+Long.toHexString(byteOffset)+")]\n"
		     + "Section CRC = "+sectionCRC+" dec (0x"+Integer.toHexString(sectionCRC)+")\n"
		     + "Section ID Number = "+sectionIDNumber+" dec (0x"+Integer.toHexString(sectionIDNumber)+")\n"
		     + "Section Length = "+sectionLength+" dec (0x"+Long.toHexString(sectionLength)+")\n"
		     + "Section Version Number = "+sectionVersionNumber+" dec (0x"+Integer.toHexString(sectionVersionNumber)+")\n"
		     + "Protocol Version Number = "+protocolVersionNumber+" dec (0x"+Integer.toHexString(protocolVersionNumber)+")\n";
	}
}
	
