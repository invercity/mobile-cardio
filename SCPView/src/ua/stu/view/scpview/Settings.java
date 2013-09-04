package ua.stu.view.scpview;

import java.util.List;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.os.Bundle;
import android.preference.ListPreference;
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
	
	//default preference on settings context
	private SharedPreferences preferences_default;
	//preference on MainActivity context
	private SharedPreferences preferences;
	
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
	
	private final void init() {
		save = new Button( this );
		save.setId( SAVE_ID );
		save.setText( R.string.settings_save );
		save.setOnClickListener( this );
		preferences_default = PreferenceManager.getDefaultSharedPreferences(this);
		preferences = getSharedPreferences(getResources().getString( R.string.app_settings_file ), MODE_PRIVATE);
	}
	
	@Override
	public void onClick(View v) {
		switch ( v.getId() ) {
		case SAVE_ID:
			saveColorsSettings(); 
			saveModeSettings();
			finish();
			break;
		}
	}
	
	private void saveColorsSettings() {
		String settingsColorsKey 	= getResources().getString( R.string.app_settings_colors );
		String settingsColorsValue 	= preferences_default.getString(settingsColorsKey, "1");
		
		String settingsColorGp = getResources().getString( R.string.app_settings_colorGp );
		String settingsColorChar = getResources().getString( R.string.app_settings_colorCh );
		String settingsColorGrap = getResources().getString( R.string.app_settings_colorG );
		
		Log.d("settingsColorsValue",settingsColorsValue);
		int colorGraphPaper 	= android.graphics.Color.RED;
		int colorCharacter 		= android.graphics.Color.BLUE;
		int colorGrap 			= android.graphics.Color.BLACK;
		if (settingsColorsValue.equals("2")) {
			colorGraphPaper 	= android.graphics.Color.GRAY;
			colorCharacter 		= android.graphics.Color.BLACK;
			colorGrap 			= android.graphics.Color.BLUE;
		}
		
		Editor ed = preferences.edit();
		ed.putInt(settingsColorGp, colorGraphPaper);
		ed.putInt(settingsColorChar, colorCharacter);
		ed.putInt(settingsColorGrap, colorGrap);
		ed.commit();
	}
	
	private void saveModeSettings() {
		String settingsModeKey 		= getResources().getString( R.string.app_settings_mode );
		String settingsModeValue 	= preferences_default.getString(settingsModeKey, "1");
		
		Editor ed = preferences.edit();
		//file manager mode
		if (settingsModeValue.equals("1")) {
			ed.putBoolean(getResources().getString(R.string.settings_mode_filemanager),true);
			ed.putBoolean(getResources().getString(R.string.settings_mode_qrcode),false);
		}
		//qr-code mode
		else if (settingsModeValue.equals("2")) {
			ed.putBoolean(getResources().getString(R.string.settings_mode_qrcode),true);
			ed.putBoolean(getResources().getString(R.string.settings_mode_filemanager),false);
		}
		ed.commit();
	}
}
