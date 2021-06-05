package com.yunzhu.module.common.update.receiver;

import android.app.DownloadManager;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;

import com.yunzhu.module.common.update.util.UpdaterUtils;
import com.orhanobut.logger.Logger;

public class ApkInstallReceiver extends BroadcastReceiver {

    @Override
    public void onReceive(Context context, Intent intent) {
        if (DownloadManager.ACTION_DOWNLOAD_COMPLETE.equals(intent.getAction())) {
            long downloadApkId = intent.getLongExtra(DownloadManager.EXTRA_DOWNLOAD_ID, -1);
            long localDownloadId = UpdaterUtils.getLocalDownloadId(context);
            if (downloadApkId == localDownloadId) {
                Logger.d("download complete. downloadId is " + downloadApkId);
                installApk(context, downloadApkId);
            }
        } else if (DownloadManager.ACTION_NOTIFICATION_CLICKED.equals(intent.getAction())) {
            //处理 如果还未完成下载，用户点击Notification
            Intent viewDownloadIntent = new Intent(DownloadManager.ACTION_VIEW_DOWNLOADS);
            viewDownloadIntent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            context.startActivity(viewDownloadIntent);
        }
    }

    private static void installApk(Context context, long downloadApkId) {
        DownloadManager dManager = (DownloadManager) context.getSystemService(Context.DOWNLOAD_SERVICE);
        if (dManager == null) {
            return;
        }
        Uri downloadFileUri = dManager.getUriForDownloadedFile(downloadApkId);
        if (downloadFileUri != null) {
            Logger.d("file location " + downloadFileUri.toString());
            UpdaterUtils.startInstall(context, downloadFileUri);
        } else {
            Logger.d("download failed");
        }
    }
}
