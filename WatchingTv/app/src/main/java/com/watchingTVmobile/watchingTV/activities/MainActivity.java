package com.watchingTVmobile.watchingTV.activities;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.support.design.widget.NavigationView;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.SearchView;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Toast;

import com.watchingTVmobile.watchingTV.R;
import com.watchingTVmobile.watchingTV.fragments.ConnectionFragment;
import com.watchingTVmobile.watchingTV.fragments.FavorisFragment;
import com.watchingTVmobile.watchingTV.fragments.FilmsFragment;
import com.watchingTVmobile.watchingTV.fragments.InscriptionFragment;
import com.watchingTVmobile.watchingTV.fragments.IntentsFragment;
import com.watchingTVmobile.watchingTV.fragments.SeriesFragment;
import com.watchingTVmobile.watchingTV.utils.Constants;
import com.watchingTVmobile.watchingTV.utils.NetworkConnection;


public class MainActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener {

    private DrawerLayout mDrawer;
    private NavigationView mNavigationView;

    private boolean doubleBackToExitPressedOnce;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        mDrawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, mDrawer, toolbar, R.string.navigation_drawer_ouvrir, R.string.navigation_drawer_fermer);
        mDrawer.setDrawerListener(toggle);
        toggle.syncState();

        mNavigationView = (NavigationView) findViewById(R.id.nav_view);
        mNavigationView.setNavigationItemSelectedListener(MainActivity.this);

        mNavigationView.setCheckedItem(R.id.nav_movies);
        setTitle(R.string.films);
        setFragment(new FilmsFragment());
    }

    @Override
    public void onBackPressed() {
        mDrawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (mDrawer.isDrawerOpen(GravityCompat.START)) {
            mDrawer.closeDrawer(GravityCompat.START);
        } else {

            if (doubleBackToExitPressedOnce) {
                super.onBackPressed();
                return;
            }

            doubleBackToExitPressedOnce = true;
            Toast.makeText(this, R.string.app_quit, Toast.LENGTH_SHORT).show();

            new Handler().postDelayed(new Runnable() {
                @Override
                public void run() {
                    doubleBackToExitPressedOnce = false;
                }
            }, 2000);
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.main, menu);

        final MenuItem searchMenuItem = menu.findItem(R.id.action_search);
        final SearchView searchView = (SearchView) searchMenuItem.getActionView();
        searchView.setQueryHint(getResources().getString(R.string.recherche_film_serie));

        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                if (!NetworkConnection.isConnected(MainActivity.this)) {
                    Toast.makeText(MainActivity.this, R.string.pas_connect, Toast.LENGTH_SHORT).show();
                    return true;
                }
                Intent intent = new Intent(MainActivity.this, RechercheActivity.class);
                intent.putExtra(Constants.QUERY, query);
                startActivity(intent);
                searchMenuItem.collapseActionView();
                return true;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                return false;
            }
        });

        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        return false;
    }

    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        int id = item.getItemId();
        mDrawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        mDrawer.closeDrawer(GravityCompat.START);

        switch (id) {
            case R.id.nav_movies:
                setTitle(R.string.films);
                setFragment(new FilmsFragment());
                return true;
            case R.id.nav_tv_shows:
                setTitle(R.string.série);
                setFragment(new SeriesFragment());
                return true;
            case R.id.nav_favorites:
                setTitle(R.string.favoris);
                setFragment(new FavorisFragment());
                return true;
            case R.id.nav_connection:
                setTitle("Connection");
                setFragment(new ConnectionFragment());
                return true;
            case R.id.nav_inscription:
                setTitle("Inscription");
                setFragment(new InscriptionFragment());
                return true;
            case R.id.intents:
                setTitle("Intents");
                setFragment(new IntentsFragment());
                return true;
        }

        return false;
    }

    private void setFragment(Fragment fragment) {
        FragmentManager fragmentManager = getSupportFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        fragmentTransaction.replace(R.id.main_activity_fragment_container, fragment);
        fragmentTransaction.commit();
    }

    public void process(View view){
        Intent intent = null;
        if(view.getId()==R.id.launchMap){
            intent = new Intent(Intent.ACTION_VIEW);
            intent.setData(Uri.parse("geo:0,0?q=cinema"));
            startActivity(intent);
        }
        if(view.getId()==R.id.launchMarket) {
            intent = new Intent(Intent.ACTION_VIEW);
            intent.setData(Uri.parse("market://search?q=tvtime"));
            startActivity(intent);
        }
//        if(view.getId()==R.id.sendMail){
//            intent = new Intent(Intent.ACTION_VIEW);
//            intent.setData(Uri.parse("market://search?q=tvtime"));
//            startActivity(intent);
//        }
    }

}
