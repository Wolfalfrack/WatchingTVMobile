package com.watchingTVmobile.watchingTV.fragments;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;


import com.watchingTVmobile.watchingTV.R;
import com.watchingTVmobile.watchingTV.adapters.FilmsDescripSmallAdapter;
import com.watchingTVmobile.watchingTV.network.films.FilmDescrip;
import com.watchingTVmobile.watchingTV.utils.Favoris;

import java.util.ArrayList;
import java.util.List;

public class FavorisFilmsFragment extends Fragment {

    private RecyclerView mFavMoviesRecyclerView;
    private List<FilmDescrip> mFavMovies;
    private FilmsDescripSmallAdapter mFavMoviesAdapter;

    private LinearLayout mEmptyLayout;

    @Override
    public View onCreateView(LayoutInflater inflater,ViewGroup container,Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_favoris_films, container, false);

        mFavMoviesRecyclerView = (RecyclerView) view.findViewById(R.id.recycler_view_fav_movies);
        mFavMovies = new ArrayList<>();
        mFavMoviesAdapter = new FilmsDescripSmallAdapter(getContext(), mFavMovies);
        mFavMoviesRecyclerView.setAdapter(mFavMoviesAdapter);
        mFavMoviesRecyclerView.setLayoutManager(new GridLayoutManager(getContext(), 3));

        mEmptyLayout = (LinearLayout) view.findViewById(R.id.layout_recycler_view_fav_movies_empty);

        loadFavMovies();

        return view;
    }

    @Override
    public void onStart() {
        super.onStart();
        mFavMoviesAdapter.notifyDataSetChanged();
    }

    private void loadFavMovies() {
        List<FilmDescrip> favFilmDescrips = Favoris.getFavMovieBriefs(getContext());
        if (favFilmDescrips.isEmpty()) {
            mEmptyLayout.setVisibility(View.VISIBLE);
            return;
        }

        for (FilmDescrip filmDescrip : favFilmDescrips) {
            mFavMovies.add(filmDescrip);
        }
        mFavMoviesAdapter.notifyDataSetChanged();
    }

}
