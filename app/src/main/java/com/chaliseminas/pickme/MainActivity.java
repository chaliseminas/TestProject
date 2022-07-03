package com.chaliseminas.pickme;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.LocationManager;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.hbb20.CountryCodePicker;

public class MainActivity extends AppCompatActivity {

    TextView _tvDone;
    EditText _etPhoneNumber;
    CountryCodePicker _etCountryCode;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(MainActivity.this,
                    new String[]{Manifest.permission.ACCESS_FINE_LOCATION}, 1);
        }
        _tvDone = findViewById(R.id.tvDone);
        _etPhoneNumber = findViewById(R.id.etPhone);
        _etCountryCode = findViewById(R.id.etCountryCode);

        _tvDone.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                _handleClick();
            }
        });
    }

    private void _handleClick() {

        String phone = _etPhoneNumber.getText().toString().trim();
        String countryCode = _etCountryCode.getSelectedCountryCode();

        if (phone.isEmpty())
            _etPhoneNumber.setError("Please enter you phone number.");
        else {
            Intent intent = new Intent(MainActivity.this, OtpActivity.class);
            intent.putExtra("phoneNumber", "+" + countryCode + phone);
            startActivity(intent);
        }

    }
}