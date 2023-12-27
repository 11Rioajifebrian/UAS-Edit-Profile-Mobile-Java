package com.rio.editprofile;

import androidx.appcompat.app.AppCompatActivity;

import android.app.DatePickerDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Toast;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Locale;

public class MainActivity extends AppCompatActivity {
    private EditText emailEditText, passwordEditText;
    private RadioGroup genderRadioGroup;
    private CheckBox agreeCheckBox;
    private Button registerButton, dateButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        emailEditText = findViewById(R.id.emailEditText);
        passwordEditText = findViewById(R.id.passwordEditText);
        genderRadioGroup = findViewById(R.id.genderRadioGroup);
        agreeCheckBox = findViewById(R.id.agreeCheckBox);
        registerButton = findViewById(R.id.registerButton);
        dateButton = findViewById(R.id.date_button);

        registerButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (agreeCheckBox.isChecked()) {
                    register();
                } else {
                    Toast.makeText(MainActivity.this, "Please agree to the terms.", Toast.LENGTH_SHORT).show();
                }
            }
        });

        dateButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                showDatePicker();
            }
        });
    }

    private void register() {
        String typedEmail = emailEditText.getText().toString();
        String typedPassword = passwordEditText.getText().toString();

        // Validasi email menggunakan simple regex
        if (!android.util.Patterns.EMAIL_ADDRESS.matcher(typedEmail).matches()) {
            Toast.makeText(this, "Please enter a valid email address.", Toast.LENGTH_SHORT).show();
            return;
        }

        // Validasi panjang password
        if (typedPassword.length() < 8 || typedPassword.length() > 10) {
            Toast.makeText(this, "Password must be between 8 and 10 characters.", Toast.LENGTH_SHORT).show();
            return;
        }

        // Extracting selected gender directly from RadioGroup
        int selectedGenderId = genderRadioGroup.getCheckedRadioButtonId();
        RadioButton selectedGenderRadioButton = findViewById(selectedGenderId);
        String typedGender = selectedGenderRadioButton.getText().toString();

        SharedPreferences sharedPreferences =
                getApplicationContext().getSharedPreferences("PROFILE_SP",
                        Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putString("SAVED_EMAIL", typedEmail);
        editor.putString("SAVED_GENDER", typedGender);
        editor.apply();

        // Pindah ke ProfileActivity dan kirim data
        Intent intent = new Intent(MainActivity.this, Profile.class);
        intent.putExtra("EMAIL", typedEmail);
        intent.putExtra("GENDER", typedGender);
        startActivity(intent);
    }

    private void showDatePicker() {
        final Calendar calendar = Calendar.getInstance();

        DatePickerDialog datePickerDialog = new DatePickerDialog(this);
        datePickerDialog.setOnDateSetListener(new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker datePicker, int year, int month, int day) {
                calendar.set(year, month, day);
                SimpleDateFormat dateFormat = new SimpleDateFormat("dd MMMM yyyy", Locale.US);
                dateButton.setText(dateFormat.format(calendar.getTime()));
            }
        });
        datePickerDialog.show();
    }
}
