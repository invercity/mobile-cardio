package ua.stu.scplib.attribute;

import java.io.*;

/**
 * <p>A class that extends {@link java.io.FilterInputStream FilterInputStream} by adding
 * the concept of little and big endian binary value encoding, and supplies functions
 * for reading various sized integer and floating point words.</p>
 *
 * @see com.pixelmed.dicom.BinaryOutputStream
 *
 * @author	dclunie
 */
public class BinaryInputStream extends FilterInputStream {

	/***/
	boolean bigEndian;
	/***/
	byte buffer[];
	/**/
	File file;

	/**
	 * @param	big
	 */
	void localInit(boolean big) {
		bigEndian=big;
		buffer=new byte[8];
	}

	/**
	 * <p>Construct a byte ordered stream from the supplied file.</p>
	 *
	 * <p>The byte order may be changed later.</p>
	 *
	 * @param	file			the file to read from
	 * @param	big	true if big endian, false if little endian
	 */
	public BinaryInputStream(File file,boolean big) throws FileNotFoundException {
		super(new BufferedInputStream(new FileInputStream(file)));
		this.file=file;
		localInit(big);
	}
	
	/**
	 * <p>Construct a byte ordered stream from the supplied stream.</p>
	 *
	 * <p>The byte order may be changed later.</p>
	 *
	 * @param	i	the input stream to read from
	 * @param	big	true if big endian, false if little endian
	 */
	public BinaryInputStream(InputStream i,boolean big) {
		super(i);
		this.file=null;
		localInit(big);
	}

	/**
	 * <p>Get the file associated with this stream.</p>
	 *
	 * @return	file	the file, or null if not a file input stream
	 */
	public File getFile() { return file; }

	/**
	 * <p>Is the stream byte order big endian ?</p>
	 *
	 * @return	true if big endian, false if little endian
	 */
	public boolean isBigEndian() { return bigEndian; }

	/**
	 * <p>Is the stream byte order little endian ?</p>
	 *
	 * @return	true if little endian, false if big endian
	 */
	public boolean isLittleEndian() { return !bigEndian; }

	/**
	 * <p>Set the stream byte order to big endian.</p>
	 */
	public void setBigEndian() { bigEndian=true; }

	/**
	 * <p>Set the stream byte order to little endian.</p>
	 */
	public void setLittleEndian() { bigEndian=false; }

	/**
	 * <p>Set the stream byte order to that specified.</p>
	 *
	 * @param	big	true if to set to big endian, false if little endian
	 */
	public void setEndian(boolean big) {
		bigEndian=big;
	}

	/***/
	final int extractUnsigned8() {
			return ((int)buffer[0])&0xff;
	}

	/***/
	final int extractUnsigned16() {
			int v1 =  ((int)buffer[0])&0xff;
			int v2 =  ((int)buffer[1])&0xff;
			return bigEndian
				? (v1 << 8) | v2
				: (v2 << 8) | v1;
	}

	/***/
	final short extractSigned16() {
			short v1 =  (short)(buffer[0]&0xff);
			short v2 =  (short)(buffer[1]&0xff);
			return (short) (bigEndian
				? (v1 << 8) | v2
				: (v2 << 8) | v1);
	}

	/***/
	final long extractUnsigned32() {
			long v1 =  ((long)buffer[0])&0xff;
			long v2 =  ((long)buffer[1])&0xff;
			long v3 =  ((long)buffer[2])&0xff;
			long v4 =  ((long)buffer[3])&0xff;
			return bigEndian
				? (((((v1 << 8) | v2) << 8) | v3) << 8) | v4
				: (((((v4 << 8) | v3) << 8) | v2) << 8) | v1;
	}

	/***/
	final int extractSigned32() {
			int v1 =  ((int)buffer[0])&0xff;
			int v2 =  ((int)buffer[1])&0xff;
			int v3 =  ((int)buffer[2])&0xff;
			int v4 =  ((int)buffer[3])&0xff;
			return bigEndian
				? (((((v1 << 8) | v2) << 8) | v3) << 8) | v4
				: (((((v4 << 8) | v3) << 8) | v2) << 8) | v1;
	}

	/***/
	final long extractUnsigned64() {
			long v1 =  ((long)buffer[0])&0xff;
			long v2 =  ((long)buffer[1])&0xff;
			long v3 =  ((long)buffer[2])&0xff;
			long v4 =  ((long)buffer[3])&0xff;
			long v5 =  ((long)buffer[4])&0xff;
			long v6 =  ((long)buffer[5])&0xff;
			long v7 =  ((long)buffer[6])&0xff;
			long v8 =  ((long)buffer[7])&0xff;
			return bigEndian
				? (((((((((((((v1 << 8) | v2) << 8) | v3) << 8) | v4) << 8) | v5) << 8) | v6) << 8) | v7) << 8) | v8
				: (((((((((((((v8 << 8) | v7) << 8) | v6) << 8) | v5) << 8) | v4) << 8) | v3) << 8) | v2) << 8) | v1;
	}

