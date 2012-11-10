package ua.stu.view.scpview;

import java.util.ArrayList;
import java.util.List;

import ua.stu.view.fragments.ECGPanelFragment;


import android.annotation.TargetApi;
import android.app.ActionBar;
import android.app.ActionBar.Tab;
import android.app.Activity;
import android.app.Fragment;
import android.app.FragmentTransaction;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.support.v4.view.ViewPager;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.SeekBar;
import android.widget.SeekBar.OnSeekBarChangeListener;
import android.widget.TextView;

//import com.viewpagerindicator.UnderlinePageIndicator;
public class SCPViewActivity extends Activity
{
	
	private PatientInfo patientInfo;
	private ECGPanelFragment ecgPanel;
	
    /** Called when the activity is first created. */
    @TargetApi(11) @Override
    public void onCreate(Bundle savedInstanceState)
    {
    	setTheme(R.style.Theme_Sherlock);
        super.onCreate(savedInstanceState);
        
        patientInfo = new PatientInfo();
        ecgPanel = new ECGPanelFragment();
        
        LayoutInflater inflater = LayoutInflater.from(this);
        List<View> pages = new ArrayList<View>();
        
        View page = inflater.inflate(R.layout.main, null);
        LinearLayout linearLayout = (LinearLayout) page.findViewById(R.id.main_linear_layout);
        pages.add(page);
        
        page = ecgPanel.onCreateView(inflater,null,savedInstanceState);
        pages.add(page);
        
        page = patientInfo.onCreateView(inflater,null,savedInstanceState);
        pages.add(page);
        
        SamplePagerAdapter pagerAdapter = new SamplePagerAdapter(pages);
        ViewPager viewPager = new ViewPager(this);
        viewPager.setAdapter(pagerAdapter);
        viewPager.setCurrentItem(0);     
        
        setContentView(viewPager);
    }
}
