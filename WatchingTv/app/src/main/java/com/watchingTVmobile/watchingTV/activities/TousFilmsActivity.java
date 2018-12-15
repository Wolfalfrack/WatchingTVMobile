package com.watchingTVmobile.watchingTV.activities;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;

import com.watchingTVmobile.watchingTV.R;
import com.watchingTVmobile.watchingTV.adapters.FilmsDescripSmallAdapter;
import com.watchingTVmobile.watchingTV.network.ApiClient;
import com.watchingTVmobile.watchingTV.network.ApiInterface;
import com.watchingTVmobile.watchingTV.network.films.FilmDescrip;
import com.watchingTVmobile.watchingTV.network.films.FilmsSortisResponse;
import com.watchingTVmobile.watchingTV.network.films.FilmsPopulairesResponse;
import com.watchingTVmobile.watchingTV.network.films.FilmsVenirResponse;
import com.watchingTVmobile.watchingTV.utils.Constants;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class TousFilmsActivity extends AppCompatActivity {


    private RecyclerView mRecyclerView;
    private List<FilmDescrip> mMovies;
    private FilmsDescripSmallAdapter mMoviesAdapter;

    private int mMovieType;

    private boolean pagesOver = false;
    private int presentPage = 1;
    private boolean loading = true;
    private int previousTotal = 0;
    private int visibleThreshold = 5;

    private Call<FilmsSortisResponse> mNowShowingMoviesCall;
    private Call<FilmsPopulairesResponse> mPopularMoviesCall;
    private Call<FilmsVenirResponse> mUpcomingMoviesCall;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_tous_films);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        Intent receivedIntent = getIntent();
        mMovieType = receivedIntent.getIntExtra(Constants.VIEW_ALL_MOVIES_TYPE, -1);

        if (mMovieType == -1) finish();

        switch (mMovieType) {
            case Constants.NOW_SHOWING_MOVIES_TYPE:
                setTitle(R.string.tous_film_sorti);
                break;
            case Constants.POPULAR_MOVIES_TYPE:
                setTitle(R.string.film_populaire);
                break;
            case Constants.UPCOMING_MOVIES_TYPE:
                setTitle(R.string.films_a_venir);
                break;
        }


        mRecyclerView = (RecyclerView) findViewById(R.id.recycler_view_view_all);
        mMovies = new ArrayList<>();
        mMoviesAdapter = new FilmsDescripSmallAdapter(TousFilmsActivity.this, mMovies);
        mRecyclerView.setAdapter(mMoviesAdapter);
        final GridLayoutManager gridLayoutManager = new GridLayoutManager(TousFilmsActivity.this, 3);
        mRecyclerView.setLayoutManager(gridLayoutManager);
        mRecyclerView.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrolled(RecyclerView recyclerView, int dx, int dy) {

                int visibleItemCount = gridLayoutManager.getChildCount();
                int totalItemCount = gridLayoutManager.getItemCount();
                int firstVisibleItem = gridLayoutManager.findFirstVisibleItemPosition();

                if (loading) {
                    if (totalItemCount > previousTotal) {
                        loading = false;
                        previousTotal = totalItemCount;
                    }
                }
                if (!loading && (totalItemCount - visibleItemCount) <= (firstVisibleItem + visibleThreshold)) {
                    loadMovies(mMovieType);
                    loading = true;
                }

            }
        });

        loadMovies(mMovieType);

    }

    @Override
    protected void onStart() {
        super.onStart();

        mMoviesAdapter.notifyDataSetChanged();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();

        if (mNowShowingMoviesCall != null) mNowShowingMoviesCall.cancel();
        if (mPopularMoviesCall != null) mPopularMoviesCall.cancel();
        if (mUpcomingMoviesCall != null) mUpcomingMoviesCall.cancel();
    }

    private void loadMovies(int movieType) {
        if (pagesOver) return;

        ApiInterface apiService = ApiClient.getClient().create(ApiInterface.class);


        switch (movieType) {
            case Constants.NOW_SHOWING_MOVIES_TYPE:
                mNowShowingMoviesCall = apiService.getNowShowingMovies(getResources().getString(R.string.MOVIE_DB_API_KEY), presentPage, "fr");
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

//                        mSmoothProgressBar.progressiveStop();
                        for (FilmDescrip filmDescrip : response.body().getResults()) {
                            if (filmDescrip != null && filmDescrip.getTitle() != null && filmDescrip.getPosterPath() != null)
                                mMovies.add(filmDescrip);
                        }
                        mMoviesAdapter.notifyDataSetChanged();
                        if (response.body().getPage() == response.body().getTotalPages())
                            pagesOver = true;
                        else
                            presentPage++;
                    }

                    @Override
                    public void onFailure(Call<FilmsSortisResponse> call, Throwable t) {

                    }
                });
                break;
            case Constants.POPULAR_MOVIES_TYPE:
                mPopularMoviesCall = apiService.getPopularMovies(getResources().getString(R.string.MOVIE_DB_API_KEY), presentPage, "fr");
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

//                        mSmoothProgressBar.progressiveStop();
                        for (FilmDescrip filmDescrip : response.body().getResults()) {
                            if (filmDescrip != null && filmDescrip.getTitle() != null && filmDescrip.getPosterPath() != null)
                                mMovies.add(filmDescrip);
                        }
                        mMoviesAdapter.notifyDataSetChanged();
                        if (response.body().getPage() == response.body().getTotalPages())
                            pagesOver = true;
                        else
                            presentPage++;
                    }

                    @Override
                    public void onFailure(Call<FilmsPopulairesResponse> call, Throwable t) {

                    }
                });
                break;
            case Constants.UPCOMING_MOVIES_TYPE:
                mUpcomingMoviesCall = apiService.getUpcomingMovies(getResources().getString(R.string.MOVIE_DB_API_KEY), presentPage, "fr");
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

//                        mSmoothProgressBar.progressiveStop();
                        for (FilmDescrip filmDescrip : response.body().getResults()) {
                            if (filmDescrip != null && filmDescrip.getTitle() != null && filmDescrip.getPosterPath() != null)
                                mMovies.add(filmDescrip);
                        }
                        mMoviesAdapter.notifyDataSetChanged();
                        if (response.body().getPage() == response.body().getTotalPages())
                            pagesOver = true;
                        else
                            presentPage++;
                    }

                    @Override
                    public void onFailure(Call<FilmsVenirResponse> call, Throwable t) {

                    }
                });
                break;

        }
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == android.R.id.home) {
            onBackPressed();
        }
        return super.onOptionsItemSelected(item);
    }

}
