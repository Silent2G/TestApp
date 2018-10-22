package com.yleaf.stas.testapplication.holder;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.yleaf.stas.testapplication.R;
import com.yleaf.stas.testapplication.adapter.DataAdapter;
import com.yleaf.stas.testapplication.fragments.items.ItemFragmentAudioBooks;
import com.yleaf.stas.testapplication.fragments.items.ItemFragmentMovies;
import com.yleaf.stas.testapplication.fragments.items.ItemFragmentPodcasts;
import com.yleaf.stas.testapplication.models.Data;
import com.yleaf.stas.testapplication.popup_menu.PopupMenuCategories;

public class DataHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

    private Data data;
    private DataAdapter dataAdapter;

    private ImageView imageView;
    private TextView textViewName;
    private TextView textViewArtistName;
    private ImageButton imageButton;

    private Context context;

    public DataHolder(@NonNull View itemView, Context context, DataAdapter dataAdapter) {
        super(itemView);
        this.context = context;
        this.dataAdapter = dataAdapter;

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

        imageButton.setOnClickListener(view -> new PopupMenuCategories(view, data, position, context, dataAdapter).showMenu());
    }

    @Override
    public void onClick(View view) {
        Toast.makeText(context, String.valueOf(data.getId()), Toast.LENGTH_SHORT).show();
        startNewFragment();
    }

    private void startNewFragment() {
        FragmentManager fragmentManager = ((FragmentActivity) context).getSupportFragmentManager();

        switch (data.getKind()) {
            case "book":
                fragmentManager.beginTransaction()
                        .replace(R.id.frame_layout, ItemFragmentAudioBooks.newInstance(data.getId()))
                        .addToBackStack(null)
                        .commit();
                break;

            case "movie":
                fragmentManager.beginTransaction()
                        .replace(R.id.frame_layout, ItemFragmentMovies.newInstance(data.getId()))
                        .addToBackStack(null)
                        .commit();
                break;

            case "podcast":
                fragmentManager.beginTransaction()
                        .replace(R.id.frame_layout, ItemFragmentPodcasts.newInstance(data.getId()))
                        .addToBackStack(null)
                        .commit();
                break;
        }

    }
}
