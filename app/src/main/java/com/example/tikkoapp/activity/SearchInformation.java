package com.example.tikkoapp.activity;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.ProgressDialog;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;

import com.example.tikkoapp.R;
import com.example.tikkoapp.model.Information;
import com.example.tikkoapp.util.RecyclerViewAdapter;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

public class SearchInformation extends AppCompatActivity {

    private RecyclerView informationView;
    private DatabaseReference infoRef;
    List<Information> list = new ArrayList<>();
    RecyclerView recyclerView;
    RecyclerView.Adapter adapter ;
    ProgressDialog progressDialog;
    private Toolbar toolbar;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search_information);
         infoRef = FirebaseDatabase.getInstance().getReference("information");
        recyclerView = (RecyclerView) findViewById(R.id.recyclerView);
        toolbar = (Toolbar) findViewById(R.id.search_bar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle("තොරතුරු සොයන්න");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        recyclerView.setLayoutManager(new LinearLayoutManager(SearchInformation.this));


        recyclerView.setHasFixedSize(true);

        progressDialog = new ProgressDialog(this);
        progressDialog.setMessage("Loading Data from Firebase Database");
        progressDialog.show();


        infoRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                for (DataSnapshot ds : dataSnapshot.getChildren()){
                    Information information = ds.getValue(Information.class);
                    list.add(information);
                }
                adapter = new RecyclerViewAdapter(SearchInformation.this, list);

                recyclerView.setAdapter(adapter);

                progressDialog.dismiss();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                progressDialog.dismiss();
            }
        });




//        ValueEventListener eventListener = new ValueEventListener() {
//            @Override
//            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
//                List<Information> informationList = new ArrayList<>();
//                for (DataSnapshot ds : dataSnapshot.getChildren()){
//                    Information information = ds.getValue(Information.class);
//                    informationList.add(information);
//                }
//                ListView listView = (ListView)findViewById(R.id.conv_list);
//                ArrayAdapter<Information> arrayAdapter = new ArrayAdapter<Information>(getApplicationContext(),R.layout.information_single, informationList);
//                listView.setAdapter(arrayAdapter);
//            }
//
//            @Override
//            public void onCancelled(@NonNull DatabaseError databaseError) {
//
//            }
//        };



    }


    }
