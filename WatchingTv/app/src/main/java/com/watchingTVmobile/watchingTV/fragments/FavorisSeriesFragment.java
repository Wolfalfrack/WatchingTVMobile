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
import com.watchingTVmobile.watchingTV.adapters.SerieDescripSmallAdapter;
import com.watchingTVmobile.watchingTV.network.series.SerieDescrip;
import com.watchingTVmobile.watchingTV.utils.Favoris;

import java.util.ArrayList;
import java.util.List;

public class FavorisSeriesFragment extends Fragment {

    private RecyclerView mFavTVShowsRecyclerView;
    private List<SerieDescrip> mFavTVShows;
    private SerieDescripSmallAdapter mFavTVShowsAdapter;

    private LinearLayout mEmptyLayout;

    @Override
    public View onCreateView(LayoutInflater inflater,  ViewGroup container,  Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_favoris_series, container, false);

        mFavTVShowsRecyclerView = (RecyclerView) view.findViewById(R.id.recycler_view_fav_tv_shows);
        mFavTVShows = new ArrayList<>();
        mFavTVShowsAdapter = new SerieDescripSmallAdapter(getContext(), mFavTVShows);
        mFavTVShowsRecyclerView.setAdapter(mFavTVShowsAdapter);
        mFavTVShowsRecyclerView.setLayoutManager(new GridLayoutManager(getContext(), 3));

        mEmptyLayout = (LinearLayout) view.findViewById(R.id.layout_recycler_view_fav_tv_shows_empty);

        loadFavTVShows();

        return view;
    }

    @Override
    public void onStart() {
        super.onStart();
        mFavTVShowsAdapter.notifyDataSetChanged();
    }

    private void loadFavTVShows() {
        List<SerieDescrip> favSerieDescrips = Favoris.getFavTVShowBriefs(getContext());
        if (favSerieDescrips.isEmpty()) {
            mEmptyLayout.setVisibility(View.VISIBLE);
            return;
        }

        for (SerieDescrip serieDescrip : favSerieDescrips) {
            mFavTVShows.add(serieDescrip);
        }
        mFavTVShowsAdapter.notifyDataSetChanged();
    }

}
