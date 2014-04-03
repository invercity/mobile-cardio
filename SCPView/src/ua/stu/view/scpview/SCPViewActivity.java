package ua.stu.view.scpview;

import group.pals.android.lib.ui.filechooser.FileChooserActivity;
import group.pals.android.lib.ui.filechooser.io.localfile.LocalFile;
import group.pals.android.lib.ui.filechooser.services.IFileProvider;

import java.util.Hashtable;
import java.util.List;

import com.google.zxing.client.android.ZXingConstants;
import com.google.zxing.client.android.decode.ZXingDecoderActivity;

import ua.stu.scplib.data.DataHandler;
import ua.stu.view.fragments.ECGPanelFragment;
import ua.stu.view.fragments.ECGPanelFragment.OnClickSliderContentListener;
import ua.stu.view.temporary.InfoO;
import ua.stu.view.temporary.InfoP;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.os.Parcelable;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentTransaction;
import android.text.TextUtils;
import android.util.Log;
import android.view.KeyEvent;
import android.webkit.MimeTypeMap;
import android.widget.Toast;

public class SCPViewActivity extends FragmentActivity implements OnClickSliderContentListener {
	
	private static final String TAG = "SCPViewActivity";
	
	private SCPViewActivity _this = this;
	public static final int REQUEST_CHOOSE_FILE = 0;
	private static final int REQUEST_SCAN_QRCODE = 1;
	private static final int REQUEST_DECODE_QR = 2;
	private static final int REQUEST_GET_FILE = 3;
	public 	static final String SCAN = "la.droid.qr.scan";
	public static final String DECODE = "la.droid.qr.decode";
	public static final String IMG = "la.droid.qr.image";
	public 	static final String RESULT = "la.droid.qr.result";
	public static final String URL = "ua.stu.view.URL";
	public static final String FILE = "ua.stu.view.file";
	public static final String ROOT = "ua.stu.view.root";
	public static final String PREFS_NAME = "ScpViewFile";
	public static final String ROOT_PATH = "/mnt/sdcard";

	private ECGPanelFragment ecgPanel;
	private DataHandler h;

	private String ecgFilePath = "";
	
	android.content.SharedPreferences settings ;
	private Bundle state;
	// choose action dialog 
	private AlertDialog dialog;
	private int touchMode = GestureListener.MODE_BASIC;
	
	private boolean isSliderExpand = false;
	
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
        setContentView(R.layout.main);
		state = savedInstanceState;
		// get settings
		settings = getSharedPreferences(getResources().getString( R.string.app_settings_file ), MODE_PRIVATE);
		final android.content.Intent intent = getIntent();

