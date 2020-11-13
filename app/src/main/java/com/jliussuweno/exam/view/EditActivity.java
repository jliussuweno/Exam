package com.jliussuweno.exam.view;

import androidx.appcompat.app.AppCompatActivity;
import androidx.exifinterface.media.ExifInterface;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
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

import com.jliussuweno.exam.R;
import com.jliussuweno.exam.model.User;
import com.jliussuweno.exam.utils.ImageUtils;
import com.jliussuweno.exam.utils.ToastUtils;
import com.jliussuweno.exam.viewmodel.EditViewModel;

import java.io.IOException;
import java.util.List;

public class EditActivity extends AppCompatActivity {

    EditText et_name_about;
    EditText et_password_about;
    EditText et_confirm_password_about;
    Button button_save;
    Button button_photo;
    Button button_gallery;
    Button button_delete;
    ImageView profilePicture;
    ImageView hideSeekPass;
    ImageView hideSeekConfirmPass;
    EditViewModel editViewModel;
    String imagePath = "";
    String name = "";
    String usernameAbout = "";
    String password = "";
    String confirmPassword = "";
    int userId;
    View context_view;
    Context context;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit);

        et_name_about = findViewById(R.id.edit_text_name_about);
        et_password_about = findViewById(R.id.edit_text_password_about);
        et_confirm_password_about = findViewById(R.id.editTextTextConfirmPassword);
        button_gallery = findViewById(R.id.button_gallery_about);
        button_photo = findViewById(R.id.button_photo_about);
        button_save = findViewById(R.id.buttonSaveProfile);
        button_delete = findViewById(R.id.buttonDelete);
        profilePicture = findViewById(R.id.image_view_about);
        context_view = findViewById(R.id.parentAbout);
        hideSeekPass = findViewById(R.id.show_pass_btn);
        hideSeekConfirmPass = findViewById(R.id.show_confirmpass_btn);
        button_save.setEnabled(false);
        context = this;

        hideSeekPass.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ShowHidePass(hideSeekPass);
            }
        });
        hideSeekConfirmPass.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
            ShowHidePass(hideSeekConfirmPass);
            }
        });

        Intent getIntent = getIntent();
        User current = (User) getIntent.getSerializableExtra("user");
        editViewModel = new ViewModelProvider(this).get(EditViewModel.class);
        editViewModel.initDataUser(current.getUserId());
        editViewModel.getListLiveDataUser().observe(this , new Observer<List<User>>() {
            @Override
            public void onChanged(List<User> users) {
                User current = users.get(0);
                userId = current.getUserId();
                name = current.getName();
                imagePath = current.getImage();
                et_name_about.setText(name);
                profilePicture.setImageBitmap(ImageUtils.decodeBase64(imagePath));
            }
        });

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

        button_save.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                name = et_name_about.getText().toString().trim();
                password = et_password_about.getText().toString().trim();

                if (name.length() > 20){
                    ToastUtils.makeToast(context,"Nama Maksimal 20 Karakter!");
                } else if (name.length() < 6){
                    ToastUtils.makeToast(context,"Nama Minimal 6 Karakter");
                } else if (password.length() < 6){
                    ToastUtils.makeToast(context,"Password harus 6 Karakter dan hanya angka!");
                } else if (confirmPassword.length() < 6){
                    ToastUtils.makeToast(context,"Confirm Password harus 6 Karakter dan hanya angka!");
                } else if (!password.equals(confirmPassword)){
                    ToastUtils.makeToast(context,"Password dan Confirm Password harus sama!");
                } else if (imagePath.isEmpty()){
                    ToastUtils.makeToast(context,"Silahkan pilih image terlebih dahulu!");
                } else if (!name.isEmpty() && !password.isEmpty() && !imagePath.isEmpty()){
                    User updatedUser = new User(name, password, imagePath);
                    editViewModel.updateUser(updatedUser);
                    ToastUtils.makeToast(context, "Berhasil Update Data!");
                    Intent intent = new Intent(EditActivity.this, ListActivity.class);
                    startActivity(intent);
                    finish();
                }
            }
        });

        button_delete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                editViewModel.deleteUser(userId);
                ToastUtils.makeToast(context, "Berhasil Hapus User!");
                Intent intent = new Intent(EditActivity.this, ListActivity.class);
                startActivity(intent);
                finish();
            }
        });
        et_password_about.addTextChangedListener(textWatcherPassword);
        et_confirm_password_about.addTextChangedListener(textWatcherPassword);
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
            password = et_password_about.getText().toString().trim();
            confirmPassword = et_confirm_password_about.getText().toString().trim();

            if (password.length() < 6){
                et_password_about.setError(getString(R.string.passwordWarning));
            } else {
                et_password_about.setError(null);
            }

            if (!confirmPassword.equals(password)){
                et_confirm_password_about.setError(getString(R.string.confirmPasswordWarning));
            } else {
                et_confirm_password_about.setError(null);
            }

            button_save.setEnabled(!name.isEmpty() &&
                    !password.isEmpty() &&
                    !confirmPassword.isEmpty() &&
                    !imagePath.isEmpty());
        }
    };

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
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
                                ei = new ExifInterface(getContentResolver().openInputStream(uri));
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
        button_save.setEnabled(!name.isEmpty() &&
                !usernameAbout.isEmpty() &&
                !password.isEmpty() &&
                !imagePath.isEmpty());
    }

    public void ShowHidePass(View view) {
        if(view.getId()==R.id.show_pass_btn){
            if(et_password_about.getTransformationMethod().equals(PasswordTransformationMethod.getInstance())){
                ((ImageView)(view)).setImageResource(R.drawable.ic_baseline_visibility_24);
                et_password_about.setTransformationMethod(HideReturnsTransformationMethod.getInstance());
            }
            else{
                ((ImageView)(view)).setImageResource(R.drawable.ic_outline_visibility_off_24);
                et_password_about.setTransformationMethod(PasswordTransformationMethod.getInstance());
            }
        } else if (view.getId()==R.id.show_confirmpass_btn){
            if(et_confirm_password_about.getTransformationMethod().equals(PasswordTransformationMethod.getInstance())){
                ((ImageView)(view)).setImageResource(R.drawable.ic_baseline_visibility_24);
                et_confirm_password_about.setTransformationMethod(HideReturnsTransformationMethod.getInstance());
            }
            else{
                ((ImageView)(view)).setImageResource(R.drawable.ic_outline_visibility_off_24);
                et_confirm_password_about.setTransformationMethod(PasswordTransformationMethod.getInstance());
            }
        }
    }
}