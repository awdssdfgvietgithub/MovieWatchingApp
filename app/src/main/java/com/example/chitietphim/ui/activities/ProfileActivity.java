package com.example.chitietphim.ui.activities;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.widget.ListView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.Volley;
import com.example.chitietphim.R;
import com.example.chitietphim.data.adapter.AdapterProfile;
import com.example.chitietphim.data.model.ClassProfile;
import com.example.chitietphim.data.repo.urls;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

public class ProfileActivity extends AppCompatActivity {
    ListView listViewpf;
    ArrayList<ClassProfile> arrayListpf;
    AdapterProfile adapterProfile;
    String makh = "";

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                this.finish();
                return true;
        }
        return true;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setHomeButtonEnabled(true);
        getSupportActionBar().setHomeAsUpIndicator(R.drawable.ic_arrow_back);
        setTitle("Thông Tin Cá Nhân");
        listViewpf = (ListView) findViewById(R.id.lsvprofile);

        arrayListpf = new ArrayList<>();
        Intent intent = getIntent();
        if (intent != null) {
            String idmakh = intent.getStringExtra("userid3");
            makh = idmakh;
            GetData(urls.urlKhachHangByMaKH.getStringURL(), makh);
        }
    }

    private void GetData(String url, String ma) {
        url += "?maKhachHang=" + ma;
        RequestQueue requestQueue = Volley.newRequestQueue(this);
        JsonArrayRequest jsonArrayRequest = new JsonArrayRequest(Request.Method.GET, url, null, response -> {
            for (int i = 0; i < response.length(); i++) {
                try {
                    JSONObject object = response.getJSONObject(i);
                    arrayListpf.add(new ClassProfile(
                            object.getString("maKhachHang"),
                            object.getString("tenKhachHang"),
                            object.getString("SDT"),
                            object.getString("email"),
                            object.getString("matKhau")
                    ));
                } catch (JSONException e) {
                    e.printStackTrace();
                }
                AdapterProfile adapter = new AdapterProfile(ProfileActivity.this, R.layout.custom_profile_listview, arrayListpf);
                listViewpf.setAdapter(adapter);
            }
        },
                error -> Toast.makeText(ProfileActivity.this, "Lỗi!" + error.toString(), Toast.LENGTH_LONG).show()
        ) {
        };
        requestQueue.add(jsonArrayRequest);
    }
}
