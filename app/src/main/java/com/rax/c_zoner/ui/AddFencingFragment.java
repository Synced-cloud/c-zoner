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
import com.google.android.gms.maps.model.CircleOptions;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.dialog.MaterialAlertDialogBuilder;
import com.rax.c_zoner.R;
import com.rax.c_zoner.databinding.FragmentAddFenceBinding;

public class AddFencingFragment extends Fragment implements OnMapReadyCallback {
    private FragmentAddFenceBinding binding;
    private Context mContext;
    private GoogleMap googleMap;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        binding = DataBindingUtil.inflate(getLayoutInflater(), R.layout.fragment_add_fence, container, false);
        return binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        mContext = getContext();
        SupportMapFragment mapFragment = (SupportMapFragment) getChildFragmentManager()
                .findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);
        binding.txtLow.setChecked(true);
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
                switch (binding.safetySelection.getCheckedRadioButtonId()) {
                    case R.id.txtLow:
                        googleMap.addCircle(getCircle(latLng, getResources().getColor(R.color.colorLow)));
                        break;
                    case R.id.txtModerate:
                        googleMap.addCircle(getCircle(latLng, getResources().getColor(R.color.colorModerate)));
                        break;
                    case R.id.txtExtreme:
                        googleMap.addCircle(getCircle(latLng, getResources().getColor(R.color.colorExtreme)));
                        break;
                }
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
