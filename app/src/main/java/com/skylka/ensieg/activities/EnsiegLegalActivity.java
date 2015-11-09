package com.skylka.ensieg.activities;

import org.json.JSONException;
import org.json.JSONObject;

import com.google.gson.Gson;
import com.skylka.ensieg.R;

import android.app.Activity;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.webkit.WebChromeClient;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.ImageView;
import android.widget.ProgressBar;

public class EnsiegLegalActivity extends Activity implements OnClickListener {
	ImageView back;
	WebView webview;
	String path;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_legal);
		path = "http://www.ensieg.com/";
		back = (ImageView) findViewById(R.id.legal_back);
		back.setOnClickListener(this);
		webview = (WebView) findViewById(R.id.ensieg_webview);

		// progressBar.setProgress(0);
		
		  String[] arr={"1","2","3"};
		  JSONObject obj=new JSONObject();
		  try {
			obj.put("arr", arr);
		} catch (JSONException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} //
		 
		System.out.println(new Gson().toJson(obj)+">>>>>>>>>>>>>>>>>");  ;
		 

		webview.getSettings().setJavaScriptEnabled(true);
		webview.getSettings().setBuiltInZoomControls(true);
		webview.getSettings().setDisplayZoomControls(false);
		webview.getSettings().setLoadWithOverviewMode(true);
		webview.getSettings().setUseWideViewPort(true);

		// webview.setInitialScale(10);
		webview.setScrollbarFadingEnabled(false);

		/*
		 * String html = "<html><head></head><body><img src=\"" + path +
		 * "\" width=\"100%\" height=\"100%\"\"></body></html>";
		 */
		webview.setWebViewClient(new WebViewClient() {
			@Override
			public void onPageStarted(WebView view, String url, Bitmap favicon) {
				super.onPageStarted(view, url, favicon);
				// Show progressbar
			}

			@Override
			public void onReceivedError(WebView view, int errorCode, String description, String failingUrl) {
				// Show error
				// Stop spinner or progressbar

			}

			@Override
			public void onPageFinished(WebView view, String url) {
				super.onPageFinished(view, url);
				// Stop spinner or progressBar

			}
		});

		webview.setScrollBarStyle(WebView.SCROLLBARS_OUTSIDE_OVERLAY);

		webview.setWebChromeClient(new WebChromeClient() {

			public void onProgressChanged(WebView view, int progress) {

				/*
				 * progressBar.setProgress(progress); if (progress == 50) {
				 * progressBar.setVisibility(View.GONE); }
				 */
			}
		});

		webview.loadUrl(path);

	}

	@Override
	public void onClick(View v) {
		switch (v.getId()) {
		case R.id.legal_back:
			EnsiegLegalActivity.this.finish();
			break;

		default:
			break;
		}

	}

}
