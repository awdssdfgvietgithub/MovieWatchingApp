package com.example.chitietphim;

import android.content.Intent;
import android.os.Bundle;
import android.widget.ListView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.Volley;
import com.example.chitietphim.Adapter.AdapterProfile;
import com.example.chitietphim.Model.ClassProfile;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

public class ProfileActivity extends AppCompatActivity {
    ListView listViewpf;
    ArrayList<ClassProfile> arrayListpf;
    AdapterProfile adapterProfile;
    String makh="";
    String urlGetData = "http://172.17.21.45:8080/service/datakhachhang.php";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);
        setTitle("Thông Tin Cá Nhân");
        listViewpf =(ListView) findViewById(R.id.lsvprofile);

        arrayListpf =new ArrayList<>();
        Intent intent = getIntent();
        if (intent != null) {
            String idmakh = intent.getStringExtra("userid3");
            makh = idmakh;
            GetData(urlGetData,makh);
        }
    }
    private void GetData(String url,String ma){
        url+="?maKhachHang="+ma;
        RequestQueue requestQueue = Volley.newRequestQueue(this);
        JsonArrayRequest jsonArrayRequest = new JsonArrayRequest(Request.Method.GET, url, null, new Response.Listener<JSONArray>() {
            @Override
            public void onResponse(JSONArray response) {
                for(int i = 0; i<response.length();i++){
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
                    AdapterProfile adapter = new AdapterProfile(ProfileActivity.this, R.layout.custom_profile_listview,arrayListpf);
                    listViewpf.setAdapter(adapter);
                }
            }
        },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Toast.makeText(ProfileActivity.this, "Lỗi!"+error.toString(), Toast.LENGTH_LONG).show();
                    }
                }
        ){};
        requestQueue.add(jsonArrayRequest);
    }
}