		if ((intent != null) && (intent.getData() != null)) {
			final android.net.Uri data = intent.getData();
			String f = data.getEncodedPath();
			// this is image
			if (mimeType(f).indexOf("image") != -1) {
				Intent decoder = new Intent(this, ZXingDecoderActivity.class);
				decoder.putExtra(ZXingConstants.RESPONSE_DECODE_IMAGE, f);
				startActivityForResult(decoder, SCPViewActivity.REQUEST_DECODE_QR);
			}
			else ecgFilePath = f;
		} // if
		else if (state == null) {
			//runActionDialog();
			// check app mode
			if (settings.getBoolean(getResources().getString(R.string.settings_mode_qrcode), false)) runScanner();
			else runFileChooser(R.style.Theme_Sherlock, ROOT_PATH, IFileProvider.FilterMode.FilesOnly);
		}
	}

	public String mimeType(String filename) {
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
	}

	@Override
	public void onResume() {
		super.onResume();

		try {
			h = new DataHandler(ecgFilePath);
		}catch(Error e){
			Log.e(TAG, e.toString());
		} catch (Exception e) {
			Log.e(TAG, e.toString());
		}
		
		initECGPanel(h);
	}
	
	@Override
	public void onStop() {
		super.onStop();
		//when activity is restarting expandable slider must hide
		isSliderExpand = false;
	}
	
	@Override
	public void eventClickSliderContent(int resID) {
		switch ( resID ) {
		case R.id.slider_camera:
				if (!isOnline()){
					Toast.makeText(SCPViewActivity.this, R.string.no_connection,Toast.LENGTH_SHORT).show();
				}
				else {
					runScanner();
				}
			break;
		case R.id.slider_file_chooser:
			runFileChooser( R.style.Theme_Sherlock, ROOT_PATH, IFileProvider.FilterMode.FilesOnly );
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
		case R.id.slider_settings:
			runSettings();
			break;
		case R.id.slider_linear:
			if (touchMode == GestureListener.MODE_BASIC) {
				// change mode
				touchMode = GestureListener.MODE_LINEAR;
				// show text notify
				Toast.makeText(SCPViewActivity.this, "Linear mode on",Toast.LENGTH_SHORT).show();
			    ecgPanel.setTouchMode(touchMode);
				ecgPanel.getView().invalidate();
			}
			else if (touchMode == GestureListener.MODE_LINEAR) {
				// change mode
				touchMode = GestureListener.MODE_BASIC;
				// show text notify
				Toast.makeText(SCPViewActivity.this, "Linear mode off",Toast.LENGTH_SHORT).show();
			    // change mode for GraphicView, Channels
			    ecgPanel.setTouchMode(touchMode);
				ecgPanel.getView().invalidate();
			}
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
		settings = getSharedPreferences(getResources().getString( R.string.app_settings_file ), MODE_PRIVATE);
		ecgPanel = new ECGPanelFragment( h ,settings);
		
		String ecgFilePath = settings.getString(getResources().getString( R.string.app_settings_file_paths_ecg ), ROOT_PATH);
		Log.d(TAG, ecgFilePath);
		
		if(settings.getBoolean(getResources().getString(R.string.settings_mode_qrcode), false)){
			Log.d(TAG, "QR-code mode");
		}
		else if(settings.getBoolean(getResources().getString(R.string.settings_mode_filemanager), false)){
			Log.d(TAG, "File manager mode");
		}
		else {
			Log.d(TAG, "Bad");
		}
		
		FragmentTransaction ft = getSupportFragmentManager().beginTransaction();
        ft.replace( R.id.ecg_panel_fragment, ecgPanel );
        ft.commitAllowingStateLoss();
	}

	@Override
	protected void onSaveInstanceState(Bundle outState) {
		super.onSaveInstanceState(outState);
		// save the current ecg file path
		String filePathKey = getResources().getString(R.string.app_file_path);
		outState.putString(filePathKey, ecgFilePath);
	}

	@Override
	protected void onRestoreInstanceState(Bundle savedInstanceState) {
		super.onRestoreInstanceState(savedInstanceState);
		// restore the current ecg file path and status of expandable slider
		String filePathKey = getResources().getString(R.string.app_file_path);
		ecgFilePath = savedInstanceState.getString(filePathKey);
	}

	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		Log.d(TAG, "onActivityResult");
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

				Log.d(TAG, files.get(0).getPath());
				
				ecgFilePath = files.get(0).getPath();
			}
			break;
		case REQUEST_SCAN_QRCODE:
			Log.d(TAG,"scan file");
			if (resultCode == RESULT_OK) {
				String result =  data.getStringExtra(ZXingConstants.SCAN_RESULT);
				final Context context = this;
				Intent intent = new Intent(context, WebViewActivity.class);
				intent.putExtra(URL,result);
			    startActivityForResult(intent, REQUEST_GET_FILE);
			}
			else if(resultCode == RESULT_CANCELED && data != null) {
                String error = data.getStringExtra(ZXingConstants.ERROR_INFO);
                if(!TextUtils.isEmpty(error)) {
                    Toast.makeText(this, error, Toast.LENGTH_SHORT).show();
                }
            }
			break;
			// retrieve file name after downloading, and open this file
		case REQUEST_GET_FILE:
			if (resultCode == RESULT_OK) {
				Log.d(TAG,"Get file");
				ecgFilePath = data.getExtras().getString(FILE);
				//dialog.hide();
			}
			break;
		case REQUEST_DECODE_QR:
			if (resultCode == RESULT_OK) {
				Log.d(TAG,"Decode file");
				final Context context = this;
				Intent intent = new Intent(context, WebViewActivity.class);
				intent.putExtra(URL,data.getExtras().getString(ZXingConstants.SCAN_RESULT));
			    startActivityForResult(intent, REQUEST_GET_FILE);
			}	
			else {
				Toast.makeText(SCPViewActivity.this, R.string.error_decode_qr,Toast.LENGTH_SHORT).show();
			}
			break;
		}	
		
		// this option was excluded on September 13, 2013
		//if ( ecgFilePath.equals(""))
			//runActionDialog();
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
	
	public boolean isCameraAvailable() {
        PackageManager pm = getPackageManager();
        return (pm.hasSystemFeature(PackageManager.FEATURE_CAMERA) || pm.hasSystemFeature(PackageManager.FEATURE_CAMERA_FRONT));
    }
	
	public void runActionDialog() {
		AlertDialog.Builder builder = new AlertDialog.Builder(this);
	    builder.setTitle(R.string.choose_action).setItems(R.array.choose_action_array, new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int which) { 
            	switch (which) {
            	case 0: {
            		runFileChooser(R.style.Theme_Sherlock, ROOT_PATH, IFileProvider.FilterMode.FilesOnly);
            		break;
            	}
            	case 1: {
            		if (!isOnline()) 
            			Toast.makeText(SCPViewActivity.this, R.string.no_connection,Toast.LENGTH_SHORT).show();
            		else runScanner();
            		break;
            	}
            	case 2: {
            		_this.finish();
            		break;
            	}
            	}
            }
        });
	    dialog = builder.create();
	    dialog.show();
	}
	
	private final void runScanner() {
		if (isCameraAvailable()) {
			Intent intent = new Intent("com.google.zxing.client.android.SCAN");
			intent.putExtra("SCAN_MODE", "QR_CODE_MODE");
			startActivityForResult(intent, REQUEST_SCAN_QRCODE);
		} else {
            Toast.makeText(this, "Camera Unavailable", Toast.LENGTH_SHORT).show();
        }
	}

	private final void runFileChooser(int style, String rootPath, IFileProvider.FilterMode mode) {
		Intent intent = new Intent(getBaseContext(), FileChooserActivity.class);
		/*
		 * by default, if not specified, default rootpath is sdcard, if sdcard
		 * is not available, "/" will be used
		 */
		intent.putExtra(FileChooserActivity._Theme, style);
		intent.putExtra(FileChooserActivity._Rootpath,
				(Parcelable) new LocalFile(rootPath));
		intent.putExtra(FileChooserActivity._FilterMode,
				mode);
		startActivityForResult(intent, REQUEST_CHOOSE_FILE);
	}
	
	private final void runSettings() {
		try {
			Intent intent = new Intent(getApplicationContext(), Settings.class);
			startActivity(intent);
		} catch (Exception e) {
			Log.e("Error in ", e.toString());
		}
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
