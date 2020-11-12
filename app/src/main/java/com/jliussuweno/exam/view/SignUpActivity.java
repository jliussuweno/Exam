package com.jliussuweno.exam.view;

import android.content.Intent;
import android.graphics.Bitmap;
import android.media.ExifInterface;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.provider.MediaStore;
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
import com.jliussuweno.exam.model.PasswordStrength;
import com.jliussuweno.exam.model.User;
import com.jliussuweno.exam.utils.ImageUtils;
import com.jliussuweno.exam.viewmodel.SignUpViewModel;

import java.io.IOException;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import static android.widget.Toast.LENGTH_SHORT;

public class SignUpActivity extends AppCompatActivity {

    EditText fullNameEditText;
    EditText usernameSignUpEditText;
    EditText passwordSignUpEditText;
    EditText confirmPasswordEditText;
    Button signUpButton;
    Button button_photo;
    Button button_gallery;
    TextView logInTextView;
    TextView strengthPassword;
    ImageView profilePicture;
    Toast toast;
    SignUpViewModel signUpViewModel;
    String imagePath = "";
    String name = "";
    String username = "";
    String password = "";
    String confirmPassword = "";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up);

        fullNameEditText = findViewById(R.id.editTextTextFullName);
        usernameSignUpEditText = findViewById(R.id.editTextTextUsernameSignUp);
        passwordSignUpEditText = findViewById(R.id.editTextTextPasswordSignUp);
        confirmPasswordEditText = findViewById(R.id.editTextTextConfirmPassword);
        button_gallery = findViewById(R.id.button_gallery);
        button_photo = findViewById(R.id.button_photo);
        signUpButton = findViewById(R.id.buttonSignUp);
        logInTextView = findViewById(R.id.textViewLogIn);
        strengthPassword = findViewById(R.id.password_strength);
        profilePicture = findViewById(R.id.imageViewSignUp);
        signUpButton.setEnabled(false);

        logInTextView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(SignUpActivity.this, LogInActivity.class);
                startActivity(intent);
            }
        });
        fullNameEditText.addTextChangedListener(signUpTextWatcherName);
        usernameSignUpEditText.addTextChangedListener(signUpTextWatcherUsername);
        passwordSignUpEditText.addTextChangedListener(signUpTextWatcherPassword);
        confirmPasswordEditText.addTextChangedListener(signUpTextWatcherPassword);

        signUpViewModel = new ViewModelProvider(this).get(SignUpViewModel.class);


        button_photo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent takePicture = new Intent(android.provider.MediaStore.ACTION_IMAGE_CAPTURE);
                startActivityForResult(takePicture, 0);
            }
        });

        button_gallery.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent pickPhoto = new Intent(Intent.ACTION_PICK, android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
                startActivityForResult(pickPhoto, 1);
            }
        });

        signUpButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String fullName = fullNameEditText.getText().toString().trim();
                String email = usernameSignUpEditText.getText().toString().trim();
                String password = passwordSignUpEditText.getText().toString().trim();

                User user = new User(fullName, email, password);
                if (signUpViewModel.checkUser(user)){
                    signUpViewModel.insertUser(user);
                    if (toast != null){
                        toast = null;
                    }
                    toast = Toast.makeText(SignUpActivity.this, "Email Berhasil Didaftarkan, silahkan Log In!", Toast.LENGTH_LONG);
                    toast.show();
                    Intent intent = new Intent(SignUpActivity.this, LogInActivity.class);
                    startActivity(intent);
                } else {
                    if (toast != null){
                        toast = null;
                    }
                    toast = Toast.makeText(SignUpActivity.this, "Email Sudah Terdaftar, silahkan gunakan email yang lain!", LENGTH_SHORT);
                    toast.show();
                }

            }
        });

    }

    private TextWatcher signUpTextWatcherName = new TextWatcher() {
        @Override
        public void beforeTextChanged(CharSequence s, int start, int count, int after) {

        }

        @Override
        public void onTextChanged(CharSequence s, int start, int before, int count) {

        }

        @Override
        public void afterTextChanged(Editable s) {
            name = fullNameEditText.getText().toString().trim();

            if (name.length() < 6){
                fullNameEditText.setError("Nama Harus lebih dari 6 Character atau sama dengan 6 Character");
            } else if (name.length() > 25){
                fullNameEditText.setError("Nama Tidak Maksimal 25 Character");
            } else {
                fullNameEditText.setError(null);
            }

            signUpButton.setEnabled(!name.isEmpty() &&
                    !username.isEmpty() &&
                    !password.isEmpty() &&
                    !confirmPassword.isEmpty() &&
                    !imagePath.isEmpty());
        }
    };


    private TextWatcher signUpTextWatcherUsername = new TextWatcher() {
        @Override
        public void beforeTextChanged(CharSequence s, int start, int count, int after) {

        }

        @Override
        public void onTextChanged(CharSequence s, int start, int before, int count) {

        }

        @Override
        public void afterTextChanged(Editable s) {
            username = usernameSignUpEditText.getText().toString().trim();

            if (username.length() < 6){
                usernameSignUpEditText.setError("Username Harus lebih dari 6 Character atau sama dengan 6 Character");
            } else if (username.length() > 25){
                usernameSignUpEditText.setError("Nama Tidak Maksimal 25 Character");
            } else {
                usernameSignUpEditText.setError(null);
            }

            signUpButton.setEnabled(!name.isEmpty() &&
                    !username.isEmpty() &&
                    !password.isEmpty() &&
                    !confirmPassword.isEmpty() &&
                    !imagePath.isEmpty());
        }
    };

    private TextWatcher signUpTextWatcherPassword = new TextWatcher() {
        @Override
        public void beforeTextChanged(CharSequence s, int start, int count, int after) {

        }

        @Override
        public void onTextChanged(CharSequence s, int start, int before, int count) {

        }

        @Override
        public void afterTextChanged(Editable s) {
            password = passwordSignUpEditText.getText().toString().trim();
            confirmPassword = confirmPasswordEditText.getText().toString().trim();

            if (password.length() < 8){
                passwordSignUpEditText.setError("Password Harus lebih dari 8 Character atau sama dengan 8 Character");
            } else if (password.length() > 25){
                passwordSignUpEditText.setError("Password Tidak Maksimal 25 Character");
            } else {
                calculatePasswordStrength(password);
                passwordSignUpEditText.setError(null);
            }

            if (!confirmPassword.equals(password)){
                confirmPasswordEditText.setError("Confirm Password Tidak Sama dengan Password");
            } else {
                confirmPasswordEditText.setError(null);
            }

            signUpButton.setEnabled(!name.isEmpty() &&
                    !username.isEmpty() &&
                    !password.isEmpty() &&
                    !confirmPassword.isEmpty() &&
                    !imagePath.isEmpty());
        }
    };

    public static boolean isEmailValid(String email) {
        String expression = "^[\\w\\.-]+@([\\w\\-]+\\.)+[A-Z]{2,4}$";
        Pattern pattern = Pattern.compile(expression, Pattern.CASE_INSENSITIVE);
        Matcher matcher = pattern.matcher(email);
        return matcher.matches();
    }

    private void calculatePasswordStrength(String str) {
        PasswordStrength passwordStrength = PasswordStrength.calculate(str);
        strengthPassword.setVisibility(View.VISIBLE);
        strengthPassword.setText(passwordStrength.msg);
        strengthPassword.setTextColor(passwordStrength.color);
    }

    public void ShowHidePass(View view) {

        if(view.getId()==R.id.show_pass_btn){
            if(passwordSignUpEditText.getTransformationMethod().equals(PasswordTransformationMethod.getInstance())){
                ((ImageView)(view)).setImageResource(R.drawable.ic_baseline_visibility_24);
                passwordSignUpEditText.setTransformationMethod(HideReturnsTransformationMethod.getInstance());
            }
            else{
                ((ImageView)(view)).setImageResource(R.drawable.ic_outline_visibility_off_24);
                passwordSignUpEditText.setTransformationMethod(PasswordTransformationMethod.getInstance());
            }
        } else if (view.getId()==R.id.show_confirmpass_btn){
            if(confirmPasswordEditText.getTransformationMethod().equals(PasswordTransformationMethod.getInstance())){
                ((ImageView)(view)).setImageResource(R.drawable.ic_baseline_visibility_24);
                confirmPasswordEditText.setTransformationMethod(HideReturnsTransformationMethod.getInstance());
            }
            else{
                ((ImageView)(view)).setImageResource(R.drawable.ic_outline_visibility_off_24);
                confirmPasswordEditText.setTransformationMethod(PasswordTransformationMethod.getInstance());
            }
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode != RESULT_CANCELED) {
            switch (requestCode) {
                case 0:
                    if (resultCode == RESULT_OK && data != null) {
                        Bitmap selectedImage = (Bitmap) data.getExtras().get("data");
                        imagePath = ImageUtils.encodeToBase64(selectedImage);
                        profilePicture.setImageBitmap(selectedImage);
                    }
                    break;
                case 1:
                    if (resultCode == RESULT_OK && data != null) {
                        Uri uri = data.getData();

                        Bitmap bitmap = null;
                        ExifInterface ei = null;
                        Bitmap rotatedBitmap = null;
                        try {
                            bitmap = MediaStore.Images.Media.getBitmap(getContentResolver(), uri);
                            imagePath = ImageUtils.encodeToBase64(bitmap);

                            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
                                ei = new ExifInterface(this.getContentResolver().openInputStream(uri));
                            }
                            int orientation = ei.getAttributeInt(ExifInterface.TAG_ORIENTATION,
                                    ExifInterface.ORIENTATION_UNDEFINED);

                            switch(orientation) {

                                case ExifInterface.ORIENTATION_ROTATE_90:
                                    rotatedBitmap = ImageUtils.rotateImage(bitmap, 90);
                                    break;

                                case ExifInterface.ORIENTATION_ROTATE_180:
                                    rotatedBitmap = ImageUtils.rotateImage(bitmap, 180);
                                    break;

                                case ExifInterface.ORIENTATION_ROTATE_270:
                                    rotatedBitmap = ImageUtils.rotateImage(bitmap, 270);
                                    break;

                                case ExifInterface.ORIENTATION_NORMAL:
                                default:
                                    rotatedBitmap = bitmap;
                            }

                            profilePicture.setImageBitmap(rotatedBitmap);
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                    }
                    break;
            }
        }
        signUpButton.setEnabled(!name.isEmpty() &&
                !username.isEmpty() &&
                !password.isEmpty() &&
                !confirmPassword.isEmpty() &&
                !imagePath.isEmpty());
    }
}