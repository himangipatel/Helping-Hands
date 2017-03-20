package com.helpinghands.activity;

import android.app.Activity;
import android.content.Intent;
import android.provider.ContactsContract;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.View;

import com.helpinghand.R;
import com.helpinghands.adapter.EmergencyContactAdapter;
import com.helpinghands.HelpingHandApp;
import com.helpinghands.database.Contacts;
import com.onegravity.contactpicker.contact.Contact;
import com.onegravity.contactpicker.contact.ContactDescription;
import com.onegravity.contactpicker.contact.ContactSortOrder;
import com.onegravity.contactpicker.core.ContactPickerActivity;
import com.onegravity.contactpicker.group.Group;
import com.onegravity.contactpicker.picture.ContactPictureType;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class AddEmergencyContactActivity extends AppCompatActivity {

    @BindView(R.id.rvEmergencyContacts)
    public RecyclerView rvEmergencyContacts;

    private static final int REQUEST_CONTACT = 123;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_emergency_contact);
        ButterKnife.bind(this);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        getSupportActionBar().setTitle("Emergency Contacts");
        getSupportActionBar().setDefaultDisplayHomeAsUpEnabled(true);

        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });

        rvEmergencyContacts = (RecyclerView) findViewById(R.id.rvEmergencyContacts);
        setAdapter();

        final FloatingActionButton fabAdd = (FloatingActionButton) findViewById(R.id.fabAdd);
        fabAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(AddEmergencyContactActivity.this, ContactPickerActivity.class)
                        .putExtra(ContactPickerActivity.EXTRA_CONTACT_BADGE_TYPE, ContactPictureType.ROUND.name())
                        .putExtra(ContactPickerActivity.EXTRA_SHOW_CHECK_ALL, true)
                        .putExtra(ContactPickerActivity.EXTRA_CONTACT_DESCRIPTION, ContactDescription.ADDRESS.name())
                        .putExtra(ContactPickerActivity.EXTRA_CONTACT_DESCRIPTION_TYPE, ContactsContract.CommonDataKinds.Email.TYPE_WORK)
                        .putExtra(ContactPickerActivity.EXTRA_CONTACT_SORT_ORDER, ContactSortOrder.AUTOMATIC.name());
                startActivityForResult(intent, REQUEST_CONTACT);
            }
        });

        if (getIntent()!=null){
            boolean addOrShow = getIntent().getBooleanExtra("Add",false);
            if (addOrShow){
                Intent intent = new Intent(AddEmergencyContactActivity.this, ContactPickerActivity.class)
                        .putExtra(ContactPickerActivity.EXTRA_CONTACT_BADGE_TYPE, ContactPictureType.ROUND.name())
                        .putExtra(ContactPickerActivity.EXTRA_SHOW_CHECK_ALL, true)
                        .putExtra(ContactPickerActivity.EXTRA_CONTACT_DESCRIPTION, ContactDescription.ADDRESS.name())
                        .putExtra(ContactPickerActivity.EXTRA_CONTACT_DESCRIPTION_TYPE, ContactsContract.CommonDataKinds.Email.TYPE_WORK)
                        .putExtra(ContactPickerActivity.EXTRA_CONTACT_SORT_ORDER, ContactSortOrder.AUTOMATIC.name());
                startActivityForResult(intent, REQUEST_CONTACT);
            }else {
                fabAdd.setVisibility(View.GONE);

            }
        }



    }

    private void setAdapter() {
        List<Contacts> mContactList = new ArrayList<>();
        mContactList.addAll(HelpingHandApp.contactsDao.loadAll());
        rvEmergencyContacts.setLayoutManager(new GridLayoutManager(AddEmergencyContactActivity.this, 3));
        EmergencyContactAdapter emergencyContactAdapter = new EmergencyContactAdapter(AddEmergencyContactActivity.this, mContactList);
        rvEmergencyContacts.setAdapter(emergencyContactAdapter);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == REQUEST_CONTACT && resultCode == Activity.RESULT_OK &&
                data != null && data.hasExtra(ContactPickerActivity.RESULT_CONTACT_DATA)) {

            // we got a result from the contact picker

            // process contacts
            List<Contact> contacts = (List<Contact>) data.getSerializableExtra(ContactPickerActivity.RESULT_CONTACT_DATA);

            for (Contact contact : contacts) {
                Contacts c = new Contacts();
                c.setName(contact.getDisplayName());
                c.setPhonuNumber(contact.getPhone(0));
                if (contact.getPhotoUri() == null) {
                    c.setPhoto("");
                } else {
                    c.setPhoto(contact.getPhotoUri().toString());
                }
                HelpingHandApp.contactsDao.insertOrReplace(c);
            }

            setAdapter();
            // process groups
            List<Group> groups = (List<Group>) data.getSerializableExtra(ContactPickerActivity.RESULT_GROUP_DATA);
            for (Group group : groups) {
                // process the groups...
            }
        }
    }
}
