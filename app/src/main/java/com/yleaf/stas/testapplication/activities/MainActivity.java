package com.yleaf.stas.testapplication.activities;

import android.support.design.widget.BottomNavigationView;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.yleaf.stas.testapplication.R;
import com.yleaf.stas.testapplication.fragments.AudioBooksFragment;
import com.yleaf.stas.testapplication.fragments.FavoriteFragment;
import com.yleaf.stas.testapplication.fragments.MoviesFragment;
import com.yleaf.stas.testapplication.fragments.PodcastsFragment;

public class MainActivity extends AppCompatActivity {

    BottomNavigationView bottomNavigationView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initViews();

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

            FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
            transaction.replace(R.id.frame_layout, selectedFragment);
            transaction.commit();
            return true;
        });
    }

    private void initViews() {
        bottomNavigationView = findViewById(R.id.bottom_navigation);
    }
}
