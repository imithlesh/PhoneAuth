package com.example.fragmentlogin.ui.register;

import android.app.AlertDialog;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;
import com.example.fragmentlogin.R;
import com.example.fragmentlogin.base.BaseFragment;
import com.example.fragmentlogin.ui.verifyOtp.VerifyOTP;
import static com.example.fragmentlogin.utils.Constants.IsFromLogin;


public class RegisterFragment extends BaseFragment {
    Button submit;
    EditText fName, lName, cityName, phoneNumber;
    public static RegisterFragment newInstance() {
        return new RegisterFragment();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_register, container, false);
        fName = v.findViewById(R.id.firstNameId);
        lName = v.findViewById(R.id.secondNameId);
        cityName = v.findViewById(R.id.cityNameId);
        phoneNumber = v.findViewById(R.id.phoneID);
        submit = v.findViewById(R.id.submitBtn);
        submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String firstName = fName.getText().toString().trim();
                String lastName = lName.getText().toString().trim();
                String city = cityName.getText().toString().trim();
                String Phone = phoneNumber.getText().toString().trim();
                if (firstName.isEmpty() || lastName.isEmpty() || city.isEmpty() || Phone.isEmpty()) {
                    Toast.makeText(getActivity(), "all fields are mandatory", Toast.LENGTH_SHORT).show();

                }
                else {
                    Fragment fragment = new VerifyOTP();
                    FragmentManager fragmentManager = getActivity().getSupportFragmentManager();
                    FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
                    Bundle args = new Bundle();
                    args.putString("phoneNumber", Phone);
                    args.putString("firstName", firstName);
                    args.putString("lastName", lastName);
                    args.putString("city", city);
                    args.putBoolean(IsFromLogin,false);
                    fragment.setArguments(args);
                    fragmentTransaction.replace(R.id.frame_layout_id, fragment);
                    fragmentTransaction.commit();
                    getActivity().getFragmentManager().popBackStack();
                }

            }
        });

        return v;
    }

    public RegisterFragment() {
    }
}