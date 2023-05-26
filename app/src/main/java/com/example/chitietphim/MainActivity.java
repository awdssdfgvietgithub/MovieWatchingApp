package com.example.chitietphim;

import android.content.Intent;
import android.graphics.drawable.AnimationDrawable;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ImageView;
import android.widget.SearchView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.view.MenuItemCompat;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.Volley;
import com.example.chitietphim.Adapter.MovieAdapter;
import com.example.chitietphim.Model.MovieItem;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;


public class MainActivity extends AppCompatActivity {

    private RecyclerView recyclerViewfull, recyclerViewhd, recyclerViewkd, recyclerViewtc, recyclerViewhh;
    private MovieAdapter movieAdapterfull, movieAdapterhd, movieAdapterkd, movieAdaptertc, movieAdapterhh;
    private List<MovieItem> movieItemsfull, movieItemshd, movieItemskd, movieItemstc, movieItemshh;

    //mạng nhà
    String urlGetData = "http://172.17.21.45:8080/service/datamovie.php";
    String urlGetDataTL = "http://172.17.21.45:8080/service/datamovietheloai.php";
    String makh = null;
    //mạng trường
//    String urlGetData = "http://172.17.119.189:8080/Demoservice/datamovie.php";
//    String urlGetDataTL = "http://172.17.119.189:8080/Demoservice/datamovietheloai.php";


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        setTitle("FiveTube");
        movieItemsfull = new ArrayList<>();
        movieItemshd = new ArrayList<>();
        movieItemskd = new ArrayList<>();
        movieItemstc = new ArrayList<>();
        movieItemshh = new ArrayList<>();
        ImageView imageView = findViewById(R.id.iv_bg);
        AnimationDrawable animationDrawable = (AnimationDrawable) imageView.getDrawable();
        animationDrawable.start();

