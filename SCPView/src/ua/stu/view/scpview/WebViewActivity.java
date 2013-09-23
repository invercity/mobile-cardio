package ua.stu.view.scpview;

import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.URL;
import java.net.URLConnection;

import android.app.Activity;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.webkit.WebView;
import android.webkit.WebViewClient;

public class WebViewActivity extends Activity{
	
	private WebView webView;
	private String path;
	private String filename;
	 
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.webview);
		webView = (WebView) findViewById(R.id.webView1);
		webView.getSettings().setJavaScriptEnabled(true);
		webView.setWebViewClient(new WebViewClient() {
			@Override 
			public boolean shouldOverrideUrlLoading (WebView view, String url) { 
			     DownloadFile d = new DownloadFile();
			     d.execute(url);
			     // wait while file will be download
			     while (d.working());
			     Intent intent = new Intent();
			     intent.putExtra(SCPViewActivity.FILE,filename);
			     setResult(RESULT_OK, intent);
			     finish();
			     return true; 
			} 
		});
		Bundle extras = getIntent().getExtras();
		if (extras != null) {
			String url = extras.getString(SCPViewActivity.URL);
			android.content.SharedPreferences settings = getSharedPreferences(getResources().getString( R.string.app_settings_file ), MODE_PRIVATE);
			path = settings.getString(getResources().getString(R.string.app_settings_file_paths_ecg), SCPViewActivity.ROOT_PATH);
			webView.loadUrl(url);
		}
		
	}
	
	private class DownloadFile extends AsyncTask<String, Integer, String> {
		//flag for checking download state
		private boolean working = true;
		
	    @Override
	    protected String doInBackground(String... sUrl) {
	        try {
	            URL url = new URL(sUrl[0]);
	            URLConnection connection = url.openConnection();
	            connection.connect();
	            filename = path + '/' + url.getFile();
			    File f = new File(filename);
	            if (!f.exists()) f.createNewFile();
	            InputStream input = new BufferedInputStream(url.openStream());
	            OutputStream output = new FileOutputStream(f,false);
	            byte data[] = new byte[1024];
	            int count;
	            while ((count = input.read(data)) != -1) output.write(data, 0, count);
	            output.flush();
	            output.close();
	            input.close();
	            working = false;
	        } catch (Exception e) {
	        }
	        return null;
	    }
	    
	    public boolean working() {
	    	return working;
	    }
	}

}
