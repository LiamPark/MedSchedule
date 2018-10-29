package com.example.liam.medschedule;

import android.app.AlertDialog;
import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.support.constraint.ConstraintLayout;
import android.support.v4.app.NavUtils;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.text.format.DateFormat;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.Toast;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;


public class AddMedicineActivity extends AppCompatActivity {

    private static final String TAG = "AddMedicineActivity";
    private Toolbar toolbar;

    EditText medicineName, medicineDose, doseUnit, medicineDesc;
    TextView medicineDate;
    Button addButton;
    Intent intent;
    private DatePickerDialog.OnDateSetListener datePickerListener;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_medicine);
        toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        //connect variables with id
        medicineName = (EditText) findViewById(R.id.medicineEditText);
        medicineDose = (EditText) findViewById(R.id.doseEditText);
        doseUnit = (EditText) findViewById(R.id.unitEditText);
        medicineDesc = (EditText) findViewById(R.id.descEditText);
        addButton = (Button) findViewById(R.id.addMedicineBtn);
        medicineDate = (TextView) findViewById(R.id.datePickerText);
        datePicker();
        addMedicine();
    }

    //datepicker initialized and activated when the time has been chosne.
    public void datePicker() {
        medicineDate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Calendar cal = Calendar.getInstance();
                int year = cal.get(Calendar.YEAR);
                int month = cal.get(Calendar.MONTH);
                int day = cal.get(Calendar.DAY_OF_MONTH);

                DatePickerDialog dateDialog = new DatePickerDialog(
                        AddMedicineActivity.this,
                        android.R.style.Theme_Material_Dialog_MinWidth,
                        datePickerListener,
                        year, month, day);
                dateDialog.show();
            }
        });

        datePickerListener = new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
                Log.d(TAG, "onDateSet: date: " + year + "/" + month + "/" + dayOfMonth);

                month = month + 1;
                String d = dayOfMonth + "/" + month + "/" + year;
                medicineDate.setText(d);
            }
        };
    }

    //select back to go homescreen
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                NavUtils.navigateUpFromSameTask(this);
                return true;
        }
        return super.onOptionsItemSelected(item);
    }

    private void addMedicine() {
        addButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String mName = medicineName.getText().toString();
                int mDose = Integer.parseInt(medicineDose.getText().toString());
                String mUnit = doseUnit.getText().toString();
                String mDesc = medicineDesc.getText().toString();
                String mDate = medicineDate.getText().toString();
                medicines m = new medicines (mName,mDose,mUnit,mDesc,mDate);
                DbHandler dbHandler = new DbHandler(AddMedicineActivity.this);
                dbHandler.addMedicine(m);
                intent = new Intent(AddMedicineActivity.this,ScheduleActivity.class);
                startActivity(intent);
                Toast.makeText(getApplicationContext(),
                        "Medicine Inserted Successfully",Toast.LENGTH_LONG).show();
            }
        });
    }
}
