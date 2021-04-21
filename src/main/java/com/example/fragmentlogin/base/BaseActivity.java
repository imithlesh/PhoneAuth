package com.example.fragmentlogin.base;

import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class BaseActivity extends AppCompatActivity {

    DatabaseReference dbInstance = null;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        dbInstance = FirebaseDatabase.getInstance().getReference();
    }
    public DatabaseReference getDbRef() {
        return dbInstance.child("UserData");
    }
}
