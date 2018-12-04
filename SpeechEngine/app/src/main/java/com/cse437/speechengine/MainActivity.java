package com.cse437.speechengine;

import android.Manifest;
import android.content.ActivityNotFoundException;
import android.content.ContentResolver;
import android.content.ContentValues;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.net.Uri;
import android.provider.CalendarContract;
import android.speech.RecognizerIntent;
import android.support.annotation.Nullable;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import java.time.Month;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Locale;

public class MainActivity extends AppCompatActivity {

    public static DBAdapter myDb;
    String Records;
    TextView testText;
    ImageView mic_imgview;
    Button displayCalendarBtn;
    String EventString;
    final static String TAG = "SPLINTER";
    Cursor cursor;


    TextView textView;

//    public static final String[] EVENT_PROJECTION = new String[] {
//            CalendarContract.Calendars._ID,                           // 0
//            CalendarContract.Calendars.ACCOUNT_NAME,                  // 1
//            CalendarContract.Calendars.CALENDAR_DISPLAY_NAME,         // 2
//            CalendarContract.Calendars.OWNER_ACCOUNT                  // 3
//    };
//
//    // The indices for the projection array above.
//    private static final int PROJECTION_ID_INDEX = 0;
//    private static final int PROJECTION_ACCOUNT_NAME_INDEX = 1;
//    private static final int PROJECTION_DISPLAY_NAME_INDEX = 2;
//    private static final int PROJECTION_OWNER_ACCOUNT_INDEX = 3;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Log.i(TAG,"In the main activity");

        textView=findViewById(R.id.textView2);
        testText=findViewById(R.id.testText);
        mic_imgview =findViewById(R.id.micView);
        displayCalendarBtn=findViewById(R.id.DisplayCalendarBtn);

        openDB();

        displayCalendarBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AccessDBAndQuery();
            }
        });

        mic_imgview.setOnClickListener(new View.OnClickListener(){
            public void onClick(View v) {
                getSpeech();
            }
        });
    }

    private void openDB() {
        myDb = new DBAdapter(this);
        myDb.open();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        closeDB();
    }

    private void closeDB() {
        myDb.close();
    }

    public void onClick_AddRecord(String [] record) {
        long newId = myDb.insertRow(record[0],record[1],record[2],record[3],record[4],record[5]);
    }

    public void getSpeech(){
        Toast.makeText(this, "Please say the event name, then the day, Month, the day number, year, the time respectively", Toast.LENGTH_SHORT).show();
        //testText.setText("Startinggg :)) ");
        Intent intent = new Intent(RecognizerIntent.ACTION_RECOGNIZE_SPEECH);
        intent.putExtra(RecognizerIntent.EXTRA_LANGUAGE_MODEL, RecognizerIntent.LANGUAGE_MODEL_FREE_FORM);
        intent.putExtra(RecognizerIntent.EXTRA_LANGUAGE, Locale.getDefault());

        try{
            startActivityForResult(intent,10);
        }catch (ActivityNotFoundException ex){
            Toast.makeText(this, "NOT SUPPORTED BY THIS DEVICE", Toast.LENGTH_SHORT).show();
        }


    }

