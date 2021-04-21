package com.example.fragmentlogin.ui.login;

import android.os.Bundle;

import androidx.appcompat.widget.AppCompatButton;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import android.text.Html;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.fragmentlogin.MainActivity;
import com.example.fragmentlogin.R;
import com.example.fragmentlogin.base.BaseActivity;
import com.example.fragmentlogin.ui.home.HomeFragment;
import com.example.fragmentlogin.ui.register.RegisterFragment;
import com.example.fragmentlogin.ui.verifyOtp.VerifyOTP;

public class LoginFragment extends Fragment {

    EditText RegisteredNumber;
    AppCompatButton loginBtn;
    TextView newUser;

    public LoginFragment() {
    }

    public static LoginFragment newInstance() {
        return new LoginFragment();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_login, container, false);
        RegisteredNumber = view.findViewById(R.id.phoneLoginET);
        loginBtn = view.findViewById(R.id.LoginBtn);
        newUser = view.findViewById(R.id.newUser);
        String text = "<font color=#FF000000>New User? </font> <font color=#EC6940>click here to register</font>";
        newUser.setText(Html.fromHtml(text));

        newUser.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Fragment fragment = new RegisterFragment();
                FragmentManager fragmentManager = getActivity().getSupportFragmentManager();
                FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
                fragmentTransaction.replace(R.id.frame_layout_id, fragment);
                fragmentTransaction.commit();
                fragmentTransaction.addToBackStack(null);
//                getActivity().getFragmentManager().popBackStack();
            }
        });

        loginBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                String phoneNumberLogin = RegisteredNumber.getText().toString().trim();
                if (phoneNumberLogin.length() != 10)
                {
                    Toast.makeText(getActivity(), "enter valid number", Toast.LENGTH_SHORT).show();
                }
                else {
                    Fragment fragment = new HomeFragment();
                    FragmentManager fragmentManager = getActivity().getSupportFragmentManager();
                    FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
                    fragmentTransaction.replace(R.id.frame_layout_id, fragment);
                    Bundle args = new Bundle();
                    args.putString("phoneNumber", phoneNumberLogin);
                    fragment.setArguments(args);
                    fragmentTransaction.commit();
                    getActivity().getFragmentManager().popBackStack();
                }

            }
        });
        return view;
    }
}