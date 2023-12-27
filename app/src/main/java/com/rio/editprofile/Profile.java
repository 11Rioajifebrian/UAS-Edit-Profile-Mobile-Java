package com.rio.editprofile;

import androidx.appcompat.app.AppCompatActivity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

public class Profile extends AppCompatActivity {
    private TextView emailTextView, genderTextView;
    private static final int EDIT_ACTIVITY_REQUEST_CODE = 1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);
        emailTextView = findViewById(R.id.emailTextView);
        genderTextView = findViewById(R.id.genderTextView);

        // Retrieve data from Intent
        Intent intent = getIntent();
        if (intent != null) {
            String savedEmail = intent.getStringExtra("EMAIL");
            String savedGender = intent.getStringExtra("GENDER");

            emailTextView.setText(savedEmail);
            genderTextView.setText(savedGender);
        }
    }

    public void launchEditActivity(View view) {
        Intent intent = new Intent(Profile.this, Edit.class);
        intent.putExtra("EMAIL", emailTextView.getText().toString());
        intent.putExtra("GENDER", genderTextView.getText().toString());
        startActivityForResult(intent, EDIT_ACTIVITY_REQUEST_CODE);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == EDIT_ACTIVITY_REQUEST_CODE && resultCode == RESULT_OK && data != null) {
            // Update data after Edit Activity is finished
            String updatedEmail = data.getStringExtra("UPDATED_EMAIL");
            String updatedGender = data.getStringExtra("UPDATED_GENDER");

            if (updatedEmail != null && updatedGender != null) {
                emailTextView.setText(updatedEmail);
                genderTextView.setText(updatedGender);
            }
        }
    }
}
