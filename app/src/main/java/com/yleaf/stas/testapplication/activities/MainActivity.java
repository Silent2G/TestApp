package com.yleaf.stas.testapplication.activities;

import android.os.PersistableBundle;
import android.support.design.widget.BottomNavigationView;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ProgressBar;

import com.yleaf.stas.testapplication.R;
import com.yleaf.stas.testapplication.data.ClearData;
import com.yleaf.stas.testapplication.data.GetData;
import com.yleaf.stas.testapplication.fragments.AudioBooksFragment;
import com.yleaf.stas.testapplication.fragments.FavoriteFragment;
import com.yleaf.stas.testapplication.fragments.MoviesFragment;
import com.yleaf.stas.testapplication.fragments.PodcastsFragment;
import com.yleaf.stas.testapplication.update.CheckDate;
import com.yleaf.stas.testapplication.update.CheckIsOnline;

public class MainActivity extends AppCompatActivity {

    BottomNavigationView bottomNavigationView;
    private static final String TAG = MainActivity.class.getSimpleName();

    private ProgressBar progressBar;

    @Override
    public void onSaveInstanceState(Bundle outState, PersistableBundle outPersistentState) {
        super.onSaveInstanceState(outState, outPersistentState);
    }

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
        }

        bottomNavigationView.setOnNavigationItemSelectedListener(item -> {
            Fragment selectedFragment = null;
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
        transaction.replace(R.id.frame_layout, AudioBooksFragment.newInstance());
        transaction.commit();
    }

    @Override
    public void onBackPressed() {
        if(getSupportFragmentManager().getBackStackEntryCount() > 0) {
            getSupportFragmentManager().popBackStack();
        } else {
            super.onBackPressed();
        }
    }
}
