package ua.stu.scplib.attribute;

/**
 * <p>A class to represent the characteristics of a DICOM Transfer Syntax, which may
 * be instantiated from a UID or from basic characteristics, as well as static
 * UID strings for known Transfer Syntaxes, and static methods for extracting
 * the characteristics of known Transfer Syntaxes.</p>
 *
 * @author	dclunie
 */
public class TransferSyntax {

	/***/
	public static final String ImplicitVRLittleEndian              = "1.2.840.10008.1.2";
	/***/
	public static final String ExplicitVRLittleEndian              = "1.2.840.10008.1.2.1";
	/***/
	public static final String ExplicitVRBigEndian                 = "1.2.840.10008.1.2.2";
	/***/
	public static final String Default                             = ImplicitVRLittleEndian;
	/***/
	public static final String DeflatedExplicitVRLittleEndian      = "1.2.840.10008.1.2.1.99";
	/***/
	public static final String JPEGBaseline                        = "1.2.840.10008.1.2.4.50";
	/***/
	public static final String JPEGExtended                        = "1.2.840.10008.1.2.4.51";
	/***/
	public static final String JPEGLossless                        = "1.2.840.10008.1.2.4.57";
	/***/
	public static final String JPEGLosslessSV1                     = "1.2.840.10008.1.2.4.70";
	/***/
	public static final String JPEGLS                              = "1.2.840.10008.1.2.4.80";
	/***/
	public static final String JPEGNLS                             = "1.2.840.10008.1.2.4.81";
	/***/
	public static final String JPEG2000Lossless                    = "1.2.840.10008.1.2.4.90";
	/***/
	public static final String JPEG2000                            = "1.2.840.10008.1.2.4.91";
	/***/
	public static final String MPEG2MPML                           = "1.2.840.10008.1.2.4.100";
	/***/
	public static final String PixelMedBzip2ExplicitVRLittleEndian = "1.3.6.1.4.1.5962.300.1";
	/***/
	public static final String PixelMedEncapsulatedRawLittleEndian = "1.3.6.1.4.1.5962.300.2";
	
	/***/
	protected String transferSyntaxUID;
	/***/
	protected String description;
	/***/
	protected boolean bigEndian;
	/***/
	protected boolean explicitVR;
	/***/
	protected boolean encapsulatedPixelData;
	/***/
	protected boolean lossy;
	/***/
	protected boolean transformedColorSpace;

