package com.jkld.hw_service;

import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.Service;
import android.app.usage.UsageStats;
import android.app.usage.UsageStatsManager;
import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.os.Handler;
import android.os.IBinder;

import androidx.core.app.NotificationCompat;

import org.jetbrains.annotations.Nullable;

import java.util.List;
import java.util.SortedMap;
import java.util.TreeMap;

public class ForegroundMonitorService extends Service {
    private static final String CHANNEL_ID = "MonitorServiceChannel";

    private Handler handler;
    private Runnable runnable;
    private String currentPackageName;

    @Override
    public void onCreate() {
        super.onCreate();
        currentPackageName = getPackageName();
        createNotificationChannel();

        Notification notification = buildNotification();
        startForeground(1, notification);

        handler = new Handler();
        runnable = new Runnable() {
            @Override
            public void run() {
                checkForegroundStatusAndRelaunch();
                handler.postDelayed(this, 10000);
            }
        };
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        handler.post(runnable);
        return START_STICKY;
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        handler.removeCallbacks(runnable);
    }

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }
    private void checkForegroundStatusAndRelaunch() {
        String foregroundApp = getForegroundAppPackage();
        if (foregroundApp != null && !foregroundApp.equals(currentPackageName)) {
            launchActivityToForeground();
        }
    }

    private String getForegroundAppPackage() {
        if (!MainActivity.hasUsageStatsPermission(this)) {
            stopSelf();
            return null;
        }

        UsageStatsManager usageStatsManager = (UsageStatsManager) getSystemService(Context.USAGE_STATS_SERVICE);
        if (usageStatsManager == null) {
            return null;
        }

        long time = System.currentTimeMillis();
        List<UsageStats> stats = usageStatsManager.queryUsageStats(
                UsageStatsManager.INTERVAL_DAILY, time - 1000, time);

        if (stats != null) {
            SortedMap<Long, UsageStats> runningTasks = new TreeMap<>();
            for (UsageStats usageStats : stats) {
                if (usageStats.getLastTimeUsed() > time - 1000) {
                    runningTasks.put(usageStats.getLastTimeUsed(), usageStats);
                }
            }

            if (!runningTasks.isEmpty()) {
                return runningTasks.get(runningTasks.lastKey()).getPackageName();
            }
        }
        return null;
    }

    private void launchActivityToForeground() {
        Intent intent = new Intent(this, MainActivity.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK |
                Intent.FLAG_ACTIVITY_SINGLE_TOP |
                Intent.FLAG_ACTIVITY_CLEAR_TOP);
        startActivity(intent);
    }

    private void createNotificationChannel() {
        NotificationChannel serviceChannel = new NotificationChannel(
                CHANNEL_ID,
                "Monitoring test",
                NotificationManager.IMPORTANCE_LOW
        );
        NotificationManager manager = getSystemService(NotificationManager.class);
        if (manager != null) {
            manager.createNotificationChannel(serviceChannel);
        }
    }

    private Notification buildNotification() {
        return new NotificationCompat.Builder(this, CHANNEL_ID)
                .setContentTitle("Testing 1 2 3")
                .setContentText("My app is Messenger Max")
                .setPriority(NotificationCompat.PRIORITY_LOW)
                .build();
    }
}
