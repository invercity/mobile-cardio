package ua.stu.scplib.attribute;

import java.util.*;
import java.io.*;
import javax.imageio.*;
import javax.imageio.stream.ImageInputStream;
import javax.imageio.spi.*;
import javax.imageio.event.IIOReadProgressListener;
import java.awt.image.*; 

import java.util.zip.*;

/**
 * <p>AttributeList class maintains a list of individual DICOM attributes.</p>
 *
 * <p>Instances of the class may be used for entire composite storage SOP instances, or fragments of such instances
 * such as meta information headers, or simply as lists of attributes to be passed to other
 * methods (e.g. lists of attributes to add or remove from another list).</p>
 *
 * <p>The class is actually implemented by extending {@link java.util.TreeMap java.util.TreeMap}
 * as a map of {@link com.pixelmed.dicom.AttributeTag AttributeTag} keys to
 * {@link com.pixelmed.dicom.Attribute Attribute} values. Consequently, all the methods
 * of the underlying collection are available, including adding key-value pairs and
 * extracting values by key. Iteration through the list of key-value pairs in
 * the map is also supported, and the iterator returns values in the ascending numerical
 * order of the {@link com.pixelmed.dicom.AttributeTag AttributeTag} keys, since
 * that is how {@link com.pixelmed.dicom.AttributeTag AttributeTag} implements
 * {@link java.lang.Comparable Comparable}.</p>
 *
 * <p>Note that large attribute values such as Pixel Data may be left on disk rather
 * than actually read in when the list is created, and loaded on demand; extreme
 * caution should be taken if the underlying file from which an AttributeList has
 * been read is moved or renamed; a specific method, {@link #setFileUsedByOnDiskAttributes(File file) setFileUsedByOnDiskAttributes()},
 * is provided to address this concern.</p>
 *
 * <p>The class provides methods for reading entire objects as a list of attributes,
 * from files or streams. For example, the following fragment will read an entire
 * object from the specified file and dump the contents of the attribute list:</p>
 *
 * <pre>
 * 	AttributeList list = new AttributeList();
 * 	list.read(arg[0],null,true,true);
 * 	System.err.print(list);
 * </pre>
 *
 * <p>Similarly, methods are provided for writing entire objects. For example, the
 * previous fragment could be extended to write the list to a file unchanged as follows:</p>
 *
 * <pre>
 *	list.write(arg[1],TransferSyntax.ExplicitVRLittleEndian,true,true);
 * </pre>
 *
 *<p>Note that in general, one would want to perform significantly more cleaning
 * up before writing an object that has just been read, and a number of such
 * methods are provided either in this class or on related classes
 * as illustrated in this example:</p>
 *
 * <pre>
 * 	AttributeList list = new AttributeList();
 * 	list.read(arg[0],null,true,true);
 *	//list.removePrivateAttributes();
 *	list.removeGroupLengthAttributes();
 *	list.removeMetaInformationHeaderAttributes();
 *  list.remove(TagFromName.DataSetTrailingPadding);
 *	FileMetaInformation.addFileMetaInformation(list,TransferSyntax.ExplicitVRLittleEndian,"OURAETITLE");
 *	list.write(arg[1],TransferSyntax.ExplicitVRLittleEndian,true,true);
 * </pre>
 *
 * <p>Note that this example is essentially the functionality of the {@link #main(String[]) main()} method
 * of this class, which may be used as a copying utility when invoked with input and output file arguments.</p>
 *
 * <p>Individual attributes can be added or deleted as desired, either using a newly created
 * list or one which has been read in from an existing object. For example, to zero out the
 * patient's name one might do something like the following:</p>
 *
 * <pre>
 *	list.replaceWithZeroLengthIfPresent(TagFromName.PatientName);
 * </pre>
 *
 * <p> or to replace it with a particular value one might do the following:</p>
 * <pre>
 *	Attribute a = new PersonNameAttribute(TagFromName.PatientName);
 *	a.addValue(value);
 *	list.put(TagFromName.PatientName,a);		// one could list.remove(TagFromName.PatientName) first, but this is implicit in the put
 * </pre>
 *
 * <p>A more compact shorthand method for adding new (or replacing existing) attributes (if they are in the dictionary so that the VR can be determined) is also supplied:</p>
 *
 * <pre>
 *	list.putNewAttribute(TagFromName.PatientName);
 * </pre>
 *
 * <p>and if a specific character set other than the default is in use:</p>
 *
 * <pre>
 *	list.putNewAttribute(TagFromName.PatientName,specificCharacterSet);
 * </pre>
 *
 * <p>and since this method returns the generated attribute, values can easily be added as:</p>
 *
 * <pre>
 *	list.putNewAttribute(TagFromName.PatientName,specificCharacterSet).addValue("Blo^Joe");
 * </pre>
 *
 *
 * <p>Note also that the {@link com.pixelmed.dicom.Attribute Attribute} class provides some useful
 * static methods for extracting and manipulating individual attributes within a list. For example:</p>
 *
 * <pre>
 *	String patientName=Attribute.getSingleStringValueOrNull(list,TagFromName.PatientName);
 * </pre>
 *
 * <p>Ideally one should take care when adding or manipulating lists of attributes to handle
 * the specific character set correctly and consistently when there is a possibility that it
 * may be other than the default. The previous example of replacing the patient's name
 * could be more properly rewritten as:</p>
 *
 * <pre>
 *	SpecificCharacterSet specificCharacterSet = new SpecificCharacterSet(Attribute.getStringValues(list,TagFromName.SpecificCharacterSet));
 *	Attribute a = new PersonNameAttribute(TagFromName.PatientName,specificCharacterSet);
 *	a.addValue(value);
 *	list.put(TagFromName.PatientName,a);
 * </pre>
 *
 * <p>Note that in this example if the SpecificCharacterSet attribute were not found or was present but empty
 * the various intervening methods would return null and the
 * {@link com.pixelmed.dicom.SpecificCharacterSet#SpecificCharacterSet(String[]) SpecificCharacterSet()}
 * constructor would use the default (ascii) character set.</p>
 *
 * <p>When an attribute list is read in, the SpecificCharacterSet attribute is automatically detected
 * and set and applied to all string attributes as they are read in and converted to the internal
 * string form which is used by Java (Unicode). The same applies when they are written, with some
 * limitations on which character sets are supported.</p>
 *
 * @see com.pixelmed.dicom.Attribute
 * @see com.pixelmed.dicom.AttributeTag
 * @see com.pixelmed.dicom.FileMetaInformation
 * @see com.pixelmed.dicom.SpecificCharacterSet
 * @see com.pixelmed.dicom.TagFromName
 * @see com.pixelmed.dicom.TransferSyntax
 *
 * @author	dclunie
 */