	/**
	 * <p>Construct a Transfer Syntax using the specified UID, automatically determining its characteristics.</p>
	 *
	 * @param	uid		the UID to use to refer to this transfer syntax
	 */
	public TransferSyntax(String uid) {
		transferSyntaxUID=uid;
		description="Unrecognized";
		bigEndian=false;
		explicitVR=true;
		encapsulatedPixelData=false;
		lossy=false;
		transformedColorSpace=false;

		if (transferSyntaxUID.equals(ImplicitVRLittleEndian)) {
			description="Implicit VR Little Endian";
			bigEndian=false;
			explicitVR=false;
			encapsulatedPixelData=false;
			lossy=false;
			transformedColorSpace=false;
		}
		else if (transferSyntaxUID.equals(ExplicitVRLittleEndian)) {
			description="Explicit VR Little Endian";
			bigEndian=false;
			explicitVR=true;
			encapsulatedPixelData=false;
			lossy=false;
			transformedColorSpace=false;
		}
		else if (transferSyntaxUID.equals(ExplicitVRBigEndian)) {
			description="Explicit VR Big Endian";
			bigEndian=true;
			explicitVR=true;
			encapsulatedPixelData=false;
			lossy=false;
			transformedColorSpace=false;
		}
		else if (transferSyntaxUID.equals(PixelMedEncapsulatedRawLittleEndian)) {
			description="PixelMed Encapsulated Raw Little Endian";
			bigEndian=false;
			explicitVR=true;
			encapsulatedPixelData=true;
			lossy=false;
			transformedColorSpace=false;
		}
		else if (transferSyntaxUID.equals(JPEGBaseline)) {
			description="JPEG Baseline";
			bigEndian=false;
			explicitVR=true;
			encapsulatedPixelData=true;
			lossy=true;
			transformedColorSpace=true;
		}
		else if (transferSyntaxUID.equals(JPEGExtended)) {
			description="JPEG Extended";
			bigEndian=false;
			explicitVR=true;
			encapsulatedPixelData=true;
			lossy=true;
			transformedColorSpace=true;
		}
		else if (transferSyntaxUID.equals(JPEG2000Lossless)) {
			description="JPEG 2000 Lossless Only";
			bigEndian=false;
			explicitVR=true;
			encapsulatedPixelData=true;
			lossy=false;
			transformedColorSpace=true;
		}
		else if (transferSyntaxUID.equals(JPEG2000)) {
			description="JPEG 2000";
			bigEndian=false;
			explicitVR=true;
			encapsulatedPixelData=true;
			lossy=true;
		}
		else if (transferSyntaxUID.equals(JPEGLossless)) {
			description="JPEG Lossless";
			bigEndian=false;
			explicitVR=true;
			encapsulatedPixelData=true;
			lossy=false;
			transformedColorSpace=true;
		}
		else if (transferSyntaxUID.equals(JPEGLosslessSV1)) {
			description="JPEG Lossless SV1";
			bigEndian=false;
			explicitVR=true;
			encapsulatedPixelData=true;
			lossy=false;
			transformedColorSpace=true;
		}
		else if (transferSyntaxUID.equals(JPEGLS)) {
			description="JPEG-LS";
			bigEndian=false;
			explicitVR=true;
			encapsulatedPixelData=true;
			lossy=false;
			transformedColorSpace=true;
		}
		else if (transferSyntaxUID.equals(JPEGNLS)) {
			description="JPEG-LS Near-lossless";
			bigEndian=false;
			explicitVR=true;
			encapsulatedPixelData=true;
			lossy=true;
			transformedColorSpace=true;
		}
		else if (transferSyntaxUID.equals(MPEG2MPML)) {
			description="MPEG2 MPML";
			bigEndian=false;
			explicitVR=true;
			encapsulatedPixelData=true;
			lossy=true;
			transformedColorSpace=true;
		}
	}

	/**
	 * <p>Construct a Transfer Syntax using the specified UID and characteristics.</p>
	 *
	 * @param	transferSyntaxUID		the UID to use to refer to this transfer syntax
	 * @param	description				the description of this transfer syntax
	 * @param	explicitVR				true if an explicit VR transfer syntax
	 * @param	bigEndian				true if big-endian transfer syntax
	 * @param	encapsulatedPixelData	true if a pixel data encapsulated transfer syntax
	 * @param	lossy					true if lossy compression
	 * @param	transformedColorSpace	true if color space transformation may be used
	 */
	public TransferSyntax(String transferSyntaxUID, String description,boolean explicitVR,boolean bigEndian,boolean encapsulatedPixelData,boolean lossy,boolean transformedColorSpace) {
		this.transferSyntaxUID=transferSyntaxUID;
		this.description=description;
		this.explicitVR=explicitVR;
		this.bigEndian=bigEndian;
		this.encapsulatedPixelData=encapsulatedPixelData;
		this.lossy=lossy;
		this.transformedColorSpace=transformedColorSpace;
	}

	/**
	 * <p>Get the UID of the Transfer Syntax.</p>
	 *
	 * @return		the UID of the Transfer Syntax 
	 */
	public String getUID()				{ return transferSyntaxUID; }

	/**
	 * <p>Get a human-readable description of the Transfer Syntax.</p>
	 *
	 * @return		the description of the Transfer Syntax 
	 */
	public String getDescription()			{ return description; }

	/**
	 * <p>Is the Transfer Syntax big endian ?</p>
	 *
	 * @return		true if big endian
	 */
	public boolean isBigEndian()			{ return bigEndian; }

