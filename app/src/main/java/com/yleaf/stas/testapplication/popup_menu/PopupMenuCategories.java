package com.yleaf.stas.testapplication.popup_menu;

import android.app.Activity;
import android.content.Context;
import android.view.MenuInflater;
import android.view.View;
import android.widget.PopupMenu;

import com.yleaf.stas.testapplication.R;
import com.yleaf.stas.testapplication.adapter.DataAdapter;
import com.yleaf.stas.testapplication.db.service.BookService;
import com.yleaf.stas.testapplication.db.service.FavoriteService;
import com.yleaf.stas.testapplication.db.service.MovieService;
import com.yleaf.stas.testapplication.db.service.PodcastService;
import com.yleaf.stas.testapplication.models.Data;

public class PopupMenuCategories {

    private View view;
    private final Data data;
    private final int position;
    private Context context;
    private DataAdapter dataAdapter;

    private static final String TAG = PopupMenuCategories.class.getSimpleName();

    public PopupMenuCategories(View view, Data data, int position, Context context, DataAdapter dataAdapter) {
        this.view = view;
        this.data = data;
        this.position = position;
        this.context = context;
        this.dataAdapter = dataAdapter;
    }

    public void showMenu() {

        PopupMenu popup = new android.widget.PopupMenu(view.getContext(), view);
        MenuInflater inflater = popup.getMenuInflater();
        inflater.inflate(R.menu.popup_menu, popup.getMenu());

        popup.setOnMenuItemClickListener(menuItem -> {
            if(menuItem.getItemId() == R.id.item_add_to_favorite) {

                switch (data.getKind()) {
                    case "book":
                        new BookService(context).deleteById(data.getId());
                        break;

                    case "movie":
                        new MovieService(context).deleteById(data.getId());
                        break;

                    case "podcast":
                        new PodcastService(context).deleteById(data.getId());
                        break;
                }

                dataAdapter.dataList.remove(position);
                dataAdapter.notifyItemRemoved(position);
                dataAdapter.notifyItemRangeChanged(position, dataAdapter.dataList.size());

                new FavoriteService(context).save(data);

                showPlaceHolder();
            }
            return false;
        });
        popup.show();
    }

    private void showPlaceHolder() {
        switch (data.getKind()) {
            case "book":
                if(new BookService(context).isEmpty()) {
                    (((Activity) context).findViewById(R.id.text_view_group_name_audio_books)).setVisibility(View.VISIBLE);
                    (((Activity) context).findViewById(R.id.text_view_content_audio_books)).setVisibility(View.VISIBLE);
                }
                break;

            case "movie":
                if(new MovieService(context).isEmpty()) {
                    (((Activity) context).findViewById(R.id.text_view_group_name_movies)).setVisibility(View.VISIBLE);
                    (((Activity) context).findViewById(R.id.text_view_content_movies)).setVisibility(View.VISIBLE);
                }
                break;

            case "podcast":
                if(new PodcastService(context).isEmpty()) {
                    (((Activity) context).findViewById(R.id.text_view_group_name_podcasts)).setVisibility(View.VISIBLE);
                    (((Activity) context).findViewById(R.id.text_view_content_podcasts)).setVisibility(View.VISIBLE);
                }
                break;
        }
    }
}
