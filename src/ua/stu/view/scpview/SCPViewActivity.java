package ua.stu.view.scpview;

import java.util.ArrayList;
import java.util.List;

import ua.stu.scplib.graphic.ECGPanel;

import android.annotation.TargetApi;
import android.app.ActionBar;
import android.app.ActionBar.Tab;
import android.app.Activity;
import android.app.Fragment;
import android.app.FragmentTransaction;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.view.ViewPager;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.LinearLayout;

//import com.viewpagerindicator.UnderlinePageIndicator;
public class SCPViewActivity extends Activity
{
	
	private PatientInfo patientInfo;
	private FragmentTransaction fTrans;
	
    /** Called when the activity is first created. */
    @TargetApi(11) @Override
    public void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main);
        
        patientInfo = new PatientInfo();
        
        LayoutInflater inflater = LayoutInflater.from(this);
        List<View> pages = new ArrayList<View>();
        
        View page = inflater.inflate(R.layout.main, null);
        LinearLayout linearLayout = (LinearLayout) page.findViewById(R.id.main_linear_layout);
        pages.add(page);
        
        page = patientInfo.onCreateView(inflater,null,savedInstanceState);
        pages.add(page);
        
        SamplePagerAdapter pagerAdapter = new SamplePagerAdapter(pages);
        ViewPager viewPager = new ViewPager(this);
        viewPager.setAdapter(pagerAdapter);
        viewPager.setCurrentItem(0);     
        
        setContentView(viewPager);
        
        Log.d("Main Tag", "Oncreate");
    }
}
