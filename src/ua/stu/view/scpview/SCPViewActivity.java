package ua.stu.view.scpview;

import java.util.ArrayList;
import java.util.List;

import ua.stu.view.adapter.SamplePagerAdapter;
import ua.stu.view.fragments.ECGPanelFragment;
import ua.stu.view.fragments.InfoFragment;
import ua.stu.view.fragments.InfoFragment.OnEventItemClickListener;
import ua.stu.view.fragments.OtherInfo;

import android.annotation.TargetApi;
import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;

@TargetApi(11)
public class SCPViewActivity extends Activity implements OnEventItemClickListener
{
	private ECGPanelFragment ecgPanel;
	private InfoFragment info;
	
	private static String TAG = "SCPViewActivity";
	
    /** Called when the activity is first created. */
	@Override
    public void onCreate(Bundle savedInstanceState)
    {
    	setTheme(R.style.Theme_Sherlock);
        super.onCreate(savedInstanceState);
        
        ecgPanel = new ECGPanelFragment();
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
    }

	@Override
	public void itemClickEvent(int position) {
		// TODO Auto-generated method stub
		switch (position)
		{
		case 0:
			Intent intent = new Intent(this,PatientInfo.class);
			startActivity(intent);
			break;
		case 1:
			intent = new Intent(this,OtherInfo.class);
			startActivity(intent);
			break;
		}
	}
}
