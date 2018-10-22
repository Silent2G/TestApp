package com.yleaf.stas.testapplication.fragments;

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
import com.yleaf.stas.testapplication.adapter.DataAdapter;
import com.yleaf.stas.testapplication.db.service.BookService;

public class AudioBooksFragment extends Fragment {

    private RecyclerView recyclerView;
    private DataAdapter dataAdapter;
    private LinearLayoutManager linearLayoutManager;
    private TextView placeHolderGroupName;
    private TextView placeHolderContent;

    private static int firstCurrentVisiblePosition;

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

        dataAdapter = new DataAdapter(new BookService(getActivity()).getAll(), getActivity());
        recyclerView.setAdapter(dataAdapter);

        return view;
    }

    private void hidePlaceHolder() {
        if(!new BookService(getActivity()).isEmpty()) {
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

    public static AudioBooksFragment newInstance() {

        Bundle args = new Bundle();

        AudioBooksFragment fragment = new AudioBooksFragment();
        fragment.setArguments(args);
        return fragment;
    }
}
