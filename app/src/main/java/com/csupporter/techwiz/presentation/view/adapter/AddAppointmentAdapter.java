package com.csupporter.techwiz.presentation.view.adapter;


import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.widget.AppCompatButton;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.csupporter.techwiz.App;
import com.csupporter.techwiz.R;
import com.csupporter.techwiz.domain.model.Account;

import java.util.List;

import de.hdodenhof.circleimageview.CircleImageView;

public class AddAppointmentAdapter extends RecyclerView.Adapter<AddAppointmentAdapter.AddAppointmentViewHolder> {

    private List<Account> doctorList;

    public AddAppointmentAdapter(List<Account> doctorList) {
        this.doctorList = doctorList;
    }

    @NonNull
    @Override
    public AddAppointmentViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.my_doctors_items, parent, false);
        return new AddAppointmentAdapter.AddAppointmentViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull AddAppointmentViewHolder holder, int position) {
        Account doctor = doctorList.get(position);
        Glide.with(App.getContext()).load(doctor.getAvatar())
                .placeholder(R.drawable.ic_default_avatar)
                .error(R.drawable.ic_default_avatar)
                .into(holder.doctorImage);
        holder.tvNameDoctor.setText(doctor.getLastName()+doctor.getFirstName());
    }

    @Override
    public int getItemCount() {
        if(doctorList != null){
            return doctorList.size();
        }
        return 0;
    }

    public static class AddAppointmentViewHolder extends RecyclerView.ViewHolder {

        private final CircleImageView doctorImage;
        private final TextView tvNameDoctor;
        private final TextView tvLocation;
        private final ImageView img_major;
        private final AppCompatButton btnBookAppointment;

        public AddAppointmentViewHolder(@NonNull View itemView) {
            super(itemView);
            doctorImage = itemView.findViewById(R.id.img_doctor_avatar);
            tvNameDoctor = itemView.findViewById(R.id.tv_name_doctor);
            tvLocation = itemView.findViewById(R.id.tv_doctor_location);
            img_major = itemView.findViewById(R.id.img_doctor_major);
            btnBookAppointment = itemView.findViewById(R.id.btn_book_appointment);
        }
    }
}
