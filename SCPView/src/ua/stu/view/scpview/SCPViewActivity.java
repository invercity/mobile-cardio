package ua.stu.view.scpview;

import group.pals.android.lib.ui.filechooser.FileChooserActivity;
import group.pals.android.lib.ui.filechooser.io.localfile.LocalFile;
import group.pals.android.lib.ui.filechooser.services.IFileProvider;

import java.util.Hashtable;
import java.util.List;

import ua.stu.scplib.data.DataHandler;
import ua.stu.scplib.tools.Loader;
import ua.stu.view.fragments.ECGPanelFragment;
import ua.stu.view.fragments.ECGPanelFragment.OnClickSliderContentListener;
import ua.stu.view.temporary.InfoO;
import ua.stu.view.temporary.InfoP;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.inputmethodservice.KeyboardView.OnKeyboardActionListener;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.os.Parcelable;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentTransaction;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.view.View.OnKeyListener;
import android.widget.Toast;

public class SCPViewActivity extends FragmentActivity implements OnClickSliderContentListener {
	
	private static final String TAG = "SCPViewActivity";
	
	private SCPViewActivity v = this;
	private static final int REQUEST_CHOOSE_FILE = 0;
	private static final int REQUEST_SCAN_QRCODE = 1;
	public 	static final String SCAN = "la.droid.qr.scan";
	public 	static final String RESULT = "la.droid.qr.result";
	
	/**
	 * this is test flag, allows to use QR code scanner
	 */
	private boolean SCANNER_ENABLED = false;
	
	private static final String ROOT_PATH = "/mnt/sdcard";

	private ECGPanelFragment ecgPanel;
	private DataHandler h;

	private String ecgFilePath = "";

	private Bundle state;
	private GraphicView graphicView;
	
	private boolean isSliderExpand = false;

	/** Called when the activity is first created. */
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		
        setContentView(R.layout.main);
        
		state 		= savedInstanceState;
		graphicView = new GraphicView(this);

		final android.content.Intent intent = getIntent();

		if (intent != null) {
			final android.net.Uri data = intent.getData();
			if (data != null) {
				ecgFilePath = data.getEncodedPath();
				// file loading comes here.
			} // if
		} // if
		if ( (state == null) && (ecgFilePath == "") ) {
			runActionDialog();
		}
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
			h = new DataHandler(ecgFilePath);
		} catch (Exception e) {
			Log.e(TAG, e.toString());
		}
		
		initECGPanel(h);
	}
	
	@Override
	public void onStop() {
		super.onStop();
		//when activity is restarting expandble slider must hide
		isSliderExpand = false;
	}
	
	@Override
	public void eventClickSliderContent(int resID) {
		switch ( resID ) {
		case R.id.slider_camera:
			if (SCANNER_ENABLED) {
				if (!isOnline()){
					Toast.makeText(SCPViewActivity.this, R.string.no_connection,Toast.LENGTH_SHORT).show();
				}
				else {
					runScanner();
				}
			}
			else { 
				Toast.makeText(SCPViewActivity.this, R.string.not_avialable,Toast.LENGTH_SHORT).show();
			}
			break;
		case R.id.slider_file_chooser:
			runFileChooser( R.style.Theme_Sherlock, ROOT_PATH );
			break;
		case R.id.slider_patient:
			transferPatientData();
			break;
		case R.id.slider_other:
			transferOtherData();
			break;
		case R.id.slider_ecg_revert:
			ecgPanel.revertECG( ecgPanel.getView() );
			break;
		}	
	}
	
	@Override
	public boolean onKeyUp(int keyCode, KeyEvent event)  {
		Log.d(TAG,"Click "+keyCode);
		if ( keyCode == KeyEvent.KEYCODE_MENU ){
			expandSliderPanel();
		}

	    return super.onKeyUp(keyCode, event);
	}
	
	private final void expandSliderPanel(){
		if ( isSliderExpand ){
			ecgPanel.getSliderPanel().animateClose();
			isSliderExpand = false;	
		}
		else {
			ecgPanel.getSliderPanel().animateOpen();
			isSliderExpand = true;
		}
	}
	
	private final void initECGPanel( DataHandler h ){
		graphicView.setH( h );
		ecgPanel = new ECGPanelFragment( graphicView );
		
		FragmentTransaction ft = getSupportFragmentManager().beginTransaction();
        ft.replace( R.id.ecg_panel_fragment, ecgPanel );
        ft.commit();
	}
	
	@Override
	protected void onSaveInstanceState(Bundle outState) {
		super.onSaveInstanceState(outState);
		// save the current ecg file path
		String filePathKey 		= getResources().getString(R.string.app_file_path);
		outState.putString(filePathKey, ecgFilePath);
	}

	@Override
	protected void onRestoreInstanceState(Bundle savedInstanceState) {
		super.onRestoreInstanceState(savedInstanceState);
		// restore the current ecg file path and status of expandble slider
		String filePathKey 	= getResources().getString(R.string.app_file_path);
		ecgFilePath 		= savedInstanceState.getString(filePathKey);
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

				ecgFilePath = files.get(0).getPath();
			}
			break;
		case REQUEST_SCAN_QRCODE:
			if (resultCode == RESULT_OK) {
			        // this part (Loader class) still not working on Android
					String result = data.getExtras().getString(RESULT);
					Loader l = new Loader();
					ecgFilePath = ROOT_PATH + l.load(result,ROOT_PATH);
			}
			break;
		}

		if ( ecgFilePath.equals("") )
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
            		if (SCANNER_ENABLED) {
            			if (!isOnline()) 
            				Toast.makeText(SCPViewActivity.this, R.string.no_connection,Toast.LENGTH_SHORT).show();
            			else runScanner();
            		}
            		else Toast.makeText(SCPViewActivity.this, R.string.not_avialable,Toast.LENGTH_SHORT).show();
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
	
	private final void transferPatientData(){
		Hashtable<String, InfoP> patientTable = new Hashtable<String, InfoP>();
		InfoP infoP = new InfoP(h.getPInfo().getAllPInfo());
		String patientKey = getResources().getString(R.string.app_patient);
		patientTable.put(patientKey, infoP);
		try {
			Intent intent = new Intent(getApplicationContext(), PatientInfo.class);
			intent.putExtra(patientKey, patientTable);
			startActivity(intent);
		} catch (Exception e) {
			Log.e("Error in ", e.toString());
		}
	}
	
	private final void transferOtherData(){		
		Hashtable<String, InfoO> otherTable = new Hashtable<String, InfoO>();
		InfoO infoO = new InfoO(h.getOInfo().getAllOInfo());
		String otherKey = getResources().getString(R.string.app_other);
		otherTable.put(otherKey, infoO);
		try {
			Intent intent = new Intent( getApplicationContext() , OtherInfo.class );
			intent.putExtra( otherKey , otherTable );
			startActivity(intent);
		} catch (Exception e) {
			Log.e("Error in ", e.toString());
		}
	}
}