public class AttributeList extends TreeMap {

	/***/
	private static final String identString = "@(#) $Header: /userland/cvs/pixelmed/imgbook/com/pixelmed/dicom/AttributeList.java,v 1.73 2007/08/01 11:41:24 dclunie Exp $";

	//private final long maximumSaneFixedValueLength = 1000000000l; // 1GB seems large enough
	
	private static boolean haveScannedForCodecs;

	/***/
	private static DicomDictionary dictionary;
	
	/***/
	private void createDictionaryifNecessary() {
		if (dictionary == null) {
//System.err.println("AttributeList.createDictionaryifNecessary(): creating static dictionary");
			dictionary = new DicomDictionary();
		}
	}
	
	private void dumpListOfAllAvailableReaders(PrintStream out) {
		String[] formats=ImageIO.getReaderFormatNames();
		for (int i=0; formats != null && i<formats.length; ++i) {
			out.println(formats[i]+":");
			Iterator readers = ImageIO.getImageReadersByFormatName(formats[i]);
			while (readers.hasNext()) {
				ImageReader reader = (ImageReader)readers.next();
				ImageReaderSpi spi = reader.getOriginatingProvider();
				out.println("\t"+spi.getDescription(Locale.US)+" "+spi.getVendorName()+" "+spi.getVersion());
			}
		}
	}


	private class OurIIOReadProgressListener implements IIOReadProgressListener {
		public void imageComplete(ImageReader source) {
//System.out.println("OurIIOReadProgressListener:imageComplete()");
		}
		public void imageProgress(ImageReader source,float percentageDone) {
//System.out.println("OurIIOReadProgressListener:imageProgress(): percentageDone="+percentageDone);
		}
		public void imageStarted(ImageReader source,int imageIndex) {
//System.out.println("OurIIOReadProgressListener:imageStarted(): imageIndex="+imageIndex);
		}
		public void readAborted(ImageReader source) {
//System.out.println("OurIIOReadProgressListener:readAborted()");
		}
		public void sequenceComplete(ImageReader source) {
//System.out.println("OurIIOReadProgressListener:sequenceComplete()");
		}
		public void sequenceStarted(ImageReader source,int minIndex) {
//System.out.println("OurIIOReadProgressListener:sequenceStarted(): minIndex="+minIndex);
		}
		public void thumbnailComplete(ImageReader source) {
//System.out.println("OurIIOReadProgressListener:thumbnailComplete()");
		}
		public void thumbnailProgress(ImageReader source,float percentageDone) {
//System.out.println("OurIIOReadProgressListener:thumbnailProgress(): percentageDone="+percentageDone);
		}
		public void thumbnailStarted(ImageReader source,int imageIndex,int thumbnailIndex) {
//System.out.println("OurIIOReadProgressListener:thumbnailStarted(): imageIndex="+imageIndex+" thumbnailIndex="+thumbnailIndex);
		}
	}

	/**
	 * @param	i
	 * @exception	IOException
	 */
	private AttributeTag readAttributeTag(DicomInputStream i) throws IOException {
		int group   = i.readUnsigned16();
		int element = i.readUnsigned16();
		return new AttributeTag(group,element);
	}

	/**
	 * @param	a
	 * @param	i
	 * @param	byteOffset
	 * @param	lengthToRead
	 * @param	specificCharacterSet
	 * @exception	IOException
	 * @exception	DicomException
	 */
	private long readNewSequenceAttribute(Attribute a,DicomInputStream i,long byteOffset,long lengthToRead,SpecificCharacterSet specificCharacterSet) throws IOException, DicomException {
		boolean undefinedLength = lengthToRead == 0xffffffffl;
		long endByteOffset=(undefinedLength) ? 0xffffffffl : byteOffset+lengthToRead-1;

//System.err.println("readNewSequenceAttribute: start byteOffset="+byteOffset+" lengthToRead="+lengthToRead+" endByteOffset="+endByteOffset);
		try {
			// CBZip2InputStream.available() always returns zero, and since we terminate
			// on exceptions anyway, just forget about it
			while (/*i.available() > 0 && */(undefinedLength || byteOffset < endByteOffset)) {
//System.err.println("readNewSequenceAttribute: loop byteOffset="+byteOffset);
				long itemStartOffset=byteOffset;
				AttributeTag tag = readAttributeTag(i);
				byteOffset+=4;
//System.err.println("readNewSequenceAttribute: tag="+tag);
				long vl = i.readUnsigned32();		// always implicit VR form for items and delimiters
				byteOffset+=4;
//System.err.println(byteOffset+" "+tag+" VL=<0x"+Long.toHexString(vl)+">");
				if (tag.equals(TagFromName.SequenceDelimitationItem)) {
//System.err.println("readNewSequenceAttribute: SequenceDelimitationItem");
					break;
				}
				else if (tag.equals(TagFromName.Item)) {
//System.err.println("readNewSequenceAttribute: Item byteOffset="+byteOffset);
					AttributeList list = new AttributeList();
					byteOffset=list.read(i,byteOffset,vl,false,specificCharacterSet);
//System.err.println("readNewSequenceAttribute: back from reading Item byteOffset="+byteOffset);
					((SequenceAttribute)a).addItem(list,itemStartOffset);
				}
				else {
					throw new DicomException("Bad tag "+tag+"(not Item or Sequence Delimiter) in Sequence at byte offset "+byteOffset);
				}
			}
		}
		catch (EOFException e) {
//System.err.println("Closing on "+e);
			if (!undefinedLength) throw new EOFException();
		}
		catch (IOException e) {
//System.err.println("Closing on "+e);
			if (!undefinedLength) throw new IOException();		// InflaterInputStream seems to throw IOException rather than EOFException
		}
//System.err.println("readNewSequenceAttribute: return byteOffset="+byteOffset);
		return byteOffset;
	}


