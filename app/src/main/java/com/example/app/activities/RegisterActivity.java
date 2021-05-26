package com.example.app.activities;

import androidx.appcompat.app.AppCompatActivity;

import android.app.AlertDialog;
import android.app.DatePickerDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.TextView;

import com.example.app.R;

import java.sql.Date;
import java.util.Calendar;

public class RegisterActivity extends AppCompatActivity {

    private EditText FirstName;
    private EditText LastName;
    private EditText Email;
    private EditText Password;
    private Button CreateAcc;
    private DatePickerDialog DatePickerDialog;
    private TextView dateButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        FirstName = (EditText) findViewById(R.id.etFIRSTNAME_2);
        LastName = (EditText) findViewById(R.id.etLASTNAME_2);
        Email = (EditText) findViewById(R.id.etEMAIL_2);
        Password = (EditText) findViewById(R.id.etPASSWORD_2);
        CreateAcc = (Button) findViewById(R.id.CreateAccButton_2);
        dateButton = (TextView) findViewById(R.id.DatePickerButton);
        initDatePicker();
        dateButton.setText(null);

        CreateAcc.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(RegisterActivity.this, MainActivity.class);
                startActivity(intent);
                LastName.setText(null);
                FirstName.setText(null);
                Email.setText(null);
                Password.setText(null);
            }
        });
    }

    private void initDatePicker(){
        DatePickerDialog.OnDateSetListener dateSetListener = new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
                String date = makeDateString(dayOfMonth, month, year);
                dateButton.setText(date);
            }
        };
        Calendar cal = Calendar.getInstance();
        int year = cal.get(Calendar.YEAR);
        int month = cal.get(Calendar.MONTH);
        int day = cal.get(Calendar.DAY_OF_MONTH);

        int style = AlertDialog.THEME_HOLO_LIGHT;

        DatePickerDialog = new DatePickerDialog(this, style, dateSetListener, year, month, day);
        DatePickerDialog.getDatePicker().setMaxDate(System.currentTimeMillis());
    }

    private String makeDateString(int day, int month, int year){
        return day + " - " + getMonthFormat(month) + " - " + year;
    }

    private String getMonthFormat(int month){
        switch (month){
            case 1:
                return "JAN";
            case 2:
                return "FEB";
            case 3:
                return "MAR";
            case 4:
                return "APR";
            case 5:
                return "MAY";
            case 6:
                return "JUN";
            case 7:
                return "JUL";
            case 8:
                return "AUG";
            case 9:
                return "SEP";
            case 10:
                return "OCT";
            case 11:
                return "NOV";
            case 12:
                return "DEC";
            default:
                break;
        }
        return "";
    }

    public void openDatePicker(View view) {
        DatePickerDialog.show();
    }
}