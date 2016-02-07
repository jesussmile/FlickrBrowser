package com.example.pannam.flickrbrowser;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import org.w3c.dom.Text;

public class ViewPhotoDetailsActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.photo_details);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
//get intent on long click form mainactivity
        Intent intent = getIntent();
        Photo photo = (Photo) intent.getSerializableExtra(SearchActivity.PHOTO_TRANSFER);
        //use the textview just created to view detail view of photo
        TextView photoTitle = (TextView) findViewById(R.id.photo_title);
        photoTitle.setText("Title: " + photo.getTitle());

        TextView photoTags = (TextView) findViewById(R.id.photo_tag);
        photoTags.setText("Tags: " + photo.getTags());

        TextView photoAuthor = (TextView) findViewById(R.id.photo_author);
        photoAuthor.setText("Author " + photo.getAuthor());

        ImageView photoImage = (ImageView) findViewById(R.id.photo_image);
        Picasso.with(this).load(photo.getLink()).error(R.drawable.placeholder)
                .placeholder(R.drawable.placeholder).into(photoImage);


    }

}
