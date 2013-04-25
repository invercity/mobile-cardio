package ua.stu.scplib.structure;

import java.io.IOException;

import android.R.bool;

import ua.stu.scplib.attribute.BinaryInputStream;

/**
 * <p>
 * A class to encapsulate the SCP-ECG Encoded Reference Beat Data and Residual
 * or Rhythm Data sections.
 * </p>
 * 
 * @author stu
 */
public class Section5Or6 extends Section {

	/**
	 * <p>
	 * Get a string name for this section.
	 * </p>
	 * 
	 * @return a string name for this section
	 */
	public String getSectionName() {
		return header.getSectionIDNumber() == 5 ? "Encoded Reference Beat Data"
				: "Encoded Residual or Rhythm Data";
	}

	private boolean flNonSection2 = false;
	private int amplitudeValueMultiplier; // nanoVolts (10^^-9)
	private int sampleTimeInterval; // microSeconds
	private int differenceDataUsed; // 0=no,1=1st difference,2=2nd difference
	private int bimodalCompressionUsed; // 0=no,1=yes ... only used for section
										// 6, reserved byte for section 5

	private int numberOfLeads; // copied from section 3

	private int[] byteLengthsOfEncodedLeads;

	private long totalBytesinCompressedLeadData;

	private byte[][] compressedLeadData;
	private short[][] LeadData;

	public int getAmplitudeValueMultiplier() {
		return amplitudeValueMultiplier;
	}

	public int getSampleTimeInterval() {
		return sampleTimeInterval;
	}

	public int getDifferenceDataUsed() {
		return differenceDataUsed;
	}

	public int getBimodalCompressionUsed() {
		return bimodalCompressionUsed;
	}

	public int getNumberOfLeads() {
		return numberOfLeads;
	}

	public int[] getByteLengthsOfEncodedLeads() {
		return byteLengthsOfEncodedLeads;
	}

	public long getTotalBytesinCompressedLeadData() {
		return totalBytesinCompressedLeadData;
	}

	public byte[][] getCompressedLeadData() {
		return compressedLeadData;
	}

	public short[][] getLeadData() {
		return LeadData;
	}

	public void setFlNonSection2(boolean flNonSection2) {
		this.flNonSection2 = flNonSection2;
	}

	public Section5Or6(SectionHeader header, int numberOfLeads,boolean fl) {
		super(header);
		this.numberOfLeads = numberOfLeads;
		this.flNonSection2=fl;
	}

	public long read(BinaryInputStream i) throws IOException {
		amplitudeValueMultiplier = i.readUnsigned16();
		bytesRead += 2;
		sectionBytesRemaining -= 2;
		sampleTimeInterval = i.readUnsigned16();
		bytesRead += 2;
		sectionBytesRemaining -= 2;
		differenceDataUsed = i.readUnsigned8();
		bytesRead++;
		sectionBytesRemaining--;
		bimodalCompressionUsed = i.readUnsigned8();
		bytesRead++;
		sectionBytesRemaining--;

		byteLengthsOfEncodedLeads = new int[numberOfLeads];
		compressedLeadData = new byte[numberOfLeads][];
		LeadData=new short[numberOfLeads][];
		int lead = 0;
		totalBytesinCompressedLeadData = 0;
		while (sectionBytesRemaining > 0 && lead < numberOfLeads) {
			byteLengthsOfEncodedLeads[lead] = i.readUnsigned16();
			bytesRead += 2;
			sectionBytesRemaining -= 2;
			totalBytesinCompressedLeadData += byteLengthsOfEncodedLeads[lead];
			++lead;
		}
		if (lead != numberOfLeads) {
			System.err
					.println("Section 6 Expected byte lengths of encoded leads for "
							+ numberOfLeads + " but only got " + lead);
		}
		if (totalBytesinCompressedLeadData != sectionBytesRemaining) {
			System.err
					.println("Section 6 Expected total byte lengths of compressed leads of "
							+ totalBytesinCompressedLeadData
							+ " but got "
							+ sectionBytesRemaining);
		}

		lead = 0;
		while (sectionBytesRemaining > 0 && lead < numberOfLeads) {
			int bytesToDecompress = byteLengthsOfEncodedLeads[lead];
			compressedLeadData[lead] = new byte[bytesToDecompress];
			LeadData[lead]=new short[bytesToDecompress/2];
			if (flNonSection2) {
							
				i.readInsistently(compressedLeadData[lead], 0,
						bytesToDecompress);
			} else {
				
				//String res = "";
				for (int k = 0; k < bytesToDecompress / 2; k++) {
					LeadData[lead][k]=(short)i.readSigned16();
				//	res += LeadData[lead][k]* 2 + " ";
				}
				//System.out.println("lead=" + lead + " point=" + res);
			}
			sectionBytesRemaining -= bytesToDecompress;
			bytesRead += bytesToDecompress;
			++lead;
		}
		skipToEndOfSectionIfNotAlreadyThere(i);
		return bytesRead;
	}

	public String toString() {
		StringBuffer strbuf = new StringBuffer();
		strbuf.append("Amplitude Value Multiplier in nanoVolts = "
				+ amplitudeValueMultiplier + " dec (0x"
				+ Integer.toHexString(amplitudeValueMultiplier) + ")\n");
		strbuf.append("Sample Time Interval in microSeconds = "
				+ sampleTimeInterval + " dec (0x"
				+ Integer.toHexString(sampleTimeInterval) + ")\n");
		strbuf.append("Difference Data Used = "
				+ differenceDataUsed
				+ " ("
				+ (differenceDataUsed == 0 ? "No"
						: (differenceDataUsed == 1 ? "First" : "Second"))
				+ ")\n");
		if (header.getSectionIDNumber() == 6) {
			strbuf.append("Bimodal Compression Used = "
					+ bimodalCompressionUsed + " ("
					+ (bimodalCompressionUsed == 0 ? "No" : "Yes") + ")\n");
		}
		strbuf.append("Byte lengths of encoded (compressed) leads:\n");
		for (int lead = 0; lead < numberOfLeads; ++lead) {
			strbuf.append("\tLead " + lead + ":\n");
			strbuf.append("\t\tbytes = " + byteLengthsOfEncodedLeads[lead]
					+ " dec (0x"
					+ Integer.toHexString(byteLengthsOfEncodedLeads[lead])
					+ ")\n");
		}
		return strbuf.toString();
	}

	public String validate() {
		return "";
	}
}
