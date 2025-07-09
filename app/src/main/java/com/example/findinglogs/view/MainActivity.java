package com.example.findinglogs.view;

import android.content.Intent;
import android.content.IntentFilter;
import android.database.Cursor;
import android.net.ConnectivityManager;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.findinglogs.R;
import com.example.findinglogs.model.broadcast.NetworkChangeReceiver;
import com.example.findinglogs.model.database.WeatherContract;
import com.example.findinglogs.model.model.Weather;
import com.example.findinglogs.model.util.NetworkMonitor;
import com.example.findinglogs.view.recyclerview.adapter.CityHistoryAdapter;
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

    private void exibirHistoricoDoContentProvider() {
        // 1. Encontre o RecyclerView no seu layout
        RecyclerView recyclerViewHistorico = findViewById(R.id.recyclerViewHistorico);

        // 2. Crie a lista que será enviada para o Adapter
        List<CityHistoryAdapter.CityHistoryItem> historicoList = new ArrayList<>();

        // 3. Defina as colunas que você quer buscar no Content Provider
        String[] projection = {
                WeatherContract.WeatherEntry.COLUMN_CITY_NAME,
                WeatherContract.WeatherEntry.COLUMN_TIMESTAMP
        };

        // 4. Execute a consulta (query) no ContentProvider
        Cursor cursor = getContentResolver().query(
                WeatherContract.WeatherEntry.CONTENT_URI,
                projection,
                null,
                null,
                WeatherContract.WeatherEntry.COLUMN_TIMESTAMP + " DESC" // Ordena pelos mais recentes
        );

        // 5. Verifique se a consulta retornou dados e processe-os
        if (cursor != null) {
            // Pega o índice de cada coluna para otimizar o acesso dentro do loop
            int cityColumnIndex = cursor.getColumnIndexOrThrow(WeatherContract.WeatherEntry.COLUMN_CITY_NAME);
            int timestampColumnIndex = cursor.getColumnIndexOrThrow(WeatherContract.WeatherEntry.COLUMN_TIMESTAMP);

            while (cursor.moveToNext()) {
                // Extrai os dados brutos do cursor
                String nomeCidade = cursor.getString(cityColumnIndex);
                long timestampMillis = cursor.getLong(timestampColumnIndex);

                // Formata o timestamp (que está em milissegundos) para uma String legível
                String dataFormatada = new java.text.SimpleDateFormat("dd/MM/yyyy HH:mm", java.util.Locale.getDefault())
                        .format(new java.util.Date(timestampMillis));

                // Cria o objeto que o Adapter espera e adiciona na lista
                historicoList.add(new CityHistoryAdapter.CityHistoryItem(nomeCidade, dataFormatada));
            }
            cursor.close(); // **MUITO IMPORTANTE: Sempre feche o cursor!**
        }

        // 6. Configure o RecyclerView com o Adapter
        CityHistoryAdapter historyAdapter = new CityHistoryAdapter(historicoList);
        recyclerViewHistorico.setAdapter(historyAdapter);
        recyclerViewHistorico.setLayoutManager(new androidx.recyclerview.widget.LinearLayoutManager(this));
    }

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
        exibirHistoricoDoContentProvider();
        registerReceiver(networkReceiver, filter);
        networkMonitor.register();
    }

    @Override
    protected void onPause() {
        super.onPause();
        unregisterReceiver(networkReceiver);
        networkMonitor.unregister();
    }

    @Override
    protected void onDestroy(){
        super.onDestroy();
    }
}