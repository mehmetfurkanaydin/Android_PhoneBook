package com.example.mfa.smartphonebook;

import android.*;
import android.Manifest;
import android.app.FragmentTransaction;
import android.content.ContentProvider;
import android.content.ContentResolver;
import android.content.ContentValues;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.net.Uri;
import android.provider.ContactsContract;
import android.support.annotation.Nullable;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.ListFragment;
import android.support.v4.content.ContextCompat;

/**
 * Created by mfa on 03.01.2016.
 */
public class ImportContacts {



    public ImportContacts(Context context) {
        ContentResolver cr =context.getContentResolver();
        Cursor cur = cr.query(ContactsContract.Contacts.CONTENT_URI, null, null, null, null);
        ContentValues values=new ContentValues();
        String phone=null;
        String workPhone=null;
        String email=null;


        if (cur.getCount() > 0) {
            while (cur.moveToNext()) {
                String id = cur.getString(
                        cur.getColumnIndex(ContactsContract.Contacts._ID));
                String name = cur.getString(
                        cur.getColumnIndex(ContactsContract.Contacts.DISPLAY_NAME));
                if (Integer.parseInt(cur.getString(cur.getColumnIndex(ContactsContract.Contacts.HAS_PHONE_NUMBER))) > 0) {
                    Cursor pCur = cr.query(
                            ContactsContract.CommonDataKinds.Phone.CONTENT_URI,
                            null,
                            ContactsContract.CommonDataKinds.Phone.CONTACT_ID + " = ?",
                            new String[]{id}, null);
                    while (pCur.moveToNext()) {
                        String number = pCur.getString(pCur.getColumnIndex(ContactsContract.CommonDataKinds.Phone.NUMBER));
                       phone=number;
                    }
                    pCur.close();


                    Cursor emails = cr.query(ContactsContract.CommonDataKinds.Email.CONTENT_URI, null,
                            ContactsContract.CommonDataKinds.Email.CONTACT_ID + " = " + id, null, null);
                    while (emails.moveToNext()) {
                         email = emails.getString(emails.getColumnIndex(ContactsContract.CommonDataKinds.Email.DATA));
                    }
                    emails.close();

                }
                values.put(PhoneContract.ContactsColumn.CONTACT_NAME, name);
                values.put(PhoneContract.ContactsColumn.CONTACT_PHONE, phone);
                //values.put(PhoneContract.ContactsColumn.CONTACT_EMAIL,email);




                Uri returned=cr.insert(PhoneContract.URI_TABLE,values);



            }
        }



    }










}
