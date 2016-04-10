package com.example.mfa.smartphonebook;

import android.*;
import android.Manifest;
import android.app.Activity;
import android.app.FragmentTransaction;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.Toolbar;
import android.view.MenuInflater;
import android.view.View;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Button;

public class MainActivity extends FragmentActivity {

    @Override
    public boolean shouldUpRecreateTask(Intent targetIntent) {
        return super.shouldUpRecreateTask(targetIntent);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        FragmentManager fragmentManager=getSupportFragmentManager();
        if(fragmentManager.findFragmentById(android.R.id.content)==null){
            ContactListFragment contactListFragment= new ContactListFragment();
            fragmentManager.beginTransaction().add(android.R.id.content, contactListFragment).commit();
        }

        SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(this);
        if(!prefs.getBoolean("firstTime", false)) {
            final int REQUEST_CODE_ASK_PERMISSION = 123;
            int hasReadContactsPermission =
                    ContextCompat.checkSelfPermission(MainActivity.this, android.Manifest.permission.READ_CONTACTS);
            if (hasReadContactsPermission != PackageManager.PERMISSION_GRANTED) {
                if (hasReadContactsPermission != PackageManager.PERMISSION_GRANTED) {
                    if (!ActivityCompat.shouldShowRequestPermissionRationale(MainActivity.this, android.Manifest.permission.READ_CONTACTS)) {

                        ActivityCompat.requestPermissions(MainActivity.this, new String[]{android.Manifest.permission.READ_CONTACTS}, REQUEST_CODE_ASK_PERMISSION);
                        return;


                    }
                }
            }



            ImportContacts imCont = new ImportContacts(getApplicationContext());
            SharedPreferences.Editor editor = prefs.edit();
            editor.putBoolean("firstTime", true);
            editor.commit();
        }


       // setContentView(R.layout.activity_main);

    }




    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater =getMenuInflater();
        inflater.inflate(R.menu.menu_main,menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        switch (item.getItemId()){

            case R.id.addRecord:
                Intent intent =new Intent(MainActivity.this,AddActivity.class);
                startActivity(intent);
                break;


            case R.id.searchRecords:
                Intent searchIntent = new Intent(MainActivity.this,SearchActivity.class);
                startActivity(searchIntent);

        }


        return super.onOptionsItemSelected(item);
    }
}
