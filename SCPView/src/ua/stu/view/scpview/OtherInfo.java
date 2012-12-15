package ua.stu.view.scpview;

import java.util.HashMap;

import com.actionbarsherlock.app.SherlockActivity;
import com.actionbarsherlock.app.SherlockFragment;
import com.actionbarsherlock.app.SherlockFragmentActivity;
import com.actionbarsherlock.app.SherlockListActivity;

import ua.stu.view.fragments.DeviceForECGFragment;
import ua.stu.view.fragments.ECGInfoFragment;
import ua.stu.view.scpview.R;
import ua.stu.view.temporary.InfoO;
import android.app.Activity;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentTransaction;
import android.util.Log;
import android.widget.CheckBox;
import android.widget.CompoundButton;

public class OtherInfo extends FragmentActivity implements android.widget.CompoundButton.OnCheckedChangeListener
{
	private static String TAG = "OtherInfo";
	
	private DeviceForECGFragment device;
	private ECGInfoFragment ecgInfo;
	private InfoO infoO;
	
	private FragmentTransaction fTrans;
	private CheckBox chDeviceForECG;
	private CheckBox chECGInfo;
	
	@Override
	public void onCreate(Bundle savedInstanceState)
    {
		setTheme(R.style.Theme_Sherlock);
		
        super.onCreate(savedInstanceState);
        setContentView(R.layout.otherinfo);

        init(infoO);
    }
	
	@Override
	public void onStop(){
		super.onStop();
		Log.d(TAG,"onStop");

		fTrans = getSupportFragmentManager().beginTransaction();

		if (isFragmentInStack(R.id.frame_dev_take_ecg)) fTrans.hide(device);
		if (isFragmentInStack(R.id.frame_ecg_info)) fTrans.hide(ecgInfo);

		fTrans.commitAllowingStateLoss();
	}
	
	private final void init(InfoO infoO)
	{
		String otherKey = getResources().getString(R.string.app_other);
		HashMap table = (HashMap) getIntent().getSerializableExtra(otherKey);
	    infoO = (InfoO)table.get(otherKey);
		
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

	private final boolean isFragmentInStack(int id)
	{
		if (this.getFragmentManager().findFragmentById(id) == null)return false;
		else return true;
	}
	
}
