package com.chaliseminas.pickme;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.FirebaseException;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.PhoneAuthCredential;
import com.google.firebase.auth.PhoneAuthOptions;
import com.google.firebase.auth.PhoneAuthProvider;

import java.util.concurrent.TimeUnit;

public class OtpActivity extends AppCompatActivity {

    ImageView _ivBack;
    TextView _tvDone;
    ProgressBar _progressBar;

    EditText _etOtp;

    FirebaseAuth firebaseAuth;
    String verificationId;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_otp);

        String phone = getIntent().getStringExtra("phoneNumber");
        firebaseAuth = FirebaseAuth.getInstance();
        sendVerificationCode(phone);

        _etOtp = findViewById(R.id.etOtp);
        _ivBack = findViewById(R.id.ivBack);
        _tvDone = findViewById(R.id.tvDone);
        _progressBar = findViewById(R.id.progressBar);

        _ivBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(OtpActivity.this, MainActivity.class));
                finish();
            }
        });

        _tvDone.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                _tvDone.setVisibility(View.GONE);
                _progressBar.setVisibility(View.VISIBLE);

                String code = _etOtp.getText().toString().trim();
                if (code.isEmpty())
                    _etOtp.setError("Enter otp code.");
                else {
                    verifyCode(code);
                }
            }
        });
    }

    private void sendVerificationCode(String number) {
        PhoneAuthOptions options =
                PhoneAuthOptions.newBuilder(firebaseAuth)
                        .setPhoneNumber(number)
                        .setTimeout(60L, TimeUnit.SECONDS)
                        .setActivity(this)
                        .setCallbacks(mCallBack)
                        .build();
        PhoneAuthProvider.verifyPhoneNumber(options);
    }

    private PhoneAuthProvider.OnVerificationStateChangedCallbacks
            mCallBack = new PhoneAuthProvider.OnVerificationStateChangedCallbacks() {

        @Override
        public void onCodeSent(String s, PhoneAuthProvider.ForceResendingToken forceResendingToken) {
            super.onCodeSent(s, forceResendingToken);
            verificationId = s;
        }

        @Override
        public void onVerificationCompleted(PhoneAuthCredential phoneAuthCredential) {
            final String code = phoneAuthCredential.getSmsCode();
            if (code != null) {
                _etOtp.setText(code);
                verifyCode(code);
            }
        }

        @Override
        public void onVerificationFailed(FirebaseException e) {
            Toast.makeText(OtpActivity.this, e.getMessage(), Toast.LENGTH_LONG).show();
        }
    };

    private void verifyCode(String code) {
        PhoneAuthCredential credential = PhoneAuthProvider.getCredential(verificationId, code);
        signInWithPhone(credential);
    }

    private void signInWithPhone(PhoneAuthCredential credential) {
        firebaseAuth.signInWithCredential(credential)
                .addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            Intent intent = new Intent(OtpActivity.this, HomeActivity.class);
                            startActivity(intent);
                            finish();
                        } else {
                            Toast.makeText(OtpActivity.this, task.getException().getMessage(), Toast.LENGTH_LONG).show();
                        }
                        _progressBar.setVisibility(View.GONE);
                        _tvDone.setVisibility(View.VISIBLE);
                    }
                });
    }
}