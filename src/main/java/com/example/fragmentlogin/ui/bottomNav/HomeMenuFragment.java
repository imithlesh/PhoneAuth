package com.example.fragmentlogin.ui.bottomNav;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.example.fragmentlogin.MyAdapter;
import com.example.fragmentlogin.R;
import com.example.fragmentlogin.base.BaseFragment;
import com.example.fragmentlogin.model.DataClass;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.ValueEventListener;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Set;

public class HomeMenuFragment extends BaseFragment {
    RecyclerView recyclerView;
    ArrayList<DataClass> dataClassArrayList = new ArrayList<>();
    MyAdapter adapterClass = new MyAdapter(dataClassArrayList, getContext());

    public HomeMenuFragment() {
        // Required empty public constructor
    }

    public static HomeMenuFragment newInstance() {
        HomeMenuFragment fragment = new HomeMenuFragment();
        return new HomeMenuFragment();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_home_menu, container, false);
        recyclerView = view.findViewById(R.id.recViewHomeMenu);
        LinearLayoutManager layoutManager = new LinearLayoutManager(getContext(), LinearLayoutManager.VERTICAL, false);
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setAdapter(adapterClass);

        ValueEventListener postListener = new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                /*for (DataSnapshot data : snapshot.getChildren()){
                    dataClassArrayList.add(data.getValue(DataClass.class));
                }*/
                dataClassArrayList.clear();
                Set<String> keys = ((HashMap<String, Object>) snapshot.getValue()).keySet();
                for (String data : keys) {
                    try {
                        HashMap<String,String> obj = ((HashMap<String, HashMap<String,String>>) snapshot.getValue()).get(data);
                       /* Gson g = new Gson();
                        DataClass p = g.fromJson(obj.toString(), DataClass.class);
                        p.setUid(data);*/
                        DataClass p = new DataClass();
                        p.setUid(data);
                        p.setFname(obj.get("fname"));
                        p.setLname(obj.get("lname"));
                        p.setCity(obj.get("city"));
                        p.setAbout(obj.get("about"));
                        dataClassArrayList.add(p);
                    }
                    catch (Exception e){
                        e.printStackTrace();
                    }

                }
                Log.e("DASDASd", "" + dataClassArrayList.size());
                adapterClass.setDataClassArrayList(dataClassArrayList);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                Toast.makeText(getContext(), "error", Toast.LENGTH_SHORT).show();
            }

        };
        getDbRef().addValueEventListener(postListener);
        return view;
    }

}