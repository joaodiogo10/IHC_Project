package com.example.login_register;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.google.android.material.textfield.TextInputEditText;


public class Profile extends AppCompatActivity {

    private TextInputEditText FirstName;
    private TextInputEditText LastName;
    private TextInputEditText Email;
    private TextInputEditText Password;
    private Button Logout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);

        FirstName = (TextInputEditText) findViewById(R.id.et_profile_FirstName);
        LastName = (TextInputEditText) findViewById(R.id.et_profile_LastName);
        Email = (TextInputEditText) findViewById(R.id.et_profile_Email);
        Password = (TextInputEditText) findViewById(R.id.et_profile_Password);
        Logout = (Button) findViewById(R.id.Profile_Logout_Button);

        Logout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Profile.this, Login.class);
                startActivity(intent);
            }
        });
    }
}