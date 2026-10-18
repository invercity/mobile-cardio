package ua.stu.view.scpview;

import java.util.List;

import android.content.Intent;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.os.Bundle;
import android.preference.PreferenceActivity;
import android.preference.PreferenceManager;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;

public class Settings extends PreferenceActivity implements OnClickListener {
	
	private final static String TAG = "Settings"; 
	private Button save;
	private final static int SAVE_ID = 0x12345678;
	
	private SharedPreferences preferences_default;
	private SharedPreferences preferences;
	
	private String ecgFilePath;
	
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		init();
		setListFooter(save);
	}
	
	@Override
	public void onBuildHeaders(List<Header> target) {
		super.onBuildHeaders(target);
	    loadHeadersFromResource(R.layout.settings_headers, target);
	}

	@Override
	protected boolean isValidFragment(String fragmentName) {
		return true;
	}
	
	private void init() {
		save = new Button(this);
		save.setId(SAVE_ID);
		save.setText(R.string.settings_save);
		save.setOnClickListener(this);
		preferences_default = PreferenceManager.getDefaultSharedPreferences(this);
		preferences = getSharedPreferences(getResources().getString(R.string.app_settings_file), MODE_PRIVATE);
	}
	
	@Override
	public void onClick(View v) {
		if (v.getId() == SAVE_ID) {
			saveColorsSettings(); 
			saveModeSettings();
			finish();
		}
	}
	
	private void saveColorsSettings() {
		String settingsColorsKey = getResources().getString(R.string.app_settings_colors);
		String settingsColorsValue = preferences_default.getString(settingsColorsKey, "1");
		
		String settingsColorGp = getResources().getString(R.string.app_settings_colorGp);
		String settingsColorChar = getResources().getString(R.string.app_settings_colorCh);
		String settingsColorGrap = getResources().getString(R.string.app_settings_colorG);
		
		int colorGraphPaper = android.graphics.Color.RED;
		int colorCharacter = android.graphics.Color.BLUE;
		int colorGrap = android.graphics.Color.BLACK;
		if ("1".equals(settingsColorsValue)) {
			colorGraphPaper = android.graphics.Color.GRAY;
			colorCharacter = android.graphics.Color.BLACK;
			colorGrap = android.graphics.Color.BLUE;
		}
		
		Editor ed = preferences.edit();
		ed.putInt(settingsColorGp, colorGraphPaper);
		ed.putInt(settingsColorChar, colorCharacter);
		ed.putInt(settingsColorGrap, colorGrap);
		ed.apply();
	}
	
	private void saveModeSettings() {
		String settingsModeKey = getResources().getString(R.string.app_settings_mode);
		String settingsModeValue = preferences_default.getString(settingsModeKey, "1");
		
		Editor ed = preferences.edit();
		if ("0".equals(settingsModeValue)) {
			ed.putBoolean(getResources().getString(R.string.settings_mode_filemanager), true);
			ed.putBoolean(getResources().getString(R.string.settings_mode_qrcode), false);
		} else if ("1".equals(settingsModeValue)) {
			ed.putBoolean(getResources().getString(R.string.settings_mode_qrcode), true);
			ed.putBoolean(getResources().getString(R.string.settings_mode_filemanager), false);
		}
		ed.apply();
	}
	
	private void saveFilePathsSettings(String ecgFilePath) {
		String settingsFilePathsECGKey = getResources().getString(R.string.app_settings_file_paths_ecg);
		Editor ed = preferences.edit();
		ed.putString(settingsFilePathsECGKey, ecgFilePath);
		ed.apply();
	}
	
	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		super.onActivityResult(requestCode, resultCode, data);
		if (requestCode == SCPViewActivity.REQUEST_CHOOSE_FILE && resultCode == RESULT_OK && data != null && data.getData() != null) {
			ecgFilePath = data.getData().toString();
			saveFilePathsSettings(ecgFilePath);
		}
	}
	
	public void runFileChooser() {
		Intent intent = new Intent(Intent.ACTION_OPEN_DOCUMENT_TREE);
		try {
			startActivityForResult(intent, SCPViewActivity.REQUEST_CHOOSE_FILE);
		} catch (Exception e) {
			Log.e(TAG, "Error choosing folder: " + e.getMessage());
		}
	}
}
