package ua.stu.view.fragments;

import ua.stu.view.scpview.ECGPanel;
import ua.stu.view.scpview.R;
import android.annotation.TargetApi;
import android.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

@TargetApi(11)
public class ECGPanelFragment extends Fragment {
	
	private ECGPanel ecgPanel;
	
	@Override
	  public View onCreateView(LayoutInflater inflater, ViewGroup container,
	      Bundle savedInstanceState) {
	    return inflater.inflate(R.layout.ecg_panel, null);
	  }

}
