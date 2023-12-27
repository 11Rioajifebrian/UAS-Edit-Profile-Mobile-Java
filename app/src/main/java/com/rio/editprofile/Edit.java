package com.rio.editprofile;

import android.app.DatePickerDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.RadioGroup;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Locale;

public class Edit extends AppCompatActivity {
    private EditText emailEditText, passwordEditText;
    private RadioGroup genderRadioGroup;
    private Button editButton, dateButton;
    private Calendar selectedDate;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit);

        emailEditText = findViewById(R.id.emailEditText);
        passwordEditText = findViewById(R.id.passwordEditText);
        genderRadioGroup = findViewById(R.id.genderRadioGroup);
        editButton = findViewById(R.id.editButton);
        dateButton = findViewById(R.id.date_button);

        selectedDate = Calendar.getInstance();

        editButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // Logic to save changes here
                register();
            }
        });

        dateButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                showDatePickerDialog();
            }
        });
    }

    private void register() {
        String typedEmail = emailEditText.getText().toString();
        String typedPassword = passwordEditText.getText().toString();

        // Validations (Email and Password)

        int selectedGenderId = genderRadioGroup.getCheckedRadioButtonId();

        if (selectedGenderId == -1) {
            Toast.makeText(this, "Please select a gender.", Toast.LENGTH_SHORT).show();
            return;
        }

        // Extracting selected gender directly from RadioGroup
        String typedGender = ((selectedGenderId == R.id.maleRadioButton) ? "Male" : "Female");

        // Save data to SharedPreferences
        SharedPreferences sharedPreferences = getApplicationContext().getSharedPreferences("PROFILE_SP", Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putString("SAVED_EMAIL", typedEmail);
        editor.putString("SAVED_GENDER", typedGender);
        editor.apply();

        // Move to ProfileActivity and send data
        Intent intent = new Intent(Edit.this, Profile.class);
        intent.putExtra("EMAIL", typedEmail);
        intent.putExtra("GENDER", typedGender);
        startActivity(intent);
    }

    private void showDatePickerDialog() {
        DatePickerDialog datePickerDialog = new DatePickerDialog(this);
        datePickerDialog.setOnDateSetListener(new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker datePicker, int year, int month, int day) {
                selectedDate.set(Calendar.YEAR, year);
                selectedDate.set(Calendar.MONTH, month);
                selectedDate.set(Calendar.DAY_OF_MONTH, day);

                SimpleDateFormat dateFormat = new SimpleDateFormat("dd MMMM yyyy", Locale.US);
                dateButton.setText(dateFormat.format(selectedDate.getTime()));
            }
        });

        datePickerDialog.setButton(DialogInterface.BUTTON_NEGATIVE, "Cancel", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int which) {
                if (which == DialogInterface.BUTTON_NEGATIVE) {
                    // Handle cancel button click
                    Toast.makeText(Edit.this, "Date selection canceled", Toast.LENGTH_SHORT).show();
                }
            }
        });

        datePickerDialog.show();
    }
}
