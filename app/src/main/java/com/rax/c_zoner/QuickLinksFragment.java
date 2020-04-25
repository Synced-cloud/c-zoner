package com.rax.c_zoner;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
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
import com.rax.c_zoner.databinding.FragmentGetNotificationBinding;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class QuickLinksFragment extends Fragment {
    QuickLinkAdapter adapter;
    String URL = "https://api.rootnet.in/covid19-in/notifications";
    private ApplicationClass applicationClass;
    private FragmentGetNotificationBinding binding;
    private Context mContext;
    private List<NotificationModel> notificationList = new ArrayList<>();
    ItemClickListener listener = new ItemClickListener() {
        @Override
        public void OnClick(int pos) {
            try {
                NotificationModel model = notificationList.get(pos);
                Intent i = new Intent(Intent.ACTION_VIEW);
                i.setData(Uri.parse(model.getLink()));
                startActivity(i);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    };

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        binding = DataBindingUtil.inflate(getLayoutInflater(), R.layout.fragment_get_notification, container, false);
        return binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        applicationClass = new ApplicationClass();
        mContext = getContext();
        applicationClass = (ApplicationClass) getActivity().getApplication();
        adapter = new QuickLinkAdapter(mContext, notificationList, listener);
        binding.rvNotification.setLayoutManager(new LinearLayoutManager(mContext));
        binding.rvNotification.setAdapter(adapter);
        getQuickLinks();
    }

    private void getQuickLinks() {
        applicationClass = new ApplicationClass();
        try {
            applicationClass.httpRequestNew(mContext, URL, null, Request.Method.GET, new VolleyCallback() {
                @Override
                public void OnSuccess(JSONObject object) {
                    JSONArray model;
                    JSONObject object1;
                    try {
                        object1 = object.getJSONObject("data");
                        model = object1.getJSONArray("notifications");
                        notificationList.clear();
                        for (int i = 0; i < model.length(); i++) {
                            notificationList.add(new NotificationModel(
                                    model.getJSONObject(i).isNull("title") ? "" : model.getJSONObject(i).getString("title"),
                                    model.getJSONObject(i).isNull("link") ? "" : model.getJSONObject(i).getString("link")
                            ));
                        }
                        adapter.notifyDataSetChanged();
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
