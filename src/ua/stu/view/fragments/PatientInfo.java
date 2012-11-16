package ua.stu.view.fragments;

import ua.stu.view.scpview.R;
import android.annotation.TargetApi;
import android.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;


@TargetApi(11) 
public class PatientInfo extends Fragment
{
	
	private static String TAG = "PatientInfo";
	
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
		      Bundle savedInstanceState) 
	{
		View v = inflater.inflate(R.layout.patientinfo, null);

		init(v);
		
//	    lvMain = (ListView) v.findViewById(R.id.lvMain);
//	    lvMain.setOnItemClickListener(this);
//
//	    //create adapter
//   ArrayAdapter<String> adapter = new ArrayAdapter<String>(inflater.getContext(),
//        android.R.layout.simple_list_item_1, infoTypes);
//
//	    //add adapter list
//	    lvMain.setAdapter(adapter);
		
		return v;
	}

	private final void init (View v)
	{

	}
}
