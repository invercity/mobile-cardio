package ua.stu.view.scpview;

import java.io.File;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.Hashtable;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentTransaction;

import ua.stu.scplib.data.DataHandler;
import ua.stu.view.fragments.ECGPanelFragment;
import ua.stu.view.fragments.ECGPanelFragment.OnClickSliderContentListener;
import ua.stu.view.temporary.InfoO;
import ua.stu.view.temporary.InfoP;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.KeyEvent;
import android.webkit.MimeTypeMap;
import android.widget.Toast;

public class SCPViewActivity extends AppCompatActivity implements OnClickSliderContentListener {
	
	private static final String TAG = "SCPViewActivity";
	
	public static final int REQUEST_CHOOSE_FILE = 0;
	private static final int REQUEST_SCAN_QRCODE = 1;
	private static final int REQUEST_DECODE_QR = 2;
	private static final int REQUEST_GET_FILE = 3;
	public static final String SCAN = "la.droid.qr.scan";
	public static final String DECODE = "la.droid.qr.decode";
	public static final String IMG = "la.droid.qr.image";
	public static final String RESULT = "la.droid.qr.result";
	public static final String URL = "ua.stu.view.URL";
	public static final String FILE = "ua.stu.view.file";
	public static final String ROOT = "ua.stu.view.root";
	public static final String PREFS_NAME = "ScpViewFile";
	public static final String ROOT_PATH = "/mnt/sdcard";

	private ECGPanelFragment ecgPanel;
	private DataHandler h;

	private String ecgFilePath = "";
	
	private SharedPreferences settings;
	private Bundle state;
	private int touchMode = GestureListener.MODE_BASIC;
	
