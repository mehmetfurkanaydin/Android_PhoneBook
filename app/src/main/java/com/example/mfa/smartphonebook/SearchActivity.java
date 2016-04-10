package com.example.mfa.smartphonebook;

import android.content.ContentResolver;
import android.os.Bundle;
import android.provider.ContactsContract;
import android.support.annotation.Nullable;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.LoaderManager;
import android.support.v4.content.Loader;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;

import java.util.List;

/**
 * Created by mfa on 03.01.2016.
 */
public class SearchActivity extends FragmentActivity implements LoaderManager.LoaderCallbacks<List<Contact>>{

    private static final String LOG_TAG= SearchActivity.class.getSimpleName();
    private ContactsCustomAdapter mContactsCustomAdapter;
    private static int LOADER_ID=2;
    private ContentResolver mContentResolver ;
    private List<Contact> contactsRetrieved;
    private ListView listView;
    private EditText mSearchEditText;
    private Button mSearchContactButton;
    private String matchText;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.search_layout);
        listView=(ListView) findViewById(R.id.searchResultList);
        mSearchEditText=(EditText) findViewById(R.id.search_name);
        mSearchContactButton = (Button) findViewById(R.id.search_button);
        mContentResolver=getContentResolver();
        mContactsCustomAdapter = new ContactsCustomAdapter(SearchActivity.this,getSupportFragmentManager());
        listView.setAdapter(mContactsCustomAdapter);


        mSearchContactButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                matchText=mSearchEditText.getText().toString();
                getSupportLoaderManager().initLoader(LOADER_ID++,null,SearchActivity.this);
            }
        });

    }


    @Override
    public Loader<List<Contact>> onCreateLoader(int id, Bundle args) {
        return new ContactsSearchListLoader(SearchActivity.this, PhoneContract.URI_TABLE,this.mContentResolver,matchText);

    }


    @Override
    public void onLoadFinished(Loader<List<Contact>> loader, List<Contact> contacts) {
        mContactsCustomAdapter.setData(contacts);
        this.contactsRetrieved=contacts;
    }


    @Override
    public void onLoaderReset(Loader<List<Contact>> loader) {
        mContactsCustomAdapter.setData(null);
    }
}
