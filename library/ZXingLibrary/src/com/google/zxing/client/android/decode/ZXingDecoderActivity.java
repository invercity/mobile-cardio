package com.google.zxing.client.android.decode;

import com.google.zxing.BarcodeFormat;
import com.google.zxing.BinaryBitmap;
import com.google.zxing.ChecksumException;
import com.google.zxing.FormatException;
import com.google.zxing.LuminanceSource;
import com.google.zxing.MultiFormatReader;
import com.google.zxing.NotFoundException;
import com.google.zxing.RGBLuminanceSource;
import com.google.zxing.Reader;
import com.google.zxing.Result;
import com.google.zxing.ResultPoint;
import com.google.zxing.client.android.R;
import com.google.zxing.client.android.ZXingConstants;
import com.google.zxing.common.HybridBinarizer;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.util.Log;

public class ZXingDecoderActivity extends Activity implements ZXingConstants {
	private static final String TAG = "ZXingDecoderActivity";
	private String imageFile;
	
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.decode);
		init();
	}

	private final void init() {
		imageFile = (String)getIntent().getSerializableExtra(ZXingConstants.RESPONSE_DECODE_IMAGE);
		Log.d(TAG, "qr code file"+imageFile);
	}
	
	@Override
	public void onStart(){
		super.onStart();

		Bitmap barcodeBmp 	= BitmapFactory.decodeFile(imageFile);
		
		int width = barcodeBmp.getWidth();
		int height = barcodeBmp.getHeight();
        int[] pixels = new int[width * height];
        barcodeBmp.getPixels(pixels, 0, width, 0, 0, width, height);
        barcodeBmp.recycle();
        barcodeBmp = null;
		
        RGBLuminanceSource source = new RGBLuminanceSource(width, height, pixels);
		BinaryBitmap bitmap = new BinaryBitmap(new HybridBinarizer(source));
		Reader reader = new MultiFormatReader();
		try {
		    Result result = reader.decode(bitmap);
		    String contents = result.getText();
		    Log.d(TAG, "text "+contents);
		    Intent dataIntent = new Intent();
            dataIntent.putExtra(SCAN_RESULT, contents);
            setResult(Activity.RESULT_OK, dataIntent);
            finish();
		} catch (NotFoundException e) {
		    e.printStackTrace();
		    return;
		} catch (ChecksumException e) {
		    e.printStackTrace();
		    return;
		} catch (FormatException e) {
		    e.printStackTrace();
		    return;
		} 
	}
}
