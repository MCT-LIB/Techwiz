package com.csupporter.techwiz.presentation.view.adapter;

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

import com.csupporter.techwiz.App;
import com.csupporter.techwiz.R;
import com.csupporter.techwiz.domain.model.Account;

import java.util.List;

import de.hdodenhof.circleimageview.CircleImageView;

public class DoctorListAdapter extends BaseAdapter {

    private LayoutInflater layoutInflater;
    private List<Account> doctorList;

    private int click = 0;

    public void setDataToDoctorList(List<Account> doctorList) {
        this.doctorList = doctorList;
        notifyDataSetChanged();
    }

    @Override
    public int getCount() {
        if (doctorList != null) {
            return doctorList.size();
        }
        return 0;
    }

    @Override
    public Object getItem(int i) {
        return null;
    }

    @Override
    public long getItemId(int i) {
        return 0;
    }

    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {
        DoctorListViewHolder viewHolder;
        Account account = doctorList.get(i);
        if (layoutInflater == null) {
            layoutInflater = (LayoutInflater) App.getContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        }
        if (view == null) {

            view = layoutInflater.inflate(R.layout.doctor_list_items, viewGroup, false);
            viewHolder = new DoctorListViewHolder();
            viewHolder.btnLike = view.findViewById(R.id.like_doctor);
            viewHolder.avatar = view.findViewById(R.id.doctor_avatar);
            viewHolder.name = view.findViewById(R.id.doctor_name);
            viewHolder.major = view.findViewById(R.id.doctor_major);

            view.setTag(viewHolder);
        } else {
            viewHolder = (DoctorListViewHolder) view.getTag();
        }

        viewHolder.name.setText(account.getLastName());


        return view;
    }

    private static class DoctorListViewHolder {
        ImageView btnLike;
        CircleImageView avatar;
        TextView name;
        ImageView major;
    }
}
