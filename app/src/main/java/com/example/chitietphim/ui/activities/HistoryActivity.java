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
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.Volley;
import com.example.chitietphim.R;
import com.example.chitietphim.data.adapter.AdapterLS;
import com.example.chitietphim.data.model.ClassLS;
import com.example.chitietphim.data.repo.urls;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

public class HistoryActivity extends AppCompatActivity {


    ListView listViewls;
    ArrayList<ClassLS> arrayListLS;
    AdapterLS adapterLS;
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
        setContentView(R.layout.activity_history);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setHomeButtonEnabled(true);
        getSupportActionBar().setHomeAsUpIndicator(R.drawable.ic_arrow_back);
        setTitle("Lịch Sử Xem Phim");
        listViewls = (ListView) findViewById(R.id.lsv);

        arrayListLS = new ArrayList<>();
        Intent intent = getIntent();
        if (intent != null) {
            String idmakh = intent.getStringExtra("userid2");
            makh = idmakh;
            GetData(urls.urlLichSuByMaKH.getStringURL(), makh);
        }
    }

    private void GetData(String url, String ma) {
        url += "?maKhachHang=" + ma;
        RequestQueue requestQueue = Volley.newRequestQueue(this);
        JsonArrayRequest jsonArrayRequest = new JsonArrayRequest(Request.Method.GET, url, null, new Response.Listener<JSONArray>() {
            @Override
            public void onResponse(JSONArray response) {
                for (int i = 0; i < response.length(); i++) {
                    try {
                        JSONObject object = response.getJSONObject(i);
                        arrayListLS.add(new ClassLS(
                                object.getString("ngayGioXem"),
                                object.getString("maKhachHang"),
                                object.getString("maPhim"),
                                object.getString("soDiemDanhGia")
                        ));
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                    AdapterLS adapter = new AdapterLS(HistoryActivity.this, R.layout.custom_history_listview, arrayListLS);
                    listViewls.setAdapter(adapter);
                }
            }
        },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Toast.makeText(HistoryActivity.this, "Lỗi!" + error.toString(), Toast.LENGTH_LONG).show();
                    }
                }
        ) {
        };
        requestQueue.add(jsonArrayRequest);
    }
}