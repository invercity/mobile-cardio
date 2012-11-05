package ua.stu.view.scpview;

import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import android.annotation.TargetApi;
import android.app.Activity;
import android.os.Bundle;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;

public class SCPViewActivity extends Activity
{
	private PatientInfo patientInfo;
	private OtherInfo otherInfo;
	
	private static String test = "/mnt/sdcard/Example.scp";
	
    /** Called when the activity is first created. */
    @TargetApi(11) @Override
    public void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        
        patientInfo = new PatientInfo();
        otherInfo = new OtherInfo();
        
        LayoutInflater inflater = LayoutInflater.from(this);
        List<View> pages = new ArrayList<View>();
        
        View page = inflater.inflate(R.layout.main, null);
        pages.add(page);
        
        page = patientInfo.onCreateView(inflater,null,savedInstanceState);
        pages.add(page);
        
        page = otherInfo.onCreateView(inflater,null,savedInstanceState);
        pages.add(page);
        
        SamplePagerAdapter pagerAdapter = new SamplePagerAdapter(pages);
        ViewPager viewPager = new ViewPager(this);
        viewPager.setAdapter(pagerAdapter);
        viewPager.setCurrentItem(0);     
        
        setContentView(viewPager);
        
        /*Temporary code*/
        
//        SCPECG scpecg = read(test);
//        String lastName=scpecg.getNamedField("LastName");
//        String id=scpecg.getNamedField("PatientIdentificationNumber");
//        String db=scpecg.getNamedField("DateOfBirth");
//        String race=scpecg.getNamedField("Race");
//        String depnum=scpecg.getNamedField("departmentNumber");
//        String devid=scpecg.getNamedField("deviceID");
//        String devtype=scpecg.getNamedField("deviceType");
//        String time=scpecg.getNamedField("TimeOfAcquisition");
//        
//        patientInfo.setLastName(lastName);
//        patientInfo.setIdPatient(id);
//        patientInfo.setBithDate(db);
//        patientInfo.setRace(race);
//        
//        otherInfo.setDepNum(depnum);
//        otherInfo.setIdDev(devid);
//        otherInfo.setTypeDev(devtype);
//        otherInfo.setTimeECG(time);
        
        /*********/
    }
    
//    private SCPECG read (String file)
//    {
//    	BinaryInputStream i = null;
//		SCPECG scpecg=null;
//		
//		try {
//			i = new BinaryInputStream(new BufferedInputStream(new FileInputStream(new File(file))),false);
//		} catch (FileNotFoundException e) {
//			// TODO Auto-generated catch block
//			e.printStackTrace();
//		}
//  		       		
//		try {
//			scpecg = new SCPECG(i,false);
//		} catch (IOException e) {
//			// TODO Auto-generated catch block
//			e.printStackTrace();
//		}
//		
//		return scpecg;
//    }
}
