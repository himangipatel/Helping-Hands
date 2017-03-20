package com.helpinghands.activity;

import android.Manifest;
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
import com.igalata.bubblepicker.BubblePickerListener;
import com.igalata.bubblepicker.model.PickerItem;
import com.igalata.bubblepicker.rendering.BubblePicker;

import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;

public class BubblePickerActivity extends AppCompatActivity {

    private static final int STORAGE_PERMISSION_CODE = 123;
    @BindView(R.id.picker)
    BubblePicker picker;
    private TextView tvAddContacts;
    private TextView tvShowContact;


    private View.OnClickListener onAddContactListener = new View.OnClickListener() {
        @Override
        public void onClick(View view) {
            Intent intent = new Intent(BubblePickerActivity.this,AddEmergencyContactActivity.class);
            intent.putExtra("Add",true);
            startActivity(intent);
        }
    };

    private View.OnClickListener onShowContactListener = new View.OnClickListener() {
        @Override
        public void onClick(View view) {
            Intent intent = new Intent(BubblePickerActivity.this,AddEmergencyContactActivity.class);
            intent.putExtra("Add",false);
            startActivity(intent);
        }
    };


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_bubble_picker);
        ButterKnife.bind(this);

        requestReadContactPermission();
        picker = (BubblePicker) findViewById(R.id.picker);
        tvShowContact = (TextView) findViewById(R.id.tvShowContact);
        tvAddContacts = (TextView) findViewById(R.id.tvAddContacts);

        tvShowContact.setOnClickListener(onShowContactListener);
        tvAddContacts.setOnClickListener(onAddContactListener);

        //picker.onResume();
        final String[] titles = getResources().getStringArray(R.array.countries);
        final TypedArray colors = getResources().obtainTypedArray(R.array.colors);
        final TypedArray images = getResources().obtainTypedArray(R.array.images);

        picker.setItems(new ArrayList<PickerItem>() {{
            for (int i = 0; i < titles.length; ++i) {
                add(new PickerItem(titles[i], colors.getColor((i * 2) % 8, 0),
                        ContextCompat.getColor(BubblePickerActivity.this, android.R.color.white),
                        ContextCompat.getDrawable(BubblePickerActivity.this, images.getResourceId(i, 0))));
            }
        }});

        picker.setListener(new BubblePickerListener() {
            @Override
            public void onBubbleSelected(@NotNull PickerItem item) {
                if (item.getTitle().equals("Add Contacts")){
                    Intent intent = new Intent(BubblePickerActivity.this,AddEmergencyContactActivity.class);
                    intent.putExtra("Add",true);
                    startActivity(intent);
                }else if (item.getTitle().equals("Show Contacts")){
                    Intent intent = new Intent(BubblePickerActivity.this,AddEmergencyContactActivity.class);
                    intent.putExtra("Add",false);
                    startActivity(intent);
                }
            }

            @Override
            public void onBubbleDeselected(@NotNull PickerItem item) {

            }
        });
    }

    @Override
    protected void onResume() {
        super.onResume();
       // picker.onResume();
    }

    @Override
    protected void onPause() {
        super.onPause();
        // picker.onPause();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
       // picker.onPause();
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {

        //Checking the request code of our request
        if(requestCode == STORAGE_PERMISSION_CODE){

            //If permission is granted
            if(grantResults.length >0 && grantResults[0] == PackageManager.PERMISSION_GRANTED){

                //Displaying a toast
            }else{
                //Displaying another toast if permission is not granted
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

    //Requesting permission
    private void requestReadContactPermission(){

        if (ActivityCompat.shouldShowRequestPermissionRationale(this,Manifest.permission.READ_CONTACTS)){
            //If the user has denied the permission previously your code will come to this block
            //Here you can explain why you need this permission
            //Explain here why you need this permission
        }

        //And finally ask for the permission
        ActivityCompat.requestPermissions(this,new String[]{Manifest.permission.READ_CONTACTS},STORAGE_PERMISSION_CODE);
    }

}
