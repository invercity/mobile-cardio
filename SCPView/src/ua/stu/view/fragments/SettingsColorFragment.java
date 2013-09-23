package ua.stu.view.fragments;

import ua.stu.view.scpview.R;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.ListPreference;
import android.preference.Preference;
import android.preference.Preference.OnPreferenceChangeListener;
import android.preference.PreferenceFragment;


public class SettingsColorFragment extends PreferenceFragment implements OnPreferenceChangeListener {
	
	private final String TAG = "SettingsColorFragment";
	private ListPreference colorSchema;
	
	//preference on MainActivity context
	private SharedPreferences preferences;
	
	public void onCreate(Bundle savedInstanceState) {
	    super.onCreate(savedInstanceState);
	    addPreferencesFromResource( R.layout.settings_colors );
	    init();
	}

	private final void init() {
		colorSchema = (ListPreference)findPreference(getResources().getString(R.string.app_settings_colors));
	    colorSchema.setOnPreferenceChangeListener(this);

	    //default color schema is Red-Black
  		if ( colorSchema.getValue() == null ) {
  			colorSchema.setValueIndex(0);
  		}

  		colorSchema.setTitle(colorSchema.getEntry());
	}
	
	@Override
	public boolean onPreferenceChange(Preference preference, Object newValue) {
		CharSequence [] colors = colorSchema.getEntries();
		CharSequence schema = colors[Integer.parseInt(newValue.toString())].toString();
		colorSchema.setTitle(schema);
		
		return true;
	}
}
