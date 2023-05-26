package com.example.chitietphim;

import static android.content.ContentValues.TAG;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.app.Dialog;
import android.app.DownloadManager;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.RatingBar;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.VideoView;

import com.android.volley.AuthFailureError;
import com.android.volley.NetworkResponse;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.JsonRequest;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.bumptech.glide.Glide;
import com.example.chitietphim.Adapter.BinhLuanAdapter;
import com.example.chitietphim.Model.BinhLuan;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

public class chitietphim extends AppCompatActivity {
    ImageView imgAnhBia;
    TextView tvTenPhim, tvNoiDung, tvSoDiem, tvNamRaMat, tvTuoi;
    String maphim, tenphim, noidungphim, nguonphim, sodiem, namramat, anhbia, nguontrailer = "", matl, tuoi;
    Button btnXemPhim, btnTrailer, btnClose, btnThemBl, btnDanhGia;

    VideoView vd;
    ArrayList<BinhLuan> binhLuans;
    BinhLuanAdapter binhLuanAdapter;
    ListView lv;
    EditText edtnoidungBL;
    String maKH = LoginActivity.malog;

    // Biến rating


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chitietphim);

        setTitle("Chi tiết phim");

        tvTenPhim = findViewById(R.id.tvTenPhim);
        tvNoiDung = findViewById(R.id.tvNoiDung);
        tvSoDiem = findViewById(R.id.tvSoDiem);
        tvNamRaMat = findViewById(R.id.tvNamRaMat);
        tvTuoi = findViewById(R.id.tvTuoi);
        imgAnhBia = findViewById(R.id.imgAnhBia);
        btnXemPhim = findViewById(R.id.btnXemPhim);
        btnTrailer = findViewById(R.id.btnTrailer);
        btnThemBl = findViewById(R.id.btnThemBl);
        btnDanhGia = findViewById(R.id.btnDanhGia);


        edtnoidungBL = findViewById(R.id.edtnoidungBL);
        lv = findViewById(R.id.lv);

        FrameLayout fTrailerViewer = findViewById(R.id.fTrailerViewer);
        vd = (VideoView) findViewById(R.id.vdTrailer);
        btnClose = findViewById(R.id.btnClose);

        binhLuans = new ArrayList<>();
        Bundle extras = this.getIntent().getExtras();
        String maPhim = extras.getString("MaPhim");

        String urlPhim = "http://172.17.21.45:8080/service/dulieuctphim.php";
        String urlBL = "http://172.17.21.45:8080/service/dulieubinhluan.php";
        String urlThemBL = "http://172.17.21.45:8080/service/thembinhluan.php";
        String urlLS = "http://172.17.21.45:8080/service/themls.php";
        String urlDG = "http://172.17.21.45:8080/service/themdg.php";

        SimpleDateFormat sdf = new SimpleDateFormat("yyyy.MM.dd HH:mm:ss z");
        String thoigianhientai = sdf.format(new Date());
        // button đánh giá

        btnDanhGia.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (maKH == null) {
                    Toast.makeText(chitietphim.this, "Cần đăng nhập", Toast.LENGTH_SHORT).show();
                } else {
                    Dialog dialog = new Dialog(chitietphim.this);
                    dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
                    dialog.setContentView(R.layout.rating_dialog);
                    dialog.show();

                    RatingBar ratingBar = dialog.findViewById(R.id.rating_bar);
                    TextView tvRating = dialog.findViewById(R.id.tv_rating);
                    Button btSubmit = dialog.findViewById(R.id.bt_submit_rating);

                    ratingBar.setOnRatingBarChangeListener(new RatingBar.OnRatingBarChangeListener() {
                        @Override
                        public void onRatingChanged(RatingBar ratingBar, float rating, boolean fromUser) {
                            tvRating.setText(String.format("(%s)", rating));
                        }
                    });

                    btSubmit.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            String sRating = String.valueOf(ratingBar.getRating());
                            //Toast.makeText(chitietphim.this, sRating, Toast.LENGTH_SHORT).show();
                            onThemDG(urlDG, maKH, maphim, sRating);
                            onGetCTPhim(urlPhim, maPhim);
                            dialog.dismiss();
                        }
                    });
                }
            }
        });

        btnXemPhim.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (maKH == null) {
                    Toast.makeText(chitietphim.this, "Cần đăng nhập", Toast.LENGTH_SHORT).show();
                } else {
                    Intent intent = new Intent(chitietphim.this, WatchActivity.class);
                    intent.putExtra("NguonPhim", nguonphim);
                    intent.putExtra("TenPhim", tenphim);
                    intent.putExtra("MaTL", matl);
                    startActivity(intent);
                    onThemLS(urlLS, maKH, maPhim, thoigianhientai);
                }
            }
        });

        btnClose.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                vd.pause();
                fTrailerViewer.setVisibility(View.INVISIBLE);
            }
        });

        btnTrailer.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //Toast.makeText(chitietphim.this, nguontrailer, Toast.LENGTH_LONG).show();
                Uri uri = Uri.parse(nguontrailer);

                vd.setVideoURI(uri);
                vd.requestFocus();
                vd.start();
                fTrailerViewer.setVisibility(View.VISIBLE);
            }
        });

        btnThemBl.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (maKH == null) {
                    Toast.makeText(chitietphim.this, "Cần đăng nhập", Toast.LENGTH_SHORT).show();
                } else {
                    if (edtnoidungBL.getText().toString().isEmpty()) {
                        Toast.makeText(chitietphim.this, "Nhập bình luận", Toast.LENGTH_LONG).show();
                    } else {
                        onThemBL(urlThemBL, maKH, maphim, edtnoidungBL.getText().toString(), thoigianhientai.toString(), urlBL);
                        edtnoidungBL.setText("");
                        //Toast.makeText(chitietphim.this,maKH + maphim + edtnoidungBL.getText().toString(),Toast.LENGTH_LONG).show();
                    }
                }
            }
        });


        // Toast.makeText(this,maphim,Toast.LENGTH_LONG).show();
        onGetCTPhim(urlPhim, maPhim);
        onGetBinhLuan(urlBL, maPhim);

    }

    void onGetCTPhim(String url, String ma) {
        url += "?maPhimCT=" + ma;
        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(Request.Method.GET, url, null, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject obj) {
                try {
                    maphim = obj.getString("maPhim");
                    tenphim = obj.getString("tenPhim");
                    nguonphim = obj.getString("nguonPhim");
                    noidungphim = obj.getString("noiDungPhim");
                    sodiem = obj.getString("soDiemPhim");
                    namramat = obj.getString("namRaMat");
                    anhbia = obj.getString("anhBia");
                    nguontrailer = obj.getString("nguonTrailer");
                    matl = obj.getString("maTheLoai");
                    tuoi = obj.getString("doTuoiChoPhep");

                    Glide.with(chitietphim.this).load(anhbia).into(imgAnhBia);
                    tvTenPhim.setText(tenphim);
                    tvSoDiem.setText(sodiem + " ★");
                    tvNamRaMat.setText(namramat);
                    tvNoiDung.setText("Nội dung: " + noidungphim);
                    tvTuoi.setText(tuoi + "+");
                } catch (JSONException e) {
                    throw new RuntimeException(e);
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(chitietphim.this, error.toString(), Toast.LENGTH_LONG).show();
            }
        });
        RequestQueue request = Volley.newRequestQueue(this);
        request.add(jsonObjectRequest);
    }

    void onGetBinhLuan(String url, String ma) {
        url += "?maPhimBL=" + ma;
        JsonArrayRequest arrayRequest = new JsonArrayRequest(Request.Method.GET, url, null,
                new Response.Listener<JSONArray>() {
                    @Override
                    public void onResponse(JSONArray response) {
                        binhLuans.clear();
                        for (int i = 0; i < response.length(); i++) {
                            try {
                                JSONObject obj = response.getJSONObject(i);
                                String makh = obj.getString("maKhachHang");
                                String noidung = obj.getString("noiDungBinhLuan");
                                // Toast.makeText(chitietphim.this,makh,Toast.LENGTH_LONG).show();
                                binhLuans.add(new BinhLuan(makh, noidung));
                            } catch (JSONException e) {
                                e.printStackTrace();
                            }
                            binhLuanAdapter = new BinhLuanAdapter(chitietphim.this, R.layout.list_binhluan, binhLuans);
                            lv.setAdapter(binhLuanAdapter);
                        }

                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(chitietphim.this, error.toString(), Toast.LENGTH_LONG).show();
            }
        });
        RequestQueue request = Volley.newRequestQueue(this);
        request.add(arrayRequest);
    }

    void onThemBL(String url, String maKH, String maP, String noiDungBL, String thoiTG, String urlBL) {
        RequestQueue request = Volley.newRequestQueue(this);
        StringRequest stringRequest = new StringRequest(Request.Method.POST, url, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                if (response.trim().equals("success")) {
                    Toast.makeText(chitietphim.this, "Bình luận thành công", Toast.LENGTH_LONG).show();
                    onGetBinhLuan(urlBL, maP);
                } else {
                    Toast.makeText(chitietphim.this, "Lỗi không thêm được", Toast.LENGTH_LONG).show();
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(chitietphim.this, "Lỗi", Toast.LENGTH_LONG).show();
            }
        }) {
            @Nullable
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> params = new HashMap<>();
                params.put("maP", maP);
                params.put("maKH", maKH);
                params.put("noidungbl", noiDungBL);
                params.put("thoiGian", thoiTG);
                return params;
            }
        };
        request.add(stringRequest);
    }

    void onThemLS(String url, String maKH, String maP, String thoiTG) {
        RequestQueue request = Volley.newRequestQueue(this);
        StringRequest stringRequest = new StringRequest(Request.Method.POST, url, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                if (response.trim().equals("success")) {
                    Toast.makeText(chitietphim.this, "Thêm lịch sử xem phim", Toast.LENGTH_LONG).show();
                } else {
                    Toast.makeText(chitietphim.this, "Lỗi không thêm được", Toast.LENGTH_LONG).show();
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(chitietphim.this, "Lỗi", Toast.LENGTH_LONG).show();
            }
        }) {
            @Nullable
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> params = new HashMap<>();
                params.put("maP", maP);
                params.put("maKH", maKH);
                params.put("thoiGian", thoiTG);
                return params;
            }
        };
        request.add(stringRequest);
    }

    void onThemDG(String url, String maKH, String maP, String diem) {
        RequestQueue request = Volley.newRequestQueue(this);
        StringRequest stringRequest = new StringRequest(Request.Method.POST, url, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                if (response.trim().equals("success")) {
                    Toast.makeText(chitietphim.this, "Thêm đánh giá xem phim", Toast.LENGTH_LONG).show();
                } else {
                    Toast.makeText(chitietphim.this, "Lỗi không thêm được", Toast.LENGTH_LONG).show();
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(chitietphim.this, "Lỗi", Toast.LENGTH_LONG).show();
            }
        }) {
            @Nullable
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> params = new HashMap<>();
                params.put("maP", maP);
                params.put("maKH", maKH);
                params.put("soDiem", diem);
                return params;
            }
        };
        request.add(stringRequest);
    }
}
