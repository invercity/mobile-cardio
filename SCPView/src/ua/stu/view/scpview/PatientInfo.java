package ua.stu.view.scpview;

import java.util.HashMap;

import com.actionbarsherlock.app.SherlockActivity;

import ua.stu.view.fragments.AddrPatientFragment;
import ua.stu.view.fragments.BloodPressFragment;
import ua.stu.view.fragments.DiagnoseFragment;
import ua.stu.view.fragments.MedicalHistoryFragment;
import ua.stu.view.fragments.PrivatePatientInfoFragment;
import ua.stu.view.scpview.R;
import ua.stu.view.temporary.InfoP;
import android.app.Activity;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentTransaction;
import android.util.Log;
import android.widget.CheckBox;
import android.widget.CompoundButton;


public class PatientInfo extends FragmentActivity implements android.widget.CompoundButton.OnCheckedChangeListener
{
	private static String TAG = "PatientInfo";
	
	private PrivatePatientInfoFragment privatePatientInfo;
	private BloodPressFragment bloodPress;
	private AddrPatientFragment addrPatient;
	private DiagnoseFragment diagnose;
	private MedicalHistoryFragment medicalHistory;
	private InfoP infoP = null;
	
	private FragmentTransaction fTrans = null;
	
	private CheckBox chPrivatePatientInfo;
	private CheckBox chBloodPress;
	private CheckBox chAddrPatient;
	private CheckBox chDiagnosPatient;
	private CheckBox chMedicalHistory;
	
	@Override
	public void onCreate(Bundle savedInstanceState)
    {
		setTheme(R.style.Theme_Sherlock);
		
        super.onCreate(savedInstanceState);
        setContentView(R.layout.patientinfo);
        
        init(infoP);
    }

	@Override
	public void onStop(){
		super.onStop();
		Log.d(TAG,"onStop");

		fTrans = getSupportFragmentManager().beginTransaction();

		if (isFragmentInStack(R.id.frame_private_patient_info)) fTrans.hide(privatePatientInfo);
		if (isFragmentInStack(R.id.frame_blood_press)) fTrans.hide(bloodPress);
		if (isFragmentInStack(R.id.frame_addr_patient)) fTrans.hide(addrPatient);
		if (isFragmentInStack(R.id.frame_diagnos_patient)) fTrans.hide(diagnose);
		if (isFragmentInStack(R.id.frame_medical_history)) fTrans.hide(medicalHistory);

		fTrans.commitAllowingStateLoss();
	}
	
	private final void init(InfoP infoP)
	{
		String patientKey = getResources().getString(R.string.app_patient);
		HashMap table = (HashMap) getIntent().getSerializableExtra(patientKey);
	    infoP = (InfoP)table.get(patientKey);
		
		chPrivatePatientInfo = (CheckBox)findViewById(R.id.check_private_patient_info);
		chBloodPress = (CheckBox)findViewById(R.id.check_blood_press);
		chAddrPatient = (CheckBox)findViewById(R.id.check_addr_patient);
		chDiagnosPatient = (CheckBox)findViewById(R.id.check_diagnos_patient);
		chMedicalHistory = (CheckBox)findViewById(R.id.check_medical_history);
	    
		chPrivatePatientInfo.setOnCheckedChangeListener(this);
		chBloodPress.setOnCheckedChangeListener(this);
		chAddrPatient.setOnCheckedChangeListener(this);
		chDiagnosPatient.setOnCheckedChangeListener(this);
		chMedicalHistory.setOnCheckedChangeListener(this);
		
		privatePatientInfo = new PrivatePatientInfoFragment(infoP);
		bloodPress = new BloodPressFragment(infoP);
		addrPatient = new AddrPatientFragment(infoP);
		diagnose = new DiagnoseFragment(infoP);
		medicalHistory = new MedicalHistoryFragment(infoP);
	}

	public void onCheckedChanged(CompoundButton view, boolean checked) 
	{
		fTrans = getSupportFragmentManager().beginTransaction();
		
		switch(view.getId()) {
        case R.id.check_private_patient_info:
            if (checked)
            {
            	fTrans.add(R.id.frame_private_patient_info, privatePatientInfo);
            } else {
            	fTrans.remove(privatePatientInfo);
            }
            break;
		case R.id.check_blood_press:
	        if (checked)
	        {
	        	fTrans.add(R.id.frame_blood_press, bloodPress);
	        } else {
	        	fTrans.remove(bloodPress);
	        }
	        break;
		case R.id.check_addr_patient:
	        if (checked)
	        {
	        	fTrans.add(R.id.frame_addr_patient, addrPatient);
	        } else {
	        	fTrans.remove(addrPatient);
	        }
	        break;
		case R.id.check_diagnos_patient:
	        if (checked)
	        {
	        	fTrans.add(R.id.frame_diagnos_patient, diagnose);
	        } else {
	        	fTrans.remove(diagnose);
	        }
	        break;
		case R.id.check_medical_history:
	        if (checked)
	        {
	        	fTrans.add(R.id.frame_medical_history, medicalHistory);
	        } else {
	       	fTrans.remove(medicalHistory);
	        }
	        break;
		}
        fTrans.commit();
	}
	
	private final boolean isFragmentInStack(int id)
	{
		if (this.getFragmentManager().findFragmentById(id) == null)return false;
		else return true;
	}
}
