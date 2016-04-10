package com.example.mfa.smartphonebook;

import android.content.ContentProvider;
import android.content.ContentValues;
import android.content.UriMatcher;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteQueryBuilder;
import android.net.Uri;
import android.provider.BaseColumns;
import android.provider.ContactsContract;
import android.support.annotation.Nullable;
import android.text.TextUtils;
import android.util.Log;

import java.util.IllegalFormatCodePointException;

/**
 * Created by mfa on 01.01.2016.
 */
public class PhoneProvider extends ContentProvider {

    private PhoneDatabase mOpenHelper;

    private static String TAG = PhoneProvider.class.getSimpleName();
    private static final UriMatcher sUriMatcher = buildUriMatcher();


    private static final int CONTACTS=100;
    private static final int CONTACTS_ID=101;


        private static UriMatcher buildUriMatcher(){
            final UriMatcher  matcher = new UriMatcher(UriMatcher.NO_MATCH);
            final String authority = PhoneContract.CONTENT_AUTHORITY;
            matcher.addURI(authority,"contacts",CONTACTS);
            matcher.addURI(authority,"contacts/*",CONTACTS_ID);
            return matcher;
        }

    @Override
    public boolean onCreate() {
        mOpenHelper=new PhoneDatabase(getContext());
        return true;
    }

    private void deleteDatabase(){
        mOpenHelper.close();
        PhoneDatabase.deleteDatabase(getContext());
        mOpenHelper=new PhoneDatabase(getContext());
    }

    @Nullable
    @Override
    public String getType(Uri uri) {
        final int match =sUriMatcher.match(uri);
        switch(match){
            case CONTACTS:
                return PhoneContract.Contacts.CONTENT_TYPE;
            case CONTACTS_ID:
                return PhoneContract.Contacts.CONTENT_ITEM_TYPE;
            default:
                try {
                } catch(Exception exp) { throw new IllegalArgumentException(exp); }
        }
        return null;
    }


    @Nullable
    @Override
    public Cursor query(Uri uri, String[] projection, String selection, String[] selectionArgs, String sortOrder) {
        final SQLiteDatabase db= mOpenHelper.getReadableDatabase();
        final int match =sUriMatcher.match(uri);

        SQLiteQueryBuilder queryBuilder = new SQLiteQueryBuilder();
        queryBuilder.setTables(PhoneDatabase.Tables.CONTACTS);


        switch(match){
            case CONTACTS:

                break;
            case CONTACTS_ID:

                String id=PhoneContract.Contacts.getContactId(uri);
                queryBuilder.appendWhere(BaseColumns._ID+"="+id);
                break;

            default:
                try {
                } catch(Exception exp) { throw new IllegalArgumentException(exp); }

        }

        Cursor cursor = queryBuilder.query(db,projection,selection,selectionArgs,null,null,sortOrder);
        return cursor;
    }

    @Nullable
    @Override
    public Uri insert(Uri uri, ContentValues values) {
        Log.v(TAG,"insert(uri="+uri+", values="+values.toString());
        final SQLiteDatabase db= mOpenHelper.getWritableDatabase();
        final int match =sUriMatcher.match(uri);

        switch (match){

            case CONTACTS:
                long recordId = db.insertOrThrow(PhoneDatabase.Tables.CONTACTS,null,values);
                return PhoneContract.Contacts.buildContactsUri(String.valueOf(recordId));
        }

        return null;

    }


    @Override
    public int update(Uri uri, ContentValues values, String selection, String[] selectionArgs) {
        Log.v(TAG,"update(uri="+uri+", values="+values.toString());
        final SQLiteDatabase db= mOpenHelper.getWritableDatabase();
        final int match =sUriMatcher.match(uri);
        String selectionCriteria =selection;
        switch (match){
            case CONTACTS:
                //do nothing
                break;
            case CONTACTS_ID:
                String id=PhoneContract.Contacts.getContactId(uri);
                selectionCriteria=BaseColumns._ID+"=" + id
                        +(!TextUtils.isEmpty(selection) ? " AND (   " +selection +")" : "");
                break;
                default:
                    throw new IllegalArgumentException("Unknown Uri= "+uri);
        }

        return db.update(PhoneDatabase.Tables.CONTACTS,values,selectionCriteria,selectionArgs);
        }

    @Override
    public int delete(Uri uri, String selection, String[] selectionArgs) {
        Log.v(TAG,"delete(uri="+uri);

        if(uri.equals(PhoneContract.BASE_CONTENT_URI)){
            deleteDatabase();
            return 0;
        }
        final SQLiteDatabase db= mOpenHelper.getWritableDatabase();
        final int match =sUriMatcher.match(uri);

        switch (match){
            case CONTACTS_ID:
                String id=PhoneContract.Contacts.getContactId(uri);
                String selectionCriteria=BaseColumns._ID+" = "+id
                        +(!TextUtils.isEmpty(selection) ? " AND " +selection +")" : "");

                return db.delete(PhoneDatabase.Tables.CONTACTS,selectionCriteria,selectionArgs);

            default:
                throw new IllegalArgumentException("Unknown Uri= "+uri);
        }

    }
}
