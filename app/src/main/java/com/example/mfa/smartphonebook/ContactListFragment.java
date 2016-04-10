package com.example.mfa.smartphonebook;

import android.content.ContentResolver;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.ListFragment;
import android.support.v4.app.LoaderManager;
import android.support.v4.content.Loader;

import java.util.List;

/**
 * Created by mfa on 02.01.2016.
 */
public class ContactListFragment extends ListFragment
        implements LoaderManager.LoaderCallbacks<List<Contact>> {

    private static final String LOG_TAG = ContactListFragment.class.getSimpleName();
    private ContactsCustomAdapter mAdapter;
    private static final int LOADER_ID=1;
    private ContentResolver mContentResolver;
    private List<Contact> mContacts;

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        setHasOptionsMenu(true);
        mContentResolver=getActivity().getContentResolver();
        mAdapter = new ContactsCustomAdapter(getActivity(),getChildFragmentManager());
        setEmptyText("No Friends");
        setListAdapter(mAdapter);
        setListShown(false);
        getLoaderManager().initLoader(LOADER_ID,null,this);

    }

    @Override
    public Loader<List<Contact>> onCreateLoader(int id, Bundle args) {
        mContentResolver = getActivity().getContentResolver();
        return new ContactsListLoader(getActivity(), PhoneContract.URI_TABLE, mContentResolver);
    }

    @Override
    public void onLoadFinished(Loader<List<Contact>> loader, List<Contact> contacts) {
        mAdapter.setData(contacts);
        mContacts=contacts;
        if(isResumed()){
            setListShown(true);
        }else{
            setListShownNoAnimation(true);
        }
    }

    @Override
    public void onLoaderReset(Loader<List<Contact>> loader) {
        mAdapter.setData(null);
    }
}