//    public void displayCalendar(){
//        cursor=getContentResolver().query(CalendarContract.Events.CONTENT_URI,null,null,null);
//        while(cursor.moveToNext()){
//            if(cursor!=null){
//                int id1 = cursor.getColumnIndex(CalendarContract.Events._ID);
//                int id2 = cursor.getColumnIndex(CalendarContract.Events.TITLE);
//                int id3 = cursor.getColumnIndex(CalendarContract.Events.DESCRIPTION);
//                int id4 = cursor.getColumnIndex(CalendarContract.Events.EVENT_LOCATION);
//
//                String idValue = cursor.getColumnName(id1);
//                String titleValue = cursor.getColumnName(id2);
//                String descriptionValue = cursor.getColumnName(id3);
//                String locationValue = cursor.getColumnName(id4);
//
//                Toast.makeText(this, idValue+" "+titleValue+" "+descriptionValue+" "+locationValue, Toast.LENGTH_SHORT).show();
//            }
//        }
//
//    }
//
//    void displayCalendar2(){
//
//        Log.i("Mina","Entered display 2");
//        final String primary = "\"primary\"";
//
//        Cursor cur = null;
//        ContentResolver cr = getContentResolver();
//        Uri uri = CalendarContract.Calendars.CONTENT_URI;
////        String selection="((" + CalendarContract.Calendars.ACCOUNT_NAME + " = ?) AND ("
////                + CalendarContract.Calendars.ACCOUNT_TYPE + " = ?) AND ("
////                + CalendarContract.Calendars.OWNER_ACCOUNT + " = ?))";
////
//        String[] selectionArgs = new String[] {"mina.wagdi63@gmail.com", "gmail.com",
//                "mina.wagdi63@gmail.com"};
//        final String selection = primary + " = 1";
//        Log.i("Mina","querying now");
////        cur = cr.query(uri, EVENT_PROJECTION, selection, selectionArgs, null);
//        cur = cr.query(uri, EVENT_PROJECTION, selection, null, null);
//        Log.i("Mina","queried cursor is "+cur.toString());
//
//        // Use the cursor to step through the returned records
//        Log.i("Mina","Getting data and displaying it");
//
//        long calID = 0;
//        String displayName = null;
//        String accountName = null;
//        String ownerName = null;
//
//        // Get the field values
//        try{
//            calID = cur.getLong(PROJECTION_ID_INDEX);
//            Log.i("Mina","got callID");
//            displayName = cur.getString(PROJECTION_DISPLAY_NAME_INDEX);
//            Log.i("Mina","got PROJECTION_DISPLAY_NAME_INDEX");
//            accountName = cur.getString(PROJECTION_ACCOUNT_NAME_INDEX);
//            Log.i("Mina","got PROJECTION_ACCOUNT_NAME_INDEX");
//            ownerName = cur.getString(PROJECTION_OWNER_ACCOUNT_INDEX);
//            Log.i("Mina","got PROJECTION_OWNER_ACCOUNT_INDEX");
//        }catch(Exception ex){
//            Log.i("Mina", "EXCEPTION"+ex.getMessage()+" "+ex.toString());
//        }
//
//
//
//
//        //Toast.makeText(this, calID+" "+displayName+" "+accountName+" "+ownerName, Toast.LENGTH_LONG).show();
//        textView.setText(calID+" "+displayName+" "+accountName+" "+ownerName);
////        while (cur.moveToNext()) {
////            long calID = 0;
////            String displayName = null;
////            String accountName = null;
////            String ownerName = null;
////
////            // Get the field values
////            calID = cur.getLong(PROJECTION_ID_INDEX);
////            displayName = cur.getString(PROJECTION_DISPLAY_NAME_INDEX);
////            accountName = cur.getString(PROJECTION_ACCOUNT_NAME_INDEX);
////            ownerName = cur.getString(PROJECTION_OWNER_ACCOUNT_INDEX);
////
////            //Toast.makeText(this, calID+" "+displayName+" "+accountName+" "+ownerName, Toast.LENGTH_LONG).show();
////            textView.setText(calID+" "+displayName+" "+accountName+" "+ownerName);
////
////
////        }
//
// }



    public void AccessDBAndQuery(){
        //onClick_DisplayRecords();

        Intent intent = new Intent(this,Events.class);
        intent.putExtra("event_details",""+EventString);
        startActivity(intent);

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        switch (requestCode){
            case 10:
                if(resultCode==RESULT_OK && data!=null){
                    ArrayList result = data.getStringArrayListExtra(RecognizerIntent.EXTRA_RESULTS);
                    testText.setText(""+result.get(0));
                    EventString=""+result.get(0);
                    String[] eventSplitted=processString(EventString);
                    onClick_AddRecord(eventSplitted);

//                    Intent intent = new Intent(this,AddEvent.class);
//                    intent.putExtra("event_details",""+EventString);
//                    startActivity(intent);

                }
                break;
        }
    }

    private String[] processString(String eventString) {
        String[] weekDays={"Monday","Tuesday","Wednesday","Thursday","Friday","Saturday","Sunday"};
        String[] Months={"January","February","March","April","May","June","July","August","September","October","November","December"};

        String eventName="";
        String day="";
        int dayIndex=-1;
        String month="";
        int MonthIndex=-1;
        String daynum="";
        int daynumIndex=-1;
        String year="";
        String time="";

        for(int i =0;i<weekDays.length;i++){
            if(eventString.contains(weekDays[i])){
                day=weekDays[i];
                Log.i(TAG,"day is "+day);
                dayIndex=eventString.indexOf(weekDays[i]);
                break;
            }
        }
        for(int i =0;i<Months.length;i++){
            if(eventString.contains(Months[i])){
                month=Months[i];
                Log.i(TAG,"month is "+month);
                MonthIndex=eventString.indexOf(Months[i]);
                daynumIndex=MonthIndex+Months[i].length();
                break;
            }
        }
        try {
            eventName = eventString.substring(0, dayIndex);
            Log.i(TAG, "eventname is " + eventName);

            daynum = eventString.substring(daynumIndex, daynumIndex + 2);
            Log.i(TAG, "daynum is " + daynum);

            year = eventString.substring((daynumIndex + 5), daynumIndex + 9);
            Log.i(TAG, "year is " + year);

            time = eventString.substring(daynumIndex + 13);
            Log.i(TAG, "time is " + time);
        }catch (Exception ex){
            Log.i(TAG,"EXCEPTION "+ex.toString());
            Toast.makeText(this, "SOMETHING IS WRONG!", Toast.LENGTH_SHORT).show();
        }

        String[] eventSplitted={eventName,day,month, daynum,year,time};
        return eventSplitted;







    }


}
