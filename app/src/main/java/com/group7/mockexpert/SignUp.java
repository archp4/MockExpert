package com.group7.mockexpert;

import android.annotation.SuppressLint;
import android.app.DatePickerDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.google.android.material.datepicker.MaterialDatePicker;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

public class SignUp extends AppCompatActivity {

    private EditText etFullname, etEmail, etPhone, etDOB;
    private Spinner spinnerGender;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_signup);

        etFullname = findViewById(R.id.et_fullName);
        etEmail = findViewById(R.id.et_email);
        etPhone = findViewById(R.id.et_Phone);
        etDOB = findViewById(R.id.et_dob);
        spinnerGender = findViewById(R.id.spinner_gender);
        setupDateOfBirthPicker();
        setupGenderSpinner();
    }

    public void openNextPage(View view) {
        if (validateFields()) {
            Intent intent = new Intent(this, SignUp2.class);
            startActivity(intent);
        }
    }

    private void setupDateOfBirthPicker() {
        etDOB.setOnClickListener(v -> {
            MaterialDatePicker<Long> materialDatePicker = MaterialDatePicker.Builder.datePicker()
                    .setSelection(MaterialDatePicker.todayInUtcMilliseconds()) // Set default to today
                    .build();

            materialDatePicker.addOnPositiveButtonClickListener(selection -> {
                SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy", Locale.getDefault());
                String formattedDate = sdf.format(new Date(selection));
                etDOB.setText(formattedDate);
            });

            // Show the date picker dialog
            materialDatePicker.show(getSupportFragmentManager(), "MATERIAL_DATE_PICKER");
        });
    }

    private void setupGenderSpinner() {
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this, R.array.gender_options, R.layout.spinner_dropdown_item_gender);
        adapter.setDropDownViewResource(R.layout.spinner_dropdown_item_gender);
        spinnerGender.setAdapter(adapter);
    }

    private boolean validateFields() {
        String fullName = etFullname.getText().toString().trim();
        String email = etEmail.getText().toString().trim();
        String phone = etPhone.getText().toString().trim();

        if (fullName.isEmpty()) {
            Toast.makeText(this, "Full name is required.", Toast.LENGTH_SHORT).show();
            return false;
        }

        if (email.isEmpty()) {
            Toast.makeText(this, "Email is required.", Toast.LENGTH_SHORT).show();
            return false;
        }

        if(!isValidateEmail(email)){
            Toast.makeText(this, "Email is not in valid format.", Toast.LENGTH_SHORT).show();
        }

        if (phone.isEmpty()) {
            Toast.makeText(this, "Phone number is required.", Toast.LENGTH_SHORT).show();
            return false;
        }

        if (phone.length() < 10) {
            Toast.makeText(this, "Phone number must have 10 digits.", Toast.LENGTH_SHORT).show();
            return false;
        }

        return true;
    }

    private boolean isValidateEmail(String email) {
        String emailPattern = "[a-zA-Z0-9._-]+@[a-zA-Z0-9._-]+\\.[a-zA-Z]{2,}";
        return email.matches(emailPattern);
    }


    public void openLoginPage(View view) {
        Intent intent = new Intent(this, Login.class);
        startActivity(intent);
    }
}


