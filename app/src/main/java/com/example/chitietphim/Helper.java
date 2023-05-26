package com.example.chitietphim;

import android.app.DownloadManager;
import android.content.Context;
import android.net.Uri;
import android.os.Environment;
import android.util.Log;
import android.widget.Toast;

import java.io.File;

public class Helper {
    public static void download(Context context, String link) {
        String[] tempLinks = link.split("/");
        String fileName = tempLinks[tempLinks.length - 1];
        Log.e("file name", "file name" + fileName);
        File file = Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DOWNLOADS);
        String getPath = file.getAbsolutePath() + "/" + fileName;
        Log.e("file path", "file path" + getPath);
        DownloadManager mgr = (DownloadManager) context.getSystemService(Context.DOWNLOAD_SERVICE);
        Uri uri = Uri.parse(link);
        DownloadManager.Request request = new DownloadManager.Request(uri);
        request.setTitle(fileName);
        request.setDescription("Đang tải...");
        request.setNotificationVisibility(DownloadManager.Request.VISIBILITY_VISIBLE_NOTIFY_COMPLETED);
        request.setVisibleInDownloadsUi(true);
        request.setDestinationInExternalPublicDir(Environment.DIRECTORY_DOWNLOADS, "infiniteCode_" + fileName);

        mgr.enqueue(request);
    }

    public static void showToast(Context context, String mess) {
        Toast.makeText(context, mess, Toast.LENGTH_LONG).show();
    }
}
