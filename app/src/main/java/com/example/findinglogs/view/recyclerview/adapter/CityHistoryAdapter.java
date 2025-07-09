package com.example.findinglogs.view.recyclerview.adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import com.example.findinglogs.R;

import java.util.List;

public class CityHistoryAdapter extends RecyclerView.Adapter<CityHistoryAdapter.ViewHolder> {

    public static class CityHistoryItem {
        public String city;
        public String timestamp;
        public CityHistoryItem(String city, String timestamp) {
            this.city = city;
            this.timestamp = timestamp;
        }
    }

    private final List<CityHistoryItem> cityHistoryList;

    public CityHistoryAdapter(List<CityHistoryItem> cityHistoryList) {
        this.cityHistoryList = cityHistoryList;
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        private final TextView tvCidade;
        private final TextView tvTimestamp;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            tvCidade = itemView.findViewById(R.id.tvCidade);
            tvTimestamp = itemView.findViewById(R.id.tvTimestamp);
        }

        public void bind(CityHistoryItem item) {
            tvCidade.setText(item.city);
            tvTimestamp.setText(item.timestamp);
        }
    }

    @NonNull
    @Override
    public CityHistoryAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_city_history, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull CityHistoryAdapter.ViewHolder holder, int position) {
        holder.bind(cityHistoryList.get(position));
    }

    @Override
    public int getItemCount() {
        return cityHistoryList.size();
    }
}
