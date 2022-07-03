package com.chaliseminas.pickme;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.SearchView;

import android.content.Intent;
import android.location.Address;
import android.location.Geocoder;
import android.location.LocationListener;
import android.os.Build;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.EditText;
import android.widget.TextView;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;

import java.io.IOException;
import java.util.List;

public class AddAddressActivity extends AppCompatActivity {

    TextView tvCancel;

    EditText etStart, etDestination;
    AutoCompleteTextView start;
    List<Address> addressList;
    List<String> addressName = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_address);

        tvCancel = findViewById(R.id.tvCancel);
        start = findViewById(R.id.etStart);
        etDestination = findViewById(R.id.etDestination);

        start.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                Geocoder geocoder = new Geocoder(AddAddressActivity.this);
                try {
                    addressList = geocoder.getFromLocationName(charSequence.toString(), 1);
                } catch (IOException e) {
                    e.printStackTrace();
                }
                Log.e("adsfadf", addressList.toString());
                Log.e("adsfadfasdf", charSequence.toString());
                if (!addressList.isEmpty()) {
                    addressName.add(addressList.get(0).getFeatureName());
                    Address address = addressList.get(0);
                    address.getLatitude();
                    address.getLongitude();
                    Log.e("adsfadfasdf", addressList.get(0).toString());
                }
            }

            @Override
            public void afterTextChanged(Editable editable) {
            }
        });

        ArrayAdapter adapter = new ArrayAdapter(AddAddressActivity.this, android.R.layout.select_dialog_item, addressName);
        start.setThreshold(1);
        start.setAdapter(adapter);

        tvCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(AddAddressActivity.this, HomeActivity.class));
                finish();
            }
        });
    }
}