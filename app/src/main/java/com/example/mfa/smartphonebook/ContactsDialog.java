package com.example.mfa.smartphonebook;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.ContentResolver;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.provider.ContactsContract;
import android.support.annotation.NonNull;
import android.support.v4.app.DialogFragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.TextView;

/**
 * Created by mfa on 02.01.2016.
 */
public class ContactsDialog extends DialogFragment {

    private LayoutInflater mLayoutInFlater;
    public static final String DIALOG_TYPE ="command";
    public static final String DELETE_RECORD="deleteRecord";
    public static final String CONFIRM_EXIT="confirmExit";


    @NonNull
    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        mLayoutInFlater=getActivity().getLayoutInflater();
        final View view =mLayoutInFlater.inflate(R.layout.contact_layout,null);
        String command=getArguments().getString(DIALOG_TYPE);
        if(command.equals(DELETE_RECORD)){
            final int _id=getArguments().getInt(PhoneContract.ContactsColumn.CONTACT_ID);
            String name=getArguments().getString(PhoneContract.ContactsColumn.CONTACT_NAME);
            TextView popupMessage=(TextView) view.findViewById(R.id.popup_message);
            popupMessage.setText("Are you sure you want to delete "+name+" from your contacts?");
            builder.setView(view).setPositiveButton("OK", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    ContentResolver contentResolver=getActivity().getContentResolver();
                    Uri uri = PhoneContract.Contacts.buildContactsUri(String.valueOf(_id));
                    contentResolver.delete(uri,null,null);
                    Intent intent =new Intent(getActivity().getApplicationContext(),MainActivity.class);
                    intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                    startActivity(intent);


                }
            });
        }else if (command.equals(CONFIRM_EXIT)){

            TextView popupMessage=(TextView) view.findViewById(R.id.popup_message);
            popupMessage.setText("Are you sure you want to exit without saving?");
            builder.setView(view).setPositiveButton("OK", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                  getActivity().finish();

                }
            });

        }


        return builder.create();
    }
}
