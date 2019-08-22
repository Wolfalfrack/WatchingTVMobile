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
import com.watchingTVmobile.watchingTV.activities.TousFilmsActivity;
import com.watchingTVmobile.watchingTV.adapters.FilmsDescripLargeAdapter;
import com.watchingTVmobile.watchingTV.adapters.FilmsDescripSmallAdapter;
import com.watchingTVmobile.watchingTV.broadcastreceivers.ConnectivityBroadcastReceiver;
import com.watchingTVmobile.watchingTV.network.ApiClient;
import com.watchingTVmobile.watchingTV.network.ApiInterface;
import com.watchingTVmobile.watchingTV.network.films.GenresList;
import com.watchingTVmobile.watchingTV.network.films.FilmDescrip;
import com.watchingTVmobile.watchingTV.network.films.FilmsSortisResponse;
import com.watchingTVmobile.watchingTV.network.films.FilmsPopulairesResponse;
import com.watchingTVmobile.watchingTV.network.films.FilmsVenirResponse;
import com.watchingTVmobile.watchingTV.utils.Constants;
import com.watchingTVmobile.watchingTV.utils.FilmsGenres;
import com.watchingTVmobile.watchingTV.utils.NetworkConnection;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


public class FilmsFragment extends Fragment {

    private ProgressBar mProgressBar;
    private boolean mNowShowingSectionLoaded;
    private boolean mPopularSectionLoaded;
    private boolean mUpcomingSectionLoaded;

    private FrameLayout mNowShowingLayout;
    private TextView mNowShowingViewAllTextView;
    private RecyclerView mNowShowingRecyclerView;
    private List<FilmDescrip> mNowShowingMovies;
    private FilmsDescripLargeAdapter mNowShowingAdapter;

    private FrameLayout mPopularLayout;
    private TextView mPopularViewAllTextView;
    private RecyclerView mPopularRecyclerView;
    private List<FilmDescrip> mPopularMovies;
    private FilmsDescripSmallAdapter mPopularAdapter;

    private FrameLayout mUpcomingLayout;
    private TextView mUpcomingViewAllTextView;
    private RecyclerView mUpcomingRecyclerView;
    private List<FilmDescrip> mUpcomingMovies;
    private FilmsDescripLargeAdapter mUpcomingAdapter;


