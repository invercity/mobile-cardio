package ua.stu.view.fragments;

import ua.stu.view.scpview.R;
import android.os.Bundle;
import android.preference.ListPreference;
import android.preference.PreferenceFragment;
import android.text.Html;
import android.util.Log;


public class SettingsColorFragment extends PreferenceFragment {
	
	public void onCreate(Bundle savedInstanceState) {
	    super.onCreate(savedInstanceState);
	    addPreferencesFromResource( R.layout.settings_colors );
	}
}
