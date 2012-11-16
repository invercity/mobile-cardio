package ua.stu.view.fragments;

import ua.stu.view.adapter.SampleArrayAdapter;
import ua.stu.view.scpview.R;
import ua.stu.view.scpview.SCPViewActivity;
import android.app.Activity;
import android.content.DialogInterface;
import android.content.DialogInterface.OnClickListener;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.ListFragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ArrayAdapter;
import android.widget.ListView;


public class InfoFragment extends Fragment implements OnItemClickListener  {
	
	private static String TAG = "PatientInfo";
	
	private ListView lvMain;
	
	private String[] values = new String[] {"Linux","BSD" };
	
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
		      Bundle savedInstanceState) 
	{
		View v = inflater.inflate(R.layout.info, null);
		
		init(v);
		
	    lvMain = (ListView) v.findViewById(R.id.lvMain);
	    lvMain.setOnItemClickListener(this);

	    //create adapter
	    ArrayAdapter<String> adapter = new ArrayAdapter<String>(inflater.getContext(),
        android.R.layout.simple_list_item_1, values);

	    //add adapter list
	    lvMain.setAdapter(adapter);
		
		return v;
	}
	
	private final void init (View v)
	{

	}

	@Override
	public void onItemClick(AdapterView<?> arg0, View v, int arg2, long arg3) {
		// TODO Auto-generated method stub
		Intent intent = new Intent(v.getContext(),SCPViewActivity.class);
		startActivity(intent);
	}
}
