package ua.stu.view.scpview;


import group.pals.android.lib.ui.filechooser.FileChooserActivity;
import group.pals.android.lib.ui.filechooser.io.localfile.LocalFile;
import group.pals.android.lib.ui.filechooser.services.IFileProvider;

import java.util.ArrayList;
import java.util.Hashtable;
import java.util.List;

import com.actionbarsherlock.app.SherlockActivity;

import ua.stu.scplib.data.DataHandler;
import ua.stu.view.adapter.SamplePagerAdapter;
import ua.stu.view.fragments.ECGPanelFragment;
import ua.stu.view.fragments.FileChooserFragment;
import ua.stu.view.fragments.FileChooserFragment.OnEventImageButtonClickListener;
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

public class SCPViewActivity extends Activity implements OnEventItemClickListener,OnEventImageButtonClickListener
{
	private static final String TAG = "SCPViewActivity";
	private static final int REQUEST_CHOOSE_FILE = 0;
	private static final String ROOT_PATH = "/mnt/sdcard";
	
	private ECGPanelFragment ecgPanel;
	private InfoFragment info;
	private FileChooserFragment fileChooser;
	private DataHandler h;
	/**
	 * Key for sending data to activity PatientInfo
	 */
	private String patientKey;
	/**
	 * Key for sending data to activity OtherInfo
	 */
	private String otherKey;
	/**
	 * Key for save file state
	 */
	private String filePathKey;
	/**
	 * ECG file path
	 */
	private String filePath;

	private Bundle state;
	private ViewPager viewPager;
	private GraphicView graphicView;
    /** Called when the activity is first created. */
	@Override
    public void onCreate(Bundle savedInstanceState)
    {
		setTheme(R.style.Theme_Sherlock);
        super.onCreate(savedInstanceState);
        
        filePathKey = getResources().getString(R.string.app_file_path);
        state = savedInstanceState;
		if (state == null){
        	runFileChooser(R.style.Theme_Sherlock,ROOT_PATH);
        }
		graphicView=new GraphicView(this);
    }
	@Override
	public void onStart()
	{
		super.onStart();
		Log.d(TAG,"onStart");
	}
	
	@Override
	public void onResume()
	{
		super.onResume();
		
		try {
			h = new DataHandler(filePath);
		}catch(Exception e){
		  	Log.e(TAG, e.toString());
		}
		graphicView.setH(h);
		ecgPanel = new ECGPanelFragment(graphicView);
		info = new InfoFragment();
		fileChooser = new FileChooserFragment();
		
		LayoutInflater inflater = LayoutInflater.from(this);
		List<View> pages = new ArrayList<View>();
		
		View page = fileChooser.onCreateView(inflater,null,state);
		pages.add(page);
		  
		page = ecgPanel.onCreateView(inflater,null,state);
		pages.add(page);
		  
		page = info.onCreateView(inflater,null,state);
		pages.add(page);
		      
		viewPager = createPager(pages);
		
		Log.d(TAG,"onResume");
	}
	
	@Override
	public void onPause()
	{
		super.onPause();
		Log.d(TAG,"onPause");
	}
	
	@Override
	public void onStop()
	{
		super.onStop();
		Log.d(TAG,"onStop");
	}
	
	@Override
	public void onRestart()
	{
		super.onRestart();
		Log.d(TAG,"onRestart");
	}
	@Override
	protected void onSaveInstanceState(Bundle outState) 
	{
	    super.onSaveInstanceState(outState);
	    outState.putString(filePathKey, filePath);
	    Log.d(TAG, "onSaveInstanceState");
	}
	@Override
	protected void onRestoreInstanceState(Bundle savedInstanceState) 
	{
	    super.onRestoreInstanceState(savedInstanceState);
	    filePath = savedInstanceState.getString(filePathKey);
	    Log.d(TAG, "onRestoreInstanceState");
	}
	
	/**
	 * Method is handle which list item clicked on the InfoFragment
	 * @param position
	 * <p>0 - item for patient information</p>
	 * <p>1 - item for other information</p>
	 */
	@Override
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
				Log.e("Error in A " , e.toString());
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
				Log.e("Error in A " , e.toString());
			}
			break;
		}
	}
	
	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data){
	    switch (requestCode)
	    {
	    case REQUEST_CHOOSE_FILE:
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
	        }
	        break;
	    }
	}

	@Override
	public void imageButtonClickEvent(int resId) {
		switch (resId) {
		case R.id.file_chooser:
			Log.d(TAG,"file chooser");
			runFileChooser(R.style.Theme_Sherlock,ROOT_PATH);
			break;
		case R.id.camera:
			Log.d(TAG,"camera");
			break;
		}
	}
	
	private final void runFileChooser(int style,String rootPath)
	{
		Intent intent = new Intent(getBaseContext(), FileChooserActivity.class);
		/*
		 * by default, if not specified, default rootpath is sdcard,
		 * if sdcard is not available, "/" will be used
		 */
		intent.putExtra(FileChooserActivity._Theme, style);
		intent.putExtra(FileChooserActivity._Rootpath, (Parcelable) new LocalFile(rootPath));
		startActivityForResult(intent, REQUEST_CHOOSE_FILE);
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