	/**
	 * @param	i
	 * @param	byteOffset
	 * @param	lengthToRead
	 * @param	stopAfterMetaInformationHeader
	 * @param	specificCharacterSet
	 * @exception	IOException
	 * @exception	DicomException
	 */
	private long read(DicomInputStream i,long byteOffset,long lengthToRead,boolean stopAfterMetaInformationHeader,SpecificCharacterSet specificCharacterSet) throws IOException, DicomException {
		return read(i,byteOffset,lengthToRead,stopAfterMetaInformationHeader,specificCharacterSet,null);
	}

	/**
	 * @param	i
	 * @param	byteOffset
	 * @param	lengthToRead
	 * @param	stopAfterMetaInformationHeader
	 * @param	specificCharacterSet
	 * @param	stopAtTag					the tag (in the top level data set) at which to stop
	 * @exception	IOException
	 * @exception	DicomException
	 */
	private long read(DicomInputStream i,long byteOffset,long lengthToRead,boolean stopAfterMetaInformationHeader,
			SpecificCharacterSet specificCharacterSet,AttributeTag stopAtTag) throws IOException, DicomException {
//System.err.println("read: Stop tag is "+stopAtTag);
		if (i.areReadingDataSet()) {
			// Test to see whether or not a codec needs to be pushed on the stream ... after the first time, the TransferSyntax will always be ExplicitVRLittleEndian 
//System.err.println("Testing for deflate and bzip2 TS");
			if (i.getTransferSyntaxToReadDataSet().isDeflated()) {
				// insert deflate into input stream and make a new DicomInputStream
//System.err.println("Creating new DicomInputStream from deflate");
				i = new DicomInputStream(new InflaterInputStream(i,new Inflater(true)),TransferSyntax.ExplicitVRLittleEndian,false);
				byteOffset=0;
			}
			else if (i.getTransferSyntaxToReadDataSet().isBzip2ed()) {
				// insert bzip2 into input stream and make a new DicomInputStream
//System.err.println("Creating new DicomInputStream from bzip2");
				try {
					Class classToUse = Thread.currentThread().getContextClassLoader().loadClass("org.apache.excalibur.bzip2.CBZip2InputStream");
					Class [] argTypes  = {InputStream.class};
					Object[] argValues = {i};
					InputStream bzipInputStream = (InputStream)(classToUse.getConstructor(argTypes).newInstance(argValues));
					i = new DicomInputStream(bzipInputStream,TransferSyntax.ExplicitVRLittleEndian,false);
					byteOffset=0;
				}
				catch (java.lang.reflect.InvocationTargetException e) {
					throw new DicomException("Not a correctly encoded bzip2 bitstream - "+e);
				}
				catch (Exception e) {	// may be ClassNotFoundException,NoSuchMethodException,InstantiationException
					throw new DicomException("Could not instantiate bzip2 codec - "+e);
				}
			}
		}
		
		createDictionaryifNecessary();
		
		boolean undefinedLength = lengthToRead == 0xffffffffl;
		long endByteOffset=(undefinedLength) ? 0xffffffffl : byteOffset+lengthToRead-1;

//System.err.println("read: start byteOffset="+byteOffset+" endByteOffset="+endByteOffset+" lengthToRead="+lengthToRead);
		byte vrBuffer[] = new byte[2];
		boolean explicit = i.getTransferSyntaxInUse().isExplicitVR();
		
		// keep track of pixel data size in case need VL for encapsulated data ...
		int rows = 0;
		int columns = 0;
		int frames = 1;
		int samplesPerPixel = 1;
		int bytesPerSample = 0;

		AttributeTag tag = null;
		try {
			// CBZip2InputStream.available() always returns zero, and since we terminate
			// on exceptions anyway, just forget about it
			while (/*i.available() > 0 && */(undefinedLength || byteOffset < endByteOffset)) {
//System.err.println("read: i.available()="+i.available());
//System.err.println("read: loop byteOffset="+byteOffset+" endByteOffset="+endByteOffset);
				tag = readAttributeTag(i);
				byteOffset+=4;
//System.err.println("read: tag="+tag);

				if (stopAtTag != null && tag.equals(stopAtTag)) {
//System.err.println("read: stopped at "+tag);
					return byteOffset;	// stop now, since we have reached the tag at which we were told to stop
				}
				
				if (tag.equals(TagFromName.ItemDelimitationItem)) {
//System.err.println("read: ItemDelimitationItem");
					// Read and discard value length
					i.readUnsigned32();
					byteOffset+=4;
					return byteOffset;	// stop now, since we must have been called to read an item's dataset
				}
				
				if (tag.equals(TagFromName.Item)) {
					// this is bad ... there shouldn't be Items here since they should
					// only be found during readNewSequenceAttribute()
					// however, try to work around Philips bug ...
					long vl = i.readUnsigned32();		// always implicit VR form for items and delimiters
					byteOffset+=4;
System.err.println("Ignoring bad Item at "+byteOffset+" "+tag+" VL=<0x"+Long.toHexString(vl)+">");
					// let's just ignore it for now
					continue;
				}

				byte vr[];
				if (explicit) {
					vr=vrBuffer;
					i.readInsistently(vr,0,2);
					byteOffset+=2;
				}
				else {
					vr = dictionary.getValueRepresentationFromTag(tag);
					if (vr == null)  {
						vr=vrBuffer;
						vr[0]='U';
						vr[1]='N';
					}
				}
	
				long vl;
				if (explicit) {
					if (ValueRepresentation.isShortValueLengthVR(vr)) {
						vl=i.readUnsigned16();
						byteOffset+=2;
					}
					else {
						i.readUnsigned16();	// reserved bytes
						vl=i.readUnsigned32();
						byteOffset+=6;
					}
				}
				else {
					vl=i.readUnsigned32();
					byteOffset+=4;
				}

				if (explicit) {
					// do not do this until AFTER the value length has been read, since explicit UN uses the long form of length
					if (ValueRepresentation.isUnknownVR(vr)) {
						byte vrd[] = dictionary.getValueRepresentationFromTag(tag);
						if (vrd != null && vrd.length >= 2) {
//System.err.println("AttributeList.read(): For tag "+tag+" consider overriding explicit VR "+ValueRepresentation.getAsString(vr)+" with "+ValueRepresentation.getAsString(vrd));
							if (!ValueRepresentation.isSequenceVR(vrd)) {
//System.err.println("AttributeList.read(): For tag "+tag+" overriding explicit VR "+ValueRepresentation.getAsString(vr)+" with "+ValueRepresentation.getAsString(vrd));
								vr[0] = vrd[0];
								vr[1] = vrd[1];
							}
						}
					}
				}
//System.err.println(byteOffset+" "+tag+" VR=<"+ValueRepresentation.getAsString(vr)+"> VL=<0x"+Long.toHexString(vl)+">");

				Attribute a = null;

				if (ValueRepresentation.isSequenceVR(vr) || (ValueRepresentation.isUnknownVR(vr) && vl == 0xffffffffl)) {
					a=new SequenceAttribute(tag);
					byteOffset=readNewSequenceAttribute(a,i,byteOffset,vl,specificCharacterSet);
				}
				else if (vl != 0xffffffffl) {
					//if (vl > maximumSaneFixedValueLength) throw new DicomException("unlikely fixed VL ("+vl+" dec, 0x"+Long.toHexString(vl)+") - probably incorrect dataset");
					a = AttributeFactory.newAttribute(tag,vr,vl,i,specificCharacterSet,explicit,bytesPerSample,byteOffset);	// creates and reads the attribute
					byteOffset+=vl;
				}
				else if (vl == 0xffffffffl && tag.equals(TagFromName.PixelData)/* && i.getTransferSyntaxInUse().isEncapsulated()*/) {	// assume encapsulated in case TS is not recognized
					int wordsPerFrame = rows*columns*samplesPerPixel;
//System.err.println("Undefined length encapsulated Pixel Data: words per frame "+wordsPerFrame);
					String tsuid = i.getTransferSyntaxInUse().getUID();
//System.err.println("Undefined length encapsulated Pixel Data: TransferSyntax UID "+tsuid);
					boolean doneReadingEncapsulatedData = false;
					EncapsulatedInputStream ei = new EncapsulatedInputStream(i);
					//try {
					{
						if (tsuid.equals(TransferSyntax.PixelMedEncapsulatedRawLittleEndian)) {
							if (bytesPerSample == 1) {
								byte[] values = new byte[wordsPerFrame*frames];
								for (int f=0; f<frames; ++f) {
									ei.read(values,f*wordsPerFrame,wordsPerFrame);
									//ei.nextFrame();
								}
								a = new OtherByteAttribute(tag);
								a.setValues(values);
								doneReadingEncapsulatedData=true;
							}
							else if (bytesPerSample == 2) {
								short[] values = new short[wordsPerFrame*frames];
								for (int f=0; f<frames; ++f) {
									ei.readUnsigned16(values,f*wordsPerFrame,wordsPerFrame);
									//ei.nextFrame();
								}
								a = new OtherWordAttribute(tag);
								a.setValues(values);
								doneReadingEncapsulatedData=true;
							}
							else {
								throw new DicomException("Encapsulated data of more than 2 bytes per sample not supported (got "+bytesPerSample+")");
							}
						}
						else {
							if (!haveScannedForCodecs) {
System.err.println("AttributeList.read(): Scanning for ImageIO plugin codecs");
								ImageIO.scanForPlugins();
								haveScannedForCodecs=true;
							}
							String readerWanted = null;
							if (tsuid.equals(TransferSyntax.JPEGBaseline) || tsuid.equals(TransferSyntax.JPEGExtended)) {
								readerWanted="JPEG";
//System.err.println("Undefined length encapsulated Pixel Data in JPEG Baseline");
							}
							else if (tsuid.equals(TransferSyntax.JPEG2000) || tsuid.equals(TransferSyntax.JPEG2000Lossless)) {
								readerWanted="JPEG2000";
//System.err.println("Undefined length encapsulated Pixel Data in JPEG 2000");
							}
							else if (tsuid.equals(TransferSyntax.JPEGLossless) || tsuid.equals(TransferSyntax.JPEGLosslessSV1)) {
								readerWanted="jpeg-lossless";
//System.err.println("Undefined length encapsulated Pixel Data in JPEG Lossless");
							}
							else if (tsuid.equals(TransferSyntax.JPEGLS) || tsuid.equals(TransferSyntax.JPEGNLS)) {
								readerWanted="jpeg-ls";
//System.err.println("Undefined length encapsulated Pixel Data in JPEG-LS");
							}
							if (readerWanted != null) {
								ImageReader reader = null;
								ImageReaderSpi spi = null;
								try {
									reader =  (ImageReader)(ImageIO.getImageReadersByFormatName(readerWanted).next());
									spi = reader.getOriginatingProvider();
System.out.println("Using reader from "+spi.getDescription(Locale.US)+" "+spi.getVendorName()+" "+spi.getVersion());
									OurIIOReadProgressListener progressListener = new OurIIOReadProgressListener();
									reader.addIIOReadProgressListener(progressListener);
//System.out.println("Back from reader.addIIOReadProgressListener()");
								}
								catch (Exception e) {
									dumpListOfAllAvailableReaders(System.err);
									throw new DicomException("No reader for "+readerWanted+" available for Transfer Syntax "+tsuid);
								}
								if (reader != null) {
									byte[]  bytePixelData = null;	// lazy instantiation of one or the other
									short[] shortPixelData = null;
									int pixelsPerFrame = columns*rows*samplesPerPixel;
									int pixelsPerMultiFrameImage = pixelsPerFrame*frames;
									for (int f=0; f<frames; ++f) {
//System.out.println("Starting frame "+f);
										BufferedImage image = null;
										ImageInputStream iiois = ImageIO.createImageInputStream(ei);
										reader.setInput(iiois,true/*seekForwardOnly*/,true/*ignoreMetadata*/);
										image = reader.read(0);
//System.out.println("Back from frame "+f+" reader.read(), BufferedImage="+image);
										if (image == null) {
											throw new DicomException("Reader "+spi.getDescription(Locale.US)+" "+spi.getVendorName()+" "+spi.getVersion()
												+" returned null image for Transfer Syntax "+tsuid);
										}
										else {
											Raster raster = image.getData();
											int numDataElements = raster.getNumDataElements();
//System.out.println("getNumDataElements="+numDataElements);
											if (numDataElements == samplesPerPixel) {
												int transferType = raster.getTransferType();
//System.out.println("getTransferType="+transferType);
												if (transferType == DataBuffer.TYPE_BYTE) {
//System.out.println("Getting "+(samplesPerPixel > 1 ? "interleaved " : "")+samplesPerPixel+" channel byte data");
													byte[] vPixelData = (byte[])(raster.getDataElements(0,0,columns,rows,null));
//System.out.println("Decompressed byte array length "+vPixelData.length+" expected "+pixelsPerFrame);
													if (bytePixelData == null) {
														if (frames == 1) {
															bytePixelData = vPixelData;
														}
														else {
															bytePixelData = new byte[pixelsPerMultiFrameImage];
														}
													}
													if (vPixelData != null) {
														System.arraycopy(vPixelData,0,bytePixelData,pixelsPerFrame*f,pixelsPerFrame);
													}
												}
												else if (transferType == DataBuffer.TYPE_SHORT
												      || transferType == DataBuffer.TYPE_USHORT) {
//System.out.println("Getting "+(samplesPerPixel > 1 ? "interleaved " : "")+samplesPerPixel+" channel byte data");
													short[] vPixelData = (short[])(raster.getDataElements(0,0,columns,rows,null));
//System.out.println("Decompressed short array length "+vPixelData.length+" expected "+pixelsPerFrame);
													if (shortPixelData == null) {
														if (frames == 1) {
															shortPixelData = vPixelData;
														}
														else {
															shortPixelData = new short[pixelsPerMultiFrameImage];
														}
													}
													if (vPixelData != null) {
														System.arraycopy(vPixelData,0,shortPixelData,pixelsPerFrame*f,pixelsPerFrame);
													}
												}
											}
										}
										ei.nextFrame();
									}
									if (bytePixelData != null) {
										a = new OtherByteAttribute(tag);
										a.setValues(bytePixelData);
									}
									else if (shortPixelData != null) {
										a = new OtherWordAttribute(tag);
										a.setValues(shortPixelData);
									}
									doneReadingEncapsulatedData=true;
								}
							}
							else {
								throw new DicomException("Unrecognized Transfer Syntax "+tsuid);
							}
						}
					}
					//catch (Exception e) {
					//	e.printStackTrace(System.err);
					//}
					if (!doneReadingEncapsulatedData) {
//System.out.println("Skipping encapsulated pixel data");
						while (ei.skip(1024) > 0);	// it is appropriate to use skip() rather than use skipInsistently() here 
					}
				}

				if (a != null) {
//System.err.println(a.toString());
					put(tag,a);

					if (tag.equals(TagFromName.FileMetaInformationGroupLength)) {
						if (i.areReadingMetaHeader()) {
//System.err.println("Found meta-header");
//System.err.println("Length attribute class="+a.getClass());
							long metaLength=a.getSingleIntegerValueOrDefault(0);
							byteOffset=read(i,byteOffset,metaLength,false,null,stopAtTag);		// detects and sets transfer syntax for reading dataset
							i.setReadingDataSet();
							if (stopAfterMetaInformationHeader) {
//System.err.println("Stopping after meta-header");
								break;
							}
							else {
//System.err.println("Calling read");
								byteOffset=read(i,byteOffset,0xffffffffl,false,null,stopAtTag);	// read to end (will detect and set own SpecificCharacterSet)
//System.err.println("Back from read after metaheader: now undefinedLength="+undefinedLength+" byteOffset="+byteOffset+" endByteOffset="+endByteOffset);
								break;	// ... no plausible reason to continue past this point
							}
						}
						else {
							// ignore it, e.g. nested within a sequence item (GE bug).
//System.err.println("Ignoring unexpected FileMetaInformationGroupLength outside meta information header");
						}
					}
					else if (tag.equals(TagFromName.TransferSyntaxUID)) {
						if (i.areReadingMetaHeader()) {
							i.setTransferSyntaxToReadDataSet(new TransferSyntax(a.getSingleStringValueOrDefault(TransferSyntax.ExplicitVRLittleEndian)));
						}
						else {
							// ignore it, e.g. nested within a sequence item (GE bug).
//System.err.println("Ignoring unexpected TransferSyntaxUID outside meta information header");
						}
					}
					else if (tag.equals(TagFromName.SpecificCharacterSet)) {
						specificCharacterSet = new SpecificCharacterSet(a.getStringValues(),a.getByteValues());
					}
					else if (tag.equals(TagFromName.Columns)) {
						columns = a.getSingleIntegerValueOrDefault(0);
					}
					else if (tag.equals(TagFromName.Rows)) {
						rows = a.getSingleIntegerValueOrDefault(0);
					}
					else if (tag.equals(TagFromName.NumberOfFrames)) {
						frames = a.getSingleIntegerValueOrDefault(1);
					}
					else if (tag.equals(TagFromName.SamplesPerPixel)) {
						samplesPerPixel = a.getSingleIntegerValueOrDefault(1);
					}
					else if (tag.equals(TagFromName.BitsAllocated)) {
						bytesPerSample = (a.getSingleIntegerValueOrDefault(16)-1)/8+1;
					}
				}
			}
		}
		catch (EOFException e) {
//System.err.println("Closing on "+e);
			if (!undefinedLength) throw new EOFException();
		}
		catch (IOException e) {
//System.err.println("Closing on "+e);
			if (!undefinedLength) throw new IOException();		// InflaterInputStream seems to throw IOException rather than EOFException
		}

		return byteOffset;
	}

