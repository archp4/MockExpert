package com.group7.mockexpert;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;

import com.group7.mockexpert.models.SharedPreferencesManager;

public class Login extends AppCompatActivity implements LoginListener {

    private EditText etEmail, etPassword;
    private CheckBox checkBoxRememberMe;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_login);
        etEmail = findViewById(R.id.et_email1);
        etPassword = findViewById(R.id.et_pwd1);
        checkBoxRememberMe = findViewById(R.id.check_box1);
        validateLoginSession();
    }

    private void validateLoginSession(){
        boolean temp = SharedPreferencesManager.validateLogin(this);
        if (temp)
        {
            Toast.makeText(this,"LoggedIn",Toast.LENGTH_SHORT).show();
            Intent intent = new Intent(this, Dashboard.class);
            startActivity(intent);
        }
    }

    public void btn_login(View view) {

        String email = etEmail.getText().toString().trim();
        String password = etPassword.getText().toString().trim();
        boolean isSave = checkBoxRememberMe.isChecked();
        if (email.isEmpty()){
            Toast.makeText(this, "Username is required.", Toast.LENGTH_SHORT).show();
            return;
        }

        if (password.isEmpty()){
            Toast.makeText(this, "Password is required.", Toast.LENGTH_SHORT).show();
            return;
        }
        LoginService loginService = new LoginService();
        loginService.loginListener = this;
        loginService.onLogin(email,password,isSave,Login.this);

    }

    public void openSignUpPage(View view) {
        Intent intent = new Intent(Login.this, SignUp.class);
        startActivity(intent);
    }

    @Override
    public void onLoginSuccessful() {
        Intent intent = new Intent(Login.this, Dashboard.class);
        startActivity(intent);
    }

    @Override
    public void onLoginError() {

    }
}