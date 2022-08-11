package com.csupporter.techwiz.presentation.view.adapter;

import android.graphics.PorterDuff;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.RecyclerView;

import com.csupporter.techwiz.App;
import com.csupporter.techwiz.R;
import com.csupporter.techwiz.presentation.internalmodel.Departments;

import de.hdodenhof.circleimageview.CircleImageView;

public class HomeCategoryDoctorAdapter extends RecyclerView.Adapter<HomeCategoryDoctorAdapter.HomeCategoryDoctorViewHolder> {

    private Departments[] departments;

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

        switch (type) {
            case "Dentist":
                holder.layout.getBackground().setColorFilter(ContextCompat.getColor(App.getContext(), R.color.blue), PorterDuff.Mode.MULTIPLY);
                break;
            case "Pediatrician":
                holder.layout.getBackground().setColorFilter(ContextCompat.getColor(App.getContext(), R.color.green_light), PorterDuff.Mode.MULTIPLY);
                break;
            case "Cardiologist":
                holder.layout.getBackground().setColorFilter(ContextCompat.getColor(App.getContext(), R.color.red_slight), PorterDuff.Mode.MULTIPLY);
                break;
            case "Beauty surgeon":
                holder.layout.getBackground().setColorFilter(ContextCompat.getColor(App.getContext(), R.color.purple_slight), PorterDuff.Mode.MULTIPLY);
                break;
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
