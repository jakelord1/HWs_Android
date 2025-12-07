package com.jkld.hw_3;

import android.graphics.Color;
import android.os.Bundle;
import android.widget.RadioGroup;
import android.widget.RelativeLayout;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;
import retrofit2.http.GET;

public class MainActivity extends AppCompatActivity {
    RecyclerView recyclerView;
    SongAdapter adapter;
    List<Songs> songList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        RelativeLayout root = findViewById(R.id.rootRL);

        recyclerView = findViewById(R.id.songList);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        songList = new ArrayList<>();
        songList.add(new Songs(R.drawable.icon1, "Crazy", "Creo", "3:36"));
        songList.add(new Songs(R.drawable.icon2, "Engine's burning out", "Venjent", "3:39"));
        songList.add(new Songs(R.drawable.icon5, "Classical VIP", "Nightkilla", "3:15"));
        songList.add(new Songs(R.drawable.icon3, "Hammer it home", "Venjent", "3:10"));
        songList.add(new Songs(R.drawable.icon4, "Messages from the stars", "The RAH band", "7:41"));
        songList.add(new Songs(R.drawable.icon5, "Make own music", "Venjent", "3:12"));

        adapter = new SongAdapter(songList, this);
        recyclerView.setAdapter(adapter);

    }
}