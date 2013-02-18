package ua.stu.view.fragments;
import it.sephiroth.demo.slider.widget.*;
import ua.stu.view.scpview.GraphicView;
import ua.stu.view.scpview.ImageViewer;
import ua.stu.view.scpview.R;
import android.app.Activity;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.View.OnTouchListener;
import android.view.ViewGroup;
import android.webkit.WebView.FindListener;
import android.widget.CheckBox;
import android.widget.RadioButton;
import android.widget.SeekBar;
import android.widget.SeekBar.OnSeekBarChangeListener;
import android.widget.TextView;

public class ECGPanelFragment extends Fragment implements OnSeekBarChangeListener,
														  OnClickListener {
	
	public interface OnClickSliderContentListener {
	    public void eventClickSliderContent(int resID);
	}
	
	private OnClickSliderContentListener eventClick;
	
	@Override
	public void onAttach(Activity activity) {
	    super.onAttach(activity);
	        try {
	        	eventClick = (OnClickSliderContentListener) activity;
	        } catch (ClassCastException e) {
	            throw new ClassCastException(activity.toString() + " must implement OnClickSliderContentListener");
	        }
	}
	
	private static final String TAG = "ECGPanelFragment ";
	/**
	 * Длина экрана
	 */
	private int displayWidth;
	/**
	 * Ширина экрана
	 */
	private int displayHeight;
	/**
	 * Максимальная скорость ЕКГ
	 */
	private static int MAX_SPEED = 100;
	/**
	 * Кореляция усиления для слайдера(потому что отображает только целый шаг)
	 */
	private static int CORRECTION_POWER = 4;
	/**
	 * Максимальное усиление ЕКГ
	 */
	private static int MAX_POWER = 16;
	
	private int speed=25;
	private int power=1;
	
	

	private static int SLIDER_SCREEN_PART = 7;
	private MultiDirectionSlidingDrawer sliderPanel;
	
	private GraphicView graphicView;
	private ImageViewer imageViewer;	
	
	private RadioButton camera;
	private RadioButton fileChooser;
	private RadioButton patient;
	private RadioButton other;

	public ECGPanelFragment(){
		
	}
	
	public ECGPanelFragment(GraphicView graphicView){
		this.graphicView=graphicView;
	}
	public static Bitmap getBitmapFromView(GraphicView view) {
	    Bitmap returnedBitmap = Bitmap.createBitmap(view.getSW(), view.getSH(),Bitmap.Config.ARGB_8888);
	   
	    Canvas canvas = new Canvas(returnedBitmap);
	    Drawable bgDrawable =view.getBackground();
	    if (bgDrawable!=null) 
	        bgDrawable.draw(canvas);
	    else 
	        canvas.drawColor(Color.WHITE);
	    view.draw(canvas);
	    return returnedBitmap;
	}
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,Bundle savedInstanceState){	
		DisplayMetrics metrics = inflater.getContext().getResources().getDisplayMetrics();
		setDisplayWidth(metrics.widthPixels);
		setDisplayHeight(metrics.heightPixels);
		
		View view = inflater.inflate( R.layout.ecg_panel, null );

		//Fragment doesn't call onDestroy и onCreate
		setRetainInstance(true);
		
		init( view );

		imageViewer=(ImageViewer)view.findViewById(R.id.ImageViewer);		
		graphicView.setXScale(speed);
		graphicView.setYScale(1);		
		imageViewer.loadImage(getBitmapFromView(graphicView));
		
	
		
	    return view;    
	}
	
	@Override
	public void onClick( View view ) {
		switch ( view.getId() ){			
		case R.id.slider_camera:
			eventClick.eventClickSliderContent( R.id.slider_camera );
			break;
		case R.id.slider_file_chooser:
			eventClick.eventClickSliderContent( R.id.slider_file_chooser );
			break;
		case R.id.slider_patient:
			eventClick.eventClickSliderContent( R.id.slider_patient );
			break;
		case R.id.slider_other:
			eventClick.eventClickSliderContent( R.id.slider_other );
			break;
		//in future, when all panel will sliding	
		/*default:
			expandSliderPanel();
			break;*/
		}
	}
	
	private final void init ( View view ) {
		initSliderPanel( view );

		
		
		camera.setOnClickListener( this );
		fileChooser.setOnClickListener( this );
		
		
	}
	
	private final void initSliderContent( View view ){
		camera 		= ( RadioButton )view.findViewById( R.id.slider_camera );
		fileChooser = ( RadioButton )view.findViewById( R.id.slider_file_chooser );
		patient		= ( RadioButton )view.findViewById( R.id.slider_patient );
		other		= ( RadioButton )view.findViewById( R.id.slider_other );
		
		camera.setOnClickListener( this );
		fileChooser.setOnClickListener( this );
		patient.setOnClickListener( this );
		other.setOnClickListener( this );
		
		if ( graphicView.isNotNull() ){
			contentClicable( true );
		}
		else {
			contentClicable( false );
		}
	}
	
	private final void contentClicable( boolean isClick ) {
		patient.setClickable(isClick);
		other.setClickable(isClick);
	}
	
	private final void initSliderPanel( View view ) {
		initSliderContent( view );

		sliderPanel		= ( MultiDirectionSlidingDrawer )view.findViewById( R.id.slider_panel );
		int topOffset 	= displayHeight - displayHeight / SLIDER_SCREEN_PART;
		sliderPanel.setTopOffset( topOffset );
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
		// TODO Auto-generated method stub
		
	}

	public MultiDirectionSlidingDrawer getSliderPanel(){
		return sliderPanel;
	}
	
	public int getSpeed() {
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

	@Override
	public void onStopTrackingTouch(SeekBar seekBar) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void onProgressChanged(SeekBar seekBar, int progress,
			boolean fromUser) {
		// TODO Auto-generated method stub
		
	}
	
	
}
