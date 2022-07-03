package com.chaliseminas.pickme;

import android.content.Context;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.AttributeSet;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.LinearLayout;

import androidx.annotation.Nullable;

public class OtpEditText extends LinearLayout implements View.OnFocusChangeListener, TextWatcher, View.OnKeyListener{

    private EditText etOtp1;
    private EditText etOtp2;
    private EditText etOtp3;
    private EditText etOtp4;
    private EditText etOtp5;
    private EditText etOtp6;
    private EditText etCurrentOtp;

    public OtpEditText(Context context) {
        super(context);
        initialize(null);
    }

    public OtpEditText(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        initialize(attrs);
    }

    public OtpEditText(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        initialize(attrs);
    }

    private void initialize(AttributeSet attrs) {
        LayoutInflater inflater = (LayoutInflater) getContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        inflater.inflate(R.layout.edit_text_otp, this);

        etOtp1 = findViewById(R.id.etOtp1);
        etOtp2 = findViewById(R.id.etOtp2);
        etOtp3 = findViewById(R.id.etOtp3);
        etOtp4 = findViewById(R.id.etOtp4);
        etOtp5 = findViewById(R.id.etOtp5);
        etOtp6 = findViewById(R.id.etOtp6);
        
        initFocusListener();
        initTextChangeListener();
        initKeyListener();
    }

    private void initFocusListener() {
        etOtp1.setOnFocusChangeListener(this);
        etOtp2.setOnFocusChangeListener(this);
        etOtp3.setOnFocusChangeListener(this);
        etOtp4.setOnFocusChangeListener(this);
        etOtp5.setOnFocusChangeListener(this);
        etOtp6.setOnFocusChangeListener(this);
    }

    private void initTextChangeListener() {
        etOtp1.addTextChangedListener(this);
        etOtp2.addTextChangedListener(this);
        etOtp3.addTextChangedListener(this);
        etOtp4.addTextChangedListener(this);
        etOtp5.addTextChangedListener(this);
        etOtp6.addTextChangedListener(this);
    }

    private void initKeyListener() {
        etOtp1.setOnKeyListener(this);
        etOtp2.setOnKeyListener(this);
        etOtp3.setOnKeyListener(this);
        etOtp4.setOnKeyListener(this);
        etOtp5.setOnKeyListener(this);
        etOtp6.setOnKeyListener(this);
    }

    @Override
    public void onFocusChange(View view, boolean b) {
        etCurrentOtp = (EditText) view;
        etCurrentOtp.setSelection(etCurrentOtp.getText().length());
    }

    @Override
    public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {
        
    }

    @Override
    public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
        if (etCurrentOtp.getText().length() >= 1 && etCurrentOtp != etOtp6)
            etCurrentOtp.focusSearch(View.FOCUS_RIGHT).requestFocus();
        else if (etCurrentOtp.getText().length() >= 1 && etCurrentOtp == etOtp6) {
            InputMethodManager inputMethodManager = (InputMethodManager) getContext()
                    .getSystemService(Context.INPUT_METHOD_SERVICE);

            if (inputMethodManager != null)
                inputMethodManager.hideSoftInputFromWindow(getWindowToken(), 0);

        } else {
            String currentValue = etCurrentOtp.getText().toString();
            if (currentValue.length() <= 0 && etCurrentOtp.getSelectionStart() <= 0)
                etCurrentOtp.focusSearch(View.FOCUS_LEFT).requestFocus();
        }
    }

    public String getOtp() {
        return makeOtp();
    }

    public void setOtp(String otp) {
        if (otp.length() != 6) return;
        etOtp1.setText(String.valueOf(otp.charAt(0)));
        etOtp2.setText(String.valueOf(otp.charAt(1)));
        etOtp3.setText(String.valueOf(otp.charAt(2)));
        etOtp4.setText(String.valueOf(otp.charAt(3)));
        etOtp5.setText(String.valueOf(otp.charAt(4)));
        etOtp6.setText(String.valueOf(otp.charAt(5)));
    }

    private String makeOtp() {
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append(etOtp1.getText().toString());
        stringBuilder.append(etOtp2.getText().toString());
        stringBuilder.append(etOtp3.getText().toString());
        stringBuilder.append(etOtp4.getText().toString());
        stringBuilder.append(etOtp5.getText().toString());
        stringBuilder.append(etOtp6.getText().toString());
        return stringBuilder.toString();
    }

    public boolean isValid() {
        return makeOtp().length() == 6;
    }

    public EditText getCurrentFocused() {
        return etCurrentOtp;
    }

    @Override
    public void afterTextChanged(Editable editable) {

    }

    @Override
    public boolean onKey(View view, int i, KeyEvent keyEvent) {
        if (keyEvent.getAction() == KeyEvent.ACTION_DOWN) {
            int id = view.getId();
            switch (id) {
                case R.id.etOtp1:
                    return isKeyDel(etOtp1, i);
                case R.id.etOtp2:
                    return isKeyDel(etOtp2, i);
                case R.id.etOtp3:
                    return isKeyDel(etOtp3, i);
                case R.id.etOtp4:
                    return isKeyDel(etOtp4, i);
                case R.id.etOtp5:
                    return isKeyDel(etOtp5, i);
                case R.id.etOtp6:
                    return isKeyDel(etOtp6, i);
                default:
                    return false;
            }

        }
        return false;
    }

    private boolean isKeyDel(EditText etDigit, int keyCode) {
        if (keyCode == KeyEvent.KEYCODE_DEL) {
            etDigit.setText(null);
            return true;
        }
        return false;
    }
}
