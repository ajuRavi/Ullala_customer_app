package com.example.ravisundar.ola;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class Final_confirmation extends AppCompatActivity {
    TextView dname, source, dest, otp, number, amount, vehiclenumber;
    String number1, number2, dname1, source1, destination1, amount1, otp1, vehiclenumber1;
    Button cancel;
    ImageButton call1;
    private DatabaseReference databaseReference, databaseReference2;
    String []arr=new String[3];

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_final_confirmation);
        dname = findViewById(R.id.dname);
        source = findViewById(R.id.source);
        dest = findViewById(R.id.destination);
        otp = findViewById(R.id.otp);
        number = findViewById(R.id.dnumber);
        amount = findViewById(R.id.amount);
        cancel = findViewById(R.id.cancel);
        vehiclenumber = findViewById(R.id.vehiclenumber);
        call1=(ImageButton)findViewById(R.id.call);

        Bundle extras = getIntent().getExtras();
        number1 = extras.getString("number");
        number2 = extras.getString("number2");
        amount1 = extras.getString("amount");

        cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(getApplicationContext(), main_Display.class);
                i.putExtra("number", number1);
                startActivity(i);

            }
        });

        call1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent callIntent = new Intent(Intent.ACTION_DIAL);
                callIntent.setData(Uri.parse("tel:" + number.getText().toString()));
//                if (ActivityCompat.checkSelfPermission(getApplicationContext(), Manifest.permission.CALL_PHONE) != PackageManager.PERMISSION_GRANTED) {
//                    // TODO: Consider calling
//                    //    ActivityCompat#requestPermissions
//                    // here to request the missing permissions, and then overriding
//                    //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
//                    //                                          int[] grantResults)
//                    // to handle the case where the user grants the permission. See the documentation
//                    // for ActivityCompat#requestPermissions for more details.
//                    return;
//                }
                startActivity(callIntent);
            }
        });


        databaseReference = FirebaseDatabase.getInstance().getReference("customer");
        databaseReference2 = FirebaseDatabase.getInstance().getReference("driver");

//         dname1= String.valueOf(databaseReference2.child(number2).child("name").get)

        databaseReference2.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                dname1=dataSnapshot.child(number2).child("username").getValue(String.class);
                dname.setText(dname1);
               // number2=dataSnapshot.child(number2).child("phonenumber").getValue(String.class);
                number.setText(number2);
                vehiclenumber1=dataSnapshot.child(number2).child("vehiclenumber").getValue(String.class);
                vehiclenumber.setText(vehiclenumber1);
                arr=vehiclenumber1.split(" ");


                //otp1=dataSnapshot.child(number2).child("otp").getValue(String.class);
              otp.setText(arr[3]);
              amount.setText(amount1);


            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });

        databaseReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                source1=dataSnapshot.child(number1).child("source").getValue(String.class);
                source.setText(source1);
                destination1=dataSnapshot.child(number1).child("destination").getValue(String.class);
                dest.setText(destination1);
               //amount.setText(amount1);

            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });

    }
}
