package ua.stu.view.scpview;

import java.util.HashMap;

import com.example.google.tv.leftnavbar.LeftNavBar;
import com.example.google.tv.leftnavbar.LeftNavBarService;

import ua.stu.view.fragments.DeviceForECGFragment;
import ua.stu.view.fragments.ECGInfoFragment;
import ua.stu.view.scpview.R;
import ua.stu.view.temporary.InfoO;
import android.app.ActionBar;
import android.app.Activity;
import android.app.ActionBar.Tab;
import android.app.ActionBar.TabListener;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.view.Window;

public class OtherInfo extends FragmentActivity implements TabListener
{
	private static String TAG = "OtherInfo";
	
	private LeftNavBar mLeftNavBar;
	
	private DeviceForECGFragment device;
	private ECGInfoFragment ecgInfo;
	private InfoO infoO;
	
	@Override
	public void onCreate(Bundle savedInstanceState)
    {
		this.requestWindowFeature(Window.FEATURE_NO_TITLE);
        super.onCreate(savedInstanceState);
        
		(LeftNavBarService.instance()).getLeftNavBar((Activity) this);
		setContentView(R.layout.otherinfo);
		
		init(infoO);

		// prepare the left navigation bar
		setupBar();
    }
	
	private void setupBar() {

		ActionBar bar = getLeftNavBar();
		bar.setBackgroundDrawable(getResources().getDrawable(
				R.drawable.leftnav_bar_background_dark));

		// no navigation
		bar.setNavigationMode(ActionBar.NAVIGATION_MODE_TABS);
		bar.setBackgroundDrawable(new ColorDrawable(Color.DKGRAY));
		
		setupTabs();
		
		//don't show title
		flipOption(ActionBar.DISPLAY_SHOW_TITLE,false);
		//don't show home icon
		flipOption(ActionBar.DISPLAY_SHOW_HOME,false);

	}
	
	private LeftNavBar getLeftNavBar() {
		if (mLeftNavBar == null) {
			mLeftNavBar = new LeftNavBar(this);
		}
		return mLeftNavBar;
	}
	
	private void flipOption(int option,boolean isShow) {
		ActionBar bar = getLeftNavBar();
		bar.setDisplayOptions(isShow ? option : 0, option);
	}
	
	private void setupTabs() {
        LeftNavBar bar = getLeftNavBar();
        bar.showOptionsMenu(false);
        
        bar.removeAllTabs();
        
        String deviceTag 	= getResources().getString(R.string.title_device_ecg);
        String ecgInfoTag 	= getResources().getString(R.string.title_ecg_info);
        
        ActionBar.Tab tab = bar.newTab().setText(R.string.title_device_ecg).setIcon(R.drawable.device)
        		.setTag(deviceTag)
                .setTabListener(this);
        bar.addTab(bar.newTab().setText(R.string.title_ecg_info).setIcon(R.drawable.ecginfo)
        		.setTag(ecgInfoTag)
        		.setTabListener(this));

        bar.addTab(tab, 0, true);
    }
	
	private final void init(InfoO infoO)
	{
		String otherKey 	= getResources().getString(R.string.app_other);
		HashMap table 		= (HashMap) getIntent().getSerializableExtra(otherKey);
	    infoO 				= (InfoO)table.get(otherKey);
		
		device 				= new DeviceForECGFragment(infoO);
		ecgInfo 			= new ECGInfoFragment(infoO);
	}

	private final boolean isFragmentInStack(int id)
	{
		if (getFragmentManager().findFragmentById(id) == null)return false;
		else return true;
	}

	@Override
	public void onTabReselected(Tab tab, android.app.FragmentTransaction ft) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void onTabSelected(Tab tab, android.app.FragmentTransaction ft) {
		String deviceTag 	= getResources().getString(R.string.title_device_ecg);
        String ecgInfoTag 	= getResources().getString(R.string.title_ecg_info);
        
		if ( tab.getTag().equals(deviceTag) ){
			if (!isFragmentInStack(R.id.frame_device_info)) {
				ft.add(R.id.frame_device_info, device);
			}
			else {
				ft.show(device);
			}
		}	else if ( tab.getTag().equals(ecgInfoTag) ){
			if (!isFragmentInStack(R.id.frame_ecg_info)){
				ft.add(R.id.frame_ecg_info, ecgInfo);
			}
			else {
				ft.show(ecgInfo);
			}
		}
	}

	@Override
	public void onTabUnselected(Tab tab, android.app.FragmentTransaction ft) {
		String deviceTag 	= getResources().getString(R.string.title_device_ecg);
        String ecgInfoTag 	= getResources().getString(R.string.title_ecg_info);

        if ( tab.getTag().equals(deviceTag) ){
        	ft.hide(device);
		}	else if ( tab.getTag().equals(ecgInfoTag) ){
			ft.hide(ecgInfo);
		}
	}	
}
