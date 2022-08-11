package com.csupporter.techwiz.presentation.view.adapter.main;

import android.content.Context;
import android.graphics.Color;
import android.graphics.PorterDuff;
import android.graphics.drawable.ColorDrawable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.RecyclerView;

import com.csupporter.techwiz.R;
import com.csupporter.techwiz.presentation.internalmodel.Departments;

import java.util.List;

import de.hdodenhof.circleimageview.CircleImageView;

public class HomeCategoryDoctorAdapter extends RecyclerView.Adapter<HomeCategoryDoctorAdapter.HomeCategoryDoctorViewHolder> {

    private Departments[] departments;
    private Context context;

    public HomeCategoryDoctorAdapter(Context context) {
        this.context = context;
    }

    public void setCategoryDoctorList() {
        this.departments = Departments.values();
    }

    @NonNull
    @Override
    public HomeCategoryDoctorViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.nav_home_category_items, parent, false);
        return new HomeCategoryDoctorViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull HomeCategoryDoctorViewHolder holder, int position) {

        Departments depart = departments[position];

        String type = depart.getCategory();

        if (type.equals("Dentist")) {
            holder.layout.getBackground().setColorFilter(ContextCompat.getColor(context, R.color.blue), PorterDuff.Mode.MULTIPLY);
        } else if (type.equals("Pediatrician")) {
            holder.layout.getBackground().setColorFilter(ContextCompat.getColor(context, R.color.green_light), PorterDuff.Mode.MULTIPLY);
        } else if (type.equals("Cardiologist")) {
            holder.layout.getBackground().setColorFilter(ContextCompat.getColor(context, R.color.red_slight), PorterDuff.Mode.MULTIPLY);
        } else if (type.equals("Beauty surgeon")) {
            holder.layout.getBackground().setColorFilter(ContextCompat.getColor(context, R.color.purple_slight), PorterDuff.Mode.MULTIPLY);
        }

        holder.image.setImageResource(depart.getResourceFile());
        holder.name.setText(depart.getCategory());
    }

    @Override
    public int getItemCount() {
        return departments.length;
    }

    public static class HomeCategoryDoctorViewHolder extends RecyclerView.ViewHolder {
        private final LinearLayout layout;
        private final CircleImageView image;
        private final TextView name;

        public HomeCategoryDoctorViewHolder(@NonNull View itemView) {
            super(itemView);
            image = itemView.findViewById(R.id.category_doctor_image);
            name = itemView.findViewById(R.id.category_doctor_type);
            layout = itemView.findViewById(R.id.nav_home_category_items_layout);
        }
    }
}
