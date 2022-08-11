package com.csupporter.techwiz.presentation.view.dialog;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;

import com.csupporter.techwiz.R;
import com.mct.components.baseui.BaseOverlayDialog;

public class ConfirmDialog extends BaseOverlayDialog implements View.OnClickListener{

    private View view;
    private ShowConfirmDialog mShowConfirmDialog;
    private Button btnCancel, btnConfirm;

    public ConfirmDialog(@NonNull Context context, ShowConfirmDialog mShowConfirmDialog) {
        super(context);
        this.mShowConfirmDialog = mShowConfirmDialog;
    }

    @NonNull
    @Override
    protected AlertDialog.Builder onCreateDialog() {
        view = LayoutInflater.from(context).inflate(R.layout.dialog_cofirm_logout, null);

        return new AlertDialog.Builder(context)
                .setCancelable(false)
                .setView(view);
    }

    @Override
    protected void onDialogCreated(@NonNull AlertDialog dialog) {
        intiView(view);
        eventClick();
    }

    @Override
    protected int getCornerRadius() {
        return 16;
    }

    private void eventClick() {
        btnCancel.setOnClickListener(this);
        btnConfirm.setOnClickListener(this);
    }

    private void intiView(View view) {
        btnCancel = view.findViewById(R.id.btn_no);
        btnConfirm = view.findViewById(R.id.btn_yes);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.btn_no:
                mShowConfirmDialog.onCancel();
                break;
            case R.id.btn_yes:
                mShowConfirmDialog.onConfirm();
                break;
        }
    }

    public interface ShowConfirmDialog {
        void onConfirm();

        void onCancel();
    }

}
