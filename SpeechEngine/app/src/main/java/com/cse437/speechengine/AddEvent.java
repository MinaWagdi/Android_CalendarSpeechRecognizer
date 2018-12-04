package com.cse437.speechengine;

import android.Manifest;
import android.content.ContentResolver;
import android.content.ContentValues;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.provider.CalendarContract;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;

import java.util.Calendar;

public class AddEvent extends AppCompatActivity {

    String event_details;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_event);
        Intent intent = getIntent();
        event_details=intent.getStringExtra("event_details");
        Log.i("Mina","In the second activity");

        addEvent();


    }

    public void addEvent() {

        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.WRITE_CALENDAR) != PackageManager.PERMISSION_GRANTED) {
            Log.i("Mina","Sorry I can't");
            return;
        }

        Log.i("Mina","passed");
        ContentResolver cr = getContentResolver();
//        ContentValues contentValues = new ContentValues();
        Log.i("Mina","passed2");
        Calendar beginTime = Calendar.getInstance();
        beginTime.set(2018, 11, 04, 9, 30);

        Calendar endTime = Calendar.getInstance();
        endTime.set(2018, 11, 04, 7, 35);
        Log.i("Mina","passed3");
        ContentValues values = new ContentValues();
        values.put(CalendarContract.Events.CALENDAR_ID, 1);
        values.put(CalendarContract.Events.TITLE, "Tech Stores");
        values.put(CalendarContract.Events.DTSTART, beginTime.getTimeInMillis());
        values.put(CalendarContract.Events.DTEND, endTime.getTimeInMillis());
        values.put(CalendarContract.Events.DESCRIPTION, "Successful Startups");
        values.put(CalendarContract.Events.EVENT_LOCATION, "London");
        values.put(CalendarContract.Events.EVENT_TIMEZONE,Calendar.getInstance().getTimeZone().getID());

        Log.i("Mina","passed4");
        try {
            cr.insert(CalendarContract.Events.CONTENT_URI, values);
        }catch(Exception e){
            Log.i("Mina","EXCEPTION!!!!"+e.getClass()+"\nMessage "+e.getMessage()+"\n"+e.getStackTrace());
        }
        Log.i("Mina","passed5");
        return;

//        Calendar cal = Calendar.getInstance();
//        Intent intent = new Intent(Intent.ACTION_EDIT);
//        intent.setType("vnd.android.cursor.item/event");
//        intent.putExtra("beginTime", cal.getTimeInMillis());
//        intent.putExtra("allDay", true);
//        intent.putExtra("rrule", "FREQ=YEARLY");
//        intent.putExtra("endTime", cal.getTimeInMillis()+60*60*1000);
//        intent.putExtra("title", "A Test Event from android app");
//        startActivity(intent);
    }
}
