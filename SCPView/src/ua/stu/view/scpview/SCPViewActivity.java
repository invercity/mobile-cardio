package ua.stu.view.scpview;

import group.pals.android.lib.ui.filechooser.FileChooserActivity;
import group.pals.android.lib.ui.filechooser.io.localfile.LocalFile;
import group.pals.android.lib.ui.filechooser.services.IFileProvider;

import com.actionbarsherlock.view.Menu;
import com.actionbarsherlock.view.MenuItem;
import com.actionbarsherlock.app.SherlockFragmentActivity;

import java.util.ArrayList;
import java.util.Hashtable;
import java.util.List;

import ua.stu.scplib.data.DataHandler;
import ua.stu.scplib.tools.Loader;
import ua.stu.view.adapter.SamplePagerAdapter;
import ua.stu.view.fragments.ECGPanelFragment;
import ua.stu.view.fragments.InfoFragment;
import ua.stu.view.fragments.InfoFragment.OnEventItemClickListener;
import ua.stu.view.temporary.InfoO;
import ua.stu.view.temporary.InfoP;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.os.Parcelable;
import android.support.v4.view.ViewPager;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Toast;

public class SCPViewActivity extends SherlockFragmentActivity implements
		OnEventItemClickListener{
	
	SCPViewActivity v = this;
	private static final String TAG = "SCPViewActivity";
	private static final int REQUEST_CHOOSE_FILE = 0;
	private static final int REQUEST_SCAN_QRCODE = 1;
	public static final String SCAN = "la.droid.qr.scan";
	public static final String RESULT = "la.droid.qr.result";
	
	// this is test flag, allows to use QR code scanner
	private boolean SCANNER_ENABLED = false;
	
	private static final String ROOT_PATH = "/mnt/sdcard";

	private ECGPanelFragment ecgPanel;
	private InfoFragment info;
	private DataHandler h;
	/**
	 * Current page in ViewPager.
	 * <p>
	 * <b>Default</b> 0 - ECGPanel
	 */
	private int currentPage = 0;
	/**
	 * ECG file path
	 */
	private String filePath = "";

	private Bundle state;
	private ViewPager viewPager;
	private GraphicView graphicView;

	/** Called when the activity is first created. */
	@Override
	public void onCreate(Bundle savedInstanceState) {
		Log.d(TAG, "onCreate");
		setTheme(R.style.Theme_Sherlock);
		super.onCreate(savedInstanceState);

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
		if (state == null & filePath == "") {
			runActionDialog();
		}
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
			if (SCANNER_ENABLED) {
				if (!isOnline()) 
					Toast.makeText(SCPViewActivity.this, R.string.no_connection,Toast.LENGTH_SHORT).show();
				else runScanner();
			}
			else Toast.makeText(SCPViewActivity.this, R.string.not_avialable,Toast.LENGTH_SHORT).show();
		
			
		}	else if (item.getTitle().equals( titleFileOpen )){
			runFileChooser(R.style.Theme_Sherlock, ROOT_PATH);
		}
		
		return true;
	}
	
	@Override
	public void onStart() {
		super.onStart();
		Log.d(TAG, "onStart");

	}
