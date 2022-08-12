package com.csupporter.techwiz.presentation.view.fragment.main.nav;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.csupporter.techwiz.R;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.mct.components.baseui.BaseFragment;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link NavHealthyTrackingFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class NavHealthyTrackingFragment extends BaseFragment implements View.OnClickListener {

    private View view;
    private FloatingActionButton btnAddTrack;

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    public NavHealthyTrackingFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment HealthyTrackingFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static NavHealthyTrackingFragment newInstance(String param1, String param2) {
        NavHealthyTrackingFragment fragment = new NavHealthyTrackingFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        view = inflater.inflate(R.layout.fragment_healthy_tracking , container, false);
        return view;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        initView(view);
        eventClick();
    }

    private void eventClick() {
        btnAddTrack.setOnClickListener(this);
    }

    private void initView(View view) {
        btnAddTrack = view.findViewById(R.id.btn_add_track);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.btn_add_track:

                break;
        }
    }
}