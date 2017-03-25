package com.helpinghands.activity;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.provider.ContactsContract;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Toast;

import com.helpinghand.R;
import com.helpinghands.OnContactListener;
import com.helpinghands.adapter.EmergencyContactAdapter;
import com.helpinghands.HelpingHandApp;
import com.helpinghands.database.Contacts;
import com.helpinghands.fragment.FancyAlertDialog;
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
    private EmergencyContactAdapter emergencyContactAdapter;
    private ArrayList<Contacts> mContactList;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_emergency_contact);
        ButterKnife.bind(this);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        getSupportActionBar().setTitle("Emergency Contacts");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);

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
        mContactList = new ArrayList<>();
        mContactList.addAll(HelpingHandApp.contactsDao.loadAll());
        rvEmergencyContacts.setLayoutManager(new GridLayoutManager(AddEmergencyContactActivity.this, 3));
        emergencyContactAdapter = new EmergencyContactAdapter(AddEmergencyContactActivity.this, mContactList, onContactListener);
        rvEmergencyContacts.setAdapter(emergencyContactAdapter);
    }

    private OnContactListener onContactListener = new OnContactListener() {
        @Override
        public void onContactLongClick(final int position, final Contacts contacts) {
             new AlertDialog.Builder(AddEmergencyContactActivity.this)
                    .setTitle("Delete Contact")
                    .setMessage("Are you sure you want to delete this contact?")
                    .setPositiveButton(android.R.string.yes, new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int which) {
                            mContactList.remove(position);
                            emergencyContactAdapter.notifyDataSetChanged();
                            HelpingHandApp.contactsDao.delete(contacts);
                        }
                    })
                    .setNegativeButton(android.R.string.no, new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int which) {
                            // do nothing
                            dialog.dismiss();
                        }
                    })
                    .setIcon(android.R.drawable.ic_dialog_alert)
                    .show();


        }

        @Override
        public void onContactClick(int position, Contacts contacts) {
            final FancyAlertDialog.Builder alert = new FancyAlertDialog.Builder(AddEmergencyContactActivity.this)
                    .setImageDrawable(contacts.getPhoto())
                    .setTextTitle(contacts.getName())
                    .setTextSubTitle(contacts.getPhonuNumber())
                    .setBody("".concat("Press long click on Contact to remove ").concat(contacts.getName()).concat(" from emergency contact list"))
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
