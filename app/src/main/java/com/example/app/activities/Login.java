package com.example.login_register;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;

import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;


public class Login extends AppCompatActivity {

    private EditText Email;
    private EditText Password;
    private Button Login;
    private Button Register;
    private int attempts = 5;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Email = (EditText) findViewById(R.id.etEMAIL);
        Password = (EditText) findViewById(R.id.etPASSWORD);
        Login = (Button) findViewById(R.id.LogInButton);
        Register = (Button) findViewById(R.id.RegisterButton);


        Login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                validateAcc(Email.getText().toString(), Password.getText().toString());
            }
        });

        Register.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Login.this, Register.class);
                intent.putExtra("username", Email.getText().toString());
                startActivity(intent);
            }
        });
    }

    public void validateAcc(String email, String password){

        if(email.equals("admin") && password.equals("admin")){
            Toast.makeText(getApplicationContext(), "Login Successfully!", Toast.LENGTH_LONG).show();
            Intent intent = new Intent(Login.this, MainMenuActivity.class);
            startActivity(intent);
        }else{
            Password.setText(null);
            Email.setText(null);
            Toast.makeText(getApplicationContext(), "Email or Password incorrect. Attempts Left: "+attempts, Toast.LENGTH_LONG).show();
            attempts--;

            if(attempts==0){
                Login.setEnabled(false);
            }
        }
    }
}