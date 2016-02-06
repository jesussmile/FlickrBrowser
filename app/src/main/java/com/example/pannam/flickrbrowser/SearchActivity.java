package com.example.pannam.flickrbrowser;

import android.app.SearchManager;
import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.SearchView;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;

public class SearchActivity extends AppCompatActivity {
    private SearchView mSearchView;

    public static final String FLICKR_QUERY = "FLICKR_QUERY";
    //we dont have  a base activity as mentioned in the tutorial hence we are adding it here
    public static final String PHOTO_TRANSFER = "PHOTO_TRANSFER";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {


        getMenuInflater().inflate(R.menu.menu_search, menu);
        final MenuItem searchItem = menu.findItem(R.id.search_view);
        mSearchView = (SearchView) searchItem.getActionView();
        SearchManager searchManager = (SearchManager) getSystemService(Context.SEARCH_SERVICE);
        mSearchView.setSearchableInfo(searchManager.getSearchableInfo(getComponentName()));
        mSearchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                //save wht user has typed

                SharedPreferences sharedPref = PreferenceManager.getDefaultSharedPreferences(getApplicationContext());
                //commit is saved
                sharedPref.edit().putString(FLICKR_QUERY, query).apply();
                // clean the textview
                mSearchView.clearFocus();
                finish();
                //true means we submitted successfully
                return true;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                //return true as we donet want any processing
                return true;
            }
        });

        mSearchView.setOnCloseListener(new SearchView.OnCloseListener() {
            @Override
            public boolean onClose() {
                finish();
                return false;
            }
        });
        return true;

    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {


        int id = item.getItemId();
        if (id == R.id.action_settings) {
            return true;

        }
        return super.onOptionsItemSelected(item);
    }
}
