package com.rax.c_zoner.ui;

import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.databinding.DataBindingUtil;
import androidx.fragment.app.Fragment;

import com.rax.c_zoner.CovidCasesFragment;
import com.rax.c_zoner.HospitalBeds;
import com.rax.c_zoner.QuickLinksFragment;
import com.rax.c_zoner.R;
import com.rax.c_zoner.TestCases;
import com.rax.c_zoner.databinding.FragmentNewsFeedBinding;

public class NewsFeedFragment extends Fragment {
    TextView covidCases, hospital, testCase, notification;
    private FragmentNewsFeedBinding binding;
    private Context mContext;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        binding = DataBindingUtil.inflate(getLayoutInflater(), R.layout.fragment_news_feed, container, false);
        return binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        mContext = getContext();

        binding.txtCovidCases.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                new DialogFragment(new CovidCasesFragment(), "Covid Cases", null)
                        .show(getChildFragmentManager(), null);

            }
        });

        binding.txtHospital.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                new DialogFragment(new HospitalBeds(), "Hospital & Beds", null)
                        .show(getChildFragmentManager(), null);

            }
        });

        binding.txtNotifications.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                new DialogFragment(new QuickLinksFragment(), "QuickLinks", null)
                        .show(getChildFragmentManager(), null);
            }
        });

        binding.txtTestCases.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                new DialogFragment(new TestCases(), "Test cases", null)
                        .show(getChildFragmentManager(), null);
            }
        });


    }


}