	private boolean isSliderExpand = false;
	
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.main);
		state = savedInstanceState;
		
		settings = getSharedPreferences(getResources().getString(R.string.app_settings_file), MODE_PRIVATE);
		final Intent intent = getIntent();

		if ((intent != null) && (intent.getData() != null)) {
			final Uri data = intent.getData();
			try {
				File cacheFile = new File(getCacheDir(), "opened_ecg.scp");
				try (InputStream in = getContentResolver().openInputStream(data);
					 OutputStream out = new FileOutputStream(cacheFile)) {
					byte[] buffer = new byte[8192];
					int len;
					while ((len = in.read(buffer)) > 0) {
						out.write(buffer, 0, len);
					}
				}
				ecgFilePath = cacheFile.getAbsolutePath();
			} catch (Exception e) {
				Log.e(TAG, "Error opening URI: " + e.getMessage());
			}
		} else if (state == null) {
			if (settings.getBoolean(getResources().getString(R.string.settings_mode_qrcode), false)) {
				runScanner();
			} else {
				runFileChooser();
			}
		}
	}

	public String mimeType(String filename) {
		int lastdot = filename.lastIndexOf(".");
		if (lastdot > 0) {
			String ext = filename.substring(lastdot + 1);
			MimeTypeMap mime = MimeTypeMap.getSingleton();
			String type = mime.getMimeTypeFromExtension(ext);
			if (type != null) {
				return type;
			}
		}
		return "no type";
	}

	@Override
	public void onResume() {
		super.onResume();

		try {
			if (!ecgFilePath.isEmpty()) {
				h = new DataHandler(ecgFilePath);
			}
		} catch (Error | Exception e) {
			Log.e(TAG, e.toString());
		}
		
		initECGPanel(h);
	}
	
	@Override
	public void onStop() {
		super.onStop();
		isSliderExpand = false;
	}
	
	@Override
	public void eventClickSliderContent(int resID) {
		if (resID == R.id.slider_camera) {
			if (!isOnline()) {
				Toast.makeText(this, R.string.no_connection, Toast.LENGTH_SHORT).show();
			} else {
				runScanner();
			}
		} else if (resID == R.id.slider_file_chooser) {
			runFileChooser();
		} else if (resID == R.id.slider_patient) {
			transferPatientData();
		} else if (resID == R.id.slider_other) {
			transferOtherData();
		} else if (resID == R.id.slider_ecg_revert) {
			if (ecgPanel != null && ecgPanel.getView() != null) {
				ecgPanel.revertECG(ecgPanel.getView());
			}
		} else if (resID == R.id.slider_settings) {
			runSettings();
		} else if (resID == R.id.slider_linear) {
			if (touchMode == GestureListener.MODE_BASIC) {
				touchMode = GestureListener.MODE_LINEAR;
				Toast.makeText(this, "Linear mode on", Toast.LENGTH_SHORT).show();
			} else {
				touchMode = GestureListener.MODE_BASIC;
				Toast.makeText(this, "Linear mode off", Toast.LENGTH_SHORT).show();
			}
			if (ecgPanel != null) {
				ecgPanel.setTouchMode(touchMode);
				if (ecgPanel.getView() != null) ecgPanel.getView().invalidate();
			}
		}
	}
	
	@Override
	public boolean onKeyUp(int keyCode, KeyEvent event) {
		if (keyCode == KeyEvent.KEYCODE_MENU) {
			expandSliderPanel();
		}
	    return super.onKeyUp(keyCode, event);
	}
	
	private void expandSliderPanel() {
		if (ecgPanel != null && ecgPanel.getSliderPanel() != null) {
			if (isSliderExpand) {
				ecgPanel.getSliderPanel().animateClose();
				isSliderExpand = false;	
			} else {
				ecgPanel.getSliderPanel().animateOpen();
				isSliderExpand = true;
			}
		}
	}
	
	private void initECGPanel(DataHandler h) {
		settings = getSharedPreferences(getResources().getString(R.string.app_settings_file), MODE_PRIVATE);
		ecgPanel = new ECGPanelFragment(h, settings);
		
		FragmentTransaction ft = getSupportFragmentManager().beginTransaction();
        ft.replace(R.id.ecg_panel_fragment, ecgPanel);
        ft.commitAllowingStateLoss();
	}

	@Override
	protected void onSaveInstanceState(Bundle outState) {
		super.onSaveInstanceState(outState);
		String filePathKey = getResources().getString(R.string.app_file_path);
		outState.putString(filePathKey, ecgFilePath);
	}

	@Override
	protected void onRestoreInstanceState(Bundle savedInstanceState) {
		super.onRestoreInstanceState(savedInstanceState);
		String filePathKey = getResources().getString(R.string.app_file_path);
		ecgFilePath = savedInstanceState.getString(filePathKey, "");
	}

	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		super.onActivityResult(requestCode, resultCode, data);
		switch (requestCode) {
		case REQUEST_CHOOSE_FILE:
			if (resultCode == RESULT_OK && data != null && data.getData() != null) {
				Uri uri = data.getData();
				try {
					File cacheFile = new File(getCacheDir(), "selected_ecg.scp");
					try (InputStream in = getContentResolver().openInputStream(uri);
						 OutputStream out = new FileOutputStream(cacheFile)) {
						byte[] buffer = new byte[8192];
						int len;
						while ((len = in.read(buffer)) > 0) {
							out.write(buffer, 0, len);
						}
					}
					ecgFilePath = cacheFile.getAbsolutePath();
					h = new DataHandler(ecgFilePath);
					initECGPanel(h);
				} catch (Exception e) {
					Log.e(TAG, "Error opening file: " + e.getMessage());
					Toast.makeText(this, "Error opening file: " + e.getMessage(), Toast.LENGTH_SHORT).show();
				}
			}
			break;
		case REQUEST_SCAN_QRCODE:
			if (resultCode == RESULT_OK && data != null) {
				String result = data.getStringExtra("SCAN_RESULT");
				if (result != null) {
					Intent intent = new Intent(this, WebViewActivity.class);
					intent.putExtra(URL, result);
					startActivityForResult(intent, REQUEST_GET_FILE);
				}
			}
			break;
		case REQUEST_GET_FILE:
			if (resultCode == RESULT_OK && data != null && data.getExtras() != null) {
				ecgFilePath = data.getExtras().getString(FILE, "");
				if (!ecgFilePath.isEmpty()) {
					try {
						h = new DataHandler(ecgFilePath);
						initECGPanel(h);
					} catch (Exception e) {
						Log.e(TAG, e.getMessage());
					}
				}
			}
			break;
		}	
	}
	
	public boolean isOnline() {
	    ConnectivityManager cm = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
	    if (cm != null) {
			NetworkInfo nInfo = cm.getActiveNetworkInfo();
			return nInfo != null && nInfo.isConnected();
		}
		return false;
	}
	
	public boolean isCameraAvailable() {
        PackageManager pm = getPackageManager();
        return (pm.hasSystemFeature(PackageManager.FEATURE_CAMERA_ANY));
    }
	
	public void runActionDialog() {
		AlertDialog.Builder builder = new AlertDialog.Builder(this);
	    builder.setTitle(R.string.choose_action).setItems(R.array.choose_action_array, new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int which) { 
            	switch (which) {
            	case 0:
            		runFileChooser();
            		break;
            	case 1:
            		if (!isOnline()) 
            			Toast.makeText(SCPViewActivity.this, R.string.no_connection, Toast.LENGTH_SHORT).show();
            		else runScanner();
            		break;
            	case 2:
            		finish();
            		break;
            	}
            }
        });
	    builder.create().show();
	}
	
	private void runScanner() {
		try {
			Intent intent = new Intent("com.google.zxing.client.android.SCAN");
			intent.putExtra("SCAN_MODE", "QR_CODE_MODE");
			startActivityForResult(intent, REQUEST_SCAN_QRCODE);
		} catch (Exception e) {
            Toast.makeText(this, "QR Scanner application not found", Toast.LENGTH_SHORT).show();
        }
	}

	private void runFileChooser() {
		Intent intent = new Intent(Intent.ACTION_OPEN_DOCUMENT);
		intent.addCategory(Intent.CATEGORY_OPENABLE);
		intent.setType("*/*");
		try {
			startActivityForResult(intent, REQUEST_CHOOSE_FILE);
		} catch (Exception e) {
			Intent getContentIntent = new Intent(Intent.ACTION_GET_CONTENT);
			getContentIntent.setType("*/*");
			startActivityForResult(Intent.createChooser(getContentIntent, "Select SCP ECG file"), REQUEST_CHOOSE_FILE);
		}
	}
	
	private void runSettings() {
		try {
			Intent intent = new Intent(getApplicationContext(), Settings.class);
			startActivity(intent);
		} catch (Exception e) {
			Log.e(TAG, "Error starting settings: " + e.toString());
		}
	}
	
	private void transferPatientData() {
		if (h == null || h.getPInfo() == null) return;
		Hashtable<String, InfoP> patientTable = new Hashtable<>();
		InfoP infoP = new InfoP(h.getPInfo().getAllPInfo());
		String patientKey = getResources().getString(R.string.app_patient);
		patientTable.put(patientKey, infoP);
		try {
			Intent intent = new Intent(getApplicationContext(), PatientInfo.class);
			intent.putExtra(patientKey, patientTable);
			startActivity(intent);
		} catch (Exception e) {
			Log.e(TAG, "Error in patient data: " + e.toString());
		}
	}
	
	private void transferOtherData() {		
		if (h == null || h.getOInfo() == null) return;
		Hashtable<String, InfoO> otherTable = new Hashtable<>();
		InfoO infoO = new InfoO(h.getOInfo().getAllOInfo());
		String otherKey = getResources().getString(R.string.app_other);
		otherTable.put(otherKey, infoO);
		try {
			Intent intent = new Intent(getApplicationContext(), OtherInfo.class);
			intent.putExtra(otherKey, otherTable);
			startActivity(intent);
		} catch (Exception e) {
			Log.e(TAG, "Error in other data: " + e.toString());
		}
	}
}
