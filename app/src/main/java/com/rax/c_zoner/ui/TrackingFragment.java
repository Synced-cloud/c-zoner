package com.rax.c_zoner.ui;

import android.content.Context;
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
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.Circle;
import com.google.android.gms.maps.model.CircleOptions;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.dialog.MaterialAlertDialogBuilder;
import com.rax.c_zoner.R;
import com.rax.c_zoner.databinding.FragmentTrackingBinding;

import java.util.ArrayList;
import java.util.List;

public class TrackingFragment extends Fragment implements OnMapReadyCallback {
    List<Circle> circles;
    private FragmentTrackingBinding binding;
    private Context mContext;
    private GoogleMap googleMap;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        binding = DataBindingUtil.inflate(getLayoutInflater(), R.layout.fragment_tracking, container, false);
        return binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        mContext = getContext();
        circles = new ArrayList<>();
        SupportMapFragment mapFragment = (SupportMapFragment) getChildFragmentManager()
                .findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);
        binding.btnSave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                new MaterialAlertDialogBuilder(mContext).setTitle("Fencing").setMessage("Fencing saved successfully").setPositiveButton("OK", null).show();
            }
        });
        binding.btnClear.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (googleMap != null) {
                    googleMap.clear();
//                    for (Circle circle : circles) {
//                        circle.remove();
//                    }
                }
                //  circles.clear();
            }
        });
        binding.btnRequest.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (googleMap != null) {
                    googleMap.addMarker(new MarkerOptions()
                            .position(new LatLng(13.034495, 80.197455))
                            .title("09.00 AM \n 13.034495, 80.197455"));
                    googleMap.addMarker(new MarkerOptions()
                            .position(new LatLng(13.036444, 80.198037))
                            .title("09.03 AM \n 13.036444, 80.198037"));
                    googleMap.addMarker(new MarkerOptions()
                            .position(new LatLng(13.037149, 80.196195))
                            .title("09.10 AM \n 13.037149, 80.196195"));
                    googleMap.addMarker(new MarkerOptions()
                            .position(new LatLng(13.039340, 80.196526))
                            .title("09.15 AM \n 13.039340, 80.196526"));
                    googleMap.addMarker(new MarkerOptions()
                            .position(new LatLng(13.040368, 80.193123))
                            .title("09.16 AM \n 13.040368, 80.193123"));
                    googleMap.addMarker(new MarkerOptions()
                            .position(new LatLng(13.042745, 80.193482))
                            .title("09.17 AM \n 13.042745, 80.193482"));
                    googleMap.addMarker(new MarkerOptions()
                            .position(new LatLng(13.042459, 80.189783))
                            .title("09.20 AM \n 13.042459, 80.189783"));
                    googleMap.addMarker(new MarkerOptions()
                            .position(new LatLng(13.043571, 80.189606))
                            .title("09.23 AM \n 13.043571, 80.189606"));
                    googleMap.addMarker(new MarkerOptions()
                            .position(new LatLng(13.046389, 80.189385))
                            .title("09.25 AM \n 13.046389, 80.189385"));
                    googleMap.addMarker(new MarkerOptions()
                            .position(new LatLng(13.047474, 80.189845))
                            .title("09.30 AM \n 13.047474, 80.189845"));

                    //  googleMap.addMarker(new MarkerOptions().position(new LatLng(13.034495,80.197455)).title("09.00 AM").icon(BitmapDescriptorFactory.fromResource(R.drawable.tower)));
                }
            }
        });
    }

    @Override
    public void onMapReady(final GoogleMap googleMap) {
        this.googleMap = googleMap;
        googleMap.setMyLocationEnabled(true);
        FusedLocationProviderClient fusedLocationProviderClient = LocationServices.getFusedLocationProviderClient(mContext);
        fusedLocationProviderClient.getLastLocation().addOnCompleteListener(
                new OnCompleteListener<Location>() {
                    @Override
                    public void onComplete(@NonNull Task<Location> task) {
                        Location location = task.getResult();
                        LatLng latLng = new LatLng(location.getLatitude(), location.getLongitude());
                        if (location != null) {
                            googleMap.moveCamera(CameraUpdateFactory.newLatLngZoom(latLng, 14.0f));
                        }
                    }
                }
        );
        googleMap.setOnMapClickListener(new GoogleMap.OnMapClickListener() {
            @Override
            public void onMapClick(LatLng latLng) {
                circles.add(googleMap.addCircle(getCircle(latLng, getResources().getColor(R.color.colorExtreme))));
            }
        });
    }

    private CircleOptions getCircle(LatLng latLng, int fillColor) {
        return new CircleOptions()
                .center(latLng)
                .radius(200)
                .fillColor(fillColor)
                .strokeColor(Color.TRANSPARENT)
                .strokeWidth(2);
    }
}
