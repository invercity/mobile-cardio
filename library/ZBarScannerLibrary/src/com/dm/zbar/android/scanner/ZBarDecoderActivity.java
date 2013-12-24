package com.dm.zbar.android.scanner;

import net.sourceforge.zbar.Config;
import net.sourceforge.zbar.Image;
import net.sourceforge.zbar.ImageScanner;
import net.sourceforge.zbar.Symbol;
import net.sourceforge.zbar.SymbolSet;
import android.annotation.TargetApi;
import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Build;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;

public class ZBarDecoderActivity extends Activity implements ZBarConstants {
	private static final String TAG = "ZBarDecoderActivity";
	private String imageFile;
	private ImageScanner mScanner;
	
	static {
	    System.loadLibrary("iconv");
	}
	
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.decoder);
		init();
	}
	
	private final void init() {
		imageFile = (String)getIntent().getSerializableExtra(ZBarConstants.RESPONSE_DECODE_IMAGE);
		Log.d(TAG, "qr code file"+imageFile);
	}
	
	@TargetApi(Build.VERSION_CODES.HONEYCOMB_MR1)
    protected int sizeOf(Bitmap data) {
        if (Build.VERSION.SDK_INT < Build.VERSION_CODES.HONEYCOMB_MR1) {
            return data.getRowBytes() * data.getHeight();
        } else {
            return data.getByteCount();
        }
    }
	
	@Override
	public void onStart(){
		super.onStart();
		setupScanner();
		
		Bitmap barcodeBmp 	= BitmapFactory.decodeFile(imageFile);
		int width 			= barcodeBmp.getWidth();
        int height 			= barcodeBmp.getHeight();
        int[] pixels 		= new int[sizeOf(barcodeBmp)];
        
        barcodeBmp.getPixels(pixels, 0, width, 0, 0, width, height);
		
		Image barcode = new Image(width, height, "RGB4");
        barcode.setData(pixels);
		
		int result = mScanner.scanImage(barcode.convert("Y800"));
        if (result != 0) {
        	SymbolSet syms = mScanner.getResults();
            for (Symbol sym : syms) {
                String symData = sym.getData();
                Log.d(TAG,symData);
                if (!TextUtils.isEmpty(symData)) {
                    Intent dataIntent = new Intent();
                    dataIntent.putExtra(SCAN_RESULT, symData);
                    setResult(Activity.RESULT_OK, dataIntent);
                    finish();
                    break;
                }
            }
        }
        
	}
	
	public void setupScanner() {
        mScanner = new ImageScanner();
        mScanner.setConfig(0, Config.X_DENSITY, 3);
        mScanner.setConfig(0, Config.Y_DENSITY, 3);

        int[] symbols = getIntent().getIntArrayExtra(SCAN_MODES);
        if (symbols != null) {
            mScanner.setConfig(Symbol.NONE, Config.ENABLE, 0);
            for (int symbol : symbols) {
                mScanner.setConfig(symbol, Config.ENABLE, 1);
            }
        }
    }
}
