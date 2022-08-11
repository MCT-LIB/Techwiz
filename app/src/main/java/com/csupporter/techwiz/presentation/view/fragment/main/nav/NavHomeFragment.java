package com.csupporter.techwiz.presentation.view.fragment.main.nav;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.TextView;

import com.csupporter.techwiz.R;
import com.csupporter.techwiz.presentation.view.adapter.main.HomeCategoryDoctorAdapter;


public class NavHomeFragment extends Fragment {

    private EditText txtSearch;
    private TextView txtMyAppointment;
    private RecyclerView categoryDoctor;
    private HomeCategoryDoctorAdapter homeCategoryDoctorAdapter;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_nav_home, container, false);

        homeCategoryDoctorAdapter = new HomeCategoryDoctorAdapter(getActivity());
        init(view);
        setDataCategoryDoctor();
        return view;
    }

    private void init(View view) {
        categoryDoctor = view.findViewById(R.id.category_doctor_list);
        txtSearch = view.findViewById(R.id.search_bar);
        txtMyAppointment = view.findViewById(R.id.nav_home_text);
    }

    private void setDataCategoryDoctor() {
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getActivity(), RecyclerView.HORIZONTAL, false);
        categoryDoctor.setLayoutManager(linearLayoutManager);
        homeCategoryDoctorAdapter.setCategoryDoctorList();
        categoryDoctor.setAdapter(homeCategoryDoctorAdapter);
    }

    private void eventClickItem(){
        
    }
}