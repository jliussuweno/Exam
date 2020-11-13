package com.jliussuweno.exam.view;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProvider;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.Parcelable;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.jliussuweno.exam.R;
import com.jliussuweno.exam.model.User;
import com.jliussuweno.exam.utils.ToastUtils;
import com.jliussuweno.exam.viewmodel.LogInViewModel;

import java.io.Serializable;

public class PasswordActivity extends AppCompatActivity implements Serializable{

    EditText editTextPassword;
    Button buttonLogin;
    String password = "";
    String userId = "";
    Context context;
    User currentUser;
    LogInViewModel logInViewModel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_password);
        editTextPassword = findViewById(R.id.editTextNumberPassword);
        buttonLogin = findViewById(R.id.buttonLogin);
        editTextPassword.addTextChangedListener(textWatcherPassword);
        context = this;

        logInViewModel = new ViewModelProvider(this).get(LogInViewModel.class);

        Intent getIntent = getIntent();
        currentUser = (User) getIntent.getSerializableExtra("user");

        buttonLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                password = editTextPassword.getText().toString().trim();
                if (password.length() > 6){
                    ToastUtils.makeToast(context, "Password harus 6 Karakter dan hanya angka!");
                } else if (password.equals(currentUser.getPassword())){
                    ToastUtils.makeToast(context, "Berhasil!");
                    Intent intent = new Intent(PasswordActivity.this, EditActivity.class);
                    intent.putExtra("user", (Serializable) currentUser);
                    startActivity(intent);
                    finish();
                } else {
                    ToastUtils.makeToast(context, "Password Salah, Silahkan coba lagi!");
                }
            }
        });
    }

    private TextWatcher textWatcherPassword = new TextWatcher() {
        @Override
        public void beforeTextChanged(CharSequence s, int start, int count, int after) {

        }

        @Override
        public void onTextChanged(CharSequence s, int start, int before, int count) {

        }

        @Override
        public void afterTextChanged(Editable s) {
            password = editTextPassword.getText().toString().trim();

            if (password.length() < 6){
                editTextPassword.setError("Password Harus 6 numeric");
            } else {
                editTextPassword.setError(null);
            }

            if (password.length() <= 6){
                if (logInViewModel.checkLogin(currentUser.getName(), password) == 1){
                    ToastUtils.makeToast(context, "Berhasil!");
                    Intent intent = new Intent(PasswordActivity.this, EditActivity.class);
                    intent.putExtra("user", (Serializable) currentUser);
                    startActivity(intent);
                    finish();
                }
            }

            buttonLogin.setEnabled(!password.isEmpty());

        }
    };
}