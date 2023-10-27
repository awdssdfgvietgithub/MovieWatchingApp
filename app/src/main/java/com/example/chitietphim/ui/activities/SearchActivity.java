package com.example.chitietphim.ui.activities;

import android.os.Bundle;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.SearchView;
import androidx.recyclerview.widget.RecyclerView;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.Volley;
import com.example.chitietphim.R;
import com.example.chitietphim.data.adapter.ResultsSearchAdapter;
import com.example.chitietphim.data.model.MovieItem;
import com.example.chitietphim.data.repo.urls;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.Objects;
import java.util.stream.Collectors;

public class SearchActivity extends AppCompatActivity {
    private ResultsSearchAdapter resultsSearchAdapter;
    private RecyclerView recyclerViewResultsSearch;
    private LinearLayout containerNotFound;
    private ArrayList<MovieItem> data;
    private androidx.appcompat.widget.SearchView searchView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search);
        Objects.requireNonNull(this.getSupportActionBar()).hide();
        addControls();

        data = GetDataFull(urls.urlPhim.getStringURL());

        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                if (!query.isEmpty()) {
                    ArrayList<MovieItem> results = data.stream().filter(movieItem ->
                                    movieItem.getTitle().trim().toLowerCase().contains(query.toLowerCase().trim()))
                            .sorted(Comparator.comparing(MovieItem::getDescription).reversed())
                            .collect(Collectors.toCollection(ArrayList<MovieItem>::new));
                    if (results.isEmpty()) {
                        recyclerViewResultsSearch.setVisibility(View.GONE);
                        containerNotFound.setVisibility(View.VISIBLE);
                    } else {
                        recyclerViewResultsSearch.setVisibility(View.VISIBLE);
                        containerNotFound.setVisibility(View.GONE);
                        resultsSearchAdapter = new ResultsSearchAdapter(getApplicationContext(), results);
                        recyclerViewResultsSearch.setAdapter(resultsSearchAdapter);
                    }
                }
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                return false;
            }
        });
    }

    private void addControls() {
        recyclerViewResultsSearch = findViewById(R.id.recycler_view_results);
        searchView = findViewById(R.id.search_view);
        containerNotFound = findViewById(R.id.container_not_found);
    }

    private ArrayList<MovieItem> GetDataFull(String url) {
        ArrayList<MovieItem> listMovie = new ArrayList<>();
        RequestQueue requestQueue = Volley.newRequestQueue(this);
        JsonArrayRequest jsonArrayRequest = new JsonArrayRequest(Request.Method.GET, url, null, response -> {
            for (int i = 0; i < response.length(); i++) {
                try {
                    JSONObject object = response.getJSONObject(i);
                    listMovie.add(new MovieItem(
                            object.getString("maPhim"),
                            object.getString("tenPhim"),
                            object.getString("soDiemPhim"),
                            object.getString("anhBia")
                    ));
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        },
                error -> Toast.makeText(getBaseContext(), "Lỗi!" + error.toString(), Toast.LENGTH_LONG).show()
        );
        requestQueue.add(jsonArrayRequest);
        return listMovie;
    }
}