package com.yleaf.stas.testapplication.fragments.items;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.yleaf.stas.testapplication.R;
import com.yleaf.stas.testapplication.data.items.GetAudioBookItem;
import com.yleaf.stas.testapplication.models.items.AudioBookItem;
import com.yleaf.stas.testapplication.models.items_response.JSONResponseAudioBook;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ItemFragmentAudioBooks extends Fragment {

    private static final String TAG = ItemFragmentAudioBooks.class.getSimpleName();
    private static final String DATA_ID = "data_id";
    private int dataId;
    private AudioBookItem audioBookItem;
    private ProgressBar progressBar;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        dataId = getArguments().getInt(DATA_ID);

        Call<JSONResponseAudioBook> response = new GetAudioBookItem().getAudioBookItem(dataId);
        response.enqueue(new Callback<JSONResponseAudioBook>() {
            @Override
            public void onResponse(Call<JSONResponseAudioBook> call, Response<JSONResponseAudioBook> response) {
                Log.i(TAG, "onResponse: Server Response: " + response.toString());

                if(response.isSuccessful()) {
                    ArrayList<AudioBookItem> results = response.body().getResults();
                    audioBookItem = results.get(0);

                    Log.i(TAG, "audioBookItem artistName " + audioBookItem.getArtistName());

                    progressBar = getActivity().findViewById(R.id.item_audio_books_progress_bar);
                    progressBar.setVisibility(View.INVISIBLE);
                }
            }

            @Override
            public void onFailure(Call<JSONResponseAudioBook> call, Throwable t) {
                Log.e(TAG, "onFailure: Something went wrong " + t.getMessage());
                Toast.makeText(getActivity(), "Something went wrong", Toast.LENGTH_SHORT).show();
            }
        });
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_item_audio_books, container, false);
        getActivity().findViewById(R.id.bottom_navigation).setVisibility(View.INVISIBLE);

        return view;
    }

    @Override
    public void onDetach() {
        super.onDetach();
        getActivity().findViewById(R.id.bottom_navigation).setVisibility(View.VISIBLE);
    }

    public static ItemFragmentAudioBooks newInstance(int id) {

        Bundle args = new Bundle();
        args.putInt(DATA_ID, id);

        ItemFragmentAudioBooks fragment = new ItemFragmentAudioBooks();
        fragment.setArguments(args);
        return fragment;
    }


}
