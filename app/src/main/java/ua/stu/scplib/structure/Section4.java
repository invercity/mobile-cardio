package ua.stu.scplib.structure;

import java.io.IOException;

import ua.stu.scplib.attribute.BinaryInputStream;

/**
 * <p>A class to encapsulate the SCP-ECG QRS Locations section.</p>
 *
 * @author	stu
 */
public class Section4 extends Section {

	/**
	 * <p>Get a string name for this section.</p>
	 *
	 * @return		a string name for this section
	 */
	public String getSectionName() { return "QRS Locations"; }

	private int lengthOfReferenceBeat0DataInMilliSeconds;
	private int sampleNumberOfQRSOfFiducial;
	private int totalNumberOfQRSComplexes;
	private int[]  beatType;
	private long[] sampleNumberOfResidualToStartSubtractingQRS;
	private long[] sampleNumberOfResidualOfFiducial;
	private long[] sampleNumberOfResidualToEndSubtractingQRS;
	private long[] sampleNumberOfResidualToStartProtectedArea;
	private long[] sampleNumberOfResidualToEndProtectedArea;
	
	public int getLengthOfReferenceBeat0DataInMilliSeconds() { return lengthOfReferenceBeat0DataInMilliSeconds; }
	public int getSampleNumberOfQRSOfFiducial() { return sampleNumberOfQRSOfFiducial; }
	public int getTotalNumberOfQRSComplexes() { return totalNumberOfQRSComplexes; }
	public int[] getBeatType() { return beatType; }
	public long[] getSampleNumberOfResidualToStartSubtractingQRS() { return sampleNumberOfResidualToStartSubtractingQRS; }
	public long[] getSampleNumberOfResidualOfFiducial() { return sampleNumberOfResidualOfFiducial; }
	public long[] getSampleNumberOfResidualToEndSubtractingQRS() { return sampleNumberOfResidualToEndSubtractingQRS; }
	public long[] getSampleNumberOfResidualToStartProtectedArea() { return sampleNumberOfResidualToStartProtectedArea; }
	public long[] getSampleNumberOfResidualToEndProtectedArea() { return sampleNumberOfResidualToEndProtectedArea; }
		
	public Section4(SectionHeader header) {
		super(header);
	}
		
	public long read(BinaryInputStream i) throws IOException {
		lengthOfReferenceBeat0DataInMilliSeconds=i.readUnsigned16();
		bytesRead+=2;
		sectionBytesRemaining-=2;
				//		System.out.println(lengthOfReferenceBeat0DataInMilliSeconds);
		sampleNumberOfQRSOfFiducial=i.readUnsigned16();
		bytesRead+=2;
		sectionBytesRemaining-=2;
						
		totalNumberOfQRSComplexes=i.readUnsigned16();
		bytesRead+=2;
		sectionBytesRemaining-=2;
		
		                                   beatType = new  int[totalNumberOfQRSComplexes];
		sampleNumberOfResidualToStartSubtractingQRS = new long[totalNumberOfQRSComplexes];
		           sampleNumberOfResidualOfFiducial = new long[totalNumberOfQRSComplexes];
		  sampleNumberOfResidualToEndSubtractingQRS = new long[totalNumberOfQRSComplexes];

		int qrsComplex=0;
		while (sectionBytesRemaining > 0 && qrsComplex < totalNumberOfQRSComplexes) {
			beatType[qrsComplex] = i.readUnsigned16();
			bytesRead+=2;
			sectionBytesRemaining-=2;
			sampleNumberOfResidualToStartSubtractingQRS[qrsComplex] = i.readUnsigned32();
			bytesRead+=4;
			sectionBytesRemaining-=4;
			sampleNumberOfResidualOfFiducial[qrsComplex] = i.readUnsigned32();
			bytesRead+=4;
			sectionBytesRemaining-=4;
			sampleNumberOfResidualToEndSubtractingQRS[qrsComplex] = i.readUnsigned32();
			bytesRead+=4;
			sectionBytesRemaining-=4;
			++qrsComplex;
		}
		if (qrsComplex != totalNumberOfQRSComplexes) {
			System.err.println("Section 4 Number Of QRS Complexes specified as "+totalNumberOfQRSComplexes
				+" but encountered "+qrsComplex+" reference beat subtraction zones");
		}
		
		sampleNumberOfResidualToStartProtectedArea = new long[totalNumberOfQRSComplexes];
		  sampleNumberOfResidualToEndProtectedArea = new long[totalNumberOfQRSComplexes];

		qrsComplex=0;
		while (sectionBytesRemaining > 0 && qrsComplex < totalNumberOfQRSComplexes) {
			sampleNumberOfResidualToStartProtectedArea[qrsComplex] = i.readUnsigned32();
			bytesRead+=4;
			sectionBytesRemaining-=4;
			sampleNumberOfResidualToEndProtectedArea[qrsComplex] = i.readUnsigned32();
			bytesRead+=4;
			sectionBytesRemaining-=4;
			++qrsComplex;
		}
		if (qrsComplex != totalNumberOfQRSComplexes) {
			System.err.println("Section 4 Number Of QRS Complexes specified as "+totalNumberOfQRSComplexes
				+" but encountered "+qrsComplex+" protected areas");
		}
		skipToEndOfSectionIfNotAlreadyThere(i);
		return bytesRead;
	}
		
