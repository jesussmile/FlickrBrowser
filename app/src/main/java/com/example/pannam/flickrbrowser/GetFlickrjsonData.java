package com.example.pannam.flickrbrowser;

import android.net.Uri;
import android.util.Log;
import android.util.SparseIntArray;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by pannam on 2/1/2016.
 */

//access data from photo class etc.
//ACCESS getrawdata class without creating a connection
public class GetFlickrjsonData extends GetRawData {
    //create Log tag
    private String LOG_TAG = GetFlickrjsonData.class.getSimpleName();
    //store photo
    private List<Photo> mPhotos;
    //record the destination Uri
    private Uri mDestinationUri;

    //constructor
//to access in mainactivity
    public List<Photo> getMPhotos() {
        return mPhotos;
    }
    //matchAll is the tag that is present in the flickr website;
    //matchAll is tagMode

    public GetFlickrjsonData(String searchCriteria, boolean matchAll) {
        //adding super(null) because Getrawdata class requries a string mRawUrl
        //null will return null from  if (params == null)
        // return null;
        super(null);
        createAndUpdateUri(searchCriteria, matchAll);
        //after putting the json data in mphotos below we create a array list
        //we initialize mphotos
        mPhotos = new ArrayList<Photo>();
    }


    public void execute() {
        //set url in getrawdata.java
        //mRAWuRL
        //so the url is not null
        super.setmRawUrl(mDestinationUri.toString());
        DownloadJsonData downloadJsonData = new DownloadJsonData();
        Log.v(LOG_TAG, "Built Uri pannam = " + mDestinationUri.toString());
        downloadJsonData.execute(mDestinationUri.toString());

    }


    //Uri stands for uniform resource identifier
    //Uri is used to create a valid URL here
    //construct the url to download
    public boolean createAndUpdateUri(String searchCriteria, boolean matchAll) {
//https://www.flickr.com/services/feeds/docs/photos_public/ check the url to be clear about strings
        final String FLICKR_API_BASE_URL = "https://api.flickr.com/services/feeds/photos_public.gne";
        final String TAGS_PARAM = "tags";
        final String TAGMODE_PARAM = "tagmode";
        final String FORMAT_PARAM = "format";
        final String NO_JSON_CALLBACK_PARAM = "nojsoncallback";

        //store BUILD URL
        //https://api.flickr.com/services/feeds/photos_public.gne?tags=android,lollipop&format=json&nojsoncallback=1
        mDestinationUri = Uri.parse(FLICKR_API_BASE_URL).buildUpon()
                .appendQueryParameter(TAGS_PARAM, searchCriteria)
                .appendQueryParameter(TAGMODE_PARAM, matchAll ? "ALL" : "ANY")
                .appendQueryParameter(FORMAT_PARAM, "json")
                .appendQueryParameter(NO_JSON_CALLBACK_PARAM, "1")
                .build();

        //return true if url is not null
        return mDestinationUri != null;

    }

    //download data
    //from GetRawData class
    //public class DownloadRawData extends AsyncTask<String, Void, String> {

    public class DownloadJsonData extends DownloadRawData {
        //adding functionality & processing
        //create two methods

        //after download is complete
        @Override
        protected void onPostExecute(String webData) {
            super.onPostExecute(webData);
            processResult();
        }


        //2nd methods
        //downloads file in background
        //executes GetRawData.java class
        @Override
        protected String doInBackground(String... params) {
            //we getting URL & not parameters
            //we want the url to work in background
            String[] par = {mDestinationUri.toString()};

            return super.doInBackground(par);
        }
    }

    //after downloading we process the data
    private void processResult() {
        //if no connection
        if (getmDownloadStatus() != DownloadStatus.OK) {
            Log.e(LOG_TAG, "Error downloding raw file");
            return;


        }
        //got this from the callback (json) url there is a container items that has
        //all the parameters

        final String FLICKR_ITEMS = "items";
        final String FLICKR_TITLE = "title";
        final String FLICKR_MEDIA = "media";
        final String FLICKR_PHOTO_URL = "m";
        final String FLICKR_AUTHOR = "author";
        final String FLICKR_AUTHOR_ID = "author_id";
        final String FLICKR_LINK = "link";
        final String FLICKR_TAGS = "tags";


        //catch & retrieve the data

        try {
            //get data that has been downloaded from GetRawData.java

            JSONObject jsonData = new JSONObject(getmData());
            JSONArray itemArray = jsonData.getJSONArray(FLICKR_ITEMS);
            for (int i = 0; i < itemArray.length(); i++) {
                JSONObject jsonPhoto = itemArray.getJSONObject(i);
                // we go the first object in arry under items
                //add it to the string
                String title = jsonPhoto.getString(FLICKR_TITLE);
                String author = jsonPhoto.getString(FLICKR_AUTHOR);
                String authorId = jsonPhoto.getString(FLICKR_AUTHOR_ID);
              //flickr has a different way of showing photos it adds _b(larger photo) or _m(thumbnail) after the photo
                //so the previously assumed link is wrong
               // String link = jsonPhoto.getString(FLICKR_LINK);
                String tags = jsonPhoto.getString(FLICKR_TAGS);

                //there is a "m" in before URL.
                JSONObject jsonMedia = jsonPhoto.getJSONObject(FLICKR_MEDIA);
                String photoUrl = jsonMedia.getString(FLICKR_PHOTO_URL);
                String link = photoUrl.replaceFirst("_m.","_b.");

                //we have all the data to send to photo.java class
                Photo photoObject = new Photo(title, author, authorId, link, tags, photoUrl);
                //mPhotos is a list we created on top
                this.mPhotos.add(photoObject);


            }

            //print out photos to check if its working after creating the list
            //in Photo.java there is a method .toString which will create
            //a string
            for (Photo singlePhoto : mPhotos) {
                Log.v(LOG_TAG, singlePhoto.toString());
            }

            //catch error
        } catch (JSONException jsone) {
            jsone.printStackTrace();
            Log.e(LOG_TAG, "Error processing Json data");

        }

    }

}
