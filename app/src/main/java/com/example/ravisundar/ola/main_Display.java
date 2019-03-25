package com.example.ravisundar.ola;

import android.content.Intent;
import android.location.Address;
import android.location.Geocoder;
import android.support.v4.app.FragmentActivity;
import android.os.Bundle;

import com.google.android.gms.common.GooglePlayServicesNotAvailableException;
import com.google.android.gms.common.GooglePlayServicesRepairableException;
import com.google.android.gms.location.places.Place;
import com.google.android.gms.location.places.ui.PlacePicker;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.LatLngBounds;
import com.google.android.gms.maps.model.MarkerOptions;

import android.Manifest;
import android.content.Context;
import android.content.pm.PackageManager;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Build;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.FragmentActivity;
import android.os.Bundle;
import android.support.v4.content.ContextCompat;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.gms.maps.model.Polyline;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.io.IOException;
import java.util.List;
import java.util.Locale;


public class main_Display extends FragmentActivity implements OnMapReadyCallback {

    private GoogleMap mMap;
    LocationListener locationListener;
    LocationManager locationManager;
    EditText yourplace, destinationplace;
    Button search, search1, cartype;
    int PLACE_PICKER_REQUEST = 1;
    int a = 0;
    Double latitude1 = 0.0, longitude1 = 0.0, latitude2 = 0.0, longitude2 = 0.0;
    private DatabaseReference databaseReference,databaseReference2;
    String number,toastMsg1,toastMsg2;
    float[] results = new float[10];

    String url;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main__display);
        // Obtain the SupportMapFragment and get notified when the map is ready to be used.
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);
        databaseReference = FirebaseDatabase.getInstance().getReference("customer");
        databaseReference2 = FirebaseDatabase.getInstance().getReference("driver");
        yourplace = findViewById(R.id.place);
        destinationplace = findViewById(R.id.destinationplace);
        search1 = findViewById(R.id.search1);
        search = findViewById(R.id.search);
        cartype = findViewById(R.id.cartype);

        Bundle extras = getIntent().getExtras();
        number = extras.getString("number");


        cartype.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Location.distanceBetween(latitude1,longitude1,latitude2,longitude2 ,results);
                //Toast.makeText(getApplicationContext(), Float.toString(results[0]),Toast.LENGTH_LONG).show();


                try {
                    databaseReference.child(number).child("latitude1").setValue(latitude1);
                    databaseReference.child(number).child("longitude1").setValue(longitude1);
                    databaseReference.child(number).child("source").setValue(toastMsg1);
                    databaseReference.child(number).child("latitude2").setValue(latitude2);
                    databaseReference.child(number).child("longitude2").setValue(longitude2);
                    final float val11= (float) (((results[0])*0.001609));
                    databaseReference.child(number).child("distance").setValue(val11);
                    databaseReference.child(number).child("destination").setValue(toastMsg2).addOnSuccessListener(new OnSuccessListener<Void>() {
                        @Override
                        public void onSuccess(Void aVoid) {
                            String s= String.valueOf(val11);
                            Intent i = new Intent(getApplicationContext(), Driving_type.class);
                            i.putExtra("number",number);
                            i.putExtra("distance",s);
                            startActivity(i);
                        }
                    });


                } catch (Exception e) {
                    e.printStackTrace();
                }




            }
        });


        //source
        search.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                a=0;
                PlacePicker.IntentBuilder builder = new PlacePicker.IntentBuilder();
                try {
                    startActivityForResult(builder.build(main_Display.this), PLACE_PICKER_REQUEST);
                } catch (GooglePlayServicesNotAvailableException e) {
                    e.printStackTrace();
                } catch (GooglePlayServicesRepairableException e) {
                    e.printStackTrace();
                } catch (Exception e) {
                    String x = e.toString();
                    Log.e("@@@", x);
                }
            }
        });

        //destination

        search1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                a=1;
                PlacePicker.IntentBuilder builder = new PlacePicker.IntentBuilder();
                try {
                    startActivityForResult(builder.build(main_Display.this), PLACE_PICKER_REQUEST);
                } catch (GooglePlayServicesNotAvailableException e) {
                    e.printStackTrace();
                } catch (GooglePlayServicesRepairableException e) {
                    e.printStackTrace();
                } catch (Exception e) {
                    String x = e.toString();
                    Log.e("@@@", x);
                }
            }
        });

    }


    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == PLACE_PICKER_REQUEST) {
            if (resultCode == RESULT_OK) {
                Place place;
                Bundle extras = getIntent().getExtras();
                //String id1=extras.getString("id");
                place = PlacePicker.getPlace(main_Display.this, data);

                if (a==0) {
                    toastMsg1 = String.format("%s", place.getName());
                    yourplace.setText(toastMsg1);
                    latitude1 = place.getLatLng().latitude;
                    longitude1 = place.getLatLng().longitude;
                   // Toast.makeText(this, latitude1 + " " + longitude1, Toast.LENGTH_LONG).show();
                    LatLng source = new LatLng(latitude1, longitude1);
                    //  databaseReference.child("driver").child(id1).setValue(latitude1,longitude1);
                    mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(source, 16));
                    mMap.addMarker(new MarkerOptions().position(source).title("Your place"));
                    mMap.moveCamera(CameraUpdateFactory.newLatLng(source));
                }
                else{
                    toastMsg2 = String.format("%s", place.getName());
                    destinationplace.setText(toastMsg2);
                    latitude2 = place.getLatLng().latitude;
                    longitude2 = place.getLatLng().longitude;
                   // Toast.makeText(this, latitude1 + " " + longitude1, Toast.LENGTH_LONG).show();
                    LatLng destination = new LatLng(latitude2, longitude2);
                    //  databaseReference.child("driver").child(id1).setValue(latitude1,longitude1);
                    mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(destination, 16));
                    mMap.addMarker(new MarkerOptions().position(destination).title("Your destination"));
                    mMap.moveCamera(CameraUpdateFactory.newLatLng(destination));
                }


            }
        }



    }


    /**
     * Manipulates the map once available.
     * This callback is triggered when the map is ready to be used.
     * This is where we can add markers or lines, add listeners or move the camera. In this case,
     * we just add a marker near Sydney, Australia.
     * If Google Play services is not installed on the device, the user will be prompted to install
     * it inside the SupportMapFragment. This method will only be triggered once the user has
     * installed Google Play services and returned to the app.
     */
    @Override
    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;


    }
}
