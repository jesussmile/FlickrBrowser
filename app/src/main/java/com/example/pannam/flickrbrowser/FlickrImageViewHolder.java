package com.example.pannam.flickrbrowser;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

/**
 * Created by pannam on 2/3/2016.
 */
//creating a class to extend Recycler view to show images etc
public class FlickrImageViewHolder extends RecyclerView.ViewHolder {

    //create two variable thumbnail & title to use in browse.xml
    protected ImageView thumbnail;
    protected TextView mtitle;

    //create constructer
    public FlickrImageViewHolder(View view) {
        super(view);
        //attach image view to thumbnail from brwoser.xml & same for textView
        this.thumbnail = (ImageView)view.findViewById(R.id.thumbnail);
        this.mtitle = (TextView)view.findViewById(R.id.mtitle);

    }
}
