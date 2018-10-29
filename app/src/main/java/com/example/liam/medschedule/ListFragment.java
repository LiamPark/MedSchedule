package com.example.liam.medschedule;


import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.PopupMenu;
import android.widget.SimpleAdapter;

import java.util.ArrayList;
import java.util.HashMap;


/**
 * A simple {@link Fragment} subclass.
 */
public class ListFragment extends Fragment {

    private final String TAG = "ListFragment";
    private ListView lv;
    public ListFragment() {
        // Required empty public constructor
    }

    //inflate view into fragment
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        // Inflate the layout for this fragment
        final View view = inflater.inflate(R.layout.fragment_list, container, false);
        DbHandler db = new DbHandler(getActivity());

        //get all medicines into medicineList
        final ArrayList<HashMap<String,String>> medicineList =db.GetMedicines();

        //initialize the listadapter to store the data from SQLite to ListView.
        lv = (ListView) view.findViewById(R.id.medicine_list);
        ListAdapter adapter = new SimpleAdapter(getActivity(),
                medicineList, R.layout.list_row_list,
                new String[]{"id","medicine_name","dose", "unit", "medicine_desc", "date"},
                new int[]{R.id.lid,R.id.lmedicine_name,R.id.ldose,R.id.lunit,R.id.lmedicine_desc, R.id.ldate}){

            //get view for menu button
            @Override
            public View getView(final int position, View convertView, final ViewGroup parent) {
                View v = super.getView(position, convertView, parent);

                //recall btnMenu and initialize onClickListener
                final ImageButton btnMenu = (ImageButton)v.findViewById(R.id.btnMenu);
                btnMenu.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {

                        //inlfate popup menu
                        PopupMenu popup = new PopupMenu(getContext(),v);
                        MenuInflater menuInflater = popup.getMenuInflater();
                        menuInflater.inflate(R.menu.card_popup_menu, popup.getMenu());
                        popup.show();

                        //set OnMenuItemClickListener
                        popup.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {

                            @Override
                            public boolean onMenuItemClick(MenuItem item) {
                                switch(item.getItemId()) {
                                    case R.id.card_edit:

                                        //get data from the view that button is clicked
                                        System.out.println(position);
                                        Intent i = new Intent(getActivity(), EditMedicineActivity.class);
                                        ListView listview = (ListView) view.findViewById(R.id.medicine_list);
                                        @SuppressWarnings("unchecked")
                                        HashMap<String, String> medicine = (HashMap<String, String>) lv.getItemAtPosition(position);

                                        String name = medicine.get("medicine_name");
                                        String dose = medicine.get("dose");
                                        String unit = medicine.get("unit");
                                        String desc = medicine.get("medicine_desc");
                                        String date = medicine.get("date");
                                        String id = medicine.get("id");
                                        Log.i(TAG, name + "\n" + dose + "\n" + unit + "\n" + desc + "\n" + date);


                                        //put data into the intent activity
                                        i.putExtra("medicine_name", name);
                                        i.putExtra("dose", dose);
                                        i.putExtra("unit", unit);
                                        i.putExtra("medicine_desc", desc);
                                        i.putExtra("date", date);
                                        i.putExtra("id", id);
                                        getActivity().startActivity(i);
                                        return true;

                                    case R.id.card_delete:
                                        System.out.println(position);
                                        ListView listview1 = (ListView) view.findViewById(R.id.medicine_list);
                                        @SuppressWarnings("unchecked")
                                        HashMap<String, String> medicine1 = (HashMap<String,String>) listview1.getItemAtPosition(position);

                                        //get id from the selected view
                                        String id1 = medicine1.get("id");
                                        Log.i(TAG, id1);
                                        DbHandler db = new DbHandler(getActivity());
                                        //delete medicine with the id
                                        db.DeleteMedicine(Integer.parseInt(id1));
                                        db.close();
                                        medicineList.remove(position);
                                        notifyDataSetChanged();
                                        return true;
                                    default:
                                }
                                return false;
                            }
                        });
                    }
                });
                return v;
            }
        };
        lv.setAdapter(adapter);
        return view;
    }
}
