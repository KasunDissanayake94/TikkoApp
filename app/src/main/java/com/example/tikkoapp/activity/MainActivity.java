package com.example.tikkoapp.activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import com.example.tikkoapp.R;

public class MainActivity extends AppCompatActivity {

    CardView insert;
    CardView search;




    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        insert = (CardView) findViewById(R.id.addInformation);
        search = (CardView) findViewById(R.id.searchInformation);

        insert.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent insertActivity = new Intent(getBaseContext(), AddInformation.class);
                startActivity(insertActivity);
            }
        });

        search.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent searchActivity = new Intent(getBaseContext(), SearchInformation.class);
                startActivity(searchActivity);
            }
        });



    }
}
