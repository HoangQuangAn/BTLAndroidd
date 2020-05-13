package com.panky.foodapp.loginPageActivity;

import android.content.Intent;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.facebook.AccessToken;
import com.facebook.CallbackManager;
import com.facebook.FacebookCallback;
import com.facebook.FacebookException;
import com.facebook.GraphRequest;
import com.facebook.GraphResponse;
import com.facebook.Profile;
import com.facebook.login.LoginManager;
import com.facebook.login.widget.LoginButton;
import com.facebook.login.widget.ProfilePictureView;
import com.panky.foodapp.R;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.Arrays;

public class LoginResult extends AppCompatActivity {

    ProfilePictureView profilePictureView;
    LoginButton loginButton;
    Button btnDangXuat;
    TextView txtName, txtEmail, txtFirstName;
    CallbackManager callbackManager;
    String name, email, firstname;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        callbackManager=CallbackManager.Factory.create();
        setContentView(R.layout.login_result_facebook);
        initView();
        btnDangXuat.setVisibility(View.VISIBLE);
        txtName.setVisibility(View.VISIBLE);
        txtEmail.setVisibility(View.VISIBLE);
        txtFirstName.setVisibility(View.VISIBLE);
       // btnDangXuat.setVisibility(View.VISIBLE);
        loginButton.setReadPermissions(Arrays.asList("public_profile","email"));
        set_loginButton();
        set_logoutButton();
    }

    private void set_logoutButton() {
        btnDangXuat.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                LoginManager.getInstance().logOut();
                btnDangXuat.setVisibility(View.INVISIBLE);
                txtEmail.setText("");
                txtFirstName.setText("");
                txtName.setText("");
                profilePictureView.setProfileId(null);
                loginButton.setVisibility(View.VISIBLE);
            }
        });
    }

    private void set_loginButton() {
        loginButton.registerCallback(callbackManager, new FacebookCallback<com.facebook.login.LoginResult>() {
            @Override
            public void onSuccess(com.facebook.login.LoginResult loginResult) {
                // hàm nhận về kết quả trên server

                loginButton.setVisibility(View.INVISIBLE);
                btnDangXuat.setVisibility(View.VISIBLE);
                txtName.setVisibility(View.VISIBLE);
                txtEmail.setVisibility(View.VISIBLE);
                txtFirstName.setVisibility(View.VISIBLE);
                result();
            }

            @Override
            public void onCancel() {

            }

            @Override
            public void onError(FacebookException error) {

            }
        });
    }

    private void result() {
        GraphRequest graphRequest=GraphRequest.newMeRequest(AccessToken.getCurrentAccessToken(), new GraphRequest.GraphJSONObjectCallback() {
            @Override
            public void onCompleted(JSONObject object, GraphResponse response) {
                Log.d("Json", response.getJSONObject().toString());
                try{
                    email=object.getString("email");
                    name=object.getString("name");
                    firstname=object.getString("first_name");
                    profilePictureView.setProfileId(Profile.getCurrentProfile().getId());
                    txtEmail.setText(email);
                    txtFirstName.setText(firstname);
                    txtName.setText(name);
                }catch (JSONException e){
                    e.printStackTrace();
                }

            }
        });
        Bundle parameters = new Bundle();
        parameters.putString("fields", "name, email, first_name");
        graphRequest.setParameters(parameters);
        graphRequest.executeAsync();
    }

    @Override
    protected void onStart() {
        LoginManager.getInstance().logOut();
        super.onStart();
    }

    private void initView() {
        profilePictureView=findViewById(R.id.friendProfilePicture);
        loginButton=findViewById(R.id.login_button);
        btnDangXuat=(Button)findViewById(R.id.buttonSignout);
        txtEmail=(TextView)findViewById(R.id.textViewEmail);
        txtName=(TextView)findViewById(R.id.textViewName);
        txtFirstName=(TextView)findViewById(R.id.textViewFirstName);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        callbackManager.onActivityResult(requestCode, resultCode, data);
    }
}
