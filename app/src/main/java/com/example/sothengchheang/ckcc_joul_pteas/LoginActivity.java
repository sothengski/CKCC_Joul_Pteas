package com.example.sothengchheang.ckcc_joul_pteas;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.facebook.AccessToken;
import com.facebook.login.widget.LoginButton;

public class LoginActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //check login status with facebook
        if(AccessToken.getCurrentAccessToken() != null){
            Intent intent = new Intent(this,HomeFragment.class);
            startActivity(intent);
            finish();
            return;
        }
        setContentView(R.layout.signin_signup);

        LoginButton btnFacebookLogin = findViewById(R.id.login_fb_button);
    }

}
