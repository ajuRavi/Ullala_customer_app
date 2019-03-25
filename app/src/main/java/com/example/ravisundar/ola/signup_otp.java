package com.example.ravisundar.ola;

import android.app.PendingIntent;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.telephony.SmsManager;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;


import com.goodiebag.pinview.Pinview;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.android.gms.tasks.TaskExecutors;
import com.google.firebase.FirebaseException;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseAuthInvalidCredentialsException;
import com.google.firebase.auth.PhoneAuthCredential;
import com.google.firebase.auth.PhoneAuthProvider;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.StorageReference;


import java.util.Random;
import java.util.concurrent.TimeUnit;

import static android.icu.lang.UCharacter.GraphemeClusterBreak.T;

public class signup_otp extends AppCompatActivity {

    private FirebaseAuth mAuth;
    Pinview pin;
    Button verify;
    private DatabaseReference databaseReference;
   // private StorageReference mstorage;
    EditText entercode;
    String code, verificationid, username, number, password;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_signup_otp);

        mAuth = FirebaseAuth.getInstance();
        databaseReference = FirebaseDatabase.getInstance().getReference();

        entercode = findViewById(R.id.entercode);

        verify = findViewById(R.id.verify);

        pin = (Pinview) findViewById(R.id.pinview);
        pin.setPinHeight(100);
        pin.setPinWidth(140);
        pin.setInputType(Pinview.InputType.NUMBER);

        Bundle extras = getIntent().getExtras();
        username = extras.getString("username");

        number = extras.getString("number");
        sendVerificationCode(number);

        verify.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (code != null) {
                    verifyVerificationCode(code);
                }
            }
        });


    }

    private void sendVerificationCode(String mobile) {
        PhoneAuthProvider.getInstance().verifyPhoneNumber(
                "+91" + mobile,
                60,
                TimeUnit.SECONDS,
                TaskExecutors.MAIN_THREAD,
                mCallbacks);
    }


    //the callback to detect the verification status
    private PhoneAuthProvider.OnVerificationStateChangedCallbacks mCallbacks = new PhoneAuthProvider.OnVerificationStateChangedCallbacks() {
        @Override
        public void onVerificationCompleted(PhoneAuthCredential phoneAuthCredential) {

            //Getting the code sent by SMS
            String code = phoneAuthCredential.getSmsCode();

            //sometime the code is not detected automatically
            //in this case the code will be null
            //so user has to manually enter the code
            if (code != null) {
                entercode.setText(code);
                //verifying the code
                verifyVerificationCode(code);
            }
        }

        @Override
        public void onVerificationFailed(FirebaseException e) {
            Toast.makeText(signup_otp.this, e.getMessage(), Toast.LENGTH_LONG).show();
        }

        @Override
        public void onCodeSent(String s, PhoneAuthProvider.ForceResendingToken forceResendingToken) {
            super.onCodeSent(s, forceResendingToken);
            Toast.makeText(signup_otp.this, "message sent", Toast.LENGTH_LONG).show();
            //storing the verification id that is sent to the user
            verificationid = s;


        }
    };


    private void verifyVerificationCode(String code) {
        //creating the credential
        PhoneAuthCredential credential = PhoneAuthProvider.getCredential(verificationid, code);

        //signing the user
        signInWithPhoneAuthCredential(credential);
    }

    private void signInWithPhoneAuthCredential(PhoneAuthCredential credential) {
        mAuth.signInWithCredential(credential)
                .addOnCompleteListener(signup_otp.this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            String id = databaseReference.push().getKey();
                            user_signup signup = new user_signup(id, username, number);
                            databaseReference.child("customer").child(number).setValue(signup).addOnSuccessListener(new OnSuccessListener<Void>() {
                                @Override
                                public void onSuccess(Void aVoid) {
                                    Log.w("done", "yes");
                                    Intent intent = new Intent(getApplicationContext(), main_Display.class);
                                    intent.putExtra("number",number);
                                    startActivity(intent);
                                }
                            });

                        } else {
                            Toast.makeText(getApplicationContext(), task.getException().getMessage(), Toast.LENGTH_LONG);
                        }

                    }
                });
    }

}

