package com.ashutosh.helpinghand.activity;

import android.content.Intent;
import android.content.res.TypedArray;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.ashutosh.helpinghand.R;
import com.igalata.bubblepicker.BubblePickerListener;
import com.igalata.bubblepicker.model.PickerItem;
import com.igalata.bubblepicker.rendering.BubblePicker;

import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;

public class BubblePickerActivity extends AppCompatActivity {

    @BindView(R.id.picker)
    BubblePicker picker;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_bubble_picker);
        ButterKnife.bind(this);

        picker = (BubblePicker) findViewById(R.id.picker);
        picker.onResume();
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
        picker.onPause();
    }
}
