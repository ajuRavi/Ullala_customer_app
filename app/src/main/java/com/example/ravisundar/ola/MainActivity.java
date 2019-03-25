package com.example.ravisundar.ola;

import android.content.Intent;
import android.graphics.Typeface;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class MainActivity extends AppCompatActivity {
Button  button,signup,Login;
Animation animBlink1,animBlink2;
TextView logoname,number;
    String num;
    private DatabaseReference databaseReference;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        button=findViewById(R.id.Login);
        logoname=findViewById(R.id.logoname);
        signup=findViewById(R.id.signup);
        Login=findViewById(R.id.Login);
        number=findViewById(R.id.number);
        num= number.getText().toString();
      animBlink1=  AnimationUtils.loadAnimation(this,R.anim.bounce);
      animBlink2=AnimationUtils.loadAnimation(this,R.anim.fadeout);
      signup.setOnClickListener(new View.OnClickListener() {
          @Override
          public void onClick(View v) {
             // animBlink=  AnimationUtils.loadAnimation(this,R.anim.bounce);
              signup.setVisibility(View.VISIBLE);
              signup.startAnimation(animBlink2);
              Intent i =new Intent(getApplicationContext(),signup.class);
              startActivity(i);
          }
      });
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                button.setVisibility(View.VISIBLE);
                button.startAnimation(animBlink1);
            }
        });
        Login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                databaseReference = FirebaseDatabase.getInstance().getReference("customer");
                databaseReference.addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                               // String val= dataSnapshot.child(number.getText().toString()).child("name").getValue(String.class);

                                if (dataSnapshot.child(number.getText().toString()).exists()) {
                                    Intent i =new Intent(getApplicationContext(),main_Display.class);
                                    i.putExtra("number",number.getText().toString());
                                    startActivity(i);

                                   }
                        else{
                                    Toast.makeText(getApplicationContext(), "check your credencials", Toast.LENGTH_SHORT).show();

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
