package ua.stu.view.fragments;

import it.sephiroth.demo.slider.widget.MultiDirectionSlidingDrawer;
import ua.stu.view.scpview.DrawChanels;
import ua.stu.view.scpview.DrawGraphPaper;
import ua.stu.view.scpview.GestureListener;
import ua.stu.view.scpview.GraphicView;
import ua.stu.view.scpview.R;

import android.content.Context;
import android.content.res.Configuration;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.RadioButton;
import android.widget.SeekBar;
import android.widget.TextView;

public class ECGPanelFragment extends Fragment implements OnClickListener {
	
	public interface OnClickSliderContentListener {
	    void eventClickSliderContent(int resID);
	}
	
	private OnClickSliderContentListener eventClick;
	
	@Override
	public void onAttach(@NonNull Context context) {
	    super.onAttach(context);
	    try {
	        eventClick = (OnClickSliderContentListener) context;
	    } catch (ClassCastException e) {
	        throw new ClassCastException(context.toString() + " must implement OnClickSliderContentListener");
	    }
	}
	
	private static final String TAG = "ECGPanelFragment ";
	// display metrics
	private int displayWidth;
	private int displayHeight;
	private int displayDensity;
	// default values
	private float speed = 50;
	private int power = 1;

	private MultiDirectionSlidingDrawer sliderPanel;
	
	private GraphicView graphicView;
	private TextView statustext;
	private DrawGraphPaper graphPaper;
	private DrawChanels chanels;
	private GestureListener gestureListener = null;
	private ua.stu.scplib.data.DataHandler dataHandler;
	
	private RadioButton camera;
	private RadioButton fileChooser;
	private RadioButton patient;
	private RadioButton other;
	private RadioButton ecgRevert;
	private RadioButton settings;
	private RadioButton linear;
	private boolean isRevert = false;

	public static final String PREFS_NAME = "ScpViewFile";
	private android.content.SharedPreferences pSettings;
	
	public ECGPanelFragment() {
	}
	
	public ECGPanelFragment(ua.stu.scplib.data.DataHandler h, android.content.SharedPreferences settings) {
		this.dataHandler = h;
		this.pSettings = settings;
	}

	public static Bitmap getBitmapFromView(View view) {
	    Bitmap returnedBitmap = Bitmap.createBitmap(view.getWidth(), view.getHeight(), Bitmap.Config.ARGB_8888);
	    Canvas canvas = new Canvas(returnedBitmap);
	    Drawable bgDrawable = view.getBackground();
	    if (bgDrawable != null) 
	        bgDrawable.draw(canvas);
	    else 
	        canvas.drawColor(Color.WHITE);
	    view.draw(canvas);
	    return returnedBitmap;
	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {	
		DisplayMetrics metrics = inflater.getContext().getResources().getDisplayMetrics();
		this.displayWidth = metrics.widthPixels;
		this.displayHeight = metrics.heightPixels;
		this.displayDensity = metrics.densityDpi;
		View view = inflater.inflate(R.layout.ecg_panel, container, false);

		graphicView = view.findViewById(R.id.GraphicView);
		statustext = view.findViewById(R.id.StatusText);
		graphPaper = view.findViewById(R.id.DrawGraphPaper);
		chanels = view.findViewById(R.id.drawChanels);

		gestureListener = new GestureListener(graphicView, chanels);
		chanels.initscale(gestureListener);
		graphicView.initscale(gestureListener);
		graphicView.setDisplayMetrics(displayWidth, displayHeight, displayDensity);
		init(view);

		graphicView.setDrawChanels(chanels);
		graphicView.setTvStatus(statustext);	
		graphicView.setH(dataHandler);
		
		graphicView.setXScale(speed);
		graphicView.setYScale(10f);			
		
		if (pSettings != null) {
			setColorThem(
				pSettings.getInt(getResources().getString(R.string.app_settings_colorGp), Color.rgb(173, 216, 230)),
				pSettings.getInt(getResources().getString(R.string.app_settings_colorG), Color.rgb(76, 76, 76)),
				pSettings.getInt(getResources().getString(R.string.app_settings_colorCh), Color.BLACK)
			);
		}
		
	    return view;    
	}

	/**
	 * Встановлення колірної схеми
	 */
	public void setColorThem(int cGraphPaper, int cGraphic, int cChar) {		
		graphicView.setGraphicColor(cGraphic);		
		graphPaper.setColorLinesAndDot(cGraphPaper);		
		statustext.setTextColor(cChar);
		chanels.setChanelNameColor(cChar);
		chanels.setGraphicColor(cGraphic);
	}
	
	@Override
	public void onClick(View view) {
		if (eventClick != null) {
			eventClick.eventClickSliderContent(view.getId());
		}
	}
	
	private void init(View view) {
		initSliderPanel(view);
	}
	
	private void initSliderContent(View view) {
		camera = view.findViewById(R.id.slider_camera);
		fileChooser = view.findViewById(R.id.slider_file_chooser);
		patient = view.findViewById(R.id.slider_patient);
		other = view.findViewById(R.id.slider_other);
		ecgRevert = view.findViewById(R.id.slider_ecg_revert);
		settings = view.findViewById(R.id.slider_settings);
		linear = view.findViewById(R.id.slider_linear);
		
		camera.setOnClickListener(this);
		fileChooser.setOnClickListener(this);
		patient.setOnClickListener(this);
		other.setOnClickListener(this);
		ecgRevert.setOnClickListener(this);
		settings.setOnClickListener(this);
		linear.setOnClickListener(this);
		
		if (dataHandler != null) {
			contentClicable(true);
		} else {
			contentClicable(false);
		}
	}
	
	private void contentClicable(boolean isClick) {
		patient.setClickable(isClick);
		other.setClickable(isClick);
		ecgRevert.setClickable(isClick);
		linear.setClickable(isClick);
	}
	
	private void initSliderPanel(View view) {
		initSliderContent(view);
		sliderPanel = view.findViewById(R.id.slider_panel);

		int orientation = view.getResources().getConfiguration().orientation;
		int topOffset = displayHeight - (int) getResources().getDimension(R.dimen.slider_top_dimension_land);
		if (orientation == Configuration.ORIENTATION_PORTRAIT) {
			topOffset = displayHeight - (int) getResources().getDimension(R.dimen.slider_top_dimension_port);
		}
			
		if (sliderPanel != null) {
			sliderPanel.setTopOffset(topOffset);
		}
	}
	
	public int getDisplayWidth() {
		return displayWidth;
	}
	
	public void setDisplayWidth(int displayWidth) {
		this.displayWidth = displayWidth;
	}

	public int getDisplayHeight() {
		return displayHeight;
	}

	public void setDisplayHeight(int displayHeight) {
		this.displayHeight = displayHeight;
	}

	public void onStartTrackingTouch(SeekBar arg0) {
	}

	public MultiDirectionSlidingDrawer getSliderPanel() {
		return sliderPanel;
	}
	
	public float getSpeed() {
		return speed;
	}

	public void setSpeed(int speed) {
		this.speed = speed;
	}

	public int getPower() {
		return power;
	}

	public void setPower(int power) {
		this.power = power;
	}

	public void revertECG(View view) {
		isRevert = !isRevert;
		graphicView.setInvert(isRevert);
		graphicView.invalidate();
	}
	
	public void setTouchMode(int mode) {
		if (this.gestureListener != null) this.gestureListener.setMode(mode);
	}
	
	public GraphicView getView() {
		return graphicView;
	}
}
