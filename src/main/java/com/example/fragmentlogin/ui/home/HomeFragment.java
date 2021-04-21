package com.example.fragmentlogin.ui.home;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;

import com.example.fragmentlogin.R;
import com.example.fragmentlogin.ui.bottomNav.HomeMenuFragment;
import com.google.android.material.bottomnavigation.BottomNavigationView;


public class HomeFragment extends Fragment {

    BottomNavigationView bottomNavigationView;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_home, container, false);
        bottomNavigationView = view.findViewById(R.id.bottomNav);
        bottomNavigationView.getMenu().findItem(R.id.home).setChecked(true);
        bottomNavigationView.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                Fragment fragment = new Fragment();
                switch (item.getItemId()) {
                    case R.id.home:
                        fragment = HomeMenuFragment.newInstance();
                        break;
                    case R.id.profile:
//                        fragment = ProfileMenuFragment.newInstance();
                        break;
                    case R.id.settings:
//                        fragment = SettingsMenuFragments.newInstance();
                        break;

                }
                FragmentManager fragmentManager = getActivity().getSupportFragmentManager();
                FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
                fragmentTransaction.replace(R.id.frameLayout2, fragment);
                fragmentTransaction.commit();
                return true;
            }
        });
        return view;
    }

    public HomeFragment() {
        // Required empty public constructor
    }

}