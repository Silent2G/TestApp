package com.yleaf.stas.testapplication.fragments;

import android.os.Bundle;
import android.os.Parcelable;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.yleaf.stas.testapplication.R;
import com.yleaf.stas.testapplication.adapter.DataAdapter;
import com.yleaf.stas.testapplication.db.service.BookService;
import com.yleaf.stas.testapplication.models.Data;

import java.util.ArrayList;

public class AudioBooksFragment extends Fragment {

    private RecyclerView recyclerView;
    private DataAdapter dataAdapter;
    private LinearLayoutManager linearLayoutManager;
    private TextView placeHolderGroupName;
    private TextView placeHolderContent;

    private ArrayList<Data> dataSet;
    private Parcelable listState;

    public static final String SAVED_RECYCLER_VIEW_STATUS_ID = "SAVED_RECYCLER_VIEW_STATUS_ID";

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

        dataSet = new BookService(getActivity()).getAll();
        dataAdapter = new DataAdapter(dataSet, getActivity());
        recyclerView.setAdapter(dataAdapter);

        return view;
    }

    private void hidePlaceHolder() {
        if(!new BookService(getActivity()).isEmpty()) {
            placeHolderGroupName.setVisibility(View.INVISIBLE);
            placeHolderContent.setVisibility(View.INVISIBLE);
        }
    }

//    @Override
//    public void onSaveInstanceState(@NonNull Bundle outState) {
//        super.onSaveInstanceState(outState);
//
//        listState = linearLayoutManager.onSaveInstanceState();
//        outState.putParcelable(SAVED_RECYCLER_VIEW_STATUS_ID, listState);
//
//    }

//    @Override
//    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
//        super.onActivityCreated(savedInstanceState);
//
//        if(savedInstanceState != null) {
//            listState = savedInstanceState.getParcelable(SAVED_RECYCLER_VIEW_STATUS_ID);
//        }
//    }

    @Override
    public void onPause() {
        super.onPause();

        long currentVisiblePositionFirst;
        long currentVisiblePositionLast;
        currentVisiblePositionFirst = linearLayoutManager.findFirstCompletelyVisibleItemPosition();
        currentVisiblePositionLast = linearLayoutManager.findFirstCompletelyVisibleItemPosition();

        Log.i("TAG", " " + currentVisiblePositionFirst + " " + currentVisiblePositionLast);
    }

    @Override
    public void onResume() {
        super.onResume();

        ((LinearLayoutManager) recyclerView.getLayoutManager()).scrollToPosition(20);
    }

    public static AudioBooksFragment newInstance() {

        Bundle args = new Bundle();

        AudioBooksFragment fragment = new AudioBooksFragment();
        fragment.setArguments(args);
        return fragment;
    }
}
