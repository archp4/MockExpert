package com.group7.mockexpert;

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

public class SignUp2 extends AppCompatActivity {

    private EditText etUsername, etPassword, etConfirmPassword;
    private Spinner spinnerTestType;
    private CheckBox checkboxTerms;
    private Button btnRegister, btnBack;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_signup2);

        etUsername = findViewById(R.id.et_username);
        etPassword = findViewById(R.id.et_pwd);
        etConfirmPassword = findViewById(R.id.et_confirmPwd);
        spinnerTestType = findViewById(R.id.spinner_testypes);
        checkboxTerms = findViewById(R.id.check_box1);
        btnRegister = findViewById(R.id.btn_Register);
        btnBack = findViewById(R.id.btn_back);

        // Spinner setup for Test Type
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this, R.array.test_types, R.layout.spinner_test_type);
        adapter.setDropDownViewResource(R.layout.spinner_test_type);
        spinnerTestType.setAdapter(adapter);
        spinnerTestType.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parentView, View selectedItemView, int position, long id) {
                String selectedTestType = parentView.getItemAtPosition(position).toString();
                Toast.makeText(SignUp2.this, "Selected Test Type: " + selectedTestType, Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onNothingSelected(AdapterView<?> parentView) {}
        });

        btnRegister.setOnClickListener(v -> {
            if (validateFields()) {
                Toast.makeText(SignUp2.this, "Registration Successful!", Toast.LENGTH_SHORT).show();
            }
        });

        btnBack.setOnClickListener(v -> onBackPressed());
    }

    private boolean validateFields() {
        String username = etUsername.getText().toString().trim();
        String password = etPassword.getText().toString().trim();
        String confirmPassword = etConfirmPassword.getText().toString().trim();

        if (username.isEmpty()) {
            etUsername.setError("Username is required!");
            return false;
        }

        if (password.isEmpty()) {
            etPassword.setError("Password is required!");
            return false;
        }

        if(!isValidPassword(password)){
            etPassword.setError("Password must contain at least 1 uppercase, 1 lowercase and 1 special character");
        }
        if (confirmPassword.isEmpty()) {
            etConfirmPassword.setError("Confirm password is required!");
            return false;
        }

        if (!password.equals(confirmPassword)) {
            etConfirmPassword.setError("Passwords do not match!");
            return false;
        }

        if (!checkboxTerms.isChecked()) {
            Toast.makeText(SignUp2.this, "You must agree to the terms and conditions!", Toast.LENGTH_SHORT).show();
            return false;
        }

        return true;
    }

    private boolean isValidPassword(String password) {
        String passwordPattern = "^(?=.*[A-Z])(?=.*\\d)(?=.*[@#$%^&+=!]).{6,}$";
        return password.matches(passwordPattern);
    }

    public void openLoginPage(View view) {
    }
}
