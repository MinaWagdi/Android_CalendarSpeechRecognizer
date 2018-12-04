package com.cse437.speechengine;

import android.app.Activity;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import org.w3c.dom.Text;

import java.util.ArrayList;

public class CustomAdapter extends ArrayAdapter{

    //to reference the Activity
    private final Activity context;
    //to store the list of countries
    //private final ArrayList<String> records;
    private final ArrayList<String> Name;
    private final ArrayList<String> Date;
    private final ArrayList<String> Day;
    private final ArrayList<String> Year;
    private final ArrayList<String> Time;



    public CustomAdapter(Activity context,ArrayList<String> name,ArrayList<String> day,ArrayList<String> date,ArrayList<String> year,ArrayList<String> time){

        super(context,R.layout.listview_row);
        Log.i(MainActivity.TAG,"CustomAdapter0");
        this.context=context;
        this.Name = name;
        this.Date = date;
        this.Day = day;
        this.Year=year;
        this.Time=time;
        Log.i(MainActivity.TAG,"CustomAdapter1");

    }


    @Override
    public int getCount() {
//        return super.getCount;
        return Name.size();
    }

    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        Log.i(MainActivity.TAG,"entered getView metod");
        LayoutInflater inflater=context.getLayoutInflater();
        View rowView=inflater.inflate(R.layout.listview_row, null,true);

        //this code gets references to objects in the listview_row.xml file
        TextView nameTextField = (TextView) rowView.findViewById(R.id.name);
        TextView dayTextField = (TextView) rowView.findViewById(R.id.day);
        TextView dateTextField = (TextView) rowView.findViewById(R.id.date);
        TextView yearTextField=(TextView) rowView.findViewById(R.id.year);
        TextView timeTextField=(TextView) rowView.findViewById(R.id.time);

        Log.i(MainActivity.TAG,"getView1");

        //this code sets the values of the objects to values from the arrays
        nameTextField.setText(Name.get(position));
        dayTextField.setText(Day.get(position));
        dateTextField.setText(Date.get(position));
        timeTextField.setText(Time.get(position));
        yearTextField.setText(Year.get(position));

        Log.i(MainActivity.TAG,"getView2");


        return rowView;
    }

//    @Override
//    public View getView(int position, View view, ViewGroup parent) {
//
//        LayoutInflater inflater=context.getLayoutInflater();
//        View rowView=inflater.inflate(R.layout.listview_row, null,true);
//
//        //this code gets references to objects in the listview_row.xml file
//        TextView nameTextField = (TextView) rowView.findViewById(R.id.name);
//        TextView dayTextField = (TextView) rowView.findViewById(R.id.day);
//        TextView dateTextField = (TextView) rowView.findViewById(R.id.date);
//        TextView yearTextField=(TextView) rowView.findViewById(R.id.year);
//        TextView timeTextField=(TextView) rowView.findViewById(R.id.time);
//
//        Log.i(MainActivity.TAG,"getView1");
//
//        //this code sets the values of the objects to values from the arrays
//        nameTextField.setText(Name.get(position));
//        dayTextField.setText(Day.get(position));
//        dateTextField.setText(Date.get(position));
//        timeTextField.setText(Time.get(position));
//        yearTextField.setText(Year.get(position));
//
//        Log.i(MainActivity.TAG,"getView2");
//
//
//        return rowView;
//
//    };



}
