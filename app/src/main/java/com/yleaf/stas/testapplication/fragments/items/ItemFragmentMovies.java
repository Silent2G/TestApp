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
import com.yleaf.stas.testapplication.data.items.GetMovieItem;
import com.yleaf.stas.testapplication.models.items.MovieItem;
import com.yleaf.stas.testapplication.models.items_response.JSONResponseMovie;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ItemFragmentMovies extends Fragment {

    private static final String TAG = ItemFragmentMovies.class.getSimpleName();
    private static final String DATA_ID = "data_id";
    private int dataId;
    private ProgressBar progressBar;
    private MovieItem movieItem;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        dataId = getArguments().getInt(DATA_ID);

        Call<JSONResponseMovie> response = new GetMovieItem().getMovieItem(dataId);
        response.enqueue(new Callback<JSONResponseMovie>() {
            @Override
            public void onResponse(Call<JSONResponseMovie> call, Response<JSONResponseMovie> response) {
                Log.i(TAG, "onResponse: Server Response: " + response.toString());

                if(response.isSuccessful()) {
                    ArrayList<MovieItem> results = response.body().getResults();
                    movieItem = results.get(0);

                    Log.i(TAG, "audioBookItem artistName " + movieItem.getArtistName());

                    progressBar = getActivity().findViewById(R.id.item_movies_progress_bar);
                    progressBar.setVisibility(View.INVISIBLE);
                }
            }

            @Override
            public void onFailure(Call<JSONResponseMovie> call, Throwable t) {
                Log.e(TAG, "onFailure: Something went wrong " + t.getMessage());
                Toast.makeText(getActivity(), "Something went wrong", Toast.LENGTH_SHORT).show();
            }
        });
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_item_movies, container, false);
        getActivity().findViewById(R.id.bottom_navigation).setVisibility(View.INVISIBLE);

        return view;
    }

    @Override
    public void onDetach() {
        super.onDetach();
        getActivity().findViewById(R.id.bottom_navigation).setVisibility(View.VISIBLE);
    }

    public static ItemFragmentMovies newInstance(int id) {

        Bundle args = new Bundle();
        args.putInt(DATA_ID, id);

        ItemFragmentMovies fragment = new ItemFragmentMovies();
        fragment.setArguments(args);
        return fragment;
    }
}
