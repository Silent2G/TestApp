package com.yleaf.stas.testapplication.fragments;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.yleaf.stas.testapplication.R;
import com.yleaf.stas.testapplication.adapter.ViewPagerAdapter;
import com.yleaf.stas.testapplication.fragments.tabs.AudioBooksTabFragment;
import com.yleaf.stas.testapplication.fragments.tabs.MoviesTabFragment;
import com.yleaf.stas.testapplication.fragments.tabs.PodcastsTabFragment;

public class FavoriteFragment extends Fragment {

    private ViewPager viewPager;
    private TabLayout tabLayout;
    public ViewPagerAdapter viewPagerAdapter;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        final View view = inflater.inflate(R.layout.fragment_favorite_tab, container, false);

        initWidgets(view);
        setWidgets();

        return view;
    }

    private void initWidgets(View view) {
        viewPager = view.findViewById(R.id.view_pager);
        tabLayout = view.findViewById(R.id.tab_layout);
    }

    private void setWidgets() {
        viewPagerAdapter = new ViewPagerAdapter(getActivity().getSupportFragmentManager());
        setupViewPager(viewPager, viewPagerAdapter);
        tabLayout.setupWithViewPager(viewPager);
    }

    public void setupViewPager(ViewPager viewPager, ViewPagerAdapter adapter) {

        adapter.addFrag(AudioBooksTabFragment.newInstance(), getString(R.string.audio_books));
        adapter.addFrag(MoviesTabFragment.newInstance(), getString(R.string.movies));
        adapter.addFrag(PodcastsTabFragment.newInstance(), getString(R.string.podcasts));

        viewPager.setAdapter(adapter);
    }

    public static FavoriteFragment newInstance() {

        Bundle args = new Bundle();

        FavoriteFragment fragment = new FavoriteFragment();
        fragment.setArguments(args);
        return fragment;
    }
}
