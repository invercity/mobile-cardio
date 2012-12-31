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
import android.app.ActionBar.LayoutParams;
import android.app.ActionBar.Tab;
import android.app.ActionBar.TabListener;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.util.Log;
import android.view.Gravity;
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

        Log.d(TAG,"onCreate");
        
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
		
		setupCustomView();
		
		setupTabs();
		
		//don't show title
		flipOption(ActionBar.DISPLAY_SHOW_TITLE,false);
		//don't show home icon
		flipOption(ActionBar.DISPLAY_SHOW_HOME,false);

	}
	
	private void setupCustomView() {
        getLeftNavBar().setCustomView(R.layout.custom_view);
        LayoutParams params = new LayoutParams(0);
        params.width = params.height = nextDimension(0);
        params.gravity = nextGravity(nextGravity(0, true), false);
        applyCustomParams(params);
    }
	
	private void applyCustomParams(LayoutParams params) {
        ActionBar bar = getLeftNavBar();
        bar.setCustomView(bar.getCustomView(), params);
    }
	
	private static int nextDimension(int dimension) {
		switch (dimension) {
		case 40:
			return 100;
		case 100:
			return LayoutParams.WRAP_CONTENT;
		case LayoutParams.FILL_PARENT:
		default:
			return 40;
		}
    }
	
	private static int nextGravity(int gravity, boolean horizontal) {
        int hGravity = gravity & Gravity.HORIZONTAL_GRAVITY_MASK;
        int vGravity = gravity & Gravity.VERTICAL_GRAVITY_MASK;
		if (horizontal) {
			switch (hGravity) {
			case Gravity.LEFT:
				hGravity = Gravity.CENTER_HORIZONTAL;
				break;
			case Gravity.CENTER_HORIZONTAL:
				hGravity = Gravity.RIGHT;
				break;
			case Gravity.RIGHT:
			default:
				hGravity = Gravity.LEFT;
				break;
			}
		} else {
			switch (vGravity) {
			case Gravity.TOP:
				vGravity = Gravity.CENTER_VERTICAL;
				break;
			case Gravity.CENTER_VERTICAL:
				vGravity = Gravity.BOTTOM;
				break;
			case Gravity.BOTTOM:
			default:
				vGravity = Gravity.TOP;
				break;
			}
		}
        return hGravity | vGravity;
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
        ActionBar bar = getLeftNavBar();
        bar.removeAllTabs();
        
        String deviceTag = getResources().getString(R.string.title_device_ecg);
        String ecgInfoTag = getResources().getString(R.string.title_ecg_info);
        
        ActionBar.Tab tab = bar.newTab().setText(R.string.title_device_ecg).setIcon(R.drawable.device)
        		.setTag(deviceTag)
                .setTabListener(this);
        bar.addTab(bar.newTab().setText(R.string.title_ecg_info).setIcon(R.drawable.tab_a)
        		.setTag(ecgInfoTag)
        		.setTabListener(this));

        bar.addTab(tab, 0, true);
    }
	
	@Override
	public void onResume(){
		super.onStop();
		Log.d(TAG,"onStop");
	}
	
	private final void init(InfoO infoO)
	{
		String otherKey = getResources().getString(R.string.app_other);
		HashMap table = (HashMap) getIntent().getSerializableExtra(otherKey);
	    infoO = (InfoO)table.get(otherKey);
		
		device = new DeviceForECGFragment(infoO);
		ecgInfo = new ECGInfoFragment(infoO);
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
		String deviceTag = getResources().getString(R.string.title_device_ecg);
        String ecgInfoTag = getResources().getString(R.string.title_ecg_info);
        
		if ( tab.getTag().equals(deviceTag) ){
			if (!isFragmentInStack(R.id.frame_device_info)) {
				Log.d(TAG,"onTabSelected add device");
				ft.add(R.id.frame_device_info, device);
			}
			else {
				Log.d(TAG,"onTabSelected show device");
				ft.show(device);
			}
		}	else if ( tab.getTag().equals(ecgInfoTag) ){
			if (!isFragmentInStack(R.id.frame_ecg_info)){
				Log.d(TAG,"onTabSelected add ecgInfo");
				ft.add(R.id.frame_ecg_info, ecgInfo);
			}
			else {
				Log.d(TAG,"onTabSelected show ecgInfo");
				ft.show(ecgInfo);
			}
		}

		Log.d(TAG,"onTabSelected");
	}

	@Override
	public void onTabUnselected(Tab tab, android.app.FragmentTransaction ft) {
		String deviceTag = getResources().getString(R.string.title_device_ecg);
        String ecgInfoTag = getResources().getString(R.string.title_ecg_info);

        if ( tab.getTag().equals(deviceTag) ){
        	ft.hide(device);
		}	else if ( tab.getTag().equals(ecgInfoTag) ){
			ft.hide(ecgInfo);
		}
	}	
}
