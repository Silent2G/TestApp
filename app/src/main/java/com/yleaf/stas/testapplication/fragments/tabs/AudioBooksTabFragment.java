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

public class AudioBooksTabFragment extends Fragment {

    private RecyclerView recyclerView;
    private DataAdapterFavorite dataAdapterFavorite;
    private TextView placeHolderGroupName;
    private TextView placeHolderContent;
    public LinearLayoutManager linearLayoutManager;

    public static int firstCurrentVisiblePosition;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_audio_books, container, false);

        placeHolderGroupName = view.findViewById(R.id.text_view_group_name_audio_books);
        placeHolderContent = view.findViewById(R.id.text_view_content_audio_books);

        recyclerView = view.findViewById(R.id.recycler_view_audio_books);
        linearLayoutManager = new LinearLayoutManager(getActivity());
        recyclerView.setLayoutManager(linearLayoutManager);

        hidePlaceHolder();

        dataAdapterFavorite = new DataAdapterFavorite(new FavoriteService(getActivity())
                .getAllByKind(getString(R.string.db_kind_audio_books)), getActivity());

        recyclerView.setAdapter(dataAdapterFavorite);

        return view;
    }

    private void hidePlaceHolder() {
        if(!new FavoriteService(getActivity()).isEmptyCurrentKind(getString(R.string.db_kind_audio_books))) {
            placeHolderGroupName.setVisibility(View.INVISIBLE);
            placeHolderContent.setVisibility(View.INVISIBLE);
        }
    }

    @Override
    public void onPause() {
        super.onPause();
        firstCurrentVisiblePosition = linearLayoutManager.findFirstCompletelyVisibleItemPosition();
    }

    @Override
    public void onResume() {
        super.onResume();
        linearLayoutManager.scrollToPosition(firstCurrentVisiblePosition);
    }

    public static AudioBooksTabFragment newInstance() {

        Bundle args = new Bundle();

        AudioBooksTabFragment fragment = new AudioBooksTabFragment();
        fragment.setArguments(args);
        return fragment;
    }
}
