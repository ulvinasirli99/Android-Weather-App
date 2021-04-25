package com.kivitool.theweatherchannel2020.activitys;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.os.Bundle;
import android.util.Log;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;

import com.kivitool.theweatherchannel2020.R;

public class RadarActivity extends AppCompatActivity {

    private static final String TAG = "RadarActivity";
    private Context context;
    private WebView webView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_radar);

        context = this;

        webView = findViewById(R.id.radarAct);

        Log.d(TAG, "onCreate: RadarMap started is service");


        webView.setWebViewClient(new WebViewClient());
        webView.loadUrl("https://openweathermap.org/weathermap?basemap=map&cities=true&layer=windspeed&lat=40.4093&lon=49.8671&zoom=5");
        WebSettings webSettings = webView.getSettings();
        webSettings.setJavaScriptEnabled(true);

    }

    @Override
    public void onBackPressed() {

        if (webView.canGoBack()){

            webView.goBack();

        }else {

            super.onBackPressed();

        }
    }

}