package com.jliussuweno.exam.view;

import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.text.method.HideReturnsTransformationMethod;
import android.text.method.PasswordTransformationMethod;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProvider;

import com.jliussuweno.exam.R;
import com.jliussuweno.exam.viewmodel.LogInViewModel;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class LogInActivity extends AppCompatActivity {

    EditText emailEditText;
    EditText passwordEditText;
    Button loginButton;
    TextView signUpTextView;
    LogInViewModel logInViewModel;
    Toast toast;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        emailEditText = findViewById(R.id.editTextTextEmailAddress);
        passwordEditText = findViewById(R.id.editTextTextPassword);
        loginButton = findViewById(R.id.buttonLogIn);
        signUpTextView = findViewById(R.id.textViewSignUp);
        loginButton.setEnabled(false);
        signUpTextView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(LogInActivity.this, SignUpActivity.class);
                startActivity(intent);
            }
        });

        logInViewModel = new ViewModelProvider(this).get(LogInViewModel.class);

        loginButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String email = emailEditText.getText().toString().trim();
                String password = passwordEditText.getText().toString().trim();

                if (logInViewModel.checkLogin(email,password) == 1) {
                    Intent intent = new Intent(LogInActivity.this, HomeActivity.class);
                    startActivity(intent);
                } else {
                    if (toast != null){
                        toast = null;
                    }
                    toast = Toast.makeText(LogInActivity.this, "Email/Password tidak ditemukan!", Toast.LENGTH_SHORT);
                    toast.show();
                }
            }
        });

        emailEditText.addTextChangedListener(logInTextWatcher);
        passwordEditText.addTextChangedListener(logInTextWatcher);
    }

    private TextWatcher logInTextWatcher = new TextWatcher() {
        @Override
        public void beforeTextChanged(CharSequence s, int start, int count, int after) {

        }

        @Override
        public void onTextChanged(CharSequence s, int start, int before, int count) {

        }

        @Override
        public void afterTextChanged(Editable s) {
            String email = emailEditText.getText().toString().trim();
            String password = passwordEditText.getText().toString().trim();

            if (!isEmailValid(email)){
                emailEditText.setError("Format Email salah, silahkan periksa kembali");
            }

            loginButton.setEnabled(!email.isEmpty() && !password.isEmpty());

        }
    };

    public static boolean isEmailValid(String email) {
        String expression = "^[\\w\\.-]+@([\\w\\-]+\\.)+[A-Z]{2,4}$";
        Pattern pattern = Pattern.compile(expression, Pattern.CASE_INSENSITIVE);
        Matcher matcher = pattern.matcher(email);
        return matcher.matches();
    }

    public void ShowHidePass(View view) {
        if(view.getId()==R.id.show_pass_btn){
            if(passwordEditText.getTransformationMethod().equals(PasswordTransformationMethod.getInstance())){
                ((ImageView)(view)).setImageResource(R.drawable.ic_baseline_visibility_24);
                passwordEditText.setTransformationMethod(HideReturnsTransformationMethod.getInstance());
            }
            else{
                ((ImageView)(view)).setImageResource(R.drawable.ic_outline_visibility_off_24);
                passwordEditText.setTransformationMethod(PasswordTransformationMethod.getInstance());
            }
        }
    }
}