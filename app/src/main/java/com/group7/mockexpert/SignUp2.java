package com.group7.mockexpert;

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

import com.group7.mockexpert.api_helpers.SignUpListener;
import com.group7.mockexpert.api_helpers.SignupService;

public class SignUp2 extends AppCompatActivity implements SignUpListener {

    private EditText etUsername, etPassword, etConfirmPassword;
    private CheckBox checkboxTerms;
    private String fullName, email, phone, dob, gender;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_signup2);
        etUsername = findViewById(R.id.et_username);
        etPassword = findViewById(R.id.et_pwd);
        etConfirmPassword = findViewById(R.id.et_confirmPwd);
        checkboxTerms = findViewById(R.id.check_box1);
        Intent intent = getIntent();
        fullName=intent.getStringExtra("fullName");
        email= intent.getStringExtra("email");
        phone= intent.getStringExtra("phone");
        dob= intent.getStringExtra("dob");
    }

    private boolean validateFields() {
        String username = etUsername.getText().toString().trim();
        String password = etPassword.getText().toString().trim();
        String confirmPassword = etConfirmPassword.getText().toString().trim();

        if (username.isEmpty()) {
            Toast.makeText(this, "Enter username", Toast.LENGTH_SHORT).show();
        }

        if (password.isEmpty()) {
            Toast.makeText(this, "Password is required", Toast.LENGTH_SHORT).show();
            return false;
        }

        if(!isValidPassword(password)){
            Toast.makeText(this, "Password must contain at least 1 uppercase, 1 lowercase and 1 special character", Toast.LENGTH_SHORT).show();
            return false;
        }
        if (confirmPassword.isEmpty()) {
            Toast.makeText(this, "Confirm password is required", Toast.LENGTH_SHORT).show();
            return false;
        }

        if (!password.equals(confirmPassword)) {
            Toast.makeText(this, "Password do not match", Toast.LENGTH_SHORT).show();
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
        Intent intent = new Intent(this, Login.class);
        startActivity(intent);
    }

    public void openDashboard(View view) {
        String username = etUsername.getText().toString().trim();
        String password = etPassword.getText().toString().trim();
        if (validateFields()) {
            SignupService signupService = new SignupService(this);
            signupService.registerUser(fullName, email, phone, username, password, this);
        }
    }

    @Override
    public void onSignUpSuccess() {
        Intent intent = new Intent(this, Dashboard.class);
        startActivity(intent);
    }

    @Override
    public void onSignUpError() {

    }
}
