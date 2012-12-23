package ua.stu.view.scpview;

import java.util.List;

import com.actionbarsherlock.app.SherlockFragmentActivity;
import com.actionbarsherlock.view.*;

import group.pals.android.lib.ui.filechooser.FileChooserActivity;
import group.pals.android.lib.ui.filechooser.io.localfile.LocalFile;
import group.pals.android.lib.ui.filechooser.services.IFileProvider;
import ua.stu.scplib.data.DataHandler;
import ua.stu.scpview.R;
import ua.stu.view.fragments.DeviceForECGFragment;
import ua.stu.view.fragments.DiagnoseFragment;
import ua.stu.view.fragments.ECGPanelFragment;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.Parcelable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.util.Log;

public class SCPViewActivity extends SherlockFragmentActivity {

	private static final String TAG = "SCPViewActivity";
	private static final int REQUEST_CHOOSE_FILE = 0;
	private static final String ROOT_PATH = "/mnt/sdcard";
	
	private DataHandler h;
	
	private int currentPage = 0;
	
	/**
	 * ECG file path
	 */
	private String filePath;

	private Bundle state;
	private GraphicView graphicView;
	
    /**
     * The {@link android.support.v4.view.PagerAdapter} that will provide fragments for each of the
     * sections. We use a {@link android.support.v4.app.FragmentPagerAdapter} derivative, which will
     * keep every loaded fragment in memory. If this becomes too memory intensive, it may be best
     * to switch to a {@link android.support.v4.app.FragmentStatePagerAdapter}.
     */
    private TitlePagerAdapter mTitlePagerAdapter;

    /**
     * The {@link ViewPager} that will host the section contents.
     */
    private ViewPager mViewPager;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        
        setContentView(R.layout.activity_scpview);
        
        state = savedInstanceState;
		graphicView = new GraphicView(this);
		final android.content.Intent intent = getIntent();
		
		if (intent != null) {
			final android.net.Uri data = intent.getData();
			if (data != null) {
				filePath = data.getEncodedPath();
				// file loading comes here.
			} // if
		} // if
		if (state == null && filePath == null) {
			runFileChooser(R.style.Theme_Sherlock, ROOT_PATH);
		}
        
        // Create the adapter that will return a fragment for each of the three primary sections
        // of the app.
        mTitlePagerAdapter = new TitlePagerAdapter(getSupportFragmentManager(),this);

        // Set up the ViewPager with the sections adapter.
        mViewPager = (ViewPager) findViewById(R.id.pager);
        mViewPager.setAdapter(mTitlePagerAdapter);

    }

    @Override
	public void onResume() {
		super.onResume();

		try {
			h = new DataHandler(filePath);
		} catch (Exception e) {
			Log.e(TAG, e.toString());
		}

		graphicView.setH(h);

		Log.d(TAG, "onResume");
	}
    
    @Override
	protected void onSaveInstanceState(Bundle outState) {
		super.onSaveInstanceState(outState);

		String filePathKey = getResources().getString(R.string.app_file_path);
		String currentPageKey = getResources().getString(R.string.app_current_page);
		
		// save the current ecg file path
		outState.putString(filePathKey, filePath);
		// save the current page into viewPager
		outState.putInt(currentPageKey, mViewPager.getCurrentItem());

		Log.d(TAG, "onSaveInstanceState");
	}

	@Override
	protected void onRestoreInstanceState(Bundle savedInstanceState) {
		super.onRestoreInstanceState(savedInstanceState);

		String filePathKey = getResources().getString(R.string.app_file_path);
		String currentPageKey = getResources().getString(R.string.app_current_page);
		
		// restore the current ecg file path
		filePath = savedInstanceState.getString(filePathKey);
		// restore the current page into viewPager
		currentPage = savedInstanceState.getInt(currentPageKey);

		Log.d(TAG, "onRestoreInstanceState");
	}
    
	@Override
    public boolean onCreateOptionsMenu(Menu menu) {
    	String titleFileOpen = getResources().getString(R.string.msg_open_file);
		String titleCamera = getResources().getString(R.string.msg_camera);
		
		menu.add(titleFileOpen)
        	.setIcon(R.drawable.file_chooser)
         	.setShowAsAction(MenuItem.SHOW_AS_ACTION_ALWAYS);
		
		menu.add(titleCamera)
    		.setIcon(R.drawable.camera)
    		.setShowAsAction(MenuItem.SHOW_AS_ACTION_ALWAYS);
        return true;
    }
    
    @Override
	public boolean onOptionsItemSelected (MenuItem item)
	{
		String titleFileOpen = getResources().getString(R.string.msg_open_file);
		String titleCamera = getResources().getString(R.string.msg_camera);
				
		if (item.getTitle().equals( titleCamera ))
		{
			
		}	else if (item.getTitle().equals( titleFileOpen )){
			runFileChooser(R.style.Theme_Sherlock, ROOT_PATH);
		}
		
		return true;
	}

    private final void runFileChooser(int style, String rootPath) {
		Intent intent = new Intent(getBaseContext(), FileChooserActivity.class);
		/*
		 * by default, if not specified, default rootpath is sdcard, if sdcard
		 * is not available, "/" will be used
		 */
		intent.putExtra(FileChooserActivity._Theme, style);
		intent.putExtra(FileChooserActivity._Rootpath,
				(Parcelable) new LocalFile(rootPath));
		startActivityForResult(intent, REQUEST_CHOOSE_FILE);
	}
    
    @Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		switch (requestCode) {
		case REQUEST_CHOOSE_FILE:
			if (resultCode == RESULT_OK) {
				/*
				 * you can use two flags included in data
				 */
				IFileProvider.FilterMode filterMode = (IFileProvider.FilterMode) data
						.getSerializableExtra(FileChooserActivity._FilterMode);
				boolean saveDialog = data.getBooleanExtra(
						FileChooserActivity._SaveDialog, false);

				/*
				 * a list of files will always return, if selection mode is
				 * single, the list contains one file
				 */
				List<LocalFile> files = (List<LocalFile>) data
						.getSerializableExtra(FileChooserActivity._Results);

				filePath = files.get(0).getPath();
			}
			break;
		}
	}
    
    
    
    
    public class TitlePagerAdapter extends FragmentPagerAdapter {

    	private Context context;
    	
    	public TitlePagerAdapter(FragmentManager fm, Context _context) {
            super(fm);
            context = _context;
        }
    	
    	@Override
    	public Fragment getItem(int position) {
    		Fragment fragment = new Fragment();
    		switch(position)
    		{
    		case 0:
    			fragment = new ECGPanelFragment(graphicView);
    			break;
    		case 1:
    			fragment = new DeviceForECGFragment();
    			break;
    		case 2:
    			fragment = new DiagnoseFragment();
    			break;
    		}
    		
            return fragment;
    	}

    	@Override
    	public int getCount() {
    		// TODO Auto-generated method stub
    		return 3;
    	}
    	
    	@Override
        public CharSequence getPageTitle(int position) {
            switch (position) {
                case 0: return context.getString(R.string.title_section_ecg).toUpperCase();
                case 1: return context.getString(R.string.title_section_patient).toUpperCase();
                case 2: return context.getString(R.string.title_section_other).toUpperCase();
            }
            return null;
        }

    }
}
