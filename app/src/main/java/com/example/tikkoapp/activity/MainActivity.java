package com.example.tikkoapp.activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;

import com.example.tikkoapp.Notifications.Client;
import com.example.tikkoapp.R;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

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
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {

        getMenuInflater().inflate(R.menu.main_menu,menu);

        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        if (item.getItemId() == R.id.main_logout_button){
            FirebaseAuth.getInstance().signOut();
            singOut();

        }
        if(item.getItemId() == R.id.main_account_Button){
            Intent profile = new Intent(MainActivity.this,UserProfile.class);
            startActivity(profile);
        }
        if(item.getItemId() == R.id.report_button){
            //TODO
        }
        return super.onOptionsItemSelected(item);
    }

    private void singOut() {

        //TODO
    }
}