	/**
	 * <p>Read the meta information header (if present) and then stop.</p>
	 *
	 * <p>Leaves the stream opened and positioned at the start of the data set.</p>
	 *
	 * @param	i		the stream to read from
	 * @exception	IOException
	 * @exception	DicomException
	 */
	public void readOnlyMetaInformationHeader(DicomInputStream i) throws IOException, DicomException {
		read(i,i.getByteOffsetOfStartOfData(),0xffffffffl,true,null);
//System.err.println("readOnlyMetaInformationHeader(): afterwards i.areReadingDataSet()="+i.areReadingDataSet());
		// important that i.areReadingDataSet() be true at this point ... triggers check for codec if read (or copied) further
	}

	/**
	 * <p>Read all the DICOM attributes in the stream until the specified tag is encountered.</p>
	 *
	 * <p>Does not read beyond the group element pair of the specified stop tag.</p>
	 *
	 * <p>Leaves the stream open.</p>
	 *
	 * @param	i		the stream to read from
	 * @param	tag		the tag (in the top level data set) at which to stop
	 * @exception	IOException
	 * @exception	DicomException
	 */
	public void read(DicomInputStream i,AttributeTag tag) throws IOException, DicomException {
//System.err.println("read(DicomInputStream i,AttributeTag tag="+tag+"):");
		read(i,i.getByteOffsetOfStartOfData(),0xffffffffl,false,null,tag);
	}