        recyclerViewfull = findViewById(R.id.rcv_list_movie);
        recyclerViewhd = findViewById(R.id.rcv_list_movie2);
        recyclerViewkd = findViewById(R.id.rcv_list_movie3);
        recyclerViewtc = findViewById(R.id.rcv_list_movie4);
        recyclerViewhh = findViewById(R.id.rcv_list_movie5);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this, RecyclerView.HORIZONTAL, false);
        recyclerViewfull.setLayoutManager(linearLayoutManager);
        LinearLayoutManager linearLayoutManager2 = new LinearLayoutManager(this, RecyclerView.HORIZONTAL, false);
        recyclerViewhd.setLayoutManager(linearLayoutManager2);
        LinearLayoutManager linearLayoutManager3 = new LinearLayoutManager(this, RecyclerView.HORIZONTAL, false);
        recyclerViewkd.setLayoutManager(linearLayoutManager3);
        LinearLayoutManager linearLayoutManager4 = new LinearLayoutManager(this, RecyclerView.HORIZONTAL, false);
        recyclerViewtc.setLayoutManager(linearLayoutManager4);
        LinearLayoutManager linearLayoutManager5 = new LinearLayoutManager(this, RecyclerView.HORIZONTAL, false);
        recyclerViewhh.setLayoutManager(linearLayoutManager5);

        GetDataFull(urlGetData);
        GetDataHanhDong(urlGetDataTL);
        GetDataKinhDi(urlGetDataTL);
        GetDataTinhCam(urlGetDataTL);
        GetDataHoatHinh(urlGetDataTL);
        Intent intent = getIntent();

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.option_menu, menu);
        MenuItem menuItem = menu.findItem(R.id.menu);
        SearchView searchView = (SearchView) MenuItemCompat.getActionView(menuItem);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        int id = item.getItemId();
        Intent intent = getIntent();
        if (intent != null) {
            String idmakh = intent.getStringExtra("userid");
            makh = idmakh;
        }
        switch (id) {
            case R.id.menu_user:
                Intent user = new Intent(MainActivity.this, LoginActivity.class);
                startActivity(user);
                break;
            case R.id.menu_history:
                if (makh == null) {
                    Toast.makeText(getBaseContext(), "Bạn chưa đăng nhập", Toast.LENGTH_LONG).show();
                } else {
                    Intent ls = new Intent(MainActivity.this, HistoryActivity.class);
                    ls.putExtra("userid2", makh);
                    startActivity(ls);
                }
                break;
            case R.id.menu_profile:
                if (makh == null) {
                    Toast.makeText(getBaseContext(), "Bạn chưa đăng nhập", Toast.LENGTH_LONG).show();
                } else {
                    Intent pf = new Intent(MainActivity.this, ProfileActivity.class);
                    pf.putExtra("userid3", makh);
                    Toast.makeText(getBaseContext(), "makh " + makh, Toast.LENGTH_LONG).show();
                    startActivity(pf);
                }
                break;
            case R.id.menu_type_action:
                Intent tlhd = new Intent(MainActivity.this, MovieTheLoaiActivity.class);
                tlhd.putExtra("movieid", "TL02");
                startActivity(tlhd);
                break;
            case R.id.menu_type_horror:
                Intent tlkd = new Intent(MainActivity.this, MovieTheLoaiActivity.class);
                tlkd.putExtra("movieid", "TL01");
                startActivity(tlkd);
                break;
            case R.id.menu_type_comedy:
                Intent tltc = new Intent(MainActivity.this, MovieTheLoaiActivity.class);
                tltc.putExtra("movieid", "TL03");
                startActivity(tltc);
                break;
            case R.id.menu_type_anime:
                Intent tlhh = new Intent(MainActivity.this, MovieTheLoaiActivity.class);
                tlhh.putExtra("movieid", "TL04");
                startActivity(tlhh);
                break;
            case R.id.menu_year_b2000:
                Intent y9x = new Intent(MainActivity.this, MovieNamRaMatActivity.class);
                y9x.putExtra("year", "1990");
                startActivity(y9x);
                break;
            case R.id.menu_year_2000_2010:
                Intent y10 = new Intent(MainActivity.this, MovieNamRaMatActivity.class);
                y10.putExtra("year", "2001");
                startActivity(y10);
                break;
            case R.id.menu_year_2011_2020:
                Intent y20 = new Intent(MainActivity.this, MovieNamRaMatActivity.class);
                y20.putExtra("year", "2011");
                startActivity(y20);
                break;
            case R.id.menu_year_a2020:
                Intent y30 = new Intent(MainActivity.this, MovieNamRaMatActivity.class);
                y30.putExtra("year", "2030");
                startActivity(y30);
                break;
            default:
        }
        return super.onOptionsItemSelected(item);
    }


    // Tất cả phim
    private void GetDataFull(String url) {
        RequestQueue requestQueue = Volley.newRequestQueue(this);
        JsonArrayRequest jsonArrayRequest = new JsonArrayRequest(Request.Method.GET, url, null, new Response.Listener<JSONArray>() {
            @Override
            public void onResponse(JSONArray response) {
                movieItemsfull.clear();
                for (int i = 0; i < response.length(); i++) {
                    try {
                        JSONObject object = response.getJSONObject(i);
                        movieItemsfull.add(new MovieItem(
                                object.getString("maPhim"),
                                object.getString("tenPhim"),
                                object.getString("soDiemPhim"),
                                object.getString("anhBia")
                        ));
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                    movieAdapterfull = new MovieAdapter(MainActivity.this, movieItemsfull);
                    recyclerViewfull.setAdapter(movieAdapterfull);
                }

            }
        },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Toast.makeText(MainActivity.this, "Lỗi!" + error.toString(), Toast.LENGTH_LONG).show();
                    }
                }
        );
        requestQueue.add(jsonArrayRequest);
    }

    // thể loại TL02 (Hành động)
    private void GetDataHanhDong(String url) {
        url += "?maTheLoai=TL02";
        RequestQueue requestQueue = Volley.newRequestQueue(this);
        JsonArrayRequest jsonArrayRequest = new JsonArrayRequest(Request.Method.GET, url, null, new Response.Listener<JSONArray>() {
            @Override
            public void onResponse(JSONArray response) {
                movieItemshd.clear();
                for (int i = 0; i < response.length(); i++) {
                    try {
                        JSONObject object = response.getJSONObject(i);
                        movieItemshd.add(new MovieItem(
                                object.getString("maPhim"),
                                object.getString("tenPhim"),
                                object.getString("soDiemPhim"),
                                object.getString("anhBia")
                        ));
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                    movieAdapterhd = new MovieAdapter(MainActivity.this, movieItemshd);
                    recyclerViewhd.setAdapter(movieAdapterhd);
                }

            }
        },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Toast.makeText(MainActivity.this, "Lỗi!" + error.toString(), Toast.LENGTH_LONG).show();
                    }
                }
        );
        requestQueue.add(jsonArrayRequest);
    }


    // thể loại TL01 (Kinh dị)
    private void GetDataKinhDi(String url) {
        url += "?maTheLoai=TL01";
        RequestQueue requestQueue = Volley.newRequestQueue(this);
        JsonArrayRequest jsonArrayRequest = new JsonArrayRequest(Request.Method.GET, url, null, new Response.Listener<JSONArray>() {
            @Override
            public void onResponse(JSONArray response) {
                movieItemskd.clear();
                for (int i = 0; i < response.length(); i++) {
                    try {
                        JSONObject object = response.getJSONObject(i);
                        movieItemskd.add(new MovieItem(
                                object.getString("maPhim"),
                                object.getString("tenPhim"),
                                object.getString("soDiemPhim"),
                                object.getString("anhBia")
                        ));
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                    movieAdapterkd = new MovieAdapter(MainActivity.this, movieItemskd);
                    recyclerViewkd.setAdapter(movieAdapterkd);
                }

            }
        },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Toast.makeText(MainActivity.this, "Lỗi!" + error.toString(), Toast.LENGTH_LONG).show();
                    }
                }
        );
        requestQueue.add(jsonArrayRequest);
    }


    // thể loại TL03 (Tình cảm)
    private void GetDataTinhCam(String url) {
        url += "?maTheLoai=TL03";
        RequestQueue requestQueue = Volley.newRequestQueue(this);
        JsonArrayRequest jsonArrayRequest = new JsonArrayRequest(Request.Method.GET, url, null, new Response.Listener<JSONArray>() {
            @Override
            public void onResponse(JSONArray response) {
                movieItemstc.clear();
                for (int i = 0; i < response.length(); i++) {
                    try {
                        JSONObject object = response.getJSONObject(i);
                        movieItemstc.add(new MovieItem(
                                object.getString("maPhim"),
                                object.getString("tenPhim"),
                                object.getString("soDiemPhim"),
                                object.getString("anhBia")
                        ));
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                    movieAdaptertc = new MovieAdapter(MainActivity.this, movieItemstc);
                    recyclerViewtc.setAdapter(movieAdaptertc);
                }

            }
        },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Toast.makeText(MainActivity.this, "Lỗi!" + error.toString(), Toast.LENGTH_LONG).show();
                    }
                }
        );
        requestQueue.add(jsonArrayRequest);
    }


    // thể loại TL02 (Hành động)
    private void GetDataHoatHinh(String url) {
        url += "?maTheLoai=TL04";
        RequestQueue requestQueue = Volley.newRequestQueue(this);
        JsonArrayRequest jsonArrayRequest = new JsonArrayRequest(Request.Method.GET, url, null, new Response.Listener<JSONArray>() {
            @Override
            public void onResponse(JSONArray response) {
                // thể loại TL02 (Hành động)
                movieItemshh.clear();
                for (int i = 0; i < response.length(); i++) {
                    try {
                        JSONObject object = response.getJSONObject(i);
                        movieItemshh.add(new MovieItem(
                                object.getString("maPhim"),
                                object.getString("tenPhim"),
                                object.getString("soDiemPhim"),
                                object.getString("anhBia")
                        ));
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                    movieAdapterhh = new MovieAdapter(MainActivity.this, movieItemshh);
                    recyclerViewhh.setAdapter(movieAdapterhh);
                }

            }
        },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Toast.makeText(MainActivity.this, "Lỗi!" + error.toString(), Toast.LENGTH_LONG).show();
                    }
                }
        );
        requestQueue.add(jsonArrayRequest);
    }
}
