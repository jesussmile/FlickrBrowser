package com.example.pannam.flickrbrowser;

import android.content.Context;
import android.os.AsyncTask;
import android.util.Log;
import android.widget.Toast;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

/**
 * Created by pannam on 1/30/2016.
 */

enum DownloadStatus {
    IDLE, PROCESSING, NOT_INITIALISED, FAILED_OR_EMPTY, OK
}

public class GetRawData {
    //for logging
    private String LOG_TAG = GetRawData.class.getSimpleName();
    private String mRawUrl;
    private String mData;
    private DownloadStatus mDownloadStatus;

    //constructor
    //getting URL
    public GetRawData(String mRawUrl) {
        this.mRawUrl = mRawUrl;
        //status of download
        this.mDownloadStatus = DownloadStatus.IDLE;

    }

    //create setter for rawUrl in GetRawData.java


    public void setmRawUrl(String mRawUrl) {
        this.mRawUrl = mRawUrl;
    }

    //getters is used to access form outside of the class
    public String getmData() {
        return mData;
    }

    public DownloadStatus getmDownloadStatus() {
        return mDownloadStatus;
    }

    public void reset() {
        //reset the download status & url
        this.mDownloadStatus = DownloadStatus.IDLE;
        this.mRawUrl = null;
        this.mData = null;


    }

    //start to get data from mainactivity
    public void execute(){
        //process
        this.mDownloadStatus = DownloadStatus.PROCESSING;
        //invoke Asynk Task
        DownloadRawData downloadRawData = new DownloadRawData();
        //saved in constructure   public GetRawData(String mRawUrl) {
        downloadRawData.execute(mRawUrl);
    }

    //creating class to download
    public class DownloadRawData extends AsyncTask<String, Void, String> {

        //after connection is successful
        @Override
        protected void onPostExecute(String webData) {
            super.onPostExecute(webData);


            mData = webData;
            Log.v(LOG_TAG,"Data returned :" +mData);
            //mData will come back null only if the URL is not good
            if(mData == null){
                //check if url is empty or wrong
                if(mRawUrl== null){
                    mDownloadStatus = DownloadStatus.NOT_INITIALISED;
                }else{
                    mDownloadStatus = DownloadStatus.FAILED_OR_EMPTY;
                }
            }else{
                //success download
                mDownloadStatus = DownloadStatus.OK;





            }


        }



        @Override
        protected String doInBackground(String... params) {
            //Create Connection
            HttpURLConnection urlConnection = null;
            BufferedReader reader = null;
            //if there is nothing coming back from server return null
            if (params == null)
                return null;

            try {
                //starting connection and collecting data
                URL url = new URL(params[0]);
                urlConnection = (HttpURLConnection) url.openConnection();
                urlConnection.setRequestMethod("GET");
                urlConnection.connect();

                InputStream inputStream = urlConnection.getInputStream();
                //check if incoming stream is empty
                if (inputStream == null) {
                    return null;
                }
                StringBuffer buffer = new StringBuffer();
                reader = new BufferedReader(new InputStreamReader(inputStream));

                String line;
                //check if we are at the end of the file

                while ((line = reader.readLine()) != null) {
                    buffer.append(line + "\n");

                }
                return buffer.toString();

                //incase of error use IOException
            } catch (IOException e) {
                Log.e(LOG_TAG, "Error", e);
                return null;
            } finally {
                //close data and reader
                if (urlConnection != null) {
                    urlConnection.disconnect();
                }
                if (reader != null) {
                    try {
                        reader.close();
                    } catch (final IOException e) {
                        Log.e(LOG_TAG, "Error Closing Stream", e);

                    }
                }
            }


        }

    }
}
