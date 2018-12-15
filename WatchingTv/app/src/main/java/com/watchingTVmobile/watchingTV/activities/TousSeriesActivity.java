package com.watchingTVmobile.watchingTV.activities;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;

import com.watchingTVmobile.watchingTV.R;
import com.watchingTVmobile.watchingTV.adapters.SerieDescripSmallAdapter;
import com.watchingTVmobile.watchingTV.network.ApiClient;
import com.watchingTVmobile.watchingTV.network.ApiInterface;
import com.watchingTVmobile.watchingTV.network.series.SerieDiffAuj;
import com.watchingTVmobile.watchingTV.network.series.SerieDiffActuResponse;
import com.watchingTVmobile.watchingTV.network.series.SeriePopulaireResponse;
import com.watchingTVmobile.watchingTV.network.series.SerieDescrip;
import com.watchingTVmobile.watchingTV.utils.Constants;

import java.util.ArrayList;
import java.util.List;


import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class TousSeriesActivity extends AppCompatActivity {


    private RecyclerView mRecyclerView;
    private List<SerieDescrip> mTVShows;
    private SerieDescripSmallAdapter mTVShowsAdapter;

    private int mTVShowType;

    private boolean pagesOver = false;
    private int presentPage = 1;
    private boolean loading = true;
    private int previousTotal = 0;
    private int visibleThreshold = 5;

    private Call<SerieDiffAuj> mAiringTodayTVShowsCall;
    private Call<SerieDiffActuResponse> mOnTheAirTVShowsCall;
    private Call<SeriePopulaireResponse> mPopularTVShowsCall;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_tous_series);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        Intent receivedIntent = getIntent();
        mTVShowType = receivedIntent.getIntExtra(Constants.VIEW_ALL_TV_SHOWS_TYPE, -1);

        if (mTVShowType == -1) finish();

        switch (mTVShowType) {
            case Constants.AIRING_TODAY_TV_SHOWS_TYPE:
                setTitle(R.string.tous_diff_aujo);
                break;
            case Constants.ON_THE_AIR_TV_SHOWS_TYPE:
                setTitle(R.string.tous_series_en_cours);
                break;
            case Constants.POPULAR_TV_SHOWS_TYPE:
                setTitle(R.string.tous_serie_populaire);
                break;

        }


        mRecyclerView = (RecyclerView) findViewById(R.id.recycler_view_view_all);
        mTVShows = new ArrayList<>();
        mTVShowsAdapter = new SerieDescripSmallAdapter(TousSeriesActivity.this, mTVShows);
        mRecyclerView.setAdapter(mTVShowsAdapter);
        final GridLayoutManager gridLayoutManager = new GridLayoutManager(TousSeriesActivity.this, 3);
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
                    loadTVShows(mTVShowType);
                    loading = true;
                }

            }
        });

        loadTVShows(mTVShowType);

    }

    @Override
    protected void onStart() {
        super.onStart();

        mTVShowsAdapter.notifyDataSetChanged();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();

        if (mAiringTodayTVShowsCall != null) mAiringTodayTVShowsCall.cancel();
        if (mOnTheAirTVShowsCall != null) mOnTheAirTVShowsCall.cancel();
        if (mPopularTVShowsCall != null) mPopularTVShowsCall.cancel();
    }

    private void loadTVShows(int tvShowType) {
        if (pagesOver) return;

        ApiInterface apiService = ApiClient.getClient().create(ApiInterface.class);


        switch (tvShowType) {
            case Constants.AIRING_TODAY_TV_SHOWS_TYPE:
                mAiringTodayTVShowsCall = apiService.getAiringTodayTVShows(getResources().getString(R.string.MOVIE_DB_API_KEY), presentPage,"fr");
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


                        for (SerieDescrip serieDescrip : response.body().getResults()) {
                            if (serieDescrip != null && serieDescrip.getName() != null && serieDescrip.getPosterPath() != null)
                                mTVShows.add(serieDescrip);
                        }
                        mTVShowsAdapter.notifyDataSetChanged();
                        if (response.body().getPage() == response.body().getTotalPages())
                            pagesOver = true;
                        else
                            presentPage++;
                    }

                    @Override
                    public void onFailure(Call<SerieDiffAuj> call, Throwable t) {

                    }
                });
                break;
            case Constants.ON_THE_AIR_TV_SHOWS_TYPE:
                mOnTheAirTVShowsCall = apiService.getOnTheAirTVShows(getResources().getString(R.string.MOVIE_DB_API_KEY), presentPage,"fr");
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


                        for (SerieDescrip serieDescrip : response.body().getResults()) {
                            if (serieDescrip != null && serieDescrip.getName() != null && serieDescrip.getPosterPath() != null)
                                mTVShows.add(serieDescrip);
                        }
                        mTVShowsAdapter.notifyDataSetChanged();
                        if (response.body().getPage() == response.body().getTotalPages())
                            pagesOver = true;
                        else
                            presentPage++;
                    }

                    @Override
                    public void onFailure(Call<SerieDiffActuResponse> call, Throwable t) {

                    }
                });
                break;
            case Constants.POPULAR_TV_SHOWS_TYPE:
                mPopularTVShowsCall = apiService.getPopularTVShows(getResources().getString(R.string.MOVIE_DB_API_KEY), presentPage,"fr");
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

                        for (SerieDescrip serieDescrip : response.body().getResults()) {
                            if (serieDescrip != null && serieDescrip.getName() != null && serieDescrip.getPosterPath() != null)
                                mTVShows.add(serieDescrip);
                        }
                        mTVShowsAdapter.notifyDataSetChanged();
                        if (response.body().getPage() == response.body().getTotalPages())
                            pagesOver = true;
                        else
                            presentPage++;
                    }

                    @Override
                    public void onFailure(Call<SeriePopulaireResponse> call, Throwable t) {

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
