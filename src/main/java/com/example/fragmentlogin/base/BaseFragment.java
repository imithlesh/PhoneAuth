package com.example.fragmentlogin.base;

import android.content.Context;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import com.google.firebase.database.DatabaseReference;

public class BaseFragment extends Fragment {
    private BaseActivity containerActivity;

    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
        containerActivity = (BaseActivity) context;
    }

    public DatabaseReference getDbRef() {
        return containerActivity.getDbRef();
    }
    public BaseActivity getContainerActivity(){
        return containerActivity;
    }
}
