package com.yleaf.stas.testapplication.fragments;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MenuInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.PopupMenu;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.yleaf.stas.testapplication.R;
import com.yleaf.stas.testapplication.db.service.FavoriteService;
import com.yleaf.stas.testapplication.db.service.PodcastService;
import com.yleaf.stas.testapplication.models.Data;

import java.util.List;

public class PodcastsFragment extends Fragment {

    private RecyclerView recyclerView;
    private DataAdapter dataAdapter;
    private TextView placeHolderGroupName;
    private TextView placeHolderContent;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_podcasts, container, false);

        placeHolderGroupName = view.findViewById(R.id.text_view_group_name_podcasts);
        placeHolderContent = view.findViewById(R.id.text_view_content_podcasts);

        recyclerView = view.findViewById(R.id.recycler_view_podcasts);
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));

        if(!new PodcastService(getActivity()).isEmpty()) {
            placeHolderGroupName.setVisibility(View.INVISIBLE);
            placeHolderContent.setVisibility(View.INVISIBLE);
        }

        dataAdapter = new DataAdapter(new PodcastService(getActivity()).getAll());
        recyclerView.setAdapter(dataAdapter);

        return view;
    }

    private class DataHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        private Data data;

        private ImageView imageView;
        private TextView textViewName;
        private TextView textViewArtistName;
        private ImageButton imageButton;

        public DataHolder(@NonNull View itemView) {
            super(itemView);
            itemView.setOnClickListener(this);

            imageView = itemView.findViewById(R.id.item_image_view);
            textViewName = itemView.findViewById(R.id.item_name);
            textViewArtistName = itemView.findViewById(R.id.item_artist_name);
            imageButton = itemView.findViewById(R.id.item_image_button);
        }

        private void showPopupMenu(View view, final Data data, final int position) {
            // inflate menu
            PopupMenu popup = new PopupMenu(view.getContext(),view);
            MenuInflater inflater = popup.getMenuInflater();
            inflater.inflate(R.menu.popup_menu, popup.getMenu());

            popup.setOnMenuItemClickListener(menuItem -> {
                if(menuItem.getItemId() == R.id.item_add_to_favorite) {

                    if(data.getKind().equals("podcast")) {
                        new PodcastService(getActivity()).deleteById(data.getId());
                    }

                    Log.i("TAG", position + " ");

                    dataAdapter.dataList.remove(position);
                    dataAdapter.notifyItemRemoved(position);
                    dataAdapter.notifyItemRangeChanged(position, dataAdapter.dataList.size());

                    new FavoriteService(getActivity()).save(data);

                }
                return false;
            });
            popup.show();
        }

        public void bindData(Data dataObj, final int position) {
            this.data = dataObj;

            Glide.with(getActivity()).load(data.getArtworkUrl100()).into(imageView);

            textViewName.setText(data.getName());
            textViewArtistName.setText(data.getArtistName());

            imageButton.setOnClickListener(view -> showPopupMenu(view, data, position));
        }

        @Override
        public void onClick(View view) {
            Toast.makeText(getActivity(), String.valueOf(data.getId()), Toast.LENGTH_SHORT).show();
        }
    }

    private class DataAdapter extends RecyclerView.Adapter<DataHolder> {
        private List<Data> dataList;

        public DataAdapter(List<Data> data) {
            this.dataList = data;
        }

        @NonNull
        @Override
        public DataHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
            LayoutInflater layoutInflater = LayoutInflater.from(getActivity());
            View view = layoutInflater.inflate(R.layout.item, viewGroup, false);
            return new DataHolder(view);
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

    public static PodcastsFragment newInstance() {
        
        Bundle args = new Bundle();
        
        PodcastsFragment fragment = new PodcastsFragment();
        fragment.setArguments(args);
        return fragment;
    }
}
