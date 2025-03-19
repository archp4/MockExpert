package com.group7.mockexpert;

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

import java.text.SimpleDateFormat;
import java.util.Calendar;

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
            Intent intent = new Intent(SignUp.this, SignUp2.class);
            startActivity(intent);  // Start Signup2 activity
        }
    }

    private void setupDateOfBirthPicker() {
        etDOB.setOnClickListener(v -> {
            Calendar calendar = Calendar.getInstance();
            int year = calendar.get(Calendar.YEAR);
            int month = calendar.get(Calendar.MONTH);
            int dayOfMonth = calendar.get(Calendar.DAY_OF_MONTH);

            DatePickerDialog datePickerDialog = new DatePickerDialog(SignUp.this, (view, selectedYear, selectedMonth, selectedDayOfMonth) -> {
                Calendar selectedDate = Calendar.getInstance();
                selectedDate.set(selectedYear, selectedMonth, selectedDayOfMonth);
                SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
                String formattedDate = sdf.format(selectedDate.getTime());
                etDOB.setText(formattedDate);
            }, year, month, dayOfMonth);

            datePickerDialog.show();
        });
    }

    private void setupGenderSpinner() {
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this, R.array.gender_options, R.layout.spinner_dropdown_item_gender);
        adapter.setDropDownViewResource(R.layout.spinner_dropdown_item_gender);
        spinnerGender.setAdapter(adapter);
        spinnerGender.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parentView, View selectedItemView, int position, long id) {
                String selectedGender = parentView.getItemAtPosition(position).toString();
                if (!selectedGender.equals("Select Gender")) {
                    Toast.makeText(SignUp.this, "Selected Gender: " + selectedGender, Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parentView) {}
        });
    }

    private boolean validateFields() {
        String fullName = etFullname.getText().toString().trim();
        String email = etEmail.getText().toString().trim();
        String phone = etPhone.getText().toString().trim();
        String dob = etDOB.getText().toString().trim();

        if (fullName.isEmpty()) {
            etFullname.setError("Full name is required!");
            return false;
        }

        if (email.isEmpty()) {
            etEmail.setError("Email is required!");
            return false;
        }

        if(!isValidateEmail(email)){
            etEmail.setError("Email is not in valid format");
        }

        if (phone.isEmpty()) {
            etPhone.setError("Phone number is required!");
            return false;
        }

        if (phone.length() < 10) {
            etPhone.setError("Phone number must have 10 numbers!");
            return false;
        }

        return true;
    }

    private boolean isValidateEmail(String email) {
        String emailPattern = "[a-zA-Z0-9._-]+@[a-zA-Z0-9._-]+\\.[a-zA-Z]{2,}";
        return email.matches(emailPattern);
    }


}
