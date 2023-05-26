package com.example.chitietphim;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.text.method.HideReturnsTransformationMethod;
import android.text.method.PasswordTransformationMethod;
import android.view.MotionEvent;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import java.util.HashMap;
import java.util.Map;

public class RegisterActivity extends AppCompatActivity {
    TextView Login;
    Boolean passwordVisible1, passwordVisible2;
    private EditText etId, etName, etPhone, etEmail, etPassword, etReenterPassword;
    private String id, name, phone, email, password, reenterPassword;

    private String URL = "http://172.17.21.45:8080/Demoservice/register.php";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
        setTitle("Đăng Ký");
        etId = findViewById(R.id.EtRegId);
        etName = findViewById(R.id.EtRegName);
        etPhone = findViewById(R.id.EtRegPhone);
        etEmail = findViewById(R.id.EtRegEmail);
        etPassword = findViewById(R.id.EtRegPass);
        etPassword.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event){
                final int Right=2;
                if(event.getAction()==MotionEvent.ACTION_UP){
                    if(event.getRawX()>=etPassword.getRight()-etPassword.getCompoundDrawables()[Right].getBounds().width()){
                        int selection = etPassword.getSelectionEnd();
                        if(passwordVisible1){
                            etPassword.setCompoundDrawablesRelativeWithIntrinsicBounds(0,0, R.drawable.ic_baseline_visibility_off_24, 0);
                            etPassword.setTransformationMethod(PasswordTransformationMethod.getInstance());
                            passwordVisible1=false;
                        }else{
                            etPassword.setCompoundDrawablesRelativeWithIntrinsicBounds(0,0, R.drawable.ic_baseline_visibility_off_24, 0);
                            etPassword.setTransformationMethod(HideReturnsTransformationMethod.getInstance());
                            passwordVisible1=true;
                        }
                        etPassword.setSelection(selection);
                        return true;
                    }
                }
                return false;
            }
        });
        etReenterPassword = findViewById(R.id.EtRegPass2);
        etReenterPassword.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event){
                final int Right=2;
                if(event.getAction()==MotionEvent.ACTION_UP){
                    if(event.getRawX()>=etReenterPassword.getRight()-etReenterPassword.getCompoundDrawables()[Right].getBounds().width()){
                        int selection = etReenterPassword.getSelectionEnd();
                        if(passwordVisible2){
                            etReenterPassword.setCompoundDrawablesRelativeWithIntrinsicBounds(0,0, R.drawable.ic_baseline_visibility_off_24, 0);
                            etReenterPassword.setTransformationMethod(PasswordTransformationMethod.getInstance());
                            passwordVisible2=false;
                        }else{
                            etReenterPassword.setCompoundDrawablesRelativeWithIntrinsicBounds(0,0, R.drawable.ic_baseline_visibility_off_24, 0);
                            etReenterPassword.setTransformationMethod(HideReturnsTransformationMethod.getInstance());
                            passwordVisible2=true;
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
        Login.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v){
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
        if(!password.equals(reenterPassword)){
            Toast.makeText(this, "Mật khẩu không trùng khớp", Toast.LENGTH_SHORT).show();
        }
        else if(!name.equals("") && !email.equals("") && !password.equals("")){
            StringRequest stringRequest = new StringRequest(Request.Method.POST, URL, new Response.Listener<String>() {
                @Override
                public void onResponse(String response) {
                    if (response.equals("success")) {
                        Toast.makeText(getApplicationContext(), "Đăng ký thành công!!", Toast.LENGTH_SHORT).show();
                        startActivity(new Intent(RegisterActivity.this, LoginActivity.class));
                    } else if (response.equals("failure")) {
                        Toast.makeText(getApplicationContext(), "Đã xảy ra sự cố!!", Toast.LENGTH_SHORT).show();                  }
                }
            }, new Response.ErrorListener() {
                @Override
                public void onErrorResponse(VolleyError error) {
                    Toast.makeText(getApplicationContext(), error.toString().trim(), Toast.LENGTH_SHORT).show();
                }
            }){
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