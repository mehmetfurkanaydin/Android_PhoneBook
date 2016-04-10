package com.example.mfa.smartphonebook;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.FragmentManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.TextView;

import java.util.List;

/**
 * Created by mfa on 02.01.2016.
 */
public class ContactsCustomAdapter extends ArrayAdapter<Contact> {

    private LayoutInflater mLayoutInflater;
    private static FragmentManager sFragmentManager;

    public ContactsCustomAdapter(Context context,FragmentManager fragmentManager){
        super(context,android.R.layout.simple_list_item_2);
        mLayoutInflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        sFragmentManager=fragmentManager;
    }


    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        final View view;
        if(convertView == null){
            view=mLayoutInflater.inflate(R.layout.custom_contact,parent,false);
        }else{
            view=convertView;
        }

        final Contact contact = getItem(position);
        final int _id=contact.getId();
        final String name=contact.getName();
        final String phone=contact.getPhone();
        final String email = contact.getEmail();


        ((TextView) view.findViewById(R.id.contact_name)).setText(name);
        ((TextView) view.findViewById(R.id.contact_phone)).setText(phone);
        ((TextView) view.findViewById(R.id.contact_email)).setText(email);


        Button editButton=(Button) view.findViewById(R.id.edit);
        editButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(getContext(),EditActivity.class);
                intent.putExtra(PhoneContract.ContactsColumn.CONTACT_ID, String.valueOf(_id));
                intent.putExtra(PhoneContract.ContactsColumn.CONTACT_NAME, name);
                intent.putExtra(PhoneContract.ContactsColumn.CONTACT_PHONE, phone);
                intent.putExtra(PhoneContract.ContactsColumn.CONTACT_EMAIL, email);

                getContext().startActivity(intent);

            }
        });


        Button deleteButton = (Button) view.findViewById(R.id.delete);
        deleteButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ContactsDialog dialog =new ContactsDialog();
                Bundle args = new Bundle();
                args.putString(ContactsDialog.DIALOG_TYPE, ContactsDialog.DELETE_RECORD);
                args.putInt(PhoneContract.ContactsColumn.CONTACT_ID,_id);
                args.putString(PhoneContract.ContactsColumn.CONTACT_NAME,name);
                dialog.setArguments(args);
                dialog.show(sFragmentManager,"delete-record");



            }
        });

        Button infoButton = (Button) view.findViewById(R.id.info);
        infoButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(getContext(),InformationContact.class);
                intent.putExtra(PhoneContract.ContactsColumn.CONTACT_ID, String.valueOf(_id));
                intent.putExtra(PhoneContract.ContactsColumn.CONTACT_NAME, name);
                intent.putExtra(PhoneContract.ContactsColumn.CONTACT_PHONE, phone);
                intent.putExtra(PhoneContract.ContactsColumn.CONTACT_EMAIL, email);

                getContext().startActivity(intent);
            }
        });
        return view;

    }

    public void setData(List<Contact> contacts){
            clear();
        if(contacts!=null){
            for(Contact contact:contacts){
                add(contact);
            }
        }
    }
}
