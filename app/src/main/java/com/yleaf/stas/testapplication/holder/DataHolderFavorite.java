package com.yleaf.stas.testapplication.holder;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.yleaf.stas.testapplication.R;
import com.yleaf.stas.testapplication.adapter.DataAdapterFavorite;
import com.yleaf.stas.testapplication.models.Data;
import com.yleaf.stas.testapplication.popup_menu.PopupMenuFavorite;

public class DataHolderFavorite extends RecyclerView.ViewHolder implements View.OnClickListener {

    private Data data;
    private DataAdapterFavorite dataAdapterFavorite;

    private ImageView imageView;
    private TextView textViewName;
    private TextView textViewArtistName;
    private ImageButton imageButton;

    private Context context;

    public DataHolderFavorite(@NonNull View itemView, Context context, DataAdapterFavorite dataAdapterFavorite) {
        super(itemView);
        this.context = context;
        this.dataAdapterFavorite = dataAdapterFavorite;

        itemView.setOnClickListener(this);

        imageView = itemView.findViewById(R.id.item_image_view);
        textViewName = itemView.findViewById(R.id.item_name);
        textViewArtistName = itemView.findViewById(R.id.item_artist_name);
        imageButton = itemView.findViewById(R.id.item_image_button);
    }

    public void bindData(Data dataObj, final int position) {
        this.data = dataObj;

        Glide.with(context).load(data.getArtworkUrl100()).into(imageView);

        textViewName.setText(data.getName());
        textViewArtistName.setText(data.getArtistName());

        imageButton.setOnClickListener(view -> new PopupMenuFavorite(view, data, position, context, dataAdapterFavorite).showMenu());
    }

    @Override
    public void onClick(View view) {
        Toast.makeText(context, String.valueOf(data.getId()), Toast.LENGTH_SHORT).show();
    }
}
