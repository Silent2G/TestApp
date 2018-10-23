package com.yleaf.stas.testapplication.fragments.items;

import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.VideoView;

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
    private MovieItem movieItem;

    private ProgressBar progressBar;
    private TextView artistName;
    private TextView trackName;
    private VideoView videoView;
    private TextView releaseDate;
    private TextView description;


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

                    setWidgets();
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

        initWidgets(view);

        return view;
    }

    private void initWidgets(View view) {
        progressBar = view.findViewById(R.id.item_movies_progress_bar);
        artistName = view.findViewById(R.id.item_movies_artist_name);
        trackName = view.findViewById(R.id.item_movies_track_name);
        videoView = view.findViewById(R.id.item_movies_video_view);
        releaseDate = view.findViewById(R.id.item_movies_release_date);
        description = view.findViewById(R.id.item_movies_description);
    }

    private void setWidgets() {
        progressBar.setVisibility(View.INVISIBLE);

        artistName.setText(movieItem.getArtistName());
        trackName.setText(movieItem.getTrackName());

        Uri uri = Uri.parse(movieItem.getPreviewUrl());
        videoView.setVideoURI(uri);
        videoView.start();

        releaseDate.setText(movieItem.getReleaseDate());
        description.setText(movieItem.getLongDescription());
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