	public String toString() {
		StringBuffer strbuf = new StringBuffer();
		strbuf.append("Length of Reference Beat 0 Data In MilliSeconds = "+lengthOfReferenceBeat0DataInMilliSeconds
			+" dec (0x"+Integer.toHexString(lengthOfReferenceBeat0DataInMilliSeconds)+")\n");
		strbuf.append("Sample Number of QRS of Fiducial = "+sampleNumberOfQRSOfFiducial+" dec (0x"+Integer.toHexString(sampleNumberOfQRSOfFiducial)+")\n");
		strbuf.append("Total Number Of QRS Complexes = "+totalNumberOfQRSComplexes+" dec (0x"+Integer.toHexString(totalNumberOfQRSComplexes)+")\n");
		strbuf.append("Reference beat subtraction zones:\n");
		for (int qrsComplex=0; qrsComplex<totalNumberOfQRSComplexes; ++qrsComplex) {
			strbuf.append("\tQRS Complex "+qrsComplex+":\n");
			strbuf.append("\t\tBeat Type "+beatType[qrsComplex]+" dec (0x"+Integer.toHexString(beatType[qrsComplex])+")\n");
			strbuf.append("\t\tSample Number of Residual to Start Subtracting QRS "+
				sampleNumberOfResidualToStartSubtractingQRS[qrsComplex]+" dec (0x"+Long.toHexString(sampleNumberOfResidualToStartSubtractingQRS[qrsComplex])+")\n");
			strbuf.append("\t\tSample Number of Residual of Fiducial "+
				sampleNumberOfResidualOfFiducial[qrsComplex]+" dec (0x"+Long.toHexString(sampleNumberOfResidualOfFiducial[qrsComplex])+")\n");
			strbuf.append("\t\tSample Number of Residual to End Subtracting QRS "+
				sampleNumberOfResidualToEndSubtractingQRS[qrsComplex]+" dec (0x"+Long.toHexString(sampleNumberOfResidualToEndSubtractingQRS[qrsComplex])+")\n");
		}
		strbuf.append("Protected areas:\n");
		for (int qrsComplex=0; qrsComplex<totalNumberOfQRSComplexes; ++qrsComplex) {
			strbuf.append("\tQRS Complex "+qrsComplex+":\n");
			strbuf.append("\t\tSample Number of Residual to Start Protected Area "+
				sampleNumberOfResidualToStartProtectedArea[qrsComplex]+" dec (0x"+Long.toHexString(sampleNumberOfResidualToStartProtectedArea[qrsComplex])+")\n");
			strbuf.append("\t\tSample Number of Residual to End Protected Area "+
				sampleNumberOfResidualToEndProtectedArea[qrsComplex]+" dec (0x"+Long.toHexString(sampleNumberOfResidualToEndProtectedArea[qrsComplex])+")\n");
		}
		return strbuf.toString();
	}
		
	public String validate() {
		return "";
	}
}