	/**
	 * <p>Read all the DICOM attributes in the stream until there are no more.</p>
	 *
	 * <p>Leaves the stream open.</p>
	 *
	 * @param	i		the stream to read from
	 * @exception	IOException
	 * @exception	DicomException
	 */
	public void read(DicomInputStream i) throws IOException, DicomException {
		read(i,i.getByteOffsetOfStartOfData(),0xffffffffl,false,null);
	}

	/**
	 * <p>Read an entire DICOM object in the specified file.</p>
	 *
	 * <p>Returns the attributes of both the meta information header (if present) and data set.</p>
	 *
	 * @param	name			the input file name
	 * @param	transferSyntaxUID	the transfer syntax to use for the data set (leave null for autodetection)
	 * @param	hasMeta			look for a meta information header
	 * @param	useBufferedStream	buffer the input for better performance
	 * @exception	IOException
	 * @exception	DicomException
	 */
	public void read(String name,String transferSyntaxUID,boolean hasMeta,boolean useBufferedStream) throws IOException, DicomException {
		InputStream i = new FileInputStream(name);
		if (useBufferedStream) i=new BufferedInputStream(i);
		DicomInputStream di = new DicomInputStream(i,transferSyntaxUID,hasMeta);
		try {
			read(di);
		}
		finally {
			di.close();
		}
	}

