package com.lvl.homecooknotifications;

import android.Manifest;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.app.NotificationCompat;
import androidx.core.app.NotificationManagerCompat;
import android.widget.Button;

public class MainActivity extends AppCompatActivity {
    private ActivityResultLauncher<String> notifPerm;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        createChannels();
        notifPerm = registerForActivityResult(
                new ActivityResultContracts.RequestPermission(),
                granted -> {}
        );
        requestNotifPermissionIfNeeded();
        Button btnRecipeSuggestion = findViewById(R.id.btnRecipeSuggestion);
        Button btnNewRecipe = findViewById(R.id.btnNewRecipe);
        Button btnUploadImage = findViewById(R.id.btnUploadImage);
        Button btnCancelUpload = findViewById(R.id.btnCancelUpload);
        btnRecipeSuggestion.setOnClickListener(v -> showRecipeSuggestionNotification());
        btnNewRecipe.setOnClickListener(v -> showNewRecipeAlert());
        btnUploadImage.setOnClickListener(v -> showImageUploadProgress());
        btnCancelUpload.setOnClickListener(v -> NotificationManagerCompat.from(this).cancel(103));
    }

    private void requestNotifPermissionIfNeeded() {
        if (Build.VERSION.SDK_INT >= 33) {
            if (checkSelfPermission(Manifest.permission.POST_NOTIFICATIONS) != PackageManager.PERMISSION_GRANTED) {
                notifPerm.launch(Manifest.permission.POST_NOTIFICATIONS);
            }
        }
    }

    private void createChannels() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            NotificationChannel suggestions = new NotificationChannel(
                    "recipe_suggestions", "Предложения рецептов", NotificationManager.IMPORTANCE_DEFAULT);
            NotificationChannel newRecipes = new NotificationChannel(
                    "new_recipes", "Новые рецепты", NotificationManager.IMPORTANCE_HIGH);
            NotificationChannel uploading = new NotificationChannel(
                    "uploading_image", "Загрузка изображения", NotificationManager.IMPORTANCE_LOW);

            NotificationManager nm = getSystemService(NotificationManager.class);
            nm.createNotificationChannel(suggestions);
            nm.createNotificationChannel(newRecipes);
            nm.createNotificationChannel(uploading);
        }
    }

    private PendingIntent appPendingIntent() {
        Intent intent = new Intent(this, MainActivity.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TOP);
        int flags = Build.VERSION.SDK_INT >= 23 ? PendingIntent.FLAG_IMMUTABLE : 0;
        return PendingIntent.getActivity(this, 0, intent, flags);
    }

    private void showRecipeSuggestionNotification() {
        String title = "Рецепт по вашему фото!";
        String text = "Мы подобрали рецепт 'Салат Цезарь'. Нажмите, чтобы посмотреть.";
        NotificationCompat.BigTextStyle style = new NotificationCompat.BigTextStyle()
                .setBigContentTitle(title)
                .bigText(text);

        NotificationCompat.Builder b = new NotificationCompat.Builder(this, "recipe_suggestions")
                .setSmallIcon(R.drawable.ic_app_icon)
                .setContentTitle(title)
                .setContentText("Нажмите, чтобы открыть приложение")
                .setStyle(style)
                .setLargeIcon(BitmapFactory.decodeResource(getResources(), R.mipmap.ic_launcher))
                .setContentIntent(appPendingIntent())
                .setAutoCancel(true)
                .setWhen(System.currentTimeMillis())
                .setPriority(NotificationCompat.PRIORITY_DEFAULT);

        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.POST_NOTIFICATIONS) != PackageManager.PERMISSION_GRANTED) {
            return;
        }
        NotificationManagerCompat.from(this).notify(101, b.build());
    }

    private void showNewRecipeAlert() {
        String title = "Новый рецепт в вашей любимой категории!";
        String text = "Добавлен рецепт 'Паста Карбонара'.";
        Intent webIntent = new Intent(Intent.ACTION_VIEW, Uri.parse("https://www.google.com/search?q=паста+карбонара+рецепт"));
        int flags = Build.VERSION.SDK_INT >= 23 ? PendingIntent.FLAG_IMMUTABLE : 0;
        PendingIntent webPendingIntent = PendingIntent.getActivity(this, 1, webIntent, flags);

        NotificationCompat.Builder b = new NotificationCompat.Builder(this, "new_recipes")
                .setSmallIcon(R.drawable.ic_app_icon)
                .setContentTitle(title)
                .setContentText(text)
                .setContentIntent(appPendingIntent())
                .addAction(new NotificationCompat.Action(0, "Посмотреть", webPendingIntent))
                .setVibrate(new long[]{0, 300, 150, 300})
                .setAutoCancel(true)
                .setPriority(NotificationCompat.PRIORITY_HIGH);

        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.POST_NOTIFICATIONS) != PackageManager.PERMISSION_GRANTED) {
            return;
        }
        NotificationManagerCompat.from(this).notify(102, b.build());
    }

    private void showImageUploadProgress() {
        NotificationCompat.Builder b = new NotificationCompat.Builder(this, "uploading_image")
                .setSmallIcon(R.drawable.ic_app_icon)
                .setContentTitle("Загрузка вашего фото…")
                .setContentText("Идет обработка изображения")
                .setOngoing(true)
                .setOnlyAlertOnce(true)
                .setProgress(0, 0, true)
                .setContentIntent(appPendingIntent())
                .setPriority(NotificationCompat.PRIORITY_LOW);

        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.POST_NOTIFICATIONS) != PackageManager.PERMISSION_GRANTED) {
            return;
        }
        NotificationManagerCompat.from(this).notify(103, b.build());
    }
}