package ua.stu.view.scpview;

import java.util.HashMap;

import ua.stu.view.fragments.AddrPatientFragment;
import ua.stu.view.fragments.BloodPressFragment;
import ua.stu.view.fragments.DiagnoseFragment;
import ua.stu.view.fragments.MedicalHistoryFragment;
import ua.stu.view.fragments.PrivatePatientInfoFragment;
import ua.stu.view.scpview.R;
import ua.stu.view.temporary.InfoP;
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
	
	private FragmentTransaction fTrans;
	
	private CheckBox chPrivatePatientInfo;
	private CheckBox chBloodPress;
	private CheckBox chAddrPatient;
	private CheckBox chDiagnosPatient;
	private CheckBox chMedicalHistory;
	
	private String patientKey;//maybe, out of constant class
	
	@Override
	public void onCreate(Bundle savedInstanceState)
    {
		setTheme(R.style.Theme_Sherlock);
		patientKey = getResources().getString(R.string.app_patient);
		
        super.onCreate(savedInstanceState);
        setContentView(R.layout.patientinfo);
        
        HashMap table = (HashMap) getIntent().getSerializableExtra(patientKey);
        InfoP infoP = (InfoP)table.get(patientKey);
        init(infoP);
    }

	private final void init(InfoP infoP)
	{
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
            	if (!isFragmentInStack(R.id.frame_private_patient_info)){fTrans.add(R.id.frame_private_patient_info, privatePatientInfo);}
            	fTrans.show(privatePatientInfo);
            } else {
            	fTrans.hide(privatePatientInfo);
            }
            break;
		case R.id.check_blood_press:
	        if (checked)
	        {
	        	if (!isFragmentInStack(R.id.frame_blood_press)){fTrans.add(R.id.frame_blood_press, bloodPress);}
            	fTrans.show(bloodPress);
	        } else {
	        	fTrans.hide(bloodPress);
	        }
	        break;
		case R.id.check_addr_patient:
	        if (checked)
	        {
	        	if (!isFragmentInStack(R.id.frame_addr_patient)){fTrans.add(R.id.frame_addr_patient, addrPatient);}
            	fTrans.show(addrPatient);
	        } else {
	        	fTrans.hide(addrPatient);
	        }
	        break;
		case R.id.check_diagnos_patient:
	        if (checked)
	        {
	        	if (!isFragmentInStack(R.id.frame_diagnos_patient)){fTrans.add(R.id.frame_diagnos_patient, diagnose);}
            	fTrans.show(diagnose);
	        } else {
	        	fTrans.hide(diagnose);
	        }
	        break;
		case R.id.check_medical_history:
	        if (checked)
	        {
	        	if (!isFragmentInStack(R.id.frame_medical_history)){fTrans.add(R.id.frame_medical_history, medicalHistory);}
            	fTrans.show(medicalHistory);
	        } else {
	        	fTrans.hide(medicalHistory);
	        }
	        break;
		}
		
		fTrans.commit();
	}
	
	private final boolean isFragmentInStack(int id)
	{
		if (this.getSupportFragmentManager().findFragmentById(id) == null)return false;
		else return true;
	}
}