    private Snackbar mConnectivitySnackbar;
    private ConnectivityBroadcastReceiver mConnectivityBroadcastReceiver;
    private boolean isBroadcastReceiverRegistered;
    private boolean isFragmentLoaded;
    private Call<GenresList> mGenresListCall;
    private Call<FilmsSortisResponse> mNowShowingMoviesCall;
    private Call<FilmsPopulairesResponse> mPopularMoviesCall;
    private Call<FilmsVenirResponse> mUpcomingMoviesCall;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_films, container, false);

        mProgressBar = (ProgressBar) view.findViewById(R.id.progress_bar);
        mProgressBar.setVisibility(View.GONE);
        mNowShowingSectionLoaded = false;
        mPopularSectionLoaded = false;
        mUpcomingSectionLoaded = false;

        mNowShowingLayout = (FrameLayout) view.findViewById(R.id.layout_now_showing);
        mPopularLayout = (FrameLayout) view.findViewById(R.id.layout_popular);
        mUpcomingLayout = (FrameLayout) view.findViewById(R.id.layout_upcoming);

        mNowShowingViewAllTextView = (TextView) view.findViewById(R.id.text_view_view_all_now_showing);
        mPopularViewAllTextView = (TextView) view.findViewById(R.id.text_view_view_all_popular);
        mUpcomingViewAllTextView = (TextView) view.findViewById(R.id.text_view_view_all_upcoming);

        mNowShowingRecyclerView = (RecyclerView) view.findViewById(R.id.recycler_view_now_showing);
        (new LinearSnapHelper()).attachToRecyclerView(mNowShowingRecyclerView);
        mPopularRecyclerView = (RecyclerView) view.findViewById(R.id.recycler_view_popular);
        mUpcomingRecyclerView = (RecyclerView) view.findViewById(R.id.recycler_view_upcoming);
        (new LinearSnapHelper()).attachToRecyclerView(mUpcomingRecyclerView);


        mNowShowingMovies = new ArrayList<>();
        mPopularMovies = new ArrayList<>();
        mUpcomingMovies = new ArrayList<>();

        mNowShowingAdapter = new FilmsDescripLargeAdapter(getContext(), mNowShowingMovies);
        mPopularAdapter = new FilmsDescripSmallAdapter(getContext(), mPopularMovies);
        mUpcomingAdapter = new FilmsDescripLargeAdapter(getContext(), mUpcomingMovies);

        mNowShowingRecyclerView.setAdapter(mNowShowingAdapter);
        mNowShowingRecyclerView.setLayoutManager(new LinearLayoutManager(getContext(), LinearLayoutManager.HORIZONTAL, false));

        mPopularRecyclerView.setAdapter(mPopularAdapter);
        mPopularRecyclerView.setLayoutManager(new LinearLayoutManager(getContext(), LinearLayoutManager.HORIZONTAL, false));

        mUpcomingRecyclerView.setAdapter(mUpcomingAdapter);
        mUpcomingRecyclerView.setLayoutManager(new LinearLayoutManager(getContext(), LinearLayoutManager.HORIZONTAL, false));


        mNowShowingViewAllTextView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (!NetworkConnection.isConnected(getContext())) {
                    Toast.makeText(getContext(), R.string.pas_connect, Toast.LENGTH_SHORT).show();
                    return;
                }
                Intent intent = new Intent(getContext(), TousFilmsActivity.class);
                intent.putExtra(Constants.VIEW_ALL_MOVIES_TYPE, Constants.NOW_SHOWING_MOVIES_TYPE);
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
                Intent intent = new Intent(getContext(), TousFilmsActivity.class);
                intent.putExtra(Constants.VIEW_ALL_MOVIES_TYPE, Constants.POPULAR_MOVIES_TYPE);
                startActivity(intent);
            }
        });
        mUpcomingViewAllTextView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (!NetworkConnection.isConnected(getContext())) {
                    Toast.makeText(getContext(), R.string.pas_connect, Toast.LENGTH_SHORT).show();
                    return;
                }
                Intent intent = new Intent(getContext(), TousFilmsActivity.class);
                intent.putExtra(Constants.VIEW_ALL_MOVIES_TYPE, Constants.UPCOMING_MOVIES_TYPE);
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

        mNowShowingAdapter.notifyDataSetChanged();
        mPopularAdapter.notifyDataSetChanged();
        mUpcomingAdapter.notifyDataSetChanged();
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
        if (mNowShowingMoviesCall != null) mNowShowingMoviesCall.cancel();
        if (mPopularMoviesCall != null) mPopularMoviesCall.cancel();
        if (mUpcomingMoviesCall != null) mUpcomingMoviesCall.cancel();

    }

    private void loadFragment() {

        if (FilmsGenres.isGenresListLoaded()) {
            loadNowShowingMovies();
            loadPopularMovies();
            loadUpcomingMovies();
        } else {
            ApiInterface apiService = ApiClient.getClient().create(ApiInterface.class);
            mProgressBar.setVisibility(View.VISIBLE);
            mGenresListCall = apiService.getMovieGenresList(getResources().getString(R.string.MOVIE_DB_API_KEY),"fr");
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

                    FilmsGenres.loadGenresList(response.body().getGenres());
                    loadNowShowingMovies();
                    loadPopularMovies();
                    loadUpcomingMovies();

                }

                @Override
                public void onFailure(Call<GenresList> call, Throwable t) {

                }
            });
        }

    }

    private void loadNowShowingMovies() {
        ApiInterface apiService = ApiClient.getClient().create(ApiInterface.class);
        mProgressBar.setVisibility(View.VISIBLE);
        mNowShowingMoviesCall = apiService.getNowShowingMovies(getResources().getString(R.string.MOVIE_DB_API_KEY), 1, "fr");
        mNowShowingMoviesCall.enqueue(new Callback<FilmsSortisResponse>() {
            @Override
            public void onResponse(Call<FilmsSortisResponse> call, Response<FilmsSortisResponse> response) {
                if (!response.isSuccessful()) {
                    mNowShowingMoviesCall = call.clone();
                    mNowShowingMoviesCall.enqueue(this);
                    return;
                }

                if (response.body() == null) return;
                if (response.body().getResults() == null) return;

                mNowShowingSectionLoaded = true;
                checkAllDataLoaded();
                for (FilmDescrip filmDescrip : response.body().getResults()) {
                    if (filmDescrip != null && filmDescrip.getBackdropPath() != null)
                        mNowShowingMovies.add(filmDescrip);
                }
                mNowShowingAdapter.notifyDataSetChanged();
            }

            @Override
            public void onFailure(Call<FilmsSortisResponse> call, Throwable t) {

            }
        });
    }

    private void loadPopularMovies() {
        ApiInterface apiService = ApiClient.getClient().create(ApiInterface.class);
        mProgressBar.setVisibility(View.VISIBLE);
        mPopularMoviesCall = apiService.getPopularMovies(getResources().getString(R.string.MOVIE_DB_API_KEY), 1, "fr");
        mPopularMoviesCall.enqueue(new Callback<FilmsPopulairesResponse>() {
            @Override
            public void onResponse(Call<FilmsPopulairesResponse> call, Response<FilmsPopulairesResponse> response) {
                if (!response.isSuccessful()) {
                    mPopularMoviesCall = call.clone();
                    mPopularMoviesCall.enqueue(this);
                    return;
                }

                if (response.body() == null) return;
                if (response.body().getResults() == null) return;

                mPopularSectionLoaded = true;
                checkAllDataLoaded();
                for (FilmDescrip filmDescrip : response.body().getResults()) {
                    if (filmDescrip != null && filmDescrip.getPosterPath() != null)
                        mPopularMovies.add(filmDescrip);
                }
                mPopularAdapter.notifyDataSetChanged();
            }

            @Override
            public void onFailure(Call<FilmsPopulairesResponse> call, Throwable t) {

            }
        });
    }

    private void loadUpcomingMovies() {
        ApiInterface apiService = ApiClient.getClient().create(ApiInterface.class);
        mProgressBar.setVisibility(View.VISIBLE);
        mUpcomingMoviesCall = apiService.getUpcomingMovies(getResources().getString(R.string.MOVIE_DB_API_KEY), 1, "fr");
        mUpcomingMoviesCall.enqueue(new Callback<FilmsVenirResponse>() {
            @Override
            public void onResponse(Call<FilmsVenirResponse> call, Response<FilmsVenirResponse> response) {
                if (!response.isSuccessful()) {
                    mUpcomingMoviesCall = call.clone();
                    mUpcomingMoviesCall.enqueue(this);
                    return;
                }

                if (response.body() == null) return;
                if (response.body().getResults() == null) return;

                mUpcomingSectionLoaded = true;
                checkAllDataLoaded();
                for (FilmDescrip filmDescrip : response.body().getResults()) {
                    if (filmDescrip != null && filmDescrip.getBackdropPath() != null)
                        mUpcomingMovies.add(filmDescrip);
                }
                mUpcomingAdapter.notifyDataSetChanged();
            }

            @Override
            public void onFailure(Call<FilmsVenirResponse> call, Throwable t) {

            }
        });
    }

    private void checkAllDataLoaded() {
        if (mNowShowingSectionLoaded && mPopularSectionLoaded && mUpcomingSectionLoaded) {
            mProgressBar.setVisibility(View.GONE);
            mNowShowingLayout.setVisibility(View.VISIBLE);
            mNowShowingRecyclerView.setVisibility(View.VISIBLE);
            mPopularLayout.setVisibility(View.VISIBLE);
            mPopularRecyclerView.setVisibility(View.VISIBLE);
            mUpcomingLayout.setVisibility(View.VISIBLE);
            mUpcomingRecyclerView.setVisibility(View.VISIBLE);
        }
    }
}
