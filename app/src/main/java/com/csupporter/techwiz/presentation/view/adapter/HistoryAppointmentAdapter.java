package com.csupporter.techwiz.presentation.view.adapter;

import android.annotation.SuppressLint;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.csupporter.techwiz.R;
import com.csupporter.techwiz.domain.model.Account;
import com.csupporter.techwiz.presentation.internalmodel.AppointmentDetail;

import java.text.DateFormat;
import java.util.Date;
import java.util.List;

public class HistoryAppointmentAdapter extends RecyclerView.Adapter<HistoryAppointmentAdapter.HistoryAppointmentViewHolder> {

    private List<AppointmentDetail> appointmentDetails;

    @SuppressLint("NotifyDataSetChanged")
    public void setAppointmentDetails(List<AppointmentDetail> appointmentDetails) {
        this.appointmentDetails = appointmentDetails;
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public HistoryAppointmentViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.history_appointment_items, parent, false);
        return new HistoryAppointmentViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull HistoryAppointmentViewHolder holder, int position) {
        AppointmentDetail detail = appointmentDetails.get(position);
        if (detail == null) {
            return;
        }
        Account account = detail.getAcc();
        holder.tvName.setText(account.getFullName());
        holder.tvAddress.setText(account.getLocation());
        holder.tvTime.setText(DateFormat.getDateTimeInstance().format(new Date(detail.getAppointment().getTime())));
        Glide.with(holder.itemView.getContext()).load(account.getAvatar())
                .placeholder(R.drawable.ic_default_avatar)
                .error(R.drawable.ic_default_avatar)
                .into(holder.imgAvatar);
        int imgRes = 0;
        switch (detail.getAppointment().getStatus()) {
            case 0:
                imgRes = R.drawable.ic_wait_circle;
                break;
            case 1:
                imgRes = R.drawable.ic_schedule_circle;
                break;
            case 2:
            case 3:
            case 4:
                imgRes = R.drawable.ic_cancel_circle;
                break;
            case 5:
            case 6:
                imgRes = R.drawable.ic_check_circle;
                break;
        }
        holder.imgStatus.setImageResource(imgRes);
        holder.btnSetAgain.setOnClickListener(v -> {

        });
    }

    @Override
    public int getItemCount() {
        if (appointmentDetails != null) {
            return appointmentDetails.size();
        }
        return 0;
    }

    static class HistoryAppointmentViewHolder extends RecyclerView.ViewHolder {

        ImageView imgAvatar, imgStatus;
        TextView tvName, tvAddress, tvTime;
        Button btnSetAgain;

        public HistoryAppointmentViewHolder(@NonNull View itemView) {
            super(itemView);
            imgAvatar = itemView.findViewById(R.id.img_avatar);
            imgStatus = itemView.findViewById(R.id.img_status);
            tvName = itemView.findViewById(R.id.tv_name);
            tvAddress = itemView.findViewById(R.id.tv_address);
            tvTime = itemView.findViewById(R.id.tv_time);
            btnSetAgain = itemView.findViewById(R.id.btn_set_again);
        }
    }

    public interface OnSetAgainClickListener {
        void onClicked(AppointmentDetail detail, int position);
    }
}
