package com.example.tikkoapp.activity;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.app.ProgressDialog;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.tikkoapp.R;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class Settings extends AppCompatActivity {

    private EditText mName;
    private Button mSaveBtn;
    private DatabaseReference mDatabseRef;
    private FirebaseUser mCurrentUser;
    private ProgressDialog mProgress;
    private Toolbar toolbar;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_settings);

        mName = (EditText) findViewById(R.id.status_input);
        mSaveBtn = (Button) findViewById(R.id.status_change_btn);
        toolbar = (Toolbar) findViewById(R.id.infor_bar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle("ඔබේ නම වෙනස් කරන්න");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        mCurrentUser = FirebaseAuth.getInstance().getCurrentUser();
        String currentUid = mCurrentUser.getUid();
        mDatabseRef = FirebaseDatabase.getInstance().getReference().child("App_Users").child(currentUid);
        String status_value = getIntent().getStringExtra("status_value");
        mName.setText(status_value);




        mSaveBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                mProgress =new ProgressDialog((Settings.this));
                mProgress.setTitle("වෙනස් කිරීම් සිදු කෙරෙමින් පවතී");
                mProgress.setMessage("මදක් රැදීසිටින්න ");
                mProgress.show();

                String status = mName.getText().toString();
                mDatabseRef.child("name").setValue(status).addOnCompleteListener(new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {
                        if (task.isSuccessful()){
                            mProgress.dismiss();
                            Toast.makeText(getApplicationContext(),"නම වෙනස් කිරීම සාර්ථකයි! ",Toast.LENGTH_LONG).show();
                        }else{
                            Toast.makeText(getApplicationContext(),"නම වෙනස් කිරීම අසාර්ථකයි!",Toast.LENGTH_LONG).show();
                        }
                    }
                });




            }
        });
    }
}
