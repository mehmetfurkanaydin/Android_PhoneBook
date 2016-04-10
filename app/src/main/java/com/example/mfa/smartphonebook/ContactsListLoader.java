package com.example.mfa.smartphonebook;


import android.content.ContentResolver;
import android.content.Context;
import android.database.Cursor;
import android.net.Uri;
import android.provider.BaseColumns;
import android.support.v4.content.AsyncTaskLoader;
import android.util.Log;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by mfa on 02.01.2016.
 */
public class ContactsListLoader extends AsyncTaskLoader<List<Contact>> {
    private static final String LOG_TAG= ContactsListLoader.class.getSimpleName();
    private List<Contact> mContacts;
    private ContentResolver mContentResolver;
    private Cursor mCursor;

    public ContactsListLoader(Context context,Uri uri,ContentResolver contentResolver){
        super(context);
        mContentResolver=contentResolver;
    }

    @Override
    public List<Contact> loadInBackground() {

        String[] projection ={BaseColumns._ID,
                PhoneContract.ContactsColumn.CONTACT_NAME,
                PhoneContract.ContactsColumn.CONTACT_PHONE,
                PhoneContract.ContactsColumn.CONTACT_EMAIL,
                PhoneContract.ContactsColumn.CONTACT_WORK_PHONE,
                PhoneContract.ContactsColumn.CONTACT_HOME_X,
                PhoneContract.ContactsColumn.CONTACT_HOME_Y,
                PhoneContract.ContactsColumn.CONTACT_WORK_X,
                PhoneContract.ContactsColumn.CONTACT_WORK_Y,
                PhoneContract.ContactsColumn.CONTACT_DURATION_IN,
                PhoneContract.ContactsColumn.CONTACT_DURATION_OUT,
                PhoneContract.ContactsColumn.CONTACT_NUMBER_IN,
                PhoneContract.ContactsColumn.CONTACT_NUMBER_OUT,
                PhoneContract.ContactsColumn.CONTACT_MISS_CALL,
                PhoneContract.ContactsColumn.CONTACT_SENT_MESSAGE,
                PhoneContract.ContactsColumn.CONTACT_REC_MESSAGE
               };

        List<Contact> entries = new ArrayList<Contact>();

        mCursor=mContentResolver.query(PhoneContract.URI_TABLE,projection,null,null,null);
        if(mCursor!=null){
            if(mCursor.moveToFirst()){
                do{
                    int _id=mCursor.getInt(mCursor.getColumnIndex(BaseColumns._ID));
                    String name = mCursor.getString(mCursor.getColumnIndex(PhoneContract.ContactsColumn.CONTACT_NAME));
                    String phone = mCursor.getString(mCursor.getColumnIndex(PhoneContract.ContactsColumn.CONTACT_PHONE));
                    String email = mCursor.getString(mCursor.getColumnIndex(PhoneContract.ContactsColumn.CONTACT_EMAIL));
                    String work_phone = mCursor.getString(mCursor.getColumnIndex(PhoneContract.ContactsColumn.CONTACT_WORK_PHONE));
                    String home_x= mCursor.getString(mCursor.getColumnIndex(PhoneContract.ContactsColumn.CONTACT_HOME_X));
                    String home_y= mCursor.getString(mCursor.getColumnIndex(PhoneContract.ContactsColumn.CONTACT_HOME_Y));
                    String work_x= mCursor.getString(mCursor.getColumnIndex(PhoneContract.ContactsColumn.CONTACT_WORK_X));
                    String work_y= mCursor.getString(mCursor.getColumnIndex(PhoneContract.ContactsColumn.CONTACT_WORK_Y));
                    String duration_in=mCursor.getString(mCursor.getColumnIndex(PhoneContract.ContactsColumn.CONTACT_DURATION_IN));
                    String duration_out=mCursor.getString(mCursor.getColumnIndex(PhoneContract.ContactsColumn.CONTACT_DURATION_OUT));
                    String number_in=mCursor.getString(mCursor.getColumnIndex(PhoneContract.ContactsColumn.CONTACT_NUMBER_IN));
                    String number_out=mCursor.getString(mCursor.getColumnIndex(PhoneContract.ContactsColumn.CONTACT_DURATION_OUT));
                    String miss_call=mCursor.getString(mCursor.getColumnIndex(PhoneContract.ContactsColumn.CONTACT_MISS_CALL));
                    String sent_message=mCursor.getString(mCursor.getColumnIndex(PhoneContract.ContactsColumn.CONTACT_SENT_MESSAGE));
                    String rec_message=mCursor.getString(mCursor.getColumnIndex(PhoneContract.ContactsColumn.CONTACT_REC_MESSAGE));

                    Contact contact = new Contact(_id,name,phone,email);
                    contact.setDuration_in(duration_in);
                    contact.setDuration_out(duration_out);
                    contact.setHome_x(home_x);
                    contact.setHome_y(home_y);
                    contact.setWork_x(work_x);
                    contact.setWork_y(work_y);
                    contact.setRec_message(rec_message);
                    contact.setSent_message(sent_message);
                    contact.setNumber_in(number_in);
                    contact.setNumber_out(number_out);
                    contact.setMiss_call(miss_call);
                    contact.setWork_phone(work_phone);

                    entries.add(contact);

                }while(mCursor.moveToNext());
            }
        }


        return entries;

    }

    @Override
    public void deliverResult(List<Contact> contacts) {
        if(isReset()){
            if(contacts != null){
                mCursor.close();
            }
        }
        List<Contact> oldContactList=mContacts;
        if(mContacts == null ||  mContacts.size() == 0 ){
            Log.d(LOG_TAG,"+++++ No Data returned");
        }
        mContacts = contacts;
         if(isStarted()){
             super.deliverResult(contacts);
         }

        if(oldContactList != null  && oldContactList != contacts){
            mCursor.close();
        }
    }

    @Override
    protected void onStartLoading() {
        if(mContacts!= null){
            deliverResult(mContacts);
        }

        if(takeContentChanged() || mContacts == null ){
            forceLoad();
        }
    }

    @Override
    protected void onStopLoading() {
        cancelLoad();
    }

    @Override
    protected void onReset() {
        onStopLoading();
        if(mCursor!=null){
            mCursor.close();
        }

        mCursor=null;
    }

    @Override
    public void onCanceled(List<Contact> contacts) {
        super.onCanceled(contacts);

        if(mCursor!=null){
            mCursor.close();
        }
    }


    @Override
    public void forceLoad() {
        super.forceLoad();
    }
}
