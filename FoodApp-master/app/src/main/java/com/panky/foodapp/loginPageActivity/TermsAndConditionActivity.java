package com.panky.foodapp.loginPageActivity;

import android.content.res.AssetManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.webkit.WebView;

import com.panky.foodapp.R;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;

public class TermsAndConditionActivity extends AppCompatActivity {

    private Toolbar customToolbar;
    private WebView webView;
    private static final String TAG = "tag";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_terms_and_condition);

        webView  = findViewById(R.id.webview);
        customToolbar = findViewById(R.id.customToolbar);
        setSupportActionBar(customToolbar);
        if(getSupportActionBar() != null){
            getSupportActionBar().setTitle(R.string.termsConditionText);
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        }


        try {
            AssetManager assetManager = this.getAssets();
            InputStream stream = assetManager.open("terms.html");
            BufferedReader r = new BufferedReader(new InputStreamReader(stream));
            StringBuilder total = new StringBuilder();
            String line;
            while ((line = r.readLine()) != null) {
                total.append(line).append("\n");
            }
            webView.loadDataWithBaseURL(null, total.toString(), "text/html", "UTF-8", null);
        } catch (Exception xxx) {
            Log.e(TAG, "Load assets/page.html", xxx);
        }
    }
}
