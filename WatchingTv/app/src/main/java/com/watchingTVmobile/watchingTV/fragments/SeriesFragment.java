package com.watchingTVmobile.watchingTV.fragments;

import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.support.design.widget.Snackbar;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.LinearSnapHelper;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;


import com.watchingTVmobile.watchingTV.R;
import com.watchingTVmobile.watchingTV.activities.TousSeriesActivity;
import com.watchingTVmobile.watchingTV.adapters.SerieDescripLargeAdapter;
import com.watchingTVmobile.watchingTV.adapters.SerieDescripSmallAdapter;
import com.watchingTVmobile.watchingTV.broadcastreceivers.ConnectivityBroadcastReceiver;
import com.watchingTVmobile.watchingTV.network.ApiClient;
import com.watchingTVmobile.watchingTV.network.ApiInterface;
import com.watchingTVmobile.watchingTV.network.series.SerieDiffAuj;
import com.watchingTVmobile.watchingTV.network.series.GenresList;
import com.watchingTVmobile.watchingTV.network.series.SerieDiffActuResponse;
import com.watchingTVmobile.watchingTV.network.series.SeriePopulaireResponse;
import com.watchingTVmobile.watchingTV.network.series.SerieDescrip;
import com.watchingTVmobile.watchingTV.utils.Constants;
import com.watchingTVmobile.watchingTV.utils.NetworkConnection;
import com.watchingTVmobile.watchingTV.utils.SeriesGenres;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


public class SeriesFragment extends Fragment {

    private ProgressBar mProgressBar;
    private boolean mAiringTodaySectionLoaded;
    private boolean mOnTheAirSectionLoaded;
    private boolean mPopularSectionLoaded;

    private FrameLayout mAiringTodayLayout;
    private TextView mAiringTodayViewAllTextView;
    private RecyclerView mAiringTodayRecyclerView;
    private List<SerieDescrip> mAiringTodayTVShows;
    private SerieDescripLargeAdapter mAiringTodayAdapter;

    private FrameLayout mOnTheAirLayout;
    private TextView mOnTheAirViewAllTextView;
    private RecyclerView mOnTheAirRecyclerView;
    private List<SerieDescrip> mOnTheAirTVShows;
    private SerieDescripSmallAdapter mOnTheAirAdapter;

    private FrameLayout mPopularLayout;
    private TextView mPopularViewAllTextView;
    private RecyclerView mPopularRecyclerView;
    private List<SerieDescrip> mPopularTVShows;
    private SerieDescripLargeAdapter mPopularAdapter;


    private Snackbar mConnectivitySnackbar;
    private ConnectivityBroadcastReceiver mConnectivityBroadcastReceiver;
    private boolean isBroadcastReceiverRegistered;
    private boolean isFragmentLoaded;
    private Call<GenresList> mGenresListCall;
    private Call<SerieDiffAuj> mAiringTodayTVShowsCall;
    private Call<SerieDiffActuResponse> mOnTheAirTVShowsCall;
    private Call<SeriePopulaireResponse> mPopularTVShowsCall;

    @Override
    public View onCreateView(LayoutInflater inflater,ViewGroup container,Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_serie, container, false);

        mProgressBar = (ProgressBar) view.findViewById(R.id.progress_bar);
        mProgressBar.setVisibility(View.GONE);
        mAiringTodaySectionLoaded = false;
        mOnTheAirSectionLoaded = false;
        mPopularSectionLoaded = false;

        mAiringTodayLayout = (FrameLayout) view.findViewById(R.id.layout_airing_today);
        mOnTheAirLayout = (FrameLayout) view.findViewById(R.id.layout_on_the_air);
        mPopularLayout = (FrameLayout) view.findViewById(R.id.layout_popular);

        mAiringTodayViewAllTextView = (TextView) view.findViewById(R.id.text_view_view_all_airing_today);
        mOnTheAirViewAllTextView = (TextView) view.findViewById(R.id.text_view_view_all_on_the_air);
        mPopularViewAllTextView = (TextView) view.findViewById(R.id.text_view_view_all_popular);

        mAiringTodayRecyclerView = (RecyclerView) view.findViewById(R.id.recycler_view_airing_today);
        (new LinearSnapHelper()).attachToRecyclerView(mAiringTodayRecyclerView);
        mOnTheAirRecyclerView = (RecyclerView) view.findViewById(R.id.recycler_view_on_the_air);
        mPopularRecyclerView = (RecyclerView) view.findViewById(R.id.recycler_view_popular);
        (new LinearSnapHelper()).attachToRecyclerView(mPopularRecyclerView);


        mAiringTodayTVShows = new ArrayList<>();
        mOnTheAirTVShows = new ArrayList<>();
        mPopularTVShows = new ArrayList<>();


        mAiringTodayAdapter = new SerieDescripLargeAdapter(getContext(), mAiringTodayTVShows);
        mOnTheAirAdapter = new SerieDescripSmallAdapter(getContext(), mOnTheAirTVShows);
        mPopularAdapter = new SerieDescripLargeAdapter(getContext(), mPopularTVShows);

