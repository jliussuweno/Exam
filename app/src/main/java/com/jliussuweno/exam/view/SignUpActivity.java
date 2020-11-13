package com.jliussuweno.exam.view;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import androidx.exifinterface.media.ExifInterface;
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
import com.jliussuweno.exam.model.User;
import com.jliussuweno.exam.utils.ImageUtils;
import com.jliussuweno.exam.viewmodel.SignUpViewModel;

import java.io.IOException;


public class SignUpActivity extends AppCompatActivity {

    EditText fullNameEditText;
    EditText usernameSignUpEditText;
    EditText passwordSignUpEditText;
    EditText confirmPasswordEditText;
    Button signUpButton;
    Button button_photo;
    Button button_gallery;
    TextView strengthPassword;
    ImageView profilePicture;
    SignUpViewModel signUpViewModel;
    String imagePath = "";
    String name = "";
    String password = "";
    String confirmPassword = "";
    View context_view;
    Context context;
    Toast toast;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up);

        fullNameEditText = findViewById(R.id.edit_text_name_about);
        passwordSignUpEditText = findViewById(R.id.edit_text_password_about);
        confirmPasswordEditText = findViewById(R.id.editTextTextConfirmPassword);
        button_gallery = findViewById(R.id.button_gallery_about);
        button_photo = findViewById(R.id.button_photo_about);
        signUpButton = findViewById(R.id.buttonSaveProfile);
        strengthPassword = findViewById(R.id.password_strength);
        profilePicture = findViewById(R.id.image_view_about);
        context_view = findViewById(R.id.parentSignUpView);
        signUpButton.setEnabled(false);

        fullNameEditText.addTextChangedListener(signUpTextWatcherName);
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
                name = fullNameEditText.getText().toString().trim();
                password = passwordSignUpEditText.getText().toString().trim();
                confirmPassword = confirmPasswordEditText.getText().toString().trim();

                User user = new User(name, password, imagePath);

                if (name.length() > 20){
                    makeToast("Nama Maksimal 20 Karakter!");
                } else if (name.length() < 6){
                    makeToast("Nama Minimal 6 Karakter");
                } else if (password.length() < 6){
                    makeToast("Password harus 6 Karakter dan hanya angka!");
                } else if (confirmPassword.length() < 6){
                    makeToast("Confirm Password harus 6 Karakter dan hanya angka!");
                } else if (!password.equals(confirmPassword)){
                    makeToast("Password dan Confirm Password harus sama!");
                } else if (imagePath.isEmpty()){
                    makeToast("Silahkan pilih image terlebih dahulu!");
                } else {
                    signUpViewModel.insertUser(user);
                    makeToast(getString(R.string.registerSuccess));
                    Intent intent = new Intent(SignUpActivity.this, ListActivity.class);
                    startActivity(intent);
                    finish();
                }
            }
        });

    }

    public void makeToast(String message) {
        if (toast != null){
            toast = null;
        }
        toast = Toast.makeText(this, message, Toast.LENGTH_SHORT);
        toast.show();
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
                fullNameEditText.setError(getString(R.string.nameWarningMin));
            } else if (name.length() > 25){
                fullNameEditText.setError(getString(R.string.nameWarningMax));
            } else {
                fullNameEditText.setError(null);
            }
            signUpButton.setEnabled(!name.isEmpty() &&
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

            if (password.length() < 6){
                passwordSignUpEditText.setError(getString(R.string.passwordWarning));
            } else {
                passwordSignUpEditText.setError(null);
            }

            if (!confirmPassword.equals(password)){
                confirmPasswordEditText.setError(getString(R.string.confirmPasswordWarning));
            } else {
                confirmPasswordEditText.setError(null);
            }

            signUpButton.setEnabled(!name.isEmpty() &&
                    !password.isEmpty() &&
                    !confirmPassword.isEmpty() &&
                    !imagePath.isEmpty());
        }
    };

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
                        Bitmap selectedImage = (Bitmap) data.getExtras().get(getString(R.string.data));
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
                !password.isEmpty() &&
                !confirmPassword.isEmpty() &&
                !imagePath.isEmpty());
    }
}