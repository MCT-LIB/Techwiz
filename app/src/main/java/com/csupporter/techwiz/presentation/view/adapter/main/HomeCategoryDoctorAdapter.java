package com.csupporter.techwiz.presentation.view.adapter.main;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.csupporter.techwiz.R;
import com.csupporter.techwiz.domain.model.CategoryDoctor;

import java.util.List;

import de.hdodenhof.circleimageview.CircleImageView;

public class HomeCategoryDoctorAdapter extends RecyclerView.Adapter<HomeCategoryDoctorAdapter.HomeCategoryDoctorViewHolder> {

    private List<CategoryDoctor> categoryDoctorList;



    public void setCategoryDoctorList(List<CategoryDoctor> categoryDoctorList) {
        this.categoryDoctorList = categoryDoctorList;
    }

    @NonNull
    @Override
    public HomeCategoryDoctorViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.nav_home_category_items, parent, false);
        return new HomeCategoryDoctorViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull HomeCategoryDoctorViewHolder holder, int position) {
        CategoryDoctor categoryDoctor = categoryDoctorList.get(position);
        holder.image.setImageResource(categoryDoctor.getImageResource());
        holder.name.setText(categoryDoctor.getNameType());
    }

    @Override
    public int getItemCount() {
        if(categoryDoctorList != null){
            return categoryDoctorList.size();
        }
        return 0;
    }

    public static class HomeCategoryDoctorViewHolder extends RecyclerView.ViewHolder {
        private final CircleImageView image;
        private final TextView name;

        public HomeCategoryDoctorViewHolder(@NonNull View itemView) {
            super(itemView);
            image = itemView.findViewById(R.id.category_doctor_image);
            name = itemView.findViewById(R.id.category_doctor_type);
        }
    }
}
