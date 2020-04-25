package com.rax.c_zoner;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

public class QuickLinkAdapter extends RecyclerView.Adapter<QuickLinkAdapter.ViewHolder> {

    ItemClickListener listener;
    private View itemView;
    private List<NotificationModel> model;


    public QuickLinkAdapter(Context mContext, List<NotificationModel> testModel, ItemClickListener listener) {
        this.model = testModel;
        this.listener = listener;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_notification, parent, false);
        return new ViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        holder.day.setText(model.get(position).getTitle());
        holder.totalTested.setText(model.get(position).getLink());

    }

    @Override
    public int getItemCount() {
        return model.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        TextView day, totalTested;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            day = (TextView) itemView.findViewById(R.id.txt_notification_title);
            totalTested = (TextView) itemView.findViewById(R.id.txt_notification_link);
            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    listener.OnClick(getAdapterPosition());
                }
            });
        }
    }
}
