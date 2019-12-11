package com.example.tikkoapp.activity;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.example.tikkoapp.R;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.FirebaseException;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseAuthInvalidCredentialsException;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.PhoneAuthCredential;
import com.google.firebase.auth.PhoneAuthProvider;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.HashMap;
import java.util.concurrent.TimeUnit;

public class PhoneNumberVerificationActivity extends AppCompatActivity {

    private EditText mPhoneText;
    private EditText mCodeText;

    private ProgressBar mPhoneProgress;
    private ProgressBar mCodeProgress;

    private Button msendButton;
    private TextView errorText;
    private PhoneAuthProvider.OnVerificationStateChangedCallbacks mCallbacks;
    private String mVerificationId;
    private PhoneAuthProvider.ForceResendingToken mResendToken;

    private FirebaseAuth mAuth;
    private DatabaseReference mDatabase;
    private ProgressDialog mProgressBar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_phone_number_verification);


        mAuth = FirebaseAuth.getInstance();
        mProgressBar = new ProgressDialog(this);
        mPhoneText = (EditText) findViewById(R.id.phoneText);
        mCodeText = (EditText) findViewById(R.id.codeText);
        mPhoneProgress = (ProgressBar) findViewById(R.id.phoneProgressBar);
        mCodeProgress = (ProgressBar) findViewById(R.id.codeProgressBar);
        msendButton = (Button) findViewById(R.id.sendVerificationBtn);
        errorText = (TextView)  findViewById(R.id.errorText);

        msendButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mPhoneProgress.setVisibility(View.VISIBLE);
                mPhoneText.setEnabled(false);
                msendButton.setEnabled(false);
                String phoneNumber = mPhoneText.getText().toString();
                PhoneAuthProvider.getInstance().verifyPhoneNumber(
                        phoneNumber,        // Phone number to verify
                        30,                 // Timeout duration
                        TimeUnit.SECONDS,   // Unit of timeout
                        PhoneNumberVerificationActivity.this,               // Activity (for callback binding)
                        mCallbacks
                );
            }
        });

        mCallbacks = new PhoneAuthProvider.OnVerificationStateChangedCallbacks() {
            @Override
            public void onVerificationCompleted(PhoneAuthCredential phoneAuthCredential) {
                signInWithPhoneAuthCredential(phoneAuthCredential);

            }

            @Override
            public void onVerificationFailed(FirebaseException e) {
                errorText.setText("There was some error in verification");
                errorText.setVisibility(View.VISIBLE);
            }
            @Override
            public void onCodeSent(String verificationId,
                                   PhoneAuthProvider.ForceResendingToken token) {

                // Save verification ID and resending token so we can use them later
                mVerificationId = verificationId;
                mResendToken = token;

                mPhoneProgress.setVisibility(View.INVISIBLE);
                mCodeText.setVisibility(View.VISIBLE);
                mCodeProgress.setVisibility(View.VISIBLE);
            }
        };
    }

    private void signInWithPhoneAuthCredential(PhoneAuthCredential credential) {
        mAuth.signInWithCredential(credential)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            // Sign in success, update UI with the signed-in user's information

                            FirebaseUser user = task.getResult().getUser();
                            final String phoneNumber = mPhoneText.getText().toString();
                            mDatabase = FirebaseDatabase.getInstance().getReference().child("App_Users").child(user.getUid());

                            HashMap<String,String> userMap = new HashMap<String, String>();
                            String phone = mPhoneText.getText().toString();
                            userMap.put("name","Unknown User");
                            userMap.put("telephone",phone);
                            userMap.put("image","default");
                            userMap.put("thumb_image","https://firebasestorage.googleapis.com/v0/b/tikkoapp-2fdf5.appspot.com/o/profile_images%2FprofilePicture.png?alt=media&token=fea599ec-7912-49f5-8a18-0b0f0e3f3233");
                            mProgressBar.setTitle("Registering User");
                            mProgressBar.setMessage("Please wait while we create your Account");
                            mProgressBar.setCanceledOnTouchOutside(false );
                            mProgressBar.show();

                            mDatabase.setValue(userMap).addOnCompleteListener(new OnCompleteListener<Void>() {
                                @Override
                                public void onComplete(@NonNull Task<Void> task) {
                                    if (task.isSuccessful()){
                                        mProgressBar.dismiss();

                                        // SharedPreferences Logic
                                        SharedPreferences userDetails =getSharedPreferences("userDetails", Context.MODE_PRIVATE);
                                        SharedPreferences.Editor edit = userDetails.edit();
                                        edit.clear();
                                        edit.putString("phoneNumber", phoneNumber);
                                        edit.putBoolean("isVerified", true);
                                        edit.putBoolean("loggedIn", true);
                                        edit.commit();

                                        Intent mainIntent = new Intent(PhoneNumberVerificationActivity.this,MainActivity.class);
                                        mainIntent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                                        startActivity(mainIntent);
                                        finish();
                                    }
                                    else{
                                        errorText.setText("Check your Internet Connection");
                                        errorText.setVisibility(View.VISIBLE);
                                    }
                                }
                            });
                        } else {
                            // Sign in failed, display a message and update the UI
                            errorText.setText("There was some error in login");
                            errorText.setVisibility(View.VISIBLE);
                            if (task.getException() instanceof FirebaseAuthInvalidCredentialsException) {
                                // The verification code entered was invalid
                            }
                        }
                    }
                });
    }
}
