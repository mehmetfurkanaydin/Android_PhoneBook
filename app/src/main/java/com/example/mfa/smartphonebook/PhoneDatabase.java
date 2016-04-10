package com.example.mfa.smartphonebook;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.provider.BaseColumns;
import android.provider.ContactsContract;

/**
 * Created by mfa on 01.01.2016.
 */
public class PhoneDatabase extends SQLiteOpenHelper {

    private static final String TAG = PhoneDatabase.class.getSimpleName();
    private static final String DATABASE_NAME="contacts.db";
    private static final int DATABASE_VERSION = 2;
    private final Context mContext;

    interface Tables{
        String CONTACTS = "contacts";
    }

    public PhoneDatabase(Context context){
        super(context,DATABASE_NAME,null,DATABASE_VERSION);
        mContext=context;

    }


    @Override
    public void onCreate(SQLiteDatabase db) {

        db.execSQL("CREATE TABLE " + Tables.CONTACTS + "("
                    + BaseColumns._ID+ " INTEGER PRIMARY KEY AUTOINCREMENT,"
                    + PhoneContract.ContactsColumn.CONTACT_NAME+ " TEXT NOT NULL,"
                    + PhoneContract.ContactsColumn.CONTACT_EMAIL+" TEXT,"
                    + PhoneContract.ContactsColumn.CONTACT_PHONE+" TEXT,"
                    + PhoneContract.ContactsColumn.CONTACT_WORK_PHONE+" TEXT,"
                    + PhoneContract.ContactsColumn.CONTACT_HOME_X+" TEXT,"
                    + PhoneContract.ContactsColumn.CONTACT_HOME_Y+" TEXT,"
                    + PhoneContract.ContactsColumn.CONTACT_WORK_X+" TEXT,"
                    + PhoneContract.ContactsColumn.CONTACT_WORK_Y+" TEXT,"
                    + PhoneContract.ContactsColumn.CONTACT_DURATION_IN+" TEXT,"
                    + PhoneContract.ContactsColumn.CONTACT_DURATION_OUT+" TEXT,"
                    + PhoneContract.ContactsColumn.CONTACT_NUMBER_IN+" TEXT,"
                    + PhoneContract.ContactsColumn.CONTACT_NUMBER_OUT+" TEXT,"
                    + PhoneContract.ContactsColumn.CONTACT_MISS_CALL+" TEXT,"
                    + PhoneContract.ContactsColumn.CONTACT_SENT_MESSAGE+" TEXT,"
                    + PhoneContract.ContactsColumn.CONTACT_REC_MESSAGE+" TEXT)");

    }


    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

            int version =oldVersion;
        if(version==1){
         version=2;
        }

        if(version!=DATABASE_VERSION){

            db.execSQL("DROP TABLE IF EXIST " + Tables.CONTACTS);
            onCreate(db);
        }


    }


    public static void deleteDatabase(Context context){

        context.deleteDatabase(DATABASE_NAME);
    }


}
