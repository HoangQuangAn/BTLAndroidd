package com.panky.foodapp.loginPageActivity;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;

import com.panky.foodapp.R;

public class SupportActivity extends AppCompatActivity {

    private Toolbar customToolbar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_support);

        customToolbar = findViewById(R.id.customToolbar);
        setSupportActionBar(customToolbar);
        if(getSupportActionBar() != null){
            getSupportActionBar().setTitle(R.string.supportText);
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        }
    }
}
