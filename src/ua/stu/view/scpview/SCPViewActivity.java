package ua.stu.view.scpview;

import java.util.ArrayList;
import java.util.Hashtable;
import java.util.List;

import ua.stu.scplib.data.DataHandler;
import ua.stu.scplib.data.OInfo;
import ua.stu.scplib.data.PInfo;
import ua.stu.view.adapter.SamplePagerAdapter;
import ua.stu.view.fragments.ECGPanelFragment;
import ua.stu.view.fragments.InfoFragment;
import ua.stu.view.fragments.InfoFragment.OnEventItemClickListener;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.view.ViewPager;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;

public class SCPViewActivity extends Activity implements OnEventItemClickListener
{
	private ECGPanelFragment ecgPanel;
	private InfoFragment info;
	private DataHandler h;
	
	private String patientKey;//maybe, out of constant class
	private String otherKey;//maybe, out of constant class

	private static String TAG = "SCPViewActivity";
	
    /** Called when the activity is first created. */
	@Override
    public void onCreate(Bundle savedInstanceState)
    {
    	setTheme(R.style.Theme_Sherlock);
        super.onCreate(savedInstanceState);

        patientKey = getResources().getString(R.string.app_patient);
        otherKey = getResources().getString(R.string.app_other);
        
        h = new DataHandler("/mnt/sdcard/Example.scp");
        ecgPanel = new ECGPanelFragment(h);
        info = new InfoFragment();
        
        LayoutInflater inflater = LayoutInflater.from(this);
        List<View> pages = new ArrayList<View>();
        
        View page = inflater.inflate(R.layout.main, null);
        pages.add(page);
      
        page = ecgPanel.onCreateView(inflater,null,savedInstanceState);
        pages.add(page);
        
        page = info.onCreateView(inflater,null,savedInstanceState);
        pages.add(page);
        
        SamplePagerAdapter pagerAdapter = new SamplePagerAdapter(pages);
        ViewPager viewPager = new ViewPager(this);
        viewPager.setAdapter(pagerAdapter);
        viewPager.setCurrentItem(2);     
        
        setContentView(viewPager);
       ///
        
       
       // GraphicView gv =(GraphicView)findViewById(R.id.ecgpanel);
       // gv.setH(h);
        ///
    }

	@Override
	public void itemClickEvent(int position) {
		switch (position)
		{
		case 0:
			Hashtable<String, PInfo> patientTable = new Hashtable<String,PInfo>();
			patientTable.put(patientKey,h.getPInfo());
			try {
				Intent intent = new Intent(this,PatientInfo.class);
				startActivity(intent);
			}catch(Exception e){
				Log.i("Error in A " , e.toString());
			} 
			break;
		case 1:
			Hashtable<String, OInfo> otherTable = new Hashtable<String,OInfo>();
			otherTable.put(otherKey, h.getOInfo());
			try {
				Intent intent = new Intent(this,OtherInfo.class);
				startActivity(intent);
			}catch(Exception e){
				Log.i("Error in A " , e.toString());
			}
			break;
		}
	}
}
