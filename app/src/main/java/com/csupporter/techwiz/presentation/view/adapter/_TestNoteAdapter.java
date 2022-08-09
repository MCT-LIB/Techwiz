package com.csupporter.techwiz.presentation.view.adapter;

import android.annotation.SuppressLint;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.csupporter.techwiz.R;
import com.csupporter.techwiz.domain.model._Note;

import java.util.List;

public class _TestNoteAdapter extends RecyclerView.Adapter<_TestNoteAdapter.NoteViewHolder> {

    private List<_Note> notes;
    private OnItemClickListener mOnItemClickListener;

    public void setOnItemClickListener(OnItemClickListener mOnItemClickListener) {
        this.mOnItemClickListener = mOnItemClickListener;
    }

    @SuppressLint("NotifyDataSetChanged")
    public void setNotes(List<_Note> notes) {
        this.notes = notes;
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public NoteViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout._test_item_note, parent, false);
        return new NoteViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull NoteViewHolder holder, int position) {
        _Note note = notes.get(position);
        if (note == null) {
            return;
        }
        holder.setNote(note);
        holder.setListener(note, position, mOnItemClickListener);
    }

    @Override
    public int getItemCount() {
        if (notes != null) {
            return notes.size();
        }
        return 0;
    }

    static class NoteViewHolder extends RecyclerView.ViewHolder {

        private final ImageView imgNote;
        private final TextView tvTitle, tvContent;
        private final Button btnUpdate, btnDelete;

        public NoteViewHolder(@NonNull View itemView) {
            super(itemView);
            imgNote = itemView.findViewById(R.id.img_note);
            tvTitle = itemView.findViewById(R.id.tv_title);
            tvContent = itemView.findViewById(R.id.tv_content);
            btnUpdate = itemView.findViewById(R.id.btn_update);
            btnDelete = itemView.findViewById(R.id.btn_delete);
        }

        public void setNote(@NonNull _Note note) {
            tvTitle.setText(note.getTitle());
            tvContent.setText(note.getContent());
            Glide.with(itemView).load(note.getUrl())
                    .error(R.drawable._ic_placeholder)
                    .placeholder(R.drawable._ic_placeholder)
                    .into(imgNote);
        }

        public void setListener(_Note note, int position, OnItemClickListener listener) {
            itemView.setOnClickListener(v -> {
                if (listener != null) listener.onItemClicked(note);
            });
            btnUpdate.setOnClickListener(v -> {
                if (listener != null) listener.onClickedUpdate(note, position);
            });
            btnDelete.setOnClickListener(v -> {
                if (listener != null) listener.onClickedDelete(note, position);
            });
        }

    }

    public interface OnItemClickListener {
        void onItemClicked(_Note note);

        void onClickedUpdate(_Note note, int position);

        void onClickedDelete(_Note note, int position);
    }
}
