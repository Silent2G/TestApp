package com.yleaf.stas.testapplication.adapter;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.yleaf.stas.testapplication.R;
import com.yleaf.stas.testapplication.holder.DataHolder;
import com.yleaf.stas.testapplication.models.Data;

import java.util.ArrayList;
import java.util.List;

public class DataAdapter extends RecyclerView.Adapter<DataHolder> {
    public ArrayList<Data> dataList;
    private Context context;

    public DataAdapter(ArrayList<Data> data, Context context) {
        this.dataList = data;
        this.context = context;
    }

    @NonNull
    @Override
    public DataHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        LayoutInflater layoutInflater = LayoutInflater.from(context);
        View view = layoutInflater.inflate(R.layout.item, viewGroup, false);
        return new DataHolder(view, context, this);
    }

    @Override
    public void onBindViewHolder(@NonNull DataHolder dataHolder, int i) {
        Data data = dataList.get(i);
        dataHolder.bindData(data, i);
    }

    @Override
    public int getItemCount() {
        return dataList.size();
    }
}