        mAiringTodayRecyclerView.setAdapter(mAiringTodayAdapter);
        mAiringTodayRecyclerView.setLayoutManager(new LinearLayoutManager(getContext(), LinearLayoutManager.HORIZONTAL, false));

        mOnTheAirRecyclerView.setAdapter(mOnTheAirAdapter);
        mOnTheAirRecyclerView.setLayoutManager(new LinearLayoutManager(getContext(), LinearLayoutManager.HORIZONTAL, false));

        mPopularRecyclerView.setAdapter(mPopularAdapter);
        mPopularRecyclerView.setLayoutManager(new LinearLayoutManager(getContext(), LinearLayoutManager.HORIZONTAL, false));

        mAiringTodayViewAllTextView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (!NetworkConnection.isConnected(getContext())) {
                    Toast.makeText(getContext(), R.string.pas_connect, Toast.LENGTH_SHORT).show();
                    return;
                }
                Intent intent = new Intent(getContext(), TousSeriesActivity.class);
                intent.putExtra(Constants.VIEW_ALL_TV_SHOWS_TYPE, Constants.AIRING_TODAY_TV_SHOWS_TYPE);
                startActivity(intent);
            }
        });
        mOnTheAirViewAllTextView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (!NetworkConnection.isConnected(getContext())) {
                    Toast.makeText(getContext(), R.string.pas_connect, Toast.LENGTH_SHORT).show();
                    return;
                }
                Intent intent = new Intent(getContext(), TousSeriesActivity.class);
                intent.putExtra(Constants.VIEW_ALL_TV_SHOWS_TYPE, Constants.ON_THE_AIR_TV_SHOWS_TYPE);
                startActivity(intent);
            }
        });
        mPopularViewAllTextView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (!NetworkConnection.isConnected(getContext())) {
                    Toast.makeText(getContext(), R.string.pas_connect, Toast.LENGTH_SHORT).show();
                    return;
                }
                Intent intent = new Intent(getContext(), TousSeriesActivity.class);
                intent.putExtra(Constants.VIEW_ALL_TV_SHOWS_TYPE, Constants.POPULAR_TV_SHOWS_TYPE);
                startActivity(intent);
            }
        });

        if (NetworkConnection.isConnected(getContext())) {
            isFragmentLoaded = true;
            loadFragment();
        }

        return view;
    }

    @Override
    public void onStart() {
        super.onStart();

        mAiringTodayAdapter.notifyDataSetChanged();
        mOnTheAirAdapter.notifyDataSetChanged();
        mPopularAdapter.notifyDataSetChanged();
    }

    @Override
    public void onResume() {
        super.onResume();

        if (!isFragmentLoaded && !NetworkConnection.isConnected(getContext())) {
            mConnectivitySnackbar = Snackbar.make(getActivity().findViewById(R.id.main_activity_fragment_container), R.string.pas_connect, Snackbar.LENGTH_INDEFINITE);
            mConnectivitySnackbar.show();
            mConnectivityBroadcastReceiver = new ConnectivityBroadcastReceiver(new ConnectivityBroadcastReceiver.ConnectivityReceiverListener() {
                @Override
                public void onNetworkConnectionConnected() {
                    mConnectivitySnackbar.dismiss();
                    isFragmentLoaded = true;
                    loadFragment();
                    isBroadcastReceiverRegistered = false;
                    getActivity().unregisterReceiver(mConnectivityBroadcastReceiver);
                }
            });
            IntentFilter intentFilter = new IntentFilter("android.net.conn.CONNECTIVITY_CHANGE");
            isBroadcastReceiverRegistered = true;
            getActivity().registerReceiver(mConnectivityBroadcastReceiver, intentFilter);
        } else if (!isFragmentLoaded && NetworkConnection.isConnected(getContext())) {
            isFragmentLoaded = true;
            loadFragment();
        }
    }

    @Override
    public void onPause() {
        super.onPause();

        if (isBroadcastReceiverRegistered) {
            mConnectivitySnackbar.dismiss();
            isBroadcastReceiverRegistered = false;
            getActivity().unregisterReceiver(mConnectivityBroadcastReceiver);
        }
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();

        if (mGenresListCall != null) mGenresListCall.cancel();
        if (mAiringTodayTVShowsCall != null) mAiringTodayTVShowsCall.cancel();
        if (mOnTheAirTVShowsCall != null) mOnTheAirTVShowsCall.cancel();
        if (mPopularTVShowsCall != null) mPopularTVShowsCall.cancel();

    }

    private void loadFragment() {

        if (SeriesGenres.isGenresListLoaded()) {
            loadAiringTodayTVShows();
            loadOnTheAirTVShows();
            loadPopularTVShows();
        } else {
            ApiInterface apiService = ApiClient.getClient().create(ApiInterface.class);
            mProgressBar.setVisibility(View.VISIBLE);
            mGenresListCall = apiService.getTVShowGenresList(getResources().getString(R.string.MOVIE_DB_API_KEY),"fr");
            mGenresListCall.enqueue(new Callback<GenresList>() {
                @Override
                public void onResponse(Call<GenresList> call, Response<GenresList> response) {
                    if (!response.isSuccessful()) {
                        mGenresListCall = call.clone();
                        mGenresListCall.enqueue(this);
                        return;
                    }

                    if (response.body() == null) return;
                    if (response.body().getGenres() == null) return;

                    SeriesGenres.loadGenresList(response.body().getGenres());
                    loadAiringTodayTVShows();
                    loadOnTheAirTVShows();
                    loadPopularTVShows();

                }

                @Override
                public void onFailure(Call<GenresList> call, Throwable t) {

                }
            });
        }

    }

    private void loadAiringTodayTVShows() {
        final ApiInterface apiService = ApiClient.getClient().create(ApiInterface.class);
        mProgressBar.setVisibility(View.VISIBLE);
        mAiringTodayTVShowsCall = apiService.getAiringTodayTVShows(getResources().getString(R.string.MOVIE_DB_API_KEY), 1,"fr");
        mAiringTodayTVShowsCall.enqueue(new Callback<SerieDiffAuj>() {
            @Override
            public void onResponse(Call<SerieDiffAuj> call, Response<SerieDiffAuj> response) {
                if (!response.isSuccessful()) {
                    mAiringTodayTVShowsCall = call.clone();
                    mAiringTodayTVShowsCall.enqueue(this);
                    return;
                }

                if (response.body() == null) return;
                if (response.body().getResults() == null) return;

                mAiringTodaySectionLoaded = true;
                checkAllDataLoaded();
                for (SerieDescrip SerieDescrip : response.body().getResults()) {
                    if (SerieDescrip != null && SerieDescrip.getBackdropPath() != null)
                        mAiringTodayTVShows.add(SerieDescrip);
                }
                mAiringTodayAdapter.notifyDataSetChanged();
            }

            @Override
            public void onFailure(Call<SerieDiffAuj> call, Throwable t) {

            }
        });
    }

    private void loadOnTheAirTVShows() {
        ApiInterface apiService = ApiClient.getClient().create(ApiInterface.class);
        mProgressBar.setVisibility(View.VISIBLE);
        mOnTheAirTVShowsCall = apiService.getOnTheAirTVShows(getResources().getString(R.string.MOVIE_DB_API_KEY), 1,"fr");
        mOnTheAirTVShowsCall.enqueue(new Callback<SerieDiffActuResponse>() {
            @Override
            public void onResponse(Call<SerieDiffActuResponse> call, Response<SerieDiffActuResponse> response) {
                if (!response.isSuccessful()) {
                    mOnTheAirTVShowsCall = call.clone();
                    mOnTheAirTVShowsCall.enqueue(this);
                    return;
                }

                if (response.body() == null) return;
                if (response.body().getResults() == null) return;

                mOnTheAirSectionLoaded = true;
                checkAllDataLoaded();
                for (SerieDescrip SerieDescrip : response.body().getResults()) {
                    if (SerieDescrip != null && SerieDescrip.getPosterPath() != null)
                        mOnTheAirTVShows.add(SerieDescrip);
                }
                mOnTheAirAdapter.notifyDataSetChanged();
            }

            @Override
            public void onFailure(Call<SerieDiffActuResponse> call, Throwable t) {

            }
        });
    }

    private void loadPopularTVShows() {
        final ApiInterface apiService = ApiClient.getClient().create(ApiInterface.class);
        mProgressBar.setVisibility(View.VISIBLE);
        mPopularTVShowsCall = apiService.getPopularTVShows(getResources().getString(R.string.MOVIE_DB_API_KEY), 1,"fr");
        mPopularTVShowsCall.enqueue(new Callback<SeriePopulaireResponse>() {
            @Override
            public void onResponse(Call<SeriePopulaireResponse> call, Response<SeriePopulaireResponse> response) {
                if (!response.isSuccessful()) {
                    mPopularTVShowsCall = call.clone();
                    mPopularTVShowsCall.enqueue(this);
                    return;
                }

                if (response.body() == null) return;
                if (response.body().getResults() == null) return;

                mPopularSectionLoaded = true;
                checkAllDataLoaded();
                for (SerieDescrip SerieDescrip : response.body().getResults()) {
                    if (SerieDescrip != null && SerieDescrip.getBackdropPath() != null)
                        mPopularTVShows.add(SerieDescrip);
                }
                mPopularAdapter.notifyDataSetChanged();
            }

            @Override
            public void onFailure(Call<SeriePopulaireResponse> call, Throwable t) {

            }
        });
    }



    private void checkAllDataLoaded() {
        if (mAiringTodaySectionLoaded && mOnTheAirSectionLoaded && mPopularSectionLoaded) {
            mProgressBar.setVisibility(View.GONE);
            mAiringTodayLayout.setVisibility(View.VISIBLE);
            mAiringTodayRecyclerView.setVisibility(View.VISIBLE);
            mOnTheAirLayout.setVisibility(View.VISIBLE);
            mOnTheAirRecyclerView.setVisibility(View.VISIBLE);
            mPopularLayout.setVisibility(View.VISIBLE);
            mPopularRecyclerView.setVisibility(View.VISIBLE);

        }
    }
}