	/**
	 * <p>Read as many bytes as requested, unless an exception occurs.</p>
	 *
	 * @param	b		buffer to read into
	 * @param	offset		offset (from 0) in buffer to read into
	 * @param	length		number of bytes to read (no more and no less)
	 * @exception	IOException
	 */
	public void readInsistently(byte[] b,int offset,int length) throws IOException {
		int remaining = length;
		while (remaining > 0) {
//System.err.println("readInsistently(): looping offset="+offset+" remaining="+remaining);
			int bytesReceived = in.read(b,offset,remaining);
//System.err.println("readInsistently(): asked for ="+remaining+" received="+bytesReceived);
			if (bytesReceived == -1) throw new IOException("read failed with "+remaining+" bytes remaining to be read, wanted "+length);
			remaining-=bytesReceived;
			offset+=bytesReceived;
		}
	}

	/**
	 * <p>Skip as many bytes as requested, unless an exception occurs.</p>
	 *
	 * @param	length		number of bytes to read (no more and no less)
	 * @exception	IOException
	 */
	public void skipInsistently(long length) throws IOException {
		long remaining = length;
		while (remaining > 0) {
//System.err.println("skipInsistently(): looping remaining="+remaining);
			long bytesSkipped = in.skip(remaining);
//System.err.println("skipInsistently(): asked for ="+remaining+" got="+bytesSkipped);
			if (bytesSkipped <= 0) throw new IOException("skip failed with "+remaining+" bytes remaining to be skipped, wanted "+length);
			remaining-=bytesSkipped;
		}
	}

	/**
	 * <p>Read one unsigned integer 8 bit value.</p>
	 *
	 * @return			an int containing an unsigned value
	 * @exception	IOException
	 */
	public final int readUnsigned8() throws IOException {
		readInsistently(buffer,0,1);
		return extractUnsigned8();
	}

	/**
	 * <p>Read one unsigned integer 16 bit value.</p>
	 *
	 * @return			an int containing an unsigned value
	 * @exception	IOException
	 */
	public final int readUnsigned16() throws IOException {
		readInsistently(buffer,0,2);
		return extractUnsigned16();

	}

	/**
	 * <p>Read one signed integer 16 bit value.</p>
	 *
	 * @return			an int containing an unsigned value
	 * @exception	IOException
	 */
	public final int readSigned16() throws IOException {
		readInsistently(buffer,0,2);
		return extractSigned16();

	}

	/**
	 * <p>Read one unsigned integer 32 bit value.</p>
	 *
	 * @return			a long containing an unsigned value
	 * @exception	IOException
	 */
	public final long readUnsigned32() throws IOException {
		readInsistently(buffer,0,4);
		return extractUnsigned32();
	}

	/**
	 * <p>Read one signed integer 32 bit value.</p>
	 *
	 * @return			an int containing an signed value
	 * @exception	IOException
	 */
	public final int readSigned32() throws IOException {
		readInsistently(buffer,0,4);
		return extractSigned32();
	}

	/**
	 * <p>Read one floating point 32 bit value.</p>
	 *
	 * @return			a float value
	 * @exception	IOException
	 */
	public final float readFloat() throws IOException {
		readInsistently(buffer,0,4);
		int binary = (int)(extractUnsigned32());
		return Float.intBitsToFloat(binary);
	}

	/**
	 * <p>Read one floating point 64 bit value.</p>
	 *
	 * @return			a double value
	 * @exception	IOException
	 */
	public final double readDouble() throws IOException {
		readInsistently(buffer,0,8);
		long binary = extractUnsigned64();
		return Double.longBitsToDouble(binary);
	}

