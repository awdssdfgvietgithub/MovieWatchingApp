package com.example.chitietphim.Dialog;

import android.app.Dialog;
import android.content.Context;
import android.media.AudioManager;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.SeekBar;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatDialogFragment;
import androidx.core.content.ContextCompat;

import com.example.chitietphim.R;

public class VolumeDialog extends AppCompatDialogFragment {

    private ImageView cross, vol;
    private TextView volume_no;
    public SeekBar seekBar;
    AudioManager audioManager;

    @NonNull
    @Override
    public Dialog onCreateDialog(@Nullable Bundle savedInstanceState) {
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        LayoutInflater inflater = getActivity().getLayoutInflater();
        View view = inflater.inflate(R.layout.vol_dialog, null);
        builder.setView(view);
        getActivity().setVolumeControlStream(AudioManager.STREAM_MUSIC);

        cross = view.findViewById(R.id.vol_close);
        volume_no = view.findViewById(R.id.vol_number);
        seekBar = view.findViewById(R.id.vol_seekbar);
        vol = view.findViewById(R.id.vol_dialog_icon);

        audioManager = (AudioManager) getContext().getSystemService(Context.AUDIO_SERVICE);
        seekBar.setMax(audioManager.getStreamMaxVolume(AudioManager.STREAM_MUSIC));
        seekBar.setProgress(audioManager.getStreamVolume(AudioManager.STREAM_MUSIC));
        int mediaVolume = audioManager.getStreamVolume(AudioManager.STREAM_MUSIC);
        // set hình icon của volume
        if (mediaVolume == 0) {
            vol.setImageDrawable(ContextCompat
                    .getDrawable(getContext(), R.drawable.baseline_volume_off));
        } else {
            vol.setImageDrawable(ContextCompat
                    .getDrawable(getContext(), R.drawable.baseline_volume_up));
        }
        // kiểm tra trạng thái (số volume và icon)
        seekBar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                audioManager.setStreamVolume(AudioManager.STREAM_MUSIC, progress, 0);
                int mediavolume = audioManager.getStreamVolume(AudioManager.STREAM_MUSIC);
                int maxvol = audioManager.getStreamMaxVolume(AudioManager.STREAM_MUSIC);
                double volPer = Math.ceil((((double) mediavolume / (double) maxvol) * (double) 100));
                volume_no.setText("" + volPer);
                if (mediavolume == 0) {
                    vol.setImageDrawable(ContextCompat
                            .getDrawable(getContext(), R.drawable.baseline_volume_off));
                } else {
                    vol.setImageDrawable(ContextCompat
                            .getDrawable(getContext(), R.drawable.baseline_volume_up));
                }
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {

            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {

            }
        });
        cross.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dismiss();
            }
        });
        return builder.create();
    }
}
