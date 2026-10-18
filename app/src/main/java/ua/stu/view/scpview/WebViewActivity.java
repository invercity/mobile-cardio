package ua.stu.view.scpview;

import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.URL;
import java.net.URLConnection;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.Toast;

public class WebViewActivity extends AppCompatActivity {
	
	private WebView webView;
	private final ExecutorService executor = Executors.newSingleThreadExecutor();
	 
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.webview);
		webView = findViewById(R.id.webView1);
		webView.getSettings().setJavaScriptEnabled(true);
		webView.setWebViewClient(new WebViewClient() {
			@Override 
			public boolean shouldOverrideUrlLoading(WebView view, String url) { 
				downloadAndFinish(url);
				return true; 
			} 
		});
		
		Bundle extras = getIntent().getExtras();
		if (extras != null) {
			String url = extras.getString(SCPViewActivity.URL);
			if (url != null) {
				webView.loadUrl(url);
			}
		}
	}
	
	private void downloadAndFinish(String urlStr) {
		executor.execute(new Runnable() {
			@Override
			public void run() {
				try {
					URL url = new URL(urlStr);
					URLConnection connection = url.openConnection();
					connection.connect();
					
					String fileNameOnly = "downloaded_ecg.scp";
					String pathName = url.getPath();
					if (pathName != null && pathName.contains("/")) {
						String lastPart = pathName.substring(pathName.lastIndexOf('/') + 1);
						if (!lastPart.isEmpty()) {
							fileNameOnly = lastPart;
						}
					}
					
					File destFile = new File(getCacheDir(), fileNameOnly);
					try (InputStream input = new BufferedInputStream(url.openStream());
						 OutputStream output = new FileOutputStream(destFile, false)) {
						byte[] data = new byte[8192];
						int count;
						while ((count = input.read(data)) != -1) {
							output.write(data, 0, count);
						}
						output.flush();
					}
					
					runOnUiThread(new Runnable() {
						@Override
						public void run() {
							Intent intent = new Intent();
							intent.putExtra(SCPViewActivity.FILE, destFile.getAbsolutePath());
							setResult(RESULT_OK, intent);
							finish();
						}
					});
				} catch (Exception e) {
					runOnUiThread(new Runnable() {
						@Override
						public void run() {
							Toast.makeText(WebViewActivity.this, "Download failed: " + e.getMessage(), Toast.LENGTH_SHORT).show();
						}
					});
				}
			}
		});
	}

	@Override
	protected void onDestroy() {
		super.onDestroy();
		executor.shutdown();
	}
}
