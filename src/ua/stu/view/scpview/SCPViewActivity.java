package ua.stu.view.scpview;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Hashtable;
import java.util.List;

import dalvik.annotation.TestTarget;
import dalvik.annotation.TestTargetClass;

import ua.stu.scplib.data.DataHandler;
import ua.stu.scplib.data.OInfo;
import ua.stu.scplib.data.PInfo;
import ua.stu.view.adapter.SamplePagerAdapter;
import ua.stu.view.fragments.ECGPanelFragment;
import ua.stu.view.fragments.InfoFragment;
import ua.stu.view.fragments.InfoFragment.OnEventItemClickListener;

import android.annotation.TargetApi;
import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;

public class SCPViewActivity extends FragmentActivity implements OnEventItemClickListener
{
	private ECGPanelFragment ecgPanel;
	private InfoFragment info;
	
	private static String TAG = "SCPViewActivity";
	
	private static String dataHandler = "DataHandler";
	private static String pInfo = "PInfo";
	private static String oInfo = "OInfo";
	public DataHandler data;
	
    /** Called when the activity is first created. */
	@Override
    public void onCreate(Bundle savedInstanceState)
    {
    	setTheme(R.style.Theme_Sherlock);
        super.onCreate(savedInstanceState);
        
        ecgPanel = new ECGPanelFragment();
        info = new InfoFragment();
        
        data = new DataHandler("/mnt/sdcard/Example.scp");
        
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
    }

	@Override
	public void itemClickEvent(int position) {
		switch (position)
		{
		case 0:
			//we can send only serialization or parceble object
			//(class which implements this interface)
			//http://www.coderanch.com/t/470615/Android/Mobile/Passing-object-activity
			Hashtable<String, String> tablePatient = new Hashtable<String, String>();
			String name = data.getPInfo().getFirstName();
			String s2 = data.getPInfo().getAge();
			String s3 = data.getPInfo().getDataOfBirth();
			String s4 = data.getPInfo().getLastName();
			tablePatient.put(dataHandler, name);
//			Bundle b = new Bundle();
//			b.putSerializable(pInfo, data);

			Intent intent = new Intent(this,PatientInfo.class).putExtra("table",tablePatient);
			startActivity(intent);
			break;
		case 1:
			Hashtable<String, OInfo> tableOther = new Hashtable<String, OInfo>();
			tableOther.put(dataHandler, data.getOInfo());
			
			intent = new Intent(this,OtherInfo.class).putExtra(oInfo, tableOther);
			startActivity(intent);
			break;
		}
	}
}
