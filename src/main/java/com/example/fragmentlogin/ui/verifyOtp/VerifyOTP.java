package com.example.fragmentlogin.ui.verifyOtp;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.appcompat.widget.AppCompatButton;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import android.text.Html;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.example.fragmentlogin.model.DataClass;
import com.example.fragmentlogin.ui.home.HomeFragment;
import com.example.fragmentlogin.R;
import com.example.fragmentlogin.base.BaseFragment;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.FirebaseException;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.PhoneAuthCredential;
import com.google.firebase.auth.PhoneAuthProvider;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.ValueEventListener;

import java.util.concurrent.TimeUnit;

import static com.example.fragmentlogin.utils.Constants.IsFromLogin;


public class VerifyOTP extends BaseFragment {
    TextView resend, numberShow;
    AppCompatButton verify;
    EditText otpView;
    String verificationBySystem;
    ProgressBar progressBar;
    PhoneAuthProvider.ForceResendingToken mResendToken;

       @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_verify_o_t_p, container, false);
        resend = v.findViewById(R.id.OtpNotReceivedTV);
        String text = "<font color=#FF000000>OTP not received yet?</font> <font color=#EC6940>Resend OTP</font>";
        resend.setText(Html.fromHtml(text));
        verify = v.findViewById(R.id.verifyBtn);
        otpView = v.findViewById(R.id.otp_view);
        numberShow = v.findViewById(R.id.tv3);
        String phone_number = getArguments().getString("phoneNumber");
        numberShow.setText("+91 " + phone_number);
        SendVerificationCodeToUser(phone_number);
        progressBar=v.findViewById(R.id.pbar);
        progressBar.setTag("please wait...");
        resend.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                resendVerificationCode (phone_number,mResendToken);
                progressBar.setVisibility(View.VISIBLE);
            }
        });
        verify.setOnClickListener(new View.OnClickListener() {
            String otpCode = otpView.getText().toString();

            @Override
            public void onClick(View v) {
                if (otpCode == null) {

                    Toast.makeText(getContext(), "enter OTP", Toast.LENGTH_SHORT).show();
                } else {
                    verifyCode(otpCode);
                    progressBar.setVisibility(View.GONE);
                    moveToNext(new HomeFragment());
                }
            }
        });
        return v;
    }

    private void resendVerificationCode(String phoneNumber,
                                        PhoneAuthProvider.ForceResendingToken token) {
        PhoneAuthProvider.getInstance().verifyPhoneNumber(
                "+91"+phoneNumber,        // Phone number to verify
                60,                 // Timeout duration
                TimeUnit.SECONDS,   // Unit of timeout
                getActivity(),               // Activity (for callback binding)
                mcallbacks,         // OnVerificationStateChangedCallbacks
                token);             // ForceResendingToken from callbacks
    }

    private void SendVerificationCodeToUser(String PhoneNumber) {
        PhoneAuthProvider.getInstance().verifyPhoneNumber("+91" + PhoneNumber, 60,
                TimeUnit.SECONDS, getActivity(), mcallbacks);

    }

    private PhoneAuthProvider.OnVerificationStateChangedCallbacks mcallbacks = new PhoneAuthProvider.OnVerificationStateChangedCallbacks() {
        @Override
        public void onVerificationCompleted(@NonNull PhoneAuthCredential phoneAuthCredential) {
            String OTP = phoneAuthCredential.getSmsCode();
            progressBar.setVisibility(View.GONE);
            verifyCode(OTP);

        }

        @Override
        public void onCodeSent(@NonNull String s, @NonNull PhoneAuthProvider.ForceResendingToken forceResendingToken) {
            super.onCodeSent(s, forceResendingToken);
            verificationBySystem = s;
            mResendToken=forceResendingToken;

        }

        @Override
        public void onVerificationFailed(@NonNull FirebaseException e) {
            Toast.makeText(getActivity(), "failed to verify" + e.getMessage(), Toast.LENGTH_SHORT).show();
        }
    };

    private void verifyCode(String otp) {
        progressBar.setVisibility(View.GONE);
        PhoneAuthCredential credential = PhoneAuthProvider.getCredential(verificationBySystem, otp);
        signInTheUserByCredintials(credential);
    }

    private void signInTheUserByCredintials(PhoneAuthCredential credential) {

        FirebaseAuth firebaseAuth = FirebaseAuth.getInstance();
        firebaseAuth.signInWithCredential(credential).addOnCompleteListener(getContainerActivity(), new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                if (task.isSuccessful()) {
                    String id = task.getResult().getUser().getUid();
                    checkIfExist(id);
                } else {
                    Toast.makeText(getActivity(), task.getException().getMessage(), Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

    private void checkIfExist(String id) {
        getDbRef().child(id).addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                if (snapshot.getValue() == null) {

                    if (getArguments().getBoolean(IsFromLogin)){
                        Toast.makeText(getContainerActivity(),"User already not exits",Toast.LENGTH_LONG).show();
                    }
                    else {
                        String firstName = getArguments().getString("firstName");
                        String lastName = getArguments().getString("lastName");
                        String city = getArguments().getString("city");
                        String phone = getArguments().getString("phoneNumber");
                        DataClass dataClass = new DataClass(firstName, lastName, city, phone);
                        getDbRef().child(id).setValue(dataClass);
                        moveToNext(new HomeFragment());
                    }

                } else {
                    moveToNext(new HomeFragment());

                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }
    public void moveToNext(Fragment fragment){
        FragmentManager fragmentManager = getActivity().getSupportFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        fragmentTransaction.replace(R.id.frame_layout_id, fragment);
        fragmentTransaction.addToBackStack(null);
        fragmentTransaction.commit();
        getActivity().getFragmentManager().popBackStack();
    }

}