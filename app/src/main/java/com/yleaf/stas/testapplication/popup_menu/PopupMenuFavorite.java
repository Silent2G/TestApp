package com.yleaf.stas.testapplication.popup_menu;

import android.app.Activity;
import android.content.Context;
import android.view.MenuInflater;
import android.view.View;
import android.widget.PopupMenu;

import com.yleaf.stas.testapplication.R;
import com.yleaf.stas.testapplication.adapter.DataAdapterFavorite;
import com.yleaf.stas.testapplication.db.service.BookService;
import com.yleaf.stas.testapplication.db.service.FavoriteService;
import com.yleaf.stas.testapplication.db.service.MovieService;
import com.yleaf.stas.testapplication.db.service.PodcastService;
import com.yleaf.stas.testapplication.models.Data;

public class PopupMenuFavorite {

    private View view;
    private final Data data;
    private final int position;
    private Context context;
    private DataAdapterFavorite dataAdapterFavorite;

    private static final String TAG = PopupMenuFavorite.class.getSimpleName();

    public PopupMenuFavorite(View view, Data data, int position, Context context, DataAdapterFavorite dataAdapterFavorite) {
        this.view = view;
        this.data = data;
        this.position = position;
        this.context = context;
        this.dataAdapterFavorite = dataAdapterFavorite;
    }

    public void showMenu() {

        PopupMenu popup = new PopupMenu(view.getContext(),view);
        MenuInflater inflater = popup.getMenuInflater();
        inflater.inflate(R.menu.popup_menu_favorite, popup.getMenu());

        popup.setOnMenuItemClickListener(menuItem -> {
            if(menuItem.getItemId() == R.id.item_delete_from_favorite) {

                dataAdapterFavorite.dataList.remove(position);
                dataAdapterFavorite.notifyItemRemoved(position);
                dataAdapterFavorite.notifyItemRangeChanged(position, dataAdapterFavorite.dataList.size());

                new FavoriteService(context).deleteById(data.getId());

                showPlaceHolder();

                switch (data.getKind()) {
                    case "book":
                        new BookService(context).save(data);
                        break;

                    case "movie":
                        new MovieService(context).save(data);
                        break;

                    case "podcast":
                        new PodcastService(context).save(data);
                        break;
                }
            }
            return false;
        });
        popup.show();
    }

    private void showPlaceHolder() {
        switch (data.getKind()) {
            case "book":
                if(new FavoriteService(context).isEmptyCurrentKind("book")) {
                    (((Activity) context).findViewById(R.id.text_view_group_name_audio_books)).setVisibility(View.VISIBLE);
                    (((Activity) context).findViewById(R.id.text_view_content_audio_books)).setVisibility(View.VISIBLE);
                }
                break;

            case "movie":
                if(new FavoriteService(context).isEmptyCurrentKind("movie")) {
                    (((Activity) context).findViewById(R.id.text_view_group_name_movies)).setVisibility(View.VISIBLE);
                    (((Activity) context).findViewById(R.id.text_view_content_movies)).setVisibility(View.VISIBLE);
                }
                break;

            case "podcast":
                if(new FavoriteService(context).isEmptyCurrentKind("podcast")) {
                    (((Activity) context).findViewById(R.id.text_view_group_name_podcasts)).setVisibility(View.VISIBLE);
                    (((Activity) context).findViewById(R.id.text_view_content_podcasts)).setVisibility(View.VISIBLE);
                }
                break;
        }
    }
}
