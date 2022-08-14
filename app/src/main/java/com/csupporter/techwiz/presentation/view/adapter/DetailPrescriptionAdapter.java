package com.csupporter.techwiz.presentation.view.adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.csupporter.techwiz.App;
import com.csupporter.techwiz.R;
import com.csupporter.techwiz.domain.model.PrescriptionDetail;

import java.util.List;

public class DetailPrescriptionAdapter extends RecyclerView.Adapter<DetailPrescriptionAdapter.ViewHolder> {

    private List<PrescriptionDetail> prescriptionDetailList;
    private OnItemClickListener mOnItemClickListener;

    public DetailPrescriptionAdapter(List<PrescriptionDetail> prescriptionDetailList, OnItemClickListener mOnItemClickListener) {
        this.prescriptionDetailList = prescriptionDetailList;
        this.mOnItemClickListener = mOnItemClickListener;
    }

    @NonNull
    @Override
    public DetailPrescriptionAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_prescription_detail, parent,false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull DetailPrescriptionAdapter.ViewHolder holder, int position) {
        PrescriptionDetail modelDetail = prescriptionDetailList.get(position);
        holder.setData(modelDetail);
        holder.itemView.setOnClickListener(v -> mOnItemClickListener.onItemCLick(modelDetail, holder.getAdapterPosition()));
    }

    @Override
    public int getItemCount() {
        if (prescriptionDetailList != null){
            return prescriptionDetailList.size();
        }
        return 0;
    }

    public
    class ViewHolder extends RecyclerView.ViewHolder {
        private ImageView imvDescription;
        private TextView tvNameDescription, tvTimePerDay, tvTimePerWeek, tvQuantity;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            initView(itemView);
        }

        private void initView(View model) {
            imvDescription = model.findViewById(R.id.imv_description);
            tvNameDescription = model.findViewById(R.id.tv_name_medicine);
            tvTimePerDay = model.findViewById(R.id.tv_time_per_day);
            tvTimePerWeek = model.findViewById(R.id.tv_time_per_week);
            tvQuantity = model.findViewById(R.id.tv_quantity);
        }

        public void setData(PrescriptionDetail model) {
            Glide.with(App.getContext()).load(model.getImageUrl())
                    .diskCacheStrategy(DiskCacheStrategy.ALL)
                    .into(imvDescription);
            tvNameDescription.setText(String.valueOf(model.getMedicineName()));
            tvTimePerDay.setText(String.valueOf(model.getTimePerADay()));
            tvTimePerWeek.setText(String.valueOf(model.getTimePerWeek()));
            tvQuantity.setText(String.valueOf(model.getQuantity()));
        }

        ;

    }

    public interface OnItemClickListener {
        void onItemCLick(PrescriptionDetail pressDetail, int pos);
    }
}