	/**
	 * <p>Read an array of unsigned integer 16 bit values.</p>
	 *
	 * @param	w		an array of sufficient size in which to return the values read
	 * @param	len		the number of 16 bit values to read
	 * @exception	IOException
	 */
	public final void readUnsigned16(short[] w,int len) throws IOException {
		readUnsigned16(w,0,len);
	}
	
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
		readInsistently(b,0,blen);
		int bcount=0;
		int wcount=0;
		//long starttime=new Date().getTime();
		//System.err.println("readUnsigned16: ready to convert at: 0");
		if (bigEndian) {
			while (wcount<len) {
				int highByte=((int)b[bcount++])&0xff;
				int  lowByte=((int)b[bcount++])&0xff;
				short value=(short)((highByte<<8) + lowByte);
				w[offset+wcount++]=(short)value;
			}
		}
		else {
			//while (wcount<len) {
			for (;wcount<len;++wcount) {
				//int  lowByte=((int)b[bcount++])&0xff;
				//int highByte=((int)b[bcount++])&0xff;
				//short value=(short)((highByte<<8) + lowByte);
				//w[wcount++]=(short)value;
				//w[wcount++]=(short)((b[bcount+1]<<8) + (b[bcount]&0xff));
				//w[wcount++]=(short)((b[bcount++]&0xff) + (b[bcount++]<<8));	// assumes left to right evaluation
				w[offset+wcount]=(short)((b[bcount++]&0xff) + (b[bcount++]<<8));	// assumes left to right evaluation
				//bcount++; bcount++;
				//bcount+=2;
			}
		}
		//System.err.println("readUnsigned16: exit at: "+(new Date().getTime()-starttime));
	}
	
	/**
	 * <p>Read an array of floating point 32 bit values.</p>
	 *
	 * @param	f		an array of sufficient size in which to return the values read
	 * @param	len		the number of 32 bit values to read
	 * @exception	IOException
	 */
	public final void readFloat(float[] f,int len) throws IOException {
		for (int i=0; i<len; ++i) f[i]=readFloat();
	}

	/**
	 * <p>Read interleaved complex floating point 32 bit value pairs into real and imaginary arrays.</p>
	 *
	 * @param	freal		an array of sufficient size in which to return the real values read, may be null if don't want real values
	 * @param	fimaginary	an array of sufficient size in which to return the real values read, may be null if don't want imaginary values
	 * @param	len		the number of 32 bit values to read
	 * @exception	IOException
	 */
	public final void readComplexFloat(float[] freal,float[] fimaginary,int len) throws IOException {
		for (int i=0; i<len; ++i) {
			float vreal=readFloat();
			float vimaginary=readFloat();
			if (freal != null) freal[i]=vreal;
			if (fimaginary != null) fimaginary[i]=vimaginary;
		}
	}

	/**
	 * <p>Read an array of floating point 64 bit values.</p>
	 *
	 * @param	f		an array of sufficient size in which to return the values read
	 * @param	len		the number of 64 bit values to read
	 * @exception	IOException
	 */
	public final void readDouble(double[] f,int len) throws IOException {
		for (int i=0; i<len; ++i) f[i]=readDouble();
	}

	/**
	 * <p>Read interleaved complex floating point 64 bit value pairs into real and imaginary arrays.</p>
	 *
	 * @param	freal		an array of sufficient size in which to return the real values read, may be null if don't want real values
	 * @param	fimaginary	an array of sufficient size in which to return the real values read, may be null if don't want imaginary values
	 * @param	len		the number of 64 bit values to read
	 * @exception	IOException
	 */
	public final void readComplexDouble(double[] freal,double[] fimaginary,int len) throws IOException {
		for (int i=0; i<len; ++i) {
			double vreal=readDouble();
			double vimaginary=readDouble();
			if (freal != null) freal[i]=vreal;
			if (fimaginary != null) fimaginary[i]=vimaginary;
		}
	}

	/**
	 * @param	bval
	 */
	final void setBufferForTest(byte[] bval) {
		buffer=bval;
	}
	
	/**
	 * <p>For testing.</p>
	 *
	 * @param	arg
	 */
	public static void main(String arg[]) {

		// little endian ...

		BinaryInputStream i=null;
		try {
			i = new BinaryInputStream(new FileInputStream(arg[0]),false);
		} catch (Exception e) {
			System.err.println(e);
			System.exit(0);
		}

		byte testBuffer[] = new byte[4];

		i.setBufferForTest(testBuffer);

		testBuffer[0]=(byte)0xff;
		testBuffer[1]=(byte)0x00;
		testBuffer[2]=(byte)0x00;
		testBuffer[3]=(byte)0x00;

		System.err.println("Extracted 0x"+Integer.toHexString(i.extractUnsigned8()));
		System.err.println("Extracted 0x"+Integer.toHexString(i.extractUnsigned16()));
		System.err.println("Extracted 0x"+   Long.toHexString(i.extractUnsigned32()));

		testBuffer[1]=(byte)0xff;

		System.err.println("Extracted 0x"+Integer.toHexString(i.extractUnsigned8()));
		System.err.println("Extracted 0x"+Integer.toHexString(i.extractUnsigned16()));
		System.err.println("Extracted 0x"+   Long.toHexString(i.extractUnsigned32()));

		testBuffer[2]=(byte)0xff;

		System.err.println("Extracted 0x"+Integer.toHexString(i.extractUnsigned8()));
		System.err.println("Extracted 0x"+Integer.toHexString(i.extractUnsigned16()));
		System.err.println("Extracted 0x"+   Long.toHexString(i.extractUnsigned32()));

		testBuffer[3]=(byte)0xff;

		System.err.println("Extracted 0x"+Integer.toHexString(i.extractUnsigned8()));
		System.err.println("Extracted 0x"+Integer.toHexString(i.extractUnsigned16()));
		System.err.println("Extracted 0x"+   Long.toHexString(i.extractUnsigned32()));

		testBuffer[0]=(byte)0x7f;
		testBuffer[1]=(byte)0x00;
		testBuffer[2]=(byte)0x00;
		testBuffer[3]=(byte)0x00;

		System.err.println("Extracted 0x"+Integer.toHexString(i.extractUnsigned8()));
		System.err.println("Extracted 0x"+Integer.toHexString(i.extractUnsigned16()));
		System.err.println("Extracted 0x"+   Long.toHexString(i.extractUnsigned32()));

		testBuffer[1]=(byte)0x7f;

		System.err.println("Extracted 0x"+Integer.toHexString(i.extractUnsigned8()));
		System.err.println("Extracted 0x"+Integer.toHexString(i.extractUnsigned16()));
		System.err.println("Extracted 0x"+   Long.toHexString(i.extractUnsigned32()));

		testBuffer[2]=(byte)0x7f;

		System.err.println("Extracted 0x"+Integer.toHexString(i.extractUnsigned8()));
		System.err.println("Extracted 0x"+Integer.toHexString(i.extractUnsigned16()));
		System.err.println("Extracted 0x"+   Long.toHexString(i.extractUnsigned32()));

		testBuffer[3]=(byte)0x7f;

		System.err.println("Extracted 0x"+Integer.toHexString(i.extractUnsigned8()));
		System.err.println("Extracted 0x"+Integer.toHexString(i.extractUnsigned16()));
		System.err.println("Extracted 0x"+   Long.toHexString(i.extractUnsigned32()));

		// big endian ...

		try {
			i = new BinaryInputStream(new FileInputStream(arg[0]),true);
		} catch (Exception e) {
			System.err.println(e);
			System.exit(0);
		}

		i.setBufferForTest(testBuffer);

		testBuffer[3]=(byte)0xff;
		testBuffer[2]=(byte)0x00;
		testBuffer[1]=(byte)0x00;
		testBuffer[0]=(byte)0x00;

		System.err.println("Extracted 0x"+Integer.toHexString(i.extractUnsigned8()));
		System.err.println("Extracted 0x"+Integer.toHexString(i.extractUnsigned16()));
		System.err.println("Extracted 0x"+   Long.toHexString(i.extractUnsigned32()));

		testBuffer[2]=(byte)0xff;

		System.err.println("Extracted 0x"+Integer.toHexString(i.extractUnsigned8()));
		System.err.println("Extracted 0x"+Integer.toHexString(i.extractUnsigned16()));
		System.err.println("Extracted 0x"+   Long.toHexString(i.extractUnsigned32()));

		testBuffer[1]=(byte)0xff;

		System.err.println("Extracted 0x"+Integer.toHexString(i.extractUnsigned8()));
		System.err.println("Extracted 0x"+Integer.toHexString(i.extractUnsigned16()));
		System.err.println("Extracted 0x"+   Long.toHexString(i.extractUnsigned32()));

		testBuffer[0]=(byte)0xff;

		System.err.println("Extracted 0x"+Integer.toHexString(i.extractUnsigned8()));
		System.err.println("Extracted 0x"+Integer.toHexString(i.extractUnsigned16()));
		System.err.println("Extracted 0x"+   Long.toHexString(i.extractUnsigned32()));

		testBuffer[3]=(byte)0x7f;
		testBuffer[2]=(byte)0x00;
		testBuffer[1]=(byte)0x00;
		testBuffer[0]=(byte)0x00;

		System.err.println("Extracted 0x"+Integer.toHexString(i.extractUnsigned8()));
		System.err.println("Extracted 0x"+Integer.toHexString(i.extractUnsigned16()));
		System.err.println("Extracted 0x"+   Long.toHexString(i.extractUnsigned32()));

		testBuffer[2]=(byte)0x7f;

		System.err.println("Extracted 0x"+Integer.toHexString(i.extractUnsigned8()));
		System.err.println("Extracted 0x"+Integer.toHexString(i.extractUnsigned16()));
		System.err.println("Extracted 0x"+   Long.toHexString(i.extractUnsigned32()));

		testBuffer[1]=(byte)0x7f;

		System.err.println("Extracted 0x"+Integer.toHexString(i.extractUnsigned8()));
		System.err.println("Extracted 0x"+Integer.toHexString(i.extractUnsigned16()));
		System.err.println("Extracted 0x"+   Long.toHexString(i.extractUnsigned32()));

		testBuffer[0]=(byte)0x7f;

		System.err.println("Extracted 0x"+Integer.toHexString(i.extractUnsigned8()));
		System.err.println("Extracted 0x"+Integer.toHexString(i.extractUnsigned16()));
		System.err.println("Extracted 0x"+   Long.toHexString(i.extractUnsigned32()));
	}
}




