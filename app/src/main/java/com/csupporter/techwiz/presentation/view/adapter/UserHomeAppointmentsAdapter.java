package com.csupporter.techwiz.presentation.view.adapter.main;

import android.view.View;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.csupporter.techwiz.R;

import org.w3c.dom.Text;

import java.util.List;

import de.hdodenhof.circleimageview.CircleImageView;

public class UserHomeAppointmentsAdapter {

    public static class UserHomeAppointments_ViewHolder extends RecyclerView.ViewHolder {
        private final CircleImageView avatar;
        private final TextView name;
        private final TextView address;
        private final TextView time;
        public UserHomeAppointments_ViewHolder(@NonNull View itemView) {
            super(itemView);
            avatar = itemView.findViewById(R.id.appointments_avatar_doctor);
            name  = itemView.findViewById(R.id.appointments_name_doctor);
            address = itemView.findViewById(R.id.appointments_address_doctor);
            time = itemView.findViewById(R.id.appointments_time_doctor);
        }
    }
}
