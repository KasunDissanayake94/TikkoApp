package com.example.tikkoapp.activity;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TimePicker;
import android.widget.Toast;

import com.example.tikkoapp.R;
import com.example.tikkoapp.model.Information;
import com.example.tikkoapp.model.Station;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import org.json.JSONObject;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

public class AddInformation extends AppCompatActivity {

    private Spinner spinner;
    private DatabaseReference mDatabase;
    private DatabaseReference mDatabase1;
    private TimePicker picker;
    private String time;
    private String date;
    private EditText comment;
    private Button submitBtn;
    private List<String> stationList;
    private String stationName;
    private String commentType;
    private String formattedDate;

    @RequiresApi(api = Build.VERSION_CODES.M)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_information);

        mDatabase = FirebaseDatabase.getInstance().getReference("station_name");
        mDatabase1 = FirebaseDatabase.getInstance().getReference("information");
        spinner = (Spinner) findViewById(R.id.stationSpinner) ;
        picker = (TimePicker)findViewById(R.id.timeSelect);
        picker.setIs24HourView(true);
        comment = (EditText) findViewById(R.id.comment) ;
        submitBtn = (Button) findViewById(R.id.button);

        final Information information = new Information();


        int hour = picker.getHour();
        int minute = picker.getMinute();
        String am_pm;

        if(hour > 12) {
            am_pm = "PM";
            hour = hour - 12;
        }
        else
        {
            am_pm="AM";
        }
        if(minute <10){
            time = hour +":0"+ minute+" "+am_pm ;

        }else{
            time = hour +":"+ minute+" "+am_pm ;

        }

//


        //Load Station names
        mDatabase.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                stationList = new ArrayList<String>();
                for(DataSnapshot dataSnapshot1: dataSnapshot.getChildren()){
                    String station_id = dataSnapshot1.getKey();
                    Station stationInstance = dataSnapshot1.getValue(Station.class);
                    stationList.add(stationInstance.getStation_name());

                }
                ArrayAdapter<String> arrayAdapter = new ArrayAdapter<String>(AddInformation.this, android.R.layout.simple_spinner_item, stationList);
                arrayAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                spinner.setAdapter(arrayAdapter);


            }
            @Override
            public void onCancelled(DatabaseError databaseError) {
                Toast.makeText(AddInformation.this,databaseError.getMessage(),Toast.LENGTH_LONG).show();
            }
        });

        //get the details

        //Date
        Date c = Calendar.getInstance().getTime();
        SimpleDateFormat df = new SimpleDateFormat("dd-MMM-yyyy");
        formattedDate = df.format(c);

        //Comment
        commentType = comment.getText().toString();
        System.out.println("Comment "+commentType);

        //Station

        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                stationName = stationList.get(i);
                information.setComment(commentType);
                information.setDate(formattedDate);
                information.setStation_name(stationName);
                information.setTime(time);
                submitBtn.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        if(information.getDate() != null && information.getStation_name() != null && information.getTime() != null){
                            String informationId = mDatabase1.push().getKey();
                            mDatabase1.child(informationId).setValue(information);

                            Toast.makeText(AddInformation.this, "සාර්ථකව ඇතුලත්කලා!", Toast.LENGTH_LONG).show();
                        }else{
                            Toast.makeText(AddInformation.this, "ඇතුලත් කිරීම අසාර්ථකයි!", Toast.LENGTH_LONG).show();
                        }
                    }
                });



            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });















    }
}
