package com.example.pannam.flickrbrowser;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.squareup.picasso.Picasso;

import java.util.List;

/**
 * Created by pannam on 2/3/2016.
 */
//adapter works in conjunction with data to produce views
//attaching Adapter to view holder we just created
public class FlickrRecyclerViewAdapter extends RecyclerView.Adapter<FlickrImageViewHolder> {
    private String LOG_TAG = FlickrRecyclerViewAdapter.class.getSimpleName();
    private List<Photo> mPhotoList;
    private Context mContext;

    public FlickrRecyclerViewAdapter(Context context, List<Photo> PhotosList) {
        mContext = context;
        this.mPhotoList = PhotosList;
    }

    @Override
    //basically creates the object
    public FlickrImageViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        //inflate Layout

        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.browse, null);
        //creates flicr view image holder object
        FlickrImageViewHolder flickrImageViewHolder = new FlickrImageViewHolder(view);


        //returns the object
        return flickrImageViewHolder;


    }

    @Override
    //updates an object on the screen
    public void onBindViewHolder(FlickrImageViewHolder holder, int position) {
        //position tells the index position that is drawn in the screen at this time
        Photo photoItem = mPhotoList.get(position);
        Picasso.with(mContext).load(photoItem.getmImages())
                //if error show this image
                .error(R.drawable.placeholder)
                //while downloading show this image
                .placeholder(R.drawable.placeholder)
                //it will then go to the thumbnail from browser.xml
                .into(holder.thumbnail);
        //FOR THE TITLE
        holder.mtitle.setText(photoItem.getmTitle());



    }


    @Override
    //returns how many items have been found in photo list
    public int getItemCount() {

        return (null != mPhotoList ? mPhotoList.size() : 0);
    }
    //creating this method so we can move the recycler create process from async task to oncreate
    //in mainactivity


    public void loadNewData(List<Photo>newPhotos){
        mPhotoList = newPhotos;
        //reprocess the intire file and redraw on the screen automatically
        notifyDataSetChanged();
    }

}