	/**
	 * <p>Is the Transfer Syntax little endian ?</p>
	 *
	 * @return		true if little endian
	 */
	public boolean isLittleEndian()			{ return !bigEndian; }

	/**
	 * <p>Is the Transfer Syntax explicit VR ?</p>
	 *
	 * @return		true if explicit VR
	 */
	public boolean isExplicitVR()			{ return explicitVR; }

	/**
	 * <p>Is the Transfer Syntax implicit VR ?</p>
	 *
	 * @return		true if implicit VR
	 */
	public boolean isImplicitVR()			{ return !explicitVR; }

	/**
	 * <p>Does the Transfer Syntax encapsulate the pixel data ?</p>
	 *
	 * @return		true if encapsulate
	 */
	public boolean isEncapsulated()			{ return encapsulatedPixelData; }

	/**
	 * <p>Does the Transfer Syntax encode the pixel data without encapsulation?</p>
	 *
	 * @return		true if not encapsulated
	 */
	public boolean isNotEncapsulated()		{ return !encapsulatedPixelData; }

	/**
	 * <p>Is the Transfer Syntax potentially lossy ?</p>
	 *
	 * @return		true if lossy
	 */
	public boolean isLossy()			{ return lossy; }

	/**
	 * <p>Does the Transfer Syntax use deflate compression ?</p>
	 *
	 * @return		true if deflated
	 */
	public boolean isDeflated()			{ return transferSyntaxUID.equals(DeflatedExplicitVRLittleEndian); }

	/**
	 * <p>Does the Transfer Syntax use bzip2 compression ?</p>
	 *
	 * @return		true if bzip2
	 */
	public boolean isBzip2ed()			{ return transferSyntaxUID.equals(PixelMedBzip2ExplicitVRLittleEndian); }

	/**
	 * <p>Does the Transfer Syntax allow transformed color spaces?</p>
	 *
	 * <p>E.g., JPEG (normally) uses YBR_FULL_422 instead of RGB</p>
	 *
	 * @return		true if transformed color spaces
	 */
	public boolean mayBeTransformedColorSpace()			{ return transformedColorSpace; }

	/**
	 * <p>Get the Transfer Syntax as a string.</p>
	 *
	 * @return		the UID of the Transfer Syntax 
	 */
	public String toString() 			{ return transferSyntaxUID; }
	
	/**
	 * <p>Is the Transfer Syntax with the specified UID explicit VR ?</p>
	 *
	 * @param	uid
	 * @return		true if explicit VR
	 */
	static public boolean isExplicitVR(String uid)		{ return !isImplicitVR(uid); }
	
	/**
	 * <p>Is the Transfer Syntax with the specified UID implicit VR ?</p>
	 *
	 * @param	uid
	 * @return		true if explicit VR
	 */
	static public boolean isImplicitVR(String uid)		{ return uid.equals(ImplicitVRLittleEndian); }

	/**
	 * <p>Is the Transfer Syntax with the specified UID big endian ?</p>
	 *
	 * @param	uid
	 * @return		true if big endian
	 */
	static public boolean isBigEndian(String uid)		{ return uid.equals(ExplicitVRBigEndian); }

	/**
	 * <p>Is the Transfer Syntax with the specified UID little endian ?</p>
	 *
	 * @param	uid
	 * @return		true if little endian
	 */
	static public boolean isLittleEndian(String uid)	{ return !isBigEndian(uid); }
	
	/**
	 * <p>Does the Transfer Syntax allow transformed color spaces?</p>
	 *
	 * <p>E.g., JPEG (normally) uses YBR_FULL_422 instead of RGB</p>
	 *
	 * @param	uid
	 * @return		true if transformed color spaces are allowed
	 */
	static public boolean mayBeTransformedColorSpace(String uid)	{ return new TransferSyntax(uid).mayBeTransformedColorSpace(); }
}


