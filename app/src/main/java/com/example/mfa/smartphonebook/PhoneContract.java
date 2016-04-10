package com.example.mfa.smartphonebook;

import android.net.Uri;
import android.provider.BaseColumns;

/**
 * Created by mfa on 01.01.2016.
 */
public class PhoneContract {

    interface ContactsColumn{

        String CONTACT_ID="_id";
        String CONTACT_NAME="contact_name";
        String CONTACT_EMAIL="contact_email";
        String CONTACT_PHONE="contact_phone";
        String CONTACT_WORK_PHONE="contact_work_phone";
        String CONTACT_HOME_X="contact_home_x";
        String CONTACT_HOME_Y="contact_home_y";
        String CONTACT_WORK_X="contact_work_x";
        String CONTACT_WORK_Y="contact_work_y";
        String CONTACT_DURATION_IN="contact_duration_in";
        String CONTACT_NUMBER_IN="contact_number_in";
        String CONTACT_DURATION_OUT="contact_duration_out";
        String CONTACT_NUMBER_OUT="contact_number_out";
        String CONTACT_MISS_CALL="contact_miss";
        String CONTACT_SENT_MESSAGE="contact_sent_message";
        String CONTACT_REC_MESSAGE="contact_rec_message";


    }

    public static final String CONTENT_AUTHORITY = "com.example.mfa.smartphonebook.provider";
    public static final Uri BASE_CONTENT_URI = Uri.parse("content://"+CONTENT_AUTHORITY);

    private static final String PATH_CONTACTS="contacts";
    public static final Uri URI_TABLE = Uri.parse(BASE_CONTENT_URI.toString()+"/"+PATH_CONTACTS);
    public static final String[] TOP_LEVEL_PATHS={
            PATH_CONTACTS
    };

   public static class Contacts implements ContactsColumn,BaseColumns{
       public static final Uri CONTENT_URI=
               BASE_CONTENT_URI.buildUpon().appendEncodedPath(PATH_CONTACTS).build();
       public static final String CONTENT_TYPE="vnd.android.cursor.dir/vnd." + CONTENT_AUTHORITY +".contacts";
       public static final String CONTENT_ITEM_TYPE="vnd.android.cursor.item/vnd." + CONTENT_AUTHORITY +".contacts";

       public static Uri buildContactsUri(String contactId){
           return CONTENT_URI.buildUpon().appendEncodedPath(contactId).build();
       }

       public static String getContactId(Uri uri){
           return uri.getPathSegments().get(1);
       }

   }


}
