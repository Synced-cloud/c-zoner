package com.rax.c_zoner;

import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.databinding.DataBindingUtil;
import androidx.fragment.app.Fragment;

import com.android.volley.Request;
import com.android.volley.VolleyError;
import com.github.mikephil.charting.data.BarData;
import com.github.mikephil.charting.data.BarDataSet;
import com.github.mikephil.charting.data.BarEntry;
import com.github.mikephil.charting.utils.ColorTemplate;
import com.rax.c_zoner.databinding.FragmentHospitalBedsBinding;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

public class HospitalBeds extends Fragment {

    ApplicationClass applicationClass;
    JSONObject data, summary, obj;
    JSONArray regional;
    ArrayList newPieChart, beds;
    BarDataSet barDataSet, bedDataSet;
    BarData barData, bedData;
    private String url_2 = "https://api.rootnet.in/covid19-in/hospitals/beds";
    private FragmentHospitalBedsBinding binding;
    private Context mContext;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        binding = DataBindingUtil.inflate(getLayoutInflater(), R.layout.fragment_hospital_beds, container, false);
        return binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        mContext = getContext();
        GetHosp();

    }

    private void GetHosp() {
        applicationClass = new ApplicationClass();
        try {
            applicationClass.httpRequestNew(mContext, url_2, null, Request.Method.GET, new VolleyCallback() {
                @Override
                public void OnSuccess(JSONObject object) {

                    try {
                        data = object.getJSONObject("data");
                        summary = data.getJSONObject("summary");
                        regional = data.getJSONArray("regional");


                        binding.txtRbed.setText(summary.getString("ruralBeds"));
                        binding.txtRhosp.setText(summary.getString("ruralHospitals"));
                        binding.txtTbeds.setText(summary.getString("totalBeds"));
                        binding.txtThosp.setText(summary.getString("totalHospitals"));
                        binding.txtUbeds.setText(summary.getString("urbanBeds"));
                        binding.txtUhosp.setText(summary.getString("urbanHospitals"));

                        newPieChart = new ArrayList();
                        beds = new ArrayList();

                        for (int i = 0; i < regional.length(); i++) {
                            if (regional.getJSONObject(i).getString("state").equals("Tamil Nadu")) {
                                obj = regional.getJSONObject(i);
                                newPieChart.add(new BarEntry(obj.getInt("ruralHospitals"), 1));
                                newPieChart.add(new BarEntry(obj.getInt("urbanHospitals"), 2));
                                newPieChart.add(new BarEntry(obj.getInt("totalHospitals"), 3));

                                barDataSet = new BarDataSet(newPieChart, "Hospital");
                                barData = new BarData(barDataSet);
                                barData.setBarWidth(50f);
                                barDataSet.setColors(ColorTemplate.COLORFUL_COLORS);
                                binding.hospChart.setData(barData);

                                beds.add(new BarEntry(obj.getInt("ruralBeds"), 1));
                                beds.add(new BarEntry(obj.getInt("urbanBeds"), 2));
                                beds.add(new BarEntry(obj.getInt("totalBeds"), 3));

                                bedDataSet = new BarDataSet(beds, "Beds");
                                bedData = new BarData(bedDataSet);
                                bedData.setBarWidth(50f);
                                bedDataSet.setColors(ColorTemplate.COLORFUL_COLORS);
                                binding.bedsChart.setData(bedData);

                            }

                        }


                    } catch (JSONException e) {
                        e.printStackTrace();
                    }


                }

                @Override
                public void OnFailure(VolleyError error) {
                    error.printStackTrace();

                }
            }, 0);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

}
