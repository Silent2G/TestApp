package com.yleaf.stas.testapplication.fragments.items;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.yleaf.stas.testapplication.R;
import com.yleaf.stas.testapplication.data.items.GetPodcastItem;
import com.yleaf.stas.testapplication.models.items.PodcastItem;
import com.yleaf.stas.testapplication.models.items_response.JSONResponsePodcast;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ItemFragmentPodcasts extends Fragment {

    private static final String TAG = ItemFragmentPodcasts.class.getSimpleName();
    private static final String DATA_ID = "data_id";
    private int dataId;
    private PodcastItem podcastItem;

    private ProgressBar progressBar;
    private TextView artistName;
    private TextView trackName;
    private ImageView imageView;
    private TextView releaseDate;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        dataId = getArguments().getInt(DATA_ID);

        Call<JSONResponsePodcast> response = new GetPodcastItem().getPodcastItem(dataId);

        response.enqueue(new Callback<JSONResponsePodcast>() {
            @Override
            public void onResponse(Call<JSONResponsePodcast> call, Response<JSONResponsePodcast> response) {
                Log.i(TAG, "onResponse: Server Response: " + response.toString());

                if(response.isSuccessful()) {
                    ArrayList<PodcastItem> results = response.body().getResults();
                    podcastItem = results.get(0);

                    setWidgets();
                }

            }

            @Override
            public void onFailure(Call<JSONResponsePodcast> call, Throwable t) {
                Log.e(TAG, "onFailure: Something went wrong " + t.getMessage());
                Toast.makeText(getActivity(), "Something went wrong", Toast.LENGTH_SHORT).show();
            }
        });
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_item_podcasts, container, false);
        getActivity().findViewById(R.id.bottom_navigation).setVisibility(View.INVISIBLE);

        initWidgets(view);

        return view;
    }

    private void initWidgets(View view) {
        progressBar = view.findViewById(R.id.item_podcasts_progress_bar);
        artistName = view.findViewById(R.id.item_podcasts_artist_name);
        trackName = view.findViewById(R.id.item_podcasts_track_name);
        imageView = view.findViewById(R.id.item_podcasts_image_view);
        releaseDate = view.findViewById(R.id.item_podcasts_release_date);
    }

    private void setWidgets() {
        progressBar.setVisibility(View.INVISIBLE);
        artistName.setText(podcastItem.getArtistName());
        trackName.setText(podcastItem.getTrackName());
        Glide.with(getActivity()).load(podcastItem.getArtworkUrl600()).into(imageView);
        releaseDate.setText(podcastItem.getReleaseDate());
    }

    @Override
    public void onDetach() {
        super.onDetach();
        getActivity().findViewById(R.id.bottom_navigation).setVisibility(View.VISIBLE);
    }

    public static ItemFragmentPodcasts newInstance(int id) {

        Bundle args = new Bundle();
        args.putInt(DATA_ID, id);

        ItemFragmentPodcasts fragment = new ItemFragmentPodcasts();
        fragment.setArguments(args);
        return fragment;
    }
}
