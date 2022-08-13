package com.csupporter.techwiz.presentation.view.adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.csupporter.techwiz.App;
import com.csupporter.techwiz.R;
import com.csupporter.techwiz.di.DataInjection;
import com.csupporter.techwiz.domain.model.Appointment;
import com.csupporter.techwiz.presentation.presenter.MainViewCallBack;
import com.mct.components.toast.ToastUtils;

import org.w3c.dom.Text;

import java.text.DateFormat;
import java.util.Date;
import java.util.List;

import de.hdodenhof.circleimageview.CircleImageView;

public class UserHomeAppointmentsAdapter extends RecyclerView.Adapter<UserHomeAppointmentsAdapter.UserHomeAppointments_ViewHolder> {

    private List<Appointment> appointmentList;

    public void setDataToAppointmentList(List<Appointment> appointmentList) {
        this.appointmentList = appointmentList;
    }

    @NonNull
    @Override
    public UserHomeAppointments_ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.home_appointment_items, parent, false);
        return new UserHomeAppointmentsAdapter.UserHomeAppointments_ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull UserHomeAppointments_ViewHolder holder, int position) {
        Appointment appointment = appointmentList.get(position);
        if (appointment == null) {
            return;
        }
        DataInjection.provideRepository().account.findAccountById(appointment.getDoctorId(), account -> {
            if (account.getAvatar() == null) {
                holder.avatar.setImageResource(R.drawable.ic_baseline_person_pin_24);
            } else {
                Glide.with(App.getContext()).load(account.getAvatar())
                        .diskCacheStrategy(DiskCacheStrategy.ALL)
                        .into(holder.avatar);
            }
        }, throwable -> {
        });
        holder.address.setText(appointment.getLocation());
        holder.time.setText(DateFormat.getDateTimeInstance().format(new Date(appointment.getTime())));
    }


    @Override
    public int getItemCount() {
        if (appointmentList != null) {
            return appointmentList.size();
        }
        return 0;
    }

    public static class UserHomeAppointments_ViewHolder extends RecyclerView.ViewHolder {
        private final CircleImageView avatar;
        private final TextView name;
        private final TextView address;
        private final TextView time;

        public UserHomeAppointments_ViewHolder(@NonNull View itemView) {
            super(itemView);
            avatar = itemView.findViewById(R.id.appointments_avatar_doctor);
            name = itemView.findViewById(R.id.appointments_name_doctor);
            address = itemView.findViewById(R.id.appointments_address_doctor);
            time = itemView.findViewById(R.id.appointments_time_doctor);
        }
    }
}
