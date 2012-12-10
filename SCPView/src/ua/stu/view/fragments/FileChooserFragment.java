package ua.stu.view.fragments;

import com.actionbarsherlock.app.SherlockFragment;

import ua.stu.view.scpview.R;
import android.app.Activity;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.ImageButton;

public class FileChooserFragment extends Fragment implements OnClickListener {

	private ImageButton fileChooser;
	private ImageButton camera;

	public interface OnEventImageButtonClickListener 
	{
	    public void imageButtonClickEvent(int resId);
	}
	
	private OnEventImageButtonClickListener onEventImageButtonClick;
	
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
		      Bundle savedInstanceState) 
	{
		View view = inflater.inflate(R.layout.selected_panel, null);
		//Fragment doesn't call onDestroy и onCreate
		setRetainInstance(true);
		init(view);
		return view;
	}

	@Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        try {
        	onEventImageButtonClick = (OnEventImageButtonClickListener) activity;
        } catch (ClassCastException e) {
        	throw new ClassCastException(activity.toString() + " must implement OnEventImageButtonClickListener");
        }
    }
	
	private final void init(View v)
	{
		setFileChooser((ImageButton)v.findViewById(R.id.file_chooser));
		setCamera((ImageButton)v.findViewById(R.id.camera));
		
		fileChooser.setOnClickListener(this);
		camera.setOnClickListener(this);
	}
	
	/**
	 * @param fileChooser the fileChooser to set
	 */
	private final void setFileChooser(ImageButton fileChooser) {
		this.fileChooser = fileChooser;
	}

	/**
	 * @param camera the camera to set
	 */
	private final void setCamera(ImageButton camera) {
		this.camera = camera;
	}
	
	@Override
	public void onClick(View view) {
		try {
        	onEventImageButtonClick = (OnEventImageButtonClickListener) view.getContext();        	
        } catch (ClassCastException e) {
        	throw new ClassCastException(view.getContext().toString() + " must implement OnEventItemListener");
        }
		onEventImageButtonClick.imageButtonClickEvent(view.getId());
		
	}
}
