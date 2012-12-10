package ua.stu.scplib.attribute;

import java.util.HashMap;
import java.util.Map;

/**
 * <p>A class to lookup the DICOM Transfer Syntax UID from a string name.</p>
 *
 * @author	dclunie
 */
public class TransferSyntaxFromName {

	static protected Map map = new HashMap();

	static {
		map.put("ImplicitVRLittleEndian",TransferSyntax.ImplicitVRLittleEndian);
		map.put("ExplicitVRLittleEndian",TransferSyntax.ExplicitVRLittleEndian);
		map.put("ExplicitVRBigEndian",TransferSyntax.ExplicitVRBigEndian);
		map.put("Default",TransferSyntax.Default);
		map.put("DeflatedExplicitVRLittleEndian",TransferSyntax.DeflatedExplicitVRLittleEndian);
		map.put("JPEGBaseline",TransferSyntax.JPEGBaseline);
		map.put("JPEGExtended",TransferSyntax.JPEGExtended);
		map.put("JPEGLossless",TransferSyntax.JPEGLossless);
		map.put("JPEGLosslessSV1",TransferSyntax.JPEGLosslessSV1);
		map.put("JPEGLS",TransferSyntax.JPEGLS);
		map.put("JPEGNLS",TransferSyntax.JPEGNLS);
		map.put("JPEG2000Lossless",TransferSyntax.JPEG2000Lossless);
		map.put("JPEG2000",TransferSyntax.JPEG2000);
		map.put("MPEG2MPML",TransferSyntax.MPEG2MPML);
		map.put("PixelMedBzip2ExplicitVRLittleEndian",TransferSyntax.PixelMedBzip2ExplicitVRLittleEndian);
		map.put("PixelMedEncapsulatedRawLittleEndian",TransferSyntax.PixelMedEncapsulatedRawLittleEndian);
	}
	
	private TransferSyntaxFromName() {
	}
	
	/**
	 * <p>Get the Transfer Syntax UID from the name.</p>
	 *
	 * @param		name	a string name of the transfer syntax
	 * @return				the UID if found, else the supplied argument if of UID form, else null
	 */
	static public String getUID(String name)	{
		String uid = null;
		if (name != null) {
			uid = (String)(map.get(name));
			if (uid == null) {
				// if string is a UID form, just return itself
				if (name.matches("[0-9.][0-9.]*")) {
					uid=name;
				}
			}
		}
		return uid;
	}
	
	/**
	 * <p>Test.</p>
	 *
	 * @param	arg	none
	 */
	public static void main(String arg[]) {
		System.err.println("Default from name: "+(TransferSyntaxFromName.getUID("Default").equals(TransferSyntax.Default)));
		System.err.println("Default from uid : "+(TransferSyntaxFromName.getUID(TransferSyntax.Default).equals(TransferSyntax.Default)));
		System.err.println("Dummy from name  : "+(TransferSyntaxFromName.getUID("Dummy") == null));
	}

}

