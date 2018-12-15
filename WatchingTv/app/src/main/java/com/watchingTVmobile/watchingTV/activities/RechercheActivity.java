package com.watchingTVmobile.watchingTV.activities;

import android.app.LoaderManager;
import android.content.Intent;
import android.content.Loader;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;

import com.watchingTVmobile.watchingTV.R;
import com.watchingTVmobile.watchingTV.adapters.RechercheResultatAdapter;
import com.watchingTVmobile.watchingTV.network.recherche.RechercheAsyncTaskLoader;
import com.watchingTVmobile.watchingTV.network.recherche.RechercheResponse;
import com.watchingTVmobile.watchingTV.network.recherche.RechercheResultat;
import com.watchingTVmobile.watchingTV.utils.Constants;

import java.util.ArrayList;
import java.util.List;


public class RechercheActivity extends AppCompatActivity {

    private String mQuery;

    private RecyclerView mSearchResultsRecyclerView;
    private List<RechercheResultat> mRechercheResultats;
    private RechercheResultatAdapter mRechercheResultatAdapter;

    private TextView mEmptyTextView;

    private boolean pagesOver = false;
    private int presentPage = 1;
    private boolean loading = true;
    private int previousTotal = 0;
    private int visibleThreshold = 5;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_recherche);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        Intent receivedIntent = getIntent();
        mQuery = receivedIntent.getStringExtra(Constants.QUERY);

        if (mQuery == null || mQuery.trim().isEmpty()) finish();

        setTitle(mQuery);

        mEmptyTextView = (TextView) findViewById(R.id.text_view_empty_search);

        mSearchResultsRecyclerView = (RecyclerView) findViewById(R.id.recycler_view_search);
        mRechercheResultats = new ArrayList<>();
        mRechercheResultatAdapter = new RechercheResultatAdapter(RechercheActivity.this, mRechercheResultats);
        mSearchResultsRecyclerView.setAdapter(mRechercheResultatAdapter);
        final LinearLayoutManager linearLayoutManager = new LinearLayoutManager(RechercheActivity.this, LinearLayoutManager.VERTICAL, false);
        mSearchResultsRecyclerView.setLayoutManager(linearLayoutManager);
        mSearchResultsRecyclerView.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrolled(RecyclerView recyclerView, int dx, int dy) {

                int visibleItemCount = linearLayoutManager.getChildCount();
                int totalItemCount = linearLayoutManager.getItemCount();
                int firstVisibleItem = linearLayoutManager.findFirstVisibleItemPosition();

                if (loading) {
                    if (totalItemCount > previousTotal) {
                        loading = false;
                        previousTotal = totalItemCount;
                    }
                }
                if (!loading && (totalItemCount - visibleItemCount) <= (firstVisibleItem + visibleThreshold)) {
                    loadSearchResults();
                    loading = true;
                }

            }
        });

        loadSearchResults();

    }

    private void loadSearchResults() {
        if (pagesOver) return;


        getLoaderManager().initLoader(presentPage, null, new LoaderManager.LoaderCallbacks<RechercheResponse>() {

            @Override
            public Loader<RechercheResponse> onCreateLoader(int i, Bundle bundle) {
                return new RechercheAsyncTaskLoader(RechercheActivity.this, mQuery, String.valueOf(presentPage));
            }

            @Override
            public void onLoadFinished(Loader<RechercheResponse> loader, RechercheResponse rechercheResponse) {

                if (rechercheResponse == null) return;
                if (rechercheResponse.getResults() == null) return;

                for (RechercheResultat rechercheResultat : rechercheResponse.getResults()) {
                    if (rechercheResultat != null)
                        mRechercheResultats.add(rechercheResultat);
                }
                mRechercheResultatAdapter.notifyDataSetChanged();
                if (mRechercheResultats.isEmpty()) mEmptyTextView.setVisibility(View.VISIBLE);
                if (rechercheResponse.getPage() == rechercheResponse.getTotalPages())
                    pagesOver = true;
                else
                    presentPage++;

            }

            @Override
            public void onLoaderReset(Loader<RechercheResponse> loader) {

            }
        }).forceLoad();

    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == android.R.id.home) {
            onBackPressed();
        }
        return super.onOptionsItemSelected(item);
    }

}
