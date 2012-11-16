package ua.stu.view.scpview;

import java.util.ArrayList;
import java.util.List;

import com.actionbarsherlock.app.SherlockActivity;

import ua.stu.view.adapter.SampleArrayAdapter;
import ua.stu.view.adapter.SamplePagerAdapter;
import ua.stu.view.fragments.ECGPanelFragment;
import ua.stu.view.fragments.InfoFragment;
import ua.stu.view.fragments.OtherInfo;
import ua.stu.view.fragments.PatientInfo;

import android.annotation.TargetApi;
import android.app.ActionBar;
import android.app.ActionBar.Tab;
import android.app.Activity;
import android.app.Fragment;
import android.app.FragmentTransaction;
import android.app.ListActivity;
import android.content.Intent;
import android.os.Bundle;
import android.preference.PreferenceActivity;
import android.support.v4.app.FragmentActivity;
import android.support.v4.view.ViewPager;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.LinearLayout;
import android.widget.SeekBar;
import android.widget.SeekBar.OnSeekBarChangeListener;
import android.widget.TextView;

//import com.viewpagerindicator.UnderlinePageIndicator;
@TargetApi(11)
public class SCPViewActivity extends Activity 
{
	private ECGPanelFragment ecgPanel;
	private InfoFragment info;
	
    /** Called when the activity is first created. */
    public void onCreate(Bundle savedInstanceState)
    {
    	setTheme(R.style.Theme_Sherlock);
        super.onCreate(savedInstanceState);
        
        ecgPanel = new ECGPanelFragment();
        info = new InfoFragment();
        
        LayoutInflater inflater = LayoutInflater.from(this);
        List<View> pages = new ArrayList<View>();
        
        View page = inflater.inflate(R.layout.main, null);
        LinearLayout linearLayout = (LinearLayout) page.findViewById(R.id.main_linear_layout);
        pages.add(page);
        
        page = ecgPanel.onCreateView(inflater,null,savedInstanceState);
        pages.add(page);
        
        page = info.onCreateView(inflater,null,savedInstanceState);
        pages.add(page);
        
        SamplePagerAdapter pagerAdapter = new SamplePagerAdapter(pages);
        ViewPager viewPager = new ViewPager(this);
        viewPager.setAdapter(pagerAdapter);
        viewPager.setCurrentItem(1);     
        
        setContentView(viewPager);
    }
}
