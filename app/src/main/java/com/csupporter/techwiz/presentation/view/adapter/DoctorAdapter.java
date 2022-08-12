package com.csupporter.techwiz.presentation.view.adapter;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;
import androidx.viewpager2.adapter.FragmentStateAdapter;

import com.csupporter.techwiz.presentation.view.fragment.docters.nav.DtCreateAppoimentFragment;
import com.csupporter.techwiz.presentation.view.fragment.docters.nav.DtHistoryMedicalFragment;
import com.csupporter.techwiz.presentation.view.fragment.docters.nav.DtHomeFragment;
import com.csupporter.techwiz.presentation.view.fragment.docters.nav.DtProfileFragment;
import com.csupporter.techwiz.presentation.view.fragment.docters.nav.DtNotifyFragment;

public class DoctorAdapter extends FragmentStateAdapter {

    public DoctorAdapter(@NonNull FragmentActivity fragmentActivity) {
        super(fragmentActivity);
    }

    @NonNull
    @Override
    public Fragment createFragment(int position) {
        switch (position){
            case 1:
                return new DtNotifyFragment();
            case 2:
                return new DtCreateAppoimentFragment();
            case 3:
                return new DtHistoryMedicalFragment();
            case 4:
                return new DtProfileFragment();
            case 0:
            default:
                return new DtHomeFragment();
        }
    }

    @Override
    public int getItemCount() {
        return 5;
    }
}
