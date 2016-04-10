package com.example.mfa.smartphonebook;

import android.*;
import android.Manifest;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.support.v4.content.ContextCompat;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.telephony.SmsManager;
import android.widget.Toast;

/**
 * Created by mfa on 04.01.2016.
 */
public class InformationContact extends FragmentActivity {

    private final String LOG_TAG=AddActivity.class.getSimpleName();
    private TextView mNameTextView,mPhoneTextView;
    private Button mCallButton,mSmsButton,mCallHistory,mSmsHistory;
    private EditText mSmsText;


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.contact_show);
        final int REQUEST_CODE_ASK_PERMISSION = 123;
        mNameTextView=(TextView) findViewById(R.id.contact_name);
        mPhoneTextView=(TextView) findViewById(R.id.contact_phone);
        mCallButton=(Button) findViewById(R.id.call_button);
        mSmsButton=(Button) findViewById(R.id.sms_button);
        mCallHistory=(Button) findViewById(R.id.calls_logs);
        mSmsHistory=(Button) findViewById(R.id.sms_logs);
        mSmsText=(EditText)findViewById(R.id.smsText);


        Intent intent= getIntent();
        final String _id = intent.getStringExtra(PhoneContract.ContactsColumn.CONTACT_ID);
        final String name = intent.getStringExtra(PhoneContract.ContactsColumn.CONTACT_NAME);
        final String phone = intent.getStringExtra(PhoneContract.ContactsColumn.CONTACT_PHONE);
        final String email = intent.getStringExtra(PhoneContract.ContactsColumn.CONTACT_EMAIL);

        mNameTextView.setText(name);
        mPhoneTextView.setText(phone);


        mCallButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (ContextCompat.checkSelfPermission(InformationContact.this,
                        android.Manifest.permission.CALL_PHONE)
                        != PackageManager.PERMISSION_GRANTED) {

                    if (ActivityCompat.shouldShowRequestPermissionRationale(InformationContact.this,
                            android.Manifest.permission.CALL_PHONE)) {

                    } else {

                        ActivityCompat.requestPermissions(InformationContact.this,
                                new String[]{Manifest.permission.CALL_PHONE},
                                REQUEST_CODE_ASK_PERMISSION);
                    }
                }

                Intent callIntent = new Intent(Intent.ACTION_CALL);
                callIntent.setData(Uri.parse("tel:" + phone));
                try {
                    startActivity(callIntent);
                } catch (Exception e) {
                    Log.e(LOG_TAG, "Error" + e.getMessage());
                }
            }
        });



        mSmsButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (ContextCompat.checkSelfPermission(InformationContact.this,
                        Manifest.permission.SEND_SMS)
                        != PackageManager.PERMISSION_GRANTED) {

                    if (ActivityCompat.shouldShowRequestPermissionRationale(InformationContact.this,
                            Manifest.permission.SEND_SMS)) {


                    } else {

                        ActivityCompat.requestPermissions(InformationContact.this,
                                new String[]{Manifest.permission.SEND_SMS},
                                REQUEST_CODE_ASK_PERMISSION);
                    }
                }

                String MyMessage = mSmsText.getText().toString();

                if (MyMessage.matches("")) {
                    Toast.makeText(getApplicationContext(), "SMS body is an empty.", Toast.LENGTH_LONG).show();

                } else {
                    SmsManager sms = SmsManager.getDefault();
                    sms.sendTextMessage(phone, null, MyMessage, null, null);
                    Toast.makeText(getApplicationContext(), "SMS sent.", Toast.LENGTH_LONG).show();
                }

            }
        });


        mCallHistory.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(InformationContact.this,CallLogs.class);
                intent.putExtra(_id, String.valueOf(_id));
                intent.putExtra("ContactName", name);
                intent.putExtra("ContactPhone", phone);
                intent.putExtra(email, email);

                startActivity(intent);
            }
        });


        mSmsHistory.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(InformationContact.this,SmsLogs.class);
                intent.putExtra(_id, String.valueOf(_id));
                intent.putExtra("ContactName", name);
                intent.putExtra("ContactPhone", phone);
                intent.putExtra(email, email);

                startActivity(intent);
            }
        });
    }


}
