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
import androidx.recyclerview.widget.LinearLayoutManager;

import com.android.volley.Request;
import com.android.volley.VolleyError;
import com.rax.c_zoner.databinding.FragmentTestCasesBinding;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

public class TestCases extends Fragment {
    ApplicationClass applicationClass;
    ArrayList<ModelTest> testModel;
    private FragmentTestCasesBinding binding;
    private Context mContext;
    private String url_3 = "https://api.rootnet.in/covid19-in/stats/testing/history";


    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        binding = DataBindingUtil.inflate(getLayoutInflater(), R.layout.fragment_test_cases, container, false);
        return binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        mContext = getContext();
        GetTestCases();
    }

    private void GetTestCases() {
        applicationClass = new ApplicationClass();
        try {
            applicationClass.httpRequestNew(mContext, url_3, null, Request.Method.GET, new VolleyCallback() {
                @Override
                public void OnSuccess(JSONObject object) {
                    JSONArray model;
                    try {
                        model = object.getJSONArray("data");
                        testModel = new ArrayList<>();
                        for (int i = 0; i < model.length(); i++) {
                            testModel.add(new ModelTest(
                                    model.getJSONObject(i).isNull("day") ? "" : model.getJSONObject(i).getString("day"),
                                    model.getJSONObject(i).isNull("totalSamplesTested") ? 0 : model.getJSONObject(i).getInt("totalSamplesTested"),
                                    model.getJSONObject(i).isNull("totalIndividualsTested") ? 0 : model.getJSONObject(i).getInt("totalIndividualsTested"),
                                    model.getJSONObject(i).isNull("totalPositiveCases") ? 0 : model.getJSONObject(i).getInt("totalPositiveCases"),
                                    model.getJSONObject(i).isNull("source") ? "" : model.getJSONObject(i).getString("source")
                            ));
                        }
                        TestCaseAdapter testCaseAdapter;
                        testCaseAdapter = new TestCaseAdapter(mContext, testModel);
                        binding.testCasesRv.setLayoutManager(new LinearLayoutManager(mContext));
                        binding.testCasesRv.setAdapter(testCaseAdapter);

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

