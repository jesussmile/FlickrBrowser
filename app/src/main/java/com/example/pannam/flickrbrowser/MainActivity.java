package com.example.pannam.flickrbrowser;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Process;
import android.preference.PreferenceManager;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {
    //for logging
    private static final String LOG_TAG = "MainActivity";
    //comes from Json parser
    private List<Photo> mPhotoList = new ArrayList<Photo>();
    private RecyclerView mRecyclerView;
    //adapter
    private FlickrRecyclerViewAdapter flickrRecyclerViewAdapter;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        //initiate recycler view
        mRecyclerView = (RecyclerView) findViewById(R.id.recyclerView);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(this));

        //initialize with empty set of data
        flickrRecyclerViewAdapter = new FlickrRecyclerViewAdapter(MainActivity.this, new ArrayList<Photo>());
        //attach adapter
        mRecyclerView.setAdapter(flickrRecyclerViewAdapter);
        //implement RecyclrItemClickListener

        mRecyclerView.addOnItemTouchListener(new RecyclerItemClickListener(this, mRecyclerView,
                new RecyclerItemClickListener.OnItemClickListener() {
                    @Override
                    public void onItemClick(View view, int position) {
                        Toast.makeText(MainActivity.this, "Short TAP", Toast.LENGTH_SHORT).show();
                    }

                    @Override
                    public void onItemLongClick(View view, int position) {
                       // Toast.makeText(MainActivity.this, "Long TAPPY", Toast.LENGTH_LONG).show();
                        Intent intent = new Intent(MainActivity.this,ViewPhotoDetailsActivity.class);
                        //put extra is telling before putting forward the intent we want to add something to it
                        intent.putExtra(SearchActivity.PHOTO_TRANSFER,flickrRecyclerViewAdapter.getPhoto(position));
                        startActivity(intent);
                    }
                }));


//        ProcessPhotos processPhotos = new ProcessPhotos("Burmese cat",true);
//        processPhotos.execute();


        //calling Getrawdata class
        //   GetRawData theRawData = new GetRawData("https://api.flickr.com/services/feeds/photos_public.gne?tags=android,lollipop&format=json&nojsoncallback=1");
        //checking
        //  GetFlickrjsonData jsonData = new GetFlickrjsonData("android, lollipop",true);
        //jsonData.execute();


    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        //using newly created menu option
        if (id == R.id.menu_search) {
            //run SearchActivity class
            Intent intent = new Intent(this, SearchActivity.class);
            startActivity(intent);
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    //CREATE async task within the method
    public class ProcessPhotos extends GetFlickrjsonData {

        //initialize json data
        public ProcessPhotos(String searchCriteria, boolean matchAll) {
            super(searchCriteria, matchAll);
        }

        public void execute() {
            super.execute();
            ProcessData processData = new ProcessData();
            processData.execute();
        }

        public class ProcessData extends DownloadJsonData {
            @Override
            protected void onPostExecute(String webData) {
                super.onPostExecute(webData);
//                flickrRecyclerViewAdapter = new FlickrRecyclerViewAdapter(MainActivity.this,getMPhotos());
//                mRecyclerView.setAdapter(flickrRecyclerViewAdapter);

                flickrRecyclerViewAdapter.loadNewData(getMPhotos());
            }
        }
    }


    @Override
    protected void onResume() {
        super.onResume();
        //on resume
        //getting this from onQueryTextSubmit in SearchActivity
//        if(flickrRecyclerViewAdapter != null){
        String query = getSavedPreferenceData(SearchActivity.FLICKR_QUERY);
        if (query.length() > 0) {
            ProcessPhotos processPhotos = new ProcessPhotos(query, true);
            processPhotos.execute();
        }
//        }

    }

    private String getSavedPreferenceData(String key) {
        SharedPreferences sharedPref = PreferenceManager.getDefaultSharedPreferences(getApplicationContext());
        //if key is empty it will give "" or the key itself
        return sharedPref.getString(key, "");

    }


}
