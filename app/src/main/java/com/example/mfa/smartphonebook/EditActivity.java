package com.example.mfa.smartphonebook;

import android.app.Activity;
import android.content.ContentResolver;
import android.content.ContentValues;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.provider.ContactsContract;
import android.support.annotation.Nullable;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.NavUtils;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

/**
 * Created by mfa on 03.01.2016.
 */
public class EditActivity extends FragmentActivity {

    private final String LOG_TAG = EditActivity.class.getSimpleName();
    private TextView mNameTextView, mEmailTextView, mPhoneTextView;
    private Button mButton;
    private ContentResolver mContentResolver;


    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.add_edit);
        getActionBar().setDisplayHomeAsUpEnabled(true);

        mNameTextView = (TextView) findViewById(R.id.contact_name);
        mEmailTextView = (TextView) findViewById(R.id.contact_email);
        mPhoneTextView = (TextView) findViewById(R.id.contact_phone);

        mContentResolver = EditActivity.this.getContentResolver();
        Intent intent= getIntent();
        final String _id = intent.getStringExtra(PhoneContract.ContactsColumn.CONTACT_ID);
        String name = intent.getStringExtra(PhoneContract.ContactsColumn.CONTACT_NAME);
        String phone = intent.getStringExtra(PhoneContract.ContactsColumn.CONTACT_PHONE);
        String email = intent.getStringExtra(PhoneContract.ContactsColumn.CONTACT_EMAIL);

        mNameTextView.setText(name);
        mPhoneTextView.setText(phone);
        mEmailTextView.setText(email);

        mButton = (Button) findViewById(R.id.saveButton);
        mButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                ContentValues values = new ContentValues();
                values.put(PhoneContract.ContactsColumn.CONTACT_NAME, mNameTextView.getText().toString());
                values.put(PhoneContract.ContactsColumn.CONTACT_PHONE, mPhoneTextView.getText().toString());
                values.put(PhoneContract.ContactsColumn.CONTACT_EMAIL, mEmailTextView.getText().toString());
                Uri uri= PhoneContract.Contacts.buildContactsUri(_id);
                int recordedUpdated =  mContentResolver.update(uri, values,null,null);
                Intent intent = new Intent(EditActivity.this, MainActivity.class);
                startActivity(intent);
                finish();

            }
        });

    }


    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()){
            case android.R.id.home:
                NavUtils.navigateUpFromSameTask(this);
                break;
        }
        return true;
    }
}
