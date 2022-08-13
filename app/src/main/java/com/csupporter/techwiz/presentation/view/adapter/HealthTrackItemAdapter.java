package com.csupporter.techwiz.presentation.view.adapter;

import android.annotation.SuppressLint;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.csupporter.techwiz.R;
import com.csupporter.techwiz.domain.model.HealthTracking;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

public class HealthTrackItemAdapter extends RecyclerView.Adapter<HealthTrackItemAdapter.ViewHolder> {

    private List<HealthTracking> trackingList;
    private OnCLickItemTrack mOnCLickItemTrack;

    public HealthTrackItemAdapter(OnCLickItemTrack mOnCLickItemTrack) {
        this.mOnCLickItemTrack = mOnCLickItemTrack;
    }

    @SuppressLint("NotifyDataSetChanged")
    public void setTrackingList(List<HealthTracking> trackingList) {
        this.trackingList = trackingList;
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_healthy_track, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        HealthTracking healthTracking = trackingList.get(position);
        holder.setData(healthTracking);
        holder.itemView.setOnClickListener(v -> mOnCLickItemTrack.onClickItem(healthTracking));
    }

    @Override
    public int getItemCount() {
        if (trackingList != null){
            return trackingList.size();
        }
        return 0;
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        private TextView tvDateCreated, tvHeartBeat, tvBloodSugar, tvBloodOPressure, tvTimeCreated;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            initView(itemView);
        }

        private void setData(HealthTracking data) {
            Date date = new Date();
            @SuppressLint("SimpleDateFormat") SimpleDateFormat dateFormatter = new SimpleDateFormat("dd MMM");
            @SuppressLint("SimpleDateFormat") SimpleDateFormat timeFormatter = new SimpleDateFormat("hh:mm:ss");
            String strDate = dateFormatter.format(date);
            String strTime = timeFormatter.format(date);
            tvDateCreated.setText(strDate);
            tvTimeCreated.setText(strTime);
            tvHeartBeat.setText(String.valueOf(data.getHeartbeat()));
            tvBloodSugar.setText(String.valueOf(data.getBloodSugar()));
            tvBloodOPressure.setText(String.valueOf(data.getBloodPressure()));
        }

        private void initView(View itemView) {
            tvDateCreated = itemView.findViewById(R.id.tv_date_created);
            tvTimeCreated = itemView.findViewById(R.id.tv_time_created);
            tvHeartBeat = itemView.findViewById(R.id.tv_heart_beat);
            tvBloodSugar = itemView.findViewById(R.id.tv_blood_sugar);
            tvBloodOPressure = itemView.findViewById(R.id.tv_blood_pressure);
        }
    }

    public interface OnCLickItemTrack {
        void onClickItem(HealthTracking healthTracking);
    }
}
