package ua.stu.view.fragments;

import group.pals.android.lib.ui.filechooser.services.IFileProvider;
import ua.stu.view.scpview.R;
import ua.stu.view.scpview.SCPViewActivity;
import ua.stu.view.scpview.Settings;
import android.os.Bundle;
import android.preference.Preference;
import android.preference.PreferenceFragment;
import android.preference.Preference.OnPreferenceClickListener;
import android.util.Log;


public class SettingsFilePathsFragment extends PreferenceFragment implements OnPreferenceClickListener {
	
	private final static String TAG = "SettingsFilePathsFragment";
	private String preferenceKey;
	
	public void onCreate(Bundle savedInstanceState) {
	    super.onCreate(savedInstanceState);
	    addPreferencesFromResource( R.layout.settings_file_paths );
	    preferenceKey = getResources().getString(R.string.app_settings_file_paths);
	    Preference filePaths = findPreference(preferenceKey);
	    filePaths.setOnPreferenceClickListener(this);
	}
	
	@Override
	public boolean onPreferenceClick(Preference preference) {
		Log.d(TAG, preference.getKey());
		if (preference.getKey().equals(preferenceKey)){
			Settings settings = (Settings)getActivity();
			settings.runFileChooser(R.style.Theme_Sherlock, SCPViewActivity.ROOT_PATH, IFileProvider.FilterMode.DirectoriesOnly);
		}
		return true;
	}
}
