package com.example.findinglogs.view;

import android.content.Intent;
import android.content.IntentFilter;
import android.net.ConnectivityManager;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.RecyclerView;

import com.example.findinglogs.R;
import com.example.findinglogs.model.broadcast.NetworkChangeReceiver;
import com.example.findinglogs.model.model.Weather;
import com.example.findinglogs.model.util.NetworkMonitor;
import com.example.findinglogs.view.recyclerview.adapter.WeatherListAdapter;
import com.example.findinglogs.viewmodel.MainViewModel;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    private WeatherListAdapter adapter;
    private final List<Weather> weathers = new ArrayList<>();
    private NetworkMonitor networkMonitor;
    private NetworkChangeReceiver networkReceiver;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        networkMonitor = new NetworkMonitor(this);
        MainViewModel mainViewModel = new ViewModelProvider(this).get(MainViewModel.class);
        RecyclerView recyclerView = findViewById(R.id.recycler_view_weather);
        FloatingActionButton fetchButton = findViewById(R.id.fetchButton);
        adapter = new WeatherListAdapter(this, weathers);
        recyclerView.setAdapter(adapter);
        mainViewModel.getWeatherList().observe(this,
                weathers -> adapter.updateWeathers(weathers));
            fetchButton.setOnClickListener(v -> {
                mainViewModel.fetchAllForecasts();
                Toast.makeText(MainActivity.this, "TO UPDATED", Toast.LENGTH_SHORT).show();
            }
        );
    }

    @Override
    protected void onResume() {
        super.onResume();
        networkReceiver = new NetworkChangeReceiver();
        IntentFilter filter = new IntentFilter(ConnectivityManager.CONNECTIVITY_ACTION);
        registerReceiver(networkReceiver, filter);
        networkMonitor.register();
    }

    @Override
    protected void onPause() {
        super.onPause();
        unregisterReceiver(networkReceiver);
        networkMonitor.unregister();
    }
}