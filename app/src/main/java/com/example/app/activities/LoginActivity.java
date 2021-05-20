package com.example.app.activities;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;

import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.app.R;


public class LoginActivity extends AppCompatActivity {

    private EditText email;
    private EditText password;
    private Button login;
    private Button register;
    private int attempts = 5;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        email = (EditText) findViewById(R.id.etEMAIL);
        password = (EditText) findViewById(R.id.etPASSWORD);
        login = (Button) findViewById(R.id.LogInButton);
        register = (Button) findViewById(R.id.RegisterButton);


        login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                boolean a = validateAcc(email.getText().toString(), password.getText().toString());
                if (a) {
                    Toast.makeText(LoginActivity.this, "Welcome!", Toast.LENGTH_SHORT).show();
                    Intent intent = new Intent(LoginActivity.this, MainActivity.class);
                    startActivity(intent);
                }else{
                    Toast.makeText(LoginActivity.this, "Incorrect credentials! Try again", Toast.LENGTH_LONG).show();
                }
            }
        });

        register.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(LoginActivity.this, RegisterActivity.class);
                intent.putExtra("username", email.getText().toString());
                startActivity(intent);
            }
        });
    }
    public boolean validateAcc(String email, String password){
        if(email.equals("admin") && password.equals("admin")){
            return true;
        }
        return false;
    }
}