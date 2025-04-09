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

public class SignUp2 extends AppCompatActivity {

    private EditText etUsername, etPassword, etConfirmPassword;
    private CheckBox checkboxTerms;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_signup2);

        etUsername = findViewById(R.id.et_username);
        etPassword = findViewById(R.id.et_pwd);
        etConfirmPassword = findViewById(R.id.et_confirmPwd);
        checkboxTerms = findViewById(R.id.check_box1);
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
        if (validateFields()) {
            Intent intent = new Intent(this, Dashboard.class);
            startActivity(intent);
        }
    }
}
