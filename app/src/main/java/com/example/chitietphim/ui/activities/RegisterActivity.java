package com.example.chitietphim.ui.activities;

import android.content.Intent;
import android.os.Bundle;
import android.text.method.HideReturnsTransformationMethod;
import android.text.method.PasswordTransformationMethod;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.example.chitietphim.R;
import com.example.chitietphim.data.repo.urls;

import java.util.HashMap;
import java.util.Map;

public class RegisterActivity extends AppCompatActivity {
    TextView Login;
    Boolean passwordVisible1, passwordVisible2;
    private EditText etId, etName, etPhone, etEmail, etPassword, etReenterPassword;
    private String id, name, phone, email, password, reenterPassword;

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        if (item.getItemId() == android.R.id.home) {
            this.finish();
            return true;
        }
        return true;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setHomeButtonEnabled(true);
        getSupportActionBar().setHomeAsUpIndicator(R.drawable.ic_arrow_back);
        setTitle("Đăng Ký");
        etId = findViewById(R.id.EtRegId);
        etName = findViewById(R.id.EtRegName);
        etPhone = findViewById(R.id.EtRegPhone);
        etEmail = findViewById(R.id.EtRegEmail);
        etPassword = findViewById(R.id.EtRegPass);
        etPassword.setOnTouchListener((v, event) -> {
            final int Right = 2;
            if (event.getAction() == MotionEvent.ACTION_UP) {
                if (event.getRawX() >= etPassword.getRight() - etPassword.getCompoundDrawables()[Right].getBounds().width()) {
                    int selection = etPassword.getSelectionEnd();
                    if (passwordVisible1) {
                        etPassword.setCompoundDrawablesRelativeWithIntrinsicBounds(0, 0, R.drawable.ic_baseline_visibility_off_24, 0);
                        etPassword.setTransformationMethod(PasswordTransformationMethod.getInstance());
                        passwordVisible1 = false;
                    } else {
                        etPassword.setCompoundDrawablesRelativeWithIntrinsicBounds(0, 0, R.drawable.ic_baseline_visibility_off_24, 0);
                        etPassword.setTransformationMethod(HideReturnsTransformationMethod.getInstance());
                        passwordVisible1 = true;
                    }
                    etPassword.setSelection(selection);
                    return true;
                }
            }
            return false;
        });
        etReenterPassword = findViewById(R.id.EtRegPass2);
        etReenterPassword.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                final int Right = 2;
                if (event.getAction() == MotionEvent.ACTION_UP) {
                    if (event.getRawX() >= etReenterPassword.getRight() - etReenterPassword.getCompoundDrawables()[Right].getBounds().width()) {
                        int selection = etReenterPassword.getSelectionEnd();
                        if (passwordVisible2) {
                            etReenterPassword.setCompoundDrawablesRelativeWithIntrinsicBounds(0, 0, R.drawable.ic_baseline_visibility_off_24, 0);
                            etReenterPassword.setTransformationMethod(PasswordTransformationMethod.getInstance());
                            passwordVisible2 = false;
                        } else {
                            etReenterPassword.setCompoundDrawablesRelativeWithIntrinsicBounds(0, 0, R.drawable.ic_baseline_visibility_off_24, 0);
                            etReenterPassword.setTransformationMethod(HideReturnsTransformationMethod.getInstance());
                            passwordVisible2 = true;
                        }
                        etReenterPassword.setSelection(selection);
                        return true;
                    }
                }
                return false;
            }
        });
        id = name = phone = email = password = reenterPassword = "";

        Login = (TextView) findViewById(R.id.tv_login);
        Login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(RegisterActivity.this, LoginActivity.class));
            }
        });
    }

    public void save(View view) {
        id = etId.getText().toString().trim();
        name = etName.getText().toString().trim();
        email = etEmail.getText().toString().trim();
        phone = etPhone.getText().toString().trim();
        password = etPassword.getText().toString().trim();
        reenterPassword = etReenterPassword.getText().toString().trim();
        if (!password.equals(reenterPassword)) {
            Toast.makeText(this, "Mật khẩu không trùng khớp", Toast.LENGTH_SHORT).show();
        } else if (!name.equals("") && !email.equals("") && !password.equals("")) {
            StringRequest stringRequest = new StringRequest(Request.Method.POST, urls.urlRegister.getStringURL(), response -> {
                if (response.equals("success")) {
                    Toast.makeText(getApplicationContext(), "Đăng ký thành công!!", Toast.LENGTH_SHORT).show();
                    startActivity(new Intent(RegisterActivity.this, LoginActivity.class));
                } else if (response.equals("failure")) {
                    Toast.makeText(getApplicationContext(), "Đã xảy ra sự cố!!", Toast.LENGTH_SHORT).show();
                }
            }, error -> Toast.makeText(getApplicationContext(), error.toString().trim(), Toast.LENGTH_SHORT).show()) {
                @Override
                protected Map<String, String> getParams() throws AuthFailureError {
                    Map<String, String> data = new HashMap<>();
                    data.put("id", id);
                    data.put("name", name);
                    data.put("phone", phone);
                    data.put("email", email);
                    data.put("password", password);
                    return data;
                }
            };
            RequestQueue requestQueue = Volley.newRequestQueue(getApplicationContext());
            requestQueue.add(stringRequest);
        }
    }
}