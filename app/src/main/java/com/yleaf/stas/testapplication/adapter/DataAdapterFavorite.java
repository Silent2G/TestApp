package com.yleaf.stas.testapplication.adapter;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.yleaf.stas.testapplication.R;
import com.yleaf.stas.testapplication.holder.DataHolderFavorite;
import com.yleaf.stas.testapplication.models.Data;

import java.util.List;

public class DataAdapterFavorite extends RecyclerView.Adapter<DataHolderFavorite> {

    public List<Data> dataList;
    private Context context;

    public DataAdapterFavorite(List<Data> data, Context context) {
        this.dataList = data;
        this.context = context;
    }

    @NonNull
    @Override
    public DataHolderFavorite onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        LayoutInflater layoutInflater = LayoutInflater.from(context);
        View view = layoutInflater.inflate(R.layout.item, viewGroup, false);
        return new DataHolderFavorite(view, context, this);
    }

    @Override
    public void onBindViewHolder(@NonNull DataHolderFavorite dataHolderFavorite, int i) {
        Data data = dataList.get(i);
        dataHolderFavorite.bindData(data, i);
    }

    @Override
    public int getItemCount() {
        return dataList.size();
    }
}
