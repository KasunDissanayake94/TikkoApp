package com.example.tikkoapp.activity;

import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.example.tikkoapp.R;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.squareup.picasso.Callback;
import com.squareup.picasso.NetworkPolicy;
import com.squareup.picasso.Picasso;

import de.hdodenhof.circleimageview.CircleImageView;

public class UserProfile extends AppCompatActivity {
    private FirebaseUser mcurrentUser;
    private DatabaseReference mUserDatabase;

    private CircleImageView circleImageView;
    private TextView mName;
    private TextView mTelephone;
    private Button changeName;
    private Button changeImage;
    private static final int GALLERY_PICK = 1;
    private static final int MAX_LENGTH = 30;
    private StorageReference mImageStorage;
    private ProgressDialog mProgressDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_profile);

        mcurrentUser = FirebaseAuth.getInstance().getCurrentUser();
        String current_id = mcurrentUser.getUid();
        mUserDatabase = FirebaseDatabase.getInstance().getReference().child("App_Users").child(current_id);
        mUserDatabase.keepSynced(true);

        mName = (TextView) findViewById(R.id.settings_display_name);
        mTelephone = (TextView) findViewById(R.id.settings_telephone);
        circleImageView = (CircleImageView) findViewById(R.id.settings_image);
        changeName = (Button) findViewById(R.id.changeNameBtn);

        mImageStorage = FirebaseStorage.getInstance().getReference();

        mUserDatabase.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                String name = dataSnapshot.child("name").getValue().toString();
                String telephone = dataSnapshot.child("telephone").getValue().toString();
                final String image = dataSnapshot.child("image").getValue().toString();
                String thumb_image = dataSnapshot.child("thumb_image").getValue().toString();

                mName.setText(name);
                mTelephone.setText(telephone);

            }
            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
        changeName.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String status_value = mName.getText().toString();

                Intent status_intend = new Intent(UserProfile.this,Settings.class);
                status_intend.putExtra("name",status_value);
                startActivity(status_intend);
            }
        });



    }
}