	/**
	 * <p>Read an entire DICOM object in the specified file.</p>
	 *
	 * <p>Returns the attributes of both the meta information header (if present) and data set.</p>
	 *
	 * <p>Always tries to automatically detect the meta information header or transfer syntax
	 * if no meta information header and buffers the input for better performance.</p>
	 *
	 * @param	name			the input file name
	 * @exception	IOException
	 * @exception	DicomException
	 */
	public void read(String name) throws IOException, DicomException {
		read(name,null,true,true);
	}

	/**
	 * <p>Associates the specified value (attribute) with the specified key (tag).</p>
	 *
	 * <p>If the map previously contained a mapping for this key, the old value is replaced.</p>
	 *
	 * <p>This untyped method is present to over ride the super class method to be sure that
	 * null values are ever inserted.</p>
	 *
	 * @param	key			key (tag) with which the specified value (attribute) is to be associated
	 * @param	value			value (attribute) to be associated with the specified key (tag)
	 * @return				previous value associated with specified key, or null if there was no mapping for key 
	 * @exception	NullPointerException	thrown if a or t is null
	 */
	public Object put(Object key, Object value) {
		if (key == null || value == null) {
			throw new NullPointerException();
		}
		else {
			return super.put(key,value);
		}
	}

	/**
	 * <p>Associates the specified value (attribute) with the specified key (tag).</p>
	 *
	 * <p>If the map previously contained a mapping for this key, the old value is replaced.</p>
	 *
	 * @see java.util.TreeMap#put(Object,Object)
	 *
	 * @param	t			key (tag) with which the specified value (attribute) is to be associated
	 * @param	a			value (attribute) to be associated with the specified key (tag)
	 * @return				previous value (attribute) associated with specified key (tag), or null if there was no mapping for key (tag) 
	 * @exception	NullPointerException	thrown if a or t is null
	 */
	public Attribute put(AttributeTag t, Attribute a) {
		if (a == null || t == null) {
			throw new NullPointerException();
		}
		else {
			return (Attribute)(super.put(t,a));
		}
	}


