package com.example.mfa.smartphonebook;

import android.*;
import android.Manifest;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.os.Bundle;
import android.provider.CallLog;
import android.support.annotation.Nullable;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.FragmentActivity;
import android.support.v4.content.ContextCompat;
import android.text.method.ScrollingMovementMethod;
import android.widget.EditText;
import android.widget.TextView;

import org.w3c.dom.Text;

import java.util.Date;

/**
 * Created by mfa on 06.01.2016.
 */
public class CallLogs extends FragmentActivity {

    TextView mCallLogs,mMaxLogs;
    private final int REQUEST_CODE=123;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.call_log_layout);

        mCallLogs=(TextView) findViewById(R.id.call_logs);
        mMaxLogs=(TextView) findViewById(R.id.max_logs);
        mMaxLogs.setMovementMethod(ScrollingMovementMethod.getInstance());
        Intent intent= getIntent();
        final String phone = intent.getStringExtra("ContactPhone");


        if (ContextCompat.checkSelfPermission(CallLogs.this,
                Manifest.permission.READ_CALL_LOG)
                != PackageManager.PERMISSION_GRANTED) {

            if (ActivityCompat.shouldShowRequestPermissionRationale(CallLogs.this,
                    Manifest.permission.READ_CALL_LOG)) {


            } else {
                ActivityCompat.requestPermissions(CallLogs.this,
                        new String[]{Manifest.permission.READ_CALL_LOG},
                        REQUEST_CODE);
            }
        }

        StringBuffer stringBuffer = new StringBuffer();
        Cursor cursor = getApplicationContext().getContentResolver().query(CallLog.Calls.CONTENT_URI,
                null, null, null, CallLog.Calls.DATE + " DESC");
        int number = cursor.getColumnIndex(CallLog.Calls.NUMBER);
        int type = cursor.getColumnIndex(CallLog.Calls.TYPE);
        int date = cursor.getColumnIndex(CallLog.Calls.DATE);
        int duration = cursor.getColumnIndex(CallLog.Calls.DURATION);
        int maxIncomCall=0;
        int maxOutCall=0;
        int maxMissCall=0;
        int maxDurat=0;
        String maxText;


        while (cursor.moveToNext()) {
            String phNumber = cursor.getString(number);
            String callType = cursor.getString(type);
            String callDate = cursor.getString(date);
            Date callDayTime = new Date(Long.valueOf(callDate));
            String callDuration = cursor.getString(duration);
            String dir = null;
            int dircode = Integer.parseInt(callType);
            switch (dircode) {
                case CallLog.Calls.OUTGOING_TYPE:
                    dir = "OUTGOING";
                    break;
                case CallLog.Calls.INCOMING_TYPE:
                    dir = "INCOMING";
                    break;

                case CallLog.Calls.MISSED_TYPE:
                    dir = "MISSED";
                    break;
            }

            if( String.valueOf(phNumber).equals(phone)) {
                switch(dir){
                    case "OUTGOING":
                        maxOutCall=maxOutCall+Integer.valueOf(callDuration);
                        maxDurat=maxDurat+Integer.valueOf(callDuration);
                        break;
                    case "INCOMING":
                        maxIncomCall=maxIncomCall+Integer.valueOf(callDuration);
                        maxDurat=maxDurat+Integer.valueOf(callDuration);
                        break;

                    case "MISSED":
                        maxMissCall=maxMissCall+1;
                        break;
                }
                stringBuffer.append("\nPhone Number:--- " + phNumber + " \nCall Type:--- "
                        + dir + " \nCall Date:--- " + callDayTime
                        + " \nCall duration in sec :--- " + callDuration);
                stringBuffer.append("\n----------------------------------");
            }
        }
        cursor.close();

        maxText="Total Incoming Duration = " + String.valueOf(maxIncomCall) +" \n"+
                 "Total Outcoming Duration= " + String.valueOf(maxOutCall) + "\n"+
                 "Total Duration = "+ String.valueOf(maxDurat)+"\n"+
                 "Number Of Missed = "+ String.valueOf(maxMissCall) + "\n" +
                "----------------------------------";

        mMaxLogs.setText(maxText);
        mCallLogs.setText(stringBuffer);
        return;


    }



}