//don't delete
/*	public String get_mime_by_filename(String filename) {
		String ext;
		String type;

		int lastdot = filename.lastIndexOf(".");
		if (lastdot > 0) {
			ext = filename.substring(lastdot + 1);
			MimeTypeMap mime = MimeTypeMap.getSingleton();
			type = mime.getMimeTypeFromExtension(ext);
			if (type != null) {
				return type;
			}
		}
		// return "application/octet-stream";
		return "no type";
	}*/

	@Override
	public void onResume() {
		super.onResume();
		try {
			h = new DataHandler(filePath);
		} catch (Exception e) {
			Log.e(TAG, e.toString());
		}

		graphicView.setH(h);
		ecgPanel = new ECGPanelFragment(graphicView);
		info = new InfoFragment();

		LayoutInflater inflater = LayoutInflater.from(this);
		List<View> pages = new ArrayList<View>();

		View page = ecgPanel.onCreateView(inflater, null, state);
		pages.add(page);

		page = info.onCreateView(inflater, null, state);
		pages.add(page);

		viewPager = createPager(pages);
		System.out.println(currentPage);
		viewPager.setCurrentItem(currentPage);

		Log.d(TAG, "onResume");
	}

	@Override
	public void onPause() {
		super.onPause();
		Log.d(TAG, "onPause");
	}

	@Override
	public void onStop() {
		super.onStop();
		Log.d(TAG, "onStop");
	}

	@Override
	public void onRestart() {
		super.onRestart();
		Log.d(TAG, "onRestart");
	}

	@Override
	protected void onSaveInstanceState(Bundle outState) {
		super.onSaveInstanceState(outState);

		String filePathKey = getResources().getString(R.string.app_file_path);
		String currentPageKey = getResources().getString(R.string.app_current_page);
		
		// save the current ecg file path
		outState.putString(filePathKey, filePath);
		// save the current page into viewPager
		outState.putInt(currentPageKey, viewPager.getCurrentItem());

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

	/**
	 * Method is handle which list item clicked on the InfoFragment
	 * 
	 * @param position
	 *            <p>
	 *            0 - item for patient information
	 *            </p>
	 *            <p>
	 *            1 - item for other information
	 *            </p>
	 */
	@Override
	public void itemClickEvent(int position) {
		switch (position) {
		case 0:
			Hashtable<String, InfoP> patientTable = new Hashtable<String, InfoP>();
			InfoP infoP = new InfoP(h.getPInfo().getAllPInfo());
			String patientKey = getResources().getString(R.string.app_patient);
			patientTable.put(patientKey, infoP);
			try {
				Intent intent = new Intent(this, PatientInfo.class);
				intent.putExtra(patientKey, patientTable);
				startActivity(intent);
			} catch (Exception e) {
				Log.e("Error in A ", e.toString());
			}
			break;
		case 1:
			Hashtable<String, InfoO> otherTable = new Hashtable<String, InfoO>();
			InfoO infoO = new InfoO(h.getOInfo().getAllOInfo());
			String otherKey = getResources().getString(R.string.app_other);
			otherTable.put(otherKey, infoO);
			try {
				Intent intent = new Intent(this, OtherInfo.class);
				intent.putExtra(otherKey, otherTable);
				startActivity(intent);
			} catch (Exception e) {
				Log.e("Error in A ", e.toString());
			}
			break;
		}
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
				@SuppressWarnings("unchecked")
				List<LocalFile> files = (List<LocalFile>) data
						.getSerializableExtra(FileChooserActivity._Results);

				filePath = files.get(0).getPath();
			}
			break;
		case REQUEST_SCAN_QRCODE:
			if (resultCode == RESULT_OK) {
			        // this part (Loader class) still not working on Android
					String result = data.getExtras().getString(RESULT);
					Loader l = new Loader();
					filePath = ROOT_PATH + l.load(result,ROOT_PATH);
			}
			break;
		}
		if (filePath.equals(""))
			runActionDialog();
	}
	
	public boolean isOnline() {
	    ConnectivityManager cm = (ConnectivityManager)getSystemService(Context.CONNECTIVITY_SERVICE);
	    NetworkInfo nInfo = cm.getActiveNetworkInfo();
	    if (nInfo != null && nInfo.isConnected()) {
	        Log.v("status", "ONLINE");
	        return true;
	    }
	    else {
	        Log.v("status", "OFFLINE");
	        return false;
	    }
	}
	

	public void runActionDialog() {
		AlertDialog.Builder builder = new AlertDialog.Builder(this);
	    builder.setTitle(R.string.choose_action).setItems(R.array.choose_action_array, new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int which) { 
            	switch (which) {
            	case 0: {
            		runFileChooser(R.style.Theme_Sherlock, ROOT_PATH);
            		break;
            	}
            	case 1: {
            		if (!isOnline()) {
        				Toast.makeText(SCPViewActivity.this, "Отсутствует интернет подключение", 
                        Toast.LENGTH_SHORT).show();
        			}
            		else runScanner();
            		break;
            	}
            	case 2: {
            		v.finish();
            		break;
            	}
            	}
            }
        });
	    AlertDialog dialog = builder.create();
	    dialog.show();
	}
	
	private final void runScanner() {
		Intent intent = new Intent(SCAN);
		startActivityForResult(intent, REQUEST_SCAN_QRCODE);
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

	private final ViewPager createPager(List<View> pages) {
		SamplePagerAdapter pagerAdapter = new SamplePagerAdapter(pages);
		ViewPager viewPager = new ViewPager(this);
		viewPager.setAdapter(pagerAdapter);
		viewPager.setCurrentItem(currentPage);
		setContentView(viewPager);
		return viewPager;
	}
}
