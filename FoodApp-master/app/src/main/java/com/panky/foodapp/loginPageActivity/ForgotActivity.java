package com.panky.foodapp.loginPageActivity;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.CardView;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.panky.foodapp.R;

public class ForgotActivity extends AppCompatActivity implements View.OnClickListener {

    private Toolbar customToolbar;
    private CardView cvSendOtp;
    private EditText etYourMobile;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_forgot);

        initViews();
        setSupportActionBar(customToolbar);

        if(getSupportActionBar() != null){
            getSupportActionBar().setTitle(R.string.forgotTextToolbar);
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        }

        cvSendOtp.setOnClickListener(this);
    }

    private void initViews() {
        customToolbar = findViewById(R.id.customToolbar);
        cvSendOtp = findViewById(R.id.cvSendOtp);
        etYourMobile = findViewById(R.id.etYourMobile);
    }

    @Override
    public void onClick(View v) {
        Toast.makeText(ForgotActivity.this, "Process this task.....", Toast.LENGTH_SHORT).show();
    }
}
