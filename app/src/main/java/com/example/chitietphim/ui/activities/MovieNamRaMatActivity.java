package com.example.chitietphim.ui.activities;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.Volley;
import com.example.chitietphim.R;
import com.example.chitietphim.data.adapter.MovieAdapter;
import com.example.chitietphim.data.model.MovieItem;
import com.example.chitietphim.data.repo.urls;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;


public class MovieNamRaMatActivity extends AppCompatActivity {

    private RecyclerView recyclerViewYear;
    private MovieAdapter movieAdapterYear;
    private List<MovieItem> movieItemsYear;
    private TextView tv_nam;


    String idtl = "";

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
        setContentView(R.layout.activity_namsx);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setHomeButtonEnabled(true);
        getSupportActionBar().setHomeAsUpIndicator(R.drawable.ic_arrow_back);
        setTitle("Tìm theo năm");
        recyclerViewYear = findViewById(R.id.rcv_list_movie_nam);
        tv_nam = findViewById(R.id.tv_nam);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this, RecyclerView.VERTICAL, false);
        recyclerViewYear.setLayoutManager(linearLayoutManager);

        movieItemsYear = new ArrayList<>();


        Intent intent = getIntent();
        if (intent != null) {
            String idmovie = intent.getStringExtra("year");
            idtl = idmovie;
            switch (idmovie) {
                case "1990":
                    tv_nam.setText("Phim ra mắt trước năm 2000");
                    break;
                case "2001":
                    tv_nam.setText("Phim ra mắt từ năm 2000 đến 2009");
                    break;
                case "2011":
                    tv_nam.setText("Phim ra mắt từ năm 2010 đến 2019");
                    break;
                default:
                    tv_nam.setText("Phim ra mắt từ năm 2020 đến nay");
                    break;
            }
            GetData(urls.urlPhimByYear.getStringURL(), idtl);
        }
    }

    private void GetData(String url, String nam) {
        url += "?namRaMat=" + nam;
        RequestQueue requestQueue = Volley.newRequestQueue(this);
        JsonArrayRequest jsonArrayRequest = new JsonArrayRequest(Request.Method.GET, url, null, response -> {
            for (int i = 0; i < response.length(); i++) {
                try {
                    JSONObject object = response.getJSONObject(i);
                    movieItemsYear.add(new MovieItem(
                            object.getString("maPhim"),
                            object.getString("tenPhim"),
                            object.getString("soDiemPhim"),
                            object.getString("anhBia")
                    ));
                } catch (JSONException e) {
                    e.printStackTrace();
                }
                movieAdapterYear = new MovieAdapter(MovieNamRaMatActivity.this, movieItemsYear);
                recyclerViewYear.setAdapter(movieAdapterYear);
            }

        },
                error -> Toast.makeText(MovieNamRaMatActivity.this, "Lỗi!" + error.toString(), Toast.LENGTH_LONG).show()
        );
        requestQueue.add(jsonArrayRequest);
    }
}