	/**
	 * <p>Associates the specified value (attribute) with the key that is the existing tag of the attribute.</p>
	 *
	 * <p>If the map previously contained a mapping for this key, the old value is replaced.</p>
	 *
	 * @see #put(AttributeTag,Attribute)
	 *
	 * @param	a			value (attribute) to be associated with the specified key (tag)
	 * @return				previous value (attribute) associated with specified key (tag), or null if there was no mapping for key (tag) 
	 * @exception	NullPointerException	thrown if a or t is null
	 */
	public Attribute put(Attribute a) {
		if (a == null) {
			throw new NullPointerException();
		}
		else {
			return put(a.getTag(),a);
		}
	}
	/**
	 * <p>Returns the value (attribute) to which this map maps the specified key (tag).</p>
	 *
	 * <p>Returns null if the map contains no mapping for this key. A return value of null
	 * does indicate that the map contains no mapping for the key, unlike {@link java.util.TreeMap#get(Object) java.util.get(Object)}
	 * since the put operation checks for and disallows null insertions. This contract will hold
	 * true unless one goes to great effort to insert a key that maps to a null value
	 * by using one of the other insertion methods of the super class, in which case
	 * other operations (like writing) may fail later with a NullPointerException.</p>
	 *
	 * @param	t	key (tag) whose associated value (attribute) is to be returned
	 */
	public Attribute get(AttributeTag t) {
		return (Attribute)(super.get(t));
	}

	/**
	 * <p>Determine whether or not this list is an image.</p>
	 *
	 * <p>An image is defined to be something with a PixelData attribute at the top level.</p>
	 *
	 * @return	true if an image 
	 */
	public boolean isImage() {
		return get(TagFromName.PixelData) != null;
	}

	/**
	 * <p>Determine whether or not this list is an SR Document.</p>
	 *
	 * <p>An SR Document is defined to be something with a ContentSequence attribute at the top level.</p>
	 *
	 * @return	true if an SR Document 
	 */
	public boolean isSRDocument() {
		return get(TagFromName.ContentSequence) != null;
	}

	/**
	 * <p>Get the dictionary in use for this list.</p>
	 *
	 * <p>Creates one if necessary.</p>
	 *
	 * @return	the dictionary 
	 */
	public DicomDictionary getDictionary() {
		createDictionaryifNecessary();
		return dictionary;
	}
	
	/**
	 * <p>Removes the mapping for this key (tag), if present.</p>
	 *
	 * @param	tag	key (tag) for which mapping should be removed
	 * @return		previous value (attribute) associated with specified key (tag), or null if there was no mapping for key (tag) 
	 */
	public Attribute remove(AttributeTag tag) {
		return (Attribute)(super.remove(tag));
	}

	// useful list handling routines beyond those inherited from TreeMap
	
	/**
	 * <p>Replaces an attribute with a zero length attribute, if present in the list.</p>
	 *
	 * <p>Does nothing if the attribute was not already present.</p>
	 *
	 * @param	tag		key (tag) for which the attribute should be replaced
	 * @exception	DicomException	thrown if there is any difficulty creating the new zero length attribute
	 */
	public void replaceWithZeroLengthIfPresent(AttributeTag tag) throws DicomException {
		Object o=get(tag);
		if (o != null) {
			//remove(tag);
			Attribute a = AttributeFactory.newAttribute(tag,getDictionary().getValueRepresentationFromTag(tag));
			put(tag,a);
		}
	}

	// list management methods ...
	
	/**
	 * <p>Remove any private attributes present in the list.</p>
	 *
	 * <p>Private attributes are all those with an odd group number.</p>
	 */
	public void removePrivateAttributes() {
		Iterator i = values().iterator();
		while (i.hasNext()) {
			Attribute a = (Attribute)i.next();
			if (a.getTag().isPrivate()) i.remove();
		}
	}
	
	/**
	 * <p>Remove any meta information header attributes present in the list.</p>
	 *
	 * <p>Meta information header attributes are all those in group 0x0002.</p>
	 *
	 * <p>Note that this should always be done when modifying the SOP Class or
	 * Instance UID of an attribute list what has been read before writing,
	 * since it is vital that the corresponding meta information header attributes
	 * match those in the data set.</p>
	 *
	 * @see com.pixelmed.dicom.FileMetaInformation
	 */
	public void removeMetaInformationHeaderAttributes() {
		Iterator i = values().iterator();
		while (i.hasNext()) {
			Attribute a = (Attribute)i.next();
			if (a.getTag().getGroup() == 0x0002) i.remove();
		}
	}
	
