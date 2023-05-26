package com.example.chitietphim;

import android.Manifest;
import android.app.DownloadManager;
import android.app.PictureInPictureParams;
import android.content.ContentResolver;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.content.res.Configuration;
import android.media.AudioManager;
import android.media.MediaMetadataRetriever;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.os.Handler;
import android.os.Looper;
import android.provider.Settings;
import android.util.DisplayMetrics;
import android.util.Log;
import android.util.Rational;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.PopupMenu;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.Volley;
import com.example.chitietphim.Adapter.GridAdapter;
import com.example.chitietphim.Adapter.IconAdapter;
import com.example.chitietphim.Dialog.BrightnessDialog;
import com.example.chitietphim.Dialog.TrackSelectionDialog;
import com.example.chitietphim.Dialog.VolumeDialog;
import com.example.chitietphim.Model.GridItemModel;
import com.example.chitietphim.Model.IconModel;
import com.google.android.exoplayer2.ExoPlayer;
import com.google.android.exoplayer2.MediaItem;
import com.google.android.exoplayer2.PlaybackParameters;
import com.google.android.exoplayer2.Player;
import com.google.android.exoplayer2.trackselection.DefaultTrackSelector;
import com.google.android.exoplayer2.ui.PlayerView;
import com.google.android.exoplayer2.util.Util;
import com.google.android.exoplayer2.video.VideoSize;
import com.google.android.gms.ads.AdError;
import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.FullScreenContentCallback;
import com.google.android.gms.ads.LoadAdError;
import com.google.android.gms.ads.MobileAds;
import com.google.android.gms.ads.rewardedinterstitial.RewardedInterstitialAd;
import com.google.android.gms.ads.rewardedinterstitial.RewardedInterstitialAdLoadCallback;
import com.karumi.dexter.Dexter;
import com.karumi.dexter.MultiplePermissionsReport;
import com.karumi.dexter.PermissionToken;
import com.karumi.dexter.listener.PermissionDeniedResponse;
import com.karumi.dexter.listener.PermissionRequest;
import com.karumi.dexter.listener.multi.MultiplePermissionsListener;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class WatchActivity extends AppCompatActivity {
    String urlGetDataTL = "http://172.17.21.45:8080/service/datamovietheloai.php";
    ProgressBar progressBar;
    PlayerView playerView;
    ExoPlayer exoPlayer;
    ImageView bt_fullscreen, bt_lockscreen, bt_back, bt_volup, vol_close, bt_quality, bt_settings;
    TextView txtMovieName;
    boolean isFullScreen = false;
    boolean isLock = false;
    Handler handler;
    AudioManager audioManager;
    private boolean isShowingTrackSelectionDialog;
    private DefaultTrackSelector trackSelector;
    GridView gridView;
    ArrayList<GridItemModel> data;
    GridAdapter gridAdapter;
    /* các biến của recyclerview */
    private ArrayList<IconModel> iconModelArrayList = new ArrayList<>();
    IconAdapter iconAdapter;
    RecyclerView recyclerViewIcon;
    boolean expand = false;
    View nightMode;
    boolean dark = false;
    boolean mute = false;
    PlaybackParameters parameters;
    float speed;
    PictureInPictureParams.Builder pictureInPicture;
    boolean isCrossChecked;
    /* các biến của recyclerview */

    /* các biến của swipe and zoom */
    private int device_height, device_weight, brightness, media_volume;
    boolean start = false;
    boolean left, right;
    private float baseX, baseY;
    boolean swipe_move = false;
    private long diffX, diffY;
    public static final int MINIMUM_DISTANCE = 100;
    boolean success = false;
    TextView vol_text, brt_text;
    ProgressBar vol_progress, brt_progress;
    LinearLayout vol_progress_container, vol_text_container, brt_progress_container, brt_text_container;
    ImageView vol_icon, brt_icon;
    AudioManager audioManager2;
    private ContentResolver contentResolver;
    private Window window;
    boolean singleTap = false;
    /* các biến của swipe and zoom */

    /* các biến extras */
    String tenPhim, maTL, nguonPhim;
    /* các biến extras */

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_watch);
        addControls();
        getExtras();

        // Grid gợi ý phim
        data = new ArrayList<>();


        // Ấn item gridview
        gridView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                GridItemModel gridItemModel = data.get(position);
                Intent intent = new Intent(WatchActivity.this, chitietphim.class);
                intent.putExtra("MaPhim", gridItemModel.getVideoID());
                startActivity(intent);
            }
        });

        // Popup settings ở đây
        bt_settings.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                PopupMenu popupMenu = new PopupMenu(WatchActivity.this, bt_settings);
                popupMenu.inflate(R.menu.setting_menu);
                popupMenu.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
                    @Override
                    public boolean onMenuItemClick(MenuItem item) {
                        switch (item.getItemId()) {
                            case R.id.Download:
                                checkPermission();
                                break;
                            case R.id.Share:
                                Uri uri = Uri.parse(nguonPhim);
                                Intent intent = new Intent(Intent.ACTION_SEND);
                                intent.setType("video/*");
                                intent.putExtra(Intent.EXTRA_STREAM, uri);
                                startActivity(Intent.createChooser(intent, "Chia sẻ video"));
                                break;
                            case R.id.Properties:
                                double duration = exoPlayer.getDuration();
                                android.app.AlertDialog.Builder alerDialog = new android.app.AlertDialog.Builder(WatchActivity.this);
                                alerDialog.setTitle("Thông tin");

                                String name = "Tên file: " + tenPhim + ".mp4";
                                String path = "Nguồn: " + nguonPhim;
                                String size = "Kích thước: " + "356MB";
                                String time = "Thời lượng: " + Math.round(duration / 60000) + " phút";


                                String f = "mp4";
                                String format = "Định dạng: " + f;

                                MediaMetadataRetriever mediaMetadataRetriever = new MediaMetadataRetriever();
                                mediaMetadataRetriever.setDataSource(nguonPhim);
                                String height = mediaMetadataRetriever.extractMetadata(MediaMetadataRetriever.METADATA_KEY_VIDEO_HEIGHT);
                                String width = mediaMetadataRetriever.extractMetadata(MediaMetadataRetriever.METADATA_KEY_VIDEO_WIDTH);
                                String resolution = "Độ phân giải: " + width + "x" + height;

                                alerDialog.setMessage(name + "\n\n" + path + "\n\n" + size + "\n\n" + time + "\n\n" + format + "\n\n" + resolution);
                                alerDialog.setPositiveButton("OK", new DialogInterface.OnClickListener() {
                                    @Override
                                    public void onClick(DialogInterface dialog, int which) {
                                        dialog.dismiss();
                                    }
                                });
                                alerDialog.show();

                                break;
                        }
                        return false;
                    }
                });
                popupMenu.show();
            }
        });

        // Nút quay lại trang chi tiết
        bt_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        // Nút lựa chọn chất lượng video (chưa hoàn thành)
        trackSelector = new DefaultTrackSelector(this);

        handler = new Handler(Looper.getMainLooper());
        bt_quality.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!isShowingTrackSelectionDialog && TrackSelectionDialog.willHaveContent(trackSelector)) {
                    isShowingTrackSelectionDialog = true;
                    TrackSelectionDialog trackSelectionDialog = TrackSelectionDialog.createForTrackSelector(trackSelector, dismissedDialog -> isShowingTrackSelectionDialog = false);
                    trackSelectionDialog.show(getSupportFragmentManager(), null);
                }
            }
        });

        // Nút mở dialog chỉnh âm lượng
        bt_volup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                VolumeDialog volumeDialog = new VolumeDialog();
                volumeDialog.show(getSupportFragmentManager(), "dialog");
            }
        });

        // Nút set màn hình ngang
        bt_fullscreen.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!isFullScreen) {
                    bt_fullscreen.setImageDrawable(
                            ContextCompat
                                    .getDrawable(getApplicationContext(), R.drawable.ic_baseline_fullscreen_exit));
                    // xoay ngang
                    setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_SENSOR_LANDSCAPE);
                } else {
                    bt_fullscreen.setImageDrawable(ContextCompat
                            .getDrawable(getApplicationContext(), R.drawable.ic_baseline_fullscreen));
                    // xoay dọc
                    setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
                }
                isFullScreen = !isFullScreen;
            }
        });

        // Nút khóa màn hình
        bt_lockscreen.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!isLock) {
                    bt_lockscreen.setImageDrawable(ContextCompat.getDrawable(getApplicationContext(), R.drawable.ic_baseline_lock));
                } else {
                    bt_lockscreen.setImageDrawable(ContextCompat.getDrawable(getApplicationContext(), R.drawable.ic_outline_lock_open));
                }
                isLock = !isLock;
                lockScreen(isLock);
            }
        });

        // Setup nút đi tới hoặc quay lui 5s video
        backandforwardVideo();

        // Theo dõi các trạng thái của video
        exoPlayer.addListener(new Player.Listener() {
            @Override
            public void onPlaybackStateChanged(int playbackState) {
                // load video sẽ hiện thanh tiến trình
                if (playbackState == Player.STATE_BUFFERING) {
                    progressBar.setVisibility(View.VISIBLE);

                } else if (playbackState == Player.STATE_READY) {
                    // sau khi video đc load lên sẽ ẩn đi
                    progressBar.setVisibility(View.GONE);
                }

                if (!exoPlayer.getPlayWhenReady()) {
                    handler.removeCallbacks(updateProgressAction);
                } else {
                    onProgress();
                }
            }
        });


        txtMovieName.setText(tenPhim);

        // Truyền link chạy video
        Uri videoUrl = Uri.parse("" + nguonPhim);
        MediaItem media = MediaItem.fromUri(videoUrl);
        exoPlayer.setMediaItem(media);
        exoPlayer.setPlaybackParameters(parameters);
        exoPlayer.prepare();
        exoPlayer.play();

        // Lấy kích thước của thiết bị
        DisplayMetrics displayMetrics = new DisplayMetrics();
        getWindowManager().getDefaultDisplay().getMetrics(displayMetrics);
        device_weight = displayMetrics.widthPixels;
        device_height = displayMetrics.heightPixels;

        playerView.setOnTouchListener(new OnSwipeTouchListener(this) {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                switch (event.getAction()) {
                    case MotionEvent.ACTION_DOWN:
                        playerView.showController();
                        start = true;
                        if (event.getX() < (device_weight / 2)) {
                            left = true;
                            right = false;
                        } else if (event.getX() > (device_weight / 2)) {
                            left = false;
                            right = true;
                        }
                        baseX = event.getX();
                        baseY = event.getY();
                        break;
                    case MotionEvent.ACTION_MOVE:
                        swipe_move = true;
                        diffX = (long) Math.ceil(event.getX() - baseX);
                        diffY = (long) Math.ceil(event.getY() - baseY);
                        double brightnessSpeed = 0.01;
                        if (Math.abs(diffY) > MINIMUM_DISTANCE) {
                            start = true;
                            if (Math.abs(diffY) > Math.abs(diffX)) {
                                boolean value;
                                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                                    value = android.provider.Settings.System.canWrite(getApplicationContext());
                                    if (value) {
                                        if (left) {
                                            contentResolver = getContentResolver();
                                            window = getWindow();
                                            try {
                                                android.provider.Settings.System.putInt(contentResolver, Settings.System.SCREEN_BRIGHTNESS_MODE, Settings.System.SCREEN_BRIGHTNESS_MODE_MANUAL);
                                                brightness = android.provider.Settings.System.getInt(contentResolver, android.provider.Settings.System.SCREEN_BRIGHTNESS);

                                            } catch (Settings.SettingNotFoundException e) {
                                                throw new RuntimeException(e);
                                            }
                                            int new_brightness = (int) (brightness - (diffY * brightnessSpeed));
                                            if (new_brightness > 250) {
                                                new_brightness = 250;
                                            } else if (new_brightness < 1) {
                                                new_brightness = 1;
                                            }
                                            double brt_percentage = Math.ceil((((double) new_brightness / (double) 250) * (double) 100));
                                            brt_progress_container.setVisibility(View.VISIBLE);
                                            brt_text_container.setVisibility(View.VISIBLE);
                                            brt_progress.setProgress((int) brt_percentage);

                                            if (brt_percentage < 30) {
                                                brt_icon.setImageResource(R.drawable.ic_brightness_low);
                                            } else if (brt_percentage > 30 && brt_percentage < 80) {
                                                brt_icon.setImageResource(R.drawable.ic_brightness_half);
                                            } else if (brt_percentage > 80) {
                                                brt_icon.setImageResource(R.drawable.ic_brightness);
                                            }

                                            brt_text.setText(" " + (int) brt_percentage + "%");
                                            android.provider.Settings.System.putInt(contentResolver, android.provider.Settings.System.SCREEN_BRIGHTNESS, (new_brightness));
                                            WindowManager.LayoutParams layoutParams = window.getAttributes();
                                            layoutParams.screenBrightness = brightness / (float) 255;
                                            window.setAttributes(layoutParams);
                                        } else if (right) {
                                            vol_text_container.setVisibility(View.VISIBLE);
                                            media_volume = audioManager2.getStreamVolume(AudioManager.STREAM_MUSIC);
                                            int maxVol = audioManager2.getStreamMaxVolume(AudioManager.STREAM_MUSIC);
                                            double cal = (double) diffY * ((double) maxVol / ((double) (device_height * 2) - brightnessSpeed));
                                            int newMediaVolume = media_volume - (int) cal;
                                            if (newMediaVolume > maxVol) {
                                                newMediaVolume = maxVol;
                                            } else if (newMediaVolume < 1) {
                                                newMediaVolume = 0;
                                            }
                                            audioManager2.setStreamVolume(AudioManager.STREAM_MUSIC, newMediaVolume, AudioManager.FLAG_REMOVE_SOUND_AND_VIBRATE);
                                            double volPercentage = Math.ceil((((double) newMediaVolume / (double) maxVol) * (double) 100));
                                            vol_text.setText(" " + (int) volPercentage + "%");
                                            if (volPercentage < 1) {
                                                vol_icon.setImageResource(R.drawable.baseline_volume_off);
                                                vol_text.setVisibility(View.VISIBLE);
                                                vol_text.setText("Off");
                                            } else if (volPercentage >= 1) {
                                                vol_icon.setImageResource(R.drawable.baseline_volume_up);
                                                vol_text.setVisibility(View.VISIBLE);
                                            }
                                            vol_progress_container.setVisibility(View.VISIBLE);
                                            vol_progress.setProgress((int) volPercentage);
                                        }
                                        success = true;
                                    } else {
                                        Toast.makeText(WatchActivity.this, "Cấp quyền tương tác màn hình", Toast.LENGTH_SHORT).show();
                                        Intent intent = new Intent(Settings.ACTION_MANAGE_WRITE_SETTINGS);
                                        intent.setData(Uri.parse("package:" + getPackageName()));
                                        startActivityForResult(intent, 111);
                                    }
                                }
                            }
                        }
                        break;
                    case MotionEvent.ACTION_UP:
                        swipe_move = false;
                        start = false;
                        vol_progress_container.setVisibility(View.GONE);
                        brt_progress_container.setVisibility(View.GONE);
                        vol_text_container.setVisibility(View.GONE);
                        brt_text_container.setVisibility(View.GONE);
                        break;
                }
                return super.onTouch(v, event);
            }

            @Override
            public void onDoubleTouch() {
                super.onDoubleTouch();
            }

            @Override
            public void onSingleTouch() {
                super.onSingleTouch();
                if (singleTap) {
                    playerView.showController();
                    singleTap = false;
                } else {
                    playerView.hideController();
                    singleTap = true;
                }
            }
        });

        horizontalIconList();

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            pictureInPicture = new PictureInPictureParams.Builder();
        }
    }

    // khi màn hình bị lock thì không thể ấn nút quay lại
    @Override
    public void onBackPressed() {
        if (isLock) return;

        // nếu người dùng đang để chế độ màn hình ngang thì nút quay lại sẽ thành màn hình dọc
        if (getResources().getConfiguration().orientation == Configuration.ORIENTATION_LANDSCAPE) {
            bt_fullscreen.performClick();
        } else super.onBackPressed();
    }

    @Override
    protected void onStop() {
        super.onStop();
        if (isCrossChecked) {
            exoPlayer.release();
            finish();
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        exoPlayer.release();
    }

    @Override
    protected void onPause() {
        super.onPause();
        exoPlayer.setPlayWhenReady(false);
        if (isInPictureInPictureMode()) {
            exoPlayer.setPlayWhenReady(true);
        } else {
            exoPlayer.setPlayWhenReady(false);
        }
        //exoPlayer.pause();
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == 111) {
            boolean value;
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                value = Settings.System.canWrite(getApplicationContext());
                if (value) {
                    success = true;
                } else {
                    Toast.makeText(this, "Chưa được cấp quyền!", Toast.LENGTH_SHORT).show();
                }
            }
        }
    }

    // Phát hiện sử dụng picture in picture
    @Override
    public void onPictureInPictureModeChanged(boolean isInPictureInPictureMode, Configuration newConfig) {
        super.onPictureInPictureModeChanged(isInPictureInPictureMode, newConfig);
        isCrossChecked = isInPictureInPictureMode;
        if (isInPictureInPictureMode) {
            playerView.hideController();
        } else {
            playerView.showController();
        }
    }

    /* ----- MY FUNCTION -----*/
    // Khởi tạo thanh progress video
    private Runnable updateProgressAction = () -> onProgress();
    long ad = 4000;
    boolean check = false;

    private void onProgress() {
        ExoPlayer player = exoPlayer;
        long position = player == null ? 0 : player.getCurrentPosition();
        handler.removeCallbacks(updateProgressAction);
        int playbackState = player == null ? Player.STATE_IDLE : player.getPlaybackState();
        if (playbackState == Player.STATE_ENDED) {
            ImageView imgGridClose = findViewById(R.id.imgGridClose);
            LinearLayout grid_container = findViewById(R.id.grid_container);
            LinearLayout controlVideoMiddle = findViewById(R.id.controlVideoMiddle);
            LinearLayout controlVideoBottom = findViewById(R.id.controlVideoBottom);
            controlVideoBottom.setVisibility(View.INVISIBLE);
            controlVideoMiddle.setVisibility(View.INVISIBLE);
            bt_lockscreen.setVisibility(View.INVISIBLE);
            bt_settings.setVisibility(View.INVISIBLE);

            grid_container.setVisibility(View.VISIBLE);
            imgGridClose.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    grid_container.setVisibility(View.INVISIBLE);
                    controlVideoMiddle.setVisibility(View.VISIBLE);
                    controlVideoBottom.setVisibility(View.VISIBLE);
                    bt_lockscreen.setVisibility(View.VISIBLE);
                    bt_settings.setVisibility(View.VISIBLE);
                }
            });
        }
        if (playbackState != Player.STATE_IDLE && playbackState != Player.STATE_ENDED) {
            long delayMs;
            if (player.getPlayWhenReady() && playbackState == Player.STATE_READY) {
                delayMs = 1000 - position % 1000;
                if (delayMs < 200) {
                    delayMs += 1000;
                }
            } else {
                delayMs = 1000;
            }

            //check to display ad
            if ((ad - 3000 <= position && position <= ad) && !check) {
                check = true;
                initAd();
            }
            handler.postDelayed(updateProgressAction, delayMs);
        }
    }

    // Setup nút back và forward cho video
    private void backandforwardVideo() {
        exoPlayer = new ExoPlayer.Builder(this).setTrackSelector(trackSelector)
                .setSeekBackIncrementMs(5000)
                .setSeekForwardIncrementMs(5000)
                .build();
        playerView.setPlayer(exoPlayer);
        playerView.setKeepScreenOn(true);
    }

    // Khởi tạo quảng cáo ảo
    RewardedInterstitialAd rewardedInterstitialAd = null;

    private void initAd() {
        if (rewardedInterstitialAd != null) return;
        MobileAds.initialize(this);
        RewardedInterstitialAd.load(this, "ca-app-pub-3940256099942544/5354046379",
                new AdRequest.Builder().build(), new RewardedInterstitialAdLoadCallback() {
                    @Override
                    public void onAdLoaded(@NonNull RewardedInterstitialAd p0) {
                        rewardedInterstitialAd = p0;
                        rewardedInterstitialAd.setFullScreenContentCallback(new FullScreenContentCallback() {
                            @Override
                            public void onAdFailedToShowFullScreenContent(@NonNull AdError adError) {
                                Log.d("WatchActivity_AD", adError.getMessage());
                            }

                            @Override
                            public void onAdShowedFullScreenContent() {
                                handler.removeCallbacks(updateProgressAction);
                            }

                            @Override
                            public void onAdDismissedFullScreenContent() {
                                //resume play
                                exoPlayer.setPlayWhenReady(true);
                                rewardedInterstitialAd = null;
                                check = false;
                            }
                        });
                        LinearLayout sec_ad_countdown = findViewById(R.id.sect_ad_countdown);
                        TextView tx_ad_countdown = findViewById(R.id.tx_ad_countdown);
                        sec_ad_countdown.setVisibility(View.VISIBLE);
                        new CountDownTimer(4000, 1000) {
                            @Override
                            public void onTick(long l) {
                                tx_ad_countdown.setText("Quảng cáo sau " + l / 1000);
                            }

                            @Override
                            public void onFinish() {
                                sec_ad_countdown.setVisibility(View.GONE);
                                rewardedInterstitialAd.show(WatchActivity.this, rewardItem ->
                                {

                                });
                            }
                        }.start();
                    }

                    @Override
                    public void onAdFailedToLoad(@NonNull LoadAdError loadAdError) {
                        rewardedInterstitialAd = null;
                    }
                });
    }

    // Ẩn controls khi khóa màn hình
    void lockScreen(boolean lock) {
        LinearLayout mid = findViewById(R.id.controlVideoMiddle);
        LinearLayout bottom = findViewById(R.id.controlVideoBottom);
        LinearLayout top = findViewById(R.id.layoutTop);
        if (lock) {
            // ẩn đi các điều khiển nếu ấn khóa
            mid.setVisibility(View.INVISIBLE);
            bottom.setVisibility(View.INVISIBLE);
            top.setVisibility(View.INVISIBLE);
            bt_settings.setVisibility(View.INVISIBLE);
        } else {
            mid.setVisibility(View.VISIBLE);
            bottom.setVisibility(View.VISIBLE);
            top.setVisibility(View.VISIBLE);
            bt_settings.setVisibility(View.VISIBLE);
        }
    }

    // Khởi tạo và sử dụng các icon trong recyclerview
    private void horizontalIconList() {
        iconModelArrayList.add(new IconModel(R.drawable.ic_right, ""));
        iconModelArrayList.add(new IconModel(R.drawable.ic_night_mode, "Đêm"));
        iconModelArrayList.add(new IconModel(R.drawable.baseline_volume_off, "Im lặng"));
        iconModelArrayList.add(new IconModel(R.drawable.ic_route, "Xoay"));

        iconAdapter = new IconAdapter(iconModelArrayList, this);
        LinearLayoutManager layoutManager = new LinearLayoutManager(this, RecyclerView.HORIZONTAL, true);
        recyclerViewIcon.setLayoutManager(layoutManager);
        recyclerViewIcon.setAdapter(iconAdapter);
        iconAdapter.notifyDataSetChanged();
        iconAdapter.setOnItemClickListener(new IconAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(int position) {
                // mở rộng recyclerview
                if (position == 0) {
                    if (expand) {
                        iconModelArrayList.clear();
                        iconModelArrayList.add(new IconModel(R.drawable.ic_right, ""));
                        iconModelArrayList.add(new IconModel(R.drawable.ic_night_mode, "Đêm"));
                        iconModelArrayList.add(new IconModel(R.drawable.baseline_volume_off, "Im lặng"));
                        iconModelArrayList.add(new IconModel(R.drawable.ic_route, "Xoay"));
                        iconAdapter.notifyDataSetChanged();
                        expand = false;
                    } else {
                        if (iconModelArrayList.size() == 4) {
                            iconModelArrayList.add(new IconModel(R.drawable.ic_pip_mode, "Cửa sổ nổi"));
                            iconModelArrayList.add(new IconModel(R.drawable.ic_brightness, "Độ sáng"));
                            iconModelArrayList.add(new IconModel(R.drawable.ic_speed, "Tốc độ"));
                        }
                        iconModelArrayList.set(position, new IconModel(R.drawable.ic_left, ""));
                        iconAdapter.notifyDataSetChanged();
                        expand = true;
                    }
                }
                // nút chỉnh chế độ màn hình đêm
                if (position == 1) {
                    if (dark) {
                        nightMode.setVisibility(View.GONE);
                        iconModelArrayList.set(position, new IconModel(R.drawable.ic_night_mode, "Đêm"));
                        iconAdapter.notifyDataSetChanged();
                        dark = false;
                    } else {
                        nightMode.setVisibility(View.VISIBLE);
                        iconModelArrayList.set(position, new IconModel(R.drawable.ic_night_mode, "Ngày"));
                        iconAdapter.notifyDataSetChanged();
                        dark = true;
                    }
                }
                // nút mute âm thanh
                if (position == 2) {
                    if (mute) {
                        exoPlayer.setVolume(1);
                        iconModelArrayList.set(position, new IconModel(R.drawable.baseline_volume_off, "Im lặng"));
                        iconAdapter.notifyDataSetChanged();
                        mute = false;
                    } else {
                        exoPlayer.setVolume(0);
                        iconModelArrayList.set(position, new IconModel(R.drawable.baseline_volume_up, "Bỏ im lặng"));
                        iconAdapter.notifyDataSetChanged();
                        mute = true;
                    }
                }
                // nút xoay màn hình
                if (position == 3) {
                    if (getResources().getConfiguration().orientation == Configuration.ORIENTATION_PORTRAIT) {
                        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE);
                        bt_fullscreen.setImageDrawable(
                                ContextCompat
                                        .getDrawable(getApplicationContext(), R.drawable.ic_baseline_fullscreen_exit));
                        iconAdapter.notifyDataSetChanged();
                    } else if (getResources().getConfiguration().orientation == Configuration.ORIENTATION_LANDSCAPE) {
                        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
                        bt_fullscreen.setImageDrawable(
                                ContextCompat
                                        .getDrawable(getApplicationContext(), R.drawable.ic_baseline_fullscreen));
                        iconAdapter.notifyDataSetChanged();
                    }
                    isFullScreen = !isFullScreen;
                }
                // Picture in picture
                if (position == 4) {
                    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                        Rational aspectRatio = new Rational(16, 9);
                        pictureInPicture.setAspectRatio(aspectRatio);
                        enterPictureInPictureMode(pictureInPicture.build());
                    } else {
                        Log.wtf("not oreo", "yes");
                    }
                }
                // Seekbar chỉnh độ sáng
                if (position == 5) {
                    BrightnessDialog brightnessDialog = new BrightnessDialog();
                    brightnessDialog.show(getSupportFragmentManager(), "dialog");
                    iconAdapter.notifyDataSetChanged();
                }
                // Chỉnh tốc độ phát
                if (position == 6) {
                    AlertDialog.Builder alBuilder = new AlertDialog.Builder(WatchActivity.this);
                    alBuilder.setTitle("Chọn tốc độ chiếu").setPositiveButton("OK", null);
                    String[] items = {"0.25x", "0.5x", "1x Tốc độ bình thường", "1.25x", "1.5x", "2x"};
                    int checkedItem = -1;
                    alBuilder.setSingleChoiceItems(items, checkedItem, new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            switch (which) {
                                case 0:
                                    speed = 0.25f;
                                    parameters = new PlaybackParameters(speed);
                                    exoPlayer.setPlaybackParameters(parameters);
                                    break;
                                case 1:
                                    speed = 0.5f;
                                    parameters = new PlaybackParameters(speed);
                                    exoPlayer.setPlaybackParameters(parameters);
                                    break;
                                case 2:
                                    speed = 1f;
                                    parameters = new PlaybackParameters(speed);
                                    exoPlayer.setPlaybackParameters(parameters);
                                    break;
                                case 3:
                                    speed = 1.25f;
                                    parameters = new PlaybackParameters(speed);
                                    exoPlayer.setPlaybackParameters(parameters);
                                    break;
                                case 4:
                                    speed = 1.5f;
                                    parameters = new PlaybackParameters(speed);
                                    exoPlayer.setPlaybackParameters(parameters);
                                    break;
                                case 5:
                                    speed = 2f;
                                    parameters = new PlaybackParameters(speed);
                                    exoPlayer.setPlaybackParameters(parameters);
                                    break;
                                default:
                                    break;
                            }
                        }
                    });
                    AlertDialog alertDialog = alBuilder.create();
                    alertDialog.show();
                }
            }
        });
    }

    // ánh xạ
    private void addControls() {
        playerView = findViewById(R.id.player);
        progressBar = findViewById(R.id.progress_bar);
        bt_fullscreen = findViewById(R.id.bt_fullscreen);
        bt_lockscreen = findViewById(R.id.exo_lock);
        bt_back = findViewById(R.id.exo_back);
        bt_volup = findViewById(R.id.bt_volup);
        bt_quality = findViewById(R.id.bt_quality);
        audioManager = (AudioManager) getSystemService(Context.AUDIO_SERVICE);
        vol_close = findViewById(R.id.vol_close);
        recyclerViewIcon = findViewById(R.id.recyclerview_icon);
        nightMode = findViewById(R.id.night_mode);
        txtMovieName = findViewById(R.id.txtMovieName);
        bt_settings = findViewById(R.id.bt_settings);
        /* -- swipe -- */
        vol_text = findViewById(R.id.vol_text);
        brt_text = findViewById(R.id.brt_text);
        vol_progress = findViewById(R.id.vol_progress);
        brt_progress = findViewById(R.id.brt_progress);
        vol_progress_container = findViewById(R.id.vol_progress_container);
        brt_progress_container = findViewById(R.id.brt_progress_container);
        vol_text_container = findViewById(R.id.vol_text_container);
        brt_text_container = findViewById(R.id.brt_text_container);
        vol_icon = findViewById(R.id.vol_icon);
        brt_icon = findViewById(R.id.brt_icon);
        audioManager2 = (AudioManager) getSystemService(Context.AUDIO_SERVICE);
        gridView = findViewById(R.id.gridView);
    }

    // Extras
    private void getExtras() {
        Bundle extras = this.getIntent().getExtras();
        nguonPhim = extras.getString("NguonPhim").trim();
        tenPhim = extras.getString("TenPhim").trim();
        maTL = extras.getString("MaTL");
        GetDataTheoTheLoai(urlGetDataTL, maTL);
    }

    // Lấy dữ liệu phim theo thể loại
    private void GetDataTheoTheLoai(String url, String matl) {
        url += "?maTheLoai=" + matl;
        RequestQueue requestQueue = Volley.newRequestQueue(this);
        JsonArrayRequest jsonArrayRequest = new JsonArrayRequest(Request.Method.GET, url, null, new Response.Listener<JSONArray>() {
            @Override
            public void onResponse(JSONArray response) {
                data.clear();
                for (int i = 0; i < 4; i++) {
                    try {
                        JSONObject object = response.getJSONObject(i);
                        data.add(new GridItemModel(
                                object.getString("anhBia"),
                                object.getString("tenPhim"),
                                object.getString("maPhim")
                        ));
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                    gridAdapter = new GridAdapter(WatchActivity.this, R.layout.grid_item, data);
                    gridView.setAdapter(gridAdapter);
                }

            }
        },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Toast.makeText(WatchActivity.this, "Lỗi!" + error.toString(), Toast.LENGTH_LONG).show();
                    }
                }
        );
        requestQueue.add(jsonArrayRequest);
    }

    // Kiểm tra các quyền download
    private void downloadFile() {
        Helper.showToast(WatchActivity.this, "Đang bắt đầu tải...");
        Helper.download(WatchActivity.this, nguonPhim);
    }

    private void checkPermission() {
        ArrayList<String> checkPermission = new ArrayList<>();
        checkPermission.add(Manifest.permission.WRITE_EXTERNAL_STORAGE);
        checkPermission.add(Manifest.permission.READ_EXTERNAL_STORAGE);

        Dexter.withContext(WatchActivity.this)
                .withPermissions(checkPermission).withListener(new MultiplePermissionsListener() {
                    @Override
                    public void onPermissionsChecked(MultiplePermissionsReport report) {
                        if (report.areAllPermissionsGranted()) {
                            // Nếu quyền thỏa thì đc phép tải
                            downloadFile();
                        } else {
                            ArrayList<String> mArrayPermissions = new ArrayList<>();
                            List<PermissionDeniedResponse> mDeniedPermission = report.getDeniedPermissionResponses();
                            for (PermissionDeniedResponse deniedResponse : mDeniedPermission) {
                                mArrayPermissions.add(deniedResponse.getPermissionName());
                            }
                        }
                    }

                    @Override
                    public void onPermissionRationaleShouldBeShown(List<PermissionRequest> permissions, PermissionToken token) {
                        token.continuePermissionRequest();
                    }
                }).check();
    }
}