package ua.stu.view.scpview;

import java.util.List;

import android.content.Intent;
import android.content.SharedPreferences;
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
		preferences = PreferenceManager.getDefaultSharedPreferences(this);
	}

	@Override
	public void onClick(View v) {
		Intent output = new Intent();

		switch ( v.getId() ) {
		case SAVE_ID:
			String settingsColorGp = getResources().getString( R.string.app_settings_colorGp );
			int colorGraphPaper = preferences.getInt("settings_color_graph_paper", 0);
			String settingsColorChar = getResources().getString( R.string.app_settings_colorCh );
			int colorCharacter = preferences.getInt("settings_color_character", 0);
			String settingsColorGrap = getResources().getString( R.string.app_settings_colorG );
			int colorGrap = preferences.getInt("settings_color_graphic", 0);
			output.putExtra( settingsColorGp, colorGraphPaper );
			output.putExtra( settingsColorChar, colorCharacter );
			output.putExtra( settingsColorGrap, colorGrap );
			setResult(RESULT_OK, output);
			finish();
			break;
		}
	}
}
