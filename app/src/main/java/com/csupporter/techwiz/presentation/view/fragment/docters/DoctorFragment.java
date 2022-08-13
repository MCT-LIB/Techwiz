package com.csupporter.techwiz.presentation.view.fragment.docters;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.viewpager2.widget.ViewPager2;

import com.csupporter.techwiz.R;
import com.csupporter.techwiz.presentation.view.adapter.DoctorAdapter;
import com.google.android.material.badge.BadgeDrawable;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.mct.components.baseui.BaseFragment;

public class DoctorFragment extends BaseFragment implements View.OnClickListener {

    private View view;
    private ViewPager2 viewPager2;
    private FloatingActionButton btn_appointment;
    private BottomNavigationView bottomNavigationView;
    private DoctorAdapter doctorAdapter;


    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_doctor, container, false);
        if (getActivity() == null) {
            return null;
        }
        doctorAdapter = new DoctorAdapter(getActivity());
        return view;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        initView(view);
        eventClick();
        setDataViewPager2();
    }

    @SuppressLint("NonConstantResourceId")
    private void setDataViewPager2() {
        BadgeDrawable badgeDrawable = bottomNavigationView.getOrCreateBadge(R.id.item_home);
        badgeDrawable.setVisible(true);
        badgeDrawable.setNumber(3);
        viewPager2.setAdapter(doctorAdapter);
        viewPager2.setUserInputEnabled(false);
        bottomNavigationView.setOnItemSelectedListener(item -> {
            switch (item.getItemId()) {
                case R.id.item_home:
                    viewPager2.setCurrentItem(0, false);
                    break;
                case R.id.item_history_medical:
                    viewPager2.setCurrentItem(1, false);
                    break;
                case R.id.item_profile:
                    viewPager2.setCurrentItem(2, false);
                    break;
            }
            return true;
        });

        viewPager2.registerOnPageChangeCallback(new ViewPager2.OnPageChangeCallback() {
            @Override
            public void onPageSelected(int position) {
                super.onPageSelected(position);

                switch (position) {
                    case 0:
                        bottomNavigationView.getMenu().findItem(R.id.item_home).setChecked(true);
                        break;
                    case 1:
                        bottomNavigationView.getMenu().findItem(R.id.item_history_medical).setChecked(true);
                        break;
                    case 2:
                        bottomNavigationView.getMenu().findItem(R.id.item_profile).setChecked(true);
                        break;
                }
            }
        });
    }

    private void eventClick() {
    }

    private void initView(View view) {
        viewPager2 = view.findViewById(R.id.dt_view_pager2);
        bottomNavigationView = view.findViewById(R.id.bottom_navigation_view);
        btn_appointment = view.findViewById(R.id.btn_appointment);
    }

    @Override
    public void onClick(View v) {

    }
}
