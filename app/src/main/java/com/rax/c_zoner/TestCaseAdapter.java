package com.rax.c_zoner;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class TestCaseAdapter extends RecyclerView.Adapter<TestCaseAdapter.ViewHolder> {

    private View itemView;
    private ArrayList<ModelTest> model;


    public TestCaseAdapter(Context mContext, ArrayList<ModelTest> testModel) {
        this.model = testModel;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.testcases_list, parent, false);
        return new ViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        holder.day.setText(model.get(position).getDay());
        holder.totalPositive.setText(model.get(position).getTotalPositiveCases() + "");
        holder.totalIndividual.setText(model.get(position).getTotalIndividualsTested() + "");
        holder.totalTested.setText(model.get(position).getTotalSamplesTested() + "");
        holder.source.setText(model.get(position).getSource());
    }

    @Override
    public int getItemCount() {
        return model.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        TextView day, totalTested, totalIndividual, totalPositive, source;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            day = (TextView) itemView.findViewById(R.id.dayRv);
            totalTested = (TextView) itemView.findViewById(R.id.totalSampleRv);
            totalIndividual = (TextView) itemView.findViewById(R.id.totalIndividualsRv);
            totalPositive = (TextView) itemView.findViewById(R.id.totalPositivesRv);
            source = (TextView) itemView.findViewById(R.id.sourceRv);
        }
    }
}
