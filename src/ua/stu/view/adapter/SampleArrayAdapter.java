package ua.stu.view.adapter;

import ua.stu.view.scpview.R;
import android.app.Activity;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

public class SampleArrayAdapter extends ArrayAdapter<String> {

	private final Activity context;
    private final String[] names;
	
	public SampleArrayAdapter(Activity context, String[] names) {
		super(context, R.layout.main,names);
		
		this.context = context;
        this.names = names;
	}
	
	/**
	 * Класс для сохранения во внешний класс и для ограничения доступа
	 * из потомков класса
	 */
    private static class ViewHolder {
        public ImageView imageView;
        public TextView textView;
    }
    
    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        // ViewHolder буферизирует оценку различных полей шаблона элемента
        ViewHolder holder;
        // Очищает сущетсвующий шаблон, если параметр задан
        // Работает только если базовый шаблон для всех классов один и тот же
        View rowView = convertView;
        if (rowView == null) {
            LayoutInflater inflater = context.getLayoutInflater();
            rowView = inflater.inflate(R.layout.main, null, true);
            holder = new ViewHolder();
            //holder.textView = (TextView) rowView.findViewById(R.id.label);
            //holder.imageView = (ImageView) rowView.findViewById(R.id.icon);
            rowView.setTag(holder);
        } else {
            holder = (ViewHolder) rowView.getTag();
        }

        holder.textView.setText(names[position]);
        //Change the icon
        String s = names[position];
        if (s.startsWith("Windows7") || s.startsWith("iPhone")
                || s.startsWith("Solaris")) {

            holder.imageView.setImageResource(R.drawable.perm_group_device_alarms_normal);
        } else {
            holder.imageView.setImageResource(R.drawable.perm_group_location_normal);
        }

        return rowView;
    }

}
