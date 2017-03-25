package com.helpinghands.activity;

import android.Manifest;
import android.app.Dialog;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.content.res.TypedArray;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.helpinghand.R;
import com.helpinghands.SharedPreferenceHelper;
import com.helpinghands.fragment.FancyAlertDialog;
import com.igalata.bubblepicker.BubblePickerListener;
import com.igalata.bubblepicker.model.PickerItem;
import com.igalata.bubblepicker.rendering.BubblePicker;

import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;

public class BubblePickerActivity extends AppCompatActivity {

    private static final int STORAGE_PERMISSION_CODE = 123;
    private static final int MY_PERMISSIONS_REQUEST_ACCESS_LOCATION = 12345;
    @BindView(R.id.picker)
    BubblePicker picker;
    private TextView tvAddContacts;
    private TextView tvShowContact;
    private TextView tvTrackLocation;
    private TextView tvProfile;


    private View.OnClickListener onAddContactListener = new View.OnClickListener() {
        @Override
        public void onClick(View view) {
            if (isReadContactAllowed()) {
                Intent intent = new Intent(BubblePickerActivity.this, AddEmergencyContactActivity.class);
                intent.putExtra("Add", true);
                startActivity(intent);
            } else {
                requestReadContactPermission();
            }
        }
    };

    private View.OnClickListener onShowContactListener = new View.OnClickListener() {
        @Override
        public void onClick(View view) {
            Intent intent = new Intent(BubblePickerActivity.this, AddEmergencyContactActivity.class);
            intent.putExtra("Add", false);
            startActivity(intent);
        }
    };

    private View.OnClickListener onTrackLocationClickListener = new View.OnClickListener() {
        @Override
        public void onClick(View view) {
            if (checkPermission()) {
                Intent intent = new Intent(BubblePickerActivity.this, MapsActivity.class);
                startActivity(intent);

            } else {
                ActivityCompat.requestPermissions(
                        BubblePickerActivity.this,
                        new String[]{Manifest.permission.ACCESS_COARSE_LOCATION, Manifest.permission.ACCESS_FINE_LOCATION},
                        MY_PERMISSIONS_REQUEST_ACCESS_LOCATION);
            }
        }
    };

    private View.OnClickListener onProfileViewClickListener = new View.OnClickListener() {
        @Override
        public void onClick(View view) {

            SharedPreferenceHelper preferenceHelper = new SharedPreferenceHelper(BubblePickerActivity.this);

            final FancyAlertDialog.Builder alert = new FancyAlertDialog.Builder(BubblePickerActivity.this)
                    .setImageDrawable(preferenceHelper.getUserProfile())
                    .setTextTitle(preferenceHelper.getUserFullName())
                    .setTextSubTitle(preferenceHelper.getUserPhoneNumber())
                    .setPositiveButtonText("OK")
                    .setPositiveColor(R.color.colorPrimaryDark_dark)
                    .setOnPositiveClicked(new FancyAlertDialog.OnPositiveClicked() {
                        @Override
                        public void OnClick(View view, Dialog dialog) {
                            dialog.dismiss();
                        }
                    })
                       /* .setAutoHide(true)*/
                    .build();
            alert.show();
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_bubble_picker);
        ButterKnife.bind(this);

        picker = (BubblePicker) findViewById(R.id.picker);
        tvShowContact = (TextView) findViewById(R.id.tvShowContact);
        tvAddContacts = (TextView) findViewById(R.id.tvAddContacts);
        tvTrackLocation = (TextView) findViewById(R.id.tvTrackLocation);
        tvProfile = (TextView) findViewById(R.id.tvProfile);

        tvShowContact.setOnClickListener(onShowContactListener);
        tvAddContacts.setOnClickListener(onAddContactListener);
        tvTrackLocation.setOnClickListener(onTrackLocationClickListener);
        tvProfile.setOnClickListener(onProfileViewClickListener);


    }


    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {

        switch (requestCode) {
            case MY_PERMISSIONS_REQUEST_ACCESS_LOCATION: {
                // If request is cancelled, the result arrays are empty.
                if (grantResults.length > 0
                        && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    Intent intent = new Intent(BubblePickerActivity.this, MapsActivity.class);
                    startActivity(intent);

                } else {
                    Toast.makeText(BubblePickerActivity.this, "Allow Location Pemission To Tract Location", Toast.LENGTH_SHORT).show();
                }
                break;
            }
            case STORAGE_PERMISSION_CODE: {

                if (grantResults.length > 0
                        && grantResults[0] == PackageManager.PERMISSION_GRANTED) {

                    Intent intent = new Intent(BubblePickerActivity.this, AddEmergencyContactActivity.class);
                    intent.putExtra("Add", true);
                    startActivity(intent);

                }
            }

        }
    }

    //We are calling this method to check the permission status
    private boolean isReadContactAllowed() {
        //Getting the permission status
        int result = ContextCompat.checkSelfPermission(this, Manifest.permission.READ_CONTACTS);

        //If permission is granted returning true
        if (result == PackageManager.PERMISSION_GRANTED)
            return true;

        //If permission is not granted returning false
        return false;
    }

    private boolean checkPermission() {
        return ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED
                && ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) == PackageManager.PERMISSION_GRANTED;
    }

    //Requesting permission
    private void requestReadContactPermission() {

        if (ActivityCompat.shouldShowRequestPermissionRationale(this, Manifest.permission.READ_CONTACTS)) {
            //If the user has denied the permission previously your code will come to this block
            //Here you can explain why you need this permission
            //Explain here why you need this permission
        }

        //And finally ask for the permission
        ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.READ_CONTACTS}, STORAGE_PERMISSION_CODE);
    }

}
