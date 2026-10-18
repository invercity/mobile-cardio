package ua.stu.view.fragments;

import ua.stu.view.scpview.R;
import android.os.Bundle;
import android.preference.ListPreference;
import android.preference.Preference;
import android.preference.Preference.OnPreferenceChangeListener;
import android.preference.PreferenceFragment;


public class SettingsModeFragment extends PreferenceFragment implements OnPreferenceChangeListener {
	
	private final static String TAG = "SettingsModeFragment";
	
	private ListPreference mode;
	
	public void onCreate(Bundle savedInstanceState) {
	    super.onCreate(savedInstanceState);
	    addPreferencesFromResource( R.layout.settings_mode );
	    init();
	}
	
	private final void init() {
		mode = (ListPreference)findPreference(getResources().getString(R.string.app_settings_mode));
	    mode.setOnPreferenceChangeListener(this);

	    //default mode is File Manager
  		if ( mode.getValue() == null ) {
  			mode.setValueIndex(0);
  		}

  		mode.setTitle(mode.getEntry());
	}

	@Override
	public boolean onPreferenceChange(Preference preference, Object newValue) {
		CharSequence [] modes = mode.getEntries();
		CharSequence currentMode = modes[Integer.parseInt(newValue.toString())].toString();
		mode.setTitle(currentMode);

		return true;
	}
}
