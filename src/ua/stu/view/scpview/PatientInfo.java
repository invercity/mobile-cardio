package ua.stu.view.scpview;

import ua.stu.view.scpview.R;
import android.annotation.TargetApi;
import android.app.Activity;
import android.os.Bundle;


@TargetApi(11) 
public class PatientInfo extends Activity
{
	private static String TAG = "PatientInfo";
	
	@Override
	public void onCreate(Bundle savedInstanceState)
    {
		setTheme(R.style.Theme_Sherlock);
        super.onCreate(savedInstanceState);
        
        setContentView(R.layout.patientinfo);
    }
}
