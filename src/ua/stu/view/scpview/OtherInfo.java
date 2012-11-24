package ua.stu.view.scpview;

import java.util.HashMap;

import ua.stu.view.fragments.DeviceForECGFragment;
import ua.stu.view.fragments.ECGInfoFragment;
import ua.stu.view.scpview.R;
import ua.stu.view.temporary.InfoO;
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
	
	private String otherKey;//maybe, out of constant class
	
	@Override
	public void onCreate(Bundle savedInstanceState)
    {
		setTheme(R.style.Theme_Sherlock);
		otherKey = getResources().getString(R.string.app_other);
		
        super.onCreate(savedInstanceState);
        setContentView(R.layout.otherinfo);
        
        HashMap table = (HashMap) getIntent().getSerializableExtra(otherKey);
        InfoO infoO = (InfoO)table.get(otherKey);
        init(infoO);
    }
	
	private final void init(InfoO infoO)
	{
		chDeviceForECG = (CheckBox)findViewById(R.id.check_dev_take_ecg);
		chECGInfo = (CheckBox)findViewById(R.id.check_ecg_info);
		
		chDeviceForECG.setOnCheckedChangeListener(this);
		chECGInfo.setOnCheckedChangeListener(this);
		
		device = new DeviceForECGFragment(infoO);
		ecgInfo = new ECGInfoFragment(infoO);
	}
	
	public void onCheckedChanged(CompoundButton view, boolean checked)
	{
		fTrans = getSupportFragmentManager().beginTransaction();
		
		switch(view.getId()) {
        case R.id.check_dev_take_ecg:
            if (checked)
            {
            	if (!isFragmentInStack(R.id.frame_dev_take_ecg)){fTrans.add(R.id.frame_dev_take_ecg, device);}
            	fTrans.show(device);
            } else {
            	fTrans.hide(device);
            }
            break;
		case R.id.check_ecg_info:
	        if (checked)
	        {
	        	if (!isFragmentInStack(R.id.frame_ecg_info)){fTrans.add(R.id.frame_ecg_info, ecgInfo);}
            	fTrans.show(ecgInfo);
	        } else {
	        	fTrans.hide(ecgInfo);
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