	/**
	 * <p>Remove any group length attributes present in the list, except the meta information header length, as well as LengthToEnd.</p>
	 *
	 * <p>Group length attributes are all those with an element of 0x0000.</p>
	 *
	 * <p>LengthToEnd (0x0008,0x0001) is always removed if present as well.</p>
	 *
	 * <p>These have never been required in DICOM and are a holdover from the old
	 * ACR-NEMA days, and are a source of constant problems, so should always
	 * be removed.</p>
	 *
	 * <p>The meta information header length is left alone, since it is mandatory.</p>
	 *
	 * @see com.pixelmed.dicom.FileMetaInformation
	 */
	public void removeGroupLengthAttributes() {
		Iterator i = values().iterator();
		while (i.hasNext()) {
			Attribute a = (Attribute)i.next();
			AttributeTag t = a.getTag();
			if (t.getElement() == 0x0000 && t.getGroup() != 0x0002) i.remove();	// leave metaheader alone
		}
		remove(TagFromName.LengthToEnd);
	}

	// Miscellaneous methods ...
	
	/**
	 * <p>Dump the contents of the attribute list as a human-readable string.</p>
	 *
	 * <p>Each attribute is written to a separate line, in the form defined
	 * for {@link com.pixelmed.dicom.Attribute#toString() com.pixelmed.dicom.Attribute.toString()}.</p>
	 *
	 * @return			the string
	 */
	public String toString() {
		StringBuffer str = new StringBuffer();

		Iterator i = values().iterator();
		while (i.hasNext()) {
			str.append(((Attribute)i.next()).toString(dictionary));
			str.append("\n");
		}
		return str.toString();
	}

	/**
	 * <p>Dump the contents of the attribute list as a human-readable string.</p>
	 *
	 * <p>Each attribute is written to a separate line, in the form defined
	 * for {@link com.pixelmed.dicom.Attribute#toString(DicomDictionary dictionary) com.pixelmed.dicom.Attribute.toString(DicomDictionary dictionary)}.</p>
	 *
	 * @param	dictionary	the dictionary to use to look up the name
	 * @return			the string
	 */
	public String toString(DicomDictionary dictionary) {
		StringBuffer str = new StringBuffer();

		Iterator i = values().iterator();
		while (i.hasNext()) {
			str.append(((Attribute)i.next()).toString(dictionary));
			str.append("\n");
		}
		return str.toString();
	}


	/**
	 * <p>Change the file containing the data used by any attribute whose values are left on disk, for example if the file has been renamed.</p>
	 *
	 * @param	file	the new file containing the data
	 */
	public void setFileUsedByOnDiskAttributes(File file) {
//System.err.println("AttributeList.setFileUsedByOnDiskAttributes(): file = "+file);
		Iterator i = values().iterator();
		while (i.hasNext()) {
			Attribute a = (Attribute)i.next();
//System.err.println("AttributeList.setFileUsedByOnDiskAttributes(): checking "+a.getClass()+" - "+a.toString(dictionary));
			if (a instanceof OtherByteAttributeOnDisk) {
//System.err.println("AttributeList.setFileUsedByOnDiskAttributes(): setting OtherByteAttributeOnDisk to file = "+file);
				((OtherByteAttributeOnDisk)a).setFile(file);
			}
			else if (a instanceof OtherWordAttributeOnDisk) {
//System.err.println("AttributeList.setFileUsedByOnDiskAttributes(): setting OtherWordAttributeOnDisk to file = "+file);
				((OtherWordAttributeOnDisk)a).setFile(file);
			}
		}
	}

	/**
	 * <p>Create a new attribute with the specified tag and insert it in the map associating the generated attribute with the specified tag as the key.</p>
	 *
	 * <p>If the map previously contained a mapping for the tag (key), the old value is replaced.</p>
	 *
	 * @param	t						key ({@link com.pixelmed.dicom.AttributeTag AttributeTag} tag) with which the generated attribute is to be associated
	 * @param	specificCharacterSet	the {@link com.pixelmed.dicom.SpecificCharacterSet SpecificCharacterSet} to be used text values
	 * @return							the newly created attribute 
	 * @exception	DicomException		if cannot create attribute, such as if cannot find tag in dictionary
	 */
	public Attribute putNewAttribute(AttributeTag t,SpecificCharacterSet specificCharacterSet) throws DicomException {
		Attribute a = null;
		byte[] vr = getDictionary().getValueRepresentationFromTag(t);
		if (vr == null) {
			throw new DicomException("No such data element as "+t+" in dictionary");
		}
		else {
			a = AttributeFactory.newAttribute(t,vr,specificCharacterSet);
			if (a == null) {
				throw new DicomException("Could not create attribute for tag "+t);
			}
			else {
				super.put(t,a);
			}
		}
		return a;
	}

	/**
	 * <p>Create a new attribute with the specified tag and insert it in the map associating the generated attribute with the specified tag as the key.</p>
	 *
	 * <p>If the map previously contained a mapping for the tag (key), the old value is replaced.</p>
	 *
	 * @param	t						key ({@link com.pixelmed.dicom.AttributeTag AttributeTag} tag) with which the generated attribute is to be associated
	 * @return							the newly created attribute 
	 * @exception	DicomException		if cannot create attribute, such as if cannot find tag in dictionary
	 */
	public Attribute putNewAttribute(AttributeTag t) throws DicomException {
		return putNewAttribute(t,null);
	}
}



