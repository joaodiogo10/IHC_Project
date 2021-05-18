package com.example.login_register;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

public class Register extends AppCompatActivity {

    private EditText FirstName;
    private EditText LastName;
    private EditText Email;
    private EditText Password;
    private Button CreateAcc;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main2);

        FirstName = (EditText) findViewById(R.id.etFIRSTNAME_2);
        LastName = (EditText) findViewById(R.id.etLASTNAME_2);
        Email = (EditText) findViewById(R.id.etEMAIL_2);
        Password = (EditText) findViewById(R.id.etPASSWORD_2);
        CreateAcc = (Button) findViewById(R.id.CreateAccButton_2);



        CreateAcc.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Register.this, Profile.class);
                startActivity(intent);
                LastName.setText(null);
                FirstName.setText(null);
                Email.setText(null);
                Password.setText(null);
            }
        });

    }

}