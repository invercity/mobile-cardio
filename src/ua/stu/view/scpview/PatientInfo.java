package ua.stu.view.scpview;

import ua.stu.view.fragments.BloodPressFragment;
import ua.stu.view.fragments.PrivatePatientInfoFragment;
import ua.stu.view.scpview.R;
import android.annotation.TargetApi;
import android.app.Activity;
import android.app.FragmentTransaction;
import android.os.Bundle;
import android.util.Log;
import android.widget.CheckBox;
import android.widget.CompoundButton;


@TargetApi(11) 
public class PatientInfo extends Activity implements android.widget.CompoundButton.OnCheckedChangeListener
{
	private static String TAG = "PatientInfo";
	
	private PrivatePatientInfoFragment privatePatientInfo;
	private BloodPressFragment bloodPress;
	private FragmentTransaction fTrans;
	
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
        
        init();
    }

	private final void init()
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
		
		privatePatientInfo = new PrivatePatientInfoFragment();
		bloodPress = new BloodPressFragment();
	}

	@Override
	public void onCheckedChanged(CompoundButton view, boolean checked) 
	{
		fTrans = getFragmentManager().beginTransaction();
		
		switch(view.getId()) {
        case R.id.check_private_patient_info:
            if (checked)
            {
            	fTrans.add(R.id.frame_private_patient_info, privatePatientInfo);
                Log.d(TAG, "private_patient_info");
            } else {
            	fTrans.remove(privatePatientInfo);
            	Log.d(TAG, "private_patient_info");
            }
            break;
		case R.id.check_blood_press:
	        if (checked)
	        {
	        	fTrans.add(R.id.frame_blood_press, bloodPress);
	            Log.d(TAG, "blood_press");
	        } else {
	        	fTrans.remove(bloodPress);
	        	Log.d(TAG, "blood_press");
	        }
	        break;
		case R.id.check_addr_patient:
	        if (checked)
	        {
	            Log.d(TAG, "addr_patient");
	        } else {
	        	Log.d(TAG, "addr_patient");
	        }
	        break;
		case R.id.check_diagnos_patient:
	        if (checked)
	        {
	            Log.d(TAG, "diagnos_patient");
	        } else {
	        	Log.d(TAG, "diagnos_patient");
	        }
	        break;
		case R.id.check_medical_history:
	        if (checked)
	        {
	            Log.d(TAG, "medical_history");
	        } else {
	        	Log.d(TAG, "medical_history");
	        }
	        break;
		}
		
		fTrans.commit();
	}
}
