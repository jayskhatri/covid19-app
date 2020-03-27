package com.jayskhatri.covid19_track.adapter;

import android.util.Log;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;
import androidx.lifecycle.Lifecycle;
import androidx.viewpager2.adapter.FragmentStateAdapter;

import com.jayskhatri.covid19_track.ui.home.HomeFragment;
import com.jayskhatri.covid19_track.ui.home.NationalFragment;
import com.jayskhatri.covid19_track.ui.home.StatewiseFragment;

import java.util.ArrayList;

public class TabPagerAdapter extends FragmentStateAdapter {

    private String TAG = TabPagerAdapter.class.getSimpleName();

    private ArrayList<Fragment> arrayList = new ArrayList<>();

    public TabPagerAdapter(@NonNull FragmentActivity fragmentActivity) {
        super(fragmentActivity);
    }

    @NonNull
    @Override
    public Fragment createFragment(int position) {
        switch (position) {
            case 0:
                return new NationalFragment();
            case 1:
                return new StatewiseFragment();
        }
        return new NationalFragment();
    }

    @Override
    public int getItemCount() {
        return arrayList.size();
    }
}
