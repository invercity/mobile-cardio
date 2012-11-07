package ua.stu.scplib.data;

import java.io.BufferedInputStream;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;

import ua.stu.scplib.attribute.BinaryInputStream;
import ua.stu.scplib.graphic.SCPSourceECG;
import ua.stu.scplib.graphic.SourceECG;

public class DataHandler {
	
	SourceECG sourceECG = null;
	int nTilesPerColumn = 0;
	int nTilesPerRow = 0;
	float widthOfTileInMilliSeconds = 0;
	float heightOfTileInMilliVolts = 0;
	float timeOffsetInMilliSeconds = 0;
	// image params
	float horizontalPixelsPerMilliSecond = 0;
	float milliMetresPerPixel = 0;
	float verticalPixelsPerMilliVolt = 0;
	// i don't know, what does it mean
	boolean truederiveAdditionalLeads = false;
	boolean fillBackGroundFirst = false;
	
	/**
	 * @param	#1 SCPECG data filename,
	 *			#2 number of tiles per column
	 *			#3 the number of tiles per row,
	 *			#4 time offset in milliseconds for the left edge of the display
	 * @throws FileNotFoundException 
	 */
	DataHandler(String filename) throws FileNotFoundException {
				sourceECG = null;
				BinaryInputStream i = null;
				i = new BinaryInputStream(new BufferedInputStream(new FileInputStream(filename)),false);		// little endian
				
				try {
					sourceECG = new SCPSourceECG(i,truederiveAdditionalLeads);
				} catch (IOException e) {e.printStackTrace();}
				
				// assume screen is 72 dpi aka 72/25.4 pixels/mm
				milliMetresPerPixel = (float)(25.4/72);

				// ECG's normally printed at 25mm/sec and 10 mm/mV
				horizontalPixelsPerMilliSecond = 25/(1000*milliMetresPerPixel);
				
				float verticalPixelsPerMilliVolt = 10/(milliMetresPerPixel);
	
				Object[] panelParams = {
					sourceECG.getSamples(),
					sourceECG.getNumberOfChannels(),
					sourceECG.getNumberOfSamplesPerChannel(),
					sourceECG.getChannelNames(),
					nTilesPerColumn,nTilesPerRow,
					sourceECG.getSamplingIntervalInMilliSeconds(),
					sourceECG.getAmplitudeScalingFactorInMilliVolts(),
					horizontalPixelsPerMilliSecond,verticalPixelsPerMilliVolt,
					timeOffsetInMilliSeconds,
					sourceECG.getDisplaySequence(),
					800,400,fillBackGroundFirst
				};
	}
}
