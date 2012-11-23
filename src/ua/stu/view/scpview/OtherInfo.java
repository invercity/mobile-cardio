package ua.stu.view.scpview;

import ua.stu.view.fragments.DeviceForECGFragment;
import ua.stu.view.fragments.ECGInfoFragment;
import ua.stu.view.scpview.R;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentTransaction;
import android.widget.CheckBox;
import android.widget.CompoundButton;

public class OtherInfo extends FragmentActivity implements android.widget.CompoundButton.OnCheckedChangeListener
{
	private static String TAG = "OtherInfo";
	
	private DeviceForECGFragment device;
	private ECGInfoFragment ecgInfo;
	
	private FragmentTransaction fTrans;
	
	private CheckBox chDeviceForECG;
	private CheckBox chECGInfo;
	
	@Override
	public void onCreate(Bundle savedInstanceState)
    {
		setTheme(R.style.Theme_Sherlock);
		
        super.onCreate(savedInstanceState);
        setContentView(R.layout.otherinfo);
        
        init();
    }
	
	private final void init()
	{
		chDeviceForECG = (CheckBox)findViewById(R.id.check_dev_take_ecg);
		chECGInfo = (CheckBox)findViewById(R.id.check_ecg_info);
		
		chDeviceForECG.setOnCheckedChangeListener(this);
		chECGInfo.setOnCheckedChangeListener(this);
		
		device = new DeviceForECGFragment();
		ecgInfo = new ECGInfoFragment();
	}
	
	public void onCheckedChanged(CompoundButton view, boolean checked)
	{
		fTrans = getSupportFragmentManager().beginTransaction();
		
		switch(view.getId()) {
        case R.id.check_dev_take_ecg:
            if (checked)
            {
            	fTrans.add(R.id.frame_dev_take_ecg, device);
            } else {
            	fTrans.remove(device);
            }
            break;
		case R.id.check_ecg_info:
	        if (checked)
	        {
	        	fTrans.add(R.id.frame_ecg_info, ecgInfo);
	        } else {
	        	fTrans.remove(ecgInfo);
	        }
	        break;
		}
		
		fTrans.commit();
	}


	
}
