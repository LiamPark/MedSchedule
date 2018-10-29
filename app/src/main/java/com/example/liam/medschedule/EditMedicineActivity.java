package com.example.liam.medschedule;

import android.app.DatePickerDialog;
import android.content.Intent;
import android.support.v4.app.NavUtils;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import java.util.Calendar;

public class EditMedicineActivity extends AppCompatActivity {

    private Toolbar toolbar;

    private String TAG = "EditMedicineActivity";
    EditText medicineName, medicineDose, doseUnit, medicineDesc;
    TextView medicineDate;
    Button editButton;
    Intent intent;
    private DatePickerDialog.OnDateSetListener datePickerListener;
    int id;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_medicine);

        toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        //connect variables with id
        medicineName = (EditText) findViewById(R.id.medicineEditText);
        medicineDose = (EditText) findViewById(R.id.doseEditText);
        doseUnit = (EditText) findViewById(R.id.unitEditText);
        medicineDesc = (EditText) findViewById(R.id.descEditText);
        editButton = (Button) findViewById(R.id.editMedicineBtn);
        medicineDate = (TextView) findViewById(R.id.datePickerText);

        //receive the data from the intent in ListFragment.
        String name = getIntent().getStringExtra("medicine_name");
        String dose = getIntent().getStringExtra("dose");
        String unit = getIntent().getStringExtra("unit");
        String desc = getIntent().getStringExtra("medicine_desc");
        String date = getIntent().getStringExtra("date");
        id = Integer.valueOf(getIntent().getStringExtra("id"));
        System.out.println(name + "\n;"+ dose + "\n;"+ unit + "\n;"+ desc + "\n;"+ date + "\n;" + id);
        medicineName.setText(name);
        medicineDose.setText(dose);
        doseUnit.setText(unit);
        medicineDesc.setText(desc);
        medicineDate.setText(date);

        datePicker();
        editMedicine();
    }

    //Getting datePicker dialog initialized and activated
    public void datePicker() {
        medicineDate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Calendar cal = Calendar.getInstance();
                int year = cal.get(Calendar.YEAR);
                int month = cal.get(Calendar.MONTH);
                int day = cal.get(Calendar.DAY_OF_MONTH);

                DatePickerDialog dateDialog = new DatePickerDialog(
                        EditMedicineActivity.this,
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

    //edit medicine does update of the data with the id in the table.
    private void editMedicine() {
        editButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String mName = medicineName.getText().toString();
                int mDose = Integer.parseInt(medicineDose.getText().toString());
                String mUnit = doseUnit.getText().toString();
                String mDesc = medicineDesc.getText().toString();
                String mDate = medicineDate.getText().toString();
                medicines m = new medicines (mName,mDose,mUnit,mDesc,mDate);
                DbHandler dbHandler = new DbHandler(EditMedicineActivity.this);
                dbHandler.UpdateMedicine(m,id);
                intent = new Intent(EditMedicineActivity.this,ScheduleActivity.class);
                startActivity(intent);
                Toast.makeText(getApplicationContext(),
                        "Medicine updated Successfully",Toast.LENGTH_LONG).show();
            }
        });
    }

    private void deleteMedicine(){

    }
}
