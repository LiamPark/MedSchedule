package com.example.liam.medschedule;


import android.app.DatePickerDialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.ImageButton;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.PopupMenu;
import android.widget.SimpleAdapter;
import android.widget.TextView;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;


/**
 * A simple {@link Fragment} subclass.
 */
public class HomeFragment extends Fragment {


    public HomeFragment() {
        // Required empty public constructor
    }
    private static final String TAG = "liam.example";
    private TextView currentDate;
    private DatePickerDialog.OnDateSetListener datePickerListener;
    private ListView lv;
    private ArrayList<HashMap<String,String>> medicineList = new ArrayList<>();
    View view;
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        view = inflater.inflate(R.layout.fragment_home,null);
        //initialize the views
        lv = (ListView) view.findViewById(R.id.medicine_List);
        currentDate = (TextView) view.findViewById(R.id.currentDate);
        //initiate the methods
        currentDate();
        datePicker();
        return view;
    }


    @Override
    public void onResume() {
        super.onResume();
        prepareMedicineData();
    }

    //prepare data when the current date is changed
    public void prepareMedicineData() {
        medicineList.clear();
        DbHandler db = new DbHandler(getActivity());
        String date = currentDate.getText().toString();
        Log.w(TAG, date);
        final ArrayList<HashMap<String, String>> m = db.GetMedicinesByDate(date);
        if (m.size() > 0) {
            //loop through contents
            for (int i = 0; i < m.size(); i++) {
                medicineList.add(m.get(i));
            }
        }
        ListAdapter adapter = new SimpleAdapter(getActivity(),medicineList,R.layout.list_row_home,
                new String[]{"id","medicine_name","dose", "unit", "medicine_desc"},
                new int[]{R.id.id,R.id.medicine_name,R.id.dose,R.id.unit,R.id.medicine_desc});
        lv.setAdapter(adapter);
    }

    //Getting current date from the locale and prepare data as current date has been set.
    public void currentDate(){
        String cDate = new SimpleDateFormat("d/MM/yyyy", Locale.getDefault())
                .format(new Date());
        currentDate.setText(cDate);
        prepareMedicineData();
    }

    //Date Picker dialog initialized and activated when current date is clicked
    public void datePicker() {
        currentDate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Calendar cal = Calendar.getInstance();
                int year = cal.get(Calendar.YEAR);
                int month = cal.get(Calendar.MONTH);
                int day = cal.get(Calendar.DAY_OF_MONTH);

                DatePickerDialog dateDialog = new DatePickerDialog(
                        getActivity(),
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
                currentDate.setText(d);
                prepareMedicineData();
            }
        };
    }
}
