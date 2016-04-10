package com.example.mfa.smartphonebook;

import android.*;
import android.Manifest;
import android.content.ContentResolver;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.provider.CallLog;
import android.support.annotation.Nullable;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.FragmentActivity;
import android.support.v4.content.ContextCompat;
import android.text.method.ScrollingMovementMethod;
import android.widget.TextView;

import java.util.Date;

/**
 * Created by mfa on 06.01.2016.
 */
public class SmsLogs extends FragmentActivity {

    private final int REQUEST_CODE = 123;
    TextView mSmsLogs,mSmsMax;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.sms_log_layout);
        Intent intent = getIntent();
        final String phone = intent.getStringExtra("ContactPhone");


        if (ContextCompat.checkSelfPermission(SmsLogs.this,
                Manifest.permission.READ_SMS)
                != PackageManager.PERMISSION_GRANTED) {

            if (ActivityCompat.shouldShowRequestPermissionRationale(SmsLogs.this,
                    Manifest.permission.READ_SMS)) {


            } else {
                ActivityCompat.requestPermissions(SmsLogs.this,
                        new String[]{Manifest.permission.READ_SMS},
                        REQUEST_CODE);
            }
        }

        mSmsLogs = (TextView) findViewById(R.id.sms_logs);
        mSmsMax=(TextView)findViewById(R.id.max_sms_logs);
        mSmsMax.setMovementMethod(ScrollingMovementMethod.getInstance());


        StringBuffer stringBuffer = new StringBuffer();
        Uri uri = Uri.parse("content://sms");
        Cursor cursor = getContentResolver().query(uri, null, null, null, null);
        int recCount=0;
        int sendCount=0;
        String maxSms;

        if (cursor.moveToFirst()) {
            for (int i = 0; i < cursor.getCount(); i++) {
                String body = cursor.getString(cursor.getColumnIndexOrThrow("body"))
                        .toString();
                String number = cursor.getString(cursor.getColumnIndexOrThrow("address"))
                        .toString();
                String date = cursor.getString(cursor.getColumnIndexOrThrow("date"))
                        .toString();
                Date smsDayTime = new Date(Long.valueOf(date));
                String type = cursor.getString(cursor.getColumnIndexOrThrow("type"))
                        .toString();
                String typeOfSMS = null;
                switch (Integer.parseInt(type)) {
                    case 1:
                        typeOfSMS = "INBOX";
                        break;

                    case 2:
                        typeOfSMS = "SENT";
                        break;

                    case 3:
                        typeOfSMS = "DRAFT";
                        break;
                }


                if (number.equals(phone)) {

                    switch(typeOfSMS){
                        case "INBOX":
                            recCount++;
                            break;
                        case "SENT":
                            sendCount++;
                            break;

                    }
                    stringBuffer.append("\nPhone Number:--- " + number + " \nMessage Type:--- "
                            + typeOfSMS + " \nMessage Date:--- " + smsDayTime
                            + " \nMessage Body:--- " + body);
                    stringBuffer.append("\n----------------------------------");
                    cursor.moveToNext();
                }

            }
            cursor.close();

            maxSms= "Total Send Message = " + String.valueOf(sendCount) +" \n"+
                    "Total Recieve Message = " + String.valueOf(recCount) + "\n"+
                    "----------------------------------";
            mSmsLogs.setText(stringBuffer);
            mSmsMax.setText(maxSms);


        }
    }
}
