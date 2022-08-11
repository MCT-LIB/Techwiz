package com.csupporter.techwiz.presentation.view.fragment.main;

import android.os.Bundle;

import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.viewpager2.widget.ViewPager2;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.csupporter.techwiz.R;
import com.csupporter.techwiz.domain.model.CategoryDoctor;
import com.csupporter.techwiz.presentation.view.adapter.main.HomeCategoryDoctorAdapter;
import com.csupporter.techwiz.presentation.view.adapter.main.MainAdapter;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.mct.components.baseui.BaseFragment;

import java.util.ArrayList;
import java.util.List;


public class MainFragment extends BaseFragment {

    private ViewPager2 viewPager2;
    private BottomNavigationView bottomNavigationView;
    private FloatingActionButton btnMakeAppointment;
    private RecyclerView categoryDoctor;

    private MainAdapter mainAdapter;
    private HomeCategoryDoctorAdapter homeCategoryDoctorAdapter;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_main, container, false);

        if (getActivity() == null) {
            return null;
        }

        mainAdapter = new MainAdapter(getActivity());
        homeCategoryDoctorAdapter = new HomeCategoryDoctorAdapter();

        init(view);
        setDataViewPager2();
        return view;
    }

    private void init(View view) {
        viewPager2 = view.findViewById(R.id.view_pager2);
        bottomNavigationView = view.findViewById(R.id.bottom_navigation_view);
        btnMakeAppointment = view.findViewById(R.id.btn_appointment);
        categoryDoctor = view.findViewById(R.id.nav_home_category_doctor);

    }

    private void setDataViewPager2() {
        viewPager2.setAdapter(mainAdapter);
        bottomNavigationView.setOnItemSelectedListener(item -> {
            switch (item.getItemId()) {
                case R.id.user_home:
                    viewPager2.setCurrentItem(0, false);
                    break;
                case R.id.user_search:
                    viewPager2.setCurrentItem(1, false);
                    break;
                case R.id.user_info:
                    viewPager2.setCurrentItem(3, false);
                    break;
                case R.id.user_history:
                    viewPager2.setCurrentItem(4, false);
                    break;
            }
            return true;
        });

        btnMakeAppointment.setOnClickListener(view -> {
            viewPager2.setCurrentItem(2, false);
        });

        viewPager2.registerOnPageChangeCallback(new ViewPager2.OnPageChangeCallback() {
            @Override
            public void onPageSelected(int position) {
                super.onPageSelected(position);
                switch (position) {
                    case 0:
                        bottomNavigationView.getMenu().findItem(R.id.user_home).setChecked(true);
                        break;
                    case 1:
                        bottomNavigationView.getMenu().findItem(R.id.user_search).setChecked(true);
                        break;
                    case 3:
                        bottomNavigationView.getMenu().findItem(R.id.user_history).setChecked(true);
                        break;
                    case 4:
                        bottomNavigationView.getMenu().findItem(R.id.user_info).setChecked(true);
                        break;
                }
            }
        });
    }

    private void setDataCategoryDoctor(){
        List<CategoryDoctor> listCategory = new ArrayList<>();
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getActivity(),RecyclerView.HORIZONTAL,false);
        categoryDoctor.setLayoutManager(linearLayoutManager);

        homeCategoryDoctorAdapter.setCategoryDoctorList(listCategory);
        categoryDoctor.setAdapter(homeCategoryDoctorAdapter);
    }
    private void setDataToListCategoryDoctor(List<CategoryDoctor> listCategory){
        //listCategory.add();
    }

}