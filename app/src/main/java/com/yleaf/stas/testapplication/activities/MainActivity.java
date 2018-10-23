package com.yleaf.stas.testapplication.activities;

import android.support.design.widget.BottomNavigationView;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ProgressBar;

import com.yleaf.stas.testapplication.R;
import com.yleaf.stas.testapplication.data.ClearData;
import com.yleaf.stas.testapplication.data.GetData;
import com.yleaf.stas.testapplication.fragments.AudioBooksFragment;
import com.yleaf.stas.testapplication.fragments.FavoriteFragment;
import com.yleaf.stas.testapplication.fragments.MoviesFragment;
import com.yleaf.stas.testapplication.fragments.PodcastsFragment;
import com.yleaf.stas.testapplication.models.tapping_history.Pair;
import com.yleaf.stas.testapplication.update.CheckDate;
import com.yleaf.stas.testapplication.update.CheckIsOnline;

import java.util.Collections;
import java.util.HashMap;
import java.util.Set;

public class MainActivity extends AppCompatActivity {

    BottomNavigationView bottomNavigationView;
    private static final String TAG = MainActivity.class.getSimpleName();

    private static final String HISTORY_COUNTER = "history counter";
    private static final String HISTORY_CONTAINER = "history container";

    private static HashMap<Integer, Pair> historyContainer = new HashMap<>();
    private static int historyCounter = 0;

    private ProgressBar progressBar;

    Fragment selectedFragment = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        initViews();

        if(savedInstanceState == null) {
            if(new CheckDate(this).isTodayNewDay() && new CheckIsOnline(this).isOnline()) {
                new ClearData(getApplicationContext()).clearData();
                new GetData(this).getData();
            } else {
                startFirstFragment();
            }
        } else {
            progressBar.setVisibility(View.INVISIBLE);

            historyCounter = savedInstanceState.getInt(HISTORY_COUNTER);
            historyContainer = (HashMap<Integer, Pair>) savedInstanceState.getSerializable(HISTORY_CONTAINER);
        }

        bottomNavigationView.setOnNavigationItemSelectedListener(item -> {
            saveTappingHistory();
            switch (item.getItemId()) {

                case R.id.navigation_audio_books:
                    selectedFragment = AudioBooksFragment.newInstance();
                    break;

                case R.id.navigation_movies:
                    selectedFragment = MoviesFragment.newInstance();
                    break;

                case R.id.navigation_podcasts:
                    selectedFragment = PodcastsFragment.newInstance();
                    break;

                case R.id.navigation_favorite:
                    selectedFragment = FavoriteFragment.newInstance();
                    break;
            }

            progressBar.setVisibility(View.INVISIBLE);

            FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
            transaction.replace(R.id.frame_layout, selectedFragment);
            transaction.commit();

            return true;
        });
    }

    private void initViews() {
        bottomNavigationView = findViewById(R.id.bottom_navigation);
        progressBar = findViewById(R.id.progress_bar);
    }

    private void startFirstFragment() {
        progressBar.setVisibility(View.INVISIBLE);

        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
        transaction.replace(R.id.frame_layout, selectedFragment = AudioBooksFragment.newInstance());
        transaction.commit();
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putInt(HISTORY_COUNTER, historyCounter);
        outState.putSerializable(HISTORY_CONTAINER, historyContainer);
    }

    private int countUpAndGet() {
        return ++ historyCounter;
    }

    private void saveTappingHistory() {
        String itemName = String.valueOf(bottomNavigationView.getMenu().findItem(bottomNavigationView.getSelectedItemId()));
        int counter = countUpAndGet();

        switch (itemName) {
            case "Audio books":
                historyContainer.put(counter, new Pair("Audio books", getPosition()));
                break;
            case "Movies":
                historyContainer.put(counter, new Pair("Movies", getPosition()));
                break;
            case "Podcasts":
                historyContainer.put(counter, new Pair("Podcasts", getPosition()));
                break;
            case "Favorite":
                historyContainer.put(counter, new Pair("Favorite", 0));
                break;
        }
    }

    private int getPosition() {
        if(selectedFragment instanceof AudioBooksFragment) {
            return ((AudioBooksFragment)selectedFragment).linearLayoutManager.findFirstCompletelyVisibleItemPosition();
        }
        if(selectedFragment instanceof MoviesFragment) {
            return ((MoviesFragment)selectedFragment).linearLayoutManager.findFirstCompletelyVisibleItemPosition();
        }
        if(selectedFragment instanceof PodcastsFragment) {
            return ((PodcastsFragment)selectedFragment).linearLayoutManager.findFirstCompletelyVisibleItemPosition();
        }
        if(selectedFragment instanceof FavoriteFragment) {
            return 0;
        }
        return 0;
    }

    @Override
    public void onBackPressed() {
        if(historyContainer.size() > 0) {
            Set<Integer> keys = historyContainer.keySet();
            int maxKey = Collections.max(keys);
            Pair pair = historyContainer.get(maxKey);
            historyContainer.remove(maxKey);

            FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
            switch (pair.getCategoryName()) {

                case "Audio books":
                    AudioBooksFragment audioBooksFragment = AudioBooksFragment.newInstance();
                    AudioBooksFragment.firstCurrentVisiblePosition = pair.getFirstVisiblePositionNumber();
                    transaction.replace(R.id.frame_layout, selectedFragment = audioBooksFragment);
                    transaction.commit();
                    bottomNavigationView.getMenu().getItem(0).setChecked(true);
                    break;

                case "Movies":
                    MoviesFragment moviesFragment = MoviesFragment.newInstance();
                    MoviesFragment.firstCurrentVisiblePosition = pair.getFirstVisiblePositionNumber();
                    transaction.replace(R.id.frame_layout, selectedFragment = moviesFragment);
                    transaction.commit();
                    bottomNavigationView.getMenu().getItem(1).setChecked(true);
                    break;

                case "Podcasts":
                    PodcastsFragment podcastsFragment = PodcastsFragment.newInstance();
                    PodcastsFragment.firstCurrentVisiblePosition = pair.getFirstVisiblePositionNumber();
                    transaction.replace(R.id.frame_layout, selectedFragment = podcastsFragment);
                    transaction.commit();
                    bottomNavigationView.getMenu().getItem(2).setChecked(true);
                    break;

                case "Favorite":
                    transaction.replace(R.id.frame_layout, selectedFragment = FavoriteFragment.newInstance());
                    transaction.commit();
                    bottomNavigationView.getMenu().getItem(3).setChecked(true);
            }
        } else {
            super.onBackPressed();
        }
    }
}
