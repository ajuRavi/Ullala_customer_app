package com.example.ravisundar.ola;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Toast;

import com.google.android.gms.common.GooglePlayServicesNotAvailableException;
import com.google.android.gms.common.GooglePlayServicesRepairableException;
import com.google.android.gms.location.places.Place;
import com.google.android.gms.location.places.ui.PlacePicker;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class Driving_type extends AppCompatActivity {
ImageButton imageButton1,imageButton2,imageButton3,imageButton4;
    private DatabaseReference databaseReference,databaseReference2;
    String number,distance,cnumber,cb,vehiclenumber,s;
    String [] arr=new String[3];



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_driving_type);
        //Bundle extras = getIntent().getExtras();

        Bundle extras=getIntent().getExtras();
        cnumber=extras.getString("number");
        distance=extras.getString("distance");

        imageButton1=findViewById(R.id.imageButton5);
        imageButton2=findViewById(R.id.imageButton6);
        imageButton3=findViewById(R.id.imageButton7);
        imageButton4=findViewById(R.id.imageButton8);

        databaseReference= FirebaseDatabase.getInstance().getReference("driver");
        databaseReference2= FirebaseDatabase.getInstance().getReference("customer");

//      a

        databaseReference.child(cnumber).child("cab").addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
             cb=dataSnapshot.getValue(String.class);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });

        imageButton1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                databaseReference.addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                        for (DataSnapshot data: dataSnapshot.getChildren()){
                            number = data.getKey();
                            String val=dataSnapshot.child(data.getKey()).child("choose").getValue(String.class);
                            //Toast.makeText(getApplicationContext(),data.getKey()+val,Toast.LENGTH_LONG).show();
                            float val2;
                            if (val.matches("bike")){
                                if (cb.matches("yes")){
                                if (Float.valueOf(distance) *8.0<=30){
                                    val2= (float) 30.0;
                                }
                                else {
                                    val2 = Math.round( (float) (Float.valueOf(distance) * 8.0)) ;
                                }


                                s=Float.toString(val2);
                               // int val2=Integer.valueOf(distance)*3;

                                    try{
                                        vehiclenumber=dataSnapshot.child(number).child("vehiclenumber").getValue(String.class);
                                        arr=vehiclenumber.split(" ");
                                         databaseReference2.child(cnumber).child("amount").setValue(s);
                                        databaseReference.child(cnumber).child("otp").setValue(arr[3]).addOnSuccessListener(new OnSuccessListener<Void>() {
                                            @Override
                                            public void onSuccess(Void aVoid) {
                                                Intent i =new Intent(getApplicationContext(),Final_confirmation.class);
                                                i.putExtra("number",cnumber);
                                                i.putExtra("number2",number);
                                                i.putExtra("amount",s);
                                                startActivity(i);
                                            }
                                        });


                                    }catch (Exception e){
                                        e.printStackTrace();
                                    }

                            }
                            else{
                                    Toast.makeText(getApplicationContext(),"no bike is available in this region",Toast.LENGTH_LONG).show();
                                }
                            }
                            else{
                                Toast.makeText(getApplicationContext(),"it takes sometime",Toast.LENGTH_LONG).show();
                            }
                        }

                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError databaseError) {

                    }
                });
            }
        });
        imageButton2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                cb="yes";
                databaseReference.addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                        for (DataSnapshot data: dataSnapshot.getChildren()){
                            number = data.getKey();
                            float val2;
                            String val=dataSnapshot.child(data.getKey()).child("choose").getValue(String.class);
                            if (val.matches("auto")){
                                if(cb.matches("yes")){
                                    if (Float.valueOf(distance) *10.0<=30){
                                        val2= (float) 40.0;
                                    }
                                    else {
                                        val2 = Math.round( (float) Math.ceil((Float.valueOf(distance)) * 10.0)) ;
                                        //val2 = Math.round( (float) (Float.valueOf(distance) * 14.0)) ;
                                    }

                                    s=Float.toString(val2);
                                    try{
                                        vehiclenumber=dataSnapshot.child(number).child("vehiclenumber").getValue(String.class);
                                        arr=vehiclenumber.split(" ");
                                        databaseReference2.child(cnumber).child("amount").setValue(s);
                                        databaseReference.child(cnumber).child("otp").setValue(arr[3]).addOnSuccessListener(new OnSuccessListener<Void>() {
                                            @Override
                                            public void onSuccess(Void aVoid) {
                                                Intent i =new Intent(getApplicationContext(),Final_confirmation.class);
                                                i.putExtra("number",cnumber);
                                                i.putExtra("number2",number);
                                                i.putExtra("amount",s);
                                                startActivity(i);
                                            }
                                        });


                                    }catch (Exception e){
                                        e.printStackTrace();
                                    }

                                }
                                else{
                                    Toast.makeText(getApplicationContext(),"no auto is available in this region",Toast.LENGTH_LONG).show();}
                            }
                            else{
                                Toast.makeText(getApplicationContext(),"It takes few minutes",Toast.LENGTH_LONG).show();
                            }
                        }

                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError databaseError) {

                    }
                });

            }
        });

        imageButton3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                cb="yes";
                databaseReference.addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                        for (DataSnapshot data: dataSnapshot.getChildren()){
                            number = data.getKey();
                            float val2;
                            String val=dataSnapshot.child(data.getKey()).child("choose").getValue(String.class);
                            if (val.matches("cars3")){
                                if(cb.matches("yes")){
                                if (Float.valueOf(distance) *12.0<=30){
                                 val2= (float) 40.0;
                                }
                                else {
                                    val2 = Math.round( (float) Math.ceil((Float.valueOf(distance)) * 14.0)) ;
                                    //val2 = Math.round( (float) (Float.valueOf(distance) * 14.0)) ;
                                }

                                 s=Float.toString(val2);
                                    try{
                                        vehiclenumber=dataSnapshot.child(number).child("vehiclenumber").getValue(String.class);
                                        arr=vehiclenumber.split(" ");
                                        databaseReference2.child(cnumber).child("amount").setValue(s);
                                        databaseReference.child(cnumber).child("otp").setValue(arr[3]).addOnSuccessListener(new OnSuccessListener<Void>() {
                                            @Override
                                            public void onSuccess(Void aVoid) {
                                                Intent i =new Intent(getApplicationContext(),Final_confirmation.class);
                                                i.putExtra("number",cnumber);
                                                i.putExtra("number2",number);
                                                i.putExtra("amount",s);
                                                startActivity(i);
                                            }
                                        });


                                    }catch (Exception e){
                                        e.printStackTrace();
                                    }

                            }
                            else{
                                    Toast.makeText(getApplicationContext(),"no car is available in this region",Toast.LENGTH_LONG).show();}
                            }
                            else{
                                Toast.makeText(getApplicationContext(),"It takes few minutes",Toast.LENGTH_LONG).show();
                            }
                        }

                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError databaseError) {

                    }
                });

            }
        });
        imageButton4.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                databaseReference.addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                        for (DataSnapshot data: dataSnapshot.getChildren()){
                            number = data.getKey();
                            float val2;
                            String val=dataSnapshot.child(data.getKey()).child("choose").getValue(String.class);
                           // Toast.makeText(getApplicationContext(),data.getKey()+val,Toast.LENGTH_LONG).show();
                            if (Float.valueOf(distance) *14.0<=30){
                                val2= (float) 40.0;
                            }
                            else {
                                val2 = Math.round( (float) (Float.valueOf(distance) * 14.0)) ;
                            }
                             s=Float.toString(val2);

                            if (val.matches("cars5")){
                                //int val2=Integer.valueOf(distance)*15;
                                try{
                                    vehiclenumber=dataSnapshot.child(number).child("vehiclenumber").getValue(String.class);
                                    arr=vehiclenumber.split(" ");
                                    databaseReference2.child(cnumber).child("amount").setValue(s);
                                    databaseReference.child(cnumber).child("otp").setValue(arr[3]).addOnSuccessListener(new OnSuccessListener<Void>() {
                                        @Override
                                        public void onSuccess(Void aVoid) {
                                            Intent i =new Intent(getApplicationContext(),Final_confirmation.class);
                                            i.putExtra("number",cnumber);
                                            i.putExtra("number2",number);
                                            i.putExtra("amount",s);
                                            startActivity(i);
                                        }
                                    });


                                }catch (Exception e){
                                    e.printStackTrace();
                                }
                            }
                            else{
                                Toast.makeText(getApplicationContext(),"no cars is available in this region",Toast.LENGTH_LONG).show();
                            }
                        }

                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError databaseError) {

                    }
                });

            }
        });
    }
}
