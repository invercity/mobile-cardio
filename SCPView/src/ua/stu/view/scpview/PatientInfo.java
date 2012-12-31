package ua.stu.view.scpview;

import java.util.HashMap;

import com.example.google.tv.leftnavbar.LeftNavBar;
import com.example.google.tv.leftnavbar.LeftNavBarService;

import ua.stu.view.fragments.AddrPatientFragment;
import ua.stu.view.fragments.BloodPressFragment;
import ua.stu.view.fragments.DiagnoseFragment;
import ua.stu.view.fragments.MedicalHistoryFragment;
import ua.stu.view.fragments.PrivatePatientInfoFragment;
import ua.stu.view.scpview.R;
import ua.stu.view.temporary.InfoP;
import android.app.ActionBar;
import android.app.ActionBar.Tab;
import android.app.ActionBar.TabListener;
import android.app.Activity;
import android.app.ActionBar.LayoutParams;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.view.Gravity;
import android.view.Window;


public class PatientInfo extends FragmentActivity implements TabListener
{
	private static String TAG = "PatientInfo";
	
	private LeftNavBar mLeftNavBar;
	
	private PrivatePatientInfoFragment privatePatientInfo;
	private BloodPressFragment bloodPress;
	private AddrPatientFragment addrPatient;
	private DiagnoseFragment diagnose;
	private MedicalHistoryFragment medicalHistory;
	private InfoP infoP;
	
	@Override
	public void onCreate(Bundle savedInstanceState)
    {
		this.requestWindowFeature(Window.FEATURE_NO_TITLE);
        super.onCreate(savedInstanceState);
        
		(LeftNavBarService.instance()).getLeftNavBar((Activity) this);
		setContentView(R.layout.patientinfo);

		init(infoP);
		
		// prepare the left navigation bar
		setupBar();
        
    }
	
	private final void init(InfoP infoP)
	{
		String patientKey 	= getResources().getString(R.string.app_patient);
		HashMap table 		= (HashMap) getIntent().getSerializableExtra(patientKey);
	    infoP 				= (InfoP)table.get(patientKey);
		
		privatePatientInfo 	= new PrivatePatientInfoFragment(infoP);
		bloodPress 			= new BloodPressFragment(infoP);
		addrPatient 		= new AddrPatientFragment(infoP);
		diagnose 			= new DiagnoseFragment(infoP);
		medicalHistory 		= new MedicalHistoryFragment(infoP);
	}

	private void setupBar() {

		ActionBar bar = getLeftNavBar();
		bar.setBackgroundDrawable(getResources().getDrawable(
				R.drawable.leftnav_bar_background_dark));

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
        params.width 	= params.height = nextDimension(0);
        params.gravity 	= nextGravity(nextGravity(0, true), false);
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
        
        String privateTag 	= getResources().getString(R.string.title_private_patient);
        String pressureTag 	= getResources().getString(R.string.title_pressure_patient);
        String addressTag 	= getResources().getString(R.string.title_address_patient);
        String diagnosisTag = getResources().getString(R.string.title_diagnosis_patient);
        String historyTag 	= getResources().getString(R.string.title_history_patient);
        
        ActionBar.Tab tab = bar.newTab().setText(R.string.title_private_patient).setIcon(R.drawable.tab_a)
        		.setTag(privateTag)
                .setTabListener(this);
        bar.addTab(bar.newTab().setText(R.string.title_pressure_patient).setIcon(R.drawable.tab_a)
        		.setTag(pressureTag)
        		.setTabListener(this));
        bar.addTab(bar.newTab().setText(R.string.title_address_patient).setIcon(R.drawable.tab_a)
        		.setTag(addressTag)
        		.setTabListener(this));
        bar.addTab(bar.newTab().setText(R.string.title_diagnosis_patient).setIcon(R.drawable.tab_a)
        		.setTag(diagnosisTag)
        		.setTabListener(this));
        bar.addTab(bar.newTab().setText(R.string.title_history_patient).setIcon(R.drawable.tab_a)
        		.setTag(historyTag)
        		.setTabListener(this));

        bar.addTab(tab, 0, true);
    }
	
	private final boolean isFragmentInStack(int id)
	{
		if ( this.getFragmentManager().findFragmentById(id) == null )return false;
		else return true;
	}

	@Override
	public void onTabReselected(Tab tab, android.app.FragmentTransaction ft) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void onTabSelected(Tab tab, android.app.FragmentTransaction ft) {
		String privateTag 	= getResources().getString(R.string.title_private_patient);
        String pressureTag 	= getResources().getString(R.string.title_pressure_patient);
        String addressTag 	= getResources().getString(R.string.title_address_patient);
        String diagnosisTag = getResources().getString(R.string.title_diagnosis_patient);
        String historyTag 	= getResources().getString(R.string.title_history_patient);
        
		if ( tab.getTag().equals(privateTag) ){
			if (!isFragmentInStack( R.id.frame_private_patient_info )) {
				ft.add( R.id.frame_private_patient_info, privatePatientInfo );
			}
			else {
				ft.show( privatePatientInfo );
			}
		}	else if ( tab.getTag().equals(pressureTag) ){
			if (!isFragmentInStack(R.id.frame_blood_press)){
				ft.add( R.id.frame_blood_press, bloodPress );
			}
			else {
				ft.show( bloodPress );
			}
		}	else if ( tab.getTag().equals(addressTag) ){
			if (!isFragmentInStack( R.id.frame_addr_patient )){
				ft.add( R.id.frame_addr_patient, addrPatient );
			}
			else {
				ft.show( addrPatient );
			}
		}	else if ( tab.getTag().equals(diagnosisTag) ){
			if (!isFragmentInStack( R.id.frame_diagnos_patient )){
				ft.add( R.id.frame_diagnos_patient, diagnose );
			}
			else {
				ft.show( diagnose );
			}
		}	else if ( tab.getTag().equals(historyTag) ){
			if (!isFragmentInStack( R.id.frame_medical_history )){
				ft.add( R.id.frame_medical_history, medicalHistory );
			}
			else {
				ft.show( medicalHistory );
			}
		}
		
	}

	@Override
	public void onTabUnselected(Tab tab, android.app.FragmentTransaction ft) {
		String privateTag 	= getResources().getString(R.string.title_private_patient);
        String pressureTag 	= getResources().getString(R.string.title_pressure_patient);
        String addressTag 	= getResources().getString(R.string.title_address_patient);
        String diagnosisTag = getResources().getString(R.string.title_diagnosis_patient);
        String historyTag 	= getResources().getString(R.string.title_history_patient);

        if ( tab.getTag().equals(privateTag) ){
			ft.hide	( privatePatientInfo );
		}	else if ( tab.getTag().equals(pressureTag) ){
			ft.hide	( bloodPress );
		}	else if ( tab.getTag().equals(addressTag) ){
			ft.hide	( addrPatient );
		}	else if ( tab.getTag().equals(diagnosisTag) ){
			ft.hide	( diagnose );
		}	else if ( tab.getTag().equals(historyTag) ){
			ft.hide	( medicalHistory );
		}
		
	}
}
