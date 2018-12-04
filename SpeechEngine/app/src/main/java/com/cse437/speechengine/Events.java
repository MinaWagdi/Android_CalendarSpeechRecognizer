package com.cse437.speechengine;

import android.content.Intent;
import android.database.Cursor;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

public class Events extends AppCompatActivity {

    ArrayList<String> Records;

    private static final long DOUBLE_CLICK_TIME_DELTA = 300;
    long lastClickTime = 0;
    int count=0;

    ArrayList<String>Name;
    ArrayList<String>Day;
    ArrayList<String>Date;
    ArrayList<String>Year;
    ArrayList<String>Time;

    int recordsArrayIndex=0;
    ListView events_list;
    ArrayAdapter events_list_adapter;
//    CustomAdapter events_list_adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_events);
        Records=new ArrayList<String>();

        Intent intent = getIntent();


        Name = new ArrayList<String>();
        Day= new ArrayList<String>();
        Date= new ArrayList<String>();
        Year= new ArrayList<String>();
        Time= new ArrayList<String>();


        Log.i(MainActivity.TAG,"onCreate in Events0");
        events_list_adapter=new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1,Records);
        SetRecordsArray();
        Log.i(MainActivity.TAG,"onCreate in Events1");
//        events_list_adapter=new CustomAdapter(this, Name,Day,Date,Year,Time);
//        SetRecordsArray();
        events_list=(ListView) findViewById(R.id.events_list);
        Log.i(MainActivity.TAG,"onCreate in Events2");
        events_list.setAdapter(events_list_adapter);
        Log.i(MainActivity.TAG,"onCreate in Events3");

//        events_list.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
//            @Override
//            public boolean onItemLongClick(AdapterView<?> parent, View view, int position, long id) {
//                MainActivity.myDb.deleteRow(id);
//                return false;
//            }
//        });

        events_list.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                try {
                    long clickTime = System.currentTimeMillis();
                    count++;
                    if (clickTime - lastClickTime < DOUBLE_CLICK_TIME_DELTA && count==2){
                        Log.i(MainActivity.TAG,"Double Tap");
                        Log.i(MainActivity.TAG, "event should be deleted, id is :" + id);
                        int ID_index = Records.get((int) id).indexOf(",");
                        String ID = Records.get((int) id).substring(0, ID_index);
                        Log.i(MainActivity.TAG, ID);
                        MainActivity.myDb.deleteRow(Integer.parseInt(ID));
                        Records.remove((int)id);
                        events_list_adapter.notifyDataSetChanged();
                        //events_list_adapter.notifyDataSetChanged();
                        Log.i(MainActivity.TAG,"After updating");

                        lastClickTime = 0;
                        count=0;
                    }
                    else{
                        Log.i(MainActivity.TAG,"Single Tap");
                        lastClickTime = clickTime;
                        if(count>2){
                            count=1;
                        }
                    }
                }catch (Exception e){
                    Log.i(MainActivity.TAG,"EXCEPTION"+e.toString());
                }
//                events_list_adapter.clear();
//                events_list.setAdapter(events_list_adapter);
//                events_list_adapter.notifyDataSetChanged();

            }
        });


    }

    public void SetRecordsArray(){
        Cursor cursor = MainActivity.myDb.getAllRows();
        displayRecordSet(cursor);
    }

    private void displayRecordSet(Cursor cursor) {


        // populate the message from the cursor

        // Reset cursor to start, checking to see if there's data:
        if (cursor.moveToFirst()) {
            do {
                // Process the data:

                int id = cursor.getInt(DBAdapter.COL_ROWID);
                String name = cursor.getString(DBAdapter.COL_EventName);
                String daynum = cursor.getString(DBAdapter.COL_DayNum);
                String day = cursor.getString(DBAdapter.COL_Day);
                String month = cursor.getString(DBAdapter.COL_Month);
                String time = cursor.getString(DBAdapter.COL_Time);
                String year = cursor.getString(DBAdapter.COL_Year);
                Log.i(MainActivity.TAG,"Passed1");

                // Append data to the message:
                String r = id
                        +", " + name
                        +", " + day
                        +", " + month
                        +", " + daynum
                        +", " + year
                        +", " + time

                        +"\n";

                Name.add(recordsArrayIndex,name);
                Day.add(recordsArrayIndex,day);
                Date.add(recordsArrayIndex,month+" "+daynum);
                Year.add(recordsArrayIndex,year);
                Time.add(recordsArrayIndex,time);


                Log.i(MainActivity.TAG,"Passed2");

                Records.add(recordsArrayIndex,r);
                Log.i(MainActivity.TAG,"Passed3");
                // [TO_DO_B6]
                // Create arraylist(s)? and use it(them) in the list view
                recordsArrayIndex++;
            } while(cursor.moveToNext());
        }
        Log.i(MainActivity.TAG,"Passed4");
        // Close the cursor to avoid a resource leak.
        cursor.close();

    }
}
