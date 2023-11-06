package com.example.chitietphim.ui.activities;

import android.os.Bundle;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.RadioGroup;
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
    private RadioGroup radioGroupTopic;
    private RadioButton radioTatCa, radioKinhDi, radioHanhDong, radioTinhCam, radioHoatHinh;
    private ArrayList<MovieItem> results;
    private String queryData = "";

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
                queryData = query;
                if (!queryData.isEmpty()) {
                    if (radioTatCa.isChecked()) {
                        results = data.stream().filter(movieItem ->
                                        movieItem.getTitle().trim().toLowerCase().contains(queryData.toLowerCase().trim()))
                                .sorted(Comparator.comparing(MovieItem::getDescription).reversed())
                                .collect(Collectors.toCollection(ArrayList<MovieItem>::new));
                    } else if (radioHanhDong.isChecked()) {
                        results = data.stream()
                                .filter(movieItem ->
                                        movieItem.getTitle().trim().toLowerCase().contains(queryData.toLowerCase().trim()))
                                .filter(movieItem -> Objects.equals(movieItem.getMaTheLoai(), "TL02"))
                                .sorted(Comparator.comparing(MovieItem::getDescription).reversed())
                                .collect(Collectors.toCollection(ArrayList<MovieItem>::new));
                    } else if (radioHoatHinh.isChecked()) {
                        results = data.stream()
                                .filter(movieItem ->
                                        movieItem.getTitle().trim().toLowerCase().contains(queryData.toLowerCase().trim()))
                                .filter(movieItem -> Objects.equals(movieItem.getMaTheLoai(), "TL04"))
                                .sorted(Comparator.comparing(MovieItem::getDescription).reversed())
                                .collect(Collectors.toCollection(ArrayList<MovieItem>::new));
                    } else if (radioTinhCam.isChecked()) {
                        results = data.stream()
                                .filter(movieItem ->
                                        movieItem.getTitle().trim().toLowerCase().contains(queryData.toLowerCase().trim()))
                                .filter(movieItem -> Objects.equals(movieItem.getMaTheLoai(), "TL03"))
                                .sorted(Comparator.comparing(MovieItem::getDescription).reversed())
                                .collect(Collectors.toCollection(ArrayList<MovieItem>::new));
                    } else if (radioKinhDi.isChecked()) {
                        results = data.stream()
                                .filter(movieItem ->
                                        movieItem.getTitle().trim().toLowerCase().contains(queryData.toLowerCase().trim()))
                                .filter(movieItem -> Objects.equals(movieItem.getMaTheLoai(), "TL01"))
                                .sorted(Comparator.comparing(MovieItem::getDescription).reversed())
                                .collect(Collectors.toCollection(ArrayList<MovieItem>::new));
                    }

                    if (results.isEmpty()) {
                        recyclerViewResultsSearch.setVisibility(View.GONE);
                        containerNotFound.setVisibility(View.VISIBLE);
                    } else {
                        recyclerViewResultsSearch.setVisibility(View.VISIBLE);
                        containerNotFound.setVisibility(View.GONE);
                        resultsSearchAdapter = new ResultsSearchAdapter(getApplicationContext(), results);
                        recyclerViewResultsSearch.setAdapter(resultsSearchAdapter);
                    }
                } else {
                    recyclerViewResultsSearch.setVisibility(View.GONE);
                    containerNotFound.setVisibility(View.GONE);
                }
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                return false;
            }
        });

        radioGroupTopic.setOnCheckedChangeListener((group, checkedId) -> {
            switch (checkedId) {
                case R.id.radio_tat_ca -> {
                    if (!queryData.isEmpty()) {
                        results = data.stream().filter(movieItem ->
                                        movieItem.getTitle().trim().toLowerCase().contains(queryData.toLowerCase().trim()))
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
                    break;
                }
                case R.id.radio_kinh_di -> {
                    if (!queryData.isEmpty()) {
                        results = data.stream()
                                .filter(movieItem ->
                                        movieItem.getTitle().trim().toLowerCase().contains(queryData.toLowerCase().trim()))
                                .filter(movieItem -> Objects.equals(movieItem.getMaTheLoai(), "TL01"))
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
                    break;
                }
                case R.id.radio_hanh_dong -> {
                    if (!queryData.isEmpty()) {
                        results = data.stream()
                                .filter(movieItem ->
                                        movieItem.getTitle().trim().toLowerCase().contains(queryData.toLowerCase().trim()))
                                .filter(movieItem -> Objects.equals(movieItem.getMaTheLoai(), "TL02"))
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
                    break;
                }
                case R.id.radio_tinh_cam -> {
                    if (!queryData.isEmpty()) {

                        results = data.stream()
                                .filter(movieItem ->
                                        movieItem.getTitle().trim().toLowerCase().contains(queryData.toLowerCase().trim()))
                                .filter(movieItem -> Objects.equals(movieItem.getMaTheLoai(), "TL03"))
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
                    break;
                }
                case R.id.radio_hoat_hinh -> {
                    if (!queryData.isEmpty()) {
                        results = data.stream()
                                .filter(movieItem ->
                                        movieItem.getTitle().trim().toLowerCase().contains(queryData.toLowerCase().trim()))
                                .filter(movieItem -> Objects.equals(movieItem.getMaTheLoai(), "TL04"))
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
                    break;
                }
            }
        });
    }

    private void addControls() {
        recyclerViewResultsSearch = findViewById(R.id.recycler_view_results);
        searchView = findViewById(R.id.search_view);
        containerNotFound = findViewById(R.id.container_not_found);
        radioGroupTopic = findViewById(R.id.radio_group_topic);
        radioTatCa = findViewById(R.id.radio_tat_ca);
        radioKinhDi = findViewById(R.id.radio_kinh_di);
        radioHanhDong = findViewById(R.id.radio_hanh_dong);
        radioHoatHinh = findViewById(R.id.radio_hoat_hinh);
        radioTinhCam = findViewById(R.id.radio_tinh_cam);
        results = new ArrayList();
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
                            object.getString("anhBia"),
                            object.getString("maTheLoai")
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