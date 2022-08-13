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

import com.csupporter.techwiz.App;
import com.csupporter.techwiz.R;
import com.csupporter.techwiz.domain.model.Account;

import java.util.List;

import de.hdodenhof.circleimageview.CircleImageView;

public class DoctorListAdapter extends RecyclerView.Adapter<DoctorListAdapter.ViewHolder> {

    private List<Account> doctorList;
    private OnItemCLickListener mOnItemCLickListener;

    public DoctorListAdapter(OnItemCLickListener mOnItemCLickListener) {
        this.doctorList = doctorList;
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

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        Account doctorModel = doctorList.get(position);
        holder.setData(doctorModel);
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

        ImageView doctorAvatar;
        TextView doctorName;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            initView(itemView);

        }

        public void initView(View itemView) {
            doctorAvatar = itemView.findViewById(R.id.doctor_avatar);
            doctorName = itemView.findViewById(R.id.doctor_name);
        }

        public void setData(Account doctorModel) {
            doctorAvatar.setImageResource(R.mipmap.app_launcher_round);
            doctorName.setText(doctorModel.getFirstName());
        }
    }


    public interface OnItemCLickListener {
        void onItemClick(Account account);
    }
}
