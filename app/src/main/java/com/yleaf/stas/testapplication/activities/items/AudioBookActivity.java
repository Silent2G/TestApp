package com.yleaf.stas.testapplication.activities.items;

import android.content.Context;
import android.content.Intent;
import android.media.AudioManager;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
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

public class AudioBookActivity extends AppCompatActivity {

    private static final String TAG = AudioBookActivity.class.getSimpleName();
    private static final String EXTRA_DATA_ID = "data_id";
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
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_item_audio_books);

        dataId = getIntent().getIntExtra(EXTRA_DATA_ID,0);

        initWidgets();

        Call<JSONResponseAudioBook> response = new GetAudioBookItem().getAudioBookItem(dataId);
        response.enqueue(new Callback<JSONResponseAudioBook>() {
            @Override
            public void onResponse(Call<JSONResponseAudioBook> call, Response<JSONResponseAudioBook> response) {
                Log.i(TAG, "onResponse: Server Response: " + response.toString());

                if(response.isSuccessful()) {
                    ArrayList<AudioBookItem> results = response.body().getResults();
                    audioBookItem = results.get(0);

                    setWidgets();
                }
            }

            @Override
            public void onFailure(Call<JSONResponseAudioBook> call, Throwable t) {
                Log.e(TAG, "onFailure: Something went wrong " + t.getMessage());
                Toast.makeText(getApplicationContext(), "Something went wrong", Toast.LENGTH_SHORT).show();
            }
        });

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

    }

    private void initWidgets() {
        progressBar = findViewById(R.id.item_audio_books_progress_bar);
        artistName = findViewById(R.id.item_audio_books_artist_name);
        collectionName = findViewById(R.id.item_audio_books_collection_name);
        imageView = findViewById(R.id.item_audio_books_image_view);
        releaseDate = findViewById(R.id.item_audio_books_release_date);
        description = findViewById(R.id.item_audio_books_description);
        play = findViewById(R.id.play);
        pause = findViewById(R.id.pause);
        stop = findViewById(R.id.stop);
    }

    private void setWidgets() {
        progressBar.setVisibility(View.INVISIBLE);

        artistName.setText(audioBookItem.getArtistName());
        collectionName.setText(audioBookItem.getCollectionName());

        Glide.with(this).load(audioBookItem.getArtworkUrl100()).into(imageView);
        releaseDate.setText(audioBookItem.getReleaseDate().substring(0, 10));
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
    protected void onPause() {
        super.onPause();

        if(mediaPlayer != null && mediaPlayer.isPlaying()) {
            mediaPlayer.stop();
            mediaPlayer = null;
        }
    }

    public static Intent newIntent(Context context, int id) {
        Intent intent = new Intent(context, AudioBookActivity.class);
        intent.putExtra(EXTRA_DATA_ID, id);
        return intent;
    }
}
