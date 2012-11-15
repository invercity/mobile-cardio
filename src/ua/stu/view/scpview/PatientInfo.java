package ua.stu.view.scpview;


import android.annotation.TargetApi;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentTransaction;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.Button;

@TargetApi(11)
public class PatientInfo extends FragmentActivity {
	
	@Override
	public void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
    }
	
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
		      Bundle savedInstanceState) 
	{
		View v = inflater.inflate(R.layout.patientinfo, null);
		
		return v;
	}

}
