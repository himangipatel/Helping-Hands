package com.helpinghands.activity;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.Typeface;
import android.net.Uri;
import android.provider.MediaStore;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.helpinghand.R;
import com.helpinghands.SharedPreferenceHelper;
import com.helpinghands.adapter.CircleTransform;
import com.squareup.picasso.Picasso;

import java.io.IOException;

public class LoginActivity extends AppCompatActivity {

    private static final int PICK_PHOTO_FOR_AVATAR = 123;
    private static final int MY_PERMISSIONS_REQUEST_READ_EXTERNAL_STORAGE = 1234;
    private EditText etUserName;
    private EditText etFullName;
    private EditText etPhoneNumber;
    private ImageView ivUserProfile;
    private String userImageURI;

    private View.OnClickListener onSubmitClickListener = new View.OnClickListener() {
        @Override
        public void onClick(View view) {
            String userName = etUserName.getText().toString();
            String userFullName = etFullName.getText().toString();
            String userPhoneNum = etPhoneNumber.getText().toString();

            if (userName.isEmpty()) {
                setToast("Please Enter Username");
            } else if (userName.length() < 4) {
                setToast("Minimum 4 character required in username");
            } else if (userFullName.isEmpty()) {
                setToast("Please Enter Fullname");
            } else if (userPhoneNum.isEmpty()) {
                setToast("Please Enter Phone");
            } else if (userPhoneNum.length() > 10) {
                setToast("Please Enter valid phone number");
            } else {

                SharedPreferenceHelper sharedPreferenceHelper = new SharedPreferenceHelper(LoginActivity.this);
                sharedPreferenceHelper.setUserFullName(userFullName);
                sharedPreferenceHelper.setUserName(userName);
                sharedPreferenceHelper.setUserPhoneNumber(userPhoneNum);
                sharedPreferenceHelper.setIsUserLogin(true);
                sharedPreferenceHelper.setUserProfile(userImageURI);

                Intent intent = new Intent(LoginActivity.this, BubblePickerActivity.class);
                startActivity(intent);
                finish();
            }

        }
    };

    private View.OnClickListener onProfileClickListener = new View.OnClickListener() {
        @Override
        public void onClick(View view) {
            if (checkPermission()) {
                pickImage();
            } else {
                ActivityCompat.requestPermissions(
                        LoginActivity.this,
                        new String[]{Manifest.permission.READ_EXTERNAL_STORAGE},
                        MY_PERMISSIONS_REQUEST_READ_EXTERNAL_STORAGE);
            }
        }
    };

    public void pickImage() {
        Intent intent = new Intent(Intent.ACTION_GET_CONTENT);
        intent.setType("image/*");
        startActivityForResult(intent, PICK_PHOTO_FOR_AVATAR);
    }


    private void setToast(String message) {
        Toast.makeText(this, message, Toast.LENGTH_SHORT).show();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        final Button btnSubmit = (Button) findViewById(R.id.btnSubmit);
        etUserName = (EditText) findViewById(R.id.etUserName);
        etFullName = (EditText) findViewById(R.id.etFullName);
        etPhoneNumber = (EditText) findViewById(R.id.etPhoneNumber);
        ivUserProfile = (ImageView) findViewById(R.id.ivUserProfile);

        btnSubmit.setOnClickListener(onSubmitClickListener);
        ivUserProfile.setOnClickListener(onProfileClickListener);

    }

    private boolean checkPermission() {
        return ActivityCompat.checkSelfPermission(this, Manifest.permission.READ_EXTERNAL_STORAGE) == PackageManager.PERMISSION_GRANTED
                && ActivityCompat.checkSelfPermission(this, Manifest.permission.READ_EXTERNAL_STORAGE) == PackageManager.PERMISSION_GRANTED;
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == PICK_PHOTO_FOR_AVATAR && resultCode == RESULT_OK && data != null && data.getData() != null) {

            Uri uri = data.getData();
            userImageURI = uri.toString();
            Picasso.with(LoginActivity.this)
                    .load(uri)
                    .error(R.drawable.ic_user)
                    .transform(new CircleTransform())
                    .into(ivUserProfile);
        }
    }

}
