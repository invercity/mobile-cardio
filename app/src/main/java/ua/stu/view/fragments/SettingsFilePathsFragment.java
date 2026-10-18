package ua.stu.view.fragments;

import ua.stu.view.scpview.R;
import ua.stu.view.scpview.Settings;
import android.os.Bundle;
import android.preference.Preference;
import android.preference.PreferenceFragment;
import android.preference.Preference.OnPreferenceClickListener;
import android.util.Log;

public class SettingsFilePathsFragment extends PreferenceFragment implements OnPreferenceClickListener {
	
	private final static String TAG = "SettingsFilePathsFragment";
	private String preferenceKey;
	
	@Override
	public void onCreate(Bundle savedInstanceState) {
	    super.onCreate(savedInstanceState);
	    addPreferencesFromResource(R.layout.settings_file_paths);
	    preferenceKey = getResources().getString(R.string.app_settings_file_paths);
	    Preference filePaths = findPreference(preferenceKey);
	    if (filePaths != null) {
	        filePaths.setOnPreferenceClickListener(this);
	    }
	}
	
	@Override
	public boolean onPreferenceClick(Preference preference) {
		Log.d(TAG, preference.getKey());
		if (preference.getKey().equals(preferenceKey)) {
			if (getActivity() instanceof Settings) {
				((Settings) getActivity()).runFileChooser();
			}
		}
		return true;
	}
}
