package com.example.mfa.smartphonebook;

import android.content.ContentResolver;
import android.content.ContentValues;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.FragmentActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import org.w3c.dom.Text;

/**
 * Created by mfa on 03.01.2016.
 */
public class AddActivity extends FragmentActivity {

    private final String LOG_TAG=AddActivity.class.getSimpleName();
    private TextView mNameTextView,mEmailTextView,mPhoneTextView;
    private Button mButton;
    private ContentResolver mContentResolver;



    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.add_edit);
        getActionBar().setDisplayHomeAsUpEnabled(true);

        mNameTextView=(TextView) findViewById(R.id.contact_name);
        mEmailTextView=(TextView) findViewById(R.id.contact_email);
        mPhoneTextView=(TextView) findViewById(R.id.contact_phone);

        mContentResolver = AddActivity.this.getContentResolver();

        mButton=(Button) findViewById(R.id.saveButton);
        mButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(isValid()){
                    ContentValues values = new ContentValues();
                    values.put(PhoneContract.ContactsColumn.CONTACT_NAME,mNameTextView.getText().toString());
                    values.put(PhoneContract.ContactsColumn.CONTACT_PHONE, mPhoneTextView.getText().toString());
                    values.put(PhoneContract.ContactsColumn.CONTACT_EMAIL, mEmailTextView.getText().toString());
                    Uri returned=mContentResolver.insert(PhoneContract.URI_TABLE,values);
                    Intent intent =new Intent(AddActivity.this,MainActivity.class);
                    intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                    startActivity(intent);
                    finish();
                }else{
                    Toast.makeText(getApplicationContext(),"Invalid Data",Toast.LENGTH_LONG).show();
                }
            }
        });

    }


    private boolean isValid(){
        if(mNameTextView.getText().toString().length()==0 ||
                mPhoneTextView.getText().toString().length()==0
                || mEmailTextView.getText().toString().length()==0) {
            return false;
        }else{
            return true;
        }
    }




    private boolean someDataEntered(){
        if(mNameTextView.getText().toString().length()>0 ||
                mEmailTextView.getText().toString().length()>0 ||
                mPhoneTextView.getText().toString().length()>0){
            return true;
        }else{
            return false;
        }
    }


    @Override
    public void onBackPressed() {
        if(someDataEntered()){
            ContactsDialog dialog=new ContactsDialog();
            Bundle args=new Bundle();
            args.putString(ContactsDialog.DIALOG_TYPE,ContactsDialog.CONFIRM_EXIT);
            dialog.setArguments(args);
            dialog.show(getSupportFragmentManager(),"confirm-exit");

        }else{
            super.onBackPressed();
        }
    }
}
