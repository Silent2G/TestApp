package com.yleaf.stas.testapplication.fragments.items;

import android.media.AudioManager;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebView;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.yleaf.stas.testapplication.R;
import com.yleaf.stas.testapplication.data.items.GetAudioBookItem;
import com.yleaf.stas.testapplication.models.items.AudioBookItem;
import com.yleaf.stas.testapplication.models.items_response.JSONResponseAudioBook;

import java.io.IOException;
import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ItemFragmentAudioBooks extends Fragment {

    private static final String TAG = ItemFragmentAudioBooks.class.getSimpleName();
    private static final String DATA_ID = "data_id";
    private int dataId;
    private AudioBookItem audioBookItem;
    private MediaPlayer mediaPlayer;

    private ProgressBar progressBar;
    private TextView artistName;
    private TextView collectionName;
    private ImageView imageView;
    private TextView releaseDate;
    private WebView description;

    private ImageButton play;
    private ImageButton stop;
    private ImageButton pause;

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

                    //initMediaPlayer();
                    setWidgets();
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

        initWidgets(view);

        play.setOnClickListener(click -> {
            if(mediaPlayer == null) {
                initMediaPlayer();
                mediaPlayer.start();
            } else {
                mediaPlayer.start();
            }
        });
        pause.setOnClickListener(click -> mediaPlayer.pause());
        stop.setOnClickListener(click -> stopAndPrepare());

        return view;
    }

    private void initWidgets(View view) {
        progressBar = view.findViewById(R.id.item_audio_books_progress_bar);
        artistName = view.findViewById(R.id.item_audio_books_artist_name);
        collectionName = view.findViewById(R.id.item_audio_books_collection_name);
        imageView = view.findViewById(R.id.item_audio_books_image_view);
        releaseDate = view.findViewById(R.id.item_audio_books_release_date);
        description = view.findViewById(R.id.item_audio_books_description);
        play = view.findViewById(R.id.play);
        pause = view.findViewById(R.id.pause);
        stop = view.findViewById(R.id.stop);
    }

    private void setWidgets() {
        progressBar.setVisibility(View.INVISIBLE);

        artistName.setText(audioBookItem.getArtistName());
        collectionName.setText(audioBookItem.getCollectionName());

        Glide.with(getActivity()).load(audioBookItem.getArtworkUrl100()).into(imageView);
        releaseDate.setText(audioBookItem.getReleaseDate());
        description.loadData(audioBookItem.getDescription(), "text/html; charset=utf-8", "utf-8");
    }

    private void initMediaPlayer() {
        mediaPlayer = new MediaPlayer();
        mediaPlayer.setAudioStreamType(AudioManager.STREAM_MUSIC);
        try {
            mediaPlayer.setDataSource(audioBookItem.getPreviewUrl());
            mediaPlayer.prepare(); // might take long! (for buffering, etc)
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void stopAndPrepare() {
        mediaPlayer.stop();
        initMediaPlayer();
    }

    @Override
    public void onDetach() {
        super.onDetach();
        getActivity().findViewById(R.id.bottom_navigation).setVisibility(View.VISIBLE);

        if(mediaPlayer != null && mediaPlayer.isPlaying()) {
            mediaPlayer.stop();
            mediaPlayer = null;
        }
    }

    public static ItemFragmentAudioBooks newInstance(int id) {

        Bundle args = new Bundle();
        args.putInt(DATA_ID, id);

        ItemFragmentAudioBooks fragment = new ItemFragmentAudioBooks();
        fragment.setArguments(args);
        return fragment;
    }


}
