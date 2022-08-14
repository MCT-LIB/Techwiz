package com.csupporter.techwiz.presentation.view.adapter;

import android.annotation.SuppressLint;
import android.content.Context;
import android.graphics.Color;
import android.graphics.PorterDuff;
import android.graphics.drawable.ColorDrawable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.csupporter.techwiz.App;
import com.csupporter.techwiz.R;
import com.csupporter.techwiz.domain.model.Account;
import com.csupporter.techwiz.domain.model.HealthTracking;
import com.csupporter.techwiz.domain.model.MyDoctor;
import com.csupporter.techwiz.presentation.internalmodel.Departments;

import java.util.ArrayList;
import java.util.List;

import de.hdodenhof.circleimageview.CircleImageView;

public class DoctorListAdapter extends RecyclerView.Adapter<DoctorListAdapter.ViewHolder> {

    private List<Account> doctorList;
    private OnItemCLickListener mOnItemCLickListener;

    public DoctorListAdapter(OnItemCLickListener mOnItemCLickListener) {
        this.mOnItemCLickListener = mOnItemCLickListener;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.doctor_list_items, parent, false);
        return new ViewHolder(view);
    }

    @SuppressLint("NotifyDataSetChanged")
    public void setDoctorList(List<Account> doctorList) {
        this.doctorList = doctorList;
        notifyDataSetChanged();
    }

    public void deleteItemAccount(int pos) {
        notifyItemRemoved(pos);
        doctorList.remove(pos);
    }


    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        Account doctorModel = doctorList.get(position);
        holder.setData(doctorModel , position);
        holder.itemView.setOnClickListener(v -> mOnItemCLickListener.onItemClick(doctorModel));
    }

    @Override
    public int getItemCount() {
        if (doctorList != null) {
            return doctorList.size();
        }
        return 0;
    }


    public class ViewHolder extends RecyclerView.ViewHolder {

        private ImageView doctorAvatar;
        private TextView doctorName;
        private ImageView imgLike;
        private ImageView doctor_major;


        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            initView(itemView);

        }

        public void initView(View itemView) {
            doctorAvatar = itemView.findViewById(R.id.doctor_avatar);
            doctorName = itemView.findViewById(R.id.doctor_name);
            imgLike = itemView.findViewById(R.id.like_doctor);
            doctor_major = itemView.findViewById(R.id.doctor_major);
        }

        public void setData(Account doctorModel , int position) {

            Glide.with(App.getContext()).load(doctorModel.getAvatar())
                    .placeholder(R.drawable.ic_default_avatar)
                    .error(R.drawable.ic_default_avatar)
                    .into(doctorAvatar);

            doctorName.setText(doctorModel.getFirstName());

            int department = doctorModel.getDepartment();
            switch (department) {
                case 0:
                    doctor_major.setImageResource(R.drawable.dentist);
                    break;
                case 1:
                    doctor_major.setImageResource(R.drawable.pediatrician_doctor);
                    break;
                case 2:
                    doctor_major.setImageResource(R.drawable.cardiologist_doctor);
                    break;
                case 3:
                    doctor_major.setImageResource(R.drawable.beauty_surgeon);
                    break;
                case 4:
                    doctor_major.setImageResource(R.drawable.psycho_doctor);
                    break;
                case 5:
                    doctor_major.setImageResource(R.drawable.obstetrical);
                    break;
            }

            imgLike.setOnClickListener(view -> {

                mOnItemCLickListener.onClickLike(doctorModel, position);
            });

        }
    }

    public interface OnItemCLickListener {
        void onItemClick(Account account );

        void onClickLike(Account account, int position);

    }
}
