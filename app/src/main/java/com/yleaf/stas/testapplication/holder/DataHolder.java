package com.yleaf.stas.testapplication.holder;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.yleaf.stas.testapplication.R;
import com.yleaf.stas.testapplication.activities.items.AudioBookActivity;
import com.yleaf.stas.testapplication.activities.items.MovieActivity;
import com.yleaf.stas.testapplication.activities.items.PodcastActivity;
import com.yleaf.stas.testapplication.adapter.DataAdapter;
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
        startNewActivity();
    }

    private void startNewActivity() {
        switch (data.getKind()) {
            case "book":
                context.startActivity(AudioBookActivity.newIntent(context, data.getId()));
                break;

            case "movie":
                context.startActivity(MovieActivity.newIntent(context, data.getId()));
                break;

            case "podcast":
                context.startActivity(PodcastActivity.newIntent(context, data.getId()));
                break;
        }
    }
}
