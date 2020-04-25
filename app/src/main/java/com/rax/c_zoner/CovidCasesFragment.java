package com.rax.c_zoner;

import android.content.Context;
import android.graphics.Color;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.databinding.DataBindingUtil;
import androidx.fragment.app.Fragment;

import com.android.volley.Request;
import com.android.volley.VolleyError;
import com.github.mikephil.charting.data.PieData;
import com.github.mikephil.charting.data.PieDataSet;
import com.github.mikephil.charting.data.PieEntry;
import com.github.mikephil.charting.utils.ColorTemplate;
import com.rax.c_zoner.databinding.FragmentCovidCasesBinding;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

public class CovidCasesFragment extends Fragment {
    ApplicationClass applicationClass;
    JSONObject data, summary;
    PieData pieData;
    PieDataSet pieDataSet;
    ArrayList newPieEntry;
    private FragmentCovidCasesBinding binding;
    private Context mContext;
    private String url_1 = "https://api.rootnet.in/covid19-in/stats/latest", string, count;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        binding = DataBindingUtil.inflate(getLayoutInflater(), R.layout.fragment_covid_cases, container, false);
        return binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        mContext = getContext();
        Log.v("gva", "view Create");
        getCovidCaseCount();

    }


    private void getCovidCaseCount() {
        try {
            applicationClass = new ApplicationClass();
            applicationClass.httpRequestNew(mContext, url_1, null, Request.Method.GET, new VolleyCallback() {

                @Override
                public void OnSuccess(JSONObject object) {

                    try {
                        data = new JSONObject();
                        summary = new JSONObject();
                        data = object.getJSONObject("data");
                        summary = data.getJSONObject("summary");

                        JSONArray regional = new JSONArray();
                        JSONObject obj = new JSONObject();
                        regional = data.getJSONArray("regional");

                        binding.txtCaseConfirmed.setText(summary.getString("confirmedCasesIndian"));
                        binding.txtCaseConfirmedForeign.setText(summary.getString("confirmedCasesForeign"));
                        binding.txtDeaths.setText(summary.getString("deaths"));
                        binding.txtDischarged.setText(summary.getString("discharged"));
                        binding.txtTotal.setText(summary.getString("total"));

                        newPieEntry = new ArrayList();
                        for (int i = 0; i < regional.length(); i++) {

                            if (regional.getJSONObject(i).getString("loc").equals("Tamil Nadu")) {
                                obj = regional.getJSONObject(i);
                                newPieEntry.add(new PieEntry(obj.getInt("confirmedCasesIndian"), 1));
                                newPieEntry.add(new PieEntry(obj.getInt("discharged"), 2));
                                newPieEntry.add(new PieEntry(obj.getInt("deaths"), 3));
                                newPieEntry.add(new PieEntry(obj.getInt("confirmedCasesForeign"), 4));
                                newPieEntry.add(new PieEntry(obj.getInt("totalConfirmed"), 5));
                            }

                        }
                        Log.v("gva", (String) obj.get("loc"));

                        pieDataSet = new PieDataSet(newPieEntry, "Covid Cases");
                        pieData = new PieData(pieDataSet);
                        binding.chartPieChart.setData(pieData);
                        pieDataSet.setColors(ColorTemplate.JOYFUL_COLORS);
                        pieDataSet.setSliceSpace(10f);
                        pieDataSet.setValueTextColor(Color.BLACK);
                        pieDataSet.setValueTextSize(10f);
                        pieDataSet.setSliceSpace(10f);


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
