package ua.stu.view.scpview;

import group.pals.android.lib.ui.filechooser.FileChooserActivity;
import group.pals.android.lib.ui.filechooser.io.localfile.LocalFile;
import group.pals.android.lib.ui.filechooser.services.IFileProvider;

import java.util.ArrayList;
import java.util.Hashtable;
import java.util.List;

import ua.stu.scplib.data.DataHandler;
import ua.stu.view.adapter.SamplePagerAdapter;
import ua.stu.view.fragments.ECGPanelFragment;
import ua.stu.view.fragments.InfoFragment;
import ua.stu.view.fragments.InfoFragment.OnEventItemClickListener;
import ua.stu.view.temporary.InfoO;
import ua.stu.view.temporary.InfoP;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.os.Parcelable;
import android.support.v4.view.ViewPager;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;

public class SCPViewActivity extends Activity implements OnEventItemClickListener
{
	private static final String TAG = "SCPViewActivity";
	private static final int RequestChooseFile = 0;
	
	private ECGPanelFragment ecgPanel;
	private InfoFragment info;
	private DataHandler h;
	
	private String patientKey;//maybe, out of constant class
	private String otherKey;//maybe, out of constant class
	private String filePath;

	private Bundle state;
	private ViewPager viewPager;
	
    /** Called when the activity is first created. */
	@Override
    public void onCreate(Bundle savedInstanceState)
    {
    	setTheme(R.style.Theme_Sherlock);
        super.onCreate(savedInstanceState);
        state = savedInstanceState;
        
        Intent intent = new Intent(getBaseContext(), FileChooserActivity.class);
		/*
		 * by default, if not specified, default rootpath is sdcard,
		 * if sdcard is not available, "/" will be used
		 */
		intent.putExtra(FileChooserActivity._Theme, R.style.Theme_Sherlock);
		intent.putExtra(FileChooserActivity._Rootpath, (Parcelable) new LocalFile("/mnt/sdcard"));
		startActivityForResult(intent, RequestChooseFile);  
    }
	/**
	 * Method is handle which list item clicked on the InfoFragment
	 * @param position
	 * <p>0 - item for patient information</p>
	 * <p>1 - item for other information</p>
	 */
	public void itemClickEvent(int position) {
		switch (position)
		{
		case 0:
			Hashtable<String, InfoP> patientTable = new Hashtable<String,InfoP>();
			InfoP infoP = new InfoP(h.getPInfo().getAllPInfo());
			patientKey = getResources().getString(R.string.app_patient);
			patientTable.put(patientKey,infoP);
			try {
				Intent intent = new Intent(this,PatientInfo.class);
				intent.putExtra(patientKey,patientTable);
				startActivity(intent);
			}catch(Exception e){
				Log.i("Error in A " , e.toString());
			} 
			break;
		case 1:
			Hashtable<String, InfoO> otherTable = new Hashtable<String,InfoO>();
			InfoO infoO = new InfoO(h.getOInfo().getAllOInfo());
			otherKey = getResources().getString(R.string.app_other);
			otherTable.put(otherKey, infoO);
			try {
				Intent intent = new Intent(this,OtherInfo.class);
				intent.putExtra(otherKey,otherTable);
				startActivity(intent);
			}catch(Exception e){
				Log.i("Error in A " , e.toString());
			}
			break;
		}
	}
	
	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
	    switch (requestCode) {
	    case RequestChooseFile:
	        if (resultCode == RESULT_OK) {
	            /*
	             * you can use two flags included in data
	             */
	            IFileProvider.FilterMode filterMode = (IFileProvider.FilterMode)
	                data.getSerializableExtra(FileChooserActivity._FilterMode);
	            boolean saveDialog = data.getBooleanExtra(FileChooserActivity._SaveDialog, false);

	            /*
	             * a list of files will always return,
	             * if selection mode is single, the list contains one file
	             */
	            List<LocalFile> files = (List<LocalFile>)
	                data.getSerializableExtra(FileChooserActivity._Results);
	            
	            filePath = files.get(0).getPath();
	            
	            try {
		            h = new DataHandler(filePath);
	            }catch(Exception e){
	            	Log.i(TAG, e.toString());
	            }
		        ecgPanel = new ECGPanelFragment(h);
	            info = new InfoFragment();
           
	            LayoutInflater inflater = LayoutInflater.from(this);
	            List<View> pages = new ArrayList<View>();
	          
	            View page = ecgPanel.onCreateView(inflater,null,state);
	            pages.add(page);
	            
	            page = info.onCreateView(inflater,null,state);
	            pages.add(page);
	            
	            viewPager = createPager(pages);
	            
	        }
	        break;
	    }
	}
	
	private final ViewPager createPager(List<View> pages)
	{
		SamplePagerAdapter pagerAdapter = new SamplePagerAdapter(pages);
        ViewPager viewPager = new ViewPager(this);
        viewPager.setAdapter(pagerAdapter);
        viewPager.setCurrentItem(0);     
        
        setContentView(viewPager);
        
        return viewPager;
	}
}
