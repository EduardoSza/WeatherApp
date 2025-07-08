package com.example.findinglogs.view;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.example.findinglogs.R;

public class DetailsActivity extends AppCompatActivity {

    private TextView tvCidade, tvTempAtual, tvTempMax, tvTempMin, tvPressao, tvUmidade;

    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_details);

        tvCidade = findViewById(R.id.tv_cidade);
        tvTempAtual = findViewById(R.id.tv_temp_atual);
        tvTempMax = findViewById(R.id.tv_temp_max);
        tvTempMin = findViewById(R.id.tv_temp_min);
        tvPressao = findViewById(R.id.tv_pressao);
        tvUmidade = findViewById(R.id.tv_umidade);

        // Receber os dados da intent
        String cidade = getIntent().getStringExtra("cidade");
        String tempAtual = getIntent().getStringExtra("tempAtual");
        String tempMax = getIntent().getStringExtra("tempMax");
        String tempMin = getIntent().getStringExtra("tempMin");
        String pressao = getIntent().getStringExtra("pressao");
        String umidade = getIntent().getStringExtra("umidade");

        // Exibir dados
        tvCidade.setText("Cidade: " + cidade);
        tvTempAtual.setText("Temperatura Atual: " + tempAtual);
        tvTempMax.setText("Temperatura Máxima: " + tempMax);
        tvTempMin.setText("Temperatura Mínima: " + tempMin);
        tvPressao.setText("Pressão: " + pressao + " hPa");
        tvUmidade.setText("Umidade: " + umidade + "%");
    }
}
