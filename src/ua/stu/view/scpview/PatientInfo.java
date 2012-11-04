package ua.stu.view.scpview;

import android.annotation.TargetApi;
import android.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ListView;


@TargetApi(11) 
public class PatientInfo extends Fragment {
	
	private static String[] infoTypes = { "Пациент", "Кровяное давления",
									  "Адрес","Диагноз или направления",
									  "История болезни"};
	
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
		      Bundle savedInstanceState) 
	{
		View v = inflater.inflate(R.layout.patientinfo, null);
		// находим список
	    ListView lvMain = (ListView) v.findViewById(R.id.lvMain);

	    // создаем адаптер
	    ArrayAdapter<String> adapter = new ArrayAdapter<String>(inflater.getContext(),
	        android.R.layout.simple_list_item_1, infoTypes);

	    // присваиваем адаптер списку
	    lvMain.setAdapter(adapter);
		
		return v;
	}

}
