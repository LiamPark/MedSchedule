package com.example.liam.medschedule;

import android.app.DatePickerDialog;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.DatePicker;
import android.widget.TextView;

import org.w3c.dom.Text;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;


public class ScheduleActivity extends AppCompatActivity implements BottomNavigationView.OnNavigationItemSelectedListener {

    //homescreen activity
    private static final String TAG = "ScheduleActivity";
    private Toolbar toolbar;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        Log.i(TAG,"onCreate");
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_schedule);

        //load toolbar as actionbar, bottom navigation and fragment for main screen
        topBar();
        bottomBar();
        loadFragment(new HomeFragment());
    }

    //initialize toolbar
    protected void topBar(){
        toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        toolbar.setNavigationIcon(R.mipmap.ic_launcher);
    }

    //inflate option menu which is add button in this activity
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {

        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.toolbar_menu, menu);
        return true;
    }

    //intent to AddMedicineActivity when add button clicked
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()){
            case R.id.action_add:
                Intent i = new Intent(this, AddMedicineActivity.class);
                startActivity(i);
                break;
                default:
                    break;
            }
            return true;
    }

    //initialize bottomBar
    protected void bottomBar(){
        Log.i(TAG,"bottomBar");
        BottomNavigationView bottomBar = findViewById(R.id.navigation);
        bottomBar.setOnNavigationItemSelectedListener(this);
    }

    //switching the fragments by pressing the bottom Navigation View
    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
        Log.i(TAG,"FragmentLoading");
        Fragment fragment = null;

        switch (item.getItemId()) {
            case R.id.navigation_home:
                fragment = new HomeFragment();
                break;

            case R.id.navigation_list:
                fragment = new ListFragment();
                break;
        }
        return loadFragment(fragment);
    }

    //load the fragment part
    private boolean loadFragment(Fragment fragment){
        Log.i(TAG,"Fragment switching");
        //switching fragment
        if(fragment != null){
            getSupportFragmentManager()
                    .beginTransaction()
                    .replace(R.id.main_container, fragment)
                    .commit();
            return true;
        }
        return false;
    }
}
