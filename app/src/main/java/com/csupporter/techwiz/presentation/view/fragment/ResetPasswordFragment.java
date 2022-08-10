package com.csupporter.techwiz.presentation.view.fragment;

import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.csupporter.techwiz.R;
import com.mct.components.baseui.BaseFragment;

public class ResetPasswordFragment extends BaseFragment implements View.OnClickListener{
    EditText edtEnterOldPw, edtEnterNewPw, edtReenterNewPw;
    Button btnConfirm;

    View view;

    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
        getActivity().getWindow().setBackgroundDrawableResource(R.drawable.forgot_password_background);
    }

    @Override
    public void onResume() {
        super.onResume();
        getActivity().getWindow().setBackgroundDrawableResource(R.drawable.forgot_password_background);
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_reset_password, container, false);
        return view;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        intiView(view);
        eventClick();

    }

    private void eventClick() {
        btnConfirm.setOnClickListener(this);
    }

    private void intiView(View view) {
        edtEnterOldPw  = view.findViewById(R.id.edt_enter_old_pw);
        edtEnterNewPw = view.findViewById(R.id.edt_enter_new_pw);
        edtReenterNewPw = view.findViewById(R.id.edt_reenter_new_pw);
        btnConfirm = view.findViewById(R.id.btn_confirm);
    }

    @Override
    public void onClick(View v) {
        if (v.getId() == R.id.btn_confirm) {
            Toast.makeText(getContext(), "onclick confirm", Toast.LENGTH_SHORT).show();
        }
    }
}
