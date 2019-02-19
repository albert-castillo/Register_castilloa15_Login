package com.example.register_castilloa15_login;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class HomeActivity extends AppCompatActivity {

    Button rButton,lButton;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);


        rButton=(Button)findViewById(R.id.reg);
        lButton=(Button)findViewById(R.id.log);



    }
    //moves user to the Log in screen if they click the Log In button
    public void moveToLog(View view) {
        startActivity(new Intent(HomeActivity.this, LogIn.class));
    }

    //moves user to the Register screen if they click the Register button
    public void moveToReg(View view) {
        startActivity(new Intent(HomeActivity.this, Register.class));
    }





}
