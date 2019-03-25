package com.example.ravisundar.ola;

import android.Manifest;
import android.app.PendingIntent;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.telephony.SmsManager;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.android.gms.tasks.TaskExecutors;
import com.google.firebase.FirebaseException;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.PhoneAuthCredential;
import com.google.firebase.auth.PhoneAuthProvider;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

import java.util.Random;
import java.util.concurrent.TimeUnit;

import static android.widget.Toast.LENGTH_SHORT;

public class signup extends AppCompatActivity {
EditText name,phone;
Button signup;
String username,number,code;


    FirebaseAuth auth;
      @Override
    protected void onCreate(Bundle savedInstanceState) {
          super.onCreate(savedInstanceState);
          setContentView(R.layout.activity_signup);
          name = findViewById(R.id.username);
          phone = findViewById(R.id.number);
          signup = findViewById(R.id.Done);


          // mstorage= FirebaseStorage.getInstance().getReference("/ullalla/XW9S3aemK2gXxBImrDvD").child("customer");


          signup.setOnClickListener(new View.OnClickListener() {
              @Override
              public void onClick(View v) {
                  username = name.getText().toString();
                  number = phone.getText().toString();


                  Intent i = new Intent(getApplicationContext(), signup_otp.class);
                  i.putExtra("username", username);
                  i.putExtra("number", number);

                  startActivity(i);
              }
          });

      }
}
