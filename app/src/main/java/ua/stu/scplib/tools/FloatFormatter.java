package ua.stu.scplib.tools;

import java.util.ArrayList;

/**
 * <p>Various static methods helpful for formatting floating point values.</p>
 *
 * @author	dclunie
 */
public class FloatFormatter {

    private static final int precisionToDisplayDouble = 4;
    private static final int maximumIntegerDigits = 8;
    private static final int maximumMaximumFractionDigits = 6;

	private FloatFormatter() {}

	/**
	 * <p>Given a double value, return a string representation without too many decimal places.</p>
	 *
	 * @param	value		the value to format into a string
	 * @return			the formatted string
	 */
	public static String toString(double value) {
		java.text.NumberFormat formatter = java.text.NumberFormat.getInstance();
		formatter.setGroupingUsed(false);
		String sValue=null;
		int numberOfIntegerDigits=(int)(Math.log(value)/Math.log(10))+1;
		int maximumFractionDigits=precisionToDisplayDouble-numberOfIntegerDigits;
		if (numberOfIntegerDigits > maximumIntegerDigits || maximumFractionDigits > maximumMaximumFractionDigits) {
			sValue=Double.toString(value);   // does scientific notation as required
		}
		else {
			if (maximumFractionDigits < 0) maximumFractionDigits=0;
			formatter.setMaximumFractionDigits(maximumFractionDigits);
			sValue=formatter.format(value);
		}
//System.err.println("FloatFormatter.toString(): value="+value+" numberOfIntegerDigits="+numberOfIntegerDigits+" maximumFractionDigits="+maximumFractionDigits+" sValue="+sValue);
		return sValue;
	}
	
	/**
	 * <p>Extract a specified number of delimited numeric values from a string into an array of doubles.</p>
	 *
	 * @param	s		the string containing delimited double values
	 * @param	wanted		the number of double values wanted
	 * @param	delimChar	the delimiter character
	 * @return			an array of doubles of the size wanted containing the values, else null
	 */
	public static final double[] fromString(String s,int wanted,char delimChar) {
		double[] values = new double[wanted];
		int count=0;
		try {
			int start=0;
			int delim=0;
			int l=s.length();
			while (count < wanted) {
				if (delim >= l || s.charAt(delim) == delimChar) {
					values[count++] = Double.parseDouble(s.substring(start,delim));
					++delim;
					start=delim;
					if (delim >= l) break;
				}
				else {
					++delim;
				}
			}
		}
		catch (NumberFormatException e) {
			e.printStackTrace(System.err);
			count=0;			// discard any intermediate results
		}
		return count != wanted ? null : values;
	}
	
	/**
	 * <p>Extract an arbitrary number of delimited numeric values from a string into an array of doubles.</p>
	 *
	 * @param	s		the string containing delimited double values
	 * @param	delimChar	the delimiter character
	 * @return			an array of doubles of the size wanted containing the values, else null
	 */
	public static final double[] fromString(String s,char delimChar) {
		// could do this more tidily with StringTokenizer :(
		ArrayList valueList = new ArrayList();
		int count=0;
		try {
			int start=0;
			int delim=0;
			int l=s.length();
			while (start < l) {
				if (delim >= l || s.charAt(delim) == delimChar) {
					valueList.add(new Double(Double.parseDouble(s.substring(start,delim))));
					++count;
					++delim;
					start=delim;
					if (delim >= l) break;
				}
				else {
					++delim;
				}
			}
		}
		catch (NumberFormatException e) {
			e.printStackTrace(System.err);
			count=0;			// discard any intermediate results
		}
		double[] values = null;
		if (count > 0) {
			values = new double[count];
			for (int i=0; i<count; ++i) {
				values[i]=((Double)valueList.get(i)).doubleValue();
			}
		}
		return values;
	}
}
