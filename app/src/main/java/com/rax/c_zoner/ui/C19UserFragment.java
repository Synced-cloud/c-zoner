package com.rax.c_zoner.ui;

import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.location.Location;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.databinding.DataBindingUtil;
import androidx.fragment.app.Fragment;

import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.Geofence;
import com.google.android.gms.location.GeofencingClient;
import com.google.android.gms.location.GeofencingRequest;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.CircleOptions;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.rax.c_zoner.R;
import com.rax.c_zoner.databinding.FragmentC19UserBinding;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Random;

public class C19UserFragment extends Fragment implements OnMapReadyCallback {

    private static final String TAG = "C19UserFragment";
    FusedLocationProviderClient mFusedLocationProviderClient;
    private List<Geofence> geofenceList;
    private Context mContext;
    private GeofencingClient geofencingClient;
    private PendingIntent geofencePendingIntent;
    private FragmentC19UserBinding binding;

    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        binding = DataBindingUtil.inflate(getLayoutInflater(), R.layout.fragment_c19_user, container, false);
        return binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        mContext = getContext();
        SupportMapFragment mapFragment = (SupportMapFragment) getChildFragmentManager()
                .findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);
        geofencingClient = LocationServices.getGeofencingClient(mContext);
        geofenceList = new ArrayList<>();
        geofenceList.add(new Geofence.Builder()
                .setRequestId("Test")
                .setCircularRegion(
                        13.0491049,
                        80.1978274,
                        200
                )
                .setExpirationDuration(60000)
                .setTransitionTypes(Geofence.GEOFENCE_TRANSITION_ENTER |
                        Geofence.GEOFENCE_TRANSITION_EXIT)
                .build());
        geofencingClient.addGeofences(getGeofencingRequest(), getGeofencePendingIntent()).addOnSuccessListener(new OnSuccessListener<Void>() {
            @Override
            public void onSuccess(Void aVoid) {

            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {

            }
        });

        mFusedLocationProviderClient = LocationServices.getFusedLocationProviderClient(mContext);
    }

    private CircleOptions getCircle(LatLng latLng, int fillColor) {
        return new CircleOptions()
                .center(latLng)
                .radius(200)
                .fillColor(fillColor)
                .strokeColor(Color.TRANSPARENT)
                .strokeWidth(2);
    }


    private GeofencingRequest getGeofencingRequest() {
        GeofencingRequest.Builder builder = new GeofencingRequest.Builder();
        builder.setInitialTrigger(GeofencingRequest.INITIAL_TRIGGER_ENTER);
        builder.addGeofences(geofenceList);
        return builder.build();
    }

    private PendingIntent getGeofencePendingIntent() {
        // Reuse the PendingIntent if we already have it.
        if (geofencePendingIntent != null) {
            return geofencePendingIntent;
        }
        Intent intent = new Intent(mContext, GeofenceBroadcastReceiver.class);
        // We use FLAG_UPDATE_CURRENT so that we get the same pending intent back when
        // calling addGeofences() and removeGeofences().
        geofencePendingIntent = PendingIntent.getBroadcast(mContext, 0, intent, PendingIntent.
                FLAG_UPDATE_CURRENT);
        return geofencePendingIntent;
    }

    @Override
    public void onMapReady(final GoogleMap googleMap) {
        googleMap.setMyLocationEnabled(true);
        FusedLocationProviderClient fusedLocationProviderClient = LocationServices.getFusedLocationProviderClient(mContext);
        //     LatLng latLng = new LatLng(googleMap.getMyLocation().getLatitude(), googleMap.getMyLocation().getLongitude());
        fusedLocationProviderClient.getLastLocation().addOnCompleteListener(
                new OnCompleteListener<Location>() {
                    @Override
                    public void onComplete(@NonNull Task<Location> task) {
                        Location location = task.getResult();
                        LatLng latLng = new LatLng(location.getLatitude(), location.getLongitude());
                        if (location != null) {
                            googleMap.addCircle(getCircle(getRandomLocation(latLng, 1000), getResources().getColor(R.color.colorLow)));
                            googleMap.addCircle(getCircle(getRandomLocation(latLng, 1000), getResources().getColor(R.color.colorModerate)));
                            googleMap.addCircle(getCircle(getRandomLocation(latLng, 1000), getResources().getColor(R.color.colorLow)));
                            googleMap.addCircle(getCircle(getRandomLocation(latLng, 1000), getResources().getColor(R.color.colorModerate)));
                            googleMap.addCircle(getCircle(getRandomLocation(latLng, 1000), getResources().getColor(R.color.colorLow)));
                            googleMap.addCircle(getCircle(getRandomLocation(latLng, 1000), getResources().getColor(R.color.colorExtreme)));
                            googleMap.addCircle(getCircle(getRandomLocation(latLng, 1000), getResources().getColor(R.color.colorLow)));
                            googleMap.addCircle(getCircle(getRandomLocation(latLng, 1000), getResources().getColor(R.color.colorExtreme)));

                            googleMap.moveCamera(CameraUpdateFactory.newLatLngZoom(latLng, 14.0f));
                        }
                    }
                }
        );


    }


    public LatLng getRandomLocation(LatLng point, int radius) {

        List<LatLng> randomPoints = new ArrayList<>();
        List<Float> randomDistances = new ArrayList<>();
        Location myLocation = new Location("");
        myLocation.setLatitude(point.latitude);
        myLocation.setLongitude(point.longitude);

        //This is to generate 10 random points
        for (int i = 0; i < 10; i++) {
            double x0 = point.latitude;
            double y0 = point.longitude;

            Random random = new Random();

            // Convert radius from meters to degrees
            double radiusInDegrees = radius / 111000f;

            double u = random.nextDouble();
            double v = random.nextDouble();
            double w = radiusInDegrees * Math.sqrt(u);
            double t = 2 * Math.PI * v;
            double x = w * Math.cos(t);
            double y = w * Math.sin(t);

            // Adjust the x-coordinate for the shrinking of the east-west distances
            double new_x = x / Math.cos(y0);

            double foundLatitude = new_x + x0;
            double foundLongitude = y + y0;
            LatLng randomLatLng = new LatLng(foundLatitude, foundLongitude);
            randomPoints.add(randomLatLng);
            Location l1 = new Location("");
            l1.setLatitude(randomLatLng.latitude);
            l1.setLongitude(randomLatLng.longitude);
            randomDistances.add(l1.distanceTo(myLocation));
        }
        //Get nearest point to the centre
        int indexOfNearestPointToCentre =
                randomDistances.indexOf(Collections.min(randomDistances));
        return randomPoints.get(indexOfNearestPointToCentre);
    }

    public class GeofenceBroadcastReceiver extends BroadcastReceiver {
        // ...
        public void onReceive(Context context, Intent intent) {

        }
    }

}
