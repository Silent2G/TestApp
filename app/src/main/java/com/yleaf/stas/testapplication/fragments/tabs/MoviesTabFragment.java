package com.yleaf.stas.testapplication.fragments.tabs;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.yleaf.stas.testapplication.R;
import com.yleaf.stas.testapplication.adapter.DataAdapterFavorite;
import com.yleaf.stas.testapplication.db.service.FavoriteService;

public class MoviesTabFragment extends Fragment {

    private RecyclerView recyclerView;
    private DataAdapterFavorite dataAdapterFavorite;
    private TextView placeHolderGroupName;
    private TextView placeHolderContent;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_movies, container, false);

        placeHolderGroupName = view.findViewById(R.id.text_view_group_name_movies);
        placeHolderContent = view.findViewById(R.id.text_view_content_movies);

        recyclerView = view.findViewById(R.id.recycler_view_movies);
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));

        hidePlaceHolder();

        dataAdapterFavorite = new DataAdapterFavorite(new FavoriteService(getActivity())
                .getAllByKind(getString(R.string.db_kind_movies)), getActivity());

        recyclerView.setAdapter(dataAdapterFavorite);

        return view;
    }

    private void hidePlaceHolder() {
        if(!new FavoriteService(getActivity()).isEmptyCurrentKind(getString(R.string.db_kind_movies))) {
            placeHolderGroupName.setVisibility(View.INVISIBLE);
            placeHolderContent.setVisibility(View.INVISIBLE);
        }
    }

    public static MoviesTabFragment newInstance() {

        Bundle args = new Bundle();

        MoviesTabFragment fragment = new MoviesTabFragment();
        fragment.setArguments(args);
        return fragment;
    }
}
